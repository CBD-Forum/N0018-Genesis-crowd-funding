package com.fbd.web.app.contract.service;

import java.util.Map;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.common.model.SearchResult;

public interface IContractTemplateService {

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
    public ContractTemplateModel getInvestConstactModel();
    
    ContractTemplateModel getContractTemplateById(String id);
}