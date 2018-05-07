<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
		function uploadFile(){
			var myfiles = document.getElementById("myfiles").value;
			$("#uploadForm").submit();
		}
</script>
<title>批量补单</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
			<form id="uploadForm" action="uploadTransactionBatchCommit" method="post" enctype="multipart/form-data">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr align="center">
	 						<td align="center">
	 							操作类型:<input readonly="readonly" value="批量补单"></input>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="center">
	 							上传格式：<input value="excel" readonly="readonly"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="center">
	 							上传文件：<input type="file" id="myfiles" name="myfiles"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="center"><input type="button" class="bluebtn"  value="上传" onclick="uploadFile();"/></td>
	 					</tr>
	 				</table>
				</div>
			</form>
	 	</div>
	 </div>
	 <c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
</body>
</html>