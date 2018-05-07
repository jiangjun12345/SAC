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
public interface FundGiveOLConstants {

	public enum CmdBatchState {
        Init("00", "待处理"), 
        ReviewReject("02", "复核不通过"), 
        WaitingReview("10", "待复核"), 
        ReviewPass("20", "复核通过"),
        Cancel("30", "作废");
        
        private String stateCode;
        private String stateValue;
        
        CmdBatchState(String stateCode, String stateValue) {
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
            for (CmdBatchState state : CmdBatchState.values()) {
                CmdBatchState.stateMap.put(state.stateCode, state.stateValue);
            }
        }
	}
	
	
	public enum CmdState {
        Init("N", "新建待发送"), 
        RequestSuccess("QS", "请求发送成功"),
        TransationSuccess("S", "交易成功"),
        TransationFail("F", "交易失败"),
        TransationSuspend("B", "挂起，备付金余额不足"),
        TransationQR("QR", "挂起,交易已发至银行等待处理");
        
        private String stateCode;
        private String stateValue;
        
        CmdState(String stateCode, String stateValue) {
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
            for (CmdState state : CmdState.values()) {
                CmdState.stateMap.put(state.stateCode, state.stateValue);
            }
        }
    }
}
