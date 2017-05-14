package com.fbd.admin.app.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fbd.admin.app.admin.service.IAdminService;
import com.fbd.admin.security.AdminRoleGrantedAuthority;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.admin.dao.IAdminDao;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.app.user.dao.IUserSecurityDao;
import com.fbd.core.app.user.model.UserSecurityModel;
import com.fbd.core.app.verifycode.dao.IVerifyCodeDao;
import com.fbd.core.app.verifycode.model.VerifyCodeModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {
    
    @Resource
    private IAdminDao adminDao;
    
    @Resource
    private IUserSecurityDao userSecurityDao;
    
    //验证码
    @Resource
    private IVerifyCodeDao verifyCodeDao;

    /**
     * Description: 用户登录的用户查询
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-15 下午7:38:39
     */
    public AdminModel getAdminByAdminId(String adminId) {
        
        AdminModel adminModel = adminDao.getAdminByUserId(adminId);
        
        if (adminModel == null) {
            adminModel = adminDao.getAdminByMobile(adminId);
        }
        return adminModel;
    }

    public List<AdminRoleGrantedAuthority> getAuthorityByUserId(String userId) {
        List<AdminModel> list = adminDao.getAuthorityByUserId(userId);
        List<AdminRoleGrantedAuthority> returnList = new ArrayList<AdminRoleGrantedAuthority>();
        for (AdminModel roleAuthority : list) {
            AdminRoleGrantedAuthority adminRoleGrantedAuthority = new AdminRoleGrantedAuthority();
            try {
                BeanUtils.copyProperties(adminRoleGrantedAuthority, roleAuthority);
            } catch (Exception e) {
                e.printStackTrace();
            }
            returnList.add(adminRoleGrantedAuthority);
        }
        return returnList;
    }

    public SearchResult<AdminModel> getAdminPage(AdminModel adminModel) {
        return adminDao.getAdminPage(adminModel);
    }

    public List<AdminModel> getAdminList(AdminModel adminModel) {
        return adminDao.getAdminList(adminModel);
    }

    public long getAdminCount(AdminModel adminModel) {
        return adminDao.getAdminCount(adminModel);
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
    public Map<String, Object> getCount4System() {
        return adminDao.getCount4System();
    }

    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String id) {
        return adminDao.modifyAdminPassword(id);
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
    public AdminModel getAdminDetail(String id) {
        return adminDao.getAdminDetail(id);
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
    public AdminModel getAdminById(String id) {
        return adminDao.getAdminById(id);
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
    public int modifyAdmin(AdminModel adminModel) {
    	
    	String mobile = adminModel.getMobile();
    	AdminModel adminByMobile  = this.adminDao.getAdminByMobile(mobile);
    	if(adminByMobile!=null){
        	if(!adminModel.getAdminId().equals(adminByMobile.getAdminId())){
        		throw new ApplicationException("手机号已经存在！");
        	}
    	}

        return adminDao.modifyAdmin(adminModel);
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
    public int saveAdmin(AdminModel adminModel) {
        UserSecurityModel userSecurity4select = userSecurityDao.findByUserId(adminModel.getAdminId(),FbdCoreConstants.userType.A);
        
        //如果用户不存在
        if (userSecurity4select==null) {
            //添加状态
            UserSecurityModel userSecurity = new UserSecurityModel();
            userSecurity.setId(PKGenarator.getId());
            userSecurity.setUserId(adminModel.getAdminId());
            userSecurity.setUserType(FbdCoreConstants.userType.A);
            userSecurity.setStatus(FbdCoreConstants.userStatus.normal);
            int num =userSecurityDao.saveUserSecurity(userSecurity);
            if (num>0) {
                adminModel.setId(PKGenarator.getId());
                //创建时间
                adminModel.setCreateTime(new Date());
                //创建人
                adminModel.setCreator(MyRequestContextHolder.getCurrentUser().getAdminId());
                return adminDao.saveAdmin(adminModel);
            }
        }
        return -1;
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
    public Map<String, Object> getAdminByMobile(String employeeNo, String mobile) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        //根据电话号码判断
        AdminModel admin = adminDao.getAdminByMobile(mobile);
        if (admin==null) {
            resultMap.put("msg", "手机号不存在");
            return resultMap;
        }else {
            if (!admin.getEmployeeNo().equals(employeeNo)) {
                resultMap.put("msg", "员工号和手机号不匹配");
            }else{
                resultMap.put("success", true); 
            }
            return resultMap;
        }
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
    public int modifyAdminPassword(String mobile, String verifyCode, String password) {
        //查询验证码是否正确
        AdminModel admin = adminDao.getAdminByMobile(mobile);
        VerifyCodeModel verifyCodeModel = verifyCodeDao.getVerifyCodeByUserAndVerifyType(admin.getAdminId(), FbdCoreConstants.userType.A,mobile, "reset_login_password_by_mobile_for_admin");
        if (verifyCodeModel != null && verifyCode.equals(verifyCodeModel.getVerifyCode())) {
            return adminDao.modifyAdminPassword(admin.getAdminId(),password);
        //验证码输入错误
        }else{
            return -1;
        }
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
    public int modifyAdminMobile(String oldMobile, String oldVerifyCode,String newMobile, String newVerifyCode) {
        //查询验证码是否正确
        AdminModel admin = adminDao.getAdminByMobile(oldMobile);
        //原手机号验证码
        VerifyCodeModel oldVerifyCodeModel = verifyCodeDao.getVerifyCodeByUserAndVerifyType(admin.getAdminId(), FbdCoreConstants.userType.A, oldMobile,"modify_mobile_by_mobile_for_admin");
        //新手机号验证码
        VerifyCodeModel newVerifyCodeModel = verifyCodeDao.getVerifyCodeByUserAndVerifyType(admin.getAdminId(), FbdCoreConstants.userType.A, newMobile,"bind_mobile_for_admin");
        //验证码输入正确
        if (oldVerifyCodeModel != null && oldVerifyCode.equals(oldVerifyCodeModel.getVerifyCode())
                && newVerifyCodeModel != null && newVerifyCode.equals(newVerifyCodeModel.getVerifyCode())) {
            admin.setMobile(newMobile);
            return adminDao.modifyAdmin(admin);
        //原手机号验证码输入错误
        }else if (oldVerifyCodeModel == null || !oldVerifyCode.equals(oldVerifyCodeModel.getVerifyCode())){
            return -1;
        }else if (newVerifyCodeModel == null || !newVerifyCode.equals(newVerifyCodeModel.getVerifyCode())){
            return -2;
        }else{
            return 0;
        }
    }

    /**
     * Description: 根据手机号查询对象
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-30 上午11:36:01
     */
    public AdminModel getAdminByMobile(String mobile) {
        return adminDao.getAdminByMobile(mobile);
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
    public int modifyAdminEmail(String oldEmail, String emailOldVerifyCode, String newEmail, String emailNewVerifyCode) {
        //查询验证码是否正确
        AdminModel admin = adminDao.getAdminByEmail(oldEmail);
        //原邮箱验证码
        VerifyCodeModel oldVerifyCodeModel = verifyCodeDao.getVerifyCodeByUserAndVerifyType(admin.getAdminId(), FbdCoreConstants.userType.A, oldEmail,"modify_mobile_by_mobile_for_admin");
        //新手机号验证码
        VerifyCodeModel newVerifyCodeModel = verifyCodeDao.getVerifyCodeByUserAndVerifyType(admin.getAdminId(), FbdCoreConstants.userType.A, newEmail,"bind_mobile_for_admin");
        //验证码输入正确
        if (oldVerifyCodeModel != null && emailOldVerifyCode.equals(oldVerifyCodeModel.getVerifyCode())
                && newVerifyCodeModel != null && emailNewVerifyCode.equals(newVerifyCodeModel.getVerifyCode())) {
            admin.setEmail(newEmail);
            return adminDao.modifyAdmin(admin);
        //原手机号验证码输入错误
        }else if (oldVerifyCodeModel == null || !emailOldVerifyCode.equals(oldVerifyCodeModel.getVerifyCode())){
            return -1;
        }else if (newVerifyCodeModel == null || !emailNewVerifyCode.equals(newVerifyCodeModel.getVerifyCode())){
            return -2;
        }else{
            return 0;
        }
    }

    /**
     * Description: 根据邮箱查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午2:02:44
     */
    public AdminModel getAdminByEmail(String email) {
        return this.adminDao.getAdminByEmail(email);
    }

    /**
     * Description: 根据用户Id修改密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String adminId, String password) {
        return adminDao.modifyAdminPassword(adminId, password);
    }

    /**
     * Description: 根据员工号查询管理员对象
     *
     * @param 
     * @return Object
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午6:02:07
     */
    public AdminModel getadminByEmployeeNo(String employeeNo) {
        return adminDao.getAdminByEmployeeNo(employeeNo);
    }

    /**
     * Description: 根据身份正好查询管理员对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午6:08:50
     */
    public AdminModel getAdminByIDNo(String realName,String idCardNo) {
        return adminDao.getAdminByIDNo(realName,idCardNo);
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
    public Map<String, Object> getCount4CrowdFundSys() {
        return adminDao.getCount4CrowdFundSys();
    }
}
