<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/js/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/js/jquery/jquery.ajax-1.0.js"></script>
<script type="text/javascript">


function transferConfirm(){
	if (window.confirm("确认?")){
		document.getElementById("draccNodeCodeError").innerHTML="";
		document.getElementById("payAmountError").innerHTML="";
		var draccNodeCode = document.getElementById("draccNodeCode").value;
		var draccAreaCode = document.getElementById("draccAreaCode").value;
		var craccNodeCode = document.getElementById("input1").value;
		var payAmount = document.getElementById("payAmount").value;
		var craccNo = document.getElementById("craccNo").value;
		if(draccNodeCode==null||draccNodeCode==""){
			document.getElementById("draccNodeCodeError").innerHTML = "请选择付款银行";
			return;
		}
		if(draccAreaCode==null||draccAreaCode==""){
			document.getElementById("draccAreaCodeError").innerHTML = "请选择付款方分行";
			return;
		}
		var craccBankName = document.getElementById("input0").value;

		if(craccBankName==null||craccBankName==""){
			document.getElementById("craccNodeCodeError").innerHTML = "请选择收款银行";
			return;
		} 
		if(craccNo==null||craccNo==""){
			document.getElementById("craccNoError").innerHTML = "账号不得为空";
			return;
		}
		
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if (payAmount.length > 0) {
			var flag = payAmount.match(regexp);
			if (flag == null) {
				document.getElementById("payAmountError").innerHTML = "格式非法";
				return;
			}
			if (payAmount.length > 20) {
				document.getElementById("payAmountError").innerHTML = "长度超长";
				return;
			}
		}else{
			document.getElementById("payAmountError").innerHTML = "金额为空";
			return;
		}
		var draccBankName=$("#draccNodeCode option:selected").text();
		var cusNo = document.getElementById("cusNo").value;
		var bussType = document.getElementById("bussType").value;
		var cusName = document.getElementById("cusName").value;
		var cusPlatAcc = document.getElementById("cusPlatAcc").value;		
		var payCurrency = document.getElementById("payCurrency").value;
		var orgCardId = document.getElementById("orgCardId").value;
		var etrxSerialNo = document.getElementById("etrxSerialNo").value;
		var craccNo = document.getElementById("craccNo").value;
		if(craccNodeCode==null||craccNodeCode==""){
			craccNodeCode = "0";
		}
		var url = "fundTransferConfirmHandle";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				cusNo : cusNo,
				cusName : cusName,
				cusPlatAcc : cusPlatAcc,
				payCurrency : payCurrency,
				draccBankName : draccBankName,
				draccNodeCode : draccNodeCode,
				craccBankName : craccBankName,
				craccNodeCode : craccNodeCode,
				draccAreaCode : draccAreaCode,
				bussType : bussType,
				payAmount : payAmount,
				orgCardId : orgCardId,
				etrxSerialNo : etrxSerialNo,
				craccNo : craccNo
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("操作成功!");
					window.close();
				}else{
					alert(data.message);
					window.close();
				}
				window.location.reload();
			},
			error : function(data) {
				alert("操作失败!");
				window.close();
				window.location.reload();
			}
		});
	}
	//document.getElementById("subForm").ajaxForm(success:function{});
}
function clearValue(){
	document.getElementById("input1").value="";

};
function setValue(){
	document.getElementById('input0').value=$('#craccNodeCode option:selected').text();
	document.getElementById('input1').value=$('#craccNodeCode option:selected').val();
};
</script>
<title>线下取回信息确认</title>
</head>
<body>
<!-- Body -->
<div class="content">
	<div class="con ">
		<form:form id="subForm"  action="fundTransferConfirm" method="POST" >
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 13px;color: #666666; font-family: 微软雅黑;">
							<tr>
	 						<td width="20%" align="right">客户号:</td>
	 						<td width="30%" align="left">
	 							<input type="text" class="txt2" id="cusNo" name="cusNo" value="${cusNo}" readonly="readonly"/>
	 						</td>
	 						<td width="20%" align="right">客户名称:</td>
	 						<td width="30%" align="left">
	 							<input type="text" class="txt2" id="cusName" name="cusName" value="${cusName}" readonly="readonly" />
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">客户平台账号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="cusPlatAcc" name="cusPlatAcc" value="${cusPlatAcc}" readonly="readonly" />
	 						</td>
	 						<td align="right">币种:</td>
	 						<td>
	 							<input type="text" class="txt2" id="payCurrencyName" name="payCurrencyName" value="${payCurrencyName}"  readonly="readonly"/>
	 							<input style="display: none" type="text" class="txt2" id="payCurrency" name="payCurrency" value="${payCurrency}"  readonly="readonly"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">组织机构代码:</td>
	 						<td>
	 							<input type="text" class="txt2" id="orgCardId" name="orgCardId" value="${orgCardId}" readonly="readonly" />
	 						</td>
	 						<td align="right">商户节点代码:</td>
	 						<td>
	 							<input type="text" class="txt2" id="merchantNcode" name="merchantNcode" value="${merchantNcode}"  readonly="readonly"/>
	 						</td>
	 					</tr>
 						<tr>
	 						<td width="10%" align="right">付款银行名称：</td>
								<td width="25%"><select id="draccNodeCode" name="draccNodeCode"
									class="select1">
										<option value="">全部</option>
										<c:forEach items="${bankList}" var="sys">
