<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="/WEB-INF/tag/easipay-tag.tld" prefix="easipay" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>修改商户信息</title>
	
</head>
<body>
    <div class="con">
    <div class="table fontcolor4 fontsize1 fontfamily2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                                    <th class="border">企业组织机构码</th>
                                    <th class="border">企业名</th>
                                    <th class="border">企业短名</th>
                                    <th class="border">企业英文名</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty ucOrgInfoList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${ucOrgInfoList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">${item.orgCode}</td>
                                    <td class="fontfamily1">${item.orgName}</td>
                                    <td class="fontfamily1">${item.shortName}</td>
                                    <td class="fontfamily1">${item.engName}</td>
                                    <td class="fontfamily1"><a href="${ctx}/editOrgInfoInit?orgId=${item.orgId}"> 修改
                                    </a></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/orgManageInit"/>
									</td>
								</tr>
							</table>
						</div>
                            </div>
    </div>



<!-- /Body -->
</body>
</html>