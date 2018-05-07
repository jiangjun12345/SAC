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
		<title>预存交易查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

function queryPrestoreMsg(){
	$("#subForm").submit();
} 


function clearText(){
	document.getElementById("startDate").value="";
	document.getElementById("endDate").value="";
	document.getElementById("applyCode").value="";
	document.getElementById("payAmount").value="";
	document.getElementById("draccName").value="";
}

function showDetail(detailId){
	var url="getPrestoreDetailById"; //转向网页的地址;
    var name="预存交易详细信息";                        //网页名称，可为空;
    var iWidth=900;                          //弹出窗口的宽度;
    var iHeight=250;                       //弹出窗口的高度;
    //获得窗口的垂直位置
    var iTop = (window.screen.availHeight-30-iHeight)/2;
    //获得窗口的水平位置
    var iLeft = (window.screen.availWidth-10-iWidth)/2;
    var params='width='+iWidth
  			   +',alwaysRaised=no'//默认是 yes
               +',height='+iHeight
               +',top='+iTop
               +',left='+iLeft
               +',channelmode=no'//是否使用剧院模式显示窗口。默认为 no
               +',directories=no'//是否添加目录按钮。默认为 yes
               +',fullscreen=no' //是否使用全屏模式显示浏览器
               +',location=no'//是否显示地址字段。默认是 yes
               +',menubar=no'//是否显示菜单栏。默认是 yes
               +',resizable=no'//窗口是否可调节尺寸。默认是 yes
               +',scrollbars=no'//是否显示滚动条。默认是 yes
               +',status=no'//是否添加状态栏。默认是 yes
               +',titlebar=no'//默认是 yes
               +',toolbar=no'//默认是 yes
               ;
    
  			   window.open(url+'?oflDepositId='+detailId , name,params);
}

</script>
</head>
<body>
<!-- Body -->
    		<div class="con ">
				<form:form id="subForm" action="${ctx}/prestoreAllTrxQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">交易日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-2000})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">交易日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:2000})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="12%" align="right">八位码：</td>
								<td width="30%">
							    <input  class="txt2" name ="applyCode" id="applyCode" value="${applyCode}" />
								</td>
								<td width="12%" align="right">企业名称：</td>
								<td width="30%">
								 <input  name="draccName" class="txt2" id="draccName" value="${draccName}"/>
								</td>
							</tr>
							<tr>
								<td width="12%" align="right">金额(CNY)：</td>
								<td width="30%">
								<input   class="txt2" id="payAmount" name="payAmount" value="${payAmount}"/>
								</td>
								<td width="10%" align="right">处理状态：</td>
								<td width="25%"><select id="dealState" name="dealState"
									class="select1">
										<option value="">全部</option>
										<c:forEach items="${dealStateList}" var="sys">
											<option value="${sys.dicValue}"
												<c:if test="${sys.dicValue == dealState}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryPrestoreMsg();"/>
								</td>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clearText();"/>
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
                                    <th class="border">序号</th>
                                    <th class="border">银行流水号</th>
                                    <th class="border">批次号</th>
                                    <th class="border">八位码</th>
                                    <th class="border">付款企业名称</th>
                                    <th class="border">金额</th>
                                    <th class="border">到账日期</th>
                                    <th class="border">处理状态</th>
                                    <th class="border">失败原因</th>
                                    <th class="border">操作</th>
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
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td class="fontfamily1">${item.bankSerialNo}</td>
                                    <td class="fontfamily1">${item.oflDepositBatchId}</td>
                                    <td class="fontfamily1">${item.applyCode}</td>
                                    <td class="fontfamily1">${item.draccName}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.bankTrxDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1"><c:if test="${item.dealState eq '00'}">未处理</c:if><c:if test="${item.dealState eq '01'}">成功待复核</c:if><c:if test="${item.dealState eq '02'}">失败待复核</c:if><c:if test="${item.dealState eq '03'}">未成功待处理</c:if><c:if test="${item.dealState eq '04'}">手工销账待复核</c:if><c:if test="${item.dealState eq '05'}">手工销账复核不通过</c:if><c:if test="${item.dealState eq '06'}">待重新制作</c:if><c:if test="${item.dealState eq '10'}">预存成功</c:if></td>
                                    <td class="fontfamily1">${item.dealMemo}</td>
                                    <td class="fontfamily1"><input type="button" value="详细"
									onClick="showDetail('${item.oflDepositId}');">&nbsp;
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/prestoreAllTrxQueryInit"/>
									</td>
								</tr>
							</table>
							</div>
    		</div>
<!-- /Body -->
</body>
</html>