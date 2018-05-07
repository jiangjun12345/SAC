/**
 * Copyright : www.easipay.net , 2011-2012
 * Project : PEPP
 * $Id$
 * $Revision$
 * Last Changed by $Author$ at $Date$
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * zhangyl     2012-2-24        Initailized
 */

package net.easipay.cbp.constant.EnumConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* ClassName: PrestoreOFLConstants <br/> 
* Function: TODO ADD FUNCTION. <br/> 
* Reason: TODO ADD REASON(可选). <br/> 
* date: 2016年3月1日 下午1:33:07 <br/> 
* 
* @author Administrator 
* @version  
* @since JDK 1.6
 */
public interface FundGiveOFLConstants {

	public enum OflCmdState {
		Init("00", "待发送"),
        QequestSuccess("01", "请求发送成功"), 
        Success("02", "交易成功"),
		Failue("03", "交易失败");
        
        private String stateCode;
        private String stateValue;
        
        OflCmdState(String stateCode, String stateValue) {
            this.stateCode = stateCode;
            this.stateValue = stateValue;
        }
        
        public static String getStateValue(String code) {
            return stateMap.get(code);
        }
        
        public String code() {
            return this.stateCode;
        }
        
        public String value() {
            return this.stateValue;
        }
        
        @Override
        public String toString() {
            return this.stateCode + ":" + this.stateValue;
        }
        
        private static Map<String, String> stateMap = new HashMap<String, String>();
        static {
            for (OflCmdState state : OflCmdState.values()) {
            	OflCmdState.stateMap.put(state.stateCode, state.stateValue);
            }
        }
	}
	
	
}
