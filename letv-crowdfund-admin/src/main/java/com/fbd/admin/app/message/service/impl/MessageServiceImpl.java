package com.fbd.admin.app.message.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.message.service.IMessageService;
import com.fbd.core.app.message.dao.IMessageNodeDao;
import com.fbd.core.app.message.dao.IMessageTemplateDao;
import com.fbd.core.app.message.model.MessageNodeModel;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class MessageServiceImpl implements IMessageService {

    @Resource
    private IMessageNodeDao messageNodeDao;
    
    @Resource
    private IMessageTemplateDao messageTemplateDao;

    /**
     * Description: 分页查询所有消息节点的列表
     *
     * @param 
     * @return SearchResult<MessageNodeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:04
     */
    public SearchResult<MessageNodeModel> getNodePage(MessageNodeModel node) {
        return messageNodeDao.getNodePage(node);
    }

    /**
     * Description: 分页查询所有消息模版的列表
     *
     * @param 
     * @return SearchResult<MessageTemplateModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:35
     */
    public SearchResult<MessageTemplateModel> getTemplPage(MessageTemplateModel template) {
        return messageTemplateDao.getTemplPage(template);
    }

    /**
     * Description: 查询所有消息列表，不分页
     *
     * @param 
     * @return List<MessageNodeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 上午10:21:45
     */
    public List<MessageNodeModel> getNodeList(MessageNodeModel node) {
        return messageNodeDao.getNodeList(node);
    }

    /**
     * Description: 添加消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 上午11:15:35
     */
    public int saveMessageTpl(MessageTemplateModel template) {
        template.setId(PKGenarator.getId());
        return messageTemplateDao.saveMessageTpl(template);
    }

    /**
     * Description: 根据Id查询消息节点对象
     *
     * @param 
     * @return MessageNodeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午5:54:26
     */
    public MessageNodeModel getNodeById(String id) {
        return this.messageNodeDao.selectByPrimaryKey(id);
    }

    /**
     * Description: 修改消息节点
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午6:02:35
     */
    public int modifyMessageNode(MessageNodeModel node) {
        return this.messageNodeDao.updateBySelective(node);
    }

    /**
     * Description: 根据Id查询消息模版对象
     *
     * @param 
     * @return MessageTemplateModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午6:21:09
     */
    public MessageTemplateModel getTplById(String id) {
        return this.messageTemplateDao.selectByPrimaryKey(id);
    }

    /**
     * Description: 根据Id删除消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午6:33:48
     */
    public int removeMessageTplById(String id) {
        return this.messageTemplateDao.deleteByPrimaryKey(id);
    }

    /**
     * Description: 保存消息节点
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午9:52:42
     */
    public int saveMessageNode(MessageNodeModel node) {
        node.setId(PKGenarator.getId());
        return this.messageNodeDao.save(node);
    }

    /**
     * Description: 修改消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-2 下午5:38:53
     */
    public int modifyMessageTpl(MessageTemplateModel template) {
        return messageTemplateDao.updateBySelective(template);
    }
}
