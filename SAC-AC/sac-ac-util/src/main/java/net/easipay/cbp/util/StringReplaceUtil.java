package net.easipay.cbp.util;


public class StringReplaceUtil
{
    public static String replaceAll(String target, String pattern, String... replacement)
    {
	for (int i = 0; i < replacement.length; i++) {
	    target = target.replaceFirst(pattern, replacement[i]);
	}
	return target;
    }
}
