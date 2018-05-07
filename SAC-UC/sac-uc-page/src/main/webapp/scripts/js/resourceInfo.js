function resourCheck(){
	$("#resourceNameEMsg").hide();
	$("#resourceNameEMsg").html("");
	
	$("#resourceCodeEMsg").hide();
	$("#resourceCodeEMsg").html("");
	
	$("#resourceUrlEMsg").hide();
	$("#resourceUrlEMsg").html("");
	
	$("#descriptionEMsg").hide();
	$("#descriptionEMsg").html("");
	
	$("#orderNumEMsg").hide();
	$("#orderNumEMsg").html("");
	
	var result = true;
	
	var resourceNameObjectval =  $("#resourceName").val();
	if(resourceNameObjectval.length<1){
		$("#resourceNameEMsg").html("菜单名称不能为空!");
		$("#resourceNameEMsg").show();
		result = false;
	}
	
	if(resourceNameObjectval.length>16){
		$("#resourceNameEMsg").html("菜单名称长度超长！");
		$("#resourceNameEMsg").show();
		result = false;
	}
	
	
	var resourceCodeval = $("#resourceCode").val();
	if (!(/^(\w){1,8}$/.exec(resourceCodeval))) {
		$("#resourceCodeEMsg").html("长度不能为空或者格式错误！");
		$("#resourceCodeEMsg").show();
		result = false;
		
	}
	var resourceUrlObjectval =  $("#resourceUrl").val();
	if(resourceUrlObjectval.length>80){
		$("#resourceUrlEMsg").html("地址长度超长！");
		$("#resourceUrlEMsg").show();
		result = false;
	}
	
	var descriptionObjectval =  $("#description").val();
	if(descriptionObjectval.length>80){
		$("#descriptionEMsg").html("描述长度超长！");
		$("#descriptionEMsg").show();
		result = false;
	}
	
	var orderNumval = $("#orderNum").val();
	if (!(/^\d{0,5}$/.exec(orderNumval))) {
		$("#orderNumEMsg").html("长度或者格式错误！");
		$("#orderNumEMsg").show();
		result = false;
		
	}
	if(!result){
		return result;
	}
	
	/*if(!uniqueCheck()){
		$("#resourceNameEMsg").html("菜单名称和权限编号重复!");
		$("#resourceNameEMsg").show();
		result = false;
	}*/
	
	return result;
}

function resourceInfoCheck(){
	if(resourCheck()){
		$("#resourceInfoForm").submit();
	}
	
}
function resourceInfoEditCheck(){
	if(resourCheck()){
		$("#resourceInfoEditForm").submit();
	}

}


var uniqueCheck = function() {
	var resourceCodeVal = $("#resourceCode").val();
	var resourceNameVal = $("#resourceName").val();
    var result = true;
	var url = "resourceCodeCheck";
	$.ajax( {
		url : url,
		cache : false,
		async : false,
		data : {
			resourceCode : resourceCodeVal,
			resourceName : resourceNameVal
		},
		type : "POST",
		dataType : "json",
		success : function(data) {
			result = data.result;
		},
		error : function(data){
			alert("系统出错！");
		}
	});
return result;
};