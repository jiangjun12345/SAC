<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>渠道参数查询</title>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript">		
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
		function clearText(){
			document.getElementById("isValidFlag").value="";
			document.getElementById("currencyType").value="";
		}
		
		function showDetail(id){
		    var url = "channelManageDetailInit";
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
					  $("#chnNoDetail").attr("value",data.chnNo);
					  $("#chnNameDetail").attr("value",data.chnName);
					  $("#bankNodeCodeDetail").attr("value",data.bankNodeCode);
					  $("#chnCodeDetail").attr("value",data.chnCode);
					  $("#sacBankNameDetail").attr("value",data.sacBankName);
					  $("#accountNameDetail").attr("value",data.accountName);
					  $("#craccBankCodeDetail").attr("value",data.craccBankCode);
					  var ccy = data.currencyType;
					  var ccyName='';
					  if(ccy=='CNY'){
							ccyName="人民币";
						}else if(ccy=='GBP'){
							ccyName="英镑";
						}else if(ccy=='HKD'){
							ccyName="港币";
						}else if(ccy=='USD'){
							ccyName="美元";
						}else if(ccy=='CHF'){
							ccyName="瑞士法郎";
						}else if(ccy=='SGD'){
							ccyName="新加坡元";
						}else if(ccy=='JPY'){
							ccyName="日元";
						}else if(ccy=='CAD'){
							ccyName="加拿大元";
						}else if(ccy=='AUD'){
							ccyName="澳大利亚元";
						}else if(ccy=='EUR'){
							ccyName="欧元";
						}else{
							ccyName=ccy;
						}
					  $("#currencyTypeDetail").attr("value",ccyName);
					  $("#sacPeriodDetail").attr("value",data.sacPeriod);
					  $("#costRateDetail").attr("value",data.costRate);
					  $("#costComWayDetail").attr("value",data.costComWay);
					  $("#costSacWayDetail").attr("value",data.costSacWay);
					  var isValidFlag = "";
		               if(data.isValidFlag == '0'){
		            	   isValidFlag = "无效";
		               }else if(data.isValidFlag == '1'){
		            	   isValidFlag = "有效";
		               }
					  $("#isValidFlagDetail").attr("value",isValidFlag);
					  if(data.createTime!=null){
		            	   $("#createTimeDetail").attr("value",new Date(data.createTime.time).format("yyyy-MM-dd hh:mm:ss"));
		               }
					  $("#memoDetail").attr("value",data.memo);
					  if(data.createTime!=null){
		            	   $("#createTimeDetail").attr("value",new Date(data.createTime.time).format("yyyy-MM-dd hh:mm:ss"));
		               }
					  if(data.updateTime!=null){
		            	   $("#updateTimeDetail").attr("value",new Date(data.updateTime.time).format("yyyy-MM-dd hh:mm:ss"));
		               }
				}	
			});
		}
		
		$(document).ready(function(event) {
		    var chnType = "${sacChannelParam.chnType}";
		    $("#chnType").find("option[value="+chnType+"]").attr("selected",true);
			$('.arrow').click(function (event) {
				$(".adminpop").toggle();
			});
		    
		});

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
			document.getElementById("channelDetailDiv").style.cssText="display:none";
			$("#layer").remove();
			$(".passtable").show();
			$(".passcon .left").hide();
			$(".passcon .right").hide();
			$('#spt1_show').val('');
		}
</script>
<script type="text/javascript">
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, //month
			"d+" : this.getDate(), //day
			"h+" : this.get
</script>
<style type="text/css">
.font {
	color: #1276bc;
}

