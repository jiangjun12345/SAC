package net.easipay.cbp.util;

public class DigitalConversion
{
    /**
     * 将字母转换成数字 A-1 B-2 C-3... W-25 Z-26 例如: ABC = 1*26*26+2*26+3
     * 
     * @param input
     * @return
     */
    public static String letterToNum(String input)
    {
	input = input.toUpperCase();
	char[] charArray = input.toCharArray();
	long l = 0;
	for (int i = 0; i < charArray.length; i++) {
	    l += (charArray[i] - 64) * Math.pow(26, charArray.length - i - 1);
	}
	return String.valueOf(l);
    }
}
