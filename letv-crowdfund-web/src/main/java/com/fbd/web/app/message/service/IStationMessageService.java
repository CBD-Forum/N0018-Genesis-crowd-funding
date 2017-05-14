package com.fbd.web.app.message.service;

import java.util.Map;
import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description: 站内信
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IStationMessageService {

    /**
     * 
     * Description: 分页查询站内信
     *
     * @param 
     * @return SearchResult<StationMessageModel>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午12:25:22
     */
    SearchResult<StationMessageModel> getPageList(StationMessageModel model);
    
     
    /**
     * 查询消息数量
     * @param model
     * @return
     */
    public long selectMessageCount(StationMessageModel model);
    
    
    
    /**
     * 
     * Description: 阅读消息，更新状态
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午3:07:46
     */
    public void updateMessage(String id);
    
    /**
     * 删除消息
     * @param ids
     */
    public void delMessage(String ids);
}
