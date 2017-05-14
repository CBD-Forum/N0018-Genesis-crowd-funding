/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundingInvestafterService.java 
 *
 * Created: [2016-8-22 下午1:33:35] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.crowdfunding.service;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundingInvestAfterModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 * @version 1.0
 *
 */

public interface ICrowdfundingInvestAfterService {
    
    
    
    /**
     * 保存投后管理
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-22 下午1:45:08
     */
    public void saveInvestAfter(CrowdfundingInvestAfterModel model);
    
    /**
     * 删除
     * Description: 
     * @param 
     * @return void
     * Create Date: 2016-8-22 下午1:34:26
     */
    public void deleteInvestAfter(String id);
    
    /**
     * 查询列表
     * Description: 
     * @param 
     * @return List<CrowdfundingInvestAfterModel>
     * @throws 
     * Create Date: 2016-8-22 下午1:35:47
     */
    public List<CrowdfundingInvestAfterModel> selectList(CrowdfundingInvestAfterModel model);
    
    
    /**
     **分页查询
     * Description: 
     * @param 
     * @return SearchResult<CrowdfundingInvestAfterModel>
     * @throws 
     * Create Date: 2016-8-22 下午1:44:20
     */
    public SearchResult<CrowdfundingInvestAfterModel> selectPageList(CrowdfundingInvestAfterModel model);
    

    public CrowdfundingInvestAfterModel selectById(String id);
    
}
