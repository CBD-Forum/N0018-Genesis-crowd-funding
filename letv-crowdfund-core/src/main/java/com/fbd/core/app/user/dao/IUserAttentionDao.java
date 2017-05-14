package com.fbd.core.app.user.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.user.model.UserAttentionModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用户关注关系表 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IUserAttentionDao extends BaseDao<UserAttentionModel>{
    public List<Map<String,Object>> getList(UserAttentionModel model);
}