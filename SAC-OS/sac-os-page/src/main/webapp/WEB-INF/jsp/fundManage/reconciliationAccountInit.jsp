<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>手工调账</title>
<script type="text/javascript">
	$(function(){
		changeCode1();
	})
	function changeCode1() {
		var code1D = document.getElementById("CODE1D").value;
		var code1C = document.getElementById("CODE1C").value;
		if(code1D=='1122'||code1D=='1002'||code1D=='1221'){
			document.getElementById("bussTypeD").style.visibility="hidden"
			document.getElementById("bussTypeDTD").style.visibility="hidden"
			document.getElementById("childAccTypeD").style.visibility="hidden"
			document.getElementById("childAccTypeDTD").style.visibility="hidden"
			document.getElementById("branchCodeD").style.visibility="visible"
			document.getElementById("branchCodeDTD").style.visibility="visible"
			
			
		}else{
			document.getElementById("bussTypeD").style.visibility="visible"
			document.getElementById("bussTypeDTD").style.visibility="visible"
			document.getElementById("childAccTypeD").style.visibility="visible"
			document.getElementById("childAccTypeDTD").style.visibility="visible"
			document.getElementById("branchCodeD").style.visibility="hidden"
			document.getElementById("branchCodeDTD").style.visibility="hidden"
		}
		
		if(code1C=='1122'||code1C=='1002'||code1C=='1221'){
			document.getElementById("bussTypeC").style.visibility="hidden"
			document.getElementById("bussTypeCTD").style.visibility="hidden"
			document.getElementById("childAccTypeC").style.visibility="hidden"
			document.getElementById("childAccTypeCTD").style.visibility="hidden"
			document.getElementById("branchCodeC").style.visibility="visible"
			document.getElementById("branchCodeCTD").style.visibility="visible"
		}else{
			document.getElementById("bussTypeC").style.visibility="visible"
			document.getElementById("bussTypeCTD").style.visibility="visible"
			document.getElementById("childAccTypeC").style.visibility="visible"
			document.getElementById("childAccTypeCTD").style.visibility="visible"
			document.getElementById("branchCodeC").style.visibility="hidden"
			document.getElementById("branchCodeCTD").style.visibility="hidden"
		}
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
		var url = "reconliciationAccountConfirm";
		
		var CODE1D = document.getElementById("CODE1D").value;
		if ("" == CODE1D || null == CODE1D) {
			document.getElementById('CODE1DError').innerHTML = "CODE1(付)为空";
			return;
		}
		var CODE1C = document.getElementById("CODE1C").value;
		if ("" == CODE1C || null == CODE1C) {
			document.getElementById('CODE1CError').innerHTML = "CODE1(收)为空";
			return;
		}
		
		var CODE2D = document.getElementById("CODE2D").value;
		if ("" == CODE2D || null == CODE2D) {
			document.getElementById('CODE2DError').innerHTML = "CODE2(付)为空";
			return;
		}
		var CODE2C = document.getElementById("CODE2C").value;
		if ("" == CODE2C || null == CODE2C) {
			document.getElementById('CODE2CError').innerHTML = "CODE2(收)为空";
			return;
		}
		
		var CODE3D = document.getElementById("CODE3D").value;
		if ("" == CODE3D || null == CODE3D) {
			document.getElementById('CODE3DError').innerHTML = "CODE3(付)为空";
			return;
		}
		var CODE3C = document.getElementById("CODE3C").value;
		if ("" == CODE3C || null == CODE3C) {
			document.getElementById('CODE3CError').innerHTML = "CODE3(收)为空";
			return;
		}
		var bussTypeD = "";
		var childAccTypeD = "";
		var branchCodeD = "";
		if(CODE1D!='1122'&&CODE1D!='1002'&&CODE1D!='1221'){
			bussTypeD = document.getElementById("bussTypeD").value;
			if ("" == bussTypeD || null == bussTypeD) {
				document.getElementById('bussTypeDError').innerHTML = "请选择业务类型(付)";
				return;
			}
			
			childAccTypeD = document.getElementById("childAccTypeD").value;
			if ("" == childAccTypeD || null == childAccTypeD) {
				document.getElementById('childAccTypeDError').innerHTML = "请选择功能账户类型(付)";
				return;
			}
		}else{
			branchCodeD = document.getElementById("branchCodeD").value;
			if ("" == branchCodeD || null == branchCodeD) {
				document.getElementById('branchCodeDError').innerHTML = "请选择分行(付)";
				return;
			}
		}
		var bussTypeC = "";
		var childAccTypeC = "";
		var branchCodeC = "";
		if(CODE1C!='1122'&&CODE1C!='1002'&&CODE1C!='1221'){
			bussTypeC = document.getElementById("bussTypeC").value;
			if ("" == bussTypeC || null == bussTypeC) {
				document.getElementById('bussTypeCError').innerHTML = "请选择业务类型(收)";
				return;
			}
			
			childAccTypeC = document.getElementById("childAccTypeC").value;
			if ("" == childAccTypeC || null == childAccTypeC) {
				document.getElementById('childAccTypeCError').innerHTML = "请选择功能账户类型(收)";
				return;
			}
		}else{
			branchCodeC = document.getElementById("branchCodeC").value;
			if ("" == branchCodeC || null == branchCodeC) {
				document.getElementById('branchCodeCError').innerHTML = "请选择分行(收)";
				return;
			}
		}
		
		var payCurrency = document.getElementById("payCurrency").value;
		if ("" == payCurrency || null == payCurrency) {
			document.getElementById('payCurrencyError').innerHTML = "请选择币种";
			return;
		}
		var payAmount = document.getElementById("payAmount").value;
		if ("" == payAmount || null == payAmount) {
			document.getElementById('payAmountError').innerHTML = "调账金额不能为空";
			return;
		}
		//var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		var regexp = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
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
				CODE1D : CODE1D,
				CODE1C : CODE1C,
				CODE2D : CODE2D,
				CODE2C : CODE2C,
				CODE3D : CODE3D,
				CODE3C : CODE3C,
				bussTypeD : bussTypeD,
				bussTypeC : bussTypeC,
				childAccTypeD : childAccTypeD,
				childAccTypeC : childAccTypeC,
				payCurrency : payCurrency,
				branchCodeC : branchCodeC,
				branchCodeD : branchCodeD,
				payAmount : payAmount
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					alert("调账成功!");
					window.location.reload()
				} else {
					if (data.valid) {
						document.getElementById(data.filed
								+ 'Error').innerHTML = data.message;
					} else {
						alert("调账失败!");
					}

				}
			},
			error : function(data) {
				alert("调账失败!");
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
						<td align="right">币种：</td>
						<td>
							<select id="payCurrency" name="payCurrency" class="select1" with="100%">
								<c:forEach items="${currencyTypeList}" var="sys">
									<option value="${sys.dicValue}">${sys.dicDesc}</option>
								</c:forEach>
							</select>
						</td>
						<td align="right" colspan="1">调账金额：</td>
						<td>
							<input id="payAmount" name="payAmount" type="text" maxlength="30" class="select1" />
							<span id="payAmountError" style="color: red;"></span>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">CODE1(付)：</td>
						<td width="30%">
							<select id="CODE1D" class="select1"  name="CODE1D" width="100px" onchange="changeCode1();" >
								<c:forEach items="${code1Map}" var="code1Map">
									<option value="${code1Map.key}">${code1Map.value}</option>
								</c:forEach>
							</select>
							<span id="CODE1DError" style="color: red;"></span>
						</td>
						<td width="20%" align="right">CODE1(收)：</td>
						<td width="30%">
							<select id="CODE1C" class="select1"  name="CODE1C" width="100px" onchange="changeCode1();">
								<c:forEach items="${code1Map}" var="code1Map">
									<option value="${code1Map.key}">${code1Map.value}</option>
								</c:forEach>
							</select>
							<span id="CODE1CError" style="color: red;"></span>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">CODE2(付)：</td>
						<td width="30%">
							<select id="CODE2D" class="select1"  name="CODE2D" width="100px" >
								<c:forEach items="${code2Map}" var="code2Map">
									<option value="${code2Map.key}">${code2Map.value}</option>
								</c:forEach>
							</select>
							<span id="CODE2DError" style="color: red;"></span>
						</td>
						<td width="20%" align="right">CODE2(收)：</td>
						<td width="30%">
							<select id="CODE2C" class="select1"  name="CODE2C" width="100px" >
								<c:forEach items="${code2Map}" var="code2Map">
									<option value="${code2Map.key}">${code2Map.value}</option>
								</c:forEach>
							</select>
							<span id="CODE2CError" style="color: red;"></span>
						</td>
					</tr>
					<tr>
						<td width="20%" align="right">CODE3(付)：</td>
						<td width="30%">
							<input id="CODE3D" name="CODE3D" type="text" maxlength="30" class="select1" />
							<span id="CODE3DError" style="color: red;"></span>
						</td>
						<td width="20%" align="right">CODE3(收)：</td>
						<td width="30%">
							<input id="CODE3C" name="CODE3C" type="text" maxlength="30" class="select1" />
							<span id="CODE3CError" style="color: red;"></span>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%" id="branchCodeDTD">分行(付)：</td>
						<td width="30%">
							<select id="branchCodeD" class="select1"  name="branchCodeD" width="100px" >
									<c:forEach items="${branchList}" var="sys">
										<option value="${sys.dicValue}" <c:if test="${sys.dicValue == '000000'}"> selected="selected"</c:if> >${sys.dicDesc}</option>
									</c:forEach>
							</select>
							<span id="branchCodeDError" style="color: red;"></span>
						</td>
						<td align="right" width="20%" id="branchCodeCTD">分行(收)：</td>
						<td>
							<select id="branchCodeC" class="select1" name="branchCodeC" width="100px" >
									<c:forEach items="${branchList}" var="sys">
										<option value="${sys.dicValue}" <c:if test="${sys.dicValue == '000000'}"> selected="selected"</c:if> >${sys.dicDesc}</option>
									</c:forEach>
							</select> 
							<span id="branchCodeCError" style="color: red;"></span>
						</td>
					</tr>
					<tr>
						<td align="right" width="20%" id="bussTypeDTD">业务类型(付)：</td>
						<td width="30%">
							<select id="bussTypeD" class="select1"  name="bussTypeD" width="100px" >
									<c:forEach items="${bussTypeList}" var="sys">
										<option value="${sys.dicValue}" >${sys.dicDesc}</option>
									</c:forEach>
							</select>
							<span id="bussTypeDError" style="color: red;"></span>
						</td>
						<td align="right" width="20%" id="bussTypeCTD">业务类型(收)：</td>
						<td>
							<select id="bussTypeC" class="select1" name="bussTypeC" width="100px" >
									<c:forEach items="${bussTypeList}" var="sys">
										<option value="${sys.dicValue}" >${sys.dicDesc}</option>
									</c:forEach>
							</select> 
							<span id="bussTypeCError" style="color: red;"></span>
						</td>
					</tr>
					<tr>
						<td align="right" id="childAccTypeDTD">功能账户类型(付)：</td>
 						<td>
 							<select id="childAccTypeD" class="select1"  name="childAccTypeD" width="100px">
								<c:forEach items="${childAccTypeMap}" var="childAccType">
									<option value="${childAccType.key}" >${childAccType.value}</option>
								</c:forEach>
							</select>
							<span id="childAccTypeDError" style="color: red;"></span>
 						</td>
						<td align="right" id="childAccTypeCTD">功能账户类型(收)：</td>
						<td>
							<select id="childAccTypeC" class="select1"  name="childAccTypeC" width="100px">
								<c:forEach items="${childAccTypeMap}" var="childAccType">
									<option value="${childAccType.key}" >${childAccType.value}</option>
								</c:forEach>
							</select> 
								<span id="childAccTypeCError" style="color: red;"></span>
						</td>
					</tr>
				</table>
				<div>
					<table align="center">
						<tr width="100%">
						<td align="center" width="100%">
							<input id="Pass" name="Pass" type="button" maxlength="30" class="bluebtn" value="调账确认" onclick="addConfirm();" />
						</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
