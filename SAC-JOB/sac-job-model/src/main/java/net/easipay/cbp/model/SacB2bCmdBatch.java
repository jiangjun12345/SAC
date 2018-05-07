/**
 * Copyright : www.easipay.net , 2009-2010
 * Project : PEPP
 * $Id$
 * $Revision$
 * Last Changed by $Author$ at $Date$
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * your name     2011-8-10        Initailized
 */

package net.easipay.cbp.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.log4j.Logger;

/**
 * 指令批次表
* ClassName: SacB2BCMDBatch <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年2月26日 下午4:55:09 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
@Entity
@Table(name = "SAC_B2B_CMD_BATCH")
public class SacB2bCmdBatch implements java.io.Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -718200127118862841L;

	private static final Logger logger = Logger.getLogger(SacB2bCmdBatch.class);
    
    private Long cmdBatchId;// 指令批次ID
    
    private String batchSerialNo;// 应答方处理流水号
    
    private Date vldDate;// 生效日期,即指令预定发送日期
    
    private String msgReceiver;// 指令接收银行代码/付款方银行代码
    
    private String batchState;// 批次状态：00: 待处理;；02 复核不通过；10 已处理待复核； 20 已复核
    
    private String batchCur;// 批币种
    
    private Long batchCount;// 批交易总笔数
    
    private BigDecimal batchAmount;// 批交易总金额
    
    private Date createTime;// 生成时间
    
    private String userName;// 手工生成的批，需要填写生成操作员名
    
    private Date operTime;// 处理时间
    
    private String operName;// 处理人
    
    private Date checkTime;// 复核时间
    
    private String checkName;// 复核人
    
    public Long getCmdBatchId() {
        return cmdBatchId;
    }
    
    public void setCmdBatchId(Long cmdBatchId) {
        this.cmdBatchId = cmdBatchId;
    }
    
    public String getBatchSerialNo() {
        return batchSerialNo;
    }
    
    public void setBatchSerialNo(String batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }
    
    

	public Date getVldDate() {
		return vldDate;
	}

	public void setVldDate(Date vldDate) {
		this.vldDate = vldDate;
	}

	public String getMsgReceiver() {
		return msgReceiver;
	}

	public void setMsgReceiver(String msgReceiver) {
		this.msgReceiver = msgReceiver;
	}

	public String getBatchState() {
        return batchState;
    }
    
    public void setBatchState(String batchState) {
        this.batchState = batchState;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public static Logger getLogger() {
        return logger;
    }
    
    public String getBatchCur() {
        return batchCur;
    }
    
    public void setBatchCur(String batchCur) {
        this.batchCur = batchCur;
    }
    
    public Long getBatchCount() {
        return batchCount;
    }
    
    public void setBatchCount(Long batchCount) {
        this.batchCount = batchCount;
    }
    
    public BigDecimal getBatchAmount() {
        return batchAmount;
    }
    
    public void setBatchAmount(BigDecimal batchAmount) {
        this.batchAmount = batchAmount;
    }

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
    
    
}
