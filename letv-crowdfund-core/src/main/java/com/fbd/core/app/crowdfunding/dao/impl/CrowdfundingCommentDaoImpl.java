/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingCommentDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:28:11] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingCommentDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹评论
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingCommentDao")
public class CrowdfundingCommentDaoImpl extends BaseDaoImpl<CrowdfundingCommentModel>
implements ICrowdfundingCommentDao{
    
    /**
     * 
     * Description: 分页查询
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 下午4:05:02
     */
    public List<Map<String,Object>> getList(CrowdfundingCommentModel model){
        return this.selectMapByFields("selectList", model);
    }
    
    public long getCount(CrowdfundingCommentModel model){
        return this.getCount("selectCount", model);
    }

}
