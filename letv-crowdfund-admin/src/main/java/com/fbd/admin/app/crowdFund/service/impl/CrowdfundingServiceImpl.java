/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingServiceImpl.java 
 *
 * Created: [2015-3-27 下午4:56:49] by haolingfeng
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

package com.fbd.admin.app.crowdFund.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.auth.service.IAuthService;
import com.fbd.admin.app.contract.service.IContractTemplateService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.admin.app.todo.service.ITodoService;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockChain.util.BlockChainStringUtil;
import com.fbd.core.app.blockChain.util.BlockChainUtil;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundBonusAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundDetailDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundPhotoDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundRefundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferExtendDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingAttentionDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingCommentDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.dao.IStockBackSetDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundPhotoModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.quartz.util.QuartzUtils;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.core.util.spring.SpringUtil;
/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("crowdfundingService")
public class CrowdfundingServiceImpl implements ICrowdfundingService{
	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingServiceImpl.class);
   
    @Resource
    private IUserDao userDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundDetailDao crowdfundDetailDao;
    
    @Resource
    private ICrowdfundingBackSetDao crowdfundingBackSetDao;
    
    @Resource
    private ICrowdfundingProgressDao crowdfundingProgressDao;
    
    @Resource
    private ICrowdfundingCommentDao crowdfundingCommentDao;
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    
    @Resource
    private ICrowdfundingAttentionDao crowdfundingAttentionDao;
    
    @Resource
    private IBusinessConfigDao businessConfigDao;
    
