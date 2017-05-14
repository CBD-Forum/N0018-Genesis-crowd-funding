package com.fbd.core.app.node.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.common.model.SearchResult;


public interface INodeDao {

    int saveNode(NodeModel nodeModel);

    SearchResult<Map<String, Object>> getNodePage(NodeModel nodeModel);

    NodeModel getNodeByCode(String code);

    NodeModel getNodeById(String id);

    int removeNode(String id);

    int modifyNode(NodeModel nodeModel);
    
    /**
     * 
     * Description:根据节点类型查询 
     *
     * @param 
     * @return List<NodeModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-2 下午5:59:27
     */
    public List<NodeModel> getNode(NodeModel node);
    
}