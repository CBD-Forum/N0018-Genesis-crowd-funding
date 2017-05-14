/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserSignatureDaoImpl.java 
 *
 * Created: [2016-8-16 上午11:58:42] by haolingfeng
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

import java.util.List;
import java.util.Map;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserSignatureDao;
import com.fbd.core.app.user.model.UserSignatureModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

 
@Repository(value="userSignatureDao")
public class UserSignatureDaoImpl extends BaseDaoImpl<UserSignatureModel> implements IUserSignatureDao {
 
    @Override
    public UserSignatureModel selectByUserId(String userId) {
        return this.selectOneByField("selectByUserId", userId);
    }
    
    @Override
    public UserSignatureModel selectBySignUserId(String signUserId){
        return this.selectOneByField("selectBySignUserId", signUserId);
    }
}
