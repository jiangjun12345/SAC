<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ page import="net.easipay.cbp.model.UcRole"%>
    <%@ page import="net.easipay.cbp.util.ConstantParams"%>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script src="${ctx}/scripts/js/role.js" type="text/javascript"></script>
		<title>修改角色</title>
	
</head>
<body>
<!-- Body -->

			<div class="con ">
 
				  <form:form commandName="ucRole" id="roleEditForm" action="${ctx}/roleEdit" method="POST">
					<input type="hidden" id="id" name="id" value="${ucRole.id}"/>
                     <input type="hidden" id="createUserId" name="createUserId" value="${ucRole.createUserId}"/>
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						     
							<tr>
								<td width="10%" align="right">角色编码：</td>
								<td width="90%">
								
								<span> <input type="text" value="${ucRole.roleCode}" name="roleCode" id="roleCode" class="txt2" /> <span style="color: red;" class="important" id="roleCodeEMsg"/> </span> 
								<span><form:errors path="roleCode" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">角色名称：</td>
								<td width="90%">
								<span> <input type="text" value="${ucRole.roleName}" name="roleName" id="roleName" class="txt2" /> <span style="color: red;" class="important" id="roleNameEMsg"/> </span> 
								<span><form:errors path="roleName" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">角色描述：</td>
								<td width="90%">
								<span> <input type="text" value="${ucRole.description}" name="description" id="description" class="txt2" /><span style="color: red;" class="important" id="descriptionEMsg"/> </span>
								<span><form:errors path="description" cssStyle="color:red"/></span> 
								</td>
							</tr>
							<tr>
								<td align="right">角色状态</td>
								<td><select name="status" class="status">
									<c:choose>
										<c:when test="${(not empty ucRole) && ucRole.status eq 'O'}">
										   <option value="O"  selected>开启</option>
										   <option value="C">关闭</option>
										</c:when>
										<c:when test="${(not empty ucRole) && ucRole.status eq 'C'}">
										   <option value="O" >开启</option>
										   <option value="C"  selected>关闭</option>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>
								</select>
								</td>
							</tr>
							<tr>
								<td align="right">&nbsp;</td>
								<td><input style="text-align: center" name="submitbtn" type="botton" 
									 value="修改角色  " class="btn1"
									id="submitbtn" onclick="roleEditCheck();"/>
								</td>
							</tr>
							
							</table>
					</div>
				 </form:form>
			</div>		


<!-- /Body -->
</body>
</html>