package com.fbd.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
 
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
 
/**
 * 类功能说明 : json对象转换的辅助
 * @Description:
 * @author FWW
 * @date 2014年7月3日 下午7:09:46
 */
public class JsonDateValueProcessor implements JsonValueProcessor{
 
    private String format = "yyyy-MM-dd";
     
     
    public JsonDateValueProcessor() {
        super();
    }
 
     
    public JsonDateValueProcessor(String format) {
        super();
        this.format = format;
    }
 
 
    @Override
    public Object processArrayValue(Object paramObject, JsonConfig paramJsonConfig) {
         
        return process(paramObject);
    }
 
    @Override
    public Object processObjectValue(String paramString, Object paramObject, JsonConfig paramJsonConfig) {
        return process(paramObject);
    }
 
    private Object process(Object value){
        if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);
            return sdf.format(value);
        }
        return (value==null)?"":value.toString();
    }
 
 
    public String getFormat() {
        return format;
    }
 
 
    public void setFormat(String format) {
        this.format = format;
    }
     
     
}