package com.fbd.web.app.huiyuan.action;

import java.util.HashMap;
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

import com.fbd.core.app.huiyuan.model.CreateEnterpriseMember;
import com.fbd.core.app.letvPay.common.LetvPayConstants.Param;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.huiyuan.service.ICreateEnterpriseMemberService;
@Controller
@Scope("prototype")
@RequestMapping("/enterpriseMember")
public class CreateEnterpriseMemberAction  extends BaseAction{
	 private static final Logger logger = LoggerFactory
	            .getLogger(CreateEnterpriseMemberAction.class);
  @Resource
  private ICreateEnterpriseMemberService createEnterpriseMemberService;
  
  @Resource
  private IUserDao userDao;
  
  @RequestMapping(value = "/saveEnterPriseMember.html", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, Object> saveEnterPriseMember(CreateEnterpriseMember member, HttpServletRequest request) {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      try{
    	  
    	  
    	  String userId = this.getUserId(request);
    	  member.setLoginName(userId);
	      long count = createEnterpriseMemberService.selectIsExists(member);
	      if(count>0){
	    	  resultMap.put(SUCCESS, false);
	    	  resultMap.put(MSG, "会员已经存在，开通会员失败!");
	  
	      }else{
	          member.setUid(PKGenarator.getId());
	          member.setId(PKGenarator.getId());
	          if(!"".equals(member.getIsActive())){
	        	  member.setIsActive("T");
	          }

	    	  Map<String,Object> enterpiseMemberMap = createEnterpriseMemberService.EnterpriseMemberRequest(member);
	    	  if(enterpiseMemberMap.get(Param.is_success).equals("T")){
	    	      String memberId =(String)enterpiseMemberMap.get("member_id");
	    	      String parentId =(String)enterpiseMemberMap.get("partner_id");
                  member.setMemberId(memberId);
                  member.setParentId(parentId);
		    	  createEnterpriseMemberService.insert(member);
		    	  //更新用户会员类型
		    	  UserModel user = this.userDao.findByUserId(userId);
		    	  user.setMemberType("1");  //设置会员类型为企业用户
		    	  this.userDao.updateByUserId(user);
		    	  resultMap.put(MSG, "会员开户成功!");
		    	  resultMap.put(SUCCESS, true);
        		  logger.info("==================企业会员开户成功=======================");

	    	  }else{
	    		  resultMap.put(MSG, enterpiseMemberMap.get("error_message"));
		    	  resultMap.put(SUCCESS, false); 
        		  logger.info("==================企业会员开户失败=======================");

	    	  }
	      }
      }catch(Exception e){
    	  e.printStackTrace();
    	  resultMap.put(MSG, "开通失败，请联系管理员!");
    	  resultMap.put(SUCCESS, false);
		  logger.info("==================区块链企业开户失败======================="+e.getMessage());

      }
      return resultMap;
  }    
  
  @RequestMapping(value = "/modifyEnterPriseMember.html", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, Object> updateEnterPriseMember(CreateEnterpriseMember member, HttpServletRequest request) {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      try{
	      long count = createEnterpriseMemberService.selectUpdateIsExists(member);
	      if(count>0){
	    	  resultMap.put(SUCCESS, false);
	    	  resultMap.put(MSG, "会员已经存在修改失败!");
	  
	      }else{
	    	  createEnterpriseMemberService.modify(member);
	    	  resultMap.put(MSG, "会员修改成功!");
	    	  resultMap.put(SUCCESS, true);
	
	      }
      }catch(Exception e){
    	  e.printStackTrace();
    	  resultMap.put(MSG, "修改失败，请联系管理员!");
    	  resultMap.put(SUCCESS, false);
      }
      return resultMap;
  }    
  
  @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, Object> getById(String userId, HttpServletRequest request) {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      try{
    	      CreateEnterpriseMember member = createEnterpriseMemberService.selectByUserId(userId);
    	      if(member!=null){
		    	  resultMap.put(MSG,member);
		    	  resultMap.put(SUCCESS, true);
	    	  }else{
	    		  resultMap.put(MSG,"还没有开通会员");
		    	  resultMap.put(SUCCESS, false);
	    	  }
	     
      }catch(Exception e){
    	  e.printStackTrace();
    	  resultMap.put(MSG, "查询失败，请联系管理员!");
    	  resultMap.put(SUCCESS, false);
      }
      return resultMap;
  }  
}
