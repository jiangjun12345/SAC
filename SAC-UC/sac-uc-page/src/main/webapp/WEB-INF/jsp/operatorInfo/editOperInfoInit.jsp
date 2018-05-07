<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>操作员修改</title>
	
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form commandName="ucOperator" action="${ctx}/editUcOperator" method="POST" enctype="multipart/form-data">   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<form:input cssStyle="display:none" path = "createUserId" class="txt2" id="createUserId" />
							<tr>
								<td width="12%" align="right">登录名：</td>
								<td width="40%">
								<span> <form:input path = "loginName" class="txt2" id="loginName" /></span>
                                <span><form:errors path="loginName" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">中文名：</td>
								<td width="40%">
								<span> <form:input path="loginNameCh" class="txt2" id="loginNameCh" /> </span>
										<span><form:errors path="loginNameCh" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">手机号：</td>
								<td width="40%">
								<span> <form:input  
										path="mobile" class="txt2" id="mobile" disabled="true"/> </span>
								<span> <form:input  type="hidden"
										path="mobile" class="txt2" id="mobile" /> </span>
										<span><form:errors path="mobile" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">邮箱：</td>
								<td width="40%">
								<span> <form:input  
										path="email" class="txt2" id="email" disabled="true"/> </span>
								<span> <form:input  type="hidden"
										path="email" class="txt2" id="email" /> </span>
										<span><form:errors path="email" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">归属角色：</td>
								<td width="40%">
								<select name="roleId" class="select1">
										<c:forEach items="${ucRoleList}" var="sys">
											<option value="${sys.id}" <c:if test="${sys.id == ucOperator.roleId}"> selected="selected" </c:if> >${sys.roleName}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">用户类型：</td>
								<td width="40%">
								<select name="userType" class="select1" disabled="true">
										<c:forEach items="${sysDicOperTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == ucOperator.userType}"> selected="selected"</c:if> >${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">操作员状态：</td>
								<td width="40%">
								<select name="status" class="select1">
										<c:forEach items="${sysDicStatusList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == ucOperator.status}"> selected="selected"</c:if> >${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">客户号：</td>
								<td width="40%">
								<span> <form:input  
										path="orgId" class="txt2" id="orgId" /> </span>
										<span><form:errors path="orgId" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right" >备注：</td>
								<td colspan="3">
								<span> <form:input  
										path="memo" class="txt2" id="memo" /> </span>
										<span><form:errors path="memo" cssStyle="color:red"/></span>
								</td>
							</tr>
							<form:hidden path="id" />
							<tr>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="submit" 
									 value="修改操作员" class="btn1"
									id="submitbtn" />
								</td>
							</tr>
							
						</table>
					</div>
				</form:form>
			</div>
    </div>



<!-- /Body -->
</body>
</html>