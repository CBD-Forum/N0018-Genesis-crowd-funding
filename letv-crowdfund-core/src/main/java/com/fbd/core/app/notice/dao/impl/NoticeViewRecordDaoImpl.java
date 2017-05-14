/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: NoticeViewRecordDaoImpl.java 
 *
 * Created: [2016-9-3 下午3:18:15] by haolingfeng
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

package com.fbd.core.app.notice.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.notice.dao.INoticeViewRecordDao;
import com.fbd.core.app.notice.model.NoticeViewRecordModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository(value="noticeViewRecordDao")
public class NoticeViewRecordDaoImpl extends BaseDaoImpl<NoticeViewRecordModel> implements
        INoticeViewRecordDao {
    
    @Override
    public NoticeViewRecordModel selectByModel(NoticeViewRecordModel model) {
        return this.selectOneByField("selectByModel", model);
    }

}
