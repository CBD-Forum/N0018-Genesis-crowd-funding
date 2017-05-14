/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IWithDrawService.java 
 *
 * Created: [2015-1-15 下午4:57:21] by haolingfeng
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
 * ProjectName: fbd-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.withdraw.service;

import java.util.Map;

import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 取现
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IWithDrawService {
    public WithDrawModel getByOrderId(String orderId);

    /**
     * Description: 查询用户提现列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午3:22:45
     */
    public SearchResult<Map<String, Object>> getWithDrawPage(Map<String, Object> param);
    
    /**
     * 
     * Description:提现审核后更新提现记录 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-20 下午6:25:08
     */
    public void updateWithDrawAfterAudit(WithDrawModel model);
    
    
    /**
     * 提现审核拒绝后的操作
     * @param model
     */
    public void saveWithDrawAuditRefuse(WithDrawModel model,String requestId);
    
    /**
     * 提现审核通过后的操作
     * @param model
     */
    public void saveWithDrawAuditPassed(WithDrawModel model);
    
    /**
     * 申请提现申请
     * @param model
     */
    public void sendWithDrawRequest(WithDrawModel model);

    
    /**
     * 调用三方支付将钱转账到用户账户
     * @param model
     */
  public void sendRequestThird(WithDrawModel withdraw);
  public long selectSuccessCount(String orderId);
    
    
}
