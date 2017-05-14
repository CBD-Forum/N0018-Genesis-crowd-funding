package com.fbd.admin.app.config.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.config.service.IBusinessConfigService;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class BusinessConfigServiceImpl implements IBusinessConfigService {
    
    @Resource
    private IBusinessConfigDao businessConfigDao;
    
//    @Resource
//    private StdScheduler scheduler;

    public SearchResult<BusinessConfigModel> getBusConfigPage(
            BusinessConfigModel businessConfigModel) {
        return businessConfigDao.getBusConfigPage(businessConfigModel);
    }

    public int saveBusConfig(BusinessConfigModel businessConfigModel) {
        BusinessConfigModel businessConfigModel4select = businessConfigDao.getBusConfigByDisplayName(businessConfigModel.getDisplayName());
        if (businessConfigModel4select != null) {
            return -1;
        }
        businessConfigModel.setId(PKGenarator.getId());
        return businessConfigDao.saveBusConfig(businessConfigModel);
    }

    public List<BusinessConfigModel> getBusConfigList(
            BusinessConfigModel businessConfigModel) {
        return businessConfigDao.getBusConfigList(businessConfigModel);
    }

    public long getBusConfigCount(BusinessConfigModel businessConfigModel) {
        return businessConfigDao.getBusConfigCount(businessConfigModel);
    }

    public int modifyBusConfig(BusinessConfigModel businessConfigModel) {
        BusinessConfigModel businessConfig = businessConfigDao.getBusConfigByDisplayName(businessConfigModel.getDisplayName());
        if (businessConfig != null && !businessConfigModel.getDisplayName().equals(businessConfig.getDisplayName())) {
            return -1;
        }
        return businessConfigDao.modifyBusConfig(businessConfigModel);
    }

    public int removeBusConfig(String id) {
        return businessConfigDao.removeBusConfig(id);
    }
    
    /**
     * 
     * Description:根据显示名查询值
     * 
     * @param
     * @return BusinessConfigModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-24 下午1:04:48
     */
    public BusinessConfigModel getByDisplayName(String displayName) {
        return this.businessConfigDao.getBusConfig(displayName);
    }

}
