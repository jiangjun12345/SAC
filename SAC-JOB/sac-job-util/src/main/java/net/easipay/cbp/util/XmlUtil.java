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
 * your name     2013-5-29        Initailized
 */


import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.easipay.pepp.common.util.StringUtil;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 处理XML
 * @author ylzhang
 *
 */
public class XmlUtil {
    
    public static final String XML_UTF_TITLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    
    public static final String XML_GBK_TITLE = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
    
    /**
	 * 根据map的key和值，组装成简单的单层xml并以string形式返回
	 * @param requestData
	 * @return
	 */
	public static String getGBKXmlByMap(Map<String, String> requestData, String xmlroot) {
		if(requestData==null || xmlroot==null){
			throw new RuntimeException("参数不完整");
		}
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("GBK");
		Element root = document.addElement(xmlroot);//添加文档根  
		
		for(Entry<String, String> entry: requestData.entrySet()){
			Element request = root.addElement(entry.getKey()); //添加root的子节点  
			request.addCDATA(entry.getValue());
		}
		
		return document.asXML();
	}
	/**
	 * 根据map的key和值，组装成简单的单层xml并以string形式返回
	 * @param requestData
	 * @return
	 */
	public static String getUTF8XmlByMap(Map<String, String> requestData, String xmlroot) {
		if(requestData==null || xmlroot==null){
			throw new RuntimeException("参数不完整");
		}
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement(xmlroot);//添加文档根  
		
		for(Entry<String, String> entry: requestData.entrySet()){
			Element request = root.addElement(entry.getKey()); //添加root的子节点  
			request.addCDATA(entry.getValue());
		}
		
		return document.asXML();
	}
	
	/**
	 * 根据map的key和值，组装成简单的单层xml并以string形式返回
	 * @param requestData
	 * @param xmlroot 根节点名
	 * @param xmlsec 二层节点名
	 * @return
	 */
	public static String getUTF8XmlByMap(Map<String, String> requestData, String xmlroot,String xmlsec) {
		if(requestData==null || xmlroot==null){
			throw new RuntimeException("参数不完整");
		}
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding("UTF-8");
		Element root = document.addElement(xmlroot);//添加文档根  
		Element sec = root.addElement(xmlsec); 
		
		
		for(Entry<String, String> entry: requestData.entrySet()){
			Element request = sec.addElement(entry.getKey()); //添加root的子节点  
			request.addCDATA(entry.getValue());
		}
		
		return document.asXML();
	}
	/**
	 * 把xml中数据组装成传入的类，并返回
	 * @param xmldata
	 * @param classname
	 * @return
	 */
	public static void getObjectByXml(String xmldata,Object classname){
		try {   
			Document doc = DocumentHelper.parseText(xmldata); // 将字符串转为XML  
			Element rootElt = doc.getRootElement(); // 获取根节点
			Class<?> classType = classname.getClass(); 
            for(Iterator<?> i = rootElt.elementIterator(); i.hasNext();){    
				Element employee = (Element) i.next();   
				//System.out.println(node.getName()+":"+node.getText()+":"); 
				
			 	String fieldName = employee.getName();
	            String stringLetter = fieldName.substring(0, 1).toUpperCase();
	            //获得相应属性的getXXX和setXXX方法名称    
	            String setName = "set" + stringLetter + fieldName.substring(1);
				
				Method setmethod = classType.getMethod(setName,String.class);
				setmethod.invoke(classname,new Object[]{employee.getText()}); 
            }    
        } catch (Exception e) {   
            System.out.println(e.getMessage());    
        }    
		
	}
	
	/**
	 * 把xml中数据组装成传入的类，并返回，可以指定需要的节点名
	 * @param xmldata
	 * @param classname
	 * @param eleName  需要得到的节点名，返回这个节点下的所有属性,如null返回第一层
	 * @return
	 */
	public static void getObjectByXml(String xmldata,Object classname,String eleName){
		if(StringUtil.isBlank(eleName)){
			getObjectByXml(xmldata,classname);
		}
		try {   
			Document doc = DocumentHelper.parseText(xmldata); // 将字符串转为XML  
			Element rootElt = doc.getRootElement(); // 获取根节点
			
			selectByElement(rootElt,classname,eleName);
			
        } catch (Exception e) {   
            System.out.println(e.getMessage());    
        }    
		
	}
	
	/**
	 * 递归寻找节点名,找到了组装
	 * @param rootElt
	 * @param classname
	 * @param eleName
	 */
	private static void selectByElement(Element rootElt,Object classname,String eleName){
		try {  
			Element ele = null;
			if(!rootElt.getName().equals(eleName)){
				for(Iterator<?> i = rootElt.elementIterator(); i.hasNext();){  
					Element ele1 = (Element) i.next();
					selectByElement(ele1,classname,eleName);
				}
			}else{
				ele = rootElt;
			}
			
			if(ele==null){
				return;
			}
			Class<?> classType = classname.getClass(); 	
			for(Iterator<?> i = ele.elementIterator(); i.hasNext();){    
				Element employee = (Element) i.next();   
				//System.out.println(node.getName()+":"+node.getText()+":"); 
				
			 	String fieldName = employee.getName();
	            String stringLetter = fieldName.substring(0, 1).toUpperCase();
	            //获得相应属性的getXXX和setXXX方法名称    
	            String setName = "set" + stringLetter + fieldName.substring(1);
				
				Method setmethod = classType.getMethod(setName,String.class);
				setmethod.invoke(classname,new Object[]{employee.getText()}); 
            }    
        } catch (Exception e) {   
            System.out.println(e.getMessage());    
        }    
		
	}
	
	
	public static void main(String[] args) {
		
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<EasipayB2CRequest><CnyPayRequest>"+
					"<BILLNO>B0009320140322131613</BILLNO>"+
		"<PayDetail>"+
			"<BILL_DATE>2014-06-10T11:09:19</BILL_DATE>"+
		"</PayDetail>"+
"</CnyPayRequest></EasipayB2CRequest>";
		getObjectByXml(s,null,"ytyty");
		
	}
}
