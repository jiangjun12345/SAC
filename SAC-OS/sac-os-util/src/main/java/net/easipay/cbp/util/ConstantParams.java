package net.easipay.cbp.util;

/**
 * Constant values used throughout the application.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ConstantParams {

  public ConstantParams() {
    // hide me
  }

  // ~ Static fields/initializers
  // =============================================

  /**
   * The name of the ResourceBundle used in this application
   */
  public static final String RES_VALID_FLAG = "N";

  /**
   * 字符分割符号 分页标签中用到
   */
  public final static String SPLIT_SIGN = "^";

  /**
   * 每日信息模块每页的最大页面数
   */
  public final static int ADMIN_PAGE_SIZE = 10;
  /**
   * 系统编码类型
   */
  public final static String DIC_TYPE_SYS = "01";
  /**
   * 菜单编码类型
   */
  public final static String DIC_TYPE_MENU = "02";
  /**
   * 菜单编码类型
   */
  public final static String DIC_ORG_TYPE = "03";

  public final static String DIC_ORG_STATUS = "04";

  public final static String DIC_ORG_APPROVALED_STATUS = "05";

  /**
   * 用户类型
   */
  public final static String DIC_OPERATOR_TYPE = "06";
  /**
   * 操作员状态
   */
  public final static String DIC_OPERATOR_STATUS = "07";

  /*
   * 获取银行信息
   */
  public final static String DIC_SAC_BANK_NAME = "10";

  public final static String DIC_CURRENCY_TYPE = "11";

  public final static String DIC_SAC_PERIOD = "12";

  public final static String DIC_COST_COM_WAY = "13";
  public final static String DIC_COST_SAC_WAY = "14";

  public static final String DIC_BUSINESS_TYPE = "15";

  public static final String DIC_TRX_TYPE = "16";

  public static final String DIC_REFUND_FLAG = "17";

  public static final String DIC_FEE_COM_WAY = "18";

  public static final String DIC_FEE_SAC_WAY = "19";

  public static final String DIC_CUS_SAC_PERIOD = "20";

  public static final String MESSAGE_MAGIC_TYPE = "6100";

  public static final String FILE_PATH = "0006";

  public static final String RTN_CODE_SUCCESSS = "000000";

  public static final String RTN_CODE_PARAM_IS_NULL = "000001";

  public static final String RTN_CODE_PARAM_RESOLVE_ERROR = "000002";

  public static final String RTN_CODE_ERROR = "000003";

  /**
   * 编码格式
   */
  public static final String CODE_FORMAT_UTF8 = "UTF-8";

  public static final String CODE_FORMAT_GBK = "GBK";

  /**
   * 密码默认值
   */
  public static final String DEFAULT_PASSWORD = "123456";

  public static final String UC_ROLE_STATIC_OPEN = "O";

  public static final String UC_ROLE_STATIC_CLOSE = "C";

  /**
   * 操作员状态 可用
   */
  public static final String UC_OPERATOR_STATUS_ENABLED = "Y";
  /**
   * 禁用
   */
  public static final String UC_OPERATOR_STATUS_DISENABLED = "F";
  /**
   * 删除
   */
  public static final String UC_OPERATOR_STATUS_DELETE = "D";

  /**
   * TC 字典表类型
   */
  public final static String DIC_TYPE_TC_TRX_STATUS = "01";
  public final static String DIC_TYPE_TC_REFUND_TRX_STATUS = "04";
  /**
   * 订单状态：退款中
   */
  public static final String ORDBILL_STATE_REF = "R";

  /**
   * 订单状态：未支付
   */
  public static final String ORDBILL_STATE_INIF = "N";

  /**
   * 订单状态：支付完成
   */
  public static final String ORDBILL_STATE_SUCC = "S";

  /**
   * 订单状态：退款完成
   */
  public static final String ORDBILL_STATE_REFSUCC = "RS";

  /**
   * 订单状态：退款失败
   */
  public static final String ORDBILL_STATE_FAIL = "F";

  /**
   * 订单状态：订单关闭
   */
  public static final String ORDBILL_STATE_CLOSE = "C";

  /**
   * 用户状态
   */
  public final static String DIC_USER_STATUS = "09";

  /**
   * 订单状态
   */
  public final static String DIC_ORDER_STATUS = "01";

  /**
   * OS请求收单系统关闭订单
   */
  public static final String ORD_BILL_CLOSE = "orderCloseRequest";

  /**
   * 系统字典编码
   */
  public static final String SAC_CCY = "11";
  public static final String SAC_BUSS_TYPE = "09";
  public static final String SAC_TRX_TYPE = "20";
  public static final String SAC_PAY_WAY = "14";
  public static final String SAC_BANK = "10";
  public static final String SAC_CHN_PARAM = "12";

  public static final String SAC_B2B_REFUND_SUCC_STATE = "313";

  public static final String SAC_REFUND_STATE = "40";
  public static final String SAC_REFUND_BATCH_STATE = "41";
  public static final String SAC_B2C_REFUND_STATE = "42";
  public static final String SAC_B2C_PUR_STATE = "43";
  public static final String SAC_B2C_REM_STATE = "44";

}
