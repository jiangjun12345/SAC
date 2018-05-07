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
		<title>划款指令批次复核</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function clearText(){
	document.getElementById("msgReceiver").value="";
}	
function queryMsg(){
	$("#subForm").attr("action","${ctx}/fundCmdBatchCheckInit").submit();
}
function downloadCheck(){
	
	if (window.confirm("确定下载？")) {
		$("#subForm").attr("action","${ctx}/downloadCheckToExcel").submit();
	}
}

function calculateAmount(id) {
	var totalAT=0;
	var box = document.getElementsByName('box');
	for(var i=0;i<box.length;i++){
		if(box[i].checked){
			var value = box[i].value;
			var num1 = parseFloat(value).toFixed(2);
			num1 = parseFloat(num1);
			var num2 = parseFloat(totalAT).toFixed(2);
			num2 = parseFloat(num2);
			totalAT = (num1+num2).toFixed(2);
		}
		document.getElementById("selectedSpan").innerHTML='所选划款金额总计:'+totalAT+'，';
	}
}

//点击"全选/全不选"复选框，如果选中，则选中全部复选框，否则取消选中全部复选框  
function checkboxall(Allname, name) {
	
	var ischecked = document.getElementById(Allname).checked;
	if (ischecked) {
		checkallbox(name);
	} else {
		discheckallbox(name);
	}
	
	calculateAmount();
}
//选中全部复选框  
function checkallbox(name) {
	var boxarray = document.getElementsByName(name);
	for ( var i = 0; i < boxarray.length; i++) {
		boxarray[i].checked = true;
	}
}
//取消选中全部复选框  
function discheckallbox(name) {
	var boxarray = document.getElementsByName(name);
	for ( var i = 0; i < boxarray.length; i++) {
		boxarray[i].checked = false;
	}
}

function showDetail(batchId){
	var url="getfundDetailByBatch"; //转向网页的地址;
    var name="出款详细信息";                        //网页名称，可为空;
    var iWidth=900;                          //弹出窗口的宽度;
    var iHeight=150;                       //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight-30-iHeight)/2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth-10-iWidth)/2;
    var params='width='+iWidth
  			   +',alwaysRaised=no'//默认是 yes
               +',height='+iHeight
               +',top='+iTop
               +',left='+iLeft
               +',channelmode=no'//是否使用剧院模式显示窗口。默认为 no
               +',directories=yes'//是否添加目录按钮。默认为 yes
               +',fullscreen=no' //是否使用全屏模式显示浏览器
               +',location=no'//是否显示地址字段。默认是 yes
               +',menubar=no'//是否显示菜单栏。默认是 yes
               +',resizable=no'//窗口是否可调节尺寸。默认是 yes
               +',scrollbars=yes'//是否显示滚动条。默认是 yes
               +',status=yes'//是否添加状态栏。默认是 yes
               +',titlebar=yes'//默认是 yes
               +',toolbar=no'//默认是 yes
               ;
    
  			   window.open(url+'?cmdBatchId='+batchId , name,params);
}


function checkConfirm() {
	if (window.confirm("确认?")){
		$("#confirmBtn").attr("disabled",true);
		$("#failBtn").attr("disabled",true);
		var batchIds = "";
		var box = document.getElementsByName('box');
		for(var i=0;i<box.length;i++){
			var flag = box[i].checked;
			if(flag){
				var value = box[i].id;
				batchIds = value+"|"+batchIds;
			}
		}
		if(batchIds==""){
			alert("请勾选批次进行操作!");
			return;
		}
		var url = "checkBatchPass";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				batchIds : batchIds
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("操作成功!");
				}else{
					alert(data.message);
				}
				window.location.reload();
			},
			error : function(data) {
				alert("操作失败!");
				window.location.reload();
			}
		});
	}
}
function checkFailue() {
	if (window.confirm("确认?")){
		$("#failBtn").attr("disabled",true);
		$("#confirmBtn").attr("disabled",true);
		var batchIds = "";
		var box = document.getElementsByName('box');
		for(var i=0;i<box.length;i++){
			var flag = box[i].checked;
			if(flag){
				var value = box[i].id;
				batchIds = value+"|"+batchIds;
			}
		}
		if(batchIds==""){
			alert("请勾选批次进行操作!");
			return;
		}
		var url = "checkBatchFailue";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				batchIds : batchIds
			},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					alert("操作成功!");
				}else{
					alert("操作失败");
				}
				window.location.reload();
			},
			error : function(data) {
				alert("操作失败!");
				window.location.reload();
			}
		});
	}
}
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form id="subForm" action="${ctx}/fundCmdBatchCheckInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">划款日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-90})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">划款日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:90})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="10%" align="right">出款银行名称：</td>
								<td width="25%"><select id="msgReceiver" name="msgReceiver"
									class="select1">
										<option value="">全部</option>
										<c:forEach items="${bankList}" var="sys">
											<option value="${sys.dicValue}"
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == msgReceiver}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryMsg();"/>
								</td>
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
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                	<th class="border">序号</th>
                                	<th class="border"><input id="checkall" type="checkbox"
						value="" onclick="checkboxall('checkall','box')" /></th>
                                    <th class="border">批次号</th>
                                    <th class="border">批次生成时间</th>
                                    <th class="border">出款银行</th>
                                    <th class="border">币别</th>
                                    <th class="border">金额</th>
                                    <th class="border">交易数</th>
                                    <th class="border">经办人</th>
                                    <th class="border">经办时间</th>
                                    <th class="border">状态</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty batchList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${batchList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="30" id="${item.cmdBatchId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="30" bgcolor="#eeeeee" id="${item.cmdBatchId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td><input type="checkbox" name="box" id="${item.cmdBatchId}"
											value="${item.batchAmount}" onclick="calculateAmount('${item.cmdBatchId}');"/>
									</td>
                                    <td class="fontfamily1">${item.cmdBatchId}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1">${item.msgReceiver}</td>
                                    <td class="fontfamily1">${item.batchCur}</td>
                                    <td class="fontfamily1">${item.batchAmount}</td>
                                    <td class="fontfamily1">${item.batchCount}</td>
                                    <td class="fontfamily1">${item.operName}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.operTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1"><c:if test="${item.batchState eq '02'}">复核不通过</c:if><c:if test="${item.batchState eq '00'}">待处理</c:if><c:if test="${item.batchState eq '10'}">待复核</c:if><c:if test="${item.batchState eq '20'}">复核通过</c:if></td>
                                    
                                   	<td class="fontfamily1"><input id="inputBtn${item.cmdBatchId}" type="button" value="详细"
									onClick="showDetail('${item.cmdBatchId}');">&nbsp;
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><span id="selectedSpan">所选划款金额总计:0，</span>待处理划款金额总计:${totalAmount}
									</td>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/fundCmdBatchCheckInit"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
    		
    		<div >
						<table  align="right">
								<tr >
								<td align="right"><input name="submitbtn" type="button" 
									 value="确认划款" class="bluebtn"
									id="confirmBtn" onclick="checkConfirm();"/>
								</td>
								<td align="right"><input name="submitbtn" type="button" 
									 value="确认不划款" class="bluebtn"
									id="failBtn" onclick="checkFailue();"/>
								</td>
								
								<td align="left"><input name="submitbtn" type="button" 
									 value="下载" class="bluebtn"
									id="downBtn" onclick="downloadCheck();"/>
								</td>
								</tr>
							</table>
							</div>
    		
    </div>
<!-- /Body -->
</body>
</html>