/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: StationMessageDaoImpl.java 
 *
 * Created: [2015-2-27 上午11:54:54] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.message.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.message.dao.IStationMessageDao;
import com.fbd.core.app.message.model.StationMessageModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.MessageUtils;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 站内信
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("stationMessageDao")
public class StationMessageDaoImpl extends BaseDaoImpl<StationMessageModel> implements 
 IStationMessageDao{

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
    public List<StationMessageModel> getPageList(StationMessageModel model) {
        return this.selectByModel("selectPageList", model);
    }
    
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
    public Long getPageCount(StationMessageModel model) {
        return this.getCount(model);
    }
    
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
    public void saveStationMessage(String userId,String messageType,String messageChildType,Map<String, String> params){
        StationMessageModel model = new StationMessageModel();
        model.setId(PKGenarator.getId());
        model.setMessageType(messageType);
        model.setMessageChildType(messageChildType);
        model.setUserId(userId);
        model.setSendTime(new Date());
        model.setStatus(FbdCoreConstants.STATION_MSG_STATUS_NOREAD);
        String content = MessageUtils.getContent(messageChildType, params);
        model.setContent(content);
        this.save(model);
    }

    
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
    public void saveStationMessage(String userId,String messageType,String messageChildType,String content){
        StationMessageModel model = new StationMessageModel();
        model.setId(PKGenarator.getId());
        model.setMessageType(messageType);
        model.setMessageChildType(messageChildType);
        model.setUserId(userId);
        model.setSendTime(new Date());
        model.setStatus(FbdCoreConstants.STATION_MSG_STATUS_NOREAD);
        model.setContent(content);
        this.save(model);
    }
}
