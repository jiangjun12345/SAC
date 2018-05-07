package net.easipay.cbp.model;

import java.util.Date;

public class SacRecordLog implements java.io.Serializable
{
    private static final long serialVersionUID = 5237322129002426618L;
    private Long id;
    private String transactionId;
    private String channel;
    private String origin;
    private String serverIp;
    private String clientIp;
    private Long timemillis;
    private String interfaceId;
    private String url;
    private String trxcode;
    private String code;
    private String message;
    private String data;
    private Date createTime;
    private String demo;

    public Long getId()
    {
	return id;
    }

    public void setId(Long id)
    {
	this.id = id;
    }

    public String getTransactionId()
    {
	return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
	this.transactionId = transactionId;
    }

    public String getChannel()
    {
	return channel;
    }

    public void setChannel(String channel)
    {
	this.channel = channel;
    }

    public String getOrigin()
    {
	return origin;
    }

    public void setOrigin(String origin)
    {
	this.origin = origin;
    }

    public String getServerIp()
    {
	return serverIp;
    }

    public void setServerIp(String serverIp)
    {
	this.serverIp = serverIp;
    }

    public String getClientIp()
    {
	return clientIp;
    }

    public void setClientIp(String clientIp)
    {
	this.clientIp = clientIp;
    }

    public Long getTimemillis()
    {
	return timemillis;
    }

    public void setTimemillis(Long timemillis)
    {
	this.timemillis = timemillis;
    }

    public String getInterfaceId()
    {
	return interfaceId;
    }

    public void setInterfaceId(String interfaceId)
    {
	this.interfaceId = interfaceId;
    }

    public String getUrl()
    {
	return url;
    }

    public void setUrl(String url)
    {
	this.url = url;
    }

    public String getTrxcode()
    {
	return trxcode;
    }

    public void setTrxcode(String trxcode)
    {
	this.trxcode = trxcode;
    }

    public String getCode()
    {
	return code;
    }

    public void setCode(String code)
    {
	this.code = code;
    }

    public String getMessage()
    {
	return message;
    }

    public void setMessage(String message)
    {
	this.message = message;
    }

    public String getData()
    {
	return data;
    }

    public void setData(String data)
    {
	this.data = data;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getDemo()
    {
	return demo;
    }

    public void setDemo(String demo)
    {
	this.demo = demo;
    }
}
