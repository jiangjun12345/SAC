package net.easipay.cbp.constant;

public final class Constants {
	
	
  /**
   * 东方支付组织机构代码
   */
  public static final String EASIPAY_CARD_ID = "674612978";
  /**
   * 交易系统退款交易集合
   */
  public static final String REFUND_TRANS_TYPE = "'3303','3305'";

  /**
   * 交易类型：0001 冲正交易
   */
  public static final String REVERSAL_WIPE = "4201";
  
  /**
   * 交易类型：1612 线下取回
   */
  public static final String OFL_GIVE = "1612";
  

  /**
   * 交易类型：0002 资金调拨交易
   */
  public static final String FUND_ALLOT = "1701";
  
  /**
   * 交易类型： 调账
   */
  public static final String RECON_ACCOUNT = "4101";

  /**
   * 交易类型：0003 结算划款交易
   */
  public static final String SETTLE_ALLOT = "4103";
  /**
   * 交易类型：0004 补单
   */
  public static final String ADJUST_DATA = "4104";
  /**
   * 交易类型：利息录入
   */
  public static final String INTERREST_IN = "4401";
  /**
   * 交易类型：利息转出
   */
  public static final String INTERREST_OUT = "4402";
  /**
   * 交易类型：补账录入
   */
  public static final String SUPPLEMENT_ACCOUNT_IN = "4411";
  /**
   * 交易类型：补账转出
   */
  public static final String SUPPLEMENT_ACCOUNT_OUT = "4412";
  /**
   * 交易类型：费用支出录入
   */
  public static final String FEE_EXPENSE_IN = "4413";
  /**
   * 交易类型：费用支出结转
   */
  public static final String FEE_EXPENSE_FORWARD = "4414";
  /**
   * 交易类型：代付交易
   */
  public static final String REPLACE_PAY = "5101";
  /**
   * The name of the ResourceBundle used in this application
   */
  public static final String BUNDLE_KEY = "ApplicationResources";
  /**
   * 会计记账digest模板
   */
  public static final String TEMPLATE_DIGEST = "%s,{%s}付给{%s}{%s}{%s}";
  /**
   * 会计记账digest模板
   */
  public static final String TEMPLATE_PARAMS = "%s,%s,%s,%s|%s,%s,%s,%s";

  /**
   * 会计记账digest模板匹配符
   */
  public static final String TEMPLATE_PATTERN = "%s";
  
  /**
   * 
   */
  public static final String TEMPLATE_COMMA = ",";
  
  /**
   *
   */
  public static final String TEMPLATE_SPLIT = "|";

  /**
   * 代付审核结果通知代收付
   */
  public static final String HELP_PAY_CHECK = "DSF-IC-0011";
  /**
   * 调账:记账
   */
  public static final String RECONLICIATION_ACCOUNT = "SAC-FA-0002";
  /**
   * 账务系统通知交易审核结果接口:notifyTrxConfirmResult
   */
  public static final String NOTIFY_CONFIRM_RESULT = "SAC-AC-0011";

  /**
   * 账务系统交易数据接收接口:receiveSacTransationDetail
   */
  public static final String RCV_TRANSACTION = "SAC-AC-0001";
  /**
   * 会计记账接口:joinRuleFinTask
   */
  public static final String JOIN_FIN_TASK = "SAC-AC-0010";

  /**
   * 账务系统交易数据接收接口:updateSacTransationDetail
   */
  public static final String UPDATE_TRANSACTION = "SAC-AC-0002";

  /**
   * 账务系统更新差错数据状态接口:updateSacRecDifferences
   */
  public static final String UPDATE_REC_DIFF_STATE = "SAC-AC-0009";
  
  /**
   * 更新付汇批次号接口
   */
  public static final String UPDATE_BATCH_NO = "SAC-AC-0015";

  /**
   * 账务系统更新差错数据状态接口:updateUcOperator
   */
  public static final String CHANGE_PWD = "SAC-UC-0007";
  /**
   * :selectUcOperator
   */
  public static final String SELECT_OPERATOR = "SAC-UC-0008";

  /**
   * 账务系统交易数据接收接口:updateSacTransationDetail
   */
  public static final String GET_PRESOTRE_TRX = "DSF-IC-0004";

  /**
   * :调用收单系统接口获取交易
   */
  public static final String SELECT_TRANSACTION_ORDER = "PEPS-ORD-0001";

  /**
   * :调用账务中心接口获取交易
   */
  public static final String SELECT_TRANSACTION_AC = "PEPS-AC-0001";

  /**
   * :调用B2B系统获取交易
   */
  public static final String SELECT_TRANSACTION_B2B = "B2B-0001";

  /**
   * 对账类型
   */
  public static final String REC_TYPE = "01";

  /**
   * 处理类型
   */
  public static final String DEAL_TYPE = "19";

