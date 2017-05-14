package com.fbd.core.app.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.SortParameters.Order;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import com.fbd.core.app.user.model.UserModel;

public class UserDaoImplTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "classpath*:/fbd/core/spring/applicationContext-config.xml");
        RedisTemplate<String, String> redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");

//        HashOperations hashOper = redisTemplate.opsForHash();
//        HashMap map = new LinkedHashMap();
//        
//        UserModel user = new UserModel();
//        user.setUserId("zhangsan");
//        user.setRealName("张三");
//        user.setId("100");
//        
//        UserModel user1 = new UserModel();
//        user1.setUserId("lisi");
//        user1.setRealName("李四");
//        user1.setId("101");
//        
//        map.put("uid:100:zhangsan", user);
//        map.put("uid:101:lisi", user1);
//        hashOper.putAll("user", map);
//        
//        //test-user-1中数据 数据结构为key=test-user-1 value=pid (为*) ；#代表是查询 test-user-1 的value  test-map-*->uid代表  test-map-*map下的key为uid的值
////        SortQuery<String> query = SortQueryBuilder.sort("test-user-1").noSort().get("#").get("test-map-*->uid").get("test-map-*->content").build();
//        
//        
//        SortQuery<String> query = SortQueryBuilder.sort("uid:*").limit(0, 1).build();
//        
//        
//        BulkMapper<UserModel, String> hm = new BulkMapper<T, String>() {  
//            public UserModel mapBulk(List<String> bulk) {  
//                return bulk.get(0);//转型 将map转换成实体  
//            }  
//        };
//        redisTemplate.sort(query,hm);
//        
//        for (Object obj : hashOper.keys("user")) {
//            System.out.println(obj);
//        }
        
        
//        for (int i = 0; i < hashOper.size("user"); i++) {
//            UserModel temp = (UserModel) hashOper.get("user", "uid:101:lisi"); 
//            System.out.println(temp.getUserId());
//        }
        
        
        ListOperations<String, String> listOper = redisTemplate.opsForList();  
        String listKey = "user:list";  
        redisTemplate.delete(listKey);
        
        listOper.leftPush(listKey, "a");  
        listOper.leftPush(listKey, "c");  
        listOper.leftPush(listKey, "b");
//        List<String> results = listOper.range(listKey, 0, -1);
        SortQueryBuilder<String> builder = SortQueryBuilder.sort(listKey);  
        builder.alphabetical(true);//对字符串使用“字典顺序”
        builder.order(Order.DESC);//倒序  
//        builder.limit(0, 2);  
        //builder.limit(new Range(0, 2));  
        List<String> results = redisTemplate.sort(builder.build());  
        for(String item : results){  
            System.out.println(item);  
        } 
        
    }
    
    
}
