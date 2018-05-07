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
		<title>备付金查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">	
function clearText(){
	document.getElementById("cusId").value = "";
	document.getElementById("currency").value = "";
}
function submitForm() {
	document.getElementById("startDateError").innerHTML="";
	document.getElementById("endDateError").innerHTML="";
	
	var startDate = document.getElementById("startDate").value;
	var endDate = document.getElementById("endDate").value;
	if(startDate==null||startDate==""){
		document.getElementById("startDateError").innerHTML="请选择查询开始日期";
		return;
	}
	if(endDate==null||endDate==""){
		document.getElementById("endDateError").innerHTML="请选择查询结束日期";
		return;
	}
	var flag = checkTime(startDate,endDate);
	if(flag){
		$("#submitForm").submit();
	}
}

function checkTime(startTime,endTime){
    if(start!=''&&end!=''){
     var start=startTime.split('-');
     var end=endTime.split('-');
     var start1=new Date(start[0],start[1]-1,start[2]);
     var end1=new Date(end[0],end[1]-1,end[2]);
     var td=new Date();
     if(td<start1){<%--开始时间必须小于当前时间--%>
      document.getElementById("startDateError").innerHTML="开始时间必须小于当前时间";
      return false;
     }
     if(start1>end1){
      document.getElementById("endDateError").innerHTML="结束时间要大于开始时间";
      return false;
     }else{
    	 return true;
     }
    }
}

//展示详情 
function queryDetail(bankName,codeId,trxCurrency,statTime){
	var cusId = $("#cusId").val();
	var currency = $("#currency").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var pageNo = ${pageNo}
	$.ajax({
		type:"POST",
		url:"preparedFundDetailQuery",
		async:false,
		data:{
			cusId:cusId,
			currency:currency,
			startDate:startDate,
			pageNo:pageNo,
			saveFlag:"Y"
		},
		success:function(){
			window.location.href = "preparedFundDetailQuery?codeId="+codeId+"&currency="+trxCurrency+"&statTime="+statTime+"&bankName="+bankName+"&endDate="+endDate;
		}
	});	
}
</script>

</head>
<body>
    <div class="content">
    <div class="con">
    	<form:form id="submitForm" commandName="finStatBankForm" action="${ctx}/preparedFundQuery" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">渠道名称：</td>
								<td width="30%">
									<select id="cusId" name="cusId" class="select1" >
										<option value="">全部</option>
										<c:forEach items="${sacChannelParamList}" var="sys">
											<option value="${sys.chnNo}" <c:if test="${sys.chnNo == finStatBankForm.cusId}"> selected="selected"</c:if>>${sys.chnName}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">币种：</td>
								<td width="30%">
									<select id="currency" name="currency" class="select1" >
										<option value="">全部</option>
										<c:forEach items="${currencyList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == finStatBankForm.currency}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="submitForm();"/>
								</td>
							</tr>
							<tr>
							
								<td width="10%" align="right">查询日期(起)：</td>
								<td width="30%">
									<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd'})"/>
									<span id="startDateError" style="color: red;"></span>
								</td>
								<td width="10%" align="right">查询日期(止)：</td>
								<td width="30%"><input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/>
											<span id="endDateError" style="color: red;"></span>
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
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                                    <th class="border">备付金银行名称</th>
                                    <th class="border">备付金银行账号</th>
                                    <th class="border">币种</th>
                                    <th class="border">借方发生额</th>
                                    <th class="border">贷方发生额</th>
                                    <th class="border">发生额</th>
                                    <th class="border">期初余额</th>
                                    <th class="border">期末余额</th>
                                    <th class="border">差额</th>
                                    <th class="border">调整后金额</th>
                                    <th class="border">日期</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty finStatBankList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${finStatBankList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.sacBankName}</td>
                                    <td class="fontfamily1">${item.bankAcc}</td>
                                    <td class="fontfamily1">
                                    	<c:forEach items="${currencyList}" var="sys">
											<c:if test="${item.currency eq sys.dicCode}">${sys.dicDesc }</c:if>
										</c:forEach>
                                    </td>
                                    <td class="fontfamily1">${item.fDebit}</td>
                                    <td class="fontfamily1">${item.fCredit}</td>
                                    <td class="fontfamily1">${item.amount}</td>
                                    <td class="fontfamily1">${item.openBal}</td>
                                    <td class="fontfamily1">${item.closeBal}</td>
                                    <td class="fontfamily1">${item.diffSum}</td>
                                    <td class="fontfamily1">${item.dealSum}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.statTime}" pattern="yyyy/MM/dd"/></td>
                                    <td class="fontfamily1">
										<input type="button" value="详情" onclick="queryDetail('${item.sacBankName}','${item.codeId}','${item.currency}','<fmt:formatDate value="${item.statTime}" pattern="yyyyMMdd"/>');">
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/preparedFundQuery"/>
									</td>
								</tr>
							</table>
						</div>
             </div>
	</div>
<!-- /Body -->
</body>
</html>