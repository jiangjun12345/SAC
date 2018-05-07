package net.easipay.cbp.view.controller;

public class testThread extends Thread{
	
	 public void run() {
		 for (int i = 0; i < 10000; i++) {
	           System.out.println("我是线程"+this.getId());
	       }
	}
	 
	 public static void main(String[] args) {
		 testThread th1 = new testThread();
		 testThread th2 = new testThread();
	       th1.start();
	       th2.start();
	   }
	 
	 
	 

}