<%-- 											<option value="${sys.dicValue}"/> --%>
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == draccNodeCode}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								<span style="color: red" id="draccNodeCodeError"></span>
								</td>
								
							<td width="10%" align="right">收款银行名称：</td>
								<td width="25%">
								<input id="input0" name = "input0" placeholder="请选择或者输入" onchange="clearValue();"/>
								<select id="craccNodeCode" name="craccNodeCode"
									class="select1" style = "width:20px;" onchange="setValue();">
										<option value="">全部</option>
										<c:forEach items="${bankList}" var="sys">
<%-- 											<option value="${sys.dicValue}"/> --%>
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == craccNodeCode}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								<input id="input1" name = "input1" type="hidden"/>
								<span style="color: red" id="craccNodeCodeError"></span>
							</td>
	 					</tr>
						<tr>
						
	 						<td width="10%" align="right">付款方分行：</td>
								<td width="25%"><select id="draccAreaCode" name="draccAreaCode"
									class="select1">
										<c:forEach items="${branchList}" var="sys">
<%-- 											<option value="${sys.dicValue}"/> --%>
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == '000000'}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								<span style="color: red" id="draccAreaCodeError"></span>
							</td>
							<td align="right">付款金额:</td>
	 						<td>
	 							<input type="text" class="txt2" id="payAmount" name="payAmount" value="${payAmount}" />
	 							<span style="color: red" id="payAmountError"></span>
	 						</td>
	 						<td>
	 							<input type="text" class="txt2" id="bussType" name="bussType" value="${bussType}" style="display: none;" />
	 						</td>
	 					</tr>
	 					
						<tr>
	 						<td align="right">银行流水号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="etrxSerialNo" name="etrxSerialNo" value="${etrxSerialNo}" />
	 						</td>
	 						<td align="right">收款方账号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="craccNo" name="craccNo" value="${craccNo}" />
	 							<span style="color: red" id="craccNoError"></span>
	 						</td>
	 					</tr>
							
						</table>
					</div>
			</div>
			</form:form>
			<div align="center" >
				<tr style="font-size: 13px;color: #666666; font-family: 微软雅黑;">
					<td >
						<input align="right" type="button" style="font-size: 13px;color: #666666; " value="线下取回确认" onclick="transferConfirm();">
					</td>
				</tr>
			</div>
			
	</div>
				
<!-- /Body -->
</body>
</html>