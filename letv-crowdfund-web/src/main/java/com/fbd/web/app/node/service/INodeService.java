package com.fbd.web.app.node.service;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.common.model.SearchResult;

public interface INodeService {

    /**
     * Description: 查询文章
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    public List<NodeModel> getNode(NodeModel node);
    
    public NodeModel getNodeById(String id);
    
    /**
     * Description: 查询文章(分页)
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    public SearchResult<Map<String,Object>> getPageNode(NodeModel node);

}