//    @Resource
//    private StdScheduler scheduler;
    
    @Resource
    private IAuthService authService;
    
    @Resource
    private ITodoService todoService;
    
    @Resource
    private ICrowdfundPhotoDao crowdfundPhotoDao;
    
    @Resource
    private IStockBackSetDao stockBackSetDao;
    
    @Resource
    private ICrowdfundAuditDao crowdfundAuditDao;
    
    @Resource
    private ICrowdfundUserStuffDao crowdfundUserStuffDao;
    
    @Resource
    private ISystemBillService systemBillService;
    
    @Resource
    private IUserBillService userBillService;
    @Resource
    private IRewardAssignDao rewardAssignDao;
    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao;
    @Resource
    private IFileUploadDao fileUploadDao;
 
    @Resource
    private IContractTemplateService contractTemplateService;
  
    @Resource
    private ICrowdfundBonusDao crowdfundBonusDao; 
    
    @Resource
    private ICrowdfundRefundAuditDao crowdfundRefundAuditDao ;
    
    @Resource
    private ICrowdfundBonusAuditDao crowdfundBonusAuditDao;
    
    @Resource
    private ICrowdfundProductTransferDao crowdfundProductTransferDao;
    @Resource
    private ICrowdfundTransferExtendDao crowdfundTransferExtendDao;
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IBlockChainCrowdfundingService blockChainCrowdfundingService;
    /**
     * 
     * Description:保存众筹项目信息 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:09:05
     */
    public void saveCrowdfunding(String operator,CrowdfundingModel model){
        try{
            model.setId(PKGenarator.getId());
            model.setLoanNo(PKGenarator.getLoanId());
            model.setCreateTime(new Date());
            model.setLoanState(CrowdfundCoreConstants.crowdFundingState.add.getValue());
            model.setChargeFee(this.getServiceRatio());
            model.setApproveAmt(0.0);
            if(model.getOverFundAmt()==null || model.getOverFundAmt() == 0){
            	model.setOverFundAmt(model.getFundAmt());
            }
            this.crowdfundingDao.save(model);
            
            // 插入项目审核记录,审核动作为：new;项目状态为new
            crowdfundAuditDao.addLoanAudit(operator, model.getLoanNo(),
                    CrowdfundCoreConstants.loanAuditAction.add.getValue(), null,
                    CrowdfundCoreConstants.crowdFundingState.add.getValue());
        }catch(Exception e){
            throw new ApplicationException("发起众筹项目失败");
        }
    }
    
    private double getServiceRatio(){
        BusinessConfigModel config = this.businessConfigDao.
                getBusConfig(CrowdfundCoreConstants.CROWDFUND_SERVICEFEERATIO_CONFIG);
        if(config == null){
            throw new ApplicationException("平台收取的服务费比例业务参数没有配置");
        }
        double feeRatio = Double.valueOf(config.getCode());
        return Arith.round(feeRatio);
    }
    private double getDepositRatio(){
        BusinessConfigModel config = this.businessConfigDao.
                getBusConfig(CrowdfundCoreConstants.CROWDFUND_DEPOSITRATIO_CONFIG);
        if(config == null){
            throw new ApplicationException("平台收取的保证金比例业务参数没有配置");
        }
        double feeRatio = Double.valueOf(config.getCode());
        return Arith.round(feeRatio);
    }
    
    
    /**
     * 
     * Description: 更新众筹项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFund(CrowdfundingModel model){
        try{
            this.crowdfundingDao.updateBySelective(model);
        }catch(Exception e){
            throw new ApplicationException("更新众筹项目失败");
        }
        
    }
    
    
    /**
     * 
     * Description: 插入审核记录
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-30 上午11:14:25
     */
    public void addLoanAudit(String operator, String loanNo, String auditState,
            String auditOpinion, String loanState) {
/*        CrowdfundAuditModel model = new CrowdfundAuditModel();
        model.setId(PKGenarator.getId());
        model.setAuditTime(new Date());
        model.setAuditor(operator);
        model.setLoanNo(loanNo);
        model.setAuditState(auditState);
        model.setLoanState(loanState);
        model.setAuditOpinion(auditOpinion);
        this.crowdfundAuditDao.save(model);*/
    	this.crowdfundAuditDao.addLoanAudit(operator, loanNo, auditState, auditOpinion, loanState);
    }
    
    
    /**
     * 
     * Description: 更新众筹项目(延期)
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFundAfterDelay(CrowdfundingModel model){
        try{
            CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(model.getLoanNo());
            crowd.setLoanState(CrowdfundCoreConstants.crowdFundingState.funding.getValue());
            crowd.setFundEndTime(model.getFundEndTime());
            if(crowd.getFundEndTime().compareTo(new Date()) < 0){
            	 throw new ApplicationException("融资截止日期不能小于当前日期");
            }
            
            crowd.setFundDays(DateUtil.diff(crowd.getReleaseTime(), model.getFundEndTime()));
            this.crowdfundingDao.update(crowd);
            
            
            //增加筹资结束调度
            String loanNo = model.getLoanNo();
            String cronExpression = DateUtil.getSchedulerCronExpression(model.getFundEndTime());
            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put(QuartzJobConstants.PARAM_CROWDFUND_FINANCE_END,loanNo);
//            QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_CROWDFUND_FINANCE_END+"_"+loanNo, 
//                    QuartzJobConstants.CLASS_CROWDFUND_FINANCE_END, parameter, 
//                    QuartzJobConstants.TRIGGER_CROWDFUND_FINANCE_END+"_"+loanNo, cronExpression, 
//                    QuartzJobConstants.DES_CROWDFUND_FINANCE_END);
            
        }catch(Exception e){
        	e.printStackTrace();
            throw new ApplicationException("更新众筹项目失败");
        }
        
    }
    
    /**
     * 
     * Description:删除项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteCrowdFunding(String loanNo){
        try{
            CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
            this.crowdfundingDao.deleteByloanNo(loanNo);
            if(crowd.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.ENTITY)||
                    crowd.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PUBLIC_SERVICE)){
                this.crowdfundingBackSetDao.deleteByloanNo(loanNo);
            }else{
                this.stockBackSetDao.deleteByLoanNo(loanNo);
            }
            this.crowdfundDetailDao.deleteByLoanNo(loanNo);
        }catch(Exception e){
            throw new ApplicationException("删除项目失败");
        }
    }
    
    /**
     * 
     * Description: 发布众筹项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:44:03
     */
    public void updateCrowdFundAfterRelease(CrowdfundingModel model){
 
    	blockChainCrowdfundingService.updateCrowdFundAfterRelease(model);
    }
    
    /**
     * 
     * Description: 预热项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-21 上午10:34:23
     */
    public void updateCrowdFundAfterPreheat(CrowdfundingModel model){
        try{
        	blockChainCrowdfundingService.updateCrowdFundAfterPreheat(model.getLoanNo(),model.getPreheatEndTime());
        	//发送消息
        	this.sendReleaseMessage(model);
        }catch(Exception e){
            throw new ApplicationException("众筹预热失败");
        }
    }
    
    
    /**
     * 发送项目发布信息
     * @param model
     * @param msg
     */
    private void sendReleaseMessage(CrowdfundingModel model){
        Map<String, String> params = new HashMap<String,String>();
        try{
            logger.info("发送项目发布手机短信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_RELEASE_MOBILE;
            params.put("loanName",model.getLoanName());
            params.put("time", DateUtil.dateTime2Str(new Date(), null));
            SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
            logger.info("发送项目发布手机短信结束");
        }catch(Exception e){
            logger.error("发送项目发布手机短信失败，"+e.getMessage());
        }
         try{
             logger.info("发送项目发布站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_RELEASE_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
             logger.info("发送项目发布站内信结束");
         }catch(Exception e){
             logger.error("发送项目发布站内信失败，"+e.getMessage());
         }
     }    
    
    
    
    
    /**
     * 
     * Description: 到预热截止日期时变更项目状态
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午12:02:31
     */
    public void updateCrowdFundPreheated(String loanNo){
        try{
            CrowdfundingModel model = this.crowdfundingDao.getByloanNo(loanNo);
            model.setLoanState(CrowdfundCoreConstants.crowdFundingState.passed.getValue());
            model.setFundEndTime(null);
            this.crowdfundingDao.update(model);
        }catch(Exception e){
            throw new ApplicationException("预热截止更新众筹项目失败");
        }
        
    }
    
    /**
     * 
     * Description: 审核
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 下午3:22:17
     */
    public void updateCrowdFund(CrowdfundingModel model,String operator,String auditState){
        try{
            CrowdfundingModel  crow = this.crowdfundingDao.getByloanNo(model.getLoanNo());
            
            System.out.println(crow.getLoanType());
            crow.setLoanState(auditState);
            crow.setAuditor(operator);
            crow.setAuditTime(new Date());
            this.crowdfundingDao.updateBySelective(crow);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("更新众筹项目失败");
        }
        
    }
    
    /**
     * 
     * Description:分页查询众筹项目 
     *
     * @param 
     * @return SearchResult<CrowdfundingModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getPageList(CrowdfundingModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingDao.getList(model));
        result.setTotal(this.crowdfundingDao.getCount(model));
        return result;
    }
    /**
     * 
     * Description: 查询项目详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:02:02
     */
    public Map<String,Object> getCrowdFundDetail(String loanNo){
        Map<String,Object> result = this.crowdfundingDao.getLoanDetail(loanNo);
        StringBuffer photoSub = new StringBuffer();
        List<CrowdfundPhotoModel> photoList = this.crowdfundPhotoDao.getByLoanNo(loanNo);
        for(CrowdfundPhotoModel photo:photoList){
            photoSub.append(photo.getPhotoUrl()).append(",");
        }
        if(photoList.size()>0){
            String photoStr = photoSub.substring(0, photoSub.length()-1);
            result.put("photoUrls",photoStr);
        }
        
    	//项目类型
    	String loanType = result.get("loanType").toString(); 
    	if("stock".equals(loanType)){//股权项目
    		//查询商业计划书
    		FileUploadModel qbusinessProposal = new FileUploadModel();
    		qbusinessProposal.setParentId(loanNo);
    		qbusinessProposal.setType("businessProposal");
    		List<FileUploadModel>  businessProposalFiles = this.fileUploadDao.selectList(qbusinessProposal);
    		result.put("businessProposalFiles", businessProposalFiles);
    		//查询其他附件
    		FileUploadModel qotherAccessories = new FileUploadModel();
    		qotherAccessories.setParentId(loanNo);
    		qotherAccessories.setType("otherAccessories");
    		List<FileUploadModel>  otherAccessoriesFiles = this.fileUploadDao.selectList(qotherAccessories);
    		result.put("otherAccessoriesFiles", otherAccessoriesFiles);    		
    		//查询退出方案
    		FileUploadModel qexitScheme = new FileUploadModel();
    		qexitScheme.setParentId(loanNo);
    		qexitScheme.setType("exitScheme");
    		List<FileUploadModel>  exitSchemeFiles = this.fileUploadDao.selectList(qexitScheme);
    		result.put("exitSchemeFiles", exitSchemeFiles);    		
    	}else if("product".equals(loanType)){ //产品
    		//查询项目的回报
    		CrowdfundingBackSetModel prawDrawBackSet = this.crowdfundingBackSetDao.queryIsPrizeDrawFlag(loanNo);	
    		if(prawDrawBackSet!=null){
    			result.put("prizeNum", prawDrawBackSet.getPrizeNum());
    		}
    		
    	}else if("public".equals(loanType)){  //公益
    		//查询公募机构项目接收证明
    		FileUploadModel qorgLoanReceiveProve = new FileUploadModel();
    		qorgLoanReceiveProve.setParentId(loanNo);
    		qorgLoanReceiveProve.setType("orgLoanReceiveProve");
    		List<FileUploadModel>  orgLoanReceiveProveFiles = this.fileUploadDao.selectList(qorgLoanReceiveProve);
    		result.put("orgLoanReceiveProveFiles", orgLoanReceiveProveFiles);    
    	}
        return result;
    }
    
    
    /**
     * 
     * Description:更新项目详情 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-21 下午2:13:32
     */
    public void updateLoanDetail(CrowdfundDetailModel detail){
       this.crowdfundDetailDao.updateByLoanNo(detail);
    }
    private void checkCrowdfundingBack(CrowdfundingBackSetModel model){
    	//验证当前回报是否设置抽奖
    	if("1".equals(model.getPrizeDrawFlag())){
    		//查询当前项目是否有参与抽奖的回报
    		CrowdfundingBackSetModel qmodel = new CrowdfundingBackSetModel();
    		qmodel.setLoanNo(model.getLoanNo());
    		qmodel.setPrizeDrawFlag("1");
    		List<Map<String,Object>> list = this.crowdfundingBackSetDao.getList(qmodel);
    		boolean judge=true;
    		if(list!=null && list.size()>0){
    			if(StringUtils.isNotEmpty(model.getId())){
    				for(Map<String,Object> data:list){
    					String id=(String) data.get("id");
    					if(model.getId().equals(id)){
    						judge=false;
    						break;
    					}
    				}
    			}
    			if(judge){
        			throw new ApplicationException("该项目已经设置参与抽奖的回报！");	
    			}
    		}
    		if(model.getPrizeNum()==null||model.getPrizeNum()<=0){
    			throw new ApplicationException("抽奖名额必须是大于0的整数");
    		}
    		if(model.getPrizeInvestNum()==null||model.getPrizeInvestNum()<=0){
    			throw new ApplicationException("抽奖购买上限必须是大于0的整数");
    		}
    		if(model.getPrizeFullNum()==null||model.getPrizeFullNum()<=0){
    			throw new ApplicationException("激活抽奖份数上限必须是大于0的整数");
    		}else{
    			if(model.getPrizeFullNum()<=model.getPrizeNum()){
    				throw new ApplicationException("激活抽奖份数上限必须大于抽奖名额");
    			}
    		}
    	}else{
    		model.setPrizeDrawFlag("0");
    		model.setPrizeNum(null);
    		model.setPrizeInvestNum(null);
    	}
    }
    /**
     * 
     * Description:保存回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午10:46:02
     */
    public void saveCrowdfundingBackSet(CrowdfundingBackSetModel model){
        try{
        	//验证当前项目已经设置的回报
        	String loanNo = model.getLoanNo();
    		CrowdfundingModel crow = this.crowdfundingDao.getByloanNo(loanNo);
    		//募集金额
    		double fundAmt = crow.getFundAmt();
    		//超募上限
    		double overFundAmt = crow.getOverFundAmt();
    		
    		CrowdfundingBackSetModel backSet = new CrowdfundingBackSetModel();
    		backSet.setLoanNo(loanNo);
        	double totalAmt = 0;
        	List<Map<String,Object>> list = crowdfundingBackSetDao.getList(backSet);
        	for(Map<String,Object> map:list){
        		double amt = map.get("amt")==null?0d:Double.parseDouble(String.valueOf(map.get("amt")));
        		int numberLimits =  map.get("numberLimits")==null?0:Integer.parseInt(String.valueOf(map.get("numberLimits")));
        		totalAmt+= (amt*numberLimits);
        	}
        	//当前回报的金额和数量
        	double amt = model.getAmt();
        	//当前回报的支持数量
        	double numberLimits = model.getNumberLimits();
        	//当前回报总金额
        	double currentBackSetAmt = Arith.round(amt * numberLimits);
        	if((currentBackSetAmt + totalAmt) > overFundAmt ){
        		throw new ApplicationException("项目回报总金额大于设置最高上限金额，请调整!");
        	}
        	
        	checkCrowdfundingBack(model);
            model.setId(PKGenarator.getId());
            model.setBackNo(PKGenarator.getLoanId());
            model.setState(FbdCoreConstants.crowdFundBackState.NOTFULL);
            this.crowdfundingBackSetDao.save(model);
        }catch(ApplicationException e){
            throw new ApplicationException(e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
        	throw new ApplicationException("保存回报设置异常");
        }
    }
    
    /**
     * 
     * Description: 查询众筹项目回报设置
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public CrowdfundingBackSetModel getBackSetById(String id){
        return this.crowdfundingBackSetDao.selectByPrimaryKey(id);
    }
    
   
    /**
     * 
     * Description: 编辑回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:57:19
     */
    public void updateBackSet(CrowdfundingBackSetModel model){
        try{
        	checkCrowdfundingBack(model);
        	
        	//查询当前回报
        	CrowdfundingBackSetModel currentBackSet  = this.crowdfundingBackSetDao.selectByPrimaryKey(model.getId());
        	//检测回报设置是否已经超过
        	//验证当前项目已经设置的回报
        	String loanNo = model.getLoanNo();
    		CrowdfundingModel crow = this.crowdfundingDao.getByloanNo(loanNo);
    		//募集金额
    		double fundAmt = crow.getFundAmt();
    		//超募上限
    		double overFundAmt = crow.getOverFundAmt();
    		
    		CrowdfundingBackSetModel backSet = new CrowdfundingBackSetModel();
    		backSet.setLoanNo(loanNo);
        	double totalAmt = 0;
        	List<Map<String,Object>> list = crowdfundingBackSetDao.getList(backSet);
        	for(Map<String,Object> map:list){
        		String backNo = map.get("backNo").toString();
        		if(backNo.equals(currentBackSet.getBackNo())){
        			continue;
        		}
        		double amt = map.get("amt")==null?0d:Double.parseDouble(String.valueOf(map.get("amt")));
        		int numberLimits =  map.get("numberLimits")==null?0:Integer.parseInt(String.valueOf(map.get("numberLimits")));
        		totalAmt+= (amt*numberLimits);
        	}
        	//当前回报的金额和数量
        	double amt = model.getAmt();
        	//当前回报的支持数量
        	double numberLimits = model.getNumberLimits();
        	//当前回报总金额
        	double currentBackSetAmt = Arith.round(amt * numberLimits);
        	if((currentBackSetAmt + totalAmt) > overFundAmt ){
        		throw new ApplicationException("项目回报总金额大于设置最高上限金额，请调整!");
        	}
        	
            this.crowdfundingBackSetDao.updateBySelective(model);
        }catch(ApplicationException e){
        	throw new ApplicationException(e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            throw new ApplicationException("更新回报设置失败");
        }
    }
    /**
     * 
     * Description:删除回报设置 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteBackSet(String id){
        try{
            this.crowdfundingBackSetDao.deleteByPrimaryKey(id);
        }catch(Exception e){
            throw new ApplicationException("删除回报设置失败");
        }
    }
    
    
    /**
     * 
     * Description: 查询众筹项目股权回报设置
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public StockBackSetModel getStockBackSet(String loanNo){
        return this.stockBackSetDao.getByLoanNo(loanNo);
    }
    
    /**
     * 
     * Description:查询众筹项目 设置
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getBackSetList(CrowdfundingBackSetModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(crowdfundingBackSetDao.getList(model));
        return result;
    }
    
    /**
     * 查询众筹项目当前回报已经设置的总金额
     * @param model
     * @return
     */
    public double getBackSetTotalAmt(String loanNo){
    	
    	CrowdfundingBackSetModel model = new CrowdfundingBackSetModel();
    	model.setLoanNo(loanNo);
    	double totalAmt = 0;
    	List<Map<String,Object>> list = crowdfundingBackSetDao.getList(model);
    	for(Map<String,Object> map:list){
    		double amt = map.get("amt")==null?0d:Double.parseDouble(String.valueOf(map.get("amt")));
    		int numberLimits =  map.get("numberLimits")==null?0:Integer.parseInt(String.valueOf(map.get("numberLimits")));
    		totalAmt+= (amt*numberLimits);
    	}
    	return totalAmt;
    }
    
    /**
     * 
     * Description:查询众筹项目进展
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingProgressDao.getList(model));
        result.setTotal(this.crowdfundingProgressDao.selectCount(model));
        return result;
    }
    
    /**
     * 
     * Description:查询众筹项目评论
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getCommentList(CrowdfundingCommentModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingCommentDao.getList(model));
        result.setTotal(this.crowdfundingCommentDao.getCount(model));
        return result;
    }
    
    /**
     * 
     * Description:查询众筹支持
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getSpportList(CrowdfundingSupportModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        try {
        	result.setRows(this.crowdfundingSupportDao.getList(model));
            result.setTotal(this.crowdfundingSupportDao.getCount(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    
    /**
     * 
     * Description:查询众筹支持（导出）
     *
     * @param 
     * @return 
     * @throws 
     * @Author wuwenbin<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getInvestPage4Loan(Map<String, Object> paramMap){
    	 return crowdfundingSupportDao.getPage("selectCount", "selectList", paramMap);
    }
    
    /**
     * 
     * Description:根据项目查询支付成功的支持记录 
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午3:48:21
     */
    public List<CrowdfundingSupportModel> getSupportList(String loanNo){
        return this.crowdfundingSupportDao.getByLoanNo(loanNo);
    }
    
    /**
     * 
     * Description: 审核评论
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午2:57:23
     */
    public void updateComment(CrowdfundingCommentModel model){
        try{
            this.crowdfundingCommentDao.updateBySelective(model);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("审核评论失败");
        }
    }
    
    /**
     * 
     * Description: 删除评论
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-11 下午2:57:23
     */
    public void delComment(String id){
        try{
            this.crowdfundingCommentDao.deleteByPrimaryKey(id);
        }catch(Exception e){
            throw new ApplicationException("删除评论失败");
        }
    }
    
    
    /**
     * 
     * Description:查询用户的资金统计
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getUserCapitalList(CrowdfundingModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingDao.getUserCapitalList(model));
        result.setTotal(this.crowdfundingDao.getUserCapitalListCount(model));
        return result;
    }
    /**
     * 
     * Description:查询用户的资金统计（导出）
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getUserCapitalList(Map<String, Object> paramMap){
   	 return crowdfundingDao.getPage("getUserCapitalCount", "selectUserCapitalList", paramMap);
    }
    
    private CrowdfundingModel getByLoanNo(String loanNo){
        CrowdfundingModel resultModel = crowdfundingDao.getByloanNo(loanNo);
        if(resultModel == null){
            throw new ApplicationException("您支持的众筹项目不存在");
        }
        return resultModel;
    }

    public CrowdfundingModel getCrowdfundByLoanNo(String loanNo) {
        return this.crowdfundingDao.getByloanNo(loanNo);
    }
    
    /**
     * 
     * Description:查询众筹的关注记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getAttentionList(CrowdfundingAttentionModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingAttentionDao.getList(model));
        result.setTotal(this.crowdfundingAttentionDao.getCount(model));
        return result;
    }
    
    
    /**
     * 
     * Description:查询众筹的审核记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<CrowdfundAuditModel> getAuditList(CrowdfundAuditModel model){
        SearchResult<CrowdfundAuditModel> result = new SearchResult<CrowdfundAuditModel>();
        result.setRows(this.crowdfundAuditDao.getList(model));
        return result;
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
	public void updateEquityBackSet(StockBackSetModel model) {
		stockBackSetDao.updateByLoanNo(model);
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
	public void updateLoanLevel(CrowdfundingModel model) {
		CrowdfundingModel crowdfundingModel = this.crowdfundingDao.getByloanNo(model.getLoanNo());
		crowdfundingModel.setLoanLevel(model.getLoanLevel());
		this.crowdfundingDao.update(crowdfundingModel);
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
	public void saveBonus(String loanNo, double bonusMoney) {
		try{
            CrowdfundingSupportModel model = new CrowdfundingSupportModel();
            model.setLoanNo(loanNo);
            model.setPayState(FbdCoreConstants.crowdFundPayState.payed);  //查询投资状态为投资成功的
            model.setState(CrowdfundCoreConstants.crowdFundOrderState.lended);
            List<Map<String,Object>> suppertList = this.crowdfundingSupportDao.getList(model);
            //查询一共投资的份数
            long sumBuyNum = this.crowdfundingSupportDao.selectSumBuyNum(model);
            
            //分红次数
            int bonusNum = this.getLastModel(loanNo);
            
            //保存项目分红总记录
            this.saveLoanBonus(loanNo,bonusNum,bonusMoney);
            
            for(Map<String,Object> item:suppertList){
            	System.out.println("item:"+item);
            	System.out.println("=====投资单号："+item.get("orderId").toString()+",支持金额："+item.get("supportAmt").toString()+",项目人："+item.get("loanUser").toString()+",投资人："+item.get("supportUser").toString()+"================");
                String investNo = item.get("orderId").toString();
                double supportAmt =Double.parseDouble(item.get("supportAmt").toString());
                String supportUser= item.get("supportUser").toString();
                //计算投资分红比例
                double ratio =Arith.div(Double.parseDouble(item.get("buyNum").toString()), sumBuyNum);
                logger.info("=============投资分红比例："+ratio+"");
                //根据分红比例计算应该获得的分红金额（分红总金额*分红比例）
                double shareAmt = Arith.round(bonusMoney*ratio);//分红金额
                logger.info("============投资编号：："+investNo+",应得分红金额："+shareAmt+"=====");
                try{
                	this.saveInvestShareBonus(loanNo, investNo, supportUser, supportAmt, shareAmt,bonusNum);
                }catch(Exception e){
                	e.printStackTrace();
                	logger.info("=====项目编号："+loanNo+",投资编号："+investNo+",应得分红金额："+shareAmt+",分红处理失败==========");
                	logger.warn(e.getMessage());
    				continue;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("分红奖励发放异常，项目编号["+loanNo+"]");
        }
	}
	
	/**
     * 
     * Description: 保存分红总记录分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	private void saveLoanBonus(String loanNo, int bonusNum,double bonusMoney) {
		CrowdfundBonusModel model = new CrowdfundBonusModel();
		model.setId(PKGenarator.getId());
		model.setBonusAmt(bonusMoney);
		model.setLoanNo(loanNo);
		model.setBonusAuditState(FbdCoreConstants.bonusAuditState.auditing);
		model.setBonusTime(new Date());
		model.setBonusNum(bonusNum+1);
		this.crowdfundBonusDao.save(model);
	}

	/***
     * * Description:发放分红详细 
     * @param invest  投资信息
     * @param shareAmt 应得分红比例
     */
    private void saveInvestShareBonus(String loanNo,String investNo,String investor,double investAmt,double shareAmt,int bonusNum){
        try{
        	
        	CrowdfundingSupportModel support = new CrowdfundingSupportModel();
        	support.setOrderId(investNo);  //设置投资编号
        	support.setLoanNo(loanNo);
        	support.setSupportAmt(investAmt);
        	support.setSupportUser(investor);
        	
            //插入分红发放明细
            this.addRewardAssgin(FbdCoreConstants.rewardAssignType.loan_share, support, shareAmt, 
            		investor, null, null,bonusNum);
//            //记账：平台出账
//             systemBillService.addLoanShareBonusSystemBill(shareAmt, investNo,investor);
//            //记账：投资人进
//            userBillService.addLoanShareBonusUserBill(shareAmt, investor, investNo);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("分红异常，投资编号["+investNo+"]");
        }
    }
    
    
    /**
     * 
     * Description: 保存分红明细
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-3-12 上午11:11:47
     */
    private void addRewardAssgin(String rewardType,CrowdfundingSupportModel invest,double assignAmt,
            String getUser,String payUser,String invitorType,int bonusNum){
        RewardAssignModel model = new RewardAssignModel();
        model.setId(PKGenarator.getId());
        model.setRewardType(rewardType);
        model.setInvestNo(invest.getOrderId());
        model.setLoanNo(invest.getLoanNo());
        model.setAssignAmt(assignAmt);
        model.setGetUser(getUser);
        model.setPayUser("system");
        model.setAssignTime(new Date());
        model.setInvestAmt(invest.getSupportAmt());
        model.setInvestor(invest.getSupportUser());
        model.setInvitorType(invitorType);
        model.setBonusNum(bonusNum+1);

        model.setCreateTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        model.setBonusState(FbdCoreConstants.bonusState.wait_bonus);

        this.rewardAssignDao.save(model);
    }
    
    
    /**
     * Description: 查询当前是第几次分红
     *
     * @param 
     * @return RewardAssignModel
     * @throws 
     * @Author wuwenbin
     */
    
    public int getLastModel(String loanNo) {
//        RewardAssignModel lastModel = this.rewardAssignDao.selectLastNum(loanNo);
//        if(null == lastModel){
//            lastModel = new RewardAssignModel();
//            lastModel.setBonusNum(0);
//        }
//        return lastModel.getBonusNum();
    	return (int) this.rewardAssignDao.selectLastNum(loanNo);
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
	public void updateEndBonus(CrowdfundingModel model) {
		
		
		//查询项目信息
		CrowdfundingModel crow = this.crowdfundingDao.selectByPrimaryKey(model.getId());
		//检测该项目是否还有在审核中的分红信息
		String loanNo = crow.getLoanNo();
		
		CrowdfundBonusModel bonus = new CrowdfundBonusModel();
		bonus.setLoanNo(loanNo);
		bonus.setBonusAuditState(FbdCoreConstants.bonusAuditState.auditing);
		List<Map<String,Object>> list = this.crowdfundBonusDao.getLoanBonusList(bonus);
		if(list!=null && list.size()>0){
			throw new ApplicationException("该项目还有审核中的分红，不能结束项目！");
		}
		this.crowdfundingDao.updateBySelective(model);
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
	public void saveCrowdfunding(CrowdfundingModel model) {
		 try{
	            model.setId(PKGenarator.getId());
	            model.setLoanNo(PKGenarator.getLoanId());
	            model.setCreateTime(new Date());
	            model.setLoanState(CrowdfundCoreConstants.crowdFundingState.add.getValue());
	            //model.setChargeFee(this.getServiceRatio());
	            //model.setLoanDeposit(this.getDepositRatio());
	            model.setApproveAmt(0.0);
	            model.setPrepayAmt(0.0);
	            
	            if(StringUtil.isEmpty(model.getLoanLevel())){
	            	model.setLoanLevel("1");
	            }
	            
	            this.crowdfundingDao.save(model);
	            
	            //增加众筹明细
	            CrowdfundDetailModel crowdfundDetail = new CrowdfundDetailModel();
	            crowdfundDetail.setId(PKGenarator.getId());
	            crowdfundDetail.setLoanNo(model.getLoanNo());
	            crowdfundDetailDao.save(crowdfundDetail);
	            
	            //增加股权回报设置
	            if(model.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.HOUSE) ||
	                    model.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
	                StockBackSetModel stockBackSet = new StockBackSetModel();
	                stockBackSet.setLoanNo(model.getLoanNo());
	                stockBackSet.setId(PKGenarator.getId());
	                this.stockBackSetDao.save(stockBackSet);
	            }
	            
	            
	            // 插入项目审核记录,审核动作为：new;项目状态为new
	            crowdfundAuditDao.addLoanAudit(model.getLoanUser(), model.getLoanNo(),
	                    CrowdfundCoreConstants.loanAuditAction.add.getValue(), null,
	                    CrowdfundCoreConstants.crowdFundingState.add.getValue());
	            
	        }catch(Exception e){
	        	e.printStackTrace();
	            throw new ApplicationException("发起众筹项目失败");
	        }
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
	public Boolean getBackByLoanNo(String loanNo) {
		
		CrowdfundingModel crowdfundModel = this.crowdfundingDao.getByloanNo(loanNo);
		/*if(crowdfundModel.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.HOUSE) ||
				crowdfundModel.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
			StockBackSetModel stockModel = this.stockBackSetDao.getByLoanNo(loanNo);
			if(null != stockModel){
				return true;
			}else{
				return false;
			}
		}else{
			CrowdfundingBackSetModel model = new CrowdfundingBackSetModel();
			model.setLoanNo(loanNo);
			List<Map<String,Object>> listMap = this.crowdfundingBackSetDao.getList(model);
			if(listMap.size()>0){
				return true;
			}else{
				return false;
			}
		}*/
		
		if(!crowdfundModel.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
			CrowdfundingBackSetModel model = new CrowdfundingBackSetModel();
			model.setLoanNo(loanNo);
			List<Map<String,Object>> listMap = this.crowdfundingBackSetDao.getList(model);
			if(listMap.size()>0){
				return true;
			}else{
				return false;
			}
		}
		return true;
		
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
	public SearchResult<Map<String, Object>> getUserPrizeList(
			CrowdfundUserPrizeModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundUserPrizeDao.getUserPrizeList(model));
		result.setTotal(this.crowdfundUserPrizeDao.getUserPrizeCount(model));
		return result;
	}

	/**
     * 
     * Description: 审核产品项目申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateRefundState(CrowdfundRefundAuditModel model) {
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(model.getSupportNo());
		model.setId(PKGenarator.getId());
		model.setAuditTime(new Date());
		this.crowdfundRefundAuditDao.save(model);
		supportModel.setRefundState(model.getAuditState());
		supportModel.setRefuseFailReason(model.getAuditOpinion());
		this.crowdfundingSupportDao.updateByOrderNo(supportModel);
		CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(supportModel.getLoanNo());
		//查询项目方
		UserModel loanUser = this.userDao.findByUserId(crowdfund.getLoanUser());
		//查询投资人
		UserModel supportUser = this.userDao.findByUserId(supportModel.getSupportUser());
		
		if(model.getAuditState().equals(FbdCoreConstants.refundState.passed)){
			//审核通过  给用户进行转账
			supportModel.setRefundState(FbdCoreConstants.refundState.auditSubmitProcess);
			//审核通过后需要将项目账户钱转账到投资人账户
		    String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
			Map<String,String> params = this.getAuditRefundPassedParams(crowdfund,supportModel, supportUser);
			logger.info("退款审核通过请求数据-params："+params);
			//保存操作数据
			String operationId = PKGenarator.getId();
	        trusteeshipOperationService.saveOperationModel(operationId, supportModel.getOrderId(),
	        		 supportModel.getOrderId(),LetvPayConstants.BusiType.refund_passed,requestUrl,"blockChain",MapUtil.mapToString(params));
	        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
	        	supportModel.setRefundState(FbdCoreConstants.refundState.refund_success); //区块链转账成功
	        	supportModel.setState(CrowdfundCoreConstants.crowdFundOrderState.refunded);  //订单状态为已退款
	        	supportModel.setRefuseComplateTime(new Date());
	        	//用户账单信息
	            //添加项目方退款成功账单
	            this.userBillService.addLoanRefundSucceeBill(supportModel, loanUser.getUserId());
	            //添加投资人退款成功账单
	            this.userBillService.addSupportUserRefundSucceeBill(supportModel, supportUser.getUserId());
	        }else{
	            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
	    		logger.info("区块链-项目方审核退款请求数据："+resultStr);
	            @SuppressWarnings("unchecked")
	            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
	            String status = resultMap.get("status").toString();
	            String message = resultMap.get("message").toString();
	            if("Success".equals(status)){//转账事务成功
	            	supportModel.setRefundState(FbdCoreConstants.refundState.auditSubmitProcess); //提交处理中
	            	//添加一条事务通知数据
	            	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
	            	blockAsynTran.setId(PKGenarator.getId());
	            	blockAsynTran.setParentId(supportModel.getOrderId());  //支持编号
	            	blockAsynTran.setCreateTime(new Date());
	            	blockAsynTran.setUpdateTime(new Date());
	            	blockAsynTran.setType(BlockChainCore.Type.REFUNDPASSEDAUDIT);
	            	blockAsynTran.setStatus(BlockChainCore.ResultStatus.SUCCESS);
	            	this.blockAsynTranDao.save(blockAsynTran);
	            	//给用户添加资金流水
	            }else{
	            	throw new ApplicationException(message);
	            }
	        }
			this.crowdfundingSupportDao.updateByOrderNo(supportModel);
		} else if(model.getAuditState().equals(FbdCoreConstants.refundState.refuse)){
			//如果拒绝需要把项目方的钱退回去 
			//审核拒绝后需要将项目账户钱转账到投资人账户
		    String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
			Map<String,String> params = this.getAuditRefundRefuseParams(crowdfund,supportModel, loanUser);
			logger.info("退款审核拒绝请求数据-params："+params);
			//保存操作数据
			String operationId = PKGenarator.getId();
	        trusteeshipOperationService.saveOperationModel(operationId, supportModel.getOrderId(),
	        		 supportModel.getOrderId(),LetvPayConstants.BusiType.refund_refuse,requestUrl,"blockChain",MapUtil.mapToString(params));
	        
	        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
	        	supportModel.setRefundState(FbdCoreConstants.refundState.refuse); //退款拒绝
	        	supportModel.setRefuseComplateTime(new Date());
	        	//添加用户账单(解冻项目方的账户)
	        	this.userBillService.addRefundAuditFailUnFrzeeBill(supportModel, loanUser.getUserId());
	        }else{
	            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
	    		logger.info("区块链-项目方审核退款请求数据："+resultStr);
	            @SuppressWarnings("unchecked")
	            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
	            String status = resultMap.get("status").toString();
	            String message = resultMap.get("message").toString();
	            if("Success".equals(status)){//转账事务成功
	            	supportModel.setRefundState(FbdCoreConstants.refundState.auditSubmitProcess); //提交处理中
	            	//添加一条事务通知数据
	            	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
	            	blockAsynTran.setId(PKGenarator.getId());
	            	blockAsynTran.setParentId(supportModel.getOrderId());  //支持编号
	            	blockAsynTran.setCreateTime(new Date());
	            	blockAsynTran.setUpdateTime(new Date());
	            	blockAsynTran.setType(BlockChainCore.Type.REFUNDREFUSEAUDIT);
	            	blockAsynTran.setStatus(BlockChainCore.ResultStatus.SUCCESS);
	            	this.blockAsynTranDao.save(blockAsynTran);
	            	//给用户添加资金流水
	            }else{
	            	logger.info("转账请求失败："+message);
	            	throw new ApplicationException(message);
	            }
	        }
			this.crowdfundingSupportDao.updateByOrderNo(supportModel);
		}
	}
	
	/**
	 * 审核通过将项目中间账户的钱转给投资人
	 * @param crowdfund
	 * @param supportModel
	 * @param supportUser
	 * @return
	 */
	private Map<String, String> getAuditRefundPassedParams(CrowdfundingModel crowdfund,CrowdfundingSupportModel supportModel,UserModel supportUser) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",supportModel.getOrderId()); //转让编号传支持编号
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress",crowdfund.getBlockChainAddress()); //项目中间账户
		params.put("sourceKey",crowdfund.getBlockChainSecretKey());
		params.put("targetAddress",supportUser.getBlockChainAddress());  //转入账户为借款人账户
		String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = "0.01";
		}else{
			amount = String.valueOf(Arith.round(supportModel.getSupportAmt()+supportModel.getTransFee()));
		}
		params.put("amount",amount);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingRefund/auditRefundPassedNotify.html");
		return params;
	}
	
	/**
	 * 审核拒绝后将项目中间账户的钱退给项目方
	 * @param crowdfund
	 * @param supportModel
	 * @param supportUser
	 * @return
	 */
	private Map<String, String> getAuditRefundRefuseParams(CrowdfundingModel crowdfund,CrowdfundingSupportModel supportModel,UserModel loanUser) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",supportModel.getOrderId()); //转让编号传支持编号
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress",crowdfund.getBlockChainAddress()); //项目中间账户
		params.put("sourceKey",crowdfund.getBlockChainSecretKey());
		params.put("targetAddress",loanUser.getBlockChainAddress());  //转入账户为借款人账户
		String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = "0.01";
		}else{
			amount = String.valueOf(Arith.round(supportModel.getSupportAmt()+supportModel.getTransFee()));
		}
		params.put("amount",amount);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingRefund/auditRefundRefuseNotify.html");
		return params;
	}

    /**
     * 
     * Description: 退款审核通过转账
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */ 
	private void sendApplicaitonRefundSuccess( CrowdfundingSupportModel supportModel) {
		Map<String,String> params = this.getRefundParam(supportModel);
		String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
		String userResultStr = "";
		if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
		     userResultStr = MockUtils.transfer(requestUrl, params);
		}else{
			
		}
        Map<String,Object> userResultMap = JsonHelper.getMapFromJson(userResultStr);
        if(userResultMap.get("status").equals("TransactionSuccess")){
        	supportModel.setState(CrowdfundCoreConstants.crowdFundOrderState.refunded);
        	this.crowdfundingSupportDao.update(supportModel);
        	//增加退款账单
        	this.userBillService.addBill(supportModel.getSupportUser(),
        			FbdCoreConstants.userTradeType.refund.getValue(),
        			supportModel.getSupportAmt(), FbdCoreConstants.tradeDirection.in.getValue(), supportModel.getLoanNo(), "用户申请退款", null, supportModel.getOrderId());
        	
        	if(supportModel.getTransFee()>0){
        		//增加退款账单
            	this.userBillService.addBill(supportModel.getSupportUser(),
            			FbdCoreConstants.userTradeType.refund_transFee.getValue(),
            			supportModel.getTransFee(), FbdCoreConstants.tradeDirection.in.getValue(), supportModel.getLoanNo(), "用户申请退款_退还运费", null, supportModel.getOrderId());
        	}
        	
        	supportModel.setRefundState(supportModel.getLoanState());
    		this.crowdfundingSupportDao.update(supportModel);
    		
    		//项目支持金额变动
    		CrowdfundingModel model=crowdfundingDao.getByloanNo(supportModel.getLoanNo());
    		model.setApproveAmt(Arith.sub(model.getApproveAmt(), supportModel.getSupportAmt()));
    		crowdfundingDao.update(model);
        }else{
        	throw new ApplicationException("退款转账失败,请重新审核！");
        }
        
	} 
	/**
     * 
     * Description: 退款审核通过转账  组装参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String,String> getRefundParam(CrowdfundingSupportModel supportModel){
		//查询用户信息
		UserModel supportUser = this.userDao.findByUserId(supportModel.getSupportUser());
		//查询项目信息
		 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(supportModel.getLoanNo());
		Map<String,String> map = new HashMap<String, String>();
		map.put("serviceID", "transfer");
		map.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		map.put("sourceAddress",crowd.getBlockChainAddress()); //转出账户地址
		map.put("sourceKey", crowd.getBlockChainSecretKey());
		map.put("targetAddress",supportUser.getBlockChainAddress());
		map.put("amount",String.valueOf(Arith.round(Arith.add(supportModel.getSupportAmt(), supportModel.getTransFee()))));
		map.put("notifyURL","");
		return map;
	}

	/**
     * 
     * Description: 查询产品项目申请退款列表
     * 
     * @param
     * @return SearchResult<Map<String, Object>>
     * @throws
     * @Author wuwenbin
     */
	public SearchResult<Map<String, Object>> getLoanAuditRefundPage(
			CrowdfundRefundAuditModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundRefundAuditDao.getLoanAuditRefundList(model));
		result.setTotal(this.crowdfundRefundAuditDao.getLoanAuditRefundCount(model));
		return result;
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
	public SearchResult<Map<String, Object>> getBuyTransferListPage(
			CrowdfundProductTransferModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.crowdfundProductTransferDao.getBuyTransferListPage(model));
		result.setTotal(this.crowdfundProductTransferDao.getBuyTransferCountPage(model));
		return result;
	}
	/**
	 * 验证转让数据
	 * @param transferModel
	 */
	private void checkProductTransferData(CrowdfundProductTransferModel transferModel){
		if(transferModel!=null){
			if(StringUtils.isEmpty(transferModel.getTransferUser())){
				throw new ApplicationException("转让人不存在");
			}
			if(StringUtils.isEmpty(transferModel.getBuyUser())){
				throw new ApplicationException("购买人不存在");
			}
			if(transferModel.getTransferAmt()==null){
				throw new ApplicationException("转让金额不存在");
			}
			if(transferModel.getTransferFee()==null){
				throw new ApplicationException("转让手续费不存在");
			}
		}else{
			throw new ApplicationException("转让编号不存在");
		}
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
	@SuppressWarnings("unchecked")
	public void updateProductTransferAudit(CrowdfundProductTransferModel model) {
		CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(model.getTransferNo());
		transferModel.setTransferAuditOpinion(model.getTransferAuditOpinion());
		checkProductTransferData(transferModel);
		//判断是否为审核通过
		if(FbdCoreConstants.transferAuditState.passed.equals(model.getTransferAuditState())){
			//区块链给 出售人转账
			if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
				this.sendProductTransfer(transferModel.getTransferNo(),model.getTransferAuditOpinion());
			}else{
				String loanNo=transferModel.getLoanNo();
				if(StringUtils.isEmpty(loanNo)){
					throw new ApplicationException("项目编号不能为空");
				}
				if(StringUtils.isEmpty(transferModel.getTransferUser())){
					throw new ApplicationException("转让人不能为空");
				}
				CrowdfundingModel loan=crowdfundingDao.getByloanNo(loanNo);
				UserModel user=userDao.findByUserId(transferModel.getTransferUser());
				String requestId=PKGenarator.getOrderId();
				double amount =Arith.add(Arith.sub(transferModel.getTransferAmt(), transferModel.getTransferFee()), transferModel.getTransFee());
				Map<String,String> params = BlockChainUtil.transfer(transferModel.getTransferNo(), loan.getBlockChainAddress(), loan.getBlockChainSecretKey(), user.getBlockChainAddress(), amount, "blockChainTransferAction/transferSuccessReceiveTransferS2S.html", requestId);
	    		//添加一条事务通知数据
	        	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
	        	blockAsynTran.setId(PKGenarator.getId());
	        	blockAsynTran.setParentId(params.get(BlockChainCore.TRANSFER_NO));  //支持编号
	        	blockAsynTran.setCreateTime(new Date());
	        	blockAsynTran.setType(BlockChainCore.Type.TRANSFER_AUDIT_SUCCESS);
	        	blockAsynTran.setRequestID(requestId);
	        	this.blockAsynTranDao.save(blockAsynTran);		
				
		        String userResultStr = MockUtils.transfer(params);
		        Map<String,Object> userResultMap = JsonHelper.getMapFromJson(userResultStr);
		        String status=userResultMap.get(BlockChainCore.STATUS).toString();
		        if(ResultStatus.ServiceSuccess.equals(status)){
		        	transferModel.setTransferAuditState(FbdCoreConstants.transferAuditState.auditing_passed);
		    		this.crowdfundProductTransferDao.update(transferModel);
		    		if(transferModel.getTransferFee()>0){
			    		String requestId1=PKGenarator.getOrderId();
			    		Map<String,String> params1 = BlockChainUtil.transfer(transferModel.getTransferNo(), loan.getBlockChainAddress(), loan.getBlockChainSecretKey(), FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT, transferModel.getTransferFee(), "blockChainTransferAction/transferSuccessReceiveTransferFeeS2S.html", requestId1);
			    		
			    		//添加一条事务通知数据
			        	BlockAsynTranModel blockAsynTran1 = new BlockAsynTranModel();
			        	blockAsynTran1.setId(PKGenarator.getId());
			        	blockAsynTran1.setParentId(params1.get(BlockChainCore.TRANSFER_NO));  //支持编号
			        	blockAsynTran1.setCreateTime(new Date());
			        	blockAsynTran1.setType(BlockChainCore.Type.TRANSFER_AUDIT_FEE_SUCCESS);
			        	blockAsynTran1.setRequestID(requestId1);
			        	this.blockAsynTranDao.save(blockAsynTran1);
			        	
				        String userResultStr1 = MockUtils.transfer(params1);
				        Map<String,Object> userResultMap1=JsonHelper.getMapFromJson(userResultStr1);	
				        String status1=userResultMap1.get(BlockChainCore.STATUS).toString();
				        if(ResultStatus.ServiceSuccess.equals(status1)){
				        	try{
					        	Thread.sleep(4000);
					            Timer timer=new Timer();
					            timer.schedule(new TransSuccessEndTask(timer,blockAsynTran.getRequestID(),blockAsynTran1.getRequestID(),params1), 5000, 3000);   
					        }catch(Exception e){
				        		e.printStackTrace();
				        	}
				        
				        }
		    		}
		        }
			}
		}else{
			if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
	    		//异步start
				//解冻购买人金额
				this.userBillService.addByUserTransferUnfreeze(transferModel);
				transferModel.setTransferAuditState(FbdCoreConstants.transferAuditState.refuse);
	    		this.crowdfundProductTransferDao.update(transferModel);
	    		Map<String, String> result=new HashMap<String,String>();
	    		result.put(BlockChainCore.TRANSFER_NO, BlockChainStringUtil.getUniqueTransferNoEnc(transferModel.getTransferNo()));
	    		blockChainCrowdfundingService.sendProductTransferErrorMsg(result);
	    		//异步end
			}else{
				//Map<String,String> params = this.getProductTransferFailParam(transferModel);
				Map<String,String> params = new HashMap<String,String>();
				String loanNo=transferModel.getLoanNo();
				if(StringUtils.isEmpty(loanNo)){
					throw new ApplicationException("项目编号不能为空");
				}
				if(StringUtils.isEmpty(transferModel.getBuyUser())){
					throw new ApplicationException("购买人不能为空");
				}
				String requestId=PKGenarator.getId();
				CrowdfundingModel loan=crowdfundingDao.getByloanNo(loanNo);
				UserModel user=userDao.findByUserId(transferModel.getTransferUser());
				//接口标识
				params.put("serviceID", "transfer");
				//转账订单号
				params.put("transferNO",BlockChainStringUtil.getUniqueTransferNoEnc(transferModel.getTransferNo()));
				//产品码
				params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
				//转出账号地址
				params.put("sourceAddress",loan.getBlockChainAddress()); //转出账户地址为平台出账户
				//转出账号秘钥
				params.put("sourceKey",loan.getBlockChainSecretKey());
				//转入账号地址
				params.put("targetAddress",user.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
				DecimalFormat dFormat=new DecimalFormat("#.00");
				String amount = dFormat.format(Arith.add(transferModel.getTransferAmt(), transferModel.getTransFee()));
				//转账金额
				params.put("amount",amount);
				params.put(BlockChainCore.REQUEST_ID, requestId);
				//异步回调地址
				params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainTransferAction/transferFailReceiveTransferS2S.html");

		        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
		        	params.put("amount","0.01");
	        	 }
				
	    		//添加一条事务通知数据
	        	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
	        	blockAsynTran.setId(PKGenarator.getId());
	        	blockAsynTran.setParentId(params.get(BlockChainCore.TRANSFER_NO));  //支持编号
	        	blockAsynTran.setCreateTime(new Date());
	        	blockAsynTran.setUpdateTime(new Date());
	        	blockAsynTran.setType(BlockChainCore.Type.TRANSFER_AUDIT_FAIL);
	        	blockAsynTran.setRequestID(requestId);
	        	this.blockAsynTranDao.save(blockAsynTran);
				
		        String userResultStr = MockUtils.transfer(params);
		        Map<String,Object> userResultMap = JsonHelper.getMapFromJson(userResultStr);
		        String status=userResultMap.get(BlockChainCore.STATUS).toString();
				
		        if(ResultStatus.ServiceSuccess.equals(status)){
		        	transferModel.setTransferAuditState(FbdCoreConstants.transferAuditState.auditing_refuse);
		    		this.crowdfundProductTransferDao.update(transferModel);
		        }	
			}
		}
		
	}

    static class TransSuccessEndTask extends java.util.TimerTask{
        private Timer timer;
        private String requestId,requestId1;
        private Map<String,String> data;
        
        public TransSuccessEndTask(Timer timer,String requestId,String requestId1,Map<String,String> data){
            this.timer=timer;
            this.requestId=requestId;
            this.requestId1=requestId1;
            this.data=data;
        }
        
        @Override
        public void run() {
            try{
                IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                BlockAsynTranModel model=dao.findByRequestId(requestId);
                if(model!=null){
                   String queryStatus=model.getQueryStatus();
                   logger.info("------------queryStatus:"+queryStatus);
                   if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                       BlockAsynTranModel model1=dao.findByRequestId(requestId1);
                       queryStatus=model1.getQueryStatus();
                       logger.info("------------queryStatus:"+queryStatus);
                       if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                           IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                           opService.updateProductTransferEndSuccess(data);
                           opService.sendProductTransferMsg(data);
                           logger.info("------------------Success-------------------------");
                           timer.cancel();
                           System.gc();  
                       }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
                    	   logger.info("------------------Error-------------------------");
                    	   //timer.cancel();
                       }
                   }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
                	   logger.info("------------------Error-------------------------");
                       //timer.cancel();    
                   }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
	
	
	private void sendProductTransfer(String transferNo,String transferAuditOpinion) {
		if(StringUtils.isEmpty(transferNo)){
			throw new ApplicationException("转让编号不能为空");
		}
		CrowdfundProductTransferModel model=crowdfundProductTransferDao.getByTransferNo(transferNo);
		model.setTransferAuditOpinion(transferAuditOpinion);
		crowdfundProductTransferDao.update(model);
		
		Map<String, String> result=new HashMap<String,String>();
		result.put(BlockChainCore.TRANSFER_NO, BlockChainStringUtil.getUniqueTransferNoEnc(transferNo));
		
		blockChainCrowdfundingService.updateProductTransferAuditSuccess(result);
		blockChainCrowdfundingService.updateProductTransferSystemAuditSuccess(result);
		blockChainCrowdfundingService.updateProductTransferEndSuccess(result);
		blockChainCrowdfundingService.sendProductTransferMsg(result);
	}
	
	/**
     * 
     * Description: 为购买用户新增投资记录
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private void saveSupport(CrowdfundProductTransferModel transferModel,double transFee) {
		//为购买用户新增投资记录
		CrowdfundingSupportModel modle = new CrowdfundingSupportModel();
		modle.setId(PKGenarator.getId());
		modle.setSupportAmt(transferModel.getTransferAmt());
		modle.setActualPayAmt(transferModel.getTransferAmt());
		modle.setOrderId(PKGenarator.getOrderId());
		modle.setSupportUser(transferModel.getTransferUser());
		modle.setBackNo(transferModel.getBackNo());
		modle.setLoanNo(transferModel.getLoanNo());
		modle.setSupportTime(new Date());
		modle.setCompleteTime(new Date());
		modle.setTransFee(transFee);
		modle.setSupportClass(CrowdfundCoreConstants.supportClass.transferSupport.getValue());
		this.crowdfundingSupportDao.save(modle);
	}
	/**
	 * 
	 * @param transferModel
	 * @return 转让审核通过
	 */
