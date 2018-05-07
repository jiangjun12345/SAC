package net.easipay.cbp.form;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 线下出款通知
 * 
 */
@XmlRootElement(name = "CashLineForm")
public class CashLineForm {

    private String trxSerialNo;//预扣流水号
    private String state;//交易状态
    private String errorDesc;//失败原因
    private String etrxSerialNo;//银行流水号

    private String pyeeAccId;//收款方银行账号

    private String pyerBnkCd;//付款方银行节点代码

    private String pyerBnkNm;//付款方银行名称

    private String pyerBnkAreaCode;//付款方银行地区码

    @XmlElement(name = "ErrorDesc")
    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @XmlElement(name = "TrxSerialNo")
    public String getTrxSerialNo() {
        return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo) {
        this.trxSerialNo = trxSerialNo;
    }

    @XmlElement(name = "State")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlElement(name = "EtrxSerialNo")
    public String getEtrxSerialNo() {
        return etrxSerialNo;
    }

    public void setEtrxSerialNo(String etrxSerialNo) {
        this.etrxSerialNo = etrxSerialNo;
    }

    @XmlElement(name = "PyeeAccId")
    public String getPyeeAccId() {
        return pyeeAccId;
    }

    public void setPyeeAccId(String pyeeAccId) {
        this.pyeeAccId = pyeeAccId;
    }

    @XmlElement(name = "PyerBnkCd")
    public String getPyerBnkCd() {
        return pyerBnkCd;
    }

    public void setPyerBnkCd(String pyerBnkCd) {
        this.pyerBnkCd = pyerBnkCd;
    }

    @XmlElement(name = "PyerBnkNm")
    public String getPyerBnkNm() {
        return pyerBnkNm;
    }

    public void setPyerBnkNm(String pyerBnkNm) {
        this.pyerBnkNm = pyerBnkNm;
    }

    @XmlElement(name = "PyerBnkAreaCode")
    public String getPyerBnkAreaCode() {
        return pyerBnkAreaCode;
    }

    public void setPyerBnkAreaCode(String pyerBnkAreaCode) {
        this.pyerBnkAreaCode = pyerBnkAreaCode;
    }
}
