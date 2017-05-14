/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainCrowdfundingServiceImpl.java 
 *
 * Created: [2016-9-26 下午3:46:56] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.blockChain.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import javax.annotation.Resource;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.app.blockChain.service.BlockChainCore.UserAuth;
import com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockChain.util.BlockChainStringUtil;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferExtendDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingAttentionDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingProgressDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.quartz.QuartzJobConstants;
import com.fbd.core.common.quartz.util.QuartzUtils;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.spring.SpringUtil;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service(value="blockChainCrowdfundingService")
public class BlockChainCrowdfundingServiceImpl implements IBlockChainCrowdfundingService{

    private static final Logger logger = LoggerFactory.getLogger(BlockChainCrowdfundingServiceImpl.class);
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private ICrowdfundingProgressDao crowdfundingProgressDao;
//    @Resource
//    private StdScheduler scheduler;
    @Resource
    private IContractTemplateDao contractTemplateDao;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private ICrowdfundProductTransferDao crowdfundProductTransferDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private ICrowdfundTransferExtendDao crowdfundTransferExtendDao;
    @Resource
    private ICrowdfundingAttentionDao crowdfundingAttentionDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private ICrowdfundingBackSetDao crowdfundingBackSetDao;
    @Resource
    private ICrowdfundUserPrizeDao crowdfundUserPrizeDao;
    
    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService#updateCrowdFundAfterRelease(com.fbd.core.app.crowdfunding.model.CrowdfundingModel)
     */
    @Override
    public void updateCrowdFundAfterRelease(CrowdfundingModel model) {
        // TODO Auto-generated method stub
        try{
            CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(model.getLoanNo());
            if(crowdfund.getChargeFee() == null){
                throw new ApplicationException("平台收取服务费未设置");
            }
            crowdfund.setLoanState(CrowdfundCoreConstants.crowdFundingState.funding.getValue());
            crowdfund.setReleaseTime(new Date());
            //根据发布时间计算截止时间
            Date financeEndDate = DateUtil.getDate(crowdfund.getReleaseTime(), crowdfund.getFundDays());
            crowdfund.setFundEndTime(financeEndDate);
            
            if(FbdCoreConstants.BLOCK_CHAIN_LOAN_IS_MOCK){
                crowdfund.setBlockChainAddress("rfigfSR4Jy2T1aVgTcE5NM7EkQryZHJFmQ");
                crowdfund.setBlockChainSecretKey("ssAeXZKnKFPy7qXY2MdTzdVm3PNUb");
                crowdfund.setBlockChainState("2");
            }
            this.crowdfundingDao.update(crowdfund);
            
            //插入一条项目进展
            Map<String,String> params = new HashMap<String,String>();
            String loanUser = crowdfund.getLoanUser();
            Calendar cal = Calendar.getInstance();
            cal.setTime(crowdfund.getFundEndTime());
            String fundEndTime = cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+cal.get(Calendar.DATE)+"日";
            UserModel user = this.userDao.findByUserId(loanUser);
            
            
            params.put("loanUser",loanUser.substring(0, 2)+"***"+loanUser.substring(loanUser.length()-3));
            params.put("loanUserName",user.getNickName());
            params.put("loanName",crowdfund.getLoanName());
            params.put("fundEndTime",fundEndTime);
            params.put("fundAmt",Arith.format(crowdfund.getFundAmt()));
            crowdfundingProgressDao.saveProgress(CrowdfundCoreConstants.NODE_CROWDFUND_PROGRESS_RELEASE, 
                    model.getLoanNo(), params);
            
            //拷贝合同
            String contractTempNo = crowdfund.getContractTplNo();
            contractTemplateDao.copyContractTemplate(model.getLoanNo(), contractTempNo);
            
            
            //增加筹资结束调度
            String loanNo = crowdfund.getLoanNo();
            String cronExpression = DateUtil.getSchedulerCronExpression(financeEndDate);
            Map<String, String> parameter = new HashMap<String, String>();
            parameter.put(QuartzJobConstants.PARAM_CROWDFUND_FINANCE_END,loanNo);
//            QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_CROWDFUND_FINANCE_END+"_"+loanNo, 
//                    QuartzJobConstants.CLASS_CROWDFUND_FINANCE_END, parameter, 
//                    QuartzJobConstants.TRIGGER_CROWDFUND_FINANCE_END+"_"+loanNo, cronExpression, 
//                    QuartzJobConstants.DES_CROWDFUND_FINANCE_END);
//            
            //发送项目消息(股权和产品的在预热的时候发送)
            if("public".equals(model.getLoanType())){
                this.sendReleaseMessage(model);
            }
            //查询收藏过项目的用户，给用户发送信息
            if("product".equals(model.getLoanType())){
                CrowdfundingAttentionModel attentionModel = new CrowdfundingAttentionModel();
                attentionModel.setLoanNo(loanNo);
                List<Map<String,Object>> aList = this.crowdfundingAttentionDao.getList(attentionModel);
                for(Map<String,Object> attention:aList){
                    String userId = attention.get("attentionUser").toString(); 
                    this.sendAttentionMessage(model, userId);
                }
            }            
            
            
        }catch(Exception e){
            throw new ApplicationException("发布众筹项目失败："+e.getMessage());
        }
    }
    

