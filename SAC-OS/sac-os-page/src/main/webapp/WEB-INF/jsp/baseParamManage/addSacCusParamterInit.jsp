<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	$(function(){
		$("#addSacCusParamterFormBtn").click(function(){
			if(validateData()){
				$("#addSacCusParamterForm").submit();	
			}
		});
	});
	
	function validateData(){
		var cusName = $("#cusName").val();
		var orgCardId = $("#orgCardId").val();
		
		var flag = true;

		if(cusName==""){//验证非空
			$("#cusNameValidate").html("必输项有空值！");
			flag = false;
		}
		if(orgCardId==""){//验证非空
			$("#orgCardIdValidate").html("必输项有空值！");
			flag = false;
		}		
	    if(orgCardId.length!=9){
			$("#orgCardIdValidate").html("格式错误！代码应为9位");
			flag = false;
		}
		return flag;
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
			<form id="addSacCusParamterForm" action="addSacCusParamter" method="post">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">商户名称：</td>
							<td><input id="cusName" name="cusName" type="text"  class="txt2"/><span class="validateSpan" id="cusNameValidate" style="color: red">*</span></td>
							<td></td>
							<td align="right">组织机构号：</td>
							<td><input id="orgCardId" name="orgCardId" type="text" class="txt2"/><span class="validateSpan" id="orgCardIdValidate" style="color: red">*</span></td>
						</tr>
						<tr>
							<td align="right">业务类型：</td>
							<td>
								<select id ="bussType" name="bussType" class="select1">
									<c:forEach items="${bussTypeList}" var="bussType">
										<option value="${bussType.dicValue}">${bussType.dicDesc}</option>
									</c:forEach>
								</select>
							</td>
							<td></td>
							<td align="right">币种：</td>
							<td>
								<select id ="sacCurrency" name="sacCurrency" class="select1">
									<c:forEach items="${currencyList}" var="ccy">
										<option value="${ccy.dicCode}">${ccy.dicDesc}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						
						<tr>
							<td colspan="3" align="center"><span id="validateStr" style="color: red"></span></td>
							<td align="right"><input id="addSacCusParamterFormBtn" value="提交" type="button" class="bluebtn"/></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</body>
</html>