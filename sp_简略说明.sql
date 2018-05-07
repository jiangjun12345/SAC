--字符进行MD5加密
CREATE OR REPLACE FUNCTION MD5(
passwd IN VARCHAR2)
RETURN VARCHAR2
IS
retval varchar2(32);
BEGIN
retval := utl_raw.cast_to_raw(DBMS_OBFUSCATION_TOOLKIT.MD5(INPUT_STRING => passwd)) ;
RETURN LOWER(retval);
END;
--写日志
create or replace function sp_wlog(
                 id in number
               ,  v_sac_date in varchar2
               ,spname                     in      VARCHAR2
               , err_num                   in      NUMBER
               , err_msg                   in      VARCHAR2
               , sys_date                  in      DATE
               , start_date                in      DATE
               , info_message              in      VARCHAR2
      )
    return varchar2
     is
    begin
       insert into sac_sp_run_log(
          id,
          work_date,
          spname,
          err_num,
          err_msg ,
          sys_date,
          start_date,
          info_message,
          CREATE_TIME,
          memo
     ) values(
          id,
          v_sac_date,
          spname,
          err_num,
          err_msg ,
          sys_date,
          start_date,
          info_message,
          sysdate,
          ''
     );
     commit;
       return(v_sac_date);
    end;
  /*
  * SP_BFJ_BANK_DATA_INSERT:备付金银行数据生成
  * 从WY_CHNL_RECONCILIATION表中获取资金文件
  * 算借方贷方发生额时，要剔除流动利
  */
    
create or replace procedure SP_BFJ_BANK_DATA_INSERT(p_trx_date IN VARCHAR2,
                                               account_no        IN VARCHAR2,
                                               err_num     out number,
                                               err_msg     out varchar2) AS
  v_init_time  VARCHAR2(21); -- 初始化时间
  v_spname     VARCHAR2(32); -- 存储过程名
  v_start_date DATE; -- 系统开始时间
  v_step       VARCHAR2(4000); -- 步骤名
  v_seq        NUMBER; -- 查询sequence
  v_account_no    VARCHAR2(32);
  v_trx_date VARCHAR2(21);
  v_last_day   VARCHAR2(21);
  v_value     NUMBER;
  v_value_1     NUMBER;
BEGIN
  --------------------------- STEP_1 初始化变量 ---------------------------
  v_spname     := 'SP_BFJ_BANK_DATA_INSERT';
  v_start_date := SYSDATE;
  v_init_time  := to_char(sysdate - 1, 'YYYYmmdd');
  v_step       := 1.1;
  v_account_no := account_no;
  v_last_day   := to_char(sysdate - 2, 'YYYYmmdd');
  v_value      := 0;
  v_value_1    := 0;

  IF p_trx_date IS NOT NULL THEN
    v_trx_date := p_trx_date;
  ELSE
    v_trx_date := v_init_time;
  END IF;

 select SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL into v_seq from dual;

 select to_char((to_date(v_trx_date, 'yyyymmdd') - 1), 'yyyymmdd')
   into v_last_day
   from dual;

  delete from bfj_bank_data where trx_date = v_trx_date;
  delete from bfj_bank_diff_data where trx_date = v_trx_date;
  commit;

   insert into bfj_bank_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    CREDIT_AMOUNT,
    CREDIT_SUM,
    DEBIT_AMOUNT,
    DEBIT_SUM,
    BALANCE_AMOUNT,
    CREDIT_MOVE_AMOUNT,
    DEBIT_MOVE_AMOUNT,
    INTEREST_AMOUNT,
    POUNDAGE_AMOUNT
    )
select w.trx_date as trx_date, --交易日期
       b.account_no as account_no, --银行31code_id
      -- b.bank_code,
       max(b.ACCOUNT_NAME) as ACCOUNT_NAME,
       sum(case
             when w.direction = '1' and remark not like '%流动利%' then
              w.amount
             else
             0
           end) as credit_amount, --贷方发生额
       sum(case
             when w.direction = '1' and remark not like '%流动利%' then
              1
             else
              0
           end) as credit_sum, --贷方交易笔数
       sum(case
             when w.direction = '2' and remark not like '%流动利%' then
              w.amount
             else
             0
           end) as debit_amount, --借方发生额
       sum(case
             when w.direction = '2' and remark not like '%流动利%' then
              1
             else
              0
           end) as debit_sum, --借方交易笔数
       0 as balance_amount, --余额
       sum(case
             when  w.direction = '1' and (remark like '%资金调拨%'  or remark in ('存款','资金划拨'))  then
              amount
             else
            0
           end) as credit_move_amount, --资金调拨贷方费用
       sum(case
             when w.direction = '2' and (remark like '%资金调拨%' or remark in ('07')) then
              amount
             else
              0
           end) as debit_move_amount, --资金调拨借方费用
       sum(case
             when (remark like '%周期转账%' or remark like '%结息%') or
                  bus_type = '0031' then
              amount
             else
              0
           end) as interest_amount, --结息
       0 poundage_amount --手续费
  from (  select (case when chnl_no in ('99990310')
        then 'SPDB000'  --浦发
          when chnl_no in ('99920301','99910301','99990301')
            then 'BC00000'  --交行
         when chnl_no in ('99930105','99920105','99910105')
            then 'CCB0000'    -- 建行
         when chnl_no in ('20140828','20140827','20140826','20140822')
            then 'CMBC000'    -- 民生
            when chnl_no in ('99910104','99990104')
            then 'BOC0000'    --中行
              else '0000000'
           end) as chn_code,substr(l.trade_date,0,8) as trx_date ,l.*
 　from WY_CHNL_RECONCILIATION l) w,
 (select * from bfj_account_no_param  where memo = '人民币账号') b       --获取备付金账户
     where w.chn_code <> '0000000' --where w.trade_date = v_trx_date and w.chnl_no = v_account_no;
     and w.chn_code = b.bank_code
     and w.trx_date = v_trx_date
     and b.account_no = v_account_no
 group by b.account_no,b.bank_code, w.trx_date;
 commit;
  -- 目前只有中行银联的差异
  -- 插入中行银联的金额
  IF v_account_no = '453359895255' THEN
   insert into bfj_bank_diff_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    AMOUNT_SUM,
    COUNT_SUM
    )
    select
     v_trx_date as TRX_DATE,
     '453359895255' as ACCOUNT_NO,
     '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
     total_sum as  AMOUNT_SUM,
     total_num as COUNT_SUM
     from sac_chn_set_detail
     where chn_no = '8000000000056863000'
     and type = 'N'
     and sac_date = v_trx_date;
   END IF;

   select count(1) into v_value from  bfj_bank_diff_data where ACCOUNT_NO =v_account_no
   and TRX_DATE = v_trx_date ;


   --如果没有插入差异数据，则插入一条为0的记录
   IF v_value = 0 THEN
   insert into bfj_bank_diff_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    AMOUNT_SUM,
    COUNT_SUM
    )values(
      v_trx_date,
      v_account_no,
      '东方电子支付有限公司客户备付金',
      0 ,
      0
    );
  END IF;

  select count(1) into v_value_1 from  bfj_bank_data where ACCOUNT_NO =v_account_no
   and TRX_DATE = v_trx_date ;

   --如果没有数据，则插入一条为0的记录
   IF v_value_1 = 0 THEN
   insert into bfj_bank_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    CREDIT_AMOUNT,
    CREDIT_SUM,
    DEBIT_AMOUNT,
    DEBIT_SUM,
    BALANCE_AMOUNT,
    CREDIT_MOVE_AMOUNT,
    DEBIT_MOVE_AMOUNT,
    INTEREST_AMOUNT,
    POUNDAGE_AMOUNT
    )values(
      v_trx_date,
      v_account_no,
      '东方电子支付有限公司客户备付金',
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0
    );
  END IF;
 commit;


  err_num := 0;
  err_msg := 'Complete success!';
  v_step  := sp_wlog(v_seq,
                     v_trx_date,
                     UPPER(v_spname),
                     err_num,
                     err_msg,
                     SYSDATE,
                     v_start_date,
                      '[v_trx_date]:' || v_trx_date || '[elapsed]:' ||
                     TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    err_num := SQLCODE;
    err_msg := SUBSTR(SQLERRM, 1, 80);
    v_step  := sp_wlog(v_seq,
                       v_trx_date,
                       UPPER(v_spname),
                       err_num,
                       err_msg,
                       SYSDATE,
                       v_start_date,
                        '[v_trx_date]:' || v_trx_date || '[elapsed]:' ||
                       TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');
END;

create or replace procedure SP_BFJ_HISTORY_DATA_INSERT(p_trx_date IN VARCHAR2,
                                               account_no        IN VARCHAR2,
                                               err_num     out number,
                                               err_msg     out varchar2) AS
  v_init_time  VARCHAR2(21); -- 初始化时间
  v_spname     VARCHAR2(32); -- 存储过程名
  v_start_date DATE; -- 系统开始时间
  v_step       VARCHAR2(4000); -- 步骤名
  v_seq        NUMBER; -- 查询sequence
  v_account_no    VARCHAR2(32);
  v_trx_date VARCHAR2(21);
  v_last_day   VARCHAR2(21);
  v_value_5    NUMBER;
BEGIN
  --------------------------- STEP_1 初始化变量 ---------------------------
  v_spname     := 'SP_BFJ_HISTORY_DATA_INSERT';
  v_start_date := SYSDATE;
  v_init_time  := to_char(sysdate - 1, 'YYYYmmdd');
  v_step       := 1.1;
  v_account_no := account_no;
  v_last_day   := to_char(sysdate - 2, 'YYYYmmdd');

  IF p_trx_date IS NOT NULL THEN
    v_trx_date := p_trx_date;
  ELSE
    v_trx_date := v_init_time;
  END IF;

 select SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL into v_seq from dual;
 select to_char((to_date(v_trx_date,'yyyymmdd')-1),'yyyymmdd') into v_last_day from dual;

--删除中间表
delete from mid_bfj_bank_data;
delete from mid_bfj_bank_diff_data;
delete from mid_bfj_qjs_data;
delete from mid_bfj_qjs_diff_data;
delete from mid_bfj_specal_data;
delete from bfj_last_time_data where trx_date = v_trx_date and ACCOUNT_NO = v_account_no;
delete from bfj_history_data where trx_date = v_trx_date and ACCOUNT_NO = v_account_no;

commit;



insert into mid_bfj_bank_data   ----中间表
  (
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,CREDIT_AMOUNT
    ,CREDIT_SUM
    ,DEBIT_AMOUNT
    ,DEBIT_SUM
    ,BALANCE_AMOUNT
    ,CREDIT_MOVE_AMOUNT
    ,DEBIT_MOVE_AMOUNT
    ,INTEREST_AMOUNT
    ,POUNDAGE_AMOUNT
      )
      select
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,CREDIT_AMOUNT
    ,CREDIT_SUM
    ,DEBIT_AMOUNT
    ,DEBIT_SUM
    ,BALANCE_AMOUNT
    ,CREDIT_MOVE_AMOUNT
    ,DEBIT_MOVE_AMOUNT
    ,INTEREST_AMOUNT
    ,POUNDAGE_AMOUNT from  bfj_bank_data
    where TRX_DATE = v_trx_date
          and ACCOUNT_NO = v_account_no;

   insert into mid_bfj_bank_diff_data   ----中间表
  (
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,AMOUNT_SUM
    ,COUNT_SUM
      )
      select
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,AMOUNT_SUM
    ,COUNT_SUM  from  bfj_bank_diff_data
    where TRX_DATE = v_trx_date
          and ACCOUNT_NO = v_account_no;

   insert into mid_bfj_qjs_data   ----中间表
  (
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,DEBIT_AMOUNT
    ,CREDIT_AMOUNT
    ,BALANCE_AMOUNT
    ,CREDIT_MOVE_AMOUNT
    ,DEBIT_MOVE_AMOUNT
    ,INTEREST_AMOUNT
    ,POUNDAGE_AMOUNT
      )
      select
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,DEBIT_AMOUNT
    ,CREDIT_AMOUNT
    ,BALANCE_AMOUNT
    ,CREDIT_MOVE_AMOUNT
    ,DEBIT_MOVE_AMOUNT
    ,INTEREST_AMOUNT
    ,POUNDAGE_AMOUNT  from  bfj_qjs_data
    where TRX_DATE = v_trx_date
          and ACCOUNT_NO = v_account_no;

    insert into mid_bfj_qjs_diff_data   ----中间表
  (
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,CREDIT_AMOUNT
    ,DEBIT_AMOUNT
    ,BALANCE_AMOUNT
      )
      select
     TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,CREDIT_AMOUNT
    ,DEBIT_AMOUNT
    ,BALANCE_AMOUNT   from  bfj_qjs_diff_data
    where TRX_DATE = v_trx_date
          and ACCOUNT_NO = v_account_no;

    insert into MID_BFJ_SPECAL_DATA   ----中间表
  (
      TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,COL_0109C
    ,COL_0109D
    ,COL_0109E
    ,COL_0110H
    ,COL_0110I
    ,COL_0110N
    ,COL_0110O
    ,COL_0110T
    ,COL_0110U
    ,COL_0111K
    ,COL_0111L
    ,COL_0111M
    ,COL_0111O
    ,COL_0112I
    ,COL_0112J
    ,COL_0112K
    ,COL_0112F
    ,COL_0201D
    ,COL_0110B
    ,COL_0110C
    ,COL_0111I
      )
      select
     TRX_DATE
    ,ACCOUNT_NO
    ,ACCOUNT_NAME
    ,COL_0109C
    ,COL_0109D
    ,COL_0109E
    ,COL_0110H
    ,COL_0110I
    ,COL_0110N
    ,COL_0110O
    ,COL_0110T
    ,COL_0110U
    ,COL_0111K
    ,COL_0111L
    ,COL_0111M
    ,COL_0111O
    ,COL_0112I
    ,COL_0112J
    ,COL_0112K
    ,COL_0112F
    ,COL_0201D
    ,COL_0110B
    ,COL_0110C
    ,COL_0111I
      from  bfj_specal_data
    where TRX_DATE = v_trx_date
          and ACCOUNT_NO = v_account_no;

 insert into bfj_last_time_data   ---需要用到的前一天数据
  (
  TRX_DATE,
  ACCOUNT_NO,
  BANK_CODE,
  COL_0101K,
  COL_0109C,
  COL_0105G,
  COL_0111C,
  COL_0111J,
  COL_0111AB,
  BANK_BALANCE_AMOUNT
      )
   select
  v_trx_date as TRX_DATE,
  v_account_no as ACCOUNT_NO,
  ''as BANK_CODE,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_01' and COL_NAME = 'colK'
    then to_number(COL_VALUE)
      else 0
        end),0)) as  COL_0101K,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_09' and COL_NAME = 'colC'
    then to_number(COL_VALUE)
      else 0
        end),0)) as  COL_0109C,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_11' and COL_NAME = 'colC'
    then to_number(COL_VALUE)
      else 0
        end),0))  as COL_0105G,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_11' and COL_NAME = 'colC'
    then to_number(COL_VALUE)
      else 0
        end),0)) as COL_0111C,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_11' and COL_NAME = 'colJ'
    then to_number(COL_VALUE)
      else 0
        end),0))  as COL_0111J,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_11' and COL_NAME = 'colAB'
    then to_number(COL_VALUE)
      else 0
        end),0)) as COL_0111AB,
  to_char(nvl(sum(case when TABLE_CODE = 'Table1_11' and COL_NAME = 'colAB'
    then to_number(COL_VALUE)
      else 0
        end),0)) as BANK_BALANCE_AMOUNT
    from  BFJ_HISTORY_DATA
    where TRX_DATE = v_last_day
          and ACCOUNT_NO = v_account_no;
 commit;

