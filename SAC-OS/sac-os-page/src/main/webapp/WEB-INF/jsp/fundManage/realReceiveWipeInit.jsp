<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>实收勾对</title>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript"
	src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function queryInfo() {
		var payAmount = document.getElementById("receiveTotSum").value;
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if (payAmount.length > 0) {
			var flag = payAmount.match(regexp);
			if (flag == null) {
				document.getElementById("receiveTotSumError").innerHTML = "格式非法";
				return;
			}
			if (payAmount.length > 20) {
				document.getElementById("receiveTotSumError").innerHTML = "长度超长";
				return;
			}
		}
		$("#subForm").submit();

	}

	function clearText() {
		document.getElementById("finSign").value = "";
		document.getElementById("currencyType").value = "";
	}

	function wipe(id, realTotAmountId) {
		var realTotAmount = document.getElementById(realTotAmountId).value;
		if (realTotAmount == null || realTotAmount == "") {
			alert("请输入实收总金额进行勾对处理");
			return;
		}
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if (realTotAmount.length > 0) {
			var flag = realTotAmount.match(regexp);
			if (flag == null) {
				alert("实收总金额格式非法");
				return;
			}
			if (realTotAmount.length > 20) {
				alert("实收总金额长度超长");
				return;
			}
		}
		if (window.confirm("确定勾对？")) {
			var url = "realReceiveWipe";
			$.ajax({
				url : url,
				cache : false,
				async : false,
				data : {
					id : id,
					realTotAmount : realTotAmount
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.success) {
						alert("勾对成功!");
						window.location.reload();
					} else {
						alert("勾对失败!");
						window.location.reload();
					}
				},
				error : function(data) {
					alert("勾对失败!");
				}
			});
		}
	}

	function checkAmount(realTotAmountId, flag) {
		var realTotAmount = document.getElementById(realTotAmountId).value;
		if (realTotAmount == null || realTotAmount == "") {
			alert("请输入实收总金额进行勾对处理");
			flag = ture;
			return flag;
		}
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if (realTotAmount.length > 0) {
			var flag = realTotAmount.match(regexp);
			if (flag == null) {
				alert("实收总金额格式非法");
				flag = ture;
				return flag;
			}
			if (realTotAmount.length > 20) {
				alert("实收总金额长度超长");
				flag = ture;
				return flag;
			}
		}
	}

	function batchWipe() {
		var box = document.getElementsByName("box");
		var count = 0;
		var failFlag = false;
		var boxValues = "";
		for (i = 0; i < box.length; i++) {
			if (box[i].checked) {
				count++;
				var boxValue = box[i].value;
				var boxId = box[i].id;
				var index = boxId.substring(8);

				var realTotAmount = document.getElementById("realTotAmount"
						+ index).value;
				if (realTotAmount == null || realTotAmount == "") {
					alert("请输入实收总金额进行勾对处理");
					failFlag = true;
					break;
				}
				var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
				if (realTotAmount.length > 0) {
					var flag = realTotAmount.match(regexp);
					if (flag == null) {
						alert("实收总金额格式非法");
						failFlag = true;
						break;
					}
					if (realTotAmount.length > 20) {
						alert("实收总金额长度超长");
						failFlag = true;
						break;
					}
				}

				boxValues = boxValues + boxValue + "_" + realTotAmount + "|";
			}
		}
		if (count == 0) {
			alert("至少要选择一个，谢谢");
		}
		if (count > 0 && !failFlag) {
			if (window.confirm("确定勾对？")) {
				var url = "realReceiveWipeByBatch";
				$.ajax({
					url : url,
					cache : false,
					async : false,
					data : {
						batchWipeStr : boxValues
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						if (data.success) {
							alert("勾对成功!");
							window.location.reload();
						} else {
							alert("勾对失败!");
							window.location.reload();
						}
					},
					error : function(data) {
						alert("勾对失败!");
					}
				});
			}
		}

	}

	//点击"全选/全不选"复选框，如果选中，则选中全部复选框，否则取消选中全部复选框  
	function checkboxall(Allname, name) {
		var ischecked = document.getElementById(Allname).checked;
		if (ischecked) {
			checkallbox(name);
		} else {
			discheckallbox(name);
		}
	}
	//选中全部复选框  
	function checkallbox(name) {
		var boxarray = document.getElementsByName(name);
		for ( var i = 0; i < boxarray.length; i++) {
			boxarray[i].checked = true;
		}
	}
	//取消选中全部复选框  
	function discheckallbox(name) {
		var boxarray = document.getElementsByName(name);
		for ( var i = 0; i < boxarray.length; i++) {
			boxarray[i].checked = false;
		}
	}
