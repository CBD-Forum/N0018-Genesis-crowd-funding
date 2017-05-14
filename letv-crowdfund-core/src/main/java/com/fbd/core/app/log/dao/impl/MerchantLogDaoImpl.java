/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: MerchantLogDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:20:30] by haolingfeng
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
import org.springframework.stereotype.Repository;
import com.fbd.core.app.log.dao.IMerchantLogDao;
import com.fbd.core.app.log.model.MerchantLogModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:商户日志
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("merchantLogDao")
public class MerchantLogDaoImpl extends BaseDaoImpl<MerchantLogModel> implements
        IMerchantLogDao {

    public SearchResult<MerchantLogModel> getLogPage(
            MerchantLogModel merchantLogModel) {
        SearchResult<MerchantLogModel> searchResult = new SearchResult<MerchantLogModel>();
        searchResult.setTotal(getMerchantLogCount(merchantLogModel));
        searchResult.setRows(getMerchantLogList(merchantLogModel));
        return searchResult;
    }

    public long getMerchantLogCount(MerchantLogModel merchantLogModel) {
        return this.getCount(merchantLogModel);
    }

    public List<MerchantLogModel> getMerchantLogList(
            MerchantLogModel merchantLogModel) {
        return this.selectByModel("select", merchantLogModel);
    }

}
