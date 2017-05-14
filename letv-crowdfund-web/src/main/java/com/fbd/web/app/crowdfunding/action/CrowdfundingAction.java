/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingAction.java 
 *
 * Created: [2015-3-27 下午7:03:44] by haolingfeng
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
 * ProjectName: rain-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.crowdfunding.action;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fbd.core.app.bill.service.ISystemBillService;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundDailyIncomeModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundRefundAuditModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingPraiseModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingProgressModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.StockBackSetModel;
import com.fbd.core.app.crowdfunding.service.IDailyIncomeJobService;
import com.fbd.core.app.letvPay.common.LetvPayConstants;
import com.fbd.core.app.reward.model.RewardAssignModel;
import com.fbd.core.app.sxyPay.common.SxyPayConstants;
import com.fbd.core.app.thirdtrusteeship.service.ITrusteeshipOperationService;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserUploadFileModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.ftp.FTPManagerFactory;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.SendMessageUtil;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.crowdfunding.service.ICrowdfundDailyIncomeService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.user.service.IUserService;
import com.fbd.web.app.verifycode.service.IVerifyCodeService;

/** 
 * Copyright (C) 201getCrowdDetail.html
 * Description: 众筹项目
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfunding")
public class CrowdfundingAction extends BaseAction{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingAction.class);

    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private ICrowdfundingBackSetDao crowdfundingBackSetDao;
    @Resource
    private IUserService userService;
    
    @Resource
    private IBusinessConfigDao businessConfigDao;
    @Resource
    private IVerifyCodeService verifyCodeService;
    @Resource
    private IBlockChainQueryService blockChainQueryService;
    @Resource
    private ITrusteeshipOperationService trusteeshipOperationService;
    @Resource 
    private IBlockAsynTranService blockAsynTranService;
    @Resource
    private ICrowdfundDailyIncomeService crowdfundDailyIncomeService;
    @Resource
    private IUserBillService userBillService ;
    @Resource
    private ISystemBillService systemBillService ;
    @Resource
    private IBlockChainQueryService blockChainQuerySerivce;
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    @Resource
    private IDailyIncomeJobService dailyIncomeJobService;
    @Resource
    private ICrowdfundProductTransferDao crowdfundProductTransferDao;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    
    /**
     * Description:查询意向金支付尾款截止时间
     * @param 
     * @return Map<String,Object>
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:22
     */
    @RequestMapping(value = "/getIntentionOverDueTime.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getIntentionOverDueTime(String loanNo,String investId,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	
        	BusinessConfigModel config = businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_DEFAULT_REFUND_DAY);
        	if(config==null){
        		throw new ApplicationException("意向金尾款支付时间获取失败，请联系客服！");
        	}
        	Integer yxjwyDay = Integer.valueOf(config.getCode());
        	Date financeEndDate = DateUtil.addDate(new Date(), yxjwyDay);
        	resultMap.put("overDueTime",DateUtil.date2Str(DateUtil.getDayMax(financeEndDate), DateUtil.DEFAULT_DATE_TIME_FORMAT));
        	resultMap.put(SUCCESS, true);
        }catch(Exception e){
        	e.printStackTrace();
        	resultMap.put(SUCCESS, false);
        	resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }     
    
    /**
     * Description:查询用户是否点赞或者收藏过项目
     * @param 
     * @return Map<String,Object>
     * @throws 
     * Create Date: 2015-3-27 下午7:16:22
     */
    @RequestMapping(value = "/getUserOperateLoan.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserOperateLoan(String loanNo,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String userId = this.getUserId(request);
        	resultMap = this.crowdfundingService.getUserOperateLoan(userId, loanNo);
        	resultMap.put(SUCCESS, true);
        }catch(Exception e){
        	e.printStackTrace();
        	resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 
    
    
    /**
     * Description:查询众筹项目列表 
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:22
     */
    @RequestMapping(value = "/getPageCrowdList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPageCrowdList(CrowdfundingModel model,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	
        	String loginUserId = "";
        	try{
        		loginUserId = this.getUserId(request);
        	}catch(Exception e){
//        		logger.info("用户未登录");
        	}
        	if("stock".equals(model.getLoanType())){
        		model.setSort("auditTime");
        	}
        	//如果是预热的项目列表，则按照复审的时间(预热的时间)
        	if("preheat".equals(model.getLoanProcess())){
        		if(StringUtil.isEmpty(model.getSort())){
        			model.setSort("preheat");
        			model.setOrder("desc");
        		}
        	}
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getPageList(model,loginUserId);
            double dailyProfitRate = 0.0;
            try{
            	//查询当前每日的预计收益率
            	BusinessConfigModel config = this.businessConfigDao.getBusConfigByDisplayName("DAILY_INCOME_RATE");
            	if(config!=null){
            		dailyProfitRate = Double.parseDouble(config.getCode());
            	}
            }catch(Exception e){
            	e.printStackTrace();
            }
            resultMap.put("dailyProfitRate",dailyProfitRate); 
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询预热的众筹项目列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:22
     */
    @RequestMapping(value = "/getPreheatCrowdList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPreheatCrowdList(CrowdfundingModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setSort("releaseTime");
            model.setLoanState(CrowdfundCoreConstants.crowdFundingState.preheat.getValue());
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getPageList(model,null);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:查询首页的众筹项目列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:22
     */
    @RequestMapping(value = "/getIndexCrowdList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getIndexCrowdList(CrowdfundingModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setSort("defaultSort");
            model.setLoanStateIn("1");
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getPageList(model,null);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    /**
     * 查询全款支付是否成功
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectPayIsSuccess.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> selectPayIsSuccess(String orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
         
            long count = this.crowdfundingService.selectPayIsSuccess(orderId);
            if(count>0){
            	 resultMap.put(SUCCESS, true);
            }else{
            	 resultMap.put(SUCCESS, false);
            }
           
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    /**
     * 查询意向金支付是否成功
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/selectPayIntentionIsSucess.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> selectPayIntentionIsSucess(String orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
         
            long count = this.crowdfundingService.selectPayIntentionIsSucess(orderId);
            if(count>0){
            	 resultMap.put(SUCCESS, true);
            }else{
            	 resultMap.put(SUCCESS, false);
            }
           
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    /**
     * 查询意向金尾款支付是否成功
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/selectIntentionEndIsSucess.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> selectIntentionEndIsSucess(String orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
         
            long count = this.crowdfundingService.selectIntentionEndIsSucess(orderId);
            if(count>0){
            	 resultMap.put(SUCCESS, true);
            }else{
            	 resultMap.put(SUCCESS, false);
            }
           
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    /**
     * 
     * Description: 查询众筹详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:03:52
     */
    @RequestMapping(value = "/getCrowdDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdDetail(String loanNo,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String userId = "";
        	try{
        		userId = this.getUserId(request);
        	}catch(Exception e){
        		logger.info("=======当前用户没有登录========");
        	}
            Map<String,Object> loanDetail = this.crowdfundingService.getCrowdFundDetail(loanNo,userId);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, loanDetail);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getCrowdPhotos.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdPhotos(String loanNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String,Object> crowdPhotos = this.crowdfundingService.getCrowdPhotos(loanNo);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, crowdPhotos);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description:根据id查询众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:41:43
     */
    @RequestMapping(value = "/getCrowdFundById.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCrowdFundById(String loanNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,this.crowdfundingService.getCrowdfundByLoanNo(loanNo));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:更新众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/updateCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateCrowdFunding(HttpServletRequest request,CrowdfundingModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
           
            this.checkBeforeUpdate(userId, model.getLoanNo());
            
            //更新项目信息
            CrowdfundingModel qmodel = crowdfundingService.getCrowdfundByLoanNo(model.getLoanNo());
            model.setId(qmodel.getId());
            this.crowdfundingService.updateCrowdFund(model);
            
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 发布众筹项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/saveCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFunding(HttpServletRequest request,CrowdfundingModel model,CrowdfundDetailModel crowdfundDetail ){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setLoanUser(userId);
            this.crowdfundingService.saveCrowdfunding(model,crowdfundDetail);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, model.getLoanNo());
        }catch(ApplicationException e){
        	 resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"项目更新失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 更新用户资料
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午3:05:44
     */
    @RequestMapping(value = "/updateUserStuff.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateUserStuff(HttpServletRequest request,CrowdfundUserStuffModel userStuff,UserModel user){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            //更新用户资料
            userStuff.setUserId(userId);
            this.crowdfundingService.updateCrowdfundUserStuff(userStuff);
//            //更新用户信息
//            user.setUserId(userId);
//            userService.updateByUserId(user);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"更新用户资料失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:公司认证 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-5-19 下午4:30:59
     */
    @RequestMapping(value = "/updateCompanyStuff.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateCompanyStuff(HttpServletRequest request,CrowdfundUserStuffModel userStuff){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            //更新公司资料
            userStuff.setUserId(userId);
            this.crowdfundingService.updateCrowdfundUserStuff(userStuff);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description: 上传项目的文件
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:34:22
     */
    @RequestMapping(value = "/uploadLoanFile.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> handleFormUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String errorMsg = "上传图片未成功，请检查图片格式和大小!";
        
        //保存  
        try {  
            String path = CrowdfundCoreConstants.CROWDFUNDING_PHOTO_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            if ("".equals(fileName)) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            long size = file.getSize();
            if(size>5242880){
            	  resultMap.put(SUCCESS,false);
                  resultMap.put(MSG, "上传图片大小不超过5M");
                  return resultMap;
            }
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String photoUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
                resultMap.put(SUCCESS,true);
                resultMap.put(MSG,photoUrl);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
            }
        } catch (Exception e) {  
            e.printStackTrace();
            logger.error(e.getMessage());  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG, errorMsg);
        }  
        return resultMap;   
    }  
    
    
    /**
     * 
     * Description: 上传项目的文件并保存
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:34:22
     */
    @RequestMapping(value = "/uploadUserFile.html", method = RequestMethod.POST)  
    public @ResponseBody
    Map<String, Object> uploadUserFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {  
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String errorMsg = "上传图片未成功，请检查图片格式和大小!";
        //保存  
        try {  
            String userId = this.getUserId(request);
            String path = CrowdfundCoreConstants.CROWDFUNDING_PHOTO_FILE + DateUtils.dateToString(new Date(), null)+"/";  
            String fileName = file.getOriginalFilename();
            if ("".equals(fileName)) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            String suffix=fileName.substring(fileName.lastIndexOf("."));
            List<String> allowSuffix = new ArrayList<String>();
            allowSuffix.add(".jpg"); 
            allowSuffix.add(".png");
            allowSuffix.add(".gif");
            allowSuffix.add(".jpeg");
            if (!allowSuffix.contains(suffix.toLowerCase())) {
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG, errorMsg);
                return resultMap;
            }
            String picId=PKGenarator.getId();
            String newfileName=picId+suffix;
            String photoUrl = path + newfileName;
            boolean flag = FTPManagerFactory.getFtpManager().uploadFile(path, newfileName, file.getInputStream());
            if(flag){//上传成功
                String fileType = request.getParameter("fileType");
                
                UserUploadFileModel userFile = new UserUploadFileModel();
                userFile.setFileType(fileType);
                userFile.setFileName(fileName);
                userFile.setFileUrl(photoUrl);
                userFile.setUserId(userId);
                this.crowdfundingService.saveUserUploadFile(userFile);
                
                resultMap.put(SUCCESS,true);
                resultMap.put(MSG,photoUrl);
            }else{
                resultMap.put(SUCCESS,false);
                resultMap.put(MSG,errorMsg);
            }
        } catch (Exception e) {  
            e.printStackTrace();
            logger.error(e.getMessage());  
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,errorMsg);
        }  
        return resultMap;   
    }  
    /**
     * 判断指定项目是否有抽奖的回报设置
     * @param request
     * @param loanNo
     * @return 存在true;不存在false;
     */
    @RequestMapping(value = "/checkLoanIsPrizeDrawFlag.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> checkLoanIsPrizeDrawFlag(HttpServletRequest request,String loanNo){
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		boolean result=false;
            this.getUserId(request);
            CrowdfundingBackSetModel model=new CrowdfundingBackSetModel();
            model.setLoanNo(loanNo);
            model.setPrizeDrawFlag("1");
            List<Map<String,Object>> list=crowdfundingBackSetDao.getList(model);
    		if(list!=null&&list.size()>0){
    			result=true;
    		}
    		resultMap.put(SUCCESS, true);
    		resultMap.put(MSG, result);
    	}catch(ApplicationException e){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
    	}catch(Exception e){
    		e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"查询异常  "+e.getMessage());    		
    	}
    	return resultMap;
    }
    
    /**
     * 
     * Description: 众筹项目回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/saveBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveCrowdFundingBackSet(HttpServletRequest request,CrowdfundingBackSetModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            
            this.checkBeforeUpdate(userId, model.getLoanNo());
            
            this.crowdfundingService.saveCrowdfundingBackSet(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    /**
     * 每日收益
     * @param request
     * @return
     */
    @RequestMapping(value = "/interestTransferS2S.html", method = RequestMethod.POST)
    public @ResponseBody
    void interestTransferS2S(HttpServletRequest request){
    	try{
	    	logger.info("=============每日收益接口异步回调开始调用============");
	        Map<String, String> map = this.getBlockChainParamsStr();
	        logger.info("=============每日收益支付接口通知参数："+map);
	        
	        String transactionID =  map.get("transactionID").toString();
	        String transferNO =  map.get("transferNO").toString();
	        String status = map.get("status")== null?"":map.get("status").toString();
	        String requestID = map.get("requestID");
	        Thread.sleep(BlockChainCore.THREAD_SLEEP_TIME);
	        
	        //查询事务异步通知信息
	        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
	        if(blockAsynTran!=null){
	            if(BlockChainCore.ResultStatus.FAIL.equals(status)){
	             	return ;
	             }else{
	                 blockAsynTran.setStatus(status);
	                 blockAsynTran.setTranId(transactionID);
	                 this.blockAsynTranDao.updateBySelective(blockAsynTran);
	                 String type="";   
	         		 Timer timer = new Timer();
	             	 if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
	             		timer.schedule( new MyTask(timer,transactionID,status,type,transferNO,requestID), 2000,2000);	
	
	             	}
	             }
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
  			
    }
    
    /**
     * 
     * Description: 股权众筹回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/saveStockBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveStockBackSet(HttpServletRequest request,StockBackSetModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            
            this.checkBeforeUpdate(userId, model.getLoanNo());
            
            this.crowdfundingService.updateStockBackSet(model);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"保存失败");
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/getStockBack.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getStockBack(String loanNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	StockBackSetModel stockBack = this.crowdfundingService.getStockBack(loanNo);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,stockBack);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    /**
     * 
     * Description: 查询回报设置列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:32:41
     */
    @RequestMapping(value = "/getBackSetList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getBackSetList(CrowdfundingBackSetModel model,HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setSort("amt");
            String siteUserId = request.getParameter("siteUserId");
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getBackSetList(model,siteUserId);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
   
    
    
    /**
     * 
     * Description: 查询回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:32:41
     */
    @RequestMapping(value = "/getBackSetById.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getBackSetById(String id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,this.crowdfundingService.getBackSetById(id));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getBackSetByBackNo.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getBackSetByBackNo(String backNo,String loanNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,this.crowdfundingService.getBackSetByBackNo(loanNo,backNo));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 编辑回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/updateBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateBackSet(HttpServletRequest request,CrowdfundingBackSetModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            CrowdfundingModel qModel = this.crowdfundingService.getCrowdfundByLoanNo(model.getLoanNo());
            if(!userId.equals(qModel.getLoanUser())){
                throw new ApplicationException("只有发起人自己可以更新项目");
            }
            if(!qModel.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.add.getValue())){
                throw new ApplicationException("只有草稿状态时才可以更新项目");
            }
            this.crowdfundingService.updateBackSet(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 删除回报设置
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/deleteBackSet.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteBackSet(HttpServletRequest request,String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            CrowdfundingBackSetModel backModel = this.crowdfundingService.getBackSetById(id);
            CrowdfundingModel qModel = this.crowdfundingService.getCrowdfundByLoanNo(backModel.getLoanNo());
            if(!userId.equals(qModel.getLoanUser())){
                throw new ApplicationException("只有发起人自己可以更新项目");
            }
            if(!qModel.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.add.getValue())){
                throw new ApplicationException("只有草稿状态时才可以更新项目");
            }
            this.crowdfundingService.deleteBackSet(id);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:更新众筹明细项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/updateCrowdFundDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateCrowdFundDetail(HttpServletRequest request,CrowdfundingModel crowdfunding,CrowdfundDetailModel model,String loanPhotos){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            
            System.out.println(model.getLoanTeam());
            
            this.checkBeforeUpdate(userId, model.getLoanNo());
            //更新项目详情信息
            this.crowdfundingService.updateLoanDetail(model);
            
            CrowdfundingModel qModel = this.crowdfundingService.getCrowdfundByLoanNo(model.getLoanNo());
            if(!"stock".equals(qModel.getLoanType())){  //如果不是股权项目则更新图片
            	//更新项目图片
                this.crowdfundingService.updateLoanPhoto(loanPhotos, model.getLoanNo());
            }
           //更新项目信息
            crowdfunding.setId(qModel.getId());
           this.crowdfundingService.updateCrowdFund(crowdfunding); 
            if("submit".equals(crowdfunding.getLoanState())){
                sendSubmitMessage( qModel);

            }
            
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    private void checkBeforeUpdate(String userId,String loanNo){
        CrowdfundingModel qModel = this.crowdfundingService.getCrowdfundByLoanNo(loanNo);
        if(!userId.equals(qModel.getLoanUser())){
            throw new ApplicationException("只有发起人自己可以更新项目");
        }
        
        if(!qModel.getLoanState().equals(CrowdfundCoreConstants.crowdFundingState.add.getValue())){
            throw new ApplicationException("只有草稿状态时才可以更新项目");
        }
    }
    
    /**
     * 
     * Description:提交众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/submit.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> submitCrowdFunding(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            CrowdfundingModel model = crowdfundingService.getCrowdfundByLoanNo(loanNo);
            model.setLoanState(CrowdfundCoreConstants.crowdFundingState.submit.getValue());
            this.crowdfundingService.updateCrowdFund(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"提交失败");
        }
        return resultMap;
    }
    /**
     * 
     * Description: 发送短信站内信
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午4:55:53
     */
    private void sendSubmitMessage(  CrowdfundingModel model){
        Map<String, String> params = new HashMap<String,String>();
        params.put("loanName",model.getLoanName());
      /*  try{
            logger.info("发送申请发布项目手机短信开始");
            String nodeCode = "";
          
             nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_APPLY_MOBILE;
            
            if(!StringUtil.isEmpty(nodeCode)){
                SendMessageUtil.sendMobileMessage(nodeCode, model.getLoanUser(), params);
            }
            logger.info("发送申请发布项目手机短信结束");
        }catch(Exception e){
            logger.error("发送申请发布项目手机短信失败，"+e.getMessage());
        }*/
         try{
             logger.info("发送申请发布项目站内信开始");
             String nodeCode = "";
             nodeCode = FbdCoreConstants.NODE_TYPE_LOAN_APPLY_MSG;
            
             if(!StringUtil.isEmpty(nodeCode)){
                 SendMessageUtil.sendStationMessage(nodeCode, FbdCoreConstants.STATION_MSG_TYPE_LOAN_MSG, model.getLoanUser(), params);
             }
             logger.info("发送申请发布项目站内信结束");
         }catch(Exception e){
             logger.error("发送申请发布项目站内信失败，"+e.getMessage());
         }
    }
    
    /**
     * 
     * Description: 查询项目进展
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:43:10
     */
    @RequestMapping(value = "/getProgessList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getProgessList(CrowdfundingProgressModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setState(CrowdfundCoreConstants.crowdFundAuditState.passed);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getProgessList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
 
    
    /**
     * 
     * Description:查询项目的支持列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getSupportList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getSupportList(CrowdfundingSupportModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            CrowdfundingModel crow = this.crowdfundingService.getByloanNo(model.getLoanNo());
            //股权项目包括意向金支付的和全款支付的
	        if("stock".equals(crow.getLoanType())){
	          	model.setMySupport("stockSupportList");
	        }else{
	            model.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
	        }
            model.setSort("supportTime");
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getSpportList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description:提交评论 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 上午11:51:57
     */
    @RequestMapping(value = "/submitComment.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> submitComment(HttpServletRequest request,CrowdfundingCommentModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setDiscussUser(userId);
            this.crowdfundingService.saveComment(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询项目评论
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:10:14
     */
    @RequestMapping(value = "/getCommentList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCommentList(CrowdfundingCommentModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setState(CrowdfundCoreConstants.crowdFundAuditState.passed);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getCommentList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }   
    
    /**
     * 
     * Description: 查询项目评论数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:10:14
     */
    @RequestMapping(value = "/getCommentCount.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCommentCount(CrowdfundingCommentModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setState(CrowdfundCoreConstants.crowdFundAuditState.passed);
            long commentCount = this.crowdfundingService.getCommentCount(model);
            resultMap.put(SUCCESS, true);
            resultMap.put("commentCount",commentCount);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }       
    
    
    
    
    /**
     * 
     * Description: 点赞
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-8 下午7:02:12
     */
    @RequestMapping(value = "/praiseCrowdfund.html", method = RequestMethod.POST)
    public   @ResponseBody Map<String,Object> praiseCrowdfund(HttpServletRequest request,CrowdfundingPraiseModel model){
//        String sessionId = request.getCookies()[0].getValue();
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    		String userId = this.getUserId(request);
            model.setPraiseSessionId(userId);
            this.crowdfundingService.savePraise(model);
            resultMap.put(SUCCESS,true);
    	}catch(Exception e){
    		e.printStackTrace();
    		resultMap.put(SUCCESS,false);
    		resultMap.put(MSG,e.getMessage());
    	}
    	return resultMap;
    }
    
    
    /**
     * 
     * Description: 发货
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-8 下午7:03:14
     */
    @RequestMapping(value = "/sendBack.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> sendBack(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            model.setSendTime(new Date());
            this.crowdfundingService.updateSupport(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"评论失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description:关注众筹项目 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-10 上午10:09:39
     */
    @RequestMapping(value = "/attentionLoan.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> attentionLoan(HttpServletRequest request,CrowdfundingAttentionModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setAttentionUser(userId);
            model.setAttentionType(CrowdfundCoreConstants.attentionType.loan);
            this.crowdfundingService.saveAttention(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    } 
    
    /**
     * 
     * Description:关注特卖 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-10 上午10:09:51
     */
    @RequestMapping(value = "/attentionGoods.html", method = RequestMethod.POST)
    public  @ResponseBody
    Map<String, Object> attentionGoods(HttpServletRequest request,CrowdfundingAttentionModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setAttentionUser(userId);
            model.setAttentionType(CrowdfundCoreConstants.attentionType.goods);
            this.crowdfundingService.saveAttention(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    } 
    
    /**
     * 
     * Description: 取消关注
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/cancelAttention.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> cancelAttention(HttpServletRequest request,CrowdfundingAttentionModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
       	    String userId = this.getUserId(request);
       	    model.setAttentionUser(userId);
            model.setAttentionType(CrowdfundCoreConstants.attentionType.loan);
            this.crowdfundingService.deleteAttention(model);
            resultMap.put(SUCCESS, true);
       } catch (Exception e) {
           logger.error(e.getMessage());
           resultMap.put(SUCCESS, false);
           resultMap.put(MSG,e.getMessage());
       }
       return resultMap;
    }
    
    /**
     * 
     * Description: 更新众筹支持的发货信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/updateSendInfo.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateSendInfo(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String errorMsg="";
        try {
            String userId = this.getUserId(request);
            CrowdfundingSupportModel support = this.crowdfundingService.getSupportById(model.getId());
            CrowdfundingModel qModel = this.crowdfundingService.getCrowdfundByLoanNo(support.getLoanNo());
            if(!userId.equals(qModel.getLoanUser())){
            	errorMsg = "只有发起人自己可以更新项目";
            	throw new ApplicationException(errorMsg);
            }
            if(!support.getState().equals(CrowdfundCoreConstants.crowdFundOrderState.lended)){
            	errorMsg = "只有放款后才可以输入发货信息";
            	throw new ApplicationException(errorMsg);

            }
            
            /*//add start
            CrowdfundProductTransferModel transferModel =crowdfundProductTransferDao.getBySupportNo(support.getOrderId());
            if(transferModel!=null){
        		//说明有转让记录
            	if(StringUtil.isEmpty(transferModel.getTransferAuditState())){
            		//说明还没购买
            		if(CrowdfundCoreConstants.transferStateFbd.transfering_locking.getValue().equals(transferModel.getTransferState())){
            			throw new ApplicationException("购买操作中不能发货");
            		}else{
            			//转让结束
            			transferModel.setTransferState(CrowdfundCoreConstants.transferStateFbd.transferend.getValue());
                        crowdfundProductTransferDao.update(transferModel);
            		}
            	}else{
                	if(FbdCoreConstants.transferAuditState.auditing.equals(transferModel.getTransferAuditState())){
                		throw new ApplicationException("后台审核中不能发货");
                	}else if(FbdCoreConstants.transferAuditState.refuse.equals(transferModel.getTransferAuditState())){
                		throw new ApplicationException("后台拒绝不能发货");
                	}else if(FbdCoreConstants.transferAuditState.passed.equals(transferModel.getTransferAuditState())){
                		//通过
                	}	
            	}     
            }*/
           
	            support.setSendTime(new Date());
	            support.setState(CrowdfundCoreConstants.crowdFundOrderState.sending);  //
	            support.setSendDelivery(model.getSendDelivery());
	            //support.setOrderId(model.getOrderId());
	            support.setSendOrderId(model.getSendOrderId());
	            errorMsg =  this.crowdfundingService.updateSupportSendInfo(support);
	            if(errorMsg!=null && !"".equals(errorMsg)){
	            	  resultMap.put(MSG,errorMsg);
	                  resultMap.put(SUCCESS, false);
	              	throw new ApplicationException(errorMsg);

	            }
            resultMap.put(SUCCESS, true);
        } catch(ApplicationException e){
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"更新订单发货信息失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询用户资料信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午3:03:52
     * hch(废弃，不知道干嘛用的)
     */
/*    @RequestMapping(value = "/getUserStuffById.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserStuffById(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            CrowdfundUserStuffModel userStuff = this.crowdfundingService.getByUserId(userId).get(0);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, userStuff);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }*/
    
    /**
     * 
     * Description: 删除项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/deleteCrowdFunding.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteCrowdFunding(String loanNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.deleteCrowdfund(loanNo);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"删除失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 删除支持订单
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-27 下午7:16:33
     */
    @RequestMapping(value = "/deleteSupport.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> deleteSupport(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.deleteSupport(id);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"删除失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 统计前台展示数据(已投资总额；项目总数；投资人总数)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getCountInfo.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCountInfo(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	Map<String, Object> map = this.crowdfundingService.getCountInfo();
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, map);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"查询信息失败");
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询用户中奖列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getUserPrizeList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserPrize(HttpServletRequest request,CrowdfundUserPrizeModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	SearchResult<Map<String, Object>> result = this.crowdfundingService.getUserPrizeList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"查询用户中奖列表失败");
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询服务费比列
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getServiceFeeScale.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getServiceFeeScale(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	Map<String, Object> result = this.crowdfundingService.getServiceFeeScale();
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,"查询服务费比列失败");
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description: 产品投资申请退款
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateRefundApplication.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateRefundApplication(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	 String userId = this.getUserId(request);
             UserModel user = this.userService.getUser(userId);
             this.verifyCodeService.checkVerifyCode(request.getParameter("verifyCode").toString(), 
                     FbdConstants.NODE_TYPE_REFUND_MODEL, userId, user.getMobile(), "mobile");
        	
        	this.crowdfundingService.checkBeforeRefundApplication(model.getOrderId());
        	this.crowdfundingService.updateRefundApplication(model);
            resultMap.put(SUCCESS, true);
        }catch (ApplicationException e){
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    @RequestMapping(value = "/test.html", method = RequestMethod.GET)
    public @ResponseBody List<Map<String,Object>> test() {
        Map<String,Object> param = new HashMap<String,Object>();
        List<Map<String,Object>>  list =crowdfundingService.selectIsnvestmentFunds(param);
        return list;
    }  
    /**
     * 
     * Description: 项目方审核投资申请退款(是否同意退款)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateLoanAuditRefund.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateLoanAuditRefund(HttpServletRequest request,CrowdfundRefundAuditModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String userId = this.getUserId(request);
        	model.setAuditor(userId);
        	this.crowdfundingService.updateLoanAuditRefund(model);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询项目方审核投资申请退款记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getLoanAuditRefundList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getLoanAuditRefundList(HttpServletRequest request,CrowdfundRefundAuditModel model){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	List<Map<String,Object>> list = this.crowdfundingService.getLoanAuditRefundList(model);
        	resultMap.put(MSG, list);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description: 分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/sendBonus.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendBonus(String loanNo,double money){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	this.crowdfundingService.checkBeforeBonus(loanNo,money);
            String bonusLoanId = this.crowdfundingService.saveBonus(loanNo,money);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, bonusLoanId);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 用户是否同意分红
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateIsAgreeBonus.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateIsAgreeBonus(String state,String loanBonusId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.saveBonus(state,loanBonusId);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 申请发货
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/updateApplicationDelivery.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateApplicationDelivery(String supportNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            this.crowdfundingService.updateApplicationDelivery(supportNo);
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
    /**
     * 
     * Description: 查询抽奖编号
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getUserPrize.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserPrize(String loanNo,String supportUser){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put(MSG, this.crowdfundingService.getUserPrize(loanNo,supportUser));
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
           logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    /**
     * 
     * Description:退款申请发送手机验证码 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     * Create Date: 2015-4-17 上午10:23:11
     */
    @RequestMapping(value = "/sendMobileRefundVerifyCode.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sendMobileLoginVerifyCode(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            UserModel user = this.userService.getUser(userId);
          //发送验证码
            this.verifyCodeService.sendVerifyCode(userId,user.getMobile(),
                    FbdCoreConstants.userType.P,FbdConstants.NODE_TYPE_REFUND_MODEL);
            resultMap.put(SUCCESS, true);
        } catch (ApplicationException e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, e.getMessage());
        }
        return resultMap;
    }
    
    
    
    /**
     * 
     * Description: 奖励发放明细
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-3 下午3:51:35
     */
    @RequestMapping(value = "/getRewardAssignList.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getRewardAssignList(RewardAssignModel rewardModel){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
         try {
             resultMap.put(MSG, this.crowdfundingService.getRewardPageList(rewardModel));
             resultMap.put(SUCCESS, true);
         } catch (Exception e) {
            logger.error(e.getMessage());
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
         }
         return resultMap;
    	
    }
    
    
    /**
     * 
     * Description: 获取投资所需要配置参数
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
    @RequestMapping(value = "/getDeployMap.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getDeployMap(){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
         try {
             resultMap.put(MSG, this.crowdfundingService.getDeployMap());
             resultMap.put(SUCCESS, true);
         } catch (Exception e) {
            logger.error(e.getMessage());
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
         }
         return resultMap;
    	
    }
    
    /**
     * 
     * Description: 查询投资信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwnebin
     */
    @RequestMapping(value = "/getSupportInfo.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getSupportInfo(String orderId){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
         try {
             resultMap.put(MSG, this.crowdfundingService.getSupportInfo(orderId));
             
             //意向金支付比例
     		 Double yxjPayScale = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode()==null?"0.0":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_PAY_SCALE).getCode());
     		 resultMap.put("yxjPayScale", yxjPayScale);
     		 //意向金支付天数
     		 Integer yxjPayDay = Integer.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode()==null?"0":businessConfigDao.getBusConfig(CrowdfundCoreConstants.INTENTION_CAN_PAY_DAY).getCode());
     		 resultMap.put("yxjPayDay", yxjPayDay);
     		 //平台收取违约意向金比例
     		 Double yxjPlatformRatio = Double.valueOf(businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode()==null?"0.0":businessConfigDao.getBusConfig("IntentionFeeReturnPlatformRatio").getCode());
     		 resultMap.put("yxjPlatformRatio", yxjPlatformRatio);
     		 //意向金支付尾款截止时间(当前时间+意向金尾款支付天数)
     		 String intentionEndTime =DateUtils.dateToString(DateUtil.addDate(new Date(), yxjPayDay),DateUtil.DEFAULT_DATE_TIME_FORMAT);
     		 resultMap.put("intentionEndTime",intentionEndTime);
             resultMap.put(SUCCESS, true);
         } catch (Exception e) {
            logger.error(e.getMessage());
             resultMap.put(SUCCESS, false);
             resultMap.put(MSG,e.getMessage());
         }
         return resultMap;
    	
    }
    //每日收益回调
    @RequestMapping(value = "/receiveTransferS2S.html", method = RequestMethod.POST)
    public void receiveRechargeCallbackS2S(HttpServletRequest request,HttpServletResponse response) {
        logger.info("=============转让接口异步回调开始调用============");
    	Map<String ,Object>map = this.getRequestParams();
		Timer timer = new Timer();

    	if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(map.get("status"))){
            this.receiveTransferCallback(map);
    	}else if(BlockChainCore.ResultStatus.SUBMIT_PROCESS.equals(map.get("status"))){

             //防止并发            
       //     Sche//duledExecutorService pool = Executors.newScheduledThreadPool(1);  
//.schedule((new MyTask(timer,(String)map.get("transactionID"),BlockChainCore.ResultStatus.SUBMIT_PROCESS,"invest")), new Date(), 20000);  
         	//timer.schedule(new MyTask(timer,(String)map.get("transactionID"),BlockChainCore.ResultStatus.SUBMIT_PROCESS,"invest"),20000); 
    	}
			
       
    }
    

    private String receiveTransferCallback(Map<String, Object> map) {
    	String  handlerResult = FbdConstants.RESULT_TRUE;
    	String orderId = map.get("transferNO").toString();
		BlockAsynTranModel blockAsynTranModel = blockAsynTranService.selectByTranId(orderId);
		
		try {
			if(!"success".equals(blockAsynTranModel.getStatus())){
				if(map.get("status").equals("TransactionSuccess")){
						trusteeshipOperationService.updateOperationModel(map.get("transferNO").toString(), 
			                    LetvPayConstants.BusiType.invest, 
			                    null, SxyPayConstants.THIRD_ID, "state", map.get("state").toString());
						
						    CrowdfundDailyIncomeModel model = new CrowdfundDailyIncomeModel();
						    Map<String,Object>paramsMap =crowdfundingService.getSupportInfo(orderId);
						   double supportAmt = Double.parseDouble(paramsMap.get("supportAmt").toString());
						 
							  //查询收益利率
					        Double incomeRate = Double.valueOf(businessConfigDao.getBusConfig(CrowdfundCoreConstants.DAILY_INCOME_RATE).getCode()==null?"0.05":businessConfigDao.getBusConfig(CrowdfundCoreConstants.DAILY_INCOME_RATE).getCode());
					      //每日收益 = 投资金额*收益利率从
			                double dailyIncomeAmt = Arith.mul(incomeRate,supportAmt);
			                
					        model.setId(PKGenarator.getId());
					        model.setIncomeNo(PKGenarator.getOrderId());
					        model.setLoanNo(paramsMap.get("loanNo").toString());
					        model.setSupportNo(orderId);
					        model.setIncomeUser(paramsMap.get("supportUser").toString());
					        model.setSupportAmt(Double.parseDouble(paramsMap.get("supportAmt").toString()));
					        model.setIncomeAmt(dailyIncomeAmt);
					        model.setIncomeRate(incomeRate);
					        model.setIncomeTime(new Date());
					        crowdfundDailyIncomeService.save(model);
					      //用户进账
					        this.userBillService.addBill(paramsMap.get("supportUser").toString(),
					                FbdCoreConstants.userTradeType.dailyIncome.getValue(), 
					                dailyIncomeAmt, FbdCoreConstants.tradeDirection.in.getValue(), model.getLoanNo(),
					                "发放每日收益", null, model.getIncomeNo());
					      //平台出账
					        this.systemBillService.addBill(FbdCoreConstants.systemTradeType.dailyIncome.getValue(),
					                dailyIncomeAmt, FbdCoreConstants.tradeDirection.out.getValue(), model.getLoanNo(),
					                "发放每日收益", model.getIncomeNo());
				
				}else{
					Timer timer = new Timer();
		    		//timer.schedule( new MyTask(timer,(String)map.get("transactionID"),BlockChainCore.ResultStatus.SUBMIT_PROCESS,""), 1000,1000);	

				}
			}	
		} catch(ApplicationException e){
			throw new ApplicationException(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("收益发放失败！"+e.getMessage());
		}
		return handlerResult;
	}

    /**
     * 定时任务
     * @author 80bug
     *
     */
	class MyTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        String transNo="";
        String requestId="";
        public MyTask(Timer mytimer,String tranId,String status,String type,String transNo,String requestId){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.transNo =transNo;
        	this.requestId = requestId;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId,requestId);
			try{
                Thread.sleep(500);

  				BlockAsynTranModel blockAsynTran =blockAsynTranDao.findByRequestId(requestId);
  				status = blockAsynTran.getQueryStatus();

                if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equalsIgnoreCase(status)){

                	dailyIncomeJobService.DailyIncomeSuccess(requestId);

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
	
//    /**
//     * 数据加密
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/loanBlockChainKeyEncode.html", method = RequestMethod.GET)
//    public @ResponseBody Map<String,Object> userBlockChainKeyEncode(HttpServletRequest request,CrowdfundingModel model) {
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//    	try{
//    		
//    		List<CrowdfundingModel> loanList = this.crowdfundingService.selectByAll(model);
//    		DesUtils des = new DesUtils();
//    		for(CrowdfundingModel loan:loanList){
//    			if(!StringUtil.isEmpty(loan.getBlockChainSecretKey())){
//    				loan.setBlockChainSecretKey(des.encrypt(loan.getBlockChainSecretKey()));
//        			this.crowdfundingDao.updateBySelective(loan);
//    			}
//    		}
//    		resultMap.put(SUCCESS,true);
//    	}catch(Exception e){
//    		e.printStackTrace();
//    		resultMap.put(SUCCESS,false);
//    	}
//    	return resultMap;
//    }
}
