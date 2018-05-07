function check(){
	$("#roleCodeEMsg").hide();
	$("#roleCodeEMsg").html("");
	
	$("#roleNameEMsg").hide();
	$("#roleNameEMsg").html("");
	
	$("#descriptionEMsg").hide();
	$("#descriptionEMsg").html("");
	var result = true;
	var roleCodeval = $("#roleCode").val();
	if (!(/^(\w){1,8}$/.exec(roleCodeval))) {
		$("#roleCodeEMsg").html("长度不能为空或者格式错误！");
		$("#roleCodeEMsg").show();
		result = false;
		
	}
	
	var roleNameObjectval =  $("#roleName").val();
	if(roleNameObjectval.length>10){
		$("#roleNameEMsg").html("长度超长！");
		$("#roleNameEMsg").show();
		result = false;
	}
	if(roleNameObjectval.length<1){
		$("#roleNameEMsg").html("长度不能为空！");
		$("#roleNameEMsg").show();
		result = false;
	}

	var descriptionObjectval =  $("#description").val();
	if(descriptionObjectval.length>100){
		$("#descriptionEMsg").html("长度超长！");
		$("#descriptionEMsg").show();
		result = false;
	}
	return result;
}

function roleCreateCheck(){
	if(check()){
		$("#roleCreateForm").submit();
	}
	
}
function roleEditCheck(){
	if(check()){
		$("#roleEditForm").submit();
	}

}