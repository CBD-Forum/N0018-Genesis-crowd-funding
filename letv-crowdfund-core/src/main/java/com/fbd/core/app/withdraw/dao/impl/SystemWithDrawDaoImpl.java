/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: SystemWithDrawDao.java 
 *
 * Created: [2016年10月21日 上午11:08:42] by haolingfeng
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

package com.fbd.core.app.withdraw.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.contract.model.ContractTemplateModel;
import com.fbd.core.app.withdraw.dao.ISystemWithDrawDao;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.app.withdraw.model.WithDrawModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("systemWithDrawDao")
public class SystemWithDrawDaoImpl extends BaseDaoImpl<SystemWithdrawModel> implements ISystemWithDrawDao {
   public int saveSystemWithDraw(SystemWithdrawModel model){
      return this.save(model);
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
   public List<Map<String,Object>> getList(SystemWithdrawModel model){
       return this.selectMapByFields("selectList", model);
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
   public Long getListCounts(SystemWithdrawModel model){
       return this.getCount("selectListCount", model);
   }
   public SearchResult<Map<String, Object>> getSystemWithDrawPage(SystemWithdrawModel systemWithdrawModel) {
       SearchResult<Map<String, Object>> searchResult = new SearchResult<Map<String, Object>>();
       searchResult.setTotal(getListCounts(systemWithdrawModel));
       searchResult.setRows(getList(systemWithdrawModel));
       return searchResult;
   }
   public long updateById(SystemWithdrawModel model){
       return this.updateBySelective(model);
   }
   public SystemWithdrawModel selectByOrderId(String orderId){
       return this.selectOneByField("selectByOrderId", orderId);
   }
   
}
