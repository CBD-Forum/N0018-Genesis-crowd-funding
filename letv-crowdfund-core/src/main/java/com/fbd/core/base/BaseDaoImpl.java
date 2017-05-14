package com.fbd.core.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.mapping.ResultMapping;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.fbd.core.common.model.SearchResult;

@Repository("baseDao")
@SuppressWarnings("all")
public class BaseDaoImpl<T> implements BaseDao<T> {

    @Resource
    protected SqlSessionTemplate sqlSession;
    private Class entityClass;// 保存对应实体类的类型

    private String sqlName;// mapper文件的namespace

    /**
     * 获取对应的实体类类名
     */
    @SuppressWarnings("rawtypes")
    public BaseDaoImpl() {// 获取对应的实体类类名
        super();
        this.entityClass = null;
        Class c = getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) t).getActualTypeArguments();
            this.entityClass = (Class) p[0];
        }
        this.sqlName = entityClass.getName() + "Mapper";
    }
    
    
    /**
     * Description: 根据Id获取ResultMapping
     *
     * @param 
     * @return List<ResultMapping>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-15 下午2:29:24
     */
    public List<ResultMapping> getResultMapping(String resultMapId){
        return this.sqlSession.getConfiguration().getResultMap(this.sqlName + ".BaseResultMap").getResultMappings();
    }

    /**
     * 
     * Description: 插入
     * 
     * @param
     * @return Integer
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:13
     */
    public int save(T obj) {
        return this.sqlSession.insert(this.sqlName + ".insert", obj);
    }

    /**
     * 
     * Description: 批量插入
     * 
     * @param
     * @return Integer
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:13
     */
    public int saveBatch(List<T> aList) {
        return this.sqlSession.insert(this.sqlName + ".insertbatch", aList);
    }

    /**
     * 
     * Description: 删除
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午5:40:53
     */
    public int deleteByPrimaryKey(String id) {
        return this.sqlSession.delete(this.sqlName + ".deleteByPrimaryKey", id);
    }

    /**
     * Description:批量删除
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-23 下午1:14:54
     */
    public int deleteBatchById(String id) {
        return this.sqlSession.delete(this.sqlName + ".deleteBatchById",
                id.split(","));
    }
    
    /**
     * Description:根据某个字段删除
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-23 下午1:14:54
     */
    public int deleteByField(String statementId,Object field) {
        return this.sqlSession.delete(this.sqlName + "." + statementId,
                field);
    }


    /**
     * 
     * Description: 有选择的插入
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:23
     */
    public int saveSelective(T obj) {
        return this.sqlSession.insert(this.sqlName + ".insertSelective", obj);
    }

    /**
     * 
     * Description: 根据主键查询
     * 
     * @param
     * @return T
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:33
     */
    public T selectByPrimaryKey(String id) {
        return this.sqlSession.selectOne(this.sqlName + ".selectByPrimaryKey",
                id);
    }

    /**
     * 
     * Description: 根据某个字段查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:00:57
     */
    public List<T> selectByField(String statementId, Object fieldValue) {
        return this.sqlSession.selectList(this.sqlName + "." + statementId,
                fieldValue);
    }

    /**
     * 
     * Description: 根据某个字段查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:00:57
     */
    public T selectOneByField(String statementId, Object fieldValue) {
        return this.sqlSession.selectOne(this.sqlName + "." + statementId,
                fieldValue);
    }
    
    /**
    * 
    * Description: 根据某个字段查询
    * 
    * @param
    * @return Map<String,Object>
    * @throws
    * @Author haolingfeng Create Date: 2014-12-12 下午7:00:57
    */
   public Map<String,Object> selectOneMapByField(String statementId, Object fieldValue) {
       return this.sqlSession.selectOne(this.sqlName + "." + statementId,fieldValue);
   }

    /**
     * 
     * Description: 无条件查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:01:15
     */
    public List<T> select(String statementId) {
        return this.sqlSession
                .selectList(this.sqlName + "." + statementId);
    }
    
    /**
     * 
     * Description: 根据实体查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:01:15
     */
    public List<T> selectByModel(String statementId, T obj) {
        return this.sqlSession
                .selectList(this.sqlName + "." + statementId, obj);
    }

    /**
     * 
     * Description: 根据map查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:01:28
     */
    public List<T> selectByFields(String statementId, Map<String, Object> amap) {
        return this.sqlSession.selectList(this.sqlName + "." + statementId,
                amap);
    }

    /**
     * 
     * Description:根据map查询，返回map
     * 
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:02:48
     */
    public List<Map<String, Object>> selectMapByFields(String statementId,
            Map<String, Object> amap) {
        return this.sqlSession.selectList(this.sqlName + "." + statementId,
                amap);
    }
    
    /**
     * 
     * Description:根据实体查询，返回map
     * 
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:02:48
     */
    public List<Map<String, Object>> selectMapByFields(String statementId,Object obj) {
        return this.sqlSession.selectList(this.sqlName + "." + statementId,obj);
    }

    /**
     * 
     * Description:根据主键ID更新，实体对象中那些属性不为空则更新，为空的属性不更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:54
     */
    public int updateBySelective(T obj) {
        return this.sqlSession.update(this.sqlName
                + ".updateByPrimaryKeySelective", obj);
    }

    /**
     * 
     * Description: 根据主键ID更新，更新所有的字段
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:52:22
     */
    public int update(T obj) {
        return this.sqlSession
                .update(this.sqlName + ".updateByPrimaryKey", obj);
    }

    /**
     * 
     * Description: 执行指定的sql进行更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2015-1-13 上午9:41:11
     */
    public int update(String statementId, Object obj) {
        return this.sqlSession.update(this.sqlName + "." + statementId, obj);
    }

    /**
     * Description: 获取记录条数
     * 
     * @param
     * @return long
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午5:28:21
     */
    public long getCount(T obj) {
        Object count = sqlSession.selectOne(this.sqlName + ".getCount", obj);
        long result = count == null ? 0 : Long.parseLong(count.toString());
        return result;
    }
    
    /**
     * Description: 获取记录条数
     * 
     * @param
     * @return long
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午5:28:21
     */
    public long getCount(String sqlId,String param) {
        Object count = sqlSession.selectOne(this.sqlName + "."+sqlId, param);
        long result = count == null ? 0 : Long.parseLong(count.toString());
        return result;
    }

    /**
     * Description: 获取记录条数
     * 
     * @param
     * @return long
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午5:28:21
     */
    public long getCount(String statementId, Object obj) {
        Object count = sqlSession.selectOne(this.sqlName + "." + statementId,
                obj);
        long result = count == null ? 0 : Long.parseLong(count.toString());
        return result;
    }

    /**
     * Description: 分页查询列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午3:39:48
     */
    public SearchResult<Map<String, Object>> getPage(String countSqlStatement,
            String mainSqlStatement,Map<String, Object> param) {
        SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
        result.setTotal(getCount(countSqlStatement,param));
        result.setRows(this.selectMapByFields(mainSqlStatement,param));
        return result;
    }

    /**
     * 
     * Description: 统计金额
     * 
     * @param
     * @return double
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 上午11:24:47
     */
    public double getsumAmt(String statementId, Map<String, Object> amap) {
        Object sumAmt = sqlSession.selectOne(this.sqlName + "." + statementId,
                amap);
        double result = sumAmt == null ? 0 : Double.parseDouble(sumAmt
                .toString());
        return result;
    }

    /**
     * 
     * Description: 统计金额
     * 
     * @param
     * @return double
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 上午11:24:47
     */
    public double getsumAmt(String statementId, T obj) {
        Object sumAmt = sqlSession.selectOne(this.sqlName + "." + statementId,
                obj);
        double result = sumAmt == null ? 0 : Double.parseDouble(sumAmt
                .toString());
        return result;
    }
}
