package com.fbd.core.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.JavaType;

public class JsonUtil {

	public static String parseObject(final Object object) {
		ObjectMapper om=new ObjectMapper();
		String ret=null;
		try {
			ret=om.writeValueAsString(object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return ret;
	}
	public static String parseObject(final Object object, final String filterob,final String[] specifypro){
		ObjectMapper om = new ObjectMapper();
		SimpleFilterProvider filter = new SimpleFilterProvider().setDefaultFilter(null);
//		SimpleFilterProvider filter = new SimpleFilterProvider().setFailOnUnknownId(false);
		filter.addFilter(filterob, SimpleBeanPropertyFilter.filterOutAllExcept(specifypro));
		om.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		om.setFilters(filter);
		String ret=null;
		try {
			ret=om.writeValueAsString(object);
		} catch (Exception e) {
		} 
		return ret;
	}
	public static String parseObject(Object object,Map<String, String[]> filterMap,String dateFormat){
		
		ObjectMapper om = new ObjectMapper();
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		om.setDateFormat(format);
//		SimpleFilterProvider filter = new SimpleFilterProvider().setFailOnUnknownId(false);
		SimpleFilterProvider filter = new SimpleFilterProvider().setDefaultFilter(null);
		for (Map.Entry<String, String[]> mapItem : filterMap.entrySet()) {
			filter.addFilter(mapItem.getKey(), SimpleBeanPropertyFilter.filterOutAllExcept(mapItem.getValue()));
		}
		om.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		om.setFilters(filter);
		String ret=null;
		try {
			ret=om.writeValueAsString(object);
		} catch (Exception e) {
		} 
		return ret;
	}
	public static String parseObject(Object object,Map<String, String[]> filterMap){
		ObjectMapper om = new ObjectMapper();
//		SimpleFilterProvider filter = new SimpleFilterProvider().setFailOnUnknownId(false);
		SimpleFilterProvider filter = new SimpleFilterProvider().setDefaultFilter(null);
		for (Map.Entry<String, String[]> mapItem : filterMap.entrySet()) {
			filter.addFilter(mapItem.getKey(), SimpleBeanPropertyFilter.filterOutAllExcept(mapItem.getValue()));
		}
		om.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		om.setFilters(filter);
		String ret=null;
		try {
			ret=om.writeValueAsString(object);
		} catch (Exception e) {
		} 
		return ret;
	}
	
	public static  Object readJson2Entity(String jsonstr,Class<?> oclass) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper om = new ObjectMapper();
		Object acc = om.readValue(jsonstr, oclass);
		return acc;
	}
	
	public static  List<?> paseJson2Collection(String string,Class<?> x) throws Exception{  
    	ObjectMapper mapper = new ObjectMapper(); 
        JavaType javaType = getCollectionType(ArrayList.class, x); 
        List<Class<?>> list =  (List<Class<?>>)mapper.readValue(string, javaType); 
        return list;
    }   
	
	/**
	 * @author ldl
	 * 把json字符串转化为Map对象
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public static Map paseJson2Map(String string) throws Exception{
		try {
			ObjectMapper mapper = new ObjectMapper(); 
			return mapper.readValue(string, Map.class);
		} catch (Exception e) {
			throw new  Exception(e.getMessage());
		}
	}
       /**   
        * 获取泛型的Collection Type  
        * @param collectionClass 泛型的Collection   
        * @param elementClasses 元素类   
        * @return JavaType Java类型   
        * @since 1.0   
        */   
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
    	ObjectMapper mapper = new ObjectMapper(); 
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    }
    
    
    public static void main(String[] args) throws Exception {
//		String str = "{\"address\":\"北京市海淀区\",\"createtime\":null,\"defaultSort\":\"\",\"description\":\"买东西了,申请贷款\",\"detail\":[{\"applyid\":\"6494d992425145048d1865d3ec8188b2\",\"counts\":10,\"createtime\":null,\"defaultSort\":\"\",\"endPage\":0,\"id\":\"3dba3628dbab4fc992b867d26d7a23d2\",\"name\":\"泡泡糖\",\"order\":\"\",\"page\":1,\"paramMap\":{\"sort\":null,\"order\":null,\"endPage\":0,\"startPage\":0},\"rows\":0,\"sort\":\"\",\"startPage\":0,\"unitprice\":0.25,\"updatetime\":null},{\"applyid\":\"6494d992425145048d1865d3ec8188b2\",\"counts\":10,\"createtime\":null,\"defaultSort\":\"\",\"endPage\":0,\"name\":\"方便面\",\"order\":\"\",\"page\":1,\"paramMap\":{\"sort\":null,\"order\":null,\"endPage\":0,\"startPage\":0},\"rows\":0,\"sort\":\"\",\"startPage\":0,\"unitprice\":1.5,\"updatetime\":null}],\"endPage\":0,\"id\":\"6494d992425145048d1865d3ec8188b2\",\"money\":500,\"order\":\"\",\"page\":1,\"paramMap\":{\"sort\":null,\"order\":null,\"endPage\":0,\"startPage\":0},\"rows\":0,\"sort\":\"\",\"startPage\":0,\"status\":\"01\",\"typeid\":\"00a3be386c3548c29bcbf1cdea19d4a2\",\"updatetime\":null,\"userid\":\"b0090cd61d96455cbd6c0208709077a0\"}";
//	
//    
//		ApplyLoanInfoModel applyloaninfo1 = (ApplyLoanInfoModel)JsonUtil.readJson2Entity(str, ApplyLoanInfoModel.class);
//    
//        System.out.println(applyloaninfo1.getDetail());
//        
    	
    	String aa = "202002";
    	System.out.println();
    }

}
