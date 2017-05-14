package com.fbd.core.app.config.dao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository(value="businessConfigDao")
public class BusinessConfigDaoImpl extends BaseDaoImpl<BusinessConfigModel> implements IBusinessConfigDao {
    
    Logger logger = LoggerFactory.getLogger(BusinessConfigDaoImpl.class);

    public SearchResult<BusinessConfigModel> getBusConfigPage(
            BusinessConfigModel businessConfigModel) {
        SearchResult<BusinessConfigModel> searchResult = new SearchResult<BusinessConfigModel>();
        searchResult.setTotal(getBusConfigCount(businessConfigModel));
        searchResult.setRows(getBusConfigList(businessConfigModel));
        return searchResult;
    }

    /**
     * 
     * Description: 根据显示名查询
     *
     * @param 
     * @return BusinessConfigModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-21 上午11:15:13
     */
    public BusinessConfigModel getBusConfig(
            String displayName) {
        return this.selectOneByField("getByDisplayName", displayName);
    }
    public List<BusinessConfigModel> getBusConfigList(
            BusinessConfigModel businessConfigModel) {
        return this.selectByModel("select", businessConfigModel);
    }

    public long getBusConfigCount(BusinessConfigModel businessConfigModel) {
        return this.getCount(businessConfigModel);
    }

    public int saveBusConfig(BusinessConfigModel businessConfigModel) {
        return this.save(businessConfigModel);
    }

    public int modifyBusConfig(BusinessConfigModel businessConfigModel) {
        return this.updateBySelective(businessConfigModel);
    }

    public int removeBusConfig(String id) {
        return this.deleteBatchById(id);
    }

    /**
     * Description: 根据名称查询对象
     *
     * @param 
     * @return BusinessConfigModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-15 上午10:03:08
     */
    public BusinessConfigModel getBusConfigByDisplayName(String displayName) {
        return this.selectOneByField("selectByDisplayName", displayName);
    }

}
