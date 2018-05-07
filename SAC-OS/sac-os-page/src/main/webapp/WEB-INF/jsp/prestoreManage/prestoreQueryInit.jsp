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
		<title>预存批次制作</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
function queryPrestoreMsg(){
	$("#subForm").submit();
}
function removeRows(id){ 
	var applyCode = document.getElementById("input"+id).value
	if(applyCode==null||applyCode.replace(/(^\s*)|(\s*$)/g,"")==""){
		alert("八位码不能为空!");
		return;
	}
	
	var node = document.getElementById(id+'TD');
	var tableId = "";
	if(node.parentNode.nodeName!='TABLE'){
		tableId = node.parentNode.parentNode.id;
	}else{
		tableId = node.parentNode.id;
	}
	if(tableId=='table1'){
		document.getElementById('inputBtn'+id).value="删除";
		document.getElementById("table2").appendChild(node);
	}else if(tableId=='table2'){
		document.getElementById('inputBtn'+id).value="标记";
		document.getElementById("table1").appendChild(node);
	}
}

function makeAll(){
	var trs = $('#table1').find('tbody').find('tr');
	for(var i=1;i<trs.length;i++){
		var tr = trs[i];
		var serial = tr.firstElementChild.innerHTML;
		var applyCode = document.getElementById("input"+serial).value
		if(applyCode!=null&&applyCode.replace(/(^\s*)|(\s*$)/g,"")!=""){
			removeRows(serial);
		}
	}
}


function createBatch(dateilList){
	if (window.confirm("确认?")){
		var serialNoListStr="";
		var nodes = document.getElementById("table2").children;
		for(var i=1;i<nodes.length;i++){
			var serialNoArr={};
			var node = nodes[i];
			var tdNodes = node.children;
			var bankSerialNo = tdNodes[0].innerHTML;
			serialNoListStr = bankSerialNo+'_'+document.getElementById("input"+bankSerialNo).value+'_'+document.getElementById("name"+bankSerialNo).value+'|'+serialNoListStr;
		}
		if(serialNoListStr==null||serialNoListStr==""){
			alert("请标记记录进行制作!");
			return;
		}
		$("#createBtn").attr("disabled",true);
		
		/* var craccNodeCode = document.getElementById("craccNodeCode").value;
		if(craccNodeCode==null||craccNodeCode==""){
			alert("请选择收款银行进行制作!");
			return;
		}
		
		var craccBankName = document.getElementById("craccNodeCode").innerHTML; */
		
		var url = "createPrestoreBatch";
		
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				serialNoListStr:serialNoListStr,
				dateilList : dateilList
					},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success){
					var nodes = document.getElementById("table2");
					var node = nodes.children;
					for(var i=node.length-1;i>=1;i--){    
						nodes.removeChild(node.item(i));    
					} 
					var msgStr = data.message;
					var msgArr = msgStr.split("_");
					var msg = msgArr[0];
					var msg1 = msgArr[1];
					if(msg!=null&&msg!=""&&msg1!=null&&msg1!=""){
						alert("制作成功,但是流水号为["+msg+"]的记录八位码错误或者已使用,流水号为["+msg1+"]的记录的八位码多次存在同一个批次中!");
					}else if(msg==null||msg==""){
						alert("制作成功,但是流水号为["+msg1+"]的记录八位码重复使用!");
					}else if(msg1==null||msg1==""){
						alert("制作成功,但是流水号为["+msg+"]的记录的八位码多次存在同一个批次中");
					}else{
						alert("制作成功!");
					}
				}else{
					alert(data.message);
				}
				window.location.reload();
			},
			error : function(data) {
				alert("制作失败!");
				window.location.reload();
			}
		});
	}
}
function spdbMake(){
	$("#subFormSPDB").submit();
}
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
      			<form:form id="subFormSPDB" action="${ctx}/spdbMakeInit" method="POST" > </form:form>
				<form:form id="subForm" action="${ctx}/prestoreQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">交易日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-365})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">交易日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:365})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryPrestoreMsg();"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input  type="button" 
									class="bluebtn" value="浦发制作"  onClick="spdbMake();"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input  type="button" 
									class="bluebtn" value="一键标记"  onClick="makeAll();"/>
								</td>
							</tr>
							
							
						</table>
					</div>
				</form:form>
			</div>
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                    <th class="border">银行流水号</th>
                                    <th class="border">付款银行账号</th>
                                    <th class="border">付款公司名称</th>
                                    <th class="border">付款银行名称</th>
                                    <th class="border">金额</th>
                                    <th class="border" style="width: 60px">到账时间</th>
                                    <th class="border">备注</th>
                                    <th class="border">八位码</th>
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
                                            <tr align="center" height="30" id="${item.bankSerialNo}TD">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="30" bgcolor="#eeeeee" id="${item.bankSerialNo}TD">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.bankSerialNo}</td>
                                    <td class="fontfamily1">&nbsp;${item.draccNo}</td>
                                    <td class="fontfamily1"><input id="name${item.bankSerialNo}" value="${item.draccName}"/></td>
                                    <td class="fontfamily1">${item.draccBankName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1" style="width: 60px" >${item.bankTrxDate}</td>
                                    <td class="fontfamily1">${item.memo}</td>
                                    <td class="fontfamily1"><input style="width: 90px" id="input${item.bankSerialNo}" value="${item.applyCode}"></input></td>
                                   	<td class="fontfamily1"><input id="inputBtn${item.bankSerialNo}" type="button" value="标记"
									onClick="removeRows('${item.bankSerialNo}');">&nbsp;
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                             &nbsp;
    		</div>
    		
    		
    		
    		<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id="table2" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                    <th class="border">银行流水号</th>
                                    <th class="border">付款银行账号</th>
                                    <th class="border">付款公司名称</th>
                                    <th class="border">付款银行名称</th>
                                    <th class="border">金额</th>
                                    <th class="border">到账日期</th>
                                    <th class="border">备注</th>
                                    <th class="border">八位码</th>
                                    <th class="border">操作</th>
                                </tr>
                            </table>
                            &nbsp;
    		</div>
    		<div class="table fontcolor4 fontsize1 fontfamily2">
    		<table id="table3" width="100%" border="0" cellpadding="0" cellspacing="0">
    				<tr>
    					<%-- <td width="25%" align="right">收款银行：</td>
    					<td width="15%"><select id="craccNodeCode" name="craccNodeCode">
										<option value="">全部</option>
										<c:forEach items="${bankList}" var="sys">
												<option value="${sys.dicValue}">${sys.dicDesc}</option>
										</c:forEach>
								</select>
						</td> --%>
    					<td align="center">
    						<input align="middle" type="button" id = "createBtn" class="bluebtn" value="制作"  onClick="createBatch('${detailList}');"/>&nbsp;
    					</td>
    				</tr>	
    				
    				
    		</table>
    		
    		</div>
    </div>
<!-- /Body -->
</body>
</html>