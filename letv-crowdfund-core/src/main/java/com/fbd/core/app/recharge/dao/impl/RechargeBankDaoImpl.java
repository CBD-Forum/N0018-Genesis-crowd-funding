/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeBankDaoImpl.java 
 *
 * Created: [2015-1-7 下午3:45:33] by haolingfeng
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

package com.fbd.core.app.recharge.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.recharge.dao.IRechargeBankDao;
import com.fbd.core.app.recharge.model.RechargeBankModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("rechargeBankDao")
public class RechargeBankDaoImpl extends BaseDaoImpl<RechargeBankModel>
        implements IRechargeBankDao {

    public List<RechargeBankModel> getList(RechargeBankModel model) {
        return this.selectByModel("selectList", model);

    }

}
