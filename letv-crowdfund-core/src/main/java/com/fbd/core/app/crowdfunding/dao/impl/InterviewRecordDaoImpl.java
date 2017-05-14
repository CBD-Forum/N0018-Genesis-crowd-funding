/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: InterviewRecordDaoImpl.java 
 *
 * Created: [2015-5-19 下午12:08:13] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.IInterviewRecordDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:约谈记录 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("interviewRecordDao")
public class InterviewRecordDaoImpl extends BaseDaoImpl<InterviewRecordModel>
implements IInterviewRecordDao{
    public List<Map<String,Object>> getList(InterviewRecordModel model){
        return this.selectMapByFields("selectList",model);
    }
    
    public long getCount(InterviewRecordModel model){
        return this.getCount("getCount",model);
    }
    
    
    public List<Map<String,Object>> getInterviewList(InterviewRecordModel model){
        return this.selectMapByFields("selectInterviewList",model);
    }
}
