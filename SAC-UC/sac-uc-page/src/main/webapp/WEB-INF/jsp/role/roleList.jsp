<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <%@ page import="net.easipay.cbp.util.ConstantParams"%>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>添加菜单</title>

	<script type="text/javascript">
	function roleEdit(){
		 var chooseBox = document.getElementsByName("chename");
		 var checked_counts = 0; 
		 var check_id = "";
		 var checkObj = "";
		    for(var i=0;i<chooseBox.length;i++){  
		        if(chooseBox[i].checked){checked_counts++; 
		        checkObj = chooseBox[i];
		        }  
		    } 
		 if(checked_counts!=1){
			 alert("请选择一个角色进行修改！");
			 return;
		 }else{
			 check_id = checkObj.id;
		 }   
		 
		 window.location.href = '${ctx}/roleEditInit?id='+check_id;
	}
	 
    </script>
	
</head>
<body>
<!-- Body -->

				<div class="con">
					<c:if test="${!empty message}">
						<script type="text/javascript">
							alert('保存成功');
						</script>
					</c:if>
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="35" bgcolor="#cccccc">
								<th class="border">选择</th>
								<th class="border">角色编码</th>
								<th class="border">角色名称</th>
								<th class="border">角色描述</th>
								<th class="border">角色状态</th>
								<th class="border">操作</th>
							</tr>
							<c:if test="${empty ucRoleList }">
								<tr>
									<td class="fontfamily1" colspan="9" align="center">没有相关记录!</td>
								</tr>
							</c:if>
							<c:forEach items="${ucRoleList}" var="item" varStatus="st">
								<c:choose>
									<c:when test="${st.index %2 == 0 }">
										<tr align="center" height="35">
									</c:when>
									<c:otherwise>
										<tr align="center" height="35" bgcolor="#eeeeee">
									</c:otherwise>
								</c:choose>
								<td><input name="chename" id="${item.id}"
									type="checkbox" /></td>
								<td class="fontfamily1">${item.roleCode}</td>
								<td class="fontfamily1">${item.roleName}</td>
								<td class="fontfamily1">${item.description}</td>
								<c:if test="${item.status eq 'O'}">
									<td class="fontfamily1">开启</td>
								</c:if>
								<c:if test="${item.status eq 'C'}">
									<td class="fontfamily1">关闭</td>
								</c:if>
								<td class="fontfamily1"><a
									href="${ctx}/menuRoleTree?roleId=${item.id}">
										<span class="fontfamily1 fontcolor4" style="text-decoration:underline;color:blue">权限配置</span> </a>
								</td>
								</tr>
							</c:forEach>
						</table>
						<div style="width: 100%; height: 32px; text-align: right;"
							id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum
											pageSize="${pageSize}" count="${count}" pageNo="${pageNo}"
											url="/roleManage"/>
									</td>
								</tr>

							</table>
						</div>
					</div>
				</div>
				<div>
					<table>
						<tr>
							<td><input type="button" value="新增" onClick="location.href='${ctx}/roleCreateInit'">&nbsp;
							   <input type="button" value="修改" onClick="roleEdit()">&nbsp;
							</td>
						</tr>
					</table>
				</div>
			</div>

		</div>


<!-- /Body -->
</body>
</html>