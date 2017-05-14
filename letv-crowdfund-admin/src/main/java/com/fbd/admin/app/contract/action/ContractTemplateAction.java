package com.fbd.admin.app.contract.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.app.contract.service.IContractTemplateService;
import com.fbd.admin.utils.ContractUtils;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/contract")
public class ContractTemplateAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2394593235634298391L;
    
    @Resource
    private IContractTemplateService contractTemplateService;
    
    
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveContractTemplate(ContractTemplateModel contractTemplate){
        int num = contractTemplateService.saveContractTemplate(contractTemplate);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_CONTRACT, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS,"合同模版编号："+contractTemplate.getContractNo()+"，添加成功");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getContractTemplatePage(ContractTemplateModel contractTemplate){
        SearchResult<Map<String, Object>> nodePage = contractTemplateService.getContractTemplatePage(contractTemplate);
        return nodePage;
    }
    
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody ContractTemplateModel getContractTemplateById(String id){
        ContractTemplateModel contractTemplateModel = contractTemplateService.getContractTemplateById(id);
        return contractTemplateModel;
    }
    
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeContractTemplate(String id){
        int num = contractTemplateService.removeContractTemplate(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_CONTRACT, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS,"合同模版Id："+id+"，删除成功");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyContractTemplate(ContractTemplateModel contractTemplate){
        int num = contractTemplateService.modifyContractTemplate(contractTemplate);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_CONTRACT, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS,"合同模版："+contractTemplate.getContractNo()+"，修改成功");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    
    public String dealContractTemplateContent(String contractContent){
        Pattern pattern = Pattern.compile("[#{](.*?)[}]");
        return pattern.matcher(contractContent).replaceAll("___");
    }
    
    @RequestMapping(value = "/getTemp.html", method = RequestMethod.GET)
    public @ResponseBody void getContractTemplatePage(HttpServletRequest request,
            HttpServletResponse response){
        ContractTemplateModel contractTemplate=new ContractTemplateModel();
        
        SearchResult<Map<String, Object>> nodePage = contractTemplateService.getContractTemplatePage(contractTemplate);
        List<Map<String, Object>> list = nodePage.getRows();
        Map<String, Object> map = list.get(0);
        String content = (String) map.get("templateContent");
        
        ContractUtils.borrowContract(response,"2015011461056104");
    }
    
    /**
     * Description: 预览模版
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-1 下午7:57:18
     */
    @RequestMapping(value = "/getView.html", method = RequestMethod.GET)
    public void getView4Contract(HttpServletRequest request,HttpServletResponse response,String id){
        ContractTemplateModel contractTemplate=contractTemplateService.getContractTemplateById(id);
        //合同编号
        String contractNo = contractTemplate.getContractNo();
        //调用合同公共方法，contractNo即为项目编号
        ContractUtils.borrowContract(response,contractNo);
    }
}
