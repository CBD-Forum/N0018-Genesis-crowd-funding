package com.fbd.admin.app.admin.service;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.admin.security.AdminRoleGrantedAuthority;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.common.model.SearchResult;

public interface IAdminService {
    
    public AdminModel getAdminByAdminId(String adminId);
    
    public List<AdminRoleGrantedAuthority> getAuthorityByUserId(String userId);

    public SearchResult<AdminModel> getAdminPage(AdminModel adminModel);
    
    public List<AdminModel> getAdminList(AdminModel adminModel);
    
    public long getAdminCount(AdminModel adminModel);

    /**
     * Description: 后台首页对系统的概况统计
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 上午9:58:13
     */
    public Map<String, Object> getCount4System();

    /**
     * Description: 重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    @PreAuthorize("hasPermission(null, 'security.operation.user_admin_resetPwd')")
    public int modifyAdminPassword(String id);

    /**
     * Description: 管理员详情
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午4:31:52
     */
    @PreAuthorize("hasPermission(null, 'security.operation.user_admin_detail')")
    public AdminModel getAdminDetail(String id);

    /**
     * Description: 根据Id查询Admin实体
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 下午6:47:47
     */
    public AdminModel getAdminById(String id);

    /**
     * Description: 修改
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-10 下午6:54:38
     */
    @PreAuthorize("hasPermission(null, 'security.operation.user_admin_modify')")
    public int modifyAdmin(AdminModel adminModel);

    /**
     * Description: 添加新的管理员
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-11 上午9:40:15
     */
    @PreAuthorize("hasPermission(null, 'security.operation.user_admin_add')")
    public int saveAdmin(AdminModel adminModel);

    /**
     * Description: 用于忘记密码的员工号和手机号验证
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午9:59:09
     */
    public Map<String, Object> getAdminByMobile(String employeeNo, String mobile);

    /**
     * Description: 忘记密码，重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String mobile, String verifyCode, String password);
    
    /**
     * Description: 根据用户Id修改密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String adminId,String password);

    /**
     * Description:修改手机号 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-23 下午3:49:17
     */
    public int modifyAdminMobile(String oldMobile, String oldverifyCode,String newMobile, String newVerifyCode);
    
    /**
     * Description: 根据手机号查询对象
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-30 上午11:36:01
     */
    public AdminModel getAdminByMobile(String mobile);

    /**
     * Description: 修改邮箱
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 上午10:21:14
     */
    public int modifyAdminEmail(String oldEmail, String emailOldVerifyCode,String newEmail, String emailNewVerifyCode);

    /**
     * Description: 根据邮箱查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午2:02:44
     */
    public AdminModel getAdminByEmail(String email);

    /**
     * Description: 根据员工号查询管理员对象
     *
     * @param 
     * @return Object
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午6:02:07
     */
    public AdminModel getadminByEmployeeNo(String employeeNo);

    /**
     * Description: 根据身份正好查询管理员对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-7 下午6:08:50
     */
    public AdminModel getAdminByIDNo(String realName,String idCardNo);
    
    /**
     * Description: 后台首页对系统的概况统计(众筹使用)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 上午9:58:13
     */
    public Map<String, Object> getCount4CrowdFundSys();

}
