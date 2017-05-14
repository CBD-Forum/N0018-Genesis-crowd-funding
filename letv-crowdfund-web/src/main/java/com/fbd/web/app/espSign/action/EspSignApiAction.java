package com.fbd.web.app.espSign.action;

import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.redis.IRedisDao;
import com.fbd.core.app.signature.service.IEspSignApiService;
import com.fbd.core.app.signature.service.impl.EspSignApiServiceImpl;
import com.fbd.core.app.user.model.UserSignatureOrgModel;
import com.fbd.core.app.user.model.UserSignaturePersonModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.exception.VerifyCodeException;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
@Controller
@Scope("prototype")
@RequestMapping("/espSignApi")
public class EspSignApiAction extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(EspSignApiAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private IEspSignApiService espSignApiService;
    @Resource
    private IRedisDao redisDao;
    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
	/**
	 * 发送短信验证码
	 * @return
	 */
    @RequestMapping(value = "/sendVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendCreateUserVerifyCode(HttpServletRequest request,String usage) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	String userId = this.getUserId(request);
        	String valiCode = request.getParameter("valiCode");
        	if(valiCode!=null){
           	 // 验证码验证
            	this.validateCode(request);
        	}
        	logger.info("------------->天威诚信发送短信验证码-usage:"+usage);
        	String authCode = this.espSignApiService.sendVerifyCode(userId, null, usage, request);
            resultMap.put(SUCCESS,true);
            resultMap.put("authCode",authCode);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
	
	/**
	 * 创建用户
	 * @return
	 */
    @RequestMapping(value = "/createUser.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> createUser(HttpServletRequest request,String userType,String authCode,UserSignaturePersonModel userPerson,
			UserSignatureOrgModel userOrg) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	String userId = this.getUserId(request);
        	Certificate cert = null;
        	logger.info("------------->天威诚信创建用户-userId:"+userId);
        	this.espSignApiService.saveCreateSignUser(userId, userType, authCode, userPerson, userOrg, cert, request);
            resultMap.put(SUCCESS,true);
        }catch(ApplicationException e){
        	 resultMap.put(SUCCESS,false);
             resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
	
    /**
     * 查询签章信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/querySignList.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>querySignList(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			String userId = this.getUserId(request);
			List<Map<String,Object>> list = this.espSignApiService.querySignList(userId);
			resultMap.put("signList",list);
			resultMap.put(SUCCESS,true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS,false);
		}
		return resultMap;
	}
	
	
	 
    
    /**
     * 创建合同
     * @param request
     * @return
     */
    @RequestMapping(value = "/createContract.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>createContract(HttpServletRequest request,HttpServletResponse response,String loanNo,String investNo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String userId = this.getUserId(request);
//			String userId ="zwlyzzfd";
			CrowdfundingSupportModel support = null;
			support = this.crowdfundingSupportDao.getByOrderId(investNo);
			if(!"notCreate".equals(support.getContractState())){ //如果合同已创建  直接返回合同编号
				resultMap.put("contractUrl",support.getContractUrl());
				resultMap.put("contractId",support.getContractId());
				resultMap.put(SUCCESS,true);
				return resultMap;
			}
			logger.info("---------->天威诚信创建合同请求开始-loanNo:"+loanNo+"-investNo:"+investNo+"--------------------------");
			String contractId = this.espSignApiService.createContract(loanNo, investNo, userId, request,response);
			//根据投资编号查询合同url
			support = this.crowdfundingSupportDao.getByOrderId(investNo);
			resultMap.put("contractUrl",support.getContractUrl());
			resultMap.put("contractId",contractId);
			resultMap.put(SUCCESS,true);
		}catch(ApplicationException e){
			resultMap.put(SUCCESS,false);
			resultMap.put(MSG,e.getMessage());
		}catch(Exception e){
			resultMap.put(MSG,"创建合同失败!");
			resultMap.put(SUCCESS,false);
		}
		return resultMap;
	}
    
    
    
    /**
     * 签约合同
     * @param request
     * @return
     */
    @RequestMapping(value = "/signContract.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>signContract(HttpServletRequest request,String loanNo,String investNo,String contractId,String authCode){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String userId = this.getUserId(request);
			logger.info("--------------->签署合同请求开始----------------------");
			this.espSignApiService.signContract(loanNo, investNo, userId, contractId, authCode, request);
			CrowdfundingSupportModel support = this.crowdfundingSupportDao.getByOrderId(investNo);
			resultMap.put("contractUrl",support.getContractUrl());
			resultMap.put("contractId",contractId);
			resultMap.put(SUCCESS,true);
		}catch(ApplicationException e){
			resultMap.put(SUCCESS,false);
			resultMap.put(MSG,e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS,false);
			resultMap.put(MSG,"签署合同失败");
		}
		return resultMap;
	}
	
	

    /**
     * 根据投资编号查询已经签署的合同信息
     * @param request
     * @param contractId
     * @return
     */
    @RequestMapping(value = "/queryByInvestNo.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>queryByContractId(HttpServletRequest request,String investNo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String userId = this.getUserId(request);
			String path = this.espSignApiService.getContract(investNo,request);
			resultMap.put("path",path);
			resultMap.put(SUCCESS,true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS,false);
		}
		return resultMap;
	}
    
    
    /**
     * 查询用户的签章信息
     * @param request
     * @param contractId
     * @return
     */
    @RequestMapping(value = "/queryUserSignInfo.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>queryUserSignInfo(HttpServletRequest request,String investNo){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String userId = this.getUserId(request);
			resultMap = this.espSignApiService.queryUserSignInfo(userId);
			resultMap.put(SUCCESS,true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS,false);
		}
		return resultMap;
	}    
    
    
    
    
    
    
	
	
    /**
     * 
     * Description: 验证码校验
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午5:29:23
     */
    private void validateCode(HttpServletRequest request) {
    	
    	String valiCode = request.getParameter("valiCode");
        String mobileTerminalFlag = request.getHeader("mobileTerminalFlag");
        if(!StringUtils.isEmpty(mobileTerminalFlag)){  //终端请求
            String imageId = request.getParameter("imageId");
            if(imageId == null || "".equals(imageId)){
            	throw new VerifyCodeException("图片验证码不能为空");
            }
            if(this.redisDao.exists(imageId)){
            	 String verifyCodeSession = this.redisDao.get(imageId);
                 if (StringUtils.isEmpty(valiCode)
                         || !verifyCodeSession.equalsIgnoreCase(valiCode)) {
                 	throw new VerifyCodeException("图片验证码错误");
                 }else{
                 	this.redisDao.del(imageId);
                 }
            }else{
            	throw new VerifyCodeException("图片验证码错误");
            }
        }else{
            String verifyCodeSession = this.getVerifyCodeFromSession(request);
            if (StringUtils.isEmpty(valiCode)
                    || !verifyCodeSession.equalsIgnoreCase(valiCode)) {
                throw new ApplicationException("图片验证码错误");
            }
        }
    }	
	
    /**
     * Description: 获取session中的验证码
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-9 下午5:23:27
     */
    private String getVerifyCodeFromSession(HttpServletRequest request) {
        Object verifyCodeSession = request.getSession().getAttribute(
                FbdConstants.CAPTCHA_SESSION);
        return null == verifyCodeSession ? "" : verifyCodeSession.toString();
    }
    
    
    
    
    
    
    /**
     * 
     * Description: 查询我要签署的合同列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:33
     */
    @RequestMapping(value = "/getSignSupportList.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getMySupportList(HttpServletRequest request,CrowdfundingSupportModel model,String signType){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            
            if("project".equals(signType)){  //项目方
            	model.setLoanUser(userId);
            	model.setSort("projectSignContractList");
            }else if("investor".equals(signType)){  //投资方
            	model.setSupportUser(userId);
            	model.setSort("investSignContractList");
            }
            
            model.setLoanType("stock");
            model.setPayState("payed");
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getSpportList(model);
            amap.put(MSG,pageList);
            amap.put(SUCCESS, true);
        }catch(Exception e){
            amap.put(SUCCESS, false);
        }
        return amap;
    }

    /**
     * 批量签约合同
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchSignContract.html", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object>batchSignContract(HttpServletRequest request,String loanNo,String authCode){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			String userId = this.getUserId(request);
			this.espSignApiService.batchSignContract(loanNo, userId, authCode, request); 
			resultMap.put(SUCCESS,true);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS,false);
		}
		return resultMap;
	}	
    
    
    
	/**
	 * 创建用户
	 * @return
	 */
   /* @RequestMapping(value = "/createUser.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> createUser(HttpServletRequest request,String userType,String authCode,UserSignaturePersonModel userPerson,
			UserSignatureOrgModel userOrg) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	String userId = this.getUserId(request);
//        	String userId = "q3pk900942";
//        	userPerson.setIdCardNum("130534199103252114");
//        	userPerson.setMobile("18591766104");  //个人手机号应该走注册时的手机号码？
//        	userPerson.setIdentifyField("idCardNum"); //身份证
//        	userPerson.setPersonalName("张立福2");
        	
//        	userOrg.setOrgCode("MA006D8G7");
//        	userOrg.setIdentifyField("idCardNum");
//        	userOrg.setOrgName("2016-2017 NBIChain SunCrowdfund System");
//        	userOrg.setUsci("8111010SDA006D8G7N");
//        	userOrg.setBusinessNum("YYZZ20160817001");
//        	userOrg.setLegalPersonName("侯锦翼");
//        	userOrg.setTransactorName("侯锦翼");
//        	userOrg.setTransactoridCardNum("150426199005193227");
//        	userOrg.setTransactorMobile("18511300138");
        	Certificate cert = null;
        	logger.info("------------->天威诚信创建用户-userId:"+userId);
        	this.espSignApiService.saveCreateSignUser(userId, userType, authCode, userPerson, userOrg, cert, request);
            resultMap.put(SUCCESS,true);
        }catch(ApplicationException e){
        	 resultMap.put(SUCCESS,false);
             resultMap.put(MSG,e.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }*/

}
