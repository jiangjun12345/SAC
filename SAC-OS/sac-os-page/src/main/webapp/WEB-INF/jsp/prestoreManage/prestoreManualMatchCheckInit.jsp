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
		<title>预存手工销账复核</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function passConfirm(){
	if (window.confirm("确认?")){
		var val=$('input:radio[name="manualMatchRadio"]:checked').val();
		if(val==null||val==""){
			alert("请选择一条记录进行操作!");
			return;
		}
		$("#confirmBtn").attr("disabled",true);
		$("#failBtn").attr("disabled",true);
		var url = "prestoreMunualMatchCheckPass";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				oflDepositId : val
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
				window.location.reload()
			}
		});
	}
}
function passFailue(){
	if (window.confirm("确认?")){
		var val=$('input:radio[name="manualMatchRadio"]:checked').val();
		if(val==null||val==""){
			alert("请选择一条记录进行操作!");
			return;
		}
		$("#confirmBtn").attr("disabled",true);
		$("#failBtn").attr("disabled",true);
		var url = "prestoreMunualMatchCheckFailue";
		$.ajax({
			url : url,
			cache : false,
			async : false,
			data : {
				oflDepositId : val
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
				window.location.reload()
			}
		});
	}
}
function queryMunualMatchCheckInfo(){
	$("#subForm").submit();
}
</script>
</head>
<body>
<!-- Body -->
<div class="content">
      <div class="con ">
				<form:form id="subForm" action="${ctx}/prestoreMunualMatchCheck" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2" style="font-size: 12px" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">核销申请日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-2000})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">核销申请日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:2000})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryMunualMatchCheckInfo();"/>
								</td>
								<td width="10%" align="left">&nbsp;</td>
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
                                    <th class="border">选择</th>
                                    <th class="border">预存申请日期</th>
                                    <th class="border">八位码</th>
                                    <th class="border">企业名称</th>
                                    <th class="border">金额(CNY)</th>
                                    <th class="border">核销号</th>
                                    <th class="border">备注原因</th>
                                    <th class="border">核销经办人</th>
                                    <th class="border">经办时间</th>
                                </tr>
                                <c:if test="${empty checkList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${checkList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="25" id="${item.oflDepositId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="25" bgcolor="#eeeeee" id="${item.oflDepositId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td> <input type="radio" name = "manualMatchRadio" value="${item.oflDepositId}"/></td>
                                    <td class="fontfamily1">${item.applyDate}</td>
                                    <td class="fontfamily1">${item.applyCode}</td>
                                    <td class="fontfamily1">${item.applyOrgName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.checkSerialNo}</td>
                                    <td class="fontfamily1">${item.dealMemo}</td>
                                    <td class="fontfamily1">${item.operatorName}</td>
                                    <td class="fontfamily1">${item.operTime}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                             <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/prestoreMunualMatchCheck"/>
									</td>
								</tr>
							</table>
							</div>
							
						<div>
						<table align="right">
								<tr>
								<td align="right"><input name="submitbtn" type="button" 
									 value="复核通过" class="bluebtn"
									id="confirmBtn" onclick="passConfirm();"/>
								</td>
								
								<td align="right"><input name="submitbtn" type="button" 
									 value="复核不通过" class="bluebtn"
									id="failBtn" onclick="passFailue();"/>
								</td>
								</tr>
							</table>
							</div>
    		</div>
   		</div>
<!-- /Body -->
</body>
</html>