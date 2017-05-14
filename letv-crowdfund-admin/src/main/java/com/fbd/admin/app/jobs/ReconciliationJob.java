package com.fbd.admin.app.jobs;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fbd.admin.app.recharge.service.IRechargeService;
import com.fbd.core.app.recharge.service.IRechargeReconciliationService;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;


@Component
public class ReconciliationJob {
    
	

    private static final Logger logger = LoggerFactory.getLogger(ReconciliationJob.class);
    
    
    @Resource
    private IRechargeReconciliationService rechargeReconciliationService;    
    
    
 
	/**
	 * 充值对账任务 
	 * @param userId
	 * @param investTransferModel
	 */
	@Scheduled(cron="0 30 09 * * ?")
	public void rechargeReconciliationTask(){
		try{
			
			String orderDate = DateUtils.dateToString(DateUtil.addDate(new Date(),-1), "yyyyMMdd");
			logger.info("=================对账订单日期:"+orderDate+"对账任务开始================");
			rechargeReconciliationService.rechargeReconciliation(orderDate,"sxyPay");
			logger.info("=================对账订单日期:"+orderDate+"对账任务结束================");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
