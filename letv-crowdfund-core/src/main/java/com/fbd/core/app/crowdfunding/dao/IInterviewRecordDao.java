package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.InterviewRecordModel;
import com.fbd.core.base.BaseDao;

public interface IInterviewRecordDao extends BaseDao<InterviewRecordModel>{
    
    public List<Map<String,Object>> getList(InterviewRecordModel model);
    
    public long getCount(InterviewRecordModel model);
    public List<Map<String,Object>> getInterviewList(InterviewRecordModel model);
}