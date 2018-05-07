package net.easipay.cbp.constant;

public interface SacConstants
{

    /**
     * 交易成功
     */
    public static final String RTN_SUCCESS = "000000";
    /**
     * 交易失败，未指定具体返回码
     */
    public static final String RTN_CODE_FAIL = "999999";
    public static final String RTN_FAIL = "999990";
    public static final String RTN_WEIZHI = "888888";
    public static final String RTN_USER_CLOSE = "330280";
    public static final String RTN_CUST_CLOSE = "330281";
    public static final String RTN_STT_CLOSE = "330282";

    
    /**
     * 常用客户号
     */
    public interface CUSCODE
    {
	public static final String CUSTOMS = "1000000099999998888";  //海关
	public static final String CUSTOMS_NAME = "上海海关";  //海关
	
	public static final String CUSTOMS_EASIPAY = "1000000000674612978";  //东方支付客户号
	
	public static final String CUSTOMS_EASIPAY_GD = "1000000000674612999";  //东方支付过渡户客户号
	
	public static final String CUSTOMS_EASIPAY_NAME_GD = "东方电子支付过渡户";  //东方电子支付过渡户

	public static final String CUSTOMS_EASIPAY_NAME = "东方电子支付有限公司";  //东方支付客户名字
    }
    
    
    /**
     * 客户 类型 
     */
    public interface CUSTYPE
    {
	public static final String M = "1";  //企业
	public static final String P = "2";  //个人
    }
    
    
    /**
     * 对账批次表-对账类型 1.交易对账 2.渠道对账3渠道差错对账
     */
    public interface RECTYPE
    {
	public static final String TRX = "1";
	public static final String BANK = "2";
	public static final String DIF = "3";
    }
    
    /**
     * 对账批次表-对账状态0:未对账；1:已对账；2:对账中
     */
    public interface RECSTATUS
    {
	public static final String INIT = "0";
	public static final String DONE = "1";
	public static final String DOING = "2";
    }

    
    /**
     * 交易表 - 交易状态 N：代支付；S 交易成功;F : 交易失败；
     * 交易表 - 清分状态 N：代支付；Y 已清分
     */
    public interface STATE
    {
	public static final String NEW = "N";
	public static final String SUCC = "S";
	public static final String FAIL = "F";
    }
    
    
    
    /**
     * 交易代码规则表 - 收付款方规则 - ：1.是  2.否
     */
    public interface RULE
    {
	public static final String DEFAULT = "0";
	public static final String YES = "1";
	public static final String NO = "2";
    }
    
    
    
    /**
     * 记账指令规则表 - 参数模板类型  - 1：一借一贷2：一借多贷
     */
    public interface PARAMS_TEMP_TYPE
    {
	public static final String ONE_TO_ONE = "1";
	public static final String ONE_TO_MORE = "2";
    }
    
    
    /**
     * 渠道参数表-客户参数配置表 - 有效标志  - 1：有效 0：无效
     */
    public interface IS_VALID_FLAG
    {
	public static final String YES = "1";
	public static final String NO = "0";
    }
    
    
    /**
     * 渠道参数表 - 渠道类型  - 1 B2B支付 2 B2C支付3 存款银行
     */
    public interface CHN_TYPE
    {
	public static final String B2B = "1";
	public static final String B2C = "2";
	public static final String DEPOSIT_BANK = "3";
	public static final String DSF = "4";
	public static final String WHT = "5";
    }
    
    /**
     * 记账规则表-ProcessType
     */
    public interface INS_RULE_PRO_TYPE
    {
	public static final String REAL_TIME = "1";
	public static final String JMS = "2";
	public static final String TABLE = "3";
    }
    
}