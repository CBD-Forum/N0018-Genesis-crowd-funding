package com.fbd.web.app.crowdfunding.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.crowdfunding.action.CrowdfundingBonusAction;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingBonusService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;


@Service(value="crowdfundingBonusService")
public class CrowdfundingBonusServiceImpl implements ICrowdfundingBonusService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingBonusServiceImpl.class);
	
    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private IUserService userService;
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private IVerifyCodeService verifyCodeService;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    @Resource
    private ICrowdfundBonusDao crowdfundBonusDao; 
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IRewardAssignDao rewardAssignDao;
    
	@Override
	public void bonusAuditRefuseAfter(CrowdfundBonusModel model,String requestID,Timer timer,boolean notifyFlag) {
		BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        String status = blockAsynTran.getQueryStatus();
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){

        	model.setBonusState("refuse"); //设置分红状态为拒绝
			model.setBonusAuditState("refuse"); //设置状态为拒绝
	        this.crowdfundBonusDao.updateBySelective(model);
	        //查询项目信息
	        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(model.getLoanNo());
	        //审核拒绝 退换借款人分红金额
			this.userBillService.addAuditRefuseUnfreeze(model,loan.getLoanUser());
			//发送消息
			this.sendLoanBonusRefuseMessage(loan, model);
			//关闭定时任务
    		if(notifyFlag){
    			timer.cancel();
    		}
        }else if(BlockChainCore.ResultStatus.TRANSACTION_FAIL.equals(status)){
        	model.setBonusState("loanSuccess"); //设置为项目方成功
			model.setBonusAuditState("auditing"); //设置状态为审核中
	        this.crowdfundBonusDao.updateBySelective(model);
        }
	}
	
	
    /**
     * 
     * Description: 发送分红审核拒绝消息（项目方）
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanBonusRefuseMessage(CrowdfundingModel model,CrowdfundBonusModel bonus){
       Map<String, String> params = new HashMap<String,String>();
       params.put("money",Arith.format(bonus.getBonusAmt()));  //分红金额
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("bonusTime",DateUtil.dateTime2Str(bonus.getBonusTime(), null));  //分红时间
       params.put("bonusAuditTime",DateUtil.dateTime2Str(bonus.getBonusAuditTime(), null));  //分红审核时间
       
       try{
           logger.info("发送项目分红审核拒绝手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_REFUSE_LOAN_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
           logger.info("发送项目分红审核拒绝手机短信结束");
       }catch(Exception e){
           logger.error("发送项目分红审核拒绝手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目分红审核拒绝站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_REFUSE_LOAN_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
            logger.info("发送项目分红审核拒绝站内信结束");
        }catch(Exception e){
            logger.error("发送项目分红审核拒绝站内信失败，"+e.getMessage());
        }
    }
	
	/**
	 * 用户分红成功后操作
	 * @param model
	 * @param timer
	 * @param notifyFlag
	 */
	public void bonusAuditPassedAfter(RewardAssignModel model,String requestID,Timer timer,boolean notifyFlag){
		
		BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        String status = blockAsynTran.getQueryStatus();
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){

        	logger.info("==================请求编号："+requestID+"事务处理成功=======================");
        	model.setBonusState(FbdCoreConstants.bonusState.bonus_success);
        	model.setAssignTime(new Date());
        	this.rewardAssignDao.update(model);
        	//用户记账
        	userBillService.addLoanShareBonusUserBill(model.getAssignAmt(), model.getGetUser(), model.getInvestNo());
        	 //查询分红
   		    CrowdfundBonusModel bonusmodel = this.crowdfundBonusDao.selectByPrimaryKey(model.getLoanBonusId());
        	//查询项目
		     CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(bonusmodel.getLoanNo());
        	//给投资人发站内信
        	this.sendInvestBonusSuccessMessage(loan, model);
        	//查询该项目下未完成的还有多少
        	logger.info("=========查询该项目下未完成的分红数量还有多少==========================");
        	RewardAssignModel qmodel = new RewardAssignModel();
        	qmodel.setLoanBonusId(model.getLoanBonusId());
        	qmodel.setBonusState(FbdCoreConstants.bonusState.wait_bonus);
        	List<Map<String,Object>> list = this.rewardAssignDao.getList(qmodel);
        	if(list== null || list.size()==0){
        		 logger.info("====================分红用户资金转账完成，开始更新分红状态======================");
        		//更新分红状态为分红成功
        		 bonusmodel.setBonusState(FbdCoreConstants.bonusState.bonus_success);  //分红状态
        		 bonusmodel.setBonusAuditState(FbdCoreConstants.bonusAuditState.passed);  //分红审核通过
        		 bonusmodel.setBonusAuditTime(new Date());
    		     this.crowdfundBonusDao.update(bonusmodel);
    		     //添加项目方分红成功账单
    		     this.userBillService.addAuditBonusPassBill(bonusmodel,loan.getLoanUser());
    		     //给项目方发站内信
    		     this.sendLoanBonusSuccessMessage(loan, bonusmodel);
    		     
        	}
			//关闭定时任务
    		if(notifyFlag){
    			timer.cancel();
    		}
        } 
	}
	
    /**
     * 
     * Description: 发送分红分红成功投资人信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendInvestBonusSuccessMessage(CrowdfundingModel model,RewardAssignModel assign){
       Map<String, String> params = new HashMap<String,String>();
       params.put("assignAmt",Arith.format(assign.getAssignAmt()));  //分红金额
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("bonusTime",DateUtil.dateTime2Str(assign.getAssignTime(), null));  //分红时间
       params.put("bonusAuditTime",DateUtil.dateTime2Str(new Date(), null));  //分红审核时间
       
       try{
           logger.info("发送项目分红审核通过投资人手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_PASSED_INVEST_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode,assign.getInvestor(), params);
           logger.info("发送项目分红审核通过投资人手机短信结束");
       }catch(Exception e){
           logger.error("发送项目分红审核通过投资人手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目分红审核通过投资人站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_PASSED_INVEST_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, assign.getInvestor(), params);
            logger.info("发送项目分红审核通过投资人站内信结束");
        }catch(Exception e){
            logger.error("发送项目分红审核通过投资人站内信失败，"+e.getMessage());
        }
    }
    
    /**
     * 
     * Description: 发送分红审核拒绝消息（项目方）
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanBonusSuccessMessage(CrowdfundingModel model,CrowdfundBonusModel bonus){
       Map<String, String> params = new HashMap<String,String>();
       params.put("money",Arith.format(bonus.getBonusAmt()));  //分红金额
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("bonusTime",DateUtil.dateTime2Str(bonus.getBonusTime(), null));  //分红时间
       params.put("bonusAuditTime",DateUtil.dateTime2Str(bonus.getBonusAuditTime(), null));  //分红审核时间
       
       try{
           logger.info("发送项目分红审核通过项目方手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_PASSED_LOAN_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
           logger.info("发送项目分红审核通过项目方手机短信结束");
       }catch(Exception e){
           logger.error("发送项目分红审核通过项目方手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目分红审核通过项目方站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_BONUS_PASSED_LOAN_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
            logger.info("发送项目分红审核通过项目方站内信结束");
        }catch(Exception e){
            logger.error("发送项目分红审核通过项目方站内信失败，"+e.getMessage());
        }
    }

}