        /**
    * 发送项目时给收藏过该项目的用户发送短信发布信息
    * @param model
    * @param msg
    */
    private void sendAttentionMessage(CrowdfundingModel model,String userId){
        Map<String, String> params = new HashMap<String,String>();
        try{
            logger.info("发送项目发布收藏用户手机短信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_ATTENTION_MOBILE;
            params.put("loanName",model.getLoanName());
            params.put("time", DateUtil.dateTime2Str(new Date(), null));
            SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
            logger.info("发送项目发布收藏用户手机短信结束");
        }catch(Exception e){
            logger.error("发送项目发布收藏用户手机短信失败，"+e.getMessage());
        }
        try{
            logger.info("发送项目发布收藏用户站内信开始");
            String nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_ATTENTION_MSG;
            SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
            logger.info("发送项目发布收藏用户站内信结束");
        }catch(Exception e){
            logger.error("发送项目发布收藏用户站内信失败，"+e.getMessage());
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
 
    @Override
    public void updateCrowdFundAfterPreheat(String loanNo, Date preheatEndTime) {
        // TODO Auto-generated method stub
        CrowdfundingModel crowdfund = this.crowdfundingDao.getByloanNo(loanNo);
        crowdfund.setLoanState(CrowdfundCoreConstants.crowdFundingState.preheat.getValue());
        //预热截止时间
        crowdfund.setPreheatEndTime(preheatEndTime);
        if(FbdCoreConstants.BLOCK_CHAIN_LOAN_IS_MOCK && "stock".equals(crowdfund.getLoanType())){

            crowdfund.setBlockChainAddress("rfigfSR4Jy2T1aVgTcE5NM7EkQryZHJFmQ");
            crowdfund.setBlockChainSecretKey("ssAeXZKnKFPy7qXY2MdTzdVm3PNUb");
            crowdfund.setBlockChainState(UserAuth.END);
            crowdfund.setPreheatTime(new Date());  //设置预热时间
        }
        if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
            crowdfund.setPreheatTime(new Date());  //设置预热时间
        }
        this.crowdfundingDao.updateBySelective(crowdfund);
    }
    
    
    
    
    
    @Override
    public void updateTimmerProductTransferAuditSuccess(
            Map<String, String> result) {
        // TODO Auto-generated method stub
        String transactionID =  result.get(BlockChainCore.TRANSACTION_ID).toString();
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message=result.get(BlockChainCore.MESSAGE);
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setTranId(transactionID);
        blockAsynTran.setUpdateTime(new Date());
        blockAsynTran.setQueryRequestID(PKGenarator.getId());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        if(ResultStatus.SUCCESS.equals(status)){
            result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
            Timer timer=new Timer();
            timer.schedule(new TransSuccessTask(timer,result), 2000, 2000);   
        }
    }

    @Override
    public void updateTimmerProductTransferAuditFail(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transactionID =  result.get(BlockChainCore.TRANSACTION_ID).toString();
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message=result.get(BlockChainCore.MESSAGE);
        
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setTranId(transactionID);
        blockAsynTran.setUpdateTime(new Date());
        blockAsynTran.setQueryRequestID(PKGenarator.getId());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        
        if(ResultStatus.SUCCESS.equals(status)){
            result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
            Timer timer=new Timer();
            timer.schedule(new TransFailTask(timer,result), 2000, 2000);
        }
    }
    static class TransSuccessTask extends java.util.TimerTask{
        private Map<String,String> valueMap;
        private Timer timer;
        
        public TransSuccessTask(Timer timer,Map<String,String> resultMap){
            this.valueMap=resultMap;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
                logger.info("-----------------转让通过(事物处理)-----------------------");
                String transactionID=valueMap.get(BlockChainCore.TRANSACTION_ID);
                String requestId=valueMap.get(BlockChainCore.QUERY_REQUEST_ID);
                
                IBlockChainQueryService service=(IBlockChainQueryService) SpringUtil.getBean("blockChainQueryService");
                service.transactionQuery(transactionID,requestId);
                    IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                    BlockAsynTranModel model=dao.findByQueryRequestId(requestId);
                    if(model!=null){
                       String queryStatus=model.getQueryStatus();
                       logger.info("------------queryStatus:"+queryStatus);
                       if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                           IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                           opService.updateProductTransferAuditSuccess(valueMap);
                           logger.info("------------------Success-------------------------");
                           timer.cancel();  
                           System.gc();
                       }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
/*                           IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                           opService.updateProductTransferErrorInit(valueMap);*/
                    	   logger.info("------------------Error-------------------------");
                           //timer.cancel();    
                       }
                    }
                //}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    static class TransFeeSuccessTask extends java.util.TimerTask{
        private Map<String,String> valueMap;
        private Timer timer;
        
        public TransFeeSuccessTask(Timer timer,Map<String,String> resultMap){
            this.valueMap=resultMap;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
                logger.info("-----------------转让通过-转让费(事物处理)-----------------------");
                String transactionID=valueMap.get(BlockChainCore.TRANSACTION_ID);
                String requestId=valueMap.get(BlockChainCore.QUERY_REQUEST_ID);
                
                IBlockChainQueryService service=(IBlockChainQueryService) SpringUtil.getBean("blockChainQueryService");
                service.transactionQuery(transactionID,requestId);
/*                String result=service.transactionQuery(transactionID,requestId);
                @SuppressWarnings("unchecked")
                Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
                String resultStatus = resultMap.get(BlockChainCore.STATUS).toString();
                if(ResultStatus.ServiceSuccess.equals(resultStatus)){*/
                    IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                    BlockAsynTranModel model=dao.findByQueryRequestId(requestId);
                    if(model!=null){
                       String queryStatus=model.getQueryStatus();
                       logger.info("------------queryStatus:"+queryStatus);
                       if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                           IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                           opService.updateProductTransferSystemAuditSuccess(valueMap);
                         
                           logger.info("------------------Success-------------------------");
                           timer.cancel();      
                       }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
/*                           IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                           opService.updateProductTransferErrorInit(valueMap);*/
                    	   logger.info("------------------Error-------------------------");
                           //timer.cancel();    
                       }
                    }
                //}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void updateProductTransferAuditSuccess(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);

        //更新投资为已转让
        CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(transferModel.getSupportNo());
        supportModel.setIsTransfer("1");
        this.crowdfundingSupportDao.update(supportModel);
        
        //为购买用户新增投资记录
        //this.saveSupport(transferModel,supportModel.getTransFee());
        CrowdfundingSupportModel modle = new CrowdfundingSupportModel();
        modle.setId(PKGenarator.getId());
        modle.setSupportAmt(transferModel.getTransferAmt());
        modle.setActualPayAmt(Arith.add(transferModel.getTransferAmt(),transferModel.getTransFee()));
        //前台购买成功会默认生成一个没关联的订单编号
        modle.setOrderId(transferModel.getBuySupportNo());
        modle.setSupportUser(transferModel.getTransferUser());
        modle.setBackNo(transferModel.getBackNo());
        modle.setLoanNo(transferModel.getLoanNo());
        modle.setSupportTime(new Date());
        modle.setCompleteTime(new Date());
        modle.setTransFee(supportModel.getTransFee());
       // modle.setSupportClass(CrowdfundCoreConstants.supportClass.transferSupport.getValue());
        CrowdfundTransferExtendModel queryEModel=new CrowdfundTransferExtendModel();
        queryEModel.setTransferNo(transferModel.getTransferNo());
        queryEModel.setIsUse("y");
        List<CrowdfundTransferExtendModel> extendList=crowdfundTransferExtendDao.findByModel(queryEModel);
        if(extendList==null||extendList.size()>1){
            throw new ApplicationException(transferModel.getTransferNo()+":转让扩展数据异常");
        }
        CrowdfundTransferExtendModel extendModel=extendList.get(0);
        modle.setSupportUser(extendModel.getUserId());
        modle.setRemark(extendModel.getSupportRemark());
        modle.setContent(extendModel.getSupportContent());
        modle.setPostAddressNo(extendModel.getPostAddressNo());
        
        //add start
        modle.setState("lended");
        modle.setPayState("payed");
        modle.setIsTransfer("0");
        //add end
        this.crowdfundingSupportDao.save(modle);
        
        //扣除购买人冻结金额
        //1 购买人出账(扣除冻结金额)
        this.userBillService.addByUserTransferBill(transferModel);
        
        //增加出售转让账单
        this.userBillService.addBill(transferModel.getTransferUser(),
                FbdCoreConstants.userTradeType.saleTransfer.getValue(),
                Arith.add(transferModel.getTransferAmt(),transferModel.getTransFee()), 
                FbdCoreConstants.tradeDirection.in.getValue(), 
                transferModel.getLoanNo(), 
                "用户产品转让成功进账",
                null, 
                transferModel.getTransferNo());
   
    
    
    }
    
    
    static class TransFailTask extends java.util.TimerTask{
        private Map<String,String> valueMap;
        private Timer timer;
        
