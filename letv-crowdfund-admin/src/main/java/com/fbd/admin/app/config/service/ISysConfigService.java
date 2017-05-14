package com.fbd.admin.app.config.service;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.common.model.SearchResult;

public interface ISysConfigService {

    SearchResult<SysConfigModel> getSysConfigPage(SysConfigModel sysConfigModel);

    /**
     * Description: 添加系统配置
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-19 下午6:44:48
     */
    @PreAuthorize("hasPermission(null, 'security.operation.system_sysConfig_add')")
    int saveSysConfig(SysConfigModel sysConfigModel);

    List<SysConfigModel> getSysConfigList(SysConfigModel sysConfigModel);

    /**
     * Description: 修改系统配置
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-19 下午6:45:24
     */
    @PreAuthorize("hasPermission(null, 'security.operation.system_sysConfig_modify')")
    int modifySysConfig(SysConfigModel sysConfigModel);

    /**
     * Description: 删除系统配置
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-19 下午6:45:54
     */
    @PreAuthorize("hasPermission(null, 'security.operation.system_sysConfig_delete')")
    int removeSysConfig(String id);
    
    /**
     * Description: 根据名称查询配置值
     *
     * @param 
     * @return String
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-13 上午9:50:36
     */
    String getCodeByDisplayName(String displayName);

}
