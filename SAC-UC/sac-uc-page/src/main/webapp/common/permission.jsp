<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<div class="sidenav">
       <c:forEach items="${menus}" var="item" varStatus="loopStatus">
		<div class="title fontsize2 fontfamily2 fontcolor1"><span>${item.resourceName}</span></div>
			 <div class="item fontsize1">
			    <ul>    
                 <c:forEach items="${item.childs}" var="childs">
           	 	<li>
            	<a href="${ctx}${childs.resourceUrl}" >${childs.resourceName}</a>
            	</li>
     	         </c:forEach>
                 </ul>
		     </div>
      </c:forEach>
          
</div>
