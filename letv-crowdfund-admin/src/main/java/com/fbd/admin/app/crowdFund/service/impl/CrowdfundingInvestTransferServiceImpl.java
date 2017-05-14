/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
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

package com.fbd.admin.app.crowdFund.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.crowdFund.action.CrowdfundingAction;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingInvestTransferService;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDetailDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.quartz.util.QuartzUtils;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 * 
 * Description:股权众筹 --挂牌
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Service("crowdfundingInvestTransferService")
public class CrowdfundingInvestTransferServiceImpl implements ICrowdfundingInvestTransferService{
	
	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingInvestTransferServiceImpl.class);

	@Resource
	private ICrowdfundingSupportDao crowdfundingSupportDao;
	
	@Resource
    private IBusinessConfigDao businessConfigDao;
	
	@Resource
    private ICrowdfundTransferDao crowdfundTransferDao;
	
	@Resource
    private ICrowdfundTransferDetailDao crowdfundTransferDetailDao;
	
//	@Resource
//    private StdScheduler scheduler;
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
		double oldPartMoney = Arith.div(supportModel.getSupportAmt(), supportModel.getBuyNum());
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
		double money = Arith.sub(model.getTransferMoney(), model.getOldPartsMoney());
		model.setTransferFee(Arith.mul(money,transferFeeRatio));
		//结束日期
		try {
			model.setDeadline(DateUtils.stringToDate(DateUtils.addDate(new Date(),model.getTransferDay(),Calendar.DATE,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			crowdfundTransferDao.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			 throw new ApplicationException("挂牌失败");
		}
		
		 //增加债权转让结束调度
        String transferNo = model.getTransferNo();
        String cronExpression = DateUtil.getSchedulerCronExpression(model.getDeadline());
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put(QuartzJobConstants.PARAM_INVESTTRANSFER_FINANCE_END,transferNo);
//        QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_INVESTTRANSFER_FINANCE_END+"_"+transferNo, 
//                QuartzJobConstants.CLASS_INVESTTRANSFER_FINANCE_END, parameter, 
//                QuartzJobConstants.TRIGGER_INVESTTRANSFER_FINANCE_END+"_"+transferNo, cronExpression, 
//                QuartzJobConstants.DES_INVESTTRANSFER_FINANCE_END);
		
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
     * Description:审核转让
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public void updateTransfer(CrowdfundTransferModel model) {
		model.setAuditTime(new Date());
		this.crowdfundTransferDao.updateBySelective(model);
		
		if(model.getAuditState().equals("audit_refuse")){
			CrowdfundTransferModel aa = this.crowdfundTransferDao.selectByPrimaryKey(model.getId());
			Map<String, Object> bb = this.crowdfundTransferDao.getCrowdfundTransferDetail(aa);
			bb.put("reason",model.getAuditOpinion());
			this.sendAuditRefuseMessage(bb, "挂牌审核拒绝");
		}
		
	}
	
	 /**
     * 发送审核拒绝短信
     * @param model
     * @param msg
     */
    private void sendAuditRefuseMessage(Map<String, Object> bb,String msg){
       /* Map<String, String> params = new HashMap<String,String>();
        String loanName = bb.get("loanName").toString();
        String userId = bb.get("transferUser").toString();
        try{
            logger.info("发送"+msg+"手机短信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFERREFUSE_MOBILE;
            params.put("loanName",loanName);
            params.put("reason",bb.get("reason").toString());
            SendMessageUtil.sendMobileMessage(nodeCode, userId, params);
            logger.info("发送"+msg+"手机短信结束");
        }catch(Exception e){
            logger.error("发送"+msg+"手机短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送"+msg+"站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFERREFUSE_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, userId, params);
             logger.info("发送"+msg+"站内信结束");
         }catch(Exception e){
             logger.error("发送"+msg+"站内信失败，"+e.getMessage());
         }*/
     }
    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return CrowdfundTransferModel
     * @throws 
     * @Author wuwenbin<br/>
     */
	public CrowdfundTransferModel getCrowdfundingTransferDetial(
			String transferNo) {
		// TODO Auto-generated method stub
		return this.crowdfundTransferDao.selectModelByTransferNo(transferNo);
	}
    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return CrowdfundTransferModel
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateCrowdFundTransfer(CrowdfundTransferModel model) {
		// TODO Auto-generated method stub
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(model.getOrderNo());
		double oldPartMoney = Arith.div(supportModel.getSupportAmt(), supportModel.getBuyNum());
		model.setOldPartsMoney(oldPartMoney);
		//查询债权转让服务费
		double transferFeeRatio =Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INVEST_TRANSFER_FEE).getCode()==null?"0.01":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INVEST_TRANSFER_FEE).getCode());
		//服务费收取规则  挂牌价-购买价= 盈利   盈利* 服务费比例
		model.setTransferMoney(Arith.mul(model.getPartMoney(),model.getTransferParts()));
		double money = Arith.sub(model.getTransferMoney(), model.getOldPartsMoney());
		model.setTransferFee(Arith.mul(money,transferFeeRatio));
		//结束日期
		try {
			model.setDeadline(DateUtils.stringToDate(DateUtils.addDate(new Date(),model.getTransferDay(),Calendar.DATE,"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		this.crowdfundTransferDao.updateBySelective(model);
	}
    /**
     * 
     * Description:查询每个项目支持份数列表
     *
     * @param 
     * @return SearchResult <Map<String,Object>>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getSupportPartsDetailList(
			CrowdfundingSupportModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(crowdfundingSupportDao.getSupportPartsDetailList(model));
		result.setTotal(crowdfundingSupportDao.getSupportPartsDetailCount(model));
		return result;
	}
	
	public SearchResult<Map<String,Object>> getSupportPartsDetailListMap(Map<String, Object> paramMap){
   	 return crowdfundingSupportDao.getPage("selectSupportPartsDetailCount", "selectSupportPartsDetailList", paramMap);
   }
	
    /**
     * 查询可转让份数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     */
	public long getCanTransferParts(String orderNo) {
		return this.crowdfundTransferDao.getCanTransferParts(orderNo);
	}

}
