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
    //~ Static fields/initializers =============================================

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
    public final static String DIC_TYPE_MENU = "21";
    /**
     * 菜单编码类型
     */
    public final static String DIC_ORG_TYPE = "22";
    
    public final static String DIC_ORG_STATUS = "23";
    
    public final static String DIC_ORG_APPROVALED_STATUS = "24";
    
    /**
     * 用户类型
     */
    public final static String DIC_OPERATOR_TYPE = "25";
    /**
     * 操作员状态
     */
    public final static String DIC_OPERATOR_STATUS = "26";
    /**
     * 法人证件类型
     */
    public final static String DIC_LAWMAN_PASSTYPE = "27";
    
    /**
     * 用户状态
     */
    public final static String DIC_USER_STATUS = "28";
    
    
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

    /**
     * 商户密码
     */
    public static final String MERCHANT_PASSWORD = "888888";
    
    public static final String UC_ROLE_STATIC_OPEN = "O";
    
    public static final String UC_ROLE_STATIC_CLOSE = "C";
    
    /**操作员状态
     * 可用
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
    
    public static final String SUPERADMIN = "bc@qq.com";

}
