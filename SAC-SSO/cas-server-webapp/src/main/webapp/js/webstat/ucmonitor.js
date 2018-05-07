
//################################################
var ucws_var_debug=false;
var ucws_stop_beat = false;
var ucws_var_beatTimeOut = 5*1000;
var ucws_var_checkagreelogin = 1*60*1000; /*2分钟后如果客户退出了，让客户再次上线*/
var ucws_var_pageBeatCount = 0;
var ucws_var_beatCountLimit = 200;
var ucws_var_currPageId = "";
var ucws_var_pageInter;
var ucws_cookiekey_visitorid = "uc_visitorid";
var ucws_var_visitorid = "";
var ucws_var_customerid = "";

var ucws_var_proxyserver = "61.152.165.75";
var ucws_var_proxyport = "9090";
var ucws_var_context = "webstat/"; //例如"webstat/"
var ucws_var_header = "http://";
var ucws_var_pageRequestUrl = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "webstatmain";
var ucws_var_userBeatUrl = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "webstatmain";
var ucws_var_domain = "";

var ucws_win_bgObj;
var ucws_win_msgObj;
var ucws_curr_inviteJson;

var ucws_monitor_type = '0'; //状态监控

//初始化正确的访问地址和端口
function ucws_path_init() {
	var str = document.getElementById("js_ucstarwebstat").src;
	if(str != null && str.length > 0) {
		var theHeader = "http://";
		var index01 = str.indexOf("http://");
		var index011 = str.indexOf("https://");
		if(index01 < 0 && index011 >= 0) {
			theHeader = "https://";
			index01 = index011;
		}
		ucws_var_header = theHeader;
		if(index01 >= 0) {
			str = str.substring(index01+theHeader.length,str.length);
			var index02 = str.indexOf("/");
			if(index02 >= 0) {
				str = str.substring(0,index02);
				var index03 = str.indexOf(":");
				if(index03 >= 0) {
					var myaddress = str;
					var myipaddr = str.substring(0,index03);
					var myport = str.substring(index03+1,str.length);
					ucws_var_proxyserver = myipaddr;
					ucws_var_proxyport = myport;
					ucws_var_pageRequestUrl = theHeader + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "webstatmain";;
					var ucws_var_userBeatUrl = theHeader + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "webstatmain";;
				} else {
					var myaddress = str;
					var myipaddr = str;
					ucws_var_proxyserver = myipaddr;
					ucws_var_proxyport = "80";
					if(ucws_var_header.indexOf("https://") >= 0) {
						ucws_var_proxyport = "443";
					}
					ucws_var_pageRequestUrl = theHeader + ucws_var_proxyserver + "/" + ucws_var_context + "webstatmain";;
					var ucws_var_userBeatUrl = theHeader + ucws_var_proxyserver + "/" + ucws_var_context + "webstatmain";;
				}
			}
		}
	}
}


