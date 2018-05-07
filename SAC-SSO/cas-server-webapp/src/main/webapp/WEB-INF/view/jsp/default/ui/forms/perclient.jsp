<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"/>
<form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
<div class="login_frame" style="top: 0px;right: 0;">
	<fieldset>
		<div class="title" style="margin-bottom: 0px;">登录东方支付</div>
		<div style="height: 15px;">
	        <div><form:errors path="*" id="msg" cssStyle="color:red;" element="div" delimiter="&sbquo;" /></div>
	    </div>
        <div>
        	<div class="ui-item"></div>
            <div class="clear5">&nbsp;</div>
            <div><label><span class="ui-icon username"></span></label>
                <input name="" type="text"  value="邮箱/手机号" class="ui-input" style="width: 151px;" id="username_tx" tabindex="1"/>
                <form:input cssClass="ui-input" cssErrorClass="ui-input" cssStyle="width: 151px;display:none;" id="username" tabindex="1" path="username" autocomplete="false" htmlEscape="true" style="display:none;"/>
            </div>
            <div class="clear5">&nbsp;</div>
            <div><label><span class="ui-icon password"></span></label>
                <input type="text"  value="密码" class="ui-input" style="width: 151px;" id="password_tx" tabindex="2"/>
                <form:password cssClass="ui-input" cssErrorClass="ui-input" cssStyle="width: 151px;display:none;" id="password" tabindex="2" path="password" htmlEscape="true" autocomplete="off" style="display:none;"/>
            </div>
            <div class="clear5">&nbsp;</div>
            <div>
                <div style="float: left;">
                   <input type="text" value="验证码" class="ui-input" id="authCode_tx" style="width: 93px;" tabindex="3">
                   <form:input path="authCode" id="authCode" tabindex="3" cssErrorClass="ui-input" cssClass="ui-input" htmlEscape="true" autocomplete="off" style="width:93px;display:none;"/>
                </div>
                <div style="float: right;margin-right:20px;margin-top:2px; cursor: pointer;">
                    <img id="imageCode" onclick="javascript:changeImageCode();" src="image/achieveAuthCode.html?t=<%=Math.random()%>" />
                </div>
            </div>
            <div class="clear5">&nbsp;</div>
        </div>
        <div class="blue_small right" style="padding-right: 10px;"><a href="${pwdUrl}" target="_top">忘记登录密码？</a></div>
        <div style="margin-top: 5px;" style="padding-right: 10px;">
            <input style="width: 100px;display: inline-block;" onclick="submit();" type="button" class="purple_bigbutton" name="登录" value="登录"/>
            <span style="margin-right:10px;"></span>
    	    <input style="width: 100px;display: inline-block;" onclick="register();" type="button" class="blue_bigbutton" name="注册" value="注册"/>
        </div>
        <div>
           <!-- 
        	<a class="grey_small left" target="_top">账户激活</a>
            <a class="blue_small register" href="${regUrl}" target="_top">免费注册</a> -->
        </div>
        <input type="hidden" name="lt" value="${loginTicket}" />
		<input type="hidden" name="execution" value="${flowExecutionKey}" />
		<input type="hidden" name="_eventId" value="submit" />
    </fieldset>
</div>
</form:form>
<script type="text/javascript">
     function submit(){
        $('#fm1').submit();
     }
     
     //注册
     function register(){
    	 window.top.location.href = '${regUrl}';
     }
     var contextPath = "${contextPath}";
     
     $(function(){
    	//用户名
         var username_tx = document.getElementById("username_tx"), username = document.getElementById("username");
         username_tx.onfocus = function(){
            if(this.value != "邮箱/手机号") return;
            this.style.display = "none";
            username.style.display = "";
            username.value = "";
            username.focus();
         };
         username.onblur = function(){
            if(this.value != "") return;
            this.style.display = "none";
            username_tx.style.display = "";
            username_tx.value = "邮箱/手机号";
         };
         
         //密码
         var password_tx = document.getElementById("password_tx"), password = document.getElementById("password");
         password_tx.onfocus = function(){
            if(this.value != "密码") return;
            this.style.display = "none";
            password.style.display = "";
            password.value = "";
            password.focus();
         };
         password.onblur = function(){
            if(this.value != "") return;
            this.style.display = "none";
            password_tx.style.display = "";
            password_tx.value = "密码";
         };
         
         //验证码
         var authCode_tx = document.getElementById("authCode_tx"), authCode = document.getElementById("authCode");
         authCode_tx.onfocus = function(){
            if(this.value != "验证码") return;
            this.style.display = "none";
            authCode.style.display = "";
            authCode.value = "";
            authCode.focus();
         };
         authCode.onblur = function(){
            if(this.value != "") return;
            this.style.display = "none";
            authCode_tx.style.display = "";
            authCode_tx.value = "验证码";
         };
     });
</script>