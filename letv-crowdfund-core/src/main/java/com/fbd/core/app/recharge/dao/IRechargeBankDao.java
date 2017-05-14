package com.fbd.core.app.recharge.dao;

import java.util.List;
import com.fbd.core.app.recharge.model.RechargeBankModel;
import com.fbd.core.base.BaseDao;

public interface IRechargeBankDao extends BaseDao<RechargeBankModel> {
    public List<RechargeBankModel> getList(RechargeBankModel model);
}