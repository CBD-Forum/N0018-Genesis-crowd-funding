package com.fbd.core.app.reconciliation.dao;

import java.util.List;
import com.fbd.core.app.reconciliation.model.ReconciliationRechargeModel;
import com.fbd.core.base.BaseDao;

public interface IReconciliationRechargeDao extends BaseDao<ReconciliationRechargeModel> {
    
    
    /**
     * 批量写入数据
     */
    public int batchInsert(List<ReconciliationRechargeModel> list);
    
}
   