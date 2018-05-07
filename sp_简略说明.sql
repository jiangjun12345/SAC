--�ַ�����MD5����
CREATE OR REPLACE FUNCTION MD5(
passwd IN VARCHAR2)
RETURN VARCHAR2
IS
retval varchar2(32);
BEGIN
retval := utl_raw.cast_to_raw(DBMS_OBFUSCATION_TOOLKIT.MD5(INPUT_STRING => passwd)) ;
RETURN LOWER(retval);
END;
--д��־
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
  * SP_BFJ_BANK_DATA_INSERT:������������������
  * ��WY_CHNL_RECONCILIATION���л�ȡ�ʽ��ļ�
  * ��跽����������ʱ��Ҫ�޳�������
  */
    
create or replace procedure SP_BFJ_BANK_DATA_INSERT(p_trx_date IN VARCHAR2,
                                               account_no        IN VARCHAR2,
                                               err_num     out number,
                                               err_msg     out varchar2) AS
  v_init_time  VARCHAR2(21); -- ��ʼ��ʱ��
  v_spname     VARCHAR2(32); -- �洢������
  v_start_date DATE; -- ϵͳ��ʼʱ��
  v_step       VARCHAR2(4000); -- ������
  v_seq        NUMBER; -- ��ѯsequence
  v_account_no    VARCHAR2(32);
  v_trx_date VARCHAR2(21);
  v_last_day   VARCHAR2(21);
  v_value     NUMBER;
  v_value_1     NUMBER;
BEGIN
  --------------------------- STEP_1 ��ʼ������ ---------------------------
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
select w.trx_date as trx_date, --��������
       b.account_no as account_no, --����31code_id
      -- b.bank_code,
       max(b.ACCOUNT_NAME) as ACCOUNT_NAME,
       sum(case
             when w.direction = '1' and remark not like '%������%' then
              w.amount
             else
             0
           end) as credit_amount, --����������
       sum(case
             when w.direction = '1' and remark not like '%������%' then
              1
             else
              0
           end) as credit_sum, --�������ױ���
       sum(case
             when w.direction = '2' and remark not like '%������%' then
              w.amount
             else
             0
           end) as debit_amount, --�跽������
       sum(case
             when w.direction = '2' and remark not like '%������%' then
              1
             else
              0
           end) as debit_sum, --�跽���ױ���
       0 as balance_amount, --���
       sum(case
             when  w.direction = '1' and (remark like '%�ʽ����%'  or remark in ('���','�ʽ𻮲�'))  then
              amount
             else
            0
           end) as credit_move_amount, --�ʽ������������
       sum(case
             when w.direction = '2' and (remark like '%�ʽ����%' or remark in ('07')) then
              amount
             else
              0
           end) as debit_move_amount, --�ʽ�����跽����
       sum(case
             when (remark like '%����ת��%' or remark like '%��Ϣ%') or
                  bus_type = '0031' then
              amount
             else
              0
           end) as interest_amount, --��Ϣ
       0 poundage_amount --������
  from (  select (case when chnl_no in ('99990310')
        then 'SPDB000'  --�ַ�
          when chnl_no in ('99920301','99910301','99990301')
            then 'BC00000'  --����
         when chnl_no in ('99930105','99920105','99910105')
            then 'CCB0000'    -- ����
         when chnl_no in ('20140828','20140827','20140826','20140822')
            then 'CMBC000'    -- ����
            when chnl_no in ('99910104','99990104')
            then 'BOC0000'    --����
              else '0000000'
           end) as chn_code,substr(l.trade_date,0,8) as trx_date ,l.*
 ��from WY_CHNL_RECONCILIATION l) w,
 (select * from bfj_account_no_param  where memo = '������˺�') b       --��ȡ�������˻�
     where w.chn_code <> '0000000' --where w.trade_date = v_trx_date and w.chnl_no = v_account_no;
     and w.chn_code = b.bank_code
     and w.trx_date = v_trx_date
     and b.account_no = v_account_no
 group by b.account_no,b.bank_code, w.trx_date;
 commit;
  -- Ŀǰֻ�����������Ĳ���
  -- �������������Ľ��
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
     '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
     total_sum as  AMOUNT_SUM,
     total_num as COUNT_SUM
     from sac_chn_set_detail
     where chn_no = '8000000000056863000'
     and type = 'N'
     and sac_date = v_trx_date;
   END IF;

   select count(1) into v_value from  bfj_bank_diff_data where ACCOUNT_NO =v_account_no
   and TRX_DATE = v_trx_date ;


   --���û�в���������ݣ������һ��Ϊ0�ļ�¼
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
      '��������֧�����޹�˾�ͻ�������',
      0 ,
      0
    );
  END IF;

  select count(1) into v_value_1 from  bfj_bank_data where ACCOUNT_NO =v_account_no
   and TRX_DATE = v_trx_date ;

   --���û�����ݣ������һ��Ϊ0�ļ�¼
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
      '��������֧�����޹�˾�ͻ�������',
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
  v_init_time  VARCHAR2(21); -- ��ʼ��ʱ��
  v_spname     VARCHAR2(32); -- �洢������
  v_start_date DATE; -- ϵͳ��ʼʱ��
  v_step       VARCHAR2(4000); -- ������
  v_seq        NUMBER; -- ��ѯsequence
  v_account_no    VARCHAR2(32);
  v_trx_date VARCHAR2(21);
  v_last_day   VARCHAR2(21);
  v_value_5    NUMBER;
