<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx}/scripts/js/jquery/jquery-ui.min.js"></script>
<link href="${ctx}/styles/jquery-ui.min.css" type="text/css" rel="stylesheet" id="jqueryId"/>
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	/* 下拉选项默认赋值  */
	var recType = "${recType}";/* 对账类型   */
	var payconType = "${payconType}";/* 支付渠道类型   */
	var chnCode = "${chnCode}";/* 支付渠道名称   */
	var recStatus = "${recStatus}";/* 对账状态   */
	changeRecType(recType,payconType);
	$('select[name="chnCode"]').attr("disabled",false).find("option[value="+chnCode+"][title="+payconType+"]").attr("selected",true);
	$('select[name="recType"]').find("option[value="+recType+"]").attr("selected",true);
	$('select[name="payconType"]').find("option[value="+payconType+"]").attr("selected",true);
	$('select[name="recStatus"]').find("option[value="+recStatus+"]").attr("selected",true);
	
	/*改变对账类型时*/
	$("#recType").change(function(){
		var recType = $("#recType").val();
		var payconType = $("#payconType").val();
		changeRecType(recType,payconType)
	});
	
	/*改变支付渠道类型时*/
	$("#payconType").change(function(){
		var payconType = $("#payconType").val();
		changePayconType(payconType);
	});
	
	/* 点击查询时 */
	$("#queryAccResult").click(queryForm);
	
});

//改变对账类型
function changeRecType(recType,payconType){
	if(recType=='1'){ // 内部对账
		$('select[name="chnCode"]').attr("disabled",true).find("option[value='']").attr("selected",true);
	}else{// 非内部对账
		$('select[name="chnCode"]').attr("disabled",false).find("option[value='']").attr("selected",true);
	}
	changePayconType(payconType);
}

//改变支付渠道类型
function changePayconType(payconType){
	if(payconType=='1'){ // B2B
		$('select[name="chnCode"]').find("option[title='1']").show();
		$('select[name="chnCode"]').find("option[title='2']").hide();
	}else if(payconType=='2'){// B2C
		$('select[name="chnCode"]').find("option[title='1']").hide();
		$('select[name="chnCode"]').find("option[title='2']").show();
	}else{
		$('select[name="chnCode"]').find("option[title='1']").show();
		$('select[name="chnCode"]').find("option[title='2']").show();
	}
}

//查询
function queryForm(){
	var startDate = $("#startDate").val();
	if(startDate==""){
		alert("起始日期不能为空!");
		return false;
	}else{
		$("#accResultForm").submit();
	}
}
	
//上传文件
function toUploadPage(chnCode,recDate){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var queryChnCode = $("#chnCode").val();
	var recType = $("#recType").val();
	var payconType = $("#payconType").val();
	var recStatus = $("#recStatus").val();
	var pageNo = ${pageNo};
	$.ajax({
		type:"POST",
		url:"uploadCheckAccFileInit",
		async:false,
		data:{
			startDate:startDate,
			endDate:endDate,
			queryChnCode:queryChnCode,
			recType:recType,
			payconType:payconType,
			recStatus:recStatus,
			pageNo:pageNo,
			saveFlag:"Y",
			uploadFlag:"Y"
		},
		success:function(){
			window.location.href = "uploadCheckAccFileInit?chnCode="+chnCode+"&trxDate="+recDate;
		}
	});	
}

