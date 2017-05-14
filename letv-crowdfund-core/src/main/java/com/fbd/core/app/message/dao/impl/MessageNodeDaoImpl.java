package com.fbd.core.app.message.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.message.dao.IMessageNodeDao;
import com.fbd.core.app.message.model.MessageNodeModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.FbdCoreConstants.messageNodeStatus;
import com.fbd.core.common.model.SearchResult;

@Repository("messageNodeDao")
public class MessageNodeDaoImpl extends BaseDaoImpl<MessageNodeModel> implements IMessageNodeDao {

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
        SearchResult<MessageNodeModel> result = new SearchResult<MessageNodeModel>();
        result.setTotal(getNodeCount(node));
        result.setRows(getNodeList(node));
        return result;
    }
    
    public long getNodeCount(MessageNodeModel node){
        return this.getCount(node);
    }
    
    public List<MessageNodeModel> getNodeList(MessageNodeModel node){
        return this.selectByModel("select", node);
    }
    public MessageNodeModel getMessageNode(MessageNodeModel node){
         List<MessageNodeModel> aList = this.selectByModel("selectModel", node);
         if(aList.size()>0){
             return aList.get(0);
         }else{
             return null;
         }
    }
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
    public boolean getNodeStatus(String code){
        MessageNodeModel nodeModel = new MessageNodeModel();
        nodeModel.setCode(code);
        MessageNodeModel model =this.getMessageNode(nodeModel);
        if(model==null){
            return false;
        }else{
            if(model.getStatus().equals(messageNodeStatus.on)){
                return true;
            }else{
                return false;
            }
        }
    }
    
    
}
