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
		/*对交易类型排序*/
		$('select[name="trxType"] option').sort(function(a,b){
			 return a.value - b.value;
		}).appendTo('select[name="trxType"]');
		
		/* 下拉选项默认赋值  */
		var trxState = "${trxDetail.trxState}";/* 交易状态  */
		$('select[name="trxState"]').find("option[value="+trxState+"]").attr("selected",true);
		var bussType = "${trxDetail.bussType}";/* 业务类型 */
		$('select[name="bussType"]').find("option[value="+bussType+"]").attr("selected",true);
		var payCurrency = "${trxDetail.payCurrency}";/* 交易币种 */
		$('select[name="payCurrency"]').find("option[value="+payCurrency+"]").attr("selected",true);
		var sacCurrency = "${trxDetail.sacCurrency}";/* 购结汇币种 */
		$('select[name="sacCurrency"]').find("option[value="+sacCurrency+"]").attr("selected",true);
		var trxType = "${trxDetail.trxType}";/*交易类型 */
		$('select[name="trxType"]').find("option[value="+trxType+"]").attr("selected",true);
		var payconType = "${trxDetail.payconType}";/*支付渠道类型 */
		$('select[name="payconType"]').find("option[value="+payconType+"]").attr("selected",true);
		var payWay = "${trxDetail.payWay}";/*支付方式 */
		$('select[name="payWay"]').find("option[value="+payWay+"]").attr("selected",true);
		
		/* 点击查询时 */
		$("#query").click(function(){
			var startDate = $("#startDate").val();
			if(startDate==""){
				$("#validateDateMsg").html("起始日期不能为空! ");
				return false;
			}else if(!amountValidate()){
				return false; 
			}else{
				$("#trxDetailForm").submit();
			}
		});
		
		/* 点击下载按钮  */
		$("#download").click(function(){
			$("#trxDetailForm").attr("action","${ctx}/trxDetailDownload").submit();
			$("#trxDetailForm").attr("action","${ctx}/trxDetailQuery");
		});
	});
	
	//验证金额数字类型 
	function amountValidate(){
		var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/; //金额格式正则表达式 
		var amountStart = $('#payAmountStart').val();
		var amountEnd = $('#payAmountEnd').val();
		if(!(amountStart==null||amountStart=="")&&!exp.test(amountStart)){
			$("#validateMsg").html("交易金额(起)格式错误！");
			return false;
		}else if(!(amountEnd==null||amountEnd=="")&&!exp.test(amountEnd)){
			$("#validateMsg").html("交易金额(止)格式错误！");
			return false;
		}else if(!(amountStart==null||amountStart=="")&&(amountEnd==null||amountEnd=="")){
			$("#validateMsg").html("起始金额不能大于终止金额 ");
			return false;
		}else if(!(amountStart==null||amountStart=="")&&parseInt(amountStart) > parseInt(amountEnd)){
			$("#validateMsg").html("起始金额不能大于终止金额 ");
			return false;
		}else{
			$("#validateMsg").html("");
			return true;
		}
	};
</script>
<style type="text/css">
	#table{
		table-layout: fixed;
		width:100%
	}
	.longNo{
		width: 15%;
	}
	td{
		word-break:break-all;
	}
