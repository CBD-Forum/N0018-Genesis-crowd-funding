/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingInvestServiceImpl.java 
 *
 * Created: [2015-5-20 下午3:01:42] by haolingfeng
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
 * ProjectName: crowdfund-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.crowdfunding.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDetailDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.quartz.util.QuartzUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestTransferService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description:股权众筹 --挂牌
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Service("crowdfundingInvestTransferService")
public class CrowdfundingInvestTransferServiceImpl implements ICrowdfundingInvestTransferService{

	@Resource
	private ICrowdfundingSupportDao crowdfundingSupportDao;
	
	@Resource
    private IBusinessConfigDao businessConfigDao;
	
	@Resource
    private ICrowdfundTransferDao crowdfundTransferDao;
	
	@Resource
    private ICrowdfundTransferDetailDao crowdfundTransferDetailDao;
	
	@Resource
    private StdScheduler scheduler;
    /**
     * 
     * Description:查询股权项目挂牌列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getCrowdfundingInvestTransferList(
			CrowdfundingSupportModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(crowdfundingSupportDao.getCrowdfundingInvestTransferList(model));
		result.setTotal(crowdfundingSupportDao.getCrowdfundingInvestTransferCount(model));
		return result;
	}
    /**
     * 
     * Description:保存股权项目挂牌
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveCrowdFundTransfer(CrowdfundTransferModel model) {
		// TODO Auto-generated method stub
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(model.getOrderNo());
		double oldPartMoney = supportModel.getPartMoney();
		model.setId(PKGenarator.getId());
		model.setTransferNo(PKGenarator.getTransferId());
		model.setApplyTime(new Date());
		model.setStatus(CrowdfundCoreConstants.transferStateFbd.transfering.getValue());
		model.setAuditState(CrowdfundCoreConstants.transferAuditState.auditing.getValue());
		model.setTransferMoney(Arith.mul(model.getPartMoney(),model.getTransferParts()));
		model.setTransferCorpus(Arith.mul(oldPartMoney,model.getTransferParts()));
		model.setOldPartsMoney(oldPartMoney);
		model.setActualTransferMoney(0.0);
		//查询债权转让服务费
		double transferFeeRatio =Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INVEST_TRANSFER_FEE).getCode()==null?"0.01":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INVEST_TRANSFER_FEE).getCode());
		//服务费收取规则  挂牌价-购买价= 盈利   盈利* 服务费比例
		double money=0.0;
		//判断购买价是否大于转让价
		if(model.getTransferMoney()>model.getTransferCorpus()){
			 money = Arith.sub(model.getTransferMoney(), model.getTransferCorpus());
		}else{
			money = Arith.sub(model.getTransferCorpus(), model.getTransferMoney());
		}
		model.setTransferFee(Arith.mul(money,transferFeeRatio));
		
		try {
			crowdfundTransferDao.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			 throw new ApplicationException("挂牌失败");
		}
		
	}
    /**
     * 
     * Description:查询股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getCrowdfundingTransferAuditList(
			CrowdfundTransferModel model) {
		// TODO Auto-generated method stub
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(crowdfundTransferDao.getCrowdfundingTransferAuditList(model));
		result.setTotal(crowdfundTransferDao.getCrowdfundingTransferAuditCount(model));
		return result;
	}
    /**
     * 
     * Description:查询股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getCrowdfundingTransferInfoList(
			CrowdfundTransferModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(crowdfundTransferDao.getCrowdfundingTransferInfoList(model));
		result.setTotal(crowdfundTransferDao.getCrowdfundingTransferInfoCount(model));
		return result;
	}
    /**
     * 
     * Description:是否同意
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateIsAgree(CrowdfundTransferModel transferModel) {
		// TODO Auto-generated method stub
		
		CrowdfundTransferModel model = crowdfundTransferDao.selectByPrimaryKey(transferModel.getId());
		transferModel.setStartTime(new Date());
		//结束日期
		try {
			transferModel.setDeadline(DateUtils.stringToDate(DateUtils.addDate(new Date(),model.getTransferDay(),Calendar.DATE,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		this.crowdfundTransferDao.updateBySelective(transferModel);
		if("0".equals(transferModel.getIsAgree())){
			//增加债权转让结束调度
	        String transferNo = model.getTransferNo();
	        String cronExpression = DateUtil.getSchedulerCronExpression(transferModel.getDeadline());
	        Map<String, String> parameter = new HashMap<String, String>();
	        parameter.put(QuartzJobConstants.PARAM_INVESTTRANSFER_FINANCE_END,transferNo);
	        QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_INVESTTRANSFER_FINANCE_END+"_"+transferNo, 
	                QuartzJobConstants.CLASS_INVESTTRANSFER_FINANCE_END, parameter, 
	                QuartzJobConstants.TRIGGER_INVESTTRANSFER_FINANCE_END+"_"+transferNo, cronExpression, 
	                QuartzJobConstants.DES_INVESTTRANSFER_FINANCE_END);
		}
	}
    /**
     * 
     * Description:查询首页挂牌列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getCrowdfundTransferDetailList(
			CrowdfundTransferModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(crowdfundTransferDao.getCrowdfundTransferDetailList(model));
		result.setTotal(crowdfundTransferDao.getCrowdfundTransferDetailCount(model));
		return result;
	}
    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getCrowdfundTransferDetail(
			CrowdfundTransferModel model) {
		// TODO Auto-generated method stub
		return this.crowdfundTransferDao.getCrowdfundTransferDetail(model);
	}
    /**
     * 
     * Description:查询成功购买的用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public List<Map<String, Object>> getCrowdfundTransferUserPayed(
			CrowdfundTransferDetailModel model) {
		// TODO Auto-generated method stub
		return this.crowdfundTransferDetailDao.getCrowdfundTransferUserPayed(model);
	}

}
