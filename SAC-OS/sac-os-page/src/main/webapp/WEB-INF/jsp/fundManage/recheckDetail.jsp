<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="javax.swing.text.Document"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="recheckDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4">
			<span style="float: left">复核</span><a href="#" onclick="closeWin2();"
				style="float: right">关闭</a>
		</div>
		<div id="jj" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">收款方客户类型：</td>
					<td>
						<input id="craccCusTypeDetail2" name="craccCusTypeDetail2" type="text"
						maxlength="30" class="txt1" readonly="readonly"/>
					</td>
					<td align="right">收款方客户号：</td>
						<td> <input id="craccCusCodeDetail2" name="craccCusCodeDetail2"
						type="text" class="txt1"  readonly="readonly" /> 
					</td>
				</tr>
				<tr>
					<td align="right">收款方账号：</td>
					<td><input id="craccNoDetail2" name="craccNoDetail2"
						type="text" class="txt1"  readonly="readonly" />
					</td>
					<td align="right">收款方账户名称：</td>
					<td><input id="craccNameDetail2" name="craccNameDetail2" type="text"
						maxlength="30" class="txt1"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">收款方银行节点代码：</td>
					<td>
						<input id="craccNodeCodeDetail2" name="craccNodeCodeDetail2" type="text"
						maxlength="30" class="txt1"  readonly="readonly" />
					</td>
					<td align="right">收款方银行名称：</td>
					<td><input id="craccBankNameDetail2"
						name="craccBankNameDetail2" type="text" maxlength="30"
						class="txt1"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">付款方客户类型：</td>
					<td>
						<input id="draccCusTypeDetail2" name="draccCusTypeDetail2" type="text"
						maxlength="30" class="txt1"  readonly="readonly" />
					</td>
					<td align="right">付款方客户号：</td>
					<td><input id="draccCusCodeDetail2" name="draccCusCodeDetail2"
						type="text" class="txt1"  readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td align="right">付款方账号：</td>
					<td><input id="draccNoDetail2" name="draccNoDetail2"
						type="text"  class="txt1"  readonly="readonly" />
					</td>
					<td align="right" colspan="1">付款方账户名称：</td>
					<td colspan="1"><input id="draccNameDetail2" name="draccNameDetail2"
						type="text" maxlength="30" class="txt1"  readonly="readonly" />
					</td>
					</td>
				</tr>
				<tr>
					<td align="right">付款银行节点代码：</td>
					<td>
						<input id="draccNodeCodeDetail2" name="draccNodeCodeDetail2" type="text"
						maxlength="30" class="txt1"  readonly="readonly" />
					</td>
					<td align="right">付款方银行名称：</td>
					<td><input id="draccBankNameDetail2" name="draccBankNameDetail2" type="text"
						maxlength="30" class="txt1"  readonly="readonly" />
					</td>
				</tr>
				
				<tr>
					<td align="right">支付币种：</td>
					<td>
						<input id="payCurrencyDetail2" name="payCurrencyDetail2" type="text"
						maxlength="30" class="txt1"  readonly="readonly" />
					</td>
					<td align="right" colspan="1">支付金额：</td>
					<td colspan="1"><input id="payAmountDetail2" name="payAmountDetail2"
						type="text" maxlength="30" class="txt1"  readonly="readonly"  />
					</td>
					</td>
				</tr>
				
				<tr>
					<td align="right">银行流水号：</td>
					<td>
						<input id="etrxSerialNoDetail2" name="etrxSerialNoDetail2" type="text"
						maxlength="30" class="txt1"   />
						<span id = "etrxSerialNoError" style="color: red;"></span>
						
						<input id="trxSerialNoDetail2" name="trxSerialNoDetail2" type="text"
						maxlength="30" class="txt1"  style="display: NONE" />
						
						<input id="trxStateDetail2" name="trxStateDetail2" type="text"
						maxlength="30" class="txt1"  style="display: NONE"  />
						
					</td>
				</tr>
				<tr>
					<td align="right"></td> 
					<td><input id="passClick" name="passClick"
						type="button" maxlength="30" class="txt1" value="复核" onclick="recheck();"/></td>
					<td align="right"></td> 
					<td colspan="1"><input id="NoPass" name="NoPass"
						type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin2();"/></td>
					</td>
				</tr>
				
			</table>
		</div>
		
	</div>
</body>
</html>