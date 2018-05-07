<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <%@ taglib uri="http://cbp.easipay.net/tags/simtag" prefix="sim" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>操作员管理</title>

	<script type="text/javascript">
		function clearText(){
			 ucOperator.loginName.value="";
			 ucOperator.loginNameCh.value="";
			 ucOperator.status.value="";
		}
		function resetPassword(id){
			 if(window.confirm("确定重置密码?")){
				    var url = "${ctx}/resetPassword";
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
 	</script>
	
</head>
<body>
    <%-- <sim:permission previlege="001001001">
      <a href="">重置密码</a>
    </sim:permission> --%>
      <div class="con ">
				<form:form commandName="ucOperator" action="${ctx}/selectUcOperatorByParamter" method="POST" enctype="multipart/form-data">   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">登录名：</td>
								<td width="30%">
								<span> <form:input path = "loginName" class="txt2" id="loginName" /> </span>
								<span><form:errors path="loginName" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">中文名：</td>
								<td width="30%">
								<span> <form:input path="loginNameCh" class="txt2" id="loginNameCh" /> </span>
										<span><form:errors path="loginNameCh" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">用户状态：</td>
								<td width="30%" >
								<select name="status" class="select1" >
										<c:forEach items="${sysDicStatusList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
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
                                    <th class="border">登录名</th>
                                    <th class="border">中文名</th>
                                    <th class="border">手机号</th>
                                    <th class="border">邮箱</th>
                                    <th class="border">创建时间</th>
                                    <th class="border">商户号</th>
                                    <th class="border">用户状态</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty ucOperInfoList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="9" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${ucOperInfoList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.loginName}</td>
                                    <td class="fontfamily1">${item.loginNameCh}</td>
                                    <td class="fontfamily1">${item.mobile}</td>
                                    <td class="fontfamily1">${item.email}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1">${item.orgId}</td>
                                    <td class="fontfamily1" > <c:if test="${item.status eq 'Y'}">可用</c:if><c:if test="${item.status eq 'F'}">禁用</c:if><c:if test="${item.status eq 'D'}">删除</c:if></td>
                                    <td class="fontfamily1"><a href="${ctx}/editOperInfoInit?id=${item.id}"> 修改</a>
                                    ||<a href="#" onclick="resetPassword('${item.id}');">密码重置</a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"
							id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum
											pageSize="${pageSize}" count="${count}" pageNo="${pageNo}"
											url="/operManageInit"/>
									</td>
								</tr>

							</table>
						</div>
    </div>

<!-- /Body -->
</body>



</html>