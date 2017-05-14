/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserModel.java 
 *
 * Created: [2014-12-3 10:46:57] by haolingfeng
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
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.core.app.log.dao;

import java.util.List;
import com.fbd.core.app.log.model.UserLogModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户日志dao
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IUserLogDao extends BaseDao<UserLogModel> {

    SearchResult<UserLogModel> getLogPage(UserLogModel userLogModel);

    long getUserLogCount(UserLogModel userLogModel);

    List<UserLogModel> getUserLogList(UserLogModel userLogModel);
}