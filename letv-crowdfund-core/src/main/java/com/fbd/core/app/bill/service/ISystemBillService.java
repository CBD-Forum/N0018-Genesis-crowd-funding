/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ISystemBillService.java 
 *
 * Created: [2014-12-22 下午4:33:59] by haolingfeng
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

package com.fbd.core.app.bill.service;

import java.util.Map;
import com.fbd.core.app.bill.model.SysTransferAcctModel;
import com.fbd.core.app.bill.model.SystemBillModel;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 系统账单
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface ISystemBillService {
    /**
     * 
     * Description: 交易查询列表
     * 
     * @param
     * @return SearchResult<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午1:52:17
     */
    public SearchResult<SystemBillModel> getSysBillList(SystemBillModel model);
    public SearchResult<Map<String, Object>> getSysBillList(Map<String, Object> paramMap);
    /**
     * 
     * Description: 垫付充值手续费账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addRechargeFeeSystemBill(RechargeModel recharge);
    
    /**
     * 
     * Description: 平台支付投资红包账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addActivityPaySystemBill(double tradeAmt, String tradeId);

    
    /**
     * 
     * Description: 平台收取转让账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addTransferFeeSystemBill(double tradeAmt, String tradeId,
            String loanNo, String detail);
    
    
    /**
     * 
     * Description: 平台收取借款服务费账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addBorrowerFeeSystemBill(double tradeAmt, String tradeId,
            String loanNo, String loanName);

    /**
     * 
     * Description: 系统准备金还款
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-12 下午4:32:13
     */
    public void addReserveRepaySystemBill(String loanNo, double tradeAmt,
            String tradeId, String oppositeSideInfo, String detail);
    
    /**
     * 
     * Description: 平台充值
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addRechargeSystemBill(double tradeAmt, String tradeId);
    
    /**
     * 
     * Description: 平台转账
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addTransferSystemBill(SysTransferAcctModel transfer);
    
    /**
     * 
     * Description:平台记账 (只适用于进出，不支持冻结与解冻)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午2:45:56
     */
    public void addBill(String trandeType,double tradeAmt,
            String direction,String parentId,String detail,String trandeId);
    
    /**
     * 
     * Description: 汇总平台账户数据
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-14 下午6:12:11
     */
    public Map<String,Object> getTotalData();
	public void addTransferSystemBill(Double transferFee, String transferNo,
			String investNo, String string);
    /**
     * Description: 项目分红 平台出账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:34:34
     */
    
    public void addLoanShareBonusSystemBill(double shareAmt, String investNo,
            String investor);
    /**
     * 查询账户可查询余额
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月22日 上午10:51:03
     */
    public  Map<String,Object> getWithDrawBalance();
}
