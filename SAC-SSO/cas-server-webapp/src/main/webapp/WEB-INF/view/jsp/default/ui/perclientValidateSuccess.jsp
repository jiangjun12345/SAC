<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${ServiceDomain}/css/global.css" rel="stylesheet" type="text/css"/>
<link href="${ServiceDomain}/css/layout.css" rel="stylesheet" type="text/css"/>
<title>perclient login success</title>
</head>
<body style="background-color:transparent">
    <div class="welcome fontcolor2 fontsize4 fontfamily2">欢迎回来</div>
    <div class="fontcolor2 fontsize1 name1">检测你已经登录的账户：</div>
    <div class="fontcolor6 fontsize1 name1"><div class="name2"></div><div class="name3">${loginName}</div></div>
    <div class="clear"></div>
    <a href="${ServiceDomain}/main.do?hmenu_0=selected" target="_top"><div class="btn2"></div></a>
</body>
</html>