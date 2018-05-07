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
		<title>浦发预存批次制作</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	

function addRows(){
	 var tbody=document.getElementById("tbName");
	 var row;
	 row=tbody.insertRow();
	 for(var i=0;i<6;i++){
		 //var cell = row.insertCell(i);
		 var td = document.createElement("td");
		 var input = document.createElement("input");
		 //td.innerHTML="<input name='tdName' style='' ></input>"
		 td.appendChild(input)
		 row.appendChild(td);
	 }
}
function createBatch(){
	var str = "";
	var tbody=document.getElementById("tbName");
	var trs = tbody.children;
	for(var i=0;i<trs.length;i++){
		var trStr = "";
		var tr = trs.item(i);
		var tds = tr.children;
		for(var j=0;j<tds.length;j++){
			var td = tds.item(j);
			var input = td.children;
			var it = input.item(0);
			var value = it.value;
			if(j==3){
				var msg = validateAmount(value);
				if(msg!=""){
					var k = i+1;
					var m = j+1;
					alert("第"+k+"行第"+m+"列"+msg);
					return;
				}
			}
			if(j==4){
				var msg = validateDate(value);
				if(msg!=""){
					var k = i+1;
					var m = j+1;
					alert("第"+k+"行第"+m+"列"+msg);
					return;
				}
			}
			if(j==5){
				var msg = validateApplyCode(value);
				if(msg!=""){
					var k = i+1;
					var m = j+1;
					alert("第"+k+"行第"+m+"列"+msg);
					return;
				}
			}
			trStr = trStr+value+"\_"
		}
		str = str + trStr + "\|";
	}
	if(str==""){
		alert("请添加数据进行制作!");
		return;
	}
	
	var url = "spdbBatchMake";
	$.ajax({
		url : url,
		cache : false,
		async : false,
		data : {
			str : str
				},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.success){
				var msg = data.message;
				if(msg!=null&&msg!=""){
					alert("制作成功,但是八位码为["+msg+"]的记录八位码错误或者已使用!");
				}else{
					alert("制作成功!");
				}
				window.location.reload();
			}else{
				alert(data.message);
			}
		},
		error : function(data) {
			alert("制作失败!");
		}
	});
	
}

function validateAmount(payAmount) {
	var msg = "";
	var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
	if (payAmount.length > 0) {
		var flag = payAmount.match(regexp);
		if (flag == null) {
			return msg = "格式非法";
		}
		if (payAmount.length > 20) {
			return  msg = "长度超长";
		}
	}else{
		return msg = "金额为空";
	}
	return msg;
}

function validateDate(date){
	
	var msg = "";
	var regexp = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)"
	if (date.length > 0) {
		var flag = date.match(regexp);
		if (flag == null) {
			return msg = "格式非法";
		}
		if (date.length > 10) {
			return  msg = "长度超长";
		}
	}else{
		return msg = "日期为空";
	}
	return msg;
	
}
function validateApplyCode(applyCode) {
	var msg = "";
	if(applyCode==null||applyCode.replace(/(^\s*)|(\s*$)/g,"")==""){
		msg = "八位码不能为空!";
	}
	return msg;
}

</script>
</head>
<body>
<!-- Body -->

	

    <div class="content">
   			 <div class="table fontcolor4 fontsize1 fontfamily2">
   			 &nbsp;
        		<table id="table2" width="100%" border="0" cellpadding="0" cellspacing="0">
                    <tr>
    					<td align="center">
    						<input align="middle" type="button" class="bluebtn" value="添加行"  onClick="addRows();"/>
    					</td>
    				</tr>	
   				</table> &nbsp;
    		</div>
    
    
    
			<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
        					<thead>
                                <tr height="25" bgcolor="#cccccc">
                                    <th class="border">付款银行账号</th>
                                    <th class="border">付款公司名称</th>
                                    <th class="border">付款银行名称</th>
                                    <th class="border">金额</th>
                                    <th class="border">到账时间</th>
                                    <th class="border">八位码</th>
                                </tr>
                             </thead>
                             <tbody id="tbName"></tbody>
                            </table>
                             &nbsp;
                            
    		</div>
    		
    		<div class="table fontcolor4 fontsize1 fontfamily2">
    		<table id="table3" width="100%" border="0" cellpadding="0" cellspacing="0">
    				<tr>
    					<td align="center">
    						<input align="middle" type="button" class="bluebtn" value="制作"  onClick="createBatch();"/>&nbsp;
    					</td>
    				</tr>	
    				
    				
    		</table>
    		
    		</div>
    </div>
<!-- /Body -->
</body>
</html>