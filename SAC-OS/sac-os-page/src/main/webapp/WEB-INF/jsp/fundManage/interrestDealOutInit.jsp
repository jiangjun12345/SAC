<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>利息转出</title>
<script type="text/javascript">

$(function(){
	showBankAcc($("#draccBankNodeCode").val(),"draccNo");
	$("#draccBankNodeCode").change(function(){
		showBankAcc($("#draccBankNodeCode").val(),"draccNo");
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
							$("#"+flag).append("<option value="+bankAcc+"|"+key+">" + bankAcc+"("+value+")"+"</option>");
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
		var url = "interrestDealOutHandle";
		
		var draccBankNodeCode = document.getElementById("draccBankNodeCode").value;
		
		var draccNoCom = document.getElementById("draccNo").value;
		
		var comArr = draccNoCom.split("|");
		
		var draccNo = comArr[0];
		
		var payCurrency = comArr[1];
		
		var payAmount = document.getElementById("payAmount").value;
		
		if ("" == payAmount || null == payAmount) {
			document.getElementById('payAmountError').innerHTML = "转出金额不能为空";
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
				draccBankNodeCode : draccBankNodeCode,
				draccNo : draccNo,
				payCurrency : payCurrency,
				payAmount : payAmount
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					alert("转出成功!");
					window.location.reload()
				} else {
					alert("转出失败!");
				}
			},
			error : function(data) {
				alert("转出失败!");
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
						<td width="20%" align="right">付款银行：</td>
						<td width="30%">
							<select id="draccBankNodeCode" name="draccBankNodeCode" class="select1">
								<c:forEach items="${channelBankMap}" var="bank">
									<option value="${bank.key}">${bank.value}</option>
								</c:forEach>
							</select> 
						</td>
						<td width="20%" align="right">付款银行账号：</td>
						<td width="30%">
							<select id="draccNo" name="draccNo" class="select1"></select>
						</td>
					</tr>
					<tr>
						<td align="right" colspan="1">转出金额：</td>
						<td>
							<input id="payAmount" name="payAmount" type="text" maxlength="30" class="select1" />
							<span id="payAmountError" style="color: red;"></span>
						</td>
					</tr>
				</table>
				<div>
					<table align="center">
						<tr width="100%">
						<td align="center" width="100%">
							<input id="Pass" name="Pass" type="button" maxlength="30" class="bluebtn" value="利息转出" onclick="addConfirm();" />
						</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
