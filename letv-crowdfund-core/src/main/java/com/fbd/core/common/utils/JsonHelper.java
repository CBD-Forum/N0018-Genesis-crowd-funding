package com.fbd.core.common.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
 
/**
 * 类功能说明 :使用 net的json进行的解析操作
 * 
 * 1、实现java对象转换为json字符串
 * 
 * 
 * 2、实现json字符串转换为单个的java对象
 * 3、实现json字符串转换为java对象数组
 * 4、实现json字符串转换为list集合
 * 5、实现json字符串转换为map对象
 * @Description:
 * @author FWW
 * @date 2014年7月2日 下午7:10:08
 */
public class JsonHelper {
 
    public static void main(String[] args) {
//      
//      User user = new User(1, "123",new Date());
//      User user1 = new User(2, "023",new Date());
//      List<User> list = new ArrayList<User>();
//      list.add(user1);
//      list.add(user);
//      String listStr = getStrFromObject(list);
//      System.out.println(listStr);
//      Object[] o = getObjectArray(listStr, User.class);
//      for (int i = 0; i < o.length; i++) {
//          User usert = (User) o[i];
//          System.out.println(usert.getName());
//      }
    }
     
    /**
     * 函数功能说明: 把一个给定的对象转换成json字符串的格式
     *              如果给定的对象是一个单一的对象的时候，返回的是一个单一对象的字符串的格式
     *              如果给的的对象是collection或者是Object数组的时候，返回的是一个字符串数组格式
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午2:11:45
     * @param object 进行转换的对象
     * @return
     */
    public static String getStrFromObject(Object object){
        //日期值处理器
        String formatString = "yyyy-MM-dd";
        return getStrFromObject(object, formatString);
    }
     