insert into BFJ_HISTORY_DATA   ----Table1_01
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_01' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         to_char(k.credit_amount - k.credit_move_amount - k.interest_amount) as COL_VALUE --（本期系统反映，本期入金）业务系统中贷记客户资金账户金额
    from mid_bfj_bank_data k
    union all
    select
          'colG' as COL_NAME,
         'double' as COL_TYPE,
         to_char(COL_0101K) as COL_VALUE                                     --（本期系统反映，本期入金）业务系统中贷记客户资金账户金额
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
    union all
    select
          'colK' as COL_NAME,
         'double' as COL_TYPE,
          to_char(nvl(sum( case when k.ACCOUNT_NO = '453359895255' --中行
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00)) as COL_VALUE                                                        --（本期系统反映，本期入金）业务系统中贷记客户资金账户金额
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
    );


   insert into BFJ_HISTORY_DATA  ----Table1_02
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_02' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
    select 'colB' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.credit_amount - k.credit_move_amount,'0.00') as COL_VALUE --本期业务系统中借记客户资金账户金额
      from mid_bfj_qjs_data k
    union all
    select 'colC' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.credit_amount - k.credit_move_amount,'0.00') as COL_VALUE --本期业务系统中借记客户资金账户金额
      from mid_bfj_qjs_data k
    union all
    select 'colD' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE  --本期系统反映，本期出金
      from mid_bfj_bank_data k
    union all
    select 'colE' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE  --本期系统反映，本期出金
      from mid_bfj_bank_data k
    union all
    select 'colF' as COL_NAME,
           'double' as COL_TYPE,
           to_number('0.00') as COL_VALUE
           from dual
  /*  select 'colF' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.COL_0109C, '0.00') - nvl(k1.COL_0109C, '0.00') as COL_VALUE                 --前期系统反映，本期出金
      from bfj_last_time_data k,
           MID_BFJ_SPECAL_DATA k1
     where k.trx_date = k1.trx_date
       and k.account_no = k1.account_no*/
    union all
    select 'colG' as COL_NAME,
           'double' as COL_TYPE,
           nvl(COL_0109E, '0.00') as COL_VALUE                                          --前期系统反映，本期出金
      from MID_BFJ_SPECAL_DATA k
     );

 insert into BFJ_HISTORY_DATA   --Table1_03
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_03' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE --交易出金
    from mid_bfj_bank_data k
    );


 insert into BFJ_HISTORY_DATA   --Table1_04
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_04' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE --本期业务系统中客户资金账户借方发生额                                                 --（应收入金业务金额）本期业务系统中已贷记客户资金账户金额
    from mid_bfj_qjs_data k
     union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_AMOUNT - k.CREDIT_MOVE_AMOUNT - k.INTEREST_AMOUNT,'0.00') as COL_VALUE --本期业务系统中客户资金账户贷方发生额                                                --（应收入金业务金额）本期业务系统中已贷记客户资金账户金额
    from mid_bfj_qjs_data k
    );

  insert into BFJ_HISTORY_DATA   --Table1_05
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_05' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         COL_0105G as COL_VALUE                  --客户资金账户期初余额
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
     union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE    --本期入金业务贷记客户资金账户金额
    from mid_bfj_qjs_data k
     union all
       select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_AMOUNT - k.CREDIT_MOVE_AMOUNT - k.INTEREST_AMOUNT,'0.00') as COL_VALUE   --本期出金业务借记客户资金账户金额
    from mid_bfj_qjs_data k
     union all
       select
         'colE' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_MOVE_AMOUNT,'0.00') as COL_VALUE        --客户资金账户借方发生额
    from mid_bfj_qjs_data k
      union all
       select
         'colF' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_MOVE_AMOUNT,'0.00') as COL_VALUE       --客户资金账户贷方发生额
    from mid_bfj_qjs_data k
    );

  insert into BFJ_HISTORY_DATA   --Table1_05
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_05' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colG' as COL_NAME,
         'double' as COL_TYPE,
        sum( case when COL_NAME in ('colB','colC','colE')
               then  to_number(COL_VALUE)
                 when COL_NAME in ('colD','colF')
               then -1 * to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                         --客户资金账户期末余额
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and TABLE_CODE = 'Table1_05'
  );


   insert into BFJ_HISTORY_DATA   --Table1_06
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_06' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_MOVE_AMOUNT,'0.00') as COL_VALUE                              --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k
    union all
    select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.INTEREST_AMOUNT,'0') as COL_VALUE                                   --收到利息收入
    from mid_bfj_bank_data k
    union all
    select
         'colL' as COL_NAME,
         'double' as COL_TYPE,
        nvl(k.CREDIT_MOVE_AMOUNT+ k.INTEREST_AMOUNT,'0.00') as COL_VALUE                  --增加备付金银行账户合计
    from mid_bfj_bank_data k
    union all
    select
         'colM' as COL_NAME,
         'double' as COL_TYPE,
        -abs(k.DEBIT_MOVE_AMOUNT) as COL_VALUE                               --备付金银行间头寸调拨（调出行）
    from mid_bfj_bank_data k
    union all
    select
         'colO' as COL_NAME,
         'double' as COL_TYPE,
        -abs(k.POUNDAGE_AMOUNT) as COL_VALUE                                   --银行扣取的手续费、管理费等费用
    from mid_bfj_bank_data k
    union all
    select
         'colZ' as COL_NAME,
         'double' as COL_TYPE,
         -abs(k.DEBIT_MOVE_AMOUNT) -abs(k.POUNDAGE_AMOUNT) as COL_VALUE                  --减少备付金银行账合计
    from mid_bfj_bank_data k
    )  ;

    insert into BFJ_HISTORY_DATA   --Table1_09
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_09' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
          nvl(sum( case when k.ACCOUNT_NO = '453359895255' --中行
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE
                                                       --（本期系统反映，本期入金）业务系统中贷记客户资金账户金额
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
    union all
      select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0109C,'0.00') as COL_VALUE                  --支付机构业务系统已减少客户资金账户余额，备付金银行未减少备付金银行账户余额
    from mid_bfj_specal_data k
     union all
      select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0109D,'0.00') as COL_VALUE                  --备付金银行已增加备付金银行账户余额，支付机构业务系统未增加客户资金账户余额
    from mid_bfj_specal_data k
      union all
      select
         'colE' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0109E,'0.00') as COL_VALUE                  --备付金银行已减少备付金银行账户余额，支付机构业务系统未减少客户资金账户余额
    from mid_bfj_specal_data k
    );

      insert into BFJ_HISTORY_DATA   --Table1_10
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_10' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
    select
         'colB' as COL_NAME,
         'int' as COL_TYPE,
         k.COUNT_SUM as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_diff_data k
    union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(sum( case when k.ACCOUNT_NO = '453359895255' --中行
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE                                                     --（支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额≤5日笔数
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
      union all
       select
         'colH' as COL_NAME,
         'int' as COL_TYPE,
          nvl(k.COL_0110H,'0') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
      union all
       select
         'colI' as COL_NAME,
         'double' as COL_TYPE,
          nvl(k.COL_0110I,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
       select
         'colN' as COL_NAME,
         'int' as COL_TYPE,
          nvl(k.COL_0110N,'0') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
       select
         'colO' as COL_NAME,
         'double' as COL_TYPE,
          nvl(k.COL_0110O,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
       select
         'colT' as COL_NAME,
         'int' as COL_TYPE,
          nvl(k.COL_0110T,'0') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
       select
         'colU' as COL_NAME,
         'double' as COL_TYPE,
          nvl(k.COL_0110U,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    );


insert into BFJ_HISTORY_DATA   --Table1_11
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_11' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         nvl(col_0111C,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
    union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
          nvl(K.BALANCE_AMOUNT,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_qjs_data k
    union all
       select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
          nvl(K.BALANCE_AMOUNT-b.col_0111C,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_qjs_data k ,bfj_last_time_data b
    where k.trx_date = b.trx_date
    and k.account_no = b.account_no
    and b.trx_date = v_trx_date and b.ACCOUNT_NO = v_account_no
    union all
       select
         'colI' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(COL_0111I),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data
    union all
       select
         'colJ' as COL_NAME,
         'double' as COL_TYPE,
         nvl(sum( case when k.ACCOUNT_NO = '453359895255' --中行
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE                                                      --（支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额≤5日笔数
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
      union all
       select
         'colK' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111K),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
       union all
       select
         'colL' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111L),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
       union all
       select
         'colM' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111M),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
      union all
       select
         'colN' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.INTEREST_AMOUNT),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k
    union all
       select
         'colO' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111O),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
       select
         'colP' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.POUNDAGE_AMOUNT),'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k
    union all
       select
         'colAA' as COL_NAME,
         'double' as COL_TYPE,
          nvl(COL_0111AB,'0.00') as COL_VALUE                              --备付金银行间头寸调拨（调入行）
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
        union all
       select
         'colAB' as COL_NAME,
         'double' as COL_TYPE,
           (case when v_account_no = '0201014210021452' then
               nvl(to_number(m.col_0201d),'0.00')
               else  nvl(to_number(K.BALANCE_AMOUNT),'0.00')
                 end)  as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k,MID_BFJ_SPECAL_DATA m
    where k.trx_date = m.trx_date
     and k.account_no = m.account_no
    );




 insert into BFJ_HISTORY_DATA   --Table1_11
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_11' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (

      select
         'colY' as COL_NAME,
         'double' as COL_TYPE,
         sum( case when COL_NAME in ('colI','colK','colL','colN','colO')
               then  to_number(COL_VALUE)
                 when COL_NAME in ('colJ','colM','colP','colH')
               then -1 * to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and k.TABLE_CODE = 'Table1_11'
     union all
     select
         'colAC' as COL_NAME,
         'double' as COL_TYPE,
         sum( case when COL_NAME in ('colAB')
               then  to_number(COL_VALUE)
                 when COL_NAME in ('colAA')
               then  -1*to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and k.TABLE_CODE = 'Table1_11'

    );

  insert into BFJ_HISTORY_DATA   --Table1_11
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_11' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (     select
         'colZ' as COL_NAME,
         'double' as COL_TYPE,
         sum( case when COL_NAME in ('colD','colY')
               then  to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and k.TABLE_CODE = 'Table1_11'
    );


      insert into BFJ_HISTORY_DATA   --Table1_12
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_12' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
           (case when v_account_no = '0201014210021452' then
               nvl(to_number(m.col_0201d),'0.00')
               else  nvl(to_number(K.BALANCE_AMOUNT),'0.00')
                 end)  as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k,MID_BFJ_SPECAL_DATA m
    where k.trx_date = m.trx_date
     and k.account_no = m.account_no
    union all
     select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.interest_amount,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k
    union all
     select
         'colF' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112F,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
     select
         'colH' as COL_NAME,
         'double' as COL_TYPE,
         nvl(sum( case when k.ACCOUNT_NO = '453359895255' --中行
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE                                                       --（支付机构业务系统已增加客户资金账户余额，备付金银行未增加备付金银行账户余额≤5日笔数
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no                  --备付金银行间头寸调拨（调入行）
    union all
     select
         'colI' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112I,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
     select
         'colJ' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112J,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
     union all
     select
         'colK' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112K,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_specal_data k
    union all
     select
         'colR' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.BALANCE_AMOUNT,'0.00') as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_qjs_data k
  );

  insert into BFJ_HISTORY_DATA   --Table1_12
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_12' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colP' as COL_NAME,
         'double' as COL_TYPE,
         sum( case when COL_NAME in ('colF','colH','colK')
               then  to_number(COL_VALUE)
               when COL_NAME in ('colI','colJ','colC')
               then -1 * to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and k.TABLE_CODE = 'Table1_12'
  );

   insert into BFJ_HISTORY_DATA   --Table1_12
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_12' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colQ' as COL_NAME,
         'double' as COL_TYPE,
         sum( case when COL_NAME in ('colB','colP')
               then  to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and k.TABLE_CODE = 'Table1_12'
  );

     insert into BFJ_HISTORY_DATA   --Table1_12
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table1_12' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colS' as COL_NAME,
         'double' as COL_TYPE,
         sum(case when COL_NAME in ('colR')
               then  to_number(COL_VALUE)
               when COL_NAME in ('colQ')
               then -1 * to_number(COL_VALUE)
                 else 0.00
                   end) as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from BFJ_HISTORY_DATA k  where TRX_DATE = v_trx_date
    and k.ACCOUNT_NO = v_account_no
    and k.TABLE_CODE = 'Table1_12'
  );


  insert into BFJ_HISTORY_DATA   --Table2_01
  (ID,
  TRX_DATE,
  ACCOUNT_NO,
  ACCOUNT_NAME,
  TABLE_CODE,
  COL_NAME,
  COL_TYPE,
  COL_VALUE
  )
  select
         SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL as id,
         v_trx_date as TRX_DATE,
         v_account_no as ACCOUNT_NO,
         '东方电子支付有限公司客户备付金' as ACCOUNT_NAME,
         'Table2_01' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
           (case when v_account_no = '0201014210021452' then
               nvl(to_number(m.col_0201d),'0.00')
               else  nvl(to_number(K.BALANCE_AMOUNT),'0.00')
                 end)  as COL_VALUE                  --备付金银行间头寸调拨（调入行）
    from mid_bfj_bank_data k,MID_BFJ_SPECAL_DATA m
    where k.trx_date = m.trx_date
     and k.account_no = m.account_no
    );

   commit;

  err_num := 0;
  err_msg := 'Complete success!';
  v_step  := sp_wlog(v_seq,
                     v_trx_date,
                     UPPER(v_spname),
                     err_num,
                     err_msg,
                     SYSDATE,
                     v_start_date,
                      '[v_trx_date]:' || v_trx_date || '[elapsed]:' ||
                     TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    err_num := SQLCODE;
    err_msg := SUBSTR(SQLERRM, 1, 80);
    v_step  := sp_wlog(v_seq,
                       v_trx_date,
                       UPPER(v_spname),
                       err_num,
                       err_msg,
                       SYSDATE,
                       v_start_date,
                        '[v_trx_date]:' || v_trx_date || '[elapsed]:' ||
                       TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');
END;
/

/*
* 生成平台数据
* 主要从记账明细表中生成
* 贷方，借方金额从fin_stat_bank中计算出，资金调拨从fin_mx表计算出
*/
create or replace procedure SP_BFJ_QJS_DATA_INSERT(p_trx_date IN VARCHAR2,
                                               account_no        IN VARCHAR2,
                                               err_num     out number,
                                               err_msg     out varchar2) AS
  v_init_time  VARCHAR2(21); -- 初始化时间
  v_spname     VARCHAR2(32); -- 存储过程名
  v_start_date DATE; -- 系统开始时间
  v_step       VARCHAR2(4000); -- 步骤名
  v_seq        NUMBER; -- 查询sequence
  v_account_no    VARCHAR2(32);
  v_trx_date VARCHAR2(21);
  v_last_day   VARCHAR2(21);
  v_value      NUMBER;
  v_value_1      NUMBER;
BEGIN
  --------------------------- STEP_1 初始化变量 ---------------------------
  v_spname     := 'SP_BFJ_QJS_DATA_INSERT';
  v_start_date := SYSDATE;
  v_init_time  := to_char(sysdate - 1, 'YYYYmmdd');
  v_step       := 1.1;
  v_account_no := account_no;
  v_last_day   := to_char(sysdate - 2, 'YYYYmmdd');
  v_value       := 0;
  v_value_1       := 0;
  IF p_trx_date IS NOT NULL THEN
    v_trx_date := p_trx_date;
  ELSE
    v_trx_date := v_init_time;
  END IF;

 select SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL into v_seq from dual;

 select to_char((to_date(v_trx_date, 'yyyymmdd') - 1), 'yyyymmdd')
   into v_last_day
   from dual;

  delete from bfj_qjs_data where trx_date = v_trx_date;
  delete from bfj_qjs_diff_data where trx_date = v_trx_date;
  commit;

   insert into bfj_qjs_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    CREDIT_AMOUNT,
    DEBIT_AMOUNT,
    BALANCE_AMOUNT,
    CREDIT_MOVE_AMOUNT,
    DEBIT_MOVE_AMOUNT,
    INTEREST_AMOUNT,
    POUNDAGE_AMOUNT
    )
select m.trx_date, --交易时间
       b.account_no as account_no, --银行31code_id
    --   b.bank_code,
       b.ACCOUNT_NAME as ACCOUNT_NAME,
       m.credit_amount, --贷方发生额
       m.debit_amount, --借方发生额
       m.balance_amount, --余额
       nvl(p.credit_move_amount,0.00) credit_move_amount, --资金调拨贷方
       nvl(p.debit_move_amount, 0.00) debit_move_amount, --借方发生额
       nvl(p.interest_amount, 0.00) interest_amount, --利息
       nvl(p.poundage_amount, 0.00) poundage_amount --手续费用
  from (select f.fdebit debit_amount,
               f.fcredit credit_amount,
               f.close_bal balance_amount,
               substr(f.code_id,7,19) as code_id,
               to_char(f.stat_time, 'yyyymmdd') as trx_date
          from fin_stat_bank f
         where length(f.code_id) = 27
           and substr(f.code_id,26,2)='01'
           and f.code_id like '%100200%') m
       , (select substr(k.code_id,7,19) as code_id,
                    to_char(k.pz_time, 'yyyymmdd') as trx_date,
                    sum(case
                          when k.direction = -1 then
                           k.amount
                          else
                           0
                        end) as credit_move_amount,
                    sum(case
                          when k.direction = 1 then
                           k.amount
                          else
                           0
                        end) as debit_move_amount,
                    0 as interest_amount,
                    0 as poundage_amount
               from fin_mx k
              where k.trx_code = '1701'
                and is_show = '1'
                and length(code_id) = 31
                and substr(code_id,26,2)='01'
              group by code_id, to_char(k.pz_time, 'yyyymmdd')) p,
            ( select chn_code,chn_no from sac_channel_param where chn_type = 3 and is_valid_flag = '1' and currency_type = 'CNY'
 and chn_code not in ('EPLATBB','CFCA000','UPOP000')) c,
   (select * from bfj_account_no_param  where memo = '人民币账号') b
    where m.trx_date = p.trx_date(+)
     and m.code_id = p.code_id(+)
     and b.bank_code = c.chn_code
     and m.code_id = c.chn_no
     and m.trx_date = v_trx_date
     and b.account_no = v_account_no
;
 commit;
  -- 目前只有中行银联的差异
   insert into bfj_qjs_diff_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    CREDIT_AMOUNT,
    DEBIT_AMOUNT,
    BALANCE_AMOUNT
    )
     select
     v_trx_date as TRX_DATE,
     v_account_no as ACCOUNT_NO,
     '东方支付备付金' as ACCOUNT_NAME,
     total_sum as  CREDIT_AMOUNT,
     0.00 as DEBIT_AMOUNT,
     0.00 as BALANCE_AMOUNT
     from sac_chn_set_detail
     where
     type = 'L'
     and sac_date = v_trx_date;
     
    select count(1) into v_value from  bfj_qjs_data where ACCOUNT_NO =v_account_no
   and TRX_DATE = v_trx_date ;


   IF v_value = 0 THEN
      insert into bfj_qjs_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    CREDIT_AMOUNT,
    DEBIT_AMOUNT,
    BALANCE_AMOUNT,
    CREDIT_MOVE_AMOUNT,
    DEBIT_MOVE_AMOUNT,
    INTEREST_AMOUNT,
    POUNDAGE_AMOUNT
    )values(
      v_trx_date,
      v_account_no,
      '东方电子支付有限公司客户备付金',
      0,
      0,
      0,
      0,
      0,
      0,
      0
    );
  END IF;

  select count(1) into v_value_1 from  bfj_qjs_diff_data where ACCOUNT_NO =v_account_no
   and TRX_DATE = v_trx_date ;


   IF v_value_1 = 0 THEN
   insert into bfj_qjs_diff_data
   (
    TRX_DATE,
    ACCOUNT_NO,
    ACCOUNT_NAME,
    CREDIT_AMOUNT,
    DEBIT_AMOUNT,
    BALANCE_AMOUNT
    )values(
      v_trx_date,
      v_account_no,
      '东方电子支付有限公司客户备付金',
      0,
      0,
      0
    );
  END IF;
  
  
