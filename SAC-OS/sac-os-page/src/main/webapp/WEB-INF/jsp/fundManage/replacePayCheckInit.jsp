<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>代付审核</title>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

//查询
function queryFundMsg() {
	$("#replacePayCheckInit").submit();
}
//清除
function clearText() {
	document.getElementById("createTime").value = "";
	document.getElementById("checkStatus").value = "";
}
//线上审核操作
function checkSucc(checkStatus,id,trxSerialNo){//审核结果
	if(confirm("确认？")){
		//通知后台处理
		$.ajax({
			url:"replacePayCheck",
			type:"POST",
			dataType:"json",
			data:{
				checkStatus:checkStatus,
				id:id,				
				trxSerialNo : trxSerialNo,
				result:"success"
			},
			success:function(data){
				if(data.success){
					alert("操作成功！");
					window.location.reload();
				}else{
					alert(data.message);
					window.location.reload();
				}
			},
			error:function(data){
				alert("审核提交异常,");
				window.location.reload();
			}	
		});	
	}
  }
  
</script>
</head>

<body>
	<div class="content">
		<div class="con ">
			<form id="replacePayCheckInit" name="replacePayCheckInit" action="replacePayCheckInit" method="POST">
				<div class="table fontcolor4 fontsize1 fontfamily2">
	            <table  width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12%" align="right">录入日期：</td>
							<td>
								<input type="text" name="createTime" id="createTime" class="txt2" value="${createTime}"
									onfocus="WdatePicker({readOnly:true,maxDate:'#F{\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})" />
							</td>
						<td align="right" colspan="1">审核状态：</td>
						<td>
							<select id="checkStatus" name="checkStatus" class="select1">
									<option value="1" <c:if test="${checkStatus eq '1'}"> selected="selected"</c:if>>审核通过</option>
									<option value="2" <c:if test="${checkStatus eq '2'}"> selected="selected"</c:if>>待审核</option>
							        <option value="3" <c:if test="${checkStatus eq '3'}"> selected="selected"</c:if>>审核未通过</option>
							</select> 
						</td>
					</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td>
								<input name="submitbtn" type="button" value="查询" class="bluebtn" id="submitbtn" onclick="queryFundMsg();" />
							</td>
							<td align="right">&nbsp;</td>
							<td>
								<input name="clearBtn" type="button" value="清除" class="bluebtn" id="clearBtn" onclick="clean();clearText();" />
							</td>
						</tr>
					</table>					
				</div>
			</form>
		</div>

		<div class="table fontcolor4 fontsize1 fontfamily2">
			<div width="500px" style="overflow: scroll;">
				<table width="1400px" border="0" cellpadding="0" cellspacing="0">
					<tr height="35" bgcolor="#cccccc">
						<th width="300px" class="border">操作</th>
						<th class="border">审核状态</th>
						<th class="border">到账类型</th>
						<th class="border">交易流水号</th>
						<th class="border">代付金额</th>
						<th class="border">手续费</th>
						<th class="border">币种</th>
						<th class="border">付款银行名称</th>
						<th class="border">付款银行节点号</th>
						<th class="border">付款方组织代码</th>
						<th class="border">收款客户名称</th>
						<th class="border">收款银行节点号</th>
						<th class="border">创建时间</th>
					</tr>
					<c:if test="${empty sacOtrxInfoList }">
						<tr>
							<td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
						</tr>
					</c:if>
   
					<c:forEach items="${sacOtrxInfoList}" var="item" varStatus="st">
						<c:choose>
							<c:when test="${st.index %2 == 0 }">
								<tr align="center" height="35">
							</c:when>
							<c:otherwise>
								<tr align="center" height="35" bgcolor="#eeeeee">
							</c:otherwise>
						</c:choose>
						<td class="fontfamily1" align="left">
							<c:if test="${item.memo eq '2'}">
							    <input id="checkSucc1" class="bluebtn" style="width: 90px;" type="button" value="审核通过" onclick="checkSucc('1','${item.id}','${item.trxSerialNo}');" />
								<input id="checkFail" class="bluebtn"  type="button" value="审核不通过" onclick="checkSucc('3','${item.id}','${item.trxSerialNo}');" />
							</c:if>
						</td>
						<td class="fontfamily1">
							<c:if test="${item.memo eq '1'}">审核通过</c:if>
							<c:if test="${item.memo eq '2'}">待审核</c:if>
							<c:if test="${item.memo eq '3'}">审核不通过</c:if> 
						</td>
						<td class="fontfamily1">
							<c:if test="${item.trxErrDealType eq '0'}">T+0到账</c:if>
							<c:if test="${item.trxErrDealType eq '1'}">T+1到账</c:if>
						</td>
						<td class="fontfamily1">${item.trxSerialNo}</td>
						<td class="fontfamily1">${item.payAmount}</td>
						<td class="fontfamily1">${item.cusCharge}</td>
						<td class="fontfamily1">${item.payCurrency}</td>
						<td class="fontfamily1">${item.draccBankName}</td>
						<td class="fontfamily1">${item.draccNodeCode}</td>
						<td class="fontfamily1">${item.draccCardId}</td>
						<td class="fontfamily1">${item.craccCusName}</td>
						<td class="fontfamily1">${item.craccNodeCode}</td>
						<td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						
					</c:forEach>
				</table>
			</div>
			<div style="width: 100%; height: 32px; text-align: right;" id="pageDiv" class="pagination1 btn">
				<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/replacePayCheckInit" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>