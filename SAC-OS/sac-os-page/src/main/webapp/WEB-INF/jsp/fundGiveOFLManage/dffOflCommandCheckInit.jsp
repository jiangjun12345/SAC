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
		<title>东方付线下出款复核</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function queryMsg(){
	$("#subForm").attr("action","${ctx}/dffCommandCheckInit").submit();
}

//线上审核操作
function checkSucc(checkStatus,index,id,otrxSerialNo,etrxSerialNo,draccAreaCode,craccNo,draccNodeCode,draccBankName,cmdId){//审核结果
	//付款方银行为线上4家银行
	if(confirm("确认审核通过？")){
		$("#checkSucc"+index).attr("disabled",true);
		$("#checkFail"+index).attr("disabled",true);
		//通知后台处理
		$.ajax({
			url:"dffCommandCheckComfirm",
			type:"POST",
			dataType:"json",
			data:{
				checkStatus:checkStatus,
				id:id,
				cmdId:cmdId,
				otrxSerialNo: otrxSerialNo,
				etrxSerialNo : etrxSerialNo,
				craccNo :craccNo,
				draccAreaCode : draccAreaCode,
				craccNo : craccNo,
				draccNodeCode : draccNodeCode,
				draccBankName : draccBankName,
				result:"success"
			},
			success:function(data){
				if(data.success){
					alert("操作成功！");
					$("form[name='splitPageForm']").submit();
				}else{
					alert(data.message);
					$("form[name='splitPageForm']").submit();
				}
			},
			error:function(data){
				alert("审核提交异常,请联系管理员!");
				window.location.reload();
			}	
		});	
	}
}

function checkSucc1(checkStatus,id,index,otrxSerialNo,cmdId){//审核结果
	var str=prompt("请填下审核失败原因:");
    if(str)
    {
      //付款方银行为线上4家银行
    	if(confirm("确认审核不通过？")){
    		$("#checkFail"+index).attr("disabled",true);
    		$("#checkSucc"+index).attr("disabled",true);
    		//通知后台处理
    		$.ajax({
    			url:"dffCommandCheckComfirm",
    			type:"POST",
    			dataType:"json",
    			data:{
    				checkStatus:checkStatus,
    				rejectArea:str,
    				id:id,
    				cmdId:cmdId,
    				otrxSerialNo:otrxSerialNo,
    				result:"success"
    			},
    			success:function(data){
    				if(data.success){
    					alert("操作成功！");
    					$("form[name='splitPageForm']").submit();
    				}else{
    					alert(data.message);
    					$("form[name='splitPageForm']").submit();
    				}
    			},
    			error:function(data){
    				alert("审核提交异常,");
    				window.location.reload();
    			}	
    		});	
    	}
    }else{
    	if(str=='null'||str==null){
    		return;
    	}
    	alert("请填下审核失败原因!");
    	return;
    }
	
}
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    
     <div class="con ">
			<form id="subForm" name="subForm" action="dffCommandCheckInit" method="POST">
				<div class="table fontcolor4 fontsize1 fontfamily2">
	            <table  width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12%" align="right">录入日期：</td>
							<td>
								<input type="text" name="createTime" id="createTime" class="txt2" value="${createTime}"
									onfocus="WdatePicker({readOnly:true,maxDate:'#F{\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})" />
							</td>
						<td align="right" colspan="1">审核状态：</td>
						<td>
							<select id="checkStatus" name="checkStatus" class="select1">
								    <option value="" >全部</option>
									<option value="1" <c:if test="${checkStatus eq '1'}"> selected="selected"</c:if>>审核通过</option>
									<option value="2" <c:if test="${checkStatus eq '2'}"> selected="selected"</c:if>>待审核</option>
							        <option value="3" <c:if test="${checkStatus eq '3'}"> selected="selected"</c:if>>审核未通过</option>
							</select> 
						</td>
					</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td>
								<input name="submitbtn" type="button" value="查询" class="bluebtn" id="submitbtn" onclick="queryMsg();" />
							</td>
							<td align="right">&nbsp;</td>
							<td>
								<input name="clearBtn" type="button" value="清除" class="bluebtn" id="clearBtn" onclick="clean();clearText();" />
							</td>
						</tr>
					</table>					
				</div>
			</form>
		</div>
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                	<th width="200px" class="border">操作</th>
                                	<th class="border">审核状态</th>
                                    <th class="border">预扣交易流水号</th>
                                    <th class="border">银行流水号</th>
                                    <th class="border">收款组织机构号</th>
                                    <th class="border">收款客户名称</th>
                                    <th class="border">收款银行名称</th>
                                    <th class="border">收款方账号</th>
                                    <th class="border">币种</th>
                                    <th class="border">金额</th>
                                    <th class="border">付款银行名称</th>
                                    <th class="border">付款银行分行</th>
                                </tr>
                                <c:if test="${empty sacOtrxInfoList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacOtrxInfoList}" var="item" varStatus="st">
									<c:choose>
										<c:when test="${st.index %2 == 0 }">
											<tr align="center" height="35">
										</c:when>
										<c:otherwise>
											<tr align="center" height="35" bgcolor="#eeeeee">
										</c:otherwise>
									</c:choose>
                                    
                                    
                                    <td class="fontfamily1" align="left">
										<c:if test="${item.trxState eq '2'}">
										    <input id="checkSucc${st.index}" class="bluebtn" style="width: 90px;" type="button" value="审核通过" 
										    onclick="checkSucc('1',${st.index},'${item.id}','${item.otrxSerialNo}','${item.etrxSerialNo}','${item.draccAreaCode}','${item.craccNo}','${item.draccNodeCode}','${item.draccBankName}','${item.memo}');" />
											<input id="checkFail${st.index}" class="bluebtn"  type="button" value="审核不通过" onclick="checkSucc1('3','${item.id}',${st.index},'${item.otrxSerialNo}','${item.memo}');" />
										</c:if>
									</td>
                                    <td class="fontfamily1">
										<c:if test="${item.trxState eq '1'}">审核完成</c:if>
										<c:if test="${item.trxState eq '2'}">待审核</c:if>
										<c:if test="${item.trxState eq '3'}">审核不通过</c:if> 
									</td>   
                                    <td class="fontfamily1">${item.otrxSerialNo}</td>
                                    <td class="fontfamily1">${item.etrxSerialNo}</td>
                                    <td class="fontfamily1">${item.craccCardId}</td>
                                    <td class="fontfamily1">${item.craccName}</td>
                                    <td class="fontfamily1">${item.craccBankName}</td>
                                    <td class="fontfamily1">${item.craccNo}</td>
                                    <td class="fontfamily1">${item.payCurrency}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.draccBankName}</td>
                                    <td class="fontfamily1">${item.craccAreaCode}</td>
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/dffCommandCheckInit"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
    		
    </div>
<!-- /Body -->
</body>
</html>