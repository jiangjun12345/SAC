package net.easipay.cbp;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getMethod();
	}
	
	public static void getMethod(){
		System.out.println(Thread.currentThread().getStackTrace()[2].getMethodName());
	}

}
