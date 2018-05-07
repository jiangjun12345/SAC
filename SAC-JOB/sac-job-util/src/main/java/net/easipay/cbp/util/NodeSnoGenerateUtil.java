package net.easipay.cbp.util;

import java.text.MessageFormat;
import java.util.Date;

import net.easipay.cbp.sequence.SequenceCreatorUtil;

public class NodeSnoGenerateUtil {
	/**
	 * 根据发送节点代码(7)＋接收节点代码(7)＋系统日期(8)＋保留字（000000，6位）＋业务流水（11）生成发送给清算系统的流水号
	 * 
	 * @param request
	 * @param response
	 * @param trxJson
	 * @throws Exception
	 */
	public static String generateNodsno(String msgSendNode, String msgRevNode) {
		String serialNo = String.valueOf(
				SequenceCreatorUtil.createTimeInMillsSeriNo()).substring(13);
		String trxSerialNo = MessageFormat.format(
				"{0}{1}{2,time,yyyyMMdd}{3}{4,number,00000000000}",
				msgSendNode, msgRevNode, new Date(), "000000",
				Long.valueOf(serialNo));
		return trxSerialNo;
	}
}
