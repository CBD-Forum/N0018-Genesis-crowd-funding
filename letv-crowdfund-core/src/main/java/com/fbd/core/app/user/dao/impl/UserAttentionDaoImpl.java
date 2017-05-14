/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserAttentionDaoImpl.java 
 *
 * Created: [2015-6-23 上午10:21:30] by haolingfeng
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

package com.fbd.core.app.user.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.IUserAttentionDao;
import com.fbd.core.app.user.model.UserAttentionModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户关注关系表
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("userAttentionDao")
public class UserAttentionDaoImpl extends BaseDaoImpl<UserAttentionModel>
implements IUserAttentionDao{

    public List<Map<String,Object>> getList(UserAttentionModel model){
        return this.selectMapByFields("selectList",model);
    }
}
