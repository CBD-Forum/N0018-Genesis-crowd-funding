package com.fbd.admin.app.jobs;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.user.service.ICrowdfundUserPrizeService;
import com.fbd.core.util.DateUtil;

/**
 * 
 * @author Administrator
 * 抽奖定时任务
 */
@Component
public class LoanUserPrizeJob {
	private static final Logger logger = LoggerFactory.getLogger(LoanUserPrizeJob.class);
	@Resource
	private ICrowdfundUserPrizeService crowdfundUserPrizeService;
	@Resource
	private ICrowdfundingDao crowdfundingDao;
	@Resource
	private ICrowdfundingBackSetDao crowdfundingBackSetDao;
	/**
	 * 抽奖
	 */
	@Scheduled(cron="30 31 9 * * ?")
	public void luckDraw(){
		logger.info("---------------执行抽奖定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")------");
		CrowdfundingModel model=new CrowdfundingModel();
		model.setQueryMethod("waitPrize");
		List<Map<String,Object>> list=crowdfundingDao.getList(model);
		for(Map<String,Object> m:list){
			String loanNo=(String) m.get("loanNo");
			CrowdfundingBackSetModel backModel=crowdfundingBackSetDao.queryIsPrizeDrawFlag(loanNo);
			if(backModel!=null){
				logger.info("---------------操作抽奖定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")逻辑 loanNo:"+loanNo+";回报编号:"+backModel.getBackNo()+";------");
				crowdfundUserPrizeService.saveCrowdfundUserPrize(loanNo, backModel.getBackNo());	
			}else{
				logger.info("---------------操作抽奖定时任务("+DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT)+")逻辑错误，因为没找到合适的回报设置 loanNo:"+loanNo+";------");
			}
		}
	}
}