</style>
<title>客户交易明细查询</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="trxDetailForm" action="trxDetailQuery" method="post" >
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">交易创建时间(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
	 						</td>
	 						<td align="right">交易创建时间(止):</td>
	 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
	 						</td>
	 						<td align="right">商户订单号：</td>
	 						<td><input type="text" class="txt2" name="cusBillNo" value="${trxDetail.cusBillNo}" /></td>
	 					</tr>
	 					<tr>
	 						<td align="right">交易完成时间(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="trxSuccStartDate" name="trxSuccStartDate" value="${trxSuccStartDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
	 						</td>
	 						<td align="right">交易完成时间(止):</td>
	 						<td>
								<input type="text" class="txt2" id="trxSuccEndDate" name="trxSuccEndDate" value="${trxSuccEndDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
	 						</td>
	 						<td align="right">业务类型：</td>
	 						<td>
	 							<select name="bussType" class="select1" >
									<option value="" >全部</option>
									<c:forEach items="${bussTypeMap}" var="bussType">
										<option value="${bussType.key}" >${bussType.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">交易金额(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="payAmountStart" name="payAmountStart" value="${payAmountStart}" onblur="amountValidate();" />
	 						</td>
	 						<td align="right">交易金额(止):</td>
	 						<td>
								<input type="text" class="txt2" id="payAmountEnd" name="payAmountEnd" value="${payAmountEnd}" onblur="amountValidate();" />
	 						</td>
	 						<td align="right">交易币种：</td>
	 						<td>
								<select name="payCurrency" class="select1" >
									<option value="" >全部</option>
									<c:forEach items="${payCurrencyMap}" var="cur">
										<option value="${cur.key}" >${cur.value}</option>
									</c:forEach>
								</select>
							</td>
	 					</tr>
	 					<tr>
	 						<td align="right">收款方客户名称：</td>
	 						<td><input type="text" class="txt2" name="craccCusName" value="${trxDetail.craccCusName}" /></td>
	 						<td align="right">收款方银行名称：</td>
	 						<td><input type="text" class="txt2" name="craccBankName" value="${trxDetail.craccBankName}" /></td>
							<td align="right">交易类型：</td>
	 						<td>
	 							<select name="trxType" class="select1" id="trxType" >
									<option value="" >全部</option>
									<c:forEach items="${trxTypeMap}" var="trxType">
										<option value="${trxType.key}">${trxType.value}(${trxType.key})</option>
									</c:forEach>
								</select>
	 						</td>
	 					</tr>
	 					<tr>
	 						<td align="right">付款方客户名称：</td>
	 						<td><input type="text" class="txt2" name="draccCusName" value="${trxDetail.draccCusName}" /></td>
	 						<td align="right">付款方银行名称：</td>
	 						<td><input type="text" class="txt2" name="draccBankName" value="${trxDetail.draccBankName}" /></td>
	 						<td align="right">支付渠道类型：</td>
	 						<td><select name="payconType" class="select1" id="payconType" >
									<option value="" >全部</option>
									<option value="1">B2B支付</option>
									<option value="2">B2C支付</option>
									<option value="3">其他</option>
								</select>
							</td>
	 					</tr>
	 					<tr>
	 						<td align="right">交易流水号：</td>
	 						<td><input type="text" class="txt2" name="trxSerialNo" value="${trxDetail.trxSerialNo}" /></td>
	 						<td align="right">外部交易流水号：</td>
	 						<td><input type="text" class="txt2" name="etrxSerialNo" value="${trxDetail.etrxSerialNo}" /></td>
	 						<td align="right">支付方式：</td>
	 						<td><select name="payWay" class="select1" id="payWay" >
									<option value="" >全部</option>
									<option value="1">快捷</option>
									<option value="2">网银</option>
									<option value="3">代收</option>
									<option value="4">代付</option>
									<option value="5">余额</option>
								</select>
							</td>
	 					</tr>
	 					<tr>
	 						<td align="right">购结汇币种：</td>
	 						<td>
								<select name="sacCurrency" class="select1" >
									<option value="" >全部</option>
									<c:forEach items="${payCurrencyMap}" var="cur">
										<option value="${cur.key}" >${cur.value}</option>
									</c:forEach>
								</select>
							</td>
	 						<td><span id="validateDateMsg" style="color: red;"></span></td>
	 						<td><span id="validateMsg" style="color: red;"></span></td>
	 						<td align="right"><input id="query" class="bluebtn" type="button" value="查询" ></td>
	 						<td><input id="download" class="bluebtn" type="button" value="下载"></td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2" >
	 		<div width="500px" style="overflow:scroll;">
		 		<table width="3000px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
		 				<th>序号</th>
						<th>交易流水号</th>
						<th>原交易流水号</th>
						<th>外部交易流水号</th>
						<th>交易状态</th>
						<th>业务类型</th>
						<th>交易类型</th>
						<th>交易金额</th>
						<th>交易币种</th>
						<th>支付方式</th>
						<th>支付渠道类型</th>
						<th>付款方客户名称</th>
						<th>付款方银行名称</th>
						<th>收款方客户名称</th>
						<th>收款方银行名称</th>
						<th>税金额</th>
						<th>运费</th>
						<th>交易创建时间</th>
						<th>交易完成时间</th>
						<th>商户订单号</th>
						<th>平台订单号</th>
						<th>购结汇币种</th>
						<th>购结汇金额</th>
						<th>购结汇汇率</th>
						<th>内部对账标志</th>
						<th>内部对账状态</th>
						<th>渠道对账标志</th>
						<th>渠道对账状态</th>
						<th>记账状态</th>
						<th>冲正状态</th>
						<th>交易批次号</th>
						<th>交易差错处理类型</th>
						<th>通道成本</th>
						<th>商户手续费</th>
		 			</tr>
		 			<c:if test="${empty trxDetailList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="trxDetail" items="${trxDetailList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
							<td class="fontfamily1">${(pageNo-1)*pageSize+status.index+1}</td>
							<td class="fontfamily1">${trxDetail.trxSerialNo}</td>
							<td class="fontfamily1">${trxDetail.otrxSerialNo}</td>
							<td class="fontfamily1">${trxDetail.etrxSerialNo}</td>
							<td class="fontfamily1">${trxDetail.trxState}</td>
							<td class="fontfamily1">${trxDetail.bussType}</td>
							<td class="fontfamily1">${trxDetail.trxType}</td>
							<td class="fontfamily1">${trxDetail.payAmount}</td>
							<td class="fontfamily1">${trxDetail.payCurrency}</td>
							<td class="fontfamily1">${trxDetail.payWay}</td>
							<td class="fontfamily1">${trxDetail.payconType}</td>
							<td class="fontfamily1">${trxDetail.draccCusName}</td>
							<td class="fontfamily1">						
							<c:choose>
							<c:when test="${trxDetail.memo =='1621' || trxDetail.memo =='1626'}">
							${trxDetail.craccBankName}
							</c:when>
							<c:otherwise>
							${trxDetail.draccBankName}
							</c:otherwise>
							</c:choose>
							</td>
							<td class="fontfamily1">${trxDetail.craccCusName}</td>
							<td class="fontfamily1">						
							<c:choose>
							<c:when test="${trxDetail.memo =='1621' || trxDetail.memo =='1626'}">
							${trxDetail.draccBankName}
							</c:when>
							<c:otherwise>
							${trxDetail.craccBankName}
							</c:otherwise>
							</c:choose>
							</td>
							<td class="fontfamily1">${trxDetail.taxAmount}</td>
							<td class="fontfamily1">${trxDetail.transportExpenses}</td>
							<td class="fontfamily1">
								<p><fmt:formatDate value="${trxDetail.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
							</td>
							<td class="fontfamily1">
								<p><fmt:formatDate value="${trxDetail.trxSuccTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
							</td>
							<td class="fontfamily1">${trxDetail.cusBillNo}</td>
							<td class="fontfamily1">${trxDetail.platBillNo}</td>
							<td class="fontfamily1">${trxDetail.sacCurrency}</td>
							<td class="fontfamily1">${trxDetail.sacAmount}</td>
							<td class="fontfamily1">${trxDetail.exRate}</td>
							<td class="fontfamily1">${trxDetail.innConFlag}</td>
							<td class="fontfamily1">${trxDetail.innConStatus}</td>
							<td class="fontfamily1">${trxDetail.chaConFlag}</td>
							<td class="fontfamily1">${trxDetail.chaConStatus}</td>
							<td class="fontfamily1">${trxDetail.accountStatus}</td>
							<td class="fontfamily1">${trxDetail.reversalStatus}</td>
							<td class="fontfamily1">${trxDetail.trxBatchNo}</td>
							<td class="fontfamily1">${trxDetail.trxErrDealType}</td>
							<td class="fontfamily1">${trxDetail.channelCost}</td>
							<td class="fontfamily1">${trxDetail.cusCharge}</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height:auto; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0">
					<tr>
	                	<td class="fontfamily1" colspan="12" align="left">
	                		<c:if test="${not empty currentMap}">
				    			<span>当页汇总：
						    		<c:forEach items="${currentMap}" var="curMap" >
						    			<span>${curMap.key}:${curMap.value}元&nbsp;</span>
						    		</c:forEach>
					    		</span>
				    		</c:if>
	                	</td>
	                </tr>
	                <tr>
	                	<td class="fontfamily1" colspan="12" align="left">
				    		<c:if test="${not empty allTrxDetailMap}">
				    			<span>查询汇总：
						    		<c:forEach items="${allTrxDetailMap}" var="allMap" >
						    			<span>${allMap.key}:${allMap.value}元&nbsp;</span>
						    		</c:forEach>
					    		</span>
				    		</c:if>
	                	</td>
	                </tr>
					<tr>
						<td align="right" >
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/trxDetailQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>