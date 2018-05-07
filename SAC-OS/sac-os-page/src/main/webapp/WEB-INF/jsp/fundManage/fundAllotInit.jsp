<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>资金调拨查询</title>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	//枚举值返显 
	var payCurrency = "${fundTransferCmd.payCurrency}";//币种
	$("#payCurrency").find("option[value="+payCurrency+"]").attr("selected",true);
	var cmdState = "${fundTransferCmd.cmdState}";//指令状态
	$("#cmdState").find("option[value="+cmdState+"]").attr("selected",true);
	var draccNodeCode = "${fundTransferCmd.draccNodeCode}";//收款方银行
	$("#draccNodeCode").find("option[value="+draccNodeCode+"]").attr("selected",true);
	var craccNodeCode = "${fundTransferCmd.craccNodeCode}";//付款方银行
	$("#craccNodeCode").find("option[value="+craccNodeCode+"]").attr("selected",true);
	showBankAcc($("#craccNodeCode").val(),"craccNo");
	showBankAcc($("#draccNodeCode").val(),"draccNo");
	var draccNo = "${fundTransferCmd.draccNo}";//收款方账号
	$("#draccNo").find("option[value="+draccNo+"]").attr("selected",true);
	var craccNo = "${fundTransferCmd.craccNo}";//付款方账号
	$("#craccNo").find("option[value="+craccNo+"]").attr("selected",true);
	
	//改变下拉框的值 
	$("#craccNodeCode").change(function(){
		showBankAcc($("#craccNodeCode").val(),"craccNo");
	});	
	$("#draccNodeCode").change(function(){
		showBankAcc($("#draccNodeCode").val(),"draccNo");
	});	
});

function showBankAcc(bankNodeCode,flag){
	if(bankNodeCode==""){
		$("#"+flag).find("option").remove();
		$("#"+flag).append("<option value=''>全部</option>");
		return;
	}
	$.ajax({
		url:"getBankAccListByBankNodeCode",
		type:"get",
		dataType:"json",
		data:{
			bankNodeCode:bankNodeCode
		},
		async:false,
		success:function(data){
			if(data.success){
				$("#"+flag).find("option").remove();
				var ccyMap = data.ccyMap;
				$.each(data.bankAccList,function(n,item){
					var bankAcc = item.bankAcc;
					var currencyType = item.currencyType;
					$.each(ccyMap,function(key,value){
						if(key==currencyType){
							$("#"+flag).append("<option value='" + bankAcc + "'>" + bankAcc+"("+value+")"+"</option>");
						}
					});
				});
			}
		}
	});
}

//查询
function queryFundMsg() {
	var payAmount = document.getElementById("payAmount").value;
	var regexp = "^([1-9][\d]{0,16}|0)+(.[0-9]{1,2})?$";
	if (payAmount.length > 0) {
		var flag = payAmount.match(regexp);
		if (flag == null) {
			document.getElementById("payAmountError").innerHTML = "格式非法";
			return;
		}
		if (payAmount.length > 20) {
			document.getElementById("payAmountError").innerHTML = "长度超长";
			return;
		}
	}
	$("#fundTransferCmdForm").submit();
}
	
