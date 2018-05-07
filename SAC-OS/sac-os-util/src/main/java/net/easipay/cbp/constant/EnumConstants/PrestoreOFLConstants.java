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
public interface PrestoreOFLConstants {

    /**
     * 
     * // 处理状态：00 未处理；01 成功待复核； 02 失败待复核；03 未成功待处理 ；04
      // 手工销账待复核；05 手工销账复核不通过；10 预存成功
     *
     * @author jj
     */
    public enum OFLDepositDealState {
        Init("00", "待处理"), 
        InitSuc("01", "成功待复核"), 
        InitFail("02", "失败待复核"),
        FailPendingProcess("03", "未成功待处理"),
        ManualCancelInit("04", "手工销账待复核"),
        ManualCancelFail("05", "手工销账复核不通过"),
        WaitMakeBatch("06", "待重新制作批次"),
        Succeed("10", "预存成功");
        
        private String stateCode;
        private String stateValue;
        
        OFLDepositDealState(String stateCode, String stateValue) {
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
            for (OFLDepositDealState state : OFLDepositDealState.values()) {
                OFLDepositDealState.stateMap.put(state.stateCode, state.stateValue);
            }
        }
    }
    
    /**
     * 
     * 核销状态：00 未核销； 10 核销成功
     *
     * @author jj
     */
    public enum OFLCheckDealState {
        Init("00", "未核销"), 
        Succeed("10", "已核销");
        
        private String stateCode;
        private String stateValue;
        
        OFLCheckDealState(String stateCode, String stateValue) {
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
            for (OFLCheckDealState state : OFLCheckDealState.values()) {
            	OFLCheckDealState.stateMap.put(state.stateCode, state.stateValue);
            }
        }
    }
    
    public enum OFLBatchState {
        Init("00", "待处理"), ReviewReject("02", "复核不通过"), 
        WaitingReview("10","待复核"), ReviewPass("20", "复核通过");
        
        private String stateCode;
        private String stateValue;
        
        OFLBatchState(String stateCode, String stateValue) {
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
            for (OFLBatchState state : OFLBatchState.values()) {
                OFLBatchState.stateMap.put(state.stateCode, state.stateValue);
            }
        }
    }
}
