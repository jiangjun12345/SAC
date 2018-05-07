package net.easipay.cbp.form;

import org.hibernate.validator.constraints.NotBlank;

public class SacRemittanceBatchIdForm implements java.io.Serializable
{
    private static final long serialVersionUID = 2147830824246267956L;
    @NotBlank(message = "交易流水号不能为空")
    private String trxSerialNo;

    @NotBlank(message = "批次号不能为空")
    private String remittanceBatchId;

    public String getTrxSerialNo()
    {
        return trxSerialNo;
    }

    public void setTrxSerialNo(String trxSerialNo)
    {
        this.trxSerialNo = trxSerialNo;
    }

    public String getRemittanceBatchId()
    {
        return remittanceBatchId;
    }

    public void setRemittanceBatchId(String remittanceBatchId)
    {
        this.remittanceBatchId = remittanceBatchId;
    }
}
