<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="refundDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4">
			<span style="float: left">退款详情</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="jj" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">退款流水号：</td>
					<td><input id="trxSerialNoDetail" name="trxSerialNoDetail"
						type="text" class="txt1" readonly="readonly"/></td>
					<td align="right">原支付流水号：</td>
					<td><input id="otrxSerialNoDetail" name="otrxSerialNoDetail" type="text"
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
					<td align="right">收款方银行节点代码：</td>
					<td><input id="craccNodeCodeDetail" name="craccNodeCodeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/>
					</td>
					<td align="right">收款方银行名称：</td>
					<td><input id="craccBankNameDetail"
						name="craccBankNameDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">付款方客户号：</td>
					<td><input id="draccCusCodeDetail" name="draccCusCodeDetail"
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
					</td>
				</tr>
				<tr>
					<td align="right">付款银行节点代码：</td>
					<td><input id="draccNodeCodeDetail" name="draccNodeCodeDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/>
					</td>
					<td align="right">付款方银行名称：</td>
					<td><input id="draccBankNameDetail" name="draccBankNameDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">币种：</td>
					<td><input id="payCurrencyDetail"
						name="payCurrencyDetail" type="text" maxlength="40" class="txt1"
						width="100%" readonly="readonly"/></td>
					<td align="right">退款金额：</td>
					<td><input id="payAmountDetail"
						name="payAmountDetail" type="text" maxlength="30"
						class="txt1" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">退款提交日期：</td>
					<td><input id="createTimeDetail" name="createTimeDetail"
						type="text"  class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">审核时间：</td>
					<td colspan="1"><input id="confirmTimeDetail" name="confirmTimeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/></td>
					</td>
				</tr>
				<tr>
					<td align="right">退款状态：</td>
					<td><input id="trxStateDetail" name="trxStateDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right">审核状态：</td>
					<td><input id="confirmStatusDetail" name="confirmStatusDetail" type="text"
						maxlength="30" class="txt1" readonly="readonly"/></td>
				</tr>
				
				<tr>
					<td align="right">业务类型：</td>
					<td><input id="bussTypeDetail" name="bussTypeDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly"/></td>
					<td align="right" colspan="1">审核人：</td>
					<td colspan="1"><input id="confirmUserDetail" name="confirmUserDetail"
						type="text" maxlength="30" class="txt1" readonly="readonly" /></td>
					</td>
				</tr>
				
				<tr>
					 <td align="right"><b>备注：</b></td>      
					 <td align="left">
					 <textarea name="wordsContent" id="wordsContent" rows="5" cols="20" maxlength="100">
					 </textarea></td>
					 <td><input id="idDetail" name="idDetail"
						 class="txt1" type="hidden"/></td>
				</tr>
				<tr>
					<td align="right"></td> 
					<td><input id="Pass" name="Pass"
						type="button" maxlength="30" class="txt1" value="审核通过" onclick="refundConfirm('Y');"/></td>
					<td align="right"></td> 
					<td colspan="1"><input id="NoPass" name="NoPass"
						type="button" maxlength="30" class="txt1" value="审核不通过" onclick="refundConfirm('N');"/></td>
					</td>
				</tr>
			</table>
		</div>
		
		
	</div>
</body>
</html>