<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript">
	$(function(){
		/* 点击查询时 */
		$("#query").click(function(){
			var startDate = $("#startDate").val();
			if(startDate==""){
				$("#validateDateMsg").html("起始日期不能为空! ");
				return false;
			}else{
				$("#refundTrxForm").submit();
			}
		});
		/* 点击下载按钮  */
		$("#download").click(function(){
			$("#refundTrxForm").attr("action","${ctx}/b2cRefundHandlingDownloadToExcel").submit();
			$("#refundTrxForm").attr("action","${ctx}/b2cRefundHandlingQuery");
		});
	});
	
	function clearText(){
		document.getElementById("refundSerialNo").value = "";
		document.getElementById("otrxSerialNo").value = "";
		document.getElementById("merchantName").value = "";
	}
	

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
	
	function batchWipe() {
		var box = document.getElementsByName("box");
		var count = 0;
		var boxValues = "";
		var firstBankName="";
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
		if (count > 0) {
			if (window.confirm("确定经办？")) {
				$("#refundTrxForm").attr("action","${ctx}/b2cRefundOperate?applyIds="+boxValues).submit();
			}
		}

	}
	Date.prototype.format =function(format)
	{
		var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
		}
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
		(this.getFullYear()+"").substr(4- RegExp.$1.length));
		for(var k in o)if(new RegExp("("+ k +")").test(format))
		format = format.replace(RegExp.$1,
		RegExp.$1.length==1? o[k] :
		("00"+ o[k]).substr((""+ o[k]).length));
		return format;
	}
	
	
	function showidTranaction(idname){	
		var isIE = (document.all) ? true : false;
		var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
		var newbox=document.getElementById(idname);
		newbox.style.zIndex="9999";
		newbox.style.display="block";
		newbox.style.position = !isIE6 ? "fixed" : "absolute";
		newbox.style.top =newbox.style.left = "50%";
		newbox.style.marginTop = - newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = - newbox.offsetWidth / 2 + "px";  
		var layer=document.createElement("div");
		layer.id="layer";
		layer.style.width=layer.style.height="100%";
		layer.style.position= !isIE6 ? "fixed" : "absolute";
		layer.style.top=layer.style.left=0;
		layer.style.backgroundColor="#000";
		layer.style.zIndex="9998";
		layer.style.opacity="0.6";
		layer.style.filter='alpha(opacity=60)';
		document.body.appendChild(layer);
		
		
		
		function layer_iestyle(){      
			layer.style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth)
			+ "px";
			layer.style.height= Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) +
			"px";
		}
		function newbox_iestyle(){      
			newbox.style.marginTop = document.documentElement.scrollTop - newbox.offsetHeight / 2 + "px";
			newbox.style.marginLeft = document.documentElement.scrollLeft - newbox.offsetWidth / 2 + "px";
		}
		
		
	}

	function closeWin(){
		document.getElementById("refundDetailDiv").style.cssText="display:none";
		$("#layer").remove();
		$(".passtable").show();
		$(".passcon .left").hide();
		$(".passcon .right").hide();
		$('#spt1_show').val('');
	}
	
	function showDetail(id){
	    var url = "b2cRefundDetail";
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				id:id
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				  $("#refundSerialNoDetail").attr("value",data.refundSerialNo);
				  $("#chnNameDetail").attr("value",data.recNcode);
				  $("#merchantNameDetail").attr("value",data.merchantName);
				  $("#otrxSerialNoDetail").attr("value",data.otrxSerialNo);
				  $("#payAmountDetail").attr("value",data.payAmount);
				  $("#crtCurrencyDetail").attr("value",data.crtCurrencyName);
				  $("#purchStateDetail").attr("value",data.purchStateName);
				  $("#rfPayAmountDetail").attr("value",data.rfPayAmount);
				  $("#remStateDetail").attr("value",data.remStateName);
				  $("#crtAmountDetail").attr("value",data.crtAmount);
				  $("#exstStateDetail").attr("value",data.exstState);
				  $("#refundAmountDetail").attr("value",data.refundAmount);
				  $("#applyStateDetail").attr("value",data.applyStateName);
				  $("#taxFlagDetail").attr("value",data.taxFlag);
				  $("#payCurrencyDetail").attr("value",data.payCurrencName);
				  $("#applyAmountDetail").attr("value",data.applyAmount);
				  $("#goodsAmountDetail").attr("value",data.goodsAmount);
				  $("#transAmountDetail").attr("value",data.transAmount);
				  $("#taxAmountDetail").attr("value",data.taxAmount);
				  if(data.refundTime!=null){
	            	   $("#refundTimeDetail").attr("value",new Date(data.refundTime.time).format("yyyy-MM-dd hh:mm:ss"));
	               }
				  $("#memoDetail").attr("value",data.memo);
				  if(data.operateTime!=null){
	            	   $("#operateTimeDetail").attr("value",new Date(data.operateTime.time).format("yyyy-MM-dd hh:mm:ss"));
	               }
				  $("#operatorIdDetail").attr("value",data.operatorId);
				  
				  if(data.auditTime!=null){
	            	   $("#auditTimeDetail").attr("value",new Date(data.auditTime.time).format("yyyy-MM-dd hh:mm:ss"));
	               }
				  $("#auditorIdDetail").attr("value",data.auditorId);
				  //$("#refundTypeDetail").attr("value",”线下退款);
				  if(data.refundOperTime!=null){
	            	   $("#refundOperTimeDetail").attr("value",new Date(data.refundOperTime.time).format("yyyy-MM-dd hh:mm:ss"));
	               }
				  $("#refundOperIdDetail").attr("value",data.refundOperId);
				  
			}	
		});
	}
