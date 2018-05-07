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
		<title>人工补单</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	/*对交易类型排序*/
	$('select[name="trxCode"] option').sort(function(a,b){
		 return a.value - b.value;
	}).appendTo('select[name="trxCode"]');
	var trxCode = "${sacRecDifferences.trxCode}";/*交易类型 */
	$('select[name="trxCode"]').find("option[value="+trxCode+"]").attr("selected",true);
}); 

function clearText(){
	document.getElementById("payconType").value="";
	document.getElementById("supplementFlag").value="";
	document.getElementById("trxCode").value="";
	document.getElementById("currencyType").value="";
}
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

  function supplementTrx(trxSerialNo,trxCode,payconType,supplementFlag,recStartDate,status){
	  if(window.confirm("确定?")){
			var url = "supplementTrx";
			$.ajax( {
				url : url,
				cache : false,
				async : false,
				data : {
					trxSerialNo : trxSerialNo,
					payconType : payconType,
					supplementFlag : supplementFlag,
					recStartDate : recStartDate,
					status : status,
					trxCode	 :	trxCode
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("补单成功!");
						window.location.reload();
					}else{
						alert("补单失败!");
						window.location.reload();
					}
					
				},
				error : function(data){
					   alert("补单失败!");
					   window.location.reload();
				}
			});
		}
	    
	};
	
	
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    
      <div class="con ">
				<form:form id="subForm" commandName="sacRecDifferences" action="${ctx}/supplementTrxQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%" align="right">交易日期(起)：</td>
								<td>
									<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y%M%d\'}',dateFmt:'yyyyMMdd'})"/>
									<span id="startDateError" style="color: red;"></span>
								</td>
								<td width="10%" align="right">交易日期(止)：</td>
								<td ><input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y%M%d'})"/>
											<span id="endDateError" style="color: red;"></span>
								</td>
								<td  align="right">交易类型：</td>
								<td >
									<select id="trxCode" name="trxCode" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${trxTypeList}" var="sys">
											<%-- <option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacRecDifferences.trxCode}"> selected="selected"</c:if>>${sys.dicDesc}(${sys.dicValue})</option> --%>
											<option value="${sys.dicValue}">${sys.dicDesc}(${sys.dicValue})</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td  align="right">渠道支付金额：</td>
								<td >
								<span> <form:input path = "payAmount" class="txt2" id="payAmount" /></span>
                                <span id="payAmountError" style="color: red;"></span>
								</td>
								<td  align="right">补单标志：</td>
								<td >
									<select id="supplementFlag" name="supplementFlag" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${supplementFlagList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacRecDifferences.supplementFlag}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							<td align="right">支付渠道类型:</td>
	 						<td>
	 							<select name="payconType" class="select1" width="100" id="payconType" >
									<option value="" >全部</option>
									<option value="1" <c:if test="${'1' == sacRecDifferences.payconType}"> selected="selected"</c:if>>B2B</option>
									<option value="2" <c:if test="${'2' == sacRecDifferences.payconType}"> selected="selected"</c:if>>B2C</option>
								</select>
	 						</td>
								
							</tr>
							
							<tr>
								
								<td width="10%" align="right">交易流水号：</td>
								<td >
								<span> <form:input  
										path="trxSerialNo" class="txt2" id="trxSerialNo" /> </span>
										<span><form:errors path="trxSerialNo" cssStyle="color:red"/></span>
								</td>
								<td  align="right">币种：</td>
								<td >
									<select id="currencyType" name="currencyType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacRecDifferences.currencyType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();"/>
									<input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
							</tr>							
							
						</table>
					</div>
				</form:form>
			</div>
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
			<div width="500px" style="overflow:scroll;">
        <table width="1500px" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                                	<th class="border">操作</th>
                                    <th class="border">交易流水号</th>
                                    <th class="border">差错原因</th>
                                    <th class="border">对账文件支付金额&nbsp;</th>
                                   <th class="border">原交易支付金额&nbsp;</th>
                                    <th class="border">币种&nbsp;</th>
                                    <th class="border">交易类型&nbsp;</th>
                                    <th class="border">支付渠道类型&nbsp;</th>
                                    <th class="border">处理状态&nbsp;</th>
                                    <th class="border">差错类型&nbsp;</th>
                                    <th class="border">交易日期</th>
                                    <th class="border">外部流水号</th>
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
	                                    <td class="fontfamily1">
	                                    <c:if test="${item.supplementFlag eq 'N' && item.status eq 'N'}"> 
	                                    <input type="button" value="补单"
										onClick="supplementTrx('${item.trxSerialNo}','${item.trxCode}','${item.payconType}','${item.supplementFlag}','${item.recStartDate}','${item.status}');">&nbsp;
										</c:if>
									</td>
                                    <td class="fontfamily1">${item.trxSerialNo}</td>
                                    <td class="fontfamily1">${item.recDiffDesc}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1"><c:if test="${item.recDiffType=='INN001'}">${item.oriInnConAmount}</c:if></td>
                                    <td class="fontfamily1">
                                    	<c:forEach items="${currencyTypeList}" var="sys">
											<c:if test="${sys.dicCode eq item.currencyType}">${sys.dicDesc}</c:if>
										</c:forEach>
									</td>
                                    <td class="fontfamily1">${item.trxCode}</td>
                                    <td class="fontfamily1"><c:if test="${item.payconType eq '1'}">B2B支付</c:if><c:if test="${item.payconType eq '2'}">B2C支付</c:if><c:if test="${item.payconType eq '4'}">代收付</c:if><c:if test="${item.payconType eq '5'}">购付汇</c:if><c:if test="${item.payconType eq '6'}">外汇通</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.status eq 'S'}">已处理</c:if><c:if test="${item.status eq 'N'}">未处理</c:if></td>
                                    <td class="fontfamily1">${item.recDiffType}</td>
                                    <td class="fontfamily1">${item.recStartDate}</td>
                                    <td class="fontfamily1">&nbsp;${item.bankSerialNo}</td>
									</c:forEach>
                            </table>
                            </div>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/supplementTrxQueryInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
    



<!-- /Body -->
</body>
</html>