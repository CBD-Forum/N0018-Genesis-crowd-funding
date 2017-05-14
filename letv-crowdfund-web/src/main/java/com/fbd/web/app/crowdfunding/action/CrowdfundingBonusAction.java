package com.fbd.web.app.crowdfunding.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockChain.service.impl.BlockChainQueryServiceImpl;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingBonusService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.crowdfunding.service.impl.CrowdfundingServiceImpl;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;


@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingBonus")
public class CrowdfundingBonusAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingBonusAction.class);

    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private IUserService userService;
    @Resource
    private IRewardAssignDao rewardAssignDao;
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
    private ICrowdfundingBonusService crowdfundingBonusService;
    
    
    
    /**
     * 
     * Description: 奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/getRewardAssignList.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getRewardAssignList(RewardAssignModel rewardModel){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
         try {
             resultMap.put(MSG, this.crowdfundingService.getRewardPageList(rewardModel));
             resultMap.put(SUCCESS, true);
         } catch (Exception e) {
            logger.error(e.getMessage());
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
         }
         return resultMap;
    	
    }
	  /**
     * 
     * Description: 分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/sendBonus.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendBonus(String loanNo,double money){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	this.crowdfundingService.checkBeforeBonus(loanNo,money);
            String bonusLoanId = this.crowdfundingService.saveBonus(loanNo,money);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, bonusLoanId);
        }catch(ApplicationException e1){
        	resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e1.getMessage());
        }catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 用户确认分红或者取消分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateIsAgreeBonus.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateIsAgreeBonus(String state,String loanBonusId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.saveBonus(state,loanBonusId);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
        	resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"分红请求失败");
        }
        return resultMap;
    }
    
    /**
     * 查询分红状态
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/selctBonusStatus.html",method= RequestMethod.POST)
    public @ResponseBody Map<String,Object> selctBonusStatus(HttpServletRequest request,String id){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		CrowdfundBonusModel model = this.crowdfundBonusDao.selectByPrimaryKey(id);
    		if("confirm".equals(model.getBonusState())){  //项目方确认分红成功
    			resultMap.put("bonusStatus", true);
    		}else{
    			resultMap.put("bonusStatus", false);
    		}
    		resultMap.put(SUCCESS, true);
    	}catch(Exception e){
    		resultMap.put(SUCCESS, false);
    	}
    	return resultMap;
    }
    
    //项目方分红异步通知
    @RequestMapping(value = "/crowdfundingBonusNotify.html")
    public void loanAuditRefundNotify(HttpServletRequest request,HttpServletResponse res){
    	
        logger.info("=============项目分红异步回调开始调用============");
        //等待同步处理数据
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=============项目分红接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID =  map.get("requestID");
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        //查询分红信息
        CrowdfundBonusModel model = this.crowdfundBonusDao.selectByPrimaryKey(transferNO);
		Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
    		crowdfundingService.loanBonusAfter(transferNO,requestID,null,false);
    	}else if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
         	timer.schedule(new loanBonusTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUBMIT_PROCESS,model),1000,3000); 
    	}
    }
    /**
     * 项目方分红确认定时任务
     * @author 80bug
     *
     */
    class loanBonusTask  extends java.util.TimerTask{
        String transId="";
        String requestID = "";
        Timer timer =null;
        String status ="";
        CrowdfundBonusModel model = null;
        public loanBonusTask(Timer mytimer,String tranId,String requestID,String status,CrowdfundBonusModel model){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.model = model;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			//查询事务
			blockChainQuerySerivce.transactionQuery(transId, requestID);
			//处理数据
			crowdfundingService.loanBonusAfter(model.getId(),requestID, timer, true);
		}
	}
    
    
    /******************************************************************************
     * 平台审核分红拒绝后的处理
     *****************************************************************************/   
    
    /**
     * 分红审核拒绝异步通知处理
     * @param request
     * @param res
     */
    @RequestMapping(value = "/auditBonusRefuseNotify.html")
    public void auditBonusRefuseNotify(HttpServletRequest request,HttpServletResponse res){
    	
        logger.info("=============分红审核拒绝接口异步回调开始调用============");
        logger.info("=============项目分红异步回调开始调用============");
        //等待同步处理数据
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=============分红审核拒绝拒绝接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        //查询分红信息
        CrowdfundBonusModel bonusmodel = this.crowdfundBonusDao.selectByPrimaryKey(blockAsynTran.getParentId());
		
		Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
    		crowdfundingBonusService.bonusAuditRefuseAfter(bonusmodel,requestID,timer, false);
    	}else{
         	timer.schedule(new auditBonusRefuseTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.bonus_refuse,bonusmodel),1000,3000); 
    	}
    }
    /**
     * 平台审核退款拒绝定时任务
     * @author 80bug
     */
    class auditBonusRefuseTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        CrowdfundBonusModel model = null;
        String requestID = "";
        public auditBonusRefuseTask(Timer mytimer,String tranId,String requestID,String status,String type,CrowdfundBonusModel model){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.model = model;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			//定时去查询任务
			blockChainQuerySerivce.transactionQuery(transId, requestID);
			//处理数据
			crowdfundingBonusService.bonusAuditRefuseAfter(model,requestID,timer, true);
		}
	}
    
    /******************************************************************************
     * 平台审核分红通过后的处理
     *****************************************************************************/
    /**
     * 分红审核通过异步通知处理
     * @param request
     * @param res
     */
    @RequestMapping(value = "/auditBonusPassedNotify.html")
    public void auditBonusPassedNotify(HttpServletRequest request,HttpServletResponse res){
        logger.info("============用户分红审核通过异步回调开始调用============");
        //等待同步处理数据
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Map<String, String> map = this.getBlockChainParamsStr();
        
        logger.info("=============用户分红审核通过异步回调接口通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        //查询用户分红信息
        RewardAssignModel model = this.rewardAssignDao.selectByPrimaryKey(transferNO);
		
		Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
    		crowdfundingBonusService.bonusAuditPassedAfter(model,requestID, timer, false);
    	}else{
         	timer.schedule(new auditBonusPassedTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.investor_bonus,model),1000,3000); 
    	}
    }
    /**
     * 平台审核分红通过定时任务
     * @author 80bug
     */
    class auditBonusPassedTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        RewardAssignModel model = null;
        String requestID = "";
        public auditBonusPassedTask(Timer mytimer,String tranId,String requestID,String status,String type,RewardAssignModel model){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.model = model;
        	this.requestID = requestID;
        }
		@Override
		public void run() {	
			//定时去查询任务
			blockChainQuerySerivce.transactionQuery(transId, requestID);
			//处理数据
			crowdfundingBonusService.bonusAuditPassedAfter(model,requestID,timer, true);
		}
	}    
}
