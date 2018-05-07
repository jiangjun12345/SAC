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
		<title>差错调账</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
function clearText(){
	document.getElementById("craccCusType").value="";
	document.getElementById("draccCusType").value="";
	document.getElementById("payCurrency").value="";
	document.getElementById("sacCurrency").value="";
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
		var url = "addConfirmForDiff";
		
		var trxSerialNo = document.getElementById("trxSerialNo").value;
		if(""==trxSerialNo||null==trxSerialNo){
			document.getElementById('trxSerialNoError').innerHTML="交易流水号不能为空";
			return;
		}
		
		var craccCusCode = document.getElementById("craccCusCode").value;
		if(""==craccCusCode||null==craccCusCode){
			document.getElementById('craccCusCodeError').innerHTML="收款方客户号不能为空";
			return;
		}
		
		var craccCusType = document.getElementById("craccCusType").value;
		
		var craccCusName = document.getElementById("craccCusName").value;
		if(""==craccCusName||null==craccCusName){
			document.getElementById('craccCusNameError').innerHTML="收款方客户名称不能为空";
			return;
		}
		
		var craccNo = document.getElementById("craccNo").value;
		if(""==craccNo||null==craccNo){
			document.getElementById('craccNoError').innerHTML="收款方帐号不能为空";
			return;
		}
		
		var craccName = document.getElementById("craccName").value;
		if(""==craccName||null==craccName){
			document.getElementById('craccNameError').innerHTML="收款账户名称不能为空";
			return;
		}
		
		var craccNodeCode = document.getElementById("craccNodeCode").value;
		if(""==craccNodeCode||null==craccNodeCode){
			document.getElementById('craccNodeCodeError').innerHTML="收款方银行节点代码不能为空";
			return;
		}
		
		var craccBankName = document.getElementById("craccBankName").value;
		if(""==craccBankName||null==craccBankName){
			document.getElementById('craccBankNameError').innerHTML="收款方银行名称不能为空";
			return;
		}
		
		var draccCusCode = document.getElementById("draccCusCode").value;
		if(""==draccCusCode||null==draccCusCode){
			document.getElementById('draccCusCodeError').innerHTML="付款方客户号不能为空";
			return;
		}
		
		var draccCusType = document.getElementById("draccCusType").value;
		
		var draccCusName = document.getElementById("draccCusName").value;
		if(""==draccCusName||null==draccCusName){
			document.getElementById('draccCusNameError').innerHTML="付款方客户名称不能为空";
			return;
		}
		
		var draccNo = document.getElementById("draccNo").value;
		if(""==draccNo||null==draccNo){
			document.getElementById('draccNoError').innerHTML="付款方帐号不能为空";
			return;
		}
		
		var draccName = document.getElementById("draccName").value;
		if(""==draccName||null==draccName){
			document.getElementById('draccNameError').innerHTML="付款账户名称不能为空";
			return;
		}
		
		var draccNodeCode = document.getElementById("draccNodeCode").value;
		if(""==draccNodeCode||null==draccNodeCode){
			document.getElementById('draccNodeCodeError').innerHTML="付款方银行节点代码不能为空";
			return;
		}
		
		var draccBankName = document.getElementById("draccBankName").value;
		if(""==draccBankName||null==draccBankName){
			document.getElementById('draccBankNameError').innerHTML="付款方银行名称不能为空";
			return;
		}
		
		
		var cusBillNo = document.getElementById("cusBillNo").value;
		
		var platBillNo = document.getElementById("platBillNo").value;
		
		var otrxSerialNo = document.getElementById("otrxSerialNo").value;
		
		
		var etrxSerialNo = document.getElementById("etrxSerialNo").value;
		if(""==etrxSerialNo||null==etrxSerialNo){
			document.getElementById('etrxSerialNoError').innerHTML="外部交易流水号不能为空";
			return;
		}
		
		var payAmount = document.getElementById("payAmount").value;
		if(""==payAmount||null==payAmount){
			document.getElementById('payAmountError').innerHTML="支付金额不能为空";
			return;
		}
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
		
		var sacAmount = document.getElementById("sacAmount").value;
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if(sacAmount.length>0){
			var flag = sacAmount.match(regexp);
			if(flag==null){
				document.getElementById("sacAmountError").innerHTML="格式非法";
				return;
			}
			if(sacAmount.length>20){
				document.getElementById("sacAmountError").innerHTML="长度超长";
				return;
			}
		}
		
		var payCurrency = document.getElementById("payCurrency").value;
		
		var sacCurrency = document.getElementById("sacCurrency").value;;
		if(sacAmount!=null&&sacAmount!=""){
			if(sacCurrency==null||sacCurrency==""){
				document.getElementById('sacCurrencyError').innerHTML="购结汇币种不能为空";
				return;
			}
		}else{
			if(sacCurrency!=null&&sacCurrency!=""){
				document.getElementById('sacAmountError').innerHTML="购结汇金额不能为空";
				return;
			}
			
		}
		
		var bussType = document.getElementById("bussType").value;
		if(""==bussType||null==bussType){
			document.getElementById('bussTypeError').innerHTML="业务类型不能为空";
			return;
		}
		
		var trxType = document.getElementById("trxType").value;
		if(""==trxType||null==trxType){
			document.getElementById('trxTypeError').innerHTML="交易类型不能为空";
			return;
		}
		
		var trxState = document.getElementById("trxState").value;
		if(""==trxState||null==trxState){
			document.getElementById('trxStateError').innerHTML="交易状态不能为空";
			return;
		}
		
		var payconType = document.getElementById("payconType").value;
		if(""==payconType||null==payconType){
			document.getElementById('payconTypeError').innerHTML="支付渠道类型不能为空";
			return;
		}
		
		var trxTimeString = document.getElementById("trxTimeString").value;
		if(""==trxTimeString||null==trxTimeString){
			document.getElementById('trxTimeStringError').innerHTML="交易时间不能为空";
			return;
		}
		var trxSuccTimeString = document.getElementById("trxSuccTimeString").value;
		if(""==trxSuccTimeString||null==trxSuccTimeString){
			document.getElementById('trxSuccTimeStringError').innerHTML="交易时间不能为空";
			return;
		}
		
		var payWay = document.getElementById("payWay").value;
		
		var trxBatchNo = document.getElementById("trxBatchNo").value;
		
		var trxCost = document.getElementById("trxCost").value;
		
		var exRate = document.getElementById("exRate").value;
		
		var issuingBank = document.getElementById("issuingBank").value;
		
		var trxErrDealType = document.getElementById("trxErrDealType").value;
		
		if(trxType=='3303'||trxType=='3305'){
			if(""==trxErrDealType||null==trxErrDealType){
				document.getElementById('trxErrDealTypeError').innerHTML="交易差错处理类型不能为空";
				return;
			}
		}
		
		var taxAmount = document.getElementById("taxAmount").value;
		
		var transportExpenses = document.getElementById("transportExpenses").value;
		
		var memo = document.getElementById("memo").value;
		
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				cusBillNo : cusBillNo,
				platBillNo : platBillNo,
				trxSerialNo : trxSerialNo,
				otrxSerialNo : otrxSerialNo,
				etrxSerialNo : etrxSerialNo,
				craccCusCode : craccCusCode,
				craccCusType : craccCusType,
				craccCusName : craccCusName,
				craccNo : craccNo,
				craccName : craccName,
				craccNodeCode : craccNodeCode,
				craccBankName : craccBankName,
				draccCusCode : draccCusCode,
				draccCusType : draccCusType,
				draccCusName : draccCusName,
				draccNo : draccNo,
				draccName : draccName,
				draccNodeCode : draccNodeCode,
				draccBankName : draccBankName,
				payCurrency : payCurrency,
				payAmount : payAmount,
				sacCurrency : sacCurrency,
				sacAmount : sacAmount,
				bussType : bussType,
				trxType : trxType,
				trxState : trxState,
				payconType : payconType,
				payWay : payWay,
				trxBatchNo : trxBatchNo,
				trxCost : trxCost,
				exRate : exRate,
				issuingBank : issuingBank,
				trxErrDealType : trxErrDealType,
				taxAmount : taxAmount,
				transportExpenses : transportExpenses,
				trxTime : trxTimeString,
				trxSuccTime : trxSuccTimeString,
				memo : memo
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("录入成功!");
					setTimeout(window.location.reload(),1000)
				}else{
					alert(data.message);
				}
				
				
			},
			error : function(data){
						alert("录入失败!");
			}
		});
	}
} 