BEGIN
  --------------------------- STEP_1 ��ʼ������ ---------------------------
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

--ɾ���м��
delete from mid_bfj_bank_data;
delete from mid_bfj_bank_diff_data;
delete from mid_bfj_qjs_data;
delete from mid_bfj_qjs_diff_data;
delete from mid_bfj_specal_data;
delete from bfj_last_time_data where trx_date = v_trx_date and ACCOUNT_NO = v_account_no;
delete from bfj_history_data where trx_date = v_trx_date and ACCOUNT_NO = v_account_no;

commit;



insert into mid_bfj_bank_data   ----�м��
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

   insert into mid_bfj_bank_diff_data   ----�м��
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

   insert into mid_bfj_qjs_data   ----�м��
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

    insert into mid_bfj_qjs_diff_data   ----�м��
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

    insert into MID_BFJ_SPECAL_DATA   ----�м��
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

 insert into bfj_last_time_data   ---��Ҫ�õ���ǰһ������
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_01' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         to_char(k.credit_amount - k.credit_move_amount - k.interest_amount) as COL_VALUE --������ϵͳ��ӳ���������ҵ��ϵͳ�д��ǿͻ��ʽ��˻����
    from mid_bfj_bank_data k
    union all
    select
          'colG' as COL_NAME,
         'double' as COL_TYPE,
         to_char(COL_0101K) as COL_VALUE                                     --������ϵͳ��ӳ���������ҵ��ϵͳ�д��ǿͻ��ʽ��˻����
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
    union all
    select
          'colK' as COL_NAME,
         'double' as COL_TYPE,
          to_char(nvl(sum( case when k.ACCOUNT_NO = '453359895255' --����
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00)) as COL_VALUE                                                        --������ϵͳ��ӳ���������ҵ��ϵͳ�д��ǿͻ��ʽ��˻����
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_02' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
    select 'colB' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.credit_amount - k.credit_move_amount,'0.00') as COL_VALUE --����ҵ��ϵͳ�н�ǿͻ��ʽ��˻����
      from mid_bfj_qjs_data k
    union all
    select 'colC' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.credit_amount - k.credit_move_amount,'0.00') as COL_VALUE --����ҵ��ϵͳ�н�ǿͻ��ʽ��˻����
      from mid_bfj_qjs_data k
    union all
    select 'colD' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE  --����ϵͳ��ӳ�����ڳ���
      from mid_bfj_bank_data k
    union all
    select 'colE' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE  --����ϵͳ��ӳ�����ڳ���
      from mid_bfj_bank_data k
    union all
    select 'colF' as COL_NAME,
           'double' as COL_TYPE,
           to_number('0.00') as COL_VALUE
           from dual
  /*  select 'colF' as COL_NAME,
           'double' as COL_TYPE,
           nvl(k.COL_0109C, '0.00') - nvl(k1.COL_0109C, '0.00') as COL_VALUE                 --ǰ��ϵͳ��ӳ�����ڳ���
      from bfj_last_time_data k,
           MID_BFJ_SPECAL_DATA k1
     where k.trx_date = k1.trx_date
       and k.account_no = k1.account_no*/
    union all
    select 'colG' as COL_NAME,
           'double' as COL_TYPE,
           nvl(COL_0109E, '0.00') as COL_VALUE                                          --ǰ��ϵͳ��ӳ�����ڳ���
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_03' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE --���׳���
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_04' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE --����ҵ��ϵͳ�пͻ��ʽ��˻��跽������                                                 --��Ӧ�����ҵ�������ҵ��ϵͳ���Ѵ��ǿͻ��ʽ��˻����
    from mid_bfj_qjs_data k
     union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_AMOUNT - k.CREDIT_MOVE_AMOUNT - k.INTEREST_AMOUNT,'0.00') as COL_VALUE --����ҵ��ϵͳ�пͻ��ʽ��˻�����������                                                --��Ӧ�����ҵ�������ҵ��ϵͳ���Ѵ��ǿͻ��ʽ��˻����
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_05' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         COL_0105G as COL_VALUE                  --�ͻ��ʽ��˻��ڳ����
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
     union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_AMOUNT - k.DEBIT_MOVE_AMOUNT - k.POUNDAGE_AMOUNT,'0.00') as COL_VALUE    --�������ҵ����ǿͻ��ʽ��˻����
    from mid_bfj_qjs_data k
     union all
       select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_AMOUNT - k.CREDIT_MOVE_AMOUNT - k.INTEREST_AMOUNT,'0.00') as COL_VALUE   --���ڳ���ҵ���ǿͻ��ʽ��˻����
    from mid_bfj_qjs_data k
     union all
       select
         'colE' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.DEBIT_MOVE_AMOUNT,'0.00') as COL_VALUE        --�ͻ��ʽ��˻��跽������
    from mid_bfj_qjs_data k
      union all
       select
         'colF' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_MOVE_AMOUNT,'0.00') as COL_VALUE       --�ͻ��ʽ��˻�����������
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                   end) as COL_VALUE                         --�ͻ��ʽ��˻���ĩ���
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_06' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.CREDIT_MOVE_AMOUNT,'0.00') as COL_VALUE                              --���������м�ͷ������������У�
    from mid_bfj_bank_data k
    union all
    select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.INTEREST_AMOUNT,'0') as COL_VALUE                                   --�յ���Ϣ����
    from mid_bfj_bank_data k
    union all
    select
         'colL' as COL_NAME,
         'double' as COL_TYPE,
        nvl(k.CREDIT_MOVE_AMOUNT+ k.INTEREST_AMOUNT,'0.00') as COL_VALUE                  --���ӱ����������˻��ϼ�
    from mid_bfj_bank_data k
    union all
    select
         'colM' as COL_NAME,
         'double' as COL_TYPE,
        -abs(k.DEBIT_MOVE_AMOUNT) as COL_VALUE                               --���������м�ͷ������������У�
    from mid_bfj_bank_data k
    union all
    select
         'colO' as COL_NAME,
         'double' as COL_TYPE,
        -abs(k.POUNDAGE_AMOUNT) as COL_VALUE                                   --���п�ȡ�������ѡ�����ѵȷ���
    from mid_bfj_bank_data k
    union all
    select
         'colZ' as COL_NAME,
         'double' as COL_TYPE,
         -abs(k.DEBIT_MOVE_AMOUNT) -abs(k.POUNDAGE_AMOUNT) as COL_VALUE                  --���ٱ����������˺ϼ�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_09' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
          nvl(sum( case when k.ACCOUNT_NO = '453359895255' --����
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE
                                                       --������ϵͳ��ӳ���������ҵ��ϵͳ�д��ǿͻ��ʽ��˻����
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
    union all
      select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0109C,'0.00') as COL_VALUE                  --֧������ҵ��ϵͳ�Ѽ��ٿͻ��ʽ��˻�������������δ���ٱ����������˻����
    from mid_bfj_specal_data k
     union all
      select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0109D,'0.00') as COL_VALUE                  --���������������ӱ����������˻���֧������ҵ��ϵͳδ���ӿͻ��ʽ��˻����
    from mid_bfj_specal_data k
      union all
      select
         'colE' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0109E,'0.00') as COL_VALUE                  --�����������Ѽ��ٱ����������˻���֧������ҵ��ϵͳδ���ٿͻ��ʽ��˻����
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_10' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
    select
         'colB' as COL_NAME,
         'int' as COL_TYPE,
         k.COUNT_SUM as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_bank_diff_data k
    union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(sum( case when k.ACCOUNT_NO = '453359895255' --����
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE                                                     --��֧������ҵ��ϵͳ�����ӿͻ��ʽ��˻�������������δ���ӱ����������˻�����5�ձ���
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
      union all
       select
         'colH' as COL_NAME,
         'int' as COL_TYPE,
          nvl(k.COL_0110H,'0') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
      union all
       select
         'colI' as COL_NAME,
         'double' as COL_TYPE,
          nvl(k.COL_0110I,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
       select
         'colN' as COL_NAME,
         'int' as COL_TYPE,
          nvl(k.COL_0110N,'0') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
       select
         'colO' as COL_NAME,
         'double' as COL_TYPE,
          nvl(k.COL_0110O,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
       select
         'colT' as COL_NAME,
         'int' as COL_TYPE,
          nvl(k.COL_0110T,'0') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
       select
         'colU' as COL_NAME,
         'double' as COL_TYPE,
          nvl(k.COL_0110U,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
         'Table1_11' as TABLE_CODE,
         COL_NAME,
         COL_TYPE,
         COL_VALUE
   from
     (
      select
         'colB' as COL_NAME,
         'double' as COL_TYPE,
         nvl(col_0111C,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
    union all
       select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
          nvl(K.BALANCE_AMOUNT,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_qjs_data k
    union all
       select
         'colD' as COL_NAME,
         'double' as COL_TYPE,
          nvl(K.BALANCE_AMOUNT-b.col_0111C,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_qjs_data k ,bfj_last_time_data b
    where k.trx_date = b.trx_date
    and k.account_no = b.account_no
    and b.trx_date = v_trx_date and b.ACCOUNT_NO = v_account_no
    union all
       select
         'colI' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(COL_0111I),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data
    union all
       select
         'colJ' as COL_NAME,
         'double' as COL_TYPE,
         nvl(sum( case when k.ACCOUNT_NO = '453359895255' --����
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE                                                      --��֧������ҵ��ϵͳ�����ӿͻ��ʽ��˻�������������δ���ӱ����������˻�����5�ձ���
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no
      union all
       select
         'colK' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111K),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
       union all
       select
         'colL' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111L),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
       union all
       select
         'colM' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111M),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
      union all
       select
         'colN' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.INTEREST_AMOUNT),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_bank_data k
    union all
       select
         'colO' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.COL_0111O),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
       select
         'colP' as COL_NAME,
         'double' as COL_TYPE,
          nvl(to_number(K.POUNDAGE_AMOUNT),'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_bank_data k
    union all
       select
         'colAA' as COL_NAME,
         'double' as COL_TYPE,
          nvl(COL_0111AB,'0.00') as COL_VALUE                              --���������м�ͷ������������У�
    from bfj_last_time_data
    where trx_date = v_trx_date and ACCOUNT_NO = v_account_no
        union all
       select
         'colAB' as COL_NAME,
         'double' as COL_TYPE,
           (case when v_account_no = '0201014210021452' then
               nvl(to_number(m.col_0201d),'0.00')
               else  nvl(to_number(K.BALANCE_AMOUNT),'0.00')
                 end)  as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                   end) as COL_VALUE                  --���������м�ͷ������������У�
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
                   end) as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                   end) as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                 end)  as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_bank_data k,MID_BFJ_SPECAL_DATA m
    where k.trx_date = m.trx_date
     and k.account_no = m.account_no
    union all
     select
         'colC' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.interest_amount,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_bank_data k
    union all
     select
         'colF' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112F,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
     select
         'colH' as COL_NAME,
         'double' as COL_TYPE,
         nvl(sum( case when k.ACCOUNT_NO = '453359895255' --����
                  then nvl(k.AMOUNT_SUM+p.BALANCE_AMOUNT,'0.00')
                 else
                  0.00
                end
            ),0.00) as COL_VALUE                                                       --��֧������ҵ��ϵͳ�����ӿͻ��ʽ��˻�������������δ���ӱ����������˻�����5�ձ���
    from mid_bfj_bank_diff_data k,mid_bfj_qjs_diff_data p  where
         k.trx_date = p.trx_date
    and k.account_no = p.account_no                  --���������м�ͷ������������У�
    union all
     select
         'colI' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112I,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
     select
         'colJ' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112J,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
     union all
     select
         'colK' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.COL_0112K,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
    from mid_bfj_specal_data k
    union all
     select
         'colR' as COL_NAME,
         'double' as COL_TYPE,
         nvl(k.BALANCE_AMOUNT,'0.00') as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                   end) as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                   end) as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                   end) as COL_VALUE                  --���������м�ͷ������������У�
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
         '��������֧�����޹�˾�ͻ�������' as ACCOUNT_NAME,
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
                 end)  as COL_VALUE                  --���������м�ͷ������������У�
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
* ����ƽ̨����
* ��Ҫ�Ӽ�����ϸ��������
* �������跽����fin_stat_bank�м�������ʽ������fin_mx������
*/
create or replace procedure SP_BFJ_QJS_DATA_INSERT(p_trx_date IN VARCHAR2,
                                               account_no        IN VARCHAR2,
                                               err_num     out number,
                                               err_msg     out varchar2) AS
  v_init_time  VARCHAR2(21); -- ��ʼ��ʱ��
  v_spname     VARCHAR2(32); -- �洢������
  v_start_date DATE; -- ϵͳ��ʼʱ��
  v_step       VARCHAR2(4000); -- ������
  v_seq        NUMBER; -- ��ѯsequence
  v_account_no    VARCHAR2(32);
  v_trx_date VARCHAR2(21);
  v_last_day   VARCHAR2(21);
  v_value      NUMBER;
  v_value_1      NUMBER;
