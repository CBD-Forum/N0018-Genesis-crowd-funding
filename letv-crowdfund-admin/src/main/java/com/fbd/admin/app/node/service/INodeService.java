package com.fbd.admin.app.node.service;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.common.model.SearchResult;

public interface INodeService {

    @PreAuthorize("hasPermission(null, 'security.operation.content_manage_add')")
    int saveNode(NodeModel nodeModel);

    SearchResult<Map<String, Object>> getNodePage(NodeModel nodeModel);

    NodeModel getNodeByCode(String code);

    NodeModel getNodeById(String id);

    @PreAuthorize("hasPermission(null, 'security.operation.content_manage_delete')")
    int removeNode(String id);

    @PreAuthorize("hasPermission(null, 'security.operation.content_manage_modify')")
    int modifyNode(NodeModel nodeModel);

    /**
     * Description: 查询文章节点列表
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    SearchResult<NodeTypeModel> getNodeTypePage(NodeTypeModel nodeType);

    /**
     * Description: 添加文章分类节点
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:42:28
     */
    @PreAuthorize("hasPermission(null, 'security.operation.content_type_add')")
    int saveNodeType(NodeTypeModel nodeType);

    /**
     * Description: 删除文章节点类型
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午11:28:03
     */
    @PreAuthorize("hasPermission(null, 'security.operation.content_type_delete')")
    int removeNodeType(String id);

    /**
     * Description: 根据Id查询文章类型
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 下午4:15:23
     */
    NodeTypeModel getNodeTypeById(String id);

    /**
     * Description: 修改文章类型
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-1 下午4:30:05
     */
    @PreAuthorize("hasPermission(null, 'security.operation.content_type_modify')")
    int modifyNodeType(NodeTypeModel nodeType);

}
