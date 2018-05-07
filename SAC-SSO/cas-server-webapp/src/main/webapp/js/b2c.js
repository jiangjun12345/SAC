function changeImageCode(){
	var myDate = new Date();
	var imgCode=document.getElementById("imageCode");
	imgCode.src= contextPath + "/image/achieveAuthCode.html?id="+myDate.getMilliseconds();
	return false;
}

function verifyAuthCode(){
	var authCode=document.getElementById("authCode");
	$.ajx({
		type:'post',
		url:'/Cas/image/verifyAuthCode.html',
		data:{'authCode':authCode},
		success: function(data, textStatus, jqXHR){
			alert(data);
		},
		dataType:'json'
	});
}