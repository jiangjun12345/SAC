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
		<title>划款指令批次明细信息</title>
		<script type="text/javascript" language="javascript" src="${ctx}/scripts/js/jquery/jquery-1.7.2.min.js" ></script>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<div class="content">
			<div class="table fontcolor4 fontsize1 fontfamily2" height="100%">
        <table  id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0" style="font-size: 12px;color: #666666; font-family: 微软雅黑;"  >
                                <tr height="25" bgcolor="#cccccc">
                                	<th class="border">序号</th>
                                    <th class="border">预扣流水号</th>
                                    <th class="border">实扣流水号</th>
                                    <th class="border">交易生成时间</th>
                                    <th class="border">出款银行</th>
                                    <th class="border">收款客户名称</th>
                                    <th class="border">收款客户账号</th>
                                    <th class="border">金额</th>
                                    <th class="border">币别</th>
                                    <th class="border">处理结果</th>
                                </tr>
                                <c:if test="${empty detailList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${detailList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="30" id="${item.cmdId}">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="30" bgcolor="#eeeeee" id="${item.cmdId}">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td class="fontfamily1">${item.otrxSerialNo}</td>
                                    <td class="fontfamily1">${item.cmdSerialNo}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1">${item.draccBankBranch}</td>
                                    <td class="fontfamily1">${item.craccName}</td>
                                    <td class="fontfamily1">${item.craccNo}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.payCurrency}</td>
                                    <td class="fontfamily1"><c:if test="${item.cmdState eq 'N'}">新建待发送</c:if><c:if test="${item.cmdState eq 'QS'}">请求发送成功</c:if><c:if test="${item.cmdState eq 'S'}">交易成功</c:if><c:if test="${item.cmdState eq 'F'}">交易失败</c:if><c:if test="${item.cmdState eq 'B'}">挂起，备付金余额不足</c:if><c:if test="${item.cmdState eq 'QR'}">挂起,交易已发至银行等待处理</c:if></td>
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            </div>
                            
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24" style="font-size: 12px;color: #666666; font-family: 微软雅黑;" >
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/getfundDetailByBatch"/>
									</td>
								</tr>
							</table>
							</div>
		</div>
	</div>
	<div class="clear"></div>
</body>
</html>
