<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8"%>
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
		}
	};

/*	var zNodes =[
		{ id:'001', pId:'0', name:"系统", open:true ,open:true},
		{ id:'001001', pId:'001', name:"菜单管理", open:true},
		{ id:'001001001', pId:'001001', name:"菜单列表",open:true},
		{ id:'001001002', pId:'001001', name:"菜单修改",open:true},
		{ id:'001002', pId:'001', name:"随意勾选 1-1-2",open:true},
		{ id:'001002001', pId:'001002', name:"随意勾选 1-1-2",open:true}
		// ${TreeMenu}		
	];*/
	 ${TreeMenu}
	

	 var code;
	
	function setCheck() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.expandAll(false);
		//py = $("#py").attr("checked")? "p":"",
		//sy = $("#sy").attr("checked")? "s":"",
		//pn = $("#pn").attr("checked")? "p":"",
		//sn = $("#sn").attr("checked")? "s":"",
		type = { "Y" : "", "N" : ""};
		zTree.setting.check.chkboxType = type;
		showCode('setting.check.chkboxType = {  "Y" : "", "N" : "" };');
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
	
	function addUpdateNode(type){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
       if(nodes!=null && nodes.length == 1){
    	   var id = nodes[0].id;
    	   $("#id").val(id);
    	   if(type == 1){
    		   $("#addNodeInit").attr("action", "addNodeInit");		 
    	   }
    	   else{
    		   $("#addNodeInit").attr("action", "updateNodeInit");	
    	   }
    	   $("#addNodeInit").submit();
    	   
       }
       else{
    	   alert("有且只能选择一个节点");
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
	        	<div class="table fontcolor4 fontsize1 fontfamily2">
	            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	                  	<tr >
							<td valign="top" width="1%" >
								<input type="button" onclick="addUpdateNode(1);" value="添加" class="bluebtn" style="margin-top:20px"/>
							</td>
							<td valign="top" width="10%" >
								<input type="button" onclick="addUpdateNode(2);" value="修改" class="bluebtn" style="margin-top:20px"/>
							</td>
						</tr>
					</table>
	            </div>
	            <div style="display: none;">
	              <form id="addNodeInit" name = "addNodeInit" action="">
	                <input id = "id" name = "id" value =""/>
	              </form>
	            </div>
            
        </div>
    </div>
    <div class="clear"></div>
</div>



<!-- /Body -->
</body>
</html>