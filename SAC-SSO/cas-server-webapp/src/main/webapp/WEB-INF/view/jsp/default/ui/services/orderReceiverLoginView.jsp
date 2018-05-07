<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}"></c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>东方支付|登录</title>
<link href="${contextPath}/1.0.2/css/global.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/1.0.2/css/layout.css" rel="stylesheet" type="text/css" />
<!-- 
<link href="themes/start/minified/jquery-ui.min.css" rel="stylesheet" type="text/css" />
 -->
<script type="text/javascript" src="${contextPath}/1.0.2/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${contextPath}/1.0.2/js/jquery.validate.js"></script>
<script type="text/javascript" src="${contextPath}/1.0.2/js/jquery-ui-1.10.3.min.js"></script>
<script id="js_ucstarwebstat" src="${contextPath}/js/webstat/ucwebstat.js"></script>
<script id="js_ucstarmonitor" src="${contextPath}/js/webstat/ucmonitor.js"></script>
<script type="text/javascript" src="${contextPath}/js/b2c.js"></script>
<script type="text/javascript">ucws_init('','0');</script>
</head>
<body> 
    <div id="header">
        <div class="header_content">
            <div class="left">
                <span class="hotcall">热线电话： 400-921-6677</span>
                <span class="customqq" onclick="ucws_preloginWin('d028579f0f5800131250','','')">在线客服</span>
            </div>
            <div class="right">
                <!-- 
                <span>你好</span> |
                <span><a href="#">注册</a></span> -->
            </div>
        </div>
    </div>
    <div id="header_menu" class="h_logo fontsize6 fontfamily2 fontcolor2">
       <!--   <a href="${pepsDomain}"></a>-->
        <div class="left logo"></div>
            <ul class="right header_menu">
                <!-- 
                <li class="selected no_padding">首页</li>
                <li class="no_padding">产品服务</li>
                <li class="no_padding">安全保障</li>
                <li class="no_padding">帮助中心</li> -->
            </ul>
    </div>
    <div id="login_banner" class="login-bg">
    	<div class="login_box">
    			<div class="easipay-adv"></div>
            <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
        	<div class="login_frame">
            	<fieldset>
            		<div class="title" style="margin-bottom: 0px;">登录东方支付</div>
	                <div style="height: 15px; font-size:12px;">
	                    <form:errors path="*" id="msg" cssStyle="color:red;" element="div" delimiter="&sbquo;" />
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
                             <input type="text"  value="密码" class="ui-input" id="password_tx" style="width: 151px;" tabindex="2"/>
                             <form:password cssClass="ui-input" cssErrorClass="ui-input" cssStyle="width: 151px;display:none;" id="password" tabindex="2" path="password" htmlEscape="true" autocomplete="off" style="display:none;"/>
                        </div>
                        <div class="clear5">&nbsp;</div>
                        <div>
                          <div style="float: left;">
                              <input type="text" value="验证码" class="ui-input" id="authCode_tx" style="width: 93px;" tabindex="3"/>
                              <form:input path="authCode" id="authCode" tabindex="3" cssErrorClass="ui-input" cssClass="ui-input" htmlEscape="true" autocomplete="off" style="width:93px;display:none;"/>
                          </div>
                          <div style="float: left;cursor: pointer;">
                              <img id="imageCode" style="height: 39px; width: 98px;" onclick="javascript:changeImageCode();" src="image/achieveAuthCode.html" />
                          </div>
                        </div>
                        <div class="clear5">&nbsp;</div>
                    </div>
                  <!--   <div class="blue_small right" style="padding-right: 10px;"><a href="${pwdUrl}">忘记登录密码？</a></div> -->
                    <div style="margin-top: 5px;" style="padding-right: 10px;">
                        <input style="width: 100px;display: inline-block;" onclick="submit();" type="button" class="purple_bigbutton" name="登录" value="登录"/>
                      <!--    <span style="margin-right:10px;"></span>
    	                <input style="width: 100px;display: inline-block;" onclick="register();" type="button" class="blue_bigbutton" name="注册" value="注册"/> -->
                    </div>
                    <div>
                        <!-- 
                    	<a class="grey_small left">账户激活</a>
                        <a class="blue_small register" href="${regUrl}">免费注册</a> -->
                    </div>
                    <input type="hidden" name="lt" value="${loginTicket}" />
		            <input type="hidden" name="execution" value="${flowExecutionKey}" />
		            <input type="hidden" name="_eventId" value="submit" />
                </fieldset>
            </div>
            </form:form>
        </div>
    </div>
    <div>
    	<ul id="advs" class="horizontal-ul">
    	     <li>
    			<div class="adv01"></div>
    			<div class="adv_item">
    				<div class="caption center">海淘 购买海外正品</div>
    				<div class="info">
    					<div><span class="title">阳光纳税</span><span>享受行邮税政策、便捷纳税</span></div>
    					<div></div>
    					<div><span class="title">快速通关</span><span>监管信息齐全、快速通关</span></div>
    				</div>
    			</div>
    		</li>
    		<li><div class="adv02"></div>
    			<div class="adv_item">
    				<div class="caption center">安全 全方位安全保障</div>
    				<div class="info">
    					<div><span class="title">个人信息安全</span><span>保障个人信息、交易数据安全</span></div>
    					<div></div>
    					<div><span class="title">商户信息安全</span><span>清单明晰，保障资金和交易安全</span></div>
    				</div>
    			</div>
    		</li>
    		<li><div class="adv03"></div>
    			<div class="adv_item">
    				<div class="caption center">商户合作 商户的不二之选</div>
    				<div class="info">
    					<div><span class="title">跨境结算</span><span>支持多币种结算方式</span></div>
    					<div><span class="title"></span></div>
    				</div>
    			</div>
    		</li>
    	</ul>
    </div>
    <div id="footer-other">
    	<div class="footer-3column">
	    	<dl>
	    		<dt>合作银行</dt>
	    		<dd><img src="${contextPath}/1.0.2/images/hzyh.jpg" /></dd>
	    	</dl>
	    	<dl style="width: 400px;">
	    		<dt>合作商家</dt>
	    		<dd><img src="${contextPath}/1.0.2/images/shangjia.jpg" /></dd>
	    	</dl>
	    	<dl style="width: 200px;">
	    		<dt>权威认证</dt>
	    		<dd>
	    			<a href="${contextPath}/1.0.2/images/paycertbig.jpg" title="支付业务许可证" target="_blank"><img src="${contextPath}/1.0.2/images/paycert.jpg" /></a>
	    			<a href="${contextPath}/1.0.2/images/qingsuancerbig.jpg" title="清算协会常务理事单位" target="_blank"><img src="${contextPath}/1.0.2/images/qingsuancer.jpg" /></a>
	    			<a href="${contextPath}/1.0.2/images/kuajingcerbig.jpg" title="跨境电子商务服务试点单位" target="_blank"><img src="${contextPath}/1.0.2/images/kuajingcer.jpg" /></a>
	    			<a href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&dn=*.easipay.net&lang=zh_cn" title="VeriSign加密服务"><img src="${contextPath}/1.0.2/images/verisign.jpg" /></a></dd>
	    	</dl>
    	</div>
    </div>
    <div id="footer">
        <div class="footer-content">
        	<div class="left">Copyright @2007-2014 Orient Electronic Payment Co.,Ltd 版权所有 东方电子支付有限公司</div>
            <div class="right">
            	ICP备案: 沪ICP11006075号
            </div>
        </div>
    </div>
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
</body>
</html>