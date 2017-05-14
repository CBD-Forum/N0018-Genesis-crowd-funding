package com.fbd.admin.app.admin.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.admin.service.IAdminService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.HashCrypt;
import com.fbd.core.util.ValidateUtils;


@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class AdminAction extends BaseAction {
    
    /**
     * 
     */
    private static final long serialVersionUID = 8780480380461329866L;
    @Resource
    private IAdminService adminService;
    
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<AdminModel> getAdminPage(AdminModel adminModel){
        SearchResult<AdminModel> busConfigList = adminService.getAdminPage(adminModel);
//        System.out.println(((SysConfigModel)RedisManager.get("sysConfig", "sms_1_password")).getCode());
        return busConfigList;
//        if (MemcachedUtil.get("adminPage")!=null) {
//            return (SearchResult<AdminModel>)MemcachedUtil.get("adminPage");
//        }else{
//            MemcachedUtil.add("adminPage", busConfigList);
//        }
    } 
    
    
    /**
     * Description: 用于忘记密码的员工号和手机号验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午9:59:09
     */
    @RequestMapping(value = "/validateMobile.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAdminByMobile(String employeeNo,String mobile) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(employeeNo)) {
            resultMap.put(MSG, "请填写员工号");
            return resultMap;
        }
        if (StringUtils.isEmpty(mobile)) {
            resultMap.put(MSG, "请填写手机号码");
            return resultMap;
        }
        if (!ValidateUtils.validateMobile(mobile)) {
            resultMap.put(MSG, "请填写有效的手机号码");
            return resultMap;
        }
        
        return  adminService.getAdminByMobile(employeeNo,mobile);
    }
    
    
    /**
     * Description: 根据Id查询Admin实体
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 下午6:47:47
     */
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody AdminModel getAdminById(String id) {
        return adminService.getAdminById(id);
    }
    
    /**
     * Description: 添加新的管理员
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-11 上午9:40:15
     */
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> saveAdmin(AdminModel adminModel) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        //判断用户名是否存在
        if (adminService.getAdminByAdminId(adminModel.getAdminId())!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "用户名已经存在");
            return resultMap;
        }
        //员工编号是否存在
        if (adminService.getadminByEmployeeNo(adminModel.getEmployeeNo())!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "员工号已经存在");
            return resultMap;
        }
        //邮箱是否存在
        if (adminService.getAdminByEmail(adminModel.getEmail())!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "邮箱已经存在");
            return resultMap;
        }
        //手机号码是否存在
        if (adminService.getAdminByMobile(adminModel.getMobile())!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "手机号码已经存在");
            return resultMap;
        }
        
        //真实姓名和身份证号 是否存在
        AdminModel idNoAdmin =adminService.getAdminByIDNo(adminModel.getRealName(),adminModel.getIdCardNo());
        if (idNoAdmin!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "真实姓名和身份证号已经存在");
            return resultMap;
        }
        
        
        int num = adminService.saveAdmin(adminModel);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ADMINUSER, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS,"管理员："+adminModel.getAdminId()+"，添加成功。");
        } else {
            if (num==-1) {
                resultMap.put(MSG, "用户名已经存在，请更改用户名");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 

    /**
     * Description: 修改
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 下午6:54:38
     */
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyAdmin(AdminModel adminModel) {
        //修改前的对象数据
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
            AdminModel admin = adminService.getAdminByAdminId(adminModel.getAdminId());
            int num = adminService.modifyAdmin(adminModel);
            if (num == 1) {
                resultMap.put(SUCCESS, true);
                if (MyRequestContextHolder.getCurrentUser().getId().equals(adminModel.getId())) {
                    try {
                        //真实姓名
                        MyRequestContextHolder.getCurrentUser().setRealName(adminModel.getRealName());
                        //身份证号
                        //员工编号
                        MyRequestContextHolder.getCurrentUser().setEmployeeNo(adminModel.getEmployeeNo());
                        //部门
                        MyRequestContextHolder.getCurrentUser().setDept(adminModel.getDept());
                        //邮箱
                        MyRequestContextHolder.getCurrentUser().setEmail(adminModel.getEmail());
                        //手机号
                        MyRequestContextHolder.getCurrentUser().setMobile(adminModel.getMobile());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } 
                }
                AuditLogUtils.log(AuditLogConstants.MODEL_LOAN, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"管理员："+adminModel.getAdminId()+"，重置密码成功。");
            } else {
                resultMap.put(SUCCESS, false);
            }
    	}catch(ApplicationException e1){
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, e1.getMessage());
    	}catch(Exception e){
    		resultMap.put(SUCCESS, false);
    		resultMap.put(MSG, "修改数据失败");
    	}
        return resultMap;
    }
    
    /**
     * Description:修改手机号 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-23 下午3:49:17
     */
    @RequestMapping(value = "/modifyMobile.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyAdminMobile(String oldMobile,String oldVerifyCode,String newMobile,String newVerifyCode) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (adminService.getAdminByMobile(newMobile)!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "该手机号码已经存在");
            return resultMap;
        }
        
        int num = adminService.modifyAdminMobile(oldMobile,oldVerifyCode,newMobile,newVerifyCode);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            MyRequestContextHolder.getCurrentUser().setMobile(newMobile);
            try {
                AdminModel admin = adminService.getAdminByMobile(oldMobile);
                AuditLogUtils.log(AuditLogConstants.MODEL_ADMINUSER, AuditLogConstants.TYPE_MODIFYMOBILE, AuditLogConstants.RESULT_SUCCESS,"管理员："+admin.getAdminId()+"，修改手机号码成功。");
            } catch (Exception e) {
                
            }
        } else {
            resultMap.put(SUCCESS, false);
            if (num == -1) {
                resultMap.put(MSG, "原手机号验证码错误");
            }
            if (num == -2) {
                resultMap.put(MSG, "新手机号验证码错误");
            }
        }
        return resultMap;
    }
    
    
    /**
     * Description: 修改邮箱
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 上午10:21:14
     */
    @RequestMapping(value = "/modifyEmail.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyAdminEmail(String oldEmail,String emailOldVerifyCode,String newEmail,String emailNewVerifyCode) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (adminService.getAdminByEmail(newEmail)!=null) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "该邮箱已经存在");
            return resultMap;
        }
        int num = adminService.modifyAdminEmail(oldEmail,emailOldVerifyCode,newEmail,emailNewVerifyCode);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            MyRequestContextHolder.getCurrentUser().setEmail(newEmail);
            AuditLogUtils.log(AuditLogConstants.MODEL_ADMINUSER, AuditLogConstants.TYPE_MODIFYEMAIL, AuditLogConstants.RESULT_SUCCESS,"原邮箱地址："+oldEmail+",改为："+newEmail);
        } else {
            resultMap.put(SUCCESS, false);
            if (num == -1) {
                resultMap.put(MSG, "原邮箱验证码错误");
            }
            if (num == -2) {
                resultMap.put(MSG, "新邮箱验证码错误");
            }
        }
        return resultMap;
    }
    
    /**
     * Description: 后台首页对系统的概况统计
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 上午9:58:13
     */
    @RequestMapping(value = "/getCount4Sys.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getCount4System(){
        return adminService.getCount4System();
    }  
    
    
    /**
     * Description: 忘记密码，重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    @RequestMapping(value = "/resetPassword.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyAdminPassword(String employeeNo,String mobile,String verifyCode,String password,String rePassword,String valiCode){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (StringUtils.isEmpty(employeeNo)) {
            resultMap.put(MSG, "请填写员工号");
            return resultMap;
        }
        if (StringUtils.isEmpty(mobile)) {
            resultMap.put(MSG, "请填写手机号码");
            return resultMap;
        }
        if (!ValidateUtils.validateMobile(mobile)) {
            resultMap.put(MSG, "请填写有效的手机号码");
            return resultMap;
        }
        if (StringUtils.isEmpty(verifyCode)) {
            resultMap.put(MSG, "请填写手机验证码");
            return resultMap;
        }
        if (StringUtils.isEmpty(password)) {
            resultMap.put(MSG, "请填写重置密码");
            return resultMap;
        }
        if (StringUtils.isEmpty(rePassword)) {
            resultMap.put(MSG, "请填写重置密码");
            return resultMap;
        }
        if (!rePassword.equals(password)) {
            resultMap.put(MSG, "两次输入密码不一致");
            return resultMap;
        }
        if (StringUtils.isEmpty(valiCode)) {
            resultMap.put(MSG, "请填写验证码");
            return resultMap;
        }
        if (!MyRequestContextHolder.getCurrentSession().getAttribute("codeImage").toString().equals(valiCode)) {
            resultMap.put(MSG, "请填写正确的验证码");
            return resultMap;
        }
        
        int num = adminService.modifyAdminPassword(mobile,verifyCode,password);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            try {
                AdminModel admin = adminService.getAdminByMobile(mobile);
                AuditLogUtils.log(admin.getAdminId(),AuditLogConstants.MODEL_ADMINUSER, AuditLogConstants.TYPE_RESETPASSWORD, AuditLogConstants.RESULT_SUCCESS,"管理员手机号："+mobile+"，重置密码成功。");
            } catch (Exception e) {
                
            }
        }else{
            //验证码输入错误
            if (num==-1) {
                resultMap.put(MSG, "请填写正确的手机验证码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * Description: 初始化密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 下午12:17:50
     */
    @RequestMapping(value = "/initPassword.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> initAdminPassword(String id){
        int num = adminService.modifyAdminPassword(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ADMINUSER, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"管理员id:"+id+"，初始化密码成功。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 修改密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-2 下午3:47:22
     */
    @RequestMapping(value = "/modifyPassword.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyPassword(String adminId,String oldPassword,String newPassword){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        AdminModel admin = adminService.getAdminByAdminId(adminId);
        if (!HashCrypt.getDigestHash(oldPassword,"MD5").equals(admin.getPassword())) {
            resultMap.put(SUCCESS, false);
            resultMap.put(MSG, "原密码输入错误，请重新输入。");
            return resultMap;
        }
        int num = adminService.modifyAdminPassword(adminId, newPassword);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ADMINUSER, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"管理员:"+adminId+"，修改密码成功。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * Description: 管理员详情
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午4:31:52
     */
    @RequestMapping(value = "/getAdminDetail.html", method = RequestMethod.POST)
    public @ResponseBody AdminModel getAdminDetail(String id){
        return adminService.getAdminDetail(id);
    } 
    
    /**
     * Description: 后台首页对系统的概况统计(众筹使用)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 上午9:58:13
     */
    @RequestMapping(value = "/getCount4CrowdfundSys.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getCount4CrowdfundSys(){
        return adminService.getCount4CrowdFundSys();
    }  
}