BEGIN
  --------------------------- STEP_1 ��ʼ������ ---------------------------
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
select m.trx_date, --����ʱ��
       b.account_no as account_no, --����31code_id
    --   b.bank_code,
       b.ACCOUNT_NAME as ACCOUNT_NAME,
       m.credit_amount, --����������
       m.debit_amount, --�跽������
       m.balance_amount, --���
       nvl(p.credit_move_amount,0.00) credit_move_amount, --�ʽ��������
       nvl(p.debit_move_amount, 0.00) debit_move_amount, --�跽������
       nvl(p.interest_amount, 0.00) interest_amount, --��Ϣ
       nvl(p.poundage_amount, 0.00) poundage_amount --��������
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
   (select * from bfj_account_no_param  where memo = '������˺�') b
    where m.trx_date = p.trx_date(+)
     and m.code_id = p.code_id(+)
     and b.bank_code = c.chn_code
     and m.code_id = c.chn_no
     and m.trx_date = v_trx_date
     and b.account_no = v_account_no
;
 commit;
  -- Ŀǰֻ�����������Ĳ���
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
     '����֧��������' as ACCOUNT_NAME,
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
      '��������֧�����޹�˾�ͻ�������',
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
      '��������֧�����޹�˾�ͻ�������',
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
* ����ÿ�����
* ��fin_mx�м���������跽�ͷ�����
* �ڳ���1 ��T��fin_mx�У���pz_no�����õ�һ�� 2���ô���T��fin_mx�У���pz_no�����õ�һ�� 3��fin_ye�н��
* ��ĩ��1 ��T��fin_mx�У���pz_no�����õ�һ�� 2���ô���T��fin_mx�У���pz_no�����õ�һ�� 3��fin_ye�н��
*/
create or replace procedure SP_FIN_STAT_BANK(p_stat_date IN VARCHAR2,
                                             err_num     out number,
                                             err_msg     out varchar2) AS
  v_trade_time    DATE; --
  v_init_time  VARCHAR2(21); -- ��ʼ��ʱ��
  v_spname     VARCHAR2(32); -- �洢������
  v_start_date DATE; -- ϵͳ��ʼʱ��
  v_step       VARCHAR2(8); -- ������
  v_seq        NUMBER; -- ��ѯsequence
  v_result     NUMBER;
