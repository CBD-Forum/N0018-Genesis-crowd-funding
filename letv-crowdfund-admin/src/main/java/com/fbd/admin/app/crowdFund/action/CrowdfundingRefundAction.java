package com.fbd.admin.app.crowdFund.action;

import java.util.Date;
import java.util.HashMap;
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

import com.fbd.admin.app.crowdFund.service.ICrowdfundingService;
import com.fbd.admin.app.crowdFund.service.impl.CrowdfundingServiceImpl;
import com.fbd.admin.app.user.service.IUserService;
import com.fbd.admin.app.verifycode.service.IVerifyCodeService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockChain.service.impl.BlockChainQueryServiceImpl;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.util.spring.SpringUtil;


@Controller
@Scope("prototype")
@RequestMapping("/crowdfundingRefund")
public class CrowdfundingRefundAction extends BaseAction {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingRefundAction.class);
 
    @Resource
    private IUserService userService;
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private IVerifyCodeService verifyCodeService;
	@Resource
	private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    @Resource
    private ICrowdfundingService crowdfundingService;
    
    /**
     * 
     * Description: 审核产品项目申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateRefundState.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String,Object> updateRefundState(CrowdfundRefundAuditModel model) {
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	try {
    		String operator = MyRequestContextHolder.getCurrentUser().getAdminId();
    		model.setAuditor(operator);
	    	this.crowdfundingService.updateRefundState(model);
	    	resultMap.put(SUCCESS, true);
	    } catch (Exception e) {
	        logger.error(e.getMessage());
	        resultMap.put(SUCCESS, false);
	        resultMap.put(MSG,"审核产品项目申请退款失败");
	    }
	    	return resultMap;
	}
    
    //查询退款审核通过状态
    @RequestMapping(value = "/selectRefundPassedStatus.html",method=RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectRefundPassedStatus(HttpServletRequest request,String orderId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(orderId);
    		resultMap.put(SUCCESS, true);
    		resultMap.put(MSG,FbdCoreConstants.refundState.refund_success.equals(supportModel.getRefundState())?true:false);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG,false);
    	}
    	return resultMap;
    }  
    
    
    //查询退款审核拒绝状态
    @RequestMapping(value = "/selectRefundPassedRefuseStatus.html",method=RequestMethod.POST)
    public @ResponseBody Map<String,Object> selectRefundPassedRefuseStatus(HttpServletRequest request,String orderId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try{
    		CrowdfundingSupportModel supportModel = this.crowdfundingSupportDao.getByOrderId(orderId);
    		resultMap.put(SUCCESS, true);
    		resultMap.put(MSG,FbdCoreConstants.refundState.refuse.equals(supportModel.getRefundState())?true:false);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG,false);
    	}
    	return resultMap;
    } 
    
}
