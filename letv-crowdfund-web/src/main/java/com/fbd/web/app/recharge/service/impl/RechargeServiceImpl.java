/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeServiceImpl.java 
 *
 * Created: [2014-12-22 下午2:32:18] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.recharge.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.push.dao.IPushDataLogDao;
import com.fbd.core.app.push.model.PushDataLogModel;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.SpringPropertiesHolder;
import com.fbd.web.app.recharge.service.IRechargeService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("rechargeService")
public class RechargeServiceImpl implements IRechargeService {
	
    private static final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
    @Resource
    private IRechargeDao rechargeDao;
    @Resource
    private IPushDataLogDao pushDataLogDao;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IUserBillService userBillService;
    /**
     * 
     * Description:充值列表查询
     * 
     * @param
     * @return SearchResult<RechargeModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:03:36
     */
    public SearchResult<Map<String,Object>> getRechargeList(RechargeModel model) {
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        if (model.getCreateStartTime() != null) {
            model.setCreateStartTime(DateUtil.getDayMin(model
                    .getCreateStartTime()));
        }
        if (model.getCreateEndTime() != null) {
            model.setCreateEndTime(DateUtil.getDayMax(model
                    .getCreateEndTime()));
        }
        result.setTotal(this.rechargeDao.getRechargeListCount(model));
        result.setRows(this.rechargeDao.getRechargeList(model));
        return result;
    }

	@Override
	public List<Map<String, Object>> pushRechargeData(
			Map<String, Object> param) {
		try{
			// TODO Auto-generated method stub
			Map<String,Object>pushMap = pushDataLogDao.selectModel("recharge");
			if(pushMap!=null){
				param.put("completeTime", pushMap.get("lastPushTime"));
			}
			param.put("paymentMethod", SpringPropertiesHolder.getProperty("paymentMethod"));
			param.put("paymentAccount", SpringPropertiesHolder.getProperty("paymentAccount"));
			PushDataLogModel pushDataLogModel=new PushDataLogModel();
			pushDataLogModel.setId(PKGenarator.getId());
			List<Map<String,Object>>list = rechargeDao.selectRechargeData(param);
			if(list!=null&&list.size()>0){
				Map<String,Object>map = list.get(0);
	
				if(map!=null){
					pushDataLogModel.setLastPushTime(DateUtil.date2Str((Date)map.get("businessDate"), DateUtil.DEFAULT_DATE_TIME_FORMAT));
					pushDataLogModel.setMemo("投资方充值推送");
					pushDataLogModel.setEventCode("recharge");
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
     * Description:查询交易中和交易失败的充值记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public SearchResult<Map<String, Object>> getUserRechargeList(
			RechargeModel model) {
		SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
		result.setRows(this.rechargeDao.getUserRechargeList(model));
		result.setTotal(this.rechargeDao.getUserRechargeCount(model));
		return result;
	}

	/**
     * 
     * Description:查询交易中和交易失败的充值记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
	public Map<String, Object> getUserRechargeDetail(RechargeModel model) {
		return this.rechargeDao.getUserRechargeDetail(model);
	}
	
	
    /**
     * 区块链转账成功后的后续操作
     * @param orderId
     */
    public void rechargeTransferAfter(String orderId,String requestID,RechargeModel recharge,Timer timer,boolean notifyFlag){
    	
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        String query_status = blockAsynTran.getQueryStatus();
        if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(query_status)){
        	recharge.setBlockChainState(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS);  //设置状态为区块链状态
    		//recharge.setState(FbdCoreConstants.rachargeState.recharge_success_auditing.getValue());
    		recharge.setBlockChainState("success");
    		recharge.setTransferComplateTime(new Date());
        	this.rechargeDao.update(recharge);
        	//添加充值账单
//        	this.userBillService.addRechargeUserBill(recharge);
        	//发送充值审核通过的站内信
//        	this.sendRechargePassedMessage(recharge);
        	
        	//添加充值冻结账单
        	this.userBillService.addUserRechargeFreezeBill(recharge);
        	if(notifyFlag){
        		timer.cancel();
        	}
        }
    }
    
	private void sendRechargePassedMessage(RechargeModel model){
		String state = model.getState();
		String userId = model.getUserId();
		String orderId = model.getOrderId();
	    Map<String, String> params = new HashMap<String,String>();
        params.put("time",DateUtil.dateTime2Str(new Date(), null));
        params.put("orderId",orderId);
        params.put("amt",String.valueOf(model.getRechargeAmt()));
		try{
	        logger.info("发送充值审核通过站内信开始");
	        String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_AUDIT_PASSED_MSG;
	        SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_SYS_MSG, userId, params);
	        logger.info("发送充值审核通过站内信结束");
	   }catch(Exception e){
	        logger.error("发送充值审核通过站内信失败，"+e.getMessage());
	   }
		
		
	   try{
           logger.info("发送充值审核通过手机短信开始");
           String nodeCode = FbdCoreConstants.NODE_TYPE_RECHARGE_AUDIT_PASSED_MOBILE;
           SendMessageUtil.sendMobileMessage(nodeCode,userId, params);
           logger.info("发送充值审核通过手机短信结束");
       }catch(Exception e){
           logger.error("发送充值审核通过手机短信失败，"+e.getMessage());
        }
	}
    /**
     * 根据orderId查询充值
     * @param orderId
     * @return
     */
    public RechargeModel selectByOrderId(String orderId){
    	return this.rechargeDao.selectByOrderId(orderId);
    }
 
}
