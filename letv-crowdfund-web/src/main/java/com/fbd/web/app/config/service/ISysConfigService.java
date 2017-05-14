package com.fbd.web.app.config.service;

import java.util.List;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.common.model.SearchResult;

public interface ISysConfigService {

    SearchResult<SysConfigModel> getSysConfigPage(SysConfigModel sysConfigModel);

    int saveSysConfig(SysConfigModel sysConfigModel);

    List<SysConfigModel> getSysConfigList(SysConfigModel sysConfigModel);

    int modifySysConfig(SysConfigModel sysConfigModel);

    int removeSysConfig(String id);

    /**
     * 
     * Description:根据显示名查询值
     * 
     * @param
     * @return SysConfigModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午1:04:48
     */
    public SysConfigModel getByDisplayName(String displayName);

}
