<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<form:form method="post" id="fm1" commandName="${commandName}"
	htmlEscape="true">
	<div class="title fontsize1 fontcolor2">
		<div style="float:left"><spring:message code="screen.welcome.label.netid" /></div><div style="float:left"><form:errors path="*" id="msg" cssStyle="color:red;" element="div" delimiter="&sbquo;" /></div>
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
	    <input name="" type="text"  value="密码" class="txt1" id="password_tx"/>
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
			<img id="imageCode" style="margin-top: 10px;"
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
		<a id="registerLink" href="javascript:void(0);">免费注册</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a id="getPwdLink" href="javascript:void(0);">找回密码</a>
	</div>
</form:form>
<script type="text/javascript">
    var pepsDomain = '${pepsDomain}';
    $('#registerLink').click(function(){
    	top.location.href = pepsDomain + '/register.html';
    });
    $('#getPwdLink').click(function(){
    	top.location.href = pepsDomain + '/getPwd.jsp';
    });
</script>