function ucws_showWin(title, msg, w, h){ 
	var titleheight = "22px"; // 提示窗口标题高度 
	var bordercolor = "#666699"; // 提示窗口的边框颜色 
	var titlecolor = "#FFFFFF"; // 提示窗口的标题颜色 
	var titlebgcolor = "#A0BDE3"; // 提示窗口的标题背景色
	var bgcolor = "#FFFFFF"; // 提示内容的背景色
	
	var iWidth = document.body.clientWidth; 
	var iHeight = document.body.clientHeight;
	var offTop = 0;
	if(iHeight - h > 0) {
		offTop = iHeight - h;
	}
	offTop = 10;
	h = 70;
	/*
	//将背景设置成灰色
	ucws_win_bgObj = document.createElement("div"); 
	ucws_win_bgObj.style.cssText = "position:absolute;left:0px;top:0px;width:"+iWidth+"px;height:"+Math.max(document.body.clientHeight, iHeight)+"px;filter:Alpha(Opacity=30);opacity:0.3;background-color:#000000;z-index:101;";
	document.body.appendChild(ucws_win_bgObj); 
	*/
	ucws_win_msgObj=document.createElement("div");
	ucws_win_msgObj.style.cssText = "position:absolute;font:11px '宋体';top:"+(offTop)/2+"px;left:"+(iWidth-w)/2+"px;width:"+w+"px;height:"+h+"px;text-align:center;border:1px solid "+bordercolor+";background-color:"+bgcolor+";padding:1px;line-height:22px;z-index:2000;";
	document.body.appendChild(ucws_win_msgObj);
	
	var table = document.createElement("table");
	ucws_win_msgObj.appendChild(table);
	table.style.cssText = "margin:0px;border:0px;padding:0px;";
	table.cellSpacing = 0;
	var tr = table.insertRow(-1);
	var titleBar = tr.insertCell(-1);
	titleBar.style.cssText = "width:100%;height:"+titleheight+"px;text-align:left;padding:3px;margin:0px;font:bold 13px '宋体';color:"+titlecolor+";border:1px solid " + bordercolor + ";cursor:move;background-color:" + titlebgcolor;
	titleBar.style.paddingLeft = "10px";
	titleBar.innerHTML = title;
	var moveX = 0;
	var moveY = 0;
	var moveTop = 0;
	var moveLeft = 0;
	var moveable = false;
	var docMouseMoveEvent = document.onmousemove;
	var docMouseUpEvent = document.onmouseup;
	titleBar.onmousedown = function() {
		var evt = getEvent();
		moveable = true; 
		moveX = evt.clientX;
		moveY = evt.clientY;
		moveTop = parseInt(ucws_win_msgObj.style.top);
		moveLeft = parseInt(ucws_win_msgObj.style.left);
		
		document.onmousemove = function() {
			if (moveable) {
				var evt = getEvent();
				var x = moveLeft + evt.clientX - moveX;
				var y = moveTop + evt.clientY - moveY;
				//if ( x > 0 &&( x + w < iWidth) && y > 0 && (y + h < iHeight) ) {
					ucws_win_msgObj.style.left = x + "px";
					ucws_win_msgObj.style.top = y + "px";
				//}
			}	
		};
		document.onmouseup = function () { 
			if (moveable) { 
				document.onmousemove = docMouseMoveEvent;
				document.onmouseup = docMouseUpEvent;
				moveable = false; 
				moveX = 0;
				moveY = 0;
				moveTop = 0;
				moveLeft = 0;
			} 
		};
	}
	
	var closeBtn = tr.insertCell(-1);
	closeBtn.style.cssText = "cursor:pointer; padding:2px;background-color:" + titlebgcolor;
	closeBtn.innerHTML = "<span style='font-size:15pt; color:"+titlecolor+";'>x</span>";
	closeBtn.onclick = function(){ 
		try {
			//删除背景设置
			document.body.removeChild(ucws_win_bgObj); 
		} catch(e) {
			
		}
		document.body.removeChild(ucws_win_msgObj); 
		
		ucws_reLogin();
	} 
	var msgBox = table.insertRow(-1).insertCell(-1);
	msgBox.style.cssText = "font:10pt '宋体';";
	msgBox.colSpan  = 2;
	msgBox.innerHTML = msg;
	
    // 获得事件Event对象，用于兼容IE和FireFox
    function getEvent() {
	    return window.event || arguments.callee.caller.arguments[0];
    }
} 

/**
 * 创建邀请的页面
 */
function ucws_createInviteWindow() {
	//请问有什么能帮助到您的？
	var innerStr = " \u8BF7\u95EE\u6709\u4EC0\u4E48\u80FD\u5E2E\u52A9\u5230\u60A8\u7684\uFF1F";
	//同意
    innerStr += "</br><input type='button' value='\u540C\u610F' onclick='ucws_userAgree()'/>";
    //拒绝
    innerStr += "&nbsp;<input type='button' value='\u62D2\u7EDD' onclick='ucws_userDisAgree()'/>";
	
    //邀请
	ucws_showWin("\u9080\u8BF7",innerStr,300,150);
	
	//停止请求
	ucws_stop_beat = true;
	
}

/**
 * 创建调试页面
 */
function ucws_createDebugDiv() {
	if(document.getElementById("ucwsDebugInfo") != null) {
    	return;
    }
    var div1 = document.createElement('div');
    div1.id='ucwsDebugInfo';
    div1.style.border='1px solid #ddd';
	document.body.appendChild(div1);
}

/**
 * pageId: id号，由客户端生成与服务器进行对应
 * title: 标题
 * browserType: 浏览器类型: 0:IE  1:火狐  2:Google
 * browserCode: 浏览器编码
 * pageUrl: 访问的页面
 * param: 页面的参数
 * statue: 状态  0:刚进入  1:退出
 */
function ucws_pageinfo(pageId,title,bsType,bsCode,pageUrl,param,statue,clientId) {
	this.pageId = pageId;
	this.title = title;
	this.bsType = bsType;
	this.bsCode = bsCode;
	this.pageUrl = pageUrl;
	this.param = param;
	this.statue = statue;
	this.clientId = clientId;
}

