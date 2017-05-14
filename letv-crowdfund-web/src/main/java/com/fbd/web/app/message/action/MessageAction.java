package com.fbd.web.app.message.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.message.service.IStationMessageService;

@Controller
@Scope("prototype")
@RequestMapping("/message")
public class MessageAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6244496133888715272L;
    
    @Resource
    private IStationMessageService stationMessageService;
    
    
    /**
     * Description: 分页查询所有消息节点的列表
     *
     * @param 
     * @return SearchResult<MessageNodeModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:04
     */
    @RequestMapping(value = "/getMessageList.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> getMessageList(HttpServletRequest request,StationMessageModel model){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            String userId = this.getUserId(request);
            model.setUserId(userId);
            model.setSort("sendTime");
            SearchResult<StationMessageModel> result = stationMessageService.getPageList(model);
            //查询未读消息数量
            StationMessageModel qmodel1 = new StationMessageModel();
            qmodel1.setUserId(userId);
            qmodel1.setStatus("0");
            long unReadCount = this.stationMessageService.selectMessageCount(qmodel1);
            
            StationMessageModel qmodel2 = new StationMessageModel();
            qmodel2.setUserId(userId);
            qmodel2.setStatus("1");
            long readCount = this.stationMessageService.selectMessageCount(qmodel2);
            resultMap.put("unReadCount",unReadCount);
            resultMap.put("readCount",readCount);
            resultMap.put(SUCCESS, true);
            resultMap.put(MSG, result);
       }catch(Exception e){
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
       return resultMap;
    }
    
    /**
     * 
     * Description: 阅读消息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午3:02:27
     */
    @RequestMapping(value = "/updateMsg.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> updateMsgStatus(String id){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            stationMessageService.updateMessage(id);
            resultMap.put(SUCCESS, true);
       }catch(Exception e){
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
       return resultMap;
    } 
    
    
    
    
    /**
     * 
     * Description: 删除消息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午3:02:27
     */
    @RequestMapping(value = "/batchDelete.html", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> batchDelete(String ids){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try{
            stationMessageService.delMessage(ids);
            resultMap.put(SUCCESS, true);
       }catch(Exception e){
           e.printStackTrace();
           resultMap.put(SUCCESS, false);
       }
       return resultMap;
    } 
    
}
