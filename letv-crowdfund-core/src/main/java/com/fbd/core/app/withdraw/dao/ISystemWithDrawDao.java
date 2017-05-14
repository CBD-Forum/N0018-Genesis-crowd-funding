/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ISystemWithDrawDao.java 
 *
 * Created: [2016年10月21日 上午11:08:10] by haolingfeng
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

package com.fbd.core.app.withdraw.dao;

import java.util.Map;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface ISystemWithDrawDao {
    public int saveSystemWithDraw(SystemWithdrawModel model);
    public SearchResult<Map<String, Object>> getSystemWithDrawPage(SystemWithdrawModel systemWithdrawModel);
    public long updateById(SystemWithdrawModel model);

    public SystemWithdrawModel selectByOrderId(String orderId);
}
