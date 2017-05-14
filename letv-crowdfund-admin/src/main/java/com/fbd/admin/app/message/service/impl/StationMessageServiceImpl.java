package com.fbd.admin.app.message.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.message.service.IStationMessageService;
import com.fbd.core.app.message.dao.IStationMessageDao;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.common.FbdCoreConstants;
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
@Service
public class StationMessageServiceImpl implements IStationMessageService {
    @Resource
    private IStationMessageDao stationMessageDao;
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
    public SearchResult<StationMessageModel> getPageList(
            StationMessageModel model) {
        SearchResult<StationMessageModel> result = new SearchResult<StationMessageModel>();
        result.setRows(stationMessageDao.getPageList(model));
        result.setTotal(stationMessageDao.getPageCount(model));
        return result;
    }
    
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
    public void updateMessage(String id){
        StationMessageModel model = this.stationMessageDao.selectByPrimaryKey(id);
        model.setStatus(FbdCoreConstants.STATION_MSG_STATUS_READ);
        model.setReadTime(new Date());
        this.stationMessageDao.update(model);
    }
    
}
