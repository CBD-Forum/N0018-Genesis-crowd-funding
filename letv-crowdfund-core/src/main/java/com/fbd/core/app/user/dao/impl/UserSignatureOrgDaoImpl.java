/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserOrgDaoImpl.java 
 *
 * Created: [2016-8-16 下午12:48:59] by haolingfeng
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

package com.fbd.core.app.user.dao.impl;

import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserSignatureOrgDao;
import com.fbd.core.app.user.model.UserSignatureOrgModel;
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


@Repository(value="userSignatureOrgDao")
public class UserSignatureOrgDaoImpl extends BaseDaoImpl<UserSignatureOrgModel> implements IUserSignatureOrgDao {
    /**
     * 查询
     * Description: 
     */
    public UserSignatureOrgModel selectByModel(UserSignatureOrgModel model){
        return this.selectOneByField("selectByModel",model);
    }
}
