package net.easipay.cbp.util;

public class CurrencyUtil {
	
	public static String  EnglishToNumber(String currency){
		String ccy = "";
		if("CNY".equals(currency)){
			ccy="01";
		}else if("GBP".equals(currency)){
			ccy="12";
		}else if("HKD".equals(currency)){
			ccy="13";
		}else if("USD".equals(currency)){
			ccy="14";
		}else if("CHF".equals(currency)){
			ccy="15";
		}else if("SGD".equals(currency)){
			ccy="18";
		}else if("JPY".equals(currency)){
			ccy="27";
		}else if("CAD".equals(currency)){
			ccy="28";
		}else if("AUD".equals(currency)){
			ccy="29";
		}else if("EUR".equals(currency)){
			ccy="38";
		}
		return ccy;
		
	}
	
	public static String  NumberToEnglish(String currency){
		String ccy = "";
		if("01".equals(currency)){
			ccy="CNY";
		}else if("12".equals(currency)){
			ccy="GBP";
		}else if("13".equals(currency)){
			ccy="HKD";
		}else if("14".equals(currency)){
			ccy="USD";
		}else if("15".equals(currency)){
			ccy="CHF";
		}else if("18".equals(currency)){
			ccy="SGD";
		}else if("27".equals(currency)){
			ccy="JPY";
		}else if("28".equals(currency)){
			ccy="CAD";
		}else if("29".equals(currency)){
			ccy="AUD";
		}else if("38".equals(currency)){
			ccy="EUR";
		}
		return ccy;
		
	}

}
