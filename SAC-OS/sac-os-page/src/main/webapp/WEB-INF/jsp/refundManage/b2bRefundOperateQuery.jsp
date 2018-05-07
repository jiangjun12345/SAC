<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/scripts/wdatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript">
	$(function(){
		/* 下拉选项默认赋值  */
		var bankNodeCode = "${sacRefundCommand.bankNodeCode}";/* 银行类型  */
		$('select[name="bankNodeCode"]').find("option[value="+bankNodeCode+"]").attr("selected",true);
		
		var trxState = "${sacRefundCommand.trxState}";/* 退款状态 */
		$('select[name="trxState"]').find("option[value="+trxState+"]").attr("selected",true);
		
		/* 点击查询时 */
		$("#query").click(function(){
			var startDate = $("#startDate").val();
			if(startDate==""){
				$("#validateDateMsg").html("起始日期不能为空! ");
				return false;
			}else{
				$("#refundTrxForm").submit();
			}
		});
		
	});
	
	function clearText(){
		document.getElementById("bankNodeCode").value = "";
		document.getElementById("trxState").value = "";
	}
	
	function queryInfo(){
		$("#subForm").submit();
		
	}
	//点击"全选/全不选"复选框，如果选中，则选中全部复选框，否则取消选中全部复选框  
	function checkboxall(Allname, name) {
		var ischecked = document.getElementById(Allname).checked;
		if (ischecked) {
			checkallbox(name);
		} else {
			discheckallbox(name);
		}
	}
	//选中全部复选框  
	function checkallbox(name) {
		var boxarray = document.getElementsByName(name);
		for ( var i = 0; i < boxarray.length; i++) {
			boxarray[i].checked = true;
		}
	}
	//取消选中全部复选框  
	function discheckallbox(name) {
		var boxarray = document.getElementsByName(name);
		for ( var i = 0; i < boxarray.length; i++) {
			boxarray[i].checked = false;
		}
	}
	
	function batchWipe() {
		var box = document.getElementsByName("box");
		var count = 0;
		var failFlag = false;
		var boxValues = "";
		var firstBankName="";
		for (i = 0; i < box.length; i++) {
			if (box[i].checked) {
				count++;
				var boxValue = box[i].value;
				var boxId = box[i].id;
				var index = boxId.substring(8);
                if(count==1){
                	firstBankName=document.getElementById("bankNodeCode"
    						+ index).value;
                 }else{
                	var bank = document.getElementById("bankNodeCode"
     						+ index).value;
                	if (bank != firstBankName) {
    					alert("付款方银行必须相同");
    					failFlag = true;
    					break;
    				}
                 }
				boxValues = boxValues + boxValue + "|";
			}
		}
		if (count == 0) {
			alert("至少要选择一个，谢谢");
		}
		
		
		if (count > 0 && !failFlag) {
			if (window.confirm("确定下载？")) {
				$("#refundTrxForm").attr("action","${ctx}/b2bRefundDownloadToExcel?wpIds="+boxValues).submit();
				$("#refundTrxForm").attr("action","${ctx}/b2bRefundOperateQuery");
			}
		}

	}
</script>
<title>网银B2B线下退款查询</title>
</head>