  /**
   * 审核状态
   */
  public static final String CONFIRM_STATE = "04";

  /**
   * 勾兑状态
   */
  public static final String WIPE_STATE = "05";

  /**
   * 结算划款状态
   */
  public static final String SETTLEMENT_STATE = "06";

  /**
   * 交易状态
   */
  public static final String TRANSACTION_STATE = "07";

  /**
   * 结算周期类型
   */
  public static final String SETTLEMENT_CYCLE = "08";

  /**
   * 业务类型
   */
  public static final String BUSS_TYPE = "09";

  /**
   * 银行
   */
  public static final String BANK_TYPE = "10";
  
  /**
   * 分行
   */
  public static final String BRANCH_TYPE = "704";
  
  /**
   * 币种
   */
  public static final String CURRENCY_TYPE = "11";
  /**
   * 对账差错类型
   */
  public static final String REC_DIFF_TYPE = "12";

  /**
   * 可用标志
   */
  public static final String ENABLE_FLAG = "13";

  /**
   * 支付方式
   */
  public static final String PAY_TYPE = "14";

  /**
   * 证件类型
   */
  public static final String CERTIFICATE_TYPE = "15";

  /**
   * 客户类型
   */
  public static final String CUSTOMER_TYPE = "16";

  /**
   * 操作类型
   */
  public static final String OPER_TYPE = "17";

  /**
   * 交易类型
   */
  public static final String TRX_TYPE = "20";
  /**
   * 补单标志
   */
  public static final String SUPPLEMENT_FLAG = "35";
  /**
   * 线上出款批次状态
   */
  public static final String FUND_BATCH_STATE = "50";
  /**
   * 预存批次状态
   */
  public static final String PRESTORE_BATCH_STATE = "51";
  /**
   * 预存交易状态
   */
  public static final String PRESTORE_DETAIL_STATE = "52";
  /**
   * 核销状态
   */
  public static final String CAV_STATE = "53";

  /**
   * 操作类型 退款 --审核通过
   */
  public static final String OPER_REFUND_PASS = "01";

  /**
   * 操作类型 退款 --审核不通过
   */
  public static final String OPER_REFUND_FORBID = "02";

  /**
   * 操作类型 实收勾兑
   */
  public static final String OPER_REAL_RECEIVE = "03";

  /**
   * 操作类型 资金调拨录入
   */
  public static final String OPER_FUND_ALLOT = "04";

  /**
   * 操作类型 资金调拨复核
   */
  public static final String OPER_FUND_ALLOT_CHECK = "05";

  /**
   * 操作类型 冲正录入
   */
  public static final String OPER_REVERSAL_WIPE = "06";

  /**
   * 操作类型 冲正复核
   */
  public static final String OPER_REVERSAL_WIPE_CHECK = "07";

  /**
   * 操作类型 补单
   */
  public static final String OPER_MANUAL_ADD_RECORD = "08";
  
  /**
   * 操作类型 调账
   */
  public static final String OPER_MANUAL_RECONLICIATION_ACCOUNT = "09";
  
  /**
   * 操作类型 利息录入
   */
  public static final String OPER_MANUAL_INTERREST_IN = "10";
  /**
   * 操作类型 利息转出
   */
  public static final String OPER_MANUAL_INTERREST_OUT = "11";
  /**
   * 操作类型 补账录入
   */
  public static final String OPER_MANUAL_SUPPLEMENT_IN = "12";
  /**
   * 操作类型 补账转出
   */
  public static final String OPER_MANUAL_SUPPLEMENT_OUT = "13";
  
  /**
   *  操作类型 差错处理
  */
 public static final String OPER_DIFF_HANDLE = "14";
 
 /**
  * 操作类型 预存批次制作
  */
 public static final String OPER_PRESTORE_CREATE_BATCH = "15";
 /**
  * 操作类型 预存批次复核
  */
 public static final String OPER_PRESTORE_CHECK_BATCH = "16";
 /**
  * 操作类型 预存销账经办
  */
 public static final String OPER_PRESTORE_MANUAL_MATCH = "17";
 /**
  * 操作类型 预存销账复核
  */
 public static final String OPER_PRESTORE_MANUAL_MATCH_CHECK = "18";
 /**
  * 操作类型 线上出款处理-划款指令处理
  */
 public static final String OPER_FUND_OL_GIVE_HANDLE = "19";
 /**
  * 操作类型 线上出款处理-划款批次作废
  */
 public static final String OPER_FUND_OL_GIVE_CANCEL = "26";
 /**
  * 操作类型 线上出款复核-划款指令复核
  */
 public static final String OPER_FUND_OL_GIVE_CHECK = "20";
 /**
  * 操作类型 线下出款复核-划款指令复核
  */
 public static final String OPER_FUND_OFF_GIVE_CHECK = "21";
 
