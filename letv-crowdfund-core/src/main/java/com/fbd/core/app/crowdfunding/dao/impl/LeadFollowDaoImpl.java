/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: LeadFollowDaoImpl.java 
 *
 * Created: [2015-5-26 下午5:51:19] by haolingfeng
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

import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ILeadFollowDao;
import com.fbd.core.app.crowdfunding.model.LeadFollowModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 领投跟投关系
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("leadFollowDao")
public class LeadFollowDaoImpl extends BaseDaoImpl<LeadFollowModel>
implements ILeadFollowDao{

    
}
