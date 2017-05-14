/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: WithDrawDaoImpl.java 
 *
 * Created: [2015-1-15 下午12:28:04] by haolingfeng
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

package com.fbd.core.app.withdraw.dao.impl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.withdraw.dao.IWithDrawDao;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("withDrawDao")
public class WithDrawDaoImpl extends BaseDaoImpl<WithDrawModel> 
    implements IWithDrawDao{

    /**
     * 
     * Description:根据取现订单号查询提现记录 
     *
     * @param 
     * @return WithDrawModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-15 下午2:46:24
     */
    public WithDrawModel getByOrderId(String orderId){
        return this.selectOneByField("selectByOrderId", orderId);
    }
    
    /**
     * 
     * Description: 查询提现列表
     *
     * @param 
     * @return List<WithDrawModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-27 上午10:25:20
     */
    public List<WithDrawModel> getList(WithDrawModel model){
        return this.selectByModel("selectList", model);
    }
    
    /**
     * 
     * Description: 取现列表条数
     *
     * @param 
     * @return Long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-27 上午10:26:28
     */
    public Long getListCounts(WithDrawModel model){
        return this.getCount("selectListCounts", model);
    }

    /**
     * Description: 查询用户提现列表
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-11 下午3:22:45
     */
    public SearchResult<Map<String, Object>> getWithDrawPage(Map<String, Object> param) {
       return this.getPage("getWidthdrawCount4Admin", "getWidthdraws4Admin", param);
    }

    /**
     * Description: 查询发送成功的提现记录 
     *
     * @param 
     * @return List<WithDrawModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-30 上午11:07:11
     */
    public List<WithDrawModel> getWithDrawList() {
        return this.selectByField("selectWithDrawList", "");
    }
    /**
     * 
     * Description: 支付兑付款项
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-31 上午9:45:04
     */
    public List<Map<String,Object>>selectWithdraw(WithDrawModel model){
        return this.selectMapByFields("selectWithdraw", model);
    }

    /**
     * Description: 查询交易成功或交易失败的提现记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 下午6:17:40
     */
    public List<Map<String, Object>> getUserWithDrawList(WithDrawModel model) {
        return this.selectMapByFields("selectUserWithDrawList", model);
    }

    /**
     * Description: 统计交易成功或交易失败的提现记录
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-1 下午6:17:40
     */
    public long getUserWithDrawCount(WithDrawModel model) {
        return this.getCount("selectUserWithDrawCount",model);
    }

    /**
     * 
     * Description:查询交易中和交易失败的提现记录详情
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin<br/>
     */
    public Map<String, Object> getUserWithDrawDetail(WithDrawModel model) {
        return this.selectOneMapByField("selectUserWithDrawDetail",model);
    }
    /**
     * 交易记录查询
     * Description: 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月14日 下午3:47:47
     */
    public List<Map<String, Object>> selectTranactionList(WithDrawModel model) {
        return this.selectMapByFields("selectTranactionList", model);
    }
    public long selectTranactionListCount(WithDrawModel model){
        return this.getCount("selectTranactionListCount",model);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.withdraw.dao.IWithDrawDao#selectWithdraw(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> selectWithdraw(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return null;
    }

   public Map<String,Object>selectTranactionById(WithDrawModel model){
       return this.selectOneMapByField("selectTranactionById", model);
   }
   /**
    * 众筹提现
    * Description: 
    *
    * @param 
    * @return List<Map<String,Object>>
    * @throws 
    * @Author haolingfeng
    * Create Date: 2016年10月17日 下午5:00:56
    */
   public List<Map<String,Object>>selectWithDrawData(Map<String,Object>param){
       return this.selectMapByFields("selectWithDrawData", param);
   }
   /**
    * 提现成功
    * Description: 
    *
    * @param 
    * @return List<Map<String,Object>>
    * @throws 
    * @Author haolingfeng
    * Create Date: 2016年10月24日 下午6:12:08
    */
   public List<Map<String,Object>>selectWithDrawDataSuccess(Map<String,Object>param){
       return this.selectMapByFields("selectWithDrawDataSuccess", param);
   }
   /**
    * 提现区块链审核成功
    * Description: 
    *
    * @param 
    * @return List<Map<String,Object>>
    * @throws 
    * @Author haolingfeng
    * Create Date: 2016年10月24日 下午6:12:08
    */
   public List<Map<String,Object>>selectWithDrawBlockData(Map<String,Object>param){
       return this.selectMapByFields("selectWithDrawBlockData", param);
   }
   public long selectSuccessCount(String orderId){
       return this.getCount("selectSuccessCount", orderId);
   }
   
}
