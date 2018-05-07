package net.easipay.cbp.service.impl;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.dao.ISacOperHistoryDao;
import net.easipay.cbp.model.SacOperHistory;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacOperHistoryService;
import net.easipay.cbp.util.PersonUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacOperHistoryService")
public class SacOperHistoryServiceImpl implements ISacOperHistoryService {
	
	private static final Logger logger = Logger.getLogger(SacOperHistoryServiceImpl.class);

	@Autowired
	private ISacOperHistoryDao sacOperHistoryDao;
	

	@Override
	public void insertSacOperHistory(String operType,HttpServletRequest request) {
		SacOperHistory sacOperHistory = new SacOperHistory();
		SecurityOperator person = PersonUtil.getUser();
		sacOperHistory.setCreateTime(new Date());
		sacOperHistory.setId(SequenceCreatorUtil.getTableId());
		sacOperHistory.setOperType(operType);
		sacOperHistory.setUserId(person.getMobile());
		sacOperHistory.setUserName(person.getUsername());
		sacOperHistory.setLoginIp(getIpAddr(request));
		sacOperHistoryDao.insertSacOperHistory(sacOperHistory);
	}
	
	public String getIpAddr(HttpServletRequest request) { 
		
		String ip = request.getHeader("x-forwarded-for");
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("Proxy-Client-IP"); 
		} 
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getRemoteAddr(); 
		} 
			return ip; 
	} 


	public String getLoginIp() {
		try {  
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {  
                NetworkInterface item = e.nextElement();  
  
                for (InterfaceAddress address : item.getInterfaceAddresses()) {  
                    if (address.getAddress() instanceof Inet4Address) {  
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();  
                        if(!inet4Address.isLinkLocalAddress()&&
                      		  !inet4Address.isLoopbackAddress()&&
                      		  !inet4Address.isMCGlobal()&&
                      		  !inet4Address.isMulticastAddress()){
                      	 return inet4Address.getHostAddress();  
                        }
                    }  
                }  
            }  
           
        } catch (IOException ex) {  
        	logger.error(ex.getMessage(),ex);
        }   
		 return "";
	}


	@Override
	public int selectSacOperHistoryCounts(SacOperHistory sacOperHistory) {
		return sacOperHistoryDao.selectSacOperHistoryCounts(sacOperHistory);
	}


	@Override
	public List<SacOperHistory> selectSacOperHistoryByParam(SacOperHistory sacOperHistory,int pageNo,int pageSize) {
		return sacOperHistoryDao.selectSacOperHistoryByParam(sacOperHistory,pageNo,pageSize);
	}
	
	
	public static void main(String [] args){
		/*Enumeration<NetworkInterface> netInterfaces = null;        
		try {            
			netInterfaces = NetworkInterface.getNetworkInterfaces();            
			while (netInterfaces.hasMoreElements()) {                
				NetworkInterface ni = netInterfaces.nextElement();                
				System.out.println("DisplayName:" + ni.getDisplayName());               
				System.out.println("Name:" + ni.getName());               
				Enumeration<InetAddress> ips = ni.getInetAddresses();                
				while (ips.hasMoreElements()) {                    
					System.out.println("IP:"+ ips.nextElement().getHostAddress());                
					}            
				}        
			} catch (Exception e) {
				e.printStackTrace();       
				}*/
		
		 /*Enumeration netInterfaces = null;        
		 InetAddress   ip   =   null;
		 try {            
			 netInterfaces = NetworkInterface.getNetworkInterfaces();            
			 while (netInterfaces.hasMoreElements()) {                
				 NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				 System.out.println("DisplayName:" + ni.getDisplayName());
				 System.out.println("Name:" + ni.getName());
				 Enumeration ips = ni.getInetAddresses();
				 while (ips.hasMoreElements()) {
					 ip=(InetAddress)ips.nextElement();
					 if(   !ip.isSiteLocalAddress()  
					         &&   !ip.isLoopbackAddress()  
					         &&   ip.getHostAddress().indexOf( ": ")==-1)
					         {
						 System.out.println( "本机的ip= "   +   ip.getHostAddress());
						 break;
					         }
					 System.out.println("IP:"+ (ip)
							 .getHostAddress());                
					 }            
				 }
				 }        
			 } catch (Exception e) {
				 e.printStackTrace();        
				 }    */
		
		
		  try {  
	              for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {  
	                  NetworkInterface item = e.nextElement();  
	    
	                  for (InterfaceAddress address : item.getInterfaceAddresses()) {  
	                      if (address.getAddress() instanceof Inet4Address) {  
	                          Inet4Address inet4Address = (Inet4Address) address.getAddress();  
	                          if(!inet4Address.isLinkLocalAddress()&&
	                        		  !inet4Address.isLoopbackAddress()&&
	                        		  !inet4Address.isMCGlobal()&&
	                        		  !inet4Address.isMulticastAddress()){
	                        	  System.out.println(inet4Address.getHostAddress());  
	                          }
	                      }  
	                  }  
	              }  
	          } catch (IOException ex) {  
	    
	          }   

		 }
}
