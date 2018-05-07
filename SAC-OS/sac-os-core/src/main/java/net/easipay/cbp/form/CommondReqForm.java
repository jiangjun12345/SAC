package net.easipay.cbp.form;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/** 预扣审核结果
 * 
 */
@XmlRootElement
public class CommondReqForm implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2307973664773347957L;
	
	private String trxSerialNo;//预扣流水号
    private String batchSerialNo;//批次流水号
    private String batchCount;//批次总笔数

    private String valTime;//生效时间
    private String payAmount;//实扣金额
    private String payCurrency;//实扣币种

    @XmlElement(name = "TrxSerialNo")
    public String getTrxSerialNo() {
        return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo) {
        this.trxSerialNo = trxSerialNo;
    }

    @XmlElement(name = "BatchSerialNo")
    public String getBatchSerialNo() {
        return batchSerialNo;
    }

    public void setBatchSerialNo(String batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }

    @XmlElement(name = "BatchCount")
    public String getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(String batchCount) {
        this.batchCount = batchCount;
    }

    @XmlElement(name = "ValTime")
    public String getValTime() {
        return valTime;
    }

    public void setValTime(String valTime) {
        this.valTime = valTime;
    }

    @XmlElement(name = "PayAmount")
    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    @XmlElement(name = "PayCurrency")
    public String getPayCurrency() {
        return payCurrency;
    }

    public void setPayCurrency(String payCurrency) {
        this.payCurrency = payCurrency;
    }
}
