package com.fbd.core.base;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.mapping.ResultMapping;
import com.fbd.core.common.model.SearchResult;

public interface BaseDao<T> {
    
    /**
     * Description: 根据Id获取ResultMapping
     *
     * @param 
     * @return List<ResultMapping>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-15 下午2:29:24
     */
    public List<ResultMapping> getResultMapping(String resultMapId);

    /**
     * 
     * Description: 插入
     * 
     * @param
     * @return Integer
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:13
     */
    public int save(T obj);

    /**
     * 
     * Description: 批量插入
     * 
     * @param
     * @return Integer
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:13
     */
    public int saveBatch(List<T> aList);

    /**
     * 
     * Description: 删除
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午5:40:53
     */
    public int deleteByPrimaryKey(String id);
    
    /**
     * Description:根据某个字段删除
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-23 下午1:14:54
     */
    public int deleteByField(String statementId,Object field);

    /**
     * 
     * Description: 有选择的插入
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:23
     */
    public int saveSelective(T obj);

    /**
     * 
     * Description: 根据主键查询
     * 
     * @param
     * @return T
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:33
     */
    public T selectByPrimaryKey(String id);

    /**
     * 
     * Description: 根据某个字段查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:00:57
     */
    public List<T> selectByField(String statementId, Object fieldValue);

    public T selectOneByField(String statementId, Object fieldValue);
    
    public Map<String, Object> selectOneMapByField(String statementId, Object fieldValue);

    /**
     * 
     * Description: 根据实体查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:01:15
     */
    public List<T> selectByModel(String statementId, T obj);

    /**
     * 
     * Description: 根据map查询
     * 
     * @param
     * @return List<T>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午7:01:28
     */
    public List<T> selectByFields(String statementId, Map<String, Object> amap);

    /**
     * 
     * Description:根据map查询，返回map
     * 
     * @param
     * @return List<Map<String,Object>>
     * @throws
     * @Author haolingfeng 
     * Create Date: 2014-12-12 下午7:02:48
     */
    public List<Map<String, Object>> selectMapByFields(String statementId, Object amap);

    /**
     * 
     * Description:有选择的更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:51:54
     */
    public int updateBySelective(T obj);

    /**
     * 
     * Description: 更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2014-12-12 下午6:52:22
     */
    public int update(T obj);

    /**
     * 
     * Description: 执行指定的sql进行更新
     * 
     * @param
     * @return int
     * @throws
     * @Author haolingfeng Create Date: 2015-1-13 上午9:41:11
     */
    public int update(String statementId, Object obj);

    public long getCount(T obj);

    /**
     * Description: 获取记录条数
     * 
     * @param
     * @return long
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午5:28:21
     */
    public long getCount(String statementId, Object obj);

    
    /**
     * Description: 分页查询
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-12 上午9:05:44
     */
    public SearchResult<Map<String, Object>> getPage(String countSqlStatement,
            String mainSqlStatement,Map<String, Object> param);
    /**
     * 
     * Description: 统计金额
     * 
     * @param
     * @return double
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 上午11:24:47
     */
    public double getsumAmt(String statementId, Map<String, Object> amap);
    
    
}
