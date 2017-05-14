/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingFounderDaoImpl.java 
 *
 * Created: [2016-8-11 上午11:40:29] by haolingfeng
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

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Repository(value="crowdfundingFounderDao")
public class CrowdfundingFounderDaoImpl extends BaseDaoImpl<CrowdfundingFounderModel> implements
        ICrowdfundingFounderDao {
    
    
    public List<CrowdfundingFounderModel> selectList(CrowdfundingFounderModel model){
        return this.selectByModel("selectList",model);
    }
    
    
    public long selectCount(CrowdfundingFounderModel model){
        return this.getCount("selectCount", model);
    }

   

}
