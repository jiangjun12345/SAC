package net.easipay.cbp.util;




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
 * your name     2013-4-2        Initailized
 */



import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.cas.users.SecurityUser;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
/**
 * TODO Add class descriptions
 * 
 * 
 */
public class PersonUtil {
        
	/**
	 * TODO:得到用户
	 */
    public static SecurityOperator getUser() {
        UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (SecurityOperator)u;
        /*SecurityUser securityUser = new SecurityUser();
        securityUser.setUsername("张三");
        securityUser.setId("121212121");
        securityUser.setIdentifyCode("310109199012121111");
        return securityUser;*/
    }
    
    public static boolean isLogin(){
        Object perObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return perObject instanceof SecurityUser;
    }
    
    public SecurityUser trimPerson(SecurityUser person){//把person字段中的空格去掉
    	person.setAddress(person.getAddress().trim());
    	person.setEmail(person.getEmail().trim());
    	person.setIdentifyCode(person.getIdentifyCode().trim());
    	person.setPersonName(person.getPersonName().trim());
    	person.setMobile(person.getMobile().trim());
//    	person.setIdentifyBankAccno(person.getIdentifyBankAccno().trim());
//    	person.setIdentifyBankNcode(person.getIdentifyBankNcode().trim());
    	return person;
    }
}