function ucws_userinfo(userid) {
	this.userid = userid;
}

//获取页面信息
function ucws_getPageInfo() {
	var vTitle = document.title;
	var vPageId = new Date().getTime();
	ucws_var_currPageId = vPageId;
	var vBrowserType = ucws_util_getBrowser();
	var vBrowserCharset = ucws_util_getPageCharset();
	var vFullUrl = document.location.href;
	
	
	var pageObj = new ucws_pageinfo();
	pageObj.pageId = vPageId;
	pageObj.title = vTitle;
	pageObj.bsType = vBrowserType;
	pageObj.bsCode = vBrowserCharset;
	pageObj.pageUrl = vFullUrl;
	pageObj.clientId = ucws_var_visitorid;
	
	ucws_util_log(ucws_JSON.stringify(pageObj),1);
	
	var url = ucws_var_pageRequestUrl; 
	url += "?callback=?" //跨域
	ucws_getJSON(url, {time:new Date().getTime(),action:'webinfo',from:ucws_var_visitorid,body:ucws_JSON.stringify(pageObj)},
		function(json){
  			ucws_util_log("发送网页信息:"+json.body);
  		}
	);
}

//退出页面
function ucws_outPageInfo() {
	ucws_util_log("Out:" + ucws_var_currPageId);
}

//*********************utils******************
//获取浏览器编码
function ucws_util_getPageCharset(){
    var charSet = "";
    var oType = ucws_util_getBrowser();
    switch(oType){
        case "0":
            charSet = document.charset;
            break;
        case "1":
            charSet = document.characterSet;
            break;
        default:
            break;
    }
    return charSet;
}
//获取浏览器类型: 0:IE  1:火狐  2:Google
function ucws_util_getBrowser(){
    var oType = "";
    if(navigator.userAgent.indexOf("MSIE")!=-1){
        oType="0"; //IE
    }else if(navigator.userAgent.indexOf("Firefox")!=-1){
        oType="1"; //Firefox
    }else {
    	oType="1";
    }
    return oType;
}
//日志调试
function ucws_util_log(msg,level) {
	
	if(ucws_var_debug == false) {
		return;
	}
	
	if(level == undefined) {
		level = 0;
	}
	var content = "level:" + level + "\n " + msg;
	//alert();
	
	if(document.getElementById('ucwsDebugInfo') == null) {
		ucws_createDebugDiv();
	}
	try {
		document.getElementById('ucwsDebugInfo').innerHTML += content + "</br>";
	} catch(e) {
		
	}
}

//获取随机生成的游客ID并存储到cookie中
function ucws_getRandomVisitorId() {
	var vTempId = "" + new Date().getTime();
	vTempId = vTempId.substring(vTempId.length - 5, vTempId.length);
	vTempId = ""+ Math.round(Math.random()*10000) + vTempId;
	return vTempId; 
}

/**
 * 获取游客的ID并放到cookie中
 * @returns
 */
function ucws_getTheVisitorId() {
	var theVisitorId = ucws_cookie(ucws_cookiekey_visitorid);
	if(theVisitorId == null || theVisitorId == '') {
		theVisitorId = ucws_getRandomVisitorId();
		ucws_cookie(ucws_cookiekey_visitorid,theVisitorId,{ expires: 3650, path: '/' });
	}
	return theVisitorId;
}

//*********************end utils**************

function ucws_pageBeat() {
	if(ucws_stop_beat == true) {
		return;
	}
	if(ucws_var_visitorid != null && ucws_var_visitorid != "") {
		
		if(ucws_var_pageBeatCount > ucws_var_beatCountLimit) {
			ucws_util_log("Beat Limit Out");
			return;
		}
		
		ucws_util_log("Page Beat: " + ucws_var_currPageId + ucws_var_pageBeatCount);
		ucws_var_pageBeatCount ++;
		ucws_postuserbeat();
	}
	
}

function ucws_postuserbeat() {
	var userInfo = new ucws_userinfo();
	userInfo.userid = ucws_var_visitorid;
	
	var url = ucws_var_pageRequestUrl; 
	url += "?callback=?" //跨域
	ucws_getJSON(url, {time:new Date().getTime(),action:'userbeat',from:ucws_var_visitorid,body:ucws_JSON.stringify(userInfo)},
		function(json){
			try {
  				ucws_util_log("握手:"+json.body);
  				if(json.invite == "true") {
  					ucws_curr_inviteJson = json;
  					ucws_createInviteWindow();
  				}
  			} catch(e) {
  				ucws_util_log(e);
  			}
  			window.setTimeout(ucws_pageBeat, ucws_var_beatTimeOut);
  		}
	);
}


