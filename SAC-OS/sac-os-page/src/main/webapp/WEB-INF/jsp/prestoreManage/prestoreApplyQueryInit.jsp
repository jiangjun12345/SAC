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
		<title>预存手工销账经办</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
/* function queryApplyMsg(){
	var url = "prestoreApplyQuery";
	var startDate = $("#startDate").value;
	var endDate = $("#endDate").value;
	var applyCode = $("#applyCode").value;
	var applyOrgName = $("#applyOrgName").value;
	var payAmount = $("#payAmount").value;
	$.ajax({
		url : url,
		cache : false,
		async : false,
		data : {
			startDate : startDate,
			endDate : endDate,
			applyCode : applyCode,
			applyOrgName : applyOrgName,
			payAmount : payAmount,
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.success){
			}else{
			}
		},
		error : function(data) {
		}
	});
} */
function clearText(){
	document.getElementById("startDate").value="";
	document.getElementById("endDate").value="";
	document.getElementById("applyCode").value="";
	document.getElementById("payAmount").value="";
	document.getElementById("applyOrgName").value="";
}

</script>
</head>
<body>
<!-- Body -->
      <div class="con ">
      			<div>
					<span style="font-size: 13px;color: #666666; font-family: 微软雅黑;">预存申请信息:</span>
				</div>
				<form:form id="subForm" action="${ctx}/prestoreApplyQuery" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">申请日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-2000})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">申请日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:2000})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="12%" align="right">八位码：</td>
								<td width="30%">
							    <input  class="txt2" name ="applyCode" id="applyCode" value="${applyCode}"/>
								</td>
								<td width="12%" align="right">企业名称：</td>
								<td width="30%">
								 <input  name="applyOrgName" class="txt2" id="applyOrgName"  value="${applyOrgName}"/>
								</td>
							</tr>
							<tr>
								<td width="12%" align="right">金额(CNY)：</td>
								<td width="30%">
								<input   class="txt2" id="payAmount" name="payAmount"   value="${payAmount}"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryApplyMsg();"/>
								</td>
								<td width="10%" align="left">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clearText();"/>
								</td>
							</tr>
							
							
						</table>
					</div>
				</form:form>
			</div>
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                    <th class="border">选择</th>
                                    <th class="border">申请日期</th>
                                    <th class="border">八位码</th>
                                    <th class="border">企业名称</th>
                                    <th class="border">金额</th>
                                    <th class="border">状态</th>
                                </tr>
                                <c:forEach items="${applyList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="25" id="${item.chargeApplyId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="25" bgcolor="#eeeeee" id="${item.chargeApplyId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td> <input type="radio" name = "apply" value="${item.chargeApplyId}"/></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.applyDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1">${item.applyCode}</td>
                                    <td class="fontfamily1">${item.applyOrgName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1"><c:if test="${item.applyState eq '0'}">未核销</c:if><c:if test="${item.applyState eq '1'}">自动销账</c:if><c:if test="{${item.applyState eq '2'}">手工销账</c:if></td>
                                    </tr>
                                </c:forEach>
                            </table>
                             <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/prestoreApplyQuery"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
<!-- /Body -->
</body>
</html>