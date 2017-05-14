/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICreateEnterpriseMemberDao.java 
 *
 * Created: [2016年9月6日 下午4:04:32] by haolingfeng
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

import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ICreateEnterpriseMemberDao {
    public void insertEnterpriseMember(CreateEnterpriseMember member);
    public long selectIsExists(CreateEnterpriseMember member);
    public long selectUpdateIsExists(CreateEnterpriseMember member);
    public long modify(CreateEnterpriseMember member);
    
    public CreateEnterpriseMember selectByUserId(String userId);
}
