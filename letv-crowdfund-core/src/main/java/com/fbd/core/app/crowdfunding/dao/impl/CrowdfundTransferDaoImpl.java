/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingCommentDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:28:11] by wuwenbin
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

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹挂牌
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundTransferDao")
public class CrowdfundTransferDaoImpl extends BaseDaoImpl<CrowdfundTransferModel>
implements ICrowdfundTransferDao{

    /**
     * Description: 通过转让编号查询model
     *
     * @param 
     * @return CrowdfundTransferModel
     * @throws 
     * @Author wuwenbin
     */
    public CrowdfundTransferModel selectModelByTransferNo(String transferNo) {
        // TODO Auto-generated method stub
        return this.selectOneByField("selectModelByTransferNo", transferNo);
    }

    /**
     * Description: 查询股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 上午10:33:57
     */
    public List<Map<String, Object>> getCrowdfundingTransferAuditList(
            CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.selectMapByFields("selectCrowdfundingTransferAuditList", model);
    }

    /**
     * Description: 统计股权项目挂牌列表(【审核中】【已审核】) 
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 上午10:34:02
     */
    public long getCrowdfundingTransferAuditCount(CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.getCount("selectCrowdfundingTransferAuditCount", model);
    }

    /**
     * Description: 查询股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午1:28:29
     */
    public List<Map<String, Object>> getCrowdfundingTransferInfoList(
            CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.selectMapByFields("selectCrowdfundingTransferInfoList", model);
    }

    /**
     * Description: 统计股权项目挂牌列表(【挂牌中】【挂牌完成】) 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午1:28:29
     */
    public long getCrowdfundingTransferInfoCount(CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.getCount("selectCrowdfundingTransferInfoCount", model);
    }

    /**
     * Description: 查询首页挂牌列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午9:14:12
     */
    public List<Map<String, Object>> getCrowdfundTransferDetailList(
            CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.selectMapByFields("selectCrowdfundTransferDetailList", model);
    }
    /**
     * Description: 统计
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-11-12 下午9:14:45
     */
    public long getCrowdfundTransferDetailCount(CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.getCount("selectCrowdfundTransferDetailCount",model);
    }

    /**
     * 
     * Description:查询挂牌详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    public Map<String, Object> getCrowdfundTransferDetail(
            CrowdfundTransferModel model) {
        // TODO Auto-generated method stub
        return this.selectOneMapByField("selectCrowdfundTransferDetail", model);
    }

    /**
     * 查询可转让份数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     */
    public long getCanTransferParts(String orderNo) {
        // TODO Auto-generated method stub
        return this.getCount("selectCanTransferParts",orderNo);
    }

}
