/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IRechargeBankService.java 
 *
 * Created: [2015-1-7 下午3:49:36] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.recharge.service;

import java.util.List;
import com.fbd.core.app.recharge.model.RechargeBankModel;

/**
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IRechargeBankService {
    public List<RechargeBankModel> getList(RechargeBankModel model);

}
