package net.easipay.cbp.form;

import java.math.BigDecimal;
import java.util.Date;


import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.form.SacTransationSendForm;
import net.easipay.cbp.util.StringUtil;


/**
 * 
* @Description: 封装账务系统交易数据form
* @author dell (作者英文名称) 
* @date 2015-7-21 下午03:10:48
* @version V1.0  
* @jdk v1.6
* @tomcat v7.0
 */
public class PayMessageForm {
	
	    private String cusBillNo;
		private String platBillNo;
		private String trxSerialNo;
		private String otrxSerialNo;
		private String etrxSerialNo;
		//private String craccCusCode;
		private String craccCardId;
		private String craccCusType;
		private String craccCusName;
		private String craccNo;
		private String craccName;
		private String craccNodeCode;
		private String craccBankName;
		//private String draccCusCode;
		private String draccCardId;
		private String draccCusType;
		private String draccCusName;
		private String draccNo;
		private String draccName;
		private String draccNodeCode;
		private String draccBankName;
		private String payCurrency;
		private BigDecimal payAmount;
		private String sacCurrency;
		private BigDecimal sacAmount;
		private String bussType;
		private String trxType;
		private String trxState;
		private String payconType;
		private String payWay;
		private String trxBatchNo;
		private BigDecimal exRate;
		private String issuingBank;
		private String trxErrDealType;
		private BigDecimal taxAmount;
		private BigDecimal transportExpenses;
		private Date trxTime;
		private BigDecimal channelCost;
		private BigDecimal cusCharge;
		private Date trxSuccTime;
		private String memo;
		private String remittanceBatchId;
		private String draccAreaCode;
		private String craccAreaCode;
		
		
		public PayMessageForm(SacOtrxInfo sacOtrxInfo){
			this.cusBillNo = StringUtil.trimStr(sacOtrxInfo.getCusBillNo());
			this.platBillNo = StringUtil.trimStr(sacOtrxInfo.getPlatBillNo());
			this.trxSerialNo = sacOtrxInfo.getTrxSerialNo();
			this.otrxSerialNo = StringUtil.trimStr(sacOtrxInfo.getOtrxSerialNo());
			this.etrxSerialNo = StringUtil.trimStr(sacOtrxInfo.getEtrxSerialNo());
			//this.craccCusCode = sacOtrxInfo.getCraccCusCode();
			this.craccCardId = sacOtrxInfo.getCraccCardId();
			this.craccCusName = sacOtrxInfo.getCraccCusName();
			this.craccCusType = sacOtrxInfo.getCraccCusType();
			this.craccNo = sacOtrxInfo.getCraccNo();
			this.craccName = sacOtrxInfo.getCraccName();
			this.craccNodeCode = sacOtrxInfo.getCraccNodeCode();
			this.craccBankName = sacOtrxInfo.getCraccBankName();
			//this.draccCusCode = sacOtrxInfo.getDraccCusCode();
			this.draccCardId = sacOtrxInfo.getDraccCardId();
			this.draccCusName = sacOtrxInfo.getDraccCusName();
			this.draccCusType = sacOtrxInfo.getDraccCusType();
			this.draccNo = sacOtrxInfo.getDraccNo();
			this.draccName = sacOtrxInfo.getDraccName();
			this.draccNodeCode = sacOtrxInfo.getDraccNodeCode();
			this.draccBankName = sacOtrxInfo.getDraccBankName();
			this.payCurrency = sacOtrxInfo.getPayCurrency();
			this.payAmount = sacOtrxInfo.getPayAmount();
			this.sacCurrency = sacOtrxInfo.getSacCurrency();
			this.sacAmount = sacOtrxInfo.getSacAmount();
			this.bussType = sacOtrxInfo.getBussType();
			this.trxType = sacOtrxInfo.getTrxType();
			this.payconType = sacOtrxInfo.getPayconType();
			this.payWay = sacOtrxInfo.getPayWay();
			this.trxBatchNo = StringUtil.trimStr(sacOtrxInfo.getTrxBatchNo());
			this.exRate = new BigDecimal("0");
			this.issuingBank = ""; 
			this.trxErrDealType = StringUtil.trimStr(sacOtrxInfo.getTrxErrDealType());
			this.memo = StringUtil.trimStr(sacOtrxInfo.getMemo());
			this.trxState = StringUtil.trimStr(sacOtrxInfo.getTrxState());
			this.trxTime = sacOtrxInfo.getTrxTime();
			this.channelCost = sacOtrxInfo.getChannelCost();
			this.cusCharge = sacOtrxInfo.getCusCharge();
			this.trxSuccTime = sacOtrxInfo.getTrxSuccTime();
			this.taxAmount = sacOtrxInfo.getTaxAmount();
			this.draccAreaCode = sacOtrxInfo.getDraccAreaCode();
			this.craccAreaCode = sacOtrxInfo.getCraccAreaCode();
			this.transportExpenses = sacOtrxInfo.getTransportExpenses();
			this.remittanceBatchId = String.valueOf( sacOtrxInfo.getRemittanceBatchId());
		}
		
