/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: INoticeViewRecordService.java 
 *
 * Created: [2016-9-3 下午3:47:54] by haolingfeng
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

package com.fbd.core.app.notice.service;

import com.fbd.core.app.notice.model.NoticeViewRecordModel;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @version 1.0
 *
 */

public interface INoticeViewRecordService {
    
    /**
     * 获取通知信息
     * Description: 
     * @param 
     * @return NoticeViewRecordModel
     * @throws 
     * Create Date: 2016-9-3 下午3:49:11
     */
    public NoticeViewRecordModel queryNoticeViewRecord(String userId,String noticeType,String parentId,String state);
    
    public void modifyNoticeViewRecord(NoticeViewRecordModel model);

}
