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
		<title>核销信息查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
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

function queryInfo(){
	$("#subForm").submit();
}
function downLoad(){
	$("#subForm").attr("action","suspendedAccInfoDownload").submit();
	$("#subForm").attr("action","suspendedAccInfoQuery");
}

</script>
</head>
<body>
    <div class="content">
      <div class="con ">
				<form id="subForm" name="subForm" action="${ctx}/suspendedAccInfoQuery" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td  align="right">组织机构代码：</td>
								<td >
									<input type="text" class="txt2" id="orgCardId" name="orgCardId" value="${orgCardId}" 
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
 							<td align="right">币种：</td>
							<td>
								<select id="currencyType" name="currencyType" class="select1" value="${currencyType}" disabled="disabled">
										<c:forEach items="${currencyTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == currencyType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
							</td>
							</tr>
							<tr>
								<td  align="right">业务类型：</td>
								<td>
								<select id="bussType" name="bussType" class="select1" value="${bussType}">
										<c:forEach items="${bussTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == bussType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td  align="right">核销类型：</td>
								<td>
								<select id="cavType" name="cavType" class="select1" value="${cavType}">
										<c:forEach items="${cavTypeList}" var="sys">
											<option value="${sys.dicValue}" <c:if test="${sys.dicValue == cavType}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
	 							<td align="right">
								<td><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryInfo();"/>
								</td>
								<td><input name="clearBtn" type="button" 
									 value="下载" class="bluebtn"
									id="downLoadBtn" onclick="downLoad();"/>
								</td>
								<td><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
								</td>
							</tr>
							
						</table>
					</div>
				</form>
			</div>
			
				<div class="table fontcolor4 fontsize1 fontfamily2">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
       							<tr height="35" bgcolor="#cccccc">
                                    <th class="border">交易流水号</th>
                                    <th class="border">交易金额</th>
                                    <th class="border">已核销金额</th>
                                    <th class="border">未核销金额</th>
                                    <th class="border">交易创建时间</th>
                                    <th class="border">交易成功时间</th>
                                    <th class="border">核销类型</th>
                                </tr>
                                <c:if test="${empty suspendedAccinfoList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${suspendedAccinfoList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="35">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="35" bgcolor="#eeeeee">
                                        </c:otherwise>
                                 	</c:choose>
                                    <td class="fontfamily1">${item.trxSerialNo}</td>
                                    <td class="fontfamily1">${item.payAmount}</td>
                                    <td class="fontfamily1">${item.yhxAmount}</td>
                                    <td class="fontfamily1">${item.whxAmount}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.trxSuccTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
                                    <td class="fontfamily1"><c:if test="${item.cavType eq '1'}">上线后未核销</c:if><c:if test="${item.cavType eq '2'}">上线后部分未核销</c:if><c:if test="${item.cavType eq '3'}">上线前未核销</c:if></td>
                                    </tr>
                                </c:forEach>
                            </table>
						<div style="width: 100%; height:auto; text-align: right;"id="pageDiv" class="pagination1 btn">
				 			<table width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td align="right" >
										<easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/suspendedAccInfoQuery"/>
									</td>
								</tr>
							</table>
	 					</div>
	 		</div>
    </div>
    
    



<!-- /Body -->
</body>
</html>