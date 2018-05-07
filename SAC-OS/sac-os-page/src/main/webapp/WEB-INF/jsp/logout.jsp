<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/style.jsp"%>
<%@ include file="/common/head.jspf" %>
<%@ include file="/common/permission.jsp" %> --%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>  

<div id="logoutdiv"  style = "display: none"></div> 	

<script type="text/javascript">
$(document).ready(function() {
    	$("#logoutdiv").empty();
    	var imgString = "";
    	$('#listMe option').each(function(){ 
    		if( $(this).val() != ''){ 
    			imgString += '<img src="'+$(this).val()+'/j_spring_cas_security_logout"></img>'; 
    		} 
    		}); 
    	$("#logoutdiv1").html(imgString);
    	window.location.href = '${ctx}/j_spring_cas_security_logout';
	});
</script>
