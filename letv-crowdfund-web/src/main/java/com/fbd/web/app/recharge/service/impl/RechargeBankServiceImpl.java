/* 
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: RechargeBankServiceImpl.java 
 *
 * Created: [2015-1-7 下午3:49:55] by haolingfeng
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

package com.fbd.web.app.recharge.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.recharge.dao.IRechargeBankDao;
import com.fbd.core.app.recharge.model.RechargeBankModel;
import com.fbd.web.app.recharge.service.IRechargeBankService;

/**
 * Copyright (C) 2014-20150BUG(北京)资产管理有限公司.
 * 
 * Description:充值银行
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("rechargeBankService")
public class RechargeBankServiceImpl implements IRechargeBankService {
    @Resource
    private IRechargeBankDao rechargeBankDao;

    public List<RechargeBankModel> getList(RechargeBankModel model) {
        return rechargeBankDao.getList(model);

    }
}
