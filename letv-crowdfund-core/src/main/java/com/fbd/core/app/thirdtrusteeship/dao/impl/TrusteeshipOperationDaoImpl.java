/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: TrusteeshipOperationDaoImpl.java 
 *
 * Created: [2014-12-17 下午4:56:40] by haolingfeng
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

package com.fbd.core.app.thirdtrusteeship.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.thirdtrusteeship.common.TrusteeshipConstants;
import com.fbd.core.app.thirdtrusteeship.dao.ITrusteeshipOperationDao;
import com.fbd.core.app.thirdtrusteeship.model.TrusteeshipOperationModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:资金托管操作
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("trusteeshipOperationDao")
public class TrusteeshipOperationDaoImpl extends
        BaseDaoImpl<TrusteeshipOperationModel> implements
        ITrusteeshipOperationDao {

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
            String operator, String trusteeship) {
        TrusteeshipOperationModel model = new TrusteeshipOperationModel();
        model.setMarkId(markId);
        model.setType(type);
        model.setOperator(operator);
        model.setTrusteeship(trusteeship);
        List<TrusteeshipOperationModel> resultList = this.selectByModel("selectByModel", model);
        if(resultList.size()>0){
            return resultList.get(0);
        }else{
            return null;
        }
    }
}
