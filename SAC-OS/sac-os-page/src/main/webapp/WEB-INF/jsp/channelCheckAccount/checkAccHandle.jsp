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
		
		/* 下拉选项默认赋值  */
		var chnCode = "${chnCode}";/* 支付渠道   */
		$('select[name="chnCode"]').find("option[value="+chnCode+"]").attr("selected",true);
		if(chnCode=='CITIC00'){
			$("#file").attr("disabled",true);
			$("#file1").attr("disabled",false);
			$("#file2").attr("disabled",false);
			$("tr[name='recType']").css('display','');
			$("#otherRecType").css('display','none');
		}else{
			$("#file").attr("disabled",false);
			$("#file1").attr("disabled",true);
			$("#file2").attr("disabled",true);
			$("tr[name='recType']").css('display','none');
			$("#otherRecType").css('display','');
		}
		
		/* 改变支付渠道  */
		$("select[name='chnCode']").change(function(){
			var chnCode = $("select[name='chnCode']").val();
			if(chnCode=='CITIC00'){
				$("#file").attr("disabled",true);
				$("#file1").attr("disabled",false);
				$("#file2").attr("disabled",false);
				$("tr[name='recType']").css('display','');
				$("#otherRecType").css('display','none');
			}else{
				$("#file").attr("disabled",false);
				$("#file1").attr("disabled",true);
				$("#file2").attr("disabled",true);
				$("tr[name='recType']").css('display','none');
				$("#otherRecType").css('display','');
			}
		});
		
		/* 点击上传  */
		$("#uploadBtn").click(function(){
			var chnCode = $("select[name='chnCode']").val();
			if(chnCode=='CITIC00'){
				var file1 = $('#file1').val();
				var file2 = $('#file2').val();
				if((file1==null||file1=="")&&(file2==null||file2=="")){
					alert("请选择文件！");
					return false;
				}
				if(!confirm("请确保一次性上传中信银行B2C正逆向文件！")){
					return false;
				}
			}else{
				var file = $('#file').val();
				if(file==null||file==""){
					alert("请选择文件！");
					return false;
				}
			}
			$("#uploadForm").submit();
		});
		
		/*无对账数据时上传*/
		$("#uploadBtn2").click(function(){
			if(confirm("请确认是否无数据?")){
				$("#uploadForm").attr("action","uploadCheckAccFileNoData").submit();
				$("#uploadForm").attr("action","uploadCheckAccFile");
			}
		});
		
		/*点击返回按钮*/
		$("#backBtn").click(function(){
			$("#uploadCheckAccFileBackForm").submit();
		});
	});
</script>
<title>渠道对账文件上传</title>
</head>

<body>
	<div style="display: none;">
		<form id="uploadCheckAccFileBackForm" action="uploadCheckAccFileBack" method="get">
			<input type="text" id="startDate" name="startDate" value="${sessionScope.startDate}"/>
			<input type="text" id="endDate" name="endDate"  value="${sessionScope.endDate}"/>
			<input type="text" id="queryChnCode" name="queryChnCode" value="${sessionScope.queryChnCode}"/>
			<input type="text" id="recType" name="recType" value="${sessionScope.recType}"/>
			<input type="text" id="payconType" name="payconType" value="${sessionScope.payconType}"/>
			<input type="text" id="recStatus" name="recStatus" value="${sessionScope.recStatus}"/>
			<input type="text" id="pageNo" name="pageNo" value="${sessionScope.pageNo}"/>
		</form>
	</div>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
			<form id="uploadForm" action="uploadCheckAccFile" method="post" enctype="multipart/form-data">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">交易日期:</td>
	 						<td><input type="text" class="txt2" id="trxDate" name="trxDate" value="${trxDate}" 
	 							onfocus="WdatePicker({readOnly:true,maxDate:'%y-%M-%d',dateFmt:'yyyyMMdd'})"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">支付渠道名称:</td>
	 						<td>
	 							<select class="select1" name="chnCode" >
									<c:forEach items="${recFileParamList}" var="channel">
										<option value="${channel.chnCode}" >${channel.chnName}</option>
									</c:forEach>
								</select>
	 						</td>
	 					</tr>
	 					<tr id="otherRecType">
	 						<td align="right">对账文件：</td>
	 						<td>
	 							<input type="file" id="file" name="checkAccFile"/>
	 						</td>
	 					</tr>
	 					<tr name="recType" style="display: none;">
	 						<td align="right">正向对账文件：</td>
	 						<td>
	 							<input type="file" id="file1" name="checkAccFile"/>
	 						</td>
	 					</tr>
	 					<tr name="recType" style="display: none;">
	 						<td align="right">逆向对账文件：</td>
	 						<td>
	 							<input type="file" id="file2" name="checkAccFile"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right"><input type="button" id="uploadBtn2" class="bluebtn"  value="无数据上传" /></td>
	 						<td align="left">
	 							<input type="button" id="uploadBtn" class="bluebtn"  value="上传" />
	 							<input type="button" id="backBtn" class="bluebtn"  value="返回" />
	 						</td>
	 					</tr>
	 				</table>
				</div>
			</form>
	 	</div>
	 </div>
</body>
</html>