 /**
  * 操作类型 银联B2B退款批次上传
  */
 public static final String B2B_REFUND_UPLOAD = "22";
 
 /**
  * 操作类型 银联B2b退款批次复核
  */
 public static final String B2B_REFUND_CHECK= "23";
 
 
 /**
  * 操作类型 银联B2c退款经办
  */
 public static final String B2C_REFUND_WAIT_CHECK = "24";
 
 
 /**
  * 操作类型 银联B2c退款复核
  */
 public static final String B2C_REFUND_CHECK = "25";
 
 /**
  * 操作类型  费用支出录入审核
  */
 public static final String FEE_IN_CHECK = "26";
 /**
  * 操作类型  费用支出结转审核
  */
 public static final String FEE_OUT_CHECK = "27";
 
 /**
  * 操作类型  支付交易审核
  */
 public static final String REPLACE_PAY_CHECK = "28";
 
 
 
  public static final String TPL_WP_RUFUND_REPORT_XLS = "template/WpRufundReport.xls";

  /**
   * 预存申请状态-自动核销
   */
  public static String CHARGE_STATE_1 = "1";// 自动核销
  /**
   * 预存申请状态-未核销
   */
  public static String CHARGE_STATE_0 = "0";// 未核销
  /**
   * 预存申请状态-手动核销
   */
  public static String CHARGE_STATE_2 = "2";// 手动核销

  /**
   * 退款状态类型
   */
  public static final String REFUND_STATE_NEW = "1";
  public static final String REFUND_STATE_WAITTING = "2";
  public static final String REFUND_STATE_NOT_SUCC = "3";
  public static final String REFUND_STATE_SUCC = "4";

  /**
   * 退款批次状态类型 00: 待处理;；02 复核失败； 20 复核通过
   */
  public static final String REFUND_BATCH_TATE_NEW = "00";
  public static final String REFUND_BATCH_TATE_SUCC = "20";
  public static final String REFUND_BATCH_TATE_NOT_SUCC = "02";

  /**
   * B2C退款状态类型 00: 待处理;02 复核不通过；10 已处理待复核； 20 已复核；30 处理完毕；90 废弃
   */

  public static final String REFUND_B2C_STATE_NEW = "317";
  public static final String REFUND_B2C_STATE_WAIT_CHECK = "319";
  public static final String REFUND_B2C_STATE_CHECK_NOT_SUCC = "318";
  public static final String REFUND_B2C_STATE_CHECK_SUCC = "320";
  
  public static final String OFL_PRESTORE_CHECK_PASS_URL = "333";
  public static final String OFL_PRESTORE_MANUAL_CHECK_PASS_URL = "334";
  public static final String ONL_FUND_GIVE_PASS_URL = "336";
  public static final String OFL_FUND_GIVE_PASS_URL = "337";

  /**
   * 退款状态:待处理
   */
  public static final String APPLY_STATE_00 = "00";
  /**
   * 退款状态:复核不通过
   */
  public static final String APPLY_STATE_02 = "02";
  /**
   * 退款状态:已处理待复核
   */
  public static final String APPLY_STATE_10 = "10";
  /**
   * 退款状态:已复核
   */
  public static final String APPLY_STATE_20 = "20";
  /**
   * 退款状态:废弃
   */
  public static final String APPLY_STATE_90 = "90";
  /**
   * 退款的购汇交易代码
   */
  public static final String REFUND_PUR_EXCHANGE_TYPE = "3803";
  
  
  /**
   * 线下预存操作类型 01:已确认线下预存02:手工确认线下预存
   */
  public static final String PRESTORE_TYPE_01 = "01";
  /**
   * 线下预存操作类型 01:已确认线下预存02:手工确认线下预存
   */
  public static final String PRESTORE_TYPE_02 = "02";
  

  /**
   * B2B退款成功通知的URL
   */
  public static final String REFUND_B2B_SUCC_NOTICE_URL = "335";
  public static final String REFUND_B2C_SUCC_NOTICE_URL = "339";
  public static final String REFUND_B2C_REM_EXCHANGE_NOTICE_URL = "338";
  
  /**
   *接口调用开关 0:关闭 (生产环境)1:打开(开发环境，测试环境) 生产环境并行期暂时关闭接口调用
   */
  public static String INTERFACE_SWITCH;

public void setINTERFACE_SWITCH(String iNTERFACE_SWITCH) {
	INTERFACE_SWITCH = iNTERFACE_SWITCH;
}

// 补单的清分标示
public static final String REPLENISHMENTS_FLAG = "Replenishments";
// 交易状态定义
public static final String TRANSACTION_SUCCESS_FLAG = "S";
public static final String TRANSACTION_NEW_FLAG = "N";

}
