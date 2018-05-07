package net.easipay.cbp;

public class Adaptee {
	public void specificRequest() {  
        System.out.println("被适配类具有 特殊功能...");  
    }  
	
	 public static void main(String[] args) {  
	        // 使用普通功能类  
	        Target concreteTarget = new ConcreteTarget();  
	        concreteTarget.request();  
	          
	        // 使用特殊功能类，即适配类  
	        Target adapter = new Adapter();  
	        adapter.request();  
	    }  
}
