package com.fbd.core.app.node.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.node.dao.INodeTypeDao;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository
public class NodeTypeDaoImpl extends BaseDaoImpl<NodeTypeModel> implements INodeTypeDao {

    /**
     * Description: 查询文章节点列表
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    public SearchResult<NodeTypeModel> getNodeTypePage(NodeTypeModel nodeType) {
        SearchResult<NodeTypeModel> result = new SearchResult<NodeTypeModel>();
        result.setRows(getNodeTypeList(nodeType));
        result.setTotal(getNodeTypeList(nodeType).size());
        return result;
    }
    
    
    public List<NodeTypeModel> getNodeTypeList(NodeTypeModel nodeType){
        return this.selectByField("select", nodeType);
    }


    /**
     * Description: 根据编码查询节点类型实体
     *
     * @param 
     * @return NodeTypeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午11:48:43
     */
    public NodeTypeModel getNodeTypeByCode(String code) {
        return this.selectOneByField("selectByCode", code);
    }
    
}
