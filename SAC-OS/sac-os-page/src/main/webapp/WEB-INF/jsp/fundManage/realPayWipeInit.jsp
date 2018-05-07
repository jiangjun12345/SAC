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
		<title>实付勾对</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	
	
	function clearText(){
		document.getElementById("finSign").value=""; 
		document.getElementById("transferStatus").value=""; 
	}
	
	function wipe(id){
		if(window.confirm("确定勾对？")){
			var url = "realPayWipe";
			$.ajax( {
				url : url,
				cache : false,
				async : false,
				data : {
					id	 :	id
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.success){
						alert("勾对成功!");
					}else{
						alert("勾对失败!");
					}
					window.location.reload();
				},
				error : function(data){
					   alert("勾对失败!");
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
				<form:form commandName="sacCusSettlement" action="${ctx}/realPayWipeInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td width="12%" align="right">结算金额：</td>
								<td width="30%">
								<span> <form:input path = "sacAmount" class="txt2" id="sacAmount" /></span>
                                <span><form:errors path="sacAmount" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">币种：</td>
								<td width="30%">
								<span> <form:input  
										path="currencyType" class="txt2" id="currencyType" /> </span>
										<span><form:errors path="currencyType" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">勾对标志：</td>
								<td width="30%">
									<select id="finSign" name="finSign" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${wipeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCusSettlement.finSign}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							
							<tr>
								<td width="12%" align="right">客户号：</td>
								<td width="30%">
								<span> <form:input path = "cusNo" class="txt2" id="cusNo" /></span>
                                <span><form:errors path="cusNo" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">客户名称：</td>
								<td width="30%">
								<span> <form:input  
										path="cusName" class="txt2" id="cusName" /> </span>
										<span><form:errors path="cusName" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">结算划款状态：</td>
								<td width="30%">
									<select id="transferStatus" name="transferStatus" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${settlementList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCusSettlement.transferStatus}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td width="12%" align="right">付款方账号：</td>
								<td width="30%">
								<span> <form:input  
										path="draccNo" class="txt2" id="draccNo" /> </span>
										<span><form:errors path="draccNo" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">付款账户名称：</td>
								<td width="30%">
								<span> <form:input  
										path="draccName" class="txt2" id="draccName" /> </span>
										<span><form:errors path="draccName" cssStyle="color:red"/></span>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="submit" 
									 value="查询" class="bluebtn"
									id="submitbtn" />
								</td>
							</tr>
							
							<tr>
								<td width="12%" align="right">付款方银行名称：</td>
								<td width="30%">
								<span> <form:input path = "draccBankName" class="txt2" id="draccBankName" /></span>
                                <span><form:errors path="draccBankName" cssStyle="color:red"/></span>
								</td>
								<td width="12%" align="right">划账日期：</td>
								<td width="20%"><form:input type="text" name="sacDate" path="sacDate"
											id="sacDate" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}" style="width: 50%" /></td>
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
                                    <th class="border">客户号</th>
                                    <th class="border">客户名称</th>
                                    <th class="border">付款方账号</th>
                                    <th class="border">付款账号名称</th>
                                    <th class="border">结算金额</th>
                                    <th class="border">币种</th>
                                    <th class="border">总笔数</th>
                                    <th class="border">勾对标志</th>
                                    <th class="border">结算划款状态</th>
                                    <th class="border">划账日期</th>
                                    <th class="border">创建日期</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty sacCusSettlementList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacCusSettlementList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.cusNo}</td>
                                    <td class="fontfamily1">${item.cusName}</td>
                                    <td class="fontfamily1">${item.draccNo}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1">${item.sacAmount}</td>
                                    <td class="fontfamily1">${item.currencyType}</td>
                                    <td class="fontfamily1">${item.totalNum}</td>
                                    <td class="fontfamily1"><c:if test="${item.finSign eq 'Y'}">已勾对</c:if><c:if test="${item.finSign eq 'N'}">未勾对</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.transferStatus eq 'Y'}">已划款</c:if><c:if test="${item.transferStatus eq 'N'}">未划款</c:if><c:if test="${item.transferStatus eq 'W'}">划款中</c:if></td>
                                    <td class="fontfamily1">${item.sacDate}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <c:if test="${item.finSign eq 'N'  &&item.transferStatus eq 'Y' &&item.currencyType ne 'CNY'}"> 
	                                    <td class="fontfamily1"><input type="button" value="勾对"
										onClick="wipe('${item.id}');">&nbsp;
									</td>
                                    </c:if>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/realPayWipeInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
    



<!-- /Body -->
</body>
</html>