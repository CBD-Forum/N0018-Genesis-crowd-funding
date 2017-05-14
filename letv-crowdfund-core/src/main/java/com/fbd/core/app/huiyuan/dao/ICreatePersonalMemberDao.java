/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICreatePersonalMemberDao.java 
 *
 * Created: [2016年9月8日 下午2:31:44] by haolingfeng
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

package com.fbd.core.app.huiyuan.dao;

import com.fbd.core.app.huiyuan.model.CreatePersonalMember;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ICreatePersonalMemberDao {
    public int savePersonalMember(CreatePersonalMember member);
    public long getCount(CreatePersonalMember member);
    public CreatePersonalMember selectByUserId(String userId);
}