</script>


</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form id="subForm" commandName="sacOtrxInfo" action="${ctx}/fundAllotInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%" align="right">商户订单号：</td>
								<td width="30%"> <input id="cusBillNo" name="cusBillNo"
									type="text" type="text" /> 
									<span id ="cusBillNoError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">平台订单号：</td>
								<td width="30%"> <input id="platBillNo" name="platBillNo"
									type="text" type="text" /> 
									<span id ="platBillNoError" style="color: red;" ></span>
								</td>
							</tr>
						
							<tr>
								<td width="10%" align="right">交易流水号：</td>
								<td width="30%"> <input id="trxSerialNo" name="trxSerialNo"
									type="text" type="text" /> 
									<span id ="trxSerialNoError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">原交易流水号：</td>
								<td width="30%"> <input id="otrxSerialNo" name="otrxSerialNo"
									type="text" type="text" /> 
									<span id ="otrxSerialNoError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">收款方客户号：</td>
									<td width="30%"> <input id="craccCusCode" name="craccCusCode"
									type="text" type="text" /> 
									<span id ="craccCusCodeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">收款方客户名称：</td>
								<td width="30%"> <input id="craccCusName" name="craccCusName"
									type="text" type="text" /> 
									<span id ="craccCusNameError" style="color: red;" ></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">收款方账号：</td>
								<td width="30%"><input id="craccNo" name="craccNo"
									type="text" type="text" />
								<span id ="craccNoError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">收款方账户名称：</td>
								<td width="30%"><input id="craccName" name="craccName" type="text"
									maxlength="30" type="text" />
									<span id ="craccNameError" style="color: red;" ></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">收款方银行节点代码：</td>
								<td width="30%"><input id="craccNodeCode" name="craccNodeCode" type="text"
									maxlength="30" type="text" />
									<span id ="craccNodeCodeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">收款方银行名称：</td>
								<td width="30%"><input id="craccBankName"
									name="craccBankName" type="text" maxlength="30"
									type="text" />
									<span id ="craccBankNameError" style="color: red;" ></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">收款方客户类型：</td>
								<td width="30%">
									<select id="craccCusType" name="craccCusType"  type="text" with="100%" >
										<c:forEach items="${customerTypeList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">付款方客户类型：</td>
								<td width="30%">
									<select id="draccCusType" name="draccCusType"  type="text" with="100%" >
										<c:forEach items="${customerTypeList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								
							</tr>
							<tr>
								<td width="10%" align="right">付款方客户号：</td>
								<td width="30%"><input id="draccCusCode" name="draccCusCode"
									type="text" type="text" />
								<span id ="draccCusCodeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">付款方客户名称：</td>
								<td width="30%"> <input id="draccCusName" name="draccCusName"
									type="text" type="text" /> 
									<span id ="draccCusNameError" style="color: red;" ></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">付款方账号：</td>
								<td width="30%"><input id="draccNo" name="draccNo"
									type="text"  type="text" />
									<span id ="draccNoError" style="color: red;" ></span>
								</td>
								<td align="right" colspan="1">付款方账户名称：</td>
								<td width="30%"><input id="draccName" name="draccName"
									type="text" maxlength="30" type="text" />
									<span id ="draccNameError" style="color: red;" ></span>
								</td>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">付款银行节点代码：</td>
								<td width="30%"><input id="draccNodeCode" name="draccNodeCode"
									type="text" maxlength="30" type="text" />
									<span id ="draccNodeCodeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">付款方银行名称：</td>
								<td width="30%"><input id="draccBankName" name="draccBankName" type="text"
									maxlength="30" type="text" />
									<span id ="draccBankNameError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">支付币种：</td>
								<td width="30%">
									<select id="payCurrency" name="payCurrency"  type="text" with="100%" >
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicCode}">${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td align="right" colspan="1">支付金额：</td>
								<td width="30%"><input id="payAmount" name="payAmount"
									type="text" maxlength="30" type="text"  />
									<span id ="payAmountError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">购结汇币种：</td>
								<td width="30%">
									<select id="sacCurrency" name="sacCurrency"  type="text" with="100%" >
										<option value="">无</option>
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicCode}">${sys.dicDesc}</option>
										</c:forEach>
									</select>
									<span id ="sacCurrencyError" style="color: red;" ></span>
								</td>
								<td align="right" colspan="1">购结汇金额：</td>
								<td width="30%"><input id="sacAmount" name="sacAmount"
									type="text" maxlength="30" type="text"  />
									<span id ="sacAmountError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">业务类型：</td>
								<td width="30%"><input id="bussType" name="bussType"
									type="text" maxlength="30" type="text"  />
									<span id ="bussTypeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">支付方式：</td>
								<td width="30%">
									<select id="payWay" name="payWay"  type="text" with="100%" >
										<c:forEach items="${payTypeList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">交易类型：</td>
								<td width="30%"><input id="trxType" name="trxType"
									type="text" maxlength="30" type="text"  />
									<span id ="trxTypeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">交易状态：</td>
								<td width="30%"><input id="trxState" name="trxState"
									type="text" maxlength="30" type="text"  />
									<span id ="trxStateError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">支付渠道类型：</td>
								<td width="30%"><input id="payconType" name="payconType"
									type="text" maxlength="30" type="text"  />
									<span id ="payconTypeError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">交易批次号：</td>
								<td width="30%"><input id="trxBatchNo" name="trxBatchNo"
									type="text" maxlength="30" type="text"  />
									<span id ="trxBatchNoError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">成本：</td>
								<td width="30%"><input id="trxCost" name="trxCost"
									type="text" maxlength="30" type="text"  />
									<span id ="trxCostError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">汇率：</td>
								<td width="30%"><input id="exRate" name="exRate"
									type="text" maxlength="30" type="text"  />
									<span id ="exRateError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">发卡行：</td>
								<td width="30%"><input id="issuingBank" name="issuingBank"
									type="text" maxlength="30" type="text"  />
									<span id ="issuingBankError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">交易差错处理类型：</td>
								<td width="30%"><input id="trxErrDealType" name="trxErrDealType"
									type="text" maxlength="30" type="text"  />
									<span id ="exRateError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">税金额：</td>
								<td width="30%"><input id="taxAmount" name="taxAmount"
									type="text" maxlength="30" type="text"  />
									<span id ="taxAmountError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">运费：</td>
								<td width="30%"><input id="transportExpenses" name="transportExpenses"
									type="text" maxlength="30" type="text"  />
									<span id ="transportExpensesError" style="color: red;" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">外部流水号：</td>
								<td width="30%"> <input id="etrxSerialNo" name="etrxSerialNo"
									type="text" type="text" /> 
									<span id ="etrxSerialNoError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">请求时间(yyyyMMddHHmmss)：</td>
								<td width="30%"><input id="trxTimeString" name="trxTimeString"
									type="text" maxlength="30" type="text"  />
									<span id ="trxTimeStringError" style="color: red;" ></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">交易成功时间(yyyyMMddHHmmss)：</td>
								<td width="30%"><input id="trxSuccTimeString" name="trxSuccTimeString"
									type="text" maxlength="30" type="text"  />
									<span id ="trxSuccTimeStringError" style="color: red;" ></span>
								</td>
								<td width="10%" align="right">备注：</td>
								<td width="30%"><input id="memo" name="memo"
									type="text" maxlength="30" type="text"  />
									<span id ="memoError" style="color: red;" ></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right"></td> 
								<td width="30%"><input id="insert" name="insert"
									type="button" maxlength="30" class="bluebtn" value="录入" onclick="addConfirm();"/></td>
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
    </div>
</body>
</html>