/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdminLogDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:20:04] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.log.dao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.log.dao.IAdminLogDao;
import com.fbd.core.app.log.model.AdminLogModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("adminLogDao")
public class AdminLogDaoImpl extends BaseDaoImpl<AdminLogModel> implements
        IAdminLogDao {
    
    private static final Logger logger = LoggerFactory.getLogger(AdminLogDaoImpl.class);

    public SearchResult<AdminLogModel> getLogPage(AdminLogModel adminLogModel) {
        SearchResult<AdminLogModel> searchResult = new SearchResult<AdminLogModel>();
        searchResult.setTotal(getAdminLogCount(adminLogModel));
        searchResult.setRows(getAdminLogList(adminLogModel));
        return searchResult;
    }

    public List<AdminLogModel> getAdminLogList(AdminLogModel adminLogModel) {
        return this.selectByModel("select", adminLogModel);
    }

    public long getAdminLogCount(AdminLogModel adminLogModel) {
        return this.getCount(adminLogModel);
    }

}
