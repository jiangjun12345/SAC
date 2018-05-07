package net.easipay.cbp.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TcNumberUtils {

	/**
	 * Get rounding decimal according to conversion factor from currency. i.e:
	 * USD = 100 conversion factor (1 USD = 100 cents). In the printout, USD
	 * will have 2 decimal point
	 * 
	 * @param convFact
	 * @return
	 */
	public static int getDecimalRounding(BigDecimal convFact) {
		BigDecimal decimal = TcNvl.nvl(convFact);
		int round = 0;
		while (decimal.compareTo(BigDecimal.TEN) >= 0) {
			round++;
			decimal = decimal.divide(BigDecimal.TEN, 0,
					BigDecimal.ROUND_HALF_UP);
		}
		return round;
	}

	/**
	 * Rounding mode to round towards "nearest neighbor"
	 * 
	 * @param value
	 * @param precision
	 * @return
	 * @see #round(BigDecimal, int, RoundingMode)
	 */
	public static BigDecimal round(BigDecimal value, int precision) {
		return round(value, precision, RoundingMode.HALF_UP);
	}

	/**
	 * Round decimal to the nearest precision
	 * 
	 * @param value
	 * @param precision
	 * @param mode
	 * @return
	 */
	public static BigDecimal round(BigDecimal value, int precision,
			RoundingMode mode) {
		return TcNvl.nvl(value).setScale(precision, mode);
	}
}