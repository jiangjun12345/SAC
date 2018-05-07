<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function fundTransferConfirm(cusNo,sacCurrencyValue,bussType){
			var url="getFundDetailByCusNo"; //转向网页的地址;
		    var name="出款详细信息";                        //网页名称，可为空;
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
		               +',directories=yes'//是否添加目录按钮。默认为 yes
		               +',fullscreen=no' //是否使用全屏模式显示浏览器
		               +',location=no'//是否显示地址字段。默认是 yes
		               +',menubar=no'//是否显示菜单栏。默认是 yes
		               +',resizable=no'//窗口是否可调节尺寸。默认是 yes
		               +',scrollbars=yes'//是否显示滚动条。默认是 yes
		               +',status=yes'//是否添加状态栏。默认是 yes
		               +',titlebar=yes'//默认是 yes
		               +',toolbar=no'//默认是 yes
		               ;
		    
		  			 var owin = window.open(url+'?cusNo='+cusNo+'&sacCurrency='+sacCurrencyValue+'&bussType='+bussType, name,params);
		  			 oWin.document.title="you title";
		  			 //window.showModalDialog(url+'?cusNo='+cusNo+'&sacCurrency='+sacCurrencyValue,params);
		}
	
</script>
<title>B2B线下出款</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="cusBalanceForm" action="fundCusBalanceQueryInit" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">客户号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="cusNo" name="cusNo" value="${cusNo}" width="100px"/>
	 						</td>
	 						<td align="right">客户名称:</td>
	 						<td>
	 							<input type="text" class="txt2" id="cusName" name="cusName" value="${cusName}" width="100px"/>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">组织机构代码:</td>
	 						<td>
	 							<input type="text" class="txt2" id="orgCardId" name="orgCardId" value="${orgCardId}" width="100px"/>
	 						</td>
	 						
	 						<td width="10%" align="right">业务类型：</td>
								<td width="25%"><select id="bussType" name="bussType"
									class="select1">
										<option value="">全部</option>
										<c:forEach items="${bussTypeList}" var="sys">
											<option value="${sys.dicValue}"
												<option value="${sys.dicValue}" <c:if test="${sys.dicValue == bussType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								<span style="color: red" id="bussTypeError"></span>
								</td>
								
	 						<td align="right"></td>
	 						<td >
	 							<input id="query" class="bluebtn" type="submit" value="查询">
	 						</td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 			<tr height="35" bgcolor="#cccccc">
	 				<th >序号</th>
	 				<th>客户号</th>
	 				<th>客户账号</th>
	 				<th>组织机构代码</th>
	 				<th>商户节点号</th>
					<th>客户名称</th>
					<th>业务类型</th>
					<th>币种</th>
					<th>客户余额</th>
					<th>操作</th>
	 			</tr>
	 			<c:if test="${empty cusBalanceList}">
                    <tr>
                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                    </tr>
                </c:if>
                <c:forEach var="balance" items="${cusBalanceList}" varStatus="status">
                	<c:choose>
	                       <c:when test="${status.index %2 == 0 }">
	                           <tr align="center" height="35">
	                       </c:when>
	                       <c:otherwise>
	                           <tr align="center" height="35" bgcolor="#eeeeee">
	                       </c:otherwise>
                     </c:choose>
                    	<td>${status.index+1}</td>
                     	<td>${balance.cusNo}</td>
                     	<td>${balance.cusNoId}</td>
                     	<td>${balance.orgCardId}</td>
                     	<td>${balance.merchantNcode}</td>
                     	<td>${balance.cusName}</td>
                     	<td>${balance.bussTypeValue}</td>
                     	<td>${balance.sacCurrency}</td>
						<td>${balance.totalAmount}</td>
						<td>
							<c:choose>
								<c:when test="${balance.cusNoId eq null}">-</c:when>
								<c:otherwise>
									<input type="button" value="取回确认" onclick="fundTransferConfirm('${balance.cusNo}','${balance.sacCurrencyValue}','${balance.bussType}');">
								</c:otherwise>
							</c:choose>
						</td>
                     </tr>
                </c:forEach>
	 		</table>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/fundCusBalanceQueryInit"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>