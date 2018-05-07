<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
		*{margin:0;padding:0;box-sizing: border-box}
		.clearfix:before,.clearfix:after{content:'';display: table;}
		.clearfix:after{clear:both;}
		.left{float: left;}
		ul{list-style-typ: none;}
		.drop-box{position: relative;}
		.drop-list{position: absolute;top: 25px;left: 5px;border: 1px solid #ccc;border-top:0;display:none;width: 100%;background:#fff;}
		.drop-list li{padding-left:5px;}
		.drop-list li.active{background: #ddd;}
		.main .content .con{overflow:visible;}
</style>
<script type="text/javascript" src="${ctx}/scripts/js/jquery/jquery-1.7.2.min.js"></script>	
<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	//input获取焦点
	$('#cusName').focus(function(){
		$(this).val().length > 0 
		? $('#dropList').show().css({width:$(this).outerWidth(),left:$(this).css('marginLeft')}) 
		: $('#dropList').hide();
	});
	//input失去焦点
	$('#cusName').blur(function(e){
		$('#dropList').hasClass('hover-on') ? $(this).focus() : $('#dropList').hide();
	});
	//input keyup
	$('#cusName').keyup(function(e){
		var self = this;
		if(e.keyCode == 38 || e.keyCode == 40) return
		if ($(this).val().length > 0) {
			$.ajax({
				type:"POST",
				dataType:"html",
				url:"cusBalancePlatAccNames",
				async:false,
				data:{
					cusName:$.trim($(this).val())
				},
				success:function(data){
					var datas = eval('('+data+')'); 
					var cusNames = datas.cusNamesList;
					var trs = "<ul>";
					for (var i = 0; i < cusNames.length; i++) {
						trs += "<li>" + cusNames[i] +"</li>";
					}
					trs += "</li>";
					$("#dropList").html(trs);
					initDropListLi();
					$('#dropList').show().css({width:$(self).outerWidth(),left:$(self).css('marginLeft')}); 
					} 
			});	
		} else {
			$('#dropList').hide();
		}
	});
	
	
});


    //键盘上下键切换选中项
	$(document).keydown(function (event) {
		if($('#dropList').is(':hidden')) return 
	   switch (event.keyCode){
	   	case 38:
	   		if($('#dropList li.active').prev().length < 1 ){
	   			$('#dropList').find('li:last').addClass('active');
	   			$('#dropList').find('li:first').removeClass('active');
	   		}else{
	   			$('#dropList li.active').removeClass('active').prev().addClass('active'); 
	   		}
				break;
		case 40:
				if($('#dropList li.active').next().length < 1 ){
					$('#dropList').find('li:first').addClass('active');
					$('#dropList').find('li:last').removeClass('active');
				}else{
					$('#dropList li.active').removeClass('active').next().addClass('active');
				}
				break;
				
	   }
	}); 

function initDropListLi(){
	//drop元素划过增加标记
	$('#dropList').hover(
		function(){$(this).addClass('hover-on');},
		function(){$(this).removeClass('hover-on');}
	);
	//droplist划过增加样式
	$('#dropList li').mouseover(function(){
		$(this).addClass('active').siblings().removeClass('active');
	});
	
	$('#dropList li').click(function(){
		$("#cusName").val($(this).text());
		$('#dropList').hide();
	});
	
}

	function queryCusBalanceDetail(data,beginDate){
		var cusNo = $("#cusNo").val();
		var cusName = $("#cusName").val();
		var bussType = $("#bussType").val();
		var childAccType = $("#childAccType").val();
		var pageNo = ${pageNo};
		var startDate = $("#oldStartDate").val();
		var endDate = $("#oldEndDate").val();
		var currency = $("#currency").val();
		var backType = $("#backType").val();
		window.location.href = "cusBalanceQueryFundDayDtail?finCode="+data+"&startDate="+startDate+"&endDate="+endDate+"&pageNoBack="+pageNo+"&cusNo="+cusNo+"&cusName="+cusName+"&bussType="+bussType+"&childAccType="+childAccType+"&currency="+currency+"&beginDate="+beginDate+"&backType="+backType;
	}
	function submitForm() {
		var cusNo = $("#cusNo").val();
		var cusName = $("#cusName").val();
		if((cusNo == null || cusNo=="") && (cusName == null || cusName=="") ){
			document.getElementById("cusNameError").innerHTML="客户号或者客户名称必选填一项";
			return;
		}
		if (cusNo != null && cusNo!= "" && cusNo.length != 19) {
			console.log(cusNo.length);
			document.getElementById("cusNameError").innerHTML="客户号必须是19位";
			return
		}
		var startDate = document.getElementById("startDate").value;
		document.getElementById("startDateError").innerHTML="";
		if(startDate==null||startDate==""){
			document.getElementById("startDateError").innerHTML="请选择查询开始日期";
			return;
		}
		var form = $("#cusBalanceForm");
		form.submit();
		
	}
	$(function(){
		var bussType = "${bussType}";/* 业务类型 */
		$('select[name="bussType"]').find("option[value="+bussType+"]").attr("selected",true);
		var childAccType = "${childAccType}";/* 账户子类型 */
		$('select[name="childAccType"]').find("option[value="+childAccType+"]").attr("selected",true);
		var currency = "${currency}";/* 币种 */
		$('select[name="currency"]').find("option[value="+currency+"]").attr("selected",true);
	});
	
