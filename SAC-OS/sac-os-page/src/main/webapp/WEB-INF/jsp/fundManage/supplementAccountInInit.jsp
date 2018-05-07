<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>补账录入</title>
<script type="text/javascript">

$(function(){
	showBankAcc($("#craccBankNodeCode").val(),"craccNo");
	$("#craccBankNodeCode").change(function(){
		showBankAcc($("#craccBankNodeCode").val(),"craccNo");
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
							$("#"+flag).append("<option value="+bankAcc+"_"+value+">" + bankAcc+"("+value+")"+"</option>");
						}
					});
				});
			}
		}
	});
}
	
	function addConfirm() {
		if (window.confirm("确认?")){
		//清空错误span
		var spanArr = document.getElementsByTagName('span');
		for ( var i = 0; i < spanArr.length; i++) {
			var spanId = spanArr[i].id;
			if (spanId.indexOf('Error') >= 0) {
				document.getElementById(spanId).innerHTML = "";
			}
		}
		var flag = true;
		var url = "supplementAccountInHandle";
		
		var craccBankNodeCode = document.getElementById("craccBankNodeCode").value;
		
		var craccNoAndCcy = document.getElementById("craccNo").value;
		
		var arr = craccNoAndCcy.split("_");
		
		var craccNo = arr[0];
		
		var ccy = arr[1];
		
		var payAmount = document.getElementById("payAmount").value;
		
		var craccCardId = document.getElementById("craccCardId").value;
		
		var bussType = document.getElementById("bussType").value;
		
		var etrxSerialNo = document.getElementById("etrxSerialNo").value;
		
		var draccNo = document.getElementById("draccNo").value;
		
		if ("" == craccCardId || null == craccCardId) {
			document.getElementById('craccCardIdError').innerHTML = "组织机构代码为空";
			return;
		}
		if ("" == payAmount || null == payAmount) {
			document.getElementById('payAmountError').innerHTML = "录入金额不能为空";
			return;
		}
		if("" == draccNo || null == draccNo){
			document.getElementById("draccNoError").innerHTML = "付款方账号不能为空";
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
		}
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				craccBankNodeCode : craccBankNodeCode,
				craccNo : craccNo,
				ccy : ccy,
				craccCardId : craccCardId,
				payAmount : payAmount,
				bussType : bussType,
				etrxSerialNo:etrxSerialNo,
				draccNo:draccNo
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					alert("录入成功!");
					window.location.reload()
				} else {
					alert(data.message);
				}
			},
			error : function(data) {
				alert("录入失败!");
				window.location.reload()
			}
		});
		}
	}
</script>
</head>
<body>
	<div class="content" >
		<div class="con" >
			<div  class="table fontcolor4 fontsize1 fontfamily2">
				<table  width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20%" align="right">收款银行：</td>
						<td width="30%">
							<select id="craccBankNodeCode" name="craccBankNodeCode" class="select1">
								<c:forEach items="${channelBankMap}" var="bank">
									<option value="${bank.key}">${bank.value}</option>
								</c:forEach>
							</select> 
						</td>
						<td width="20%" align="right">收款银行账号：</td>
						<td width="30%">
							<select id="craccNo" name="craccNo" class="select1"></select>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="1">收款组织机构代码：</td>
						<td>
							<input id="craccCardId" name="craccCardId" type="text" maxlength="30" class="select1" />
							<span id="craccCardIdError" style="color: red;"></span>
						</td>
						<td align="right" colspan="1">收款组织业务类型：</td>
						<td>
							<select id="bussType" name="bussType" class="select1">
								<c:forEach items="${bussTypeList}" var="type">
									<option value="${type.dicValue}">${type.dicDesc}</option>
								</c:forEach>
							</select> 
						</td>
					</tr>
					<tr>
						<td align="right" colspan="1">录入金额：</td>
						<td>
							<input id="payAmount" name="payAmount" type="text" maxlength="30" class="select1" />
							<span id="payAmountError" style="color: red;"></span>
						</td>
						<td align="right" colspan="1">银行流水号：</td>
						<td>
							<input id="etrxSerialNo" name="etrxSerialNo" type="text"  class="select1" />
						</td>
					</tr>
					<tr>
						<td align="right" colspan="1">付款方账号：</td>
						<td>
							<input id="draccNo" name="draccNo" type="text"  class="select1" />
							<span id="draccNoError" style="color: red;"></span>
						</td>
					</tr>
				</table>
				<div>
					<table align="center">
						<tr width="100%">
						<td align="center" width="100%">
							<input id="Pass" name="Pass" type="button" maxlength="30" class="bluebtn" value="补账录入" onclick="addConfirm();" />
						</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
