<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>

</head>
<body>
	<div id="settleDetailDiv" style="display: none;">
		<div class="passtitle2 fontfamily2 fontsize1 fontcolor4">
			<span style="float: left">划款录入</span><a href="#" onclick="closeWin();"
				style="float: right">关闭</a>
		</div>
		<div id="jj" class="passtable fontcolor5 fontsize2 fontfamily2"
			style="width: 100%; height: 60%;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td align="right">收款方客户类型：</td>
					<td>
						<select id="craccCusTypeDetail" name="craccCusTypeDetail"  class="txt1" with="100%" >
							<c:forEach items="${customerTypeList}" var="sys">
								<option value="${sys.dicValue}">${sys.dicDesc}</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">收款方客户号：</td>
						<td> <input id="craccCusCodeDetail" name="craccCusCodeDetail"
						type="text" class="txt1" /> 
						<span id ="craccCusCodeError" style="color: red;" ></span>
					</td>
				</tr>
				<tr>
					<td align="right">收款方证件类型：</td>
					<td>
						<select id="craccIdentifyTypeDetail" name="craccIdentifyTypeDetail"  class="txt1" with="100%" >
							<c:forEach items="${certificateTypeList}" var="sys">
								<option value="${sys.dicValue}">${sys.dicDesc}</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">收款方证件号码：</td>
					<td><input id="craccIdentifyCodeDetail" name="craccIdentifyCodeDetail" type="text"
						maxlength="30" class="txt1" />
						<span id ="craccIdentifyCodeError" style="color: red;" ></span>
					</td>
				</tr>
				<tr>
					<td align="right">收款方账号：</td>
					<td><input id="craccNoDetail" name="craccNoDetail"
						type="text" class="txt1" />
					<span id ="craccNoError" style="color: red;" ></span>
					</td>
					<td align="right">收款方账户名称：</td>
					<td><input id="craccNameDetail" name="craccNameDetail" type="text"
						maxlength="30" class="txt1" />
						<span id ="craccNameError" style="color: red;" ></span>
					</td>
				</tr>
				<tr>
					<td align="right">收款方银行节点代码：</td>
					<td>
						<select id="craccNodeCodeDetail" name="craccNodeCodeDetail" class="txt1" >
											<c:forEach items="${sacChannelParamList}" var="sys">
												<option value="${sys.bankNodeCode}" >${sys.bankNodeCode}</option>
											</c:forEach>
						</select>
					</td>
					<td align="right">收款方银行名称：</td>
					<td><input id="craccBankNameDetail"
						name="craccBankNameDetail" type="text" maxlength="30"
						class="txt1" />
						<span id ="craccBankNameError" style="color: red;" ></span>
					</td>
				</tr>
				<tr>
					<td align="right">付款方客户类型：</td>
					<td>
						<select id="draccCusTypeDetail" name="draccCusTypeDetail"  class="txt1" with="100%" >
							<c:forEach items="${customerTypeList}" var="sys">
								<option value="${sys.dicValue}">${sys.dicDesc}</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">付款方客户号：</td>
					<td><input id="draccCusCodeDetail" name="draccCusCodeDetail"
						type="text" class="txt1" />
					<span id ="draccCusCodeError" style="color: red;" ></span>
					</td>
				</tr>
				<tr>
					<td align="right">付款方证件类型：</td>
					<td>
						<select id="draccIdentifyTypeDetail" name="draccIdentifyTypeDetail"  class="txt1" with="100%" >
							<c:forEach items="${certificateTypeList}" var="sys">
								<option value="${sys.dicValue}">${sys.dicDesc}</option>
							</c:forEach>
						</select>
					</td>
					<td align="right">付款方证件号码：</td>
					<td><input id="draccIdentifyCodeDetail" name="draccIdentifyCodeDetail" type="text"
						maxlength="30" class="txt1" />
						<span id ="draccIdentifyCodeError" style="color: red;" ></span>
					</td>
				</tr>
				<tr>
					<td align="right">付款方账号：</td>
					<td><input id="draccNoDetail" name="draccNoDetail"
						type="text"  class="txt1" />
						<span id ="draccNoError" style="color: red;" ></span>
					</td>
					<td align="right" colspan="1">付款方账户名称：</td>
					<td colspan="1"><input id="draccNameDetail" name="draccNameDetail"
						type="text" maxlength="30" class="txt1" />
						<span id ="draccNameError" style="color: red;" ></span>
					</td>
					</td>
				</tr>
				<tr>
					<td align="right">付款银行节点代码：</td>
					<td>
						<select id="draccNodeCodeDetail" name="draccNodeCodeDetail" class="txt1" >
											<c:forEach items="${sacChannelParamList}" var="sys">
												<option value="${sys.bankNodeCode}" >${sys.bankNodeCode}</option>
											</c:forEach>
						</select>
						<span id ="draccNodeCodeError" style="color: red;" ></span>
					</td>
					<td align="right">付款方银行名称：</td>
					<td><input id="draccBankNameDetail" name="draccBankNameDetail" type="text"
						maxlength="30" class="txt1" />
						<span id ="draccBankNameError" style="color: red;" ></span>
					</td>
				</tr>
				
				<tr>
					<td align="right">支付币种：</td>
					<td>
						<select id="payCurrencyDetail" name="payCurrencyDetail"  class="txt1" with="100%" >
							<c:forEach items="${currencyList}" var="sys">
								<option value="${sys.dicCode}">${sys.dicDesc}</option>
							</c:forEach>
						</select>
					</td>
					<td align="right" colspan="1">支付金额：</td>
					<td colspan="1"><input id="payAmountDetail" name="payAmountDetail"
						type="text" maxlength="30" class="txt1"  />
						<span id ="payAmountError" style="color: red;" ></span>
					</td>
					</td>
				</tr>
				<tr>
					<td align="right">支付方式：</td>
					<td>
						<select id="payWayDetail" name="payWayDetail"  class="txt1" with="100%" >
							<c:forEach items="${payTypeList}" var="sys">
								<option value="${sys.dicValue}">${sys.dicDesc}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right"></td> 
					<td><input id="Pass" name="Pass"
						type="button" maxlength="30" class="txt1" value="录入" onclick="addConfirm();"/></td>
					<td align="right"></td> 
					<td colspan="1"><input id="NoPass" name="NoPass"
						type="button" maxlength="30" class="txt1" value="取消" onclick="closeWin();"/></td>
					</td>
				</tr>
				
			</table>
		</div>
		
	</div>
	
</body>
</html>