    /**
     * 函数功能说明: 把一个给定的对象转换成json字符串的格式
     *              如果给定的对象是一个单一的对象的时候，返回的是一个单一对象的字符串的格式
     *              如果给的的对象是collection或者是Object数组的时候，返回的是一个字符串数组格式
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午2:38:49
     * @param object 进行转换的对象
     * @param formatString  日期转换的格式
     * @return
     */
    public static String getStrFromObject(Object object,String formatString){
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(formatString));
        String jsonString = null;
        if(null!=object){
            if(object instanceof Collection<?> || object instanceof Object[] ){
                jsonString = JSONArray.fromObject(object, jsonConfig).toString();
            }else{
                jsonString = JSONObject.fromObject(object, jsonConfig).toString();
            }
        }
        return (jsonString==null)?"{}":jsonString;
    }
     
    /**
     * 函数功能说明: 设定日期的转换格式
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午2:50:25
     */
    private static void setDataFormat2JAVA(){ 
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{
                "yyyy-MM-dd","yyyy-MM-dd HH:mm:ss"}));
         
    }
     
    /**
     * 函数功能说明: 把一个指定的jsonString字符串，转换为一个clazz对象
     * 形如{"id" : idValue, "name" : nameValue, "aBean" : {"aBeanId" : aBeanIdValue, ...}} 
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午3:14:31
     * @param jsonString 进行转换的字符串
     * @param clazz  字符串按照类clazz进行转换
     * @return
     */
    public static Object getObjectFromJsonString(String jsonString,Class<?> clazz){
        JSONObject jsonObject = null;
        try {
//            setDataFormat2JAVA();
            jsonObject = JSONObject.fromObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toBean(jsonObject, clazz);
    }
     
    /**
     * 函数功能说明: 从一个JSON 对象字符格式中得到一个java对象，其中dogs,cats表示的各是一类的集合，形如：
     * {"id" : idValue, "name" : nameValue, "cats" : {"catId" : catIdValue, ...},dogs:[{}, {}, ...]} 
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午3:20:31
     * @param jsonString
     * @param clazz
     * @param map 集合属性的类型 (key : 集合属性名, value : 集合属性类型class) 
     *          如：Map map = new HashMap();
     *          map.put("dogs",Dog.class);
     *          map.put("cats",Cat.class);
     * @return
     */
    public static Object getObjectFromJsonString(String jsonString,Class<?> clazz,Map<String, Object> map){
        JSONObject jsonObject = null;
        try {
            setDataFormat2JAVA();
            jsonObject = JSONObject.fromObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toBean(jsonObject, clazz, map);
    }
     
    /**
     * 函数功能说明: 从一个JSON数组得到一个对象数组
     *      形如格式：[{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...]   
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午4:52:23
     * @param jsonString 要进行转换的对象字符串
     * @param clazz 字符串要转换为的对象
     * @return
     */
    public static Object[] getObjectArray(String jsonString,Class<?> clazz){
        setDataFormat2JAVA();     
        JSONArray array = JSONArray.fromObject(jsonString);     
        Object[] obj = new Object[array.size()];     
        for(int i = 0; i < array.size(); i++){     
            JSONObject jsonObject = array.getJSONObject(i);     
            obj[i] = JSONObject.toBean(jsonObject, clazz);     
        }     
        return obj;     
    }
    /**
     * 函数功能说明: 从一个JSON数组得到一个java的对象数组
     * [{"id" : idValue, "name" : nameValue}, {"id" : idValue, "name" : nameValue}, ...] 
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午6:44:06
     * @param jsonString
     * @param clazz
     * @param map 参见方法getObjectFromJsonString(String jsonString,Class<?> clazz,Map<String, Object> map)的map
     * @return
     */
    public static Object[] getObjectArray(String jsonString,Class<?> clazz,Map<String,Object> map){
        setDataFormat2JAVA();     
        JSONArray array = JSONArray.fromObject(jsonString);     
        Object[] obj = new Object[array.size()];     
        for(int i = 0; i < array.size(); i++){     
            JSONObject jsonObject = array.getJSONObject(i);     
            obj[i] = JSONObject.toBean(jsonObject, clazz,map);     
        }     
        return obj;     
    }
     
    /**
     * 函数功能说明:从一个JSON数组得到一个java对象集合 
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午6:51:23
     * @param jsonString
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<?> getObjectList(String jsonString,Class<?> clazz){
//        setDataFormat2JAVA();
        JSONArray array = JSONArray.fromObject(jsonString);
        List list = new ArrayList();
        for (Iterator iterator = array.iterator(); iterator.hasNext();) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            list.add(JSONObject.toBean(jsonObject, clazz));
        }
        return list;
    }
    /**
     * 函数功能说明: 从一个JSON数组得到一个java对象集合，其中对象中包含有集合属性
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午6:53:20
     * @param jsonString
     * @param clazz
     * @param map 参见方法getObjectFromJsonString(String jsonString,Class<?> clazz,Map<String, Object> map)的map
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<?> getObjectList(String jsonString,Class<?> clazz,Map<String,Object> map){
//        setDataFormat2JAVA();
        JSONArray array = JSONArray.fromObject(jsonString);
        List list = new ArrayList();
        for (Iterator iterator = array.iterator(); iterator.hasNext();) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            list.add(JSONObject.toBean(jsonObject, clazz,map));
        }
        return list;
    }
     
     
    /**
     * 函数功能说明: 从json hash表达式中获取一个map，该map支持嵌套功能
     * 形如：{"id" : "johncon", "name" : "小强"}
     * 注意commons-collections版本，必须包含org.apache.commons.collections.map.MultiKeyMap
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午6:57:19
     * @param jsonString
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map getMapFromJson(String jsonString){
        setDataFormat2JAVA();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Map map = new HashMap();
        for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
            String key = (String) iterator.next();
            map.put(key, jsonObject.get(key));
        }
        return map;
    }
     
    /**
     * 函数功能说明: 从json数组中得到相应的java数组
     * json形如：["123", "456"] 
     * 修改者名字:  fuweiwei
     * 修改日期 : 2014年7月3日 下午6:59:55
     * @param jsonString
     * @return
     */
    public static Object[] getObjectArrayFromJson(String jsonString){
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }
}