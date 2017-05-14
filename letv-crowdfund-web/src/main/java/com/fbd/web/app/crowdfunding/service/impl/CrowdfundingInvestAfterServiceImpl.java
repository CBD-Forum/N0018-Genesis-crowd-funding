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

package com.fbd.web.app.crowdfunding.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundingInvestAfterDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingInvestAfterModel;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestAfterService;

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
    
    
    @Override
    public void saveInvestAfter(CrowdfundingInvestAfterModel model) {
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
        List<CrowdfundingInvestAfterModel> rows = new ArrayList<CrowdfundingInvestAfterModel>();
        List<CrowdfundingInvestAfterModel> list = selectList(model);
        for(CrowdfundingInvestAfterModel investAfterModel:list){
        	String id = investAfterModel.getId();
        	FileUploadModel file = new FileUploadModel();
            file.setParentId(id);
            file.setType("");
            List<FileUploadModel> fileList = this.fileUploadDao.selectList(file);
            investAfterModel.setFileList(fileList);
            rows.add(investAfterModel);
        }
        search.setRows(rows);
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
