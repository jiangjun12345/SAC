<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>  
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提示页面</title>
<script type="text/javascript">
</script>
</head>
<body>
  <div class="con">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<div class="info fontsize1 fontcolor4" style="text-align: center;">
						<div class="left">
							<img src="${ctx}/images/gou.png">
						</div>
						<div class="right fontcolor5 fontsize3" >
							${successMessage}<br>
								<a href="${ctx}/index" style="color:blue">返回首页</a>
						</div>
					</div>
				</div>
			</div>
</body>
</html>