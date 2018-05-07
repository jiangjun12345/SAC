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
	/**
	 * TC 字典表类型
	 */
	public final static String DIC_TYPE_TC_TRX_STATUS = "01";
	// 交易类型定义
	public static final String PAYMENT_TRANSACTION = "3304";
	public static final String REFUND_TRANSACTION = "3305";
	public static final String RURCHASE_EXCHANGE_TRANSACTION = "3516";
	public static final String RAYMENT_EXCHANGE_TRANSACTION = "3520";
	
	//一级科目组合
	public static final String CODE_BANKSAVINGS_BANK = "100200"; //银行存款
	public static final String CODE_RECEIVABLE_BANK = "112212"; //应收款-银行
	public static final String CODE_OTHERRECEIVABLE_FEE = "122101"; //其他应收款-手续费
	public static final String CODE_OTHERRECEIVABLE_BANKSHORT = "122102"; //其他应收款-银行短款
	public static final String CODE_PAYABLE_DEPOSIT = "220203"; //应付账款-备付金
	public static final String CODE_OTHERPAYABLE_BANKFEE = "224104"; //其他应付款-银行手续费
	public static final String CODE_OTHERPAYABLE_BANKLONG = "224105"; //其他应付款-银行长款
	public static final String CODE_MAINCOST_COST = "640106"; //主营业务成本-成本
	public static final String CODE_FINCOST_DRAWFEE = "660307"; //财务费用-划款费用
	public static final String CODE_FINCOST_EXCHANGEFEE = "660308"; //财务费用-汇兑手续费
	public static final String CODE_FINCOST_INTERESTINCOME = "660309"; //财务费用-利息收入
	public static final String CODE_MAININCOME_FEEINCOME = "600110"; //主营业务收入-手续费收入
	public static final String CODE_MAININCOME_YEARINCOM = "600111"; //主营业务收入-年费收入
	
	//客户账户类型
	public static final String ACCOUNT_DEFAULT = "00"; //默认
	public static final String ACCOUNT_NORMAL = "01"; //一般计收资金账户
	public static final String ACCOUNT_FLOW = "02"; //流动资金账户
	public static final String ACCOUNT_CUSTOMS = "03"; //海关监管账户
	public static final String ACCOUNT_EXCHANGE = "04"; //即期结售汇
	public static final String ACCOUNT_ORIENT = "05"; //定向专用账户
	public static final String ACCOUNT_GUARANTEE = "06"; //保证金账户
	public static final String ACCOUNT_REFUND = "07"; //退款专用账户
	public static final String ACCOUNT_PAY = "08"; //付款账户
	public static final String ACCOUNT_FREEZE = "09"; //冻结账户
	public static final String ACCOUNT_PAYMENT = "10"; //收付专用账户
	public static final String ACCOUNT_BALANCE = "11";  //结算账户
	
	public static final int DIRECTION_DEBIT = 1;
	public static final int DIRECTION_CREDIT = -1;
	
	public static final String BUSI_TYPE_DEFAULT = "00";
}
