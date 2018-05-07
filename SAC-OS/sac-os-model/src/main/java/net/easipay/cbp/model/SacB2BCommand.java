package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 划款指令表
* ClassName: SacB2BCommand <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午4:53:35 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
@Entity
@Table(name = "SAC_B2B_COMMAND")
public class SacB2BCommand implements java.io.Serializable {
    
    private static final long serialVersionUID = -7620356147779428998L;
    private Long cmdId;// 指令ID
    
    private String cmdType;// 指令类型
    
    private String cmdSerialNo;// 交易流水号
    
    private Date reqTime;// 指令发出时间，对应交易时间。内部对账的基准时间。
    
    private String cmdState;// 指令状态：N 新建待发送;QS 请求发送成功;S 交易成功;F 交易失败;B 挂起，备付金余额不足
    
    private Date createTime;// 记录创建时间
    
    private String rtrxSerialNo;// 应答方处理流水号
    
    private String rtnCode;// 返回码
    
    private String ertnCode;// 扩展返回码
    
    private String ertnInfo;// 扩展返回信息
    
    private Date rdoTime;// 应答方处理时间
    
    private String msgReceiver;// 指令接收银行代码/付款方银行代码
    
    private String dbtCode;// 付款方代码，即企业的组织机构代码
    
    private String draccNo;// 付款方账号
    
    private String draccName;// 付款账户名称
    
    private String draccBankCode;// 付款方银行名称
    
    private String draccBankBranch;// 付款账户开户行联行号
    
    private String payCurrency;// 支付币种
    
    private Long payCount;// 支付总笔数
    
    private BigDecimal payAmount;// 支付金额
    
    private String payPri;// 支付优先级
    
    private Date vldDate;// 生效日期,即指令预定发送日期
    
    private String crtCode;// 收款方代码，即企业的组织机构代码
    
    private String craccNo;// 收款方账号
    
    private String craccName;// 收款账户名称
    
    private String craccBankCode;// 收款账户开户行联行号
    
    private String craccBankBranch;// 收款账户开户行名称
    
    private String reqSpt1;
    
    private String reqSpt2;
    
    private String reqSpt3;
    
    private String reqMemo;// 请求报文备注
    
    private String batchSerialNo;// 批流水号
    
    private String otrxSerialNo;//
    
    private Long orgId;// 企业id
    
    private String bankName;// 银行
    
    public String getBatchSerialNo() {
        return batchSerialNo;
    }
    
    public void setBatchSerialNo(String batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }
    
    public String getOtrxSerialNo() {
        return otrxSerialNo;
    }
    
    public void setOtrxSerialNo(String otrxSerialNo) {
        this.otrxSerialNo = otrxSerialNo;
    }
    
    public Long getCmdId() {
        return cmdId;
    }
    
    public void setCmdId(Long cmdId) {
        this.cmdId = cmdId;
    }
    
    public String getCmdSerialNo() {
        return cmdSerialNo;
    }
    
    public void setCmdSerialNo(String cmdSerialNo) {
        this.cmdSerialNo = cmdSerialNo;
    }
    
    public String getCmdType() {
        return cmdType;
    }
    
    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }
    
    public Date getReqTime() {
        return reqTime;
    }
    
    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }
    
    public String getCmdState() {
        return cmdState;
    }
    
    public void setCmdState(String cmdState) {
        this.cmdState = cmdState;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getRtrxSerialNo() {
        return rtrxSerialNo;
    }
    
    public void setRtrxSerialNo(String rtrxSerialNo) {
        this.rtrxSerialNo = rtrxSerialNo;
    }
    
    public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getErtnCode() {
        return ertnCode;
    }
    
    public void setErtnCode(String ertnCode) {
        this.ertnCode = ertnCode;
    }
    
    public String getErtnInfo() {
        return ertnInfo;
    }
    
    public void setErtnInfo(String ertnInfo) {
        this.ertnInfo = ertnInfo;
    }
    
    public Date getRdoTime() {
        return rdoTime;
    }
    
    public void setRdoTime(Date rdoTime) {
        this.rdoTime = rdoTime;
    }
    
    public String getMsgReceiver() {
        return msgReceiver;
    }
    
    public void setMsgReceiver(String msgReceiver) {
        this.msgReceiver = msgReceiver;
    }
    
    public String getDbtCode() {
        return dbtCode;
    }
    
    public void setDbtCode(String dbtCode) {
        this.dbtCode = dbtCode;
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
    
    public String getDraccBankCode() {
        return draccBankCode;
    }
    
    public void setDraccBankCode(String draccBankCode) {
        this.draccBankCode = draccBankCode;
    }
    
    public String getDraccBankBranch() {
        return draccBankBranch;
    }
    
    public void setDraccBankBranch(String draccBankBranch) {
        this.draccBankBranch = draccBankBranch;
    }
    
    public String getPayCurrency() {
        return payCurrency;
    }
    
    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }
    
    public Long getPayCount() {
        return payCount;
    }
    
    public void setPayCount(Long payCount) {
        this.payCount = payCount;
    }
    
    public BigDecimal getPayAmount() {
        return payAmount;
    }
    
    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    
    public String getPayPri() {
        return payPri;
    }
    
    public void setPayPri(String payPri) {
        this.payPri = payPri;
    }
    
    public Date getVldDate() {
        return vldDate;
    }
    
    public void setVldDate(Date vldDate) {
        this.vldDate = vldDate;
    }
    
    public String getCrtCode() {
        return crtCode;
    }
    
    public void setCrtCode(String crtCode) {
        this.crtCode = crtCode;
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
    
    public String getCraccBankCode() {
        return craccBankCode;
    }
    
    public void setCraccBankCode(String craccBankCode) {
        this.craccBankCode = craccBankCode;
    }
    
    public String getCraccBankBranch() {
        return craccBankBranch;
    }
    
    public void setCraccBankBranch(String craccBankBranch) {
        this.craccBankBranch = craccBankBranch;
    }
    
    public String getReqSpt1() {
        return reqSpt1;
    }
    
    public void setReqSpt1(String reqSpt1) {
        this.reqSpt1 = reqSpt1;
    }
    
    public String getReqSpt2() {
        return reqSpt2;
    }
    
    public void setReqSpt2(String reqSpt2) {
        this.reqSpt2 = reqSpt2;
    }
    
    public String getReqSpt3() {
        return reqSpt3;
    }
    
    public void setReqSpt3(String reqSpt3) {
        this.reqSpt3 = reqSpt3;
    }
    
    public String getReqMemo() {
        return reqMemo;
    }
    
    public void setReqMemo(String reqMemo) {
        this.reqMemo = reqMemo;
    }
    
    public Long getOrgId() {
        return orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
}
