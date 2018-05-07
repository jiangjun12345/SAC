<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function queryPreparedFundDetail(data){
		var chnNo = $("#chnNo").val();
		var chnName = $("#chnName").val();
		var pageNo = ${pageNo}
		$.ajax({
			type:"POST",
			url:"preparedFundRealTimeDetailQuery",
			async:false,
			data:{
				chnNo:chnNo,
				chnName:chnName,
				pageNo:pageNo,
				saveFlag:"Y"
			},
			success:function(){
				window.location.href = "preparedFundRealTimeDetailQuery?finCode="+data;
			}
		});	
	}
	
	
</script>
<title>备付金实时查询</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="preparedFundManageForm" action="preparedFundRealTimeQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">渠道号:</td>
	 						<td>
	 							<input type="text" class="select1" id="chnNo" name="chnNo" value="${chnNo}" width="100px"/>
	 						</td>
	 						<td align="right">渠道名称:</td>
	 						<td>
	 							<input type="text" class="select1" id="chnName" name="chnName" value="${chnName}" width="100px"/>
	 						</td>
	 						<td>
	 							<input id="query" class="bluebtn" type="submit" value="查询">
	 						</td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 			<tr height="35" bgcolor="#cccccc">
	 				<th>渠道账号</th>
					<th>渠道名称</th>
					<th>备付金余额</th>
					<th>操作</th>
	 			</tr>
	 			<c:if test="${empty preparedFundRealTimeList}">
                    <tr>
                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                    </tr>
                </c:if>
                <c:forEach var="preparedFund" items="${preparedFundRealTimeList}" varStatus="status">
                	<c:choose>
	                       <c:when test="${status.index %2 == 0 }">
	                           <tr align="center" height="35">
	                       </c:when>
	                       <c:otherwise>
	                           <tr align="center" height="35" bgcolor="#eeeeee">
	                       </c:otherwise>
                     </c:choose>
                     	<td class="fontfamily1">${preparedFund.yeId}</td>
                     	<td class="fontfamily1">
                     		<c:forEach items="${ccyMap}" var="ccy">
                     			<c:if test="${ccy.key eq preparedFund.currencyType}">${preparedFund.chnName}(${ccy.value})</c:if>
                     		</c:forEach>
                     	</td>
						<td class="fontfamily1">${preparedFund.totalAmount eq null?0.00:preparedFund.totalAmount}</td>
						<td class="fontfamily1">
							<c:choose>
								<c:when test="${preparedFund.yeId eq null}">-</c:when>
								<c:otherwise>
									<input type="button" value="详情" onclick="queryPreparedFundDetail('${preparedFund.yeId}');">
								</c:otherwise>
							</c:choose>
						</td>
                     </tr>
                </c:forEach>
	 		</table>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/preparedFundRealTimeQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>