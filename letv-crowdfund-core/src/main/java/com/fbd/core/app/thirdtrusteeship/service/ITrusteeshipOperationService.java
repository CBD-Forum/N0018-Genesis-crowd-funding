/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: TrusteeshipOperationService.java 
 *
 * Created: [2014-12-18 上午10:31:16] by haolingfeng
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


/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface ITrusteeshipOperationService {
    /**
     * 
     * Description: 返回时更新操作数据
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午6:31:05
     */
    public void updateOperationModel(String operationId, String status,
            String resInfo);
    
    
    /**
     * 
     * Description: 返回时更新操作数据
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午6:31:05
     */
    public void updateOperationModel(String markId, String type,String operator,
            String thirdId,String status,String resInfo);

    /**
     * 
     * Description: 插入操作数据
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午5:46:52
     */
    @SuppressWarnings("deprecation")
    public void saveOperationModel(String operationId, String userId,
            String markId, String type, String params);
    
    /**
     * 
     * Description: 插入操作数据（资金池）
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午5:46:52
     */
    @SuppressWarnings("deprecation")
    public void saveOperationModel(String operationId, String userId,
    		String markId, String type, String params, String requestUrl);
    
    
    /**
     * 
     * Description: 插入操作数据(汇付宝)
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-17 下午5:46:52
     */
    @SuppressWarnings("deprecation")
    public void saveOperationModel(String operationId, String userId,
            String markId, String type,String requestUrl, String thirdId,String params);

}
