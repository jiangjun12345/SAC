<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>冲正</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	/*对交易类型排序*/
	$('select[name="trxType"] option').sort(function(a,b){
		 return a.value - b.value;
	}).appendTo('select[name="trxType"]');
	var trxType = "${sacOtrxInfo.trxType}";/*交易类型 */
	$('select[name="trxType"]').find("option[value="+trxType+"]").attr("selected",true);
}); 

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
	document.getElementById("trxType").value="";
	document.getElementById("bussType").value="";
	document.getElementById("trxState").value="";
	document.getElementById("payCurrency").value="";
}

function queryFundMsg(){
	var payAmount = document.getElementById("payAmount").value;
	var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
	if(payAmount.length>0){
		var flag = payAmount.match(regexp);
		if(flag==null){
			document.getElementById("payAmountError").innerHTML="格式非法";
			return;
		}
		if(payAmount.length>20){
			document.getElementById("payAmountError").innerHTML="长度超长";
			return;
		}
	}
	$("#subForm").submit();
	
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
	document.getElementById("reversalDetailDiv").style.cssText="display:none";
	$("#layer").remove();
	$(".passtable").show();
	$(".passcon .left").hide();
	$(".passcon .right").hide();
	$('#spt1_show').val('');
}




function queryDetailForReversal(id,reversalStatus,trxState){
	
	if(reversalStatus=='S'|| reversalStatus=='D'||trxState=='N'){
		document.getElementById('confirmClick').disabled=true;
	}else{
		document.getElementById('confirmClick').disabled=false;
	}
    var url = "reversalDetailQuery";
	$.ajax( {
		url : url,
		cache : false,
		async : false,
		data : {
			id : id
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			
			$("#trxSerialNoDetail").attr("value",data.trxSerialNo);
			$("#etrxSerialNoDetail").attr("value",data.etrxSerialNo);
			$("#platBillNoDetail").attr("value",data.platBillNo);
			$("#cusBillNoDetail").attr("value",data.cusBillNo);
			$("#craccNoDetail").attr("value",data.craccNo);
			$("#craccNameDetail").attr("value",data.craccName);
			$("#craccCusCodeDetail").attr("value",data.craccCusCode);
			$("#craccCusNameDetail").attr("value",data.craccCusName);
			$("#craccBankNameDetail").attr("value",data.craccBankName);
			$("#draccNoDetail").attr("value",data.draccNo);
			$("#draccNameDetail").attr("value",data.draccName);
			$("#draccCusCodeDetail").attr("value",data.draccCusCode);
			$("#draccCusNameDetail").attr("value",data.draccCusName);
			$("#draccBankNameDetail").attr("value",data.draccBankName);
			
			$("#payCurrencyDetail").attr("value",data.payCurrency);
			$("#payAmountDetail").attr("value",data.payAmount);
			$("#sacCurrencyDetail").attr("value",data.sacCurrency);
			$("#sacAmountDetail").attr("value",data.sacAmount);
			$("#transportExpensesDetail").attr("value",data.transportExpenses);
			$("#taxAmountDetail").attr("value",data.taxAmount);
			$("#trxStateDetail").attr("value",data.trxState);
			$("#accountStatusDetail").attr("value",data.accountStatus);
			$("#reversalStatusDetail").attr("value",data.reversalStatus);
			$("#bussTypeDetail").attr("value",data.bussType);
			$("#trxTypeDetail").attr("value",data.trxType);
			if(data.createTime!=null){
	         	 $("#createTimeDetail").attr("value",new Date(data.createTime.time).format("yyyy-MM-dd hh:mm:ss"));
	        }
		}	
	});
}

function reversalConfirm(){
	if(window.confirm("确定冲正?")){
		var trxSerialNo = document.getElementById('trxSerialNoDetail').value;
	    var url = "reversalConfirmInit";
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				trxSerialNo : trxSerialNo
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("冲正成功,请进行审核!");
					setTimeout(window.location.reload(),3000)
				}else{
					alert("冲正失败!");
				}
				
				
			},
			error : function(data){
				   alert("冲正失败!");
			}
		});
	}
}

	
</script>

<style type="text/css">
.font {
	color: #1276bc;
}

