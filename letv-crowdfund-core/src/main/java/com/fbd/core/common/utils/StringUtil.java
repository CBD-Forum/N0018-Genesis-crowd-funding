package com.fbd.core.common.utils;

public class StringUtil {

	/**
	 * Case insensitive && string trimmed match
	 * 
	 * @param string1
	 * @param string2
	 * @return true if strings seem to have
	 */
	public static boolean isSameTextValue(String string1, String string2) {

		if (string1 == null || string2 == null) {
			if (string1 == null && string2 == null)
				return true;
			else
				return false;
		}

		if (string1.trim().equalsIgnoreCase(string2.trim())) {
			return true;
		}

		return false;
	}

	public static boolean isEmpty(String s) {
		boolean flg = true;
		if (s != null && s.trim().length() > 0) {
			return false;
		}

		return flg;
	}
	
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

}
