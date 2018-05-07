<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
       
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script src="${ctx}/scripts/js/resourceInfo.js" type="text/javascript"></script>
		<title>修改菜单</title>
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form commandName="resourceInfo" id = "resourceInfoEditForm" action="${ctx}/updateNode"  method="POST">   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						    <input type = "hidden" id = "resourceId" name = "resourceId" value = "${resourceInfo.resourceId}" />
						    <input type = "hidden" id = "parentId" name = "parentId" value = "${resourceInfo.parentId}" />
						    <input type = "hidden" id = "validFlag" name = "validFlag" value = "${resourceInfo.validFlag}" />
							<input type = "hidden" id = "createUser" name = "createUser" value = "${resourceInfo.createUser}" />
							<input type = "hidden" id = "memo" name = "memo" value = "${resourceInfo.memo}" />
							<tr>
								<td width="10%" align="right">系统代码：</td>
								<td width="90%">
								<select name="dicCode" class="select1">
										<c:forEach items="${sysDicSysList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == resourceInfo.dicCode}">selected</c:if> >${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
							</tr>
							<tr>
								<td align="right">菜单名称：</td>
								<td><span> <input type="text" 
										name="resourceName" class="txt2" id="resourceName" value ="${resourceInfo.resourceName}"/><span style="color: red;" class="important" id="resourceNameEMsg"/> </span>
										<span><form:errors path="resourceName" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td align="right">权限编号：</td>
								<td><span> <input type="text" 
										name="resourceCode" class="txt2" id="resourceCode" value ="${resourceInfo.resourceCode}"/><span style="color: red;" class="important" id="resourceCodeEMsg"/> </span>
										<span><form:errors path="resourceCode" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td align="right">菜单URL：</td>
								<td><span> <input type="text" value="${resourceInfo.resourceUrl}" id="resourceUrl"
										name="resourceUrl"  class="txt2" /> </span> <span class="clr"><span style="color: red;" class="important" id="resourceUrlEMsg"/></span>
										<span><form:errors path="resourceUrl" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td align="right">菜单描述：</td>
								<td><span> <input type="text" value="${resourceInfo.description}" id="description"
										name="description" class="txt2" /> </span> <span class="clr"><span style="color: red;" class="important" id="descriptionEMsg"/></span>
										<span><form:errors path="description" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td align="right">菜单序号：</td>
								<td><span> <input type="text" value="${resourceInfo.orderNum}" id="orderNum"
										name="orderNum" class="txt2" /> </span> <span class="clr"><span style="color: red;" class="important" id="orderNumEMsg"/></span>
								</td>
							</tr>
							<tr>
								<td align="right">菜单类型：</td>
								<td>
						
								<select name="resourceType" class="select1">
										<c:forEach items="${sysDicMenuList}" var="menu">
											<option value="${menu.dicValue}" <c:if test="${menu.dicValue == resourceInfo.resourceType}">selected</c:if> >${menu.dicDesc}</option>
										</c:forEach>
								</select>
					
								</td>
							</tr>

							<tr>
								<td align="right">&nbsp;</td>
								<td><input style="text-align: center" name="submitbtn" type="botton" value="修改菜单" class="btn1" onclick="resourceInfoEditCheck();"
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