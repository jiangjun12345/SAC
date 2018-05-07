<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>预存批次明细信息</title>
<script type="text/javascript" language="javascript" src="${ctx}/scripts/js/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript">
function downloadDetail(){
	
	if (window.confirm("确定下载？")) {
		$("#subForm").attr("action","${ctx}/prestoreDetailDownload").submit();
	}
}
</script>
</head>
<body>
	<div class="content">
			<div class="table fontcolor4 fontsize1 fontfamily2" height="100%">
        <table  id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;color: #666666; font-family: 微软雅黑;"  >
                                <tr height="25" bgcolor="#cccccc">
                                    <th class="border">批次号</th>
                                    <th class="border">总笔数</th>
                                    <th class="border">总金额</th>
                                    <th class="border">创建时间</th>
                                    <th class="border">批次复核状态</th>
                                    <th class="border">批次经办人</th>
                                </tr>
                                <tr align="center" height="25">
                                	<td class="fontfamily1">${batch.oflDepositBatchId}</td>
                                    <td class="fontfamily1">${batch.batchTcount}</td>
                                    <td class="fontfamily1">${batch.batchTamount}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${batch.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1"><c:if test="${batch.batchState eq '00'}">待复核</c:if><c:if test="${batch.batchState eq '20'}">复核成功</c:if><c:if test="${batch.batchState eq '02'}">复核失败</c:if></td>
                                    <td class="fontfamily1">${batch.operatorName}</td>
                                </tr>
                            </table>
                            </div>
                            
                            <div class="table fontcolor4 fontsize1 fontfamily2">
        <table id="table2" width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;color: #666666; font-family: 微软雅黑;">
        		<thead>
                                <tr height="25" bgcolor="#cccccc">
                                    <th class="border">序号</th>
                                    <th class="border">银行流水号</th>
                                    <th class="border">八位码</th>
                                    <th class="border">付款企业名称</th>
                                    <th class="border">金额</th>
                                    <th class="border">到账日期</th>
                                    <th class="border">失败原因</th>
                                </tr>
                </thead>
                <tbody id="table2tbody">
                                <c:if test="${empty detailList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${detailList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="25" id="${item.oflDepositId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="25" bgcolor="#eeeeee" id="${item.oflDepositId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td class="fontfamily1">${item.bankSerialNo}</td>
                                    <td class="fontfamily1">${item.applyCode}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.bankTrxDate}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
                                    <td class="fontfamily1">${item.dealMemo}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24" style="font-size: 12px;color: #666666; font-family: 微软雅黑;" >
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/getPrestoreDetailByBatch"/>
									</td>
								</tr>
							</table>
							</div>
							
							 <div style="width: 100%; height: 32px; text-align: right;" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24" style="font-size: 12px;color: #666666; font-family: 微软雅黑;" >
									<td align="right" height="24">
										<input name="submitbtn" type="button" 
									 value="下载" class="bluebtn"
									id="submitbtn" onclick="downloadDetail();"/>
									</td>
								</tr>
							</table>
							</div>
							
							<div style="display: none;" class="pagination1 btn" >
							<table width="100%" cellspacing="0" cellpadding="0" height="24" style="font-size: 12px;color: #666666; font-family: 微软雅黑;" >
							<form:form id="subForm" method="POST" >   	    
								<div class="table fontcolor4 fontsize1 fontfamily2">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td width="30%">
											<input   class="txt2" id="oflDepositBatchId" name="oflDepositBatchId" value="${batch.oflDepositBatchId}"/>
											</td>
										</tr>
									</table>
								</div>
							</form:form>
							</table>
							</div>
		</div>
	</div>
	<div class="clear"></div>
</body>
</html>
