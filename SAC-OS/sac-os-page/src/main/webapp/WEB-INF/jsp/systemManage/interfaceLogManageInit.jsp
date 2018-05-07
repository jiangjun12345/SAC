<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>接口日志查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	Date.prototype.format =function(format)
	{
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
	}

	function clearText(){
		document.getElementById("channel").value=""; 
		document.getElementById("origin").value=""; 
		document.getElementById("interfaceId").value=""; 
		document.getElementById("serviceId").value=""; 
	}
	
	function showidTranaction(idname){	
		var isIE = (document.all) ? true : false;
		var isIE6 = isIE && ([/MSIE (\d)\.0/i.exec(navigator.userAgent)][0][1] == 6);
		var newbox=document.getElementById(idname);
		newbox.style.zIndex="9999";
		newbox.style.display="block";
		newbox.style.position = !isIE6 ? "fixed" : "absolute";
		newbox.style.top =newbox.style.left = "50%";
		newbox.style.marginTop = - newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = - newbox.offsetWidth / 2 + "px";  
		var layer=document.createElement("div");
		layer.id="layer";
		layer.style.width=layer.style.height="100%";
		layer.style.position= !isIE6 ? "fixed" : "absolute";
		layer.style.top=layer.style.left=0;
		layer.style.backgroundColor="#000";
		layer.style.zIndex="9998";
		layer.style.opacity="0.6";
		layer.style.filter='alpha(opacity=60)';
		document.body.appendChild(layer);
		
		
		
		function layer_iestyle(){      
			layer.style.width=Math.max(document.documentElement.scrollWidth, document.documentElement.clientWidth)
			+ "px";
			layer.style.height= Math.max(document.documentElement.scrollHeight, document.documentElement.clientHeight) +
			"px";
		}
		function newbox_iestyle(){      
			newbox.style.marginTop = document.documentElement.scrollTop - newbox.offsetHeight / 2 + "px";
			newbox.style.marginLeft = document.documentElement.scrollLeft - newbox.offsetWidth / 2 + "px";
		}
		
		
	}

	function closeWin(){
		document.getElementById("interfaceRecLogDetailDiv").style.cssText="display:none";
		$("#layer").remove();
		$(".passtable").show();
		$(".passcon .left").hide();
		$(".passcon .right").hide();
		$('#spt1_show').val('');
	}
	
	function showDetail(id){
	    var url = "interfaceLogDetailInit";
		$.ajax( {
			url : url,
			cache : false,
			async : false,
			data : {
				id:id
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				  $("#interfaceIdDetail").attr("value",data.interfaceId);
				  $("#demoDetail").attr("value",data.demo);
				  $("#channelDetail").attr("value",data.channel);
				  $("#originDetail").attr("value",data.origin);
				  $("#serverIpDetail").attr("value",data.serverIp);
				  $("#urlDetail").attr("value",data.url);
				  $("#timemillisDetail").attr("value",data.timemillis);
				  if(data.createTime!=null){
	            	   $("#createTimeDetail").attr("value",new Date(data.createTime.time).format("yyyy-MM-dd hh:mm:ss"));
	              }
				  $("#codeDetail").attr("value",data.code);
				  $("#messageDetail").attr("value",data.message);
				  $("#dataDetail").attr("value",data.data);
			}	
		});
	}
</script>
<style type="text/css">
.font {
	color: #1276bc;
}

#interfaceRecLogDetailDiv {
	display: none;
	left: 10%;
	top: 10%;
	margin-left: -120px;
	margin-top: -140px;
	width: 900px;
	z-index: 1001;
	position: absolute;
	background-color: White;
	border: solid 1px #dddddd;
	padding: 0 0 10px;
	height: 650px;
	overflow: auto;
	background-color: white;
}

.passtable {
	width: 880px;
}
</style>
</head>
<body>
    <div class="content">
    <div class="con">
    	<form:form commandName="sacRecordLog" action="${ctx}/interfaceLogManageInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">调用渠道：</td>
								<td width="30%">
								<span> <form:input  
										path="channel" class="txt2" id="channel" /> </span>
								</td>
								<td width="10%" align="right">来源渠道：</td>
								<td width="30%">
								<span> <form:input  
										path="origin" class="txt2" id="origin" /> </span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">服务端IP：</td>
								<td width="30%">
								<span> <form:input  
										path="serverIp" class="txt2" id="serverIp" /> </span>
								</td>
								<td width="12%" align="right">接口名称：</td>
								<td width="30%">
								<span> <form:input  
										path="demo" class="txt2" id="demo" /> </span>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
								</td>
							</tr>
							<tr>
								<td align="right">记录日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-90})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">记录日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:90})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
							</tr>
							
						</table>
					</div>
				</form:form>
		</div>
    	<div class="table fontcolor4 fontsize1 fontfamily2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                           		    <th class="border">接口ID</th>
                           		    <th class="border">接口名称</th>
                                    <th class="border">调用渠道</th>
                                    <th class="border">来源渠道</th>
                                    <th class="border">服务端IP</th>
                                    <th class="border">客户端IP</th>
                                    <th class="border">调用时长</th>
                                    <th class="border">返回值</th>
                                    <th class="border">记录时间</th>
                                    <th class="border">操作</th>
                                    
                                </tr>
                                <c:if test="${empty sacRecordLogList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacRecordLogList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.interfaceId}</td>
                                    <td class="fontfamily1">${item.demo}</td>
                                    <td class="fontfamily1">${item.channel}</td>
                                    <td class="fontfamily1">${item.origin}</td>
                                    <td class="fontfamily1">${item.serverIp}</td>
                                    <td class="fontfamily1">${item.clientIp}</td>
                                    <td class="fontfamily1">${item.timemillis}</td>
                                    <td class="fontfamily1">${item.code}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><input type="button" value="详细"
										onClick="showidTranaction('interfaceRecLogDetailDiv'); showDetail('${item.id}');">&nbsp;
									</td>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/interfaceLogManageInit"/>
									</td>
								</tr>
							</table>
						</div>
             </div>
	</div>
	<%@include file="/WEB-INF/jsp/systemManage/interfaceLogDetail.jsp"%>

<!-- /Body -->
</body>
</html>