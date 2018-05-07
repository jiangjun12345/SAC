<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>预存明细信息</title>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="content">
	<div class="con ">
		<div>
			<span style="font-size: 13px;color: #666666; font-family: 微软雅黑;">概要信息:</span>
		</div>
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;color: #666666; font-family: 微软雅黑;">
							<tr>
								<td align="right" width="15%">到账时间:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="bankTrxDate" name="bankTrxDate" value="<fmt:formatDate value="${detail.bankTrxDate}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">预存记录状态:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="dealState" name="dealState" value="${detail.dealState}" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%">银行流水号:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="bankSerialNo" name="bankSerialNo" value="${detail.bankSerialNo}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">八位码:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="applyCode" name="applyCode" value="${detail.applyCode}" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%">企业名称:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="draccName" name="draccName" value="${detail.draccName}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">金额(CNY):</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="payAmount" name="payAmount" value="${detail.payAmount}" readonly="readonly"/>
								</td>
							</tr>
						</table>
					</div>
			<div>
			
			
			<span style="font-size: 13px;color: #666666; font-family: 微软雅黑;">明细信息:</span>
		</div>
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;color: #666666; font-family: 微软雅黑;">
							<tr>
								<td align="right" width="15%">批次号:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="oflDepositBatchId" name="oflDepositBatchId" value="${detail.oflDepositBatchId}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">创建时间:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="createTime" name="createTime" value="<fmt:formatDate value="${detail.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%">销账状态:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="checkState" name="checkState" value="${detail.checkState}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">失败原因:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="dealMemo" name="dealMemo" value="${detail.dealMemo}" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%">付款银行名称:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="draccBankName" name="draccBankName" value="${detail.draccBankName}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">付款银行账号:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="draccNo" name="draccNo" value="${detail.draccNo}" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%">经办人:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="operatorName" name="operatorName" value="${detail.operatorName}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">经办时间:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="createTime" name="createTime" value="<fmt:formatDate value="${detail.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"/>
								</td>
							</tr>
							<tr>
								<td align="right" width="15%">复核人:</td>
	 							<td width="30%">
	 							<input type="text" class="txt2" id="auditorName" name="auditorName" value="${detail.auditorName}" readonly="readonly"/>
		 						</td>
		 						<td align="right" width="15%">复核时间:</td>
		 						<td width="30%">
								<input type="text" class="txt2" id="auditTime" name="auditTime" value="<fmt:formatDate value="${detail.auditTime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="readonly"/>
								</td>
							</tr>
						</table>
					</div>		
					
					
			</div>
	</div>
</body>
</html>
