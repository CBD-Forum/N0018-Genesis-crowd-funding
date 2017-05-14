/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdFundFounderService.java 
 *
 * Created: [2016-8-11 上午11:47:53] by haolingfeng
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

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderBusinessModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderEducationsModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderWorksModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @version 1.0
 *
 */

public interface ICrowdFundFounderService {
    
    
    /**
     *保存创始人信息
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:52:30
     */
    public void saveFounder(CrowdfundingFounderModel model);
    
    
    /**
     * 根据ID查询创始人信息
     * Description: 
     *
     * @param 
     * @return CrowdfundingFounderModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-6 下午5:24:36
     */
    public CrowdfundingFounderModel selectFounderInfoById(String id);
    
    
    /**
     *删除创始人信息
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:52:30
     */
    public void deleteFounder(String id);
    
    
    /**
     * 分页查询创始人列表
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<CrowdfundingFounderModel> selectFounderPageList(CrowdfundingFounderModel model);

    
    /**
     * 查询创始人列表
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * Create Date: 2015-3-27 下午5:11:45
     */
    public List<CrowdfundingFounderModel> selectFounderList(CrowdfundingFounderModel model);
    
    
    /**
     * 保存创始人创业信息
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:53:48
     */
    public void saveFounderBusiness(CrowdfundingFounderBusinessModel model);
    
    /**
     * 删除创始人创业信息
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 下午12:09:47
     */
    public void deleteFounderBusiness(String id);
    
    /**
     * 查询创始人创业信息列表
     * Description: 
     *
     * @param 
     * @return List<CrowdfundingFounderBusinessModel>
     * @throws 
     * Create Date: 2016-8-11 下午12:22:22
     */
    public List<CrowdfundingFounderBusinessModel> selectFounderBusinessList(CrowdfundingFounderBusinessModel model);

    
    /**
     * 保存创始人工作经历
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:53:48
     */
    public void saveFounderWorks(CrowdfundingFounderWorksModel model);
    
    /**
     * 查询创始人工作经历
     * Description: 
     * @param 
     * @return List<CrowdfundingFounderWorksModel>
     * @throws 
     * Create Date: 2016-8-11 下午12:23:31
     */
    public List<CrowdfundingFounderWorksModel> selectFounderWorksList(CrowdfundingFounderWorksModel model);
    /**
     * 删除创始人工作经历
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:53:48
     */
    public void deleteFounderWorks(String id);
    
    /**
     * 保存创始人教育经历
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:53:48
     */
    public void saveFounderEducations(CrowdfundingFounderEducationsModel model);
    
    
    
    /**
     * 查询创始人教育经历
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 上午11:53:48
     */
    public List<CrowdfundingFounderEducationsModel> selectFounderEducationsList(CrowdfundingFounderEducationsModel model);
    
    /**
     * 删除创始人教育经历
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 下午12:11:04
     */
    public void deleteFounderEducations(String id);
    

}
