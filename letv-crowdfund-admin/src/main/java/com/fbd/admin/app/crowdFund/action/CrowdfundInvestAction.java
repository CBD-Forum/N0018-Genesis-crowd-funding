/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingAction.java 
 *
 * Created: [2015-3-27 下午7:03:44] by haolingfeng
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
 * ProjectName: rain-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.crowdFund.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.auth.service.IAuthService;
import com.fbd.admin.app.blockChain.service.BlockChainCrowdfundInvestService;
import com.fbd.admin.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundInvestService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingLendService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.admin.app.todo.service.ITodoService;
import com.fbd.admin.app.user.service.IUserService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.crowdfunding.model.CrowdfundLeadInvestorModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.exception.ApplicationException;
/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundInvest")
public class CrowdfundInvestAction extends BaseAction{
    private static final Logger logger = LoggerFactory.getLogger(CrowdfundInvestAction.class);
    
    @Resource
    private ITodoService todoService;
    
    //资源
    @Resource
    private IAuthService authService;
    @Resource
    private ICrowdfundInvestService crowdfundInvestService;
    @Resource
    private BlockChainCrowdfundInvestService blockChainCrowdfundInvestService;
    @Resource
    private ICrowdfundingService crowdfundingService;

    @Resource
    private IBlockAsynTranService blockAsynTranService;

    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    @Resource
    private IUserService userService;
    
    @Resource
    private IUserBillService userBillService;
    @Resource
    private IBlockChainCrowdfundingService blockChainCrowdfundingService;
    @Resource
    private ICrowdfundingLendService crowdfundingLendService;
    
    /**
     * 
     * Description: 后台投标
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:44:32
     */
    @RequestMapping(value = "/backSupport.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> backSupport(CrowdfundingSupportModel model,String loanType){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            this.crowdfundInvestService.saveSupportByBack(model, loanType);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "后台投标失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询项目的约谈列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getInterviewRecord.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getInterviewRecord(InterviewRecordModel model) {
            SearchResult<Map<String,Object>> pageList = this.crowdfundInvestService.getInterviewList(model);
            return pageList;
    }
    
    /**
     * 
     * Description:查询项目的认购列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getOrderSupportList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getOrderSupportList(CrowdfundingSupportModel model) {
            SearchResult<Map<String,Object>> pageList = this.crowdfundInvestService.getOrderSupportList(model);
            return pageList;
    }
    
    
    /**
     * 
     * Description: 去放款
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 上午10:31:04
     */
    @RequestMapping(value = "/sendLend.html", method = RequestMethod.POST)
    public @ResponseBody
      Map<String, Object> sendLend(String loanNo){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_SEND;
        try{
 
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目编号："+loanNo); 
            
        	crowdfundInvestService.checkBeforeLend(loanNo);

        	crowdfundingLendService.sendLoanLend(loanNo);
            returnMap.put(SUCCESS, true);
        }catch(ApplicationException e){
            e.printStackTrace();
            logger.error(e.getMessage());
            returnMap.put(SUCCESS, false);
            returnMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目编号："+loanNo+";错误信息："+e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            returnMap.put(SUCCESS, false);
            returnMap.put(MSG,"放款失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目编号："+loanNo+";错误信息：放款失败");
        }
        return returnMap;
    }
    
    @RequestMapping(value = "/selectLoanState.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectLoanState(String loanNo){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		CrowdfundingModel loan = this.crowdfundInvestService.getByLoanNo(loanNo);
    		resultMap.put(MSG, CrowdfundCoreConstants.crowdFundingState.lended.getValue().equals(loan.getLoanState())?true:false);
    		resultMap.put(SUCCESS, true);
    	}catch(Exception e){
    		resultMap.put(SUCCESS, false);
    	}
    	return resultMap;
    }

   
    