function ucws_reLogin() {
	ucws_stop_beat = false;
	ucws_init(ucws_var_visitorid,ucws_monitor_type,ucws_var_domain);
}

/**
 * 判断是否是函数
 */
function isUcFunction( fn ) {
	return !!fn && !fn.nodeName && fn.constructor != String &&
	fn.constructor != RegExp && fn.constructor != Array &&
	/function/i.test( fn + "" );
}

/**
 * 同意邀请
 */
function ucws_userAgree() {
	//alert('Agree:' + ucws_var_visitorid);
	if(ucws_curr_inviteJson != null) {
		//alert('Invite:' + ucws_curr_inviteJson.seatId + "|" + ucws_curr_inviteJson.seatName);
		var funcucws_callback_agree;
		try { funcucws_callback_agree = eval('ucws_callback_agree') } catch(e) { }
		if(funcucws_callback_agree != undefined && isUcFunction(funcucws_callback_agree)) {
			funcucws_callback_agree(ucws_curr_inviteJson.seatId,ucws_curr_inviteJson.seatName,ucws_var_customerid);
		} else {
			ucws_preloginWin(ucws_curr_inviteJson.seatId,ucws_curr_inviteJson.seatName,ucws_var_customerid, '1');
		}
	}
	
	var agreeUrl = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "ucmonitor_control.jsp";
	agreeUrl += "?callback=?"; //跨域
	ucws_getJSON(agreeUrl,{action:'agree',visitorId:ucws_var_visitorid});
	
	try {
		//删除背景设置
		document.body.removeChild(ucws_win_bgObj); 
	} catch(e) {
		
	}
	document.body.removeChild(ucws_win_msgObj); 
	
	window.setTimeout(ucws_checkReLogin,ucws_var_checkagreelogin);
}

/**
 * 拒绝邀请
 */
function ucws_userDisAgree() {
	//alert('DisAgree:' + ucws_var_visitorid);
	if(ucws_curr_inviteJson != null) {
		//alert('Invite:' + ucws_curr_inviteJson.seatId + "|" + ucws_curr_inviteJson.seatName);
		var url = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "ucmonitor_control.jsp";
		url += "?callback=?"; //跨域
		ucws_getJSON(url,{action:'disagree',visitorId:ucws_var_visitorid});
	}
	
	try {
		//删除背景设置
		document.body.removeChild(ucws_win_bgObj); 
	} catch(e) {
			
	}
	document.body.removeChild(ucws_win_msgObj); 
	
	ucws_reLogin();
	
}

/**
 * 页面关闭退出监控
 */
function ucws_prelogout() {
	if(ucws_var_visitorid == undefined || ucws_var_visitorid == null) {
		return;
	}
	var userInfo = new ucws_userinfo();
	userInfo.userid = ucws_var_visitorid;
	
	if(ucws_monitor_type == '0') {
		//不进行状态监控
		return;
	}
	var url = ucws_var_pageRequestUrl; 
	url += "?callback=?" //跨域
	ucws_getJSON(url, {time:new Date().getTime(),action:'logout',from:ucws_var_visitorid,body:ucws_JSON.stringify(userInfo)},
		function(json){
  			ucws_util_log("登出:"+json.body);
  			//add by polarrwl , 2011.06.24
  			//ucws_getPageInfo();
  			ucws_var_pageInter = window.setTimeout(ucws_pageBeat, 3*1000);
  		}
	);
}

window.onbeforeunload = function() {
	ucws_prelogout();
}

/**
 * 检查重连情况
 */
function ucws_checkReLogin() {
	if(ucws_stop_beat == true) {
		ucws_reLogin();
	} else {
		window.setTimeout(ucws_checkReLogin,ucws_var_checkagreelogin);
	}
}

/**********************提供外部调用的接口***************************/

/**
 * 监控初始化
 * @param {Object} customerId 游客ID，如果为空，则自动创建
 * @param {Object} mtype 是否进行邀请监控。0:否 1:是 缺省是"是"
 * @param {Object} domain 域(服务器组织架构中对应的域)
 */