BEGIN
  --------------------------- STEP_1 ��ʼ������ ---------------------------
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
                   sum(f1.fdebit) - sum(f1.fcredit) as AMOUNT  --������
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
* �̻�ÿ�����
* ��fin_mx�м���������跽�ͷ�����
* �ڳ���1 ��T��fin_mx�У���pz_no�����õ�һ�� 2���ô���T��fin_mx�У���pz_no�����õ�һ�� 3��fin_ye�н��
* ��ĩ��1 ��T��fin_mx�У���pz_no�����õ�һ�� 2���ô���T��fin_mx�У���pz_no�����õ�һ�� 3��fin_ye�н��
*/
create or replace procedure SP_FIN_STAT_ORG(p_stat_date IN VARCHAR2,
                                             err_num     out number,
                                             err_msg     out varchar2) AS
  v_trade_time    DATE; --
  v_init_time  VARCHAR2(21); -- ��ʼ��ʱ��
  v_spname     VARCHAR2(32); -- �洢������
  v_start_date DATE; -- ϵͳ��ʼʱ��
  v_step       VARCHAR2(8); -- ������
  v_seq        NUMBER; -- ��ѯsequence
  v_result     NUMBER;
BEGIN
  --------------------------- STEP_1 ��ʼ������ ---------------------------
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
* ����ƽ��
* ��fin_mx�м��������Ӧ���˿���跽�����д���Ӧ���˿
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
  *    �������ƣ�SP_SAC_CHN_SETTLEMENT
  *    �����������������Ӧ����ϸ
  *    ���������P_SAC_DATE            VARCHAR2        ��������
  *    ���������ERR_NUM               NUMBER          ���ش���
  *              ERR_MSG               VARCHAR2        ������Ϣ
  *    ��д��Ա��������
  *    �������ڣ�20150703
  *    ������ ��
  *    1. ���ͬ�ڵ���������
  *
  *    �޸���ʷ��
       0114��������еĳ����޳����Լ��������������
       0118: ���������ϸ��ȷ������һ��
  */
 AS
  V_SAC_DATE   VARCHAR2(21); -- ��������
  V_SPNAME     VARCHAR2(32); -- �洢������
  V_START_DATE DATE; -- ϵͳ��ʼʱ��
  V_STEP       VARCHAR2(8); -- ������
  V_RUN_DATE   VARCHAR2(21); -- ��������
  V_SEQ        NUMBER; -- ��ѯsequence
  V_FISRT_WORK_DATE  VARCHAR2(8);
  V_PAOPI_DATE  VARCHAR2(8);


