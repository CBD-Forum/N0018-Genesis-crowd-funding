/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingFounderEducationsDaoImpl.java 
 *
 * Created: [2016-8-11 上午11:41:31] by haolingfeng
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
import java.util.Map;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingFounderEducationsDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderEducationsModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderWorksModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Repository(value="crowdfundingFounderEducationsDao")
public class CrowdfundingFounderEducationsDaoImpl extends BaseDaoImpl<CrowdfundingFounderEducationsModel>
        implements ICrowdfundingFounderEducationsDao {
 
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
    public List<CrowdfundingFounderEducationsModel> selectList(CrowdfundingFounderEducationsModel model) {
        return this.selectByModel("selectList", model);
    }
}
