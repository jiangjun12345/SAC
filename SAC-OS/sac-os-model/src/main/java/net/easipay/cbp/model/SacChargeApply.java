
package net.easipay.cbp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 预存申请表
* ClassName: SacChargeApply <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午4:47:16 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
@Entity
@Table(name = "SAC_CHARGE_APPLY")
public class SacChargeApply implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6683380528568740694L;

	private Long chargeApplyId;
    
    private Long applyOrgId;
    
    private String applyDbtCode;
    
    private String applyOrgName;
    
    private String applyCode;
    
    private String payCurrency;
    
    private java.math.BigDecimal payAmount;
    
    private java.util.Date applyDate;
    
    private java.util.Date expiredDate;
    
    private String craccNo;
    
    private String craccName;
    
    private String craccNodeCode;
    
    private String craccBankName;
    
    private String applyState;
    
    private String checkedSerialNo;
    
    private Date createTime;
    
    private Date lastUpdateTime;
    
    private String applyMemo;
    
    private	String draccNo;
    
    private String draccName;
    
    public Long getChargeApplyId() {
        return chargeApplyId;
    }
    
    public void setChargeApplyId(Long chargeApplyId) {
        this.chargeApplyId = chargeApplyId;
    }
    
    public Long getApplyOrgId() {
        return applyOrgId;
    }
    
    public void setApplyOrgId(Long applyOrgId) {
        this.applyOrgId = applyOrgId;
    }
    
    public String getApplyDbtCode() {
        return applyDbtCode;
    }
    
    public void setApplyDbtCode(String applyDbtCode) {
        this.applyDbtCode = applyDbtCode;
    }
    
    public String getApplyOrgName() {
        return applyOrgName;
    }
    
    public void setApplyOrgName(String applyOrgName) {
        this.applyOrgName = applyOrgName;
    }
    
    public String getApplyCode() {
        return applyCode;
    }
    
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }
    
    public String getPayCurrency() {
        return payCurrency;
    }
    
    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }
    
    public java.math.BigDecimal getPayAmount() {
        return payAmount;
    }
    
    public void setPayAmount(java.math.BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    
    public java.util.Date getApplyDate() {
        return applyDate;
    }
    
    public void setApplyDate(java.util.Date applyDate) {
        this.applyDate = applyDate;
    }
    
    public java.util.Date getExpiredDate() {
        return expiredDate;
    }
    
    public void setExpiredDate(java.util.Date expiredDate) {
        this.expiredDate = expiredDate;
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
    
    public String getApplyState() {
        return applyState;
    }
    
    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }
    
    public String getCheckedSerialNo() {
        return checkedSerialNo;
    }
    
    public void setCheckedSerialNo(String checkedSerialNo) {
        this.checkedSerialNo = checkedSerialNo;
    }
    
    public String getApplyMemo() {
        return applyMemo;
    }
    
    public void setApplyMemo(String applyMemo) {
        this.applyMemo = applyMemo;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
    
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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

	@Override
    public String toString() {
        return "B2bChargeApplyDomain [" + "chargeApplyId="
                + chargeApplyId
                + "applyOrgId="
                + applyOrgId
                + "applyDbtCode="
                + applyDbtCode
                + "applyOrgName="
                + applyOrgName
                + "applyCode="
                + applyCode
                + "payCurrency="
                + payCurrency
                + "payAmount="
                + payAmount
                + "applyDate="
                + applyDate
                + "expiredDate="
                + expiredDate
                + "craccNo="
                + craccNo
                + "craccName="
                + craccName
                + "craccNodeCode="
                + craccNodeCode
                + "craccBankName="
                + craccBankName
                + "applyState="
                + applyState
                + "checkedSerialNo="
                + checkedSerialNo
                + "createTime="
                + createTime
                + "lastUpdateTime="
                + lastUpdateTime
                + "applyMemo="
                + applyMemo
                + "]";
    }
    
}
