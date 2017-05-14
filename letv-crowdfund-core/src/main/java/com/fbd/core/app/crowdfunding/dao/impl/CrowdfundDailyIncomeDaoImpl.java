/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundDailyIncomeDaoImpl.java 
 *
 * Created: [2016-8-31 上午11:20:00] by haolingfeng
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

package com.fbd.core.app.crowdfunding.dao.impl;

import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundDailyIncomeDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundDailyIncomeModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户每日收益
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundDailyIncomeDao")
public class CrowdfundDailyIncomeDaoImpl extends BaseDaoImpl<CrowdfundDailyIncomeModel>
    implements ICrowdfundDailyIncomeDao {

 /*   public int save(CrowdfundDailyIncomeModel crowdfundDailyIncomeModel){
        return this.save(crowdfundDailyIncomeModel);
    }*/
    public int saveCrowdModel(CrowdfundDailyIncomeModel crowdfundDailyIncomeModel){
      return this.save(crowdfundDailyIncomeModel);
    }
}