    private void lendAfter(String loanNo){
		String operateType = AuditLogConstants.TYPE_SEND;
        
   	 //发送放款请求
       //blockChainCrowdfundInvestService.sendLendTrans(loanNo);
       //更新投资
       CrowdfundingModel loan = crowdfundInvestService.getByLoanNo(loanNo);
       if("payed".equals(loan.getPlatformTransferState())&&"payed".equals(loan.getLoanTransferState())){
       
       List<CrowdfundingSupportModel> supportList = crowdfundInvestService.getNoLendSupportList(loanNo);
       for(CrowdfundingSupportModel support:supportList){
    	   crowdfundInvestService.updateSupportAfterLend(support, loan);
       }
       String operator = MyRequestContextHolder.getCurrentUser()
               .getAdminId();
       //更新项目
       crowdfundInvestService.updateAfterLoan(loan, "");
      //筹款结束 如果该项目为产品,分配抽奖编号
       if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
    	   blockChainCrowdfundingService.saveUserPrize(loan.getLoanNo());
       }
       AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
               AuditLogConstants.RESULT_SUCCESS,"项目编号："+loanNo);
       }
   }
 
 
    
   
      
     
   
    /**
     * 
     * Description: 流标
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 上午10:31:04
     */
    @RequestMapping(value = "/sendFlow.html", method = RequestMethod.POST)
    public @ResponseBody
      Map<String, Object> sendFlow(String loanNo){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_FLOW;
        try{
        	logger.info("============>项目编号loanNo:"+loanNo+"-开始流标请求====================");
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            //验证流标
            crowdfundInvestService.checkBeforeLend(loanNo);
            //投资记录
            CrowdfundingModel loan = this.crowdfundInvestService.getByLoanNo(loanNo);
            if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){

	            crowdfundInvestService.sendFlowSuccess( loan);
	
	            crowdfundInvestService.updateAfterFlow(loan, operator);
            }else{
            	logger.info("=============查询投资列表(可流标投资记录)=============");
	            List<CrowdfundingSupportModel> supportList = this.crowdfundInvestService.getNoLendSupportList(loanNo);
	            logger.info("=============可流标数量："+supportList.size()+"=================");
	            for(CrowdfundingSupportModel support:supportList){
	                this.crowdfundInvestService.sendSupportFlow(support, loan);
	            }
            }
            returnMap.put(SUCCESS, true);
        }catch(ApplicationException e){
            logger.error(e.getMessage());
            returnMap.put(SUCCESS, false);
            returnMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目编号："+loanNo+";错误信息："+e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            returnMap.put(SUCCESS, false);
            returnMap.put(MSG,"流标失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目编号："+loanNo+";错误信息：流标失败");
        }
        return returnMap;
    }
    
    
    
    
    @RequestMapping(value = "/getFlowState.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getFlowState(String loanNo){
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try{
        	CrowdfundingModel loan = this.crowdfundingService.getCrowdfundByLoanNo(loanNo);
        	//查询项目流标状态
        	if(CrowdfundCoreConstants.crowdFundingState.flow.getValue().equals(loan.getLoanState())){
        		returnMap.put(MSG, true);
        	}else{
        		returnMap.put(MSG, false);
        	}
        	returnMap.put(SUCCESS, true);
        }catch(Exception e){
        	returnMap.put(SUCCESS, false);
        }
        return returnMap;
    } 
    
    
   
    /**
     * 
     * Description:处理约谈 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:46:26
     */
    @RequestMapping(value = "/processInterview.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> processInterview(InterviewRecordModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            model.setState(CrowdfundCoreConstants.ProcessState.processed);
            this.crowdfundInvestService.updateInterviewState(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "处理约谈失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:审核项目动态
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:46:26
     */
    @RequestMapping(value = "/auditProgress.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> auditProgress(CrowdfundingProgressModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            model.setAuditTime(new Date());
            model.setAuditor(operator);
            this.crowdfundInvestService.updateCrowdfundProgress(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "处理约谈失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:保存项目进展 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午9:14:25
     */
    @RequestMapping(value = "/addProgress.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> addProgress(CrowdfundingProgressModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            model.setEnterUser(operator);
            this.crowdfundInvestService.saveCrowdfundProgress(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "处理约谈失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:置为领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:46:26
     */
    @RequestMapping(value = "/setLoanLeader.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> setLoanLeader(CrowdfundLeadInvestorModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            this.crowdfundInvestService.saveLoanLeader(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "置为领投人失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:取消领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:46:26
     */
    @RequestMapping(value = "/cancelLoanLeader.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> cancelLoanLeader(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            this.crowdfundInvestService.deleteLoanLeader(id);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "取消领投人失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-26 下午8:06:50
     */
    @RequestMapping(value = "/getLoanLeader.html", method = RequestMethod.POST)
    public @ResponseBody List<Map<String,Object>> getLeader(String loanNo){
        return this.crowdfundInvestService.getLoanLeader(loanNo);
    }
    
    /**
     * 
     * Description:手动中止
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午7:46:26
     */
    @RequestMapping(value = "/handEndLoan.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> handEndLoan(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
       
        try { 
        	 List<CrowdfundingSupportModel> list = crowdfundingService.selectIntentionsByLoanNo(loanNo);
             if(list.size()<=0){
	            String operateType = AuditLogConstants.TYPE_END;
	            String operator = MyRequestContextHolder.getCurrentUser()
	                    .getAdminId();
	            this.crowdfundInvestService.updateAfterHandEndLoan(loanNo, operator);
	            
	            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
	                    AuditLogConstants.RESULT_SUCCESS,operator+"手动停止项目，项目编号"+loanNo);
	            resultMap.put(SUCCESS, true);
             }else{
            	 List supportList = new ArrayList();
            	 for(int i=0;i<list.size();i++){
            		 CrowdfundingSupportModel model = list.get(i);
            		 supportList.add(model.getOrderId());
            	 }
            	 resultMap.put(SUCCESS, false);
            	 resultMap.put(MSG, "意向金尾款未支付订单号为:"+supportList.toString()+",不能手动结束项目");
             }
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "手动停止失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询支付成功的份数 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-12-23 下午5:52:05
     */
    @RequestMapping(value = "/getTotalBuyNum.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getTotalBuyNum(String loanNo){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
            long buyNum = this.crowdfundInvestService.getTotalBuyNum(loanNo);
            resultMap.put("buyNum",buyNum);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询项目的认购列表是否有领投人
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getSupportListIsLeader.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getSupportListIsLeader(CrowdfundingSupportModel model) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
        	Boolean isLeader = this.crowdfundInvestService.getSupportListIsLeader(model);
        	resultMap.put(SUCCESS, true);
        	resultMap.put(MSG, isLeader);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "查询失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询业务参数服务费比列
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getChargeFeeScale.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getChargeFeeScale() {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
        	resultMap.put(SUCCESS, true);
        	resultMap.put(MSG, this.crowdfundInvestService.getChargeFeeScale());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "查询失败");
        }
        return resultMap;
    }
}
