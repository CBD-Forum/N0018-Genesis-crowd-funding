/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundUserPrizeDaoImpl.java 
 *
 * Created: [2016-8-10 下午4:40:16] by haolingfeng
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

package com.fbd.core.app.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao;
import com.fbd.core.app.user.model.CrowdfundUserPrizeModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 中奖纪录
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
@Repository("crowdfundUserPrizeDao")
public class CrowdfundUserPrizeDaoImpl extends BaseDaoImpl<CrowdfundUserPrizeModel>
    implements ICrowdfundUserPrizeDao {

    /**
     * Description: 统计一共参与分红的人数
     *
     * @param 
     * @return long
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午6:45:41
     */
    public long getCountPrize(String loanNo) {
        return this.getCount("selectCountPrize", loanNo);
    }

    /**
     * Description: 修改 根据项目编号和抽奖单号
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-11 上午10:13:09
     */
    public void updatePrize(int firstNum, String loanNo) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("prizeNo", firstNum);
        map.put("loanNo", loanNo);
        this.update("updatePrize", map);
    }

    public List<Map<String, Object>> getUserPrizeList(
            CrowdfundUserPrizeModel model) {
        return this.selectMapByFields("selectUserPrizeList", model);
    }
    public long getUserPrizeCount(CrowdfundUserPrizeModel model) {
        return this.getCount("selectUserPrizeCount",model);
    }

    /**
     * Description: 查询抽奖编号
     *
     * @param 
     * @return wuwenbin
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午8:22:50
     */
    public CrowdfundUserPrizeModel getUserPrize(String loanNo,
            String prizeUser) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loanNo",loanNo);
        map.put("prizeUser",prizeUser);
        return this.selectOneByField("selectUserPrize", map);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao#updateNoPrizeByLoanNo(java.lang.String)
     */
    @Override
    public void updateNoPrizeByLoanNo(String loanNo) {
        // TODO Auto-generated method stub
        this.update("updateNoPrizeByLoanNo", loanNo);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.user.dao.ICrowdfundUserPrizeDao#deleteByLoanNo(java.lang.String)
     */
    @Override
    public int deleteByLoanNo(String loanNo) {
        // TODO Auto-generated method stub
        return this.deleteByField("deleteByLoanNo", loanNo);
    }

}
