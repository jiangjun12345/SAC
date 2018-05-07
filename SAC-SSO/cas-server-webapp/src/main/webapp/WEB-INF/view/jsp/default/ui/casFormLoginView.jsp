<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="org.jasig.cas.authentication.principal.Service" %>
<%@ page import="net.easipay.pepp.peps.util.ServiceUtil" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <c:set var="contextPath" value="${pageContext.servletContext.contextPath}"></c:set>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link href="${ServiceDomain}/1.1.0/css/global.css" rel="stylesheet" type="text/css"/>
        <link href="${ServiceDomain}/1.1.0/css/layout.css" rel="stylesheet" type="text/css"/>
        <script type="text/javascript" src="${ServiceDomain}/1.1.0/js/jquery-1.10.2.js"></script>
        <script type="text/javascript" src="${ServiceDomain}/1.1.0/js/jquery.validate.js"></script>
        <script type="text/javascript" src="${ServiceDomain}/1.1.0/js/jquery-ui-1.10.3.min.js"></script>     
        <script type="text/javascript">
            function submit(){
        	    $('#fm1').submit();
            }
            var contextPath = "${contextPath}";
        </script>
	    <script type="text/javascript" src="<c:url value="/js/b2c.js" />"></script>
	</head>
	<body style="background-color:transparent">
	    <jsp:include page="${formPageName}" />
    </body>
</html>
