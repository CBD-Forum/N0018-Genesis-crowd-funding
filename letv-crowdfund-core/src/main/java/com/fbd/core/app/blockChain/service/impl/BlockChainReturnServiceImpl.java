/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainReturnServiceImpl.java 
 *
 * Created: [2016-8-26 下午5:17:22] by haolingfeng
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainReturnService;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.MapUtil;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="blockChainReturnService")
public class BlockChainReturnServiceImpl implements IBlockChainReturnService {
    
    private static final Logger logger = LoggerFactory.getLogger(BlockChainReturnServiceImpl.class);
    
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ISystemBillService sysBillService;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    /**
     * 意向金退款
     * Description: 
     * @param 
     * @return void
     * Create Date: 2016-8-26 下午5:16:54
     */
    @Override
    public void intentionPayReturnTransfer(String orderId) {
        
        CrowdfundingSupportModel support = this.crowdfundingSupportDao.getByOrderId(orderId);
        if(support!=null){
            //如果已经支付或者订单已取消则不进行取消调度
            if(CrowdfundCoreConstants.crowdFundPayState.payed.equals(support.getPayState())
                    ||CrowdfundCoreConstants.crowdFundOrderState.cancel.equals(support.getState())){
                logger.info("=================支付或者订单已取消则不进行取消调度=====================");
                return ;
            }
            double ratio =0.0;
            BusinessConfigModel config = this.businessConfigDao.getBusConfigByDisplayName("IntentionFeeReturnPlatformRatio");
            if(config!=null){
                ratio = Double.parseDouble(config.getCode());
            }
            double actualPayAmt = support.getActualPayAmt();
            double platformAmt = actualPayAmt * ratio;  //平台收取罚金
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            //查询项目信息
            CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(support.getLoanNo());
            double returnUserFee  = Arith.round(actualPayAmt - platformAmt);
            if(returnUserFee>0){
                //查询用户
                UserModel user = this.userDao.findByUserId(support.getSupportUser());
                //给用户退还预付金
                Map<String,String> userParams = new HashMap<String,String>();
                String requestId = PKGenarator.getOrderId();
                userParams.put("serviceID", "transfer");
                userParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
                userParams.put("sourceAddress",crowd.getBlockChainAddress()); //转出账户地址
                userParams.put("sourceKey", crowd.getBlockChainSecretKey());
                userParams.put("targetAddress",user.getBlockChainAddress());
                double amount =Arith.round(returnUserFee);
                if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
                    amount = 0.01;
                }
                userParams.put("amount",String.valueOf(amount));
                userParams.put("transferNO",PKGenarator.getId());
                userParams.put("targetAddress",user.getBlockChainAddress());
                userParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"intentionReturn/returnS2S.html");
                userParams.put("requestID",requestId);
                //添加一条事务通知数据
                BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(support.getOrderId());  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setUpdateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.intention_user_return);
               // blockAsynTran.setStatus(userState);
                blockAsynTran.setRequestID(requestId);
                blockAsynTran.setRequestData(MapUtil.mapToString(userParams));
                this.blockAsynTranDao.save(blockAsynTran);
                logger.info("================>意向金退款用户退还资金转账请求参数："+userParams);
                String userResultStr = MockUtils.transfer(userParams);
                logger.info("================>意向金退款用户退还资金转账相应参数："+userResultStr);
                @SuppressWarnings("unchecked")
                Map<String,Object> userResultMap = JsonHelper.getMapFromJson(userResultStr);
                String userState = userResultMap.get("status").toString();
                if(BlockChainCore.ResultStatus.ServiceSuccess.equals(userState)){ //转账成功
                    logger.info("==============意向金退款平台罚金【转账划款平台罚金】提交成功=============================");
                }else{
                    String message = userResultMap.get("status")==null?"":userResultMap.get("message").toString();
                       logger.info("意向金退款平台罚金【转账划款平台罚金】失败-【"+message+"】！");
                }
            }
           
            if(platformAmt>0){
                //给平台收款账户转账退款罚金
                Map<String,String> platformParams = new HashMap<String,String>();
                String requestID = PKGenarator.getOrderId();
                platformParams.put("serviceID", "transfer");
                platformParams.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
                platformParams.put("sourceAddress",crowd.getBlockChainAddress()); //转出账户地址
                platformParams.put("sourceKey", crowd.getBlockChainSecretKey());
                platformParams.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_INCOME_ACCOUNT);
                platformParams.put("requestID",requestID);
                double amount =Arith.round(platformAmt);
                if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
                    amount = 0.01;
                }
                platformParams.put("amount",String.valueOf(amount));
                platformParams.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"intentionReturn/returnS2S.html");
                platformParams.put("transferNO",PKGenarator.getId());
                //添加一条事务通知数据
                BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
                blockAsynTran.setId(PKGenarator.getId());
                blockAsynTran.setParentId(support.getOrderId());  //支持编号
                blockAsynTran.setCreateTime(new Date());
                blockAsynTran.setUpdateTime(new Date());
                blockAsynTran.setType(BlockChainCore.Type.intention_system_return_fee);
                blockAsynTran.setRequestID(requestID);
                
                this.blockAsynTranDao.save(blockAsynTran);
                
                logger.info("================>意向金退款平台罚金转账请求参数："+platformParams);
                String platformResultStr = MockUtils.transfer(platformParams);
                logger.info("================>意向金退款平台罚金转账相应参数："+platformResultStr);
                @SuppressWarnings("unchecked")
                Map<String,Object> platformResultMap = JsonHelper.getMapFromJson(platformResultStr);
                String platformState = platformResultMap.get("status").toString();
                if(BlockChainCore.ResultStatus.ServiceSuccess.equals(platformState)){ //转账成功
                    logger.info("意向金退款【转账划款平台罚款】提交成功！");
                }else{
                    String message = platformResultMap.get("status")==null?"":platformResultMap.get("message").toString();
                       logger.info("意向金退款【转账划款平台罚款】失败-【"+message+"】！");
                    throw new ApplicationException(message);   
                }
            }
        
            
        }
    }
    //退款成功以后的处理
    public void returnBackSuccess( String requestId,Timer mytimer){
        //查询当前事务状态
        BlockAsynTranModel model = blockAsynTranDao.findByRequestId(requestId);
        CrowdfundingSupportModel support = crowdfundingSupportDao.getByOrderId(model.getParentId());
        model.setStatus(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS);
        double ratio =0;
        BusinessConfigModel config = this.businessConfigDao.getBusConfigByDisplayName("IntentionFeeReturnPlatformRatio");
        if(config!=null){
            ratio = Double.parseDouble(config.getCode());
        }
        double actualPayAmt = support.getActualPayAmt();
        double platformAmt = actualPayAmt * ratio;
        //查询项目信息
        CrowdfundingModel crowd = this.crowdfundingDao.getByloanNo(support.getLoanNo());
        //如果查询转账事务为事务成功，处理用户数据
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(model.getQueryStatus())){
            double returnUserFee  = Arith.round(actualPayAmt - platformAmt);
            
            String type = model.getType();
              //用户收费账单
            if(BlockChainCore.Type.intention_user_return.equals(type)){ //如果当前转账任务为用户退款任务，
                  logger.info("============>意向金退了转账成功，添加用户退款账单=========");
                  userBillService.addBill(crowd.getLoanUser(), 
                    CrowdfundCoreConstants.userTradeType.intentionPayReturnFee.getValue(), 
                    returnUserFee, 
                    FbdCoreConstants.tradeDirection.in.getValue(), 
                    support.getOrderId(),
                    crowd.getLoanName(),
                    "项目【"+crowd.getLoanName()+"】投资【"+support.getOrderId()+"】意向金退款["+returnUserFee+"],平台收取罚金【"+platformAmt+"】原金额【"+actualPayAmt+"】", 
                    crowd.getLoanNo());
             }else{
                 //平台收费账单
                 logger.info("============>意向金退了转账成功，添加平台罚金账单=========");
                 sysBillService.addBill(CrowdfundCoreConstants.systemTradeType.intentionPayReturnFee.getValue(), 
                     Arith.round(platformAmt), 
                     FbdCoreConstants.tradeDirection.in.getValue(), 
                     support.getOrderId(), "项目["+crowd.getLoanName()+"]投资支持单号【"+support.getOrderId()+"】意向金到期平台收取罚金["+platformAmt+"]",  support.getOrderId());
             }
             //更新退款数据
             this.intentionReturnSuccessAfter(support, crowd,type,requestId);
              //关闭调度任务
             if(mytimer!=null){
                 mytimer.cancel();
                 System.gc();
             }
        }else if(BlockChainCore.ResultStatus.TRANSACTION_FAIL.equalsIgnoreCase(model.getQueryStatus())){
            mytimer.cancel();
            System.gc();
        }
    }
    
    
    /**
     * 意向金退款成功后续操作
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-11-21 下午6:07:37
     */
    public void intentionReturnSuccessAfter(CrowdfundingSupportModel support,CrowdfundingModel crowd,String type,String requestId){

        BlockAsynTranModel model  = new BlockAsynTranModel(); 
        model.setParentId(support.getOrderId());
        if(BlockChainCore.Type.intention_user_return.equals(type)){ //如果当前转账任务为用户退款任务
            model.setType(BlockChainCore.Type.intention_system_return_fee);
        }else{
            model.setType(BlockChainCore.Type.intention_user_return);
        }
        BlockAsynTranModel qmodel = this.blockAsynTranDao.selectByModel(model);
        String queryState = qmodel.getQueryStatus();
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(queryState)){
            //更新当前订单状态
//          support.setState(CrowdfundCoreConstants.crowdFundOrderState.returnback);
            support.setState(CrowdfundCoreConstants.crowdFundOrderState.cancel);  //设置订单状态为取消
            support.setIntentionState(CrowdfundCoreConstants.crowdFundOrderState.returnback); //意向金状态为退款取消
            crowdfundingSupportDao.update(support);
            //更新项目
            crowd.setApproveAmt(crowd.getApproveAmt()-support.getSupportAmt());
            this.crowdfundingDao.updateByLoanNo(crowd);
            
            //给用户发送站内信
        }
        
    }

}
