/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeService.java 
 *
 * Created: [2014-12-22 上午10:30:35] by haolingfeng
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

package com.fbd.core.app.thirdtrusteeship.service;

import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.withdraw.model.WithDrawModel;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 取现接口
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface CreateWithDrawService {
    
    public void checkWithDraw(String userId,double withDrawAmt);
    
    /**
     * 
     * Description: 汇付提现调用
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 上午10:37:50
     */
    public String createWithDraw(String userId, String thirdNo, String orderId, double withDrawAmt);

    /**
     * 
     * Description: 提现成功后
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午2:38:48
     */
    public void updateWithDrawSuccess(String orderId, Map<String, String> paramMap);
    
    /**
     * 
     * Description: 发送取现审核请求
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 下午7:59:07
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String sendAuditWithDrawRequest(WithDrawModel withDraw);
    
    /**
     * 
     * Description: 取现复核成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-15 下午4:49:25
     */
    public void updateAuditSuccess(WithDrawModel withDraw);
    /**
     * 
     * Description: 发送取消取现请求
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 下午7:59:07
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String sendCancelWithDrawRequest(WithDrawModel withDraw);

    /**
     * 
     * Description: 取现复核拒绝
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-15 下午4:49:25
     */
    public void updateAuditRefuse(WithDrawModel withDraw);
}
