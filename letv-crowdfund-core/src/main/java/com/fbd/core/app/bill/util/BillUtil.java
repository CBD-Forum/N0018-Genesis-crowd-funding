/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BillUtil.java 
 *
 * Created: [2014-12-22 下午4:20:28] by haolingfeng
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

package com.fbd.core.app.bill.util;

import com.fbd.core.common.FbdCoreConstants;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 账单工具类
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class BillUtil {
    public static Double judgeDirection(Double balance, String direction) {
        String out = FbdCoreConstants.tradeDirection.out.getValue();
        String freeze = FbdCoreConstants.tradeDirection.freeze.getValue();
        if (direction.equals(out) || direction.equals(freeze)) {// 出与冻结
            return -balance;
        } else {
            return balance;
        }
    }
}
