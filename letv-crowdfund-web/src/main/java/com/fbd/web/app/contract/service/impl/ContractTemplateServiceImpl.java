package com.fbd.web.app.contract.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.contract.service.IContractTemplateService;

@Service
public class ContractTemplateServiceImpl implements IContractTemplateService {

    @Resource
    private IContractTemplateDao contractTemplateDao;

    /**
     * 
     * Description: 查询投资合同范文
     *
     * @param 
     * @return ContractTemplateModel
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 上午10:56:03
     */
    public ContractTemplateModel getInvestConstactModel() {
        ContractTemplateModel model = new ContractTemplateModel();
        model.setContractType(FbdConstants.CONTRACT_TEMPLATE_INVEST_MODEL);
        model.setStatus(FbdConstants.CONTRACT_TEMPLATE_STATUS_RELEASE);
        List<ContractTemplateModel> aList =  contractTemplateDao.getList(model);
        if(aList.size()>0){
            return aList.get(0);
        }else{
            return null;
        }
    }
    
    public ContractTemplateModel getContractTemplateById(String id) {
        return contractTemplateDao.getContractTemplateById(id);
    }

}
