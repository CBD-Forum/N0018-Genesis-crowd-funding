/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeAction.java 
 *
 * Created: [2014-12-24 下午6:00:17] by haolingfeng
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

package com.fbd.web.app.recharge.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.impl.BlockChainQueryServiceImpl;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.recharge.dao.IRechargeDao;
import com.fbd.core.app.recharge.model.RechargeBankModel;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.recharge.service.IRechargeReconciliationService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;
import com.fbd.web.app.recharge.service.IRechargeBankService;
import com.fbd.web.app.recharge.service.IRechargeService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:充值列表
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/recharge")
public class RechargeAction extends BaseAction {
	
	
    private static final Logger logger = LoggerFactory
            .getLogger(RechargeAction.class);

    /**
     * 
     */
    private static final long serialVersionUID = -4803963548470295353L;

    @Resource
    private IRechargeService rechargeService;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IRechargeBankService rechargeBankService;
    @Resource
    private IRechargeDao rechargeDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private BlockChainQueryServiceImpl blockChainQuerySerivce;
    @Resource
    private IRechargeReconciliationService rechargeReconciliationService;    
    
    
    
    /**
     * @param
     * @return SearchResult<InvestModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:02:32
     */
    @RequestMapping(value = "/testRechargeReconciliation.html", method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object> test(HttpServletRequest request,
            RechargeModel model) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
        	rechargeReconciliationService.rechargeReconciliation(DateUtils.dateToString(DateUtil.addDate(new Date(),-1), "yyyyMMdd"),"sxyPay");
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }    
    
    
    
    
    
    /**
     * 
     * Description: 查询充值列表
     * 
     * @param
     * @return SearchResult<InvestModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:02:32
     */
    @RequestMapping(value = "/getRechargeList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getRechargeList(HttpServletRequest request,
            RechargeModel model) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            SearchResult<Map<String,Object>> result = rechargeService
                    .getRechargeList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

    /**
     * 
     * Description: 查询充值银行列表
     * 
     * @param
     * @return SearchResult<RechargeBankModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:02:32
     */
    @RequestMapping(value = "/getRechargeBankList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getRechargeList(RechargeBankModel model) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{ 
            SearchResult<RechargeBankModel> result = new SearchResult<RechargeBankModel>();
            model.setBusiType("B2C");
            List<RechargeBankModel> aList = rechargeBankService.getList(model);
            result.setRows(aList);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
     }
    
    @RequestMapping(value = "/test.html", method = RequestMethod.GET)
    public @ResponseBody List<Map<String,Object>> test() {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map<String,Object>>  list =rechargeService.pushRechargeData(param);
        return list;
    } 
    
    /**
     * 充值区块链异步通知处理
     * @param request
     * @param res
     */
    @RequestMapping(value = "/rechargeNotify.html")
    public  void rechargeNotify(HttpServletRequest request,HttpServletResponse res) throws Exception{
        logger.info("=============充值区块链异步通知开始============");
        //等待同步处理数据再次等待1秒
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=============充值区块链异步通知参数："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        
        //查询事务异步通知信息
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        if(blockAsynTran==null){
        	//添加一条事务通知数据
        	blockAsynTran = new BlockAsynTranModel();
        	blockAsynTran.setId(PKGenarator.getId());
        	blockAsynTran.setRequestID(requestID);
        	blockAsynTran.setParentId(transferNO);  //订单编号
        	blockAsynTran.setCreateTime(new Date());
        	blockAsynTran.setUpdateTime(new Date());
        	blockAsynTran.setType(BlockChainCore.Type.recharge);
        	blockAsynTran.setStatus(BlockChainCore.ResultStatus.add);
        	this.blockAsynTranDao.save(blockAsynTran);
        }
       
        blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
        blockAsynTran.setStatus(status);
        blockAsynTran.setTranId(transactionID);
        this.blockAsynTranDao.updateBySelective(blockAsynTran);
        //查询充值信息
        RechargeModel recharge = this.rechargeDao.selectByOrderId(blockAsynTran.getParentId());
        Timer timer = new Timer();
    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
    		rechargeService.rechargeTransferAfter(recharge.getOrderId(),requestID, recharge, timer, false);
    	}else{
    		timer.schedule(new rechargeTask(timer,transactionID,requestID,
         			 BlockChainCore.ResultStatus.SUCCESS,BlockChainCore.Type.recharge,recharge),1000,3000); 
    	}
    }
    /**
     * 充值区块链异步通知定时任务
     * @author 80bug
     */
    class rechargeTask  extends java.util.TimerTask{
        String transId="";
        String requestID = "";
        Timer timer =null;
        String status ="";  
        String type ="";
        RechargeModel recharge = null;
        public rechargeTask(Timer mytimer,String tranId,String requestID,String status,String type,RechargeModel recharge){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.requestID = requestID;
        	this.status = status;
        	this.type = type;
        	this.recharge = recharge;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId,requestID);
			try{
				//处理数据
				rechargeService.rechargeTransferAfter(recharge.getOrderId(),requestID, recharge, timer, true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
    
 
}
