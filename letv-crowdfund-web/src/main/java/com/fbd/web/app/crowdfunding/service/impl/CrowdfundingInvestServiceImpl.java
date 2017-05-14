/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingInvestServiceImpl.java 
 *
 * Created: [2015-5-20 下午3:01:42] by haolingfeng
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
 * ProjectName: crowdfund-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.crowdfunding.service.impl;

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

import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundAuditDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundLeadInvestorDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.dao.IInterviewRecordDao;
import com.fbd.core.app.crowdfunding.dao.ILeadFollowDao;
import com.fbd.core.app.crowdfunding.dao.IStockBackSetDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.app.crowdfunding.model.LeadFollowModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.message.dao.IStationMessageDao;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.MessageUtils;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.LockUtil;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description:股权众筹投资 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("crowdfundingInvestService")
public class CrowdfundingInvestServiceImpl implements ICrowdfundingInvestService{
	 private static final Logger logger = LoggerFactory
	            .getLogger(CrowdfundingInvestServiceImpl.class);
    @Resource
    private IInterviewRecordDao interviewRecordDao;
    @Resource
    private IStockBackSetDao stockBackSetDao;
    @Resource
    private ICrowdfundingBackSetDao crowdfundingBackSetDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IStationMessageDao stationMessageDao;
    @Resource
    private IUserDao userDao;
    
    @Resource
    private ILeadFollowDao leadFollowDao;
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    
    @Resource
    private ICrowdfundUserStuffDao crowdfundUserStuffDao;
    
    @Resource
    private ICrowdfundLeadInvestorDao crowdfundLeadInvestorDao;
    
    @Resource
    private IUserBillService userBillService ;

    @Resource
    private ISystemBillService sysBillService;
    @Resource
    private ICrowdfundAuditDao crowdfundAuditDao;
    @Resource
    private StdScheduler scheduler;

    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao ;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    /**
     * 
     * 
     * Description:保存约谈记录 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午3:04:52
     */
    public void saveInterview(InterviewRecordModel model){
        CrowdfundingModel crowdfund = crowdfundingDao.getByloanNo(model.getLoanNo());
        if(!crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.funding.getValue()) &&
                !crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.preheat.getValue())){
            throw new ApplicationException("对不起，该项目已融资结束");
        }
        
        
        //保存约谈记录
        model.setId(PKGenarator.getId());
        model.setReceiveUser(crowdfund.getLoanUser());
        model.setInterviewTime(new Date());
        model.setState(CrowdfundCoreConstants.ProcessState.processing);
        this.interviewRecordDao.save(model);
        
        
        //发站内信
        if(model.getIsStationMsg()!=null && model.getIsStationMsg().equals(FbdCoreConstants.IS)){
        	
        	
        	//查询约谈人信息
        	UserModel user = this.userDao.findByUserId(model.getApplyUser());
        	
        	 
    	    Map<String, String> params = new HashMap<String,String>();
            params.put("interviewTime",DateUtil.dateTime2Str(model.getInterviewTime(), DateUtil.DEFAULT_DATE_FORMAT));
            params.put("applyUser",user.getUserId());
            params.put("applyUserRealName",user.getRealName());
            params.put("applyUserNickName",user.getNickName());
            params.put("applyUserMobile",user.getMobile());
            params.put("loanNo",crowdfund.getLoanNo());
            params.put("loanName",crowdfund.getLoanName());
            params.put("content",model.getInterviewContent());
            try{
		        logger.info("发送约谈站内信开始");
		        String nodeCode = FbdCoreConstants.STATION_MSG_CHILD_TYPE_LOAN_MSG;
		        SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, crowdfund.getLoanUser(), params);
		        logger.info("发送约谈站内信结束");
		   }catch(Exception e){
		        logger.error("发送约谈站内信失败，"+e.getMessage());
		   }
