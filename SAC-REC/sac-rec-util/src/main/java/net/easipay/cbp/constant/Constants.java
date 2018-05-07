package net.easipay.cbp.constant;

/**
 * Constant values used throughout the application.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {

  private Constants() {
  }

  /**
   * The name of the configuration hashmap stored in application scope.
   */
  public static final String CONFIG = "appConfig";

  /**
   * The name of the ResourceBundle used in this application
   */
  public static final String BUNDLE_KEY = "ApplicationResources";

  // 对账类型定义
  public static final String REC_TYPE_INNER_TRX = "1";
  public static final String REC_TYPE_CHANNEL = "2";
  public static final String REC_TYPE_CHANNEL_DIFF = "3";
  // 对账状态定义
  public static final String REC_STATUS_NEW = "0";
  public static final String REC_STATUS_SUCC = "1";
  public static final String REC_STATUS_MIDDLE = "2";
  // 交易状态定义
  public static final String TRANSACTION_SUCCESS_FLAG = "S";
  public static final String TRANSACTION_NEW_FLAG = "N";
  // 交易记账状态定义
  public static final String TRANSACTION_ACCOUNT_SUCC = "S";
  public static final String TRANSACTION_ACCOUNT_NEW = "N";

  // 交易记账状态定义
  public static final String FIN_TASK_TRX_SEND_SUCC = "S";
  public static final String FIN_TASK_TRX_SEND_NEW = "N";
  // 对账差错类型定义

  public static final String INNER_DIFF_INCONSISTENT = "52";
  public static final String INNER_DIFF_NOT_EXISTED = "51";
  public static final String INNER_DIFF_NOT_EXISTED_RECBATCH = "510";

  public static final String REC_DIFF_TYPE_LONG = "50";
  public static final String REC_DIFF_TYPE_SHORT = "49";
  public static final String REC_DIFF_NOT_EXISTED = "53";
  public static final String REC_DIFF_TYPE_NO_INNER = "200";

  // 对账差错处理类型定义
  public static final String REC_DIFF_DEALTYPE_SUCC = "S";
  public static final String REC_DIFF_DEALTYPE_NEW = "N";

  // 对账差错长款自动增补交易处理状态定义
  public static final String REC_DIFF_SUPPLEMENT_SUCC = "S";
  public static final String REC_DIFF_SUPPLEMENT_NEW = "N";

  // 支付方式定义
  public static final String PAYWAY_QUICKLY = "1";
  public static final String PAYWAY_EBANK = "2";
  public static final String PAYWAY_UNION = "3";

  // 对账差错处理方式定义
  public static final String REC_DIFF_DEALSTYLE_MAN = "1";
  public static final String REC_DIFF_DEALSTYLE_SYS = "2";
  // 对账差错处理人员,系统处理员定义
  public static final String REC_DIFF_DEALOPER_SYS = "SYS";

  // 查找短款需要限制的交易类型定义
  public static final String SHORT_TERM_TRX_TYPE1 = "3302";
  public static final String SHORT_TERM_TRX_TYPE2 = "3304";
  public static final String SHORT_TERM_TRX_TYPE3 = "1312";
  public static final String SHORT_TERM_TRX_TYPE4 = "1315";
  public static final String SHORT_TERM_TRX_TYPE5 = "1631";
  public static final String SHORT_TERM_TRX_TYPE6 = "3303";
  // 查找渠道差错需要限制的交易类型定义
  public static final String CHANNEL_DIFF_TRX_TYPE1 = "1601";
  public static final String CHANNEL_DIFF_TRX_TYPE2 = "1305";
  public static final String CHANNEL_DIFF_TRX_TYPE3 = "1302";
  public static final String CHANNEL_DIFF_TRX_TYPE4 = "9338";
  public static final String CHANNEL_DIFF_TRX_TYPE5 = "9308";

  // 对账完成需要记账的交易代码定义
  public static final String REC_FINISH_TRXCODE_PAYMENT = "3302";
  public static final String REC_FINISH_TRXCODE_REFUND = "3303";
  public static final String REC_FINISH_TRXCODE_SHIPPING3 = "1312";
  public static final String REC_FINISH_TRXCODE_SHIPPING2 = "1315";
  public static final String REC_FINISH_TRXCODE_SHIPPING1 = "1631";

  // 对账查找短款需要限制的交易字典表定义
  public final static String SHORT_TERM_TRX_TYPE = "31";
  // 对账查找渠道差错需要限制的交易字典表定义
  public final static String CHANNEL_DIFF_TRX_TYPE = "32";
  // 对账完成需要记账的交易字典表定义
  public final static String REC_FINISH_TRXCODE_TYPE = "33";

  //内部对账短款 需要排除的的交易字典表定义
  public final static String INNER_REC_EXCLUDE_TRXCODE_TYPE = "600";

  // 内部对账，渠道对账标识，对账状态定义
  public static final String REC_FLAG_NEW = "N";
  public static final String REC_STATUS_NEW_FLAG = "N";
  public static final String REC_FLAG_SUCC = "S";
  public static final String REC_STATUS_SUCC_FLAG = "S";

  public static final Integer MAX_RECORDS_ONEPAGE = 1000;
  public static final String REC_SUCC_FINTASK_STATUS = "D";
  public static final String REC_SUCC_BLEND_FINTASK_STATUS = "G";
  // 交易冲正状态定义
  public static final String TRANSACTION_REVERSAL_SUCC = "S";
  public static final String TRANSACTION_REVERSAL_NEW = "N";

  // 接口服务ID定义
  public static final String UPDATE_SACTRANSATION_DETAIL = "SAC-AC-0002";
  public static final String JOIN_RULEFIN_TASK = "SAC-AC-0010";

  public static final String FINTASK_UNDO_TASK = "SAC-FA-0005";

  // 补单的清分标示
  public static final String REPLENISHMENTS_FLAG = "Replenishments";

  // 需要特殊处理的内部对账交易3411
  public static final String INNER_REC_TRX_SPECIAL = "3411";

  // 支付渠道类型定义
  public static final String PAY_CONTYPE_B2B = "1";
  public static final String PAY_CONTYPE_B2C = "2";

  //3411&3893
  public static final String FIN_TASK_TRX_TYPE_3803 = "3803";
  public static final String FIN_TASK_TRX_TYPE_3411 = "3411";

  //交易类型 3302
  public static final String TRX_TYPE_3302 = "3302";
  //交易类型 3423
  public static final String TRX_TYPE_3423 = "3423";
  /**
   * 调账:记账
   */
  public static final String RECONLICIATION_ACCOUNT = "SAC-FA-0002";
  /**
   * 更新付汇批次号
   */
  public static final String UPDATE_REMITTANCE = "SAC-AC-0021";

  /**
   * 会计记账digest模板
   */
  public static final String TEMPLATE_DIGEST = "%s,{%s}付给{%s}{%s}{%s}";
  /**
   * 会计记账digest模板
   */
  public static final String TEMPLATE_PARAMS = "%s,%s,%s,%s|%s,%s,%s,%s";
}
