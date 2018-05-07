<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="interfaceRecLogDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4" >
			<span style="float: left">接口日志详情</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="lmc" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<!-- <table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">接口ID：</td>
					<td><input id="interfaceIdDetail" name="interfaceIdDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">接口名称：</td>
					<td><input id="demoDetail" name="demoDetail" type="text"
						 class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">调用渠道：</td>
					<td><input id="channelDetail"
						name="channelDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">来源渠道：</td>
					<td><input id="originDetail"
						name="originDetail" type="text" 
						class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">服务端IP：</td>
					<td><input id="serverIpDetail" name="serverIpDetail"
						type="text"  class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">URL：</td>
					<td colspan="1"><input id="urlDetail" name="urlDetail"
						type="text"  class="txt1" readonly="readonly"/></td>
					</td>
				</tr>
				<tr>
					<td align="right">调用时长：</td>
					<td><input id="timemillisDetail" name="timemillisDetail" type="text"
						 class="txt1" readonly="readonly"/></td>
					<td align="right">记录时间：</td>
					<td><input id="createTimeDetail" name="createTimeDetail" type="text"
						 class="txt1" readonly="readonly"/></td>
				</tr>
				<tr width="100%">
					<td align="left">返回信息：</td>
					<td align="left"><input id="messageDetail" name="messageDetail"
						type="text"  class="txt1"  readonly="readonly"/></td>
					</td>
				</tr>
				<tr>
					<td align="right"></td> 
					<td colspan="1" align="center"><input id="aaa" name="aaa"
						type="button"  class="txt1" value="取消" onclick="closeWin();"/></td>
					</td>
				</tr>
			</table> -->
			<table>
				<tbody>
				<tr width="100%">
					<td align="left">返回信息：</td>
					<td align="left"><textarea rows="10" cols="110" id="messageDetail" name="messageDetail"
					 readonly="readonly"> </textarea></td>
					</td>
				</tr>
				</tbody>
			</table>
			<table>
				<tbody>
					<tr width="100%">
					<td align="left">报文信息：</td>
					<td align="left">
						<textarea rows="15" cols="110" id="dataDetail" name = "dataDetail" readonly="readonly"> </textarea>
					</td>
				</tr>
				</tbody>
			</table>
		</div>
		
	</div>
</body>
</html>