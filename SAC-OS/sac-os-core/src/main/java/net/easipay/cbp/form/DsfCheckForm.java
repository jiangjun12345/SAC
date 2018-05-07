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
public class DsfCheckForm {
	
	    private String orderNo;
	    private String stateCode;
	    private String stateDesc;
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getStateCode() {
			return stateCode;
		}
		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}
		public String getStateDesc() {
			return stateDesc;
		}
		public void setStateDesc(String stateDesc) {
			this.stateDesc = stateDesc;
		}
		
		

		
}
