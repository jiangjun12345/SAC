package net.easipay.cbp.model;


import java.util.Date;

import net.easipay.dsfc.ws.fws.FwsAnno;

/**
 * 功能：接收渠道对账文件对象
 * @author Administrator
 */   
public class SacReceiveBankRecon implements java.io.Serializable{
	//外部流水号
	@FwsAnno(0)
	private String bankSerialNo; 
	
    //支付渠道类型1 B2B支付 2 B2C支付
	@FwsAnno(1)
	private String payconType;
	
	//币种
	@FwsAnno(2)
	private String currencyType;
	
	//支付金额
	@FwsAnno(3)
	private String payAmount;
	
	//交易时间
	@FwsAnno(4)
	private Date trxTime;

	//操作员 b2c:000001 
	@FwsAnno(5)
	private String recOper;
	
	//渠道号即银行节点代码
	@FwsAnno(6)
	private String chnNo;
	
	//正逆向交易标示:1,正向交易2.逆向交易
	@FwsAnno(7)
	private String busiType;
	
	//对账开始日期，格式如下： yyyyMMddHHmmss
	@FwsAnno(8)
	private Date recStartDate;
	
	//对账结束日期，格式如下： yyyyMMddHHmmss
	@FwsAnno(9)
	private Date recEndDate;
	
	//交易数量
	@FwsAnno(10)
	private Long  recCount;
	
	
	//交易流水号
	@FwsAnno(11)
	private String trxSerialNo;
	
	//交易代码
	@FwsAnno(12)
	private String trxCode;
	
	//私有域，不能包含|
	@FwsAnno(13)
	private String privDomain;
	
	
	public String getBankSerialNo() {
		return bankSerialNo;
	}

	public void setBankSerialNo(String bankSerialNo) {
		this.bankSerialNo = bankSerialNo;
	}

	public String getPayconType() {
		return payconType;
	}

	public void setPayconType(String payconType) {
		this.payconType = payconType;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	

	public String getRecOper() {
		return recOper;
	}

	public void setRecOper(String recOper) {
		this.recOper = recOper;
	}

	public String getChnNo() {
		return chnNo;
	}

	public void setChnNo(String chnNo) {
		this.chnNo = chnNo;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}



	public Long getRecCount() {
		return recCount;
	}

	public void setRecCount(Long recCount) {
		this.recCount = recCount;
	}

	public Date getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}

	public Date getRecStartDate() {
		return recStartDate;
	}

	public void setRecStartDate(Date recStartDate) {
		this.recStartDate = recStartDate;
	}

	public Date getRecEndDate() {
		return recEndDate;
	}

	public void setRecEndDate(Date recEndDate) {
		this.recEndDate = recEndDate;
	}

	public String getTrxSerialNo() {
		return trxSerialNo;
	}

	public void setTrxSerialNo(String trxSerialNo) {
		this.trxSerialNo = trxSerialNo;
	}

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public String getPrivDomain() {
		return privDomain;
	}

	public void setPrivDomain(String privDomain) {
		this.privDomain = privDomain;
	}
	
	
	

}
