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

import com.fbd.core.app.log.model.OperateLogModel;
import com.fbd.core.base.BaseDao;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 操作日志
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IOperateLogDao extends BaseDao<OperateLogModel> {
    
    /**
     * Description: 添加
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-24 下午9:48:10
     */
    public int saveOperateLog(OperateLogModel operateLog);
}