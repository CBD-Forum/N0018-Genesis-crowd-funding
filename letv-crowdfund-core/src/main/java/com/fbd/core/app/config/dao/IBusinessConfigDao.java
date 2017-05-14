package com.fbd.core.app.config.dao;

import java.util.List;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.common.model.SearchResult;


public interface IBusinessConfigDao {

    SearchResult<BusinessConfigModel> getBusConfigPage(BusinessConfigModel businessConfigModel);
    
    List<BusinessConfigModel> getBusConfigList(BusinessConfigModel businessConfigModel);
    
    long getBusConfigCount(BusinessConfigModel businessConfigModel);

    int saveBusConfig(BusinessConfigModel businessConfigModel);

    int modifyBusConfig(BusinessConfigModel businessConfigModel);

    int removeBusConfig(String id);
    
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
            String displayName);

    /**
     * Description: 根据名称查询对象
     *
     * @param 
     * @return BusinessConfigModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-15 上午10:03:08
     */
    BusinessConfigModel getBusConfigByDisplayName(String displayName);
    
}