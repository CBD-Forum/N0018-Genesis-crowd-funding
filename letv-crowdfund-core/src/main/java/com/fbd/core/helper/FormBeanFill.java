package com.fbd.core.helper;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.StringConverter;

public class FormBeanFill {
	static {
		DateConverter d = new DateConverter(null);
		String[] datePattern = { "yyyy-mm-dd", "yyyy/mm/dd", "yyyy.mm.dd", "yyyy-mm-dd HH:mm:ss" };
		d.setPatterns(datePattern);
		ConvertUtils.register(d, java.util.Date.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);  
        ConvertUtils.register(new FloatConverter(null), Float.class);  
        ConvertUtils.register(new DoubleConverter(null), Double.class);  
        ConvertUtils.register(new StringConverter(),String.class);  
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);  

		
//		ConvertUtils.register(d, java.lang.String.class);

	}

	public static void populate(HttpServletRequest request, Object obj) {
		try {
			Map<String, String[]> map = request.getParameterMap();
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getProperty(Object bean, String name) throws Exception {
		Object obj = PropertyUtils.getProperty(bean, name);
		if (obj == null) {
			return "";
		} else {
			return obj;
		}
	}

}
