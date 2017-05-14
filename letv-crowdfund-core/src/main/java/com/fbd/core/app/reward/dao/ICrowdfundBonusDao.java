package com.fbd.core.app.reward.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundBonusDao extends BaseDao<CrowdfundBonusModel>{

    /**
     * Description: 查询分红项目待审核列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-23 下午5:49:58
     */
    
    List<Map<String, Object>> getLoanBonusList(CrowdfundBonusModel model);

    /**
     * Description: 统计分红项目待审核列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-23 下午5:50:07
     */
    
    long getLoanBonusCount(CrowdfundBonusModel model);

    /**
     * Description: 删除分红项目   和分红项目明细
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 下午4:01:21
     */
    
    void deleteByLoanBonusId(String loanBonusId);
    /**
     * 收到投资分红、兑付款项
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:44:05
     */
    public List<Map<String,Object>>pushReciveBonus(Map<String,Object>params);
    
    /**
     * 查询分红次数
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-11-8 下午2:16:01
     */
    public long selectLastNum(String loanNo);

}