BEGIN
  --------------------------- STEP_1 ��ʼ������ ---------------------------
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

  -- STEP_1.1 ����м������
  V_STEP := '1.1';
  DELETE FROM MID_SAC_TRX_DETAIL;
  commit;

  -- STEP_1.2 ������ʼ�����ݷ�Χ
  /*
    ȡT-1�ս���Ϊ�ɹ�����S����������
  */
  V_STEP := '1.2';
  INSERT INTO MID_SAC_TRX_DETAIL --����ʱ��ͽ�����ϸ����ͬ
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
            TRX_SUCC_TIME,            ---��������
            ( CASE WHEN CHN_NO IN(   --�ڼ������
              '8000000000056863000'  --����B2B B2C
             ,'8000000000380318000') --����B2B B2C
               AND V_FISRT_WORK_DATE != 0 and V_FISRT_WORK_DATE != V_SAC_DATE  --�ڼ������
                THEN  V_FISRT_WORK_DATE
               WHEN CHN_NO IN(   --�ڼ������
              '8000000000056863000'--����B2B B2C
             , '8000000000380318000')--����B2B B2C
                 AND V_FISRT_WORK_DATE = V_SAC_DATE  --�ڼ������
                THEN  TO_CHAR(TRX_SUCC_TIME+1,'YYYYMMDD')
              WHEN CHN_NO IN
                ('8000000000056863000'--����B2B B2C
                ,'8000000000380318000'--����B2B B2C
                 ) AND V_FISRT_WORK_DATE = 0
                   THEN (CASE WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 6  --������  ����ʱ��Ϊ����һ
                          THEN TO_CHAR(TRX_SUCC_TIME+3,'YYYYMMDD')
                           WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 7  --������  ����ʱ��Ϊ����һ
                             THEN TO_CHAR(TRX_SUCC_TIME+2,'YYYYMMDD')
                               ELSE  TO_CHAR(TRX_SUCC_TIME+1,'YYYYMMDD')
                                 END
                            )
                WHEN  CHN_NO = '8000000000344866000' AND TRX_TYPE='1621'   -- �ַ������ֽ��
                     THEN (CASE WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 2 --����һ
                                    THEN TO_CHAR(TRX_SUCC_TIME+2,'YYYYMMDD')
                              WHEN (TO_CHAR(TRX_SUCC_TIME,'D') = 3 or TO_CHAR(TRX_SUCC_TIME,'D') = 5) --���ڶ�,��
                                    THEN TO_CHAR(TRX_SUCC_TIME+1,'YYYYMMDD')
                               WHEN (TO_CHAR(TRX_SUCC_TIME,'D') = 4 and  TO_CHAR(TRX_SUCC_TIME,'hh24') >= 13)  --����������13��
                                    THEN TO_CHAR(TRX_SUCC_TIME+2,'YYYYMMDD')
                               WHEN (TO_CHAR(TRX_SUCC_TIME,'D') = 6 and TO_CHAR(TRX_SUCC_TIME,'hh24') >= 13)  --����������13��
                                    THEN TO_CHAR(TRX_SUCC_TIME+5,'YYYYMMDD')
                               WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 7  --������  ����ʱ��Ϊ������
                                    THEN TO_CHAR(TRX_SUCC_TIME+4,'YYYYMMDD')
                               WHEN TO_CHAR(TRX_SUCC_TIME,'D') = 1  --������  ����ʱ��Ϊ������
                                    THEN TO_CHAR(TRX_SUCC_TIME+3,'YYYYMMDD')
                               ELSE TO_CHAR(TRX_SUCC_TIME,'YYYYMMDD') --������,������       
                              END
                       )
                ELSE TO_CHAR(TRX_SUCC_TIME,'YYYYMMDD')
              END) AS TRANS_DATE    --��������
      FROM SAC_TRX_DETAIL
     WHERE
     TRX_STATE = 'S' --����״̬
     AND NVL(CHN_BATCH_NO,'1') = '1'
     AND TRX_SUCC_TIME <= TO_DATE(V_SAC_DATE ||  ' 23:59:59','YYYYMMDD HH24:MI:SS')
     AND ((CHA_CON_STATUS = 'S' AND TRX_TYPE IN ('3302','1302', '1631', '1315', '1312','3303','1730'))
            OR TRX_TYPE IN ('1601', '1705','3423', '1701', '1305','5201')
            OR (CHN_NO = '8000000000344866000' AND TRX_TYPE='1621'));


    INSERT INTO MID_SAC_TRX_DETAIL --����ʱ��ͽ�����ϸ����ͬ
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
            A.TRX_SUCC_TIME,                 ---��������
            to_char(B.Bank_Trx_Date,'yyyymmdd') AS TRANS_DATE    --��������
      FROM SAC_TRX_DETAIL A, sac_deposit_detail B
     WHERE
     A.TRX_SERIAL_NO = B.trx_serial_no
     AND A.TRX_STATE = 'S' --����״̬������
     AND NVL(A.CHN_BATCH_NO,'1') = '1'
     AND TRX_SUCC_TIME <= TO_DATE(V_SAC_DATE ||  ' 23:59:59','YYYYMMDD HH24:MI:SS')
     AND TRX_TYPE in ('1621','1626')
     AND CHN_NO <> '8000000000344866000' ;

  --  and ���˳ɹ�; --ȡ����״̬���ڳɹ��Ľ���

  -- STEP_1.3 ���������ϸ
  /*
    1. �����������֡�֧�����������������ϸ
    2. ���������ף������� �Լ������
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
      FROM (SELECT --����֧�������տ�
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             '' AS TRX_DATE,
             TRANS_DATE AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             'N' AS TYPE, --����������N�������L�����̿S����������O��
             COUNT(1) AS TOTAL_NUM, --����
             SUM(D.TRX_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             D.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' || TRANS_DATE AS CHN_BATCH_NO,
             '1' AS BUSI_TYPE --������
              FROM SAC_CHANNEL_PARAM P, MID_SAC_TRX_DETAIL D
             WHERE P.CHN_NO = D.CHN_NO
               AND P.CURRENCY_TYPE = D.TRX_CURRENCY_TYPE
               AND P.IS_VALID_FLAG = '1'
               AND P.CHN_TYPE IN ('1', '2','3','4') --ȡ��������ΪB2B��B2C
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
            --֧������(3302) �����ʽ�����(1705) ����֧��(1302) ����Ԥ��(1631) ���Ԥ��(1601) ���ֱ֧ͨ��(1305) ����ֱ֧ͨ��(1315) ��ȷ������Ԥ��(1621) δȷ������Ԥ��(1626) ����֧��(1312)
             GROUP BY P.BANK_NODE_CODE,
                      P.CHN_NO,
                      P.CURRENCY_TYPE,
                      D.PAYCON_TYPE,
                      TRANS_DATE
            UNION ALL
            SELECT --����֧�������տ�
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             '' AS TRX_DATE,
             TRANS_DATE AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             'N' AS TYPE, --����������N�������L�����̿S����������O��
             COUNT(1) AS TOTAL_NUM, --����
             SUM(D.SAC_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             D.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' || TRANS_DATE AS CHN_BATCH_NO,
             '1' AS BUSI_TYPE --������
              FROM SAC_CHANNEL_PARAM P, MID_SAC_TRX_DETAIL D
             WHERE P.CHN_NO = D.CHN_NO
               AND P.CURRENCY_TYPE = D.SAC_CURRENCY
               AND P.IS_VALID_FLAG = '1'
               AND P.CHN_TYPE IN ('1', '2') --ȡ��������ΪB2B��B2C
               AND D.PAYCON_TYPE = P.CHN_TYPE
               AND D.TRX_TYPE IN ('3423')
            --�˿��㽻��(3423)
             GROUP BY P.BANK_NODE_CODE,
                      P.CHN_NO,
                      P.CURRENCY_TYPE,
                      D.PAYCON_TYPE,
                      TRANS_DATE
            UNION ALL
            SELECT --����֧����������
             P.CHN_NO,
             P.BANK_NODE_CODE AS BANK_NODE_CODE,
             MAX(P.SAC_BANK_NAME) AS SAC_BANK_NAME,
             MAX(P.CHN_NAME) AS CHN_NAME,
             MAX(P.BANK_ACC) AS BANK_ACC,
             '' AS TRX_DATE,
             TRANS_DATE AS TRANS_DATE,
             V_PAOPI_DATE AS SAC_DATE,
             'N' AS TYPE,
             COUNT(1) AS TOTAL_NUM, --����
             SUM(D.TRX_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             D.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' ||TRANS_DATE AS CHN_BATCH_NO,
             '0' AS BUSI_TYPE --������
              FROM SAC_CHANNEL_PARAM P, MID_SAC_TRX_DETAIL D
             WHERE P.CHN_NO = D.CHN_NO
               AND P.CURRENCY_TYPE = D.TRX_CURRENCY_TYPE
               AND P.IS_VALID_FLAG = '1'
               AND P.CHN_TYPE IN ('1', '2') --ȡ��������ΪB2B��B2C
               AND D.PAYCON_TYPE = P.CHN_TYPE
               AND D.TRX_TYPE IN ('3303', '1730') --����B2C�˿�(3303,1730)(�����׵Ľ������ʹ���)
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
             'L' AS TYPE,--����
             COUNT(1) AS TOTAL_NUM, --����
             SUM(S.PAY_AMOUNT) TRX_AMOUNT,
             '0' AS TRX_COST,
             P.CURRENCY_TYPE,
             S.PAYCON_TYPE,
             P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_L_' ||
             S.PAYCON_TYPE || '_' || TO_CHAR(S.UPDATE_TIME-1, 'YYYYMMDD') AS CHN_BATCH_NO,
             S.BUSI_TYPE AS BUSI_TYPE
              FROM SAC_CHANNEL_PARAM P, SAC_REC_DIFFERENCES S
             WHERE S.REC_DIFF_TYPE in ('CHA000','CHA002') --�����
               AND S.STATUS = 'N' -- δ����
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

  -- STEP_1.5 ��sequence��ȡ����id
  --����ֽ������SAC_CHN_SETTLEMENT
  /*
    ����������ֽ��
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
     CHN_BATCH_NO --����������κ�
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
                   SUM( CASE WHEN CHN_NO IN    --����ͳ�����һ��
                           ('7000000000056863000','8000000000056863000'--����B2B B2C
                           ,'7000000000380318000','8000000000380318000'--����B2B B2C
                          ) THEN 1
                            ELSE TOTAL_NUM
                         END
                   ) AS TOTAL_NUM, --����
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
                   TOTAL_NUM, --����
                   TOTAL_SUM      AS TRX_AMOUNT,
                   TRX_COST,
                   CURRENCY_TYPE,
                   CHN_BATCH_NO
              FROM SAC_CHN_SET_DETAIL D
             WHERE D.CHN_BATCH_NO NOT IN (SELECT CHN_BATCH_NO FROM SAC_CHN_SETTLEMENT)
               AND D.TYPE ='L');

  -- STEP_1.6 ��sequence��ȡ����id
  --���½�����ϸ������������κ�
  v_step := '1.6';
  UPDATE MID_SAC_TRX_DETAIL D
     SET D.CHN_BATCH_NO =
         (SELECT P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' ||TRANS_DATE AS CHN_BATCH_NO
            FROM SAC_CHANNEL_PARAM P
           WHERE P.CHN_NO = D.CHN_NO
             AND P.CURRENCY_TYPE = D.TRX_CURRENCY_TYPE
             AND P.IS_VALID_FLAG = '1'
             AND P.CHN_TYPE IN ('1', '2','3','4') --ȡ��������ΪB2B��B2C
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
                  '5201'); --����B2C�˿�(3303,1730)(�����׵Ľ������ʹ���)  ;

     V_STEP := '1.7';
  UPDATE MID_SAC_TRX_DETAIL D
     SET D.CHN_BATCH_NO =
         (SELECT P.BANK_NODE_CODE || '_' || P.CURRENCY_TYPE || '_N_' ||TRANS_DATE AS CHN_BATCH_NO
            FROM SAC_CHANNEL_PARAM P
           WHERE P.CHN_NO = D.CHN_NO
             AND P.CURRENCY_TYPE = D.SAC_CURRENCY
             AND P.IS_VALID_FLAG = '1'
             AND P.CHN_TYPE IN ('1', '2') --ȡ��������ΪB2B��B2C
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