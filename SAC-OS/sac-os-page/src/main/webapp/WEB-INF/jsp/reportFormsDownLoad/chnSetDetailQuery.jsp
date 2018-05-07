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
		/* 点击下载按钮  */
		$("#download").click(function(){
			$("#chnSetDetailForm").attr("action","${ctx}/chnSetDetailDownload").submit();
			$("#chnSetDetailForm").attr("action","${ctx}/chnSetDetailQuery");
		});
		//点击退回按钮 
		$("#back").click(function(){
			$("#chnSetDetailQueryBackForm").submit();
		});
	});
</script>
<title>渠道应收款明细及下载</title>
</head>
<body>
	<div style="display: none;">
		<form id="chnSetDetailQueryBackForm" action="chnSetDetailQueryBack" method="get">
			<input type="text" id="startSacDate" name="startSacDate" value="${sessionScope.startSacDate}"/>
			<input type="text" id="endSacDate" name="endSacDate"  value="${sessionScope.endSacDate}"/>
			<input type="text" id="startTransDate" name="startTransDate" value="${sessionScope.startTransDate}"/>
			<input type="text" id="endTransDate" name="endTransDate" value="${sessionScope.endTransDate}"/>
			<input type="text" id="bankNodeCode" name="bankNodeCode" value="${sessionScope.bankNodeCode}"/>
			<input type="text" id="currencyType" name="currencyType" value="${sessionScope.currencyType}"/>
			<input type="text" id="pageNo2" name="pageNo" value="${sessionScope.pageNo}"/>
		</form>
	</div>
	 <div class="content">
	 	<div class="con ">
	 		<form id="chnSetDetailForm" action="chnSetDetailQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">应收清分批次号:</td>
							<td>
								<input type="text" class="txt2" id="chnBatchNo" name="chnBatchNo" value="${chnBatchNo}" />
							</td>
							<td>
								<input id="query" class="bluebtn" type="submit" value="查询">
								<input id="download" class="bluebtn" type="button" value="下载">
								<input id="back" class="bluebtn" type="button" value="返回">
							</td>
						</tr>
	 				</table>
	 			</div>
			</form>
	 	</div>
	 	
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 			<tr height="35" bgcolor="#cccccc">
					<th>支付渠道名称</th>
					<th>支付渠道类型</th>
					<th>清算行名称</th>
					<th>来款账号</th>
					<th>到账日期</th>
					<th>清算日期</th>
					<th>类型</th>
					<th>正逆向</th>
					<th>笔数</th>
					<th>总金额</th>
					<!-- <th>成本</th> -->
					<th>币种</th>
					<th>应收清分批次号</th>
					<th>创建时间</th>
				</tr>
				<c:if test="${empty chnSetDetailList}">
                    <tr>
                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                    </tr>
                </c:if>
                <c:forEach var="chnSetDetail" items="${chnSetDetailList}" varStatus="status">
                	 <c:choose>
	                       <c:when test="${status.index %2 == 0 }">
	                           <tr align="center" height="35">
	                       </c:when>
	                       <c:otherwise>
	                           <tr align="center" height="35" bgcolor="#eeeeee">
	                       </c:otherwise>
                     </c:choose>
						<td class="fontfamily1">${chnSetDetail.chnName}</td>
						<td class="fontfamily1">${chnSetDetail.payconType}</td>
						<td class="fontfamily1">${chnSetDetail.sacBankName}</td>
						<td class="fontfamily1">${chnSetDetail.accountNumber}</td>
						<td class="fontfamily1">${channelSettlement.transDate}</td>
						<td class="fontfamily1">${chnSetDetail.sacDate}</td>
						<td class="fontfamily1">${chnSetDetail.type}</td>
						<td class="fontfamily1">${chnSetDetail.busiType}</td>
						<td class="fontfamily1">${chnSetDetail.totalNum}</td>
						<td class="fontfamily1">${chnSetDetail.totalSum}</td>
						<%-- <td class="fontfamily1">${chnSetDetail.trxCost}</td> --%>
						<td class="fontfamily1">${chnSetDetail.currencyType}</td>
						<td class="fontfamily1">${chnSetDetail.chnBatchNo}</td>
						<td class="fontfamily1">
							<fmt:formatDate value="${chnSetDetail.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
						</td>
                     </tr>
                </c:forEach>
	 		</table>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/chnSetDetailQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	 </div>
	
</body>
</html>