        public TransFailTask(Timer timer,Map<String,String> resultMap){
            this.valueMap=resultMap;
            this.timer=timer;
        }
        
        @Override
        public void run() {
            try{
                logger.info("-----------------转让失败(事物处理)-----------------------");
                String transactionID=valueMap.get(BlockChainCore.TRANSACTION_ID);
                String requestId=valueMap.get(BlockChainCore.QUERY_REQUEST_ID);
                
                IBlockChainQueryService service=(IBlockChainQueryService) SpringUtil.getBean("blockChainQueryService");
                service.transactionQuery(transactionID,requestId);
                IBlockAsynTranDao dao=(IBlockAsynTranDao) SpringUtil.getBean("blockAsynTranDao");
                BlockAsynTranModel model=dao.findByQueryRequestId(requestId);
                if(model!=null){
                    String queryStatus=model.getQueryStatus();
                    logger.info("------------queryStatus:"+queryStatus);
                    if(ResultStatus.TRANSACTION_SUCCESS.equals(queryStatus)){
                        IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                        opService.updateProductTransferAuditFail(valueMap);
                        logger.info("------------------Success-------------------------");
                        timer.cancel();
                    }else if(ResultStatus.TRANSACTION_FAIL.equals(queryStatus)){
/*                            IBlockChainCrowdfundingService opService=(IBlockChainCrowdfundingService) SpringUtil.getBean("blockChainCrowdfundingService");
                            opService.updateProductTransferErrorInit(valueMap);*/
                    	logger.info("------------------Error-------------------------");
                        //timer.cancel();   
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void updateProductTransferAuditFail(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
        this.userBillService.addByUserTransferUnfreeze(transferModel);
        transferModel.setTransferAuditState(FbdCoreConstants.transferAuditState.refuse);
        this.crowdfundProductTransferDao.update(transferModel);
        this.sendProductTransferErrorMsg(result);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService#updateProductTransferErrorInit(java.util.Map)
     */
    @Override
    public void updateProductTransferErrorInit(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
        transferModel.setTransferAuditState(FbdCoreConstants.transferAuditState.auditing);
        this.crowdfundProductTransferDao.update(transferModel);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService#saveUserPrize(java.lang.String)
     */
    @Override
    public void saveUserPrize(String loanNo) {
        // TODO Auto-generated method stub
        CrowdfundingBackSetModel backModel=crowdfundingBackSetDao.queryIsPrizeDrawFlag(loanNo);
        if(backModel!=null){
            if(backModel.getPrizeNum()==null||backModel.getPrizeNum()<=0){
                logger.info("=============项目【"+loanNo+"】,回报编号【"+backModel.getBackNo()+"】抽奖名额必须是大于0的整数===================");
                return;
                //throw new ApplicationException("抽奖名额必须是大于0的整数");
                
            }
            if(backModel.getPrizeFullNum()==null||backModel.getPrizeFullNum()<=0){
                logger.info("=============项目【"+loanNo+"】,回报编号【"+backModel.getBackNo()+"】激活抽奖份数上限必须是大于0的整数======");
                return ;
                //throw new ApplicationException("激活抽奖份数上限必须是大于0的整数");
            }else{
                if(backModel.getPrizeFullNum()<=backModel.getPrizeNum()){
                    logger.info("=============项目【"+loanNo+"】,回报编号【"+backModel.getBackNo()+"】激活抽奖份数上限必须大于抽奖名额===================");
                    return;
                    //throw new ApplicationException("激活抽奖份数上限必须大于抽奖名额");
                }
            }
            List<CrowdfundingSupportModel> supportList = this.crowdfundingSupportDao.getPaySucessUser(loanNo,backModel.getBackNo());
            if(supportList!=null&&supportList.size()>0){
                //当投资次数大于等于激活抽奖份数上限时候激活抽奖逻辑
                if(supportList.size()>=backModel.getPrizeFullNum()){
                    crowdfundUserPrizeDao.deleteByLoanNo(loanNo);
                    int i=0;
                    for(CrowdfundingSupportModel model : supportList){
                        CrowdfundUserPrizeModel prizeModel = new CrowdfundUserPrizeModel();
                        prizeModel.setId(PKGenarator.getId());
                        prizeModel.setLoanNo(model.getLoanNo());
                        prizeModel.setPrizeUser(model.getSupportUser());
                        prizeModel.setPrizeNo(i++);
                        prizeModel.setSupportNo(model.getOrderId());
                        prizeModel.setBackNo(model.getBackNo());
                        this.crowdfundUserPrizeDao.save(prizeModel);
                    }
                    CrowdfundingModel loan=crowdfundingDao.getByloanNo(loanNo);
                    loan.setPrizeStatus("start");
                    crowdfundingDao.update(loan);
                    
/*                    logger.info("====================项目【"+loanNo+"】增加定时调度任务=========================");
                    //增加定时调度任务
                    Date financeEndDate = DateUtil.getDate(DateUtils.stringToDate(DateUtils.dateToString(new Date(), "")+" 09:31:00", "yyyy-MM-dd HH:mm:ss"), 1);
                    String cronExpression = DateUtil.getSchedulerCronExpression(financeEndDate);
                    Map<String, String> parameter = new HashMap<String, String>();
                    parameter.put(QuartzJobConstants.PARAM_CROWDFUND_PRIZE_LOAN_NO,loanNo);
                    parameter.put(QuartzJobConstants.PARAM_CROWDFUND_PRIZE_BACK_NO,backModel.getBackNo());
                    QuartzUtils.saveJob(scheduler, QuartzJobConstants.JOB_CROWDFUND_PRIZE_FINANCE_END+"_"+loanNo, 
                            QuartzJobConstants.CLASS_CROWDFUND_PRIZE_FINANCE_END, parameter, 
                            QuartzJobConstants.TRIGGER_CROWDFUND_PRIZE_FINANCE_END+"_"+loanNo, cronExpression, 
                            QuartzJobConstants.DES_CROWDFUND_PRIZE_FINANCE_END);  */   
                }     
            }else{
                logger.info("=================================================");
            }
        }else{
            logger.info("=========项目["+loanNo+"]没有可参与抽奖的回报投资==============");
        }
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService#updateProductTransferSystemAuditSuccess(java.lang.String)
     */
    @Override
    public void updateProductTransferSystemAuditSuccess(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
   
        //增加出售转让账单
        this.userBillService.addBill(transferModel.getTransferUser(),
                FbdCoreConstants.userTradeType.TRANSFER_FEE.getValue(),
                transferModel.getTransferFee(), FbdCoreConstants.tradeDirection.out.getValue(), transferModel.getLoanNo(), "用户产品转让手续费", null, transferModel.getTransferNo());
        //添加平台服务费账单
        StringBuffer str=new StringBuffer();
        str.append("项目编号:").append(transferModel.getLoanNo()).append(",从 出让人:").append(transferModel.getTransferUser()).append("获取转让手续费").append(transferModel.getTransferFee());
       this.systemBillService.addTransferFeeSystemBill(transferModel.getTransferFee(), transferModel.getTransferNo(), transferModel.getLoanNo(), str.toString());
    
    
    
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService#updateProductTransferEndSuccess(java.util.Map)
     */
    @Override
    public void updateProductTransferEndSuccess(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);        
        //如果为审核通过 
        transferModel.setTransferAuditState(FbdCoreConstants.transferAuditState.passed);
        this.crowdfundProductTransferDao.update(transferModel);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.blockChain.service.IBlockChainCrowdfundingService#updateTimmerProductTransferFeeAuditSuccess(java.util.Map)
     */
    @Override
    public void updateTimmerProductTransferFeeAuditSuccess(
            Map<String, String> result) {
        // TODO Auto-generated method stub
        String transactionID =  result.get(BlockChainCore.TRANSACTION_ID).toString();
        String status = result.get(BlockChainCore.STATUS)== null?"":result.get(BlockChainCore.STATUS).toString();
        String message=result.get(BlockChainCore.MESSAGE);
        
        String requestId=result.get(BlockChainCore.REQUEST_ID);
        
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestId);
        blockAsynTran.setStatus(status);
        blockAsynTran.setMessage(message);
        blockAsynTran.setTranId(transactionID);
        blockAsynTran.setUpdateTime(new Date());
        blockAsynTran.setQueryRequestID(PKGenarator.getId());
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        if(ResultStatus.SUCCESS.equals(status)){
            result.put(BlockChainCore.QUERY_REQUEST_ID, blockAsynTran.getQueryRequestID());
            Timer timer=new Timer();
            timer.schedule(new TransFeeSuccessTask(timer,result), 2000, 2000);   
        }
    }
 
    /**
     * 转让审核成功站内信
     */
    @Override
    public void sendProductTransferMsg(Map<String, String> result) {
        // TODO Auto-generated method stub
        
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
        
        Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(new Date(), null));
        params.put("money",Arith.format(Arith.add(transferModel.getTransferAmt(),transferModel.getTransFee())));
        CrowdfundingModel loanModel=this.crowdfundingDao.getByloanNo(transferModel.getLoanNo());
        params.put("loanNo", transferModel.getLoanNo());
        params.put("loanName", loanModel.getLoanName());
        params.put("transferNo", transferNo);
        params.put("transferFee", String.valueOf(Arith.round(transferModel.getTransferFee())));
        params.put("transferAmt", String.valueOf(Arith.round(transferModel.getTransferAmt())));
        params.put("actualAmt", String.valueOf(Arith.round(transferModel.getTransferAmt()-transferModel.getTransferFee())));
        
        logger.info("===============发送转让审核通过出让人信息=========================");
        try{
             logger.info("发送投资站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_PASSED_TRANSFER_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.NODE_TYPE_PRODUCT_TRANSFER, transferModel.getTransferUser(), params);
             logger.info("发送投资站内信结束");
        }catch(Exception e){
             logger.error("发送投资站内信失败，"+e.getMessage());
        }
        logger.info("===============发送转让审核通过购买人信息=========================");
        try{
              logger.info("发送投资站内信开始");
              String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_PASSED_BUY_MSG;
              SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.NODE_TYPE_PRODUCT_TRANSFER, transferModel.getBuyUser(), params);
              logger.info("发送投资站内信结束");
         }catch(Exception e){
              logger.error("发送投资站内信失败，"+e.getMessage());
         }
    }

 
    
    /**
     * 转让拒绝后发送站内信
     */
    @Override
    public void sendProductTransferErrorMsg(Map<String, String> result) {
        // TODO Auto-generated method stub
        String transferNo=result.get(BlockChainCore.TRANSFER_NO);
        transferNo=BlockChainStringUtil.getUniqueTransferNoDec(transferNo);
        CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
        CrowdfundingModel loanModel=this.crowdfundingDao.getByloanNo(transferModel.getLoanNo());
        CrowdfundingSupportModel supportModel = new CrowdfundingSupportModel();
        supportModel.setSupportClass("");
        crowdfundingSupportDao.updateBySelective(supportModel);
        
        Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(new Date(), null));
        params.put("money",Arith.format(Arith.add(transferModel.getTransferAmt(),transferModel.getTransFee())));
        params.put("loanNo", transferModel.getLoanNo());
        params.put("loanName", loanModel.getLoanName());
        params.put("transferNo", transferNo);
        params.put("reason", transferModel.getTransferAuditOpinion());
        
        logger.info("===============发送转让审核拒绝出让人信息=========================");
         try{
             logger.info("发送转让审核拒绝出让人站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_EFUSE_TRANSFER_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.NODE_TYPE_PRODUCT_TRANSFER, transferModel.getTransferUser(), params);
             logger.info("发送转让审核拒绝出让人站内信结束");
         }catch(Exception e){
             logger.error("发送转让审核拒绝出让人站内信失败，"+e.getMessage());
         }
         logger.info("===============发送转让审核拒绝购买人信息=========================");
         try{
             logger.info("发送转让审核拒绝购买人站内信开始");
             String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_EFUSE_BUY_MSG;
             SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.NODE_TYPE_PRODUCT_TRANSFER, transferModel.getBuyUser(), params);
             logger.info("发送转让审核拒绝购买人站内信结束");
         }catch(Exception e){
             logger.error("发送转让审核拒绝购买人站内信失败，"+e.getMessage());
         }
    }
}
