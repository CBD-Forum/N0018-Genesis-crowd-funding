/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdminDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:14:46] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.admin.dao.IAdminDao;
import com.fbd.core.app.admin.model.AdminModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("adminDao")
public class AdminDaoImpl extends BaseDaoImpl<AdminModel> implements IAdminDao {

    public List<AdminModel> getAdminList(AdminModel adminModel) {
        
        return this.selectByModel("select", adminModel);
    }

    public List<AdminModel> getAuthorityByUserId(String userId) {
        return this.selectByField("getAuth", userId);
    }

    public SearchResult<AdminModel> getAdminPage(AdminModel adminModel) {
        SearchResult<AdminModel> searchResult = new SearchResult<AdminModel>();
        searchResult.setTotal(getAdminCount(adminModel));
        searchResult.setRows(getAdminList(adminModel));
        return searchResult;
    }

    public long getAdminCount(AdminModel adminModel) {
        return this.getCount(adminModel);
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
        return this.selectOneMapByField("getCount4Sys", null);
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
        return this.selectOneMapByField("getCount4CrowdFundSys", null);
    }
    
    

    /**
     * Description: 初始化密码
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-6 下午2:38:21
     */
    public int modifyAdminPassword(String id) {
        return this.update("initPassword", id);
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
        return this.selectOneByField("selectAdminDetail", id);
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
        return this.selectByPrimaryKey(id);
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
        return this.updateBySelective(adminModel);
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
        return this.save(adminModel);
    }

    /**
     * Description: 根据员工号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:27:15
     */
    public AdminModel getAdminByEmployeeNo(String employeeNo) {
        return this.selectOneByField("selectByEmployeeNo", employeeNo);
    }

    /**
     * Description: 根据手机号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByMobile(String mobile) {
        return this.selectOneByField("selectByMobile", mobile);
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
    public int modifyAdminPassword(String adminId, String password) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("adminId", adminId);
        param.put("password", password);
        return this.update("resetPassword", param);
    }

    /**
     * Description: 根据邮箱查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByEmail(String email) {
        return this.selectOneByField("selectByEmail", email);
    }

    /**
     * Description: 根据用户编号查询对象
     *
     * @param 
     * @return AdminModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午10:30:19
     */
    public AdminModel getAdminByUserId(String userId) {
        return this.selectOneByField("selectByUserId", userId);
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
        Map<String, String> param = new HashMap<String, String>();
        param.put("realName", realName);
        param.put("idCardNo", idCardNo);
        return this.selectOneByField("selectByIDNo", param);
    }
}
