package com.fbd.admin.app.auth.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.auth.service.IAuthService;
import com.fbd.core.app.auth.dao.IPermissionDao;
import com.fbd.core.app.auth.dao.IRoleDao;
import com.fbd.core.app.auth.dao.IRolePermissionDao;
import com.fbd.core.app.auth.dao.IUserRoleDao;
import com.fbd.core.app.auth.model.PermissionModel;
import com.fbd.core.app.auth.model.RoleModel;
import com.fbd.core.helper.PKGenarator;

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
    
    public int savePermission(PermissionModel permissionModel) {
        if ("menu".equals(permissionModel.getPermissionType())) {
            int maxSeqNum = permissionDao.getMaxSeqNumByPid(permissionModel.getPid());
            permissionDao.modifyOrderByPermission("top",maxSeqNum+1, null);
            
            permissionModel.setSeqNum(maxSeqNum+1);
        }else if ("operate".equals(permissionModel.getPermissionType())) {
            permissionModel.setSeqNum(null);
        }
        
        return permissionDao.savePermission(permissionModel);
    }
    public int modifyPermission(PermissionModel permissionModel) {
        return permissionDao.modifyPermission(permissionModel);
    }
    
    /**
     * Description: 排序权限列表
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-9 上午9:33:11
     */
    public int modifyOrderByPermission(String targetId, String sourceId,String point) {
        PermissionModel targetPermission = permissionDao.getPermissionById(targetId);
        PermissionModel sourcePermission = permissionDao.getPermissionById(sourceId);
        int targetSeqNum=targetPermission.getSeqNum();
        int sourceSeqNum=sourcePermission.getSeqNum();
        //向上移动
        if ("top".equals(point)) {
            //修改targetNode 和 sourceNode 中间的节点序号
            if (sourceSeqNum<targetSeqNum) {
                permissionDao.modifyOrderByPermission("bottom",sourceSeqNum,targetSeqNum);
            }else{
                permissionDao.modifyOrderByPermission("top",targetSeqNum,sourceSeqNum);
            }
        }else if ("bottom".equals(point)) {
            //修改targetNode 和 sourceNode 中间的节点序号
            if (targetSeqNum<sourceSeqNum) {
                permissionDao.modifyOrderByPermission("top",targetSeqNum,sourceSeqNum);
            }else{
                permissionDao.modifyOrderByPermission("bottom",sourceSeqNum,targetSeqNum);
            }
        }
        //将targetNode的序号修改为sourceNode的序号
        sourcePermission.setSeqNum(targetSeqNum);
        permissionDao.modifyPermission(sourcePermission);
        return 0;
    }
    
    private String getChildLst4Permission(String id){
        List<PermissionModel> list = this.getPermissionListByPid(id);
        for (PermissionModel permission : list) {
            id+=","+getChildLst4Permission(permission.getId());
        }
        return id;
    }
    
    
    public int removePermission(String id) {
        //判断是否是父节点，如果是父节点，就删除父节点和其下的所有子节点
        PermissionModel permission = permissionDao.getPermissionById(id);
        id=getChildLst4Permission(id);
        int removeCount = permissionDao.removePermission(id);
        //更新删除节点以后的顺序值
        permissionDao.modifyOrderByPermission("bottom",permission.getSeqNum(),null);
        return removeCount;
    }
    public List<PermissionModel> getPermissionList(
            PermissionModel permissionModel) {
        return permissionDao.getPermissionList(permissionModel);
    }
    public int saveRole(RoleModel roleModel) {
        roleModel.setId(PKGenarator.getId());
        return roleDao.saveRole(roleModel);
    }
    public int modifyRole(RoleModel roleModel) {
        return roleDao.modifyRole(roleModel);
    }
    public int removeRole(String id) {
        //删除角色下的权限
        rolePermissionDao.removePermission4Role(id);
        return roleDao.removeRole(id);
    }
    public List<RoleModel> getRoleList(RoleModel roleModel) {
        return roleDao.getRoleList(roleModel);
    }
    public int savePermission4Role(String roleId, String permissionId,String permissionChecked) {
        //删除角色下的所有的权限
        rolePermissionDao.removePermission4Role(roleId);
        String permissionIds[] = permissionId.split(",");
        String permissionCheckeds[] = permissionChecked.split(",");
        permissionId="";
        for (int i = 0; i < permissionIds.length; i++) {
            if ("true".equals(permissionCheckeds[i])) {
                permissionId+=","+this.getChildLst4Permission(permissionIds[i]);
            }else{
                permissionId+=","+permissionIds[i];
            }
        }
        permissionId=permissionId.substring(1);
        
        //去除重复的Id
        Set<String> set = new HashSet<String>();
        for (int i = 0,length=permissionId.split(",").length; i < length; i++) {
            set.add(permissionId.split(",")[i]);
        }
        
        return rolePermissionDao.savePermission4Role(roleId,StringUtils.join(set, ","));
    }
    public int removePermission4Role(String roleId) {
        return rolePermissionDao.removePermission4Role(roleId);
    }
    public List<PermissionModel> getPermission4Role(String roleId) {
        return permissionDao.getPermission4Role(roleId);
    }
    public int saveRole4User(String userId, String roleId) {
        //先删除用户的所有角色
        int removeNum = userRoleDao.removeRole4UserByuserId(userId);
        
        return userRoleDao.saveRole4User(userId,roleId);
    }
    public int removeRole4User(String id) {
        return 0;
    }
    public List<RoleModel> getRole4User(String userId) {
        return roleDao.getRole4User(userId);
    }
    public List<PermissionModel> getPermissionListByPid(String pid) {
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setPid(pid);
        return permissionDao.getPermissionList(permissionModel);
    }
    
    
    /**
     * 根据资源ID值获取url
     * @param code 资源ID
     * @return
     */
    public String getLinkByCode(String code){
/*        String result = "";
        if(StringUtils.isEmpty(code)){
            return result;
        }
        try {
            result = permissionDao.getPermissionByCode(code).getUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;*/
    	return permissionDao.getLinkByCode(code);
    }
    
    /**
     * 
     * Description: 根据角色查询用户
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-12 下午6:48:59
     */
    public List<Map<String,Object>> getUserByRole(String roleId) {
        return this.userRoleDao.getUserByRoleId(roleId);
    }
}
