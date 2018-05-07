package net.easipay.cbp.form;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * 参数
 * Created by lc on 2017/3/15.
 */
@XmlRootElement
public class OnbehalfExampleRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2796577805409779030L;
	
	private String trxSerialNo;
    private String state;
    private String errorDesc;

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

    @XmlElement(name = "ErrorDesc")
    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
