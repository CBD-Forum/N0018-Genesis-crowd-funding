/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
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

package com.fbd.web.app.withdraw.service;

import java.util.List;
import java.util.Map;

import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
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
     * 
     * Description: 分页查询提现记录
     *
     * @param 
     * @return SearchResult<WithDrawModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-1-27 上午10:29:02
     */
    public SearchResult<WithDrawModel> getPageList(WithDrawModel model);
    /**
     * 
     * Description: 查询银行卡列表
     *
     * @param 
     * @return List<UserBankModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-9 上午11:36:57
     */
    public List<UserBankModel> getUserBankList(UserBankModel model);
    
    /**
     * 
     * Description: 保存提现
     *
     * @param 
     * @return WithDrawModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-15 下午12:30:56
     */
    public void saveWithDraw(WithDrawModel model,String requestId);
    
    /**
     * 
     * Description: 取现前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-3 下午6:10:23
     */
    public void checkWithDraw(WithDrawModel withDraw);
    /**
     * 
     * Description:查询交易中和交易失败的提现记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getUserWithDrawList(
			WithDrawModel model);
	
	/**
     * 
     * Description:查询交易中和交易失败的提现记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getUserWithDrawDetail(WithDrawModel model);
	
	
	/**
	 * 提现转账通知操作
	 * @param params
	 */
	public void withDrawTransferAfter(Map<String,Object> params);
	
	/**
	 * 提现审核转账通知操作
	 * @param params
	 */
	public void withDrawAuditTransferAfter(Map<String,Object> params);
	
	 /**
     * 提现申请成功
     * @param withDraw
     */
    public void withDrawApplySuccess(WithDrawModel withDraw);
    public void withDrawSuccess(WithDrawModel model);
    
	/**
     * 拒绝成功异步回调
     * @param transactionID
     */
    public void withDrawRefuseSucess(String transactionID);
    /**
     * 财务审核通过提现
     * @param transactionID
     */
    public void withDrawAuditSuccess(String transactionID);
    /**
     * 
     * Description:查询交易中
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getTrasactionList(
			WithDrawModel model);
    public Map<String,Object>selectTranactionById(WithDrawModel model);
    /**
     * 
     * Description: 查询取现服务费
     *
     * @param 
     * @return double
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午10:22:51
     */
    public double getWithDrawFee(String feeType);
    
    public void withDrawFailAfter(String requestId);

}
