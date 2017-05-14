/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBlockChainReturnService.java 
 *
 * Created: [2016-8-26 下午5:15:40] by haolingfeng
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

package com.fbd.core.app.blockChain.service;

import java.util.Timer;


/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @version 1.0
 *
 */

public interface IBlockChainReturnService {
    
    
    /**
     * 意向金退款
     * Description: 
     * @param 
     * @return void
     * Create Date: 2016-8-26 下午5:16:54
     */
    public void intentionPayReturnTransfer(String orderId); 

    //退款成功以后的处理
    public void returnBackSuccess( String requestId,Timer mytimer);

}