		public PayMessageForm(SacTransationSendForm sendForm){
			this.cusBillNo = StringUtil.trimStr(sendForm.getCusBillNo());
			this.platBillNo = StringUtil.trimStr(sendForm.getPlatBillNo());
			this.trxSerialNo = sendForm.getTrxSerialNo();
			this.otrxSerialNo = StringUtil.trimStr(sendForm.getOtrxSerialNo());
			this.etrxSerialNo = StringUtil.trimStr(sendForm.getEtrxSerialNo());
			//this.craccCusCode = sendForm.getCraccCusCode();
			this.craccCusName = sendForm.getCraccCusName();
			this.craccCusType = sendForm.getCraccCusType();
			this.craccNo = sendForm.getCraccNo();
			this.craccName = sendForm.getCraccName();
			this.craccNodeCode = sendForm.getCraccNodeCode();
			this.craccBankName = sendForm.getCraccBankName();
			//this.draccCusCode = sendForm.getDraccCusCode();
			this.draccCusName = sendForm.getDraccCusName();
			this.draccCusType = sendForm.getDraccCusType();
			this.draccNo = sendForm.getDraccNo();
			this.draccName = sendForm.getDraccName();
			this.draccNodeCode = sendForm.getDraccNodeCode();
			this.draccBankName = sendForm.getDraccBankName();
			this.payCurrency = sendForm.getPayCurrency();
			this.payAmount = sendForm.getPayAmount();
			this.sacCurrency = sendForm.getSacCurrency();
			this.sacAmount = sendForm.getSacAmount();
			this.bussType = sendForm.getBussType();
			this.trxType = sendForm.getTrxType();
			this.payconType = sendForm.getPayconType();
			this.payWay = sendForm.getPayWay();
			this.trxBatchNo = StringUtil.trimStr(sendForm.getTrxBatchNo());
			this.exRate = new BigDecimal("0");
			this.issuingBank = ""; 
			this.trxErrDealType = StringUtil.trimStr(sendForm.getTrxErrDealType());
			this.memo = StringUtil.trimStr(sendForm.getMemo());
			this.trxState = StringUtil.trimStr(sendForm.getTrxState());
			this.trxTime = sendForm.getTrxTime();
			this.channelCost = sendForm.getChannelCost();
			this.cusCharge = sendForm.getCusCharge();
			this.trxSuccTime = sendForm.getTrxSuccTime();
			this.taxAmount = sendForm.getTaxAmount();
			this.transportExpenses = sendForm.getTransportExpenses();
		}
		public String getCusBillNo() {
			return cusBillNo;
		}
		public void setCusBillNo(String cusBillNo) {
			this.cusBillNo = cusBillNo;
		}
		public String getPlatBillNo() {
			return platBillNo;
		}
		public void setPlatBillNo(String platBillNo) {
			this.platBillNo = platBillNo;
		}
		public String getTrxSerialNo() {
			return trxSerialNo;
		}
		public void setTrxSerialNo(String trxSerialNo) {
			this.trxSerialNo = trxSerialNo;
		}
		public String getOtrxSerialNo() {
			return otrxSerialNo;
		}
		public void setOtrxSerialNo(String otrxSerialNo) {
			this.otrxSerialNo = otrxSerialNo;
		}
		public String getCraccCusType() {
			return craccCusType;
		}
		public void setCraccCusType(String craccCusType) {
			this.craccCusType = craccCusType;
		}
		public String getCraccNo() {
			return craccNo;
		}
		public void setCraccNo(String craccNo) {
			this.craccNo = craccNo;
		}
		public String getCraccName() {
			return craccName;
		}
		public void setCraccName(String craccName) {
			this.craccName = craccName;
		}
		public String getCraccNodeCode() {
			return craccNodeCode;
		}
		public void setCraccNodeCode(String craccNodeCode) {
			this.craccNodeCode = craccNodeCode;
		}
		public String getCraccBankName() {
			return craccBankName;
		}
		public void setCraccBankName(String craccBankName) {
			this.craccBankName = craccBankName;
		}
		public String getDraccCusType() {
			return draccCusType;
		}
		public void setDraccCusType(String draccCusType) {
			this.draccCusType = draccCusType;
		}
		public String getDraccNo() {
			return draccNo;
		}
		public void setDraccNo(String draccNo) {
			this.draccNo = draccNo;
		}
		public String getDraccName() {
			return draccName;
		}
		public void setDraccName(String draccName) {
			this.draccName = draccName;
		}
		public String getDraccNodeCode() {
			return draccNodeCode;
		}
		public void setDraccNodeCode(String draccNodeCode) {
			this.draccNodeCode = draccNodeCode;
		}
		public String getDraccBankName() {
			return draccBankName;
		}
		public void setDraccBankName(String draccBankName) {
			this.draccBankName = draccBankName;
		}
		public String getPayCurrency() {
			return payCurrency;
		}
		public void setPayCurrency(String payCurrency) {
			this.payCurrency = payCurrency;
		}
		public BigDecimal getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(BigDecimal payAmount) {
			this.payAmount = payAmount;
		}
		public String getBussType() {
			return bussType;
		}
		public void setBussType(String bussType) {
			this.bussType = bussType;
		}
		public String getTrxType() {
			return trxType;
		}
		public void setTrxType(String trxType) {
			this.trxType = trxType;
		}
		public String getPayconType() {
			return payconType;
		}
		public void setPayconType(String payconType) {
			this.payconType = payconType;
		}
		public String getPayWay() {
			return payWay;
		}
		public void setPayWay(String payWay) {
			this.payWay = payWay;
		}
		public String getTrxBatchNo() {
			return trxBatchNo;
		}
		public void setTrxBatchNo(String trxBatchNo) {
			this.trxBatchNo = trxBatchNo;
		}
		public String getIssuingBank() {
			return issuingBank;
		}
		public void setIssuingBank(String issuingBank) {
			this.issuingBank = issuingBank;
		}
		public String getTrxErrDealType() {
			return trxErrDealType;
		}
		public void setTrxErrDealType(String trxErrDealType) {
			this.trxErrDealType = trxErrDealType;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public String getSacCurrency() {
			return sacCurrency;
		}
		public void setSacCurrency(String sacCurrency) {
			this.sacCurrency = sacCurrency;
		}
		public BigDecimal getSacAmount() {
			return sacAmount;
		}
		public void setSacAmount(BigDecimal sacAmount) {
			this.sacAmount = sacAmount;
		}
		public BigDecimal getExRate() {
			return exRate;
		}
		public void setExRate(BigDecimal exRate) {
			this.exRate = exRate;
		}
		public String getEtrxSerialNo() {
			return etrxSerialNo;
		}
		public void setEtrxSerialNo(String etrxSerialNo) {
			this.etrxSerialNo = etrxSerialNo;
		}
		public String getCraccCusName() {
			return craccCusName;
		}
		public void setCraccCusName(String craccCusName) {
			this.craccCusName = craccCusName;
		}
		public String getDraccCusName() {
			return draccCusName;
		}
		public void setDraccCusName(String draccCusName) {
			this.draccCusName = draccCusName;
		}
		public String getTrxState() {
			return trxState;
		}
		public void setTrxState(String trxState) {
			this.trxState = trxState;
		}
		public Date getTrxTime() {
			return trxTime;
		}
		public void setTrxTime(Date trxTime) {
			this.trxTime = trxTime;
		}

		public BigDecimal getChannelCost() {
			return channelCost;
		}

		public void setChannelCost(BigDecimal channelCost) {
			this.channelCost = channelCost;
		}

		public BigDecimal getCusCharge() {
			return cusCharge;
		}

		public void setCusCharge(BigDecimal cusCharge) {
			this.cusCharge = cusCharge;
		}

		public Date getTrxSuccTime() {
			return trxSuccTime;
		}

		public void setTrxSuccTime(Date trxSuccTime) {
			this.trxSuccTime = trxSuccTime;
		}

		public String getCraccCardId() {
			return craccCardId;
		}

		public void setCraccCardId(String craccCardId) {
			this.craccCardId = craccCardId;
		}

		public String getDraccCardId() {
			return draccCardId;
		}

		public void setDraccCardId(String draccCardId) {
			this.draccCardId = draccCardId;
		}

		public BigDecimal getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(BigDecimal taxAmount) {
			this.taxAmount = taxAmount;
		}

		public BigDecimal getTransportExpenses() {
			return transportExpenses;
		}

		public void setTransportExpenses(BigDecimal transportExpenses) {
			this.transportExpenses = transportExpenses;
		}

		public String getRemittanceBatchId() {
			return remittanceBatchId;
		}

		public void setRemittanceBatchId(String remittanceBatchId) {
			this.remittanceBatchId = remittanceBatchId;
		}

		public String getDraccAreaCode() {
			return draccAreaCode;
		}

		public void setDraccAreaCode(String draccAreaCode) {
			this.draccAreaCode = draccAreaCode;
		}

		public String getCraccAreaCode() {
			return craccAreaCode;
		}

		public void setCraccAreaCode(String craccAreaCode) {
			this.craccAreaCode = craccAreaCode;
		}
		
		
}
