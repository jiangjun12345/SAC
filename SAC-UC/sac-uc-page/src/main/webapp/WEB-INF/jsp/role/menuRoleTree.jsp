<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>中心数据库维护 >> 菜单树</title>

	<script type="text/javascript">
	
	var setting = {
		check: {
			enable: true

		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
				beforeCheck: beforeCheck
			}
	};

	 ${TreeMenu}
	

	 var code;
	
	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(false);
		//py = $("#py").attr("checked")? "p":"",
		//sy = $("#sy").attr("checked")? "s":"",
		//pn = $("#pn").attr("checked")? "p":"",
		//sn = $("#sn").attr("checked")? "s":"",
		type = { "Y" : "ps", "N" : "s"};
		zTree.setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = {  "Y" : "ps", "N" : "s" };');
	}
	function beforeCheck(treeId, treeNode) {
		//alert("treeNode.doCheck="+treeNode.doCheck);
		if(treeNode.doCheck == false){
			alert("不能管理此权限,请联系管理员!");
			return (treeNode.doCheck !== false);
		}
		return;
	}
	function showCode(str) {
		if (!code) code = $("#code");
		code.empty();
		code.append("<li>"+str+"</li>");
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		setCheck();
		$("#py").bind("change", setCheck);
		$("#sy").bind("change", setCheck);
		$("#pn").bind("change", setCheck);
		$("#sn").bind("change", setCheck);
	});
	
	function saveRole(){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		var roleId = $("#roleId").val(); 
		
		var obj = eval(nodes);
		var str = "";
		for(temp2 in obj){
			for(key in obj[temp2]){
				if("id" == key){
					str += obj[temp2][key] + ',';
				}
			}
		}

		if(confirm("确认保存")){
			$.ajax({
				url: "${ctx}/saveMenuRole",
				cache : false,
				async : false,
				data : {
					str : str,
					roleId : roleId
				},
				type : "POST",
				dataType : "json",
				 success: function(data) {
				  data=eval("("+data+")");//获取从后台返回的数据,通常是Json格式
				      if (data == true) {
				    	   alert("保存成功！");
			               window.location.href="${ctx}/menuRoleTree?roleId="+roleId;
				       }
				    }
				   });
		}
		
		     

	}
	
	
	</script>
	
</head>
<body>
<!-- Body -->

    <div class="content">
    	        
            <div class="con fontfamily2">
   
                <div class="content_wrap">
					<div class="zTreeDemoBackground left">
						<ul id="treeDemo" class="ztree" style="background:#eeeeee; border:1px solid #cccccc"></ul>
					</div>
				</div>
 
    </div>
    	        	<div class="table fontcolor4 fontsize1 fontfamily2">
	            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	            	<input type="hidden"  name="roleId" id="roleId" value="${roleId}"/>
	                  	<tr >
							<td valign="top" width="1%" >
								<input type="button" onclick="saveRole();" value="保存修改" class="bluebtn" style="margin-top:20px"/>
							</td>
						</tr>
					</table>
	            </div>
    <div class="clear"></div>
</div>



<!-- /Body -->
</body>
</html>