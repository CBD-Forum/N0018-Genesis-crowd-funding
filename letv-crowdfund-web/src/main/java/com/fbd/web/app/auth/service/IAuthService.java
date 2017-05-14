package com.fbd.web.app.auth.service;

import java.util.List;
import com.fbd.core.app.auth.model.PermissionModel;

public interface IAuthService {

    /**
     * Description: 根据父节点ID查询所有子节点的菜单
     *
     * @param 
     * @return List<PermissionModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-26 下午12:05:46
     */
    List<PermissionModel> getPermissionListByPid(String pid);
    
    /**
     * 根据资源ID值获取url
     * @param code 资源ID
     * @return
     */
    public String getLinkByCode(String code);


}
