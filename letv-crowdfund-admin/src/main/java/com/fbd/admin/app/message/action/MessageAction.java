package com.fbd.admin.app.message.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.message.service.IMessageService;
import com.fbd.core.app.message.model.MessageNodeModel;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/message")
public class MessageAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6244496133888715272L;
    
    @Resource
    private IMessageService messageService;
    
    
    /**
     * Description: 分页查询所有消息节点的列表
     *
     * @param 
     * @return SearchResult<MessageNodeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:04
     */
    @RequestMapping(value = "/getNodePage.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<MessageNodeModel> getNodePage(MessageNodeModel node){
        SearchResult<MessageNodeModel> nodePage = messageService.getNodePage(node);
        return nodePage;
    }
    
    @RequestMapping(value = "/getNodeList.html", method = RequestMethod.POST)
    public @ResponseBody List<MessageNodeModel> getNodeList(MessageNodeModel node){
        List<MessageNodeModel> nodeList = messageService.getNodeList(node);
        return nodeList;
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
    @RequestMapping(value = "/getNodeById.html", method = RequestMethod.POST)
    public @ResponseBody MessageNodeModel getNodeById(String id){
        return messageService.getNodeById(id);
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
    @RequestMapping(value = "/modifyNode.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> modifyMessageNode(MessageNodeModel node) {
        int num = messageService.modifyMessageNode(node);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_MESSAGENODE, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "消息节点："+node.getName()+",添加成功");
        } else {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/saveNode.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> saveMessageNode(MessageNodeModel node) {
        int num = messageService.saveMessageNode(node);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_MESSAGENODE, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "消息节点："+node.getName()+",添加成功。");
        } else {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/getTplList.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<MessageTemplateModel> getTemplPage(MessageTemplateModel template){
        SearchResult<MessageTemplateModel> templatePage = messageService.getTemplPage(template);
        return templatePage;
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
    @RequestMapping(value = "/saveTpl.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> saveMessageTpl(MessageTemplateModel template) {
        int num = messageService.saveMessageTpl(template);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_MESSAGETPL, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "消息模版："+template.getTplName()+",添加成功。");
        } else {
            if (num==-1) {
                resultMap.put(MSG, "此用户名已经存在，请更改用户名");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/getTplById.html", method = RequestMethod.POST)
    public @ResponseBody MessageTemplateModel getTplById(String id){
        return messageService.getTplById(id);
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
    @RequestMapping(value = "/removeTpl.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> removeMessageTplById(String id) {
        MessageTemplateModel messageTpl = messageService.getTplById(id);
        int num = messageService.removeMessageTplById(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_MESSAGETPL, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS, "消息模版："+messageTpl.getTplName()+",删除成功。");
        } else {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/modifyTpl.html", method = RequestMethod.POST)
    public @ResponseBody  Map<String, Object> modifyMessageTpl(MessageTemplateModel template) {
        int num = messageService.modifyMessageTpl(template);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_MESSAGETPL, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "消息模版："+template.getTplName()+",修改成功。");
        } else {
            if (num==-1) {
                resultMap.put(MSG, "此用户名已经存在，请更改用户名");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    } 

}