</script>
<style type="text/css">
.font {
	color: #1276bc;
}

#refundDetailDiv {
	display: none;
	left: 10%;
	top: 10%;
	margin-left: -120px;
	margin-top: -140px;
	width: 900px;
	z-index: 1001;
	position: absolute;
	background-color: White;
	border: solid 1px #dddddd;
	padding: 0 0 10px;
	height: 650px;
	overflow: auto;
	background-color: white;
}

.passtable {
	width: 880px;
}
</style>
<title>B2C退款申请经办</title>
</head>

<body>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
	 		<form id="refundTrxForm" action="b2cRefundHandlingQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">退款日期(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
	 						</td>
	 						<td align="right">退款日期(止):</td>
	 						<td>
	 							<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
	 								onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
	 						</td>
		 					<td align="right">退款流水号：</td>
		 					<td><input type="text" class="txt2" id="refundSerialNo" name="refundSerialNo" value="${sacB2cExrefundApply.refundSerialNo}" /></td>
		 				</tr>
	 					<tr>
	 						<td align="right">原交易流水号：</td>
	 						<td><input type="text" class="txt2" id="otrxSerialNo" name="otrxSerialNo" value="${sacB2cExrefundApply.otrxSerialNo}" /></td>
	 						<td align="right">商户名称号：</td>
	 						<td><input type="text" class="txt2" id="merchantName" name="merchantName" value="${sacB2cExrefundApply.merchantName}" /></td>
	 					</tr>
	 					<tr>
	 						<td align="right">商户号：</td>
	 						<td><input type="text" class="txt2" name="recNcode" value="${sacB2cExrefundApply.recNcode}" /></td>
	 						<td colspan="2"><span id="validateDateMsg" style="color: red;"></span></td>
							<td align="right"><input id="query" class="bluebtn" type="button" value="查询" ></td>
							<td><input name="clearBtn" type="button" value="清除"
								class="bluebtn" id="clearBtn" onclick="clean();clearText();" />&nbsp;
								<input id="download" class="bluebtn" type="button" value="下载">
								<input name="batchWipeClick" type="button" value="勾选经办"
									class="bluebtn" id="batchWipeClick" onclick="batchWipe();" />
							</td>
	 					</tr>
	 					<tr>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<div width="500px" style="overflow:scroll;">
	 			<table width="1500px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
		 				<th class="border"><input id="checkall" type="checkbox"
						value="" onclick="checkboxall('checkall','box')" /> 全选</th>
		 				<th>原交易流水号</th>
						<th>商户名称</th>
						<th>支付金额(元)</th>
						<th>退款金额(元)</th>
						<th>购汇金额(元)</th>
						<th>购汇状态</th>
						<th>付汇状态</th>
						<th>退款提交日期</th>
						<th>退款状态</th>
						<th>操作</th>
		 			</tr>
		 			<c:if test="${empty commandList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="cmdDetail" items="${commandList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
			                <td><c:if test="${cmdDetail.applyState!='4'}">
									<input type="checkbox" name="box" id="checkbox${status.index}"
										value="${cmdDetail.exrefundApplyId}"/>
								</c:if>
							</td>
							<td class="fontfamily1">${cmdDetail.otrxSerialNo}</td>
							<td class="fontfamily1">${cmdDetail.merchantName}</td>
							<td class="fontfamily1">${cmdDetail.payAmount}</td>
							<td class="fontfamily1">${cmdDetail.applyAmount}</td>
							<td class="fontfamily1">${cmdDetail.frnAmount}</td>
							
							<td class="fontfamily1">${cmdDetail.purchStateName}</td>
							<td class="fontfamily1">${cmdDetail.remStateName}</td>
							<td class="fontfamily1">
								<p><fmt:formatDate value="${cmdDetail.refundTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
							</td>
							<td class="fontfamily1">${cmdDetail.applyStateName}</td>
							<td class="fontfamily1"><input type="button" value="详细"
							    onClick="showidTranaction('refundDetailDiv'); showDetail('${cmdDetail.exrefundApplyId}');">&nbsp;
						    </td>
							
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/b2cRefundHandlingQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
	<%@include file="/WEB-INF/jsp/refundManage/b2cRefundDetail.jsp"%>
	<div class="clear"></div>
</body>
</html>