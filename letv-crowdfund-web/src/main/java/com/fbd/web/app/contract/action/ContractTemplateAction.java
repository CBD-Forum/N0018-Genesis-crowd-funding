package com.fbd.web.app.contract.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.admin.utils.ContractUtils;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.contract.service.IContractTemplateService;

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
    
    /**
     * 
     * Description:获得投资协议模板 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 上午11:03:39
     */
    @RequestMapping(value = "/getInvestContract.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getContractTemplateById(){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            ContractTemplateModel model = contractTemplateService.getInvestConstactModel();
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, model);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
