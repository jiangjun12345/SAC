<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		/* 下拉选项默认赋值  */
		var bankNodeCode = "${chnSettlement.bankNodeCode}";/* 银行节点    */
		$('select[name="bankNodeCode"]').find("option[value="+bankNodeCode+"]").attr("selected",true);
		var currencyType = "${chnSettlement.currencyType}";/* 币种   */
		$('select[name="currencyType"]').find("option[value="+currencyType+"]").attr("selected",true);
		
		/* 点击查询时 */
		$("#query").click(function(){
			var startDate = $("#startTransDate").val();
			if(startDate==""){
				$("#validateDateMsg").html("起始日期不能为空! ");
				return false;
			}else{
				$("#reportFormQuery").submit();
			}
		});
		/* 点击下载按钮  */
		$("#download").click(function(){
			$("#reportFormQuery").attr("action","${ctx}/channelSettlementDownload").submit();
			$("#reportFormQuery").attr("action","${ctx}/channelSettlementQuery");
		});
	});
	function queryDetail(chnBatchNo){
		var startSacDate = $("#startSacDate").val();
		var endSacDate = $("#endSacDate").val();
		var startTransDate = $("#startTransDate").val();
		var endTransDate = $("#endTransDate").val();
		var bankNodeCode = $("#bankNodeCode").val();
		var currencyType = $("#currencyType").val();
		var pageNo = ${pageNo};
		$.ajax({
			type:"POST",
			url:"chnSetDetailQuery",
			async:false,
			data:{
				startSacDate:startSacDate,
				endSacDate:endSacDate,
				startTransDate:startTransDate,
				endTransDate:endTransDate,
				bankNodeCode:bankNodeCode,
				currencyType:currencyType,
				pageNo:pageNo,
				saveFlag:"Y"
			},
			success:function(){
				window.location.href = "chnSetDetailQuery?chnBatchNo="+chnBatchNo;
			}
		});	
	}
</script>
<title>渠道应收款查询及下载</title>
</head>
<body>
	 <div class="content">
	 	<div class="con ">
	 		<form id="reportFormQuery" action="channelSettlementQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">清算日期(起):</td>
							<td>
								<input type="text" class="txt2" id="startSacDate" name="startSacDate" value="${startSacDate}" 
									onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endSacDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
							</td>
							<td align="right">清算日期(止):</td>
							<td>
								<input type="text" class="txt2" id="endSacDate" name="endSacDate" value="${endSacDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startSacDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
							</td>
						</tr>
						<tr>
							<td align="right">到账日期(起):</td>
							<td>
								<input type="text" class="txt2" id="startTransDate" name="startTransDate" value="${startTransDate}" 
									onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endTransDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
							</td>
							<td align="right">到账日期(止):</td>
							<td>
								<input type="text" class="txt2" id="endTransDate" name="endTransDate" value="${endTransDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startTransDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
							</td>
							<td><span id="validateDateMsg" style="color: red;"></span></td>
						</tr>
						<tr>
							<td align="right">清算行名称:</td>
							<td>
								<select name="bankNodeCode" class="select1" id="bankNodeCode">
									<option value="" >全部</option>
									<c:forEach items="${bankMap}" var="bank">
										<option value="${bank.key}" >${bank.value}</option>
									</c:forEach>
								</select>
							</td>
							<td align="right">币种:</td>
							<td>
								<select name="currencyType" class="select1" id="currencyType" >
									<option value="" >全部</option>
									<c:forEach items="${ccyMap}" var="cur">
										<option value="${cur.key}" >${cur.value}</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="query" class="bluebtn" type="button" value="查询">
								<input id="download" class="bluebtn" type="button" value="下载">
							</td>
						</tr>
	 				</table>
	 			</div>
			</form>
	 	</div>
	 	
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 			<tr height="35" bgcolor="#cccccc">
					<th>清算行名称</th>
					<th>来款备付金账号</th>
					<th>清算日期</th>
					<th>到账日期</th>
					<th>类型</th>
					<th>笔数</th>
					<th>总金额</th>
					<th>币种</th>
					<!-- <th>成本</th> -->
					<th>东方支付应收支付渠道金额</th>
					<th>操作</th>
				</tr>
				<c:if test="${empty channelSettlementList}">
                    <tr>
                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                    </tr>
                </c:if>
                <c:set var="totalSum" value="0"></c:set>
                <c:set var="receiveTotSum" value="0"></c:set>
                <c:forEach var="channelSettlement" items="${channelSettlementList}" varStatus="status">
                	 <c:choose>
	                       <c:when test="${status.index %2 == 0 }">
	                           <tr align="center" height="35">
	                       </c:when>
	                       <c:otherwise>
	                           <tr align="center" height="35" bgcolor="#eeeeee">
	                       </c:otherwise>
                     </c:choose>
						<td class="fontfamily1">${channelSettlement.sacBankName}</td>
						<td class="fontfamily1">${channelSettlement.accountNumber}</td>
						<td class="fontfamily1">${channelSettlement.sacDate}</td>
						<td class="fontfamily1">${channelSettlement.transDate}</td>
						<td class="fontfamily1">
							<c:if test="${channelSettlement.type eq 'N'}">正常</c:if>
							<c:if test="${channelSettlement.type eq 'L'}">长款</c:if>
							<c:if test="${channelSettlement.type eq 'S'}">短款</c:if>
							<c:if test="${channelSettlement.type eq 'O'}">其他</c:if>
						</td>
						<td class="fontfamily1">${channelSettlement.totalNum}</td>
						<td class="fontfamily1">${channelSettlement.totalSum}</td>
						<td class="fontfamily1">
							<c:forEach items="${ccyMap}" var="cur">
								<c:if test="${channelSettlement.currencyType eq cur.key}">${cur.value}</c:if>
							</c:forEach>
						</td>
						<%-- <td class="fontfamily1">${channelSettlement.trxCost}</td> --%>
						<td class="fontfamily1">${channelSettlement.receiveTotSum}</td>
						<td class="fontfamily1"><a href="javascript:void(0)" onclick="queryDetail('${channelSettlement.chnBatchNo}')">查看详情</a></td>
                     </tr>
                </c:forEach>
               	<c:forEach items="${countList}" var="countMap" varStatus="status">
               		<tr align="center" height="35">
                		<td class="fontfamily1" colspan="6" align="right">${status.index eq 0?"查询总汇总:":""}</td>
						<td class="fontfamily1">${countMap.ALL_TOTAL_SUM}</td>
						<td class="fontfamily1">${countMap.CURRENCY_TYPE}</td>
						<td class="fontfamily1">${countMap.ALL_RECEIVE_TOT_SUM}</td>
					</tr>
				</c:forEach>
	 		</table>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/channelSettlementQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	 </div>
	
</body>
</html>