/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: SystemBillServiceImpl.java 
 *
 * Created: [2014-12-22 下午4:34:33] by haolingfeng
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

package com.fbd.core.app.bill.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bill.dao.ISystemBillDao;
import com.fbd.core.app.bill.model.SysTransferAcctModel;
import com.fbd.core.app.bill.model.SystemBillModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.util.BillUtil;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 平台账单
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("systemBillService")
public class SystemBillServiceImpl implements ISystemBillService {

	@Resource
	private ISystemBillDao systemBillDao;

	/**
	 * 
	 * Description: 交易查询列表
	 * 
	 * @param
	 * @return SearchResult<UserBillModel>
	 * @throws
	 * @Author haolingfeng Create Date: 2015-1-8 下午1:52:17
	 */
	public SearchResult<SystemBillModel> getSysBillList(SystemBillModel model) {
		if (model.getTradeStartTime() != null) {
			model.setTradeStartTime(DateUtil.getDayMin(model
					.getTradeStartTime()));
		}
		if (model.getTradeEndTime() != null) {
			model.setTradeEndTime(DateUtil.getDayMax(model.getTradeEndTime()));
		}
		SearchResult<SystemBillModel> result = new SearchResult<SystemBillModel>();
		result.setRows(this.systemBillDao.getList(model));
		result.setTotal(this.systemBillDao.getListCount(model));
		return result;
	}

	public SearchResult<Map<String, Object>> getSysBillList(
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
		return this.systemBillDao
				.getPage("selectCount", "selectList", paramMap);
	}

