package com.fbd.web.app.node.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.node.dao.INodeDao;
import com.fbd.core.app.node.model.NodeModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.node.service.INodeService;

@Service
public class NodeServiceImpl implements INodeService {

    @Resource
    private INodeDao nodeDao;
    

    /**
     * Description: 查询文章
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    public List<NodeModel> getNode(NodeModel node) {
        return nodeDao.getNode(node);
    }
    
    /**
     * Description: 查询文章(分页)
     *
     * @param 
     * @return SearchResult<NodeTypeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-2 上午10:40:29
     */
    public SearchResult<Map<String,Object>> getPageNode(NodeModel node) {
    	return nodeDao.getNodePage(node);
    }
    
    public NodeModel getNodeById(String id) {
        return nodeDao.getNodeById(id);
    }
}
