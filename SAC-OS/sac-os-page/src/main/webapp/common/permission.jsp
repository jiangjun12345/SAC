<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<div class="sidenav">
       <c:forEach items="${menus}" var="item" varStatus="loopStatus">
		 <div class="title fontsize2 fontfamily2 fontcolor1"><span style="cursor:pointer;">${item.resourceName}</span></div>
		 <div class="item fontsize1">
		    <ul>    
                <c:forEach items="${item.childs}" var="childs" varStatus="status">
                	<c:set var="index" value="${loopStatus.index+1}_${status.index+1}" scope="page"/>
                	<c:set var="selected" value="${param.selected == null? session.selected:param.selected}" scope="page"/>
                	<c:choose>
                		<c:when test="${selected eq index}">
                			<li id="${loopStatus.index+1}_${status.index+1}" style="background-color: #CCCCCC;" >
			            		<a href="${ctx}${childs.resourceUrl}?selected=${loopStatus.index+1}_${status.index+1}" >${childs.resourceName}</a>
			            	</li>
                		</c:when>
                		<c:otherwise>
                			<li id="${loopStatus.index+1}_${status.index+1}" >
			            		<a href="${ctx}${childs.resourceUrl}?selected=${loopStatus.index+1}_${status.index+1}" >${childs.resourceName}</a>
			            	</li>
                		</c:otherwise> 
                	</c:choose>
     	        </c:forEach>
               </ul>
	     </div>
      </c:forEach>
</div>
<script>
var js_menus = {
	index : 10,
	length : 0,
	init : function() {
		<%-- this.index = '<%=request.getParameter("selected")%>'; --%>
		this.index = '<%= session.getAttribute("selected")%>';
		this.order = this.index.split('_');
		this.bind();
		this._default();
	},
	_default : function() {
		$(".item").hide();
		$("div.title").eq(this.order[0] - 1).click();
	},
	bind : function() {
		$("div.title").toggle(function() {
			$(".item").hide();
			$("p").fadeIn("");
			$(this).next(".item").eq(0).fadeIn("slow");
		}, function() {
			$(".item").hide();
			$(this).next(".item").eq(0).fadeOut("slow");
		});
	}
}
js_menus.init();
</script>