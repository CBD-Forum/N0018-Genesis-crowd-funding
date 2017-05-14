/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ICrowdfundUserPrizeService.java 
 *
 * Created: [2016-8-10 下午6:01:42] by haolingfeng
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

package com.fbd.core.app.user.service;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 抽奖
 *
 * @author wuwenbin
 * @version 1.0
 *
 */

public interface ICrowdfundUserPrizeService {

    /**
     * Description: 保存中奖用户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午6:06:32
     */
    
    void saveCrowdfundUserPrize(String loanNo,String backNo);
    /**
     * 
     * Description: 对中奖幸运编号产生-项目方发送信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-27 下午4:08:42
     */
    public void sendPrizeLoanUser(String loanNo,String prizeStr);
    /**
     * 
     * Description: 对中奖幸运编号产生-中奖用户发送信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-27 下午4:17:45
     */
    public void sendPrizeUser(String loanNo,String userId);
    /**
     * 
     * Description:对中奖幸运编号产生-未中奖用户发送信息 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-27 下午4:18:55
     */
    public void sendPrizeNoUser(String loanNo,String userId,String prizeStr);
}
