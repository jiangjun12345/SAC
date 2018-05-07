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
		<title>退款处理</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	
	function queryInfo(){
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
		$("#queryForm").submit();
		
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

	function clearText(){
		queryForm.trxState.value=""; 
		queryForm.confirmStatus.value=""; 
		queryForm.payCurrency.value=""; 
	}
	
	$(document).ready(function(event) {
	    $('.arrow').click(function (event) {
			$(".adminpop").toggle();
		});
	});

	function showidTranaction(idname ,billNo){	
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
	function refundConfirm(flag){
		/* var memo = document.getElementById('wordsContent').value;
		if(flag=='N'){
			if (memo.replace(/(^\s*)|(\s*$)/g,'') == "") {
				alert("请将拒绝理由写入备注!");
				return;
			}
		} */
		if(window.confirm("确定?")){
			var url = "refundConfirm";
			var id = document.getElementById("idDetail").value;
			$.ajax( {
				url : url,
				cache : false,
				async : false,
				data : {
					flag : flag,
					id	 :	id
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("审核成功!");
						closeWin();
						window.location.reload();
					}else{
						alert("审核失败!");
						closeWin();
						window.location.reload();
					}
					
					
				},
				error : function(data){
					   alert("审核失败!");
				}
			});
		}
	}
	

	function showDetail(id){
	    var url = "showRefundDetail";
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
			   $("#idDetail").attr("value",id);
	           $("#trxSerialNoDetail").attr("value",data.trxSerialNo);
	           $("#otrxSerialNoDetail").attr("value",data.otrxSerialNo);
	           var state = "";
               if(data.trxState == 'N'){
            	   state = "待支付";
               }else if(data.trxState == 'S'){
            	   state = "交易成功";
               } 
               var confirmState = "";
               if(data.confirmStatus == 'I'){
            	   confirmState = "未审核";
               }else if(data.confirmStatus == 'N'){
            	   confirmState = "审核未通过";
               }else if(data.confirmStatus == 'P'){
            	   confirmState = "审核通过";
               }
	           $("#trxStateDetail").attr("value",state);
	           $("#confirmStatusDetail").attr("value",confirmState);
	           
               if(data.createTime!=null){
            	   $("#createTimeDetail").attr("value",new Date(data.createTime.time).format("yyyy-MM-dd hh:mm:ss"));
               }
	           if(data.confirmTime!=null){
            	   $("#confirmTimeDetail").attr("value",new Date(data.confirmTime.time).format("yyyy-MM-dd hh:mm:ss"));
               }
	           
           		$("#craccCusCodeDetail").attr("value",data.craccCusCode);
				$("#craccCusTypeDetail").attr("value",data.craccCusType);
				$("#craccNoDetail").attr("value",data.craccNo);
				$("#craccNameDetail").attr("value",data.craccName);
				$("#craccNodeCodeDetail").attr("value",data.craccNodeCode);
				$("#craccBankNameDetail").attr("value",data.craccBankName);
				$("#draccCusCodeDetail").attr("value",data.draccCusCode);
				$("#draccCusTypeDetail").attr("value",data.draccCusType);
				$("#draccNoDetail").attr("value",data.draccNo);
				$("#draccNameDetail").attr("value",data.draccName);
				$("#draccNodeCodeDetail").attr("value",data.draccNodeCode);
				$("#draccBankNameDetail").attr("value",data.draccBankName);
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
				
				
			   $("#payCurrencyDetail").attr("value",ccyName);
	           $("#payAmountDetail").attr("value",data.payAmount);
	           $("#confirmUserDetail").attr("value",data.confirmUser);
	           $("#bussTypeDetail").attr("value",data.bussType);
	           
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
				<form:form commandName="sacOtrxInfo" id="queryForm" action="${ctx}/refundDealInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">退款提交日期：</td>
								<td width="30%"><form:input type="text" name="createTime" path="createTime"
										id="createTime" class="txt2" 
										onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
										value="${queryForm.date}" />
								</td>
								<td width="12%" align="right">退款金额：</td>
								<td width="30%">
								<span> <form:input path = "payAmount" class="txt2" id="payAmount" /></span>
                                <span id="payAmountError" style="color: red;"></span>
								</td>
								<td width="12%" align="right">币种：</td>
								<td width="30%">
									<select id="payCurrency" name="payCurrency" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacOtrxInfo.payCurrency}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>		
							</tr>
							
							<tr>
								<td width="10%" align="right">退款流水号：</td>
								<td width="30%">
								<span> <form:input  
										path="trxSerialNo" class="txt2" id="trxSerialNo" /> </span>
										<span><form:errors path="trxSerialNo" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">原交易流水号：</td>
								<td width="30%">
								<span> <form:input  
										path="otrxSerialNo" class="txt2" id="otrxSerialNo" /> </span>
										<span><form:errors path="otrxSerialNo" cssStyle="color:red"/></span>
								</td>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();" />
								</td>
								
							</tr>
							
							<tr>
								<td width="10%" align="right">审核状态：</td>
								<td width="30%">
									<select id="confirmStatus" name="confirmStatus" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${confirmStateList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOtrxInfo.confirmStatus}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">退款状态：</td>
								<td width="30%">
									<select id="trxState" name="trxState" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${trxStateList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOtrxInfo.trxState}"> selected="selected"</c:if>><c:if test="${sys.dicValue eq 'N'}"> 待支付</c:if><c:if test="${sys.dicValue eq 'S'}">交易成功</c:if></option>
										</c:forEach>
									</select>
								</td>
								<td align="right">&nbsp;</td>
								<td><input name="clearBtn" type="button" 
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
                                    <th class="border">退款流水号</th>
                                    <th class="border">原交易流水号</th>
                                    <th class="border">退款金额</th>
                                    <th class="border">退款状态</th>
                                    <th class="border">审核状态</th>
                                    <th class="border">审核人</th>
                                    <th class="border">审核时间</th>
                                    <th class="border">退款提交日期</th>
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
                                    <td class="fontfamily1">${item.otrxSerialNo}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1"><c:if test="${item.trxState eq 'N'}">待支付</c:if><c:if test="${item.trxState eq 'S'}">交易成功</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.confirmStatus eq 'P'}">审核通过</c:if><c:if test="${item.confirmStatus eq 'N'}">审核未通过</c:if><c:if test="${item.confirmStatus eq 'I'}">未审核</c:if></td>
                                    <td class="fontfamily1">${item.confirmUser}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.confirmTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <c:if test="${item.trxState eq 'N' &&item.confirmStatus eq 'I'}"> 
	                                    <td class="fontfamily1"><input type="button" value="审核"
										onClick="showidTranaction('refundDetailDiv','${item.trxSerialNo}');showDetail('${item.id}');">&nbsp;
									</td>
                                    </c:if>
                                    
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/refundDealInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
    <%@include file="/WEB-INF/jsp/reconDataManage/refundDetail.jsp"%>
	<div class="clear"></div>



<!-- /Body -->
</body>
</html>