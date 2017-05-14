package com.fbd.web.app.htmlForPDF.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.utils.ContractUtils;
import com.fbd.web.app.node.service.INodeService;
@Controller
@Scope("prototype")
@RequestMapping("/htmlforpdf")
public class HtmlForPDFAction extends BaseAction{

	private static final Logger logger = LoggerFactory.getLogger(HtmlForPDFAction.class);
	
	@Resource
    private INodeService nodeService;
	/**
     * Description: 下载合同PDF
     *
     * @param 
     * @return void
     * @throws 
     */
    @RequestMapping(value = "/getContractView.html", method = RequestMethod.GET)
    public void getView4Contract(HttpServletResponse response,NodeModel nodeModel){
    	List<NodeModel> node = nodeService.getNode(nodeModel);
        ContractUtils.downloadContract(response,node.get(0).getBody(),node.get(0).getNodeType());
    }
}
