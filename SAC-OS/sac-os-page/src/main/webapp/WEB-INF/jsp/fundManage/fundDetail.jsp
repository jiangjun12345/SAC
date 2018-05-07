<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>资金调拨录入</title>
<script type="text/javascript">
	$(function(){
		showBankAcc($("#craccBankNodeCode").val(),"craccNoDetail");
		showBankAcc($("#draccBankNodeCode").val(),"draccNoDetail");
		$("#craccBankNodeCode").change(function(){
			showBankAcc($("#craccBankNodeCode").val(),"craccNoDetail");
		});	
		$("#draccBankNodeCode").change(function(){
			showBankAcc($("#draccBankNodeCode").val(),"draccNoDetail");
		});	 
		
	});
	
	function showBankAcc(bankNodeCode,flag){
		$.ajax({
			url:"getBankAccListByBankNodeCode",
			type:"get",
			dataType:"json",
			data:{
				bankNodeCode:bankNodeCode
			},
			async:false,
			success:function(data){
				if(data.success){
					$("#"+flag).find("option").remove();
					var ccyMap = data.ccyMap;
					$.each(data.bankAccList,function(n,item){
						var bankAcc = item.bankAcc;
						var currencyType = item.currencyType;
						$.each(ccyMap,function(key,value){
							if(key==currencyType){
								$("#"+flag).append("<option value='" + currencyType +"-"+ bankAcc + "'>" + bankAcc+"("+value+")"+"</option>");
							}
						});
					});
				}
			}
		});
	}
	
	function addConfirm() {
		//清空错误span
		var spanArr = document.getElementsByTagName('span');
		for ( var i = 0; i < spanArr.length; i++) {
			var spanId = spanArr[i].id;
			if (spanId.indexOf('Error') >= 0) {
				document.getElementById(spanId).innerHTML = "";
			}
		}
		var flag = true;
		var url = "addConfirm";
		var craccNoDetailValue = document.getElementById("craccNoDetail").value;
		var cracc = craccNoDetailValue.split("-");
		var craccCcy = cracc[0];
		var craccNo = cracc[1];
		if ("" == craccNo || null == craccNo) {
			document.getElementById('craccNoError').innerHTML = "请选择收款方账号";
			return;
		}
		var draccNoDetailValue = document.getElementById("draccNoDetail").value;
		var dracc = draccNoDetailValue.split("-");
		var draccCcy = dracc[0];
		var draccNo = dracc[1];
		if ("" == draccNo || null == draccNo) {
			document.getElementById('draccNoError').innerHTML = "请选择付款方账号";
			return;
		}
		var craccName = $("#craccBankNodeCode").val();
		var draccName = $("#draccBankNodeCode").val();
		var draccAreaCode = $("#draccAreaCode").val();
		var craccAreaCode = $("#craccAreaCode").val();

		if (craccName == draccName&&draccAreaCode==craccAreaCode) {
			document.getElementById('craccBankNodeCodeError').innerHTML = "不允许相同银行相同分行间调拨";
			return;
		}
		var payCurrency = document.getElementById("payCurrencyDetail").value;
		if (payCurrency != craccCcy) {
			document.getElementById('craccNoError').innerHTML = "请选择与调拨币种相同的账号";
			return;
		}
		if (payCurrency != draccCcy) {
			document.getElementById('draccNoError').innerHTML = "请选择与调拨币种相同的账号";
			return;
		}
		var payAmount = document.getElementById("payAmountDetail").value;
		if ("" == payAmount || null == payAmount) {
			document.getElementById('payAmountDetailError').innerHTML = "调拨金额不能为空";
			return;
		}
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if (payAmount.length > 0) {
			var flag = payAmount.match(regexp);
			if (flag == null) {
				document.getElementById("payAmountDetailError").innerHTML = "格式非法";
				return;
			}
			if (payAmount.length > 20) {
				document.getElementById("payAmountDetailError").innerHTML = "长度超长";
				return;
			}
		}
		var payWay = document.getElementById("payWayDetail").value;
		var memo = document.getElementById("memoDetail").value;
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				craccNo : craccNo,
				draccNo : draccNo,
				payCurrency : payCurrency,
				payAmount : payAmount,
				payWay : payWay,
				draccAreaCode : draccAreaCode,
				craccAreaCode : craccAreaCode,
				memo : memo
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					alert("录入成功!");
					setTimeout(window.location.reload(), 3000)
				} else {
					if (data.valid) {
						document.getElementById(data.filed
								+ 'Error').innerHTML = data.message;
					} else {
						alert("录入失败!");
						setTimeout(window.location.reload(), 3000)
					}

				}
			},
			error : function(data) {
				alert("录入失败!");
			}
		});
	}
