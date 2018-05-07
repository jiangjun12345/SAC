<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
	$(function(){
		//点击退回按钮 
		$("#back").click(function(){
			$("#queryBatchDetailBackForm").submit();
		});
	});
</script>
<body>
	<div style="display: none;">
		<form id="queryBatchDetailBackForm" action="queryBatchDetailBack" method="get">
			<input type="text" id="startDate" name="startDate" value="${sessionScope.startDate}"/>
			<input type="text" id="endDate" name="endDate"  value="${sessionScope.endDate}"/>
			<input type="text" id="chnCode" name="chnCode" value="${sessionScope.chnCode}"/>
			<input type="text" id="payconType" name="payconType" value="${sessionScope.payconType}"/>
			<input type="text" id="recType" name="recType" value="${sessionScope.recType}"/>
			<input type="text" id="recStatus" name="recStatus" value="${sessionScope.recStatus}"/>
			<input type="text" id="pageNo2" name="pageNo" value="${sessionScope.pageNo}"/>
		</form>
	</div>
	<div class="content">
	 	<div class="con ">
	 		<div class="table fontcolor4 fontsize1 fontfamily2">
 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">对账批次号:</td>
						<td>
							<input type="text" disabled="disabled"  class="txt2" id="recBatchId" name="recBatchId" value="${recBatchId}" />
						</td>
						<td>
							<input id="back" class="bluebtn" type="button" value="返回">
						</td>
					</tr>
 				</table>
 			</div>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<div style="overflow:scroll;" >
	 			<table id="batchDetailTable" border="0" cellpadding="0" cellspacing="0" width="1200px" >
					<tr height="35" bgcolor="#cccccc">
						<th>对账批次号</th>
						<th>支付渠道</th>
						<th>交易代码</th>
						<th>交易流水号</th>
						<th>交易起始日期</th>
						<th>交易终止日期</th>
						<th>交易时间</th>
						<th>支付金额</th>
						<th>外部流水号</th>
						<th>支付渠道类型</th>
						<th>差异类型</th>
						<th>币种</th>
						<th>正逆向标志</th>
						<th>操作人</th>
					</tr>
					<c:if test="${empty recBatchDetailList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="recBatchDetail" items="${recBatchDetailList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
					      	<td class="fontfamily1">${recBatchDetail.recBatchId}</td>
							<td class="fontfamily1">${recBatchDetail.chnCode}</td>
							<td class="fontfamily1">${recBatchDetail.trxCode}</td>
							<td class="fontfamily1">${recBatchDetail.trxSerialNo}</td>
							<td class="fontfamily1"><fmt:formatDate value="${recBatchDetail.recStartDate}" pattern="yyyyMMdd"/></td>
							<td class="fontfamily1"><fmt:formatDate value="${recBatchDetail.recEndDate}" pattern="yyyyMMdd"/></td>
							<td class="fontfamily1"><fmt:formatDate value="${recBatchDetail.trxTime}" pattern="HH:mm:ss"/></td>
							<td class="fontfamily1">${recBatchDetail.payAmount}</td>
							<td class="fontfamily1">${recBatchDetail.bankSerialNo}</td>
							<td class="fontfamily1">${recBatchDetail.payconType eq 1?"B2B":"B2C"}</td>
							<td class="fontfamily1">
								<c:if test="${recBatchDetail.diffType eq 1}">长款</c:if>
								<c:if test="${recBatchDetail.diffType eq 2}">短款</c:if>
								<c:if test="${recBatchDetail.diffType eq 3}">其他</c:if>
							</td>
							<td class="fontfamily1">${recBatchDetail.currencyType}</td>
							<td class="fontfamily1">
								<c:if test="${recBatchDetail.busiType eq 1}">正向</c:if>
								<c:if test="${recBatchDetail.busiType eq 2}">逆向</c:if>
								<c:if test="${recBatchDetail.busiType ne 1 and recBatchDetail.busiType ne 2}">-</c:if>
							</td>
							<td class="fontfamily1">${recBatchDetail.recOper}</td>
	                     </tr>
	                </c:forEach>
				</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/queryBatchDetail"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>