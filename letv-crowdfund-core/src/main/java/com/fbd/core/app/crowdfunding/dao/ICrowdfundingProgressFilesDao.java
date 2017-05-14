package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressFilesModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundingProgressFilesDao extends BaseDao<CrowdfundingProgressFilesModel>{
    
    
    /**
     * 根据父ID删除资源信息
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-6-21 下午6:17:39
     */
    public void deleteByParentId(String parentId);
    
    
    
    public List<CrowdfundingProgressFilesModel> selectList(CrowdfundingProgressFilesModel model);
    
}