function ucws_init(customerId,mtype,domain) {
	
	//初始化IP地址
	ucws_path_init();
	
	//设置domain
	if(domain != undefined) {
		ucws_var_domain = domain;
	}
	
	//初始化当前的客户ID(正式客户用customerId,游客用ucws_var_visitorid);
	if(customerId != null && customerId.length > 0) {
		ucws_var_visitorid = customerId;
		ucws_var_customerid = customerId;
		ucws_util_log("customer id :" + ucws_var_visitorid);
	} else {
		ucws_var_visitorid = ucws_getTheVisitorId();
		ucws_util_log("Visitor id :" + ucws_var_visitorid);
	}
	
	var userInfo = new ucws_userinfo();
	userInfo.userid = ucws_var_visitorid;
	
	if(mtype != undefined) {
		ucws_monitor_type = '' + mtype;
	}
	//监控游客户或者客户的浏览情况
	if(ucws_monitor_type == '1') {
		var url = ucws_var_pageRequestUrl; 
		url += "?callback=?" //跨域
		ucws_getJSON(url, {time:new Date().getTime(),action:'login',from:ucws_var_visitorid,body:ucws_JSON.stringify(userInfo)},
			function(json){
	  			ucws_util_log("登陆:"+json.body);
	  			//add by polarrwl , 2011.06.24
	  			//ucws_getPageInfo();
	  			ucws_var_pageInter = window.setTimeout(ucws_pageBeat, 3*1000);
	  		}
		);
	}
}

/**
 * 正常的登录方式
 */
function ucws_prelogin(_config) {
	if(_config == undefined || _config == null) {
		alert('Config not Found');
	}
	var theUserType = _config.usertype; /*用户类型:0:游客,1:正式客户,2:网站客户*/
	var username = _config.username; /*用户帐号:如果是正式客户和网站客户需要用到*/
	var issso = _config.issso; /*是否单点登录*/
	var serverip = _config.serverip; /*单点登录的服务器地址*/
	var xmppport = _config.xmppport; /*单点登录的端口号*/
	var serverport = _config.serverport; /*IM服务器的HTTP端口号,单点登录用*/
	var winWidth = _config.winwidth; /*弹出窗口宽*/
	var winHeight = _config.winheight; /*弹出窗口高*/ 
	var custname = _config.custname; /*客户名称*/
	var openSeat = _config.openseat; /*是否直接打开座席或者座席队列*/
	var seatId = _config.seatid; /*是否直接打开座席或者座席队列*/
	var seatType = _config.seattype; /*是否直接打开座席或者座席队列*/
	var opentype = _config.opentype; /*打开类型*/
	var clienttype = _config.clienttype; /*客户端类型: phone:手机客户端(对应ucstarclient_phone目录) 其他:web客户端(对应ucstarclient目录)*/
	/*初始化数据*/
	if(theUserType == undefined || theUserType == null) {
		theUserType = '0';
	}
	if(clienttype == undefined || clienttype == null) {
		clienttype = '';
	}
	//如果是游客，则帐号采用自动生成的游客ID
	if(theUserType == '0' && (username == undefined || username == null || username == '')) {
		username = ucws_var_visitorid;
	}
	if(opentype == undefined) {
		opentype = "0";
	}
	if(issso == undefined) {
		issso = true;
	}
	if(custname == undefined) {
		custname = "客户";
	}
	if(winWidth == undefined) {
		winWidth = 600;
	}
	if(winHeight == undefined) {
		winHeight = 400;
	}
	if(openSeat == undefined) {
		openSeat = false;
	}
	if(seatId == undefined) {
		seatId = "";
	}
	if(seatType == undefined) {
		seatType = "0";
	}
	/*初始化数据-END*/
	
	var url = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context;
	if(openSeat == true) {
		url += "ucmonitor_login_seat.jsp"
	} else {
		url += "ucmonitor_login_normal.jsp"
	}
	
	url += "?username=" + username;
	url += "&userType=" + theUserType;
	url += "&serverip=" + serverip;
	url += "&xmppport=" + xmppport;
	url += "&serverport=" + serverport;
	url += "&custname=" + encodeURIComponent(custname);
	url += "&issso=" + issso;
	url += "&seatId=" + seatId;
	url += "&seatType=" + seatType;
	url += "&clienttype=" + clienttype;
	if(opentype == '1') {
		location.href = url;
	} else {
		var myWin = window.open(url,"login",'width=' + winWidth + ',height=' + winHeight + ',scrollbars=yes,status=no,toolbar=no,location=no,menu=no,resizable=no');
	}
}

/**
 * 直接打开座席会话
 * @param {} _visitorId
 * @param {} _seatId
 */
