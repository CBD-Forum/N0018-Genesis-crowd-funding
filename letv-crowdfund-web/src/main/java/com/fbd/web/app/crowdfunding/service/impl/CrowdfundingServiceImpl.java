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

package com.fbd.web.app.crowdfunding.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.bill.dao.IUserBillDao;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundDetailDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundPhotoDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundRefundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingAttentionDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingCommentDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingOperateDataDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingPraiseDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressFilesDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.dao.IStockBackSetDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundPhotoModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingOperateDataModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingPraiseModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressFilesModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.fileupload.dao.IFileUploadDao;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.dao.IRewardAssignDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSignatureDao;
import com.fbd.core.app.user.dao.IUserUploadFileDao;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSignatureModel;
import com.fbd.core.app.user.model.UserUploadFileModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.core.util.SpringPropertiesHolder;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.user.service.IUserService;

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
    private IBusinessConfigDao businessConfigDao;
    
    @Resource
    private ICrowdfundingPraiseDao crowdfundingPraiseDao;
    
    @Resource
    private ICrowdfundingAttentionDao crowdfundingAttentionDao;
    
    @Resource
    private ICrowdfundUserStuffDao crowdfundUserStuffDao;
    
    @Resource
    private ICrowdfundPhotoDao crowdfundPhotoDao;
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IStockBackSetDao stockBackSetDao;
    @Resource
    private ICrowdfundAuditDao crowdfundAuditDao;
    @Resource
    private IUserUploadFileDao userUploadFileDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserService userService;
    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao;
    @Resource
    private IFileUploadDao fileUploadDao;
	@Resource
	private ICrowdfundingProgressFilesDao crowdfundingProgressFilesDao;
    @Resource
    private ICrowdfundingOperateDataDao crowdfundingOperateDataDao;
    @Resource
    private IPushDataLogDao pushDataLogDao;
    @Resource
    private ICrowdfundBonusDao crowdfundBonusDao; 
    @Resource
    private IRewardAssignDao rewardAssignDao;
    @Resource
    private ICrowdfundRefundAuditDao crowdfundRefundAuditDao ;
	@Resource
	private IUserSignatureDao userSignatureDao;
	@Resource
	private IUserBillDao userBillDao;
	@Resource
	private IUserBillService userBillService ;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
	
    @Resource
    private ICrowdfundProductTransferDao crowdfundProductTransferDao;
	
    
	
	/**
	 * 查询用户是否点赞过或者收藏过该项目
	 * @param userId
	 * @param LoanNo
	 * @return
	 */
	public Map<String,Object> getUserOperateLoan(String userId,String loanNo){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//查询收藏
     	CrowdfundingAttentionModel attention = null;
     	//查询点赞
     	CrowdfundingPraiseModel praise = null;
  		attention = new CrowdfundingAttentionModel();
  		attention.setLoanNo(loanNo);
  		attention.setAttentionUser(userId);
  		long count  = this.crowdfundingAttentionDao.getCount(attention);
  		if(count>0){
  			resultMap.put("attentionFlag", true);  //关注标识(收藏)
  		}else{
  			resultMap.put("attentionFlag", false);  //关注标识(收藏)
  		}
  		//查询是否点赞过项目
  		praise = new CrowdfundingPraiseModel();
  		praise.setLoanNo(loanNo);
  		praise.setPraiseSessionId(userId);
  		CrowdfundingPraiseModel qPraise = this.crowdfundingPraiseDao.selectByModel(praise);
  		if(qPraise!=null){   
  			resultMap.put("praiseFlag", true);  //点赞标识
  		}else{
  			resultMap.put("praiseFlag", false);
  		}
		return resultMap;
	}
 
	 
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
    public void saveCrowdfunding(CrowdfundingModel model,CrowdfundDetailModel crowdfundDetail ){
        try{
        	
        	String loanType = model.getLoanType();
        	if("stock".equals(loanType)){  //如果是股权项目，验证项目发起人是否设置印章，如果没有设置印章，则提示用户去设置印章
    		    //查询用户签约信息
                UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(model.getLoanUser());
        		if(userSignature==null){
        			throw new ApplicationException("项目发起人未设置电子签章！");
        		}
        	}
            String loanNo = PKGenarator.getLoanId();
            model.setId(PKGenarator.getId());
            model.setLoanNo(loanNo);
            model.setCreateTime(new Date());
            model.setLoanState(CrowdfundCoreConstants.crowdFundingState.add.getValue());
            
            model.setChargeFee((Double)this.getServiceFeeScale().get("serviceFeeScale"));
            //model.setChargeFee(this.getServiceRatio());
            //model.setLoanDeposit(this.getDepositRatio());
            model.setApproveAmt(0.0);
            model.setPrepayAmt(0.0);
            if(StringUtil.isEmpty(model.getLoanLevel())){
            	model.setLoanLevel("1");
            }
            //设置超募金额，如果超募金额为空则设置超募金额为募集金额
/*            if(model.getOverFundAmt() == 0 || model.getOverFundAmt()==null){
            	model.setOverFundAmt(model.getFundAmt());
            }*/
            if(!"stock".equals(model.getLoanType())){
                if(model.getOverFundAmt()==null || model.getOverFundAmt() == 0){
                	throw new ApplicationException("最高上限金额不能为空");
                }else if(model.getOverFundAmt()<model.getFundAmt()){
                	throw new ApplicationException("最高上限金额必须大于等于筹集金额");
                }else{
                	if(Arith.sub(model.getOverFundAmt(),model.getFundAmt())>0){
                		//当超募情况
                        model.setCanOver("1");
                	}else{
                		model.setCanOver("0");
                	}
                }	
            }else{
            	//股权
            	if(model.getOverFundAmt()==null || model.getOverFundAmt() == 0){
                	model.setOverFundAmt(model.getFundAmt());
                	model.setCanOver("0");
                }
            }
            this.crowdfundingDao.save(model);
            
            //如果是股权类项目则需要更新收益的项目编号
            if("stock".equals(model.getLoanType())){
            	String tempLoanNo = model.getTempLoanNo();
            	CrowdfundingOperateDataModel operateData = new CrowdfundingOperateDataModel();
            	operateData.setLoanNo(loanNo);
            	operateData.setTempLoanNo(tempLoanNo);
            	crowdfundingOperateDataDao.updateloanNoByLoanNo(operateData);
            	//更新附件
            	
            	FileUploadModel fileUpload = new FileUploadModel();
            	fileUpload.setParentId(loanNo);
            	fileUpload.setTempParentId(tempLoanNo);
            	this.fileUploadDao.updateParentIdByParentId(fileUpload);
            }
            //增加众筹明细
            crowdfundDetail.setId(PKGenarator.getId());
            crowdfundDetail.setLoanNo(model.getLoanNo());
            crowdfundDetail.setLoanDetail(model.getLoanDetail());
            crowdfundDetail.setLoanVideo(model.getLoanVideo());
            crowdfundDetailDao.save(crowdfundDetail);
            //增加股权回报设置
//            if(model.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.HOUSE) ||
//                    model.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
//                StockBackSetModel stockBackSet = new StockBackSetModel();
//                stockBackSet.setLoanNo(model.getLoanNo());
//                stockBackSet.setId(PKGenarator.getId());
//                this.stockBackSetDao.save(stockBackSet);
//            }
            
            
            // 插入项目审核记录,审核动作为：new;项目状态为new
            crowdfundAuditDao.addLoanAudit(model.getLoanUser(), model.getLoanNo(),
                    CrowdfundCoreConstants.loanAuditAction.add.getValue(), null,
                    CrowdfundCoreConstants.crowdFundingState.add.getValue());
            
        }catch(Exception e){
        	e.printStackTrace();
            throw new ApplicationException(e.getMessage());
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
     * Description: 查询众筹项目明细
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public CrowdfundingModel getCrowdfundByLoanNo(String loanNo){
        return this.crowdfundingDao.getByloanNo(loanNo);
    }
    
    
    /**
     * 
     * Description: 查询众筹项目明细
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:37:32
     */
    public CrowdfundingModel getCrowdfundById(String id){
        return this.crowdfundingDao.selectByPrimaryKey(id);
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
        	System.out.println("loanState:"+model.getLoanState());
        	
        	if(model.getOverFundAmt()==null|| model.getOverFundAmt() == 0 ){
        		//当不存在的情况不处理
            }else if(model.getOverFundAmt()<model.getFundAmt()){
            	throw new ApplicationException("最高上限金额必须大于等于筹集金额");
            }else{
            	if(Arith.sub(model.getOverFundAmt(),model.getFundAmt())>0){
            		//当超募情况
                    model.setCanOver("1");
            	}else{
            		model.setCanOver("0");
            	}
            }
        	this.crowdfundingDao.updateBySelective(model);
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
    public SearchResult<Map<String,Object>> getPageList(CrowdfundingModel model,String loaginUserId){
        if(model.getSort() == null || "".equals(model.getSort())){
            model.setSort("defaultSort");
        }
        //查询个人中心中我申请的项目全部项目
        String loanStateAll = model.getLoanStateAll();
        if("all".equals(loanStateAll)){
        	model.setLoanState(null);
        }else{
        	if(model.getLoanState() == null || "".equals(model.getLoanState())){
            	model.setLoanStateIn("1");
            }
        }
        
        //查询项目的意向金支付比例
        //意向金支付比例
		Double yxjPayScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode()==null?"0.0":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode());
		//意向金支付天数
		Integer yxjPayDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode()==null?"0":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode());
		//平台收取违约意向金比例
		Double yxjPlatformRatio = Double.valueOf(businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode()==null?"0.0":businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode());
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        List<Map<String,Object>> resultList = this.crowdfundingDao.getList(model);
        
        for(Map<String,Object> item:resultList){
        	item.put("yxjPayDay", yxjPayDay);
        	item.put("yxjPlatformRatio", yxjPlatformRatio);
        	item.put("yxjPayScale", yxjPayScale);
        }
        
        if(loaginUserId!=null && !"".equals(loaginUserId)){
        	//遍历项目列表查询用户是否收藏过该项目
        	List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        	//查询收藏
        	CrowdfundingAttentionModel attention = null;
        	//查询点赞
        	CrowdfundingPraiseModel praise = null;
         	for(Map<String,Object> item:resultList){
         		String loanNo = item.get("loanNo").toString();
         		attention = new CrowdfundingAttentionModel();
         		attention.setLoanNo(loanNo);
         		attention.setAttentionUser(loaginUserId);
         		long count  = this.crowdfundingAttentionDao.getCount(attention);
         		if(count>0){
         			item.put("attentionFlag", true);  //关注标识(收藏)
         		}else{
         			item.put("attentionFlag", false);  //关注标识(收藏)
         		}
         		//查询是否点赞过项目
         		praise = new CrowdfundingPraiseModel();
         		praise.setLoanNo(loanNo);
         		praise.setPraiseSessionId(loaginUserId);
         		CrowdfundingPraiseModel qPraise = this.crowdfundingPraiseDao.selectByModel(praise);
         		if(qPraise!=null){   
         			item.put("praiseFlag", true);  //点赞标识
         		}else{
         			item.put("praiseFlag", false);
         		}
         		rows.add(item);
        	}
         	result.setRows(rows);
        }else{
        	result.setRows(resultList);
        }
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
    public Map<String,Object> getCrowdFundDetail(String loanNo,String userId){
    	
    	Map<String,Object> loanDetail = this.crowdfundingDao.getLoanDetail(loanNo);
    	if(loanDetail!=null){
    	   	//项目类型
        	String loanType = loanDetail.get("loanType").toString(); 
        	if("stock".equals(loanType)){//股权项目
        		//查询商业计划书
        		FileUploadModel qbusinessProposal = new FileUploadModel();
        		qbusinessProposal.setParentId(loanNo);
        		qbusinessProposal.setType("businessProposal");
        		List<FileUploadModel>  businessProposalFiles = this.fileUploadDao.selectList(qbusinessProposal);
        		loanDetail.put("businessProposalFiles", businessProposalFiles);
        		//查询其他方案
        		FileUploadModel qotherAccessories = new FileUploadModel();
        		qotherAccessories.setParentId(loanNo);
        		qotherAccessories.setType("otherAccessories");
        		List<FileUploadModel>  otherAccessoriesFiles = this.fileUploadDao.selectList(qotherAccessories);
        		loanDetail.put("otherAccessoriesFiles", otherAccessoriesFiles);    		
        		//查询退出方案
        		FileUploadModel qexitScheme = new FileUploadModel();
        		qexitScheme.setParentId(loanNo);
        		qexitScheme.setType("exitScheme");
        		List<FileUploadModel>  exitSchemeFiles = this.fileUploadDao.selectList(qexitScheme);
        		loanDetail.put("exitSchemeFiles", exitSchemeFiles);    
        		//查询预计收益（列表）
        		CrowdfundingOperateDataModel qOperateData = new CrowdfundingOperateDataModel();
        		qOperateData.setLoanNo(loanNo);
        		List<CrowdfundingOperateDataModel> operateDataList = this.crowdfundingOperateDataDao.selectList(qOperateData);
        		loanDetail.put("operateDataList", operateDataList);
        		
        		//查询用户有没有投资过该项目
        		
        		if(!StringUtil.isEmpty(userId)){
            		CrowdfundingSupportModel supportModel =  new CrowdfundingSupportModel();
            		supportModel.setSupportUser(userId);
            		supportModel.setPayState("payed");
            		supportModel.setLoanNo(loanNo);
            		List<Map<String,Object>> supportList = this.crowdfundingSupportDao.getList(supportModel);
            		if(supportList!=null && supportList.size()>0){
            			loanDetail.put("investFlag", true);
            		}else{
            			loanDetail.put("investFlag", false);
            		}
        		}
        		//意向金支付比例
        		Double yxjPayScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode()==null?"0.0":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode());
        		loanDetail.put("yxjPayScale", yxjPayScale);
        		
        		//意向金支付天数
        		Integer yxjPayDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode()==null?"0":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode());
        		loanDetail.put("yxjPayDay", yxjPayDay);
        		
        		//平台收取违约意向金比例
        		Double yxjPlatformRatio = Double.valueOf(businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode()==null?"0.0":businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode());
        		loanDetail.put("yxjPlatformRatio", yxjPlatformRatio);
        		
        	}else if("product".equals(loanType)){ //产品
        		
        	}else if("public".equals(loanType)){  //公益
        		//查询公募机构项目接收证明
        		FileUploadModel qorgLoanReceiveProve = new FileUploadModel();
        		qorgLoanReceiveProve.setParentId(loanNo);
        		qorgLoanReceiveProve.setType("orgLoanReceiveProve");
        		List<FileUploadModel>  orgLoanReceiveProveFiles = this.fileUploadDao.selectList(qorgLoanReceiveProve);
        		loanDetail.put("orgLoanReceiveProveFiles", orgLoanReceiveProveFiles);    
        	}
        	
	    	if(userId!=null && !"".equals(userId)){
	         	//查询收藏
	         	CrowdfundingAttentionModel attention = null;
	         	//查询点赞
	         	CrowdfundingPraiseModel praise = null;
	      		attention = new CrowdfundingAttentionModel();
	      		attention.setLoanNo(loanNo);
	      		attention.setAttentionUser(userId);
	      		long count  = this.crowdfundingAttentionDao.getCount(attention);
	      		if(count>0){
	      			loanDetail.put("attentionFlag", true);  //关注标识(收藏)
	      		}else{
	      			loanDetail.put("attentionFlag", false);  //关注标识(收藏)
	      		}
	      		//查询是否点赞过项目
	      		praise = new CrowdfundingPraiseModel();
	      		praise.setLoanNo(loanNo);
	      		praise.setPraiseSessionId(userId);
	      		CrowdfundingPraiseModel qPraise = this.crowdfundingPraiseDao.selectByModel(praise);
	      		if(qPraise!=null){   
	      			loanDetail.put("praiseFlag", true);  //点赞标识
	      		}else{
	      			loanDetail.put("praiseFlag", false);
	      		}
	         }else{
	        	 loanDetail.put("praiseFlag", false);
	         } 
        	
        	//查询当前每日的预计收益率
        	double dailyProfitRate = 0.0;
        	BusinessConfigModel config = this.businessConfigDao.getBusConfigByDisplayName("DAILY_INCOME_RATE");
        	if(config!=null){
        		dailyProfitRate = Double.parseDouble(config.getCode());
        	}
        	loanDetail.put("dailyProfitRate", dailyProfitRate);
    	}
 
        return loanDetail;
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
            model.setId(PKGenarator.getId());
            model.setBackNo(PKGenarator.getLoanId());
            model.setState(FbdCoreConstants.crowdFundBackState.NOTFULL);
            this.crowdfundingBackSetDao.save(model);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("保存回报设置失败");
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
            this.crowdfundingBackSetDao.updateBySelective(model);
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
            e.printStackTrace();
            throw new ApplicationException("删除回报设置失败");
        }
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
    public SearchResult<Map<String,Object>> getBackSetList(CrowdfundingBackSetModel model,String userId){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        List<Map<String,Object>> list = crowdfundingBackSetDao.getList(model);
        if(!StringUtil.isEmpty(userId)){
        	List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        	for(Map<String,Object> map:list){
        		String loanNo = (String)map.get("loanNo");
        		String backNo = (String)map.get("backNo");
        		
        		//查询用户支付成功的投资次数
        		CrowdfundingSupportModel qmodel = new CrowdfundingSupportModel();
        		qmodel.setLoanNo(loanNo);
        		qmodel.setBackNo(backNo);
        		qmodel.setSupportUser(userId);
        		qmodel.setPayState("payed");
        		long count = this.crowdfundingSupportDao.getCount(qmodel);
                long payingNum =crowdfundingSupportDao.selectPayingParts(loanNo);

        		map.put("supportCount", count+payingNum);
        		rows.add(map);
        	}
        	result.setRows(rows);
        }else{
        	result.setRows(list);
        }
        return result;
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
//    public SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model){
//        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
//        result.setRows(this.crowdfundingProgressDao.getList(model));
//        return result;
//    }
    
    
	/**
	 * 分页查询
	 */
    public SearchResult<Map<String,Object>> getProgessList(CrowdfundingProgressModel model){
       
    	SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.selectDetailPageList(model));
//        result.setTotal(this.crowdfundingProgressDao.selectCount(model));
        return result;
    }
    
    
    private List<Map<String,Object>> selectDetailPageList(CrowdfundingProgressModel model){
    	List<Map<String,Object>> list = this.crowdfundingProgressDao.getList(model);
    	List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
    	
    	for(Map<String,Object> item :list){
    		String id = item.get("id").toString();
    		CrowdfundingProgressFilesModel model1 = new CrowdfundingProgressFilesModel();
    		model1.setType("img");
    		model1.setParentId(id);
    	    List<CrowdfundingProgressFilesModel> imgFileList = this.crowdfundingProgressFilesDao.selectList(model1);
    	    item.put("imgFileList", imgFileList);
    	    resultList.add(item);
    	}
    	return resultList;
    }
    
    private List<CrowdfundingProgressModel> selectDetailList(CrowdfundingProgressModel model){
    	List<Map<String,Object>> list = this.crowdfundingProgressDao.getList(model);
    	List<CrowdfundingProgressModel> resultList = new ArrayList<CrowdfundingProgressModel>();
    	
    	for(Map<String,Object> item :list){
    		String id = item.get("id").toString();
    		CrowdfundingProgressModel model1 = this.selectDetailById(id);
    		resultList.add(model1); 
    	}
    	return resultList;
    }
    
	/**
	 * 查询详情
	 * @param id
	 * @return
	 */
	public CrowdfundingProgressModel selectDetailById(String id){
		
		CrowdfundingProgressModel model = this.crowdfundingProgressDao.selectByPrimaryKey(id);
		CrowdfundingProgressFilesModel model1 = new CrowdfundingProgressFilesModel();
		model1.setType("img");
		model1.setParentId(id);
	    List<CrowdfundingProgressFilesModel> imgFileList = this.crowdfundingProgressFilesDao.selectList(model1);
		
	    CrowdfundingProgressFilesModel model2 = new CrowdfundingProgressFilesModel();
		model2.setType("vedio");
		model2.setParentId(id);
	    List<CrowdfundingProgressFilesModel> vedioFileList = this.crowdfundingProgressFilesDao.selectList(model2);
	    model.setImgFileList(imgFileList);
	    model.setVedioFileList(vedioFileList);
		return model;
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
        List<Map<String,Object>> list = this.crowdfundingCommentDao.getList(model);
        List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        for(Map<String,Object> map:list){
        	String id = map.get("id").toString();
        	CrowdfundingCommentModel subModel = new CrowdfundingCommentModel();
        	subModel.setPid(id);
        	List<Map<String,Object>> subList = this.crowdfundingCommentDao.getList(subModel);
        	map.put("subList", subList);
        	rows.add(map);
        }
        result.setRows(rows);
        result.setTotal(this.crowdfundingCommentDao.getCount(model));
        return result;
    }
    /**
     * 查询项目评论数量
     * @param model
     * @return
     */
    public long getCommentCount(CrowdfundingCommentModel model){
    	return this.crowdfundingCommentDao.getCount(model);
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
        
        result.setRows(this.crowdfundingSupportDao.getList(model));
        result.setTotal(this.crowdfundingSupportDao.getCount(model));
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
    public CrowdfundingSupportModel getSupportById(String id){
        return this.crowdfundingSupportDao.selectByPrimaryKey(id);
    }
    
    
    /**
     * 
     * Description: 评论
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-31 上午11:03:12
     */
    public void saveComment(CrowdfundingCommentModel model){
        model.setId(PKGenarator.getId());
        model.setState(FbdCoreConstants.crowdFundAuditState.passed);
        model.setDiscussTime(new Date());
        crowdfundingCommentDao.save(model);
    }
    
    /**
     * 
     * Description:用户的总支持金额 
     *
     * @param 
     * @return double
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 上午11:35:07
     */
    public Map<String,Object> getSupportAmtAndBuyAmt(String userId){
        return this.crowdfundingSupportDao.getSupportAmtAndBuyAmt(userId);
    }
    
    /**
     * 
     * Description:点赞保存 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午4:26:07
     */
    public void savePraise(CrowdfundingPraiseModel model){
    	CrowdfundingPraiseModel qmodel = this.crowdfundingPraiseDao.selectByModel(model);
    	if(qmodel!=null){
    		throw new ApplicationException("对不起，不能重复点赞！");
    	}
        model.setId(PKGenarator.getId());
        crowdfundingPraiseDao.save(model);
    }
    
    /**
     * 
     * Description: 更新支持
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-8 下午7:06:36
     */
    public void updateSupport(CrowdfundingSupportModel model){
        this.crowdfundingSupportDao.updateBySelective(model);
    }
    
    
    /**
     * 
     * Description: 更新支持的发货信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-8 下午7:06:36
     */
    public String updateSupportSendInfo(CrowdfundingSupportModel model){
    	//查询是否有转让的信息
    	CrowdfundProductTransferModel transferModel =crowdfundProductTransferDao.getBySupportNo(model.getOrderId());
        String errorMsg="";
        CrowdfundingSupportModel support = this.crowdfundingSupportDao.selectByPrimaryKey(model.getId());
        CrowdfundingModel crowdfund = this.getCrowdfundByLoanNo(support.getLoanNo());
    	if(transferModel!=null){
    		//说明有转让记录
        	if(StringUtil.isEmpty(transferModel.getTransferAuditState())){
        		//说明还没购买
        		if(CrowdfundCoreConstants.transferStateFbd.transfering_locking.getValue().equals(transferModel.getTransferState())){
        			errorMsg = "购买操作中不能发货";
        			return	errorMsg;
        		}else{
        			//转让结束
        			transferModel.setTransferState(CrowdfundCoreConstants.transferStateFbd.transferend.getValue());
                    crowdfundProductTransferDao.update(transferModel);
                    
                    //查看产品项目是否存在于转让市场，如果在转让市场的话，取消转让
                    if("product".equals(crowdfund.getLoanType())){
                    	this.sendSendingCancelTranserMessage(crowdfund, transferModel);
                    }
        		}
        	}else{
            	if(FbdCoreConstants.transferAuditState.auditing.equals(transferModel.getTransferAuditState())){
            		errorMsg = "后台审核中不能发货";
            		return errorMsg;
            	}/*else if(FbdCoreConstants.transferAuditState.refuse.equals(transferModel.getTransferAuditState())){
            		errorMsg = "后台拒绝不能发货";
            		return	errorMsg;
            	}*/else if(FbdCoreConstants.transferAuditState.passed.equals(transferModel.getTransferAuditState())){
            		//通过
            	}	
        	}     
        }
        this.crowdfundingSupportDao.updateBySelective(model);
        
        //全部发货成功后，变更众筹的状态为结束
        long noSendCnt = this.crowdfundingSupportDao.getNoSendSupportListCount(support.getLoanNo());
        if(noSendCnt == 0){
            crowdfund.setLoanState(CrowdfundCoreConstants.crowdFundingState.end.getValue());
            this.crowdfundingDao.update(crowdfund);
        }
        //发送投资发货信息
        this.sendSendingMessage(support,crowdfund);
        return errorMsg;
    }
    
    
    /**
     * Description: 发送发货消息
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendSendingMessage(CrowdfundingSupportModel model,CrowdfundingModel crowdfund){
       Map<String, String> params = new HashMap<String,String>();
       params.put("loanName",crowdfund.getLoanName());
       params.put("loanNo",model.getLoanNo());
       try{
           logger.info("发送项目发货消息手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_SUPPORT_SENDING_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getSupportUser(), params);
           logger.info("发送项目发货消息手机短信结束");
       }catch(Exception e){
           logger.error("发送项目发货消息手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目发货消息站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_SUPPORT_SENDING_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG,model.getSupportUser(), params);
            logger.info("发送项目发货消息站内信结束");
        }catch(Exception e){
            logger.error("发送项目发货消息站内信失败，"+e.getMessage());
        }
    }
    
    
    
    /**
     * Description: 发送发货取消转让消息
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendSendingCancelTranserMessage(CrowdfundingModel crowdfund,CrowdfundProductTransferModel transferModel){
       Map<String, String> params = new HashMap<String,String>();
       params.put("loanName",crowdfund.getLoanName());
       params.put("transferNo",transferModel.getTransferNo());
     
        try{
            logger.info("发送项目发货取消转让消息站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_SENDING_CANCEL_TRANSFER_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG,transferModel.getTransferUser(), params);
            logger.info("发送项目发货取消转让消息站内信结束");
        }catch(Exception e){
            logger.error("发送项目发货取消转让消息站内信失败，"+e.getMessage());
        }
    }
    
    /**
     * 
     * Description:关注 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-9 下午8:40:25
     */
    public void saveAttention(CrowdfundingAttentionModel model){
        List<Map<String,Object>> aList = this.crowdfundingAttentionDao.getList(model);
        if(aList.size()>0){
            throw new ApplicationException("您已经关注");
        }
        try{
            model.setId(PKGenarator.getId());
            model.setAttentionTime(new Date());
            this.crowdfundingAttentionDao.save(model);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("关注失败");
        }
    }
    
    /**
     * 
     * Description:分页查询关注列表 
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-10 上午10:02:14
     */
    public SearchResult<Map<String,Object>> getAttentionList(CrowdfundingAttentionModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingAttentionDao.getList(model));
        result.setTotal(this.crowdfundingAttentionDao.getCount(model));
        return result;
    }
    
    
    /**
     * 
     * Description:取消关注
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteAttention(String id){
        try{
            this.crowdfundingAttentionDao.deleteByPrimaryKey(id);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("取消关注失败");
        }
    }
    
    
    /**
     * 
     * Description:更新众筹发起人的资料 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午3:13:50
     */
    public void updateCrowdfundUserStuff(CrowdfundUserStuffModel userStuff){
        this.crowdfundUserStuffDao.updateBySelective(userStuff);
    }
    
    /**
     * 
     * Description:查询众筹发起人的资料 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午3:13:50
     */
    public void getCrowdfundUserInfo(String userId){
        this.crowdfundUserStuffDao.getCrowdfundUserInfo(userId);
    }
    
    
    /**
     * 
     * Description: 上传项目图片
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午3:56:54
     */
    public void updateLoanPhoto(String loanPhotos,String loanNo){
        if(StringUtil.isEmpty(loanPhotos)){
            return;
        }
        //删除原有图片
        this.crowdfundPhotoDao.deleteByLoanNo(loanNo);
        //新增图片
        String[] photoList = loanPhotos.split(",");
        for(String photo:photoList){
            CrowdfundPhotoModel model = new CrowdfundPhotoModel();
            model.setId(PKGenarator.getId());
            model.setLoanNo(loanNo);
            model.setPhotoUrl(photo);
            crowdfundPhotoDao.save(model);
        }
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
    
    /**
     * 
     * Description: 更新股权回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午8:22:38
     */
    public void updateStockBackSet(StockBackSetModel model){
        CrowdfundingModel crowdfund = this.getCrowdfundByLoanNo(model.getLoanNo());
        double fundAmt = crowdfund.getFundAmt();
        double projectFundAmt = model.getProjectFinanceAmt();
        double investFundAmt = Arith.round(fundAmt-projectFundAmt);
        model.setInvestFinanceAmt(investFundAmt);
        double projectRatio = Arith.round(model.getProjectBonusRatio()/100);
        if(projectRatio > 1){
            throw new ApplicationException("项目方分红比例不能超过100%");
        }
        model.setProjectBonusRatio(projectRatio);
        model.setInvestBonusRatio(Arith.round(1-projectRatio));
        this.stockBackSetDao.updateByLoanNo(model);
    }
    
    
    /**
     * 
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-28 上午11:38:19
     */
    public void saveUserUploadFile(UserUploadFileModel model){
        UserUploadFileModel result = this.userUploadFileDao.getByFileType(model.getUserId(), model.getFileType());
        if(result == null){
            model.setId(PKGenarator.getId());
            model.setFileState(CrowdfundCoreConstants.UserFileState.auditing);
            this.userUploadFileDao.save(model);
        }else{
            this.userUploadFileDao.update(model);
        }
    }

	public Map<String, Object> getCrowdPhotos(String loanNo) {
		Map<String,Object> crowdPhotos = new HashMap<String, Object>();
		List<CrowdfundPhotoModel> photoModels = crowdfundPhotoDao.getByLoanNo(loanNo);
		String photos = "";
		if(photoModels != null && photoModels.size() >0 ){
			for(int i=0;i<photoModels.size();i++){
				photos  += photoModels.get(i).getPhotoUrl()+",";
				crowdPhotos.put("loanPhotos", photos);
			}
		}
		return crowdPhotos;
	}

	public StockBackSetModel getStockBack(String loanNo) {
		return stockBackSetDao.getByLoanNo(loanNo);
	}
    
    /**
     * 
     * Description:删除项目 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-9 下午3:12:09
     */
    public void deleteCrowdfund(String loanNo){
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
        this.crowdfundingDao.deleteByloanNo(loanNo);
        if(crowd.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.ENTITY)||
                crowd.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PUBLIC_SERVICE)){
            this.crowdfundingBackSetDao.deleteByloanNo(loanNo);
        }else{
            this.stockBackSetDao.deleteByLoanNo(loanNo);
        }
        this.crowdfundDetailDao.deleteByLoanNo(loanNo);
    }
    
    /**
     * 
     * Description:删除支持列表
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-9 下午3:12:09
     */
    public void deleteSupport(String id){
        CrowdfundingSupportModel support = this.crowdfundingSupportDao.selectByPrimaryKey(id);
        if(support.getPayState().equals(CrowdfundCoreConstants.crowdFundPayState.noPay)){
            this.crowdfundingSupportDao.deleteByPrimaryKey(id);
        }else{
            throw new ApplicationException("支持状态为已支付，无法删除");
        }
    }

	public SearchResult<Map<String, Object>> getLeaderSupportList(
			CrowdfundingSupportModel model) {
		SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingSupportDao.getLeaderSupportList(model));
        result.setTotal(this.crowdfundingSupportDao.getLeaderSupportCount(model));
        return result;
	}
	
	 /**
     * 
     * Description: 平台统计
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-12-11 下午12:24:40
     */
	public Map<String,Object> getStatsData(){
		return this.crowdfundingDao.getStatsData();
	}

	/**
     * 
     * Description: 统计前台展示数据(已投资总额；项目总数；投资人总数)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getCountInfo() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		//查询已投资总额
		double sumSupportAmt = this.crowdfundingDao.getSumSupportAmt();
		//查询项目总数
		long countLoan = this.crowdfundingDao.getCountLoan();
		//查询投资人总数
		long countUser = this.userDao.getCountUser();
		
		map.put("sumSupportkAmt", sumSupportAmt);
		map.put("countLoan", countLoan);
		map.put("countUser",countUser);
		return map;
	}
	
	/**
     * 
     * Description:取消关注
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:58:24
     */
    public void deleteAttention(CrowdfundingAttentionModel model){
    	
    	List<Map<String,Object>> aList = this.crowdfundingAttentionDao.getList(model);
        try{
        	 if(aList.size()>0){
        		 this.crowdfundingAttentionDao.deleteByPrimaryKey(aList.get(0).get("id").toString());
             }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("取消关注失败");
        }
    }

    /**
     * 
     * Description: 保存用户认证信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveUserAuth(CrowdfundUserStuffModel model) {
		String id = model.getId();
		if(StringUtil.isEmpty(id)){
			id = PKGenarator.getId();
			model.setId(id);
		}
		//查询是否存在当前用户的认证信息
		CrowdfundUserStuffModel qmodel = new CrowdfundUserStuffModel();
		qmodel.setUserId(model.getUserId());
		qmodel.setAuthType(model.getAuthType());
		CrowdfundUserStuffModel crowdUserStuff = this.crowdfundUserStuffDao.getCrowdfundUserAuth(model);
		if(crowdUserStuff!=null){
			throw new ApplicationException("认证信息已经存在！");
		}else{
			model.setUserLevel(CrowdfundCoreConstants.UserLevel.common);
	        this.crowdfundUserStuffDao.save(model);
	        //更新附件
	        String tempId = model.getTempId();
	    	FileUploadModel fileUpload = new FileUploadModel();
	    	fileUpload.setParentId(id);
	    	fileUpload.setTempParentId(tempId);
	    	this.fileUploadDao.updateParentIdByParentId(fileUpload);
		}
	}

	/**
     * 
     * Description: 查询用户中奖列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
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
     * Description: 查询认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public CrowdfundUserStuffModel getCrowdfundUserAuth(CrowdfundUserStuffModel model) {
		
		CrowdfundUserStuffModel crowdUserStuff = this.crowdfundUserStuffDao.getCrowdfundUserAuth(model);
		//orgLeadInvestor-领投机构  orgInvestor-跟投机构 leadInvestor-领投人   investor-投资人
		if(("orgLeadInvestor".equals(model.getAuthType()) || "orgInvestor".equals(model.getAuthType())) && crowdUserStuff!=null ){  //如果认证类型是机构的查询机构相关的文件信息
			//机构认证中机构证件
			FileUploadModel orgCardPhoto = new FileUploadModel();
			orgCardPhoto.setParentId(crowdUserStuff.getId());
			orgCardPhoto.setType("orgCardPhoto");
    		List<FileUploadModel>  orgCardPhotoList = this.fileUploadDao.selectList(orgCardPhoto);
    		crowdUserStuff.setOrgCardPhotoList(orgCardPhotoList);
    		
    		//机构资产认证资料
			FileUploadModel orgAssetsCredentials = new FileUploadModel();
			orgAssetsCredentials.setParentId(crowdUserStuff.getId());
			orgAssetsCredentials.setType("orgAssetsCredentials");
    		List<FileUploadModel>  orgAssetsCredentialsList = this.fileUploadDao.selectList(orgAssetsCredentials);
    		crowdUserStuff.setOrgAssetsCredentialsList(orgAssetsCredentialsList);
    		
    		//机构历史投资证明
			FileUploadModel orgHistoricalInvestMent = new FileUploadModel();
			orgHistoricalInvestMent.setParentId(crowdUserStuff.getId());
			orgHistoricalInvestMent.setType("orgHistoricalInvestMent");
    		List<FileUploadModel>  orgHistoricalInvestMentList = this.fileUploadDao.selectList(orgHistoricalInvestMent);
    		crowdUserStuff.setOrgHistoricalInvestMentList(orgHistoricalInvestMentList);
		}else if(("leadInvestor".equals(model.getAuthType()) || "investor".equals(model.getAuthType()))  && crowdUserStuff!=null){
			
			//个人资产认证资料
			FileUploadModel assetsCredentials = new FileUploadModel();
			assetsCredentials.setParentId(crowdUserStuff.getId());
			assetsCredentials.setType("assetsCredentials");
    		List<FileUploadModel>  assetsCredentialsList = this.fileUploadDao.selectList(assetsCredentials);
    		crowdUserStuff.setAssetsCredentialsList(assetsCredentialsList);
    		
    		//历史投资资料
			FileUploadModel historicalInvestMent = new FileUploadModel();
			historicalInvestMent.setParentId(crowdUserStuff.getId());
			historicalInvestMent.setType("historicalInvestMent");
    		List<FileUploadModel>  orgAssetsCredentialsList = this.fileUploadDao.selectList(historicalInvestMent);
    		crowdUserStuff.setHistoricalInvestMentList(orgAssetsCredentialsList);
		}
		
		return crowdUserStuff;
	}
	
	
	
	/**
     * 
     * Description: 查询认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
	public CrowdfundUserStuffModel selectByModel(CrowdfundUserStuffModel model) {
		return this.crowdfundUserStuffDao.selectByModel(model);
	}
	

	/**
     * 
     * Description: 查询服务费比列
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getServiceFeeScale() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Double serviceFeeScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.SERVICE_FEE_SCALE).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.SERVICE_FEE_SCALE).getCode());
		map.put("serviceFeeScale", serviceFeeScale);
		return map;
	}

	/**
     * 
     * Description: 产品投资申请退款前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void checkBeforeRefundApplication(String orderId) {
		//判断当前项目是否支持退款 ,判断当前投资是否已经转让或者已经为退款状态
		CrowdfundingSupportModel model = this.crowdfundingSupportDao.getByOrderId(orderId);
		CrowdfundingModel crowdfundModle = this.crowdfundingDao.getByloanNo(model.getLoanNo());
		
		
		//只有已经放款的项目才能进行退款
		if(!FbdCoreConstants.crowdFundingState.lended.getValue().equals(crowdfundModle.getLoanState())){
			throw new ApplicationException("该项目不支持申请退款！");
			
		}
        if("1".equals(model.getIsTransfer())){
			throw new ApplicationException("您已经转让该投资不能申请退款！");
		}
//        if(FbdCoreConstants.refundState.auditing.equals(model.getRefundState())||
//				FbdCoreConstants.refundState.auditing.equals(model.getRefundState())){
//			throw new ApplicationException("您已在申请退款中,请不要重复申请！");
//		}
//        if(FbdCoreConstants.refundState.passed.equals(model.getRefundState())){
//			throw new ApplicationException("您已申请退款成功！");
//		}
	}
	
	/**
     * 
     * Description: 产品投资申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateRefundApplication(CrowdfundingSupportModel supportModel) {
		CrowdfundingSupportModel model = this.crowdfundingSupportDao.getByOrderId(supportModel.getOrderId());
		model.setRefundState(FbdCoreConstants.refundState.waitConfirm);
		model.setApplicationContent(supportModel.getApplicationContent());
		model.setRefuseApplyTime(new Date());
		this.crowdfundingSupportDao.update(model);
		
		
		//发送站内信
		CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(model.getLoanNo());
		CrowdfundingBackSetModel setModel = this.crowdfundingBackSetDao.getByLoanNoAndSetNo(model.getLoanNo(),model.getBackNo());
		this.sendRefundMessage(model,crowdfund,setModel.getBackContent());
		
	}


	@Override
	public List<Map<String, Object>> selectLoanFail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try{
			// TODO Auto-generated method stub
			Map<String,Object>pushMap = pushDataLogDao.selectModel("loanfail");
			if(pushMap!=null){
				param.put("completeTime", pushMap.get("lastPushTime"));
			}
			param.put("payMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
			param.put("payAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
			PushDataLogModel pushDataLogModel=new PushDataLogModel();
			pushDataLogModel.setId(PKGenarator.getId());
			List<Map<String,Object>>list = crowdfundingDao.pushLoanFail(param);
			if(list!=null&&list.size()>0){
				Map<String,Object>map = list.get(0);
	
				if(map!=null){
					pushDataLogModel.setLastPushTime(DateUtil.date2Str((Date)map.get("businessDate"), DateUtil.DEFAULT_DATE_TIME_FORMAT));
					pushDataLogModel.setMemo("募集失败退款推送");
					pushDataLogModel.setEventCode("loanfail");
					pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
					pushDataLogDao.savePushDataLog(pushDataLogModel);
				}
			}
		   return list;	
		}catch(Exception e){
				e.printStackTrace();
		}
			return null;
	}

	@Override
	public List<Map<String, Object>> selectIsnvestmentFunds(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
				try{
					// TODO Auto-generated method stub
					Map<String,Object>pushMap = pushDataLogDao.selectModel("investmentFunds");
					if(pushMap!=null){
						param.put("completeTime", pushMap.get("lastPushTime"));
					}
					param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
					param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
					PushDataLogModel pushDataLogModel=new PushDataLogModel();
					pushDataLogModel.setId(PKGenarator.getId());
					List<Map<String,Object>>list = crowdfundingDao.selectIsnvestmentFunds(param);
					if(list!=null&&list.size()>0){
						Map<String,Object>map = list.get(0);
			
						if(map!=null){
							pushDataLogModel.setLastPushTime(DateUtil.date2Str((Date)map.get("businessDate"), DateUtil.DEFAULT_DATE_TIME_FORMAT));
							pushDataLogModel.setMemo("投资划款推送");
							pushDataLogModel.setEventCode("investmentFunds");
							pushDataLogModel.setPushTime(DateUtil.date2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
							pushDataLogDao.savePushDataLog(pushDataLogModel);
						}
					}
				   return list;	
				}catch(Exception e){
						e.printStackTrace();
				}
				return null;
	}


	
	/**
     * 
     * Description: 发送申请退款站内信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendRefundMessage(CrowdfundingSupportModel model,CrowdfundingModel crowdfund,String backContent){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
       params.put("loanName",crowdfund.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("refundAmt",Arith.format(Arith.add(model.getSupportAmt(), model.getTransFee())));
       params.put("backContent",backContent);
       
        try{
            logger.info("发送支付站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_REFUND_APPLICATION_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, null, model.getLoanUser(), params);
            logger.info("发送支付站内信结束");
        }catch(Exception e){
            logger.error("发送支付站内信失败，"+e.getMessage());
        }
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
	public String saveBonus(String loanNo, double bonusMoney) {
		String bonusLoanId = "";
		try{
			//查询是否有正在审核中的分红
			CrowdfundBonusModel qmodel = new CrowdfundBonusModel();
			qmodel.setBonusAuditState(FbdCoreConstants.bonusAuditState.auditing);
			qmodel.setLoanNo(loanNo);
			List<Map<String, Object>> bonusList = this.crowdfundBonusDao.getLoanBonusList(qmodel);
			if(bonusList!=null && bonusList.size()>0){
				throw new ApplicationException("目前还有正在审核中的分红！");
			}
            CrowdfundingSupportModel model = new CrowdfundingSupportModel();
            model.setLoanNo(loanNo);
            model.setPayState(FbdCoreConstants.crowdFundPayState.payed);  //查询投资状态为投资成功的
            model.setState(CrowdfundCoreConstants.crowdFundOrderState.lended);
            List<Map<String,Object>> suppertList = this.crowdfundingSupportDao.getList(model);
            //查询一共投资的份数
            long sumBuyNum = this.crowdfundingSupportDao.selectSumBuyNum(model);
            if(sumBuyNum<=0){
            	throw new ApplicationException("该项目没有可以分红的投资！");
            }
            //查询项目的分红次数
            int bonusNum = this.getLastModel(loanNo);
            //保存项目分红总记录
            bonusLoanId = this.saveLoanBonus(loanNo,bonusNum,bonusMoney);
            
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
                	this.saveInvestShareBonus(loanNo, investNo, supportUser, supportAmt, shareAmt,bonusNum,bonusLoanId);
                }catch(Exception e){
                	e.printStackTrace();
                	logger.info("=====项目编号："+loanNo+",投资编号："+investNo+",应得分红金额："+shareAmt+",分红处理失败==========");
                	logger.warn(e.getMessage());
    				continue;
                }
            }
        }catch(ApplicationException e1){
        	throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("分红奖励发放异常，项目编号["+loanNo+"]");
        }
		return bonusLoanId;
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
	public void checkBeforeBonus(String loanNo,double money) {
		
		CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
		
		//验证账户余额
        UserBillModel latestBill = this.userBillDao.selectByUserId(loan.getLoanUser());
        if(null == latestBill){
            throw new ApplicationException("账户余额不足，请充值");
        }else if(money>latestBill.getBalance()){
            throw new ApplicationException("账户余额不足，请充值");
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
	private String saveLoanBonus(String loanNo, int bonusNum,double bonusMoney) {
		CrowdfundBonusModel model = new CrowdfundBonusModel();
		model.setId(PKGenarator.getId());
		model.setBonusAmt(bonusMoney);
		model.setLoanNo(loanNo);
		model.setBonusAuditState(FbdCoreConstants.bonusAuditState.waitAudit); //审核状态为等待审核
		model.setBonusState(FbdCoreConstants.bonusState.add); //设置状态为新建
		model.setBonusTime(new Date());
		model.setBonusNum(bonusNum+1);
		this.crowdfundBonusDao.save(model);
		return model.getId();
	}

	/***
     * * Description:发放分红详细 
     * @param invest  投资信息
     * @param shareAmt 应得分红比例
     */
    private void saveInvestShareBonus(String loanNo,String investNo,String investor,double investAmt,double shareAmt,int bonusNum,String bonusLoanId){
        try{
        	
        	CrowdfundingSupportModel support = new CrowdfundingSupportModel();
        	support.setOrderId(investNo);  //设置投资编号
        	support.setLoanNo(loanNo);
        	support.setSupportAmt(investAmt);
        	support.setSupportUser(investor);
        	
            //插入分红发放明细
            this.addRewardAssgin(FbdCoreConstants.rewardAssignType.loan_share, support, shareAmt, 
            		investor, null, null,bonusNum,bonusLoanId);
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
            String getUser,String payUser,String invitorType,int bonusNum,String bonusLoanId){
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
        model.setBonusState(FbdCoreConstants.bonusState.wait_bonus);
        model.setLoanBonusId(bonusLoanId);
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
//        return (int) this.rewardAssignDao.selectLastNum(loanNo);
    	
    	return (int)this.crowdfundBonusDao.selectLastNum(loanNo);
    }

    /**
     * 
     * Description: 项目方审核投资申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateLoanAuditRefund(CrowdfundRefundAuditModel model) {
		
		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(model.getSupportNo());
		model.setId(PKGenarator.getId());
		model.setAuditTime(new Date());
		this.crowdfundRefundAuditDao.save(model);
		CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(supportModel.getLoanNo());
		
		//开始请求区块链转账(将项目方账户的前转让到项目中间账户)
		//查询项目方信息
	    UserModel loanUser = this.userDao.findByUserId(crowdfund.getLoanUser());
	    String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
		Map<String,String> params = this.getLoanAuditRefundParams(crowdfund,supportModel, loanUser);
		logger.info("项目方审核退款请求数据-params："+params);
		//保存操作数据
		String operationId = PKGenarator.getId();
        trusteeshipOperationService.saveOperationModel(operationId, supportModel.getOrderId(),
        		 supportModel.getOrderId(),LetvPayConstants.BusiType.refund_loan,requestUrl,"blockChain",MapUtil.mapToString(params));
		//项目方同意退款
        if(FbdCoreConstants.refundState.loanPassed.equals(model.getAuditState())){
        	if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
            	supportModel.setRefundState(FbdCoreConstants.refundState.auditing); //区块链转账成功
    			supportModel.setRefuseReason(model.getRefuseReason()); //退款拒绝原因
    			supportModel.setRefuseLoanAuditTime(new Date());
    			this.crowdfundingSupportDao.update(supportModel);
            	//冻结用户资金
            	this.userBillService.addLoanRefundFrzeeBill(supportModel, loanUser.getUserId());
            	CrowdfundingBackSetModel setModel = this.crowdfundingBackSetDao.getByLoanNoAndSetNo(supportModel.getLoanNo(),supportModel.getBackNo());
            	this.sendLoanAuditRefundMessage(FbdCoreConstants.refundState.loanPassed, supportModel.getOrderId(), crowdfund, 
            			setModel.getBackContent(), supportModel.getSupportUser());
            }else{
                String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        		logger.info("区块链-项目方审核退款请求数据："+resultStr);
                @SuppressWarnings("unchecked")
                Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
                String status = resultMap.get("status").toString();
                String message = resultMap.get("message").toString();
                if("Success".equals(status)){//转账事务成功
                	supportModel.setRefundState(FbdCoreConstants.refundState.submitProcess); //提交处理中
                	//添加一条事务通知数据
                	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
                	blockAsynTran.setId(PKGenarator.getId());
                	blockAsynTran.setParentId(supportModel.getOrderId());  //支持编号
                	blockAsynTran.setCreateTime(new Date());
                	blockAsynTran.setUpdateTime(new Date());
                	blockAsynTran.setType(BlockChainCore.Type.REFUNDLOANAUDIT);
                	blockAsynTran.setStatus(BlockChainCore.ResultStatus.SUCCESS);
                	this.blockAsynTranDao.save(blockAsynTran);
                }else{
                	throw new ApplicationException(message);
                }
            }
		}else if(FbdCoreConstants.refundState.loanRefuse.equals(model.getAuditState())){  //项目方拒绝退款
			supportModel.setRefundState(model.getAuditState()); //状态
			supportModel.setRefuseReason(model.getRefuseReason()); //退款拒绝原因
			supportModel.setRefuseLoanAuditTime(new Date());
			this.crowdfundingSupportDao.update(supportModel);
			
			CrowdfundingBackSetModel setModel = this.crowdfundingBackSetDao.getByLoanNoAndSetNo(supportModel.getLoanNo(),supportModel.getBackNo());

			this.sendLoanAuditRefundMessage(FbdCoreConstants.refundState.loanRefuse, supportModel.getOrderId(), 
					                   crowdfund,setModel.getBackContent(),supportModel.getSupportUser());
			 
		}
	}
	
	private Map<String, String> getLoanAuditRefundParams(CrowdfundingModel crowdfund,CrowdfundingSupportModel supportModel,UserModel loanUser) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",supportModel.getOrderId()); //转让编号传支持编号
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress",loanUser.getBlockChainAddress()); //转出账户地址为项目方账户
		params.put("sourceKey",loanUser.getBlockChainSecretKey());
		params.put("targetAddress",crowdfund.getBlockChainAddress());  //转入账户为项目中间账户
		String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.isClientEnvironment)){
			amount = "0.01";
		}else{
			amount = String.valueOf(Arith.round(supportModel.getSupportAmt()+supportModel.getTransFee()));
		}
		params.put("amount",amount);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingRefund/loanAuditRefundNotify.html");
		return params;
	}
	
	/**
	 * 项目方审核成功的后续操作
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	public void loanAuditRefundAfter(String tranId,String requestID,Timer timer,boolean notifyFlag){
		try{
			
			BlockAsynTranModel model = blockAsynTranDao.findByRequestId(requestID);
        	String orderId = model.getParentId();
        	String status = model.getQueryStatus();
        	CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(orderId);
        	
        	CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(supportModel.getLoanNo());
        	
        	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
	            supportModel.setRefundState(FbdCoreConstants.refundState.auditing); //区块链转账成功
    			supportModel.setRefuseLoanAuditTime(new Date());
    			this.crowdfundingSupportDao.update(supportModel);
            	//冻结项目方资金
            	this.userBillService.addLoanRefundFrzeeBill(supportModel, crowdfund.getUserId());
                //发送消息	
            	CrowdfundingBackSetModel setModel = this.crowdfundingBackSetDao.getByLoanNoAndSetNo(supportModel.getLoanNo(),supportModel.getBackNo());
            	this.sendLoanAuditRefundMessage(FbdCoreConstants.refundState.loanPassed, supportModel.getOrderId(), 
            			 crowdfund, setModel.getBackContent(), supportModel.getSupportUser());
            	if(notifyFlag){
            		timer.cancel();
            	}
        	}else if(BlockChainCore.ResultStatus.TRANSACTION_FAIL.equals(status)){
        		logger.info("===============退款转账事务失败================================");
        		if(notifyFlag){
            		timer.cancel();
            	}
        	}else{
        		logger.info("===============================================");
        	}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 平台审核退款后续操作 
	 * @param params
	 */
	@SuppressWarnings("unchecked")
	public void platformAuditRefundAfter(String tranId,String requestID,String auditStatus,Timer timer,boolean notifyFlag){
		try{
			 
        	BlockAsynTranModel model = blockAsynTranDao.findByRequestId(requestID);
        	String orderId = model.getParentId();
        	String status = model.getQueryStatus();
        	CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(orderId);
        	CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(supportModel.getLoanNo());
        	//查询项目方信息
        	UserModel loanUser = this.userDao.findByUserId(crowdfund.getLoanUser());
        	//查询投资人信息
        	UserModel supportUser = this.userDao.findByUserId(supportModel.getSupportUser());
        	
        	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
        		if("passed".equals(auditStatus)){  //审核通过事务成功操作
        			supportModel.setRefundState(FbdCoreConstants.refundState.refund_success);  //退款成功
        			supportModel.setState(CrowdfundCoreConstants.crowdFundOrderState.refunded);  //设置订单状态为已退款
		    		supportModel.setRefuseAuditTime(new Date());  //设置退款审核通过时间
		    		supportModel.setRefuseComplateTime(new Date());
		            crowdfundingSupportDao.updateByOrderNo(supportModel);
		            //添加项目方退款成功账单
		            this.userBillService.addLoanRefundSucceeBill(supportModel, loanUser.getUserId());
		            //添加投资人退款成功账单
		            this.userBillService.addSupportUserRefundSucceeBill(supportModel, supportUser.getUserId());
		            
		            if(notifyFlag){
		            	timer.cancel();
		            }
        		}else if("refuse".equals(auditStatus)){  //审核拒绝事务成功操作
        			supportModel.setRefundState(FbdCoreConstants.refundState.refuse); //退款拒绝
		    		supportModel.setRefuseAuditTime(new Date());  //设置退款审核通过时间
		    		supportModel.setRefuseComplateTime(new Date());
		    		crowdfundingSupportDao.updateByOrderNo(supportModel);
		    		//添加项目方解冻账单
		    		this.userBillService.addRefundAuditFailUnFrzeeBill(supportModel, loanUser.getUserId());
		    		if(notifyFlag){
			            timer.cancel();
			        }
        		}
        		//发送站内信
        	}else if(BlockChainCore.ResultStatus.TRANSACTION_FAIL.equals(status)){
        		//审核事务失败还是在审核状态
	        	supportModel.setRefundState(FbdCoreConstants.refundState.auditing);
	    		supportModel.setRefuseAuditTime(new Date());
	            crowdfundingSupportDao.updateByOrderNo(supportModel);
	            //失败后解冻金额
		        this.userBillService.addRefundAuditFailUnFrzeeBill(supportModel, crowdfund.getLoanUser());
        	} 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
     * 
     * Description: 发送项目方审核投资申请退款站内信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanAuditRefundMessage(String auditState,String supportNo,CrowdfundingModel crowdfund,String backContent,String supportUser){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
       params.put("loanName",crowdfund.getLoanName());
       params.put("supportNo",supportNo);
       params.put("backContent",backContent);
       if(FbdCoreConstants.refundState.loanPassed.equals(auditState)){
    	   params.put("state","审核通过");
       }else if(FbdCoreConstants.refundState.loanRefuse.equals(auditState)){
    	   params.put("state","审核拒绝");
       }
	   try{
	        logger.info("发送支付站内信开始");
	        String nodeCode = FbdCoreConstants.NODE_TYPE_LOANAUDITREFUND_APPLICATION_MSG;
	        SendMessageUtil.sendStationMessage(nodeCode, null, supportUser, params);
	        logger.info("发送支付站内信结束");
	   }catch(Exception e){
	        logger.error("发送支付站内信失败，"+e.getMessage());
	   }
    }
	
	

	/**
     * 
     * Description: 查询项目方审核投资申请退款记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public List<Map<String, Object>> getLoanAuditRefundList(
			CrowdfundRefundAuditModel model) {
		return this.crowdfundRefundAuditDao.getLoanAuditRefundList(model);
	}

	/**
     * 
     * Description: 查询回报设置backNo
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public CrowdfundingBackSetModel getBackSetByBackNo(String loanNo,String backNo) {
		return this.crowdfundingBackSetDao.getByLoanNoAndSetNo(loanNo, backNo);
	}

	/**
     * 
     * Description: 申请发货
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateApplicationDelivery(String supportNo) {
		CrowdfundingSupportModel model = this.crowdfundingSupportDao.getByOrderId(supportNo);
		model.setIsApplicationDelivery("1");
		this.crowdfundingSupportDao.update(model);
		
		//给发货人 发送站内信通知
		CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(model.getLoanNo());
		CrowdfundingBackSetModel setModel = this.crowdfundingBackSetDao.getByLoanNoAndSetNo(model.getLoanNo(),model.getBackNo());
		this.sendMessageByApplicationDelivery(crowdfund, setModel.getBackContent(), supportNo);
	}
    
	
	/**
     * 
     * Description: 申请发货站内信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendMessageByApplicationDelivery(CrowdfundingModel crowdfund,String backContent,String supportNo){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
       params.put("loanName",crowdfund.getLoanName());
       params.put("supportNo",supportNo);
       params.put("backContent",backContent);
       
        try{
            logger.info("发送支付站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_APPLICATION_DELIVERY_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, null, crowdfund.getLoanUser(), params);
            logger.info("发送支付站内信结束");
        }catch(Exception e){
            logger.error("发送支付站内信失败，"+e.getMessage());
        }
    }

    /**
     * 
     * Description: 查询抽奖编号
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public CrowdfundUserPrizeModel getUserPrize(String loanNo, String supportUser) {
		return this.crowdfundUserPrizeDao.getUserPrize(loanNo,supportUser);
	}

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
	public SearchResult<Map<String,Object>> getRewardPageList(RewardAssignModel model) {
		SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.rewardAssignDao.getList(model));
        result.setTotal(this.rewardAssignDao.getListCount(model));
        return result;
	}

	/**
     * 
     * Description: 项目方分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void saveBonus(String state, String loanBonusId) {
		
		if(state.equals(FbdCoreConstants.bonusAuditState.success)){
			
			CrowdfundBonusModel model = this.crowdfundBonusDao.selectByPrimaryKey(loanBonusId);
			//判断用户账户余额
			this.checkBeforeBonus(model.getLoanNo(),model.getBonusAmt());
			model.setBonusAuditState(state);
			//用户同意分红冻结用户金额
			CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(model.getLoanNo());
			//查询项目方信息
		    UserModel loanUser = this.userDao.findByUserId(loan.getLoanUser());
		    String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
		    //组装请求参数
		    String requestID = PKGenarator.getOrderId();
			Map<String,String> params = this.getLoanBonusParams(loan, model,requestID,loanUser);
			logger.info("项目方分红请求数据-params："+params);
			//保存操作数据
			String operationId = PKGenarator.getId();
	        trusteeshipOperationService.saveOperationModel(operationId, model.getId(),
	        		model.getId(),LetvPayConstants.BusiType.loan_bonus,requestUrl,"blockChain",MapUtil.mapToString(params));
			 
	        
			if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
				model.setBonusAuditState(FbdCoreConstants.bonusAuditState.auditing); //设置审核状态为审核中
				model.setBonusState(FbdCoreConstants.bonusAuditState.confirm);  //分红状态为项目方确认
				//冻结用户资金
				this.userBillService.addUserBonusFreeze(model,loan.getLoanUser());
	        }else{
            	//添加一条事务通知数据
            	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
            	blockAsynTran.setId(PKGenarator.getId());
            	blockAsynTran.setParentId(model.getId());  //分红ID
            	blockAsynTran.setRequestID(requestID);
            	blockAsynTran.setCreateTime(new Date());
            	blockAsynTran.setUpdateTime(new Date());
            	blockAsynTran.setType(BlockChainCore.Type.loan_bonus);
            	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
            	this.blockAsynTranDao.save(blockAsynTran);
	        	
	            String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
	    		logger.info("区块链-项目方确认分红请求数据："+resultStr);
	            @SuppressWarnings("unchecked")
	            Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
	            String status = resultMap.get("status").toString();
	            String message = resultMap.get("message").toString();
	            
	            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账提交成功
//	            	model.setBonusAuditState(FbdCoreConstants.bonusAuditState.submitProcess); //提交处理中
	            	model.setBonusState(FbdCoreConstants.bonusAuditState.submitProcess);//分红状态为提交处理中
	            	//更新请求处理状态
	            	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);
	            	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
	            	blockAsynTranModel.setUpdateTime(new Date());
	            	this.blockAsynTranDao.update(blockAsynTranModel);
	            }else{
	            	throw new ApplicationException(message);
	            }
	        }
			this.crowdfundBonusDao.update(model);
		}else{
			this.crowdfundBonusDao.deleteByLoanBonusId(loanBonusId);
		}
	}
	
	/**
	 * 项目方确认分红后的操作
	 * @param model
	 */
	public void loanBonusAfter(String id,String requestID,Timer timer,boolean notifyFlag){
		
		BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        String status = blockAsynTran.getQueryStatus();
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){

        	CrowdfundBonusModel model = this.crowdfundBonusDao.selectByPrimaryKey(id);
        	if(FbdCoreConstants.bonusAuditState.confirm.equals(model.getBonusState())){
        		logger.info("=============项目方分红处理完成=====");
        		return;
        	}else{
            	model.setBonusState(FbdCoreConstants.bonusAuditState.confirm);  
        		model.setBonusAuditState(FbdCoreConstants.bonusAuditState.auditing);
        		this.crowdfundBonusDao.update(model);
        		CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(model.getLoanNo());
        		//查询项目方分红冻结账单
        		this.userBillService.addUserBonusFreeze(model,loan.getLoanUser());
        	}
        	if(notifyFlag){
    			timer.cancel();
    		}
        }else{
        	
        }
	}
	
	
	private Map<String, String> getLoanBonusParams(CrowdfundingModel crowdfund,CrowdfundBonusModel crowdBonus,String requestID,UserModel loanUser) {
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",crowdBonus.getId()); //转账编号传分红编号
		params.put("requestID",requestID);
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		
		String amount = "0.01";
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = "0.01";
//			params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT); //转出账户地址为项目方账户
//			params.put("sourceKey",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_KEY);
//			params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_IN_ACCOUNT);  //转入账户为项目中间账户
		}else{
			amount = String.valueOf(Arith.round(crowdBonus.getBonusAmt()));
		}
		params.put("sourceAddress",loanUser.getBlockChainAddress()); //转出账户地址为项目方账户
		params.put("sourceKey",loanUser.getBlockChainSecretKey());
		params.put("targetAddress",crowdfund.getBlockChainAddress());  //转入账户为项目中间账户
		
		params.put("amount",amount);
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingBonus/crowdfundingBonusNotify.html");
		return params;
	}
	

	/**
     * 
     * Description: 获取投资所需要配置参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
	public Map<String,Object> getDeployMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		//意向金支付比例
		Double yxjPayScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode()==null?"0.4":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode());
		map.put("yxjPayScale", yxjPayScale);
		
		//意向金支付天数
		Integer yxjPayDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode()==null?"0.4":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode());
		map.put("yxjPayDay", yxjPayDay);
		
		//平台收取违约意向金比例
		Double yxjPlatformRatio = Double.valueOf(businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode()==null?"0.4":businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode());
		map.put("yxjPlatformRatio", yxjPlatformRatio);
		
		
		return map;
	}

	/**
     * 
     * Description: 查询投资信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
	public Map<String, Object> getSupportInfo(String orderId) {
		return this.crowdfundingSupportDao.getSupportInfo(orderId);
	}
	
    /**
     * 查询用户项目统计
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * Create Date: 2016-8-31 下午7:33:49
     */
    public  Map<String, Object>  selectUserProjectInfo(String userId){
    	return this.crowdfundingDao.selectUserProjectInfo(userId);
    }

	@Override
	public CrowdfundingModel getByloanNo(String loanNo) {
		// TODO Auto-generated method stub
		return crowdfundingDao.getByloanNo(loanNo);
	}

	@Override
	public long selectIntentionEndIsSucess(String orderId) {
		// TODO Auto-generated method stub
		return crowdfundingSupportDao.selectIntentionEndIsSucess(orderId);
	}

	@Override
	public long selectPayIntentionIsSucess(String orderId) {
		// TODO Auto-generated method stub
		return crowdfundingSupportDao.selectPayIntentionIsSucess(orderId);
	}

	@Override
	public long selectPayIsSuccess(String orderId) {
		// TODO Auto-generated method stub
		return crowdfundingSupportDao.selectPayIsSuccess(orderId);
	}

	@Override
	public long selectPayingParts(String loanNo) {
		// TODO Auto-generated method stub
		return crowdfundingSupportDao.selectPayingParts(loanNo);
	}
	
    public List<CrowdfundingModel> selectByAll(CrowdfundingModel model){
    	return this.crowdfundingDao.selectByAll(model);
    }
 
}
