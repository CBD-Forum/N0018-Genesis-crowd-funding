package com.fbd.web.app.node.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.node.service.INodeService;

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
     * Description: 查询文章列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:33:19
     */
    @RequestMapping(value = "/getNode.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getNode(NodeModel nodeModel){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            List<NodeModel> node = nodeService.getNode(nodeModel);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, node);
       }catch(Exception e){
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    }
    
    /**
     * Description: 查询文章列表(分页)
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:33:19
     */
    @RequestMapping(value = "/getPageNode.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getPageNode(NodeModel nodeModel){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
        	SearchResult<Map<String,Object>> node = nodeService.getPageNode(nodeModel);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, node);
       }catch(Exception e){
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    }
    
    /**
     * Description: 查询文章详情
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:33:19
     */
    @RequestMapping(value = "/getNodeDetail.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getNodeDetail(String id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            NodeModel node = nodeService.getNodeById(id);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, node);
       }catch(Exception e){
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
        return resultMap;
    }
    
}
