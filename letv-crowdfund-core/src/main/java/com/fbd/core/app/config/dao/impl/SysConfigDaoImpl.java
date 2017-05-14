package com.fbd.core.app.config.dao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository
public class SysConfigDaoImpl extends BaseDaoImpl<SysConfigModel> implements
        ISysConfigDao {

    private static Logger logger = LoggerFactory.getLogger(SysConfigDaoImpl.class);

    public SearchResult<SysConfigModel> getSysConfigPage(SysConfigModel sysConfigModel) {
        SearchResult<SysConfigModel> searchResult = new SearchResult<SysConfigModel>();
        searchResult.setTotal(getSysConfigCount(sysConfigModel));
        searchResult.setRows(getSysConfigList(sysConfigModel));
        return searchResult;
    }

    public long getSysConfigCount(SysConfigModel sysConfigModel) {
        return this.getCount(sysConfigModel);
    }

    public List<SysConfigModel> getSysConfigList(SysConfigModel sysConfigModel) {
        return this.selectByModel("select", sysConfigModel);
    }

    public int saveBusConfig(SysConfigModel sysConfigModel) {
        return this.save(sysConfigModel);
    }

    public int modifySysConfig(SysConfigModel sysConfigModel) {
        return this.updateBySelective(sysConfigModel);
    }

    public int removeSysConfig(String id) {
        return this.deleteBatchById(id);
    }

    /**
     * 
     * Description: 根据显示名查询值
     * 
     * @param
     * @return SysConfigModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午1:02:10
     */
    public SysConfigModel getByDisplayName(String displayName) {
        return this.selectOneByField("selectByDisplayName", displayName);
    }

    public SysConfigModel getSysConfigByCode(String code) {
        return this.selectOneByField("getSysConfigByCode", code);
    }

}
