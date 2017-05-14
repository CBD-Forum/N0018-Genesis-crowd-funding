/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: WithDrawAction.java 
 *
 * Created: [2015-1-27 上午10:30:00] by haolingfeng
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

package com.fbd.web.app.withdraw.action;

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

import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.withdraw.dao.ISystemWithDrawDao;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.web.app.auth.service.IAuthService;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.todo.service.ITodoService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;
import com.fbd.web.app.withdraw.service.IWithDrawService;

/** 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 提现
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/withdraw")
public class WithDrawAction extends BaseAction{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(WithDrawAction.class);
    
    @Resource
    private IWithDrawService withDrawService;
    @Resource
    private ITodoService todoService;
    @Resource
    private IAuthService authService;
    @Resource
    private IVerifyCodeService verifyCodeService;
    @Resource
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    @Resource
    private IWithDrawQueryService withDrawQueryService;
    @Resource
    private IWithDrawDao withDrawDao;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private ISystemWithDrawDao systemWithDrawDao;
    
    /**
     * 
     * Description: 查询提现列表
     * 
     * @param
     * @return SearchResult<InvestModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午6:02:32
     */
    @RequestMapping(value = "/getWithDrawList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getWithDrawList(HttpServletRequest request,
            WithDrawModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            SearchResult<WithDrawModel> result = withDrawService.getPageList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * Description: 查询用户银行卡
     * @param 
     * @return Map<String,Object>
     * @throws 
     * Create Date: 2015-2-9 上午11:40:50
     */
    @RequestMapping(value = "/getUserBankList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getUserBankList(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            UserBankModel userBank = new UserBankModel();
            userBank.setUserId(userId);
            List<UserBankModel> result = withDrawService.getUserBankList(userBank);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description:查询提现手续费
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午6:50:27
     */
    @RequestMapping(value = "/getWithdrawFee.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> getWithdrawFee(HttpServletRequest request,WithDrawModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            double actualMoney = model.getActualMoney(); //提现金额
            double fee = withDrawService.getWithDrawFee("");
            //查询提现手续费费率
            resultMap.put("actualMoney",actualMoney); //应扣金额(实际扣除金额)
            resultMap.put("fee", fee);
            resultMap.put("amt", actualMoney-fee);  //实际到账金额
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"提现申请失败");
        }
        return resultMap;
    } 
    @RequestMapping(value = "/selectWithdrawFee.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> selectWithdrawFee(HttpServletRequest request){
    	double fee = withDrawService.getWithDrawFee("");
    	Map<String,Object>map = new HashMap<String,Object>();
    	map.put("fee", fee);
    	return map;
    }
    
    /**
     * 提现前的验证
     * @param request
     * @param verifyCode
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkWithdraw.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> checkWithdraw(HttpServletRequest request,String verifyCode,WithDrawModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            //验证手机验证码
            this.verifyCodeService.checkVerifyCode(verifyCode, 
                    FbdConstants.NODE_TYPE_WITHDRAW_REMIND, userId);
            withDrawService.checkWithDraw(model);
            resultMap.put(SUCCESS, true);
        }catch(ApplicationException e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"验证失败");
        }
        return resultMap;
    }
    /**
     * 
     * Description:提交提现申请 
     * @param 
     * @return Map<String,Object>
     * @throws 
     * Create Date: 2015-4-2 下午6:50:27
     */
    @RequestMapping(value = "/saveWithdrawOrder.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveWithdrawOrder(HttpServletRequest request,WithDrawModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            String requestId = PKGenarator.getOrderId();
            //保存提现申请
            withDrawService.saveWithDraw(model,requestId);
            //添加待办事项
            String link = authService.getLinkByCode(FbdCoreConstants.permissionCode.WITHDRAW_AUDIT);
            todoService.saveTodo(FbdCoreConstants.withDrawState.auditing.getValue(), model.getOrderId(), 
                    userId+"提交提现申请："+model.getOrderId(), link, 
                    FbdCoreConstants.todoPost.finance);
            resultMap.put(SUCCESS, true);
            resultMap.put("orderId",model.getOrderId());
            resultMap.put("requestId",requestId);

        }catch(ApplicationException e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"提现申请失败");
        }
        return resultMap;
    } 
    
    /**
     * 查询提现请求状态
     * @param request
     * @return
     */
    @RequestMapping(value = "/selectWithdrawState.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String,Object> selectWithdrawState(HttpServletRequest request,String orderId){
    	Map<String,Object>resultMap = new HashMap<String,Object>();
    	try{
    		WithDrawModel model = this.withDrawService.getByOrderId(orderId);
    		resultMap.put("state", model.getState());
    		resultMap.put(SUCCESS, true);
        }catch(Exception e){
        	resultMap.put(SUCCESS, false);
        }
    	return resultMap;
    }
    
    
    
    /**
     * 提现冻结异步通知
     * @param request
     * @param response
     */
    @RequestMapping(value = "/withDrawNotify.html",method = RequestMethod.POST)
    public  void withDrawNotify(HttpServletRequest request,HttpServletResponse response){
    	logger.info("==========提现冻结区块链通知开始===================");
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=========提现冻结-区块链异步通知结果："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        try{
	        //等待同步处理数据
	    	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
	        BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestID);
	        blockAsynTran.setStatus(status);
            blockAsynTran.setTranId(transactionID);
            this.blockAsynTranDao.updateBySelective(blockAsynTran);
            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
            	
            	return ;
            }else if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
        		 Timer timer = new Timer();
            	 timer.schedule(new MyTask(timer,transactionID,BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.withdraw_apply,transferNO,requestID), 2000,2000);	
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    
    /**
     * 平台提现异步通知
     * @param request
     * @param response
     */
    @RequestMapping(value = "/systemWithDrawAuditNotify.html",method = RequestMethod.POST)
    public  void systemWithDrawAuditNotify(HttpServletRequest request,HttpServletResponse response){
    	logger.info("==========平台提现区块链通知开始===================");
        Map<String, String> map = this.getBlockChainParamsStr();
        logger.info("=========平台提现-区块链异步通知结果："+map);
        String transactionID =  map.get("transactionID").toString();
        String transferNO =  map.get("transferNO").toString();
        String status = map.get("status")== null?"":map.get("status").toString();
        String requestID = map.get("requestID");
        try{
	      //等待同步处理数据
	    	Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
	        BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestID);
	        
            blockAsynTran.setStatus(status);
            blockAsynTran.setTranId(transactionID);
            this.blockAsynTranDao.updateBySelective(blockAsynTran);
            WithDrawModel withdrawModel = this.withDrawDao.getByOrderId(blockAsynTran.getParentId());
            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
            	 withdrawModel.setMemo(map.get("message"));
            	 withDrawDao.updateBySelective(withdrawModel);
             	 return ;
            }else if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
         		 Timer timer = new Timer();
             	 timer.schedule( new MyTask(timer,transactionID,BlockChainCore.ResultStatus.SUBMIT_PROCESS,BlockChainCore.Type.withdraw_apply,transferNO,requestID), 2000,2000);	
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
   
    public void withDrawApplySuccess(Map<String,String>params){
    	String orderId = (String)params.get("orderId");
    	IWithDrawService withDrawService=(IWithDrawService) SpringUtil.getBean("withDrawService");
        WithDrawModel withDrawModel = withDrawService.getByOrderId(orderId);
        withDrawService.withDrawApplySuccess(withDrawModel);
        
  }
   
    /**
     * 提现审核异步通知
     */
    @RequestMapping(value = "/withDrawAuditNotify.html", method = RequestMethod.POST)
    public  void withDrawAuditNotify(){
    	try{
	    	logger.info("==========提现审核区块链通知开始===================");
	        Map<String, String> map = this.getBlockChainParamsStr();
	        logger.info("=============提现审核-区块链异步通知结果："+map);
	        String transactionID =  map.get("transactionID").toString();
	        String transferNO =  map.get("transferNO").toString();
	        String status = map.get("status")== null?"":map.get("status").toString();
	        String requestID = map.get("requestID");
	        Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
	        BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestID);
	        if(blockAsynTran!=null){
                WithDrawModel withdrawModel = this.withDrawDao.getByOrderId(blockAsynTran.getParentId());
                blockAsynTran.setStatus(status);
                blockAsynTran.setTranId(transactionID);
                this.blockAsynTranDao.updateBySelective(blockAsynTran);
	            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
	            	withdrawModel.setMemo(map.get("message"));
	            	withDrawDao.updateBySelective(withdrawModel);
	             	return ;
	            }else if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
	             	Timer timer = new Timer();
	             	timer.schedule( new MyTask(timer,transactionID,blockAsynTran.getType(),BlockChainCore.Type.withdraw_audit,transferNO,requestID), 2000,2000);	
	            }
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
 
	class MyTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        String transNo;
        String requestId;
        public MyTask(Timer mytimer,String tranId,String status,String type,String transNo,String requestId){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.transNo = transNo;
        	this.requestId = requestId;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId,requestId);
			try{
  				BlockAsynTranModel blockAsynTran =blockAsynTranDao.findByRequestId(requestId);
  				status = blockAsynTran.getQueryStatus();
  				type =blockAsynTran.getType();
                WithDrawModel model = withDrawDao.getByOrderId(blockAsynTran.getParentId());
                if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equalsIgnoreCase(status)){
                	Map<String, String> map = new HashMap<String,String>();
                	map.put("transferNO", transNo);
                	map.put("status", status);
                	map.put("message", "");
                	map.put("transactionID", transId);
                	map.put("orderId", blockAsynTran.getParentId());
                	if(BlockChainCore.Type.withdraw_apply.equals(type)){           //提现申请
                		new WithDrawAction().withDrawApplySuccess(map);
                	}else if(BlockChainCore.Type.withdraw_audit.equals(type)){     //提现审核
                		withDrawService.withDrawAuditSuccess(transId);
                	}else if(BlockChainCore.Type.withdraw_refuse.equals(type)){    //提现拒绝
                		withDrawService.withDrawRefuseSucess(transId);
                	}else if(BlockChainCore.Type.withdraw_system_deal.equals(type)){//用户提现平台手续费
                		withDrawQueryService.withDrawSystemSuccess(model);
                	}else if(BlockChainCore.Type.withdraw_user_deal.equals(type)){  //用户提现成功
                		withDrawQueryService.withDrawUserSuccess(model); 
                	}else if(BlockChainCore.Type.withdraw_fail_user_deal.equals(type)){  //用户提现成功
                		withDrawService.withDrawFailAfter(requestId); 
                	}else if(BlockChainCore.Type.platform_withdraw.equals(type)){   //平台提现
                		/*systemWithDrawDao.g
                		systemWithDrawUserSuccess*/
                	}
                	
                	timer.cancel();
                	System.gc();
                
                }
			}catch(Exception e){
				e.printStackTrace();
				timer.cancel();
            	System.gc();
			}
		 }
	}
    
    //提现审核异步通知
    @RequestMapping(value = "/getWithDrawState.html", method = RequestMethod.POST)
    public  void getWithDrawState(WithDrawModel recharge){
    	logger.info("==========查询提现请求状态===================");
    	
    	
    	
    	
 
    }
    
    
    
    
    
    //提现审核拒绝异步通知
    @RequestMapping(value = "/withDrawRefuseNotify.html", method = RequestMethod.POST)
    public  void withDrawRefuseNotify(HttpServletRequest request,HttpServletResponse response){
      	try{
	    	logger.info("==========提现审核拒绝区块链通知开始===================");
	        Map<String, String> map = this.getBlockChainParamsStr();
	        logger.info("=============提现审核拒绝区块区块链异步通知结果："+map);
	        String transactionID =  map.get("transactionID").toString();
	        String transferNO =  map.get("transferNO").toString();
	        String status = map.get("status")== null?"":map.get("status").toString();
	        String requestID = map.get("requestID");
	        //等待指定时间
	        Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
	        //查询事务异步通知信息
	        BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestID);
	        if(blockAsynTran!=null){
	            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
	             	return ;
	            }else{
	                 blockAsynTran.setStatus(status);
	                 blockAsynTran.setTranId(transactionID);
	                 this.blockAsynTranDao.updateBySelective(blockAsynTran);
	             	 if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
	             		 Timer timer = new Timer();
	             		 timer.schedule( new MyTask(timer,transactionID,BlockChainCore.ResultStatus.SUCCESS,BlockChainCore.Type.withdraw_refuse,transferNO,requestID), 2000,2000);	
	             	}
	            }
	        }
      	}catch(Exception e){
      		e.printStackTrace();
      	}
    }
    
    
    
    
    /**
     * 查询余额
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/getBalance.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> getBalance(String orderId) {
        String balance = withDrawQueryService.QueryBalanceSxy();
    	Map<String,Object>map = new HashMap<String,Object>();
    	map.put("balance", balance);
    	return map;
    }

}
