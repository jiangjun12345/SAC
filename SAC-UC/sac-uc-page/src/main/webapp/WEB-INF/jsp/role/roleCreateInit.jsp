<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ page import="net.easipay.cbp.util.ConstantParams"%>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script src="${ctx}/scripts/js/role.js" type="text/javascript"></script>
		<title>添加角色</title>
	
</head>
<body>
<!-- Body -->

			<div class="con ">
 
				  <form:form commandName="ucRole" id ="roleCreateForm" action="${ctx}/roleCreate" method="POST">


					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%" align="right">角色编码：</td>
								<td width="90%">
									<span>
										 <form:input path="roleCode" id="roleCode" class="txt2" />
										 <span style="color: red;" class="important" id="roleCodeEMsg"/>
									</span> 
									<span>
										<form:errors path="roleCode" cssStyle="color:red" />
									</span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">角色名称：</td>
								<td width="90%">
								<span> <form:input path="roleName" id="roleName" class="txt2" /><span style="color: red;" class="important"  id="roleNameEMsg"></span> </span> 
								<span><form:errors path="roleName" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">角色描述：</td>
								<td width="90%">
								<span> <form:input path="description" id="description" class="txt2" /> <span style="color: red;" class="important"  id="descriptionEMsg"></span> </span> 
								<span><form:errors path="description" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td align="right">角色状态：</td>
								<td><select name="status" class="status">
										<option value=<%=ConstantParams.UC_ROLE_STATIC_OPEN %>>开启</option>
										<option value=<%=ConstantParams.UC_ROLE_STATIC_CLOSE %>>关闭</option>
								</select>
								</td>
							</tr>
							<tr>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="button" onclick="roleCreateCheck();" value="创建角色" class="btn1" id="submitbtn" />
								</td>
							</tr>
							
							</table>
					</div>
				 </form:form>
			</div>		


<!-- /Body -->
</body>
</html>