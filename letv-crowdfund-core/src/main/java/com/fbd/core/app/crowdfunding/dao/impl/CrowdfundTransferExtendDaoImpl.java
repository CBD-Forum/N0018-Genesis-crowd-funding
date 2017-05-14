/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundTransferExtendDaoImpl.java 
 *
 * Created: [2016-9-20 下午5:39:46] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferExtendDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author hanchenghe
 * @version 1.0
 *
 */
@Repository("crowdfundTransferExtendDao")
public class CrowdfundTransferExtendDaoImpl extends BaseDaoImpl<CrowdfundTransferExtendModel> implements ICrowdfundTransferExtendDao{

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferExtendDao#deleteUserAndTrans(com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel)
     */
    @Override
    public int deleteUserAndTrans(CrowdfundTransferExtendModel model) {
        // TODO Auto-generated method stub
        if(StringUtils.isEmpty(model.getTransferNo())){
            throw new ApplicationException("转让编号不能为空");
        }
        if(StringUtils.isEmpty(model.getUserId())){
            throw new ApplicationException("用户编号不能为空");
        }
        return this.deleteByField("deleteUserAndTrans", model);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferExtendDao#findByModel(com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel)
     */
    @Override
    public List<CrowdfundTransferExtendModel> findByModel(
            CrowdfundTransferExtendModel model) {
        // TODO Auto-generated method stub
        return this.selectByField("findByModel", model);
    }

}
