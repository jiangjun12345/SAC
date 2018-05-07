<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		//点击退回按钮 
		$("#back").click(function(){
			window.location.href = "b2bRefundBatchDetailBack";
		});
	});
</script>
<title>银联B2B退款复核明细</title>
</head>

<body>
	<div class="content">
	 	<div style="overflow:scroll;">
	 			<table  width="100%" border="0" cellpadding="0" cellspacing="0">
	 			    <tr>
	 			       <td height="35">
	 			                  <font size="3">退款批次信息</font> 
	 			       </td>
	 			    
	 			    </tr>
		 			<tr height="35" bgcolor="#cccccc">
						<th>批次流水号</th>
		 				<th>总笔数</th>
		 				<th>总金额</th>
						<th>上传时间</th>
						<th>批次复核状态</th>
						<th>上传人</th>
						<th>复核日期</th>
						<th>复核人</th>
		 			</tr>
                    <c:if test="${empty batchList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
                <c:forEach var="batch" items="${batchList}" varStatus="status">
                	<c:choose>
	                       <c:when test="${status.index %2 == 0 }">
	                           <tr align="center" height="35">
	                       </c:when>
	                       <c:otherwise>
	                           <tr align="center" height="35" bgcolor="#eeeeee">
	                       </c:otherwise>
                     </c:choose>
                     	<td>${batch.batchSerialNo}</td>
                     	<td>${batch.batchTcount}</td>
                     	<td>${batch.batchTamount}</td>
                     	<td>
								<p><fmt:formatDate value="${batch.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
						</td>
						<td>${batch.batchStateName}</td>
						<td>${batch.operatorName}</td>
						<td>
							   <p><fmt:formatDate value="${batch.auditTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
						</td>
						<td>${batch.auditorName}</td>
                     </tr>
                </c:forEach>
	                
		 		</table>
		 		&nbsp;&nbsp;
	 		</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2" >
	 		<div style="overflow:scroll;">
	 			<table  id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
		 			<tr>
	 			       <td height="35">
	 			                  <font size="3">退款批次详细信息</font> 
	 			       </td>
	 			    </tr>
		 			<tr height="35" bgcolor="#cccccc">
						<th>退款时间</th>
		 				<th>退款流水号</th>
		 				<th>金额(CNY)</th>
						<th>银行账号</th>
						<th>开户行名称</th>
						<th>银行流水号</th>
						<th>退款状态</th>
		 			</tr>
		 			<c:if test="${empty commandList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="command" items="${commandList}" varStatus="status">
	                	<c:set var="flag" value="0" />
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
	                     	<td><p><fmt:formatDate value="${command.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p></td>
							<td>${command.otrxSerialNo}</td>
							<td>${command.payAmount}</td>
							<td>${command.craccNo}</td>
							<td>${command.craccBankBranch}</td>
							<td>${command.rtrxSerialNo}</td>
							<td>
								<c:choose>
                                  	<c:when test="${command.trxState=='1'}">
                                         	待下载
                                    </c:when>
                                    <c:when test="${command.trxState=='2'}">
                                         	已下载待处理
                                    </c:when>
                                    <c:when test="${command.trxState=='3'}">
                                         	退款未处理
                                    </c:when>
                                    <c:otherwise>
                                                                                                                          退款成功
                                    </c:otherwise>
                                 </c:choose>
							</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table id = "table2"  width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
						  	<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/b2bRefundBatchDetailQuery?oflWithdrawBatchId=${oflWithdrawBatchId}"/>
						</td>
					</tr>
					
				</table>
	 		</div>
	 		<div class="table fontcolor4 fontsize1 fontfamily2">
    		<table id="table3" width="100%" border="0" cellpadding="0" cellspacing="0">
    				<tr>
    					<td align="center">
    						<input id="back" class="bluebtn" type="button" value="返回">
    					</td>
    				</tr>	
    		</table>
    		
    		</div>
	 	</div>
	</div>
</body>
</html>