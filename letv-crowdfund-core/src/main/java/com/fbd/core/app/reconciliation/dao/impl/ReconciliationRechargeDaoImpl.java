package com.fbd.core.app.reconciliation.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.reconciliation.dao.IReconciliationRechargeDao;
import com.fbd.core.app.reconciliation.model.ReconciliationRechargeModel;
import com.fbd.core.base.BaseDaoImpl;

@Repository(value="reconciliationRechargeDao")
public class ReconciliationRechargeDaoImpl extends BaseDaoImpl<ReconciliationRechargeModel> implements IReconciliationRechargeDao {
    
    
    /**
     * 批量写入数据
     */
    public int batchInsert(List<ReconciliationRechargeModel> list){
        return this.sqlSession.insert("batchInsert", list);
    }
}