//展示批次明细
function batchDetailShow(recBatchId){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var chnCode = $("#chnCode").val();
	var recType = $("#recType").val();
	var payconType = $("#payconType").val();
	var recStatus = $("#recStatus").val();
	var pageNo = ${pageNo};
	$.ajax({
		type:"POST",
		url:"queryBatchDetail",
		async:false,
		data:{
			startDate:startDate,
			endDate:endDate,
			chnCode:chnCode,
			recType:recType,
			payconType:payconType,
			pageNo:pageNo,
			recStatus:recStatus,
			saveFlag:"Y"
		},
		success:function(){
			window.location.href = "queryBatchDetail?recBatchId="+recBatchId;
		}
	});	
}
//展示差错明细
function diffDetailShow(recBatchId){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var chnCode = $("#chnCode").val();
	var recType = $("#recType").val();
	var payconType = $("#payconType").val();
	var recStatus = $("#recStatus").val();
	var pageNo = ${pageNo};
	$.ajax({
		type:"POST",
		url:"queryDiffDetail",
		async:false,
		data:{
			startDate:startDate,
			endDate:endDate,
			chnCode:chnCode,
			recType:recType,
			payconType:payconType,
			pageNo:pageNo,
			recStatus:recStatus,
			saveFlag:"Y"
		},
		success:function(){
			window.location.href = "queryDiffDetail?recBatchId="+recBatchId;
		}
	});	
}
</script>
<style type="text/css">
.filebtn{
	width:70px;
	height:20px;
	margin-left:0px;
	border:0px solid #cccccc;
	background-color:#cccccc;
	text-align:center;
	color:#666666;
	font-family:"微软雅黑";
	cursor:pointer;
}
</style>
<title>对账结果查询</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="accResultForm" action="checkAccResult" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">交易日期(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-6})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
	 						</td>
	 						<td align="right">交易日期(止):</td>
	 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:6})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
	 						</td>
	 						<td align="right">支付渠道名称:</td>
	 						<td>
	 							<select name="chnCode" class="select1" width="100" id="chnCode" >
									<option value="" title="">全部</option>
									<c:forEach items="${recFileParamList}" var="channel">
										<option value="${channel.chnCode}" title="${channel.payconType}">${channel.chnName}</option>
									</c:forEach>
								</select>
	 						</td>
	 					</tr>
	 					<tr>
		 					<td align="right">对账类型:</td>
	 						<td>
	 							<select name="recType" class="select1" width="100" id="recType" >
									<option value="" >全部</option>
									<option value="1" >交易对账</option>
									<option value="2" >渠道对账</option>
									<option value="3" >渠道差错对账</option>
								</select>
	 						</td>
	 						<td align="right">支付渠道类型:</td>
	 						<td>
	 							<select name="payconType" class="select1" width="100" id="payconType" >
									<option value="" >全部</option>
									<option value="1" >B2B</option>
									<option value="2" >B2C</option>
								</select>
	 						</td>
	 						<td align="right">对账状态:</td>
	 						<td>
	 							<select name="recStatus" class="select1" width="100" id="recStatus" >
									<option value="" >全部</option>
									<option value="0" >未对账</option>
									<option value="1" >已对账</option>
								</select>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">操作员:</td>
	 						<td>
	 							<input id="recOper" name="recOper" class="txt2" value="${recOper}"/>
	 						</td>
	 						<td><input id="queryAccResult" class="bluebtn" type="button" value="查询" /></td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2" >
	 		<div width="800px" style="overflow:scroll;">
	 			<table width="1500px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
						<th>查询日期</th>
						<th>对账类型</th>
						<th>支付渠道类型</th>
						<th>支付渠道名称</th>
						<th>操作</th>
						<th>是否人工上传</th>
						<th>批次号</th>
						<th>对账状态</th>
						<th>对账时间</th>
						<th>总笔数</th>
						<th>单边笔数</th>
						<th>单边未处理笔数</th>
						<th>上传时间</th>
						<th>交易日期(起)</th>
						<th>交易日期(止)</th>
						<th>操作员</th>
		 			</tr>
		 			<c:if test="${empty checkAccResultList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="checkAccResult" items="${checkAccResultList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
	                     	<td class="fontfamily1">${checkAccResult.queryDate}</td>
					      	<td class="fontfamily1">
					      		<c:if test="${checkAccResult.recType eq 1}">交易对账</c:if>
								<c:if test="${checkAccResult.recType eq 2}">渠道对账</c:if>
								<c:if test="${checkAccResult.recType eq 3}">渠道差错对账</c:if>
					      	</td>
					      	<td class="fontfamily1">${checkAccResult.payconType eq 1?"B2B":"B2C"}</td>
					      	<td class="fontfamily1">${checkAccResult.chnName eq null?"-":checkAccResult.chnName}</td>
					      	<td class="fontfamily1">
					      		<c:if test="${checkAccResult.diffCount > 0}"><!-- 差错笔数不为0，差错详情 -->
					      			<input type="button" class="filebtn" value="差错详情" onclick="diffDetailShow('${checkAccResult.recBatchId}');"><br>
								</c:if>
								<c:if test="${checkAccResult.recBatchId ne null and checkAccResult.recCount > 0}"><!-- 有批次且总数不为0，批次详情 -->
									<input type="button" class="filebtn" value="批次详情" onclick="batchDetailShow('${checkAccResult.recBatchId}');">
								</c:if>
								<c:if test="${checkAccResult.recBatchId eq null}"><!-- 批次号为空 -->
					      			<c:if test="${checkAccResult.recType eq 2 and checkAccResult.payconType eq 2}"><!-- 且是B2C渠道对账 -->
										<input type="button" class="filebtn" value="上传" onclick="toUploadPage('${checkAccResult.chnCode}','${checkAccResult.queryDate}');">
									</c:if>
					      		</c:if>
							</td>
							<td class="fontfamily1">${checkAccResult.recFlag eq 'N'?"否":"是"}</td>
					      	<td class="fontfamily1">${checkAccResult.recBatchId}</td>
					      	<td class="fontfamily1">
								<c:if test="${checkAccResult.recStatus eq 0}">未对账</c:if>
								<c:if test="${checkAccResult.recStatus eq 1 or checkAccResult.recStatus eq 2}">已对账</c:if>
								<%-- <c:if test="${checkAccResult.recStatus eq 1}">已对账</c:if>
								<c:if test="${checkAccResult.recStatus eq 2}">对账中</c:if> --%>
							</td>
							<td class="fontfamily1">${checkAccResult.recStatus ne 0?checkAccResult.updateTime:""}</td>
					      	<td class="fontfamily1">${checkAccResult.recCount}</td>
							<td class="fontfamily1">${checkAccResult.diffCount}</td>
					      	<td class="fontfamily1">${checkAccResult.diffCount-checkAccResult.handleCount}</td>
					      	<td class="fontfamily1">${checkAccResult.createTime}</td>
					      	<td class="fontfamily1">${checkAccResult.recStartDate}</td>
					      	<td class="fontfamily1">${checkAccResult.recEndDate}</td>
					      	<td class="fontfamily1">${checkAccResult.recOper}</td>
					      	
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/checkAccResult"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>