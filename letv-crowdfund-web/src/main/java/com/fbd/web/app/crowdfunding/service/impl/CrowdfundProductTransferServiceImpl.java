package com.fbd.web.app.crowdfunding.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
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
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.crowdfunding.service.ICrowdfundProductTransferService;

@Service("crowdfundProductTransferService")
public class CrowdfundProductTransferServiceImpl implements
		ICrowdfundProductTransferService {
	
	
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundProductTransferServiceImpl.class);
   
    @Resource
    private ICrowdfundingDao crowdfundingDao;
	@Resource
	private ICrowdfundingSupportDao crowdfundingSupportDao ;

	@Resource
	private ICrowdfundProductTransferDao crowdfundProductTransferDao ;
	
	@Resource
	private IBusinessConfigDao businessConfigDao;
	
	@Resource
    private StdScheduler scheduler;
	
	/**
     * 
     * Description:查询可转让产品投资
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getCanTransferList(
			CrowdfundingSupportModel model) {
		
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundingSupportDao.getCanTransferList(model));
		result.setTotal(this.crowdfundingSupportDao.getCanTransferCount(model));
		return result;
	}
	
	/**
     * 
     * Description:查询可转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getCanTransferDetail(
			CrowdfundingSupportModel model) {
		return this.crowdfundingSupportDao.getCanTransferDetail(model);
	}

	/**
     * 
     * Description:保存产品项目转让
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveCrowdFundTransfer(CrowdfundProductTransferModel model) {
		try {
			if(model.getTransferAmt()==null){
				throw new ApplicationException("转让金额不能为空");
			}
			if(model.getTransferFee()==null){
				throw new ApplicationException("转让手续费不能为空");
			}
			CrowdfundingSupportModel supportModle = this.crowdfundingSupportDao.getByOrderId(model.getSupportNo());
			Map<String,Object> checkRsult=crowdfundProductTransferDao.checkTransferAmt(supportModle.getSupportAmt(),model.getTransferAmt());
			boolean judge=(Boolean) checkRsult.get("success");
			if(judge){
				double fee=(Double) checkRsult.get("fee");
				if(model.getTransferFee()!=fee){
					throw new ApplicationException("转让手续费异常,请联系客服！");
				}
				model.setId(PKGenarator.getId());
				model.setTransferNo(PKGenarator.getTransferId());
				model.setBackNo(supportModle.getBackNo());
				model.setLoanNo(supportModle.getLoanNo());
				model.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfering.getValue());
				model.setTransferTime(new Date());
				model.setTransFee(supportModle.getTransFee());
				
				//获取转让天数
				Integer transferDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.TRANSFER_PRODUCT_DAY).getCode());
				if(transferDay==null){
					throw new ApplicationException("获取产品转让天数失败,请联系客服");
				}
				Date transferEndTime = DateUtil.getDate(new Date(), transferDay);
				model.setTransferEndTime(transferEndTime);
				
				//增加调度
				Date financeEndDate = model.getTransferEndTime();
				//Date financeEndDate = DateUtil.getDate(new Date(), 1);
	            String cronExpression = DateUtil.getSchedulerCronExpression(financeEndDate);
	            Map<String, String> parameter = new HashMap<String, String>();
	            parameter.put(QuartzJobConstants.PARAM_CROWDFUND_TRANSFER_FINANCE_END,model.getTransferNo());
	            QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_CROWDFUND_TRANSFER_FINANCE_END+"_"+model.getTransferNo(), 
	                    QuartzJobConstants.CLASS_CROWDFUND_TRANSFER_FINANCE_END, parameter, 
	                    QuartzJobConstants.TRIGGER_CROWDFUND_TRANSFER_FINANCE_END+"_"+model.getTransferNo(), cronExpression, 
	                    QuartzJobConstants.DES_CROWDFUND_TRANSFER_FINANCE_END);
				
				this.crowdfundProductTransferDao.save(model);	
				
				CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(supportModle.getLoanNo());
				//发送转让申请提交成功信息
				this.sendProductTransferMessage(model, supportModle, crowd);
				
			}else{
				throw new ApplicationException("验证转让金额异常");
			}
		}catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("保存产品转让失败");
		}
	}
	
	
	
    /**
     * 
     * Description: 发送转让申请提交成功信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendProductTransferMessage(CrowdfundProductTransferModel model,CrowdfundingSupportModel supportModle,CrowdfundingModel crowd ){

       Map<String, String> params = new HashMap<String,String>();
       params.put("loanName",crowd.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("transferNo",model.getTransferNo());
       params.put("transferAmt",String.valueOf(Arith.format(model.getTransferAmt())));
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
        try{
            logger.info("发送转让申请提交成功站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_SUBMIT_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getTransferUser(), params);
            logger.info("发送转让申请提交成功站内信结束");
        }catch(Exception e){
            logger.error("发送转让申请提交成功站内信失败，"+e.getMessage());
        }
    }

	/**
     * 
     * Description:查询市场转让列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getCrowdfundTransferDetailList(
			CrowdfundProductTransferModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundProductTransferDao.getCrowdfundTransferDetailList(model));
		result.setTotal(this.crowdfundProductTransferDao.getCrowdfundTransferDetailCount(model));
		return result;
	}

	/**
     * 
     * Description:查询转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getCrowdfundTransferDetail(
			CrowdfundProductTransferModel model) {
		return this.crowdfundProductTransferDao.getCrowdfundTransferDetail(model);
	}

	public int getTransferDay() {
		//获取转让天数
		return Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.TRANSFER_PRODUCT_DAY).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.TRANSFER_PRODUCT_DAY).getCode());
	}

}
