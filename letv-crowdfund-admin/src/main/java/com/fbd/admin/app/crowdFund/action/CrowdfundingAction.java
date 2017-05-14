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
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fbd.admin.app.auth.service.IAuthService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.admin.app.todo.service.ITodoService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.CrowdfundCoreConstants.CrowdfundType;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtils;

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
@RequestMapping("/crowdfunding")
public class CrowdfundingAction extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingAction.class);
    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private IBlockChainAccountService blockChainAccountService;      
    @Resource
    private ITodoService todoService;
    
    //资源
    @Resource
    private IAuthService authService;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundProductTransferDao crowdfundProductTransferDao;
    /**
     * 
     * Description:查询众筹项目列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:22
     */
    @RequestMapping(value = "/getPageCrowdList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getPageCrowdList(CrowdfundingModel model) {
    	
    	
    	//预热项目排序
    	if("preheat".equals(model.getLoanState())){
    		if(StringUtil.isEmpty(model.getSort())){  
    			model.setSort("preheat");
    			model.setOrder("desc");    
        	} 
    	}
    	//待复审项目排序
    	if("passed".equals(model.getLoanState())){
    		if(StringUtil.isEmpty(model.getSort())){   
    			model.setSort("secondAuditing");    
        	} 
    	} 
    	
    	//待发布项目排序
    	if("re_passed".equals(model.getLoanState())){
    		if(StringUtil.isEmpty(model.getSort())){   
    			model.setSort("re_passed");    
        	} 
    	}     	
    	
    	if("stock".equals(model.getLoanType())){
    		model.setSort("auditTime");
    	}
    	if(StringUtil.isEmpty(model.getSort())){  //如果排序不为空 
    		model.setSort("defaultSort");    
    	} 
        String fixLoanType = model.getFixLoanType();
        if(null!=fixLoanType){
            model.setFixLoanType(fixLoanType);
        }
        SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getPageList(model);
        return pageList;
    }
    /**
     * 
     * Description: 查询众筹详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:03:52
     */
    @RequestMapping(value = "/getCrowdDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdDetail(String loanNo) {
    	try{
    		 Map<String,Object> loanDetail = this.crowdfundingService.getCrowdFundDetail(loanNo);
    		 return loanDetail;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return null;
        
    }
    
    /**
     * 
     * Description:更新众筹明细项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/updateCrowdFundDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateCrowdFundDetail(HttpServletRequest request,CrowdfundDetailModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //更新项目详情信息
            this.crowdfundingService.updateLoanDetail(model);
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
     * Description:审核通过众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/auditPass.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> auditPass(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_APPROVE;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            
            CrowdfundingModel crow = crowdfundingService.getCrowdfundByLoanNo(model.getLoanNo());
            if("stock".equals(crow.getLoanType())){
                if(crow.getContractTplNo()==null || "".equals(crow.getContractTplNo())){
                	throw new ApplicationException("合同模板未设置,请选择合同模板！");
                }
            }
            
            
            this.crowdfundingService.updateCrowdFund(model, operator,
                    CrowdfundCoreConstants.crowdFundingState.passed.getValue());
            
            this.crowdfundingService.addLoanAudit(operator, model.getLoanNo(), 
                    CrowdfundCoreConstants.loanAuditAction.audit_pass.getValue(),
                    model.getAuditOpinion(), CrowdfundCoreConstants.crowdFundingState.passed.getValue());
            
            todoService.modifyTodoByEventObj(CrowdfundCoreConstants.crowdFundingState.submit.getValue(),
                    model.getLoanNo(), 
                    operator,
                    CrowdfundCoreConstants.crowdFundingState.passed.getValue());
            String link = authService.getLinkByCode(FbdCoreConstants.permissionCode.CROWDFUND_REAUDIT);
            todoService.saveTodo(CrowdfundCoreConstants.crowdFundingState.passed.getValue(), model.getLoanNo(), 
                    operator+"审核项目："+model.getLoanNo(), link, 
                    FbdCoreConstants.todoPost.zcfs);
            
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目编号"+model.getLoanNo());
            
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){ 
        	resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"审核失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType,
                    AuditLogConstants.RESULT_FAIL,"项目编号"+model.getLoanNo()+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:审核拒绝众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/auditRefuse.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> auditRefuse(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_REJECT;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            this.crowdfundingService.updateCrowdFund(model, operator,
                    CrowdfundCoreConstants.crowdFundingState.add.getValue());
            
           
            this.crowdfundingService.addLoanAudit(operator, model.getLoanNo(), 
                    CrowdfundCoreConstants.loanAuditAction.audit_refuse.getValue(),
                    model.getAuditOpinion(), CrowdfundCoreConstants.crowdFundingState.add.getValue());
            
            todoService.modifyTodoByEventObj(CrowdfundCoreConstants.crowdFundingState.submit.getValue(),
                    model.getLoanNo(), 
                    operator,
                    CrowdfundCoreConstants.crowdFundingState.refuse.getValue());
            
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目编号"+model.getLoanNo());
            
            //发送拒绝信息
            CrowdfundingModel aa = this.crowdfundingService.getCrowdfundByLoanNo(model.getLoanNo());
            aa.setAuditOpinion(model.getAuditOpinion());
           
            this.sendAuditRefuseMessage(aa, "众筹项目初审拒绝");
            
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"审核失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType,
                    AuditLogConstants.RESULT_FAIL,model.getLoanNo()+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 发送审核拒绝短信
     * @param model
     * @param msg
     */
    private void sendAuditRefuseMessage(CrowdfundingModel model,String msg){
        Map<String, String> params = new HashMap<String,String>();
        try{
            logger.info("发送"+msg+"手机短信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOANREFUSE_MOBILE;
            params.put("loanName",model.getLoanName());
            params.put("reason",model.getAuditOpinion());
            SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
            logger.info("发送"+msg+"手机短信结束");
        }catch(Exception e){
            logger.error("发送"+msg+"手机短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送"+msg+"站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_LOANREFUSE_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, model.getLoanUser(), params);
             logger.info("发送"+msg+"站内信结束");
         }catch(Exception e){
             logger.error("发送"+msg+"站内信失败，"+e.getMessage());
         }
     }
    
    /**
     * 
     * Description:复审通过众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/reAuditPass.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> preAuditPass(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_APPROVE;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            this.crowdfundingService.updateCrowdFund(model, operator,
                    CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
            
            this.crowdfundingService.addLoanAudit(operator, model.getLoanNo(), 
                    CrowdfundCoreConstants.loanAuditAction.review_pass.getValue(),
                    model.getAuditOpinion(), CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
            
            todoService.modifyTodoByEventObj(CrowdfundCoreConstants.crowdFundingState.passed.getValue(),
                    model.getLoanNo(), 
                    operator,
                    CrowdfundCoreConstants.crowdFundingState.re_passed.getValue());
            
            //判断项目类型  如果为股权  则添加链接为 预热申请审核
            if(CrowdfundCoreConstants.CrowdfundType.STOCK.equals(model.getLoanType())){
            	String link = authService.getLinkByCode(FbdCoreConstants.permissionCode.CROWDFUND_APPLYPREHEAT);
                todoService.saveTodo(CrowdfundCoreConstants.crowdFundingState.re_passed.getValue(), model.getLoanNo(), 
                        operator+"审核项目："+model.getLoanNo(), link, 
                        FbdCoreConstants.todoPost.yrsh);
            }else{
            	String link = authService.getLinkByCode(FbdCoreConstants.permissionCode.CROWDFUND_RELEASE);
                todoService.saveTodo(CrowdfundCoreConstants.crowdFundingState.re_passed.getValue(), model.getLoanNo(), 
                        operator+"审核项目："+model.getLoanNo(), link, 
                        FbdCoreConstants.todoPost.zcfb);
            }
            
            
            
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目编号"+model.getLoanNo());
            
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"复审失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType,
                    AuditLogConstants.RESULT_FAIL,"项目编号"+model.getLoanNo()+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:复审拒绝众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/reAuditRefuse.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> preAuditRefuse(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_REJECT;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            this.crowdfundingService.updateCrowdFund(model, operator,
                    CrowdfundCoreConstants.crowdFundingState.add.getValue());
            
            
            this.crowdfundingService.addLoanAudit(operator, model.getLoanNo(), 
                    CrowdfundCoreConstants.loanAuditAction.review_refuse.getValue(),
                    model.getAuditOpinion(), CrowdfundCoreConstants.crowdFundingState.add.getValue());
            
            todoService.modifyTodoByEventObj(CrowdfundCoreConstants.crowdFundingState.passed.getValue(),
                    model.getLoanNo(), 
                    operator,
                    CrowdfundCoreConstants.crowdFundingState.re_refuse.getValue());
            
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目编号"+model.getLoanNo());
            
            //发送拒绝信息
            CrowdfundingModel aa = this.crowdfundingService.getCrowdfundByLoanNo(model.getLoanNo());
            aa.setAuditOpinion(model.getAuditOpinion());
            this.sendAuditRefuseMessage(aa, "众筹项目复审拒绝");
            
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"复审失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType,
                    AuditLogConstants.RESULT_FAIL,model.getLoanNo()+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:根据id查询众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:41:43
     */
    @RequestMapping(value = "/getCrowdFundById.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdFundById(String loanNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,this.crowdfundingService.getCrowdfundByLoanNo(loanNo));
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description:更新众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/updateCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateCrowdFunding(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_MODIFY;
        try {
        	//checkCrowdFund(model);
            this.crowdfundingService.updateCrowdFund(model);
            resultMap.put(SUCCESS, true);
            
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目loanNo:"+model.getLoanNo());
        }catch(ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目ID"+model.getId()+"，错误信息："+e.getMessage());
        } catch (Exception e) {
           logger.error(e.getMessage());
           e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"保存失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目ID"+model.getId()+"，错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:发布众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/releaseCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> releaseCrowdFunding(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_RELEASE;
        try {
        	logger.info("=======发布项目开始=========>loanNo:"+model.getLoanNo());
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            String loanNo=model.getLoanNo();
            if(StringUtil.isEmpty(loanNo)){
            	throw new ApplicationException("项目编号不能为空");
            }
        	CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(model.getLoanNo());
        	//如果是股权项目或者项目开户为true时，不进行区块链开户(股权项目开户是在复审时候开户)
        	if(FbdCoreConstants.BLOCK_CHAIN_LOAN_IS_MOCK 
        			|| 	CrowdfundType.STOCK.equals(crowdfund.getLoanType())){
        		
                this.crowdfundingService.updateCrowdFundAfterRelease(model);
 
                this.blockChainAccountService.modifyLoanFundingLogMsg(loanNo, operator);
        	}else{
        		//区块链
        		logger.info("==============项目【"+model.getLoanNo()+"】进行区块链开户=========================");
                this.blockChainAccountService.createLoanAccount(model.getLoanNo(),operator);
        	}
            resultMap.put(SUCCESS, true);
            
        } catch(ApplicationException e){
             logger.error(e.getMessage());
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"发布项目异常");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目ID"+model.getId()+"，错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    private void opPreheatCrowdFunding(CrowdfundingModel model,String operator,String operateType){
  
    	model.setPreheatTime(new Date());
    	
    	this.crowdfundingService.updateCrowdFundAfterPreheat(model);
        
        this.blockChainAccountService.modifyLoanPreaheatLogMsg(model.getLoanNo(), operator);
    }
    /**
     * 
     * Description:预热众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/preheatCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> preheatCrowdFunding(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_PREHEAT;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            String loanNo=model.getLoanNo();
            if(StringUtil.isEmpty(loanNo)){
            	throw new ApplicationException("项目编号不能为空");
            }
        	CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(model.getLoanNo());
        	             
        	if(CrowdfundType.STOCK.equals(crowdfund.getLoanType())){
        		//项目区块链开户
        		if(FbdCoreConstants.BLOCK_CHAIN_LOAN_IS_MOCK){
        			opPreheatCrowdFunding(model, operator, operateType);
            	}else{
            		logger.info("===========股权项目区块链进行开户=======================");
       			  	this.blockChainAccountService.createLoanPreheatAccount(loanNo, model.getPreheatEndTime(),operator);
            	}
        	}else{
        		//项目余额
        		logger.info("=========产品项目进行预热操作=====================");
        		opPreheatCrowdFunding(crowdfund, operator, operateType);
        	}
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
        	e.printStackTrace();
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目ID"+model.getId()+"，错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 新增众筹项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/saveCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFunding(HttpServletRequest request,CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_ADD;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            model.setCreator(operator);
            this.crowdfundingService.saveCrowdfunding(operator,model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, model.getLoanNo());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目ID"+model.getId());
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目ID"+model.getId()+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 删除项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/deleteCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteCrowdFunding(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_DELETE;
        try {
            this.crowdfundingService.deleteCrowdFunding(loanNo);
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目loanNo:"+loanNo);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目loanNo:"+loanNo+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 删除预热项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/deletePreheatCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deletePreheatCrowdFunding(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_DELETE;
        try {
        	CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
        	crowd.setLoanState(FbdCoreConstants.loanState.delete.getValue()); //设置项目状态为删除状态
            this.crowdfundingService.updateCrowdFund(crowd);
            resultMap.put(SUCCESS, true);
//            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
//                    AuditLogConstants.RESULT_SUCCESS,"项目loanNo:"+loanNo);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目loanNo:"+loanNo+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 上传项目的文件
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:34:22
     */
    @RequestMapping(value = "/uploadLoanFile.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> handleFormUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String errorMsg = "上传图片未成功，请检查图片格式和大小!";
        //保存  
        try {  
            String path = CrowdfundCoreConstants.CROWDFUNDING_PHOTO_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String photoUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
                resultMap.put(SUCCESS,true);
                resultMap.put(MSG,photoUrl);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
            }
        } catch (Exception e) {  
           logger.error(e.getMessage());  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG, errorMsg);
        }  
        return resultMap;   
    }  
    
    
    
    /**
     * 
     * Description: 众筹项目回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/saveBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFundingBackSet(CrowdfundingBackSetModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.saveCrowdfundingBackSet(model);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
        	 resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询回报设置列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:32:41
     */
    @RequestMapping(value = "/getBackSetList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getBackSetList(CrowdfundingBackSetModel model) {
        SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getBackSetList(model);
        return pageList;
    }
    
    /**
     * 
     * Description: 查询回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:32:41
     */
    @RequestMapping(value = "/getBackSetById.html", method = RequestMethod.POST)
    public @ResponseBody
    CrowdfundingBackSetModel getBackSetById(String id) {
        return this.crowdfundingService.getBackSetById(id);
    }
    
    /**
     * 
     * Description: 查询股权回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:32:41
     */
    @RequestMapping(value = "/getStockBackByLoanNo.html", method = RequestMethod.POST)
    public @ResponseBody
    StockBackSetModel getStockBackByLoanNo(String loanNo) {
        return this.crowdfundingService.getStockBackSet(loanNo);
    }
    
    
    /**
     * 
     * Description: 股权回报设置修改
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateEquityBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateEquityBackSet(StockBackSetModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.updateEquityBackSet(model);
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
     * Description: 编辑回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/updateBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateBackSet(CrowdfundingBackSetModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.updateBackSet(model);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
	       	resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 删除回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/deleteBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteBackSet(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.deleteBackSet(id);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 查询当前项目已经设置的回报总额
     */
    @RequestMapping(value = "/selectBackSetTotalAmt.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectBackSetTotalAmt(CrowdfundingBackSetModel model) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		String loanNo = model.getLoanNo();
    		CrowdfundingModel crow = crowdfundingService.getCrowdfundByLoanNo(loanNo);
    		
    		double totalAmt = this.crowdfundingService.getBackSetTotalAmt(loanNo);
    		resultMap.put("loanNo", loanNo);  //项目编号
    		resultMap.put("fundAmt", crow.getFundAmt());  //募集金额
    		resultMap.put("overFundAmt", crow.getOverFundAmt());  //募集上线
    		resultMap.put("alreadySetAmt", totalAmt); //已经设置的总金额
    		
    		resultMap.put(SUCCESS, true);
    	}catch(ApplicationException e){
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, e.getMessage());
    		return resultMap;
    	}catch(Exception e){
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, "获取当前项目已经设置的回报总额失败！");
    	}
    	return resultMap;
        
    }
    
    
    /**
     * 验证项目的回报设置
     */
    @RequestMapping(value = "/checkBackSet.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> checkBackSet(CrowdfundingBackSetModel model) {
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		String loanNo = model.getLoanNo();
    		CrowdfundingModel crow = crowdfundingService.getCrowdfundByLoanNo(loanNo);
    		double totalAmt = this.crowdfundingService.getBackSetTotalAmt(loanNo);
    		if(totalAmt<=0){
    			throw new ApplicationException("项目回报未设置,请设置项目回报！");
    		}
    		//募集金额
    		double overFundAmt = crow.getOverFundAmt();
    		if(totalAmt>overFundAmt){
    			throw new ApplicationException("项目回报总金额大于设置最高上限金额，请调整!");
    		}
    		
    		resultMap.put("loanNo", loanNo);  //项目编号
    		resultMap.put("fundAmt", crow.getFundAmt());  //募集金额
    		resultMap.put("overFundAmt", crow.getOverFundAmt());  //募集上线
    		resultMap.put("alreadySetAmt", totalAmt); //已经设置的总金额
    		
    		resultMap.put(SUCCESS, true);
    	}catch(ApplicationException e){
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, e.getMessage());
    		return resultMap;
    	}catch(Exception e){
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, "验证项目回报设置失败,请联系管理员！");
    	}
    	return resultMap;
        
    }
    
    
    /**
     * 
     * Description:提交众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/submit.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> submitCrowdFunding(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_SUBMIT;
        try {
            String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
            CrowdfundingModel model = crowdfundingService.getCrowdfundByLoanNo(loanNo);
            
            if("stock".equals(model.getLoanType())){
                if(model.getContractTplNo()==null || "".equals(model.getContractTplNo())){
                	throw new ApplicationException("合同模板未设置,请选择合同模板！");
                }
            }
            String loanState = CrowdfundCoreConstants.crowdFundingState.submit.getValue();
            model.setLoanState(loanState);
            this.crowdfundingService.updateCrowdFund(model);
            
            this.crowdfundingService.addLoanAudit(operator, loanNo, 
                    CrowdfundCoreConstants.loanAuditAction.submit.getValue(),
                    null, loanState);
            
            resultMap.put(SUCCESS, true);
            
            String link = authService.getLinkByCode(FbdCoreConstants.permissionCode.CROWDFUND_AUDIT);
            todoService.saveTodo(loanState, model.getLoanNo(), operator+"提交项目："+model.getLoanNo(), 
                    link, FbdCoreConstants.todoPost.zcsh);
            
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目loanNo:"+loanNo);
        }catch(ApplicationException e){
        	resultMap.put(SUCCESS,false);
            resultMap.put(MSG, e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目loanNo:"+loanNo+";错误信息："+e.getMessage());
        }catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"提交失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,"项目loanNo:"+loanNo+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description: 查询项目进展
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:43:10
     */
    @RequestMapping(value = "/getProgessList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model) {
    	
    	SearchResult<Map<String,Object>> pageList = new SearchResult<Map<String,Object>>();
    	try{
    		pageList = this.crowdfundingService.getProgessList(model);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return pageList;
    }
    
    /**
     * 
     * Description: 查询项目评论
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:10:14
     */
    @RequestMapping(value = "/getCommentList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getCommentList(CrowdfundingCommentModel model) {
        SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getCommentList(model);
        return pageList;
    }
    
    /**
     * 
     * Description:查询项目的支持列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getSupportList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String,Object>> getSupportList(CrowdfundingSupportModel model) {
        SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getSpportList(model);
        return pageList;
    }
    
    @RequestMapping(value = "/auditComment.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> auditComment(CrowdfundingCommentModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_APPROVE;
        try {
            String operator = MyRequestContextHolder.getCurrentUser()
                    .getAdminId();
            model.setAuditor(operator);
            model.setAuditTime(new Date());
            this.crowdfundingService.updateComment(model);
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"项目编号:"+model.getLoanNo()+";评论人："+model.getDiscussUser());
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
            AuditLogUtils.log(AuditLogConstants.MODEL_CROWDFUND, operateType, 
                    AuditLogConstants.RESULT_FAIL,
                    "项目编号:"+model.getLoanNo()+";评论人："+model.getDiscussUser()+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    
    @RequestMapping(value = "/delComment.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> delComment(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.delComment(id);
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
     * Description: 用户资金统计
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-16 下午2:55:56
     */
    @RequestMapping(value = "/getUserCapitalList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getUserCapitalList(CrowdfundingModel model) {
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getUserCapitalList(model);
            return pageList;
    }
    
    
    /**
     * Description: 项目延期
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-8 上午10:46:42
     */
    @RequestMapping(value = "/deferLoan.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> modifyDeferLoan(CrowdfundingModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try { 
        	if(model.getFundEndTime()==null){
        		throw new ApplicationException("融资截止日期不能为空");
        	}
        	if(model.getFundEndTime().getTime()<=new Date().getTime()){
        		throw new ApplicationException("融资截止日期必须大于当前日期");
        	}
            String userId = MyRequestContextHolder.getCurrentUser().getAdminId();
            this.crowdfundingService.updateCrowdFundAfterDelay(model);
            
            String auditState = FbdCoreConstants.loanAuditAction.delay.getValue();
            this.crowdfundingService.addLoanAudit(userId, model.getLoanNo(), auditState,null, 
                    CrowdfundCoreConstants.crowdFundingState.funding.getValue());
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_LOAN, AuditLogConstants.TYPE_DEFER, AuditLogConstants.RESULT_SUCCESS,"操作人："+userId+",项目："+model.getLoanNo()+"，延期到"+model.getFundEndTime());
        } catch(ApplicationException e){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "项目延期失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:查询项目的关注列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getLoanAttentionList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String,Object>> getLoanAttentionList(CrowdfundingAttentionModel model) {
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getAttentionList(model);
            return pageList;
    }
    
    
    /**
     * 
     * Description: 查询项目审核列表
     * 
     * @param
     * @return SearchResult<LoanAuditModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-30 下午2:46:55
     */
    @RequestMapping(value = "/getAuditList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<CrowdfundAuditModel> getList(CrowdfundAuditModel model) {
        SearchResult<CrowdfundAuditModel> aList = crowdfundingService.getAuditList(model);
        return aList;
    }
    
    
    
    /**
     * 
     * Description: 上传项目的合同
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:34:22
     */
    @RequestMapping(value = "/uploadLoanContract.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> uploadLoanContract(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //保存  
        try {  
            String path = CrowdfundCoreConstants.CROWDFUNDING_CONTRACT_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".doc"); 
            allowSuffix.add(".docx");
            allowSuffix.add(".pdf");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, "上传类型必须是doc,docx,pdf中的一种");
                return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String photoUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
                String loanNo = request.getParameter("loanNo");
                CrowdfundDetailModel model = new CrowdfundDetailModel();
                model.setLoanNo(loanNo);
                model.setLoanContract(photoUrl);
                this.crowdfundingService.updateLoanDetail(model);
                resultMap.put(MSG,photoUrl);
                resultMap.put(SUCCESS,true);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG,"上传失败");
            }
        } catch (Exception e) {  
           logger.error(e.getMessage());  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,"上传失败"+e.getMessage());
        }  
        return resultMap;   
    }  
    
    
    
    /**
     * 
     * Description: 上传公募机构的项目接收证明
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/uploadOrgLoanReceiveProve.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> uploadOrgLoanReceiveProve(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //保存  
        try {  
            String path = CrowdfundCoreConstants.CROWDFUNDING_ORG_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".doc"); 
            allowSuffix.add(".docx");
            allowSuffix.add(".pdf");
            allowSuffix.add(".pdf");
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, "上传类型必须是doc,docx,pdf,jpg,png,gif,jpeg中的一种");
                return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String photoUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
//                String loanNo = request.getParameter("loanNo");
//                CrowdfundDetailModel model = new CrowdfundDetailModel();
//                model.setLoanNo(loanNo);
//                model.setLoanContract(photoUrl);
//                this.crowdfundingService.updateLoanDetail(model);
                resultMap.put(MSG,photoUrl);
                resultMap.put(SUCCESS,true);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG,"上传失败");
            }
        } catch (Exception e) {  
           logger.error(e.getMessage());  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,"上传失败"+e.getMessage());
        }  
        return resultMap;   
    }  
    
    
    /**
     * 
     * Description: 修改项目等级
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/modifyLoanLevel.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> modifyLoanLevel(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.updateLoanLevel(model);
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
            this.crowdfundingService.saveBonus(loanNo,money);
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
     * Description: 结束分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/endBonus.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> endBonus(CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.updateEndBonus(model);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e1){
        	 logger.error(e1.getMessage());
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
     * 验证项目数据正确性
     * @param model
     */
    private void checkCrowdFund(CrowdfundingModel model)throws ApplicationException{
    	if(StringUtil.isEmpty(model.getLoanUser())){
    		throw new ApplicationException("请选择发起人");
    	}
    	if(StringUtil.isEmpty(model.getCanOver())){
    		throw new ApplicationException("请选择超募状态");
    	}
    	if("1".equals(model.getCanOver())){
    		//当时超募状态的时候
            if(model.getOverFundAmt()==null || model.getOverFundAmt() == 0){
            	throw new ApplicationException("超募上限金额不能为空");
            }else if(model.getOverFundAmt()<model.getFundAmt()){
            	throw new ApplicationException("超募上限金额必须大于等于筹集金额");
            }
    	}else{
    		model.setOverFundAmt(model.getFundAmt());
    	}
    }
    /**
     * 
     * Description: 发布众筹项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/saveCrowdFund.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFund(HttpServletRequest request,CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	checkCrowdFund(model);
            this.crowdfundingService.saveCrowdfunding(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, model.getLoanNo());
            resultMap.put("ID", model.getId());
        } catch(ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"项目更新失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询是否设置回报
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getBackByLoanNo.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getBackByLoanNo(String loanNo) {
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	try {
	    	Boolean flag = this.crowdfundingService.getBackByLoanNo(loanNo);
	    	resultMap.put(SUCCESS, true);
	    	resultMap.put(MSG, flag);
	    } catch (Exception e) {
	        logger.error(e.getMessage()); 	
	        resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,"查询回报失败");
	    }
	   return resultMap;
	}
    
    /**
     * 
     * Description: 查询项目审核列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getUserPrizeList.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getUserPrizeList(CrowdfundUserPrizeModel model) {
        SearchResult<Map<String, Object>> aList = crowdfundingService.getUserPrizeList(model);
        return aList;
    }
    
    
    /**
     * 
     * Description: 查询产品项目申请退款     审核记录 列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getLoanAuditRefundPage.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getLoanAuditRefundPage(CrowdfundRefundAuditModel model) {
        SearchResult<Map<String, Object>> aList = crowdfundingService.getLoanAuditRefundPage(model);
        return aList;
    }
    
    /**
     * 
     * Description: 查询用户购买转让记录表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getBuyTransferListPage.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getBuyTransferListPage(CrowdfundProductTransferModel model) {
        SearchResult<Map<String, Object>> aList = crowdfundingService.getBuyTransferListPage(model);
        return aList;
    }
    
    /**
     * 
     * Description:项目转让审核
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateProductTransferAudit.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> updateProductTransferAudit(CrowdfundProductTransferModel model) {
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	try {
	    	this.crowdfundingService.updateProductTransferAudit(model);
	    	resultMap.put(SUCCESS, true);
	    } catch(ApplicationException e){
	        resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,e.getMessage());
	    }catch (Exception e) {
	    	e.printStackTrace();
	        logger.error(e.getMessage());
	        resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,"项目转让审核失败");
	    }
	    	return resultMap;
	}
    /** 
     * 查询项目状态
     * @param 
     * @return
     */
    @RequestMapping(value = "/queryLoanStatus.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryLoanStatus(String loanNo,String loanState){
	   Map<String,Object> resultMap = new HashMap<String,Object>();
	   try{
		   if(StringUtil.isEmpty(loanNo)){
			   throw new ApplicationException("项目编号不能为空");
		   }
		   if(StringUtil.isEmpty(loanState)){
			   throw new ApplicationException("项目状态不能为空");
		   }
		   CrowdfundingModel model=new CrowdfundingModel();
		   model.setLoanNo(loanNo);
		   //model.setLoanState(loanState);
          Map<String,Object> data = this.crowdfundingDao.getList(model).get(0);
           String state=(String) data.get("loanState");
           if(state.indexOf("_locking")==-1){
               if(loanState.equals(state)){
                   resultMap.put(MSG, true);   
               }else{
                   //throw new ApplicationException("处理异常");
            	   resultMap.put(MSG, false);
               }
           }else{
               resultMap.put(MSG, false);   
           }
           resultMap.put(SUCCESS, true);
	   }catch(Exception e){
		   e.printStackTrace();
		   resultMap.put(SUCCESS, false);
	   }
	   return resultMap;
   } 
    /** 
     * 查询转让审核状态
     * @param 
     * @return
     */
    @RequestMapping(value = "/queryTransferAuditStatus.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> queryTransfertatus(String transferNo,String transferAuditState){
	   Map<String,Object> resultMap = new HashMap<String,Object>();
	   try{
		   if(StringUtil.isEmpty(transferNo)){
			   throw new ApplicationException("转让编号不能为空");
		   }
		   if(StringUtil.isEmpty(transferAuditState)){
			   throw new ApplicationException("中审核态不能为空");
		   }
		   boolean result=false;
		   CrowdfundProductTransferModel model= this.crowdfundProductTransferDao.getByTransferNo(transferNo);
		   /*if(StringUtil.isEmpty(model.getTransferAuditState())||FbdCoreConstants.transferAuditState.auditing.equals(model.getTransferAuditState())){
			   throw new ApplicationException("转让审核失败");
		   }else */
		   if(transferAuditState.equals(model.getTransferAuditState())){
			   result=true;
		   }else{
			   result=false;
		   }
		   resultMap.put(MSG, result);
		   resultMap.put(SUCCESS, true);
	   }catch(Exception e){
		   e.printStackTrace();
		   resultMap.put(SUCCESS, false);
	   }
	   return resultMap;
   } 
}