commit;
  err_num := 0;
  err_msg := 'Complete success!';
  v_step  := sp_wlog(v_seq,
                     v_trx_date,
                     UPPER(v_spname),
                     err_num,
                     err_msg,
                     SYSDATE,
                     v_start_date,
                      '[v_trx_date]:' || v_trx_date || '[elapsed]:' ||
                     TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    err_num := SQLCODE;
    err_msg := SUBSTR(SQLERRM, 1, 80);
    v_step  := sp_wlog(v_seq,
                       v_trx_date,
                       UPPER(v_spname),
                       err_num,
                       err_msg,
                       SYSDATE,
                       v_start_date,
                        '[v_trx_date]:' || v_trx_date || '[elapsed]:' ||
                       TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');
END;

/

/*
* 银行每日余额
* 从fin_mx中计算贷方，借方和发生额
* 期初余额：1 从T日fin_mx中，按pz_no升序拿第一条 2，拿大于T日fin_mx中，按pz_no升序拿第一条 3拿fin_ye中金额
* 期末余额：1 从T日fin_mx中，按pz_no降序拿第一条 2，拿大于T日fin_mx中，按pz_no降序拿第一条 3拿fin_ye中金额
*/
create or replace procedure SP_FIN_STAT_BANK(p_stat_date IN VARCHAR2,
                                             err_num     out number,
                                             err_msg     out varchar2) AS
  v_trade_time    DATE; --
  v_init_time  VARCHAR2(21); -- 初始化时间
  v_spname     VARCHAR2(32); -- 存储过程名
  v_start_date DATE; -- 系统开始时间
  v_step       VARCHAR2(8); -- 步骤名
  v_seq        NUMBER; -- 查询sequence
  v_result     NUMBER;
BEGIN
  --------------------------- STEP_1 初始化变量 ---------------------------
  v_spname     := 'SP_FIN_STAT_BANK';
  v_start_date := SYSDATE;
  v_init_time  := to_char(sysdate - 1, 'YYYYmmdd');
  v_step       := 1.1;
  v_result     := 0;

  IF p_stat_date IS NOT NULL THEN
    v_trade_time := to_date(p_stat_date, 'yyyymmdd');
  ELSE
    v_trade_time := to_date(v_init_time, 'yyyymmdd');
  END IF;

  select SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL into v_seq from dual;

/*
  select COUNT(*)
    into v_result
    from fin_task m, sac_fin_ins_rule n
   where m.status != 1
     and m.trx_code = n.trx_code
     and m.step = n.step
     and n.trx_state = 'G';


  IF v_result>0 THEN
     return;
  END IF;
*/


  insert into fin_stat_bank
    (STAT_BANK_ID,
     CODE_ID,
     OPEN_BAL,
     FDEBIT,
     FCREDIT,
     CLOSE_BAL,
     AMOUNT,
     DIRECTION,
     STAT_TIME)
    select SEQ_SAC_CHN_SETTLEMENT_ID.NEXTVAL as STAT_BANK_ID,
           a.code_id,
           case when b.open_bal IS NOT NULL
                THEN b.open_bal
               WHEN d.open_bal IS NOT NULL
               THEN  d.open_bal
              else nvl(e.TOTAL_AMOUNT, 0)
             END,
           nvl(a.fdebit, 0),
           nvl(a.fcredit, 0),
           --  nvl(c.close_bal, 0),
           case when c.close_bal IS NOT NULL
                THEN c.close_bal
               WHEN d.open_bal IS NOT NULL
               THEN  d.open_bal
              else nvl(e.TOTAL_AMOUNT, 0)
             END,
             
        /* case when nvl(c.close_bal, 0) = 0
               then nvl(d.close_bal,0)
              else nvl(c.close_bal, 0)
             end,*/
           nvl(abs(a.AMOUNT), 0) as AMOUNT,
           (case
             when nvl(a.AMOUNT, 0) > 0 then
              1
             when nvl(a.AMOUNT, 0) = 0 then
              0
             else
              -1
           end) as DIRECTION,
           v_trade_time as STAT_TIME
      from (select f2.code_id,
                   sum(f1.fdebit) as fdebit,
                   sum(f1.fcredit) as fcredit,
                   sum(f1.fdebit) - sum(f1.fcredit) as AMOUNT  --发生额
              from (select *
                      from fin_mx f
                     where f.is_show = '1'
                       and f.pz_time > v_trade_time
                       and f.pz_time < v_trade_time + 1) f1,
                   (select distinct substr(code_id,0,27) as code_id from fin_code cm where code_id like '1002009%'
                   and cm.code_id not in (select code_id from fin_stat_bank where to_char(stat_time, 'yyyymmdd')= to_char(v_trade_time, 'yyyymmdd')))  f2
             where f2.code_id = f1.code_id(+)
             group by f2.code_id) a, 
           (SELECT l.*
              FROM (SELECT f.*,
                           ROW_NUMBER() OVER(partition by f.code_id ORDER BY f.pz_no ASC) ROWNO
                      FROM (select *
                              from fin_mx
                             where code_id in (select distinct substr(code_id,0,27)as code_id from fin_code  where code_id like '1002009%')
                               and is_show = '1'
                               and pz_time > v_trade_time
                               and pz_time < v_trade_time + 1) f) l
             WHERE rowNo = 1) b,
           (SELECT l.*
              FROM (SELECT f.*,
                           ROW_NUMBER() OVER(partition by f.code_id ORDER BY f.pz_no DESC) ROWNO
                      FROM (select *
                              from fin_mx
                             where code_id in (select distinct substr(code_id,0,27) as code_id from fin_code  where code_id like '1002009%')
                               and is_show = '1'
                               and pz_time > v_trade_time
                               and pz_time < v_trade_time + 1) f) l
             WHERE rowNo = 1) c,
             (SELECT l.*
              FROM (SELECT f.*,
                           ROW_NUMBER() OVER(partition by f.code_id ORDER BY f.pz_no ASC) ROWNO
                      FROM (select *
                              from fin_mx
                             where code_id in (select distinct substr(code_id,0,27) as code_id from fin_code  where code_id like '1002009%')
                               and is_show = '1'
                               and pz_time > v_trade_time + 1) f) l
             WHERE rowNo = 1) d,
             fin_ye e          
     where a.code_id = b.code_id(+)
       and a.code_id = c.code_id(+)
       and a.code_id = d.code_id(+)
       AND a.code_id = e.ye_id(+);
  commit;

  err_num := 0;
  err_msg := 'Complete success!';
  v_step  := sp_wlog(v_seq,
                     TO_CHAR(v_trade_time, 'yyyymmdd'),
                     UPPER(v_spname),
                     err_num,
                     err_msg,
                     SYSDATE,
                     v_start_date,
                     '[v_trade_time]:' || v_trade_time || '[elapsed]:' ||
                     TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    err_num := SQLCODE;
    err_msg := SUBSTR(SQLERRM, 1, 80);
    v_step  := sp_wlog(v_seq,
                       TO_CHAR(v_trade_time, 'yyyymmdd'),
                       UPPER(v_spname),
                       err_num,
                       err_msg,
                       SYSDATE,
                       v_start_date,
                       '[v_trade_time]:' || v_trade_time || '[elapsed]:' ||
                       TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');
END;
/

/*
* 商户每日余额
* 从fin_mx中计算贷方，借方和发生额
* 期初余额：1 从T日fin_mx中，按pz_no升序拿第一条 2，拿大于T日fin_mx中，按pz_no升序拿第一条 3拿fin_ye中金额
* 期末余额：1 从T日fin_mx中，按pz_no降序拿第一条 2，拿大于T日fin_mx中，按pz_no降序拿第一条 3拿fin_ye中金额
*/
create or replace procedure SP_FIN_STAT_ORG(p_stat_date IN VARCHAR2,
                                             err_num     out number,
                                             err_msg     out varchar2) AS
  v_trade_time    DATE; --
  v_init_time  VARCHAR2(21); -- 初始化时间
  v_spname     VARCHAR2(32); -- 存储过程名
  v_start_date DATE; -- 系统开始时间
  v_step       VARCHAR2(8); -- 步骤名
  v_seq        NUMBER; -- 查询sequence
  v_result     NUMBER;
BEGIN
  --------------------------- STEP_1 初始化变量 ---------------------------
  v_spname     := 'SP_FIN_STAT_ORG';
  v_start_date := SYSDATE;
  v_init_time  := to_char(sysdate - 1, 'YYYYmmdd');
  v_step       := 1.1;
  v_result     := 0;

  IF p_stat_date IS NOT NULL THEN
    v_trade_time := to_date(p_stat_date, 'yyyymmdd');
  ELSE
    v_trade_time := to_date(v_init_time, 'yyyymmdd');
  END IF;

  select SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL into v_seq from dual;

/*
  select COUNT(*)
    into v_result
    from fin_task m, sac_fin_ins_rule n
   where m.status != 1
     and m.trx_code = n.trx_code
     and m.step = n.step
     and n.trx_state = 'G';


  IF v_result>0 THEN
     return;
  END IF;
*/


  insert into fin_stat_org
    (STAT_BANK_ID,
     CODE_ID,
     SAC_CURRENCY,
     BUSS_TYPE,
     OPEN_BAL,
     FDEBIT,
     FCREDIT,
     CLOSE_BAL,
     AMOUNT,
     DIRECTION,
     STAT_TIME
     )
        select SEQ_SAC_CHN_SETTLEMENT_ID.NEXTVAL as STAT_BANK_ID,
           a.code_id,
           substr(a.code_id,26,2) as SAC_CURRENCY,
           case when length(a.code_id)>=29 
                 then substr(a.code_id,28,2)
                else '00'
                  end
                  as BUSS_TYPE,   
           case when b.open_bal IS NOT NULL
                THEN b.open_bal
               WHEN d.open_bal IS NOT NULL
               THEN  d.open_bal
              else nvl(e.TOTAL_AMOUNT, 0)
             END,
           nvl(a.fdebit, 0),
           nvl(a.fcredit, 0),
           --  nvl(c.close_bal, 0),
           case when c.close_bal IS NOT NULL
                THEN c.close_bal
               WHEN d.open_bal IS NOT NULL
               THEN  d.open_bal
              else nvl(e.TOTAL_AMOUNT, 0)
             END,
           nvl(abs(a.AMOUNT), 0) as AMOUNT,
           (case
             when nvl(a.AMOUNT, 0) > 0 then
              1
             when nvl(a.AMOUNT, 0) = 0 then
              0
             else
              -1
           end) as DIRECTION,
           v_trade_time as STAT_TIME
      from (select f1.code_id,
                   sum(f2.fdebit) as fdebit,
                   sum(f2.fcredit) as fcredit,
                   sum(f2.fdebit) - sum(f2.fcredit) as AMOUNT
              from 
              (select distinct substr(code_id,0,27) as code_id from fin_code cm where code_id like '220203%'
                   and cm.code_id not in (select code_id from fin_stat_org where to_char(stat_time, 'yyyymmdd')= to_char(v_trade_time, 'yyyymmdd')))  f1,
              (select *
                      from fin_mx f
                     where f.is_show = '1'
                       and length(f.code_id) = 27
                       and f.pz_time > v_trade_time
                       and f.pz_time < v_trade_time + 1) f2                   
             where f1.code_id = f2.code_id(+)
             group by f1.code_id) a,
           (SELECT l.*
              FROM (SELECT f.*,
                           ROW_NUMBER() OVER(partition by f.code_id ORDER BY f.pz_no ASC,f.pz_time ASC) ROWNO
                      FROM (select *
                              from fin_mx
                             where code_id in (select distinct substr(code_id,0,27) from fin_code)
                               and is_show = '1'
                               and pz_time > v_trade_time
                               and pz_time < v_trade_time + 1) f) l
             WHERE rowNo = 1) b,
           (SELECT l.*
              FROM (SELECT f.*,
                           ROW_NUMBER() OVER(partition by f.code_id ORDER BY f.pz_no DESC,f.pz_time DESC) ROWNO
                      FROM (select *
                              from fin_mx
                             where code_id in (select distinct substr(code_id,0,27) from fin_code)
                               and is_show = '1'
                               and pz_time > v_trade_time
                               and pz_time < v_trade_time + 1) f) l
             WHERE rowNo = 1) c,
             (SELECT l.*
              FROM (SELECT f.*,
                           ROW_NUMBER() OVER(partition by f.code_id ORDER BY f.pz_no ASC) ROWNO
                      FROM (select *
                              from fin_mx
                             where code_id in (select distinct substr(code_id,0,27) from fin_code)
                               and is_show = '1'
                               and pz_time > v_trade_time + 1) f) l
             WHERE rowNo = 1) d,
             fin_ye e          
     where a.code_id = b.code_id(+)
       and a.code_id = c.code_id(+)
       and a.code_id = d.code_id(+)
       AND a.code_id = e.ye_id(+);
  commit;

  err_num := 0;
  err_msg := 'Complete success!';
  v_step  := sp_wlog(v_seq,
                     TO_CHAR(v_trade_time, 'yyyymmdd'),
                     UPPER(v_spname),
                     err_num,
                     err_msg,
                     SYSDATE,
                     v_start_date,
                     '[v_trade_time]:' || v_trade_time || '[elapsed]:' ||
                     TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    err_num := SQLCODE;
    err_msg := SUBSTR(SQLERRM, 1, 80);
    v_step  := sp_wlog(v_seq,
                       v_trade_time,
                       UPPER(v_spname),
                       err_num,
                       err_msg,
                       SYSDATE,
                       v_start_date,
                       '[v_trade_time]:' || v_trade_time || '[elapsed]:' ||
                       TO_CHAR((SYSDATE - v_start_date) * 86400, '99999') || 's');
END;
/

/*
* 试算平衡
* 从fin_mx中计算贷方（应付账款），借方（银行存款和应收账款）
* 
* 
*/
create or replace procedure SP_FIN_TRIAL_BALANCING AS
BEGIN
insert into fin_trial_balancing
SELECT SEQ_FIN_TRIAL_BALANCING_ID.NEXTVAL ID, A.*
  FROM (SELECT SUBSTR(T.YE_ID, 0, 4) CODE_ID,
               SUBSTR(T.YE_ID, 26, 2) SAC_CURRENCY,
               SUM(T.TOTAL_AMOUNT) BALANCE,
               SYSDATE CREATE_TIME,
               '' MEMO
          FROM FIN_YE T
         WHERE LENGTH(T.YE_ID) = 27
         GROUP BY SUBSTR(T.YE_ID, 0, 4), SUBSTR(T.YE_ID, 26, 2)) A;
  commit;
END;
/

prompt
prompt Creating procedure SP_SAC_CHN_SETTLEMENT
prompt ========================================
prompt
CREATE OR REPLACE PROCEDURE SP_SAC_CHN_SETTLEMENT(P_SAC_DATE IN VARCHAR2 --20160311
                                                 ,
                                                  ERR_NUM    OUT NUMBER,
                                                  ERR_MSG    OUT VARCHAR2)
/**
  *    过程名称：SP_SAC_CHN_SETTLEMENT
  *    过程描述：清分渠道应收明细
  *    输入参数：P_SAC_DATE            VARCHAR2        清算日期
  *    输出参数：ERR_NUM               NUMBER          返回代码
  *              ERR_MSG               VARCHAR2        返回信息
  *    编写人员：李明超
  *    创建日期：20150703
  *    处理步聚 ：
  *    1. 清除同期的数据数据
  *
  *    修改历史：
       0114：将差错中的长款剔除，以及长款添加正逆向
       0118: 渠道清分明细精确到渠道一级
  */
 AS
  V_SAC_DATE   VARCHAR2(21); -- 清算日期
  V_SPNAME     VARCHAR2(32); -- 存储过程名
  V_START_DATE DATE; -- 系统开始时间
  V_STEP       VARCHAR2(8); -- 步骤名
  V_RUN_DATE   VARCHAR2(21); -- 清算日期
  V_SEQ        NUMBER; -- 查询sequence
  V_FISRT_WORK_DATE  VARCHAR2(8);
  V_PAOPI_DATE  VARCHAR2(8);


BEGIN
  --------------------------- STEP_1 初始化变量 ---------------------------
  V_SPNAME     := 'SP_SAC_CHN_SETTLEMENT';
  V_START_DATE := SYSDATE;
  V_RUN_DATE   := TO_CHAR(SYSDATE - 1, 'YYYYMMDD');
  V_PAOPI_DATE := TO_CHAR(SYSDATE, 'YYYYMMDD');

  IF P_SAC_DATE IS NOT NULL THEN
    V_SAC_DATE := P_SAC_DATE;
  ELSE
    V_SAC_DATE := V_RUN_DATE;
  END IF;

  SELECT SEQ_SAC_CUS_PAYMENT_ID.NEXTVAL INTO V_SEQ FROM DUAL;

  SELECT NVL(MIN(S.FIRST_WORK_DATE),0) INTO V_FISRT_WORK_DATE  FROM SAC_CHANNEL_HOLIDAY S WHERE V_SAC_DATE BETWEEN S.HOLIDAY_START_DATE-1 AND S.HOLIDAY_END_DATE+1 AND S.IS_VALID_FLAG = '1';

  -- STEP_1.1 清空中间表数据
  V_STEP := '1.1';
  DELETE FROM MID_SAC_TRX_DETAIL;
  commit;

  -- STEP_1.2 划定初始化数据范围
  /*
    取T-1日交易为成功（‘S’）的数据
  */
  V_STEP := '1.2';
  INSERT INTO MID_SAC_TRX_DETAIL --该临时表和交易明细表相同
    (ID,
     CUS_BILL_NO,
     PLAT_BILL_NO,
     TRX_BATCH_NO,
     TRX_SERIAL_NO,
     OTRX_SERIAL_NO,
     RECEIVER_PLAT_ACC,
     PAYMENT_PLAT_ACC,
     BUSS_TYPE,
     TRX_TYPE,
     TRX_AMOUNT,
     TRX_CURRENCY_TYPE,
   --  TRX_COST,
     EX_RATE,
     ISSUING_BANK,
     PAYCON_TYPE,
     PAY_WAY,
     TRX_STATE,
     UPDATE_TIME,
     CREATE_TIME,
     MEMO,
     DRACC_NODE_CODE,
     CRACC_NODE_CODE,
     CHN_BATCH_NO,
     CUS_BATCH_NO,
     SAC_CURRENCY,
     SAC_AMOUNT,
     CHN_NO,
     CHA_CON_STATUS,
     TRX_TIME,
     CHANNEL_COST ,
     CUS_CHARGE,
     TRX_SUCC_TIME,
     trans_date)
      SELECT ID,
           CUS_BILL_NO,
           PLAT_BILL_NO,
           TRX_BATCH_NO,
           TRX_SERIAL_NO,
           OTRX_SERIAL_NO,
           RECEIVER_PLAT_ACC,
           PAYMENT_PLAT_ACC,
           BUSS_TYPE,
           TRX_TYPE,
           TRX_AMOUNT,
           TRX_CURRENCY_TYPE,
        --   TRX_COST,
           EX_RATE,
           ISSUING_BANK,
           PAYCON_TYPE,
           PAY_WAY,
           TRX_STATE,
           UPDATE_TIME,
           CREATE_TIME,
           MEMO,
           DRACC_NODE_CODE,
           CRACC_NODE_CODE,
           CHN_BATCH_NO,
           CUS_BATCH_NO,
           SAC_CURRENCY,
           SAC_AMOUNT,
           CHN_NO,
           CHA_CON_STATUS,
           TRX_TIME,
            CHANNEL_COST ,
            CUS_CHARGE,
            TRX_SUCC_TIME,            ---交易日期
            ( CASE WHEN CHN_NO IN(   --节假日情况
              '8000000000056863000'  --银联B2B B2C
             ,'8000000000380318000') --银联B2B B2C
               AND V_FISRT_WORK_DATE != 0 and V_FISRT_WORK_DATE != V_SAC_DATE  --节假日情况
                THEN  V_FISRT_WORK_DATE
               WHEN CHN_NO IN(   --节假日情况
              '8000000000056863000'--银联B2B B2C
             , '8000000000380318000')--银联B2B B2C
                 AND V_FISRT_WORK_DATE = V_SAC_DATE  --节假日情况
                THEN  TO_CHAR(TRX_SUCC_TIME+1,'YYYYMMDD')
              WHEN CHN_NO IN
                ('8000000000056863000'--银联B2B B2C
                ,'8000000000380318000'--银联B2B B2C
                 ) AND V_FISRT_WORK_DATE = 0
                   THEN (CASE WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 6  --星期五  清算时间为下周一
                          THEN TO_CHAR(TRX_SUCC_TIME+3,'YYYYMMDD')
                           WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 7  --星期六  清算时间为下周一
                             THEN TO_CHAR(TRX_SUCC_TIME+2,'YYYYMMDD')
                               ELSE  TO_CHAR(TRX_SUCC_TIME+1,'YYYYMMDD')
                                 END
                            )
                WHEN  CHN_NO = '8000000000344866000' AND TRX_TYPE='1621'   -- 浦发银行现金柜
                     THEN (CASE WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 2 --星期一
                                    THEN TO_CHAR(TRX_SUCC_TIME+2,'YYYYMMDD')
                              WHEN (TO_CHAR(TRX_SUCC_TIME,'D') = 3 or TO_CHAR(TRX_SUCC_TIME,'D') = 5) --星期二,四
                                    THEN TO_CHAR(TRX_SUCC_TIME+1,'YYYYMMDD')
                               WHEN (TO_CHAR(TRX_SUCC_TIME,'D') = 4 and  TO_CHAR(TRX_SUCC_TIME,'hh24') >= 13)  --星期三下午13点
                                    THEN TO_CHAR(TRX_SUCC_TIME+2,'YYYYMMDD')
                               WHEN (TO_CHAR(TRX_SUCC_TIME,'D') = 6 and TO_CHAR(TRX_SUCC_TIME,'hh24') >= 13)  --星期五下午13点
                                    THEN TO_CHAR(TRX_SUCC_TIME+5,'YYYYMMDD')
                               WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 7  --星期六  清算时间为下周三
                                    THEN TO_CHAR(TRX_SUCC_TIME+4,'YYYYMMDD')
                               WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 1  --星期天  清算时间为下周三
                                    THEN TO_CHAR(TRX_SUCC_TIME+3,'YYYYMMDD')
                               ELSE TO_CHAR(TRX_SUCC_TIME,'YYYYMMDD') --星期三,五上午       
                              END
                       )
                ELSE TO_CHAR(TRX_SUCC_TIME,'YYYYMMDD')
              END) AS TRANS_DATE    --到账日期
      FROM SAC_TRX_DETAIL
     WHERE
     TRX_STATE = 'S' --交易状态
     AND NVL(CHN_BATCH_NO,'1') = '1'
     AND TRX_SUCC_TIME <= TO_DATE(V_SAC_DATE ||  ' 23:59:59','YYYYMMDD HH24:MI:SS')
     AND ((CHA_CON_STATUS = 'S' AND TRX_TYPE IN ('3302','1302', '1631', '1315', '1312','3303','1730'))
            OR TRX_TYPE IN ('1601', '1705','3423', '1701', '1305','5201')
            OR (CHN_NO = '8000000000344866000' AND TRX_TYPE='1621'));


    INSERT INTO MID_SAC_TRX_DETAIL --该临时表和交易明细表相同
    (ID,
     CUS_BILL_NO,
     PLAT_BILL_NO,
     TRX_BATCH_NO,
     TRX_SERIAL_NO,
     OTRX_SERIAL_NO,
     RECEIVER_PLAT_ACC,
     PAYMENT_PLAT_ACC,
     BUSS_TYPE,
     TRX_TYPE,
     TRX_AMOUNT,
     TRX_CURRENCY_TYPE,
   --  TRX_COST,
     EX_RATE,
     ISSUING_BANK,
     PAYCON_TYPE,
     PAY_WAY,
     TRX_STATE,
     UPDATE_TIME,
     CREATE_TIME,
     MEMO,
     DRACC_NODE_CODE,
     CRACC_NODE_CODE,
     CHN_BATCH_NO,
     CUS_BATCH_NO,
     SAC_CURRENCY,
     SAC_AMOUNT,
     CHN_NO,
     CHA_CON_STATUS,
     TRX_TIME,
     CHANNEL_COST ,
     CUS_CHARGE,
     TRX_SUCC_TIME,
     trans_date)
      SELECT A.ID,
           A.CUS_BILL_NO,
           A.PLAT_BILL_NO,
           A.TRX_BATCH_NO,
           A.TRX_SERIAL_NO,
           A.OTRX_SERIAL_NO,
           A.RECEIVER_PLAT_ACC,
           A.PAYMENT_PLAT_ACC,
           A.BUSS_TYPE,
           A.TRX_TYPE,
           A.TRX_AMOUNT,
           A.TRX_CURRENCY_TYPE,
        --   TRX_COST,
           A.EX_RATE,
           A.ISSUING_BANK,
           A.PAYCON_TYPE,
           A.PAY_WAY,
           A.TRX_STATE,
           A.UPDATE_TIME,
           A.CREATE_TIME,
           A.MEMO,
           A.DRACC_NODE_CODE,
           A.CRACC_NODE_CODE,
           A.CHN_BATCH_NO,
           A.CUS_BATCH_NO,
           A.SAC_CURRENCY,
           A.SAC_AMOUNT,
           A.CHN_NO,
           A.CHA_CON_STATUS,
           A.TRX_TIME,
            A.CHANNEL_COST ,
            A.CUS_CHARGE,
            A.TRX_SUCC_TIME,                 ---交易日期
            to_char(B.Bank_Trx_Date,'yyyymmdd') AS TRANS_DATE    --到账日期
      FROM SAC_TRX_DETAIL A, sac_deposit_detail B
     WHERE
     A.TRX_SERIAL_NO = B.trx_serial_no
     AND A.TRX_STATE = 'S' --交易状态　　　
     AND NVL(A.CHN_BATCH_NO,'1') = '1'
     AND TRX_SUCC_TIME <= TO_DATE(V_SAC_DATE ||  ' 23:59:59','YYYYMMDD HH24:MI:SS')
     AND TRX_TYPE in ('1621','1626')
     AND CHN_NO <> '8000000000344866000' ;

  --  and 对账成功; --取交易状态等于成功的交易

  -- STEP_1.3 渠道清分明细
  /*
    1. 按渠道、币种、支付渠道类型来清分明细
    2. 包含正向交易，逆向交易 以及差错长款
  */
  V_STEP := '1.3';
  INSERT INTO SAC_CHN_SET_DETAIL
    (ID,
     CHN_NO,
     BANK_NODE_CODE,
     SAC_BANK_NAME,
     CHN_NAME,
     ACCOUNT_NUMBER,
     TRX_DATE,
     TRANS_DATE,
     SAC_DATE,
     TYPE,
     TOTAL_NUM,
     TOTAL_SUM,
     TRX_COST,
     CURRENCY_TYPE,
     PAYCON_TYPE,
     CHN_BATCH_NO,
     BUSI_TYPE,
     CREATE_TIME,
     UPDATE_TIME,
     MEMO)
    SELECT SEQ_SAC_CHN_SETTLEMENT_ID.NEXTVAL AS ID,
           CHN_NO,
           BANK_NODE_CODE,
           SAC_BANK_NAME,
           CHN_NAME,
           BANK_ACC       AS ACCOUNT_NUMBER,
           TRX_DATE,
           TRANS_DATE,
           SAC_DATE,
           TYPE,
           TOTAL_NUM,
           TRX_AMOUNT AS TOTAL_SUM,
           TRX_COST,
           CURRENCY_TYPE,
           PAYCON_TYPE,
           CHN_BATCH_NO,
           BUSI_TYPE,
           SYSDATE AS CREATE_TIME,
           SYSDATE AS UPDATE_TIME,
           '' AS MEMO
      FROM (SELECT --东方支付渠道收款
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             '' AS TRX_DATE,
             TRANS_DATE AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             'N' AS TYPE, --区分正常（N），长款（L），短款（S），其它（O）
             COUNT(1) AS TOTAL_NUM, --笔数
             SUM(D.TRX_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             D.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' || TRANS_DATE AS CHN_BATCH_NO,
             '1' AS BUSI_TYPE --正向交易
              FROM SAC_CHANNEL_PARAM P, MID_SAC_TRX_DETAIL D
             WHERE P.CHN_NO = D.CHN_NO
               AND P.CURRENCY_TYPE = D.TRX_CURRENCY_TYPE
               AND P.IS_VALID_FLAG = '1'
               AND P.CHN_TYPE IN ('1', '2','3','4') --取渠道类型为B2B，B2C
               AND D.PAYCON_TYPE = P.CHN_TYPE
               AND D.TRX_TYPE IN ('3302',
                                  '1705',
                                  '1302',
                                  '1631',
                                  '1601',
                                  '1701',
                                  '1305',
                                  '1315',
                                  '1621',
                                  '1626',
                                  '1312',
                                  '5201'
                                  )
            --支付交易(3302) 购汇资金入账(1705) 在线支付(1302) 网银预存(1631) 快捷预存(1601) 快捷直通支付(1305) 网银直通支付(1315) 已确认线下预存(1621) 未确认线下预存(1626) 网银支付(1312)
             GROUP BY P.BANK_NODE_CODE,
                      P.CHN_NO,
                      P.CURRENCY_TYPE,
                      D.PAYCON_TYPE,
                      TRANS_DATE
            UNION ALL
            SELECT --东方支付渠道收款
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             '' AS TRX_DATE,
             TRANS_DATE AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             'N' AS TYPE, --区分正常（N），长款（L），短款（S），其它（O）
             COUNT(1) AS TOTAL_NUM, --笔数
             SUM(D.SAC_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             D.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' || TRANS_DATE AS CHN_BATCH_NO,
             '1' AS BUSI_TYPE --正向交易
              FROM SAC_CHANNEL_PARAM P, MID_SAC_TRX_DETAIL D
             WHERE P.CHN_NO = D.CHN_NO
               AND P.CURRENCY_TYPE = D.SAC_CURRENCY
               AND P.IS_VALID_FLAG = '1'
               AND P.CHN_TYPE IN ('1', '2') --取渠道类型为B2B，B2C
               AND D.PAYCON_TYPE = P.CHN_TYPE
               AND D.TRX_TYPE IN ('3423')
            --退款结汇交易(3423)
             GROUP BY P.BANK_NODE_CODE,
                      P.CHN_NO,
                      P.CURRENCY_TYPE,
                      D.PAYCON_TYPE,
                      TRANS_DATE
            UNION ALL
            SELECT --东方支付渠道出款
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             '' AS TRX_DATE,
             TRANS_DATE AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             'N' AS TYPE,
             COUNT(1) AS TOTAL_NUM, --笔数
             SUM(D.TRX_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             D.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' ||TRANS_DATE AS CHN_BATCH_NO,
             '0' AS BUSI_TYPE --逆向交易
              FROM SAC_CHANNEL_PARAM P, MID_SAC_TRX_DETAIL D
             WHERE P.CHN_NO = D.CHN_NO
               AND P.CURRENCY_TYPE = D.TRX_CURRENCY_TYPE
               AND P.IS_VALID_FLAG = '1'
               AND P.CHN_TYPE IN ('1', '2') --取渠道类型为B2B，B2C
               AND D.PAYCON_TYPE = P.CHN_TYPE
               AND D.TRX_TYPE IN ('3303', '1730') --包括B2C退款(3303,1730)(逆向交易的交易类型代码)
             GROUP BY P.BANK_NODE_CODE,
                      P.CHN_NO,
                      P.CURRENCY_TYPE,
                      D.PAYCON_TYPE,
                      TRANS_DATE
            UNION ALL
            SELECT
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             TO_CHAR(S.UPDATE_TIME-1, 'YYYYMMDD') AS TRX_DATE,
             TO_CHAR(S.UPDATE_TIME-1, 'YYYYMMDD') AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             --      TO_CHAR(S.TRX_TIME, 'YYYYMMDD') AS TRX_DATE,
             'L' AS TYPE,--长款
             COUNT(1) AS TOTAL_NUM, --笔数
             SUM(S.PAY_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             S.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_L_' ||
             S.PAYCON_TYPE || '_' || TO_CHAR(S.UPDATE_TIME-1, 'YYYYMMDD') AS CHN_BATCH_NO,
             S.BUSI_TYPE AS BUSI_TYPE
              FROM SAC_CHANNEL_PARAM P, SAC_REC_DIFFERENCES S
             WHERE S.REC_DIFF_TYPE in ('CHA000','CHA002') --差错长款
               AND S.STATUS = 'N' -- 未处理
               AND P.CHN_CODE = S.CHN_CODE
               AND P.CHN_TYPE = S.PAYCON_TYPE
               AND P.CURRENCY_TYPE = S.CURRENCY_TYPE
               AND TO_CHAR(S.UPDATE_TIME-1, 'YYYYMMDD') = V_SAC_DATE
             GROUP BY P.BANK_NODE_CODE,
                      P.CHN_NO,
                      P.CURRENCY_TYPE,
                      S.PAYCON_TYPE,
                      S.BUSI_TYPE,
                      TO_CHAR(S.UPDATE_TIME-1, 'YYYYMMDD')
                      );

  -- STEP_1.5 从sequence中取主键id
  --将清分结果插入SAC_CHN_SETTLEMENT
  /*
    按清算行清分结果
  */

  V_STEP := '1.5';

  INSERT INTO SAC_CHN_SETTLEMENT
    (ID,
     BANK_NODE_CODE,
     SAC_BANK_NAME,
     ACCOUNT_NUMBER,
     SAC_DATE,
     TRX_DATE,
     TRANS_DATE,
     TYPE,
     TOTAL_NUM,
     TOTAL_SUM,
     TRX_COST,
     RECEIVE_TOT_SUM,
     REAL_TOT_AMOUNT,
     CURRENCY_TYPE,
     FIN_SIGN,
     CREATE_TIME,
     UPDATE_TIME,
     MEMO,
     CHN_BATCH_NO --渠道清分批次号
     )
    SELECT SEQ_SAC_CHN_SETTLEMENT_ID.NEXTVAL AS ID,
           BANK_NODE_CODE,
           SAC_BANK_NAME,
           ACCOUNT_NUMBER,
           SAC_DATE,
           TRX_DATE,
           TRANS_DATE,
           TYPE,
           TOTAL_NUM,
           TRX_AMOUNT,
           TRX_COST,
           TRX_AMOUNT - TRX_COST AS RECEIVE_TOT_SUM,
           0 AS REAL_TOT_AMOUNT,
           CURRENCY_TYPE,
           'N' AS FIN_SIGN,
           SYSDATE,
           SYSDATE,
           '' AS MEMO,
           CHN_BATCH_NO

      FROM (SELECT MAX(D.BANK_NODE_CODE) AS BANK_NODE_CODE,
                   MAX(D.SAC_BANK_NAME) AS SAC_BANK_NAME,
                   MAX(D.ACCOUNT_NUMBER) AS ACCOUNT_NUMBER,
                   MAX(D.SAC_DATE) AS SAC_DATE,
                   MAX(D.TRX_DATE) AS TRX_DATE,
                   MAX(D.TRANS_DATE) AS TRANS_DATE,
                   MAX(D.TYPE) AS TYPE,
                   SUM( CASE WHEN CHN_NO IN    --银联统计算成一笔
                           ('7000000000056863000','8000000000056863000'--银联B2B B2C
                           ,'7000000000380318000','8000000000380318000'--银联B2B B2C
                          ) THEN 1
                            ELSE TOTAL_NUM
                         END
                   ) AS TOTAL_NUM, --笔数
                   SUM(CASE
                         WHEN D.BUSI_TYPE = '1' THEN
                          D.TOTAL_SUM
                         ELSE
                          0
                       END) - SUM(CASE
                                    WHEN D.BUSI_TYPE = '0' THEN
                                     D.TOTAL_SUM
                                    ELSE
                                     0
                                  END) AS TRX_AMOUNT,
                   0 AS TRX_COST,
                   MAX(D.CURRENCY_TYPE) AS CURRENCY_TYPE,
                   CHN_BATCH_NO
              FROM SAC_CHN_SET_DETAIL D
             WHERE --D.CHN_BATCH_NO not in (select CHN_BATCH_NO from sac_chn_settlement)
               D.Trans_Date = V_SAC_DATE
               AND D.TYPE = 'N'
             GROUP BY CHN_BATCH_NO
            UNION ALL
            SELECT BANK_NODE_CODE,
                   SAC_BANK_NAME,
                   ACCOUNT_NUMBER,
                   SAC_DATE,
                   TRX_DATE,
                   D.Trans_Date  AS TRANS_DATE,
                   TYPE,
                   TOTAL_NUM, --笔数
                   TOTAL_SUM      AS TRX_AMOUNT,
                   TRX_COST,
                   CURRENCY_TYPE,
                   CHN_BATCH_NO
              FROM SAC_CHN_SET_DETAIL D
             WHERE D.CHN_BATCH_NO NOT IN (SELECT CHN_BATCH_NO FROM SAC_CHN_SETTLEMENT)
               AND D.TYPE ='L');

  -- STEP_1.6 从sequence中取主键id
  --更新交易明细表渠道清分批次号
  v_step := '1.6';
  UPDATE MID_SAC_TRX_DETAIL D
     SET D.CHN_BATCH_NO =
         (SELECT P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' ||TRANS_DATE AS CHN_BATCH_NO
            FROM SAC_CHANNEL_PARAM P
           WHERE P.CHN_NO = D.CHN_NO
             AND P.CURRENCY_TYPE = D.TRX_CURRENCY_TYPE
             AND P.IS_VALID_FLAG = '1'
             AND P.CHN_TYPE IN ('1', '2','3','4') --取渠道类型为B2B，B2C
             AND D.PAYCON_TYPE = P.CHN_TYPE
           GROUP BY P.BANK_NODE_CODE,
                    P.CURRENCY_TYPE,
                    D.PAYCON_TYPE,
                    TRANS_DATE)
                  WHERE D.TRX_TYPE IN ('3302',
                  '1705',
                  '1302',
                  '1631',
                  '1601',
                  '1701',
                  '1305',
                  '1315',
                  '1621',
                  '1626',
                  '1312',
                  '3303',
                  '1730',
                  '5201'); --包括B2C退款(3303,1730)(逆向交易的交易类型代码)  ;

     V_STEP := '1.7';
  UPDATE MID_SAC_TRX_DETAIL D
     SET D.CHN_BATCH_NO =
         (SELECT P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' ||TRANS_DATE AS CHN_BATCH_NO
            FROM SAC_CHANNEL_PARAM P
           WHERE P.CHN_NO = D.CHN_NO
             AND P.CURRENCY_TYPE = D.SAC_CURRENCY
             AND P.IS_VALID_FLAG = '1'
             AND P.CHN_TYPE IN ('1', '2') --取渠道类型为B2B，B2C
             AND D.PAYCON_TYPE = P.CHN_TYPE
           GROUP BY P.BANK_NODE_CODE,
                    P.CURRENCY_TYPE,
                    D.PAYCON_TYPE,
                    TRANS_DATE)
                    WHERE D.TRX_TYPE IN ( '3423');

  UPDATE SAC_TRX_DETAIL D
     SET D.CHN_BATCH_NO =
         (SELECT CHN_BATCH_NO FROM MID_SAC_TRX_DETAIL M WHERE D.ID = M.ID)
   WHERE NVL(D.CHN_BATCH_NO,'1')= '1';

  COMMIT;

  ERR_NUM := 0;
  ERR_MSG := 'COMPLETE SUCCESS!';
  V_STEP  := SP_WLOG(V_SEQ,
                     V_SAC_DATE,
                     UPPER(V_SPNAME),
                     ERR_NUM,
                     ERR_MSG,
                     SYSDATE,
                     V_START_DATE,
                     '[V_FISRT_WORK_DATE]:'|| V_FISRT_WORK_DATE ||  '[V_SAC_DATE]:' || V_SAC_DATE || '[ELAPSED]:' ||
                     TO_CHAR((SYSDATE - V_START_DATE) * 86400, '99999') || 'S');

EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    ERR_NUM := SQLCODE;
    ERR_MSG := SUBSTR(SQLERRM, 1, 80);
    V_STEP  := SP_WLOG(V_SEQ,
                      V_SAC_DATE,
                       UPPER(V_SPNAME),
                       ERR_NUM,
                       ERR_MSG,
                       SYSDATE,
                       V_START_DATE,
                       '[V_SAC_DATE]:' || V_SAC_DATE || '[ELAPSED]:' ||
                       TO_CHAR((SYSDATE - V_START_DATE) * 86400, '99999') ||
                       '''S');
END;
/


spool off