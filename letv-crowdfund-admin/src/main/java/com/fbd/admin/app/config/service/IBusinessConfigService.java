package com.fbd.admin.app.config.service;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.common.model.SearchResult;

public interface IBusinessConfigService {

    SearchResult<BusinessConfigModel> getBusConfigPage(BusinessConfigModel businessConfigModel);
    
    List<BusinessConfigModel> getBusConfigList(BusinessConfigModel businessConfigModel);
    
    long getBusConfigCount(BusinessConfigModel businessConfigModel);

    @PreAuthorize("hasPermission(null, 'security.operation.system_busConfig_add')")
    int saveBusConfig(BusinessConfigModel businessConfigModel);

    @PreAuthorize("hasPermission(null, 'security.operation.system_busConfig_modify')")
    int modifyBusConfig(BusinessConfigModel businessConfigModel);

    @PreAuthorize("hasPermission(null, 'security.operation.system_busConfig_delete')")
    int removeBusConfig(String id);
    
    
    /**
     * 
     * Description:根据显示名查询值
     * 
     * @param
     * @return BusinessConfigModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午1:04:48
     */
    public BusinessConfigModel getByDisplayName(String displayName) ;

}