<body>
	<c:if test="${!empty message}">
		<script type="text/javascript">
           alert("${message}");
        </script>
	</c:if>
	<div class="content">
	 	<div class="con ">
	 		<form id="refundTrxForm" action="b2bRefundOperateQuery" method="post">
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">退款日期(起):</td>
	 						<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})"/>
	 						</td>
	 						<td align="right">退款日期(止):</td>
	 						<td>
	 							<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
	 								onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})"/>
	 						</td>
	 						<td align="right">银行名称：</td>
	 						<td>
								<select name="bankNodeCode" class="select1" id="bankNodeCode">
									<option value="" >全部</option>
									<c:forEach items="${bankMap}" var="bank">
										<option value="${bank.key}" >${bank.value}</option>
									</c:forEach>
								</select>
							</td>
	 					</tr>
	 					<tr>
	 						
	 						<td align="right">退款状态：</td>
	 						<td>
	 							<select id="trxState" name="trxState" class="select1" >
									<option value="" >全部</option>
									<c:forEach items="${refundStateMap}" var="cur">
										<option value="${cur.key}" >${cur.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 						<td colspan="2"><span id="validateDateMsg" style="color: red;"></span></td>
							<td align="right"><input id="query" class="bluebtn" type="button" value="查询" onclick="queryInfo();">
							</td>
							<td><input name="clearBtn" type="button" value="清除"
								class="bluebtn" id="clearBtn" onclick="clean();clearText();" />&nbsp;
								<input name="batchWipeClick" type="button" value="勾选下载"
								class="bluebtn" id="batchWipeClick" onclick="batchWipe();" /></td>
	 					</tr>
	 					<tr>
	 						
	 						
	 						
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<div width="500px" style="overflow:scroll;">
	 			<table width="1500px" border="0" cellpadding="0" cellspacing="0">
		 			<tr height="35" bgcolor="#cccccc">
		 				<th class="border"><input id="checkall" type="checkbox"
						value="" onclick="checkboxall('checkall','box')" /> 全选</th>
		 				<th>退款流水号</th>
						<th>原交易流水号</th>
						<th>退款状态</th>
						<th>金额</th>
						<th>收款方户名</th>
						<th>收款方开户行名称</th>
						<th>收款方账号</th>
						<th>付款方开户行名称</th>
						<th>退款时间</th>
		 			</tr>
		 			<c:if test="${empty commandList}">
	                    <tr>
	                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
	                    </tr>
	                </c:if>
	                <c:forEach var="cmdDetail" items="${commandList}" varStatus="status">
	                	<c:choose>
		                       <c:when test="${status.index %2 == 0 }">
		                           <tr align="center" height="35">
		                       </c:when>
		                       <c:otherwise>
		                           <tr align="center" height="35" bgcolor="#eeeeee">
		                       </c:otherwise>
	                     </c:choose>
			                <td><c:if test="${cmdDetail.trxState!='4'}">
									<input type="checkbox" name="box" id="checkbox${status.index}"
										value="${cmdDetail.wpRefundId}"/>
								</c:if>
							</td>
							<td class="fontfamily1">${cmdDetail.trxSerialNo}</td>
							<td class="fontfamily1">${cmdDetail.otrxSerialNo}</td>
							<td class="fontfamily1">
					            <c:choose>
                                  	<c:when test="${cmdDetail.trxState=='1'}">
                                         	待下载
                                    </c:when>
                                    <c:when test="${cmdDetail.trxState=='2'}">
                                         	已下载待处理
                                    </c:when>
                                    <c:when test="${cmdDetail.trxState=='3'}">
                                         	退款未处理
                                    </c:when>
                                    <c:otherwise>
                                                                                                                          退款成功
                                    </c:otherwise>
                                 </c:choose>
							</td>
							<td class="fontfamily1">${cmdDetail.payAmount}</td>
							<td class="fontfamily1">${cmdDetail.craccName}</td>
							<td class="fontfamily1">${cmdDetail.craccBankBranch}</td>
							<td class="fontfamily1">${cmdDetail.craccNo}</td>
							<td class="fontfamily1">
							    <input type="hidden" name="bankNodeCode${status.index}" id="bankNodeCode${status.index}""
										value="${cmdDetail.bankNodeCode}" readonly="readonly"/>
							
							   ${cmdDetail.bankNodeCode}
							</td>
							<td class="fontfamily1">
								<p><fmt:formatDate value="${cmdDetail.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></p>
							</td>
	                     </tr>
	                </c:forEach>
		 		</table>
	 		</div>
	 		<div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
	 			<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="/b2bRefundOperateQuery"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>