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
		<title>差错查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">


function download(){
	$("#subForm").attr("action","${ctx}/shortPatternQueryDownload").submit();
	$("#subForm").attr("action","${ctx}/shortPatternQueryInit");
}

function queryInfo(){
	var payAmount = document.getElementById("payAmount").value;
	var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
	if(payAmount.length>0){
		var flag = payAmount.match(regexp);
		if(flag==null){
			document.getElementById("payAmountError").innerHTML="格式非法";
			return;
		}
		if(payAmount.length>20){
			document.getElementById("payAmountError").innerHTML="长度超长";
			return;
		}
	}
	$("#subForm").submit();
	
}

  function showOutOfTrxNo(recType){
	  if(recType=='01'){
		  document.getElementById("outTransactionTr").style.display="none";
	  }else{
		  document.getElementById("outTransactionTr").style.display="";
	  }
	    
	};
	
	function clearText(){
		document.getElementById("status").value=""; 
		document.getElementById("recDiffType").value=""; 
		document.getElementById("currencyType").value=""; 
	}
	
	//确认差错已处理
	function updateStatus(id,trxSerialNo,diffType,oriPayAmount,payAmount,handleFlag){
		if(diffType == 'INN000'||diffType == 'CHA000'||(diffType == 'INN002'&&handleFlag=='')){
			//弹窗
			showidTranaction(id,trxSerialNo,diffType,payAmount,oriPayAmount);
		}else{
			if(confirm("请确认差错已经处理完毕？")){
				dataSubmit(id,trxSerialNo,diffType,handleFlag);
			}
		}
	}
	
	//提交数据到后台 
	function dataSubmit(id,trxSerialNo,diffType,handleFlag){
		var id2 = (id==""? $("#idValidate").val():id);
		var trxSerialNo2 = (trxSerialNo==""? $("#trxSerialNoValidate").val():trxSerialNo);
		var diffType2 = (diffType==""? $("#diffTypeValidate").val():diffType);
		//var payAmount = $("#payAmount").val();
		//var trxState = $("#trxState").val();
		closeWin();
		$.ajax({
			url:"handleRecStatus",
			type:"POST",
			dataType:"json",
			data:{
				recId:id2,
				trxSerialNo:trxSerialNo2,
				diffType:diffType2,
				handleFlag:handleFlag
			},
			success:function(data){
				if(data.success){//处理成功
					alert("处理成功！");
					$("form[name='splitPageForm']").submit();
				}else{
					alert("处理失败！"+data.message);
				}
			},
			error:function(data){
				alert("处理异常！");
			}
		});
	}
	
	//弹窗修饰
	function showidTranaction(id,trxSerialNo,diffType,payAmount,oriPayAmount) {
		var isIE = (document.all) ? true : false;
		var isIE6 = isIE && ([ /MSIE (\d)\.0/i.exec(navigator.userAgent) ][0][1] == 6);
		var newbox = document.getElementById("checkContent");
		newbox.style.zIndex = "9999";
		newbox.style.display = "block";
		newbox.style.position = !isIE6 ? "fixed" : "absolute";
		newbox.style.top = newbox.style.left = "50%";
		newbox.style.marginTop = -newbox.offsetHeight / 2 + "px";
		newbox.style.marginLeft = -newbox.offsetWidth / 2 + "px";
		var layer = document.createElement("div");
		layer.id = "layer";
		layer.style.width = layer.style.height = "100%";
		layer.style.position = !isIE6 ? "fixed" : "absolute";
		layer.style.top = layer.style.left = 0;
		layer.style.backgroundColor = "#000";
		layer.style.zIndex = "9998";
		layer.style.opacity = "0.6";
		layer.style.filter = 'alpha(opacity=60)';
		document.body.appendChild(layer);
		function layer_iestyle() {
			layer.style.width = Math.max(document.documentElement.scrollWidth,document.documentElement.clientWidth)+ "px";
			layer.style.height = Math.max(document.documentElement.scrollHeight,document.documentElement.clientHeight) + "px";
		}
		function newbox_iestyle() {
			newbox.style.marginTop = document.documentElement.scrollTop - newbox.offsetHeight / 2 + "px";
			newbox.style.marginLeft = document.documentElement.scrollLeft - newbox.offsetWidth / 2 + "px";
		}
		$("#idValidate").val(id);
		$("#trxSerialNoValidate").val(trxSerialNo);
		$("#diffTypeValidate").val(diffType);
		if(diffType == 'INN002'){
			$("#payAmountValidate").val(oriPayAmount);
		}else{
			$("#payAmountValidate").val(payAmount);			
		}
		
	}
	
	function closeWin() {
		$("#validateStr").html("*");
		$("#idValidate").val("");
		$("#trxSerialNoValidate").val("");
		$("#diffTypeValidate").val("");
		$("#payAmountValidate").val("");
		document.getElementById("checkContent").style.cssText = "display:none";
		$("#layer").remove();
		$(".passtable").show();
		$(".passcon .left").hide();
		$(".passcon .right").hide();
		$('#spt1_show').val('');
	}
	
