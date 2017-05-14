/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserBillService.java 
 *
 * Created: [2014-12-22 下午3:51:42] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.bill.service;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户账单
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IUserBillService {
    /**
     * 
     * Description: 用户充值账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addRechargeUserBill(RechargeModel recharge);
    /**
      * 
      * Description: 用户充值冻结账单插入
      * 
      * @param
      * @return void
      * @throws
      * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
      */
     public void addUserRechargeFreezeBill(RechargeModel recharge);
    /**
     * 
     * Description: 用户充值手续费账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addRechargeFeeUserBill(RechargeModel recharge);

    
    /**
     * Description:用户充值解冻账单
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addUserRechargeUnFreezeBill(RechargeModel recharge);
    /**
     * 
     * Description:插入冻结记录
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
     */
//    public void addFreezeUserBill(InvestModel invest);

    /**
     * 
     * Description: 插入放款账单
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-5 下午3:40:49
     */
//    public void addLoansUserBill(InvestModel invest, double fee, LoanModel loan);
    
    
    public void addBorrowerBill(String tradeId, String loanName,
            String oppositeSideInfo, String userId,double tradeAmt) ;
    
    
    /**
  * 
  * Description:项目方借款服务费账单
  * 
  * @param
  * @return void
  * @throws
  * @Author haolingfeng Create Date: 2015-1-5 下午3:51:59
  */
 public void addLoanBorrowFeeBill(String loanNo,
         String loanName, String userId, double fee) ;

    /**
     * 
     * Description: 投资人流标账单
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-5 下午3:51:59
     */
//    public void addFlowBill(InvestModel invest, String loanName);
    
    /**
     * 
     * Description:插入取现冻结记录
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
     */
    public void addWithDrawFreezeUserBill(WithDrawModel withDraw);
    
    /** 
    * Description:插入取现账单
    * 
    * @param
    * @return void
    * @throws
    * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
    */
   public void addWithDrawUserBill(WithDrawModel withDraw,double withDrawAmt);
   
   /**
     * Description: 插入拒绝提现账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-5-6 上午11:00:18
     */
    public void addCancelWithDrawUserBill(WithDrawModel withDraw);
    
        /**
      * Description:  提现失败账单
      * 
      * @param
      * @return void
      * @throws
      * @Author dongzhongwei Create Date: 2015-5-6 上午9:44:32
      */
     public void addWithDrawFailUserBill(WithDrawModel withDraw);

    /**
     * 
     * Description:查询最新的用户账单
     * 
     * @param
     * @return UserBillModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午6:32:12
     */
    public  UserBillModel getLatestBill(String userId);

 
    /**
     * 用户推荐提成账单插入
     * @param tradeAmt
     * @param userId
     * @param tradeId
     * @param recommandType 奖励类型
     */
    public void addRecommendRewardUserBill(double tradeAmt,String userId,String tradeId, String recommandType);
  
    
    /**
     * 
     * Description: 用户调账账单插入
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
     */
    public void addAdjustAccountUserBill(AdjustAccountModel adjustModel);
    
    /**
     * 
     * Description:用户记账 (只适用于进出，不支持冻结与解冻)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午2:45:56
     */
    public void addBill(String userId,String trandeType,double tradeAmt,
            String direction,String parentId,String detail,String oppositeSideInfo,
            String trandeId);

    /**
     * 
     * Description: 验证余额是否足够操作
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-25 上午11:01:41
     */
    public void checkBalance(double checkAmt, String userId);

    /**
     * 
     * Description: 交易查询列表
     * 
     * @param
     * @return SearchResult<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午1:52:17
     */
    public SearchResult<UserBillModel> getUserBillList(UserBillModel model);
    public SearchResult<Map<String, Object>> getUserBillList(Map<String, Object> paramMap);
    /**
     * 
     * Description: 交易查询列表
     * 
     * @param
     * @return SearchResult<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午1:52:17
     */
    public List<UserBillModel> getList(UserBillModel model);
    
	public void checkFreezeAmt(double checkAmt, String userId);


    /**
     * 添加转让人账单
     * @param investModel
     * @param investTransferModel
     */
//	public void addSaleTransferUserBill(InvestModel investModel, InvestTransferModel investTransferModel);

	/**
	 * 添加购买人账单 
	 * @param userId
	 * @param investTransferModel
	 */
	public void addBuyTransferUserBill(String userId, CrowdfundTransferDetailModel investTransferModel);

    /**
     * Description: 项目分红投资人进账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:36:41
     */
    
    public void addLoanShareBonusUserBill(double shareAmt, String investor,
            String investNo);
 
    /**
     * 
     * Description: 投资人放款账单
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-5 下午3:51:59
     */
    public void addInvestorBill(CrowdfundingSupportModel invest, String loanName,
            String oppositeSideInfo, String userId);
    /**
     * Description: 添加购买转让冻结账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    public void addByUserTransferFreeze(CrowdfundProductTransferModel model);

    /**
     * Description: 投资冻结账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 下午3:22:14
     */
    
    public void addInvestFreeze(CrowdfundingModel loan,
            String orderId , double tradeAmt,String userId);

    /**
     * Description:购买转让出账账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-29 下午1:30:49
     */
    
    public void addByUserTransferBill(
            CrowdfundProductTransferModel transferModel);

    /**
     * Description: 添加购买转让审核失败解冻账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    
    public void addByUserTransferUnfreeze(
            CrowdfundProductTransferModel transferModel);

    /**
     * Description: 用户同意分红冻结金额
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 下午4:31:40
     */
    
    public void addUserBonusFreeze(CrowdfundBonusModel model,String userId);

    /**
     * Description: 后台审核分红通过扣除冻结账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:46:58
     */
    
    public void addAuditBonusPassBill(CrowdfundBonusModel bonusmodel,
            String loanUser);

    /**
     * Description: 项目分红审核失败解冻账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 下午4:48:06
     */
    
    public void addAuditRefuseUnfreeze(CrowdfundBonusModel bonusmodel,
            String loanUser);
    
    
    
    /**
     * 查询用户资金信息
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     */
    public Map<String,Object>selectUserCapitalInfo(String userId);

    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return UserBillModel
     * @throws
     */
    
    public UserBillModel getUserBillDetail(UserBillModel model);
    
    
    /**
     * Description:退款项目方冻结账单
     * @return void
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addLoanRefundFrzeeBill(CrowdfundingSupportModel supportModel,String loanUser);
    
    /**
     * Description:退款成功后项目方账单
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addLoanRefundSucceeBill(CrowdfundingSupportModel supportModel,String loanUser);
    
    /**
     * Description:退款失败解冻账单
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addRefundAuditFailUnFrzeeBill(CrowdfundingSupportModel supportModel,String loanUser);
    
    
    /**
     * Description:退款成功后投资人账单
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addSupportUserRefundSucceeBill(CrowdfundingSupportModel supportModel,String supportUser);
    public UserBillModel getLatestBillByRedis(String userId);
}
