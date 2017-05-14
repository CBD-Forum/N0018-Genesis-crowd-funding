package com.fbd.web.app.config.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.config.service.ISysConfigService;

@Service
public class SysConfigServiceImpl implements ISysConfigService {

    @Resource
    private ISysConfigDao sysConfigDao;

    public SearchResult<SysConfigModel> getSysConfigPage(
            SysConfigModel sysConfigModel) {
        return sysConfigDao.getSysConfigPage(sysConfigModel);
    }

    public int saveSysConfig(SysConfigModel sysConfigModel) {
        SysConfigModel sysConfigModel4select = new SysConfigModel();
        sysConfigModel4select.setCode(sysConfigModel.getCode());
        long count = sysConfigDao.getSysConfigCount(sysConfigModel4select);
        if (count > 0) {
            return -1;
        }
        sysConfigModel.setId(PKGenarator.getId());
        return sysConfigDao.saveBusConfig(sysConfigModel);
    }

    public List<SysConfigModel> getSysConfigList(SysConfigModel sysConfigModel) {
        return sysConfigDao.getSysConfigList(sysConfigModel);
    }

    public int modifySysConfig(SysConfigModel sysConfigModel) {
        SysConfigModel sysConfigModel4select = new SysConfigModel();
        sysConfigModel4select.setCode(sysConfigModel.getCode());
        long count = sysConfigDao.getSysConfigCount(sysConfigModel4select);
        if (count > 0) {
            return -1;
        }
        return sysConfigDao.modifySysConfig(sysConfigModel);
    }

    public int removeSysConfig(String id) {
        return sysConfigDao.removeSysConfig(id);
    }

    /**
     * 
     * Description:根据显示名查询值
     * 
     * @param
     * @return SysConfigModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午1:04:48
     */
    public SysConfigModel getByDisplayName(String displayName) {
        return this.sysConfigDao.getByDisplayName(displayName);
    }

}
