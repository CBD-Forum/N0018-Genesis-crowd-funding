package com.fbd.web.app.auth.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.fbd.core.app.auth.dao.IPermissionDao;
import com.fbd.core.app.auth.dao.IRoleDao;
import com.fbd.core.app.auth.dao.IRolePermissionDao;
import com.fbd.core.app.auth.dao.IUserRoleDao;
import com.fbd.core.app.auth.model.PermissionModel;
import com.fbd.web.app.auth.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService {
    
    //权限
    @Resource
    private IPermissionDao permissionDao;
    //角色
    @Resource
    private IRoleDao roleDao;
    //角色权限
    @Resource
    private IRolePermissionDao rolePermissionDao;
    //用户角色
    @Resource
    private IUserRoleDao userRoleDao;

    /**
     * Description: 根据父节点ID查询所有子节点的菜单
     *
     * @param 
     * @return List<PermissionModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-26 下午12:05:46
     */
    public List<PermissionModel> getPermissionListByPid(String pid) {
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setPid(pid);
        permissionModel.setStatus("0");
        return permissionDao.getPermissionList(permissionModel);
    }
    
    /**
     * 根据资源ID值获取url
     * @param code 资源ID
     * @return
     */
    public String getLinkByCode(String code){
        String result = "";
        if(StringUtils.isEmpty(code)){
            return result;
        }
        try {
            result = permissionDao.getPermissionByCode(code).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
