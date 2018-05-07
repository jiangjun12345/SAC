/** 
* <p>Title: jquery.ajax</p> 
* <p>Description: use ajax method,you can pass json object and string object.</p> 
* <p>Copyright: Copyright (c) 2012</p> 
* <p>Company: easipay</p> 
* @author jjzhao 
* @date 2012-8-13 
* @version 1.0 
*/ 

function ajaxOfJquery(url,param,type,success_method,error_method){
	$.ajax( {
		type : 'POST',
		//contentType : 'application/json;charset="utf-8"',
		contentType : 'application/x-www-form-urlencoded', 
		url : url,
		data : param,
		dataType : type,
		success : success_method,
		error : error_method
	}); 
}

function jsonAjax(url,param){
	ajaxOfJquery(url,param,'json',defaultSuccess,defaultError);
}

function jsonAjax(url,param,success_method){
	ajaxOfJquery(url,param,'json',success_method,defaultError);
}

function jsonAjax(url,param,success_method,error_method){
	ajaxOfJquery(url,param,'json',success_method,error_method);
}

function textAjax(url,param){
	ajaxOfJquery(url,param,'text',defaultSuccess,defaultError);
}

function textAjax(url,param,success_method,error_method){
	ajaxOfJquery(url,param,'text',success_method,error_method);
}

function defaultSuccess(data){
	alert("新增成功！" + data);
}

function defaultError(data){
	alert("error");
}

/*jQuery.param=function( a ) {
	var s = [ ];
	var encode=function(str){
		str=escape(str);
		//str=str.replace(/+/g,"%u002B");
		return str;
	};
	function add( key, value ){
		s[ s.length ] = encode(key) + '=' + encode(value);
	};
	// If an array was passed in, assume that it is an array
	// of form elements
	if ( jQuery.isArray(a) || a.jquery )
		// Serialize the form elements
		jQuery.each( a, function(){
			add( this.name, this.value );
		});

	// Otherwise, assume that it's an object of key/value pairs
	else
		// Serialize the key/values
		for ( var j in a )
			// If the value is an array then the key names need to be repeated
			if ( jQuery.isArray(a[j]) )
				jQuery.each( a[j], function(){
					add( j, this );
				});
			else
				add( j, jQuery.isFunction(a[j]) ? a[j]() : a[j] );

	// Return the resulting serialization
	return s.join("&").replace(/%20/g, "+");
}*/