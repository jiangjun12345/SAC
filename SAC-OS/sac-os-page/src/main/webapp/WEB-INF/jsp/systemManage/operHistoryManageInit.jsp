<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>操作日志查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	function clearText(){
		document.getElementById("operType").value=""; 
	}
</script>

</head>
<body>
    <div class="content">
    <div class="con">
    	<form:form commandName="sacOperHistory" action="${ctx}/operHistoryManageInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">操作人手机号：</td>
								<td width="30%">
								<span> <form:input  
										path="userId" class="txt2" id="userId" /> </span>
										<span><form:errors path="userId" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">操作人名称：</td>
								<td width="30%">
								<span> <form:input  
										path="userName" class="txt2" id="userName" /> </span>
										<span><form:errors path="userName" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">操作类型：</td>
									<td width="30%">
									<select id="operType" name="operType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${operTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacOperHistory.operType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="12%" align="right">操作时间：</td>
								<td width="20%"><form:input type="text" name="createTime" path="createTime"
											id="createTime" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}" style="width: 63%" /></td>
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
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                                    <th class="border">操作人手机号</th>
                                    <th class="border">操作人名称</th>
                                    <th class="border">操作类型</th>
                                    <th class="border">操作时间</th>
                                    <th class="border">登录IP</th>
                                </tr>
                                <c:if test="${empty sacOperHistoryList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacOperHistoryList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.userId}</td>
                                    <td class="fontfamily1">${item.userName}</td>
                                    <td class="fontfamily1">${item.operType}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1">${item.loginIp}</td>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/operHistoryManageInit"/>
									</td>
								</tr>
							</table>
						</div>
             </div>
	</div>

<!-- /Body -->
</body>
</html>