/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: SysTransferAcctDaoImpl.java 
 *
 * Created: [2015-3-14 下午1:24:52] by haolingfeng
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

package com.fbd.core.app.bill.dao.impl;

import org.springframework.stereotype.Repository;
import com.fbd.core.app.bill.dao.ISysTransferAcctDao;
import com.fbd.core.app.bill.model.SysTransferAcctModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 转账
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("sysTransferAcctDao")
public class SysTransferAcctDaoImpl extends BaseDaoImpl<SysTransferAcctModel>
 implements ISysTransferAcctDao{

    public SysTransferAcctModel getByOrderId(String orderId){
        return this.selectOneByField("selectByOrderId", orderId);
    }
}
