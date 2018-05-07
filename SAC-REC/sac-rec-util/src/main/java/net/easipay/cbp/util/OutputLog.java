package net.easipay.cbp.util;

public class OutputLog 
{
	  static String fixMsg = "\n ############################################################################# \n";
      public static String formatLog(String outputMsg)
      {
    	  return fixMsg + outputMsg + fixMsg;
      }
}
