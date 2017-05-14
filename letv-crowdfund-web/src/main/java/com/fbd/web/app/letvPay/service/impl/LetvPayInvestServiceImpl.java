package com.fbd.web.app.letvPay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.bill.dao.IUserBillDao;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainAccountService;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.dao.ILeadFollowDao;
import com.fbd.core.app.crowdfunding.dao.IStockBackSetDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.LeadFollowModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.quartz.util.QuartzUtils;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;
import com.fbd.core.util.LockUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.letvPay.service.ILetvPayInvestService;

@Service("letvPayInvestService")
public class LetvPayInvestServiceImpl implements ILetvPayInvestService {
	private static final Logger logger = LoggerFactory
            .getLogger(LetvPayInvestServiceImpl.class);
	
	@Resource
	private ICrowdfundingDao crowdfundingDao;
	
	@Resource
	private ICrowdfundingBackSetDao crowdfundingBackSetDao ;
	
	@Resource
	private ICrowdfundingSupportDao crowdfundingSupportDao ;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
	@Resource
	private IStockBackSetDao stockBackSetDao ;
	
	@Resource
	private ICrowdfundUserStuffDao crowdfundUserStuffDao;
	
	@Resource
    private ILeadFollowDao leadFollowDao;
	
	@Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
	
	@Resource
	private IBlockChainAccountService blockChainAccountService; 
	
	@Resource
	private IUserDao userDao;
	
	@Resource
	private IUserBillService userBillService;
	
	@Resource
	private ICrowdfundingProgressDao crowdfundingProgressDao;
	
	@Resource
	private IUserBillDao userBillDao;
	
	@Resource
	private IBusinessConfigDao businessConfigDao;
	
	@Resource
    private StdScheduler scheduler;
	
