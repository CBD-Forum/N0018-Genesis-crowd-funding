package com.fbd.web.app.auth.action;

import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.auth.model.PermissionModel;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.auth.service.IAuthService;

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
    
    
    //根据父节点查询子节点权限列表
    @RequestMapping(value = "/getPermissionsByPid.html", method = RequestMethod.POST)
    public @ResponseBody List<PermissionModel> getPermissionListByPid(String pid){
        pid="338bed1e0ec64d418e77b71c44288393";
        List<PermissionModel> list = authService.getPermissionListByPid(pid);
        return list;
    }
    
    //查询关于我们子菜单
    @RequestMapping(value = "/getAboutOurList.html", method = RequestMethod.POST)
    public @ResponseBody List<PermissionModel> getAboutOurList(){
    	String pid = "ef6b220032a84ffd9923b97110fd3d72";
    	List<PermissionModel> list = authService.getPermissionListByPid(pid);
    	return list;
    }
    
    
    
    //wap端查询关于爱筹网
    @RequestMapping(value = "/getAboutIChouList.html", method = RequestMethod.POST)
    public @ResponseBody List<PermissionModel> getAboutIChouList(){
    	String pid = "4f25f4932fce4ec2a2e264fa0bbb4d15";
    	List<PermissionModel> list = authService.getPermissionListByPid(pid);
    	return list;
    }
}
