package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundProductTransferModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundProductTransferDao extends BaseDao<CrowdfundProductTransferModel>{

    /**
     * 
     * Description: 根据支持编号查询转让数据
     *
     * @param 
     * @return CrowdfundProductTransferModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-12 下午5:11:17
     */
    CrowdfundProductTransferModel getBySupportNo(String supportNo);
    /**
     * 
     * Description: 根据购买支持编号查询转让数据
     *
     * @param 
     * @return CrowdfundProductTransferModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-12 下午5:11:17
     */
    CrowdfundProductTransferModel getByBuySupportNo(String buySupportNo);
    /**
     * Description: 通过转让编号查询
     *
     * @param 
     * @return CrowdfundProductTransferModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 上午10:09:36
     */
    
    CrowdfundProductTransferModel getByTransferNo(String transferNo);

    /**
     * Description: 查询市场转让列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 上午11:06:45
     */
    
    List<Map<String, Object>> getCrowdfundTransferDetailList(
            CrowdfundProductTransferModel model);

    /**
     * Description: 统计市场转让列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 上午11:07:16
     */
    
    long getCrowdfundTransferDetailCount(CrowdfundProductTransferModel model);

    /**
     * Description: 查询转让详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 下午1:25:29
     */
    
    Map<String, Object> getCrowdfundTransferDetail(
            CrowdfundProductTransferModel model);

    /**
     * Description: 查询用户购买转让记录表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-29 上午10:53:35
     */
    
    List<Map<String, Object>> getBuyTransferListPage(
            CrowdfundProductTransferModel model);

    /**
     * Description: 统计 查询用户购买转让记录表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-29 上午10:53:44
     */
    
    long getBuyTransferCountPage(CrowdfundProductTransferModel model);
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

     public List<Map<String,Object>>transferRecive(Map<String,Object>param);
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
     public List<Map<String,Object>>transferPay(Map<String,Object>param);
     /**
      * 
      * Description:获取转让系统配置参数 
      *
      * @param 
      * @return Map<String,Double>
      * @throws 
      * @Author haolingfeng
      * Create Date: 2016-10-14 下午3:19:26
      */
     public Map<String,Double> getTransferConfig();
     /**
      * 
      * Description: 验证转让金额，并返回值(手续费之类的)
      *
      * @param 
      * @return Map<String,Object>
      * @throws 
      * @Author haolingfeng
      * Create Date: 2016-10-14 上午10:13:54
      */
     public Map<String,Object> checkTransferAmt(double investAmt,double transferAmt);

}