/*	private Map<String, String> getProductTransferParam(
			CrowdfundProductTransferModel transferModel) {
		Map<String,String> params = new HashMap<String,String>();
		String loanNo=transferModel.getLoanNo();
		if(StringUtils.isEmpty(loanNo)){
			throw new ApplicationException("项目编号不能为空");
		}
		if(StringUtils.isEmpty(transferModel.getTransferUser())){
			throw new ApplicationException("转让人不能为空");
		}
		CrowdfundingModel loan=crowdfundingDao.getByloanNo(loanNo);
		UserModel user=userDao.findByUserId(transferModel.getTransferUser());
		//接口标识
		params.put("serviceID", "transfer");
		//转账订单号
		params.put("transferNO",BlockChainStringUtil.getUniqueTransferNoEnc(transferModel.getTransferNo()));
		//产品码
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		//转出账号地址
		params.put("sourceAddress",loan.getBlockChainAddress()); //转出账户地址为平台出账户
		//转出账号秘钥
		params.put("sourceKey",loan.getBlockChainSecretKey());
		//转入账号地址
		params.put("targetAddress",user.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
		DecimalFormat dFormat=new DecimalFormat("#.00");
		String amount = dFormat.format(Arith.add(transferModel.getTransferAmt(), transferModel.getTransFee()));
		//转账金额
		params.put("amount",amount);
		//异步回调地址
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainTransferAction/transferSuccessReceiveTransferS2S.html");
		return params;
	}*/
	/**
	 * 
	 * @param transferModel
	 * @return 转让审核失败
	 */
