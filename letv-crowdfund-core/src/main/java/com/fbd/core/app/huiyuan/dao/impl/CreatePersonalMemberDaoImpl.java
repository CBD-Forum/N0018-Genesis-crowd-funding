/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CreatePersonalMemberDaoImpl.java 
 *
 * Created: [2016年9月8日 下午2:32:02] by haolingfeng
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
import com.fbd.core.app.huiyuan.dao.ICreatePersonalMemberDao;
import com.fbd.core.app.huiyuan.model.CreatePersonalMember;
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

@Repository("createPersonalMemberDao")
public class CreatePersonalMemberDaoImpl extends BaseDaoImpl<CreatePersonalMember> implements ICreatePersonalMemberDao{
   public int savePersonalMember(CreatePersonalMember member){
       return this.save(member);
   }
   public long getCount(CreatePersonalMember member){
      return this.getCount("selectPersonalMemberIsRegister", member);
   }
   public CreatePersonalMember selectByUserId(String userId){
       return this.selectOneByField("selectByUserId", userId);
   }
}