#channelDetailDiv {
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
</head>
<body>
	<div class="content">
		<div class="con">
			<form:form commandName="sacChannelParam"
				action="${ctx}/channelManageInit" method="POST">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width="10%" align="right">渠道名称：</td>
							<td width="30%"><span> <form:input path="chnName"
										class="txt2" id="chnName" /> </span> <span><form:errors
										path="chnName" cssStyle="color:red" /> </span>
							</td>
							<td width="12%" align="right">渠道节点代码：</td>
							<td width="30%"><span> <form:input path="chnCode"
										class="txt2" id="chnCode" /> </span> <span><form:errors
										path="chnCode" cssStyle="color:red" /> </span>
							</td>
							<td width="10%" align="right">渠道类型：</td>
							<td width="30%"><select id="chnType" name="chnType"
								class="select1">
									<option value="">全部</option>
									<option value="1">B2B支付</option>
									<option value="2">B2C支付</option>
									<option value="3">存款银行</option>
							</select>
							</td>
						</tr>
						<tr>
							<td width="10%" align="right">清算行名称：</td>
							<td width="30%"><span> <form:input path="sacBankName"
										class="txt2" id="sacBankName" /> </span> <span><form:errors
										path="sacBankName" cssStyle="color:red" /> </span>
							</td>
							<td width="10%" align="right">清算行节点代码：</td>
							<td width="30%"><span> <form:input
										path="bankNodeCode" class="txt2" id="bankNodeCode" /> </span> <span><form:errors
										path="bankNodeCode" cssStyle="color:red" /> </span>
							</td>
						</tr>
						<tr>
							<td width="10%" align="right">币种：</td>
							<td width="30%"><select id="currencyType"
								name="currencyType" class="select1">
									<option value="">全部</option>
									<c:forEach items="${currencyList}" var="sys">
										<option value="${sys.dicCode}"
											<c:if test="${sys.dicCode == sacChannelParam.currencyType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
									</c:forEach>
							</select>
							</td>
							<td width="10%" align="right">有效标志：</td>
							<td width="30%"><select id="isValidFlag" name="isValidFlag"
								class="select1">
									<option value="">全部</option>
									<c:forEach items="${enableFlagList}" var="sys">
										<option value="${sys.dicValue}"
											<c:if test="${sys.dicValue == sacChannelParam.isValidFlag}"> selected="selected"</c:if>>${sys.dicDesc}</option>
									</c:forEach>
							</select>
							</td>
							<td width="10%" align="right"><input name="submitbtn"
								type="submit" value="查询" class="bluebtn" id="submitbtn" /></td>
							<td width="15%" align="left"><input name="clearBtn"
								type="button" value="清除" class="bluebtn" id="clearBtn"
								onclick="clean();clearText();" /></td>
						</tr>
					</table>
				</div>
			</form:form>
		</div>
		<div class="table fontcolor4 fontsize1 fontfamily2">
			<div width="500px" style="overflow: scroll;">
				<table width="1200px" border="0" cellpadding="0" cellspacing="0">
					<tr height="35" bgcolor="#cccccc">
						<th class="border">渠道号</th>
						<th class="border">渠道名称</th>
						<th class="border">渠道节点代码</th>
						<th class="border">渠道类型</th>
						<th class="border">币种</th>
						<th class="border">清算行节点代码</th>
						<th class="border">银行科目代码</th>
						<th class="border">清算行名称</th>
						<th class="border">开户名称</th>
						<th class="border">银行账号</th>
						<th class="border">有效标志</th>
						<th class="border">创建日期</th>
						<th class="border">操作</th>
					</tr>
					<c:if test="${empty sacChannelParamList }">
						<tr>
							<td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
						</tr>
					</c:if>
					<c:forEach items="${sacChannelParamList}" var="item" varStatus="st">
						<c:choose>
							<c:when test="${st.index %2 == 0 }">
								<tr align="center" height="35">
							</c:when>
							<c:otherwise>
								<tr align="center" height="35" bgcolor="#eeeeee">
							</c:otherwise>
						</c:choose>
						<td class="fontfamily1">${item.chnNo}</td>
						<td class="fontfamily1">${item.chnName}</td>
						<td class="fontfamily1">${item.chnCode}</td>
						<td class="fontfamily1"><c:if test="${item.chnType eq '1'}">B2B支付</c:if>
							<c:if test="${item.chnType eq '2'}">B2C支付</c:if> <c:if
								test="${item.chnType eq '3'}">存款银行</c:if>
						</td>
						<td class="fontfamily1">
							<c:forEach items="${currencyList}" var="sys">
								<c:if test="${sys.dicCode eq item.currencyType}">${sys.dicDesc}</c:if>
							</c:forEach>
						</td>
						<td class="fontfamily1">${item.bankNodeCode}</td>
						<td class="fontfamily1">${item.chnCode}</td>
						<td class="fontfamily1">${item.sacBankName}</td>
						<td class="fontfamily1">${item.accountName}</td>
						<td class="fontfamily1">${item.bankAcc}</td>
						<td class="fontfamily1"><c:if
								test="${item.isValidFlag eq '0'}">无效</c:if> <c:if
								test="${item.isValidFlag eq '1'}">有效</c:if></td>
						<td class="fontfamily1"><fmt:formatDate
								value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						<td class="fontfamily1"><input type="button" value="详细"
							onClick="showidTranaction('channelDetailDiv'); showDetail('${item.id}');">&nbsp;
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div style="width: 100%; height: 32px; text-align: right;"
				id="pageDiv" class="pagination1 btn">
				<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24"><easipay:pageNum
								pageSize="${pageSize}" count="${count}" pageNo="${pageNo}"
								url="/channelManageInit" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/jsp/baseParamManage/channelDetail.jsp"%>
	<div class="clear"></div>

	<!-- /Body -->
</body>
</html>