package com.fbd.admin.app.auth.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.auth.service.IAuthService;
import com.fbd.core.app.auth.model.PermissionModel;
import com.fbd.core.app.auth.model.RoleModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.TreeModel;
import com.fbd.core.common.utils.AuditLogUtils;
import com.fbd.core.helper.PKGenarator;

@Controller
@Scope("prototype")
@RequestMapping("/auth")
public class AuthAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4594340169219677825L;
    @Resource
    private IAuthService authService;
    
    
    //添加权限
    @RequestMapping(value = "/permission/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> savePermission(PermissionModel permissionModel){
        String id = PKGenarator.getId();
        permissionModel.setId(id);
        int num = authService.savePermission(permissionModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            resultMap.put("id", id);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    //修改权限
    @RequestMapping(value = "/permission/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyPermission(PermissionModel permissionModel){
        int num = authService.modifyPermission(permissionModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            resultMap.put("id", permissionModel.getId());
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/permission/orderBy.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyOrderByPermission(String targetId,String sourceId,String point){
        int num = authService.modifyOrderByPermission(targetId,sourceId,point);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //删除权限
    @RequestMapping(value = "/permission/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removePermission(String id){
        int num = authService.removePermission(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num > 0) {
            resultMap.put(SUCCESS, true);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //权限列表
    @RequestMapping(value = "/permission/getList.html", method = RequestMethod.POST)
    public @ResponseBody List<PermissionModel> getPermissionList(PermissionModel permissionModel){
        List<PermissionModel> list = authService.getPermissionList(permissionModel);
        return list;
    }
    
    //根据父节点查询子节点权限列表
    @RequestMapping(value = "/permission/getPermissionsByPid.html", method = RequestMethod.POST)
    public @ResponseBody List<TreeModel> getPermissionListByPid(String pid){
        List<PermissionModel> list = authService.getPermissionListByPid(pid);
        List<TreeModel> returnList = new ArrayList<TreeModel>();
        for (PermissionModel pm : list) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(pm.getId());
            treeModel.setText(pm.getLabel());
            if (authService.getPermissionListByPid(pm.getId()).size()>0) {
                treeModel.setState("closed");
            }
            treeModel.setAttributes(pm);
            returnList.add(treeModel);
        }
        return returnList;
    }
    
    //添加角色
    @RequestMapping(value = "/role/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveRole(RoleModel roleModel){
        int num = authService.saveRole(roleModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "角色："+roleModel.getName()+",添加成功。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //修改角色
    @RequestMapping(value = "/role/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyRole(RoleModel roleModel){
        int num = authService.modifyRole(roleModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"角色："+roleModel.getName()+",修改成功。");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //删除角色
    @RequestMapping(value = "/role/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeRole(String id){
        int num = authService.removeRole(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    //角色列表
    @RequestMapping(value = "/role/getList.html", method = RequestMethod.POST)
    public @ResponseBody List<RoleModel> getRoleList(RoleModel roleModel){
        List<RoleModel> list = authService.getRoleList(roleModel);
        return list;
    }
    
    @RequestMapping(value = "/role/getById.html", method = RequestMethod.POST)
    public @ResponseBody RoleModel getRoleById(String id){
        RoleModel roleModel = new RoleModel();
        roleModel.setId(id);
        List<RoleModel> list = authService.getRoleList(roleModel);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
    
    //根据父节点查询子节点权限列表
    @RequestMapping(value = "/role/getRolesByPid.html", method = RequestMethod.POST)
    public @ResponseBody List<TreeModel> getRoleListByPid(String pid){
        RoleModel roleModel = new RoleModel();
        roleModel.setPid(pid);
        List<RoleModel> list = authService.getRoleList(roleModel);
        List<TreeModel> returnList = new ArrayList<TreeModel>();
        for (RoleModel rm : list) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(rm.getId());
            treeModel.setText(rm.getName());
            treeModel.setState("closed");
            treeModel.setAttributes(rm);
            returnList.add(treeModel);
        }
        return returnList;
    }
    
    //给角色添加权限
    @RequestMapping(value = "/role/addPermission.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> savePermission4Role(String roleId,String permissionId,String permissionChecked){
         int num = authService.savePermission4Role(roleId,permissionId,permissionChecked);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
             AuditLogUtils.log(AuditLogConstants.MODEL_ROLE, AuditLogConstants.TYPE_ADDPERMISSION, AuditLogConstants.RESULT_SUCCESS,"角色Id："+roleId+",成功添加了权限。");
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    
    //删除角色的权限
    @RequestMapping(value = "/role/removePermission.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removePermission4Role(String roleId){
         int num = authService.removePermission4Role(roleId);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    
    //查询角色下的权限
    @RequestMapping(value = "/role/getPermission.html", method = RequestMethod.POST)
    public @ResponseBody List<TreeModel> getPermission4Role(String roleId,String pid){
        PermissionModel permissionModel = new PermissionModel();
        permissionModel.setPid(pid);
        List<PermissionModel> list = authService.getPermission4Role(roleId);
        //有权限的Id
        List<String> permissionIds =new ArrayList<String>();
        for (PermissionModel p : list) {
            permissionIds.add(p.getId());
        }
        //所有权限
        List<PermissionModel> permissionAll = authService.getPermissionList(permissionModel);
        List<TreeModel> returnList = new ArrayList<TreeModel>();
        for (PermissionModel permission : permissionAll) {
            TreeModel treeModel = new TreeModel();
            treeModel.setId(permission.getId());
            treeModel.setText(permission.getLabel());
            if (authService.getPermissionListByPid(permission.getId()).size()>0) {
                treeModel.setState("closed");
            }
            treeModel.setAttributes(permission);
            if (permissionIds.contains(permission.getId())) {
                treeModel.setChecked(true);
            }
            returnList.add(treeModel);
        }
        
        return returnList;
    }
    
    //给用户添加角色
    @RequestMapping(value = "/user/addRole.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveRole4User(String userId,String roleId){
         int num = authService.saveRole4User(userId,roleId);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    //删除用户下的角色
    @RequestMapping(value = "/user/removeRole.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeRole4User(String id){
         int num = authService.removeRole4User(id);
         Map<String, Object> resultMap = new HashMap<String, Object>();
         if (num > 0) {
             resultMap.put(SUCCESS, true);
         }else{
             resultMap.put(SUCCESS, false);
         }
         return resultMap;
    }
    //用户下的角色列表
    @RequestMapping(value = "/user/getRole.html", method = RequestMethod.POST)
    public @ResponseBody List<RoleModel> getRole4User(String userId){
        List<RoleModel> list = authService.getRole4User(userId);
        return list;
    }
    
    
    
    //查询角色下的用户
    @RequestMapping(value = "/user/getUserByRole.html", method = RequestMethod.POST)
    public @ResponseBody List<Map<String,Object>> getUserByRole(String roleCode){
        List<Map<String,Object>> list = authService.getUserByRole(roleCode);
        return list;
    }
}
