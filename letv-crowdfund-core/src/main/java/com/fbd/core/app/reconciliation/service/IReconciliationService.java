package com.fbd.core.app.reconciliation.service;

import java.util.Map;
import com.fbd.core.app.reconciliation.model.ReconciliationRechargeModel;
import com.fbd.core.common.model.SearchResult;

public interface IReconciliationService {
    
    
    /**
     * Description: 保存充值对账
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-12 上午11:15:24
     */
    public int saveTrf();
    
    /**
     * Description: 保存充值对账
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-12 上午11:15:24
     */
    public int saveRecharge();
    
    
 
  

    /**
     * Description: 分页查询充值对账的结果
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-13 上午11:27:14
     */
    public SearchResult<Map<String, Object>> getRechargePage(Map<String, Object> param);

 
    /**
     * Description: 分页查询还款对账的结果
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-13 下午12:44:19
     */
    public SearchResult<Map<String, Object>> getRepayPage(Map<String, Object> param);


    /**
     * Description: 分页查询自动对账的调度记录
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-13 下午5:24:23
     */
    public SearchResult<Map<String, Object>> getRecordPage(Map<String, Object> param);

}
