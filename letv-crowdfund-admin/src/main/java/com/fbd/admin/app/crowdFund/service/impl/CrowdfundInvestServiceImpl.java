/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundInvestServiceImpl.java 
 *
 * Created: [2015-5-25 下午7:52:01] by haolingfeng
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
 * ProjectName: crowdfund-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.crowdFund.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.fbd.admin.app.contract.service.IContractTemplateService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundInvestService;
import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.core.app.address.model.PostAddressModel;
import com.fbd.core.app.address.service.IPostAddressService;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.util.BlockChainStringUtil;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundLeadInvestorDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.dao.IInterviewRecordDao;
import com.fbd.core.app.crowdfunding.dao.IStockBackSetDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundLeadInvestorModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserUploadFileDao;
import com.fbd.core.app.user.model.UserModel;
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

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System有限公司.
 * 
 * Description: 支持
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("crowdfundInvestService")
public class CrowdfundInvestServiceImpl implements ICrowdfundInvestService{
    
    private static final Logger logger = LoggerFactory
            .getLogger(CrowdfundInvestServiceImpl.class);
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    
    @Resource
    private IStockBackSetDao stockBackSetDao;
    
    @Resource
    private ICrowdfundingBackSetDao crowdfundingBackSetDao;
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    
    @Resource
    private IUserBillService userBillService;
    
    @Resource
    private IInterviewRecordDao interviewRecordDao;
    @Resource
    private ICrowdfundUserStuffDao crowdfundUserStuffDao;
    
    @Resource
    private IUserUploadFileDao userUploadFileDao;
    
    @Resource
    private ICrowdfundingService crowdfundingService;
    
    @Resource
    private ISystemBillService sysBillService;
    
    @Resource
    private ICrowdfundingProgressDao crowdfundingProgressDao;
    
    @Resource
    private ICrowdfundLeadInvestorDao crowdfundLeadInvestorDao;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    
//    @Resource
//    private StdScheduler scheduler;
    @Resource
    private IUserDao userDao;
    @Resource
    private IPostAddressService postAddressService;
    
    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao ;
    @Resource
    private IContractTemplateService contractTemplateService;
    
    @Resource
   	private IBusinessConfigDao businessConfigDao;
    
