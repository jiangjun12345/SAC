<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>添加商户信息</title>
	
</head>
<body>
<!-- Body -->

    <div class="content">
      <div class="con ">
				<form:form commandName="ucOrgInfo" action="${ctx}/addOrgInfo" method="POST" enctype="multipart/form-data">   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">企业组织机构码：</td>
								<td width="40%">
								<span> <form:input path = "orgCode" class="txt2" id="orgCode" /> </span>
                                <span><form:errors path="orgCode" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">企业名：</td>
								<td width="40%">
								<span> <form:input path="orgName" class="txt2" id="orgName" /> </span>
										<span><form:errors path="orgName" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">企业短名：</td>
								<td width="40%">
								<span> <form:input  
										path="shortName" class="txt2" id="shortName" /> </span>
										<span><form:errors path="shortName" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">企业英文名：</td>
								<td width="40%">
								<span> <form:input  
										path="engName" class="txt2" id="engName" /> </span>
										<span><form:errors path="engName" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">企业注册国家：</td>
								<td width="40%">
								<span> <form:input  
										path="regCountry" class="txt2" id="regCountry" /> </span>
										<span><form:errors path="regCountry" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">企业所在国家：</td>
								<td width="40%">
								<span> <form:input  
										path="locCountry" class="txt2" id="locCountry" /> </span>
										<span><form:errors path="locCountry" cssStyle="color:red"/></span>
								</td>
							</tr>
							
								<tr>
								<td width="10%" align="right">法人：</td>
								<td width="40%">
								<span> <form:input  
										path="corporation" class="txt2" id="corporation" /> </span>
										<span><form:errors path="corporation" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">邮编：</td>
								<td width="40%">
								<span> <form:input  
										path="zip" class="txt2" id="zip" /> </span>
										<span><form:errors path="zip" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">办公地址：</td>
								<td width="40%">
								<span> <form:input  
										path="address" class="txt2" id="address" /> </span>
										<span><form:errors path="address" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">传真：</td>
								<td width="40%">
								<span> <form:input  
										path="fax" class="txt2" id="fax" /> </span>
										<span><form:errors path="fax" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">联系人：</td>
								<td width="40%">
								<span> <form:input  
										path="linkman" class="txt2" id="linkman" /> </span>
										<span><form:errors path="linkman" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">电话：</td>
								<td width="40%">
								<span> <form:input  
										path="phone" class="txt2" id="phone" /> </span>
										<span><form:errors path="phone" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">电子邮箱：</td>
								<td width="40%">
								<span> <form:input  
										path="email" class="txt2" id="email" /> </span>
										<span><form:errors path="email" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">企业类型：</td>
								<td width="40%">
								<select name="orgType" class="select1">
										<c:forEach items="${sysDicOrgTypeList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
							</tr>
							
							
							<tr>
								<td width="10%" align="right">客户号：</td>
								<td width="40%">
								<span> <form:input  
										path="customerCode" class="txt2" id="customerCode" /> </span>
										<span><form:errors path="customerCode" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">联系人职位：</td>
								<td width="40%">
								<span> <form:input  
										path="title" class="txt2" id="title" /> </span>
										<span><form:errors path="title" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">状态：</td>
								<td width="40%">
									<select name="status" id = "status" class="select1">
										<c:forEach items="${sysDicStatusList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">经营范围：</td>
								<td width="40%">
								<span> <form:input  
										path="dutyScope" class="txt2" id="dutyScope" /> </span>
										<span><form:errors path="dutyScope" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">法人：</td>
								<td width="40%">
								<span> <form:input  
										path="lawman" class="txt2" id="lawman" /> </span>
										<span><form:errors path="lawman" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">法人证件类型：</td>
								<td width="40%">
								<select name="lawmanPasstype" id = "lawmanPasstype" class="select1">
										<c:forEach items="${sysDicLawanPasstypeList}" var="sys">
											<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
						<%-- 		<span> <form:input  
										path="lawmanPasstype" class="txt2" id="lawmanPasstype" /> </span>
										<span><form:errors path="lawmanPasstype" cssStyle="color:red"/></span> --%>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">法人证件号码：</td>
								<td width="40%">
								<span> <form:input  
										path="lawmanPasscode" class="txt2" id="lawmanPasscode" /> </span>
										<span><form:errors path="lawmanPasscode" cssStyle="color:red"/></span>
								</td> 
								<td width="10%" align="right">审核人：</td>
								<td width="40%">
								<span> <form:input  
										path="auditUser" class="txt2" id="auditUser" /> </span>
										<span><form:errors path="auditUser" cssStyle="color:red"/></span>
								</td>
							</tr>
							<!-- 
							<tr>
								<td width="10%" align="right">批复状态：</td>
								<td width="40%">
								<select name="approvaledStatus" class="select1">
										<c:forEach items="${sysDicApprovalidStatusList}" var="sys">
											<option value="${sys.dicCode}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">批复备注：</td>
								<td width="40%">
								<span> <form:input  
										path="approvaledNotes" class="txt2" id="approvaledNotes" /> </span>
										<span><form:errors path="approvaledNotes" cssStyle="color:red"/></span>
								</td>
							</tr>
							 -->
							<tr>
								<td width="10%" align="right">企业联系电话：</td>
								<td width="40%">
								<span> <form:input  
										path="orgPhone" class="txt2" id="orgPhone" /> </span>
										<span><form:errors path="orgPhone" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">注册地址：</td>
								<td width="40%">
								<span> <form:input  
										path="regAddress" class="txt2" id="regAddress" /> </span>
										<span><form:errors path="regAddress" cssStyle="color:red"/></span>
								</td>
							</tr>
								<tr>
								<td width="10%" align="right">移动电话：</td>
								<td width="40%">
								<span> <form:input  
										path="mobilePhone" class="txt2" id="mobilePhone" /> </span>
										<span><form:errors path="mobilePhone" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">企业开户行：</td>
								<td width="40%">
								<span> <form:input  
										path="orgBranch" class="txt2" id="orgBranch" /> </span>
										<span><form:errors path="orgBranch" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">备注：</td>
								<td width="40%">
								<span> <form:input  
										path="memo" class="txt2" id="memo" /> </span>
										<span><form:errors path="memo" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">商户号：</td>
								<td width="40%">
								<span> <form:input  
										path="merchantNcode" class="txt2" id="merchantNcode" /> </span>
										<span><form:errors path="merchantNcode" cssStyle="color:red"/></span>
								</td>
							</tr>
							<tr>
						<td  align="right">
							营业执照扫描件：
						</td>
						<td style="width: 16%">
					     <input type="file" name="dutyLicenseFileParam" />
							<!--  <iframe src="/htmls/uploadFileExclude.jsp" id="queryFrame" name="queryFrame" frameborder="0" width="80%"
								height="80px;" align="left"></iframe>-->
						</td>
						<td align="right" style="width: 16%">
							机构代码证扫描件：
						</td>
						<td align="left" >
					     <input type="file" name="orgLicenseFileParam" />
							<!--  <iframe src="/htmls/uploadFileExclude.jsp" id="queryFrame" name="queryFrame" frameborder="0" width="80%"
								height="80px;" align="left"></iframe>-->
						</td>
					</tr>
						<tr>
						<td align="right">
							税务登记证：
						</td>
						<td align="left" >
					     <input type="file" name="taxyRegLicenseFileParam" />
							<!--  <iframe src="/htmls/uploadFileExclude.jsp" id="queryFrame" name="queryFrame" frameborder="0" width="80%"
								height="80px;" align="left"></iframe>-->
						</td>
						<td align="right">
							法人身份证：
						</td>
						<td >
					     <input type="file" name="lawManLicenseFileParam" />
							<!--  <iframe src="/htmls/uploadFileExclude.jsp" id="queryFrame" name="queryFrame" frameborder="0" width="80%"
								height="80px;" align="left"></iframe>-->
						</td>
					</tr>
					<tr>
					        <td width="10%" align="right">密钥：</td>
								<td width="100%" colspan="3" >
								<span> <form:input  
										path="merchantSecure" class="txt2" id="merchantSecure" /> </span>
										<span><form:errors path="merchantSecure" cssStyle="color:red"/></span>
							</td>
					</tr>
							<tr>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="submit" 
									 value="创建商户" class="btn1"
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