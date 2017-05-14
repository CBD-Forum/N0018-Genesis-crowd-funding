package com.fbd.core.app.notice.dao;

import com.fbd.core.app.notice.model.NoticeViewRecordModel;
import com.fbd.core.base.BaseDao;

public interface INoticeViewRecordDao extends BaseDao<NoticeViewRecordModel> {
    
    
    public NoticeViewRecordModel selectByModel(NoticeViewRecordModel model);
   
}