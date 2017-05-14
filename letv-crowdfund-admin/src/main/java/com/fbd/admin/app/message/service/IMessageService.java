package com.fbd.admin.app.message.service;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.message.model.MessageNodeModel;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.common.model.SearchResult;

public interface IMessageService {

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
     * Description: 分页查询所有消息模版的列表
     *
     * @param 
     * @return SearchResult<MessageTemplateModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:35
     */
    SearchResult<MessageTemplateModel> getTemplPage(MessageTemplateModel template);

    /**
     * Description: 添加消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 上午11:15:35
     */
    @PreAuthorize("hasPermission(null, 'security.operation.message_tpl_add')")
    int saveMessageTpl(MessageTemplateModel template);

    /**
     * Description: 根据Id查询消息节点对象
     *
     * @param 
     * @return MessageNodeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午5:54:26
     */
    MessageNodeModel getNodeById(String id);

    /**
     * Description: 修改消息节点
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午6:02:35
     */
    @PreAuthorize("hasPermission(null, 'security.operation.message_node_modify')")
    int modifyMessageNode(MessageNodeModel node);

    /**
     * Description: 根据Id查询消息模版对象
     *
     * @param 
     * @return MessageTemplateModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午6:21:09
     */
    MessageTemplateModel getTplById(String id);

    /**
     * Description: 根据Id删除消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午6:33:48
     */
    @PreAuthorize("hasPermission(null, 'security.operation.message_tpl_delete')")
    int removeMessageTplById(String id);

    /**
     * Description: 保存消息节点
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午9:52:42
     */
    @PreAuthorize("hasPermission(null, 'security.operation.message_node_add')")
    int saveMessageNode(MessageNodeModel node);

    /**
     * Description: 修改消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-2 下午5:38:53
     */
    @PreAuthorize("hasPermission(null, 'security.operation.message_tpl_modify')")
    int modifyMessageTpl(MessageTemplateModel template);

}
