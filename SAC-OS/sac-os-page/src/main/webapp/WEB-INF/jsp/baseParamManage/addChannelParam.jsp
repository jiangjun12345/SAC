<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	$(function(){
		var chnType = "${sacChannelParam.chnType}";
		$("#chnType").find("option[value="+chnType+"]").attr("selected",true);
		var currencyType = "${sacChannelParam.currencyType}";
		$("#currencyType").find("option[value="+currencyType+"]").attr("selected",true);
		
		$("#addChannelParamBtn").click(function(){
			if(validateData()){
				$("#addChannelParamForm").submit();	
			}
		});
	});
	
	function validateData(){
		var flag = true;
		var reg = new RegExp("^[0-9]*$");
		var reg2 = new RegExp("^[0-9]+(.[0-9]{2})?$");
		var chnCode = $("#chnCode").val();
		var chnName = $("#chnName").val();
		var bankNodeCode = $("#bankNodeCode").val();
		var sacBankName = $("#sacBankName").val();
		var accountName = $("#accountName").val();
		var bankAcc = $("#bankAcc").val();
		var craccBankCode = $("#craccBankCode").val();
		var sacPeriod = $("#sacPeriod").val();
		var costRate = $("#costRate").val();
		if(chnCode==""||chnName==""||bankNodeCode==""||sacBankName==""||accountName==""||bankAcc==""
				||craccBankCode==""||sacPeriod==""||costRate==""){//验证非空
			$("#validateStr").html("必输项有空值！");
			flag = false;
		}else if(chnCode.length!=7){
			spanValue();
			$("#chnCodeValidate").html("格式错误！代码应为7位");
			flag = false;
		}else if(bankNodeCode.length!=7){
			spanValue();
			$("#bankNodeCodeValidate").html("格式错误！代码应为7位");
			flag = false;
		}else if(!reg.test(bankAcc)){
			spanValue();
			$("#bankAccValidate").html("格式错误！有非数字");
			flag = false;
		}else if(!reg.test(sacPeriod)){
			spanValue();
			$("#sacPeriodValidate").html("格式错误！有非数字");
			flag = false;
		}else if(!reg2.test(costRate)){
			spanValue();
			$("#costRateValidate").html("格式错误！");
			flag = false;
		}
		return flag;
	}
	
	function spanValue(){
		$("span[class='validateSpan']").each(function(){
			$(this).html("*");
		});
		$("#validateStr").html("");
	}
</script>
</head>
<body>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
		<div class="con ">
			<form id="addChannelParamForm" action="addChannelParam" method="post">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">渠道节点代码：</td>
							<td><input id="chnCode" name="chnCode" type="text"  class="txt2"/><span class="validateSpan" id="chnCodeValidate" style="color: red">*</span></td>
							<td></td>
							<td align="right">渠道名称：</td>
							<td><input id="chnName" name="chnName" type="text" class="txt2"/><span class="validateSpan" style="color: red">*</span></td>
						</tr>
						<tr>
							<td align="right">渠道类型：</td>
							<td>
								<select id ="chnType" name="chnType" class="select1">
									<option value="1">B2B支付</option>
									<option value="2">B2C支付</option>
								</select>
								<span class="validateSpan" style="color: red">*</span>
							</td>
							<td></td>
							<td align="right">币种：</td>
							<td>
								<select id ="currencyType" name="currencyType" class="select1">
									<c:forEach items="${currencyTypeMap}" var="ccy">
										<option value="${ccy.key}">${ccy.value}</option>
									</c:forEach>
								</select>
								<span class="validateSpan" style="color: red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">清算行节点代码：</td>
							<td><input id="bankNodeCode" name="bankNodeCode" type="text" class="txt2"/><span class="validateSpan" id="bankNodeCodeValidate" style="color: red">*</span></td>
							<td></td>
							<td align="right">清算行名称：</td>
							<td><input id="sacBankName" name="sacBankName" type="text" class="txt2"/><span class="validateSpan" style="color: red">*</span></td>
						</tr>
						<tr>
							<td align="right">开户名称：</td>
							<td><input id="accountName" name="accountName" type="text"  class="txt2"/><span class="validateSpan" style="color: red">*</span></td>
							<td></td>
							<td align="right">银行账号：</td>
							<td><input id="bankAcc" name="bankAcc" type="text" class="txt2"/><span class="validateSpan" id="bankAccValidate" style="color: red">*</span></td>
						</tr>
						<tr>
							<td align="right">联行号：</td>
							<td><input id="craccBankCode" name="craccBankCode" type="text"  class="txt2"/><span class="validateSpan" style="color: red">*</span></td>
							<td></td>
							<td align="right">有效标志：</td>
							<td>
								<select id ="isValidFlag" name="isValidFlag" class="select1">
									<option value="1">有效</option>
									<option value="0">无效</option>
								</select>
								<span class="validateSpan" style="color: red">*</span>
							</td>
						</tr>
						<tr>
							<td align="right">清算周期：</td>
							<td><input id="sacPeriod" name="sacPeriod" type="text" maxlength="3" class="txt2"/><span class="validateSpan" id="sacPeriodValidate" style="color: red">*</span></td>
							<td></td>
							<td align="right">成本费率：</td>
							<td><input id="costRate" name="costRate" type="text"  class="txt2"/><span class="validateSpan" id="costRateValidate" style="color: red">*</span></td>
						</tr>
						<tr>
							<td align="right">成本计算方式：</td>
							<td><input id="costComWay" name="costComWay" type="text"  class="txt2"/> </td>
							<td></td>
							<td align="right" >成本结算方式：</td>
							<td><input id="costSacWay" name="costSacWay" type="text" class="txt2"/></td>
						</tr>
						<tr>
							<td align="right">渠道成本扎差标志：</td>
							<td><input id="chnFeeFlag" name="chnFeeFlag" maxlength="4" type="text" class="txt2"/></td>
							<td></td>
							<td align="right">备注：</td>
							<td><input id="memo" name="memo" type="text" class="txt2"/></td>
						</tr>
						<tr>
							<td colspan="3" align="center"><span id="validateStr" style="color: red"></span></td>
							<td align="right"><input id="addChannelParamBtn" value="提交" type="button" class="bluebtn"/></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>