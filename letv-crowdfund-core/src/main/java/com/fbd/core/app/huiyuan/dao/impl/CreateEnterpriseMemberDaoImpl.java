/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CreateEnterpriseMemberDaoImpl.java 
 *
 * Created: [2016年9月6日 下午4:04:45] by haolingfeng
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

package com.fbd.core.app.huiyuan.dao.impl;

import org.springframework.stereotype.Repository;
import com.fbd.core.app.huiyuan.dao.ICreateEnterpriseMemberDao;
import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("createEnterpriseMemberDao")
public class CreateEnterpriseMemberDaoImpl extends BaseDaoImpl<CreateEnterpriseMember> implements ICreateEnterpriseMemberDao{
    public void insertEnterpriseMember(CreateEnterpriseMember member) {
        // TODO Auto-generated method stub
        this.save(member);
    }
    public long selectIsExists(CreateEnterpriseMember member){
       return this.getCount("selectIsExists", member);
    }
    public long selectUpdateIsExists(CreateEnterpriseMember member){
        return this.getCount("selectUpdateIsExists", member);
    }
    public long modify(CreateEnterpriseMember member){
        return this.updateBySelective(member);
    }
    
    public CreateEnterpriseMember selectByUserId(String userId){
        return this.selectOneByField("selectByUserId", userId);
    }
}
