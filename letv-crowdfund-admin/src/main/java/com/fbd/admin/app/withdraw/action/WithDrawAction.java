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
import com.fbd.admin.app.todo.service.ITodoService;
import com.fbd.admin.app.withdraw.service.IWithDrawService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;

@Controller
@Scope("prototype")
@RequestMapping("/withdraw")
public class WithDrawAction extends BaseAction {
    
    private static final Logger logger = LoggerFactory.getLogger(WithDrawAction.class);

    /**
     * 
     */
    private static final long serialVersionUID = 8045242887137723212L;
    
    @Resource
    private IWithDrawService withDrawService;
    @Resource
    private IWithDrawDao withDrawDao;
    @Resource
    private ITodoService todoService;
    @Resource
    private IUserBillService userBillService;
    @Resource
    private IWithDrawQueryService withDrawQueryService;
    @Resource
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private IBlockChainQueryService blockChainQueryService;
    
    /**
     * Description: 查询用户提现列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午3:22:45
     */
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getWithDrawPage(){
        Map<String, Object> param = new HashMap<String, Object>();
        param.putAll(this.getRequestParam());
        param.putAll(this.getPageParam());
        SearchResult<Map<String, Object>> withDrawPage = withDrawService.getWithDrawPage(param);
        return withDrawPage;
    }
    
    
    @RequestMapping(value = "/auditPass.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendAuditPass(String orderId,String auditOpinion){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_APPROVE;
        try {
            String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
            WithDrawModel withDraw = withDrawService.getByOrderId(orderId);
            withDraw.setState(FbdCoreConstants.withDrawState.passed.getValue());
            withDraw.setAuditOpinion(auditOpinion);
            withDraw.setAuditor(operator);
            withDrawService.updateWithDrawAfterAudit(withDraw);
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_WITHDRAWAUDIT, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"提现单号:"+orderId);
            
            todoService.modifyTodoByEventObj(FbdCoreConstants.withDrawState.auditing.getValue(),
                    orderId, 
                    operator,
                    FbdCoreConstants.withDrawState.passed.getValue());
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "提现审核失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_WITHDRAWAUDIT, operateType, 
                    AuditLogConstants.RESULT_FAIL,"提现单号:"+orderId+";错误信息："+e.getMessage());
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/auditRefuse.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendAuditRefuse(String orderId,String auditOpinion){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String operateType = AuditLogConstants.TYPE_REJECT;
        try {
            String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
            WithDrawModel withDraw = withDrawService.getByOrderId(orderId);
            withDraw.setState(FbdCoreConstants.withDrawState.refuse.getValue());
            withDraw.setAuditOpinion(auditOpinion);
            withDraw.setAuditor(operator);
            withDrawService.updateWithDrawAfterAudit(withDraw);
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_WITHDRAWAUDIT, operateType, 
                    AuditLogConstants.RESULT_SUCCESS,"提现单号:"+orderId);
            
            todoService.modifyTodoByEventObj(FbdCoreConstants.withDrawState.auditing.getValue(),
                    orderId, 
                    operator,
                    FbdCoreConstants.withDrawState.refuse.getValue());
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "提现审核失败");
            AuditLogUtils.log(AuditLogConstants.MODEL_WITHDRAWAUDIT, operateType, 
                    AuditLogConstants.RESULT_FAIL,"提现单号:"+orderId+"；错误信息："+e.getMessage());
        }
        return resultMap;
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
    public @ResponseBody Map<String, Object> auditPass(WithDrawModel withdraw){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	 try {
             WithDrawModel withDraw = withDrawService.getByOrderId(withdraw.getOrderId());
             double amount = withDraw.getAmt();
             //运营审核
             if("operator".equals(withdraw.getAuditType())){
    			 withDraw.setOperatorAuditTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
    			 withDraw.setOperator( MyRequestContextHolder.getCurrentUser().getEmployeeNo());
    			 withDraw.setOperatorOpinion(withdraw.getOperatorOpinion());
    			 if("operator_refuse".equals(withdraw.getState())){  //运营审核拒绝
    				 withDraw.setState(withdraw.getState());
    				 //将众筹令由平台中间账户转到用户账户(同时记录用户提现拒绝账单)
    				 withDraw.setStateName("运营审核不通过");
    				 String requestId = PKGenarator.getOrderId();
    				 withDrawService.saveWithDrawAuditRefuse(withDraw,requestId);
    				 resultMap.put(SUCCESS, true);
				     resultMap.put(MSG, "运营审核不通过");
				     resultMap.put("requestId",requestId);
    			 }else{
    				 String balance = withDrawQueryService.QueryBalanceSxy();
    	             if(balance!=null && amount<=Double.valueOf(balance)){
	    				 withDraw.setState(withdraw.getState());
	    				 withDraw.setStateName("运营审核通过");
	    				 this.withDrawDao.updateBySelective(withDraw);
	    				 resultMap.put(SUCCESS, true);
	    	             resultMap.put(MSG, "审核成功");
    	             }else{
    	            	 resultMap.put(SUCCESS, false);
    	            	 resultMap.put(MSG, "账户余额不足,账户余额为:"+balance);
    	             }
    			 }
    		 }else if("financial".equals(withdraw.getAuditType())){ //财务审核
    			 withDraw.setFinancialOpinion(withDraw.getFinancialOpinion());
    			 withDraw.setFinancialAuditor(MyRequestContextHolder.getCurrentUser().getEmployeeNo());

    			 if("refuse".equals(withdraw.getState())){  //财务审核拒绝
    				 withDraw.setFinancialAuditTime(DateUtil.dateTime2Str(new Date(), DateUtil.DEFAULT_DATE_TIME_FORMAT));
    				 withDraw.setState(withdraw.getState());
    				 withDraw.setStateName("财务审核不通过");
    				 //将众筹令由平台中间账户转到用户账户(同时记录用户提现拒绝账单)
    				 String requestId = PKGenarator.getOrderId();
    				 withDrawService.saveWithDrawAuditRefuse(withDraw,requestId);
    				 resultMap.put(SUCCESS, true);
				     resultMap.put(MSG, "流程审核成功");
				     resultMap.put("requestId",requestId);
    			 }else{  //财务审核通过
    				 //财务审核通过后将众筹令由平台中间账户转账到平台如账户
    				 if(FbdCoreConstants.BLOCK_CHAIN_IS_MOCK){
    					 String balance = withDrawQueryService.QueryBalanceSxy();
	    	             if(balance!=null && amount<=Double.valueOf(balance)){
    				         this.withDrawService.saveWithDrawAuditPassed(withDraw);
    				         resultMap.put(SUCCESS, true);
    				         resultMap.put(MSG, "流程审核成功");
	    	             }else{
  	    	            	 resultMap.put(SUCCESS, false);
  	    	            	 resultMap.put(MSG, "账户余额不足,账户余额是:"+balance);
  	    	            	 throw new ApplicationException("账户余额不足,账户余额是:"+balance);
  	    	             }  
    				 }else{
	    				 String balance = withDrawQueryService.QueryBalanceSxy();
	    	             if(balance!=null && amount<=Double.valueOf(balance)){
	    	            	 try{
			    				 //调用三方支付将钱转账到用户账户
			    				 withDrawQueryService.sendRequestThird(withDraw);
			    				 resultMap.put(SUCCESS, true);
		    				     resultMap.put(MSG, "流程审核成功");
	    	            	 }catch(Exception e){
	        	            	 resultMap.put(SUCCESS, false);
	        	            	 resultMap.put(MSG, e.getMessage());
	    	            	 }
	    	             }else{
	    	            	 resultMap.put(SUCCESS, false);
	    	            	 resultMap.put(MSG, "账户余额不足,账户余额是:"+balance);
	    	             }
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
    
    private Map<String, Object> confirmIsSuccess(String orderId,String type){
    	BlockAsynTranModel model = new BlockAsynTranModel();
    	model.setParentId(orderId);
    	model.setType(type);
    	long count = blockAsynTranService.selectSuccessCount(model);
    	Map<String,Object>map = new HashMap<String,Object>();
    	if(count>0){
    		map.put(SUCCESS, true);
    		map.put(MSG, "流程审核成功");
    	}else{

    		map.put(SUCCESS, false);
    		map.put(MSG, "审核失败，请联系管理员");
    	}
    	return map;
    }

    
    
    /**
     * 查询事件是否成功
     * @param request
     * @param response
     */
    @RequestMapping(value = "/selectQueryIsSuccess.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> queryResult(String requestId) {
    	String status = blockChainQueryService.searchIsSucess(requestId);
    	Map<String,Object>map = new HashMap<String,Object>();
    	if(status!=null && status.equals(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS)){
      	    map.put(SUCCESS, true);
      	    map.put(MSG, "审核不同意成功！");
    	}else if(status!=null && status.equals(BlockChainCore.ResultStatus.FAIL)){
    		 map.put(SUCCESS, true);
       	     map.put(MSG, "审核不同意失败！");
    	}else{
    		map.put(SUCCESS, false);
    		map.put(MSG, "");
    	}
    	return map;
    } 	
    
    /**
     * 查询事件是否成功
     * @param request
     * @param response
     */
    @RequestMapping(value = "/withDrawIsSuccess.html", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> withDrawIsSuccess(String orderId) {
    	long count = withDrawDao.selectSuccessCount(orderId);
    	Map<String,Object>map = new HashMap<String,Object>();
    	if(count>0){
      	    map.put(SUCCESS, true);
      	    map.put(MSG, "审核同意成功！");
    	}else{
    		map.put(SUCCESS, false);
    		map.put(MSG, "");
    	}
    	return map;
    } 	
   
}
