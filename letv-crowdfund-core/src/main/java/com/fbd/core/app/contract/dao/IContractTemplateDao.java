package com.fbd.core.app.contract.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.common.model.SearchResult;


public interface IContractTemplateDao {

    int saveContractTemplate(ContractTemplateModel contractTemplate);

    SearchResult<Map<String, Object>> getContractTemplatePage(
            ContractTemplateModel contractTemplate);

    ContractTemplateModel getContractTemplateById(String id);

    int removeContractTemplate(String id);

    int modifyContractTemplate(ContractTemplateModel contractTemplate);
    
    public List<ContractTemplateModel> getList(ContractTemplateModel contractTemplate);
    
    public ContractTemplateModel getModel(ContractTemplateModel contractTemplate);
    
    public Map<String, Object> selectContractByLoanNo(String loanNo);
    
    public List<Map<String,Object>>selectInvestor4borrowContract(String loanNo);
    
    public Map<String,Object>select4borrowContract(String loanNo);
    
    
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