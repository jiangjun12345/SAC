<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function queryCusBalanceDetail(data){
		var batchState = $("#batchState").val();
		var pageNo = ${pageNo};
		$.ajax({
			type:"POST",
			url:"b2bRefundBatchDetailQuery",
			async:false,
			data:{
				batchState:batchState,
				pageNo:pageNo,
				saveFlag:"Y"
			},
			success:function(){
				window.location.href = "b2bRefundBatchDetailQuery?oflWithdrawBatchId="+data;
			}
		});	
	}
	$(function(){
		var batchState = "${batchState}";/* 批次状态类型 */
		$('select[name="batchState"]').find("option[value="+batchState+"]").attr("selected",true);
	});
	
	
	//点击"全选/全不选"复选框，如果选中，则选中全部复选框，否则取消选中全部复选框  
	function checkboxall(Allname, name) {
		var ischecked = document.getElementById(Allname).checked;
		if (ischecked) {
			checkallbox(name);
		} else {
			discheckallbox(name);
		}
	}
	//选中全部复选框  
	function checkallbox(name) {
		var boxarray = document.getElementsByName(name);
		for ( var i = 0; i < boxarray.length; i++) {
			boxarray[i].checked = true;
		}
	}
	//取消选中全部复选框  
	function discheckallbox(name) {
		var boxarray = document.getElementsByName(name);
		for ( var i = 0; i < boxarray.length; i++) {
			boxarray[i].checked = false;
		}
	}
	
	function batchWipe(button) {
		var box = document.getElementsByName("box");
		var count = 0;
		var failFlag = false;
		var boxValues = "";
		for (i = 0; i < box.length; i++) {
			if (box[i].checked) {
				count++;
				var boxValue = box[i].value;
				boxValues = boxValues + boxValue + "|";
			}
		}
		if (count == 0) {
			alert("至少要选择一个，谢谢");
		}
		var batchState = $("#batchState").val();
		if (count > 0 && !failFlag) {
			if(button=='1'){
				if (window.confirm("是否确定通过？")) {
					$("#batchWipeClick1").attr("disabled",true);
					$("#batchWipeClick2").attr("disabled",true);
					$("#refundCheckForm").attr("action","${ctx}/reviewB2BRefundPass?wpIds="+boxValues+"&batchState="+batchState).submit();
				}
			 }else{
				 if (window.confirm("是否确定不通过？")) {
					$("#batchWipeClick1").attr("disabled",true);
					$("#batchWipeClick2").attr("disabled",true);
				    $("#refundCheckForm").attr("action","${ctx}/reviewB2BRefundReject?wpIds="+boxValues+"&batchState="+batchState).submit();
				 }
			 }
		}

	}
</script>
<title>银联B2B退款复核</title>
</head>

<body>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
	 		<form id="refundCheckForm" action="b2bRefundBatchQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">批次状态类型:</td>
	 						<td>
	 							<select id="batchState" class="select1"  name="batchState" width="100px" >
	 								<option value="" >全部</option>
									<c:forEach items="${batchSateMap}" var="batState">
										<option value="${batState.key}" >${batState.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 						<td>
	 							<input id="query" class="bluebtn" type="submit" value="查询">
	 							<input name="batchWipeClick1" type="button" value="确认通过"
								class="bluebtn" id="batchWipeClick1" onclick="batchWipe('1');" />
								<input name="batchWipeClick2" type="button" value="确认不通过"
								class="bluebtn" id="batchWipeClick2" onclick="batchWipe('2');" />
	 						</td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 			<tr height="35" bgcolor="#cccccc">
	 				<th class="border"><input id="checkall" type="checkbox"
						value="" onclick="checkboxall('checkall','box')" /> 全选</th>
	 				<th>批次流水号</th>
	 				<th>总笔数</th>
	 				<th>总金额</th>
					<th>创建时间</th>
					<th>批次复核状态</th>
					<th>批次经办人</th>
					<th>操作</th>
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
                     	<td><c:if test="${batch.batchState=='00'}">
									<input type="checkbox" name="box" id="checkbox${status.index}"
										value="${batch.oflWithdrawBatchId}"/>
							</c:if>
						</td>
                     	<td>${batch.batchSerialNo}</td>
                     	<td>${batch.batchTcount}</td>
                     	<td>${batch.batchTamount}</td>
                     	<td>
								<p><fmt:formatDate value="${batch.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
						</td>
						<td>${batch.batchStateName}</td>
						<td>${batch.operatorName}</td>
						<td>
							<input type="button" value="详情" onclick="queryCusBalanceDetail('${batch.oflWithdrawBatchId}');">
						</td>
                     </tr>
                </c:forEach>
	 		</table>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/b2bRefundBatchQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>