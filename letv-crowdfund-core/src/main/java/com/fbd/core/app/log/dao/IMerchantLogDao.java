/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdminDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:14:46] by haolingfeng
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

package com.fbd.core.app.log.dao;

import java.util.List;
import com.fbd.core.app.log.model.MerchantLogModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:商户日志
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IMerchantLogDao extends BaseDao<MerchantLogModel> {

    SearchResult<MerchantLogModel> getLogPage(MerchantLogModel merchantLogModel);

    long getMerchantLogCount(MerchantLogModel merchantLogModel);

    List<MerchantLogModel> getMerchantLogList(MerchantLogModel merchantLogModel);
}