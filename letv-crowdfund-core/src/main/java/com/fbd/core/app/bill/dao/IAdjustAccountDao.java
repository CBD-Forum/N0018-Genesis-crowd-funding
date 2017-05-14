package com.fbd.core.app.bill.dao;

import java.util.List;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.base.BaseDao;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:调账记录 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IAdjustAccountDao extends BaseDao<AdjustAccountModel>{
    public List<AdjustAccountModel> getList(AdjustAccountModel model);
    public long getCount(AdjustAccountModel model);
}