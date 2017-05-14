package com.fbd.admin.app.node.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.node.service.INodeService;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/node")
public class NodeAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -5033588474594719230L;
    
    @Resource
    private INodeService nodeService;
    
    
    /**
     * Description: 添加文章
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 下午4:37:52
     */
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveNode(NodeModel nodeModel){
        Map<String, Object> resultMap = new HashMap<String, Object>();
    	try{
    	       int num = nodeService.saveNode(nodeModel);

    	        if (num == 1) {
    	            resultMap.put(SUCCESS, true);
    	            AuditLogUtils.log(AuditLogConstants.MODEL_NODE, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "文章："+nodeModel.getCode()+",添加成功");
    	        }else{
    	            if (num==-1) {
    	                resultMap.put(MSG, "已经存在相同的编码");
    	            }
    	            resultMap.put(SUCCESS, false);
    	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
 
        return resultMap;
    }
    
    /**
     * Description: 分页查询文章列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:33:19
     */
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getNodePage(NodeModel nodeModel){
        SearchResult<Map<String, Object>> nodePage = nodeService.getNodePage(nodeModel);
        return nodePage;
        
    }
    
    @RequestMapping(value = "/getByCode.html", method = RequestMethod.POST)
    public @ResponseBody NodeModel getNodeByCode(String code){
        return nodeService.getNodeByCode(code);
    }
    
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody NodeModel getNodeById(String id){
        return nodeService.getNodeById(id);
    }
    
    /**
     * Description: 修改文章
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 下午4:39:42
     */
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyNode(NodeModel nodeModel){
        int num = nodeService.modifyNode(nodeModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_NODE, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "文章："+nodeModel.getCode()+",修改成功");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeNode(String id){
        int num = nodeService.removeNode(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_NODE, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS, "文章："+id+",删除成功");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    /**
     * Description: 查询文章节点列表
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    @RequestMapping(value = "/getTypePage.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<NodeTypeModel> getNodeTypePage(NodeTypeModel nodeType){
        SearchResult<NodeTypeModel> nodeTypePage = nodeService.getNodeTypePage(nodeType);
        return nodeTypePage;
        
    }
    
    /**
     * Description: 添加文章分类节点
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:42:28
     */
    @RequestMapping(value = "/saveType.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveNodeType(NodeTypeModel nodeType){
        int num = nodeService.saveNodeType(nodeType);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_NODETYPE, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "文章类型："+nodeType.getName()+",添加成功");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 修改文章类型
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 下午4:30:05
     */
    @RequestMapping(value = "/modifyType.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyNodeType(NodeTypeModel nodeType){
        int num = nodeService.modifyNodeType(nodeType);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_NODETYPE, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "文章类型："+nodeType.getCode()+",修改成功");
        }else{
            if (num==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 根据Id查询文章类型
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 下午4:15:23
     */
    @RequestMapping(value = "/getTypeById.html", method = RequestMethod.POST)
    public @ResponseBody NodeTypeModel getNodeTypeById(String id){
        return nodeService.getNodeTypeById(id);
    } 
    
    /**
     * Description: 删除文章节点类型
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午11:28:03
     */
    @RequestMapping(value = "/removeType.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeNodeType(String id){
        int num = nodeService.removeNodeType(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_NODETYPE, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS, "文章类型："+id+",删除成功");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 查询全部的文章类型列表
     *
     * @param 
     * @return List<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 下午12:02:28
     */
    @RequestMapping(value = "/getTypeList.html", method = RequestMethod.POST)
    public @ResponseBody List<NodeTypeModel> getNodeTypeList(){
        return nodeService.getNodeTypePage(null).getRows();
    }
    
}
