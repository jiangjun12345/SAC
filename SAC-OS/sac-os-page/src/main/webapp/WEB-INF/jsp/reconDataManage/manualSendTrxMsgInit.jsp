<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>手工发送交易报文</title>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript">


//清除
function clearText() {
	document.getElementById("sendMsg").value = "";
}
function sendMsg1(){
	if (window.confirm("确认?")){
		var sendMsg = document.getElementById("sendMsg").value;
		var interfaceCode = document.getElementById("interfaceCode").value;
		$.ajax({
			url:"manualSendTrxMsg",
			type:"POST",
			dataType:"json",
			data:{
				sendMsg:sendMsg,
				interfaceCode:interfaceCode
			},
			success:function(data){
				if(data.success){//处理成功
					alert("处理成功！");
				}else{
					alert("处理失败！"+data.message);
				}
			},
			error:function(data){
				alert("处理异常！");
			}
		}); 
	} 
}
</script>

</head>
<body>
	  <div class="content">
      <div class="con ">
				<form id="subForm"  action="${ctx}/manualSendTrxMsg" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%" align="right">Json Message：</td>
								<td >
								<span> <textarea rows="15" cols="100" id="sendMsg"  name = "sendMsg"></textarea> 
										 </span>
								</td>
							</tr>
							
							<tr>
								<td width="10%" align="right">Interface Code：</td>
								<td >
								<span> <input id="interfaceCode"  name = "interfaceCode"></input> 
										 </span>
								</td>
							</tr>
							
							<tr>
								<td><input name="submitbtn" type="button" 
									 value="交易发送" class="bluebtn"
									id="submitbtn" onclick="sendMsg1();"/>
								</td>
								<td><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clearText();"/>
								</td>
								
							</tr>
							
							
						</table>
					</div>
				</form>
			</div>
    </div>

<!-- /Body -->
</body>
</html>