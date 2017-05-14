package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundingCommentModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹评论
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface ICrowdfundingCommentDao extends BaseDao<CrowdfundingCommentModel>{
    
    /**
     * 
     * Description: 分页查询
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 下午4:05:02
     */
    public List<Map<String,Object>> getList(CrowdfundingCommentModel model);
    
    public long getCount(CrowdfundingCommentModel model);
}