package com.fbd.core.helper;

import java.text.ParseException;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import com.fbd.core.util.DateUtils;
import com.fbd.core.util.TimeUtils;



/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 日期时间转换器
 *
 * @author dongzhongwei
 * @version 1.0
 *
 */

public class DateTimeConverter implements Converter<String, Date> {

    public Date convert(String source) {
        Date result = null;
        if (source == null || source.length() == 0) {
            return null;
        } else if (source.length() == 10) {
            result = DateUtils.stringToDate(source, DateUtils.DEFAULT_DATE_FORMAT);
        } else if (source.length() == 19) {
            try {
                result = TimeUtils.toUtilDateFromStrDateByFormat(source, TimeUtils.DEFAULT_TIME_FORMAT);
            } catch (ParseException e) {
                throw new IllegalArgumentException("string conveter to time error, source is[" + source + "]", e);
            }
        } else {
            throw new IllegalArgumentException("string conveter to time error, unsupport this format, source is[" + source + "]");
        }
        return result;
    }
    
}
