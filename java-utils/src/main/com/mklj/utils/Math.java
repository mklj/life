package com.mklj.utils;

/**
 * Created with IntelliJ IDEA.
 * User: michael
 * Date: 30/07/13
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class Math {

	/**
	 * Modulus operation giving a result with same sign as the divisor.
	 * The default Java definition of the modulus operation yields a result
	 * with same sign as the dividend.
	 * @param dividend - dividend (numerator)
	 * @param divisor - divisor
	 * @return the result of the modulus operation with same sign as the
	 * divisor
	 */
	public static int divisorSignModulo(int dividend, int divisor) {
		int r = dividend % divisor;
		if (dividend < 0)
			r += divisor;
		return r;
	}

}
