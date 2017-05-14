/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserCenter.java 
 *
 * Created: [2015-4-2 上午11:22:20] by haolingfeng
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

package com.fbd.web.app.userCenter.action;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingAttentionModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.app.huiyuan.model.CreatePersonalMember;
import com.fbd.core.app.notice.dao.INoticeViewRecordDao;
import com.fbd.core.app.notice.model.NoticeViewRecordModel;
import com.fbd.core.app.notice.service.INoticeViewRecordService;
import com.fbd.core.app.recharge.model.RechargeModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.dao.IUserSignatureDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSignatureModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.Arith;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.config.service.IBusinessConfigService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingInvestService;
import com.fbd.web.app.crowdfunding.service.ICrowdfundingService;
import com.fbd.web.app.huiyuan.service.ICreateEnterpriseMemberService;
import com.fbd.web.app.huiyuan.service.ICreatePersonalMemberService;
import com.fbd.web.app.recharge.service.IRechargeService;
import com.fbd.web.app.withdraw.service.IWithDrawService;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹的用户中心
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/crowdfundUserCenter")
public class CrowdfundUserCenterAction extends BaseAction{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
            .getLogger(CrowdfundUserCenterAction.class);
    
    @Resource
    private IUserBillService userBillService;
    @Resource
    private ICrowdfundingService crowdfundingService;
    @Resource
    private ICrowdfundingInvestService crowdfundingInvestService;
    @Resource
    private IBusinessConfigService businessConfigService;
    @Resource
    private IUserDao userDao;
    @Resource
    private IUserBankDao userBankDao;
	@Resource
	private IUserSignatureDao userSignatureDao;
	@Resource
	private IRechargeService rechargeService;
	@Resource
	private IWithDrawService withDrawService;
	@Resource
	private INoticeViewRecordDao noticeViewRecordDao;
	@Resource
	private INoticeViewRecordService noticeViewRecordService;
    @Resource
    private ICreateEnterpriseMemberService createEnterpriseMemberService;
	@Resource
	private  ICreatePersonalMemberService  createPersonalMemberService;
    
