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
import com.fbd.core.app.log.dao.IGuaranteeLogDao;
import com.fbd.core.app.log.model.GuaranteeLogModel;
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
@Repository
public class GuaranteeLogDaoImpl extends BaseDaoImpl<GuaranteeLogModel> implements
        IGuaranteeLogDao {
    
    private static final Logger logger = LoggerFactory.getLogger(GuaranteeLogDaoImpl.class);

    public SearchResult<GuaranteeLogModel> getLogPage(GuaranteeLogModel guaranteeLogModel) {
        SearchResult<GuaranteeLogModel> searchResult = new SearchResult<GuaranteeLogModel>();
        searchResult.setTotal(getGuaranteeLogCount(guaranteeLogModel));
        searchResult.setRows(getGuaranteeLogList(guaranteeLogModel));
        return searchResult;
    }

    public List<GuaranteeLogModel> getGuaranteeLogList(GuaranteeLogModel guaranteeLogModel) {
        return this.selectByModel("select", guaranteeLogModel);
    }

    public long getGuaranteeLogCount(GuaranteeLogModel guaranteeLogModel) {
        return this.getCount(guaranteeLogModel);
    }

}
