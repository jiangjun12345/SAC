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
		<title>结算划款</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	/*对交易类型排序*/
	$('select[name="trxType"] option').sort(function(a,b){
		 return a.value - b.value;
	}).appendTo('select[name="trxType"]');
	var trxType = "${sacCusSettlement.trxType}";/*交易类型 */
	$('select[name="trxType"]').find("option[value="+trxType+"]").attr("selected",true);
});
function queryInfo(){
	var payAmount = document.getElementById("sacAmount").value;
	var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
	if(payAmount.length>0){
		var flag = payAmount.match(regexp);
		if(flag==null){
			document.getElementById("sacAmountError").innerHTML="格式非法";
			return;
		}
		if(payAmount.length>20){
			document.getElementById("sacAmountError").innerHTML="长度超长";
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
		document.getElementById("settleDetailDiv").style.cssText="display:none";
		$("#layer").remove();
		$(".passtable").show();
		$(".passcon .left").hide();
		$(".passcon .right").hide();
		$('#spt1_show').val('');
	}
	
	function clearText(){
		document.getElementById("trxType").value=""; 
		document.getElementById("currencyType").value=""; 
		document.getElementById("bussType").value=""; 
	}
	
	function wipe(id,ccy){
		if(window.confirm("确定划款？")){
			if(ccy=='CNY'){
				var url = "settleAllot";
				$.ajax( {
					url : url,
					cache : false,
					async : false,
					data : {
						id	 :	id
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if(data.success){
							alert("划款成功!");
							window.location.reload();
						}else{
							alert("划款失败!");
							window.location.reload();
						}
					},
					error : function(data){
						   alert("划款失败!");
					}
				});
			}else{
				showidTranaction('settleDetailDiv'); 
				settleAllotAddDetail();
			}
		}
	}
	
	function settleAllotAddDetail(){
	    var url = "settleAllotAddDetail";
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
			}	
		});
	}
	
	function addConfirm(){
		if(window.confirm("确定录入?")){
			//清空错误span
			var spanArr = document.getElementsByTagName('span');
			for(var i =0 ; i<spanArr.length;i++){
				var spanId = spanArr[i].id;
				if(spanId.indexOf('Error')>=0){
					document.getElementById(spanId).innerHTML="";
				}
			}
			var flag = true;
			var url = "settleConfirm";
			var craccCusCode = document.getElementById("craccCusCodeDetail").value;
			if(""==craccCusCode||null==craccCusCode){
				document.getElementById('craccCusCodeError').innerHTML="收款方客户号不能为空";
				return;
			}else if(craccCusCode.length!=19){
				document.getElementById('craccCusCodeError').innerHTML="收款方客户号长度定长19位";
				return;
			}
			var craccCusType = document.getElementById("craccCusTypeDetail").value;
			var craccIdentifyType = document.getElementById("craccIdentifyTypeDetail").value;
			var craccIdentifyCode = document.getElementById("craccIdentifyCodeDetail").value;
			if(""==craccIdentifyCode||null==craccIdentifyCode){
				document.getElementById('craccIdentifyCodeError').innerHTML="收款方证件号码不能为空";
				return;
			}
			var craccNo = document.getElementById("craccNoDetail").value;
			if(""==craccNo||null==craccNo){
				document.getElementById('craccNoError').innerHTML="收款方账号不能为空";
				return;
			}
			var craccName = document.getElementById("craccNameDetail").value;
			if(""==craccName||null==craccName){
				document.getElementById('craccNameError').innerHTML="收款方账户名称不能为空";
				return;
			}
			var craccNodeCode = document.getElementById("craccNodeCodeDetail").value;
			if(""==craccNodeCode||null==craccNodeCode){
				document.getElementById('craccNodeCodeError').innerHTML="银行节点代码不能为空";
				return;
			}
			var craccBankName = document.getElementById("craccBankNameDetail").value;
			if(""==craccBankName||null==craccBankName){
				document.getElementById('craccBankNameError').innerHTML="收款方银行名称不能为空";
				return;
			}
			var draccCusCode = document.getElementById("draccCusCodeDetail").value;
			if(""==draccCusCode||null==draccCusCode){
				document.getElementById('draccCusCodeError').innerHTML="付款方客户号不能为空";
				return;
			}else if(draccCusCode.length!=19){
				document.getElementById('draccCusCodeError').innerHTML="付款方客户号长度定长19位";
				return;
			}
			var draccCusType = document.getElementById("draccCusTypeDetail").value;
			var draccIdentifyType = document.getElementById("draccIdentifyTypeDetail").value;
			var draccIdentifyCode = document.getElementById("draccIdentifyCodeDetail").value;
			if(""==draccIdentifyCode||null==draccIdentifyCode){
				document.getElementById('draccIdentifyCodeError').innerHTML="付款方证件号码不能为空";
				return;
			}
			var draccNo = document.getElementById("draccNoDetail").value;
			if(""==draccNo||null==draccNo){
				document.getElementById('draccNoError').innerHTML="付款方账号不能为空";
				return;
			}
			var draccName = document.getElementById("draccNameDetail").value;
			if(""==draccName||null==draccName){
				document.getElementById('draccNameError').innerHTML="付款方账户名称不能为空";
				return;
			}
			var draccNodeCode = document.getElementById("draccNodeCodeDetail").value;
			if(""==draccNodeCode||null==draccNodeCode){
				document.getElementById('draccNodeCodeError').innerHTML="银行节点代码不能为空";
				return;
			}
			var draccBankName = document.getElementById("draccBankNameDetail").value;
			if(""==draccBankName||null==draccBankName){
				document.getElementById('draccBankNameError').innerHTML="银行名称不能为空";
				return;
			}
			var payCurrency = document.getElementById("payCurrencyDetail").value;
			var payAmount = document.getElementById("payAmountDetail").value;
			if(""==payAmount||null==payAmount){
				document.getElementById('payAmountError').innerHTML="支付金额不能为空";
				return;
			}
			
			var payWay = document.getElementById("payWayDetail").value;
			
			$.ajax( {
				url : url,
				cache : false,
				async : false,
				data : {
					craccCusCode : craccCusCode,
					craccCusType : craccCusType,
					craccIdentifyType : craccIdentifyType,
					craccIdentifyCode : craccIdentifyCode,
					draccCusCode : draccCusCode,
					draccCusType : draccCusType,
					draccIdentifyType : draccIdentifyType,
					draccIdentifyCode : draccIdentifyCode,
					craccNo : craccNo,
					craccName : craccName,
					craccNodeCode : craccNodeCode,
					craccBankName : craccBankName,
					draccNo : draccNo,
					draccName : draccName,
					draccNodeCode : draccNodeCode,
					draccBankName : draccBankName,
					payCurrency : payCurrency,
					payAmount : payAmount,
					payWay : payWay
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("录入成功,3秒后将自动刷新页面!");
						closeWin();
						setTimeout(window.location.reload(),3000)
					}else{
						if(data.valid){
							document.getElementById(data.filed[0].code+'Error').innerHTML=data.message;
						}else{
							alert("录入失败,3秒后将自动刷新页面!");
							closeWin();
							setTimeout(window.location.reload(),3000)
							//function(){$("#queryForm").submit()}
						}
						
					}
					
					
				},
				error : function(data){
							alert("录入失败!");
							closeWin();
				}
			});
		}
	} 
	
