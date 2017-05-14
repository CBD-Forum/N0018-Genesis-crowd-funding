package com.fbd.core.base;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.fbd.core.helper.FormBeanFill;

public class BasePo extends SqlSessionDaoSupport {

	/**
	 * 保存功能
	 * 
	 * @param tablename表名
	 * @param filedname主键字段名
	 * @param fieldvalue主键值
	 * @throws Exception
	 */
	public int save(String tableName, Object obj) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			List<Map<String, Object>> filedMap = new ArrayList<Map<String, Object>>();

			for (int i = 0, length = fields.length; i < length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("filedname", fields[i].getName());
				map.put("fieldvalue", FormBeanFill.getProperty(obj, fields[i].getName()));
				filedMap.add(map);
			}
			parameter.put("tableName", tableName);
			parameter.put("filedMap", filedMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getSqlSession().insert("save", parameter);

	}

	/**
	 * 修改功能
	 * 
	 * @param tablename表名
	 * @param filedname主键字段名
	 * @param fieldvalue主键值
	 * @throws Exception
	 */
	public int update(String tableName, Object obj, String filedname) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			List<Map<String, Object>> filedMap = new ArrayList<Map<String, Object>>();

			for (int i = 0, length = fields.length; i < length; i++) {
				if (!fields[i].getName().equals(filedname)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("filedname", fields[i].getName());
					map.put("fieldvalue", FormBeanFill.getProperty(obj, fields[i].getName()));
					filedMap.add(map);
				}
			}
			parameter.put("tableName", tableName);
			parameter.put("filedMap", filedMap);
			parameter.put("pkId", filedname);
			parameter.put("pkIdValue", FormBeanFill.getProperty(obj, filedname));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getSqlSession().update("update", parameter);
	}

	public Object[] searchPage(String tableName, Map<String, Object> parameter1,Map<String, Object> parameter2) {
		Object[] returnObj = new Object[2];
		Map<String, Object> parameter=new HashMap<String, Object>();
		parameter.put("tableName", tableName);
		parameter.put("pageStr", parameter1);
		List<Map<String, Object>> whereStr = new ArrayList<Map<String, Object>>();
		if (parameter2!=null) {
			for (Iterator iterator = parameter2.keySet().iterator(); iterator.hasNext();) {
				Map<String, Object> map = new HashMap<String, Object>();
				String key =  (String) iterator.next();
				map.put("filedname", key);
				map.put("fieldvalue", parameter2.get(key));
				whereStr.add(map);
			}
		}
		parameter.put("whereStr", whereStr);
		List<Map<String, Object>> returnList = this.getSqlSession().selectList("searchPage", parameter);
		returnObj[0] = returnList;
		try {
			if (returnList.get(0).get("totalcount") instanceof BigDecimal) {
				returnObj[1] = ((BigDecimal) returnList.get(0).get("totalcount")).intValue();
			} else {
				returnObj[1] = returnList.get(0).get("totalcount");
			}
		} catch (Exception e) {
			returnObj[1] = 0;
		}

		return returnObj;
	}

	/**
	 * 删除功能
	 * 
	 * @param tablename表名
	 * @param filedname主键字段名
	 * @param fieldvalue主键值
	 * @throws Exception
	 */
	public int deleteByFiled(String tableName, String filedname, String fieldvalue) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("tableName", tableName);
		parameter.put("filedname", filedname);
		parameter.put("fieldvalue", fieldvalue.split(","));
		return this.getSqlSession().delete("delete", parameter);
	}
}
