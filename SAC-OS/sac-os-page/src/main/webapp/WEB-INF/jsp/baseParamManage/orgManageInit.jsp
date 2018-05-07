<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>客户参数查询</title>
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
			document.getElementById("sacType").value="";
			document.getElementById("isValidFlag").value="";
			document.getElementById("sacCurrency").value="";
		}
		
		function showDetail(id){
		    var url = "orgManageDetailInit";
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
					  $("#cusNoDetail").attr("value",data.cusNo);
					  $("#cusNameDetail").attr("value",data.cusName);
					  $("#cusPlatAccDetail").attr("value",data.cusPlatAcc);
					  $("#cusPlatAccNameDetail").attr("value",data.cusPlatAccName);
					  var bussType = "";
		               if(data.bussType == '20'){
		            	   bussType = "航付通";
		               }else if(data.bussType == '30'){
		            	   bussType = "跨境进口";
		               }else if(data.bussType == '40'){
		            	   bussType = "代收付";
		               }else if(data.bussType == '50'){
		            	   bussType = "外汇通";
		               }
					  $("#bussTypeDetail").attr("value",bussType);
					  $("#refundFlagDetail").attr("value",data.refundFlag);
					  $("#sacBankAccDetail").attr("value",data.sacBankAcc);
					  $("#accNameDetail").attr("value",data.accName);
					  $("#depositBankDetail").attr("value",data.depositBank);
					  $("#craccBankCodeDetail").attr("value",data.craccBankCode);
					  $("#feeRateDetail").attr("value",data.feeRate);
					  $("#feeComWayDetail").attr("value",data.feeComWay);
					  $("#feeSacWayDetail").attr("value",data.feeSacWay);
					  var sacType = "";
		               if(data.sacType == '1'){
		            	   sacType = "日";
		               }else if(data.sacType == '2'){
		            	   sacType = "星期";
		               }else if(data.sacType == '3'){
		            	   sacType = "月";
		               }
					  $("#sacTypeDetail").attr("value",sacType);
					  $("#sacPeriodDetail").attr("value",data.sacPeriod);
					  $("#intervalNumberDetail").attr("value",data.intervalNumber);
					  var ccy = data.sacCurrency;
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
					  $("#sacCurrencyDetail").attr("value",ccyName);
					  $("#sacStartAmountDetail").attr("value",data.sacStartAmount);
					  $("#trxUpLimDetail").attr("value",data.trxUpLim);
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
					  if(data.updateTime!=null){
		            	   $("#updateTimeDetail").attr("value",new Date(data.updateTime.time).format("yyyy-MM-dd hh:mm:ss"));
		               }
					  $("#orgCardIdDetail").attr("value",data.orgCardId);
					  $("#bussTypeDetail").attr("value",data.bussType);
				}	
			});
		}
		
		$(document).ready(function(event) {
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
			document.getElementById("orgDetailDiv").style.cssText="display:none";
			$("#layer").remove();
			$(".passtable").show();
			$(".passcon .left").hide();
			$(".passcon .right").hide();
			$('#spt1_show').val('');
		}
</script>

<style type="text/css">
.font {
	color: #1276bc;
}

#orgDetailDiv {
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
	overflow:auto;
	background-color: white;
}
.passtable{
	width:880px;
}
</style>
</head>
<body>
    <div class="content">
    <div class="con">
    	<form:form commandName="sacCusParameter" action="${ctx}/orgManageInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">客户号：</td>
								<td width="30%">
								<span> <form:input  
										path="cusNo" class="txt2" id="cusNo" /> </span>
										<span><form:errors path="cusNo" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">客户名称：</td>
								<td width="30%">
								<span> <form:input  
										path="cusName" class="txt2" id="cusName" /> </span>
										<span><form:errors path="cusName" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">客户平台账号：</td>
								<td width="30%">
								<span> <form:input  
										path="cusPlatAcc" class="txt2" id="cusPlatAcc" /> </span>
										<span><form:errors path="cusPlatAcc" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">结算周期类型：</td>
								<td width="30%">
									<select id="sacType" name="sacType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${settlementCycleList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCusParameter.sacType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">结算币种：</td>
								<td width="30%">
									<select id="sacCurrency" name="sacCurrency" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacCusParameter.sacCurrency}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">组织机构代码：</td>
								<td width="30%">
								<span> <form:input  
										path="orgCardId" class="txt2" id="orgCardId" /> </span>
										<span><form:errors path="orgCardId" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">有效标志：</td>
								<td width="30%">
									<select id="isValidFlag" name="isValidFlag" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${enableFlagList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCusParameter.isValidFlag}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
							</tr>
							
							
						</table>
					</div>
				</form:form>
		</div>
    	<div class="table fontcolor4 fontsize1 fontfamily2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                               		<th class="border">组织机构代码&nbsp;</th>
                                    <th class="border">客户号&nbsp;</th>
                                    <th class="border">客户名称&nbsp;</th>
                                    <th class="border">客户平台账号&nbsp;</th>
                                    <th class="border">结算币种&nbsp;</th>
                                    <th class="border">客户平台账户名称&nbsp;</th>
                                    <th class="border">结算银行账号&nbsp;</th>
                                    <th class="border">有效标志&nbsp;</th>
                                    <th class="border">业务类型&nbsp;</th>
                                    <th class="border">创建日期&nbsp;</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty sacCusParameterList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacCusParameterList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.orgCardId}</td>
                                    <td class="fontfamily1">${item.cusNo}</td>
                                    <td class="fontfamily1">${item.cusName}</td>
                                    <td class="fontfamily1">${item.cusPlatAcc}</td>
                                    <td class="fontfamily1">
                                    	<c:forEach items="${currencyList}" var="sys">
											<c:if test="${sys.dicCode eq item.sacCurrency}">${sys.dicDesc}</c:if>
										</c:forEach>
									</td>
                                    <td class="fontfamily1">${item.cusPlatAccName}</td>
                                    <td class="fontfamily1">${item.sacBankAcc}</td>
                                    <td class="fontfamily1"><c:if test="${item.isValidFlag eq '0'}">无效</c:if><c:if test="${item.isValidFlag eq '1'}">有效</c:if></td>
                                    <td class="fontfamily1">${item.bussType}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><input type="button" value="详细"
										onClick="showidTranaction('orgDetailDiv'); showDetail('${item.id}');">&nbsp;
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/orgManageInit"/>
									</td>
								</tr>
							</table>
						</div>
             </div>
	</div>
 <%@include file="/WEB-INF/jsp/baseParamManage/orgInfoDetail.jsp"%>
			 <div class="clear"></div>

<!-- /Body -->
</body>
</html>