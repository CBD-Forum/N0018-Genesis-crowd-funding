/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IPushDataLog.java 
 *
 * Created: [2016-8-24 下午6:18:52] by haolingfeng
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

package com.fbd.core.app.push.dao;

import java.util.Map;
import com.fbd.core.app.push.model.PushDataLogModel;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IPushDataLogDao {
    int savePushDataLog(PushDataLogModel model); 
    /**
     * 推送数据的最近一次推送数据
     * Description: 
     *
     * @param 
     * @return PushDataLogModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-24 下午6:36:07
     */
    public Map<String,Object> selectModel(String eventCode);
}