/*	private Map<String, String> getProductTransferFailParam(
			CrowdfundProductTransferModel transferModel) {
		Map<String,String> params = new HashMap<String,String>();
		String loanNo=transferModel.getLoanNo();
		if(StringUtils.isEmpty(loanNo)){
			throw new ApplicationException("项目编号不能为空");
		}
		if(StringUtils.isEmpty(transferModel.getBuyUser())){
			throw new ApplicationException("购买人不能为空");
		}
		CrowdfundingModel loan=crowdfundingDao.getByloanNo(loanNo);
		UserModel user=userDao.findByUserId(transferModel.getTransferUser());
		//接口标识
		params.put("serviceID", "transfer");
		//转账订单号
		params.put("transferNO",BlockChainStringUtil.getUniqueTransferNoEnc(transferModel.getTransferNo()));
		//产品码
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		//转出账号地址
		params.put("sourceAddress",loan.getBlockChainAddress()); //转出账户地址为平台出账户
		//转出账号秘钥
		params.put("sourceKey",loan.getBlockChainSecretKey());
		//转入账号地址
		params.put("targetAddress",user.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
		DecimalFormat dFormat=new DecimalFormat("#.00");
		String amount = dFormat.format(Arith.add(transferModel.getTransferAmt(), transferModel.getTransFee()));
		//转账金额
		params.put("amount",amount);
		//异步回调地址
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainTransferAction/transferFailReceiveTransferS2S.html");
		return params;
	}*/



	@Override
	public List<CrowdfundingSupportModel> selectIntentionsByLoanNo(String loanNo) {
		// TODO Auto-generated method stub
		return crowdfundingSupportDao.selectIntentionsByLoanNo(loanNo);
	}


    
}
