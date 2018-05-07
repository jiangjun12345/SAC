 function cusNoCheck(){
    	var result = true;
    	var cusNoObject = $("#cusNo");
    	var cusNoErrObject = $("#cusNoErr");
    	
    	var reyx = /^[A-Za-z0-9]{1,8}$/;
    	
    	if(!reyx.exec(cusNoObject.val())){
    		cusNoErrObject.css("display","block");
    		cusNoErrObject.css("color","red");
    		cusNoErrObject.html("商户号格式错误！");		
    		result = false;
    		return result;
    	}
    	cusNoErrObject.css("display","none");
    	cusNoErrObject.html("");
    	return result;
    }
    
    function cusPlatAccCheck(){
    	var result = true;
    	var cusPlatAccObject = $("#cusPlatAcc");
    	var cusPlatAccErrObject = $("#cusPlatAccErr");
    	var reyx = /^[A-Za-z0-9]{1,30}$/;
    	if(!reyx.exec(cusPlatAccObject.val())){
    		cusPlatAccErrObject.css("display","block");
    		cusPlatAccErrObject.css("color","red");
    		cusPlatAccErrObject.html("商户平台账号格式错误！");		
    		result = false;
    		return result;
    	}
    	cusPlatAccErrObject.css("display","none");
    	cusPlatAccErrObject.html("");
    	return result;
    }
    
    
    function cusPlatAccNameCheck(){
    	var result = true;
    	var cusPlatAccNameObject = $("#cusPlatAccName");
    	var cusPlatAccNameErrObject = $("#cusPlatAccNameErr");
    	if(cusPlatAccNameObject.val().length>16){
    		cusPlatAccNameErrObject.css("display","block");
    		cusPlatAccNameErrObject.css("color","red");
    		cusPlatAccNameErrObject.html("商户平台账号长度超长！");		
    		result = false;
    		return result;
    	}
    	cusPlatAccNameErrObject.css("display","none");
    	cusPlatAccNameErrObject.html("");
    	return result;
    }
    
    
    function sacBankAccCheck(){
    	var result = true;
    	var sacBankAccObject = $("#sacBankAcc");
    	var sacBankAccErrObject = $("#sacBankAccErr");
    	var reyx = /^[0-9]{1,29}$/;
    	if(!reyx.exec(sacBankAccObject.val())){
    		sacBankAccErrObject.css("display","block");
    		sacBankAccErrObject.css("color","red");
    		sacBankAccErrObject.html("银行账号错误！");		
    		result = false;
    		return result;
    	}
    	sacBankAccErrObject.css("display","none");
    	sacBankAccErrObject.html("");
    	return result;
    }
    
    function accNameChenk(){
    	var result = true;
    	var accNameObject = $("#accName");
    	var accNameErrObject = $("#accNameErr");
    	if(accNameObject.val().length>16){
    		accNameErrObject.css("display","block");
    		accNameErrObject.css("color","red");
    		accNameErrObject.html("结算账户名称错误！");		
    		result = false;
    		return result;
    	}
    	accNameErrObject.css("display","none");
    	accNameErrObject.html("");
    	return result;
    }
    
    function craccBankCodeChenk(){
    	var result = true;
    	var craccBankCodeObject = $("#craccBankCode");
    	var craccBankCodeErrObject = $("#craccBankCodeErr");
    	var reyx = /^[0-9]{1,29}$/;
    	if(!reyx.exec(craccBankCodeObject.val())){
    		craccBankCodeErrObject.css("display","block");
    		craccBankCodeErrObject.css("color","red");
    		craccBankCodeErrObject.html("联行号错误！");		
    		result = false;
    		return result;
    	}
    	craccBankCodeErrObject.css("display","none");
    	craccBankCodeErrObject.html("");
    	return result;
    }
    
    function feeRateChenk(){
    	var result = true;
    	var feeRateObject = $("#feeRate");
    	var feeRateErrObject = $("#feeRateErr");
    	var reyx = /^[0-9]+(.[0-9]{1,2})?$/;
    	
    	if(!reyx.exec(feeRateObject.val())){
    		feeRateErrObject.css("display","block");
    		feeRateErrObject.css("color","red");
    		feeRateErrObject.html("手续费率错误！");		
    		result = false;
    		return result;
    	}
    	feeRateErrObject.css("display","none");
    	feeRateErrObject.html("");
    	return result; 	

    }
    
    
    function trcUplimChenk(){
    	var result = true;
    	var trcUplimObject = $("#trcUplim");
    	var trcUplimErrObject = $("#trcUplimErr");
    	var reyx = /^[0-9]+(.[0-9]{1,2})?$/;
    	
    	if(!reyx.exec(trcUplimObject.val())){
    		trcUplimErrObject.css("display","block");
    		trcUplimErrObject.css("color","red");
    		trcUplimErrObject.html("单笔交易上限错误！");		
    		result = false;
    		return result;
    	}
    	trcUplimErrObject.css("display","none");
    	trcUplimErrObject.html("");
    	return result; 	
    }
    
    function memoChenk(){
    	var result = true;
    	var memoObject = $("#memo");
    	var memoErrObject = $("#memoErr");
    	if(memoObject.val().length>30){
    		memoErrObject.css("display","block");
    		memoErrObject.css("color","red");
    		memoErrObject.html("备注信息超长！");		
    		result = false;
    		return result;
    	}
    	memoErrObject.css("display","none");
    	memoErrObject.html("");
    	return result;
    }
    
    
    function cusParamSubmit(){
    	if(cusNoCheck()&&cusPlatAccCheck()&&cusPlatAccNameCheck()&&bankAccCheck()&&bankAccCheck()&&accNameChenk()&&craccBankCodeChenk()
    			&&feeRateChenk()&&trcUplimChenk()&&memoChenk()){
    		$("#cusParameterFormId").submit();
    	}
    }
    
    function cusParamEditSubmit(){
    	if(cusNoCheck()&&cusPlatAccCheck()&&cusPlatAccNameCheck()&&bankAccCheck()&&bankAccCheck()&&accNameChenk()&&craccBankCodeChenk()
    			&&feeRateChenk()&&trcUplimChenk()&&memoChenk()){
    		$("#cusParameterEditFormId").submit();
    	}
    } 