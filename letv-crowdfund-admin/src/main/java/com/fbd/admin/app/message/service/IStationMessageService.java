package com.fbd.admin.app.message.service;

import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
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
}
