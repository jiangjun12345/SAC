/**
 * Copyright : www.easipay.net , 2009-2010
 * Project : B2B
 * $Id$
 * $Revision$
 * Last Changed by $Author$ at $Date$
 * $URL$
 * 
 * Change Log
 * Author      Change Date    Comments
 *-------------------------------------------------------------
 * cjwang     2011-6-22        Initailized
 */

package net.easipay.cbp.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import net.easipay.pepp.common.util.Base64Util;

import org.apache.log4j.Logger;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

public class MarshallerUtil {
    
    private static final Logger logger = Logger.getLogger(MarshallerUtil.class);
    
    public static String marshal(Object object) {
        return marshal(object, false);
    }
    
    public static String marshal(Object object, boolean shouleBase64Encode) {
        StringWriter output = new StringWriter(2048);
        try {
            Marshaller.marshal(object, output);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
        String string = output.toString();
        if (shouleBase64Encode) {
            try {
                string = new String(Base64Util.encode(string.getBytes()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return string;
        
    }
    
    /**
     * xmlObject绑定
     * @param clazz
     * @param str
     * @return
     * @throws MarshalException
     * @throws ValidationException
     */
    public static <T> T unmarshal(Class<T> clazz, String str) throws Exception{
    	StringReader sr = null;
    	try {
    		sr = new StringReader(str);
    		T object = (T)Unmarshaller.unmarshal(clazz, sr);
    		return object;
		} finally {
			if(null != sr){
				sr.close();
			}
		}
    	
    	
    }
    
    public static void main(String[] args) {
		
	}
}
