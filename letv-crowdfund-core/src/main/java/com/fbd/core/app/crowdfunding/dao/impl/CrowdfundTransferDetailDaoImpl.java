/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingCommentDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:28:11] by haolingfeng
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

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDetailDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferDetailModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹挂牌详情
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundTransferDetailDao")
public class CrowdfundTransferDetailDaoImpl extends BaseDaoImpl<CrowdfundTransferDetailModel>
implements ICrowdfundTransferDetailDao{

    /**
     * Description: 统计已经成功购买的份数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-13 下午2:49:21
     */
    public long selectCountBuyParts(String transferNo) {
        // TODO Auto-generated method stub
        return this.getCount("selectCountBuyParts", transferNo);
    }

    /**
     * Description: 根据购买订单号 查询Model
     *
     * @param 
     * @return CrowdfundTransferDetailModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-13 下午4:04:56
     */
    public CrowdfundTransferDetailModel getByBuyOrderId(String orderId) {
        // TODO Auto-generated method stub
        return this.selectOneByField("selectByBuyOrderId", orderId);
    }

    /**
     * 
     * Description:查询成功购买的用户
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    public List<Map<String, Object>> getCrowdfundTransferUserPayed(
            CrowdfundTransferDetailModel model) {
        // TODO Auto-generated method stub
        return this.selectMapByFields("selectCrowdfundTransferUserPayed", model);
    }

}
