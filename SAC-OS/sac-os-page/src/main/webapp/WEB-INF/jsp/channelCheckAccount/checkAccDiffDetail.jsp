<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
	$(function(){
		//点击退回按钮 
		$("#back").click(function(){
			$("#queryDiffDetailBackForm").submit();
		});
	});
</script>
<body>
	<div style="display: none;">
		<form id="queryDiffDetailBackForm" action="queryDiffDetailBack" method="get">
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
	 			<table id="diffDetailTable" border="0" cellpadding="0" cellspacing="0" width="1300px" >
					<tr height="35" bgcolor="#cccccc">
						<th>对账批次ID</th>
						<th>支付渠道</th>
						<th>交易代码</th>
						<th>交易起始日期</th>
						<th>交易终止日期</th>
						<th>交易流水号</th>
						<th>支付金额</th>
						<th>差错类型</th>
						<th>差错原因</th>
						<th>处理状态</th>
						<th>处理方式</th>
						<th>处理人员</th>
						<th>记录创建时间</th>
					</tr>
					<c:if test="${empty recDiffDetailList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="recDiffDetail" items="${recDiffDetailList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
					      	<td class="fontfamily1">${recDiffDetail.recBatchId}</td>
							<td class="fontfamily1">${recDiffDetail.chnCode}</td>
							<td class="fontfamily1">${recDiffDetail.trxCode}</td>
							<td class="fontfamily1"><fmt:formatDate value="${recDiffDetail.recStartDate}" pattern="yyyyMMdd"/></td>
							<td class="fontfamily1"><fmt:formatDate value="${recDiffDetail.recEndDate}" pattern="yyyyMMdd"/></td>
							<td class="fontfamily1">${recDiffDetail.trxSerialNo}</td>
							<td class="fontfamily1">${recDiffDetail.payAmount}</td>
							<td class="fontfamily1">${recDiffDetail.recDiffType}</td>
							<td class="fontfamily1">${recDiffDetail.recDiffDesc}</td>
							<td class="fontfamily1">${recDiffDetail.status}</td>
							<td class="fontfamily1">${recDiffDetail.dealType}</td>
							<td class="fontfamily1">${recDiffDetail.dealOper}</td>
							<td class="fontfamily1">
								<fmt:formatDate value="${recDiffDetail.createTime}" pattern="yyyyMMdd"/>
							</td>
	                     </tr>
	                </c:forEach>
				</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/queryDiffDetail"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>