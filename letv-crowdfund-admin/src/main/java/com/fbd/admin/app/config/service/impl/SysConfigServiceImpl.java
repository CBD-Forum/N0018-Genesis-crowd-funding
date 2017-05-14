package com.fbd.admin.app.config.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.config.service.ISysConfigService;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class SysConfigServiceImpl implements ISysConfigService {
    
    @Resource
    private ISysConfigDao sysConfigDao;

    public SearchResult<SysConfigModel> getSysConfigPage(SysConfigModel sysConfigModel) {
        return sysConfigDao.getSysConfigPage(sysConfigModel);
    }

    public int saveSysConfig(SysConfigModel sysConfigModel) {
        SysConfigModel sysConfigModel4Select = sysConfigDao.getByDisplayName(sysConfigModel.getDisplayName());
        if (sysConfigModel4Select!=null) {
            return -1;
        }
        sysConfigModel.setId(PKGenarator.getId());
        return sysConfigDao.saveBusConfig(sysConfigModel);
    }

    public List<SysConfigModel> getSysConfigList(SysConfigModel sysConfigModel) {
        return sysConfigDao.getSysConfigList(sysConfigModel);
    }

    public int modifySysConfig(SysConfigModel sysConfigModel) {
        SysConfigModel sysConfigModel4select = sysConfigDao.getByDisplayName(sysConfigModel.getDisplayName());
        
        if (sysConfigModel4select!=null && !sysConfigModel.getDisplayName().equals(sysConfigModel4select.getDisplayName())) {
            return -1;
        }
        return sysConfigDao.modifySysConfig(sysConfigModel);
    }

    public int removeSysConfig(String id) {
        return sysConfigDao.removeSysConfig(id);
    }

    /**
     * Description: 根据名称查询配置值
     *
     * @param 
     * @return String
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-13 上午9:50:36
     */
    public String getCodeByDisplayName(String displayName) {
        SysConfigModel sysConfig = sysConfigDao.getByDisplayName(displayName);
        if (sysConfig!=null) {
            return sysConfig.getCode();
        }
        return null;
    }

}
