/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class SacTransferCommandReceiveForm implements Serializable
{
    private static final long serialVersionUID = -1730784066120397137L;

    @NotBlank(message = "指令类型不能为空")
    @Pattern(regexp = "^10|20|30|40|50$", message = "指令类型必须为10 清算付款；20 备付金归集；30 计提风险准备金； 40 计提营业收入； 50 头寸调拨")
    private String cmdType;// 指令类型

    @NotBlank(message = "交易流水号不能为空")
    private String cmdSerialNo;// 交易流水号

    @NotBlank(message = "指令接收银行代码不能为空")
    private String msgReceiver;// 指令接收银行代码/付款方银行代码

    @NotBlank(message = "付款方代码不能为空")
    private String dbtCode;// 付款方代码，即企业的组织机构代码

    @NotBlank(message = "付款方账号不能为空")
    private String draccNo;// 付款方账号

    @NotBlank(message = "付款账户名称不能为空")
    private String draccName;// 付款账户名称

    private String draccBankCode;// 付款方银行名称

    private String draccBankBranch;// 付款账户开户行联行号

    @NotBlank(message = "支付币种不能为空")
    private String payCurrency;// 支付币种

    @NotNull(message = "支付总笔数不能为空")
    private Long payCount;// 支付总笔数

    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0.00", message = "支付金额非法")
    private BigDecimal payAmount = new BigDecimal(0L);// 支付金额

    @NotBlank(message = "支付优先级不能为空,默认为01")
    private String payPri;// 支付优先级

    private Date vldDate;// 生效日期,即指令预定发送日期

    @NotBlank(message = "收款方代码")
    private String crtCode;// 收款方代码，即企业的组织机构代码

    @NotBlank(message = "收款方账号不能为空")
    private String craccNo;// 收款方账号

    @NotBlank(message = "收款账户名称不能为空")
    private String craccName;// 收款账户名称

    private String craccBankCode;// 收款账户开户行联行号

    private String craccBankBranch;// 收款账户开户行名称

    private String batchSerialNo;// 批流水号

    @NotBlank(message = "原指令流水号不能为空")
    private String otrxSerialNo;//

    @NotNull(message = "企业id不能为空")
    private Long orgId;// 企业id
    
    private String flag="0";//0:线上出款 1：线下出款 上线出款 线下出款标志

    private String reqSpt1;

    private String reqSpt2;

    private String reqSpt3;

    private String reqMemo;// 请求报文备注

    public String getCmdType()
    {
	return cmdType;
    }

    public void setCmdType(String cmdType)
    {
	this.cmdType = cmdType;
    }

    public String getCmdSerialNo()
    {
	return cmdSerialNo;
    }

    public void setCmdSerialNo(String cmdSerialNo)
    {
	this.cmdSerialNo = cmdSerialNo;
    }

    public String getMsgReceiver()
    {
	return msgReceiver;
    }

    public void setMsgReceiver(String msgReceiver)
    {
	this.msgReceiver = msgReceiver;
    }

    public String getDbtCode()
    {
	return dbtCode;
    }

    public void setDbtCode(String dbtCode)
    {
	this.dbtCode = dbtCode;
    }

    public String getDraccNo()
    {
	return draccNo;
    }

    public void setDraccNo(String draccNo)
    {
	this.draccNo = draccNo;
    }

    public String getDraccName()
    {
	return draccName;
    }

    public void setDraccName(String draccName)
    {
	this.draccName = draccName;
    }

    public String getDraccBankCode()
    {
	return draccBankCode;
    }

    public void setDraccBankCode(String draccBankCode)
    {
	this.draccBankCode = draccBankCode;
    }

    public String getDraccBankBranch()
    {
	return draccBankBranch;
    }

    public void setDraccBankBranch(String draccBankBranch)
    {
	this.draccBankBranch = draccBankBranch;
    }

    public String getPayCurrency()
    {
	return payCurrency;
    }

    public void setPayCurrency(String payCurrency)
    {
	this.payCurrency = payCurrency;
    }

    public Long getPayCount()
    {
	return payCount;
    }

    public void setPayCount(Long payCount)
    {
	this.payCount = payCount;
    }

    public BigDecimal getPayAmount()
    {
	return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount)
    {
	this.payAmount = payAmount;
    }

    public String getPayPri()
    {
	return payPri;
    }

    public void setPayPri(String payPri)
    {
	this.payPri = payPri;
    }

    public Date getVldDate()
    {
	return vldDate;
    }

    public void setVldDate(Date vldDate)
    {
	this.vldDate = vldDate;
    }

    public String getCrtCode()
    {
	return crtCode;
    }

    public void setCrtCode(String crtCode)
    {
	this.crtCode = crtCode;
    }

    public String getCraccNo()
    {
	return craccNo;
    }

    public void setCraccNo(String craccNo)
    {
	this.craccNo = craccNo;
    }

    public String getCraccName()
    {
	return craccName;
    }

    public void setCraccName(String craccName)
    {
	this.craccName = craccName;
    }

    public String getCraccBankCode()
    {
	return craccBankCode;
    }

    public void setCraccBankCode(String craccBankCode)
    {
	this.craccBankCode = craccBankCode;
    }

    public String getCraccBankBranch()
    {
	return craccBankBranch;
    }

    public void setCraccBankBranch(String craccBankBranch)
    {
	this.craccBankBranch = craccBankBranch;
    }

    public String getBatchSerialNo()
    {
	return batchSerialNo;
    }

    public void setBatchSerialNo(String batchSerialNo)
    {
	this.batchSerialNo = batchSerialNo;
    }

    public String getOtrxSerialNo()
    {
	return otrxSerialNo;
    }

    public void setOtrxSerialNo(String otrxSerialNo)
    {
	this.otrxSerialNo = otrxSerialNo;
    }

    public Long getOrgId()
    {
	return orgId;
    }

    public void setOrgId(Long orgId)
    {
	this.orgId = orgId;
    }

    public String getReqSpt1()
    {
	return reqSpt1;
    }

    public void setReqSpt1(String reqSpt1)
    {
	this.reqSpt1 = reqSpt1;
    }

    public String getReqSpt2()
    {
	return reqSpt2;
    }

    public void setReqSpt2(String reqSpt2)
    {
	this.reqSpt2 = reqSpt2;
    }

    public String getReqSpt3()
    {
	return reqSpt3;
    }

    public void setReqSpt3(String reqSpt3)
    {
	this.reqSpt3 = reqSpt3;
    }

    public String getReqMemo()
    {
	return reqMemo;
    }

    public void setReqMemo(String reqMemo)
    {
	this.reqMemo = reqMemo;
    }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
    

}
