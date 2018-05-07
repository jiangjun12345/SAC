<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="easipay" uri="/WEB-INF/tag/easipay-tag.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>利息转出审核</title>
<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

//查询
function queryFundMsg() {
	$("#interrestDealOutCheckid").submit();
}
//清除
function clearText() {
	document.getElementById("createTime").value = "";
	document.getElementById("checkStatus").value = "";
}
//线上审核操作
function checkSucc(checkStatus,id,draccNodeCode,draccNo,payAmount,index){//审核结果
	//付款方银行为线上4家银行
	if(confirm("确认审核通过？")){
		$("#checkSucc"+index).attr("disabled",true);
		$("#checkFail"+index).attr("disabled",true);
		//通知后台处理
		$.ajax({
			url:"interrestDealOut",
			type:"POST",
			dataType:"json",
			data:{
				checkStatus:checkStatus,
				id:id,				
				draccBankNodeCode : draccNodeCode,
				draccNo : draccNo,
				payAmount : payAmount,
				result:"success"
			},
			success:function(data){
				if(data.success){
					alert("操作成功！");
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
function downSubmit(){
		$("#interrestDealOutCheckid").attr("target","newWin");
		$("#interrestDealOutCheckid").attr("action","${ctx}/interrestOutCommonDownload").submit();
		$("#interrestDealOutCheckid").attr("action","${ctx}/interrestDealOutCheck");
		$("#interrestDealOutCheckid").attr("target","");
}
  
function showDetail(){
	var url="InterrestOutCommonDownload"; //转向网页的地址;
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
  
function checkSucc1(checkStatus,id,index){//审核结果
	//付款方银行为线上4家银行
	if(confirm("确认审核不通过？")){
		$("#checkSucc"+index).attr("disabled",true);
		$("#checkFail"+index).attr("disabled",true);
		//通知后台处理
		$.ajax({
			url:"interrestDealOut",
			type:"POST",
			dataType:"json",
			data:{
				checkStatus:checkStatus,
				id:id,
				result:"success"
			},
			success:function(data){
				if(data.success){
					alert("操作成功！");
					$("form[name='splitPageForm']").submit();
				}else{
					alert(data.message);
					window.location.reload();
				}
			},
			error:function(data){
				alert("审核提交异常,");
				window.location.reload();
				
			}	
		});	
	}
  }
</script>
</head>

<body>
	<div class="content">
		<div class="con ">
			<form id="interrestDealOutCheckid" name="interrestDealOutCheckid" action="interrestDealOutCheck" method="POST">
				<div class="table fontcolor4 fontsize1 fontfamily2">
	            <table  width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="12%" align="right">录入日期(起)：</td>
						<td>
							<input type="text" name="startDate" id="startDate" class="txt2" value="${startDate}"
								onfocus="WdatePicker({readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}',dateFmt:'yyyyMMdd'})" />
						</td>
						<td width="12%" align="right">录入日期(止)：</td>
						<td>
							<input type="text" name="endDate" id="endDate" class="txt2" value="${endDate}"
								onfocus="WdatePicker({readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyyMMdd',maxDate:'%y-%M-%d'})" />
						</td>
						<td align="right" colspan="1">审核状态：</td>
						<td>
							<select id="checkStatus" name="checkStatus" class="select1">
								    <option value="" >全部</option>
									<option value="1" <c:if test="${checkStatus eq '1'}"> selected="selected"</c:if>>审核通过</option>
									<option value="2" <c:if test="${checkStatus eq '2'}"> selected="selected"</c:if>>待审核</option>
							        <option value="3" <c:if test="${checkStatus eq '3'}"> selected="selected"</c:if>>审核未通过</option>
							</select> 
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
							<td align="right">&nbsp;</td>
							<td>
								<input name="clearBtn" type="button" value="下载" class="bluebtn" id="downLoadBtn" onclick="showDetail();downSubmit();" />
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
						<th class="border">付款银行</th>
						<th class="border">付款银行账号</th>
						<th class="border">币种</th>
						<th class="border">录入金额</th>
						<th class="border">创建时间</th>
					</tr>
					<c:if test="${empty sacOtrxInfoList }">
						<tr>
							<td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
						</tr>
					</c:if>
   
					<c:forEach items="${sacOtrxInfoList}" var="item" varStatus="st">
						<c:choose>
							<c:when test="${st.index %2 == 0 }">
								<tr align="center" height="35">
							</c:when>
							<c:otherwise>
								<tr align="center" height="35" bgcolor="#eeeeee">
							</c:otherwise>
						</c:choose>
						<td class="fontfamily1" align="left">
							<c:if test="${item.memo eq '2'}">
							    <input id="checkSucc${st.index}" class="bluebtn" style="width: 90px;" type="button" value="审核通过" onclick="checkSucc('1','${item.id}','${item.draccNodeCode}','${item.draccNo}','${item.payAmount}',${st.index});" />
								<input id="checkFail${st.index}" class="bluebtn"  type="button" value="审核不通过" onclick="checkSucc1('3','${item.id}',${st.index});" />
							</c:if>
						</td>
						<td class="fontfamily1">
							<c:if test="${item.memo eq '1'}">审核完成</c:if>
							<c:if test="${item.memo eq '2'}">待审核</c:if>
							<c:if test="${item.memo eq '3'}">审核不通过</c:if> 
						</td>
						<td class="fontfamily1">${item.draccNodeCode}</td>
						<td class="fontfamily1">${item.draccNo}</td>
						<td class="fontfamily1">${item.payCurrency}</td>
						<td class="fontfamily1">${item.payAmount}</td>
						<td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss" /></td></td>
					</c:forEach>
				</table>
			</div>
			<div style="width: 100%; height: 32px; text-align: right;" id="pageDiv" class="pagination1 btn">
				<table width="100%" cellspacing="0" cellpadding="0" height="24">
					<tr>
						<td align="right" height="24">
							<easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/interrestDealOutCheck" />
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>