</script>
</head>
<body>
	<div class="content">
		<div class="con ">
			<div class="table fontcolor4 fontsize1 fontfamily2">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="10%" align="right">收款银行：</td>
						<td width="20%">
							<select id="craccBankNodeCode" name="craccBankNodeCode" class="select1">
								<c:forEach items="${channelBankMap}" var="bank">
									<option value="${bank.key}">${bank.value}</option>
								</c:forEach>
							</select> 
						</td>
						<td width="20%" align="left"><span id="craccBankNodeCodeError" style="color: red;"></span></td>
						<td width="10%" align="right">付款银行：</td>
						<td width="20%">
							<select id="draccBankNodeCode" name="draccBankNodeCode" class="select1">
								<c:forEach items="${channelBankMap}" var="bank">
									<option value="${bank.key}">${bank.value}</option>
								</c:forEach>
							</select> 
						</td>
						<td width="20%" align="left"></td>
					</tr>
					<tr>
						<td align="right">收款银行账号：</td>
						<td>
							<select id="craccNoDetail" name="craccNoDetail" class="select1"></select> 
						</td>
						<td align="left"><span id="craccNoError" style="color: red;"></span></td>
						<td align="right">付款银行账号：</td>
						<td>
							<select id="draccNoDetail" name="draccNoDetail" class="select1"></select> 
						</td>
						<td align="left"><span id="draccNoError" style="color: red;"></span></td>
					</tr>
					<tr>
						<td align="right">币种：</td>
						<td>
							<select id="payCurrencyDetail" name="payCurrencyDetail" class="select1" with="100%">
								<c:forEach items="${currencyList}" var="sys">
									<option value="${sys.dicCode}" <c:if test="${sys.dicCode eq 'CNY'}">selected="selected"</c:if>>${sys.dicDesc}</option>
								</c:forEach>
							</select>
						</td>
						<td align="left"></td>
						<td align="right" colspan="1">调拨金额：</td>
						<td colspan="1">
							<input id="payAmountDetail" name="payAmountDetail" type="text" maxlength="30" class="select1" />
						</td>
						<td align="left"><span id="payAmountDetailError" style="color: red;"></span></td>
					</tr>
					<tr>
						<td align="right">支付方式：</td>
						<td>
							<select id="payWayDetail" name="payWayDetail" class="select1" with="100%">
								<c:forEach items="${payTypeList}" var="sys">
									<option value="${sys.dicValue}">${sys.dicDesc}</option>
								</c:forEach>
							</select>
						</td>
						<td align="left"></td>
						<td align="right" colspan="1">调拨原因：</td>
						<td colspan="1">
							<input id="memoDetail" name="memoDetail" type="text" maxlength="30" class="select1" /> 
						</td>
						<td align="left"><span id="memoError" style="color: red;"></span></td>
					</tr>
					<tr>
						<td width="10%" align="right">收款方分行：</td>
						<td width="20%">
							<select id="craccAreaCode" name="craccAreaCode" class="select1">
								<c:forEach items="${branchList}" var="sys">
<%-- 											<option value="${sys.dicValue}"/> --%>
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == '000000'}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
							</select> 
						</td>
						<td width="20%" align="left"><span id="craccAreaCodeError" style="color: red;"></span></td>
						<td width="10%" align="right">付款方分行：</td>
						<td width="20%">
							<select id="draccAreaCode" name="draccAreaCode" class="select1">
								<c:forEach items="${branchList}" var="sys">
<%-- 											<option value="${sys.dicValue}"/> --%>
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == '000000'}"> selected="selected"</c:if>>${sys.dicDesc}</option>
								</c:forEach>
							</select> 
						</td>
						<td width="20%" align="left"><span id="draccAreaCodeError" style="color: red;"></span></td>
					</tr>
					
					<tr>
						<td align="right"></td>
						<td>
							<input id="Pass" name="Pass" type="button" maxlength="30" class="bluebtn" value="录入" onclick="addConfirm();" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</body>
</html>
