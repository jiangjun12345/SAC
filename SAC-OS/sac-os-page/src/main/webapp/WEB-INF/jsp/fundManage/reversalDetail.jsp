<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="reversalDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4">
			<span style="float: left">交易详情</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="jj" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">交易流水号：</td>
					<td><input id="trxSerialNoDetail" name="trxSerialNoDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">外部交易流水号：</td>
					<td><input id="etrxSerialNoDetail" name="etrxSerialNoDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">商户订单号：</td>
					<td><input id="cusBillNoDetail" name="cusBillNoDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">平台订单号：</td>
					<td><input id="platBillNoDetail" name="platBillNoDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">收款方客户号：</td>
					<td> <input id="craccCusCodeDetail" name="craccCusCodeDetail"
						type="text" class="txt1" readonly="readonly"/> 
					</td>
					<td align="right">收款方客户名称：</td>
					<td> <input id="craccCusNameDetail" name="craccCusNameDetail"
						type="text" class="txt1" readonly="readonly"/> 
					</td>
				</tr>
				<tr>
					<td align="right">收款方账号：</td>
					<td><input id="craccNoDetail" name="craccNoDetail"
						type="text" class="txt1" readonly="readonly" />
					</td>
					<td align="right">收款方账户名称：</td>
					<td><input id="craccNameDetail" name="craccNameDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">收款方银行名称：</td>
					<td><input id="craccBankNameDetail"
						name="craccBankNameDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/>
					</td>
					<td align="right">付款方银行名称：</td>
					<td><input id="draccBankNameDetail" name="draccBankNameDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">付款方客户号：</td>
					<td> <input id="draccCusCodeDetail" name="draccCusCodeDetail"
						type="text" class="txt1" readonly="readonly"/> 
					</td>
					<td align="right">付款方客户名称：</td>
					<td> <input id="draccCusNameDetail" name="draccCusNameDetail"
						type="text" class="txt1" readonly="readonly"/> 
					</td>
				</tr>
				<tr>
					<td align="right">付款方账号：</td>
					<td><input id="draccNoDetail" name="draccNoDetail"
						type="text"  class="txt1" readonly="readonly" />
					</td>
					<td align="right" colspan="1">付款方账户名称：</td>
					<td colspan="1"><input id="draccNameDetail" name="draccNameDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">支付币种：</td>
					<td><input id="payCurrencyDetail"
						name="payCurrencyDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">支付金额：</td>
					<td><input id="payAmountDetail"
						name="payAmountDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">购结汇币种：</td>
					<td><input id="sacCurrencyDetail"
						name="sacCurrencyDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">购结汇金额：</td>
					<td><input id="sacAmountDetail"
						name="payAmountDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">运费：</td>
					<td><input id="transportExpensesDetail"
						name="transportExpensesDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">税额：</td>
					<td><input id="taxAmountDetail"
						name="taxAmountDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">交易时间：</td>
					<td><input id="createTimeDetail" name="createTimeDetail"
						type="text"  class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">记账状态：</td>
					<td colspan="1"><input id="accountStatusDetail" name="accountStatusDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">交易状态：</td>
					<td><input id="trxStateDetail" name="trxStateDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right">冲正状态：</td>
					<td><input id="reversalStatusDetail" name="reversalStatusDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">业务类型：</td>
					<td><input id="bussTypeDetail" name="bussTypeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">交易类型：</td>
					<td colspan="1"><input id="trxTypeDetail" name="trxTypeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
				</tr>
				
				<tr>
					 <td><input id="idDetail" name="idDetail"
						 class="txt1" type="hidden"/></td>
				</tr>
				<tr>
				   
				    <td align="right"></td> 
						<td>
						   <input id="confirmClick" name="confirmClick"
							type="button" maxlength="30" class="txt1" value="确认" onclick="reversalConfirm();"/>
							</td>
						<td align="right"></td> 
				   
					<td colspan="1"><input id="cancel" name="cancel"
						type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/></td>
					
				</tr>
			</table>
		</div>
		
		
	</div>
</body>
</html>