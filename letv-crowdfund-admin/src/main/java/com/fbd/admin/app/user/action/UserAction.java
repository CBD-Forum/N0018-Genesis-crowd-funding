package com.fbd.admin.app.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.crowdFund.service.ICrowdfundInvestService;
import com.fbd.admin.app.user.service.IUserService;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.app.user.model.UserUploadFileModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.common.utils.MessageUtils;
import com.fbd.core.common.utils.SendMessageUtil;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 167174126359697398L;
    @Resource
    public IUserService userService;
    @Resource
    public ICrowdfundInvestService crowdfundingInvestService;
    

    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getUserList(UserModel user) {
        SearchResult<Map<String, Object>> userModels = userService.getUserPage(user);
        return userModels;
    }
    
    /**
     * Description: 查询普通用户列表(投资用户管理、借款人管理)
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    @RequestMapping(value = "/getCrowdfundUserlist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getCrowdfundUserlist(UserModel user) {
        SearchResult<Map<String, Object>> userModels = userService.getCrowdfundUserPage(user);
        return userModels;
    }
    
    /**
     * Description: 查询用户的资金统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    @RequestMapping(value = "/getUserCapitallist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getUserCapitallist(UserModel user) {
        SearchResult<Map<String, Object>> userModels = userService.getUserCaptialPage(user);
        return userModels;
    }

    /**
     * 
     * Description: 查询借款人真实姓名
     * 
     * @param
     * @return SearchResult<UserModel>
     * @throws
     * @Author haolingfeng 
     * Create Date: 2014-12-30 下午4:02:53
     */
    @RequestMapping(value = "/getUserName.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserName(String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        UserModel user = userService.getUser(userId);
        if (user == null || user.getIsBorrower().equals(FbdCoreConstants.NOT)) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "借款人不存在");
        } else {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, user.getRealName());
        }
        return resultMap;
    }
    
    /**
     * 
     * Description: 查询真实姓名
     * 
     * @param
     * @return SearchResult<UserModel>
     * @throws
     * @Author haolingfeng 
     * Create Date: 2014-12-30 下午4:02:53
     */
    @RequestMapping(value = "/getUserRealName.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getUserRealName(String userId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        UserModel user = userService.getUser(userId);
        if (user == null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "此用户不存在");
        } else {
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, user.getRealName());
        }
        return resultMap;
    }
    
    /**
     * Description: 修改用户安全表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-2 下午9:41:02
     */
    @RequestMapping(value = "/modifySecurity.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyUserSecurity(UserSecurityModel userSecurity){
        int num = userService.modifyUserSecurity(userSecurity);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            //审计日志
            try {
                String status = userSecurity.getStatus();
                String operateType = AuditLogConstants.TYPE_DISABLE;
                String memo = "被禁用";
                if (FbdCoreConstants.userStatus.normal.equals(status)) {
                    operateType = AuditLogConstants.TYPE_ENABLE;
                    memo = "被启用";
                }
                AuditLogUtils.log(AuditLogConstants.MODEL_INVESTUSER, operateType, AuditLogConstants.RESULT_SUCCESS,"用户："+userSecurity.getUserId()+"，"+memo);
           } catch (Exception e) {
                                
           }
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 查询用户详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 上午10:06:32
     */
    @RequestMapping(value = "/getUserDetail.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getUserDetail(String userId) {
        Map<String, Object> user = userService.getUserDetail(userId);
        return user;
    }
    
    /**
     * Description: 查询借款用户详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-3 上午10:06:32
     */
    @RequestMapping(value = "/getLoanUserDetail.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getLoanUserDetail(String userId) {
        Map<String,Object> user = userService.getCrowdfundUserDetail(userId);
        CrowdfundUserStuffModel stuffModel=new CrowdfundUserStuffModel();
        stuffModel.setUserId(userId);
        stuffModel.setInvestAuthState(CrowdfundCoreConstants.crowdFundAuditState.passed);
        List<CrowdfundUserStuffModel> userStuff = this.crowdfundingInvestService.getByUserId(stuffModel);
        List<Map<String,Object>> userFiles = this.crowdfundingInvestService.getUserUploadFiles(userId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("user", user);
        result.put("userStuff", userStuff);
        result.put("userFiles", userFiles);
        return result;
    }
    
    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 上午10:24:48
     */
    @RequestMapping(value = "/resetPassword.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyUserPassword(String id){
        int num = userService.modifyUserPassword(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 根据Id查询用户实体信息
     *
     * @param 
     * @return UserModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:16:03
     */
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody UserModel getUserById(String id){
        UserModel user = userService.getUserById(id);
//        CrowdfundUserStuffModel userStuff = this.crowdfundingInvestService.getByUserId(user.getUserId());
//        user.setUserLevel(userStuff.getUserLevel());
        return user;
    } 
    
    /**
     * Description: 修改用户信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 上午10:39:34
     */
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyUser(UserModel user){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(user.getEmail())) {
			//判断邮箱是否存在
			UserModel emailUser = userService.getUserByEmail(user.getEmail());
			if (emailUser != null
					&& !emailUser.getUserId().equals(user.getUserId())) {
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "邮箱已经存在");
				return resultMap;
			}
		}
		if (StringUtils.isNotEmpty(user.getMobile())) {
			//判断手机号是否存在
			UserModel mobileUser = userService
					.getUserByMobile(user.getMobile());
			if (mobileUser != null
					&& !mobileUser.getUserId().equals(user.getUserId())) {
				resultMap.put(SUCCESS, false);
				resultMap.put(MSG, "手机号码已经存在");
				return resultMap;
			}
		}
		int num = userService.modifyUser(user);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
  
  
     
    
    /**
     * Description: 删除用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/removeUser.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeUser(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int num = userService.removeUser(id);
        if (num==1) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 群发短信
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-28 下午2:56:32
     */
    @RequestMapping(value = "/sendSMS.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendSMS(UserModel user,String SMSContent){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> userList = userService.getUserList(user);
            for (Map<String, Object> map : userList) {
                if (map.get("mobile")!=null) {
                    boolean send = MessageUtils.sendSMS(SMSContent, (String)map.get("mobile"));
                    if (send) {
                        AuditLogUtils.log(AuditLogConstants.MODEL_GROUPSENDMSG, AuditLogConstants.TYPE_SENDSMS, AuditLogConstants.RESULT_SUCCESS, "用户："+map.get("userId")+",已成功接收到短信。手机号为："+map.get("mobile")+",内容为："+SMSContent);
                    }else{
                        AuditLogUtils.log(AuditLogConstants.MODEL_GROUPSENDMSG, AuditLogConstants.TYPE_SENDSMS, AuditLogConstants.RESULT_FAIL, "用户："+map.get("userId")+",接收短信失败。手机号为："+map.get("mobile")+",内容为："+SMSContent);
                    }
                }else{
                    AuditLogUtils.log(AuditLogConstants.MODEL_GROUPSENDMSG, AuditLogConstants.TYPE_SENDSMS, AuditLogConstants.RESULT_FAIL, "用户："+map.get("userId")+",没有配置手机号码，接收短信失败。内容为："+SMSContent);
                }
            }
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 群发邮件
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-28 下午4:05:56
     */
    @RequestMapping(value = "/sendEmail.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendEmail(UserModel user,String subject,String emailContent){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> userList = userService.getUserList(user);
            for (Map<String, Object> map : userList) {
                if (map.get("email")!=null) {
                    boolean send = MessageUtils.sendMail((String)map.get("email"), subject, emailContent);
                    if (send) {
                        AuditLogUtils.log(AuditLogConstants.MODEL_GROUPSENDMSG, AuditLogConstants.TYPE_SENDEMAIL, AuditLogConstants.RESULT_SUCCESS, "用户："+map.get("userId")+",已成功接收到邮件。邮件主题为："+subject+",内容为："+emailContent);
                    }else{
                        AuditLogUtils.log(AuditLogConstants.MODEL_GROUPSENDMSG, AuditLogConstants.TYPE_SENDEMAIL, AuditLogConstants.RESULT_FAIL, "用户："+map.get("userId")+",接收邮件失败。邮件主题为："+subject+",内容为："+emailContent);
                    }
                }else{
                    AuditLogUtils.log(AuditLogConstants.MODEL_GROUPSENDMSG, AuditLogConstants.TYPE_SENDEMAIL, AuditLogConstants.RESULT_FAIL, "用户："+map.get("userId")+",没有配置邮箱，接收邮件失败。邮件主题为："+subject+",内容为："+emailContent);
                }
            }
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 查询众筹用户的资金统计
     * 
     * @param
     * @return List<UserModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午2:18:18
     */
    @RequestMapping(value = "/getCrowdfundUserCapitallist.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<Map<String, Object>> getCrowdfundUserCapitallist(UserModel user) {
        SearchResult<Map<String, Object>> userModels = userService.getUserCaptialPage(user);
        return userModels;
    }
    
    
    
    /**
     * Description: 修改用户等级
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author 武文斌
     */
    @RequestMapping(value = "/modifyUserGrade.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyUserGrade(CrowdfundUserStuffModel model){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		userService.modifyUserGrade(model);
    		 resultMap.put(SUCCESS, true);
		} catch (Exception e) {
			  resultMap.put(SUCCESS, false);
		}
        return resultMap;
    }
    
    /**
     * Description: 群发站内信
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-2 上午11:13:40
     */
    @RequestMapping(value = "/sendStation.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> sendStation(UserModel user,String StationContent){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            List<Map<String, Object>> userList = userService.getUserList(user);
            for (Map<String, Object> map : userList) {
            	 String nodeCode = FbdCoreConstants.STATION_MSG_TYPE_SYS_MSG;
                 SendMessageUtil.sendDirectStationMessage(nodeCode, nodeCode, 
                		 map.get("userId").toString(),StationContent);
            }     
            resultMap.put(SUCCESS, true);
        } catch (Exception e) {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 修改用户类型（是否为明星）
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/modifyUserIsCelebrity.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyUserIsCelebrity(UserModel model){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		userService.modifyUserIsCelebrity(model);
    		 resultMap.put(SUCCESS, true);
		} catch (Exception e) {
			  resultMap.put(SUCCESS, false);
		}
        return resultMap;
    }
    
    /**
     * Description: 修改公司认证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author 武文斌
     */
    @RequestMapping(value = "/modifyCompany.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyCompany(UserUploadFileModel model){
    	 Map<String, Object> resultMap = new HashMap<String, Object>();
    	try {
    		crowdfundingInvestService.modifyCompany(model);
    		 resultMap.put(SUCCESS, true);
		} catch (Exception e) {
			  resultMap.put(SUCCESS, false);
		}
        return resultMap;
    }
    /**
     * Description: 查询公司认证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/getCompanyDetail.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getCompanyDetail(String userId) {
        List<Map<String,Object>> userFiles = this.crowdfundingInvestService.getUserUploadFiles(userId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("userFiles", userFiles);
        return result;
    }
    
    
    /**
     * Description: 查询所有用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     */
    @RequestMapping(value = "/selectAllUserList.html", method = RequestMethod.POST)
    public @ResponseBody List<UserModel> selectAllUserList(HttpServletRequest request) {
    	UserModel user = new UserModel();
        List<UserModel> userList = this.userService.selectAllUser(user);
        return userList;
    }
}