	/**
	 * 
	 * Description: 平台转账
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addTransferSystemBill(SysTransferAcctModel transfer) {
		String direction = FbdCoreConstants.tradeDirection.transfer.getValue();
		// 插入转出账单
		SystemBillModel sysOutBill = new SystemBillModel();
		sysOutBill.setChildAcctNo(transfer.getOutChildAcct());
		sysOutBill.setChildAcctType(transfer.getOutChildAcct());
		this.initSystemBill(sysOutBill,
				FbdCoreConstants.systemTradeType.transfer.getValue(),
				direction, transfer.getTransferAmt(), transfer.getOutUserId(),
				transfer.getOrderId());
		SystemBillModel latestBill = this.getLatestBill(sysOutBill.getUserId());
		sysOutBill.setBalance(latestBill.getBalance());
		sysOutBill.setSeqNum(latestBill.getSeqNum() + 1);
		sysOutBill.setOppositeSide(transfer.getInChildAcct());
		sysOutBill.setDetail("向" + transfer.getInChildAcct() + "转出"
				+ transfer.getTransferAmt() + "元");
		this.systemBillDao.save(sysOutBill);

		// 插入转入账单

		SystemBillModel sysInBill = new SystemBillModel();
		sysInBill.setChildAcctNo(transfer.getInChildAcct());
		sysInBill.setChildAcctType(transfer.getInChildAcct());
		this.initSystemBill(sysInBill,
				FbdCoreConstants.systemTradeType.transfer.getValue(),
				direction, transfer.getTransferAmt(), transfer.getInUserId(),
				transfer.getOrderId());
		SystemBillModel latestBill2 = this.getLatestBill(sysInBill.getUserId());
		sysInBill.setBalance(latestBill2.getBalance());
		sysInBill.setSeqNum(latestBill2.getSeqNum() + 1);
		sysInBill.setOppositeSide(transfer.getOutChildAcct());
		sysInBill.setDetail("由" + transfer.getOutChildAcct() + "转入"
				+ transfer.getTransferAmt() + "元");
		this.systemBillDao.save(sysInBill);
	}

	/**
	 * 
	 * Description: 平台充值
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addRechargeSystemBill(double tradeAmt, String tradeId) {
		String direction = FbdCoreConstants.tradeDirection.in.getValue();
		SystemBillModel sysBill = new SystemBillModel();
		sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_merdt"));
		sysBill.setChildAcctType(SpringPropertiesHolder
				.getProperty("sys_merdt"));
		this.initSystemBill(sysBill,
				FbdCoreConstants.systemTradeType.recharge.getValue(),
				direction, tradeAmt,
				SpringPropertiesHolder.getProperty("sysUserId"), tradeId);
		// 插入系统账单
		this.saveBill(sysBill);
	}

	/**
	 * 
	 * Description: 垫付充值手续费账单插入
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addRechargeFeeSystemBill(RechargeModel recharge) {
		String direction = FbdCoreConstants.tradeDirection.out.getValue();
		double tradeAmt = recharge.getRechargeFee();
		SystemBillModel sysBill = new SystemBillModel();
		sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_merdt"));
		sysBill.setChildAcctType(SpringPropertiesHolder
				.getProperty("sys_merdt"));
		this.initSystemBill(sysBill,
				FbdCoreConstants.systemTradeType.advanceFee.getValue(),
				direction, tradeAmt,
				SpringPropertiesHolder.getProperty("sysUserId"),
				recharge.getOrderId());
		// 插入系统账单
		this.saveBill(sysBill);
	}

	/**
	 * 
	 * Description: 平台活动支出账单插入
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午3:52:05
	 */
	public void addActivityPaySystemBill(double tradeAmt, String tradeId) {
		String direction = FbdCoreConstants.tradeDirection.out.getValue();
		SystemBillModel sysBill = new SystemBillModel();
		sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_basedt"));
		sysBill.setChildAcctType(SpringPropertiesHolder
				.getProperty("sys_basedt"));
		this.initSystemBill(sysBill,
				FbdCoreConstants.systemTradeType.activityPay.getValue(),
				direction, tradeAmt,
				SpringPropertiesHolder.getProperty("sysUserId"), tradeId);
		// 插入系统账单
		this.saveBill(sysBill);
	}

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
			String loanNo, String loanName) {
		String direction = FbdCoreConstants.tradeDirection.in.getValue();
		SystemBillModel sysBill = new SystemBillModel();
		sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_basedt"));
		sysBill.setChildAcctType(SpringPropertiesHolder
				.getProperty("sys_basedt"));
		this.initSystemBill(sysBill,
				FbdCoreConstants.systemTradeType.borrowFee.getValue(),
				direction, tradeAmt,
				SpringPropertiesHolder.getProperty("sysUserId"), tradeId);
		sysBill.setParentId(loanNo);
		sysBill.setDetail(loanName);
		// 插入系统账单
		this.saveBill(sysBill);
	}

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
			String tradeId, String oppositeSideInfo, String detail) {
		String direction = FbdCoreConstants.tradeDirection.out.getValue();
		SystemBillModel sysBill = new SystemBillModel();
		sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_dep"));
		sysBill.setChildAcctType(SpringPropertiesHolder.getProperty("sys_dep"));
		this.initSystemBill(sysBill,
				FbdCoreConstants.systemTradeType.reserverRepay.getValue(),
				direction, tradeAmt,
				SpringPropertiesHolder.getProperty("sysUserId"), tradeId);
		sysBill.setParentId(loanNo);
		sysBill.setDetail(detail);
		// 插入系统账单
		this.saveBill(sysBill);
	}

	/**
	 * 
	 * Description:平台记账 (只适用于进出，不支持冻结与解冻)
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2015-3-30 下午2:45:56
	 */
	public void addBill(String trandeType, double tradeAmt, String direction,
			String parentId, String detail, String trandeId) {
		SystemBillModel sysBill = new SystemBillModel();
		this.initSystemBill(sysBill, trandeType, direction, tradeAmt,
				SpringPropertiesHolder.getProperty("sysUserId"), trandeId);
		sysBill.setParentId(parentId);
		sysBill.setDetail(detail);
		this.saveBill(sysBill);
	}

	private void initSystemBill(SystemBillModel sysBill, String tradeType,
			String direction, double tradeAmt, String userId, String tradeId) {
		sysBill.setId(PKGenarator.getId());
		sysBill.setTradeType(tradeType);
		sysBill.setTradeDirection(direction);
		sysBill.setAmt(tradeAmt);
		sysBill.setUserId(userId);
		sysBill.setTradeTime(new Date());
		sysBill.setTradeId(tradeId);
	}

	/**
	 * 
	 * Description:查询最新的系统账单
	 * 
	 * @param
	 * @return UserBillModel
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-22 下午6:32:12
	 */
	private SystemBillModel getLatestBill(String userId) {
		SystemBillModel latestBill = this.systemBillDao.selectByUserId(userId);
		if (null == latestBill) {
			latestBill = new SystemBillModel();
			latestBill.setSeqNum(0);
			latestBill.setBalance(0.00);
		}
		return latestBill;
	}

	/**
	 * 
	 * Description:插入系统账单
	 * 
	 * @param
	 * @return void
	 * @throws
	 * @Author haolingfeng Create Date: 2014-12-25 上午9:40:30
	 */
	private synchronized void saveBill(SystemBillModel sysBill) {
		SystemBillModel latestBill = this.getLatestBill(sysBill.getUserId());
		double tempBalance = BillUtil.judgeDirection(sysBill.getAmt(),
				sysBill.getTradeDirection());
		sysBill.setBalance(Arith.add(latestBill.getBalance(), tempBalance));
		sysBill.setSeqNum(latestBill.getSeqNum() + 1);
		this.systemBillDao.save(sysBill);
	}

	/**
	 * 
	 * Description: 汇总平台账户数据
	 * 
	 * @param
	 * @return Map<String,Object>
	 * @throws
	 * @Author haolingfeng Create Date: 2015-3-14 下午6:12:11
	 */
	public Map<String, Object> getTotalData() {
		return this.systemBillDao.getTotalData();
	}

	public void addTransferSystemBill(Double transferFee, String transferNo,
			String investNo, String detail) {
		String direction = FbdCoreConstants.tradeDirection.in.getValue();
		SystemBillModel sysBill = new SystemBillModel();
		sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_basedt"));
		sysBill.setChildAcctType(SpringPropertiesHolder
				.getProperty("sys_basedt"));
		this.initSystemBill(sysBill,
				FbdCoreConstants.systemTradeType.transferFee.getValue(),
				direction, transferFee,
				SpringPropertiesHolder.getProperty("sysUserId"), transferNo);
		sysBill.setParentId(investNo);
		sysBill.setDetail(detail);
		// 插入系统账单
		this.saveBill(sysBill);
	}

	/**
     * Description: 项目分红 平台出账
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-1-25 下午4:34:34
     */
    public void addLoanShareBonusSystemBill(double tradeAmt, String tradeId,
            String oppositeSide) {
        String direction = FbdCoreConstants.tradeDirection.out.getValue();
        SystemBillModel sysBill = new SystemBillModel();
        sysBill.setChildAcctNo(SpringPropertiesHolder.getProperty("sys_basedt"));
        sysBill.setChildAcctType(SpringPropertiesHolder
                .getProperty("sys_basedt"));
        sysBill.setDetail("项目分红：投资编号【"+tradeId+"】,分红金额【"+tradeAmt+"】");
        sysBill.setOppositeSide(oppositeSide);
        this.initSystemBill(sysBill,
                FbdCoreConstants.systemTradeType.loan_share.getValue(),
                direction, tradeAmt,
                SpringPropertiesHolder.getProperty("sysUserId"), tradeId);
        // 插入系统账单
        this.saveBill(sysBill);
        
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.bill.service.ISystemBillService#addTransferFeeSystemBill(double, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void addTransferFeeSystemBill(double tradeAmt, String tradeId,
            String loanNo, String detail) {
        // TODO Auto-generated method stub
        String direction = FbdCoreConstants.tradeDirection.in.getValue();
        SystemBillModel sysBill = new SystemBillModel();
        this.initSystemBill(sysBill,
                FbdCoreConstants.systemTradeType.TRANSFER_FEE.getValue(),
                direction, tradeAmt,
                SpringPropertiesHolder.getProperty("sysUserId"), tradeId);
        sysBill.setParentId(loanNo);
        sysBill.setDetail(detail);
        // 插入系统账单
        this.saveBill(sysBill);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.bill.service.ISystemBillService#getWithDrawBalance()
     */
    @Override
    public  Map<String,Object> getWithDrawBalance() {
        // TODO Auto-generated method stub
        return systemBillDao.getWithDrawBalance();
    }
}