</script>
<title>客户每日余额查询</title>
</head>

<body>
	<div class="content">
	 	<div class="con ">
	 		<form id="cusBalanceForm" action="cusBalanceFundDayQuery" method="post">
	 		<input type="hidden" name = "oldStartDate" id = "oldStartDate" value= "${startDate}"/>
	 		<%-- <input type="hidden" name = "oldEndDate" id = "oldEndDate" value= "${endDate}"/> --%>
	 		<input type="hidden" name = "backType" id = "backType" value= "${backType}"/>
	 			<div class="table fontcolor4 fontsize1 fontfamily2">
	 				<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 					<tr>
	 						<td align="right">客户号:</td>
	 						<td>
	 							<input type="text" class="txt2" id="cusNo" name="cusNo" value="${cusNo}" width="100px"/>
	 						</td>
	 						<td align="right">客户名称:</td>
	 						<td>
	 						<div style="position:relative;float:left;z-index:1;" class = "dropListFather">
	 							<input autocomplete="off" type="text" class="txt2" id="cusName" name="cusName" value="${cusName}" width="100px"/>
	 							<div class="left drop-list" id="dropList">
								</div>
							</div>
	 							<span id="cusNameError" style="color: red;"></span>
	 						</td>
	 						
	 					</tr>
	 					
	 					<tr>
								<td width="10%" align="right">查询日期(起)：</td>
								<td width="30%">
									<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyy-MM-dd'})"/>
									<span id="startDateError" style="color: red;"></span>
								</td>
								
								<td width="10%" align="right">币种：</td>
								<td width="30%">
									<select id="currency" name="currency" class="select1" >
										<option value="">全部</option>
										<c:forEach items="${currencyList}" var="sys">
											<option value="${sys.dicCode}"> ${sys.dicDesc}</option>
										</c:forEach>
									</select>
								</td>
								<td width="30%" style="display: none;"><input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/>
											<span id="endDateError" style="color: red;"></span>
								</td>
								<td width="10%" align="right">&nbsp;</td>
							</tr>
	 					<tr>
	 						<td align="right">业务类型:</td>
	 						<td>
	 							<select id="bussType" class="select1"  name="bussType" width="100px" >
	 								<option value="" >无</option>
									<c:forEach items="${bussTypeMap}" var="bussType">
										<option value="${bussType.key}" >${bussType.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 						<td align="right">功能账户类型:</td>
	 						<td>
	 							<select id="childAccType" class="select1"  name="childAccType" width="100px" >
	 								<option value="" >无</option>
									<c:forEach items="${childAccTypeMap}" var="childAccType">
										<option value="${childAccType.key}" >${childAccType.value}</option>
									</c:forEach>
								</select>
	 						</td>
	 						<td>
	 							<input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="submitForm();"/>
	 						</td>
	 					</tr>
	 				</table>
	 			</div>
	 		</form>
	 	</div>
	 	<div class="table fontcolor4 fontsize1 fontfamily2">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="0">
	 			<tr height="35" bgcolor="#cccccc">
	 				<th>客户账号</th>
	 				<th>业务类型</th>
	 				<th>功能账户类型</th>
					<th>客户名称</th>
					<th>币种</th>
					<th>借方发生额</th>
                    <th>贷方发生额</th>
                    <th>发生额</th>
                    <th>期初余额</th>
                    <th>期末余额</th>
                    <th>日期</th>
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
                     	<td>${balance.CUSNOID}</td>
                     	<td>${balance.bussType}</td>
                     	<td>${balance.childAccType}</td>
                     	<td>${balance.CUSNAME}</td>
                     	<td>${balance.SACCURRENCY}</td>
                     	<td>${balance.FDEBIT}</td>
                        <td>${balance.FCREDIT}</td>
                        <td>${balance.AMOUNT}</td>
                        <td>${balance.OPENBAL}</td>
                        <td>${balance.CLOSEBAL}</td>
                        <td>${balance.STATTIME}</td>
                       <%--  <td><fmt:formatDate value="${balance.statTime}" pattern="yyyy/MM/dd"/></td> --%>
						<td>
							<c:choose>
								<c:when test="${balance.CUSNOID eq null}">-</c:when>
								<c:otherwise>
									<input type="button" value="详情" onclick="queryCusBalanceDetail('${balance.CUSNOID}','${balance.STATTIME}');">
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
							<easipay:pageNum pageSize="${pageSize}" count="${totalCount}" pageNo="${pageNo}" url="${queryUrl}"/>
						</td>
					</tr>
				</table>
	 		</div>
	 	</div>
	</div>
</body>
</html>