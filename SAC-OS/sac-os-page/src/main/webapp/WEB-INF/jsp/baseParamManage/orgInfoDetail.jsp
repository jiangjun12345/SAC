<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="orgDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4">
			<span style="float: left">客户参数详情</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="lmc" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">客户号：</td>
					<td><input id="cusNoDetail" name="cusNoDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">客户名称：</td>
					<td><input id="cusNameDetail" name="cusNameDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">客户平台账号：</td>
					<td><input id="cusPlatAccDetail"
						name="cusPlatAccDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">客户平台账户名称：</td>
					<td><input id="cusPlatAccNameDetail"
						name="cusPlatAccNameDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">结算银行账号：</td>
					<td><input id="sacBankAccDetail" name="sacBankAccDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right">账号名称：</td>
					<td><input id="accNameDetail" name="accNameDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				
				<tr>
					<td align="right">开户行：</td>
					<td><input id="depositBankDetail" name="depositBankDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">联行号：</td>
					<td colspan="1"><input id="craccBankCodeDetail" name="craccBankCodeDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/></td>
					</td>
				</tr>
				
				<tr>
					<td align="right">手续费率：</td>
					<td><input id="feeRateDetail" name="feeRateDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">手续费计算方式：</td>
					<td colspan="1"><input id="feeComWayDetail" name="feeComWayDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
					</td>
				</tr>
				
				<tr>
					<td align="right">手续费结算方式：</td>
					<td><input id="feeSacWayDetail" name="feeSacWayDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">结算周期类型：</td>
					<td colspan="1"><input id="sacTypeDetail" name="sacTypeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
					</td>
				</tr>
				
				<tr>
					<td align="right">结算周期：</td>
					<td><input id="sacPeriodDetail" name="sacPeriodDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly" /></td>
					<td align="right" colspan="1">间隔天数：</td>
					<td colspan="1"><input id="intervalNumberDetail" name="intervalNumberDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/></td>
					</td>
				</tr>
				<tr>
					<td align="right">结算币种：</td>
					<td><input id="sacCurrencyDetail" name="sacCurrencyDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">结算起点金额：</td>
					<td colspan="1"><input id="sacStartAmountDetail" name="sacStartAmountDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
					</td>
				</tr>
				<tr>
					<td align="right">单笔交易上限：</td>
					<td><input id="trxUpLimDetail" name="trxUpLimDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">有效标志：</td>
					<td colspan="1"><input id="isValidFlagDetail" name="isValidFlagDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
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
					<td align="right">备注：</td>
					<td><input id="orgCardIdDetail" name="orgCardIdDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">退款扎差标志：</td>
					<td colspan="1"><input id="refundFlagDetail" name="refundFlagDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/></td>
					</td>
				</tr>
				<tr>
					<td align="right">业务类型：</td>
					<td><input id="bussTypeDetail" name="bussTypeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="center"></td> 
					<td colspan="1"><input id="NoPass" name="NoPass"
						type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/></td>
					</td>
				</tr>
			</table>
		</div>
		
		
	</div>
</body>
</html>