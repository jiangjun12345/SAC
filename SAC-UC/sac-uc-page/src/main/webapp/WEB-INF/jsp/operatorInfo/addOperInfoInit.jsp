<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>操作员创建</title>

  <script type="text/javascript">
  var validMobile = true;
  var validEmail = true;
  function checkMobile(mobile){
	    var url = "${ctx}/checkRegister";
	    $("#errorMobile").html("");
		$("#spaName1").html("");
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				mobile : mobile
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				//data=eval("("+data+")");
				if(data.mobile){
					validMobile = true;
				}
				if(data.mobile && validEmail){
					//$("#spaName1").html("<font color=red>该手机可以使用</font>");  
					document.getElementById("submitbtn").disabled=false;
				}else if(!data.mobile){
					$("#spaName1").html("<font color=red>该手机号已被注册</font>");  
					document.getElementById("submitbtn").disabled=true;
					validMobile = false;
				}
				
			},
			errror : function(data){
				alert("操作有误，请联系管理员!");
			}
			
		});
	};
	
  function checkEmail(email){
	    var url = "${ctx}/checkRegister";
	    $("#errorEmail").html("");
		$("#spaName").html("");
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				email : email
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				//data=eval("("+data+")");
				if(data.email){
					validEmail = true;
				}
				if(data.email && validMobile){
					//$("#spaName").html("<font color=red>邮箱可以使用</font>");  
					document.getElementById("submitbtn").disabled=false;
				}else if(!data.email){
					$("#spaName").html("<font color=red>该邮箱已被注册</font>");  
					document.getElementById("submitbtn").disabled=true;
					validEmail = false;
				}
				
			},
			errror : function(data){
				alert("操作有误，请联系管理员!");
			}
			
		});
	};

  </script>
	
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form commandName="ucOperator" action="${ctx}/addUcOperator" method="POST" enctype="multipart/form-data">   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">登录名：</td>
								<td width="40%">
								<span> <form:input path = "loginName" class="txt2" id="loginName" /> </span>
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
										path="mobile" class="txt2" id="mobile" onblur="checkMobile(this.value)"/> </span>
										<span id="errorMobile"><form:errors path="mobile" cssStyle="color:red"/></span>
										<span id="spaName1" ></span>
								</td>
								<td width="10%" align="right">邮箱：</td>
								<td width="40%">
								<span> <form:input  
										path="email" class="txt2" id="email" onblur="checkEmail(this.value)" /> </span>
										<span id="errorEmail"><form:errors path="email" cssStyle="color:red"/></span>
										<span id="spaName" ></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">归属角色：</td>
								<td width="40%">
								<select name="roleId" class="select1">
										<c:forEach items="${ucRoleList}" var="sys">
											<option value="${sys.id}">${sys.roleName}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">用户类型：</td>
								<td width="40%">
								<select name="userType" class="select1">
										<c:forEach items="${sysDicOperTypeList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
							</tr>
							
								<tr>
								<td width="10%" align="right">操作员状态：</td>
								<td width="40%">
								<select name="status" class="select1">
										<c:forEach items="${sysDicStatusList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">机构号：</td>
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
							<tr>
								<td align="right">&nbsp;</td>
								<td><input id ="submitbtn"  name="submitbtn" type="submit" 
									 value="创建操作员" class="btn1"
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