<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>用户管理</title>
<%--   	<link rel="stylesheet" href="${ctx}/styles/zTreeStyle.css" type="text/css"/>
  	<link href="${ctx}/styles/global.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/layout.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/styles/pagination.css" rel="stylesheet" type="text/css" />
  	<script src="${ctx}/scripts/jquery.ztree.core-3.5.js" type="text/javascript"></script>
  	<script src="${ctx}/scripts/jquery.ztree.excheck-3.5.js" type="text/javascript"></script> --%>

	<script type="text/javascript">
	function clearText(){
		 ucUser.personName.value="";
		 ucUser.mobile.value="";
		 ucUser.email.value="";
		 ucUser.identifyCode.value="";
	}
	
	
	function resetUserPassword(id){
		 if(window.confirm("确定重置密码?")){
			    var url = "${ctx}/resetUserPassword";
				$.ajax( {
					url : url,
					cache : false,
					async : false,
					data : {
						id : id
					},
					type : "POST",
					dataType : "json",
					success : function(data) {
						//data=eval("("+data+")");
						if(data.success){
							alert("密码重置成功!");
							
						}else{
							alert("重置失败!");
						}
						
					},
					errror : function(data){
						alert("重置失败!");
					}
					
				});
		 }
	};
	
	function lockUser(id,personState){
		    var url = "${ctx}/lockUser";
			$.ajax( {
				url : url,
				cache : false,
				async : false,
				data : {
					id : id,
					personState : $("#href"+id).data("state") || personState
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					//data=eval("("+data+")");
					if(data.success){
						if(data.ucUser){
							if(data.ucUser.personState == "Y"){
							    $("#href"+id).text("注销用户")
							    $("#td"+id).text("可用")
							    $("#href"+id).data("state", "Y") 
							}else if(data.ucUser.personState == "N"){
							    $("#href"+id).text("锁定用户")
							    $("#td"+id).text("注销")
							    $("#href"+id).data("state", "N") 
							}else if(data.ucUser.personState == "F"){
							    $("#td"+id).text("锁定")
							    $("#href"+id).text("开启用户")
							    $("#href"+id).data("state", "F") 
							}
						}else{
							alert("操作失败!");
						}
					}else{
						alert("操作失败!");
					}
					
				},
				errror : function(data){
					alert("操作失败!");
				}
				
			});
	};
 	</script>
	
</head>
<body>
	<div class="con ">
				<form:form commandName="ucUser" action="${ctx}/selectUcUserByParamter" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" align="left">
							<tr>
								<td width="12%" align="right">用户名称：</td>
								<td width="30%">
								<span> <form:input path="personName" class="txt2" id="personName" /> </span>
										<span><form:errors path="personName" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">手机号：</td>
								<td width="30%">
								<span> <form:input path="mobile" class="txt2" id="mobile" /> </span>
										<span><form:errors path="mobile" cssStyle="color:red"/></span>
								</td>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
								</td>
							</tr>
							
							<tr>
								<td width="12%" align="right">邮箱：</td>
								<td width="30%">
								<span> <form:input path="email" class="txt2" id="email" /> </span>
										<span><form:errors path="email" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">证件号：</td>
								<td width="30%">
								<span> <form:input path="identifyCode" class="txt2" id="identifyCode" /> </span>
										<span><form:errors path="identifyCode" cssStyle="color:red"/></span>
								</td>
								
								
								<td align="right">&nbsp;</td>
								<td><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clearText();"/>
								</td>
							</tr>
						</table>
					</div>
				</form:form>
	</div>
	

    <div class="table fontcolor4 fontsize1 fontfamily2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                                    <th class="border">用户名称</th>
                                    <th class="border">手机号</th>
                                    <th class="border">邮箱</th>
                                    <th class="border">证件号</th>
                                    <th class="border">创建时间</th>
                                    <th class="border">用户状态</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty ucUserInfoList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${ucUserInfoList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.personName}</td>
                                    <td class="fontfamily1">${item.mobile}</td>
                                    <td class="fontfamily1">${item.email}</td>
                                    <td class="fontfamily1">${item.identifyCode}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1" id="td${item.id}"> <c:if test="${item.personState eq 'Y'}">可用</c:if><c:if test="${item.personState eq 'N'}">注销</c:if><c:if test="${item.personState eq 'F'}">锁定</c:if><c:if test="${item.personState eq 'B'}">已列入黑名单</c:if></td>
                                    <td class="fontfamily1"><a href="${ctx}/editUserInfoInit?id=${item.id}"> 修改</a>
                                    ||<a href="#" onclick="resetUserPassword('${item.id}');"> 密码重置</a>
                                    <%--
                                    ||<a id="href${item.id}" href="#" onclick="lockUser('${item.id}','${item.personState}')"> <c:if test="${item.personState eq 'Y'}">注销用户 |</c:if><c:if test="${item.personState eq 'N'}">锁定用户|</c:if><c:if test="${item.personState eq 'F'}">开启用户</c:if></a>
                                    </td> --%>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/userManageInit"/>
									</td>
								</tr>
							</table>
						</div>
    </div>



<!-- /Body -->
</body>
</html>