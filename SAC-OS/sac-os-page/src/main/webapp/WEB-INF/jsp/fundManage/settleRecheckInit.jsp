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
		<title>结算复核</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
function clearText(){
	document.getElementById("craccNodeCode").value="";
	document.getElementById("craccNo").value="";
	document.getElementById("draccNodeCode").value="";
	document.getElementById("draccNo").value="";
	document.getElementById("trxState").value="";
	document.getElementById("payCurrency").value="";
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


function closeWin2(){
	document.getElementById("recheckDetailDiv").style.cssText="display:none";
	$("#layer").remove();
	$(".passtable").show();
	$(".passcon .left").hide();
	$(".passcon .right").hide();
	$('#spt1_show').val('');
}


function queryDetail(id,trxState){
	if(trxState=='S'){
		document.getElementById('passClick').disabled=true;
	}
	document.getElementById('etrxSerialNoError').innerHTML="";
    var url = "querySettleDetailById";
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
			
			$("#craccCusCodeDetail2").attr("value",data.craccCusCode);
			$("#craccCusTypeDetail2").attr("value",data.craccCusType);
			$("#craccIdentifyTypeDetail2").attr("value",data.craccIdentifyType);
			$("#craccIdentifyCodeDetail2").attr("value",data.craccIdentifyCode);
			$("#draccCusCodeDetail2").attr("value",data.draccCusCode);
			$("#draccCusTypeDetail2").attr("value",data.draccCusType);
			$("#draccIdentifyTypeDetail2").attr("value",data.draccIdentifyType);
			$("#draccIdentifyCodeDetail2").attr("value",data.draccIdentifyCode);
			$("#craccNoDetail2").attr("value",data.craccNo);
			$("#craccNameDetail2").attr("value",data.craccName);
			$("#craccNodeCodeDetail2").attr("value",data.craccNodeCode);
			$("#craccBankNameDetail2").attr("value",data.craccBankName);
			$("#draccNoDetail2").attr("value",data.draccNo);
			$("#draccNameDetail2").attr("value",data.draccName);
			$("#draccNodeCodeDetail2").attr("value",data.draccNodeCode);
			$("#draccBankNameDetail2").attr("value",data.draccBankName);
			var ccy = data.payCurrency;
			var ccyName='';
			if(ccy=='01'){
				ccyName="人民币";
			}else if(ccy=='12'){
				ccyName="英镑";
			}else if(ccy=='13'){
				ccyName="港币";
			}else if(ccy=='14'){
				ccyName="美元";
			}else if(ccy=='15'){
				ccyName="瑞士法郎";
			}else if(ccy=='18'){
				ccyName="新加坡元";
			}else if(ccy=='27'){
				ccyName="日元";
			}else if(ccy=='28'){
				ccyName="加拿大元";
			}else if(ccy=='29'){
				ccyName="澳大利亚元";
			}else if(ccy=='38'){
				ccyName="欧元";
			}else{
				ccyName=ccy;
			}
			
			
			$("#payCurrencyDetail2").attr("value",ccyName);
			$("#payAmountDetail2").attr("value",data.payAmount);
			$("#etrxSerialNoDetail2").attr("value","");
			$("#trxStateDetail2").attr("value",data.trxState);
			$("#trxSerialNoDetail2").attr("value",data.trxSerialNo);
			if(data.trxState=='S'){
				document.getElementById('passClick').disabled=true;
				$("#etrxSerialNoDetail2").attr("value",data.etrxSerialNo);
			}
		}	
	});
}

function recheck(){
	if(window.confirm("确定复核?")){
		var etrxSerialNo = document.getElementById('etrxSerialNoDetail2').value;
		if(etrxSerialNo==null||etrxSerialNo==''){
			document.getElementById('etrxSerialNoError').innerHTML="请填写银行流水号后进行复核!";
			return;
		}
		var trxState = document.getElementById('trxStateDetail2').value;
		var trxSerialNo = document.getElementById('trxSerialNoDetail2').value;
	    var url = "settleRecheck";
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				trxSerialNo : trxSerialNo,
				trxState : trxState,	
				etrxSerialNo : etrxSerialNo,
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("复核成功,3秒后将自动刷新页面!");
					setTimeout(window.location.reload(),3000)
				}else{
					alert("复核失败,3秒后将自动刷新页面!");
					setTimeout(window.location.reload(),3000)
					//function(){$("#queryForm").submit()}
					
				}
				
				
			},
			error : function(data){
				   alert("复核失败!");
			}
		});
	}
}

	
</script>


