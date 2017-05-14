/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: OperateDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:20:54] by haolingfeng
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

import org.springframework.stereotype.Repository;
import com.fbd.core.app.log.dao.IOperateLogDao;
import com.fbd.core.app.log.model.OperateLogModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("operateLogDao")
public class OperateLogDaoImpl extends BaseDaoImpl<OperateLogModel> implements
        IOperateLogDao {

    /**
     * Description: 添加
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-24 下午9:48:10
     */
    public int saveOperateLog(OperateLogModel operateLog) {
        return this.save(operateLog);
    }

}
