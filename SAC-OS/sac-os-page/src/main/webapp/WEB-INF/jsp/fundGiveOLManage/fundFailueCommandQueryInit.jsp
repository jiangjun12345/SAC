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
		<title>线上划款失败指令处理</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function queryMsg() {
	var payAmount = document.getElementById("payAmount").value;
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
	$("#subForm").submit();
}
function clearText(){
	$("#payAmount").value="";
	$("#craccName").value="";
}

function dealFailue(cmdId){
	if(window.confirm("确定处理?")){
		var url = "dealCommandOfFailue";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				cmdId : cmdId
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("操作成功!");
				}else{
					alert("操作失败");
				}
				window.location.reload();
			},
			error : function(data) {
				alert("操作失败!");
				window.location.reload();
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
				<form:form id="subForm" action="${ctx}/fundFailueCommandQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">出款申请日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-90})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">出款申请日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:90})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="10%" align="right">收款账户名称：</td>
								<td width="25%">
									<input type="text" class="txt2" id="craccName" name="craccName" value="${craccName}"/>
								</td>
								<td width="10%" align="right">支付金额：</td>
								<td width="25%">
									<input type="text" class="txt2" id="payAmount" name="payAmount" value="${payAmount}"/>
									<span id = "payAmountError" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryMsg();"/>
								</td>
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
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                	<th class="border">序号</th>
                                	<th class="border">支付金额</th>
                                    <th class="border">支付币种</th>
                                    <th class="border">收款方代码</th>
                                    <th class="border">收款方账号</th>
                                    <th class="border">收款账户名称</th>
                                    <th class="border">付款方银行代码</th>
                                    <th class="border">付款方账户名称</th>
                                    <th class="border">出款申请时间</th>
                                    <th class="border">出款状态</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty detailList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${detailList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="30" id="${item.cmdId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="30" bgcolor="#eeeeee" id="${item.cmdId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.payCurrency}</td>
                                    <td class="fontfamily1">${item.crtCode}</td>
                                    <td class="fontfamily1">${item.craccNo}</td>
                                    <td class="fontfamily1">${item.craccName}</td>
                                    <td class="fontfamily1">${item.msgReceiver}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1">出款失败</td>
                                    <td class="fontfamily1"><input type="button" value="处理" onClick="dealFailue('${item.cmdId}');">&nbsp;</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/fundFailueCommandQueryInit"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
    </div>
<!-- /Body -->
</body>
</html>