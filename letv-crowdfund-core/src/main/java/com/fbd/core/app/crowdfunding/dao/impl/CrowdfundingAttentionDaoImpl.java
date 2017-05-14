/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingAttentionDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:24:59] by haolingfeng
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

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingAttentionDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹关注
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingAttentionDao")
public class CrowdfundingAttentionDaoImpl extends BaseDaoImpl<CrowdfundingAttentionModel> 
implements ICrowdfundingAttentionDao{

    public List<Map<String,Object>> getList(CrowdfundingAttentionModel model){
        return this.selectMapByFields("selectList", model);
    }
    
    public long getCount(CrowdfundingAttentionModel model){
        return this.getCount("getCount", model);
    }
}