</script>
<style type="text/css">
.font {
	color: #1276bc;
}

.checkContent {
	display: none;
	left: 10%;
	top: 10%;
	margin-left: -120px;
	margin-top: -140px;
	width: 700px;
	z-index: 1001;
	position: absolute;
	background-color: White;
	border: solid 1px #dddddd;
	padding: 0 0 10px;
	height: 200px;
	overflow:auto;
	background-color: white;
}
.passtitle2{
	width:600px;
}
</style>
</head>
<body>
	<div id="checkContent" class="checkContent" style="display: none;">
		<div id="checkContentTable" class="passtable fontcolor5 fontsize2 fontfamily2" style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">差错ID：</td>
					<td>
						<input id="idValidate" name="idValidate" type="text" class="txt1" style="width: 200px;" readonly="readonly"/>
					</td>
					<td align="right">差错类型代码：</td>
					<td>
						<input id="diffTypeValidate" name="diffTypeValidate" type="text" class="txt1" style="width: 200px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">交易流水号：</td>
					<td>
						<input id="trxSerialNoValidate" name="trxSerialNoValidate" type="text" class="txt1" style="width: 200px;" readonly="readonly"/>
					</td>
					<td align="right">交易金额：</td>
					<td>
						<input id="payAmountValidate" name="payAmountValidate" type="text" class="txt1" style="width: 200px;" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">交易状态：</td>
					<td>
						<input id="trxStateValidate" name="trxStateValidate" type="text" class="txt1" style="width: 200px;" readonly="readonly" value="交易成功"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="right">
						<input id="Pass" name="Pass" type="button" maxlength="30" class="txt1" value="确定" onclick="dataSubmit('','','','');"/>
					</td>
					<td>
						&emsp;<input id="NoPass" name="NoPass" type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/>
					</td>
				</tr>
			</table>
		</div>
	</div>

    <div class="content">
      <div class="con ">
				<form:form id="subForm" commandName="sacRecDifferences" action="${ctx}/shortPatternQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td  align="right">对账时间(起)：</td>
								<td ><form:input type="text" name="recStartDate" path="recStartDate"
											id="recStartDate" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}"  /></td>
								<td  align="right">对账时间(止)：</td>
								<td ><form:input type="text" name="recEndDate" path="recEndDate"
											id="recEndDate" class="txt2" 
											onfocus="WdatePicker({errDealMode:0});" readonly="readonly"
											value="${queryForm.date}"  /></td>			
								<td width="10%" align="right">交易流水号：</td>
								<td >
								<span> <form:input  
										path="trxSerialNo" class="txt2" id="trxSerialNo" maxlength="50" /> </span>
										<span><form:errors path="trxSerialNo" cssStyle="color:red"/></span>
								</td>
							</tr>
							
							<tr>
								<td  align="right">渠道支付金额：</td>
								<td >
								<span> <form:input path = "payAmount" class="txt2" id="payAmount" /></span>
                                <span id="payAmountError" style="color: red;"></span>
								</td>
								<td  align="right">处理状态：</td>
								<td >
									<select id="status" name="status" class="select1" >
										<option value="" >全部</option>
										<option value="H" <c:if test="${'H' == sacRecDifferences.status}"> selected="selected"</c:if>>处理中</option>
										<c:forEach items="${sysDicStatusList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacRecDifferences.status}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<!--  <td align="right">&nbsp;</td>-->
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();"/>
								</td>
								<td>
								<input id="downloadId" class="bluebtn" type="button" value="下载"  onclick="download();"/>
								</td>
							</tr>
							
							<tr>
								<td  align="right">币种：</td>
								<td >
									<select id="currencyType" name="currencyType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicCode}" <c:if test="${sys.dicCode == sacRecDifferences.currencyType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td  align="right">差错类型：</td>
								<td >
									<select id="recDiffType" name="recDiffType" class="select1" >
										<option value="" >全部</option>
										<c:forEach items="${recDiffTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == sacRecDifferences.recDiffType}"> selected="selected"</c:if>><c:if test="${sys.dicValue == 'INN002'}">内部-对账文件不存在该交易</c:if><c:if test="${sys.dicValue == 'INN001'}">内部-清算交易不存在</c:if><c:if test="${sys.dicValue == 'INN000'}">内部对账不一致</c:if><c:if test="${sys.dicValue == 'CHA002'}">渠道-清算交易不存在</c:if><c:if test="${sys.dicValue == 'CHA001'}">渠道对账短款</c:if><c:if test="${sys.dicValue == 'CHA000'}">渠道对账存在不一致</c:if><c:if test="${sys.dicValue == 'CHA003'}">渠道对账未内部对账</c:if></option>
										</c:forEach>
									</select>
								</td>
								
								<td><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
								<td ></td>
							</tr>							
							
						</table>
					</div>
				</form:form>
			</div>
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
			<div width="500px" style="overflow:scroll;">
        <table width="1800px" border="0" cellpadding="0" cellspacing="0">
                                <tr height="35" bgcolor="#cccccc">
                                	<th width="200px" class="border">操作</th>
                                    <th class="border">交易流水号</th>
                                    <th class="border">外部流水号</th>
                                    <th class="border">对账文件支付金额</th>
                                    <th class="border">原交易支付金额</th>
                                    <th class="border">币种</th>
                                    <th class="border">差错类型</th>
                                    <th class="border">交易类型</th>
                                    <th class="border">支付渠道类型</th>
                                    <th class="border">处理状态</th>
                                    <th class="border">对账开始时间</th>
                                    <th class="border">对账结束时间</th>
                                    <th class="border">交易时间</th>
                                    <!-- <th class="border">差错原因</th> -->
                                </tr>
                                <c:if test="${empty sacRecDifferencesList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${sacRecDifferencesList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1" align="left">
                                    	<c:if test="${item.status ne 'S'}">
                                    	
                                    		 <c:choose>
		                                        <c:when test="${item.recDiffType eq 'INN002'}">
		                                            <input type="button" class="bluebtn" id="confirmBtn2" value="冲正处理" onclick="updateStatus('${item.id}','${item.trxSerialNo}','${item.recDiffType}',${item.oriInnConAmount},'${item.payAmount}','');"/>
		                                        </c:when>
		                                        <c:otherwise>
		                                            <input type="button" class="bluebtn" id="confirmBtn1" value="确认已处理" onclick="updateStatus('${item.id}','${item.trxSerialNo}','${item.recDiffType}','','${item.payAmount}','');"/>
		                                        </c:otherwise>
                                    		</c:choose>
                                    		
