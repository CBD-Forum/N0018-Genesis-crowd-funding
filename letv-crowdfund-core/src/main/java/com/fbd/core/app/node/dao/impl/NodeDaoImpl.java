package com.fbd.core.app.node.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.node.dao.INodeDao;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository
public class NodeDaoImpl extends BaseDaoImpl<NodeModel> implements INodeDao {

    public int saveNode(NodeModel nodeModel) {
        return this.save(nodeModel);
    }

    public SearchResult<Map<String, Object>> getNodePage(NodeModel nodeModel) {
        SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
        searchResult.setTotal(getNodeCount(nodeModel));
        searchResult.setRows(getNodeList(nodeModel));
        return searchResult;
    }
    public long getNodeCount(NodeModel nodeModel){
        return this.getCount(nodeModel);
    }
    
    public List<Map<String, Object>> getNodeList(NodeModel nodeModel){
        return this.selectMapByFields("select", nodeModel);
    }
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
    public List<NodeModel> getNode(NodeModel node){
        return this.selectByModel("getByModel", node);
    }
    

    public NodeModel getNodeByCode(String code) {
        return this.selectOneByField("getByCode", code);
    }

    public NodeModel getNodeById(String id) {
        return this.selectByPrimaryKey(id);
    }

    public int removeNode(String id) {
        return this.deleteByPrimaryKey(id);
    }

    public int modifyNode(NodeModel nodeModel) {
        return this.updateBySelective(nodeModel);
    }

}
