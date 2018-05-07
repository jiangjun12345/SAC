

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	request.setAttribute("bctx", basePath);
%>
	

<script type="text/javascript" src="<c:url value='/scripts/js/jquery/jquery-1.7.2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/js/jquery/jquery.form-3.18.js'/>"></script>

<link href="<c:url value="/styles/global.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/styles/layout.css"/>" rel="stylesheet" type="text/css" />
<link href="<c:url value="/styles/pagination.css"/>" rel="stylesheet"/>

<link href="<c:url value="/styles/zTreeStyle.css"/>" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value='/scripts/tree/jquery.ztree.core-3.5.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/tree/jquery.ztree.excheck-3.5.js'/>"></script>