    /**
     * 
     * Description: 查询我的个人信息(基本信息)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
    @RequestMapping(value = "/selectCrowdfundUserDetail.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> selectCrowdfundUserDetail(HttpServletRequest request){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            UserModel user = this.userDao.findByUserId(userId);
            user.setPassword("*******");
//            user.setBlockChainAddress("*******");
            user.setBlockChainSecretKey("*******");
            amap.put("user", user);
            //查询用户是否绑定银行卡
            UserBankModel userBank = new UserBankModel();
            userBank.setUserId(userId);
            List<UserBankModel> bankList = this.userBankDao.getList(userBank);
            if(bankList!=null && bankList.size()>0){
            	amap.put("isBindCard",true);
            	amap.put("bindCardCount", bankList.size());
            }else{
            	amap.put("isBindCard",false);
            	amap.put("bindCardCount", 0);
            }
            //查询用户是否实名认证  isAuth
            amap.put("isAuth", "1".equals(user.getIsAuth())?true:false);
            //会员类型
            String userType = user.getMemberType();
            if(userType!=null &&!"".equals(userType)){
            	amap.put("memberState",true);   //是否开通会员
            	amap.put("userType",userType);  //设置用户类型 0-个人会员   1-企业会员
            	
            	if("0".equals(userType)){  //个人会员
            		 //查询会员资料
            		CreatePersonalMember member =createPersonalMemberService.selectByUserId(userId);
            		amap.put("memberInfo",member);
            	}else if("1".equals(userType)){  //企业会员
            		CreateEnterpriseMember member = createEnterpriseMemberService.selectByUserId(userId);
            		amap.put("memberInfo",member);
            	}
            }else{
            	amap.put("memberState",false);  
            }
            //查询用户签章信息
            UserSignatureModel userSignature = this.userSignatureDao.selectByUserId(userId);
            if(userSignature!=null){
            	amap.put("isSetSignature", true);
            }else{
            	amap.put("isSetSignature", false);  //是否设置签章
            }
            
            
            if(userType!=null){
            	if("0".equals(userType)){  //个人会员
            		//查询领投人认证情况
            		CrowdfundUserStuffModel qLeadInvestor = new CrowdfundUserStuffModel();
            		qLeadInvestor.setUserId(userId);
            		qLeadInvestor.setAuthType("leadInvestor"); //领投人认证状态
            		CrowdfundUserStuffModel userStuffModel2 = this.crowdfundingService.selectByModel(qLeadInvestor);
            		
            		boolean leadInvestorAuthState = false;
            		if(userStuffModel2!=null){
            			leadInvestorAuthState = true;
            			String leadInvestorAuthStateValue = userStuffModel2.getInvestAuthState();
            			amap.put("leadInvestorAuthState",leadInvestorAuthState);
            			amap.put("leadInvestorAuthStateValue",leadInvestorAuthStateValue);
            		}else{
            			amap.put("leadInvestorAuthState",leadInvestorAuthState);
            			amap.put("leadInvestorAuthStateValue","");
            		}
            		
            		//查询个人会员认证情况
            		CrowdfundUserStuffModel qInvestor = new CrowdfundUserStuffModel();
            		qInvestor.setUserId(userId);
            		qInvestor.setAuthType("investor"); //投资人认证状态
            		CrowdfundUserStuffModel userStuffModel1 = this.crowdfundingService.selectByModel(qInvestor);
            		if(userStuffModel1!=null){
            			amap.put("investorAuthState",true);
            			amap.put("investorAuthStateValue",userStuffModel1.getInvestAuthState());
            		}else{
            			amap.put("investorAuthState",false);
            			amap.put("investorAuthStateValue","");
            		}
            		
            	}else{  //企业会员
            		//查询企业会员认证情况
            		CrowdfundUserStuffModel qOrgInvestor = new CrowdfundUserStuffModel();
            		qOrgInvestor.setUserId(userId);
            		qOrgInvestor.setAuthType("orgInvestor"); //跟投机构认证状态
            		CrowdfundUserStuffModel orgStuffModel1 = this.crowdfundingService.selectByModel(qOrgInvestor);
            		if(orgStuffModel1!=null){
            			String orgInvestAuthState = orgStuffModel1.getInvestAuthState();
            			amap.put("orgInvestAuthState",true);
            			amap.put("orgInvestAuthStateValue",orgInvestAuthState);
            		}else{
            			amap.put("orgInvestAuthState",false);
            			amap.put("orgInvestAuthStateValue","");
            		}
            		
            		CrowdfundUserStuffModel qorgLeadInvestor = new CrowdfundUserStuffModel();
            		qorgLeadInvestor.setUserId(userId);
            		qorgLeadInvestor.setAuthType("orgLeadInvestor"); //投资机构认证状态
            		CrowdfundUserStuffModel orgStuffModel2 = this.crowdfundingService.selectByModel(qorgLeadInvestor);
            		if(orgStuffModel2!=null){
            			String orgLeadAuthState = orgStuffModel2.getInvestAuthState();
            			amap.put("orgLeadAuthState",true);
            			amap.put("orgLeadAuthStateValue",orgLeadAuthState);
            		}else{
            			amap.put("orgLeadAuthState",false);
            			amap.put("orgLeadAuthStateValue","");
            		}
            	}
            }
            //查询是否显示有一条未到账信息(充值)
            NoticeViewRecordModel qnoticeViewRecord = this.noticeViewRecordService.queryNoticeViewRecord(userId, "recharge", null, null);
            //如果点击时间在通知时间之前则显示，否则不显示
            //Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false； 
            //查询列表时更新最后点击时间
            //入账时更新最新的通知时间
            if(qnoticeViewRecord.getLatestNoticeTime().after(qnoticeViewRecord.getLatestClickTime())){
            	amap.put("noAccountNotice",true);  //资金未到账显示
            }else{
            	amap.put("noAccountNotice",false);  //资金未到账隐藏
            } 
            
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            amap.put(SUCCESS, false);
        }
        return amap;
    }
    
    /**
     * 
     * Description: 查询用户个人资产信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
    @RequestMapping(value = "/selectUserCenterInfo.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> selectUserCenterInfo(HttpServletRequest request){
        Map<String, Object> amap = new HashMap<String,Object>();
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            
            UserModel user = this.userDao.findByUserId(userId);
            resultMap.put("safetyEyeFlag ", user.getSafetyEyeFlag()==null?"0":user.getSafetyEyeFlag());
            
            //可用余额
            UserBillModel userBill = userBillService.getLatestBill(userId);
            double balance = userBill.getBalance();
            double frozenAmt = userBill.getFrozenAmt();
            
            resultMap.put("availableAssets",Arith.format(balance)); //可用资产
            resultMap.put("frozenAssets",Arith.format(frozenAmt));  //冻结资产
//            resultMap.put("collectAssets",150);  //待收资产
            //查询用户收益
            double yesterdayProfit = 0.0;
            double cumulativeProfit = 0.0;
            Map<String,Object> capitalMap = this.userBillService.selectUserCapitalInfo(userId);
            if(capitalMap!=null){
            	yesterdayProfit = Double.parseDouble(capitalMap.get("yesterdayProfit")==null?"0.0":capitalMap.get("yesterdayProfit").toString());
            	cumulativeProfit = Double.parseDouble(capitalMap.get("cumulativeProfit")==null?"0.0":capitalMap.get("cumulativeProfit").toString());
            }
            resultMap.put("yesterdayProfit",Arith.format(yesterdayProfit));  //昨日收益
            resultMap.put("cumulativeProfit",Arith.format(cumulativeProfit));  //累计收益
            //统计投资
            Map<String,Object> countInvestAll = this.crowdfundingInvestService.getCountInvestAll(userId);
            resultMap.put("estimatedEarnings",Arith.format(Double.valueOf(countInvestAll.get("estimatedEarnings").toString())));  //预计收益
            resultMap.put("totalAssets",Arith.format(Arith.add(Arith.add(balance, frozenAmt), Double.valueOf(countInvestAll.get("estimatedEarnings").toString()))));  //总资产
            resultMap.put("historyInvest",Arith.format(Double.valueOf(countInvestAll.get("historyInvest").toString())));  //历史投资
            resultMap.put("transferingAssets",Arith.format(Double.valueOf(countInvestAll.get("transferingAssets").toString())));  //转让中资产
            resultMap.put("canTransferAssets",Arith.format(Double.valueOf(countInvestAll.get("canTransferAssets").toString())));  //可转让资产
            resultMap.put("alreadyTransferAssets",Arith.format(Double.valueOf(countInvestAll.get("alreadyTransferAssets").toString())));  //已转让资产
            resultMap.put(SUCCESS, true);
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }    
    
    
    /**
     * 
     * Description: 查询用户项目统计信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
    @RequestMapping(value = "/selectUserProjectInfo.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> selectUserProjectInfo(HttpServletRequest request){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
//            resultMap.put("productSupportCount",10);  //产品项目支持的项目数
//            resultMap.put("productApplyCount",2);  //产品项目申请的项目数
//            resultMap.put("productTransferCount",1);  //产品转让项目
//            resultMap.put("productKeepCount",10);  //产品项目收藏
//            resultMap.put("stockSupportCount",10);  //非公开融资项目支持的项目数
//            resultMap.put("stockApplyCount",2);     //非公开融资项目 申请的项目数
//            resultMap.put("stockKeepCount",20);    //非公开融资项目收藏的数量
//            resultMap.put("publicSupportCount",10);  //公益项目支持的项目数
//            resultMap.put("publicApplyCount",2);     //公益项目 申请的项目数
//            resultMap.put("publicKeepCount",15);    //公益项目收藏的数量
            resultMap = this.crowdfundingService.selectUserProjectInfo(userId);
            
            
            
            
            
            resultMap.put(SUCCESS, true);
            
        }catch(Exception e){
        	e.printStackTrace();
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }    
        
     
    /**
     * 
     * Description: 查询我的账户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
    @RequestMapping(value = "/getAccountInfo.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getUserAccount(HttpServletRequest request){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            //可用余额
            UserBillModel userBill = userBillService.getLatestBill(userId);
            double balance = userBill.getBalance();
            
           
            //按每笔提现费用配置
            double feeParam = 0.0;
            BusinessConfigModel feeConfig = businessConfigService.getByDisplayName(CrowdfundCoreConstants.WITHDRAW_FEE);
            if(feeConfig!=null){
                feeParam = Double.valueOf(feeConfig.getCode());
            }else{
                throw new ApplicationException("提现费用未设置");
            }
            
            //支持金额&购买金额
            //Map<String,Object> totalAmtMap = crowdfundingService.getSupportAmtAndBuyAmt(userId);
            amap.put(SUCCESS, true);
            amap.put("balance",balance);
            amap.put("feeParam",feeParam);
            //amap.put("totalSupportAmt",totalAmtMap.get("supportAmt"));
            //amap.put("totalBuyAmt",totalAmtMap.get("payAmt"));
        }catch(Exception e){
            logger.error(e.getMessage());
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
    }
    
    /**
     * 
     * Description: 查询我的众筹
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:33
     */
    @RequestMapping(value = "/getMyCrowdfundList.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getMyCrowdfundList(HttpServletRequest request,CrowdfundingModel model){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setSort("createTime");
            model.setOrder("desc");
            model.setLoanUser(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getPageList(model,null);
            DecimalFormat dFormat=new DecimalFormat("#.00");
            if("product".equals(model.getLoanType())){
                //处理科学记数法
                for(Map<String,Object> data:pageList.getRows()){
                	Double overFundAmt=(Double) data.get("overFundAmt");
                	data.put("overFundAmtStr", dFormat.format(overFundAmt));
                }
            }

            amap.put(MSG,pageList);
            amap.put(SUCCESS, true);
        }catch(Exception e){
             logger.error(e.getMessage());
             
             e.printStackTrace();
            amap.put(SUCCESS, false);
        }
        return amap;
    }
    
