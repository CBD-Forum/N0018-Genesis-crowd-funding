/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: DailyIncomeJobServiceImpl.java 
 *
 * Created: [2016-8-31 上午10:30:23] by haolingfeng
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

package com.fbd.core.app.crowdfunding.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundDailyIncomeDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundDailyIncomeModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.service.IDailyIncomeJobService;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
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
 * Description: 每日收益结算
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Service
public class DailyIncomeJobServiceImpl implements IDailyIncomeJobService {

    
    @Resource
    ICrowdfundingSupportDao crowdfundingSupportDao ;
    
    @Resource
    IBusinessConfigDao businessConfigDao ;
    
    @Resource
    private ICrowdfundDailyIncomeDao crowdfundDailyIncomeDao ;
    
   
    
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    
    @Resource
    private IUserDao userDao;

    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ISystemBillService systemBillService;
    
    
    /**
     * Description: 每日收益
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-31 上午10:33:19
     */
    public void sendDailyIncome() {
        //查询已支付的为放款的投资
        List<CrowdfundingSupportModel> supportList = this.crowdfundingSupportDao.getDailyIncomeSupportList();
        //查询收益利率
        Double incomeRate = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.DAILY_INCOME_RATE).getCode()==null?"0.05":businessConfigDao.getBusConfig(CrowdfundCoreConstants.DAILY_INCOME_RATE).getCode());
       for(CrowdfundingSupportModel model : supportList){
            try {
              //每日收益 = 投资金额*收益利率从
                double dailyIncomeAmt = Arith.mul(incomeRate,model.getSupportAmt());
                if(!FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
                //保存用户每日收益
                this.saveDailyIncom(dailyIncomeAmt,model,incomeRate);
                }else{
                    try{
                        DailyIncomeTransaction( dailyIncomeAmt,
                             model, incomeRate,PKGenarator.getId());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
      }
        
    }
    /**
     * 每日收益异步回调
     * Description: 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月10日 下午2:56:31
     */
    public void DailyIncomeSuccess(String requestId){
        BlockAsynTranModel blockModel = blockAsynTranDao.findByRequestId(requestId);
         //查询收益利率
        Double incomeRate = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.DAILY_INCOME_RATE).getCode()==null?"0.05":businessConfigDao.getBusConfig(CrowdfundCoreConstants.DAILY_INCOME_RATE).getCode());
        CrowdfundingSupportModel model =crowdfundingSupportDao.getByOrderId(blockModel.getParentId());
           try {
               //每日收益 = 投资金额*收益利率从
                 double dailyIncomeAmt = Arith.mul(incomeRate,model.getSupportAmt());
                
                 try{
                     DailyIncomeTransaction( dailyIncomeAmt,
                          model, incomeRate,requestId);
                 }catch(Exception e){
                     e.printStackTrace();
                 }
                
             } catch (Exception e) {
                 e.printStackTrace();
             }
       
    }
    public void DailyIncomeTransaction(double dailyIncomeAmt,
            CrowdfundingSupportModel supportModel,double incomeRate,String requestId){
       
        trusteeshipOperationService.updateOperationModel(requestId, 
                LetvPayConstants.BusiType.refund_interest, 
                null, SxyPayConstants.THIRD_ID, "passed", "每日收益发放成功，订单号是:"+supportModel.getOrderId());
        CrowdfundDailyIncomeModel model = new CrowdfundDailyIncomeModel();
        model.setId(PKGenarator.getId());
        model.setIncomeNo(PKGenarator.getOrderId());
        model.setLoanNo(supportModel.getLoanNo());
        model.setSupportNo(supportModel.getOrderId());
        model.setIncomeUser(supportModel.getSupportUser());
        model.setSupportAmt(supportModel.getSupportAmt());
        model.setIncomeAmt(dailyIncomeAmt);
        model.setIncomeRate(incomeRate);
        model.setIncomeTime(new Date());
      this.crowdfundDailyIncomeDao.saveCrowdModel(model);

      //用户进账
        this.userBillService.addBill(supportModel.getSupportUser(),
                FbdCoreConstants.userTradeType.dailyIncome.getValue(), 
                dailyIncomeAmt, FbdCoreConstants.tradeDirection.in.getValue(), model.getLoanNo(),
                "项目编号:"+supportModel.getLoanNo()
                +  "发放每日收益", null, model.getIncomeNo());
      //平台出账
        this.systemBillService.addBill(FbdCoreConstants.systemTradeType.dailyIncome.getValue(),
                dailyIncomeAmt, FbdCoreConstants.tradeDirection.out.getValue(), model.getLoanNo(),
                "项目编号:"+supportModel.getLoanNo()
               +"发放每日收益", model.getIncomeNo());
    }
    /**
     * Description: 保存用户每日收益
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-31 上午11:23:04
     */
    
    private void saveDailyIncom(double dailyIncomeAmt,
            CrowdfundingSupportModel supportModel,double incomeRate) {
      
      //查询用户信息
        UserModel user = this.userDao.findByUserId(supportModel.getSupportUser());
        Map<String,String> params = new HashMap<String, String>();
        params.put("serviceID", "transfer");
        params.put("transferNO",supportModel.getOrderId());
        String operationId = PKGenarator.getId();

        params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        params.put("sourceAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_ACCOUNT); //转出账户地址为平台出账户
        params.put("sourceKey",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_OUT_KEY);
        params.put("targetAddress",user.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
        params.put("requestID",operationId);  

        BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
        blockAsynTran.setParentId(supportModel.getOrderId());
        blockAsynTran.setId(PKGenarator.getId());
        blockAsynTran.setRequestID(params.get("requestID"));
        blockAsynTran.setType(BlockChainCore.Type.INTEREST);
        blockAsynTran.setCreateTime(new Date());
       // blockAsynTran.setStatus(status);
        blockAsynTranDao.save(blockAsynTran);
        if(Boolean.parseBoolean(FbdCoreConstants.isClientEnvironment)){
            dailyIncomeAmt = 0.01;
        }
        params.put("amount",String.valueOf(dailyIncomeAmt));
        params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"crowdfunding/interestTransferS2S.html");
        String requestUrl = "";
        //保存操作数据
        trusteeshipOperationService.saveOperationModel(operationId, supportModel.getSupportUser(),
                operationId,LetvPayConstants.BusiType.refund_interest,requestUrl,SxyPayConstants.THIRD_ID,MapUtil.mapToString(params));
      
        String result = MockUtils.transfer(params);
        Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
        String status = resultMap.get("status").toString();
        String message = resultMap.get("message").toString();
        if(BlockChainCore.ResultStatus.ServiceSuccess.equals(resultMap.get("status"))){
     
        }else{
            throw new ApplicationException(resultMap.get("message").toString());
        }
        
    }
    
    
}
