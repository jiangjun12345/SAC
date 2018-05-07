<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isELIgnored="false"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	$(function(){
		$("#back").click(function(){
			$("#preparedFundDetailQueryBackForm").submit();
		});
	});
	
	function downSubmit(){
		$("#preparedFundDetailForm").attr("target","newWin");
		$("#preparedFundDetailForm").attr("action","${ctx}/finMxDailyYEDownload").submit();
		$("#preparedFundDetailForm").attr("action","${ctx}/preparedFundDetailQuery");
		$("#preparedFundDetailForm").attr("target","");
	}
	
	function showDetail(){
		var url="finMxDailYEDownload"; //转向网页的地址;
	    var name="newWin";                        //网页名称，可为空;
	    var iWidth=700;                          //弹出窗口的宽度;
	    var iHeight=300;                       //弹出窗口的高度;
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
	  			   window.open('',name,params);
		  		  
	}
</script>
<title>备付金每日余额明细查询</title>
</head>

<body>
	<div style="display: none;">
		<form id="preparedFundDetailQueryBackForm" action="preparedFundDetailQueryBack" method="get">
			<input type="text" id="startDate" name="startDate" value="${sessionScope.startDate}"/>
			<input type="text" id="endDate" name="endDate"  value="${endDate}"/>
			<input type="text" id="cusId" name="cusId" value="${sessionScope.cusId}"/>
			<input type="text" id="currency" name="currency"  value="${sessionScope.currency}"/>
			<input type="text" id="pageNo2" name="pageNo" value="${sessionScope.pageNo}"/>
		</form>
	</div>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
	 		<form id="preparedFundDetailForm" action="preparedFundDetailQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">凭证日期:</td>
	 						<td>
	 							<input type="text" class="txt2" id="tradeTime" name="tradeTime" value="${tradeTime}" readonly="readonly"/>
	 						</td>
	 						<td align="right">银行名:</td>
	 						<td>
								<input type="text" class="txt2" id="bankName" name="bankName" value="${bankName}" readonly="readonly"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">币种:</td>
	 						<td>
	 							<c:forEach items="${ccyMap}" var="ccy">
	 								<c:if test="${ccy.key eq currency}">
	 									<input type="text" class="txt2" id="currency" name="currency" value="${ccy.value}" readonly="readonly"></input>
	 								</c:if>
	 							</c:forEach>
	 						</td>
	 						<td>
								<input type="text" class="txt2" id="codeId" name="codeId" value="${codeId}" style="display: none;"/>
								<input type="text" class="txt2" id="ccyEnglish" name="ccyEnglish" value="${currency}" style="display: none;"/>
	 						</td>
	 						<td colspan="2">
	 							<input id="back" class="bluebtn" type="button" value="返回">
	 							<input id="download" class="bluebtn" type="button" value="下载" onclick="showDetail();downSubmit();">
	 						</td>
	 						
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<div style="overflow:scroll;">
		 		<table width="1800px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
						<th>科目代码</th>
		 				<th>凭证号</th>
		 				<th>凭证时间</th>
						<th>借贷标志</th>
						<th>借方发生额</th>
						<th>贷方发生额</th>
						<th>发生额</th>
						<th>期初余额</th>
						<th>期末余额</th>
						<th>结汇损益</th>
						<th>业务类型</th>
						<th>交易类型</th>
						<th>交易时间</th>
						<th>交易流水号</th>
		 			</tr>
		 			<c:if test="${empty preparedFundDetailList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="balance" items="${preparedFundDetailList}" varStatus="status">
	                	<c:set var="flag" value="0" />
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
	                     	<td>${balance.codeId}</td>
							<td>${balance.pzNo}</td>
							<td>
								<fmt:formatDate value="${balance.mxTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
							</td>
							<td>
								<c:choose>
									<c:when test="${balance.direction eq 1}">借</c:when>
									<c:when test="${balance.direction eq -1}">贷</c:when>
								</c:choose>
							</td>
							<td>${balance.fDebit}</td>
							<td>${balance.fCredit}</td>
							<td>${balance.amount}</td>
							<td>${balance.openBal}</td>
							<td>${balance.closeBal}</td>
							<td>${balance.gains eq null?"-":balance.gains}</td>
							<td>
								<c:forEach items="${bussTypeMap}" var="busiType">
									<c:if test="${busiType.key eq balance.busiType }">
										${busiType.value}
										<c:set var="flag" value="1" />
									</c:if>
								</c:forEach>
							</td>
							<td>
								<c:forEach items="${trxTypeMap}" var="trxType">
									<c:if test="${trxType.key eq balance.trxCode }">
										${trxType.value}
										<c:set var="flag" value="1" />
									</c:if>
								</c:forEach>
							</td>
							<td>
								<fmt:formatDate value="${balance.tradeTime}" pattern="yyyy/MM/dd HH:mm:ss"/>
							</td>
							<td>${balance.serno}</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/preparedFundDetailQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>