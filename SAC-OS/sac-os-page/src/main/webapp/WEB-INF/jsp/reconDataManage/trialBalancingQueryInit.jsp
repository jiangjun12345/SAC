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
		<title>试算平衡查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function queryInfo(){
	$("#subForm").submit();
}
</script>
</head>
<body>
    <div class="content">
      <div class="con ">
				<form id="subForm" name="subForm" action="${ctx}/trialBalanceQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td  align="right">查询时间：</td>
								<td >
									<input type="text" class="txt2" id="queryDate" name="queryDate" value="${queryDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'queryDate\',{d:2000})}',maxDate:'%y-%M-%d'})"/>
	 							</td>
	 							<td align="right">币种：</td>
							<td>
								<select id="sacCurrency" name="sacCurrency" class="select1" value="${sacCurrency}">
										<option value="">全部</option>
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacCurrency}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
							</td>
	 							<td align="right">&nbsp;</td>
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();"/>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			
				<div class="table fontcolor4 fontsize1 fontfamily2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
       							<tr height="35" bgcolor="#cccccc">
                                    <th class="border">借方</th>
                                    <th class="border">金额</th>
                                    <th class="border">贷方</th>
                                    <th class="border">金额</th>
                                    <th class="border">币种</th>
                                    <th class="border">差额</th>
                                    <th class="border">时间</th>
                                </tr>
                                <c:if test="${empty finTrialBalancingList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${finTrialBalancingList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1"><c:if test="${item.direction eq '1'}">${item.codeName}</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.direction eq '1'}">${item.balance}</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.direction eq '-1'}">${item.codeName}</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.direction eq '-1'}">${item.balance}</c:if></td>
                                    <td class="fontfamily1">${item.sacCurrency}</td>
                                    <td class="fontfamily1">${item.diffBalance}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            </div>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/trialBalanceQueryInit"/>
									</td>
								</tr>
							</table>
						</div>
    </div>
    
    



<!-- /Body -->
</body>
</html>