<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
	       <form:errors path="*" id="msg" cssClass="error fontcolor6 fontsize1" element="div" delimiter="&sbquo;"/>
           <div class="textbox">
        	   <div class="left"></div>
               <div class="right">
                  <form:input cssClass="txt1" maxlength="100" cssErrorClass="txt1" id="username" tabindex="1" path="username" autocomplete="false" htmlEscape="true" />
               </div>
           </div>
           <div class="textbox2">
        	   <div class="left2"></div>
               <div class="right2">
                  <form:password cssClass="txt1" cssErrorClass="txt1" id="password" tabindex="2" path="password" htmlEscape="true" autocomplete="off" />
               </div>
           </div>
           <div class="textbox1">
        	   <div class="left">
        	      <form:input path="authCode" id="authCode" tabindex="3" cssErrorClass="txt2" cssClass="txt2" htmlEscape="true" autocomplete="off"/>
        	   </div>
               <div class="right">
                  <img id="imageCode" onclick="javascript:changeImageCode();" src="image/achieveAuthCode.html?t=<%=Math.random()%>"/>
               </div>
           </div>
                       
           <input type="hidden" name="lt" value="${loginTicket}" />
		   <input type="hidden" name="execution" value="${flowExecutionKey}" />
		   <input type="hidden" name="_eventId" value="submit" />
		   
		   <div class="rp fontfamily2 fontcolor3 fontsize1"><a>忘记密码？</a></div>
           <div class="btn fontfamily2"  onclick="submit();"><a href="javascript:void(0);">登&nbsp;录</a></div>
           <div class="rp fontfamily2 fontcolor3 fontsize1"><a href="register.html">免费注册</a></div>
</form:form>