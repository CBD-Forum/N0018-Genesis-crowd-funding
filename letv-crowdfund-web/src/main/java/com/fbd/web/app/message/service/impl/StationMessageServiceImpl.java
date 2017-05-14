package com.fbd.web.app.message.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.message.dao.IStationMessageDao;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.web.app.message.service.IStationMessageService;
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
        result.setTotal(selectMessageCount(model));
        return result;
    }
    
    /**
     * 查询消息数量
     * @param model
     * @return
     */
    public long selectMessageCount(StationMessageModel model){
    	return this.stationMessageDao.getPageCount(model);
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
    
    /**
     * 删除消息
     * @param ids
     */
    public void delMessage(String ids){
    	
    	if(ids==null || "".equals(ids)){
    		throw new ApplicationException("请选择要删除的消息");
    	}
    	String idCode[] = ids.split(",");
    	if(idCode.length>0){
    		for(String id:idCode){
    			this.stationMessageDao.deleteByPrimaryKey(id);
    		}
    	}
    }
}