function ucws_preloginWin(_seatId, _seatName, _customerId, _seatType, _winWidth, _winHeight) {
	if(_seatType == undefined || _seatType == ""){
		_seatType = "0";
	}
	var theUserType = "0";
	if(_customerId != null && _customerId != '') {
		theUserType = "1";
	}
	if(ucws_var_visitorid == null || _seatId == null || ucws_var_visitorid == '' || _seatId == '') {
		alert('error,seatid or visitorid is null!');
		return;
	}
//	var url = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "ucmonitor_login.jsp";
	var url = "http://61.152.165.75:9090/webstat/ucmonitor_login.jsp";
//	alert(url);
	if(theUserType == 1) {
		url += "?username=" + _customerId;
	} else {
		url += "?username=" + ucws_var_visitorid;
	}
	url += "&seatId=" + _seatId;
	url += "&seatName=" + _seatName;
	url += "&userType=" + theUserType;
	url += "&domainUri=" + (null == ucws_var_domain ? "":ucws_var_domain);
	url += "&seatType=" + _seatType;
	var winW = 1040;
	var winH = 588;
	if(_winWidth != undefined) {
		winW = _winWidth;
	}
	if(_winHeight != undefined) {
		winH = _winHeight;
	}
	var myWin = window.open(url,"login",'width=' + winW + ',height=' + winH + ',scrollbars=yes,status=no,toolbar=no,location=no,menu=no,resizable=no');
}

/**
 * 直接打开座席会话   安信
 * @author lggang
 * @param {} _visitorId
 * @param {} _seatId
 */
function ucws_preloginWin_anxin(_customerId, _customerSource, _ssoServerIp, _ssoLoginUrl, _ssoServerDomain, _ssoXmppPort, _ssoServerPort, _winWidth, _winHeight) {
	var webClientUrl = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "anxin_webclient/client/";
	var url = "";
	if(_customerId != undefined && _customerId != ""){
		url = _ssoLoginUrl;
		url += "?thetime=" + new Date().getTime(); 
		url += "&username=" + _customerId;
		url += "&usersource=" + _customerSource;
		url += "&serverip=" + _ssoServerIp;
		url += "&serverdomain=" + _ssoServerDomain;
		url += "&xmppport=" + _ssoXmppPort;
		url += "&serverport=" + _ssoServerPort;
		url += "&webclienturl=" + webClientUrl;
	}else{
		url = webClientUrl;
		url += "ucallclient.html?usersource="+_customerSource;
	}
	var winW = 1024;
	var winH = 768;
	if(_winWidth != undefined) {
		winW = _winWidth;
	}
	if(_winHeight != undefined) {
		winH = _winHeight;
	}
	//window.location.href=''+url;
	var myWin = window.open(url,"login",'width=' + winW + ',height=' + winH + ',top=0,left=0,scrollbars=yes,status=no,toolbar=no,location=no,menu=no,resizable=no');
}

/**********************提供外部调用的接口-END***************************/


/**
 * 直接打开座席会话   安信
 * @author lggang
 * @param {} _visitorId
 * @param {} _seatId
 */
function ucws_preloginWin_webcall(_customerId, _customerSource, _ssoServerIp, _ssoLoginUrl, _ssoServerDomain, _ssoXmppPort, _ssoServerPort, _winWidth, _winHeight) {
	var webClientUrl = ucws_var_header + ucws_var_proxyserver + ":" + ucws_var_proxyport + "/" + ucws_var_context + "ucstarclient_webcall/client/";
	var url = "";
	if(_customerId != undefined && _customerId != ""){
		url = _ssoLoginUrl;
		url += "?thetime=" + new Date().getTime(); 
		url += "&username=" + _customerId;
		url += "&usersource=" + _customerSource;
		url += "&serverip=" + _ssoServerIp;
		url += "&serverdomain=" + _ssoServerDomain;
		url += "&xmppport=" + _ssoXmppPort;
		url += "&serverport=" + _ssoServerPort;
		url += "&webclienturl=" + webClientUrl;
	}else{
		url = webClientUrl;
		url += "ucallclient.html?usersource="+_customerSource;
	}
	var winW = 800;
	var winH = 600;
	if(_winWidth != undefined) {
		winW = _winWidth;
	}
	if(_winHeight != undefined) {
		winH = _winHeight;
	}
	var myWin = window.open(url,"login",'width=' + winW + ',height=' + winH + ',scrollbars=yes,status=no,toolbar=no,location=no,menu=no,resizable=no');
}

