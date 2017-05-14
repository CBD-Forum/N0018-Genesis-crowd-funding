package com.fbd.core.app.config.dao;

import java.util.List;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

public interface ISysConfigDao extends BaseDao<SysConfigModel> {

    SearchResult<SysConfigModel> getSysConfigPage(SysConfigModel sysConfigModel);

    List<SysConfigModel> getSysConfigList(SysConfigModel sysConfigModel);

    long getSysConfigCount(SysConfigModel sysConfigModel);

    int saveBusConfig(SysConfigModel sysConfigModel);

    int modifySysConfig(SysConfigModel sysConfigModel);

    int removeSysConfig(String id);

    /**
     * 
     * Description: 根据显示名查询值
     * 
     * @param
     * @return SysConfigModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午1:02:10
     */
    public SysConfigModel getByDisplayName(String displayName);

    SysConfigModel getSysConfigByCode(String code);

}