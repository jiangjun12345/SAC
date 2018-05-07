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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SignAssert {

	private static final Logger logger = Logger.getLogger(SignAssert.class);

	public static void isTrue(boolean express, String errorMsg) {
		if (!express) {
			logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
	}

	public static void notNull(Object obj, String errorMsg) {
		if (obj == null) {
			logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
	}

	public static void notBlank(String str, String errorMsg) {
		if (StringUtils.isBlank(str)) {
			logger.error(errorMsg);
			throw new RuntimeException(errorMsg);
		}
	}

}
