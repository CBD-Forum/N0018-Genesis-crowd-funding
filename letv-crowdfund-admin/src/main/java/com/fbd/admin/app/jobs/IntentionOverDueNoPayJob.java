package com.fbd.admin.app.jobs;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fbd.core.app.blockChain.service.IBlockChainReturnService;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.util.DateUtil;


@Component
public class IntentionOverDueNoPayJob {


    private static final Logger logger = LoggerFactory.getLogger(IntentionOverDueNoPayJob.class);
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IBlockChainReturnService blockChainReturnService;
	/**
	 * 意向金到期未支付任务(每天凌晨00:00执行调度任务) 
	 * @param userId
	 * @param investTransferModel
	 */
	@Scheduled(cron="0 00 00 * * ?")
	public void rechargeReconciliationTask(){
		try{
			String orderDate = DateUtil.date2Str(new Date(),DateUtil.DEFAULT_DATE_TIME_FORMAT);
			logger.info("============意向金到期未支付处理["+orderDate+"]开始==========");
			//查询需要处理的到期未支付尾款的项目
			CrowdfundingSupportModel model = new CrowdfundingSupportModel();
			List<CrowdfundingSupportModel> list = this.crowdfundingSupportDao.selectOverDueNoComplateList(model);
		    for(CrowdfundingSupportModel support:list){
		    	String orderId = support.getOrderId();
		    	this.blockChainReturnService.intentionPayReturnTransfer(orderId);
		    	Thread.sleep(3000);
		    }
			logger.info("============意向金到期未支付处理["+orderDate+"]结束==========");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
