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
		<title>长款查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
  
	function queryInfo(){
		var payAmount = document.getElementById("payAmount").value;
		var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
		if(payAmount.length>0){
			var flag = payAmount.match(regexp);
			if(flag==null){
				document.getElementById("payAmountError").innerHTML="格式非法";
				return;
			}
			if(payAmount.length>20){
				document.getElementById("payAmountError").innerHTML="长度超长";
				return;
			}
		}
		$("#subForm").submit();
		
	}
  
  
  function showOutOfTrxNo(recType){
	  if(recType=='01'){
		  document.getElementById("outTransactionTr").style.display="none";
	  }else{
		  document.getElementById("outTransactionTr").style.display="";
	  }
	    
	};
	
	function clearText(){
		
	}
	
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form id="subForm" commandName="sacRecDifferences" action="${ctx}/longPatternQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">对账日期：</td>
									<%--<span> <form:input path="recDate" class="txt2" id="recDate"  /> </span>
									 <input type="text" name="date"
											id="date" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}" style="width: 80%" />
									<span id="errorRecDate"><form:errors path="recDate" cssStyle="color:red"/></span> --%>
									<td width="30%"><form:input type="text" name="recDate" path="recDate"
											id="recDate" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}" style="width: 50%" /></td>
								<td width="10%" align="right">外部流水号：</td>
								<td width="40%">
									<span> <form:input  
											path="bankSerialNo" class="txt2" id="bankSerialNo" /> </span>
									<span><form:errors path="bankSerialNo" cssStyle="color:red" /></span>
								</td>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();"/>
								</td>
							</tr>
							
							<tr>
								<td width="12%" align="right">支付金额：</td>
								<td width="40%">
								<span> <form:input path = "payAmount" class="txt2" id="payAmount" /></span>
                                <span id="payAmountError" style="color: red;"></span>
								</td>
								<td width="10%" align="right">交易流水号：</td>
								<td width="40%">
								<span> <form:input  
										path="trxSerialNo" class="txt2" id="trxSerialNo" /> </span>
										<span><form:errors path="trxSerialNo" cssStyle="color:red"/></span>
								</td>
								<td align="right">&nbsp;</td>
								<td><input name="clearBtn" type="button" 
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
                                    <th class="border">交易流水号</th>
                                    <th class="border">外部流水号</th>
                                    <th class="border">支付金额</th>
                                    <th class="border">支付渠道类型</th>
                                    <th class="border">处理状态</th>
                                    <th class="border">对账日期</th>
                                    <th class="border">交易时间</th>
                                    <th class="border">差错原因</th>
                                </tr>
                                <c:if test="${empty sacRecDifferencesList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacRecDifferencesList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.trxSerialNo}</td>
                                    <td class="fontfamily1">${item.bankSerialNo}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.payconType}</td>
                                    <td class="fontfamily1"><c:if test="${item.status eq 'S'}">已处理</c:if><c:if test="${item.status eq 'N'}">未处理</c:if></td>
                                    <td class="fontfamily1">${item.recDate}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.trxTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1">${item.recDiffDesc}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/longPatternQueryInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
    



<!-- /Body -->
</body>
</html>