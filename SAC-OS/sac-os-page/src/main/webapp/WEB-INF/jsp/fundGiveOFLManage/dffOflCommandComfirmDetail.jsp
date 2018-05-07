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
		var draccNodeCode = document.getElementById("draccNodeCode").value;
		var draccAreaCode = document.getElementById("draccAreaCode").value;
		var payAmount = document.getElementById("payAmount").value;
		var craccNo = document.getElementById("craccNo").value;
		var craccNodeCode = document.getElementById("craccNodeCode").value;
		var craccBankName = document.getElementById("craccBankName").value;
		if(draccNodeCode==null||draccNodeCode==""){
			document.getElementById("draccNodeCodeError").innerHTML = "请选择出款银行";
			return;
		}
		
		if(draccNodeCode==null||draccNodeCode==""){
			document.getElementById("draccNodeCodeError").innerHTML = "请选择出款分行";
			return;
		}
		
		if(craccNo==null||craccNo==""){
			document.getElementById("craccNoError").innerHTML = "账号不能为空";
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
		var etrxSerialNo = document.getElementById("etrxSerialNo").value;
		if(etrxSerialNo==null||etrxSerialNo==""){
			document.getElementById("etrxSerialNoError").innerHTML = "银行流水号不能为空!";
			return;
		}
		var draccNodeCodeText=$("#draccNodeCode option:selected").text();
		var draccAreaCodeText=$("#draccAreaCode option:selected").text();
		var craccName = document.getElementById("craccName").value;
		var payCurrency = document.getElementById("payCurrency").value;
		var craccCardId = document.getElementById("craccCardId").value;
		var skSerialNo = document.getElementById("skSerialNo").value;
		var ykSerialNo = document.getElementById("ykSerialNo").value;
		var id = document.getElementById("id").innerHTML;
		var url = "dffOflCommandConfirmHandle";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				id : id,
				craccCardId : craccCardId,
				craccName : craccName,
				payCurrency : payCurrency,
				payAmount : payAmount,
				draccNodeCodeText : draccNodeCodeText,
				draccAreaCodeText : draccAreaCodeText,
				draccNodeCode : draccNodeCode,
				draccAreaCode : draccAreaCode,
				etrxSerialNo : etrxSerialNo,
				skSerialNo : skSerialNo,
				ykSerialNo : ykSerialNo,
				craccNodeCode : craccNodeCode,
				craccBankName : craccBankName,
				craccNo : craccNo
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("操作成功!");
					window.opener.callback();
				}else{
					alert(data.message);
				}
				window.close();
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
	 						<td align="right">预扣交易流水号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="ykSerialNo" name="ykSerialNo" value="${ykSerialNo}" readonly="readonly" />
	 						</td>
	 						<td width="20%" align="right">实扣交易流水号:</td>
	 						<td width="30%" align="left">
	 							<input type="text" class="txt2" id="skSerialNo" name="skSerialNo" value="${skSerialNo}" readonly="readonly" />
	 						</td>
	 					</tr>
						<tr>
	 						<td align="right">组织机构代码:</td>
	 						<td>
	 							<input type="text" class="txt2" id="craccCardId" name="craccCardId" value="${craccCardId}" readonly="readonly" />
	 						</td>
	 						<td width="20%" align="right">客户名称:</td>
	 						<td width="30%" align="left">
	 							<input type="text" class="txt2" id="craccName" name="craccName" value="${craccName}" readonly="readonly" />
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">收款方银行代码:</td>
	 						<td>
	 							<input type="text" class="txt2" id="craccNodeCode" name="craccNodeCode" value="${craccNodeCode}"  readonly="readonly"/>
	 						</td>
	 						<td align="right">收款方银行名称:</td>
	 						<td>
	 							<input type="text" class="txt2" id="craccBankName" name="craccBankName" value="${craccBankName}"  readonly="readonly"/>
	 						</td>
	 					</tr>
 						<tr>
	 						<td align="right">出款金额:</td>
	 						<td>
	 							<input type="text" class="txt2" id="payAmount" name="payAmount" value="${payAmount}" readonly="readonly" />
	 							<span style="color: red" id="payAmountError"></span>
	 						</td>
	 						<td align="right">币种:</td>
	 						<td>
	 							<input type="text" class="txt2" id="payCurrencyName" name="payCurrencyName" value="${payCurrencyName}"  readonly="readonly"/>
	 							<input style="display: none" type="text" class="txt2" id="payCurrency" name="payCurrency" value="${payCurrency}"  readonly="readonly"/>
	 						</td>
	 					</tr>
						
						<tr>
	 						<td align="right">银行流水号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="etrxSerialNo" name="etrxSerialNo" value="${etrxSerialNo}" />
	 							<span style="color: red" id="etrxSerialNoError"></span>
	 						</td>
	 						<td align="right">收款方账号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="craccNo" name="craccNo" value="${craccNo}" readonly="readonly"/>
	 							<span style="color: red" id="craccNoError"></span>
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
							<span style="display: none;" id="id" >${id}</span>
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