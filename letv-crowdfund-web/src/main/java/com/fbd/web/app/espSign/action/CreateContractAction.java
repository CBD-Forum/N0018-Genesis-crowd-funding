package com.fbd.web.app.espSign.action;

import java.io.File;
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

import com.fbd.core.app.contract.dao.IContractTemplateDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.app.signature.service.IContractCreateService;
import com.fbd.core.base.BaseAction;
import com.fbd.core.helper.PKGenarator;


@Controller
@Scope("prototype")
@RequestMapping("/createContract")
public class CreateContractAction extends BaseAction {
	
	
	
    @Resource
	private IContractCreateService contractCreateService;
    @Resource
    private ICrowdfundingDao crowdfundingDao;
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;
    @Resource
    private IContractTemplateDao contractTemplateDao;
	
 
    @RequestMapping(value = "/testCreateContract.html", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> sendCreateUserVerifyCode(HttpServletRequest request,String usage,HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
        	
        	String basePath = request.getSession().getServletContext().getRealPath("/")+"tempFile";
	        File file = new File(basePath);
	        if(!file.exists()){
	        	file.mkdirs();
	        }
	   		String inFile = basePath+File.separator+PKGenarator.getId()+".pdf";
            String outFile = basePath+File.separator+PKGenarator.getId()+".pdf";
            //查询合同模板
        	this.contractCreateService.createCrontractPdf("2016010426977933","", inFile, outFile, request, response);
        	
            resultMap.put(SUCCESS,true);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
            resultMap.put(MSG,e.getMessage());
        }
        return resultMap;
    }
    
    
     
	

}
