<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
        <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>预存手工销账经办</title>
		<script type="text/javascript" language="javascript" src="${ctx}/scripts/js/jquery/jquery-1.7.2.min.js" ></script>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function queryApplyMsg(){
	$("#subForm").submit();
}

function queryPrestoreMsg(){
	$("#subForm1").submit();
	
}

function manualMatch(){
	if (window.confirm("确认?")){
		var val=$('input:radio[name="apply"]:checked').val();
		var val1=$('input:radio[name="detail"]:checked').val();
		
		if(val==""||val==null){
			alert("请选择预存申请信息进行操作!");
			return;
		}
		if(val1==""||val1==null){
			alert("请选择预存交易信息进行操作!");
			return;
		}
		var url = "prestoreMunualMatch";
		var chargeApplyId = val;
		var oflDepositId = val1;
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				chargeApplyId : chargeApplyId,
				oflDepositId : oflDepositId
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					$("#subForm").submit();
					$("#subForm1").submit();
					alert("经办成功")
				}else{
					$("#subForm").submit();
					$("#subForm1").submit();
					alert("经办失败")
				}
			},
			error : function(data) {
				$("#subForm").submit();
				$("#subForm1").submit();
				alert("经办失败")
			}
		});
	}
}
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    	<%@include file="prestoreApplyQueryInit.jsp"%>
		<%@include file="prestoreDetailQueryInit.jsp"%>
    </div>
<!-- /Body -->
</body>
</html>