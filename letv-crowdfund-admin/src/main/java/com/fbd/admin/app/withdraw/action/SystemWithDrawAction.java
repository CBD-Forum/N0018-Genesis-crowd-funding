package com.fbd.admin.app.withdraw.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.admin.app.withdraw.service.ISystemWithDrawService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.MockUtils;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
@Controller
@Scope("prototype")
@RequestMapping("/systemWithdraw")
public class SystemWithDrawAction extends BaseAction{
    private static final Logger logger = LoggerFactory.getLogger(SystemWithDrawAction.class);
    @Resource
    private ISystemBillService systemBillService;
    @Resource
    private ISystemWithDrawService systemWithDrawService ;
    @Resource
    private IWithDrawQueryService withDrawQueryService;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IUserDao userDao;
    @Resource IBlockAsynTranService blockAsynTranService;
    @RequestMapping(value = "/getWithDrawBalance.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> queryResult(String requestId) {
    	Map<String,Object>map  = systemBillService.getWithDrawBalance();
    	
    	return map;
    }
    /**
     * 
     * Description: 保存提现申请
     *
     * @param 
     * @return WithDrawModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-15 下午12:30:56
     */
    public void saveWithDraw(SystemWithdrawModel withDraw,String requestId){
        try{
        	
        	BlockAsynTranModel blockAsynTran = blockAsynTranDao.findByRequestId(requestId);
        	String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        	String orderId = PKGenarator.getOrderId();
        	double amount=withDraw.getAmt()+withDraw.getFee();
        	if(blockAsynTran==null){
        	
	            blockAsynTran = new BlockAsynTranModel();
	            blockAsynTran.setParentId(orderId);
		       	blockAsynTran.setType(BlockChainCore.Type.withdraw_apply);
		       	blockAsynTran.setRequestID(requestId);
		       	blockAsynTran.setCreateTime(new Date());
		       	blockAsynTranService.save(blockAsynTran);
		       	withDraw.setId(PKGenarator.getId());
		        withDraw.setOrderId(orderId);
		        withDraw.setFee(1d);		          
		          //等待处理
		        withDraw.setState(CrowdfundCoreConstants.withDrawState.auditing.getValue());
		        withDraw.setApplyTime(new Date());
		        withDraw.setThirdWtihDrawType(LetvPayConstants.thirdType.SXY);
		        systemWithDrawService.saveSystemWithDraw(withDraw);
        	} else{
        		  withDraw.setFee(1d);
	  		          //等待处理
	  		      withDraw.setState(CrowdfundCoreConstants.withDrawState.auditing.getValue());
	  		      withDraw.setApplyTime(new Date());
	  		      withDraw.setThirdWtihDrawType(LetvPayConstants.thirdType.SXY);
	  		      systemWithDrawService.updateById(withDraw);
        	}
        	//发送提现转账请求(将用户的钱转到平台中间账户)
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "transfer");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("transferNO",orderId);
            /*params.put("sourceAddress",FbdCoreConstants.); //转出账户地址
            params.put("sourceKey", FbdCoreConstants.);*/
            params.put("targetAddress",FbdCoreConstants.BLOCK_CHAIN_PLATFORM_MIDDLE_ACCOUNT);  //转入到平台中间账户
            if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            	amount = 0.01;
    		}
            params.put("amount",String.valueOf(Arith.round(amount)));
            params.put("requestID", requestId);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"/withdraw/systemWithDrawNotify.html");
          
            String platformResultStr = MockUtils.opBlockChain(requestUrl, params);
       //    String platformResultStr="{status:\"Success\"}";
            @SuppressWarnings("unchecked")
            Map<String,Object> resultMap = JsonHelper.getMapFromJson(platformResultStr);
            String status = resultMap.get("status").toString();
          
            if(BlockChainCore.ResultStatus.ServiceSuccess.equals(status)){ //转账成功

              
             
            }else{
                String message = resultMap.get("message")==null?"":resultMap.get("message").toString();
                 logger.info("提现申请失败-【"+message+"】！");
                throw new ApplicationException(message);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }
    @RequestMapping(value = "/saveSystemWithDraw.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveSystemWithDraw(SystemWithdrawModel model) {
    	Map<String,Object>map = new HashMap<String,Object>();
    	try{
	    	String adminId = MyRequestContextHolder.getCurrentUser().getAdminId();
	    	model.setApplyPerson(adminId);
	    	model.setApplyTime(new Date());
	    	model.setOrderId(PKGenarator.getOrderId());
	    	model.setState("auditing");
	    	int count = systemWithDrawService.saveSystemWithDraw(model);
	    	if(count>0){
	    		map.put(SUCCESS, true);
	    	}else{
	    		map.put(SUCCESS, false);
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return map;
    }
    @RequestMapping(value = "/getList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String,Object>> getList(SystemWithdrawModel model) {
    	
             SearchResult<Map<String,Object>> result = systemWithDrawService.getSystemWithDrawPage(model);
             return result;
         
    }
    /**
     * Description: 审核提现
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/auditState.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> auditPass(SystemWithdrawModel withdraw){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	 try {
    		 SystemWithdrawModel withDraw = systemWithDrawService.selectByOrderId(withdraw.getOrderId());
             double amount = withDraw.getAmt();
    		
    		  if(withdraw.getOpionion()!=null &&!"".equals(withdraw.getOpionion())){
    			// withDraw.setFinancialAuditTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
    			 withDraw.setOpionion(withDraw.getOpionion());
    			 withDraw.setFianacialAuditor(MyRequestContextHolder.getCurrentUser().getEmployeeNo());

    			 if("refuse".equals(withdraw.getState())){
    				 withDraw.setFianacialAuditTime(new Date());
    				 withDraw.setState(withdraw.getState());
    				//将众筹令由平台中间账户转到用户账户(同时记录用户提现拒绝账单)
    				 String requestId = PKGenarator.getOrderId();
    				// withDrawService.saveWithDrawAuditRefuse(withDraw,requestId);
    				// resultMap =confirmIsSuccess(withdraw.getOrderId(),BlockChainCore.Type.withdraw_refuse);
    				 resultMap.put(SUCCESS, true);
				       resultMap.put(MSG, "流程审核成功");
				       resultMap.put("requestId",requestId);
    			 }else{
    			//	 withDraw.setState(withdraw.getState());
    				// withDraw.setStateName("财务审核通过");
    				 //财务审核通过后将众筹令由平台中间账户转账到平台如账户
    				 if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
    					// String balance = withDrawQueryService.QueryBalanceSxy();
	    	            // if(balance!=null && amount<=Double.valueOf(balance)){
    				     //  this.withDrawService.saveWithDrawAuditPassed(withDraw);
    	    				// resultMap =confirmIsSuccess(withdraw.getOrderId(),BlockChainCore.Type.withdraw_audit);
    					 withDrawQueryService.platformWithDraw(withDraw);
    				       resultMap.put(SUCCESS, true);
    				       resultMap.put(MSG, "流程审核成功");
	    	             /*} else{
  	    	            	 resultMap.put(SUCCESS, false);
  	    	            	 resultMap.put(MSG, "账户余额不足,账户余额是:"+balance);
  	    	            	 throw new ApplicationException("账户余额不足,账户余额是:"+balance);
  	    	             }  */
    				 }else{
	    				 /*String balance = withDrawQueryService.QueryBalanceSxy();
	    	             if(balance!=null && amount<=Double.valueOf(balance)){*/
	    	            	 try{
			    				 //调用三方支付将钱转账到用户账户
			    				// withDrawQueryService.sendRequestThird(withDraw);
	    	            		// withDrawQueryService.withDrawBySXYSuccess(withDraw);
			    				 resultMap.put(SUCCESS, true);
		    				       resultMap.put(MSG, "流程审核成功");
	    	            	 }catch(Exception e){
	
	        	            	 resultMap.put(SUCCESS, false);
	        	            	 resultMap.put(MSG, e.getMessage());
	    	            	 }
	    	            /* }else{
	    	            	 resultMap.put(SUCCESS, false);
	    	            	 resultMap.put(MSG, "账户余额不足,账户余额是:"+balance);
	    	             }*/
    				 }
    			 }
    		 } 
             
    	 }catch (Exception e) {
    		 e.printStackTrace();
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
         }
         return resultMap;
    }
    
}
