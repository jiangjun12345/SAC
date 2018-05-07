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
		$("#purchaseAndPayDetailQueryBackForm").submit();
	});
});
</script>
<title>购汇交易明细查询</title>
</head>
<body>
	<div style="display: none;">
		<form id="purchaseAndPayDetailQueryBackForm" action="purchaseAndPayDetailQueryBack" method="get">
			<input type="text" id="startDate" name="startDate" value="${sessionScope.startDate}"/>
			<input type="text" id="endDate" name="endDate"  value="${sessionScope.endDate}"/>
			<input type="text" id="trxType" name="trxType" value="${sessionScope.trxType}"/>
			<input type="text" id="draccCusName" name="draccCusName" value="${sessionScope.draccCusName}"/>
			<input type="text" id="sacCurrency" name="sacCurrency" value="${sessionScope.sacCurrency}"/>
			<input type="text" id="payCurrency" name="payCurrency" value="${sessionScope.payCurrency}"/>
			<input type="text" id="pageNo2" name="pageNo" value="${sessionScope.pageNo}"/>
		</form>
	</div>
	<div class="content">
	 	<div class="con ">
	 		<form id="trxDetailForm" action="purchaseAndPayDetailQuery" method="post" >
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">交易批次号：</td>
	 						<td><input type="text" class="txt2" name="trxBatchId" value="${trxBatchId}" /></td>
	 					</tr>
	 					<tr>
	 						<td align="right"><input id="query" class="bluebtn" type="submit" value="查询" ></td>
	 						<td align="left"><input id="back" class="bluebtn" type="button" value="返回" ></td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2" >
	 		<div width="500px" style="overflow:scroll;">
		 		<table width="2000px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
		 				<th>序号</th>
						<th>交易完成时间</th>
						<!-- <th>平台订单编号</th> -->
						<th>支付流水号</th>
						<!-- <th>订单号/退款申请编号/购结汇批次号</th> -->
						<th>业务类型</th>
						<th>交易类型</th>
						<th>交易金额</th>
						<th>交易币种</th>
						<!-- <th>成本(RMB)</th> -->
						<th>购结汇币种</th>
						<th>购汇汇率/结汇汇率</th>
						<th>购汇金额/结汇金额</th>
						<th>支付方式</th>
						<th>支付渠道类型</th>
						<th>收款账户</th>
						<th>收款银行</th>
						<th>付款账户</th>
						<th>付款银行</th>
						<th>交易状态</th>
		 			</tr>
		 			<c:if test="${empty trxDetailList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="trxDetail" items="${trxDetailList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
							<td class="fontfamily1">${(pageNo-1)*pageSize+status.index+1}</td>
							<td class="fontfamily1">
								<p><fmt:formatDate value="${trxDetail.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
							</td>
							<%-- <td class="fontfamily1">${trxDetail.platBillNo}</td> --%>
							<td class="fontfamily1">${trxDetail.trxSerialNo}</td>
							<%-- <td class="fontfamily1">${trxDetail.cusBillNo}</td> --%>
							<td class="fontfamily1">${trxDetail.bussType}</td>
							<td class="fontfamily1">${trxDetail.trxType}</td>
							<td class="fontfamily1">${trxDetail.payAmount}</td>
							<td class="fontfamily1">${trxDetail.payCurrency}</td>
							<!-- <td class="fontfamily1">0.00</td> -->
							<td class="fontfamily1">${trxDetail.sacCurrency}</td>
							<td class="fontfamily1">${trxDetail.exRate}</td>
							<td class="fontfamily1">${trxDetail.sacAmount}</td>
							<td class="fontfamily1">${trxDetail.payWay}</td>
							<td class="fontfamily1">${trxDetail.payconType}</td>
							<td class="fontfamily1">${trxDetail.craccName}</td>
							<td class="fontfamily1">${trxDetail.craccNodeCode}</td>
							<td class="fontfamily1">${trxDetail.draccName}</td>
							<td class="fontfamily1">${trxDetail.draccNodeCode}</td>
							<td class="fontfamily1">${trxDetail.trxState}</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height:auto; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
	                	<td class="fontfamily1" colspan="12" align="left">
	                		<c:if test="${not empty currentMap}">
				    			<span>当页汇总：
						    		<c:forEach items="${currentMap}" var="curMap" >
						    			<span>${curMap.key}:${curMap.value}元&nbsp;</span>
						    		</c:forEach>
					    		</span>
				    		</c:if>
	                	</td>
	                </tr>
	                <tr>
	                	<td class="fontfamily1" colspan="12" align="left">
				    		<c:if test="${not empty allTrxDetailMap}">
				    			<span>查询汇总：
						    		<c:forEach items="${allTrxDetailMap}" var="allMap" >
						    			<span>${allMap.key}:${allMap.value}元&nbsp;</span>
						    		</c:forEach>
					    		</span>
				    		</c:if>
	                	</td>
	                </tr>
					<tr>
						<td align="right" >
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/purchaseAndPayDetailQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>