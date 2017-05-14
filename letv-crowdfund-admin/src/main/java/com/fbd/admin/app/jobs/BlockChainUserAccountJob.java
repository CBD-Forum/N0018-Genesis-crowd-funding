package com.fbd.admin.app.jobs;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fbd.core.app.blockChain.service.IBlockChainUserJobService;


@Component
public class BlockChainUserAccountJob {
	
	

    private static final Logger logger = LoggerFactory.getLogger(BlockChainUserAccountJob.class);
    
    //注入service
    @Resource
    private IBlockChainUserJobService blockChainUserJobService;
    
	
	/**
	 * 用户开户异常检测job
	 * 每隔10分钟检测一次
	 */
    @Scheduled(fixedDelay = 1000*60*10) 
	public void userOpenAccountCheckJob(){
		try{
			logger.info("=========用户开户异常检测job开始========================");
		//	blockChainUserJobService.modifyUserCreateAccount();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
    /**
	 * 用户激活账户异常检测job
	 * 每隔10分钟检测一次
	 */
    @Scheduled(fixedDelay = 1000*60*10) 
	public void userActivateCheckJob(){
		try{
			logger.info("=========用户激活异常检测job开始========================");
//			blockChainUserJobService.modifyUserActivationAccount();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
    
    /**
 	 * 用户账户添加信任异常检测job
 	 * 每隔10分钟检测一次
 	 */
     @Scheduled(fixedDelay = 1000*60*10) 
 	public void userSetTrustCheckJob(){
 		try{
 			logger.info("=========用户添加信任异常检测job开始========================");
// 			blockChainUserJobService.modifyUserTrustAccount(); 			
 		}catch(Exception e){
 			e.printStackTrace();
 		}
 	}
}
