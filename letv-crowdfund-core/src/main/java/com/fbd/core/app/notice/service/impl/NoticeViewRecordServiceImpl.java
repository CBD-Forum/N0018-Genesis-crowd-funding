/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: NoticeViewRecordServiceImpl.java 
 *
 * Created: [2016-9-3 下午3:49:53] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.notice.service.impl;

import java.util.Date;
import java.util.Random;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.notice.dao.INoticeViewRecordDao;
import com.fbd.core.app.notice.model.NoticeViewRecordModel;
import com.fbd.core.app.notice.service.INoticeViewRecordService;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="noticeViewRecordService")
public class NoticeViewRecordServiceImpl implements INoticeViewRecordService {

    @Resource
    private INoticeViewRecordDao noticeViewRecordDao;
    
    @Override
    public NoticeViewRecordModel queryNoticeViewRecord(String userId,
            String noticeType, String parentId, String state) {
        
        
        NoticeViewRecordModel qmodel = new NoticeViewRecordModel();
        qmodel.setUserId(userId);
        qmodel.setNoticeType(noticeType);
        qmodel.setParentId(parentId);
        qmodel.setState(state);
        try{
            Random random = new Random();
            Thread.sleep(random.nextInt(1000));
        }catch(Exception e){
            e.printStackTrace();
        }
        
        NoticeViewRecordModel noticeViewRecord = this.noticeViewRecordDao.selectByModel(qmodel);
        if(noticeViewRecord==null){
            noticeViewRecord = new NoticeViewRecordModel();
            noticeViewRecord.setId(PKGenarator.getId());
            noticeViewRecord.setUserId(userId);
            noticeViewRecord.setNoticeType(noticeType);
            noticeViewRecord.setParentId(parentId);
            noticeViewRecord.setState(state);
            noticeViewRecord.setLatestClickTime(new Date());
            noticeViewRecord.setLatestNoticeTime(new Date());
            noticeViewRecord.setCreateTime(new Date());
            noticeViewRecord.setUpdateTime(new Date());
            this.noticeViewRecordDao.save(noticeViewRecord);
        }
        return noticeViewRecord;
    }
    
    
    public void modifyNoticeViewRecord(NoticeViewRecordModel model){
        model.setUpdateTime(new Date());
        this.noticeViewRecordDao.update(model);
    }
    

}
