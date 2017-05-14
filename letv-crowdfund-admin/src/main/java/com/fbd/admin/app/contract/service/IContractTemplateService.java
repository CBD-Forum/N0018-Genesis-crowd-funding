package com.fbd.admin.app.contract.service;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.common.model.SearchResult;

public interface IContractTemplateService {

    @PreAuthorize("hasPermission(null, 'security.operation.contract_tpl_add')")
    int saveContractTemplate(ContractTemplateModel contractTemplate);

    SearchResult<Map<String, Object>> getContractTemplatePage(ContractTemplateModel contractTemplate);

    ContractTemplateModel getContractTemplateById(String id);

    @PreAuthorize("hasPermission(null, 'security.operation.contract_tpl_delete')")
    int removeContractTemplate(String id);

    @PreAuthorize("hasPermission(null, 'security.operation.contract_tpl_modify')")
    int modifyContractTemplate(ContractTemplateModel contractTemplate);
    
    /**
     * 
     * Description: 拷贝合同
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-27 下午6:31:27
     */
    public void copyContractTemplate(String loanNo,String contractTmpNo);

}