<%--                                     	<input type="button" class="bluebtn" id="confirmBtn1" value="确认已处理" onclick="updateStatus('${item.id}','${item.trxSerialNo}','${item.recDiffType}','${item.payAmount}','');"/> --%>
                                    		<c:if test="${item.recDiffType eq 'INN001' || item.recDiffType eq 'CHA002' || item.recDiffType eq 'INN002'}">
	                                    		<input type="button" class="bluebtn" id="confirmBtn1" value="直接处理" onclick="updateStatus('${item.id}','${item.trxSerialNo}','${item.recDiffType}','','${item.payAmount}','Y');"/>                              	
		                                   	</c:if>
	                                   	</c:if>
                                    </td>
                                    <td class="fontfamily1">${item.trxSerialNo}</td>
                                    <td class="fontfamily1">&nbsp;${item.bankSerialNo}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                     <c:choose>
                                    	<c:when test="${item.recDiffType=='INN001'||item.recDiffType=='INN000'||item.recDiffType=='INN002'}">
                                           <td class="fontfamily1">${item.oriInnConAmount}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="fontfamily1">${item.oriChaConAmount}</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td class="fontfamily1">
                                    	<c:forEach items="${currencyTypeList}" var="sys">
											<c:if test="${sys.dicCode eq item.currencyType}">${sys.dicDesc}</c:if>
										</c:forEach>
									</td>
                                    <td class="fontfamily1">${item.recDiffDesc}</td>
                                    <td class="fontfamily1">${item.trxCode}</td>
                                    <td class="fontfamily1"><c:if test="${item.payconType eq '1'}">B2B支付</c:if><c:if test="${item.payconType eq '2'}">B2C支付</c:if></td>
                                    <td class="fontfamily1"><c:if test="${item.status eq 'S'}">已处理</c:if><c:if test="${item.status eq 'N'}">未处理</c:if><c:if test="${item.status eq 'H'}">处理中</c:if></td>
                                    <%-- <td class="fontfamily1"><c:if test="${item.recDiffType == 'INN001'}">内部-原交易不存在</c:if><c:if test="${item.recDiffType == 'INN000'}">内部对账不一致</c:if><c:if test="${item.recDiffType == 'CHA002'}">渠道-原交易不存在</c:if><c:if test="${item.recDiffType == 'CHA001'}">渠道对账短款</c:if><c:if test="${item.recDiffType == 'CHA000'}">渠道对账长款</c:if><c:if test="${item.recDiffType == 'CHA003'}">渠道对账未内部对账</c:if></td> --%>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.recStartDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.recEndDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.trxTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                   <%--  <td class="fontfamily1">${item.recDiffDesc}</td> --%>
                                    </tr>
                                </c:forEach>
                            </table>
                            </div>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/shortPatternQueryInit"/>
									</td>
								</tr>
							</table>
						</div>
    		</div>
    </div>
    
    



<!-- /Body -->
</body>
</html>