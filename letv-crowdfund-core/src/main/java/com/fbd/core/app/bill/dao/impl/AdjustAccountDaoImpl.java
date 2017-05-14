/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdjustAccountDaoImpl.java 
 *
 * Created: [2015-3-13 上午10:00:00] by haolingfeng
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

package com.fbd.core.app.bill.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.bill.dao.IAdjustAccountDao;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 调账
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("adjustAccountDao")
public class AdjustAccountDaoImpl extends BaseDaoImpl<AdjustAccountModel>
 implements IAdjustAccountDao{
    
    public List<AdjustAccountModel> getList(AdjustAccountModel model){
        return this.selectByModel("selectList",model);
    }
    
    public long getCount(AdjustAccountModel model){
        return this.getCount("selectListCount", model);
    }

}
