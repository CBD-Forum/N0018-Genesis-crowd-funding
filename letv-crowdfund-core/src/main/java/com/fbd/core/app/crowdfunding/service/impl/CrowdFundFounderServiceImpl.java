/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdFundFounderServiceImpl.java 
 *
 * Created: [2016-8-11 上午11:48:29] by haolingfeng
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

package com.fbd.core.app.crowdfunding.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderBusinessDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderEducationsDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderWorksDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderBusinessModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderEducationsModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderWorksModel;
import com.fbd.core.app.crowdfunding.service.ICrowdFundFounderService;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 * @version 1.0
 *
 */


@Service(value="crowdFundFounderService")
public class CrowdFundFounderServiceImpl implements ICrowdFundFounderService {
    
    
    @Resource
    private ICrowdfundingFounderDao crowdfundingFounderDao;
    @Resource
    private ICrowdfundingFounderBusinessDao crowdfundingFounderBusinessDao;
    @Resource
    private ICrowdfundingFounderEducationsDao crowdfundingFounderEducationsDao;
    @Resource
    private ICrowdfundingFounderWorksDao crowdfundingFounderWorksDao;
    
    
    
    
    
    
    
    
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
    @Override
    public CrowdfundingFounderModel selectFounderInfoById(String id){
        
        CrowdfundingFounderModel founder = this.crowdfundingFounderDao.selectByPrimaryKey(id);
        if(founder!=null){
         //查询工作经历
            CrowdfundingFounderWorksModel qworkModel = new CrowdfundingFounderWorksModel();
            qworkModel.setFounderId(id);
            List<CrowdfundingFounderWorksModel> workList = this.crowdfundingFounderWorksDao.selectList(qworkModel);
            founder.setWorkList(workList);
            
            CrowdfundingFounderEducationsModel qeducationModel = new CrowdfundingFounderEducationsModel(); 
            qeducationModel.setFounderId(id);
            List<CrowdfundingFounderEducationsModel> educationList = this.crowdfundingFounderEducationsDao.selectList(qeducationModel);
            founder.setEducationList(educationList);
            
            CrowdfundingFounderBusinessModel qbusinessModel = new CrowdfundingFounderBusinessModel();
            qbusinessModel.setFounderId(id);
            List<CrowdfundingFounderBusinessModel> businessList = this.crowdfundingFounderBusinessDao.selectList(qbusinessModel);
            founder.setBusinessList(businessList);
        }
        
        return founder;
        
    }
   
    
    @Override
    public void saveFounder(CrowdfundingFounderModel model) {
        
        model.setId(PKGenarator.getId());
        model.setCreateTime(new Date());
        this.crowdfundingFounderDao.save(model);
    }
 
    @Override
    public void deleteFounder(String id) {
        
        //删除创始人信息
        this.crowdfundingFounderDao.deleteByPrimaryKey(id);
        //删除教育经历
        this.crowdfundingFounderEducationsDao.deleteByFounderId(id);
        //删除创业经历
        this.crowdfundingFounderBusinessDao.deleteByFounderId(id);
        //删除工作经历
        this.crowdfundingFounderWorksDao.deleteByFounderId(id);
        
    }
   
    @Override
    public SearchResult<CrowdfundingFounderModel> selectFounderPageList(
            CrowdfundingFounderModel model) {
        
        SearchResult<CrowdfundingFounderModel> search = new SearchResult<CrowdfundingFounderModel>();
        search.setRows(this.crowdfundingFounderDao.selectList(model));
        search.setTotal(this.crowdfundingFounderDao.selectCount(model));
        return search;
    }
    
    
    @Override
    public List<CrowdfundingFounderModel> selectFounderList(CrowdfundingFounderModel model) {
        return this.crowdfundingFounderDao.selectList(model);
    }

    @Override
    public void saveFounderBusiness(CrowdfundingFounderBusinessModel model) {
        model.setId(PKGenarator.getId());
        model.setCreateTime(new Date());
        this.crowdfundingFounderBusinessDao.save(model);
    }
 
    @Override
    public void deleteFounderBusiness(String id) {
         this.crowdfundingFounderBusinessDao.deleteByPrimaryKey(id);
        
    }
 
    @Override
    public List<CrowdfundingFounderBusinessModel> selectFounderBusinessList( CrowdfundingFounderBusinessModel model) {
        return this.crowdfundingFounderBusinessDao.selectList(model);
    }
 
    @Override
    public void saveFounderWorks(CrowdfundingFounderWorksModel model) {
        model.setId(PKGenarator.getId());
        model.setCreateTime(new Date());
        this.crowdfundingFounderWorksDao.save(model);
    }
    @Override
    public List<CrowdfundingFounderWorksModel> selectFounderWorksList( CrowdfundingFounderWorksModel model) {
        return this.crowdfundingFounderWorksDao.selectList(model);
    }
 
    @Override
    public void deleteFounderWorks(String id) {
        this.crowdfundingFounderWorksDao.deleteByPrimaryKey(id);
    }
 
    @Override
    public void saveFounderEducations(CrowdfundingFounderEducationsModel model) {
        model.setId(PKGenarator.getId());
        model.setCreateTime(new Date());
        this.crowdfundingFounderEducationsDao.save(model);
    }
 
    @Override
    public List<CrowdfundingFounderEducationsModel> selectFounderEducationsList(
            CrowdfundingFounderEducationsModel model) {
        return this.crowdfundingFounderEducationsDao.selectList(model);
    }

    
    
    @Override
    public void deleteFounderEducations(String id) {
         this.crowdfundingFounderEducationsDao.deleteByPrimaryKey(id);
    }
}
