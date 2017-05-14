/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserBillServiceImpl.java 
 *
 * Created: [2014-12-22 下午3:51:26] by haolingfeng
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

package com.fbd.core.app.bill.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bill.dao.IUserBillDao;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.bill.util.BillUtil;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.DateMorpherEx;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户账单
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("userBillService")
public class UserBillServiceImpl implements IUserBillService {
    
    
    private static final Logger logger = LoggerFactory.getLogger(UserBillServiceImpl.class);
      
	@Resource
	private IUserBillDao userBillDao;
	@Resource
	private IUserDao userDao;
	@Resource
	private ISystemBillService systemBillService;
	@Resource
	private IRedisDao redisDao;

	/**
	 * 
	 * Description: 用户充值账单插入
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addRechargeUserBill(RechargeModel recharge) {
		String direction = FbdCoreConstants.tradeDirection.in.getValue();
		double tradeAmt = recharge.getRechargeAmt();
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.recharge.getValue(), direction,
				tradeAmt, recharge.getUserId(), recharge.getOrderId());
		
		UserBillModel latestBill = this.getLastUserBillByUserId(recharge.getUserId());
		        
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + recharge.getUserId()
					+ "]的余额小于0");
		}
		userBill.setBalance(newBalance);
		userBill.setFrozenAmt(latestBill.getFrozenAmt());
		userBill.setDetail("用户充值，充值单号："+recharge.getOrderId());
		this.userBillDao.save(userBill);
		
        //更新缓存
		this.setLatestBillRedis(userBill);
	}

	/**
	 * 
	 * Description: 用户充值手续费账单插入
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addRechargeFeeUserBill(RechargeModel recharge) {
		String direction = FbdCoreConstants.tradeDirection.out.getValue();
		double tradeAmt = recharge.getRechargeFee();
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.rechargeFee.getValue(),
				direction, tradeAmt, recharge.getUserId(),
				recharge.getOrderId());
		double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		
		
        UserBillModel latestBill = this.getLastUserBillByUserId(recharge.getUserId());
       
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + recharge.getUserId()
					+ "]的余额小于0");
		}
		userBill.setBalance(newBalance);
		userBill.setFrozenAmt(latestBill.getFrozenAmt());
		this.userBillDao.save(userBill);
        //更新缓存
		this.setLatestBillRedis(userBill);
	}
	
	
    /**
     * 用户充值冻结账单
     */
    @Override
    public void addUserRechargeFreezeBill(RechargeModel recharge) {
        
        String direction = FbdCoreConstants.tradeDirection.freeze.getValue();
        double tradeAmt = recharge.getRechargeAmt();// 金额
        UserBillModel userBill = new UserBillModel();
        String userId = recharge.getUserId();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.recharge_freeze.getValue(),
                direction, tradeAmt,userId,
                recharge.getOrderId());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);

        double tempFrozenAmt = latestBill.getFrozenAmt();
        double newFrozenAmt = 0.0;
        if(tempFrozenAmt<0){
            newFrozenAmt = tradeAmt;
        }else{
            newFrozenAmt = (tradeAmt+tempFrozenAmt);
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        // 变更余额=原金额
        userBill.setBalance(latestBill.getBalance());
        userBill.setDetail("用户充值成功冻结金额,充值单号："+recharge.getOrderId());
        //写人流水
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    /**
     * 用户充值解冻账单
     */
    @Override
    public void addUserRechargeUnFreezeBill(RechargeModel recharge) {
        String direction = FbdCoreConstants.tradeDirection.unfreeze.getValue();
        double tradeAmt = recharge.getRechargeAmt();// 金额
        UserBillModel userBill = new UserBillModel();
        String userId = recharge.getUserId();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.recharge_unFreeze.getValue(),
                direction, tradeAmt,userId,
                recharge.getOrderId());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + recharge.getUserId()
                    + "]的余额小于0");
        }
        userBill.setBalance(newBalance);
        userBill.setFrozenAmt(latestBill.getFrozenAmt()-tempBalance);
        userBill.setDetail("用户充值解冻，充值单号："+recharge.getOrderId());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
       
    }
    
 
	
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
            String oppositeSideInfo, String userId) {
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        double tradeAmt = invest.getSupportAmt()+invest.getTransFee();// 投资充值金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.investCash.getValue(),
                direction, tradeAmt, userId, invest.getOrderId());
        userBill.setParentId(invest.getLoanNo());
        userBill.setDetail(loanName);
        userBill.setOppositeSide(oppositeSideInfo);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);      
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newFrozenAmt = Arith.add(latestBill.getFrozenAmt(), tempBalance);
        if (newFrozenAmt.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的冻结余额小于0");
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        userBill.setBalance(latestBill.getBalance());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
 
	
	  /**
     * 
     * Description: 借款人放款账单
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-5 下午3:51:59
     */
     public void addBorrowerBill(String tradeId, String loanName,
            String oppositeSideInfo, String userId,double tradeAmt) {
        String direction = FbdCoreConstants.tradeDirection.in.getValue();
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.investCash.getValue(),
                direction, tradeAmt, userId, tradeId);
        userBill.setParentId(tradeId);
        userBill.setDetail("项目【"+loanName+"】放款入账");
        userBill.setOppositeSide(oppositeSideInfo);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        // 变更余额
        userBill.setBalance(newBalance);
        userBill.setFrozenAmt(latestBill.getFrozenAmt());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }

	
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
            String loanName, String userId, double fee) {
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        double tradeAmt = fee;
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.borrowFee.getValue(), direction,
                tradeAmt, userId, loanNo);
        userBill.setDetail(loanName);
        userBill.setParentId(loanNo);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        // 变更余额
        userBill.setBalance(newBalance);
        userBill.setFrozenAmt(latestBill.getFrozenAmt());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }

	/**
	 * 
	 * Description:插入取现冻结记录
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
	 */
	public void addWithDrawFreezeUserBill(WithDrawModel withDraw) {
		String direction = FbdCoreConstants.tradeDirection.freeze.getValue();
		String userId = withDraw.getUserId();
		// 冻结金额
		double tradeAmt = (withDraw.getAmt() > withDraw.getActualMoney()) ? withDraw
				.getAmt() : withDraw.getActualMoney();

		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.withdrawFreeze.getValue(),
				direction, tradeAmt, userId, withDraw.getOrderId());
		
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
		
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的余额小于0");
		}
		// 记录冻结金额
		userBill.setFrozenAmt(Arith.add(latestBill.getFrozenAmt(), tradeAmt));
		userBill.setBalance(newBalance);
		this.userBillDao.save(userBill);
        //更新缓存
		this.setLatestBillRedis(userBill);
	}

	/**
	 * 
	 * Description:插入取现账单
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
	 */
	public void addWithDrawUserBill(WithDrawModel withDraw, double tradeAmt) {
		// 插入取现账单
		this.addWithDrawAmtUserBill(withDraw, tradeAmt);
		// 插入取现服务费账单
		this.addWithDrawFeeUserBill(withDraw);
	}

	/**
	 * Description: 取消提现账单
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author dongzhongwei Create Date: 2015-5-6 上午9:44:32
	 */
	public void addCancelWithDrawUserBill(WithDrawModel withDraw) {
		// 取现时间交易金额
		double tradeAmt = withDraw.getActualMoney();
		// 交易方向
		String direction = FbdCoreConstants.tradeDirection.in.getValue();
		// 用户名
		String userId = withDraw.getUserId();
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.withdrawRefuse.getValue(),
				direction, tradeAmt, userId, withDraw.getOrderId());
		userBill.setDetail(withDraw.getAuditOpinion());
		// 查询最新的用户账单
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);	
		// 最新余额查询
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的余额小于0");
		}
		userBill.setFrozenAmt(Arith.add(latestBill.getFrozenAmt(), -tradeAmt));
		userBill.setBalance(newBalance);
		this.userBillDao.save(userBill);
        //更新缓存
		this.setLatestBillRedis(userBill);
	}
	
	
	   /**
     * Description:  提现失败账单
     * 
     * @param
     * @return void
     * @throws
     * @Author dongzhongwei Create Date: 2015-5-6 上午9:44:32
     */
    public void addWithDrawFailUserBill(WithDrawModel withDraw) {
        // 取现时间交易金额
        double tradeAmt = withDraw.getActualMoney();
        // 交易方向
        String direction = FbdCoreConstants.tradeDirection.in.getValue();
        // 用户名
        String userId = withDraw.getUserId();
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.withdrawFail.getValue(),
                direction, tradeAmt, userId, withDraw.getOrderId());
        userBill.setDetail(withDraw.getAuditOpinion());
        // 查询最新的用户账单
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        // 最新余额查询
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        userBill.setFrozenAmt(Arith.add(latestBill.getFrozenAmt(), -tradeAmt));
        userBill.setBalance(newBalance);
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }

	/**
	 * 
	 * Description:插入取现账单
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
	 */
	private void addWithDrawAmtUserBill(WithDrawModel withDraw, double tradeAmt) {
		String direction = FbdCoreConstants.tradeDirection.out.getValue();
		String userId = withDraw.getUserId();
		// 取现金额
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.withdraw.getValue(), direction,
				tradeAmt, userId, withDraw.getOrderId());
		
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
		
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newFrozenBalance = Arith.add(latestBill.getFrozenAmt(),
				tempBalance);
		if (newFrozenBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的冻结余额小于0");
		}
		userBill.setFrozenAmt(newFrozenBalance);
		userBill.setBalance(latestBill.getBalance());
		this.userBillDao.save(userBill);
	      
        //更新缓存
		this.setLatestBillRedis(userBill);
	}

	/**
	 * 
	 * Description:插入取现服务费账单
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-25 下午6:23:10
	 */
	public void addWithDrawFeeUserBill(WithDrawModel withDraw) {
		String direction = FbdCoreConstants.tradeDirection.out.getValue();
		String userId = withDraw.getUserId();
		// 冻结金额
		double tradeAmt = withDraw.getFee();
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.withdrawFee.getValue(),
				direction, tradeAmt, userId, withDraw.getOrderId());
		
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
		
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newFrozenBalance = Arith.add(latestBill.getFrozenAmt(),
				tempBalance);
		if (newFrozenBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的冻结余额小于0");
		}
		// 记录冻结金额
		userBill.setFrozenAmt(newFrozenBalance);
		userBill.setBalance(latestBill.getBalance());
		this.userBillDao.save(userBill);
		
        //更新缓存
		this.setLatestBillRedis(userBill);
  
	}

	/**
	 * 
	 * Description: 用户推荐奖励
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addRecommendRewardUserBill(double tradeAmt, String userId,
			String tradeId, String recommandType) {
		String userTradeType = recommandType
				.equals(FbdCoreConstants.invitorType.direct) ? FbdCoreConstants.userTradeType.directRecommendReward
				.getValue()
				: FbdCoreConstants.userTradeType.indirectRecommendReward
						.getValue();

		String direction = FbdCoreConstants.tradeDirection.in.getValue();
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill, userTradeType, direction, tradeAmt, userId,
				tradeId);
     
		UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的余额小于0");
		}
		userBill.setBalance(newBalance);
		userBill.setFrozenAmt(latestBill.getFrozenAmt());
		this.userBillDao.save(userBill);
        //更新缓存
		this.setLatestBillRedis(userBill);
	}

	/**
	 * 
	 * Description: 用户调账账单插入
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addAdjustAccountUserBill(AdjustAccountModel adjustModel) {
		UserBillModel userBill = new UserBillModel();
		double tradeAmt = adjustModel.getAdjustAmt();
		String direction = adjustModel.getAdjustType();
		String userId = adjustModel.getUserId();
		this.initUserBill(userBill,
				FbdCoreConstants.userTradeType.reviseBill.getValue(),
				direction, tradeAmt, userId, adjustModel.getOrderId());

		UserBillModel latestBill = this.getLastUserBillByUserId(userId);
		
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的余额小于0");
		}
		userBill.setBalance(newBalance);
		userBill.setFrozenAmt(latestBill.getFrozenAmt());
		this.userBillDao.save(userBill);
		
        //更新缓存
		this.setLatestBillRedis(userBill);		
	}

	/**
	 * 
	 * Description:用户记账 (只适用于进出，不支持冻结与解冻)
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2015-3-30 下午2:45:56
	 */
	public void addBill(String userId, String trandeType, double tradeAmt,
			String direction, String parentId, String detail,
			String oppositeSideInfo, String trandeId) {
		UserBillModel userBill = new UserBillModel();
		this.initUserBill(userBill, trandeType, direction, tradeAmt, userId,
				trandeId);
		userBill.setParentId(parentId);
		userBill.setDetail(detail);
		userBill.setOppositeSide(oppositeSideInfo);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
		Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
		Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
		if (newBalance.compareTo(0.00) < 0) {
			throw new ApplicationException("用户[" + userId + "]的余额小于0");
		}
		userBill.setFrozenAmt(latestBill.getFrozenAmt());
		userBill.setBalance(newBalance);
		this.userBillDao.save(userBill);
		//更新缓存
		this.setLatestBillRedis(userBill);
	}

	private void initUserBill(UserBillModel userBill, String tradeType,
			String direction, double tradeAmt, String userId, String tradeId) {
		userBill.setId(PKGenarator.getId());
		userBill.setTradeType(tradeType);
		userBill.setTradeDirection(direction);
		userBill.setAmt(tradeAmt);
		userBill.setUserId(userId);
		userBill.setTradeTime(new Date());
		userBill.setTradeId(tradeId);
	}
    private byte[] lock = new byte[0];

	/**
	 * 
	 * Description:查询最新的用户账单
	 * 
	 * @param
	 * @return UserBillModel
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午6:32:12
	 */
	public  UserBillModel getLatestBill(String userId) {
	    synchronized(lock){
    		UserBillModel latestBill = this.userBillDao.selectByUserId(userId);
    		if (null == latestBill) {
    			latestBill = new UserBillModel();
    			latestBill.setSeqNum(0);
    			latestBill.setBalance(0.00);
    			latestBill.setFrozenAmt(0.0);
    		}
    		this.setLatestBillRedis(latestBill);
    		return latestBill;
	    }
	}
	
	
	/**
	 * 
	 * Description: 验证余额是否足够操作
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-25 上午11:01:41
	 */
	public void checkBalance(double checkAmt, String userId) {
		// 验证账户余额
		UserBillModel bill = this.getLatestBill(userId);
		if (checkAmt > bill.getBalance()) {
			throw new ApplicationException("对不起，您的账户余额不足");
		}
	}

	public void checkFreezeAmt(double checkAmt, String userId) {
		// 验证账户余额
		UserBillModel bill = this.getLatestBill(userId);
		if (checkAmt > bill.getFrozenAmt()) {
			throw new ApplicationException("对不起，您的账户余额不足");
		}
	}

	/**
	 * 
	 * Description: 交易查询列表
	 * 
	 * @param
	 * @return SearchResult<UserBillModel>
	 * @throws
	 * @Author haolingfeng Create Date: 2015-1-8 下午1:52:17
	 */
	public SearchResult<UserBillModel> getUserBillList(UserBillModel model) {
		if (model.getTradeStartTime() != null) {
			model.setTradeStartTime(DateUtil.getDayMin(model
					.getTradeStartTime()));
		}
		if (model.getTradeEndTime() != null) {
			model.setTradeEndTime(DateUtil.getDayMax(model.getTradeEndTime()));
		}
		SearchResult<UserBillModel> result = new SearchResult<UserBillModel>();
		result.setRows(this.userBillDao.getList(model));
		result.setTotal(this.userBillDao.getListCount(model));
		return result;
	}

	public SearchResult<Map<String, Object>> getUserBillList(
			Map<String, Object> paramMap) {
		Object tradeStartTime = paramMap.get("tradeStartTime");
		if (null != tradeStartTime
				&& !StringUtil.isEmpty(tradeStartTime.toString())) {
			Date start = DateUtil.str2Date(tradeStartTime.toString(),
					DateUtil.DEFAULT_DATE_TIME_FORMAT);
			paramMap.put("tradeStartTime", DateUtil.getDayMin(start));
		}
		Object tradeEndTime = paramMap.get("tradeEndTime");
		if (null != tradeEndTime
				&& !StringUtil.isEmpty(tradeEndTime.toString())) {
			Date end = DateUtil.str2Date(tradeEndTime.toString(),
					DateUtil.DEFAULT_DATE_TIME_FORMAT);
			paramMap.put("tradeEndTime", DateUtil.getDayMin(end));
		}
		return this.userBillDao.getPage("selectCount", "selectList", paramMap);
	}

	/**
	 * 
	 * Description: 交易查询列表
	 * 
	 * @param
	 * @return SearchResult<UserBillModel>
	 * @throws
	 * @Author haolingfeng Create Date: 2015-1-8 下午1:52:17
	 */
	public List<UserBillModel> getList(UserBillModel model) {
		if (model.getTradeStartTime() != null) {
			model.setTradeStartTime(DateUtil.getDayMin(model
					.getTradeStartTime()));
		}
		if (model.getTradeEndTime() != null) {
			model.setTradeEndTime(DateUtil.getDayMax(model.getTradeEndTime()));
		}
		return this.userBillDao.getList(model);
	}

	 
    public void addBuyTransferUserBill(String userId,
            CrowdfundTransferDetailModel investTransferModel) {
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        double tradeAmt = investTransferModel.getBuyMoney();
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.buyTransfer.getValue(), direction,
                tradeAmt, userId, investTransferModel.getTransferNo());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId
                    + "]的余额小于0");
        }
        userBill.setBalance(newBalance);
        userBill.setFrozenAmt(latestBill.getFrozenAmt());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }

    /**
     * Description: 项目分红投资人进账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:36:41
     */
    public void addLoanShareBonusUserBill(double tradeAmt, String userId,
            String tradeId) {
        String direction = FbdCoreConstants.tradeDirection.in.getValue();
        UserBillModel userBill = new UserBillModel();
        userBill.setDetail("项目分红：投资编号【"+tradeId+"】,分红金额【"+tradeAmt+"】");
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.loan_share.getValue(),
                direction, tradeAmt, userId, tradeId);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        userBill.setBalance(newBalance);
        userBill.setFrozenAmt(latestBill.getFrozenAmt());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
        
    }
    /**
     * 从redis获取最新的流水记录
     * Description: 
     *
     * @param 
     * @return UserBillModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年11月9日 下午3:02:03
     */
    public UserBillModel getLatestBillByRedis(String userId){
        String userbillStr = this.redisDao.get("userBill_"+userId); 
        UserBillModel model =null;
        if(userbillStr!=null && !"".equals(userbillStr)){
            model =  DateMorpherEx.JsonToBean(UserBillModel.class, userbillStr);
            return model;  
        }
        return null;
    }
    /**
     * 设置最新的数据到redis
     * Description: 
     *
     * @param 
     * @return UserBillModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年11月9日 下午3:02:29
     */
    public void setLatestBillRedis(UserBillModel model){
        String userbillStr = JsonHelper.getStrFromObject(model);

       this.redisDao.set("userBill_"+model.getUserId(), userbillStr);
       
    }
    /**
     * Description: 添加购买转让冻结账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    public  void  addByUserTransferFreeze(CrowdfundProductTransferModel model) {
        String direction = FbdCoreConstants.tradeDirection.freeze.getValue();
        String userId = model.getBuyUser();
        // 冻结金额
        double tradeAmt = model.getTransferAmt()+model.getTransFee();
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.buyTransferFreeze.getValue(),
                direction, tradeAmt, userId, model.getTransferNo());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(userId); 
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        // 记录冻结金额
        userBill.setFrozenAmt(Arith.add(latestBill.getFrozenAmt(), tradeAmt));
        userBill.setBalance(newBalance);
        this.userBillDao.save(userBill);
        //更新缓存
        setLatestBillRedis(userBill);
    }
    
    
    
    
    /**
     * Description: 添加购买转让扣除冻结账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    public void addByUserTransferBill(CrowdfundProductTransferModel model) {
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        double tradeAmt = Arith.add(model.getTransferAmt(), model.getTransFee());// 购买转让金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.buyTransfer.getValue(),
                direction, tradeAmt, model.getBuyUser(), model.getSupportNo());
        userBill.setParentId(model.getLoanNo());
        userBill.setDetail("用户购买转让");
 
        UserBillModel latestBill = this.getLastUserBillByUserId(model.getBuyUser());
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newFrozenAmt = Arith.add(latestBill.getFrozenAmt(), tempBalance);
        if (newFrozenAmt.compareTo(0.00) < 0) {
            throw new ApplicationException("冻结金额为："+latestBill.getFrozenAmt()+"==需要解冻的金额"+tradeAmt +"= 用户[" + model.getBuyUser() + "]的冻结余额不足");
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        userBill.setBalance(latestBill.getBalance());
        this.setLatestBillRedis(userBill);
        this.userBillDao.save(userBill);
        
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    
    /**
     * Description: 添加购买转让审核失败解冻账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    public void addByUserTransferUnfreeze(CrowdfundProductTransferModel model) {
        String direction = FbdCoreConstants.tradeDirection.unfreeze.getValue();
        String userId = model.getBuyUser();
        double tradeAmt = model.getTransferAmt();// 金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.TRANSFER_UNFREEZE.getValue(),
                direction, tradeAmt, userId, model.getSupportNo());
        userBill.setParentId(model.getLoanNo());
        String detail = "转让审核失败解冻金额";
        userBill.setDetail(detail);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newFrozenAmt = Arith
                .add(latestBill.getFrozenAmt(), -tempBalance);
        if (newFrozenAmt.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的冻结余额小于0");
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        // 变更余额=余额+交易金额
        userBill.setBalance(Arith.add(latestBill.getBalance(), tempBalance));
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    

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
            String orderId , double tradeAmt,String userId) {
        String direction = FbdCoreConstants.tradeDirection.freeze.getValue();
         
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.investFreeze.getValue(),
                direction, tradeAmt, userId, orderId);
        
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        // 记录冻结金额
        userBill.setFrozenAmt(Arith.add(latestBill.getFrozenAmt(), tradeAmt));
        userBill.setBalance(newBalance);
        userBill.setDetail(loan.getLoanName()+"_"+loan.getLoanNo());
        this.userBillDao.save(userBill);
        
        //更新缓存
        this.setLatestBillRedis(userBill);
    }

    /**
     * Description: 用户同意分红冻结金额
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 下午4:31:40
     */
    public void addUserBonusFreeze(CrowdfundBonusModel model,String userId) {
        String direction = FbdCoreConstants.tradeDirection.freeze.getValue();
        double tradeAmt = model.getBonusAmt();
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.loan_bonus_freeze.getValue(),
                direction, tradeAmt, userId, model.getLoanNo());

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newBalance = Arith.add(latestBill.getBalance(), tempBalance);
        if (newBalance.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的余额小于0");
        }
        // 记录冻结金额
        userBill.setFrozenAmt(Arith.add(latestBill.getFrozenAmt(), tradeAmt));
        userBill.setBalance(newBalance);
        userBill.setDetail("借款人分红冻结金额_项目编号"+model.getLoanNo());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    
    /**
     * Description: 后台审核分红通过扣除冻结账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    public void addAuditBonusPassBill(CrowdfundBonusModel model,String userId) {
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        double tradeAmt = model.getBonusAmt();// 分红金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.loan_bonus_out.getValue(),
                direction, tradeAmt, userId, model.getLoanNo());
        userBill.setParentId(model.getLoanNo());
        userBill.setDetail("项目分红审核通过扣除项目方金额_项目编号"+model.getLoanNo());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        
        logger.info("========>latestBill.getFrozenAmt():"+latestBill.getFrozenAmt());
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        logger.info("========>tempBalance:"+tempBalance);
        Double newFrozenAmt = Arith.add(latestBill.getFrozenAmt(), tempBalance);
        logger.info("========>tempBalance:"+newFrozenAmt);
        if (newFrozenAmt.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的冻结余额小于0");
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        userBill.setBalance(latestBill.getBalance());
        this.userBillDao.save(userBill);
        
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    
    /**
     * Description: 项目分红审核失败解冻账单
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 上午11:39:42
     */
    public void addAuditRefuseUnfreeze(CrowdfundBonusModel model,String userId) {
        String direction = FbdCoreConstants.tradeDirection.unfreeze.getValue();
        double tradeAmt = model.getBonusAmt();// 金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.loan_bonus_unfreeze.getValue(),
                direction, tradeAmt, userId, model.getLoanNo());
        userBill.setParentId(model.getLoanNo());
        String detail = "项目分红审核失败解冻项目方金额_"+model.getLoanNo();
        userBill.setDetail(detail);

        UserBillModel latestBill = this.getLastUserBillByUserId(userId);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newFrozenAmt = Arith
                .add(latestBill.getFrozenAmt(), -tempBalance);
        if (newFrozenAmt.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + userId + "]的冻结余额小于0");
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        // 变更余额=余额+交易金额
        userBill.setBalance(Arith.add(latestBill.getBalance(), tempBalance));
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    
    /**
     * 查询用户资金信息
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     */
    public Map<String,Object>selectUserCapitalInfo(String userId){
        return this.userBillDao.selectUserCapitalInfo(userId);
    }

    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return UserBillModel
     * @throws
     */
    public UserBillModel getUserBillDetail(UserBillModel model) {
        return this.userBillDao.getUserBillDetail(model);
    }
    
    /**
     * Description:退款项目方冻结账单
     * @return void
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addLoanRefundFrzeeBill(CrowdfundingSupportModel supportModel,String loanUser){        
        
        String direction = FbdCoreConstants.tradeDirection.freeze.getValue();
        double tradeAmt = supportModel.getSupportAmt()+supportModel.getTransFee();// 金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.refund_freeze.getValue(),
                direction, tradeAmt, loanUser, supportModel.getOrderId());
        userBill.setParentId(supportModel.getOrderId());
        String detail = "项目同意退款冻结资金_"+supportModel.getOrderId();
        userBill.setDetail(detail);
        
        UserBillModel latestBill = this.getLastUserBillByUserId(loanUser);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newFrozenAmt = Arith.add(latestBill.getFrozenAmt(), -tempBalance);
        if (newFrozenAmt.compareTo(0.00) < 0) {
            throw new ApplicationException("用户[" + loanUser + "]的冻结余额小于0");
        }
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        // 变更余额=余额+交易金额
        userBill.setBalance(Arith.add(latestBill.getBalance(), tempBalance));
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    /**
     * Description:退款成功后项目方账单
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addLoanRefundSucceeBill(CrowdfundingSupportModel supportModel,String loanUser){
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        double tradeAmt = supportModel.getSupportAmt()+supportModel.getTransFee();// 金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.refund_success.getValue(),
                direction, tradeAmt,loanUser,
                supportModel.getOrderId());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(loanUser);
        
        Double newFrozenAmt = Arith.add(latestBill.getFrozenAmt(), -tradeAmt);
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        userBill.setFrozenAmt(latestBill.getFrozenAmt());
        userBill.setBalance(latestBill.getBalance());
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    /**
     * Description:退款失败解冻账单
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addRefundAuditFailUnFrzeeBill(CrowdfundingSupportModel supportModel,String loanUser){
        
        String direction = FbdCoreConstants.tradeDirection.in.getValue();
        double tradeAmt = supportModel.getSupportAmt()+supportModel.getTransFee();// 金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.refund_fail.getValue(),
                direction, tradeAmt,loanUser,
                supportModel.getOrderId());
        
        UserBillModel latestBill = this.getLastUserBillByUserId(loanUser);

        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        Double newFrozenAmt = Arith .add(latestBill.getFrozenAmt(), -tempBalance);
        
        // 变更冻结金额=冻结金额-交易金额
        userBill.setFrozenAmt(newFrozenAmt);
        // 变更余额=余额+交易金额
        userBill.setBalance(Arith.add(latestBill.getBalance(), tempBalance));
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    
    /**
     * Description:退款成功后投资人账单
     * @param 
     * @return void
     * @throws 
     * Create Date: 2016-8-30 下午4:46:58
     */
    public void addSupportUserRefundSucceeBill(CrowdfundingSupportModel supportModel,String supportUser){
        
        String direction = FbdCoreConstants.tradeDirection.in.getValue();
        double tradeAmt = supportModel.getSupportAmt()+supportModel.getTransFee();// 金额
        UserBillModel userBill = new UserBillModel();
        this.initUserBill(userBill,
                FbdCoreConstants.userTradeType.refund_success.getValue(),
                direction, tradeAmt,supportUser,
                supportModel.getOrderId());
        UserBillModel latestBill = this.getLastUserBillByUserId(supportUser);
        
        Double tempBalance = BillUtil.judgeDirection(tradeAmt, direction);
        // 变更余额=余额+交易金额
        userBill.setDetail("审核退款通过退款，支持编号："+supportModel.getOrderId());
        userBill.setBalance(Arith.add(latestBill.getBalance(), tempBalance));
        this.userBillDao.save(userBill);
        //更新缓存
        this.setLatestBillRedis(userBill);
    }
    
    /**
     * 查询用户最新账单
     * Description: 
     * @param 
     * @return UserBillModel
     * @throws 
     * Create Date: 2016-12-14 上午11:56:40
     */
    private UserBillModel getLastUserBillByUserId(String userId){
        UserBillModel latestBill = null;
        try{
            latestBill = this.getLatestBillByRedis(userId);
            if(latestBill == null){
                logger.info("=====>redis中没有查询到账单信息，从数据库中获取");
                latestBill = this.getLatestBill(userId);
            }
        }catch(Exception e){
            e.printStackTrace();
            latestBill = this.getLatestBill(userId);
        }
        return latestBill;
    } 

    
    
    public static void main(String[]args){
        String str =" {\"amt\":400,\"balance\":68942.06,\"tradeEndTime\":\"\",\"tradeTime\":\"2016-11-10\",\"frozenAmt\":8626,\"id\":\"60659c3ebb694433a785fe4e779de738\",\"parentId\":\"2016110115510912\",\"seqNum\":555,\"tradeId\":\"2016110162192093\",\"userId\":\"1kbr231315\"}";
    
       UserBillModel  model =  DateMorpherEx.JsonToBean(UserBillModel.class, str);
       System.out.println(model);
    }
}
