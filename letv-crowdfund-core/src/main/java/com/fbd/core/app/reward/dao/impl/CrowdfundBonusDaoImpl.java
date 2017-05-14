/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundBonusDaoImpl.java 
 *
 * Created: [2016-8-23 下午5:30:53] by haolingfeng
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

package com.fbd.core.app.reward.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.reward.dao.ICrowdfundBonusDao;
import com.fbd.core.app.reward.model.CrowdfundBonusModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundBonusDao")
public class CrowdfundBonusDaoImpl extends BaseDaoImpl<CrowdfundBonusModel> implements ICrowdfundBonusDao {

    /**
     * Description: 查询分红项目待审核列表
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-23 下午5:49:58
     */
    public List<Map<String, Object>> getLoanBonusList(CrowdfundBonusModel model) {
        return this.selectMapByFields("selectLoanBonusList", model);
    }

    /**
     * Description: 统计分红项目待审核列表
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-23 下午5:50:07
     */
    public long getLoanBonusCount(CrowdfundBonusModel model) {
        return this.getCount("selectLoanBonusCount",model);
    }

    /**
     * Description: 删除分红项目   和分红项目明细
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-26 下午4:01:21
     */
    public void deleteByLoanBonusId(String loanBonusId) {
        this.deleteByField("deleteByLoanBonusId", loanBonusId);
    }
    /**
     * 收到投资分红、兑付款项
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-30 下午4:44:05
     */
    public List<Map<String,Object>>pushReciveBonus(Map<String,Object>params){
      return  this.selectMapByFields("selectReciveBonus", params);
    }
    
    /**
     * 查询分红次数
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-11-8 下午2:16:01
     */
    public long selectLastNum(String loanNo){
        return this.getCount("selectLastNum", loanNo);
    }
}
