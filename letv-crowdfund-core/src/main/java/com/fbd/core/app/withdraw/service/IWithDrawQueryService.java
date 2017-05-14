/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IWithDrawQueryService.java 
 *
 * Created: [2016-8-30 上午10:54:17] by haolingfeng
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

package com.fbd.core.app.withdraw.service;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IWithDrawQueryService {

    /**
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 上午11:01:08
     */
    
    void updateWithDraw();
    /**
     * 
     * Description: 支付兑付款项
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-31 上午9:45:04
     */
    public List<Map<String,Object>>pushWithdraw(Map<String,Object>param);
    /**
     * 发送提现请求
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月29日 下午12:14:34
     */
    void sendWithDrawSxyPay(WithDrawModel model);
    /**
     * 调用三方支付将钱转账到用户账户
     * @param model
     */
    public void sendRequestThird(WithDrawModel model) ;
    /**
     * 查询余额
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年9月30日 下午4:48:47
     */
    public String  QueryBalanceSxy();
    /**
     * 众筹提现
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 下午5:00:56
     */
    public List<Map<String,Object>>selectWithDrawData(Map<String,Object>param);
    /**
     * 众筹提现
     */
    public List<Map<String,Object>>pushWithdrawData(Map<String,Object>param);
    /**
     * 用户记账
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月19日 下午7:57:18
     */
    public void withDrawUserSuccess(WithDrawModel model);
    /**
     * 系统记账
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月19日 下午7:57:40
     */
    public void withDrawSystemSuccess(WithDrawModel model);
    /**
     * 首信易支付成功
     * @param model
     */
    public void withDrawBySXYSuccess(WithDrawModel model);
    /**
     * 平台提现收取手续费
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月24日 上午9:58:43
     */
    public void systemWithDrawUserSuccess(SystemWithdrawModel model);
    public List<Map<String, Object>> pushWithDrawData(
            Map<String, Object> param);
    /**
     * 提现区块链审核成功
     */
    public List<Map<String, Object>> pushWithDrawAuditingSuccessData(
            Map<String, Object> param) ;
    /**
     * 提现成功
     */
    public List<Map<String, Object>> pushWithDrawSuccessData(
            Map<String, Object> param);
    
    /**
     * 平台提现
     * @param model
     */
    public void platformWithDraw(SystemWithdrawModel model);
    
}
