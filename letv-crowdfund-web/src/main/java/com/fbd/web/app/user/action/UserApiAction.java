package com.fbd.web.app.user.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.app.huiyuan.model.CreatePersonalMember;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.huiyuan.service.ICreateEnterpriseMemberService;
import com.fbd.web.app.huiyuan.service.ICreatePersonalMemberService;
import com.fbd.web.app.user.service.IUserApiService;

@Controller
@Scope("prototype")
@RequestMapping("/userApi")
public class UserApiAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Resource
    private IUserApiService userApiService;
	@Resource
	private  ICreatePersonalMemberService  createPersonalMemberService;
    @Resource
    private ICreateEnterpriseMemberService createEnterpriseMemberService;
	
	/**
	 * 全量查询用户信息
	 * @param user
	 * @return
	 */
    @RequestMapping(value = "/selectAllUserList.html", method = RequestMethod.GET) 
	public @ResponseBody Map<String,Object> selectAllUserList(UserModel user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			List<UserModel> userList = this.userApiService.selectUserListForApi(user);
			resultMap.put("list", userList);
			resultMap.put(SUCCESS, true);
		}catch(ApplicationException e){
			resultMap.put(MSG, e.getMessage());
			resultMap.put(SUCCESS, false);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS, false);
			resultMap.put(MSG,"查询用户信息失败！");
		}
		return resultMap;
	}
	/**
	 * 增量查询用户信息(updateTime)
	 * @param user
	 * @return
	 */
    @RequestMapping(value = "/selectIncrementalUserList.html", method = RequestMethod.GET) 
	public @ResponseBody Map<String,Object> selectIncrementalUserList(UserModel user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			user.setLastUpdateTime(DateUtil.addDate(new Date(), -5));
			List<UserModel> userList = this.userApiService.selectUserListForApi(user);
			resultMap.put("list", userList);
			resultMap.put(SUCCESS, true);
		}catch(ApplicationException e){
			resultMap.put(MSG, e.getMessage());
			resultMap.put(SUCCESS, false);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(SUCCESS, false);
			resultMap.put(MSG,"查询用户信息失败！");
		}
		return resultMap;
	}    
    
	
	/**
	 * 实名认证查询接口
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/selectUserIsAuth.html", method = RequestMethod.POST) 
	public @ResponseBody Map<String,Object> selectUserIsAuth(UserModel user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			
			UserModel model = this.userApiService.selectUserModelForApi(user);
			if(model == null){
				throw new ApplicationException("用户信息不存在");
			} 
			UserModel returnUser = new UserModel();
			if("1".equals(model.getIsAuth())){ //已经实名
				returnUser.setRealName(model.getRealName());
				returnUser.setCertificateNo(model.getCertificateNo());
				returnUser.setAuthTime(model.getAuthTime());  //认证时间
				returnUser.setIsAuth(model.getIsAuth());
			}else{  //未实名
				returnUser.setIsAuth("0");  //未认证
			}
			resultMap.put("user", returnUser);
			resultMap.put(SUCCESS, true);
		}catch(ApplicationException e){
			resultMap.put(MSG, e.getMessage());
			resultMap.put(SUCCESS, false);
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put(MSG, "查询实名信息失败");
			resultMap.put(SUCCESS, false);
		}
		return resultMap;
	}
    
	/**
	 * 会员查询接口
	 * @param user
	 * @return
	 */
    @RequestMapping(value = "/selectMemberInfo.html", method = RequestMethod.POST) 
	public @ResponseBody Map<String,Object> selectMemberInfo(UserModel user){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			UserModel model = this.userApiService.selectUserModelForApi(user);
			if(model == null){
				throw new ApplicationException("用户信息不存在");
			} 
			if("0".equals(model.getMemberType())){  //个人会员
				resultMap.put("memberType", "0");
				//查询个人会员信息
				CreatePersonalMember member =createPersonalMemberService.selectByUserId(model.getUserId());
				resultMap.put("member",member);
			}else if("1".equals(model.getMemberType())){  //机构会员
				resultMap.put("memberType", "1");
				//查询机构会员信息
				CreateEnterpriseMember member = createEnterpriseMemberService.selectByUserId(model.getUserId());
				resultMap.put("member",member);
			}
			resultMap.put(SUCCESS, true);
		}catch(ApplicationException e){
			resultMap.put(MSG, e.getMessage());
			resultMap.put(SUCCESS, false);
		}catch(Exception e){
			resultMap.put(MSG, "获取会员信息失败！");
			resultMap.put(SUCCESS, false);
		}
		return resultMap;
	}	
	//充值交易查询
	
	//会员余额查询接口
	
	//融资人信息查询
	
	//投资交易信息查询
	
	//项目进展查询
	
	//发货情况查询
	
	//提现查询
	
	//退款查询(已废弃)
	
	
	

}
