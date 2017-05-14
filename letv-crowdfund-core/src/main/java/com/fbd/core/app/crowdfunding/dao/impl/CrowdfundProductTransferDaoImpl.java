/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundProductTransferDaoImpl.java 
 *
 * Created: [2016-8-11 下午5:34:21] by haolingfeng
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.config.model.BusinessConfigModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.TransferCore;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.util.Arith;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 产品转让
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundProductTransferDao")
public class CrowdfundProductTransferDaoImpl extends BaseDaoImpl<CrowdfundProductTransferModel> implements
        ICrowdfundProductTransferDao {
    
    @Resource
    private IBusinessConfigDao businessConfigDao;

    /**
     * Description: 通过转让编号查询
     *
     * @param 
     * @return CrowdfundProductTransferModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 上午10:09:36
     */
    public CrowdfundProductTransferModel getByTransferNo(String transferNo) {
        
        return this.selectOneByField("selectByTransferNo", transferNo);
    }

    /**
     * Description: 查询市场转让列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 上午11:06:45
     */
    
    public List<Map<String, Object>> getCrowdfundTransferDetailList(
            CrowdfundProductTransferModel model) {
        return this.selectMapByFields("selectCrowdfundTransferDetailList",model);
    }

    /**
     * Description: 统计市场转让列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 上午11:07:16
     */
    public long getCrowdfundTransferDetailCount(
            CrowdfundProductTransferModel model) {
        return this.getCount("selectCrowdfundTransferDetailCount",model);
    }

    /**
     * Description: 查询转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 下午1:25:29
     */
    public Map<String, Object> getCrowdfundTransferDetail(
            CrowdfundProductTransferModel model) {
        return this.selectOneMapByField("getCrowdfundTransferDetail",model);
    }

    /**
     * Description: 查询用户购买转让记录表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-29 上午10:53:35
     */
    public List<Map<String, Object>> getBuyTransferListPage(
            CrowdfundProductTransferModel model) {
        
        return this.selectMapByFields("selectBuyTransferListPage", model);
    }

    /**
     * Description: 统计 查询用户购买转让记录表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-29 上午10:53:44
     */
    public long getBuyTransferCountPage(CrowdfundProductTransferModel model) {
        return this.getCount("selectBuyTransferCountPage",model);
    }
   /**
    * 转让收款
    * Description: 
    *
    * @param 
    * @return List<Map<String,Object>>
    * @throws 
    * @Author haolingfeng
    * Create Date: 2016-8-31 下午12:59:48
    */

    public List<Map<String,Object>>transferRecive(Map<String,Object>param){
        return this.selectMapByFields("transferRecive", param);
    }
    /**
     * 转让付款
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-31 下午1:00:08
     */
    public List<Map<String,Object>>transferPay(Map<String,Object>param){
        return this.selectMapByFields("transferPay", param);
    }
    @Override
    public CrowdfundProductTransferModel getBySupportNo(String supportNo) {
        // TODO Auto-generated method stub
        return this.selectOneByField("getBySupportNo", supportNo);
    }
    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao#getByBuySupportNo(java.lang.String)
     */
    @Override
    public CrowdfundProductTransferModel getByBuySupportNo(String buySupportNo) {
        // TODO Auto-generated method stub
        return this.selectOneByField("getByBuySupportNo", buySupportNo);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao#checkTransferAmt(boolean)
     */
    @Override
    public Map<String, Object> checkTransferAmt(double investAmt,double transferAmt) {
        // TODO Auto-generated method stub
        Map<String, Double> configData=getTransferConfig();
        double minAmt=Arith.mul(investAmt, configData.get(TransferCore.TRANSFER_MIN_AMT));
        double maxAmt=Arith.mul(investAmt, configData.get(TransferCore.TRANSFER_MAX_AMT));
        if(minAmt<=transferAmt&&transferAmt<=maxAmt){
            Map<String, Object> result=new HashMap<String, Object>();
            result.put("success", true);
            result.put("minAmt",minAmt);
            result.put("maxAmt",maxAmt);
            result.put("fee", Arith.mul(transferAmt, configData.get(TransferCore.TRANSFER_FEE)));
            result.put("investAmt", investAmt);
            result.put("transferAmt", transferAmt);
            return result;
        }else{
            StringBuffer sb=new StringBuffer();
            sb.append("该转让金额必须大于等于").append(minAmt).append("小于等于").append(maxAmt);
            throw new ApplicationException(sb.toString());
        }
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundProductTransferDao#getTransferConfig()
     */
    @Override
    public Map<String, Double> getTransferConfig() {
        // TODO Auto-generated method stub
        BusinessConfigModel b1=businessConfigDao.getBusConfig(TransferCore.TRANSFER_MIN_AMT);
        if(b1==null){
            throw new ApplicationException("后台业务参数:转让金额最低下限("+TransferCore.TRANSFER_MIN_AMT+")为空");
        }
        BusinessConfigModel b2=businessConfigDao.getBusConfig(TransferCore.TRANSFER_MAX_AMT);
        if(b2==null){
            throw new ApplicationException("后台业务参数:转让金额最高下限("+TransferCore.TRANSFER_MAX_AMT+")为空");
        }
        BusinessConfigModel b3=businessConfigDao.getBusConfig(TransferCore.TRANSFER_FEE);
        if(b3==null){
            throw new ApplicationException("后台业务参数:转让手续费("+TransferCore.TRANSFER_FEE+")为空");
        }
        Map<String, Double> value=new HashMap<String, Double>();
        value.put(TransferCore.TRANSFER_MIN_AMT, Double.parseDouble(b1.getCode()));
        value.put(TransferCore.TRANSFER_MAX_AMT, Double.parseDouble(b2.getCode()));
        value.put(TransferCore.TRANSFER_FEE, Double.parseDouble(b3.getCode()));
        return value;
    }
}
