package com.fbd.admin.app.jobs;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fbd.admin.app.recharge.service.IRechargeService;


@Component
public class RechargeHandleJob {
	
    @Resource
	private IRechargeService rechargeService;
 
	/**
	 * 定时匹配队列（每隔1分钟执行一次）
	 * @param userId
	 * @param investTransferModel
	 */
//	@Scheduled(cron="0 30 04 * * ?")
//    @Scheduled(fixedDelay = 1000*60) 
	public void rechargeHandle(){
		try{
//			System.out.println("rechargeTask:"+new Date());
//			rechargeService.rechargeHandleTask();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