    @Resource
    private ICrowdfundingService crowdfundingService;	
    //回报支持满额
    public boolean BackSetisFull(CrowdfundingSupportModel model){
    	String loanNo = model.getLoanNo();
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(model.getLoanNo());

    	//实物回报支持验证
        if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PUBLIC_SERVICE)|| 
                loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
            CrowdfundingBackSetModel backSet = crowdfundingBackSetDao.getByLoanNoAndSetNo(loanNo, model.getBackNo());
            CrowdfundingSupportModel q = new CrowdfundingSupportModel();
            q.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
            q.setLoanNo(loanNo);
            q.setBackNo(model.getBackNo());
            long supportNum = crowdfundingSupportDao.getCount(q);
            if(backSet.getNumberLimits() > 0){
                if(supportNum+1 > backSet.getNumberLimits() || 
                        backSet.getState().equals(CrowdfundCoreConstants.crowdFundBackState.FULL)){
                	return true;
                  //  throw new ApplicationException("谢谢支持，此回报支持已经满额");
                }
            }
        }
        return false;
    }
    
	/**
     * 
     * Description: 支持前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void checkBeforeSupport(CrowdfundingSupportModel model,boolean validateBalanceFlag) {
		String loanNo = model.getLoanNo();
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        if(loan ==null){
        	logger.info("=========项目信息不存在,项目编号：["+loanNo+"]");
        	throw new ApplicationException("项目信息不存在!");
        }
        String state = loan.getLoanState();
        if(!state.equals(CrowdfundCoreConstants.crowdFundingState.funding.getValue())){
            throw new ApplicationException("此众筹项目已经结束");
        }
        //众筹类型判断
        if(loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
                
	        	CrowdfundUserStuffModel stuffmodel = new CrowdfundUserStuffModel();
		        stuffmodel.setInvestAuth("investAuth");
		        stuffmodel.setUserId(model.getSupportUser());
		        List<Map<String,Object>> userStuff = this.crowdfundUserStuffDao.getAuthList(stuffmodel);
		        if(userStuff.size()==0){
		        	throw new ApplicationException("请先进行投资人认证");
		        } 
                /*long isExist = this.crowdfundLeadInvestorDao.getByLeaderAndLoanNo(loanNo, model.getSupportUser());
                if(isExist > 0){
                    throw new ApplicationException("您已领投，所以不能跟投");
                }*/
                if(StringUtils.isEmpty(model.getLeadInvestor())){
                    throw new ApplicationException("请选择至少一位领投人");
                }else{
                    String[] leadInvestors = model.getLeadInvestor().split(",");
                    CrowdfundUserStuffModel queryModel=new CrowdfundUserStuffModel();
                    queryModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
                    queryModel.setAuthTypeState("lead");
                    for(String leadInvestor:leadInvestors){
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
                loan.getLoanType().equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
            CrowdfundingBackSetModel backSet = crowdfundingBackSetDao.getByLoanNoAndSetNo(loanNo, model.getBackNo());
            CrowdfundingSupportModel q = new CrowdfundingSupportModel();
            q.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
            q.setLoanNo(loanNo);
            q.setBackNo(model.getBackNo());
            long supportNum = crowdfundingSupportDao.getCount(q);
    		long newbuy = crowdfundingSupportDao.selectPayingParts(loanNo);
    		supportNum =supportNum+newbuy;
            if(backSet.getNumberLimits() > 0){
                if(supportNum+1 > backSet.getNumberLimits() || 
                        backSet.getState().equals(CrowdfundCoreConstants.crowdFundBackState.FULL)){
                    throw new ApplicationException("谢谢支持，此回报支持已经满额");
                }
            }
          
            
            //实物允许超募
            double fundAmt = loan.getOverFundAmt();
            if( fundAmt>0){
                
            }else{
                fundAmt =loan.getFundAmt();
            }
            double approveAmt = loan.getApproveAmt();
            if(Arith.add(model.getSupportAmt(), approveAmt) > fundAmt){
                throw new ApplicationException("目前支持的金额超过目标金额"+fundAmt);
            }
            
            //抽奖验证 start
            String backNo=model.getBackNo();
            if(StringUtils.isEmpty(backNo)){
            	throw new ApplicationException("回报编号不能为空");
            }
            CrowdfundingBackSetModel backModel=crowdfundingBackSetDao.getByLoanNoAndSetNo(loanNo, backNo);
            if("1".equals(backModel.getPrizeDrawFlag())){
        		if(backModel.getPrizeNum()==null||backModel.getPrizeNum()<=0){
        			throw new ApplicationException("指定回报的抽奖名额数据异常");
        		}
        		if(backModel.getPrizeInvestNum()==null||backModel.getPrizeInvestNum()<=0){
        			throw new ApplicationException("指定回报的抽奖购买上限数据异常");
        		}
        		if(StringUtils.isEmpty(model.getSupportUser())){
        			throw new ApplicationException("支持人不能为空");
        		}
        		CrowdfundingSupportModel queryModel=new CrowdfundingSupportModel();
        		queryModel.setSupportUser(model.getSupportUser());
        		queryModel.setBackNo(backNo);
        		queryModel.setLoanNo(loanNo);
        		queryModel.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
        		long investCount=crowdfundingSupportDao.getCount(queryModel);
        		investCount =investCount+newbuy;
        		if((investCount+1)>backModel.getPrizeInvestNum()){
        			throw new ApplicationException("已超过抽奖购买上限");
        		}
            }
            //抽奖验证 end
        }else{
            //查询认购成功的份数
            long payedParts = crowdfundingSupportDao.getPayedParts(loanNo);
//            StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(loanNo);
            //剩余份数为0
            if(loan.getStockNum()-payedParts == 0){
                throw new ApplicationException("对不起，该项目已全部认购");
            }
            double minInvestAmt = loan.getStockPartAmt();
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
        if(null == model.getTransFee()){
        	model.setTransFee(0.0);
        }
        
        //验证账户余额
        if(validateBalanceFlag){
            UserBillModel latestBill = this.userBillDao.selectByUserId(model.getSupportUser());
            if(null == latestBill){
                throw new ApplicationException("账户余额不足，请充值");
            }else if(Arith.add(model.getSupportAmt(),model.getTransFee())>latestBill.getBalance()){
                throw new ApplicationException("账户余额不足，请充值");
            }
        }
        if(null != model.getIntentionAmt() && model.getIntentionAmt()>0 ){
        	int yxjDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode());

        	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");  
        	String todayStr =  ft.format(new Date());
        	Date endTime = DateUtil.getDate(DateUtils.stringToDate(todayStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss"), yxjDay);
           if(DateUtil.compareDate(endTime, loan.getFundEndTime(), "yyyy-MM-dd HH:mm:ss")>0){
            	throw new ApplicationException("意向金支付时间已过,请全额投资");
            }
        }
		
	}

	/**
     * 
     * Description: 用户投资
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String,Object> createFullInvest(CrowdfundingSupportModel supportModel) {
		CrowdfundingSupportModel model = this.crowdfundingSupportDao.getByOrderId(supportModel.getOrderId());

      try{
        String operationId = PKGenarator.getId();
        //组装参数
        Map<String,String> params = this.getInvestParam(model,operationId);
        logger.info("区块链-投资请求参数："+params);
        String requestUrl = "";

        //保存操作数据
        trusteeshipOperationService.saveOperationModel(operationId, model.getSupportUser(),
                model.getOrderId(),LetvPayConstants.BusiType.invest,requestUrl,"blockBain",MapUtil.mapToString(params));
        Map<String,Object> resultMap = null;
        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
    	    logger.info("================投资成功==============================");
            resultMap = new HashMap<String,Object>();
    	    resultMap.put("orderId", supportModel.getOrderId()); 
	    	String type = "";
	        String buyType = model.getBuyType();
	        if("investPay".equals(buyType)){  //跟投
	        	 //转账需要判断 是否为意向金支付
          		if(null != model.getIntentionAmt() && model.getIntentionAmt()>0){
          			type = BlockChainCore.Type.intention_pay;
          		}else{
          			type = BlockChainCore.Type.invest_pay;
          		}
	        }else if("leaderPay".equals(buyType)){  //领投
	        	type = BlockChainCore.Type.leader_pay;
	        }
        	this.updateInvest(resultMap,type);
        }else{
        	String orderId = model.getOrderId();
        	if (this.isSupportLock(orderId)) {
                logger.info("请求失败,orderId被锁！" + orderId);
            }else{
            	 if(supportModel.getNewBuyNum()!=null){
            		 supportModel.setNewBuyNum(supportModel.getNewBuyNum()+1);
            	 }else{
            		 supportModel.setNewBuyNum(1); 
            	 }
	        	 String type = "";
	        	 String buyType = model.getBuyType();
	        	 if("investPay".equals(buyType)){  //跟投
		        	 //转账需要判断 是否为意向金支付
	          		if(null != model.getIntentionAmt() && model.getIntentionAmt()>0){
	          			type = BlockChainCore.Type.intention_pay;
	          			supportModel.setIntentionState("locking");
	          		}else{
	          			type = BlockChainCore.Type.invest_pay;
	          			supportModel.setPayState("locking");
	          		}
		        }else if("leaderPay".equals(buyType)){  //领投
		        	type = BlockChainCore.Type.leader_pay;
		        }
            	crowdfundingSupportDao.updateByOrderNo(supportModel);
	        	//添加一条事务通知数据
	          	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
	          	blockAsynTran.setId(operationId);
	          	blockAsynTran.setParentId(supportModel.getOrderId());  //支持编号
	          	blockAsynTran.setCreateTime(new Date());
	          	blockAsynTran.setUpdateTime(new Date());
	          	blockAsynTran.setType(type);
	          	blockAsynTran.setRequestID(params.get("requestID"));
	          	this.blockAsynTranDao.save(blockAsynTran);
	          	resultMap = new HashMap<String,Object>();
	     	    resultMap.put("orderId", supportModel.getOrderId()); 
	        	
	        	String resultStr = MockUtils.transfer(params);
	        	resultMap = JsonHelper.getMapFromJson(resultStr);
	        	String status = resultMap.get("status").toString();
	            String message = resultMap.get("message").toString();
	            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){//转账事务成功
	     
	            }else{
	             	throw new ApplicationException(message);
	            }
	            return resultMap;
            }
         }
         return resultMap;
      }catch(Exception e){
    	  e.printStackTrace();
    	  if(supportModel.getNewBuyNum()!=null)
     	     supportModel.setNewBuyNum(0);
    	  if(null != model.getIntentionAmt() && model.getIntentionAmt()>0){
    	      supportModel.setPayState(CrowdfundCoreConstants.crowdFundPayState.noPay);
    	  }else{
    		  supportModel.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.noPay);
    	  }
     	 crowdfundingSupportDao.updateByOrderNo(supportModel);
      }
      return null;  
	}
	
	/**
     * Description: 组装投资参数
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String, String> getInvestParam(CrowdfundingSupportModel model,String operateId) {
		Map<String,String> params = new HashMap<String, String>();
		UserModel user = this.userDao.findByUserId(model.getSupportUser());
		CrowdfundingModel crowdfundingModel =	crowdfundingService.getByloanNo(model.getLoanNo());
        double amount=0;
		//转账需要判断 是否为意向金支付
		if(null != model.getIntentionAmt() && model.getIntentionAmt()>0){
			//为意向金支付
			System.out.println("IntentionAmt:"+model.getIntentionAmt());
			amount =model.getIntentionAmt();
		}else{
			amount =  model.getSupportAmt();
		}
		double transFee = model.getTransFee();
		//判断是否需要运费
		if(transFee >0){
			System.out.println("transFee:"+model.getTransFee());
			amount = Arith.add(transFee, amount);
		}
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = 0.01;
		}
		//查询项目信息
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(model.getLoanNo());
        params.put("serviceID", "transfer");
		params.put("transferNO",operateId);
		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress",user.getBlockChainAddress()); //转出账户地址为投资人账户出账户
		params.put("sourceKey",user.getBlockChainSecretKey());
		params.put("targetAddress",loan.getBlockChainAddress());  //转入账户为项目账号 
        params.put("requestID", PKGenarator.getOrderId());
		params.put("amount",String.valueOf(amount));
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"letvPay/invest/investPayNotify.html");
		return params;
	}

	/**
     * 
     * Description: 保存投资单
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-28 下午7:00:41
     */
    public void addSupportOrder(CrowdfundingSupportModel invest){
        CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
        invest.setId(PKGenarator.getId());
        // 获得订单号
        if(StringUtil.isEmpty(invest.getOrderId())){
            invest.setOrderId(PKGenarator.getInvestId());   
        }
        invest.setPayState(CrowdfundCoreConstants.crowdFundPayState.noPay);
        invest.setSupportTime(new Date());
        invest.setIsTransfer("0");
        invest.setSupportWay(CrowdfundCoreConstants.supportWay.byUser);
        invest.setState(CrowdfundCoreConstants.crowdFundOrderState.add); //设置订单状态为新建
       
        String loanType = crowdfund.getLoanType();
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.STOCK)){
        	if(("IOS".equals(invest.getDeviceType())||"ANDROID".equals(invest.getDeviceType().toUpperCase()) )  && (invest.getLeadInvestor()==null||"".equals(invest.getLeadInvestor()))){
        		invest.setLeadInvestor(invest.getSupportUser());
        	}
            if(StringUtils.isEmpty(invest.getLeadInvestor())){
                throw new ApplicationException("请选择至少一位领投人");
            }
            double minInvestAmt = crowdfund.getStockPartAmt();
            double investAmt = invest.getBuyNum()*minInvestAmt;
            invest.setSupportAmt(Arith.round(investAmt));
            invest.setPartMoney(minInvestAmt);
            invest.setTransferAmt(0.0);
            invest.setTransFee(0.0);
            //意向金调度任务
          /*  if(null != invest.getIntentionAmt() && invest.getIntentionAmt()>0){
            	invest.setIntentionNo(PKGenarator.getOrderId());
            	//获取意向金支付比例
            	Double yxjScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode()==null?"0.5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode());
            	invest.setIntentionAmt(Arith.mul(invest.getSupportAmt(), yxjScale));
            	Integer yxjwyDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode());
            	
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");  
            	String todayStr =  ft.format(new Date());
            	
            	Date financeEndDate = DateUtil.getDate(DateUtils.stringToDate(todayStr+" 23:59:00", "yyyy-MM-dd HH:mm:ss"), yxjwyDay);
            	//增加调度
                String cronExpression = DateUtil.getSchedulerCronExpression(financeEndDate);
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put(QuartzJobConstants.PARAM_CROWDFUND_INTENTION_FINANCE_END,invest.getOrderId());
                QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_CROWDFUND_INTENTION_FINANCE_END+"_"+invest.getOrderId(), 
                        QuartzJobConstants.CLASS_CROWDFUND_INTENTION_FINANCE_END, parameter, 
                        QuartzJobConstants.TRIGGER_CROWDFUND_INTENTION_FINANCE_END+"_"+invest.getOrderId(), cronExpression, 
                        QuartzJobConstants.DES_CROWDFUND_INTENTION_FINANCE_END);
                invest.setIntentionEndTime(financeEndDate);
                
            }*/
        }else{
            invest.setBuyNum(1);
        }
        //保存投资
        this.crowdfundingSupportDao.save(invest);
        //保存领投跟投人关系
        if(null != invest.getLeadInvestor()){
            String[] leadInvestors = invest.getLeadInvestor().split(",");
            for(String leadInvestor:leadInvestors){
                LeadFollowModel leadFollow = new LeadFollowModel();
                leadFollow.setId(PKGenarator.getId());
                leadFollow.setInvestor(invest.getSupportUser());
                leadFollow.setLoanNo(invest.getLoanNo());
                leadFollow.setOrderId(invest.getOrderId());
                leadFollow.setSuperInvestor(leadInvestor);
                this.leadFollowDao.save(leadFollow);
            }
        }
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
	 * 投资后续处理
	 * @param orderId
	 * @param resultMap
	 */
	public void investPayAfter(String orderId,String requestId,Timer timer){
		
		BlockAsynTranModel model = blockAsynTranDao.findByRequestId(requestId);
    	String status = model.getQueryStatus();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){  //事务成功，投资成功
    		logger.info("=================投资成功========================");	
    		Map<String,Object> resultMap = new HashMap<String,Object>();
    		resultMap.put("orderId", orderId);
    		this.updateInvest(resultMap,model.getType());
    		logger.info("===============投资成功停止定时任务==================");
    		timer.cancel();
    	}else if(BlockChainCore.ResultStatus.TRANSACTION_FAIL.equals(status)){  //事务失败，投资失败
    		logger.info("==================投资失败========================");
    		timer.cancel();
    	}else{

    	}
    }
    /**
     * 
     * Description: 更新用户投资
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	@SuppressWarnings("unused")
	public void updateInvest(Map<String, Object> resultMap,String type) {
		String orderId = resultMap.get("orderId").toString();
		if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
			if (this.isSupportLock(orderId)) {
	            logger.info("请求失败,orderId被锁！" + orderId);
	            return;
	        }
		}
        CrowdfundingSupportModel invest = this.crowdfundingSupportDao.getByOrderId(orderId);
        invest.setNewBuyNum(0);
        try {
            if (invest == null) {
                throw new ApplicationException("订单号为" + orderId + "的投资单不存在");
            }
            String payState = CrowdfundCoreConstants.crowdFundPayState.payed;
            if (  invest.getState()!=null && payState.equals(invest.getPayState()) ) {// 充值状态已经更新
                logger.info(orderId + "支付已经完成！");
                return;
            }
            if(null != invest.getIntentionAmt() && invest.getIntentionAmt()>0 && invest.getIntentionState()!=null&&invest.getIntentionState().equals("payed"))
            {
            	 logger.info(orderId + "支付已经完成！");
                 return;
            }
            //意向金支付
//            if(null != invest.getIntentionAmt() && invest.getIntentionAmt()>0){
//            	invest.setActualPayAmt(invest.getIntentionAmt());
//            	invest.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.payed);
//            }else{
//            	 invest.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
//                 invest.setCompleteTime(new Date());
//            	 invest.setActualPayAmt(invest.getSupportAmt());
//            }
            Date financeEndDate = null;
            if("1".equals(invest.getIntentionFlag())){  //意向金支付
            	invest.setActualPayAmt(invest.getIntentionAmt());
            	invest.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.payed);
            	invest.setIntentionNo(PKGenarator.getOrderId());
            	//获取意向金支付比例
            	Double yxjScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode()==null?"0.5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode());
            	invest.setIntentionAmt(Arith.mul(invest.getSupportAmt(), yxjScale));
            	Integer yxjwyDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode());
            	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");  
            	String todayStr =  ft.format(new Date());
            	//计算意向金支付尾款截止时间
            	financeEndDate = DateUtil.getDate(DateUtils.stringToDate(todayStr+" 23:59:00", "yyyy-MM-dd HH:mm:ss"), yxjwyDay);
                //设置调度任务
            	invest.setIntentionEndTime(financeEndDate);
            	
            }else{
                invest.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
                invest.setCompleteTime(new Date());
         	    invest.setActualPayAmt(invest.getSupportAmt());	
            }
            this.crowdfundingSupportDao.updateBySelective(invest);
            
            CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
            
            //投资冻结金额 = 实际支付金额+运费
            double trandAmt = invest.getActualPayAmt()+invest.getTransFee();
            userBillService.addInvestFreeze(loan,invest.getOrderId(),
            		trandAmt,invest.getSupportUser());
            //更新项目
            this.updateLoanAfterInvest(invest, loan.getLoanType());
            //添加意向金尾款支付截止时间调度任务(意向金尾款支付过期使用job代替)
//            if("1".equals(invest.getIntentionFlag())){
//            	//增加调度
//                String cronExpression = DateUtil.getSchedulerCronExpression(financeEndDate);
//                Map<String, String> parameter = new HashMap<String, String>();
//                parameter.put(QuartzJobConstants.PARAM_CROWDFUND_INTENTION_FINANCE_END,invest.getOrderId());
//                QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_CROWDFUND_INTENTION_FINANCE_END+"_"+invest.getOrderId(), 
//                        QuartzJobConstants.CLASS_CROWDFUND_INTENTION_FINANCE_END, parameter, 
//                        QuartzJobConstants.TRIGGER_CROWDFUND_INTENTION_FINANCE_END+"_"+invest.getOrderId(), cronExpression, 
//                        QuartzJobConstants.DES_CROWDFUND_INTENTION_FINANCE_END);
//            }
            //发送信息
            invest.setLoanName(loan.getLoanName());
            this.sendInvestMessage(invest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("更新支付单失败：" + e.getMessage());
        } finally {
            LockUtil.getInstance().remove(
                    FbdCoreConstants.LOCK_RECHARGE + orderId);
        }
	}
	/**
     * 
     * Description: 更新用户投资
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateInvestBlock(Map<String, Object> resultMap,String type) {
		String orderId = resultMap.get("orderId").toString();
		if (this.isSupportLock(orderId)) {
            logger.info("请求失败,orderId被锁！" + orderId);
            return;
        }
        CrowdfundingSupportModel invest = this.crowdfundingSupportDao.getByOrderId(orderId);
        try {
            if (invest == null) {
                throw new ApplicationException("订单号为" + orderId + "的投资单不存在");
            }
            String payState = CrowdfundCoreConstants.crowdFundPayState.payed;
            if (  invest.getState()!=null && payState.equals(invest.getPayState()) ) {// 充值状态已经更新
                logger.info(orderId + "支付已经完成！");
                return;
            }
            if(null != invest.getIntentionAmt() && invest.getIntentionAmt()>0 && invest.getIntentionState()!=null&&invest.getIntentionState().equals("payed"))
            {
            	 logger.info(orderId + "支付已经完成！");
                 return;
            }
            //意向金支付
            if(null != invest.getIntentionAmt() && invest.getIntentionAmt()>0){
            	invest.setActualPayAmt(invest.getIntentionAmt());
            	invest.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.payed);
            //	invest.setIntentionNo(PKGenarator.getOrderId());
            }else{
            	 invest.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
                 invest.setCompleteTime(new Date());
            	 invest.setActualPayAmt(invest.getSupportAmt());
            }
            this.crowdfundingSupportDao.updateBySelective(invest);
            
            CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
            
            //投资冻结金额 = 实际支付金额+运费
            double trandAmt = invest.getActualPayAmt()+invest.getTransFee();
            userBillService.addInvestFreeze(loan,invest.getOrderId(),
            		trandAmt,invest.getSupportUser());
            //更新项目
            this.updateLoanAfterInvest(invest, loan.getLoanType());
            //发送信息
            invest.setLoanName(loan.getLoanName());
            this.sendInvestMessage(invest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("更新支付单失败：" + e.getMessage());
        } finally {
            LockUtil.getInstance().remove(
                    FbdCoreConstants.LOCK_RECHARGE + orderId);
        }
	}
	private void updateLoanAfterInvest(CrowdfundingSupportModel invest,String loanType){
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.PUBLIC_SERVICE)|| 
                loanType.equals(CrowdfundCoreConstants.CrowdfundType.PRODUCT)){
            CrowdfundingBackSetModel backSet = crowdfundingBackSetDao.
                    getByLoanNoAndSetNo(invest.getLoanNo(), invest.getBackNo());
            if(backSet.getNumberLimits() > 0){//有名额限制时，更新回报设置
                CrowdfundingSupportModel q = new CrowdfundingSupportModel();
                q.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
                q.setLoanNo(invest.getLoanNo());
                q.setBackNo(invest.getBackNo());
                long supportNum = crowdfundingSupportDao.getCount(q);
                if(supportNum >= backSet.getNumberLimits()){
                   //更新回报设置的状态
                    backSet.setState(CrowdfundCoreConstants.crowdFundBackState.FULL);
                    crowdfundingBackSetDao.update(backSet);
                }
            }
        }
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
        //更新众筹项目信息
        loan.setApproveAmt(Arith.add(loan.getApproveAmt(),invest.getSupportAmt()));
        
        
       //如果项目已融资金额等于目标融资金额上限时，结束众筹  针对股权与房产众筹
        if(loanType.equals(CrowdfundCoreConstants.CrowdfundType.STOCK)|| 
                loanType.equals(CrowdfundCoreConstants.CrowdfundType.HOUSE)){
        	double fundAmt = loan.getOverFundAmt();
        	if(fundAmt<=0){
        		fundAmt = loan.getFundAmt();
        	}
            if(loan.getApproveAmt() >=fundAmt){
                //项目进展-筹资结束
                String progressNode = CrowdfundCoreConstants.CROWDFUND_PROGRESS_FUNDEND;
                boolean isNeedAdd = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(), progressNode);
                if(isNeedAdd){
                    long supportCnt = crowdfundingSupportDao.getSupportUserCounts(loan.getLoanNo());
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("approveAmt",Arith.format(loan.getApproveAmt()));
                    params.put("supportRatio","100");
                    params.put("supportUserCnt",String.valueOf(supportCnt));
                    crowdfundingProgressDao.saveProgress(progressNode, loan.getLoanNo(), params);
                }
                loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.funded.getValue());
            }
        }else{
            if(loan.getApproveAmt() >= loan.getFundAmt()){
                //项目进展-筹资成功
                String progressNode = CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_FUNDOK;
                boolean isNeedAdd = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(), progressNode);
                if(isNeedAdd){
                    Date sDate = new Date();
                    String supportTime = DateUtil.date2Str(new Date(), null)+" "+sDate.getHours();
                    long supportCnt = crowdfundingSupportDao.getSupportUserCounts(loan.getLoanNo());
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("approveAmt",Arith.format(loan.getApproveAmt()));
                    params.put("supportTime",supportTime);
                    params.put("supportUserCnt",String.valueOf(supportCnt));
                    crowdfundingProgressDao.saveProgress(progressNode, loan.getLoanNo(), params);
                }
            }
            
            double fundAmt = loan.getOverFundAmt();
        	if(fundAmt<=0){
        		fundAmt = loan.getFundAmt();
        	}
            //add start
            if(loan.getApproveAmt()>=fundAmt){
            	//当筹资金额大于等于超募金额的时候关闭项目
                loan.setLoanState(CrowdfundCoreConstants.crowdFundingState.funded.getValue());
            }
            //add end
        }
        crowdfundingDao.update(loan);
        
        //判断是否是第一人投资
        String progressNode = CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_FIRSTINVEST;
        boolean isNeedAdd = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(),progressNode);
        if(isNeedAdd){
        	UserModel supportUser = this.userDao.findByUserId(invest.getSupportUser());
            Map<String,String> params = new HashMap<String,String>();
            params.put("supportUser",invest.getSupportUser());
            params.put("supportUserNickName",supportUser.getNickName());
            params.put("supportUserRealName",supportUser.getRealName());
            params.put("investAmt",Arith.format(invest.getSupportAmt()));
            crowdfundingProgressDao.saveProgress(progressNode, loan.getLoanNo(), params);
        }else{
            //判断是否需要增加50人支持的进展  supportTime
            long supportCnt = crowdfundingSupportDao.getSupportUserCounts(loan.getLoanNo());
            if(supportCnt == 50){
                progressNode = CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_50INVESTOR;
                isNeedAdd = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(), progressNode);
                if(isNeedAdd){
                    Date sDate = new Date();
                    String supportTime = DateUtil.date2Str(new Date(), null)+" "+sDate.getHours();
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("supportTime",supportTime);
                    crowdfundingProgressDao.saveProgress(progressNode, loan.getLoanNo(), params);
                }
              //判断是否需要增加100人支持的进展  supportTime
            }else if(supportCnt == 100){
                progressNode = CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_100INVESTOR;
                isNeedAdd = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(), progressNode);
                if(isNeedAdd){
                    Date sDate = new Date();
                    String supportTime = DateUtil.date2Str(new Date(), null)+" "+sDate.getHours();
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("supportTime",supportTime);
                    crowdfundingProgressDao.saveProgress(progressNode, loan.getLoanNo(), params);
                }
            }
        }
        //项目进展-达到50%
        double ratio = Arith.round(loan.getApproveAmt()/loan.getFundAmt(),2);
        if(ratio >= 0.5){
            String progressNode1 = CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_FUNDOK;
            boolean isNeedAdd1 = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(), progressNode1);
            
            progressNode = CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_50FUND;
            isNeedAdd = crowdfundingProgressDao.isNeedAdd(loan.getLoanNo(), progressNode);
            if(isNeedAdd && isNeedAdd1){
                Date sDate = new Date();
                String supportTime = DateUtil.date2Str(new Date(), null)+" "+sDate.getHours();
                Map<String,String> params = new HashMap<String,String>();
                params.put("supportTime",supportTime);
                crowdfundingProgressDao.saveProgress(progressNode, loan.getLoanNo(), params);
                
                //项目筹集达到50%给项目方发站内信
                this.sendLoanProgressMessage(loan);
            }
            
           
        }
    }
	
	/**
	 * 项目达到50%时给项目方发送短信验证码
	 * @param loan
	 */
	private void sendLoanProgressMessage(CrowdfundingModel loan){
		
	   Map<String, String> params = new HashMap<String,String>();
       params.put("loanName",loan.getLoanName());
       params.put("loanNo",loan.getLoanNo());
       params.put("progress",String.valueOf(50));
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
       try{
            logger.info("发送项目达到50%时内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_PROGRESS_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, loan.getLoanUser(), params);
            logger.info("发送项目达到50%时站内信结束");
       }catch(Exception e){
            logger.error("发送项目达到50%时站内信失败，"+e.getMessage());
       }
	}
	
	
	
	
	

    /**
     * 
     * Description: 发送投资信息
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendInvestMessage(CrowdfundingSupportModel model){
       Map<String, String> params = new HashMap<String,String>();
       params.put("time",DateUtil.dateTime2Str(new Date(), null));
       params.put("amt",String.valueOf(model.getActualPayAmt()));
       params.put("userName",model.getSupportUser());
       params.put("loanName",model.getLoanName());
       params.put("loanNo",model.getLoanNo());
       params.put("orderId",model.getOrderId());
       try{
           logger.info("发送手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_PAY_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode, model.getSupportUser(), params);
           logger.info("发送手机短信结束");
       }catch(Exception e){
           logger.error("发送手机短信失败，"+e.getMessage());
       }
        try{
            logger.info("发送支付站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_PAY_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, null, model.getSupportUser(), params);
            logger.info("发送支付站内信结束");
        }catch(Exception e){
            logger.error("发送支付站内信失败，"+e.getMessage());
        }
    }

    /**
     * 
     * Description:用户领头前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void checkBeforeLendSupport(CrowdfundingSupportModel model,boolean validateBalanceFlag) {
		String loanNo = model.getLoanNo();
		/*if(null == model.getOrderId() || "".equals(model.getOrderId())){
			throw new ApplicationException("订单号不能为空");
		}*/
        CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(loanNo);
        String state = loan.getLoanState();
        if(!state.equals(CrowdfundCoreConstants.crowdFundingState.preheat.getValue())){
            throw new ApplicationException("此众筹项目已经预热结束");
        }
        
        CrowdfundUserStuffModel stuffmodel = new CrowdfundUserStuffModel();
        stuffmodel.setAuthTypeState("lead");
        stuffmodel.setUserId(model.getSupportUser());
        List<CrowdfundUserStuffModel> userStuff = this.crowdfundUserStuffDao.getByUserId(stuffmodel);
        if(userStuff==null||userStuff.size()==0){
            throw new ApplicationException("请先进行领投人认证");
        } 
        
        //查询认购成功的份数
        long payedParts = crowdfundingSupportDao.getPayedParts(loanNo);
//	        StockBackSetModel backSet =  stockBackSetDao.getByLoanNo(loanNo);
        int buyNum = model.getBuyNum();
        long payingNum =crowdfundingSupportDao.selectPayingParts(loanNo);
        if(loan.getStockNum() < payedParts+buyNum +payingNum){
            throw new ApplicationException("对不起，购买份数超过剩余份数");
        }
        
        double investAmt = Arith.round(model.getBuyNum()*loan.getStockPartAmt());
        if(validateBalanceFlag){
            //验证账户余额
            UserBillModel latestBill = this.userBillDao.selectByUserId(model.getSupportUser());
            if(null == latestBill){
                throw new ApplicationException("账户余额不足，请充值");
            }else if(investAmt>latestBill.getBalance()){
                throw new ApplicationException("账户余额不足，请充值");
            }
        }

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
     * Description: 用户支付意向金尾款
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public Map<String, Object> createInvestByIntentionEndAmt(
			CrowdfundingSupportModel model) {
		Map<String,Object> resultMap =null;
		try{
		   this.updateInvestByIntentionEndAmt(model);
           //查询用户信息
			UserModel user = this.userDao.findByUserId(model.getSupportUser());
			CrowdfundingModel crowdfundingModel =	crowdfundingService.getByloanNo(model.getLoanNo());
            String operationId = PKGenarator.getId();

			Map<String, String> params =  getIntentionParams( model, crowdfundingModel, user,operationId);
			String result = MockUtils.transfer(params);
            String requestUrl = "";
          //添加一条事务通知数据
         	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
         	blockAsynTran.setId(operationId);
         	blockAsynTran.setParentId(model.getOrderId());  //支持编号
         	blockAsynTran.setCreateTime(new Date());
         	blockAsynTran.setUpdateTime(new Date());
         	blockAsynTran.setRequestID(params.get("requestID"));
            blockAsynTran.setType(BlockChainCore.Type.intention_retainage_pay);
         	this.blockAsynTranDao.save(blockAsynTran);
     // 保存操作数据
        trusteeshipOperationService.saveOperationModel(operationId, model.getSupportUser(),
                model.getIntentionNo(),LetvPayConstants.BusiType.invest,requestUrl,SxyPayConstants.THIRD_ID,MapUtil.mapToString(params));
   
		 resultMap = JsonHelper.getMapFromJson(result);
		if(resultMap.get("status").equals(BlockChainCore.ResultStatus.ServiceSuccess)){
			
		}
		}catch(Exception e){
			e.printStackTrace();
			if(resultMap!=null)
				resultMap =new HashMap<String, Object> ();
			resultMap.put("status", BlockChainCore.ResultStatus.FAIL);
			model.setIntentionNo(CrowdfundCoreConstants.crowdFundPayState.noPay);
			this.crowdfundingSupportDao.updateByOrderNo(model);

		}
	    return resultMap;
	}

	private void updateInvestByIntentionEndAmt(
			CrowdfundingSupportModel model) {
		model.setIntentionNo(PKGenarator.getOrderId());
		model.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.locking);
		this.crowdfundingSupportDao.updateByOrderNo(model);
	}

	/**
     * 
     * Description: 组装
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String, String> getIntentionParams(CrowdfundingSupportModel model,CrowdfundingModel crowdfundingModel,UserModel user,String operationId) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("serviceID", "transfer");
		params.put("transferNO",operationId);
		params.put("requestID",PKGenarator.getOrderId());

		params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
		params.put("sourceAddress",user.getBlockChainAddress()); //转出账户地址为平台出账户us
		params.put("sourceKey",user.getBlockChainSecretKey());
		params.put("targetAddress",crowdfundingModel.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
		double amount =Arith.round(model.getSupportAmt())-Arith.round(model.getIntentionAmt());
		if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
			amount = 0.01;
		}
		params.put("amount",String.valueOf(Arith.round(amount)));
		params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"letvPay/invest/receiveTransferIntentionS2S.html");
		return params;
	}
    /**
     * 意向金成功以后处理
     */
	public void IntentionAsynSucess(String requestId,BlockAsynTranModel model){
		String status = model.getQueryStatus();
    	 CrowdfundingSupportModel supportModel =crowdfundingSupportDao.getByOrderId(model.getParentId());
		 Map<String,Object> map = new HashMap<String,Object>();

		 map.put("intentionNo", supportModel.getIntentionNo());
		if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
			trusteeshipOperationService.updateOperationModel(map.get("intentionNo").toString(), 
                    LetvPayConstants.BusiType.invest, 
                    null, SxyPayConstants.THIRD_ID, "status","意向金尾款支付，项目编号:"+supportModel.getLoanNo());
			
			this.updateInvestIntentionEndAmt(map);
		}	else{
			//更新投资尾款失败 从区块链中间账户转账给投资人
			this.updateInvestIntentionFail(map);
		}
	}
	/**
     * 
     * Description: 意向金尾款支付
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateInvestIntentionEndAmt(Map<String, Object> resultMap) {
		String intentionNo = resultMap.get("intentionNo").toString();
		if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
			if (this.isSupportLock(intentionNo)) {
	            logger.info("请求失败,orderId被锁！" + intentionNo);
	            return;
	        }
		}
        CrowdfundingSupportModel invest = this.crowdfundingSupportDao.getByIntentionNo(intentionNo);
        try {
            if (invest == null) {
                throw new ApplicationException("订单号为" + intentionNo + "的投资单不存在");
                
            }
            if(invest.getActualPayAmt()!=null && invest.getSupportAmt()!=null&&CrowdfundCoreConstants.crowdFundPayState.payed.equals(invest.getPayState()))
	            if (Arith.round(invest.getActualPayAmt())== Arith.round(invest.getSupportAmt())
	                    || invest.getActualPayAmt().equals(invest.getSupportAmt())) {// 充值状态已经更新
	                logger.info(intentionNo + "支付已经完成！");
	                return;
	            }
            invest.setCompleteTime(new Date());
            invest.setActualPayAmt(invest.getSupportAmt());
            invest.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.payed);
            invest.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
            this.crowdfundingSupportDao.updateBySelective(invest);
            
            CrowdfundingModel loan = this.crowdfundingDao.getByloanNo(invest.getLoanNo());
            
            
          //扣除投资金额
          /*  userBillService.addBill(invest.getSupportUser(), FbdCoreConstants.userTradeType.Intention.getValue(), Arith.sub(invest.getSupportAmt(),invest.getIntentionAmt()),
            		FbdCoreConstants.tradeDirection.out.getValue(), invest.getLoanNo(), loan.getLoanName()+"_用户支付意向金尾款", null, invest.getOrderId());
            */
            //扣除投资金额
            userBillService.addInvestFreeze(loan,invest.getIntentionNo(),
            		Arith.sub(invest.getSupportAmt(), invest.getIntentionAmt()),invest.getSupportUser());
            this.sendInvestMessage(invest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysException("更新支付单失败：" + e.getMessage());
        } finally {
            LockUtil.getInstance().remove(
                    FbdCoreConstants.LOCK_RECHARGE + intentionNo);
        }
	}

	public String saveUserSupport(CrowdfundingSupportModel model) {
		String orderId = PKGenarator.getOrderId();
		model.setOrderId(orderId);
		this.addSupportOrder(model);
		return orderId;
	}

	
	
	/**
     * 
     * Description:投资失败 区块链从中间账户转账到用户账户
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateInvestFail(Map<String, Object> map) {
		try {
			
			String orderId = map.get("orderId").toString();
			CrowdfundingSupportModel invest = this.crowdfundingSupportDao.getByOrderId(orderId);
			String payState = CrowdfundCoreConstants.crowdFundPayState.noPay;
			if (invest.getPayState().equals(payState)) {// 投资状态"等待支付"
				invest.setState(CrowdfundCoreConstants.crowdFundOrderState.cancel);
                this.crowdfundingSupportDao.update(invest);
           /*     Map<String,String> params = this.getInvestFailParam(invest);
		        String requestUrl = "";
		        String result = MockUtils.transfer(requestUrl, params);
				Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
				if(!resultMap.get("state").equals("TransactionSuccess")){
					throw new ApplicationException("投资失败区块链转账失败！！");
				}*/
			}
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	
	
	/**
     * 
     * Description: 组装
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String, String> getInvestFailParam(CrowdfundingSupportModel model) {
		Map<String,String> map = new HashMap<String, String>();
		UserModel user = this.userDao.findByUserId(model.getSupportUser());
		//转账需要判断 是否为意向金支付
		if(null != model.getIntentionAmt() && model.getIntentionAmt()>0){
			//为意向金支付
		}
		
		//判断是否需要运费
		if(null != model.getTransFee() && model.getTransFee() >0){
			
		}
		
		map.put("serviceID", "");
		map.put("sourceAddress", "transfer");
		map.put("sourceKey", "transfer");
		map.put("targetAddress", "transfer");
		map.put("productCode", "transfer");
		map.put("notifyURL", "transfer");
		map.put("amt", "10");
		return map;
	}

	/**
     * 
     * Description:投资尾款失败 从区块链中间账户转账给投资人
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateInvestIntentionFail(Map<String, Object> map) {
		try {
			String intentionNo = map.get("intentionNo").toString();
			CrowdfundingSupportModel invest = this.crowdfundingSupportDao.getByIntentionNo(intentionNo);
			String payState = CrowdfundCoreConstants.crowdFundPayState.noPay;
			if (invest.getIntentionState().equals(payState)) {// 投资状态"等待支付"
				invest.setIntentionState(CrowdfundCoreConstants.crowdFundPayState.payFail);
                this.crowdfundingSupportDao.update(invest);
               /* Map<String,String> params = this.getInvestIntentionFailParam(invest);
		        String requestUrl = "";
		        String result = MockUtils.transfer(requestUrl, params);
				Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
				if(!resultMap.get("state").equals(BlockChainCore.ResultStatus.SUCCESS)){
					throw new ApplicationException("尾款投资失败区块链转账失败！！");
				}*/
				
			}
		} catch (Exception e) {
			throw new ApplicationException("尾款投资失败区块链转账失败！！");
		}
	}
	
	/**
     * 
     * Description: 组装
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	private Map<String, String> getInvestIntentionFailParam(CrowdfundingSupportModel model) {
		Map<String,String> map = new HashMap<String, String>();
		UserModel user = this.userDao.findByUserId(model.getSupportUser());
		map.put("serviceID", "");
		map.put("sourceAddress", "transfer");
		map.put("sourceKey", "transfer");
		map.put("targetAddress", "transfer");
		map.put("productCode", "transfer");
		map.put("notifyURL", "transfer");
		map.put("amt", Arith.format(Arith.sub(model.getSupportAmt(),model.getIntentionAmt())));
		return map;
	}

	/**
     * 
     * Description: 意向金支付前验证
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void checkBeforeIntentionSupport(CrowdfundingSupportModel model) {
		if(model.getCompleteTime()!=null){
			Integer yxjwyDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode());
			Date endDate = DateUtil.getDate(model.getCompleteTime(),yxjwyDay);
			//如果当前时间 大于 尾款支付日期 则不能支付
			if(DateUtil.compareDateTime(new Date(),endDate,"yyyyMMddhhmmss")>0){
				throw new ApplicationException("已超过意向金尾款支付日期,正在违约退款中！");
			}
		}
	}
	/**
	 * 检测意向金支付
	 * @param model
	 * @return
	 */
	public boolean checkBeforeIntentionPay(CrowdfundingSupportModel model) {
		Integer yxjwyDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode()==null?"5":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY).getCode());
		Date endDate = DateUtil.getDate(model.getCompleteTime(),yxjwyDay);
		//如果当前时间 大于 尾款支付日期 则不能支付
		if(DateUtil.compareDateTime(new Date(),endDate,"yyyyMMddhhmmss")>0){
			return false;
		}
		return true;
	}
	/**
	 * 判断是否可以使用意向金支付
	 */
	public boolean checkIsIntentionPay(CrowdfundingModel model) {
		String day = businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode();
		Integer yxjwyDay = Integer.valueOf(day==null?"5":day);
		Date endDate = DateUtil.subDate(model.getFundEndTime(),yxjwyDay);
		//如果当前时间 大于 尾款支付日期 则不能支付
		if(DateUtil.compareDateTime(new Date(),endDate,"yyyyMMddhhmmss")>0){
			return false;
		}
		return true;
	}
	/**
	 * 检测项目是否在区块链上开户，如果未开户需要从新开户
	 * @param loanNo
	 */
	public void checkLoanAccountForBlockChain(String loanNo){
		
		 //查询项目信息
		 CrowdfundingModel model = this.crowdfundingDao.getByloanNo(loanNo);
		 if(!"2".equals(model.getBlockChainState())){
			 try{     
//	            throw new ApplicationException("项目方在区块链开户失败，请联系客服！");
				 logger.info("----------------->项目账户异常，请联系客服！");
				 throw new ApplicationException("项目账户异常，请联系客服！");
	         }catch(Exception e){
	        	 throw new ApplicationException(e.getMessage());
	         }
		 }
	}
	
	
}
