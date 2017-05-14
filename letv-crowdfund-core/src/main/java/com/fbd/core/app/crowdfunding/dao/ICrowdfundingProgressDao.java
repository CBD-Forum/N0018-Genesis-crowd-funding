package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹进展
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingProgressDao extends BaseDao<CrowdfundingProgressModel>{
    
    /**
     * 
     * Description: 查询项目进展
     *
     * @param 
     * @return List<CrowdfundingProgressModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 下午3:10:02
     */
    public List<Map<String,Object>> getList(CrowdfundingProgressModel model);
    
    
    /**
     * 查询数量
     * Description: 
     *
     * @param 
     * @return long
     * Create Date: 2016-6-22 下午2:32:49
     */
    public long selectCount(CrowdfundingProgressModel model);
    
    /**
     * 
     * Description: 保存项目进展
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-13 下午5:21:13
     */
    public void saveProgress(String nodeCode,String loanNo,Map<String,String> params);
    
    /**
     * 
     * Description: 根据node查询进展是否插入
     *
     * @param 
     * @return List<CrowdfundingProgressModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 下午3:10:02
     */
    public boolean isNeedAdd(String loanNo,String node);
}