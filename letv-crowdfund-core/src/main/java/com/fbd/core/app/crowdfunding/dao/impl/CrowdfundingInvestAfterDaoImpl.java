/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingInvestAfterDaoImpl.java 
 *
 * Created: [2016-8-22 下午12:42:55] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingInvestAfterDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingInvestAfterModel;
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

@Repository(value="crowdfundingInvestAfterDao")
public class CrowdfundingInvestAfterDaoImpl extends BaseDaoImpl<CrowdfundingInvestAfterModel> implements
        ICrowdfundingInvestAfterDao {

    
    @Override
    public List<CrowdfundingInvestAfterModel> selectList(CrowdfundingInvestAfterModel model) {
        return this.selectByField("selectList", model);
    }
 
    @Override
    public long selectCount(CrowdfundingInvestAfterModel model) {
        return this.getCount("selectCount", model);
    }

}