    /**
     * 
     * Description: 查询我的认购
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:33
     */
    @RequestMapping(value = "/getMySupportList.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getMySupportList(HttpServletRequest request,CrowdfundingSupportModel model){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setSort("defaultSort");
            model.setOrder("desc");
            //请求标识
            String requestRemark = request.getParameter("requestRemark");
            
            if("investor".equals(requestRemark)){ //如果是投资人  ，设置支持用户为当前用户
            	 model.setSupportUser(userId);
            }else if("project".equals(requestRemark)){//如果是项目方  设置项目用户为当前用户
            	model.setLoanUser(userId);
            }
            
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getSpportList(model);
            amap.put(MSG,pageList);
            amap.put(SUCCESS, true);
        }catch(Exception e){
             logger.error(e.getMessage());
            amap.put(SUCCESS, false);
        }
        return amap;
    }
    
    
    /**
     * 
     * Description: 查询我的约谈
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午12:34:33
     */
    @RequestMapping(value = "/getMyInterviewList.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getMyInterviewList(HttpServletRequest request,InterviewRecordModel model){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setApplyUser(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingInvestService.getInterviewList(model);
            amap.put(MSG,pageList);
            amap.put(SUCCESS, true);
        }catch(Exception e){
             logger.error(e.getMessage());
            amap.put(SUCCESS, false);
        }
        return amap;
    }
    
    /**
     * 
     * Description: 查询我的评论
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-2 下午3:19:19
     */
    @RequestMapping(value = "/getMyCommentList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getMyCommentList(HttpServletRequest request,CrowdfundingCommentModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setState(FbdCoreConstants.crowdFundAuditState.passed);
            model.setDiscussUser(userId);
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
     * Description:我的关注列表 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-4-10 上午10:13:40
     */
    @RequestMapping(value = "/getMyAttentionList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getMyAttentionList(HttpServletRequest request,CrowdfundingAttentionModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setAttentionUser(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getAttentionList(model);
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
     * Description:查询谁关注了我发的项目
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-28 下午4:31:12
     */
    @RequestMapping(value = "/getMyProjectAttentionList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getMyProjectAttentionList(HttpServletRequest request,CrowdfundingAttentionModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setLoanUser(userId);
            SearchResult<Map<String,Object>> pageList = this.crowdfundingService.getAttentionList(model);
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
     * Description: 查询个人中心
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     * Create Date: 2015-4-2 下午12:34:23
     */
    @RequestMapping(value = "/getUserCenterInfo.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> getUserCenterInfo(HttpServletRequest request){
        Map<String, Object> amap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            
            amap.put(MSG, this.crowdfundingInvestService.getUserCenterInfo(userId));
//            //可用余额
            UserBillModel userBill = userBillService.getLatestBill(userId);
//            double balance = userBill.getBalance();
        }catch(Exception e){
            logger.error(e.getMessage());
            amap.put(SUCCESS, false);
            amap.put(MSG, e.getMessage());
        }
        return amap;
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
    @RequestMapping(value = "/getUserRechargeList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserRechargeList(HttpServletRequest request,RechargeModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            
            if(model.getCreateStartTime()!=null){
            	model.setCreateStartTime(DateUtil.getDayMin(model.getCreateStartTime()));
            }else{
            	model.setCreateStartTime(DateUtil.getDayMin(new Date()));
            }
            if(model.getCreateEndTime()!=null){
            	model.setCreateEndTime(DateUtil.getDayMax(model.getCreateEndTime()));
            }else{
            	model.setCreateEndTime(DateUtil.getDayMax(new Date()));
            }
            
            SearchResult<Map<String,Object>> pageList = this.rechargeService.getUserRechargeList(model);
            
            try{
            	 NoticeViewRecordModel qnoticeViewRecord = this.noticeViewRecordService.queryNoticeViewRecord(userId, "recharge", null, null);
                 //如果点击时间在通知时间之前则显示，否则不显示
                 //Date1.after(Date2),当Date1大于Date2时，返回TRUE，当小于等于时，返回false； 
                 //查询列表时更新最后点击时间
            	 qnoticeViewRecord.setLatestClickTime(new Date());
            	 this.noticeViewRecordService.modifyNoticeViewRecord(qnoticeViewRecord);
            }catch(Exception e){
            	logger.error(e.getMessage());
            }
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
     * Description:查询交易中和交易失败的充值记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getUserRechargeDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserRechargeDetail(HttpServletRequest request,RechargeModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            Map<String,Object> pageList = this.rechargeService.getUserRechargeDetail(model);
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
     * Description:查询交易中和交易失败的提现记录
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getUserWithDrawList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserWithDrawList(HttpServletRequest request,WithDrawModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            
            if(model.getCreateStartTime()!=null){
            	model.setCreateStartTime(DateUtil.getDayMin(model.getCreateStartTime()));
            }else{
            	model.setCreateStartTime(DateUtil.getDayMin(new Date()));
            }
            if(model.getCreateEndTime()!=null){
            	model.setCreateEndTime(DateUtil.getDayMax(model.getCreateEndTime()));
            }else{
            	model.setCreateEndTime(DateUtil.getDayMax(new Date()));
            }
            SearchResult<Map<String,Object>> pageList = this.withDrawService.getUserWithDrawList(model);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG,pageList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    @RequestMapping(value = "/getTrasactionList.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getTrasactionList(HttpServletRequest request,WithDrawModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String userId = this.getUserId(request);
            model.setUserId(userId);
            SearchResult<Map<String,Object>> pageList = this.withDrawService.getTrasactionList(model);
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
     * Description:查询交易中和交易失败的提现记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getUserWithDrawDetail.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserWithDrawDetail(HttpServletRequest request,WithDrawModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            
            if(model.getCreateStartTime()!=null){
            	model.setCreateStartTime(DateUtil.getDayMin(model.getCreateStartTime()));
            }else{
            	model.setCreateStartTime(DateUtil.getDayMin(new Date()));
            }
            if(model.getCreateEndTime()!=null){
            	model.setCreateEndTime(DateUtil.getDayMax(model.getCreateEndTime()));
            }else{
            	model.setCreateEndTime(DateUtil.getDayMax(new Date()));
            }
            Map<String,Object> pageList = this.withDrawService.getUserWithDrawDetail(model);
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
     * Description:查询用户认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    @RequestMapping(value = "/getUserInfoByAuth.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserIsAuth(HttpServletRequest request,CrowdfundUserStuffModel model) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	String userId = this.getUserId(request);
            model.setUserId(userId);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, this.crowdfundingInvestService.getUserInfoByAuth(model));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    @RequestMapping(value = "/selectTranactionById.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> selectTranactionById(HttpServletRequest request,String id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
        	WithDrawModel model = new WithDrawModel();
        	
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, withDrawService.selectTranactionById(model));
        } catch (Exception e) {
            logger.error(e.getMessage());
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
}
