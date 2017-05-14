/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingInvestafterServiceImpl.java 
 *
 * Created: [2016-8-22 下午1:45:39] by haolingfeng
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

package com.fbd.admin.app.crowdFund.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.admin.app.crowdFund.service.ICrowdfundingInvestAfterService;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingInvestAfterDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingInvestAfterModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="crowdfundingInvestAfterService")
public class CrowdfundingInvestAfterServiceImpl implements ICrowdfundingInvestAfterService {

    @Resource
    private ICrowdfundingInvestAfterDao crowdfundingInvestAfterDao;
    @Resource
    private IFileUploadDao fileUploadDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    
    @Override
    public void saveInvestAfter(CrowdfundingInvestAfterModel model) {
    	
		String loanNo = model.getLoanNo();
		CrowdfundingModel crowdfundingModel = this.crowdfundingDao.getByloanNo(loanNo);
		if(crowdfundingModel==null){
			throw new ApplicationException("项目编号不存在！");
		}
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        this.crowdfundingInvestAfterDao.save(model);
    }

    @Override
    public void deleteInvestAfter(String id) {
        try{
            int num = this.crowdfundingInvestAfterDao.deleteByPrimaryKey(id);
            if(num>0){
                num = this.fileUploadDao.deleteByParentId(id);
            }
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
 
    @Override
    public List<CrowdfundingInvestAfterModel> selectList(CrowdfundingInvestAfterModel model) {
        return this.crowdfundingInvestAfterDao.selectList(model);
    }

    @Override
    public SearchResult<CrowdfundingInvestAfterModel> selectPageList(CrowdfundingInvestAfterModel model) {
        SearchResult<CrowdfundingInvestAfterModel> search = new SearchResult<CrowdfundingInvestAfterModel>();
        search.setTotal(this.crowdfundingInvestAfterDao.selectCount(model));
        search.setRows(selectList(model));
        return search;
    }
  
    @Override
    public CrowdfundingInvestAfterModel selectById(String id) {
        CrowdfundingInvestAfterModel model = this.crowdfundingInvestAfterDao.selectByPrimaryKey(id);
        //查询文件
        FileUploadModel file = new FileUploadModel();
        file.setParentId(id);
        List<FileUploadModel> fileList = this.fileUploadDao.selectList(file);
        model.setFileList(fileList);
        return model;
    }

}
