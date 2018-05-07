<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="channelDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4" >
			<span style="float: left">渠道参数详情</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="lmc" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">渠道号：</td>
					<td><input id="chnNoDetail" name="chnNoDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">渠道名称：</td>
					<td><input id="chnNameDetail" name="chnNameDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">清算行节点代码：</td>
					<td><input id="bankNodeCodeDetail"
						name="bankNodeCodeDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">银行科目代码：</td>
					<td><input id="chnCodeDetail"
						name="chnCodeDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">清算行名称：</td>
					<td><input id="sacBankNameDetail" name="sacBankNameDetail"
						type="text"  class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">开户名称：</td>
					<td colspan="1"><input id="accountNameDetail" name="accountNameDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/></td>
					</td>
				</tr>
				<tr>
					<td align="right">银行账号：</td>
					<td><input id="craccBankCodeDetail" name="craccBankCodeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right">币种：</td>
					<td><input id="currencyTypeDetail" name="currencyTypeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				
				<tr>
					<td align="right">清算周期：</td>
					<td><input id="sacPeriodDetail" name="sacPeriodDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">成本费率：</td>
					<td colspan="1"><input id="costRateDetail" name="costRateDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/></td>
					</td>
				</tr>
				
				<tr>
					<td align="right">成本计算方式：</td>
					<td><input id="costComWayDetail" name="costComWayDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">成本结算方式：</td>
					<td colspan="1"><input id="costSacWayDetail" name="costSacWayDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/></td>
					</td>
				</tr>
				
				<tr>
					<td align="right">有效标志：</td>
					<td><input id="isValidFlagDetail" name="isValidFlagDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">备注：</td>
					<td colspan="1"><input id="memoDetail" name="memoDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/></td>
					</td>
				</tr>
				
				<tr>
					<td align="right">创建时间：</td>
					<td><input id="createTimeDetail" name="createTimeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">更新时间：</td>
					<td colspan="1"><input id="updateTimeDetail" name="updateTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
					</td>
				</tr>
				
				<tr>
					<td align="right"></td> 
					<td colspan="1" align="center"><input id="NoPass" name="NoPass"
						type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/></td>
					</td>
				</tr>
			</table>
		</div>
		
		
	</div>
</body>
</html>