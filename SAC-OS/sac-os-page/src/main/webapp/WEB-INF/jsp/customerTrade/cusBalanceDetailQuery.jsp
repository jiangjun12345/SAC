<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
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
			$("#cusBalanceForm").attr("action","${ctx}/cusBalanceDownload").submit();
			$("#cusBalanceForm").attr("action","${ctx}/cusBalanceQuery");
		});
		//点击退回按钮 
		$("#back").click(function(){
			$("#cusBalanceQueryBackForm").submit();
		});
	});
</script>
<title>客户余额明细查询</title>
</head>

<body>
	<div style="display: none;">
		<form id="cusBalanceQueryBackForm" action="cusBalanceQueryBack" method="get">
			<input type="text" id="cusNo" name="cusNo" value="${sessionScope.cusNo}"/>
			<input type="text" id="cusName" name="cusName"  value="${sessionScope.cusName}"/>
			<input type="text" id="bussType" name="bussType" value="${sessionScope.bussType}"/>
			<input type="text" id="childAccType" name="childAccType" value="${sessionScope.childAccType}"/>
			<input type="text" id="pageNo2" name="pageNo" value="${sessionScope.pageNo}"/>
		</form>
	</div>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
	 		<form id="cusBalanceForm" action="cusBalanceQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">凭证时间(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd'})"/>
	 						</td>
	 						<td align="right">凭证时间(止):</td>
	 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">客户账号:</td>
	 						<td><input type="text" class="txt2" name="finCode" value="${finCode}" /></td>
	 						<td colspan="2">
	 							<input id="query" class="bluebtn" type="submit" value="查询">
	 							<input id="download" class="bluebtn" type="button" value="下载">
	 							<input id="back" class="bluebtn" type="button" value="返回">
	 						</td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2" >
	 		<div style="overflow:scroll;">
	 			<table  width="1800px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
						<th>科目代码</th>
		 				<th>凭证号</th>
		 				<th>凭证时间</th>
						<th>借贷标志</th>
						<th>借方发生额</th>
						<th>贷方发生额</th>
						<th>发生额</th>
						<th>期初余额</th>
						<th>期末余额</th>
						<th>结汇损益</th>
						<th>业务类型</th>
						<th>交易类型</th>
						<th>交易时间</th>
						<th>交易流水号</th>
		 			</tr>
		 			<c:if test="${empty cusBalanceDetailList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="balance" items="${cusBalanceDetailList}" varStatus="status">
	                	<c:set var="flag" value="0" />
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
	                     	<td>${balance.codeId}</td>
							<td>${balance.pzNo}</td>
							<td>
								<fmt:formatDate value="${balance.mxTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${balance.direction eq 1}">借</c:when>
									<c:when test="${balance.direction eq -1}">贷</c:when>
								</c:choose>
							</td>
							<td>${balance.fDebit}</td>
							<td>${balance.fCredit}</td>
							<td>${balance.amount}</td>
							<td>${balance.openBal}</td>
							<td>${balance.closeBal}</td>
							<td>${balance.gains eq null?"-":balance.gains}</td>
							<td>
								<c:forEach items="${bussTypeMap}" var="busiType">
									<c:if test="${busiType.key eq balance.busiType }">
										${busiType.value}
										<c:set var="flag" value="1" />
									</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${trxTypeMap}" var="trxType">
									<c:if test="${trxType.key eq balance.trxCode }">
										${trxType.value}
										<c:set var="flag" value="1" />
									</c:if>
								</c:forEach>
							</td>
							<td>
								<fmt:formatDate value="${balance.tradeTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
							</td>
							<td>${balance.serno}</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/cusBalanceQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>