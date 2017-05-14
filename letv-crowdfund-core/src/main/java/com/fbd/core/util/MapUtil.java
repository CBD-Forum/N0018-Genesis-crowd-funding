package com.fbd.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

/**
 * Company: jdp2p <br/>
 * Copyright: Copyright (c)2013 <br/>
 * Description: java.util.Map 与之相关的工具类
 * 
 * @author: wangzhi
 * @version: 1.0 Create at: 2014-4-3 下午4:34:51
 * 
 *           Modification History: <br/>
 *           Date Author Version Description
 *           ------------------------------------------------------------------
 *           2014-4-3 wangzhi 1.0
 */
public class MapUtil {

    /**
     * json字符串变为hashMap
     * 
     * @param str
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Map<String, String> jsonStringToHashMap(String str)
            throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(str, HashMap.class);
    }

    /**
     * hashMap字符串变为json
     * 
     * @param str
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static String hashMapToJsonString(Map<String, String> map) {
        return new JSONObject(map).toString();
    }

    /**
     * 字符串变为map，字符串格式 key1=value1, key2=value2...
     * 
     * @param str
     *            HashMap<String,String> toString()以后产生的字符串
     * @return
     * @deprecated
     * @see jsonStringToHashMap
     */
    @Deprecated
    public static Map<String, String> stringToHashMap(String str) {
        Map<String, String> map = new HashMap<String, String>();
        for (final String entry : str.split(",")) {
            final String key = entry.substring(0, entry.indexOf('=')).trim();
            final String value = entry.substring(entry.indexOf('=') + 1,
                    entry.length()).trim();
            map.put(key, value);
        }
        return map;
    }

    /**
     * HashMap 转换为 String
     * 
     * @param map
     * @return
     * @deprecated
     * @see
     */
    @Deprecated
    public static String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append(',').append(' ');
            }
        }
        return sb.toString();
    }
    public static String mapToString2(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, String>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            if (iter.hasNext()) {
                sb.append('&');
            }
        }
        return sb.toString();
    }

}
