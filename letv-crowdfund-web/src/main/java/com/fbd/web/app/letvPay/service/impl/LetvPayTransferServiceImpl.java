package com.fbd.web.app.letvPay.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.core.app.bill.dao.IUserBillDao;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferExtendDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.sxyPay.common.SxyPayConstants.SyxParam;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.SysException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.LockUtil;
import com.fbd.core.util.MapUtil;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.web.app.blockChain.service.IBlockChainAsynTranscationService;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.letvPay.service.ILetvPayTransferService;

@Service("letvPayTransferService")
public class LetvPayTransferServiceImpl implements ILetvPayTransferService{
	private static final Logger logger = LoggerFactory
            .getLogger(LetvPayTransferServiceImpl.class);
	@Resource
	private ICrowdfundProductTransferDao crowdfundProductTransferDao ;
	
	@Resource
	private ICrowdfundingSupportDao crowdfundingSupportDao ;
	@Resource
	private ICrowdfundingBackSetDao crowdfundingBackSetDao;
	@Resource
	private ICrowdfundingDao crowdfundingDao;
	@Resource
	private IUserDao userDao;
	
	@Resource
	private IUserBillService userBillService;
	
	@Resource
	private IUserBillDao userBillDao;
	@Resource
	private ICrowdfundTransferExtendDao crowdfundTransferExtendDao;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
	@Resource
	private ITrusteeshipOperationService trusteeshipOperationService;
	@Resource
	private IBlockChainAsynTranscationService blockChainAsynTransactionService;
	/**
     * 
     * Description: 购买转让商品前验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void checkBeforeBuyTransfer(CrowdfundProductTransferModel model) {
		if(StringUtils.isEmpty(model.getTransferNo())){
			throw new ApplicationException("转让编号不能为空");
		}
		
		if(StringUtils.isEmpty(model.getTransferExtendId())){
			//throw new ApplicationException("转让扩展id不能为空");
			logger.info("=========转让扩展id不能为空===============");
			throw new ApplicationException("参数异常，请联系客服！");
		}
		if(StringUtils.isEmpty(model.getBuyUser())){
			throw new ApplicationException("购买人不能为空");
		}
		CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(model.getTransferNo());
		if (!transferModel.getTransferState().equals(
                CrowdfundCoreConstants.transferStateFbd.transfering.getValue())) {
            throw new ApplicationException("对不起,没有转让中项目");
        }
		CrowdfundTransferExtendModel extendModel=crowdfundTransferExtendDao.selectByPrimaryKey(model.getTransferExtendId());
		if(extendModel==null){
			throw new ApplicationException("转让扩展表数据异常");
		}
		//验证账户余额
        UserBillModel latestBill = this.userBillDao.selectByUserId(model.getBuyUser());
        if(null == latestBill){
            throw new ApplicationException("账户余额不足，请充值");
        }else if(transferModel.getTransferAmt()>latestBill.getBalance()){
            throw new ApplicationException("账户余额不足，请充值");
        }
	}
	
	
	/**
     * 
     * Description: 购买转让商品
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public void createTransferOrder(CrowdfundProductTransferModel model) {
		logger.info("=================>购买转让商品开始============");
		String transferNo = model.getTransferNo();
		try {
			 if (this.isTransferLock(transferNo)) {
				 logger.info("请求失败,transferNo被锁！" + transferNo);
				 throw new ApplicationException("该转让已被锁定");
		     }	       
			if(StringUtils.isEmpty(model.getTransferExtendId())){
				throw new ApplicationException("扩展表id不能为空");
			}
			CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(model.getTransferNo());
			if(CrowdfundCoreConstants.transferStateFbd.transfering_locking.getValue().equals(transferModel.getTransferState())){
				throw new ApplicationException("该转让已被锁定");
			}
			transferModel.setBuyUser(model.getBuyUser());
			
			if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
				transferModel.setBuyTime(new Date());
				transferModel.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfering_locking.getValue());
				this.crowdfundProductTransferDao.update(transferModel);
				CrowdfundTransferExtendModel extendModel=crowdfundTransferExtendDao.selectByPrimaryKey(model.getTransferExtendId());
				if(extendModel==null){
					throw new ApplicationException("扩展表数据异常");
				}
				extendModel.setIsUse("y");
				crowdfundTransferExtendDao.update(extendModel);
				//异步逻辑start
				//这一行做过调整
				model = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
				
				CrowdfundTransferExtendModel queryEModel=new CrowdfundTransferExtendModel();
				queryEModel.setTransferNo(transferNo);
				queryEModel.setIsUse("y");
				List<CrowdfundTransferExtendModel> extendList=crowdfundTransferExtendDao.findByModel(queryEModel);
				if(extendList==null||extendList.size()>1){
					throw new ApplicationException(transferNo+":转让扩展数据异常");
				}
				model.setBuyTime(new Date());
				model.setBuyUser(model.getBuyUser());
				model.setTransferAuditState(FbdCoreConstants.transferAuditState.auditing);
				model.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfered.getValue());
				model.setBuySupportNo(PKGenarator.getOrderId());
				this.crowdfundProductTransferDao.update(model);
				//异步逻辑end				
				//1 购买人出账
				this.userBillService.addByUserTransferFreeze(model);
			}else{
				//Map<String,String> params = this.getTransferParam(transferModel);
				String requestId=PKGenarator.getId();
				UserModel user = this.userDao.findByUserId(transferModel.getBuyUser());
				CrowdfundingModel loan=this.crowdfundingDao.getByloanNo(transferModel.getLoanNo());
				if(StringUtils.isEmpty(user.getBlockChainAddress())){
					throw new ApplicationException("出账用户地址为空");
				}
				if(StringUtils.isEmpty(loan.getBlockChainAddress())){
					throw new ApplicationException("转入项目账户地址为空");
				}
				Map<String,String> params = new HashMap<String,String>();
				params.put("serviceID", "transfer");
				params.put("transferNO",transferModel.getTransferNo());
				params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
				params.put("sourceAddress",user.getBlockChainAddress()); //转出账户地址为平台出账户
				params.put("sourceKey",user.getBlockChainSecretKey());
				params.put("targetAddress",loan.getBlockChainAddress());  //转入账户为用户账号(测试使用用户)
				DecimalFormat dFormat=new DecimalFormat("#.00");
				String amount = dFormat.format(Arith.add(transferModel.getTransferAmt(), transferModel.getTransFee()));
				params.put("amount",amount);
				params.put(BlockChainCore.REQUEST_ID, requestId);
				params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"letvPay/transfer/receiveTransferS2S.html");
				
				if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
     				params.put("amount","0.02");
				}
				
	  			//添加一条事务通知数据
	        	BlockAsynTranModel blockAsynTran = new BlockAsynTranModel();
	        	blockAsynTran.setId(PKGenarator.getId());
	        	blockAsynTran.setParentId(transferNo);  //支持编号
	        	blockAsynTran.setCreateTime(new Date());
	        	blockAsynTran.setType(BlockChainCore.Type.TRANSFER);
	        	blockAsynTran.setRequestID(requestId);
	        	this.blockAsynTranDao.save(blockAsynTran);
				
				String result = MockUtils.transfer(params);
				@SuppressWarnings("unchecked")
				Map<String,Object> resultMap = JsonHelper.getMapFromJson(result);
				String status = resultMap.get(BlockChainCore.STATUS).toString();
				
				
				if(ResultStatus.ServiceSuccess.equals(status)){
					transferModel.setBuyTime(new Date());
					transferModel.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfering_locking.getValue());
					this.crowdfundProductTransferDao.update(transferModel);
					CrowdfundTransferExtendModel extendModel=crowdfundTransferExtendDao.selectByPrimaryKey(model.getTransferExtendId());
					if(extendModel==null){
						throw new ApplicationException("扩展表数据异常");
					}
					extendModel.setIsUse("y");
					crowdfundTransferExtendDao.update(extendModel);
			    }else{
			    	throw new ApplicationException("购买转让(区块链):"+resultMap.get(BlockChainCore.MESSAGE).toString());
			    }
			}
		} catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("购买转让商品失败");
		}finally {
            LockUtil.getInstance().remove(
            		 CrowdfundCoreConstants.LOCK_TRANSFER + transferNo);
		}
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
	private void saveSupport(CrowdfundProductTransferModel transferModel) {
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
		modle.setTransFee(transferModel.getTransFee());
		modle.setSupportClass(CrowdfundCoreConstants.supportClass.transferSupport.getValue());
		this.crowdfundingSupportDao.save(modle);
	}


	/**
     * 
     * Description: 修改转让成功
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
	public void updateTransferSuccess(Map<String, String> resultMap) {
		 String transferNo = resultMap.get(BlockChainCore.TRANSFER_NO);
		 CrowdfundProductTransferModel model = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
		 CrowdfundingModel crowdModel = this.crowdfundingDao.getByloanNo(model.getLoanNo());
		 CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(model.getSupportNo());
		 try {
			 if (this.isTransferLock(transferNo)) {
				 logger.info("请求失败,transferNo被锁！" + transferNo);
		         return;   
		     }	       
			 if(model == null) {
	                throw new ApplicationException("订单号为" + transferNo + "转让账单不存在");
	         }
			 String transferState = CrowdfundCoreConstants.transferStateFbd.transfered.getValue();
			 if (model.getTransferState().equals(transferState)) {// 充值状态已经更新
	            logger.info(transferNo + "转让已经完成！");
	            return;
	         }   
			 CrowdfundTransferExtendModel queryEModel=new CrowdfundTransferExtendModel();
			 queryEModel.setTransferNo(transferNo);
			 queryEModel.setIsUse("y");
			 List<CrowdfundTransferExtendModel> extendList=crowdfundTransferExtendDao.findByModel(queryEModel);
			 if(extendList==null||extendList.size()>1){
			 	throw new ApplicationException(transferNo+":转让扩展数据异常");
			 }
			 model.setBuyTime(new Date());
			 model.setBuyUser(model.getBuyUser());
			 model.setTransferAuditState(FbdCoreConstants.transferAuditState.auditing);
			 model.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfered.getValue());
			 model.setBuySupportNo(PKGenarator.getOrderId());
			 this.crowdfundProductTransferDao.update(model);
			 
			 supportModel.setSupportClass("transferSupport");
			 crowdfundingSupportDao.updateBySelective(supportModel);
			 //1 购买人出账
			 this.userBillService.addByUserTransferFreeze(model);
			

	         //发送站内信
			 this.sendInvestTransferMessage(model,crowdModel);
		} catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
           throw new SysException("更新充值单失败：" + e.getMessage());
        }finally {
              LockUtil.getInstance().remove(
            		  CrowdfundCoreConstants.LOCK_TRANSFER + transferNo);
        }    
	}

	
	 
	/**
    * 
    * Description: 发送转让购买成功信息 
    *
    * @param 
    * @return void
    * @throws 
    * @Author wuwenbin<br/>
    */
    private void sendInvestTransferMessage( CrowdfundProductTransferModel model,CrowdfundingModel crowdModel){
        Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(new Date(), null));
        params.put("transferAmt",Arith.format(model.getTransferAmt()));
        params.put("transferNo",model.getTransferNo());
        params.put("loanNo",crowdModel.getLoanNo());
        params.put("loanName",crowdModel.getLoanName());
        params.put("transferTime",DateUtil.dateTime2Str(model.getTransferTime(), null));
       
