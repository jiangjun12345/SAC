/**
 * Copyright : www.easipay.net , 2009-2010
 * Project : PEPP
 * $Id$
 * $Revision$
 * Last Changed by $Author$ at $Date$
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * your name     2011-6-22        Initailized
 */

package net.easipay.cbp.util;

import java.util.Map;

import net.easipay.cbp.security.EnvelopInfo;
import net.easipay.cbp.security.XmlSign;

import org.apache.log4j.Logger;

public class SignXmlUtil {

	private static final Logger logger = Logger.getLogger(SignXmlUtil.class);

	public static String signXmlToCleanSys(Map<String, String> signKeyMap,
			EnvelopInfo info, String msgContent) {
		String keyStoreUrl = signKeyMap.get("keyStoreUrl");
		String keyPassword = signKeyMap.get("keyPassword");
		String storePassword = keyPassword;
		String alias = signKeyMap.get("alias");
		XmlSign sign = new XmlSign(keyStoreUrl, keyPassword, storePassword,
				alias);
		String signMsgContent = sign.signXml(msgContent, info);
		return signMsgContent;
	}
}
