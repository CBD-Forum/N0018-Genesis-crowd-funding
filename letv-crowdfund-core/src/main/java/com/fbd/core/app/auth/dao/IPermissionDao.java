package com.fbd.core.app.auth.dao;

import java.util.List;
import com.fbd.core.app.auth.model.PermissionModel;

public interface IPermissionDao {

    int savePermission(PermissionModel permissionModel);

    List<PermissionModel> getPermissionList(PermissionModel permissionModel);

    List<PermissionModel> getPermission4Role(String roleId);

    int removePermission(String id);

    int modifyPermission(PermissionModel permissionModel);
    
    /**
     * Description: 根据Id查询权限对象
     *
     * @param 
     * @return PermissionModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-12 下午4:30:49
     */
    PermissionModel getPermissionById(String id);
    
    /**
     * Description: 根据资源Id查询权限对象
     *
     * @param 
     * @return PermissionModel
     * @throws 
     * @Author sunbo
     * Create Date: 2015-4-16
     */
    PermissionModel getPermissionByCode(String code);

    /**
     * Description: 排序权限列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-9 上午9:33:11
     */
    void modifyOrderByPermission(String operate,Integer startSeqNum, Integer endSeqNum);

    /**
     * Description: 得到父节点下最大的序列号
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-13 上午9:35:02
     */
    int getMaxSeqNumByPid(String pid);
    /**
     * 根据资源ID值获取url
     * @param code 资源ID
     * @return
     */
    public String getLinkByCode(String code);
    
}