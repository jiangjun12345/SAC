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
		var trxType = "${otrxInfo.trxType}";/* 购结汇类型  */
		$('select[name="trxType"]').find("option[value="+trxType+"]").attr("selected",true);
		var payCurrency = "${otrxInfo.payCurrency}";/* 付款币种  */
		$('select[name="payCurrency"]').find("option[value="+payCurrency+"]").attr("selected",true);
		var draccNodeCode = "${otrxInfo.draccNodeCode}";/* 付款银行  */
		$('select[name="draccNodeCode"]').find("option[value="+draccNodeCode+"]").attr("selected",true);
		var craccNodeCode = "${otrxInfo.craccNodeCode}";/* 收款银行  */
		$('select[name="craccNodeCode"]').find("option[value="+craccNodeCode+"]").attr("selected",true);
		var sacCurrency = "${otrxInfo.sacCurrency}";/* 收款币种  */
		$('select[name="sacCurrency"]').find("option[value="+sacCurrency+"]").attr("selected",true);
		
		/* 点击查询时 */
		$("#query").click(function(){
			var startDate = $("#startDate").val();
			if(startDate==""){
				$("#validateDateMsg").html("起始日期不能为空! ");
				return false;
			}else{
				$("#purchaseAndPayForm").submit();
			}
		});
		/* 点击下载按钮  */
		$("#download").click(function(){
			$("#purchaseAndPayForm").attr("action","${ctx}/purchaseAndPayDownload").submit();
			$("#purchaseAndPayForm").attr("action","${ctx}/purchaseAndPayQuery");
		});
	});
	
	/*查看购汇批次详情 */
	function trxDetailShow(trxBatchId){
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var trxType = $("#trxType").val();
		var draccCusName = $("#draccCusName").val();
		var sacCurrency = $("#sacCurrency").val();
		var payCurrency = $("#payCurrency").val();
		var pageNo = ${pageNo};
		$.ajax({
			type:"POST",
			url:"purchaseAndPayDetailQuery",
			async:false,
			data:{
				startDate:startDate,
				endDate:endDate,
				trxType:trxType,
				draccCusName:draccCusName,
				sacCurrency:sacCurrency,
				payCurrency:payCurrency,
				pageNo:pageNo,
				saveFlag:"Y"
			},
			success:function(){
				window.location.href = "purchaseAndPayDetailQuery?trxBatchId="+trxBatchId;
			}
		});
	};
</script>
<title>购付汇结果查询</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="purchaseAndPayForm" action="purchaseAndPayQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">查询时间(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
	 						</td>
	 						<td align="right">查询时间(止):</td>
	 						<td>
	 							<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
	 								onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
	 						</td>
	 						<td align="right">交易类型：</td>
	 						<td>
	 							<select id="trxType" name="trxType" class="select1" >
									<option value="" >全部</option>
									<option value="3411" >购汇(3411)</option>
									<option value="3412" >付汇(3412)</option>
									<option value="3423" >结汇(3423)</option>
								</select>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">批次号：</td>
	 						<td>
								<input type="text" class="txt2" id="trxBatchNo" name="trxBatchNo" value="${otrxInfo.trxBatchNo}"/>
	 						</td>
	 						<td align="right">收款币种：</td>
	 						<td>
	 							<select id="sacCurrency" name="sacCurrency" class="select1" >
									<option value="" >全部</option>
									<c:forEach items="${ccyMap}" var="cur">
										<option value="${cur.key}" >${cur.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 						<td align="right">付款币种：</td>
	 						<td>
								<select id="payCurrency" name="payCurrency" class="select1" >
									<option value="" >全部</option>
									<c:forEach items="${ccyMap}" var="cur">
										<option value="${cur.key}" >${cur.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 					</tr>
	 					<tr>
	 						<%-- <td align="right">商户号：</td>
	 						<td>
								<input type="text" class="txt2" id="draccCusCode" name="draccCusCode" value="${otrxInfo.draccCusCode eq null?draccCusCode:otrxInfo.draccCusCode}"/>
	 						</td> --%>
	 						<td align="right">商户名称：</td>
	 						<td>
								<input type="text" class="txt2" id="draccCusName" name="draccCusName" value="${otrxInfo.draccCusName eq null?draccCusName:otrxInfo.draccCusName}"/>
	 						</td>
	 						<td colspan="2"><span id="validateDateMsg" style="color: red;"></span></td>
	 						<td align="right"><input id="query" class="bluebtn" type="button" value="查询"></td>
	 						<td><input id="download" class="bluebtn" type="button" value="下载"></td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<div width="500px" style="overflow:scroll;">
	 			<table width="1500px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
		 				<th>交易日期</th>
						<th>交易类型</th>
						<th>商户号</th>
						<th>商户名称</th>
						<th>批次号</th>
						<th>流水号</th>
						<th>笔数</th>
						<th>付款银行</th>
						<th>付款银行账号</th>
						<th>付款金额</th>
						<th>付款币种</th>
						<th>收款银行</th>
						<th>收款银行账号</th>
						<th>收款金额</th>
						<th>收款币种</th>
						<th>购结汇汇率</th>
		 			</tr>
		 			<c:if test="${empty otrxInfoList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="trxDetail" items="${otrxInfoList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
	                     	<td class="fontfamily1">
								<p><fmt:formatDate value="${trxDetail.trxTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
							</td>
							<td class="fontfamily1">${trxDetail.trxType}</td>
							<td class="fontfamily1">${trxDetail.draccCusCode}</td>
							<td class="fontfamily1">${trxDetail.draccCusName}</td>
							<td class="fontfamily1">${trxDetail.trxBatchNo}</td>
							<td class="fontfamily1">${trxDetail.trxSerialNo}</td>
							<td class="fontfamily1">
								<c:if test="${trxDetail.memo > 0}"><!-- 笔数大于0 -->
					      			<a href="javascript:void(0);" onclick="trxDetailShow('${trxDetail.trxSerialNo}')"><span style="text-decoration:underline">${trxDetail.memo}</span></a>
								</c:if>
								<c:if test="${trxDetail.memo <= 0}"><!-- 笔数大于0 -->
					      			${trxDetail.memo}
								</c:if>
							</td>
							<td class="fontfamily1">${trxDetail.draccBankName}</td>
							<td class="fontfamily1">${fn:substring(trxDetail.draccNo,fn:length(trxDetail.draccNo)-4,fn:length(trxDetail.draccNo))}</td>
							<td class="fontfamily1">${trxDetail.payAmount}</td>
							<td class="fontfamily1">${trxDetail.payCurrency}</td>
							<td class="fontfamily1">${trxDetail.craccBankName}</td>
							<td class="fontfamily1">${fn:substring(trxDetail.craccNo,fn:length(trxDetail.craccNo)-4,fn:length(trxDetail.craccNo))}</td>
							<td class="fontfamily1">${trxDetail.sacAmount}</td>
							<td class="fontfamily1">${trxDetail.sacCurrency}</td>
							<td class="fontfamily1">${trxDetail.exRate}</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/purchaseAndPayQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>