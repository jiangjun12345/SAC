package net.easipay.cbp.form;



/**
 * 
* @Description: 封装会计记账数据form
* @author dell (作者英文名称) 
* @date 2015-7-21 下午03:10:48
* @version V1.0  
* @jdk v1.6
* @tomcat v7.0
 */
public class FinTasksForm {
	
	    private String trxCode;
	    private String trxState;
	    private String trxSerialNo;
		
		
		public FinTasksForm() {
			super();
			// TODO Auto-generated constructor stub
		}
		

		public FinTasksForm(String trxCode, String trxState, String trxSerialNo) {
			super();
			this.trxCode = trxCode;
			this.trxState = trxState;
			this.trxSerialNo = trxSerialNo;
		}


		public String getTrxCode() {
			return trxCode;
		}


		public void setTrxCode(String trxCode) {
			this.trxCode = trxCode;
		}


		public String getTrxState() {
			return trxState;
		}


		public void setTrxState(String trxState) {
			this.trxState = trxState;
		}


		public String getTrxSerialNo() {
			return trxSerialNo;
		}


		public void setTrxSerialNo(String trxSerialNo) {
			this.trxSerialNo = trxSerialNo;
		}
		

		
}
