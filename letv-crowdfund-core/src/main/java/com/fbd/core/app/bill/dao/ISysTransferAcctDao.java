package com.fbd.core.app.bill.dao;

import com.fbd.core.app.bill.model.SysTransferAcctModel;
import com.fbd.core.base.BaseDao;

public interface ISysTransferAcctDao extends BaseDao<SysTransferAcctModel>{
    public SysTransferAcctModel getByOrderId(String orderId);
}