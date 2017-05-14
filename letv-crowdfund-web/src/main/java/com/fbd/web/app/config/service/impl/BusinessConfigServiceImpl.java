package com.fbd.web.app.config.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.config.service.IBusinessConfigService;

@Service
public class BusinessConfigServiceImpl implements IBusinessConfigService {

    @Resource
    private IBusinessConfigDao businessConfigDao;

    public SearchResult<BusinessConfigModel> getBusConfigPage(
            BusinessConfigModel businessConfigModel) {
        return businessConfigDao.getBusConfigPage(businessConfigModel);
    }

    public int saveBusConfig(BusinessConfigModel businessConfigModel) {
        BusinessConfigModel businessConfigModel4select = new BusinessConfigModel();
        businessConfigModel4select.setCode(businessConfigModel.getCode());
        long count = businessConfigDao
                .getBusConfigCount(businessConfigModel4select);
        if (count > 0) {
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
        BusinessConfigModel businessConfigModel4select = new BusinessConfigModel();
        businessConfigModel4select.setCode(businessConfigModel.getCode());
        long count = businessConfigDao
                .getBusConfigCount(businessConfigModel4select);
        if (count > 0) {
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
