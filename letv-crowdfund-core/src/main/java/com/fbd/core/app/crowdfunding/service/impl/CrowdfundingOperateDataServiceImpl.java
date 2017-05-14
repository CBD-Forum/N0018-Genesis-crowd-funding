/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingOperateDataServiceImpl.java 
 *
 * Created: [2016-8-12 下午3:05:31] by haolingfeng
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
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingOperateDataDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingOperateDataModel;
import com.fbd.core.app.crowdfunding.service.ICrowdfundingOperateDataService;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service(value="crowdfundingOperateDataService")
public class CrowdfundingOperateDataServiceImpl implements ICrowdfundingOperateDataService {

    
    @Resource
    private ICrowdfundingOperateDataDao crowdfundingOperateDataDao;

    @Override
    public SearchResult<CrowdfundingOperateDataModel> selectPageList(CrowdfundingOperateDataModel model) {
        // TODO Auto-generated method stub
        SearchResult<CrowdfundingOperateDataModel> search = new SearchResult<CrowdfundingOperateDataModel>();
        search.setRows(this.crowdfundingOperateDataDao.selectList(model));
        search.setTotal(this.crowdfundingOperateDataDao.selectCount(model));
        return search;
    }
    
    
    
    public void saveOperateData(CrowdfundingOperateDataModel model){
        model.setId(PKGenarator.getId());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        this.crowdfundingOperateDataDao.save(model);
    }
    
    
    public void deleteOperateData(String id){
        this.crowdfundingOperateDataDao.deleteByPrimaryKey(id);
    }
    
    
}
