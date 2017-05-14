/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ITrusteeshipOperationDao.java 
 *
 * Created: [2014-12-17 下午4:56:14] by haolingfeng
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

package com.fbd.core.app.thirdtrusteeship.dao;

import com.fbd.core.app.thirdtrusteeship.model.TrusteeshipOperationModel;
import com.fbd.core.base.BaseDao;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:资金托管操作
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface ITrusteeshipOperationDao extends
        BaseDao<TrusteeshipOperationModel> {
    
    /**
     * 
     * Description: 变更响应状态前查询
     *
     * @param 
     * @return TrusteeshipOperationModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-26 下午6:21:14
     */
    public TrusteeshipOperationModel find(String markId, String type,
            String operator, String trusteeship);

}