</script>
</head>
<body>
	<!-- Body -->

	<div class="content">

		<div class="con ">
			<form:form id="subForm" commandName="sacChnSettlement"
				action="${ctx}/realReceiveWipeInit" method="POST">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">应收总金额：</td>
							<td><span> <form:input path="receiveTotSum"
										class="txt2" id="receiveTotSum" />
							</span> <span id="receiveTotSumError" style="color: red;"></span> <span><form:errors
										path="receiveTotSum" cssStyle="color:red" />
							</span></td>
							<td align="right">币种：</td>
							<td><select id="currencyType" name="currencyType"
								class="select1">
									<option value="">全部</option>
									<c:forEach items="${currencyTypeList}" var="sys">
										<option value="${sys.dicCode}"
											<c:if test="${sys.dicCode == sacChnSettlement.currencyType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
									</c:forEach>
							</select></td>
							<td align="right">勾对标志：</td>
							<td><select id="finSign" name="finSign" class="select1">
									<option value="">全部</option>
									<c:forEach items="${sysDicStatusList}" var="sys">
										<option value="${sys.dicValue}"
											<c:if test="${sys.dicValue == sacChnSettlement.finSign}"> selected="selected"</c:if>>${sys.dicDesc}</option>
									</c:forEach>
							</select></td>
						</tr>

						<tr>
							<td align="right">银行节点代码：</td>
							<td><span> <form:input path="bankNodeCode"
										class="txt2" id="bankNodeCode" />
							</span> <span><form:errors path="bankNodeCode"
										cssStyle="color:red" />
							</span></td>
							<td align="right">清算行名称：</td>
							<td><span> <form:input path="sacBankName"
										class="txt2" id="sacBankName" /> </span> <span><form:errors
										path="sacBankName" cssStyle="color:red" />
							</span></td>
							<td align="right">到账日期()：</td>
							<td><form:input type="text" name="transDate"
									path="transDate" id="transDate" class="txt2"
									onfocus="WdatePicker({dateFmt:'yyyyMMdd',errDealMode:0});" readonly="readonly"
									value="${queryForm.date}" />
							</td>
						</tr>
						<tr>
							<td align="right">清算日期(起)：</td>
							<td><form:input type="text" name="startSacDate"
									path="startSacDate" id="startSacDate" class="txt2"
									onfocus="WdatePicker({dateFmt:'yyyyMMdd',errDealMode:0});" readonly="readonly"
									value="${startSacDate}" />
							</td>
							<td align="right">清算日期(止)：</td>
							<td><form:input type="text" name="endSacDate" path="endSacDate"
									id="endSacDate" class="txt2"
									onfocus="WdatePicker({dateFmt:'yyyyMMdd',errDealMode:0});" readonly="readonly"
									value="${endSacDate}" />
							</td>
							<td align="right"><input name="submitbtn" type="button"
								value="查询" class="bluebtn" id="submitbtn" onclick="queryInfo();" />
							</td>
							<td><input name="clearBtn" type="button" value="清除"
								class="bluebtn" id="clearBtn" onclick="clean();clearText();" />&nbsp;
								<input name="batchWipeClick" type="button" value="批量勾对"
								class="bluebtn" id="batchWipeClick" onclick="batchWipe();" /></td>
						</tr>

					</table>
				</div>
			</form:form>
		</div>
		<div class="table fontcolor4 fontsize1 fontfamily2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr height="35" bgcolor="#cccccc">
					<th class="border"><input id="checkall" type="checkbox"
						value="" onclick="checkboxall('checkall','box')" /> 全选</th>
					<th class="border">来款账号</th>
					<th class="border">银行节点代码</th>
					<th class="border">清算行名称</th>
					<th class="border">应收总金额</th>
					<th class="border">实收总金额</th>
					<th class="border">币种</th>
					<th class="border">总笔数</th>
					<th class="border">勾对标志</th>
					<th class="border">清算日期</th>
					<th class="border">到账日期</th>
					<th class="border">操作</th>
				</tr>
				<c:if test="${empty sacChnSettlementList }">
					<tr>
						<td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
					</tr>
				</c:if>
				<c:forEach items="${sacChnSettlementList}" var="item" varStatus="st">
					<c:choose>
						<c:when test="${st.index %2 == 0 }">
							<tr align="center" height="35">
						</c:when>
						<c:otherwise>
							<tr align="center" height="35" bgcolor="#eeeeee">
						</c:otherwise>
					</c:choose>
					<td><c:if test="${item.finSign eq 'N'}">
							<input type="checkbox" name="box" id="checkbox${st.index}"
								value="${item.id}" />
						</c:if>
					</td>
					<td class="fontfamily1">${item.accountNumber}</td>
					<td class="fontfamily1">${item.bankNodeCode}</td>
					<td class="fontfamily1">${item.sacBankName}</td>
					<td class="fontfamily1">${item.receiveTotSum}</td>
					<td><input id="realTotAmount${st.index}" style="width: 60px"
						<c:choose>
							<c:when test="${item.finSign eq 'Y'||item.bankNodeCode eq 'BOC0000'||item.bankNodeCode eq 'CMBC000'||item.bankNodeCode eq 'CCB0000'||item.bankNodeCode eq 'CMB0000'}">value="${item.realTotAmount}"</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${item.finSign eq 'Y'}">readonly="readonly"</c:when>
						</c:choose>
						/>
					</td>
					<td class="fontfamily1">
						<c:forEach items="${currencyTypeList}" var="sys">
							<c:if test="${sys.dicCode eq item.currencyType}">${sys.dicDesc}</c:if>
						</c:forEach>
					</td>
					<td class="fontfamily1">${item.totalNum}</td>
					<td class="fontfamily1"><c:if test="${item.finSign eq 'Y'}">已勾对</c:if>
						<c:if test="${item.finSign eq 'N'}">未勾对</c:if>
					</td>
					<td class="fontfamily1">${item.sacDate}</td>
					<td class="fontfamily1">${item.transDate}</td>
					<c:if test="${item.finSign eq 'N'}">
						<td class="fontfamily1"><input type="button" value="勾对"
							onClick="wipe('${item.id}','realTotAmount${st.index}');">&nbsp;
						
						</td>
					</c:if>
				</c:forEach>
			</table>
			<div style="width: 100%; height: 32px; text-align: right;"
				id="pageDiv" class="pagination1 btn">
				<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24"><easipay:pageNum
								pageSize="${pageSize}" count="${count}" pageNo="${pageNo}"
								url="/realReceiveWipeInit" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>





	<!-- /Body -->
</body>
</html>