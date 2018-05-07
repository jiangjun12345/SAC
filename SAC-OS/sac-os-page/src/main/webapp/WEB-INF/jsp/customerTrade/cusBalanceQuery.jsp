<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function queryCusBalanceDetail(data){
		var cusNo = $("#cusNo").val();
		var cusName = $("#cusName").val();
		var bussType = $("#bussType").val();
		var childAccType = $("#childAccType").val();
		var pageNo = ${pageNo};
		$.ajax({
			type:"POST",
			url:"cusBalanceQuery",
			async:false,
			data:{
				cusNo:cusNo,
				cusName:cusName,
				bussType:bussType,
				childAccType:childAccType,
				pageNo:pageNo,
				saveFlag:"Y"
			},
			success:function(){
				window.location.href = "cusBalanceQuery?finCode="+data;
			}
		});	
	}
	$(function(){
		var bussType = "${bussType}";/* 业务类型 */
		$('select[name="bussType"]').find("option[value="+bussType+"]").attr("selected",true);
		var childAccType = "${childAccType}";/* 账户子类型 */
		$('select[name="childAccType"]').find("option[value="+childAccType+"]").attr("selected",true);
	});
</script>
<title>客户余额查询</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="cusBalanceForm" action="cusBalanceQueryInit" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">客户号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="cusNo" name="cusNo" value="${cusNo}" width="100px"/>
	 						</td>
	 						<td align="right">客户名称:</td>
	 						<td>
	 							<input type="text" class="txt2" id="cusName" name="cusName" value="${cusName}" width="100px"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">业务类型:</td>
	 						<td>
	 							<select id="bussType" class="select1"  name="bussType" width="100px" >
	 								<option value="" >无</option>
									<c:forEach items="${bussTypeMap}" var="bussType">
										<option value="${bussType.key}" >${bussType.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 						<td align="right">功能账户类型:</td>
	 						<td>
	 							<select id="childAccType" class="select1"  name="childAccType" width="100px" >
	 								<option value="" >无</option>
									<c:forEach items="${childAccTypeMap}" var="childAccType">
										<option value="${childAccType.key}" >${childAccType.value}</option>
									</c:forEach>
								</select>
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
	 				<th>客户账号</th>
	 				<th>业务类型</th>
	 				<th>功能账户类型</th>
					<th>客户名称</th>
					<th>客户余额</th>
					<th>操作</th>
	 			</tr>
	 			<c:if test="${empty cusBalanceList}">
                    <tr>
                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                    </tr>
                </c:if>
                <c:forEach var="balance" items="${cusBalanceList}" varStatus="status">
                	<c:choose>
	                       <c:when test="${status.index %2 == 0 }">
	                           <tr align="center" height="35">
	                       </c:when>
	                       <c:otherwise>
	                           <tr align="center" height="35" bgcolor="#eeeeee">
	                       </c:otherwise>
                     </c:choose>
                     	<td>${balance.cusNoId}</td>
                     	<td>${balance.bussType}</td>
                     	<td>${balance.childAccType}</td>
                     	<td>${balance.cusName}(${balance.sacCurrency})</td>
						<td>${balance.totalAmount}</td>
						<td>
							<c:choose>
								<c:when test="${balance.cusNoId eq null}">-</c:when>
								<c:otherwise>
									<input type="button" value="详情" onclick="queryCusBalanceDetail('${balance.cusNoId}');">
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
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/cusBalanceQueryInit"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>