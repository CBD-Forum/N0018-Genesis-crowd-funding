/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundingOperateDataService.java 
 *
 * Created: [2016-8-12 下午3:04:50] by haolingfeng
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

package com.fbd.core.app.crowdfunding.service;

import com.fbd.core.app.crowdfunding.model.CrowdfundingOperateDataModel;
import com.fbd.core.common.model.SearchResult;

public interface ICrowdfundingOperateDataService {
    
    
    public SearchResult<CrowdfundingOperateDataModel> selectPageList(CrowdfundingOperateDataModel model);
    
    
    
    public void saveOperateData(CrowdfundingOperateDataModel model);
    
    
    public void deleteOperateData(String id);
    
    
    
    

}
