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
		<title>东方付线下出款经办</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function queryMsg() {
	var payAmount = document.getElementById("payAmount").value;
	var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
	if (payAmount.length > 0) {
		var flag = payAmount.match(regexp);
		if (flag == null) {
			document.getElementById("payAmountError").innerHTML = "格式非法";
			return;
		}
		if (payAmount.length > 20) {
			document.getElementById("payAmountError").innerHTML = "长度超长";
			return;
		}
	}
	$("#subForm").submit();
}
function clearText(){
	$("#payAmount").value="";
	$("#craccName").value="";
}

function dealFailue(id){
	if(window.confirm("确定处理?")){
		var url = "dealCommandOfFailue";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				id : id
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


function dffOflCommandCancel(checkStatus,id,index,otrxSerialNo){//审核结果
	
	var str=prompt("请填下作废原因:");
    if(str)
    {
      //付款方银行为线上4家银行
    	if(confirm("确认作废？")){
    		$("#cancel"+index).attr("disabled",true);
    		$("#deal"+index).attr("disabled",true);
    		//通知后台处理
    		$.ajax({
    			url:"dffCommandCancel",
    			type:"POST",
    			dataType:"json",
    			data:{
    				checkStatus:checkStatus,
    				rejectArea:str,
    				cmdId:id,
    				otrxSerialNo:otrxSerialNo,
    				result:"success"
    			},
    			success:function(data){
    				if(data.success){
    					alert("操作成功！");
    					$("form[name='subForm']").submit();
    				}else{
    					alert(data.message);
    					$("form[name='subForm']").submit();
    				}
    			},
    			error:function(data){
    				alert("提交异常,");
    				window.location.reload();
    			}	
    		});	
    	}
    }else{
    	if(str=='null'||str==null){
    		return;
    	}
    	alert("请填下作废原因!");
    	return;
    }
	
}


function dffOflCommandBackFill(id){
	var url="getDffOflCommandDetail"; //转向网页的地址;
    var name="出款详细信息";                        //网页名称，可为空;
    var iWidth=900;                          //弹出窗口的宽度;
    var iHeight=250;                       //弹出窗口的高度;
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
    
  			 var owin = window.open(url+'?id='+id, name,params);
  			 oWin.document.title="you title";
  			 //window.showModalDialog(url+'?cusNo='+cusNo+'&sacCurrency='+sacCurrencyValue,params);
}

function callback() {
	window.location.reload();
}


</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form id="subForm" name ="subForm" action="${ctx}/getDffOflCommandInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">出款申请日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-90})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">出款申请日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:90})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="10%" align="right">收款账户名称：</td>
								<td width="25%">
									<input type="text" class="txt2" id="craccName" name="craccName" value="${craccName}"/>
								</td>
								<td width="10%" align="right">支付金额：</td>
								<td width="25%">
									<input type="text" class="txt2" id="payAmount" name="payAmount" value="${payAmount}"/>
									<span id = "payAmountError" style="color: red"></span>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">指令类型：</td>
								<td>
									<select id="cmdType" name="cmdType" class="select1">
										    <option value="" >全部</option>
											<option value="00" <c:if test="${cmdType eq '00'}"> selected="selected"</c:if>>线上自动转线下出款</option>
											<option value="01" <c:if test="${cmdType eq '01'}"> selected="selected"</c:if>>指令录入出款</option>
									</select> 
								</td>
								<td width="10%" align="right">指令状态：</td>
								<td>
									<select id="cmdState" name="cmdState" class="select1">
										    <option value="" >全部</option>
   									        <option value="N" <c:if test="${cmdState eq 'N'}"> selected="selected"</c:if>>待经办</option>
											<option value="JS" <c:if test="${cmdState eq 'JS'}"> selected="selected"</c:if>>经办成功</option>
											<option value="S" <c:if test="${cmdState eq 'S'}"> selected="selected"</c:if>>审核通过</option>
									        <option value="F" <c:if test="${cmdState eq 'F'}"> selected="selected"</c:if>>审核未通过</option>
									        <option value="C" <c:if test="${cmdState eq 'C'}"> selected="selected"</c:if>>作废</option>
									        
									</select> 
								</td>
							</tr>
							<tr>
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
                                	<th class="border">支付金额</th>
                                    <th class="border">支付币种</th>
                                    <th class="border">收款方代码</th>
                                    <th class="border">收款方银行名称</th>
                                    <th class="border">收款方账号</th>
                                    <th class="border">收款账户名称</th>
                                    <th class="border">出款申请时间</th>
                                    <th class="border">出款状态</th>
                                    <th class="border">指令类型</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty detailList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${detailList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="30" id="${item.id}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="30" bgcolor="#eeeeee" id="${item.id}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.payCurrency}</td>
                                    <td class="fontfamily1">${item.craccCardId}</td>
                                    <td class="fontfamily1">${item.craccBankName}</td>
                                    <td class="fontfamily1">${item.craccNo}</td>
                                    <td class="fontfamily1">${item.craccName}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td class="fontfamily1">
										<c:if test="${item.cmdState eq 'N'}">待经办</c:if>
										<c:if test="${item.cmdState eq 'S'}">审核通过</c:if>
										<c:if test="${item.cmdState eq 'F'}">审核不通过</c:if> 
										<c:if test="${item.cmdState eq 'JS'}">经办成功</c:if> 
										<c:if test="${item.cmdState eq 'C'}">作废</c:if> 
									</td>    
									<td class="fontfamily1">
										<c:if test="${item.cmdType eq '00'}">线上自动转线下出款</c:if>
										<c:if test="${item.cmdType eq '01'}">指令录入出款</c:if>
									</td>                                   
									<td class="fontfamily1">
									<c:if test="${item.cmdState eq 'N'}">
									<input  id="deal${st.index}" type="button" value="处理" onClick="dffOflCommandBackFill('${item.id}');">&nbsp;
									<input  id="cancel${st.index}" type="button" value="作废" onClick="dffOflCommandCancel('3','${item.id}',${st.index},'${item.ykSerialNo}');">
									</c:if>
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/getDffOflCommandInit"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
    </div>
<!-- /Body -->
</body>
</html>