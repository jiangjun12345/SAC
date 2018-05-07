<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>渠道参数复核</title>
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		var cmdState = "${cmdState}";
		$("#cmdState").find("option[value="+cmdState+"]").attr("selected",true);
	});
	
	function checkResult(id,result,index){//审核结果
		$("#checkSucc"+index).attr("disabled",true);
		$("#checkFail"+index).attr("disabled",true);
		$.ajax({
			url:"channelParamCheckResult",
			type:"GET",
			dataType:"json",
			data:{
				id:id,
				result:result
			},
			success:function(data){
				alert(data.message);
				$("form[name='splitPageForm']").submit();
			},
			error:function(data){
				alert("审核提交异常,"+data);
			}	
		});
	}
</script>
</head>
<body>
	<div class="content">
		<div class="con">
			<form id="channelParamCheck" action="channelParamCheck" method="post">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">创建时间(起):</td>
							<td><input type="text" class="txt2" id="startDate"
								name="startDate" value="${startDate}"
								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})" />
							</td>
							<td align="right">创建时间(止):</td>
							<td><input type="text" class="txt2" id="endDate"
								name="endDate" value="${endDate}"
								onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})" />
							</td>
						</tr>
						<tr>
							<td align="right">指令状态：</td>
							<td><select id="cmdState" name="cmdState" class="select1">
									<option value="1">待审核</option>
									<option value="2">审核通过</option>
									<option value="3">审核不通过</option>
							</select></td>
							<td align="right"><input name="submitbtn" type="submit"
								value="查询" class="bluebtn" id="submitbtn" />
							</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<div class="table fontcolor4 fontsize1 fontfamily2">
			<div width="500px" style="overflow: scroll;">
				<table width="1500px" border="0" cellpadding="0" cellspacing="0">
					<tr height="35" bgcolor="#cccccc">
						<th class="border">操作</th>
						<th class="border">状态</th>
						<th class="border">渠道名称</th>
						<th class="border">渠道节点代码</th>
						<th class="border">渠道类型</th>
						<th class="border">币种</th>
						<th class="border">清算行节点代码</th>
						<th class="border">清算行名称</th>
						<th class="border">开户名称</th>
						<th class="border">银行账号</th>
						<th class="border">联行号</th>
						<th class="border">清算周期</th>
						<th class="border">成本费率</th>
						<th class="border">成本计算方式</th>
						<th class="border">成本结算方式</th>
						<th class="border">渠道成本扎差标示</th>
						<th class="border">有效标志</th>
						<th class="border">创建日期</th>
					</tr>
					<c:if test="${empty sacChannelParamCmdList }">
						<tr>
							<td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
						</tr>
					</c:if>
					<c:forEach items="${sacChannelParamCmdList}" var="item"
						varStatus="st">
						<c:choose>
							<c:when test="${st.index %2 == 0 }">
								<tr align="center" height="35">
							</c:when>
							<c:otherwise>
								<tr align="center" height="35" bgcolor="#eeeeee">
							</c:otherwise>
						</c:choose>
						<td class="fontfamily1">
							<c:if test="${item.cmdState eq '1'}">
								<input id="checkSucc${st.index}" class="bluebtn" type="button" value="审核通过" onclick="checkResult('${item.id}','succ',${st.index});" />
								<input id="checkFail${st.index}" class="bluebtn" type="button" value="审核不通过" onclick="checkResult('${item.id}','fail',${st.index});" />
							</c:if>
						</td>
						<td class="fontfamily1">
							<c:if test="${item.cmdState eq '1'}">待审核</c:if>
							<c:if test="${item.cmdState eq '2'}">审核通过</c:if> 
							<c:if test="${item.cmdState eq '3'}">审核不通过</c:if>
						</td>
						<td class="fontfamily1">${item.chnName}</td>
						<td class="fontfamily1">${item.chnCode}</td>
						<td class="fontfamily1">
							<c:if test="${item.chnType eq '1'}">B2B支付</c:if>
							<c:if test="${item.chnType eq '2'}">B2C支付</c:if> 
							<c:if test="${item.chnType eq '3'}">存款银行</c:if>
						</td>
						<td class="fontfamily1">${item.currencyType}</td>
						<td class="fontfamily1">${item.bankNodeCode}</td>
						<td class="fontfamily1">${item.sacBankName}</td>
						<td class="fontfamily1">${item.accountName}</td>
						<td class="fontfamily1">${item.bankAcc}</td>
						<td class="fontfamily1">${item.craccBankCode}</td>
						<td class="fontfamily1">${item.sacPeriod}</td>
						<td class="fontfamily1">${item.costRate}</td>
						<td class="fontfamily1">${item.costComWay}</td>
						<td class="fontfamily1">${item.costSacWay}</td>
						<td class="fontfamily1">${item.chnFeeFlag}</td>
						<td class="fontfamily1">${item.isValidFlag}</td>
						<td class="fontfamily1">
							<fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss" />
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div style="width: 100%; height: 32px; text-align: right;" id="pageDiv" class="pagination1 btn">
				<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/channelParamCheck" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>