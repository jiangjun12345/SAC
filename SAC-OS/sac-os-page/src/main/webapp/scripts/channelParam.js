 function chnNoCheck(){
    	var result = true;
    	var reyx = /^(\w){1,8}$/;
    	var chnNo = $("#chnNo").val();
    	if (!reyx.exec(chnNo)) {
    		$("#chnNoErr").css("display","block");
    		$("#chnNoErr").css("color","red");
    		$("#chnNoErr").html("长度或者格式错误！");		
    		result = false;
    		return result;
    	}
    		$("#chnNoErr").css("display","none");
    		$("#chnNoErr").html("");
    	    return result;
    }
    
    function chnNameCheck(){
    	var result = true;
    	var chnName = $("#chnName").val();
    	if(chnName.length>16){
    		$("#chnNameErr").css("display","block");
    		$("#chnNameErr").css("color","red");
    		$("#chnNameErr").html("长度超过16位！");		
    		result = false;
    		return result;
    	}
    	$("#chnNameErr").css("display","none");
		$("#chnNameErr").html("");
	    return result;
    }
    
    
    function accountNameCheck(){
    	var result = true;
    	var accountName = $("#accountName").val();
    	if(accountName.length>16){
    		$("#accountNameErr").css("display","block");
    		$("#accountNameErr").css("color","red");
    		$("#accountNameErr").html("长度超过16位！");		
    		result = false;
    		return result;
    	}
    	$("#accountNameErr").css("display","none");
		$("#accountNameErr").html("");
	    return result;
    }
    
    function bankAccCheck(){
    	var result = true;
    	var bankAcc = $("#bankAcc").val();
/*     	if(bankAcc.length()>10){
    		$("#bankAccErr").css("display","block");
    		$("#bankAccErr").css("color","red");
    		$("#bankAccErr").html("长度超过10位！");		
    		result = false;
    		return result;
    	} */
    	var reyx = /^[0-9]{1,29}$/;
    	if (!reyx.exec(bankAcc)) {
    		$("#bankAccErr").css("display","block");
    		$("#bankAccErr").css("color","red");
    		$("#bankAccErr").html("银行账号不合规！");		
    		result = false;
    		return result;
    	}
    	$("#bankAccErr").css("display","none");
		$("#bankAccErr").html("");
	    return result;
    }
    
    function craccBankCodeChenk(){
    	var result = true;
    	var craccBankCode = $("#craccBankCode").val();
    	var reyx = /^[0-9]{1,29}$/;
    	if (!reyx.exec(craccBankCode)) {
    		$("#craccBankCodeErr").css("display","block");
    		$("#craccBankCodeErr").css("color","red");
    		$("#craccBankCodeErr").html("联行号不合规！");		
    		result = false;
    		return result;
    	}
    	$("#craccBankCodeErr").css("display","none");
		$("#craccBankCodeErr").html("");
	    return result;
    }
    
    function costRateChenk(){
    	var result = true;
    	var costRate = $("#costRate").val();
    	var reyx = /^[0-9]+(.[0-9]{1,2})?$/;
    	if (!reyx.exec(costRate) || costRate.length>16) {
    		$("#costRateErr").css("display","block");
    		$("#costRateErr").css("color","red");
    		$("#costRateErr").html("费率填写不合规！");		
    		result = false;
    		return result;
    	}
    	$("#costRateErr").css("display","none");
		$("#costRateErr").html("");
	    return result;
    }
    
    function sacPeriodChenk(){
    	var result = true;
    	var sacPeriod = $("#sacPeriod").val();
    	var reyx = /^[0-9]*[1-9][0-9]*$/;
    	if (!reyx.exec(sacPeriod)) {
    		$("#sacPeriodErr").css("display","block");
    		$("#sacPeriodErr").css("color","red");
    		$("#sacPeriodErr").html("清算周期只能为正整数！");		
    		result = false;
    		return result;
    	}
    	$("#sacPeriodErr").css("display","none");
		$("#sacPeriodErr").html("");
	    return result;
    }
    
    
    function channelSubmit(){
    	if(chnNoCheck()&&chnNameCheck()&&accountNameCheck()&&bankAccCheck()&&craccBankCodeChenk()&&costRateChenk()&&sacPeriodChenk()){
    		$("#ucChannelParamFormId").submit();
    	}
    }
    
    function channelEditSubmit(){
    	if(chnNoCheck()&&chnNameCheck()&&accountNameCheck()&&bankAccCheck()&&craccBankCodeChenk()&&costRateChenk()&&sacPeriodChenk()){
    		$("#ucChannelParamEditId").submit();
    	}
    } 