    /**
     * 
     * Description: 后台支持
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-17 下午3:10:54
     */
    public void saveSupportByBack(CrowdfundingSupportModel invest,String loanType){
        //投资前验证
        this.checkBeforeInvest(invest, loanType);
        
        invest.setId(PKGenarator.getId());
        // 获得订单号
        invest.setOrderId(PKGenarator.getInvestId());
        invest.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
        invest.setSupportTime(new Date());
        invest.setCompleteTime(new Date());
        invest.setSupportWay(CrowdfundCoreConstants.supportWay.bySystem);
        
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.HOUSE)|| 
                loanType.equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
            StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(invest.getLoanNo());
            double minInvestAmt = backSet.getMiniInvestAmt();
            double investAmt = invest.getBuyNum()*minInvestAmt;
            invest.setSupportAmt(Arith.round(investAmt));
            invest.setPartMoney(minInvestAmt);
            invest.setTransferAmt(0.0);
        }else{
            invest.setBuyNum(1);
        }
        //保存投资记录
        this.crowdfundingSupportDao.save(invest);
        
        //插入账单
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
        
        //充值账单
        userBillService.addBill(invest.getSupportUser(), CrowdfundCoreConstants.userTradeType.recharge.getValue(),
                invest.getSupportAmt(), FbdCoreConstants.tradeDirection.in.getValue(), 
                invest.getLoanNo(), "线下充值,项目编号【"+invest.getLoanNo()+"】",null, invest.getOrderId());
        //投资账单
        userBillService.addBill(invest.getSupportUser(), CrowdfundCoreConstants.userTradeType.invest.getValue(),
                invest.getSupportAmt(), FbdCoreConstants.tradeDirection.out.getValue(), 
                invest.getLoanNo(), "平台代投资,项目编号【"+invest.getLoanNo()+"】",loan.getLoanUser(), invest.getOrderId());
        
        
        //更新众筹项目
        this.updateLoanAfterInvest(invest,loanType);
        
        
    }
    
    private void updateLoanAfterInvest(CrowdfundingSupportModel invest,String loanType){
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.PUBLIC_SERVICE)|| 
                loanType.equals(CrowdfundCoreConstants.CrowdfundType.ENTITY)){
            CrowdfundingBackSetModel backSet = crowdfundingBackSetDao.
                    getByLoanNoAndSetNo(invest.getLoanNo(), invest.getBackNo());
            if(backSet.getNumberLimits() > 0){//有名额限制时，更新回报设置
                CrowdfundingSupportModel q = new CrowdfundingSupportModel();
                q.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
                q.setLoanNo(invest.getLoanNo());
                q.setBackNo(invest.getBackNo());
                long supportNum = crowdfundingSupportDao.getCount(q);
                if(supportNum == backSet.getNumberLimits()){
                   //更新回报设置的状态
                    backSet.setState(CrowdfundCoreConstants.crowdFundBackState.FULL);
                    crowdfundingBackSetDao.update(backSet);
                }
            }
        }
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
        //更新众筹项目信息
        loan.setApproveAmt(Arith.add(loan.getApproveAmt(),invest.getSupportAmt()));
        
        //如果项目已融资金额等于目标融资金额上限时，结束众筹，针对股权与房产
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.STOCK)|| 
                loanType.equals(CrowdfundCoreConstants.CrowdfundType.HOUSE)){
            long payedParts = crowdfundingSupportDao.getPayedParts(invest.getLoanNo());
            StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(invest.getLoanNo());
            if(backSet.getFinanceNum() == payedParts){
                loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.funded.getValue());
            }
        }
        crowdfundingDao.update(loan);
    }
    
    private void checkBeforeInvest(CrowdfundingSupportModel invest,String loanType){
        if(StringUtils.isEmpty(loanType)){
            throw new ApplicationException("对不起，必须传入项目类型");
        }
        CrowdfundingModel crowdfund = crowdfundingDao.getByloanNo(invest.getLoanNo());
        if(!crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.funding.getValue())&&
        		!crowdfund.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.preheat.getValue())){
            throw new ApplicationException("对不起，该项目状态不是筹款中");
        }
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.HOUSE)|| 
                loanType.equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
            //查询认购成功的份数
            long payedParts = crowdfundingSupportDao.getPayedParts(invest.getLoanNo());
            StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(invest.getLoanNo());
            //剩余份数为0
            if(backSet.getFinanceNum()<payedParts+invest.getBuyNum()){
                throw new ApplicationException("对不起，购买份数超过总份数:"+backSet.getFinanceNum());
            }
        }else{
        	//判断收获地址
        	if(StringUtil.isEmpty(invest.getPostAddressNo())){
        		 throw new ApplicationException("对不起，该用户没有选择收货地址");
        	}
        	PostAddressModel address = postAddressService.selectPostAddressByNo(invest.getPostAddressNo());
        	if(address == null){
        		 throw new ApplicationException("对不起，用户的收货地址不存在");
        	}
            CrowdfundingBackSetModel backSet = crowdfundingBackSetDao.getByLoanNoAndSetNo(invest.getLoanNo(), invest.getBackNo());
            if(backSet.getState().equals(CrowdfundCoreConstants.crowdFundBackState.FULL)){
                throw new ApplicationException("对不起，此回报已经满额");
            }
        }
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
        result.setRows(this.interviewRecordDao.getList(model));
        result.setTotal(this.interviewRecordDao.getCount(model));
        return result;
    }
    
    
    /**
     * 
     * Description:查询众筹的认购列表
     *
     * @param 
     * @return SearchResult<CrowdfundingBackSetModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午5:11:45
     */
    public SearchResult<Map<String,Object>> getOrderSupportList(CrowdfundingSupportModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.crowdfundingSupportDao.getOrderSupportList(model));
        result.setTotal(this.crowdfundingSupportDao.getOrderSupportCount(model));
        return result;
    }
    
    public List<CrowdfundUserStuffModel> getByUserId(CrowdfundUserStuffModel model){
        return this.crowdfundUserStuffDao.getByUserId(model);
    }
    
    
    public List<Map<String,Object>> getUserUploadFiles(String userId){
        return this.userUploadFileDao.getFilesByUserId(userId);
    }
    
    /**
     * 
     * Description:申请认证列表 
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午6:40:41
     */
    public SearchResult<Map<String,Object>> getApplyAuthList(CrowdfundUserStuffModel stuff){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        try {
        	result.setRows(this.crowdfundUserStuffDao.getAuthList(stuff));
            result.setTotal(this.crowdfundUserStuffDao.getAuthCount(stuff));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    
    /**
     * 
     * Description:审核投资人认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午7:25:27
     */
    public void updateInvestorAuthState(CrowdfundUserStuffModel stuff){
        if(stuff.getInvestAuthState().equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
            stuff.setUserLevel(CrowdfundCoreConstants.UserLevel.authed);
        }
        stuff.setInvestAuditTime(new Date());
        this.crowdfundUserStuffDao.updateBySelective(stuff);
        
        
        CrowdfundUserStuffModel model =  this.crowdfundUserStuffDao.selectByPrimaryKey(stuff.getId());
        //发送站内信和短信
        String state = stuff.getInvestAuthState();
        if(state.equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
        	sendAuthPassMessage(model,"投资人认证申请");
        }else{
        	sendAuthRefuseMessage(model,"投资人认证申请");
        }
    }
    
    /**
     * 
     * Description: 发送认证审核通过信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendAuthPassMessage(CrowdfundUserStuffModel model,String msg){
       Map<String, String> params = new HashMap<String,String>();
       try{
           logger.info("发送"+msg+"手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_AUTHPASS_MOBILE;
           params.put("authPre",msg);
           SendMessageUtil.sendMobileMessage(nodeCode, model.getUserId(), params);
           logger.info("发送"+msg+"手机短信结束");
       }catch(Exception e){
           logger.error("发送"+msg+"手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送"+msg+"站内信开始");
            params.put("authPre",msg);
            String nodeCode = FbdCoreConstants.NODE_TYPE_AUTHPASS_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, model.getUserId(), params);
            logger.info("发送"+msg+"站内信结束");
        }catch(Exception e){
            logger.error("发送"+msg+"站内信失败，"+e.getMessage());
        }
    }
    
    
    /**
     * 
     * Description: 发送认证审核拒绝信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendAuthRefuseMessage(CrowdfundUserStuffModel model,String msg){
       Map<String, String> params = new HashMap<String,String>();
       try{
           logger.info("发送"+msg+"手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_AUTHREFUSE_MOBILE;
           params.put("authPre",msg);
           SendMessageUtil.sendMobileMessage(nodeCode, model.getUserId(), params);
           logger.info("发送"+msg+"手机短信结束");
       }catch(Exception e){
           logger.error("发送"+msg+"手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送"+msg+"站内信开始");
            params.put("authPre",msg);
            String nodeCode = FbdCoreConstants.NODE_TYPE_AUTHREFUSE_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_RECHARGE_WITHDRAW, model.getUserId(), params);
            logger.info("发送"+msg+"站内信结束");
        }catch(Exception e){
            logger.error("发送"+msg+"站内信失败，"+e.getMessage());
        }
    }
    
    /**
     * 
     * Description:审核领投人认证
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-31 下午7:25:27
     */
    public void updateLeadAuthState(CrowdfundUserStuffModel stuff){
        if(stuff.getInvestAuthState().equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
            stuff.setUserLevel(CrowdfundCoreConstants.UserLevel.lead);
        }
        stuff.setInvestAuditTime(new Date());
        this.crowdfundUserStuffDao.updateBySelective(stuff);
        
        CrowdfundUserStuffModel model =  this.crowdfundUserStuffDao.selectByPrimaryKey(stuff.getId());
        //发送站内信和短信
        String state = stuff.getInvestAuthState();
        if(state.equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
        	CrowdfundUserStuffModel queryModel=new CrowdfundUserStuffModel();
        	queryModel.setUserId(model.getUserId());
        	queryModel.setAuthType("investor");
        	queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
        	long count=crowdfundUserStuffDao.getCount(queryModel);
        	if(count==0){
        		queryModel.setInvestAuthState(null);
        		crowdfundUserStuffDao.deleteByUserAndAuth(queryModel);
               	//当领头时候创建跟头人记录
            	CrowdfundUserStuffModel model1=new CrowdfundUserStuffModel();
            	BeanUtils.copyProperties(model, model1);
            	model1.setUserLevel(CrowdfundCoreConstants.UserLevel.common);
            	model1.setAuthType("investor");
            	model1.setId(PKGenarator.getId());
            	crowdfundUserStuffDao.save(model1);	
        	}
        	sendAuthPassMessage(model,"领投人认证申请");
        }else{
        	sendAuthRefuseMessage(model,"领投人认证申请");
        }
    }
    
    
    
    
    
    
    
    /**
     * 
     * Description: 放款前验证项目状态
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2015-1-9 下午12:28:41
     */
    public void checkBeforeLend(String loanNo) {
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        if (!loan.getLoanState().equals(
                        CrowdfundCoreConstants.crowdFundingState.funded.getValue())) {
            throw new ApplicationException("项目状态不是投标结束");
        }
        //验证股权项目项目方是否已经将合同签署完成
        if("stock".equals(loan.getLoanType())){
        	CrowdfundingSupportModel support = new CrowdfundingSupportModel();
        	support.setLoanNo(loanNo);
        	long projectNoSignCount = this.crowdfundingSupportDao.selectContractSignNum(support);
        	if(projectNoSignCount>0){
        		BusinessConfigModel config = this.businessConfigDao.getBusConfig("lend_contract_sign_switch");
        		String flag = "N";
        		if(config!=null){
        			flag = config.getCode();
        		}
        		if("Y".equals(flag)){
            		//给项目方发送签署合同的信息
            		this.sendLendContractNoSignMessage(loan);
        			throw new ApplicationException("项目方还有未签署的合同,不能进行放款");
        		}
        	}
        	//验证是否还有未到期未支付尾款的投资
        	List<CrowdfundingSupportModel> noComplateSupport = this.crowdfundingSupportDao.selectNoComplateListByLoanNo(loanNo);
        	if(noComplateSupport!=null && noComplateSupport.size()>0){
        		throw new ApplicationException("当前项目有未支付的尾款，不能进行放款！");
        	}
        }
    }
    
    /**
     * 
     * Description: 发送放款合同未签署信息
     *
     * @param 
     * @return void
     * @throws 
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendLendContractNoSignMessage(CrowdfundingModel loan){
       Map<String, String> params = new HashMap<String,String>();
       params.put("loanName",loan.getLoanName());
       params.put("loanNo",loan.getLoanNo());
       try{
           logger.info("发送放款合同未签署手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_CONTRACT_SIGN_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, loan.getLoanUser(), params);
           logger.info("发送放款合同未签署手机短信结束");
       }catch(Exception e){
           logger.error("发送放款合同未签署手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送放款合同未签署站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_CONTRACT_SIGN_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, loan.getLoanUser(), params);
            logger.info("发送放款合同未签署站内信结束");
        }catch(Exception e){
            logger.error("发送放款合同未签署站内信失败，"+e.getMessage());
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
        //变更项目
        loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.lended.getValue());
        loan.setCashTime(new Date());
        loan.setCashUser(operator);
        crowdfundingDao.update(loan);
        this.crowdfundingService.addLoanAudit(operator,loanNo, 
                CrowdfundCoreConstants.loanAuditAction.cash.getValue(),
                null, CrowdfundCoreConstants.crowdFundingState.lended.getValue());
        //给发起人发送放款信息
        this.sendLoanCashMessage(loan);
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
     * Description:流标后更新投资 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-11 上午11:26:33
     */
    public void sendSupportFlow(CrowdfundingSupportModel support,CrowdfundingModel loan){
    	String supportUser = support.getSupportUser();
    	UserModel supportUserModel = this.userDao.findByUserId(supportUser);
    	String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
    	
    	String requestID = PKGenarator.getOrderId();
    	//给借款人转账
		Map<String,String>params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress", loan.getBlockChainAddress()); //转出账户地址
        params.put("sourceKey", loan.getBlockChainSecretKey());
        double amount =support.getSupportAmt()+support.getTransFee();
        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = 0.01;
		}
        params.put("amount",String.valueOf(amount));
        params.put("targetAddress", supportUserModel.getBlockChainAddress());
        params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfundingInvest/flowTransferS2S.html");
        params.put("transferNO",BlockChainStringUtil.getUniqueTransferNoEnc(support.getOrderId()));
        params.put("requestID",requestID);
        logger.info("===========>流标loanNo:"+support.getLoanNo()+"-support_orderId-"+support.getOrderId()+"-区块链转账请求参数-params:"+params);
	    //查询事务异步通知信息
	    BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByQueryRequestId(requestID);
	    if(blockAsynTran==null){
	        BlockAsynTranModel blockAsynTranModel = new BlockAsynTranModel();
	        blockAsynTranModel.setParentId(support.getOrderId());
	        blockAsynTranModel.setType(BlockChainCore.Type.FLOW);
	        blockAsynTranModel.setCreateTime(new Date());
	        blockAsynTranModel.setRequestID(requestID);
	        blockAsynTranModel.setId(PKGenarator.getId());
	        blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.add);
	        blockAsynTranDao.save(blockAsynTranModel);
	    }
	    String resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
		logger.info("==========>区块链-流标请求响应数据："+resultStr);
        @SuppressWarnings("unchecked")
        Map<String,Object> resultMap = JsonHelper.getMapFromJson(resultStr);
        String message = resultMap.get("message")==null?"":resultMap.get("message").toString();
        String status = resultMap.get("status")==null?"":resultMap.get("status").toString();
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){
        	BlockAsynTranModel blockAsynTranModel = this.blockAsynTranDao.findByRequestId(requestID);
        	blockAsynTranModel.setStatus(BlockChainCore.ResultStatus.ServiceSuccess);
        	blockAsynTranModel.setUpdateTime(new Date());
        	this.blockAsynTranDao.update(blockAsynTranModel);
        }else{        	
	         logger.info("放款流标【"+loan.getLoanNo()+"】支持编号【"+support.getOrderId()+"】-【"+message+"】！");
	       	 throw new ApplicationException("放款失败-【"+message+"】！");
         } 
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
			 double noLendAmt = 0.0;// this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(loanNo);	 
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
     * Description:流标操作 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-1 上午11:46:25
     */
    public void updateAfterFlow(CrowdfundingModel loan,String operator){
        //变更项目
        loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.flow.getValue());
        crowdfundingDao.update(loan);
        this.crowdfundingService.addLoanAudit(operator,loan.getLoanNo(), 
                CrowdfundCoreConstants.loanAuditAction.flow.getValue(),
                null, CrowdfundCoreConstants.crowdFundingState.flow.getValue());
        //发送流标的站内信
        sendLoanFlowMessage(loan, loan.getLoanUser());
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
  
    public void sendFlowSuccess(CrowdfundingModel loan){
        List<CrowdfundingSupportModel> supportList = this.getNoLendSupportList(loan.getLoanNo());
        for(CrowdfundingSupportModel support:supportList){
		   	 //更新投资为已取消
		        support.setState(CrowdfundCoreConstants.crowdFundOrderState.cancel);
		        crowdfundingSupportDao.update(support);
		        //  记账 -支持人账单
		        userBillService.addBill(support.getSupportUser(), 
		               CrowdfundCoreConstants.userTradeType.investFlow.getValue(), 
		               support.getSupportAmt()+support.getTransFee(),
		               FbdCoreConstants.tradeDirection.unfreeze.getValue(), 
		               support.getLoanNo(),
		               "项目标号:"+support.getLoanNo()+"流标",
		               null, 
		               support.getOrderId());
		        //给支持者发送投标放款信息
		        this.sendInvestFlowMessage(support, loan);
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
    private void sendInvestFlowMessage(CrowdfundingSupportModel model,CrowdfundingModel loan){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(model.getSupportTime(), null));
       params.put("money",Arith.format(model.getSupportAmt()));
       params.put("loanNo",loan.getLoanNo());
       params.put("loanName",loan.getLoanName());
       try{
           logger.info("发送投标流标手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_FLOW_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getSupportUser(), params);
           logger.info("发送投标流标手机短信结束");
       }catch(Exception e){
           logger.error("发送投标流标手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送投标流标站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_FLOW_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getSupportUser(), params);
            logger.info("发送投标流标站内信结束");
        }catch(Exception e){
            logger.error("发送投标流标站内信失败，"+e.getMessage());
        }
    }
    /**
	 * 转账异步回调处理
	 * @param loanNo
	 */
    public void dealWithSystemTransaction(String loanNo){
 
		 //查询项目信息
		 CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(loanNo);
		 //查询未放款的总金额  (包括运费)
		 double noLendAmt = 0.0; //this.crowdfundingSupportDao.selectNoLendAmtByLoanNo(loanNo);
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
    /**
     * 处理约谈
     */
    public void updateInterviewState(InterviewRecordModel model){
        this.interviewRecordDao.updateBySelective(model);
    }
    
    /**
     * 处理项目进展
     */
    public void updateCrowdfundProgress(CrowdfundingProgressModel model){
        this.crowdfundingProgressDao.updateBySelective(model);
    }
    
    
    /**
     * 保存项目进展
     */
    public void saveCrowdfundProgress(CrowdfundingProgressModel model){
        model.setId(PKGenarator.getId());
        model.setEnterTime(new Date());
        model.setState(CrowdfundCoreConstants.crowdFundAuditState.auditing);
        this.crowdfundingProgressDao.save(model);
    }
    
    
   /**
    * 
    * Description:设为领投人 
    *
    * @param 
    * @return void
    * @throws 
    * @Author haolingfeng
    * Create Date: 2015-6-8 下午2:35:26
    */
    public void saveLoanLeader(CrowdfundLeadInvestorModel model){
        model.setId(PKGenarator.getId());

        CrowdfundUserStuffModel stuffmodel = new CrowdfundUserStuffModel();
        stuffmodel.setAuthTypeState("lead");
        stuffmodel.setUserId(model.getLeadInvestor());
        List<CrowdfundUserStuffModel> userStuff = this.crowdfundUserStuffDao.getByUserId(stuffmodel);
        if(userStuff==null||userStuff.size()==0){
        	throw new ApplicationException("该投资人还未进行领投人认证。");
        } 
        this.crowdfundLeadInvestorDao.save(model);
    }
    
    /**
     * 
     * Description:取消领投人 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-8 下午2:35:08
     */
    public void deleteLoanLeader(String id){
        this.crowdfundLeadInvestorDao.deleteByPrimaryKey(id);
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
    
    public CrowdfundingModel getByLoanNo(String loanNo){
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        return loan;
    }
    
    /**
     * 
     * Description: 手动停止项目
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-23 下午6:52:45
     */
    public void updateAfterHandEndLoan(String loanNo,String operator){
//        String jobName ="crowdfund_financeEndJob"+"_"+loanNo;
//        try {
//            scheduler.deleteJob(jobName, "fbdJobs");
//        } catch (SchedulerException e) {
//            throw new ApplicationException("删除定时任务失败");
//        }
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.funded.getValue());
//        
        loan.setFundEndTime(new Date());
        this.crowdfundingDao.update(loan);
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
    public long getTotalBuyNum(String loanNo){
    	return this.crowdfundingSupportDao.getTotalBuyNum(loanNo);
    }

 

	/**
     * 
     * Description:查询项目的认购列表是否有领投人
     *
     * @param 
     * @return Boolean
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Boolean getSupportListIsLeader(CrowdfundingSupportModel model) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> listMap = this.crowdfundingSupportDao.getOrderSupportList(model);
		for(Map<String,Object> map : listMap){
			String isLeader = map.get("isLeader").toString();
			if("1".equals(isLeader)){
				return true;
			}
		}
		return false;
	}

	/**
     * Description: 修改公司认证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author 武文斌
     */
	public void modifyCompany(UserUploadFileModel model) {
		UserUploadFileModel userUploadFileModel = this.userUploadFileDao.selectByTypeAndUserId(model);
		if(null != userUploadFileModel){
			this.userUploadFileDao.updateByFileType(model);
		}else{
			model.setId(PKGenarator.getId());
			model.setFileState("auditing");
			this.userUploadFileDao.save(model);
		}
		
	}

	/**
     * 
     * Description:机构领投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin	
     */
	public void auditOrgLeadorAuth(CrowdfundUserStuffModel stuff) {
		if(stuff.getInvestAuthState().equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
            stuff.setUserLevel(CrowdfundCoreConstants.UserLevel.lead);
        }
		stuff.setInvestAuditTime(new Date());
        stuff.setOrgLendAuditTime(new Date());
        this.crowdfundUserStuffDao.updateBySelective(stuff);
        
        
        CrowdfundUserStuffModel model =  this.crowdfundUserStuffDao.selectByPrimaryKey(stuff.getId());
        //发送站内信和短信
        String state = stuff.getInvestAuthState();
        if(state.equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
        	CrowdfundUserStuffModel queryModel=new CrowdfundUserStuffModel();
        	queryModel.setUserId(model.getUserId());
        	queryModel.setAuthType("orgInvestor");
        	queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
        	long count=crowdfundUserStuffDao.getCount(queryModel);
        	if(count==0){
        		queryModel.setInvestAuthState(null);
        		crowdfundUserStuffDao.deleteByUserAndAuth(queryModel);
               	//当领头时候创建跟头人记录
            	CrowdfundUserStuffModel model1=new CrowdfundUserStuffModel();
            	BeanUtils.copyProperties(model, model1);
            	model1.setUserLevel(CrowdfundCoreConstants.UserLevel.common);
            	model1.setAuthType("orgInvestor");
            	model1.setId(PKGenarator.getId());
            	crowdfundUserStuffDao.save(model1);	
        	}
        	sendAuthPassMessage(model,"领投机构认证申请");
        }else{
        	sendAuthRefuseMessage(model,"领投机构认证申请");
        }
	}

	/**
     * 
     * Description:机构跟投投人认证审核 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin	
     */
	public void auditOrgInvestorAuth(CrowdfundUserStuffModel stuff) {
		
		
		
		if(stuff.getInvestAuthState().equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
            stuff.setUserLevel(CrowdfundCoreConstants.UserLevel.authed);
        }
        stuff.setOrgInvestAuditTime(new Date());
        stuff.setInvestAuditTime(new Date());
        this.crowdfundUserStuffDao.updateBySelective(stuff);
        
        CrowdfundUserStuffModel model =  this.crowdfundUserStuffDao.selectByPrimaryKey(stuff.getId());
        //发送站内信和短信
        String state = stuff.getInvestAuthState();
        if(state.equals(CrowdfundCoreConstants.crowdFundAuditState.passed)){
        	sendAuthPassMessage(model,"跟投机构认证申请");
        }else{
        	sendAuthRefuseMessage(model,"跟投机构认证申请");
        }
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
	public Map<String,Object> getChargeFeeScale() {
		Map<String,Object> map = new HashMap<String, Object>();
		Double chargeScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.SERVICE_FEE_SCALE).getCode()==null?"0.1":businessConfigDao.getBusConfig(CrowdfundCoreConstants.SERVICE_FEE_SCALE).getCode());
		map.put("chargeScale", chargeScale);
		return map;
	}
    

}