//清除
function clearText() {
	document.getElementById("craccNo").value = "";
	document.getElementById("draccNo").value = "";
	document.getElementById("trxState").value = "";
	document.getElementById("payCurrency").value = "";
}

	//线上审核操作
	function checkSucc1(id,index){//审核结果
		//付款方银行为线上4家银行
		if(confirm("线上资金调拨目前付款方银行仅支持\n中国银行、中国建设银行、招商银行、民生银行\n是否继续？")){
			//通知后台处理
			$("#checkSuccOl"+index).attr("disabled",true);
			$("#checkSuccOf"+index).attr("disabled",true);
			$("#checkFail"+index).attr("disabled",true);
			$.ajax({
				url:"recheckForFundAllot",
				type:"POST",
				dataType:"json",
				data:{
					cmdId:id,
					result:"success",
					flag:"dsf"
				},
				success:function(data){
					if(data.success){
						alert("审核操作成功！"+data.message);
						$("form[name='splitPageForm']").submit();
					}else{
						alert(data.message);
					}
				},
				error:function(data){
					alert("审核提交异常,"+data);
				}	
			});	
		}
		
	}
	
	// 线下审核操作
	function checkSucc2(id,index){//审核结果
		//var etrxSerialNo = $("#etrxSerialNo").val();
		/* if(etrxSerialNo==null||etrxSerialNo==""){
			$("#validateStr").html("银行流水号不能为空！");
			return;
		} */
		//通知后台处理
		if (window.confirm("确认?")){
			$("#checkSuccOl"+index).attr("disabled",true);
			$("#checkSuccOf"+index).attr("disabled",true);
			$("#checkFail"+index).attr("disabled",true);
			$.ajax({
				url:"recheckForFundAllot",
				type:"POST",
				dataType:"json",
				data:{
					cmdId:id,
					result:"success",
					flag:"ac"
				},
				success:function(data){
					if(data.success){
						alert("审核操作成功！"+data.message);
						$("form[name='splitPageForm']").submit();
					}else{
						alert(data.message);
					}
				},
				error:function(data){
					alert("审核提交异常,"+data);
				}	
			});	
		}
		
	}
	
	// 审核不通过操作
	function checkFail(id,index){//审核结果
		if (window.confirm("确认?")){
			$("#checkSuccOl"+index).attr("disabled",true);
			$("#checkSuccOf"+index).attr("disabled",true);
			$("#checkFail"+index).attr("disabled",true);
			//通知后台处理
			$.ajax({
				url:"recheckForFundAllot",
				type:"POST",
				dataType:"json",
				data:{
					cmdId:id,
					result:"fail"
				},
				success:function(data){
					if(data.success){
						alert("审核操作成功！"+data.message);
						$("form[name='splitPageForm']").submit();
					}else{
						alert(data.message);
						window.location.reload();
					}
				},
				error:function(data){
					alert("审核提交异常,"+data);
					window.location.reload();
				}	
			});
		}
	}

	//弹窗修饰
	function showidTranaction(id) {
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
	}

	function closeWin() {
		$("#validateStr").html("*");
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

#checkContent {
	display: none;
	left: 10%;
	top: 10%;
	margin-left: -120px;
	margin-top: -140px;
	width: 600px;
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
	<div id="checkContent" style="display: none;">
		<div id="checkContentTable" class="passtable fontcolor5 fontsize2 fontfamily2" style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="30%" align="right">指令ID：</td>
					<td width="30%">
						<input id="idValidate" name="idValidate" type="text" class="txt1" style="width: 200px;" readonly="readonly"/>
					</td>
					<td width="40%"></td>
				</tr>
				<!-- <tr>
					<td align="right">银行流水号：</td>
					<td>
						<input id="etrxSerialNo" name="etrxSerialNo" type="text" class="txt1" style="width: 200px;"/>
					</td>
					<td align="left"><span style="color: red" id="validateStr">*</span></td>
				</tr> -->
				<tr>
					<td align="right">
						<input id="Pass" name="Pass" type="button" maxlength="30" class="txt1" value="确定" onclick="checkSucc2();"/>
					</td>
					<td>
						&emsp;<input id="NoPass" name="NoPass" type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/>
					</td>
					<td></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="content">
		<div class="con ">
			<form id="fundTransferCmdForm" name="fundTransferCmdForm" action="fundAllotInit" method="POST">
				<div class="table fontcolor4 fontsize1 fontfamily2">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td align="right">收款银行：</td>
							<td>
								<select id="craccNodeCode" name="craccNodeCode" class="select1">
									<option value="">全部</option>
									<c:forEach items="${channelBankMap}" var="bank">
										<option value="${bank.key}">${bank.value}</option>
									</c:forEach>
								</select> 
							</td>
							<td align="right">付款银行：</td>
							<td>
								<select id="draccNodeCode" name="draccNodeCode" class="select1">
									<option value="">全部</option>
									<c:forEach items="${channelBankMap}" var="bank">
										<option value="${bank.key}">${bank.value}</option>
									</c:forEach>
								</select> 
							</td>
						</tr>
						<tr>
							<td align="right">收款银行账号：</td>
							<td>
								<select id="craccNo" name="craccNo" class="select1">
									<option value="">全部</option>
								</select> 
							</td>
							<td align="right">付款银行账号：</td>
							<td>
								<select id="draccNo" name="draccNo" class="select1">
									<option value="">全部</option>
								</select> 
							</td>
						</tr>
						<tr>
							<td align="right">币种：</td>
							<td>
								<select id="payCurrency" name="payCurrency" class="select1">
										<option value="">全部</option>
										<c:forEach items="${currencyList}" var="sys">
											<option value="${sys.dicCode}" >${sys.dicDesc}</option>
										</c:forEach>
								</select>
							</td>
							<td align="right">审核状态：</td>
							<td>
								<select id="cmdState" name="cmdState" class="select1">
									<option value="">全部</option>
									<option value="1">待审核</option>
									<option value="2">审核处理完成</option>
									<option value="3">审核不通过</option>
									<option value="4">线上审核处理中</option>
									<option value="5">线上审核处理失败</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">调拨日期：</td>
							<td>
								<input type="text" name="queryDate" id="queryDate" class="txt2" value="${queryDate}"
									onfocus="WdatePicker({readOnly:true,maxDate:'#F{\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})" />
							</td>
							<td align="right">调拨金额：</td>
							<td>
								<input name="payAmount" class="txt2" id="payAmount" value="${fundTransferCmd.payAmount}"/>
								<span id="payAmountError" style="color: red;"></span>
							</td>
						</tr>
						<tr>
							<td align="right">&nbsp;</td>
							<td>
								<input name="submitbtn" type="button" value="查询" class="bluebtn" id="submitbtn" onclick="queryFundMsg();" />
							</td>
							<td align="right">&nbsp;</td>
							<td>
								<input name="clearBtn" type="button" value="清除" class="bluebtn" id="clearBtn" onclick="clean();clearText();" />
							</td>
						</tr>
					</table>					
				</div>
			</form>
		</div>

		<div class="table fontcolor4 fontsize1 fontfamily2">
			<div width="500px" style="overflow: scroll;">
				<table width="1400px" border="0" cellpadding="0" cellspacing="0">
					<tr height="35" bgcolor="#cccccc">
						<th width="300px" class="border">操作</th>
						<th class="border">审核状态</th>
						<th class="border">指令ID</th>
						<th class="border">交易流水号</th>
						<th class="border">付款方银行</th>
						<th class="border">付款方账号</th>
						<th class="border">收款方银行</th>
						<th class="border">收款方账号</th>
						<th class="border">币种</th>
						<th class="border">支付金额</th>
						<th class="border">支付方式</th>
						<th class="border">收款方分行</th>
						<th class="border">付款方分行</th>
						<th class="border">创建时间</th>
					</tr>
					<c:if test="${empty fundTransferCmdList }">
						<tr>
							<td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
						</tr>
					</c:if>
					<c:forEach items="${fundTransferCmdList}" var="item" varStatus="st">
						<c:choose>
							<c:when test="${st.index %2 == 0 }">
								<tr align="center" height="35">
							</c:when>
							<c:otherwise>
								<tr align="center" height="35" bgcolor="#eeeeee">
							</c:otherwise>
						</c:choose>
						<td class="fontfamily1" align="left">
							<c:if test="${item.cmdState eq '1'}">
							    <input id="checkSuccOl${st.index}" class="bluebtn" style="width: 90px;" type="button" value="线上审核处理" onclick="checkSucc1('${item.id}',${st.index});" />
								<input id="checkSuccOf${st.index}" class="bluebtn" style="width: 90px;" type="button" value="线下审核处理" onclick="checkSucc2('${item.id}',${st.index});"/>
								<input id="checkFail${st.index}" class="bluebtn"  type="button" value="审核不通过" onclick="checkFail('${item.id}',${st.index});" />
							</c:if>
						</td>
						<td class="fontfamily1">
							<c:if test="${item.cmdState eq '1'}">待审核</c:if>
							<c:if test="${item.cmdState eq '2'}">审核处理完成</c:if>
							<c:if test="${item.cmdState eq '3'}">审核不通过</c:if> 
							<c:if test="${item.cmdState eq '4'}">线上审核处理中</c:if>
							<c:if test="${item.cmdState eq '5'}">线上审核处理失败</c:if>
						</td>
						<td class="fontfamily1">${item.id}</td>
						<td class="fontfamily1">${item.trxSerialNo}</td>
						<td class="fontfamily1">
							<c:forEach items="${channelBankMap}" var="bank">
								<c:if test="${bank.key eq item.draccNodeCode }">${bank.value}</c:if>
							</c:forEach>
						</td>
						<td class="fontfamily1">${item.draccNo}</td>
						<td class="fontfamily1">
							<c:forEach items="${channelBankMap}" var="bank">
								<c:if test="${bank.key eq item.craccNodeCode }">${bank.value}</c:if>
							</c:forEach>
						</td>
						<td class="fontfamily1">${item.craccNo}</td>
						<td class="fontfamily1">${item.payCurrency}</td>
						<td class="fontfamily1">${item.payAmount}</td>
						<td class="fontfamily1">
							<c:if test="${item.payWay eq '1'}">快捷</c:if>
							<c:if test="${item.payWay eq '2'}">网银</c:if>
							<c:if test="${item.payWay eq '3'}">代收</c:if>
							<c:if test="${item.payWay eq '4'}">代付</c:if>
							<c:if test="${item.payWay eq '5'}">余额</c:if>
						</td>
						<td class="fontfamily1">
							<c:forEach items="${branchList}" var="branch">
								<c:if test="${branch.dicValue eq item.craccAreaCode }">${branch.dicDesc}</c:if>
							</c:forEach>
						</td>
						<td class="fontfamily1">
							<c:forEach items="${branchList}" var="branch">
								<c:if test="${branch.dicValue eq item.draccAreaCode }">${branch.dicDesc}</c:if>
							</c:forEach>
						</td>
						<td class="fontfamily1">
							<fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div style="width: 100%; height: 32px; text-align: right;" id="pageDiv" class="pagination1 btn">
				<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/fundAllotInit" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>