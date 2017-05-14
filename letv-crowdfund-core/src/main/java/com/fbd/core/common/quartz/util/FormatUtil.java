package com.fbd.core.common.quartz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

    public static final String DATE_FORMAT_PATTERN = "yy.MM.dd hh:mm";
    static SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    /**
    *
    * @param date
    * @return
    */
   public static String getDateAsString(Date date)
   {
       if (date==null)
       {
           return null;
       }
       return dateFormatter.format(date);
   }

   /**
    *
    * @param dateStr
    * @return
    * @throws ParseException
    */
   public static Date parseStringToDate(String dateStr) throws ParseException
   {
       if (dateStr==null)
       {
           return null;
       }
       return dateFormatter.parse(dateStr);
   }
    
}
