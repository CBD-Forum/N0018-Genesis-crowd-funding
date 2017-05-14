/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBlockChainCrowdfundService.java 
 *
 * Created: [2016-9-26 下午3:45:19] by haolingfeng
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

import java.util.Date;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IBlockChainCrowdfundingService {
    /**
     * 
     * Description: 发布众筹项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFundAfterRelease(CrowdfundingModel model);
    /**
     * 
     * Description: 预热保存
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 下午6:02:42
     */
    public void updateCrowdFundAfterPreheat(String loanNo, Date preheatEndTime);
    
    
    /**
     * 转让审核成功(timmer)
     */
    public void updateTimmerProductTransferAuditSuccess(Map<String,String> result);
    /**
     * 转让审核成功(timmer)
     */
    public void updateTimmerProductTransferFeeAuditSuccess(Map<String,String> result);
    /**
     * 转让审核失败(timmer)
     */
    public void updateTimmerProductTransferAuditFail(Map<String,String> result);
    /**
     * 转让审核成功(购买人部分)
     */
    public void updateProductTransferAuditSuccess(Map<String,String> result);
    /**
     * 
     * Description: 转让审核成功(系统部分)
     */
    public void updateProductTransferEndSuccess(Map<String, String> result);
    /**
     * 
     * Description: 转让审核成功(系统部分)
     */
    public void updateProductTransferSystemAuditSuccess(Map<String, String> result);
    /**
     * 转让审核失败
     */
    public void updateProductTransferAuditFail(Map<String,String> result);
    /**
     * 转让审核异常初始化
     */    
    public void updateProductTransferErrorInit(Map<String,String> result);
    
    /**
     * 
     * Description: 转让审核成功发送短信及站内信
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-19 下午6:52:07
     */
    public void sendProductTransferMsg(Map<String, String> result);
    /**
     * 
     * Description: 转让审核失败发送短信及站内信
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-19 下午6:52:07
     */
    public void sendProductTransferErrorMsg(Map<String, String> result);
    /**
     * 
     * Description: 筹款结束 如果该项目为产品,分配抽奖编号
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-27 下午5:29:38
     */
    public void saveUserPrize(String loanNo);
}
