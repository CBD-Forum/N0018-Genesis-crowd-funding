/**
 * 
 */
package com.fbd.core.app.letvPay.utils;

import java.math.BigDecimal;

/**
 * @author liuliangliang
 *
 */
public class XdaiTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new BigDecimal("119.22"));
	}
	
//	public static Money convertMoney(String moneyStr) throws CommonException {
//		Money result = new Money();
//		if (StringUtil.isEmpty(moneyStr)) {
//			return result;
//		} else {
//			try {
//				result = new Money(moneyStr);
//			} catch (NumberFormatException e) {
//				// 金额格式错误，有字母等
//				throw CommonDefinedException.ILLEGAL_AMOUNT_FORMAT;
//			}
//			return result;
//		}
//	}

}
