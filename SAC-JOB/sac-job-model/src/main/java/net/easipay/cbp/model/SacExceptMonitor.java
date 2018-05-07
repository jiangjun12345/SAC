package net.easipay.cbp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "SAC_EXCEPT_MONITOR")
public class SacExceptMonitor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4687370860012225329L;
	private Long id; //主键ID
	private String serverIp; 	//服务端IP
	private String clientIp; 	//客户端IP
	private String reqChanner;	//请求方
	private String serChanner;	//服务方
	private String serviceId;	//服务ID
	private String reqUrl;		//请求URL
	private String operateType;	//操作类型
	private Date createTime = new Date();	//创建时间
	private String busiType;	//业务类型
	private String txnCode; 	//交易码
	private String trxSerialId;	//交易流水号
	private String recBatchId;	//批次号
	private String reqData;		//请求数据
	private String errorNo;		//错误码
	private String errorMsg;	//错误描述
	private String errorReason;	//错误原因
	private String status;		//状态
	private String tableId;		//各个表ID
	private Date recordTime;    //各个表记录时间
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getReqChanner() {
		return reqChanner;
	}
	public void setReqChanner(String reqChanner) {
		this.reqChanner = reqChanner;
	}
	public String getSerChanner() {
		return serChanner;
	}
	public void setSerChanner(String serChanner) {
		this.serChanner = serChanner;
	}
	public String getReqUrl() {
		return reqUrl;
	}
	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBusiType() {
		return busiType;
	}
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	public String getTxnCode() {
		return txnCode;
	}
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	public String getTrxSerialId() {
		return trxSerialId;
	}
	public void setTrxSerialId(String trxSerialId) {
		this.trxSerialId = trxSerialId;
	}
	public String getRecBatchId() {
		return recBatchId;
	}
	public void setRecBatchId(String recBatchId) {
		this.recBatchId = recBatchId;
	}
	public String getReqData() {
		return reqData;
	}
	public void setReqData(String reqData) {
		this.reqData = reqData;
	}
	public String getErrorNo() {
		return errorNo;
	}
	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getTableId() {
		return tableId;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	
	
	
}