</script>
<style type="text/css">
.font {
	color: #1276bc;
}

#settleDetailDiv {
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
				<form:form id="subForm" commandName="sacCusSettlement" action="${ctx}/settleAllotInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">结算金额：</td>
								<td width="30%">
								<span> <form:input path = "sacAmount" class="txt2" id="sacAmount" /></span>
								<span id="sacAmountError" style="color: red;"></span>
                                <span><form:errors path="sacAmount" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">币种：</td>
								<td width="30%">
									<select id="currencyType" name="currencyType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacCusSettlement.currencyType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">业务类型：</td>
								<td width="30%">
									<select id="bussType" name="bussType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${bussTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCusSettlement.bussType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td width="12%" align="right">组织机构代码：</td>
								<td width="30%">
								<span> <form:input path = "cusNo" class="txt2" id="cusNo" /></span>
                                <span><form:errors path="cusNo" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">客户名称：</td>
								<td width="30%">
								<span> <form:input  
										path="cusName" class="txt2" id="cusName" /> </span>
										<span><form:errors path="cusName" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();"/>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">交易类型：</td>
								<td width="30%">
									<select id="trxType" name="trxType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${trxTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCusSettlement.trxType}"> selected="selected"</c:if>>${sys.dicDesc}(${sys.dicValue})</option>
										</c:forEach>
									</select>
								</td>
								<td width="12%" align="right">划账日期：</td>
								<td width="20%"><form:input type="text" name="sacDate" path="sacDate"
											id="sacDate" class="txt2" 
											onfocus="WdatePicker({errDealMode:0,dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'});" readonly="readonly"
											value="${queryForm.date}" style="width: 50%" /></td>
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
                                    <th class="border">收款方组织机构代码</th>
                                    <th class="border">收款方客户名称</th>
                                    <th class="border">币种</th>
                                    <th class="border">结算金额</th>
                                    <th class="border">总笔数</th>
                                    <th class="border">总金额</th>
                                    <th class="border">交易类型</th>
                                    <th class="border">业务类型</th>
                                    <th class="border">付款方银行名称</th>
                                    <th class="border">付款方客户名称</th>
                                    <!-- <th class="border">结算标志</th> -->
                                    <!-- <th class="border">勾对标志</th> -->
                                    <th class="border">结算划款状态</th>
                                    <th class="border">划账日期</th>
                                    <th class="border">创建日期</th>
                                </tr>
                                <c:if test="${empty sacCusSettlementList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacCusSettlementList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.cusNo}</td>
                                    <td class="fontfamily1">${item.cusName}</td>
                                    <td class="fontfamily1">
                                    	<c:forEach items="${currencyTypeList}" var="sys">
											<c:if test="${sys.dicCode eq item.currencyType}">${sys.dicDesc}</c:if>
										</c:forEach>
									</td>
									<td class="fontfamily1">${item.sacAmount}</td>
									<td class="fontfamily1">${item.totalNum}</td>
									<td class="fontfamily1">${item.totalAmount}</td>
									<td class="fontfamily1">${item.trxTypeDesc}</td>
									<td class="fontfamily1">${item.bussTypeDesc}</td>
                                    <td class="fontfamily1">${item.draccBankName}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1"><c:if test="${item.transferStatus eq 'Y'}">已划款</c:if><c:if test="${item.transferStatus eq 'N'}">未划款</c:if><c:if test="${item.transferStatus eq 'W'}">划款中</c:if></td>
                                    <td class="fontfamily1">${item.sacDate}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/settleAllotInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
<%@include file="/WEB-INF/jsp/fundManage/settleDetail.jsp"%>
			 <div class="clear"></div>


<!-- /Body -->
</body>
</html>