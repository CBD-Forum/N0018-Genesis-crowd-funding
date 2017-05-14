/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserAttentionServiceImpl.java 
 *
 * Created: [2015-6-23 上午10:24:23] by haolingfeng
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
 * ProjectName: crowdfund-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.user.dao.IUserAttentionDao;
import com.fbd.core.app.user.model.UserAttentionModel;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.user.service.IUserAttentionService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description: 用户关注关系表
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("userAttentionService")
public class UserAttentionServiceImpl implements IUserAttentionService{
    
    @Resource
    private IUserAttentionDao userAttentionDao;

    /**
     * 
     * Description:保存关注关系表 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-23 上午10:31:31
     */
    public void saveAttention(UserAttentionModel model){
        model.setId(PKGenarator.getId());
        model.setAttentionTime(new Date());
        userAttentionDao.save(model);
    }
    
    /**
     * 
     * Description: 查询关注了哪些人与哪些人关注了我
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-23 上午10:38:00
     */
    public List<Map<String,Object>> getAttentionUser(UserAttentionModel model){
        return userAttentionDao.getList(model);
    }
}
