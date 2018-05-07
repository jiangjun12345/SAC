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
		<title>预存批次查询</title>
		<script type="text/javascript" src="${ctx}/scripts/js/clean.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function queryBatchMsg(){
	$("#subForm").submit();
}
function clearText(){
	$("#batchState").value="";
}


function showDetail(batchId){
	var url="getPrestoreDetailByBatch"; //转向网页的地址;
    var name="预存详细信息";                        //网页名称，可为空;
    var iWidth=900;                          //弹出窗口的宽度;
    var iHeight=350;                       //弹出窗口的高度;
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
    
  			   window.open(url+'?oflDepositBatchId='+batchId , name,params);
}
</script>
</head>
<body>
<!-- Body -->

    <div class="content">
    	<div class="con ">
				<form:form id="subForm" action="${ctx}/prestoreBatchQueryInit" method="POST" >   	    
					<div class="table fontcolor4 fontsize1 fontfamily2" >
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="right">批次制作日期(起):</td>
	 							<td>
	 							<input type="text" class="txt2" id="startDate" name="startDate" value="${startDate}" 
	 								onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',minDate:'#F{$dp.$D(\'endDate\',{d:-2000})}',maxDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
		 						</td>
		 						<td align="right">批次制作日期(止):</td>
		 						<td>
								<input type="text" class="txt2" id="endDate" name="endDate" value="${endDate}" 
									onfocus="WdatePicker({readOnly:true,dateFmt:'yyyyMMdd',maxDate:'#F{$dp.$D(\'startDate\',{d:2000})}',minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})"/>
							</tr>
							<tr>
								<td width="10%" align="right">批次状态：</td>
								<td width="25%"><select id="batchState" name="batchState"
									class="select1">
										<option value="">全部</option>
										<c:forEach items="${batchStateList}" var="sys">
											<option value="${sys.dicValue}"
												<c:if test="${sys.dicValue == batchState}"> selected="selected"</c:if>>${sys.dicDesc}</option>
										</c:forEach>
								</select>
								</td>
								<td width="12%" align="right">批次号：</td>
								<td width="30%">
								 <input  name="oflDepositBatchId" class="txt2" id="oflDepositBatchId" value="${oflDepositBatchId}"/>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">&nbsp;</td>
								<td width="15%"><input name="submitbtn" type="button" 
									 value="查询" class="bluebtn"
									id="submitbtn" onclick="queryBatchMsg();"/>
								</td>
								<td width="10%" align="left">&nbsp;</td>
								<td width="15%"><input name="clearBtn" type="button" 
									 value="清除" class="bluebtn"
									id="clearBtn" onclick="clean();clearText();"/>
								</td>
							</tr>
							
						</table>
					</div>
				</form:form>
			</div>
			
			
			<div class="table fontcolor4 fontsize1 fontfamily2">
        <table id = "table1"  width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr height="25" bgcolor="#cccccc">
                                	<th class="border">序号</th>
                                    <th class="border">批次号</th>
                                    <th class="border">总笔数</th>
                                    <th class="border">总金额</th>
                                    <th class="border">创建时间</th>
                                    <th class="border">批次复核状态</th>
                                    <th class="border">批次经办人</th>
                                    <th class="border">操作</th>
                                </tr>
                                <c:if test="${empty batchList }">
                                    <tr>
                                        <td class="fontfamily1" colspan="5" align="center">没有相关记录!</td>
                                    </tr>
                                </c:if>
                                <c:forEach items="${batchList}" var="item" varStatus="st">
                                    <c:choose>
                                        <c:when test="${st.index %2 == 0 }">
                                            <tr align="center" height="25" ">
                                        </c:when>
                                        <c:otherwise>
                                            <tr align="center" height="25" bgcolor="#eeeeee" ">
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    
                                    <td class="fontfamily1">${st.index+1}</td>
                                    <td class="fontfamily1">${item.oflDepositBatchId}</td>
                                    <td class="fontfamily1">${item.batchTcount}</td>
                                    <td class="fontfamily1">${item.batchTamount}</td>
                                    <td class="fontfamily1"><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                                    <td class="fontfamily1"><c:if test="${item.batchState eq '00'}">待复核</c:if><c:if test="${item.batchState eq '20'}">复核成功</c:if><c:if test="${item.batchState eq '02'}">复核失败</c:if></td>
                                    <td class="fontfamily1">${item.operatorName}</td>
                                   	<td class="fontfamily1"><input type="button" value="详细"
									onClick="showDetail('${item.oflDepositBatchId}');">&nbsp;
									</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div style="width: 100%; height: 32px; text-align: right;"id="pageDiv" class="pagination1 btn">
							<table width="100%" cellspacing="0" cellpadding="0" height="24">
								<tr>
									<td align="right" height="24"><easipay:pageNum pageSize="${pageSize}" count="${count}" pageNo="${pageNo}" url="/prestoreBatchQueryInit"/>
									</td>
								</tr>
							</table>
							</div>
							
    		</div>
    </div>
<!-- /Body -->
</body>
</html>