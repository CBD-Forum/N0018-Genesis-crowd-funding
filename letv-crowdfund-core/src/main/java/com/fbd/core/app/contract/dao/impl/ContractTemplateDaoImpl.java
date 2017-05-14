package com.fbd.core.app.contract.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Repository(value="contractTemplateDao")
public class ContractTemplateDaoImpl extends BaseDaoImpl<ContractTemplateModel> implements IContractTemplateDao {

    private static final Logger logger = LoggerFactory.getLogger(ContractTemplateDaoImpl.class);
    
    public int saveContractTemplate(ContractTemplateModel contractTemplate) {
        return this.save(contractTemplate);
    }

    public SearchResult<Map<String, Object>> getContractTemplatePage(ContractTemplateModel contractTemplate) {
        SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
        searchResult.setTotal(getContractTemplateCount(contractTemplate));
        searchResult.setRows(getContractTemplateList(contractTemplate));
        return searchResult;
    }
    
    public long getContractTemplateCount(ContractTemplateModel contractTemplate){
        return this.getCount(contractTemplate);
    }
    
    public List<Map<String, Object>> getContractTemplateList(ContractTemplateModel contractTemplate){
        return this.selectMapByFields("select", contractTemplate);
    }
    
    public List<ContractTemplateModel> getList(ContractTemplateModel contractTemplate){
        return this.selectByModel("selectByModel", contractTemplate);
    }
    
    public ContractTemplateModel getModel(ContractTemplateModel contractTemplate){
        List<ContractTemplateModel> aList = this.selectByModel("selectByModel", contractTemplate);
        if(aList.size()>0){
            return aList.get(0);
        }else{
            return null;
        }
    }

    public ContractTemplateModel getContractTemplateById(String id) {
        return this.selectByPrimaryKey(id);
    }

    public int removeContractTemplate(String id) {
        return this.deleteByPrimaryKey(id);
    }

    public int modifyContractTemplate(ContractTemplateModel contractTemplate) {
        return this.updateBySelective(contractTemplate);
    }
    
    public Map<String, Object> selectContractByLoanNo(String loanNo){
        return this.selectOneMapByField("selectContractByLoanNo", loanNo);
    }
    
    public List<Map<String,Object>>selectInvestor4borrowContract(String loanNo){
        return this.selectMapByFields("selectInvestor4borrowContract", loanNo);
    }
    
    public Map<String,Object>select4borrowContract(String loanNo){
        return this.selectOneMapByField("select4borrowContract", loanNo);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.contract.dao.IContractTemplateDao#copyContractTemplate(java.lang.String, java.lang.String)
     */
    @Override
    public void copyContractTemplate(String loanNo, String contractTmpNo) {
        // TODO Auto-generated method stub
        ContractTemplateModel qmodel = new ContractTemplateModel();
        qmodel.setContractNo(contractTmpNo);
        ContractTemplateModel contractTemplate = this.getModel(qmodel);
        ContractTemplateModel newModel = new ContractTemplateModel();
        BeanUtils.copyProperties(contractTemplate, newModel);
        newModel.setId(PKGenarator.getId());
        newModel.setContractNo(loanNo);
        this.saveContractTemplate(newModel);
    }

}
