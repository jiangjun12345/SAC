<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" 
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="refundDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4" >
			<span style="float: left">退款详情</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="lmc" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">退款流水号：</td>
					<td><input id="refundSerialNoDetail" name="refundSerialNoDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">原交易流水号：</td>
					<td><input id="otrxSerialNoDetail" name="otrxSerialNoDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">商户名称：</td>
					<td><input id="merchantNameDetail"
						name="merchantNameDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">商户号：</td>
					<td><input id="recNcodeDetail"
						name="recNcodeDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">原支付金额：</td>
					<td><input id="payAmountDetail" name="payAmountDetail"
						type="text"  class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">货款退款金额：</td>
					<td colspan="1"><input id="goodsAmountDetail" name="goodsAmountDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">结汇状态：</td>
					<td><input id="exstStateDetail" name="exstStateDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right">运费退款金额：</td>
					<td><input id="transAmountDetail" name="transAmountDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				
				<tr>
					<td align="right">行邮税退款金额：</td>
					<td><input id="taxAmountDetail" name="taxAmountDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">已退款累计金额：</td>
					<td colspan="1"><input id="refundAmountDetail" name="refundAmountDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/>
					</td>
				</tr>
				
				<tr>
					<td align="right">本次退款金额：</td>
					<td><input id="applyAmountDetail" name="applyAmountDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">外币结汇金额：</td>
					<td colspan="1"><input id="crtAmountDetail" name="crtAmountDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/>
					</td>
				</tr>
				
				<tr>
					<td align="right">外币金额：</td>
					<td><input id="rfPayAmountDetail" name="rfPayAmountDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">购汇金额：</td>
					<td colspan="1"><input id="frnAmountDetail" name="frnAmountDetail"
						type="text" maxlength="30" class="txt1"  readonly="readonly"/>
					</td>
				</tr>
				
				<tr>
					<td align="right">购汇状态：</td>
					<td><input id="purchStateDetail" name="purchStateDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">付汇状态：</td>
					<td colspan="1"><input id="remStateDetail" name="remStateDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				
				<tr>
					<td align="right">购汇币种：</td>
					<td><input id="crtCurrencyDetail" name="crtCurrencyDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">退款币种：</td>
					<td colspan="1"><input id="updateTimeDetail" name="updateTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				
				<tr>
					<td align="right">退款方式：</td>
					<td><input id="createTimeDetail" name="createTimeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">商户退款提交日期：</td>
					<td colspan="1"><input id="refundTimeDetail" name="refundTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				
				<tr>
					<td align="right">退款经办操作员号：</td>
					<td><input id="operatorIdDetail" name="operatorIdDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">退款经办日期：</td>
					<td colspan="1"><input id="operateTimeDetail" name="operateTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				
				<tr>
					<td align="right">退款状态：</td>
					<td><input id="applyStateDetail" name="applyStateDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">退款复核操作员号：</td>
					<td colspan="1"><input id="updateTimeDetail" name="updateTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">退款复核日期：</td>
					<td><input id="auditTimeDetail" name="auditTimeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">订单申报状态：</td>
					<td colspan="1"><input id="updateTimeDetail" name="updateTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">订单申报状态：</td>
					<td><input id="taxFlagDetail" name="taxFlagDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">退款完成日期：</td>
					<td colspan="1"><input id="refundOperTimeDetail" name="refundOperTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">备注：</td>
					<td><input id="memoDetail" name="memoDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">退款完成操作员号：</td>
					<td colspan="1"><input id="refundOperIdDetail" name="refundOperIdDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">退款方式：</td>
					<td>线下退款</td>
				</tr>
				<tr>
					<td align="right"></td> 
					<td colspan="1" align="center"><input id="NoPass" name="NoPass"
						type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/>
					</td>
				</tr>
			</table>
		</div>
		
		
	</div>
</body>
</html>