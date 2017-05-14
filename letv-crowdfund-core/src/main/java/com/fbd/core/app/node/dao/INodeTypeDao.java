package com.fbd.core.app.node.dao;

import java.util.List;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;


public interface INodeTypeDao extends BaseDao<NodeTypeModel> {

    /**
     * Description: 分页查询文章节点列表
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    SearchResult<NodeTypeModel> getNodeTypePage(NodeTypeModel nodeType);
    
    
    /**
     * Description: 全查文章节点列表
     *
     * @param 
     * @return List<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午11:35:51
     */
    List<NodeTypeModel> getNodeTypeList(NodeTypeModel nodeType);


    /**
     * Description: 根据编码查询节点类型实体
     *
     * @param 
     * @return NodeTypeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午11:48:43
     */
    NodeTypeModel getNodeTypeByCode(String code);
    
}