package com.fbd.core.app.message.dao;

import java.util.List;
import com.fbd.core.app.message.model.MessageNodeModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

public interface IMessageNodeDao extends BaseDao<MessageNodeModel> {

    /**
     * Description: 分页查询所有消息节点的列表
     *
     * @param 
     * @return SearchResult<MessageNodeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:04
     */
    SearchResult<MessageNodeModel> getNodePage(MessageNodeModel node);
    
    /**
     * Description: 查询所有消息列表，不分页
     *
     * @param 
     * @return List<MessageNodeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 上午10:21:45
     */
    List<MessageNodeModel> getNodeList(MessageNodeModel node);
    
    /**
     * 
     * Description: 获取消息节点的状态，true为启用，false为不启用
     *
     * @param 
     * @return boolean
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-10 下午6:38:08
     */
    public boolean getNodeStatus(String code);
}