package com.fbd.admin.app.node.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.node.service.INodeService;
import com.fbd.admin.web.MyRequestContextHolder;
import com.fbd.core.app.node.dao.INodeDao;
import com.fbd.core.app.node.dao.INodeTypeDao;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.app.node.model.NodeTypeModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class NodeServiceImpl implements INodeService {

    @Resource
    private INodeDao nodeDao;
    
    @Resource
    private INodeTypeDao nodeTypeDao;

    public int saveNode(NodeModel nodeModel) {
        NodeModel node = this.getNodeByCode(nodeModel.getCode());
        if (node!=null) {
            return -1;
        }
        nodeModel.setId(PKGenarator.getId());
        Date createDate= new Date();
        nodeModel.setCreateTime(createDate);
        nodeModel.setUpdateTime(createDate);
        String userId=MyRequestContextHolder.getCurrentUser().getAdminId();
        nodeModel.setCreator(userId);
        nodeModel.setLastModifyUser(userId);
        return nodeDao.saveNode(nodeModel);
    }

    public SearchResult<Map<String, Object>> getNodePage(NodeModel nodeModel) {
        return nodeDao.getNodePage(nodeModel);
    }

    public NodeModel getNodeByCode(String code) {
        return nodeDao.getNodeByCode(code);
    }

    public NodeModel getNodeById(String id) {
        return nodeDao.getNodeById(id);
    }

    public int removeNode(String id) {
        return nodeDao.removeNode(id);
    }

    public int modifyNode(NodeModel nodeModel) {
    	nodeModel.setUpdateTime(new Date());
        return nodeDao.modifyNode(nodeModel);
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
    public SearchResult<NodeTypeModel> getNodeTypePage(NodeTypeModel nodeType) {
        return nodeTypeDao.getNodeTypePage(nodeType);
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
    public int saveNodeType(NodeTypeModel nodeType) {
        NodeTypeModel nodeType4Select = nodeTypeDao.getNodeTypeByCode(nodeType.getCode());
        if (nodeType4Select != null) {
           return -1; 
        }
        nodeType.setId(PKGenarator.getId());
        return nodeTypeDao.save(nodeType);
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
    public int removeNodeType(String id) {
        return nodeTypeDao.deleteByPrimaryKey(id);
    }

    public NodeTypeModel getNodeTypeById(String id) {
        return nodeTypeDao.selectByPrimaryKey(id);
    }

    public int modifyNodeType(NodeTypeModel nodeType) {
        return nodeTypeDao.updateBySelective(nodeType);
    }
}
