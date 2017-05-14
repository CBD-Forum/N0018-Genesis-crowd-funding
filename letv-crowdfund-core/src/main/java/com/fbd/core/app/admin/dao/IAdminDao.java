/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserModel.java 
 *
 * Created: [2014-12-3 10:46:57] by haolingfeng
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
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.core.app.admin.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 管理员
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IAdminDao extends BaseDao<AdminModel> {
    
    public List<AdminModel> getAdminList(AdminModel adminModel);

    public List<AdminModel> getAuthorityByUserId(String userId);

    public SearchResult<AdminModel> getAdminPage(AdminModel adminModel);

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
    public int saveAdmin(AdminModel adminModel);
    
    /**
     * Description: 根据员工号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:27:15
     */
    public AdminModel getAdminByEmployeeNo(String employeeNo);
    
    /**
     * Description: 根据手机号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByMobile(String mobile);

    /**
     * Description: 忘记密码，重置密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String adminId, String password);

    /**
     * Description: 根据邮箱查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByEmail(String email);

    /**
     * Description: 根据用户编号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByUserId(String userId);

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