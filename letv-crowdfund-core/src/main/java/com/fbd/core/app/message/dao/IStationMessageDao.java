package com.fbd.core.app.message.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.base.BaseDao;
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
public interface IStationMessageDao extends BaseDao<StationMessageModel>{
    /**
     * 
     * Description:分页查询 
     *
     * @param 
     * @return List<StationMessageModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-27 上午11:52:54
     */
    List<StationMessageModel> getPageList(StationMessageModel model);
    /**
     * 
     * Description: 分页总条数
     *
     * @param 
     * @return Long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-2-27 上午11:58:40
     */
    public Long getPageCount(StationMessageModel model);
    /**
     * 
     * Description:保存站内信 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午5:09:46
     */
    public void saveStationMessage(String userId,String messageType,String messageChildType,Map<String, String> params);
    
    /**
     * 
     * Description:保存站内信 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-27 下午5:09:46
     */
    public void saveStationMessage(String userId,String messageType,String messageChildType,String content);

}