<style type="text/css">
.font {
	color: #1276bc;
}

#recheckDetailDiv {
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

.passtitle{
	width:880px;
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
				<form:form commandName="sacOtrxInfo" action="${ctx}/settleRecheckInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">收款银行：</td>
								<td width="30%">
									<select id="craccNodeCode" name="craccNodeCode" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${sacChannelParamList}" var="sys">
											<option value="${sys.bankNodeCode}" <c:if test="${sys.bankNodeCode == sacOtrxInfo.craccNodeCode}"> selected="selected"</c:if>>${sys.sacBankName}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">收款账号：</td>
								<td width="30%">
									<select id="craccNo" name="craccNo" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${sacChannelParamList}" var="sys">
											<option value="${sys.bankAcc}" <c:if test="${sys.bankAcc == sacOtrxInfo.craccNo}"> selected="selected"</c:if>>${sys.bankAcc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">币种：</td>
								<td width="30%">
									<select id="payCurrency" name="payCurrency" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacOtrxInfo.payCurrency}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td width="12%" align="right">付款银行：</td>
								<td width="30%">
									<select id="draccNodeCode" name="draccNodeCode" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${sacChannelParamList}" var="sys">
											<option value="${sys.bankNodeCode}" <c:if test="${sys.bankNodeCode == sacOtrxInfo.draccNodeCode}"> selected="selected"</c:if>>${sys.sacBankName}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">付款账号：</td>
								<td width="30%">
									<select id="draccNo" name="draccNo" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${sacChannelParamList}" var="sys">
											<option value="${sys.bankAcc}" <c:if test="${sys.bankAcc == sacOtrxInfo.draccNo}"> selected="selected"</c:if>>${sys.bankAcc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">调拨日期：</td>
								<td width="30%"><form:input type="text" name="createTime" path="createTime"
											id="createTime" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}" style="width:96%" /></td>
							</tr>
							<tr>
								<td width="12%" align="right">调拨金额：</td>
								<td width="30%">
								<span> <form:input  
										path="payAmount" class="txt2" id="payAmount" /> </span>
										<span><form:errors path="payAmount" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">查询类型：</td>
								<td width="30%">
									<select id="trxState" name="trxState" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${transactionList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOtrxInfo.trxState}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
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
                                    <th class="border">付款账号</th>
                                    <th class="border">收款账号</th>
                                    <th class="border">支付渠道类型</th>
                                    <th class="border">支付方式</th>
                                    <th class="border">支付金额</th>
                                    <th class="border">币种</th>
                                    <th class="border">交易状态</th>
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
                                    <td class="fontfamily1">${item.draccNo}</td>
                                    <td class="fontfamily1">${item.craccNo}</td>
                                    <td class="fontfamily1"><c:if test="${item.payconType eq '1'}">B2B</c:if><c:if test="${item.payconType eq '2'}">B2C</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.payWay eq '1'}">快捷</c:if><c:if test="${item.payWay eq '2'}">网银</c:if></td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.payCurrency}</td>
                                    <td class="fontfamily1"><c:if test="${item.trxState eq 'N'}">待复核</c:if><c:if test="${item.trxState eq 'S'}">已复核</c:if></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><input type="button" value="复核"
										onClick="showidTranaction('recheckDetailDiv');queryDetail('${item.id}','${item.trxState}');">&nbsp;
										<%-- recheck('${item.trxState}','${item.trxSerialNo}'); --%>
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/settleRecheckInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
        <%@include file="/WEB-INF/jsp/fundManage/recheckDetail.jsp"%>
			 <div class="clear"></div>
    
<!-- /Body -->
</body>
</html>