//            stationMessageDao.saveStationMessage(crowdfund.getLoanUser(), FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, 
//                    FbdCoreConstants.STATION_MSG_CHILD_TYPE_LOAN_MSG,model.getInterviewContent());
        }
        
        //发邮件
        if(model.getIsEmail()!=null && 
                model.getIsEmail().equals(FbdCoreConstants.IS)){
            UserModel user = userDao.findByUserId(crowdfund.getLoanUser());
            if(user.getEmail() == null){
                throw new ApplicationException("对不起，您没有设置邮件地址");
            }
            String subject = "关于项目【"+crowdfund.getLoanName()+"】的约谈";
            boolean send = MessageUtils.sendMail(user.getEmail(), subject, model.getInterviewContent());
            if(!send){
                throw new ApplicationException("约谈邮件未发送成功");
            }
        }
    }
    
    /**
     * 
     * Description:(股权) 众筹投资
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午7:36:30
     */
    public void saveStockInvest(CrowdfundingSupportModel model){
        this.checkBeforeInvest(model.getLoanNo(),model.getBuyNum());
        StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(model.getLoanNo());
        double minInvestAmt = backSet.getMiniInvestAmt();
        double investAmt = Arith.round(model.getBuyNum()*minInvestAmt);
        model.setId(PKGenarator.getId());
        model.setSupportAmt(investAmt);
        model.setState(CrowdfundCoreConstants.crowdFundPayState.noPay);
        model.setSupportTime(new Date());
        crowdfundingSupportDao.save(model);
        
        //保存领投跟投人关系
        if(model.getInvestType().equals(CrowdfundCoreConstants.StockSupportType.followInvest)){//跟投
            String[] leadInvestors = model.getLeadInvestor().split(",");
            for(String leadInvestor:leadInvestors){
                LeadFollowModel leadFollow = new LeadFollowModel();
                leadFollow.setId(PKGenarator.getId());
                leadFollow.setInvestor(model.getSupportUser());
                leadFollow.setLoanNo(model.getLoanNo());
                leadFollow.setOrderId(model.getOrderId());
                leadFollow.setSuperInvestor(leadInvestor);
                this.leadFollowDao.save(leadFollow);
            }
        }
    }
    
    
    /**
     * 
     * Description:(股权) 众筹投资
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-20 下午7:36:30
     */
    public void saveOrderInvest(CrowdfundingSupportModel model){
        CrowdfundingModel crowdfund = crowdfundingDao.getByloanNo(model.getLoanNo());
        if(!crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.preheat.getValue())){
            throw new ApplicationException("对不起，该项目已结束预约认购");
        }
        
        //查询认购成功的份数
        long payedParts = crowdfundingSupportDao.getPayedParts(model.getLoanNo());
        StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(model.getLoanNo());
        
        int buyNum = (int)(model.getSupportAmt()/backSet.getMiniInvestAmt());
        if(backSet.getFinanceNum() < payedParts+buyNum){
            throw new ApplicationException("对不起，购买份数超过剩余份数");
        }
        
        model.setId(PKGenarator.getId());
        model.setBuyNum(buyNum);
        model.setState(CrowdfundCoreConstants.crowdFundPayState.payed);
        model.setSupportTime(new Date());
        crowdfundingSupportDao.save(model);
    }
    
    
    /**
     * 
     * Description: 约谈，投标前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-21 上午10:09:10
     */
    private void checkBeforeInvest(String loanNo,int buyNum){
        CrowdfundingModel crowdfund = crowdfundingDao.getByloanNo(loanNo);
        if(!crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.funding.getValue())){
            throw new ApplicationException("对不起，该项目已融资结束");
        }
        
        //查询认购成功的份数
        long payedParts = crowdfundingSupportDao.getPayedParts(loanNo);
        StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(loanNo);
        if(backSet.getFinanceNum() < payedParts+buyNum){
            throw new ApplicationException("对不起，购买份数超过剩余份数");
        }
    }
    
    /**
     * 
     * Description: 预购前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-21 上午10:09:10
     */
    private void checkBeforePreSupport(String loanNo){
        
        
        CrowdfundingModel crowdfund = crowdfundingDao.getByloanNo(loanNo);
        if(!crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.preheat.getValue())){
            throw new ApplicationException("对不起，该项目无法预约");
        }
    }
    
    
    /**
     * 
     * Description:查询领投人/跟投人
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getInvestorList(CrowdfundUserStuffModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundUserStuffDao.getList(model));
        result.setTotal(this.crowdfundUserStuffDao.getCount(model));
        return result;
    } 
    
    /**
     * 
     * Description:查询领投人
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public List<Map<String,Object>> getLoanLeader(String loanNo){
        return this.crowdfundLeadInvestorDao.getLoanLearder(loanNo);
    } 
    
    /**
     * 
     * Description:认购定金前验证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午2:13:50
     */
    public void checkBeforeOrderInvest(CrowdfundingSupportModel model){
        String loanNo = model.getLoanNo();
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        String state = loan.getLoanState();
        if(!state.equals(CrowdfundCoreConstants.crowdFundingState.preheat.getValue())){
            throw new ApplicationException("此众筹项目已经预热结束");
        }
        CrowdfundUserStuffModel queryModel=new CrowdfundUserStuffModel();
        queryModel.setUserId(model.getSupportUser());
        queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
        List<CrowdfundUserStuffModel> userStuffList = this.crowdfundUserStuffDao.getByUserId(queryModel);
        if(userStuffList==null||userStuffList.size()==0){
            throw new ApplicationException("请先进行投资人认证");
        }
            
//        CrowdfundingSupportModel qModel = new CrowdfundingSupportModel();
//        qModel.setLoanNo(loanNo);
//        qModel.setSupportUser(model.getSupportUser());
//        List<Map<String,Object>> orderList = this.crowdfundingSupportDao.getOrderSupportList(qModel);
//        if(orderList.size()>0){
//            throw new ApplicationException("您已经跟投过此项目");
//        }
        
        
        
        //查询认购成功的份数
        long payedParts = crowdfundingSupportDao.getPayedParts(loanNo);
        StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(loanNo);
        int buyNum = model.getBuyNum();
        if(backSet.getFinanceNum() < payedParts+buyNum){
            throw new ApplicationException("对不起，购买份数超过剩余份数");
        }
        
        double investAmt = Arith.round(model.getBuyNum()*backSet.getMiniInvestAmt());
        double fundAmt = loan.getOverFundAmt();
        if( fundAmt>0){
            
        }else{
            fundAmt =loan.getFundAmt();
        }
        double approveAmt = loan.getApproveAmt();
        if(Arith.add(investAmt, approveAmt) > fundAmt){
            throw new ApplicationException("目前支持的金额超过目标金额"+fundAmt);
        }
        
    }
    
    
    /**
     * 
     * Description:实物众筹支持前验证 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-30 下午2:13:50
     */
    public void checkBeforeEntitySupport(CrowdfundingSupportModel model){
        String loanNo = model.getLoanNo();
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        String state = loan.getLoanState();
        if(!state.equals(CrowdfundCoreConstants.crowdFundingState.funding.getValue())){
            throw new ApplicationException("此众筹项目已经结束");
        }
        //众筹类型判断
        if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.HOUSE)){
            CrowdfundUserStuffModel queryModel=new CrowdfundUserStuffModel();
            queryModel.setUserId(model.getSupportUser());
            queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
            List<CrowdfundUserStuffModel> userStuff = this.crowdfundUserStuffDao.getByUserId(queryModel);
            if(userStuff==null||userStuff.size()==0){
            	throw new ApplicationException("请先进行投资人认证");
            }
        }else if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
	            CrowdfundUserStuffModel queryModel=new CrowdfundUserStuffModel();
	            queryModel.setUserId(model.getSupportUser());
	            queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
                List<CrowdfundUserStuffModel> userStuff = this.crowdfundUserStuffDao.getByUserId(queryModel);
                
                if(userStuff==null||userStuff.size()==0){
                    throw new ApplicationException("请先进行投资人认证");
                }
