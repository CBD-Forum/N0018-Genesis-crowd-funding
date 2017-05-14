/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IRechargeServiceJob.java 
 *
 * Created: [2016-8-29 下午5:08:58] by haolingfeng
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

package com.fbd.core.app.recharge.service;

import java.util.List;
import java.util.Map;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IRechargeServiceJob {
    public List<Map<String, Object>> pushRechargeData(
            Map<String, Object> param);
    /**
     * 区块链成功
     */
    public List<Map<String, Object>> pushBlockRechargeData(
            Map<String, Object> param) ;
}
