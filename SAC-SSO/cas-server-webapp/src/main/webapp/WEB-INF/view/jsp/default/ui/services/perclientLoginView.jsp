<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<link href="${ServiceDomain}/css/global.css" rel="stylesheet"
	type="text/css" />
<link href="${ServiceDomain}/css/layout.css" rel="stylesheet"
	type="text/css" />
<title>东方支付</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<script type="text/javascript">
    function submit(){
       $('#fm1').submit();
    }
    var contextPath = "${contextPath}";
</script>
<script type="text/javascript" src="<c:url value="/js/b2c.js" />"></script>
<script type="text/javascript" src="<c:url value="/js/jquery-1.9.1.js" />"></script>
</head>
<body style="background-color: transparent">
	<div class="header">
		<div class="center">
			<div class="left fontsize1 ">
			  <div class="mail"></div><div style="float:left; color:white">help@easipay.net</div>
			  <div class="icon"></div><div style="float:left; padding-left:8px;"><a href="${pepsDomain}/help.jsp">帮助中心</a></div>
			</div>
			<div class="right fontsize1 fontcolor1">
				<a href="#">登录</a>&nbsp;|&nbsp;<a
					href="${ServiceDomain}/register.html">免费注册</a>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div class="logo">
		<div class="left">
		</div>
		<div class="right">
		</div>
	</div>
	<div class="clear"></div>

	<div class="banner">
		<div class="login">
			<div class="border">
				<form:form method="post" id="fm1" commandName="${commandName}"
					htmlEscape="true">
					<div class="title fontsize1 fontcolor2">
						<div style="float: left">
							<spring:message code="screen.welcome.label.netid" />
						</div>
						<div style="float: left">
							<form:errors path="*" id="msg" cssStyle="color:red;"
								element="div" delimiter="&sbquo;"/>
						</div>
					</div>
					<div class="clear"></div>
					<div class="textbox">
						<form:input cssClass="txt1" maxlength="100" cssErrorClass="txt1"
							id="username" size="25" tabindex="1" path="username"
							autocomplete="false" htmlEscape="true" />
					</div>
					<div class="title fontsize1 fontcolor2">
						<spring:message code="screen.welcome.label.password" />
					</div>
					<div class="textbox">
						<form:password cssClass="txt1" cssErrorClass="txt1" id="password"
							tabindex="2" path="password" htmlEscape="true" autocomplete="off"
							onkeyup="submitx(event);" />
					</div>
					<div class="title fontsize1 fontcolor2">
						<spring:message code="screen.welcome.label.authcode" />
					</div>
					<div style="width: 285px;">
						<div class="textbox1" style="float: left">
							<form:input path="authCode" id="authCode" tabindex="3"
								cssErrorClass="txt2" cssClass="txt2" htmlEscape="true"
								autocomplete="off" />
						</div>
						<div style="float: right">
							<img id="imageCode" style="margin-top: 10px; float: right;"
								onclick="javascript:changeImageCode();"
								src="image/achieveAuthCode.html" />
						</div>
					</div>
					<input type="hidden" name="lt" value="${loginTicket}" />
					<input type="hidden" name="execution" value="${flowExecutionKey}" />
					<input type="hidden" name="_eventId" value="submit" />

					<div class="clear"></div>
					<div class="btn" onclick="submit();">
						<a href="javascript:void(0);"></a>
					</div>
					<div class="rp fontsize1 fontcolor2">
						<a href="${pepsDomain}/register.html">免费注册</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a	href="${pepsDomain}/getPwd.jsp">找回密码</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="part">
	    <div class="left"></div>
	</div>
	<div class="link fontsize1 fontcolor2">
		<a href="http://www.easipay.net/model/model/qygs.htm">关于东方支付</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
			href="contact.html">联系我们</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
			href="http://www.buyeasi.com">跨境通</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
			href="http://www.easipay.net/model/model/index.htm">东方支付主站</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
			href="privacy.html">隐私保护</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
			href="pact.html">法律协议</a>
	</div>
	<div class="copy fontsize1 fontcolor1">Copyright 2006-2013
		东方电子支付有限公司 版权所有 ICP备案:沪ICP11006075号</div>
</body>
</html>