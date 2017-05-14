/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IMessageTemplateDao.java 
 *
 * Created: [2014-12-15 下午6:27:55] by haolingfeng
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

package com.fbd.core.app.message.dao;

import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:信息模板
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IMessageTemplateDao extends BaseDao<MessageTemplateModel> {
    /**
     * 
     * Description: 根据模板编码找模板
     * 
     * @param
     * @return MessageTemplateModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 上午11:07:01
     */
    public MessageTemplateModel findByCode(String tplCode);

    /**
     * Description: 分页查询所有消息模版的列表
     *
     * @param 
     * @return SearchResult<MessageTemplateModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-4 下午12:20:35
     */
    public SearchResult<MessageTemplateModel> getTemplPage(MessageTemplateModel template);

    /**
     * Description: 添加消息模版
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-2-27 上午11:15:35
     */
    public int saveMessageTpl(MessageTemplateModel template);
}
