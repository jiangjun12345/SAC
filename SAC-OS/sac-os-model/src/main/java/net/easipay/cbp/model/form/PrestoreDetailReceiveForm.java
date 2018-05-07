package net.easipay.cbp.model.form;


/**
 *线下预存交易表(接口)
* ClassName: SacDepositDetail <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午4:42:28 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */

public class PrestoreDetailReceiveForm implements java.io.Serializable {
    
	private static final long serialVersionUID = -7583006390717906076L;
	public String serialNumber;//平台流水号
	public String tradeDate;//交易时间
	public String amount;//金额
	public String channelFee;//渠道成本
	public String tradeResult;//交易结果  0 成功 1 失败
	public String remark;//备注
	public String tradeType; //交易类型  01代收 02代付03其它
	public String oSerialNumber; //银行流水号
	public String frBankAcctNo; //付款方账户
	public String frBankAcctName; //付款方名称
	public String frLineNumber; //付款方银行联行号
	public String frBankName; //付款方银行名称
	public String toBankAcctNo; //收款方账户 
	public String toBankAcctName; //收款方名称
	public String toLineNumber; //收款方银行联行号
	public String toBankName; //收款方银行名称
	public String direction; //来往账标识
	public String acctBal; //交易后可用余额
	public String busType;
	public String furinfo;//摘要字段

	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getChannelFee() {
		return channelFee;
	}
	public void setChannelFee(String channelFee) {
		this.channelFee = channelFee;
	}
	public String getTradeResult() {
		return tradeResult;
	}
	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getoSerialNumber() {
		return oSerialNumber;
	}
	public void setoSerialNumber(String oSerialNumber) {
		this.oSerialNumber = oSerialNumber;
	}
	public String getFrBankAcctNo() {
		return frBankAcctNo;
	}
	public void setFrBankAcctNo(String frBankAcctNo) {
		this.frBankAcctNo = frBankAcctNo;
	}
	public String getFrBankAcctName() {
		return frBankAcctName;
	}
	public void setFrBankAcctName(String frBankAcctName) {
		this.frBankAcctName = frBankAcctName;
	}
	public String getFrLineNumber() {
		return frLineNumber;
	}
	public void setFrLineNumber(String frLineNumber) {
		this.frLineNumber = frLineNumber;
	}
	public String getFrBankName() {
		return frBankName;
	}
	public void setFrBankName(String frBankName) {
		this.frBankName = frBankName;
	}
	public String getToBankAcctNo() {
		return toBankAcctNo;
	}
	public void setToBankAcctNo(String toBankAcctNo) {
		this.toBankAcctNo = toBankAcctNo;
	}
	public String getToBankAcctName() {
		return toBankAcctName;
	}
	public void setToBankAcctName(String toBankAcctName) {
		this.toBankAcctName = toBankAcctName;
	}
	public String getToLineNumber() {
		return toLineNumber;
	}
	public void setToLineNumber(String toLineNumber) {
		this.toLineNumber = toLineNumber;
	}
	public String getToBankName() {
		return toBankName;
	}
	public void setToBankName(String toBankName) {
		this.toBankName = toBankName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAcctBal() {
		return acctBal;
	}
	public void setAcctBal(String acctBal) {
		this.acctBal = acctBal;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getFurinfo() {
		return furinfo;
	}
	public void setFurinfo(String furinfo) {
		this.furinfo = furinfo;
	}
	
	
}
