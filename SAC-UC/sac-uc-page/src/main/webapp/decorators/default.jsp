<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/style.jsp"%>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="icon" href="<c:url value="/images/favicon.ico"/>"/>
    <title><decorator:title/></title>
    <decorator:head/>

</head>



<body <decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
       <%@ include file="/common/head.jspf" %>
         <div class="main">
       <%@ include file="/common/permission.jsp" %>
       <div class="content">
    	<div class="title fontsize2 fontfamily2 fontcolor1"><span>欢迎使用</span></div>
        <div class="detail">

       <decorator:body/>

        </div>
    </div>
    <div class="clear"></div>
</div>
<%@include file="/common/foot.jspf" %>
    
</body>
</html>
