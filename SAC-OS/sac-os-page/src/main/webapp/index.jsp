<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>运营系统-首页2</title>
<script type="text/javascript" src="/scripts/jquery-1.8.0.js"></script>
<script type="text/javascript">
function hh(){
	$.ajax( {
		url : "simpleJson",
		type : "get",
		dataType : "xml",
		success : function(data) {
			alert(data.id);
		//	result = data.result;
		}
	});
}

function getUser(){
	$.ajax( {
		url : "simpleXml",
		type : "get",
		dataType : "xml",
		success : function(data) {
			alert(data);
			alert($("name",data).text());
		}
	});
}


</script>
</head>
<body>
   hello world!!!首页
   <div onclick="hh();">点我吧,嘿嘿</div>
   <div onclick="getUser();">点我吧,给你我的信息</div>
</body>
</html>