//                long isExist = this.crowdfundLeadInvestorDao.getByLeaderAndLoanNo(loanNo, model.getSupportUser());
//                if(isExist > 0){
//                    throw new ApplicationException("您已领投，所以不能跟投");
//                }
                if(StringUtils.isEmpty(model.getLeadInvestor())){
                    throw new ApplicationException("请选择至少一位领投人");
                }else{
                    String[] leadInvestors = model.getLeadInvestor().split(",");
                    queryModel=new CrowdfundUserStuffModel();
                    queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
                    queryModel.setAuthTypeState("lead");
                    for(String leadInvestor:leadInvestors){
/*                        if(crowdfundUserStuffDao.getByUserId(leadInvestor).size() == 0){
                            throw new ApplicationException("领投人不存在");
                        }
                        String userLevel = crowdfundUserStuffDao.getByUserId(leadInvestor).get(0).getUserLevel();
                        if(!userLevel.equals(CrowdfundCoreConstants.UserLevel.lead)){
                            throw new ApplicationException("该投资人没有通过领投人认证");
                        }*/
                        if(StringUtils.isNotEmpty(leadInvestor)){
                        	queryModel.setUserId(leadInvestor);
                        	List<CrowdfundUserStuffModel> cList=crowdfundUserStuffDao.getByUserId(queryModel);
                        	if(cList==null || cList.size()==0){
                        		throw new ApplicationException("该投资人"+leadInvestor+"没有通过领投人认证");
                        	}
                        }
                    }
                    
                }
           
        }
        //实物回报支持验证
        if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PUBLIC_SERVICE)|| 
                loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.ENTITY)){
            CrowdfundingBackSetModel backSet = crowdfundingBackSetDao.getByLoanNoAndSetNo(loanNo, model.getBackNo());
            CrowdfundingSupportModel q = new CrowdfundingSupportModel();
            q.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
            q.setLoanNo(loanNo);
            q.setBackNo(model.getBackNo());
            long supportNum = crowdfundingSupportDao.getCount(q);
            if(backSet.getNumberLimits() > 0){
                if(supportNum+1 > backSet.getNumberLimits() || 
                        backSet.getState().equals(CrowdfundCoreConstants.crowdFundBackState.FULL)){
                    throw new ApplicationException("谢谢支持，此回报支持已经满额");
                }
            }
          
            
            //实物允许超募
//            double fundAmt = loan.getFundAmt();
//            double approveAmt = loan.getApproveAmt();
//            if(Arith.add(model.getSupportAmt(), approveAmt) > fundAmt){
//                throw new ApplicationException("目前支持的金额超过目标金额"+fundAmt);
//            }
        }else{
            //查询认购成功的份数
            long payedParts = crowdfundingSupportDao.getPayedParts(loanNo);
            StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(loanNo);
            //剩余份数为0
            if(backSet.getFinanceNum()-payedParts == 0){
                throw new ApplicationException("对不起，该项目已全部认购");
            }
            double minInvestAmt = backSet.getMiniInvestAmt();
            double investAmt = model.getBuyNum()*minInvestAmt;
            double fundAmt = loan.getOverFundAmt();
            if( fundAmt>0){
                
            }else{
                fundAmt =loan.getFundAmt();
            }
            double approveAmt = loan.getApproveAmt();
            if(Arith.add(investAmt, approveAmt) > fundAmt){
                throw new ApplicationException("目前支持的金额超过目标金额"+fundAmt);
            }
        }
    }
    
    
    /**
     * 
     * Description: 查询众筹用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午2:50:05
     */
    public Map<String,Object> getCrowdfundUserInfo(String userId){
        return this.crowdfundUserStuffDao.getCrowdfundUserInfo(userId);
    }
    
    
    
    /**
     * 
     * Description:查询众筹的约谈记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getInterviewList(InterviewRecordModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.interviewRecordDao.getInterviewList(model));
        result.setTotal(this.interviewRecordDao.getCount(model));
        return result;
    }
    
     
    
    
    /**
     * 
     * Description:查询领投记录
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getLeadSpportList(CrowdfundingSupportModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingSupportDao.getLeadSupportList(model));
        return result;
    }

	public Map<String,Object> getUserCenterInfo(String userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		//账户余额
		double balance = this.userBillService.getLatestBill(userId).getBalance();
		map.put("balance",balance);
		
		
		return map;
	}
	/**
     * Description: 统计个人中心资产
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 上午11:17:09
     */
	public Map<String, Object> getCountInvestAll(String userId) {
		return this.crowdfundingSupportDao.getCountInvestAll(userId);
	}

	/**
     * 
     * Description:查询用户认证信息
     *
     * @param 
     * @return String 1为认证 0为未认证
     * @throws 
     * @Author wuwenbin<br/>
     */
	public String getUserInfoByAuth(CrowdfundUserStuffModel model) {
		List<Map<String,Object>> userStuff = this.crowdfundUserStuffDao.getAuthList(model);
        if(userStuff.size()>0){
        	return "1";
        }else{
        	return "0";
        }
		
	}
	/**
	 * 转账异步回调处理
	 * @param loanNo
	 */
    public void dealWithSystemTransaction(String loanNo){
 
		 //查询项目信息
		 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
		 if(!crowd.getPlatformTransferState().equals("payed")){
			 //查询未放款的总金额  (包括运费)
			 double noLendAmt = 0.0;// this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(loanNo);
			 Double chargeScale = crowd.getChargeFee();
			 //查询项目手续费
			 double chargeFee = Arith.mul(noLendAmt, chargeScale);
	    	 //平台收费账单
	         sysBillService.addBill(CrowdfundCoreConstants.systemTradeType.borrowFee.getValue(), 
	        		crowd.getChargeFee(), 
	                FbdCoreConstants.tradeDirection.in.getValue(), 
	                null, "["+crowd.getLoanName()+"]放款时收取发起人的服务费和保证金", loanNo);
	        //更新状态
	        crowd.setPlatformTransferState("payed");
	        this.crowdfundingDao.updateBySelective(crowd);
		 }
    }
    public CrowdfundingModel getByloanNo(String loanNo){
    	//查询项目信息
		 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
		 return crowd;
    }
	/**
	 * 发起人异
	 * 步回调处理
	 * @param loanNo
	 */
    public void dealWithOrganiserTransaction(String loanNo){
    	try{
	    	//查询项目信息
			 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
			 //查询未放款的总金额  (包括运费)
			 double noLendAmt = 0.0;
			 //this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(loanNo);	 
			 Double chargeScale = crowd.getChargeFee();
			 //查询项目手续费
			 double chargeFee = Arith.mul(noLendAmt, chargeScale);
			 //项目方应收款项
			 double projectFee = Arith.round(noLendAmt - chargeFee);
			 
			List<Map<String,Object>>list =crowdfundingSupportDao.selectUserSupportByLoanNo(loanNo);
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				double transFee =0;
				if(map.get("transFee")!=null)
					transFee =(Double)map.get("transFee");
				
				double supportAmt =0;
				if(map.get("supportAmt")!=null)
					supportAmt =(Double)map.get("supportAmt");
					//给用户账户解冻以及扣款
					 userBillService.addBill((String)map.get("supportUser"), 
				                CrowdfundCoreConstants.userTradeType.invest.getValue(), 
				                Arith.add(supportAmt, transFee), 
				                FbdCoreConstants.tradeDirection.unfreeze.getValue(), 
				                loanNo,
				                crowd.getLoanName(),
				                null, 
				                loanNo);
					 
					 userBillService.addBill((String)map.get("supportUser"), 
				                CrowdfundCoreConstants.userTradeType.invest.getValue(), 
				                Arith.add(supportAmt, transFee), 
				                FbdCoreConstants.tradeDirection.out.getValue(), 
				                loanNo,
				                crowd.getLoanName(),
				                null, 
				                loanNo);
			}
	    	//给借款人添加放款记账
	        // 发起人进账账单
	        userBillService.addBill(crowd.getLoanUser(), 
	                CrowdfundCoreConstants.userTradeType.investCash.getValue(), 
	                projectFee, 
	                FbdCoreConstants.tradeDirection.in.getValue(), 
	                loanNo,
	                crowd.getLoanName(),
	                null, 
	                loanNo);
	        //更新项目状态
	        //更新项目方转账成功
	        crowd.setLoanTransferState("payed"); 
	        this.crowdfundingDao.updateBySelective(crowd);

    	}catch(Exception e){
    		e.printStackTrace();
    	}
 
    }
    /**
     * 
     * Description:放款后更新投资 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-11 上午11:26:33
     */
    public void updateSupportAfterLend(CrowdfundingSupportModel support,CrowdfundingModel loan){
        support.setState(CrowdfundCoreConstants.crowdFundOrderState.lended);
        crowdfundingSupportDao.update(support);
        //添加投资放款账单
//        this.userBillService.addInvestorBill(support, loan.getLoanName(), "项目【"+loan.getLoanName()+"】,项目编号【"+loan.getLoanNo()+"】", support.getSupportUser());
        //给支持者发送投标放款信息
        this.sendInvestCashMessage(support, loan);
    }
    /**
     * 
     * Description:放款操作 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-1 上午11:46:25
     */
    public void updateAfterLoan(CrowdfundingModel loan,String operator){
        String loanNo = loan.getLoanNo();
        loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.lended.getValue());
        loan.setCashTime(new Date());
        loan.setCashUser(operator);
        crowdfundingDao.update(loan);
        this.crowdfundAuditDao.addLoanAudit(operator,loanNo, 
                CrowdfundCoreConstants.loanAuditAction.cash.getValue(),
                null, CrowdfundCoreConstants.crowdFundingState.lended.getValue());
        //给发起人发送放款信息
        this.sendLoanCashMessage(loan);
    }
    /**
     * 
     * Description: 发送项目放款消息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanCashMessage(CrowdfundingModel model){
       double chargeFee = Arith.round(model.getApproveAmt()*model.getChargeFee());
       Map<String, String> params = new HashMap<String,String>();
       params.put("money",Arith.format(model.getApproveAmt()));
       params.put("loanName",model.getLoanName());
       params.put("fee",String.valueOf(chargeFee));
       params.put("time",DateUtil.dateTime2Str(model.getCashTime(), null));
       try{
           logger.info("发送项目放款手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_CASH_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
           logger.info("发送项目放款手机短信结束");
       }catch(Exception e){
           logger.error("发送项目放款手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目放款站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_CASH_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
            logger.info("发送项目放款站内信结束");
        }catch(Exception e){
            logger.error("发送项目放款站内信失败，"+e.getMessage());
        }
    }
    
    /**
     * 
     * Description: 发送众筹放款信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendInvestCashMessage(CrowdfundingSupportModel model,CrowdfundingModel loan){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(model.getSupportTime(), null));
       params.put("money",Arith.format(model.getSupportAmt()));
       params.put("loanName",loan.getLoanName());
       try{
           logger.info("发送投标放款手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_SUPPORT_CASH_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getSupportUser(), params);
           logger.info("发送投标放款手机短信结束");
       }catch(Exception e){
           logger.error("发送投标放款手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送投标放款站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_SUPPORT_CASH_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getSupportUser(), params);
            logger.info("发送投标放款站内信结束");
        }catch(Exception e){
            logger.error("发送投标放款站内信失败，"+e.getMessage());
        }
    }
    
    /**
     * 
     * Description: 查询未放款的列表
     *
     * @param 
     * @return List<CrowdfundingSupportModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-11 上午11:22:40
     */
    public List<CrowdfundingSupportModel> getNoLendSupportList(String loanNo){
        List<CrowdfundingSupportModel> supportList = this.crowdfundingSupportDao.getByLoanNo(loanNo);
        return supportList;
    }
    /**
     * 
     * Description: 判断支持单是否被锁定
     * 
     * @param
     * @return boolean
     * @throws
     * @Author wuwenbin
     */
    public synchronized boolean isSupportLock(String orderId) {
        boolean isLock = true;
        Object lockValue = LockUtil.getInstance().get(
                CrowdfundCoreConstants.LOCK_INVEST + orderId);
        if (lockValue == null) {
            LockUtil.getInstance().put(
                    CrowdfundCoreConstants.LOCK_INVEST + orderId, orderId);
            isLock = false;
        }
        return isLock;
    }
    /**
     * 流标给用户退款成功
     * @param requestId 
     */
    public void sendFlowSuccess(String requestId,Timer timer,boolean notifyFlag){
    	BlockAsynTranModel blockAsynTran =blockAsynTranDao.findByRequestId(requestId);
    	CrowdfundingSupportModel support = crowdfundingSupportDao.getByOrderId(blockAsynTran.getParentId());
    	CrowdfundingModel loan = crowdfundingDao.getByloanNo(support.getLoanNo());
    	String query_status = blockAsynTran.getQueryStatus();
    	logger.info("==========>流标异步通知处理数据-查询区块链处理状态-query_status:"+query_status);
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(query_status)){
        	  //更新投资为已取消
        	logger.info("------------>开始更新投资[编号:"+blockAsynTran.getParentId()+"]状态为已取消-------->");
	        support.setState(CrowdfundCoreConstants.crowdFundOrderState.cancel);
	        crowdfundingSupportDao.update(support);
	        //  记账 -支持人账单
	        logger.info("------------->记账添加投资人流标解冻账单-------------------------");
	        userBillService.addBill(support.getSupportUser(), 
	               CrowdfundCoreConstants.userTradeType.investFlow.getValue(), 
	               support.getSupportAmt()+support.getTransFee(), 
	               FbdCoreConstants.tradeDirection.unfreeze.getValue(), 
	               support.getLoanNo(),
	               loan.getLoanName() +"流标",
	               null, 
	               support.getOrderId());
	        //给支持者发送投标流标信息
	        logger.info("------------->给支持者发送投标流标信息-------------------------");
	        this.sendLoanFlowMessage(loan,support.getSupportUser());
	        logger.info("================查询项目流标处理数据=====================");
	        List<CrowdfundingSupportModel> supportList = this.getNoLendSupportList(support.getLoanNo());
	        if(supportList.size()==0){
	        	logger.info("===================更新项目状态数据=============");
	        	updateAfterFlow( loan,loan.getLoanUser());
	        }
	        if(notifyFlag){
	        	timer.cancel();
	        	System.gc();
	        }
        }
    }
    /**
     * 
     * Description:流标操作 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-1 上午11:46:25
     */
    public void updateAfterFlow(CrowdfundingModel loan,String operator){
    	logger.info("======================updateAfterFlow====="+loan);
        //变更项目
        loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.flow.getValue());
        crowdfundingDao.update(loan);
        crowdfundAuditDao.addLoanAudit(loan.getLoanUser(), loan.getLoanNo(),
                CrowdfundCoreConstants.loanAuditAction.add.getValue(), null,
                CrowdfundCoreConstants.crowdFundingState.add.getValue());
        //发送项目流标短信和站内信
        this.sendLoanFlowMessage(loan, loan.getLoanUser());
    }
    /**
     * 
     * Description: 发送众筹放款信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLoanFlowMessage(CrowdfundingModel loan,String userId){
       Map<String, String> params = new HashMap<String,String>();
       params.put("loanName",loan.getLoanName());
       params.put("loanNo",loan.getLoanNo());
       try{
           logger.info("发送项目投资流标手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_FLOW_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode,userId, params);
           logger.info("发送项目投资流标手机短信结束");
       }catch(Exception e){
           logger.error("发送项目投资流标手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送项目投资流标站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_FLOW_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG,userId, params);
            logger.info("发送项目投资流标站内信结束");
        }catch(Exception e){
            logger.error("发送项目投资流标站内信失败，"+e.getMessage());
        }
    }
}
