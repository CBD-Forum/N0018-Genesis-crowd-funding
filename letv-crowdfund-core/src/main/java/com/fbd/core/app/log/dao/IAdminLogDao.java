/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdminDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:14:46] by haolingfeng
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

package com.fbd.core.app.log.dao;

import java.util.List;
import com.fbd.core.app.log.model.AdminLogModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 管理员日志
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IAdminLogDao extends BaseDao<AdminLogModel> {

    SearchResult<AdminLogModel> getLogPage(AdminLogModel adminLogModel);
    
    long getAdminLogCount(AdminLogModel adminLogModel);
    
    List<AdminLogModel> getAdminLogList(AdminLogModel adminLogModel);
}