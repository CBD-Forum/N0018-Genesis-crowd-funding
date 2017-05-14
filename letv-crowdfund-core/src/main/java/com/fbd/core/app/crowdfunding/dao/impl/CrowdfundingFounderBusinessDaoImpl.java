/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingFounderBusinessDaoImpl.java 
 *
 * Created: [2016-8-11 上午11:38:15] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderBusinessDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderBusinessModel;
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

@Repository(value="crowdfundingFounderBusinessDao")
public class CrowdfundingFounderBusinessDaoImpl extends BaseDaoImpl<CrowdfundingFounderBusinessModel>
        implements ICrowdfundingFounderBusinessDao {
    
    
    
    /**
     * 根据领导人id删除列表
     * Description: 
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-11 下午12:37:59
     */
    public void deleteByFounderId(String founderId){
        this.deleteByField("deleteByFounderId", founderId);
    }

  
    @Override
    public List<CrowdfundingFounderBusinessModel> selectList(
            CrowdfundingFounderBusinessModel model) {
        return this.selectByModel("selectList", model);
    }
    
    
    
 
}
