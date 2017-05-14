/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserLogImpl.java 
 *
 * Created: [2014-12-10 下午6:23:25] by haolingfeng
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

package com.fbd.core.app.log.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.log.dao.IUserLogDao;
import com.fbd.core.app.log.model.GuaranteeLogModel;
import com.fbd.core.app.log.model.UserLogModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("userLogDao")
public class UserLogDaoImpl extends BaseDaoImpl<UserLogModel> implements
        IUserLogDao {

    public SearchResult<UserLogModel> getLogPage(UserLogModel userLogModel) {
        SearchResult<UserLogModel> searchResult = new SearchResult<UserLogModel>();
        searchResult.setTotal(getUserLogCount(userLogModel));
        searchResult.setRows(getUserLogList(userLogModel));
        return searchResult;
    }

    public long getUserLogCount(UserLogModel userLogModel) {
        return this.getCount(userLogModel);
    }

    public List<UserLogModel> getUserLogList(UserLogModel userLogModel) {
        return this.selectByModel("select", userLogModel);
    }
}
