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
		<title>预存手工销账经办</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

/* function queryPrestoreMsg(){

	var url = "prestoreDetailQuery";
	var startDate = $("#startDate1").value;
	var endDate = $("#endDate1").value;
	var applyCode = $("#applyCode1").value;
	var draccName = $("#draccName").value;
	var payAmount = $("#payAmount1").value;
	$.ajax({
		url : url,
		cache : false,
		async : false,
		data : {
			startDate : startDate,
			endDate : endDate,
			applyCode : applyCode,
			draccName : draccName,
			payAmount : payAmount,
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.success){
				
				$("#table2tbody").hmtl();
			}else{
			}
		},
		error : function(data) {
		}
	});
	
} */


function clearText1(){
	document.getElementById("startDate1").value="";
	document.getElementById("endDate1").value="";
	document.getElementById("applyCode1").value="";
	document.getElementById("payAmount1").value="";
	document.getElementById("draccName").value="";
}

</script>
</head>
<body>
<!-- Body -->
    		<div class="con ">
    			<div>
					<span style="font-size: 13px;color: #666666; font-family: 微软雅黑;">预存交易信息:</span>
				</div>
				<form:form id="subForm1" action="${ctx}/prestoreTrxQuery" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">交易日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate1" name="startDate1" value="${startDate1}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate1\',{d:-2000})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate1\')}'})"/>
		 						</td>
		 						<td align="right">交易日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate1" name="endDate1" value="${endDate1}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate1\',{d:2000})}',minDate:'#F{$dp.$D(\'startDate1\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="12%" align="right">八位码：</td>
								<td width="30%">
							    <input  class="txt2" name ="applyCode1" id="applyCode1" value="${applyCode1}" />
								</td>
								<td width="12%" align="right">企业名称：</td>
								<td width="30%">
								 <input  name="draccName" class="txt2" id="draccName" value="${draccName}"/>
								</td>
							</tr>
							<tr>
								<td width="12%" align="right">金额(CNY)：</td>
								<td width="30%">
								<input   class="txt2" id="payAmount1" name="payAmount1" value="${payAmount1}"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryPrestoreMsg();"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clearText1();"/>
								</td>
							</tr>
							
							
						</table>
					</div>
				</form:form>
			</div>
    		<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id="table2" width="100%" border="0" cellpadding="0" cellspacing="0">
        		<thead>
                                <tr height="25" bgcolor="#cccccc">
                               		<th class="border">选择</th>
                                    <th class="border">银行流水号</th>
                                    <th class="border">八位码</th>
                                    <th class="border">付款企业名称</th>
                                    <th class="border">金额</th>
                                    <th class="border">到账日期</th>
                                    <th class="border">失败原因</th>
                                </tr>
                </thead>
                <tbody id="table2tbody">
                                <c:forEach items="${detailList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35" id="${item.oflDepositId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee" id="${item.oflDepositId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td> <input type="radio" name = "detail" value="${item.oflDepositId}"/></td>
                                    <td class="fontfamily1">${item.bankSerialNo}</td>
                                    <td class="fontfamily1">${item.applyCode}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.bankTrxDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1">${item.dealMemo}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize1}" count="${count1}" pageNo="${pageNo1}" url="/prestoreTrxQuery"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
    		<div class="table fontcolor4 fontsize1 fontfamily2">
    		<table id="table3" width="100%" border="0" cellpadding="0" cellspacing="0">
    				<tr>
    					<td align="center">
    						<input align="middle" type="button" class="bluebtn" value="手工销账"  onClick="manualMatch();"/>&nbsp;
    					</td>
    				</tr>	
    				
    				
    		</table>
    		
    		</div>
<!-- /Body -->
</body>
</html>