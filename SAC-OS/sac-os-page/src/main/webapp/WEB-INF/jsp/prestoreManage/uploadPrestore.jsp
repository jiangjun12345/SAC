<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		/* 点击上传  */
		$("#uploadBtn").click(function(){
				var file = $('#file').val();
				if(file==null||file==""){
					alert("请选择文件！");
					return false;
				}
				
			    var getxlsa=file.substring(file.lastIndexOf(".")+1,file.length);
			    if(getxlsa==""){
				    alert("请上传一个 .xls 格式的文件.");
				    return false; 
			    }else{ 
					if(getxlsa=="xls"){ 
						$("#uploadForm").submit();
					}else{
						alert("请上传一个 .xls 格式的文件.");
				        return false;
					}
			    } 
			
		});
	});
</script>
<title>预存批次Excel文件上传</title>
</head>

<body>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
			<form id="uploadForm" action="uploadPrestoreFile" method="post" enctype="multipart/form-data">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<div  align="center">
	 					<tr id="otherRecType">
	 						<td align="right"><font size="3">预存批次Excel文件：</font></td>
	 						<td>
	 							<input type="file" id="file" name="fileRes"/>
	 						</td>
	 						
	 					</tr>
	 					<tr>	
	 						<td align="right">
	 							<input type="button" id="uploadBtn" class="bluebtn"  value="上传" />
	 						</td>
	 					</tr>
	 				   </div>
	 				</table>
				</div>
			</form>
	 	</div>
	 </div>
</body>
</html>