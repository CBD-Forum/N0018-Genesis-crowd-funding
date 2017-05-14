/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingProgressFilesDaoImpl.java 
 *
 * Created: [2016-6-21 下午5:17:12] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: crowdfund-sina-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressFilesDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressFilesModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

 

@Repository(value="crowdfundingProgressFilesDao")
public class CrowdfundingProgressFilesDaoImpl extends BaseDaoImpl<CrowdfundingProgressFilesModel> implements
        ICrowdfundingProgressFilesDao {
    
    
    
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
    public void deleteByParentId(String parentId){
        this.deleteByField("deleteByParentId",parentId);
    }
    
    
    
    public List<CrowdfundingProgressFilesModel> selectList(CrowdfundingProgressFilesModel model){
        
        return this.selectByField("selectList", model);
    }
 

}