        try{
           logger.info("发送购买人购买支付成功内信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_PAY_BUY_MSG;
           SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getBuyUser(), params);
           logger.info("发送购买人购买支付站内信结束");
        }catch(Exception e){
           logger.error("发送购买人购买支付站内信失败，"+e.getMessage());
        }
        try{
           logger.info("发送转让人站内信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_TRANSFER_PAY_TRANSFE_MSG;
           SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getTransferUser(), params);
           logger.info("发送转让人站内信结束");
        }catch(Exception e){
           logger.error("发送转让人站内信失败，"+e.getMessage());
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
    public synchronized boolean isTransferLock(String orderId) {
        boolean isLock = true;
        Object lockValue = LockUtil.getInstance().get(
                CrowdfundCoreConstants.LOCK_TRANSFER + orderId);
        if (lockValue == null) {
            LockUtil.getInstance().put(
                    CrowdfundCoreConstants.LOCK_TRANSFER + orderId, orderId);
            isLock = false;
        }
        logger.info("众筹支持单isLock=====================" + isLock);
        return isLock;
    }


	@Override
	public String saveTransUserInfo(CrowdfundTransferExtendModel model) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(model.getUserId())){
			throw new ApplicationException("用户编号不能为空");
		}
		if(StringUtils.isEmpty(model.getTransferNo())){
			throw new ApplicationException("转让编号不能为空");
		}
		if(StringUtils.isEmpty(model.getSupportContent())){
			throw new ApplicationException("内容不能为空");
		}
		crowdfundTransferExtendDao.deleteUserAndTrans(model);
		model.setId(PKGenarator.getId());
		crowdfundTransferExtendDao.save(model);
		return model.getId();
	}


	@Override
	public CrowdfundTransferExtendModel getTransUserInfo(String id) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(id)){
			throw new ApplicationException("id不能为空");
		}
		return crowdfundTransferExtendDao.selectByPrimaryKey(id);
	}

    public static void main(String[] args){
/*    	Map<String, String> map=new HashMap<String, String>();
    	map.put("status", "false");
    	map.put("count", "0");
    	updateTimerTransferSusccess(map);*/
    }

	@Override
	public void updateTransferSyncFail(Map<String, String> result) {
		// TODO Auto-generated method stub
		String transferNo =  result.get(BlockChainCore.TRANSFER_NO).toString();
		CrowdfundProductTransferModel transferModel = this.crowdfundProductTransferDao.getByTransferNo(transferNo);
		transferModel.setBuyUser(null);
		transferModel.setBuyTime(null);
		transferModel.setTransferState(CrowdfundCoreConstants.transferStateFbd.transfering.getValue());
		this.crowdfundProductTransferDao.update(transferModel);
		CrowdfundTransferExtendModel queryEModel=new CrowdfundTransferExtendModel();
		queryEModel.setTransferNo(transferNo);
		queryEModel.setIsUse("y");
		List<CrowdfundTransferExtendModel> extendList=crowdfundTransferExtendDao.findByModel(queryEModel);
		if(extendList==null||extendList.size()>=0){
			for(CrowdfundTransferExtendModel extend:extendList){
				extend.setIsUse(null);
				crowdfundTransferExtendDao.update(extend);	
			}
		}
	}
	
	
	
}