#reversalDetailDiv {
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
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form id="subForm" commandName="sacOtrxInfo" action="${ctx}/reversalDealInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">交易流水号：</td>
								<td width="30%">
								<span> <form:input  
										path="trxSerialNo" class="txt2" id="trxSerialNo" /> </span>
										<span id="trxSerialNoError" style="color: red;"></span>
								</td>
								<td width="10%" align="right">交易类型：</td>
								<td width="25%">
									<select id="trxType" name="trxType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${trxTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOtrxInfo.trxType}"> selected="selected"</c:if>>${sys.dicDesc}(${sys.dicValue})</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">业务类型：</td>
								<td width="25%">
									<select id="bussType" name="bussType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${bussTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOtrxInfo.bussType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								
							</tr>
							<tr>
								<td width="12%" align="right">交易金额：</td>
								<td width="30%">
								<span> <form:input  
										path="payAmount" class="txt2" id="payAmount" /> </span>
										<span id="payAmountError" style="color: red;"></span>
								</td>
								<td width="10%" align="right">币种：</td>
								<td width="25%">
									<select id="payCurrency" name="payCurrency" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacOtrxInfo.payCurrency}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryFundMsg();"/>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">交易日期：</td>
								<td width="30%"><form:input type="text" name="createTime" path="createTime"
											id="createTime" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}" style="width:62%" /></td>
								<td width="10%" align="right">交易状态：</td>
								<td width="30%">
									<select id="trxState" name="trxState" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${trxStateList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOtrxInfo.trxState}"> selected="selected"</c:if>><c:if test="${sys.dicValue == 'N'}"> 待支付</c:if><c:if test="${sys.dicValue == 'S'}"> 支付成功</c:if></option>
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
                                    <th class="border">交易流水号</th>
                                    <th class="border">收款账户名称</th>
                                    <th class="border">付款账户名称</th>
                                    <th class="border">支付金额</th>
                                    <th class="border">支付币种</th>
                                    <th class="border">业务类型</th>
                                    <th class="border">交易类型</th>
                                    <th class="border">交易状态</th>
                                    <th class="border">状态</th>
                                    <th class="border">创建时间</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty sacOtrxInfoList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacOtrxInfoList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.trxSerialNo}</td>
                                    <td class="fontfamily1">${item.craccName}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">
                                    	<c:forEach items="${currencyList}" var="sys">
											<c:if test="${sys.dicCode eq item.payCurrency}">${sys.dicDesc}</c:if>
										</c:forEach>
									</td>
                                    <td class="fontfamily1">${item.bussType}</td>
                                    <td class="fontfamily1">${item.trxType}</td>
                                    <td class="fontfamily1"><c:if test="${item.trxState eq 'N'}">待支付</c:if><c:if test="${item.trxState eq 'S'}">支付成功</c:if><c:if test="${item.trxState eq 'F'}">支付失败</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.reversalStatus eq 'N'}">未冲正</c:if><c:if test="${item.reversalStatus eq 'D'}">未审核</c:if><c:if test="${item.reversalStatus eq 'S'}">已冲正</c:if></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1">
	                                   <c:choose>
	                                     <c:when test="${item.reversalStatus eq 'N' && item.trxState eq 'S'}">
	                                     <input type="button" id="reversalButton" value="冲正"
										  onClick="showidTranaction('reversalDetailDiv');queryDetailForReversal('${item.id}','${item.reversalStatus}','${item.trxState}');">&nbsp;
										<%-- recheck('${item.trxState}','${item.trxSerialNo}'); --%>
	                                     </c:when>
	                                     <c:otherwise>
	                                     <input type="button" id="reversalButton" value="详情"
										onClick="showidTranaction('reversalDetailDiv');queryDetailForReversal('${item.id}','${item.reversalStatus}','${item.trxState}');">&nbsp;
										<%-- recheck('${item.trxState}','${item.trxSerialNo}'); --%>
	                                     </c:otherwise>
	                                   </c:choose>
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/reversalDealInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
        <%@include file="/WEB-INF/jsp/fundManage/reversalDetail.jsp"%>
			 <div class="clear"></div>
    
<!-- /Body -->
</body>
</html>