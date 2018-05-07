<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/scripts/js/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">
 $(function(){
	downLoadingQuery();
	//setTimeout("downLoadingQuery();",2000); 
});
var intervalID = null;
function downLoadingQuery(){
	window.clearInterval(intervalID);
	var id = document.getElementById("id").value;
	var url = "downloadingQuery";
	$.ajax( {
		url : url,
		cache : false,
		async : false,
		data : {
			id	 :	id
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.success){
				window.location.href="downloadToBrowser?id="+id;
				document.getElementById("msg").innerText ="下载完成";
				if(intervalID!=null){
					window.clearInterval(intervalID);
				}
			}else{
				intervalID = window.setInterval("downLoadingQuery();",2000);
			}
		},
		error : function(data){
			$("#msg").innerHTML="下载失败";
		}
	});
}


</script>
<style type="text/css">
	#table{
		table-layout: fixed;
		width:100%
	}
	.longNo{
		width: 15%;
	}
	td{
		word-break:break-all;
	}
</style>
<title>下载进度</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form  action="downloadingQuery" method="post" >
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td>
	 							<input type="text" class="txt2" id="id" name="id" value="${id}" style="display:none;" />
	 						</td>
	 					</tr>
	 					<tr>
	 						<td colspan="10" align="center"><span id="msg" name="msg" style="color: red;">下载中...请稍等...</span></td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	</div>
</body>
</html>
