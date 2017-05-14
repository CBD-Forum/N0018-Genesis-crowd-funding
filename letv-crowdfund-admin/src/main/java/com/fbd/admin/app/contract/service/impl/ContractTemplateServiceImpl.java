package com.fbd.admin.app.contract.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.contract.service.IContractTemplateService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service(value="contractTemplateService")
public class ContractTemplateServiceImpl implements IContractTemplateService {

    @Resource
    private IContractTemplateDao contractTemplateDao;

    public int saveContractTemplate(ContractTemplateModel contractTemplate) {
        contractTemplate.setId(PKGenarator.getId());
        Date createDate= new Date();
        contractTemplate.setCreateTime(createDate);
        contractTemplate.setUpdateTime(createDate);
        String userId=MyRequestContextHolder.getCurrentUser().getAdminId();
        contractTemplate.setCreator(userId);
        contractTemplate.setLastModifyUser(userId);
        return contractTemplateDao.saveContractTemplate(contractTemplate);
    }

    public SearchResult<Map<String, Object>> getContractTemplatePage(ContractTemplateModel contractTemplate) {
        return contractTemplateDao.getContractTemplatePage(contractTemplate);
    }

    public ContractTemplateModel getContractTemplateById(String id) {
        return contractTemplateDao.getContractTemplateById(id);
    }

    public int removeContractTemplate(String id) {
        return contractTemplateDao.removeContractTemplate(id);
    }

    public int modifyContractTemplate(ContractTemplateModel contractTemplate) {
        return contractTemplateDao.modifyContractTemplate(contractTemplate);
    }
    
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
    public void copyContractTemplate(String loanNo,String contractTmpNo){
/*        ContractTemplateModel qmodel = new ContractTemplateModel();
        qmodel.setContractNo(contractTmpNo);
        ContractTemplateModel contractTemplate = this.contractTemplateDao.getModel(qmodel);
        ContractTemplateModel newModel = new ContractTemplateModel();
        BeanUtils.copyProperties(contractTemplate, newModel);
        newModel.setId(PKGenarator.getId());
        newModel.setContractNo(loanNo);
        this.contractTemplateDao.saveContractTemplate(newModel);*/
    	this.contractTemplateDao.copyContractTemplate(loanNo, contractTmpNo);
    }
}
