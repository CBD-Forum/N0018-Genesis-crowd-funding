/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingBackSetDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:26:43] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingBackSetModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹回报设置
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingBackSetDao")
public class CrowdfundingBackSetDaoImpl extends BaseDaoImpl<CrowdfundingBackSetModel> 
implements ICrowdfundingBackSetDao{

    
    /**
     * 
     * Description: 查询回报设置
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-28 上午11:02:03
     */
    public List<Map<String,Object>> getList(CrowdfundingBackSetModel model){
        return this.selectMapByFields("selectList", model);
    }
    
    public CrowdfundingBackSetModel getByLoanNoAndSetNo(String loanNo,String backNo){
        CrowdfundingBackSetModel model  = new CrowdfundingBackSetModel();
        model.setLoanNo(loanNo);
        model.setBackNo(backNo);
        return this.selectOneByField("selectByLoanNoAndSetNo", model);
    }
    public CrowdfundingBackSetModel getByLoanNo(String loanNo){
        List<CrowdfundingBackSetModel> backSet = this.selectByField("selectByLoanNo", loanNo);
        if(backSet.size()>0){
            return backSet.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * 
     * Description: 删除回报设置
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-17 下午6:01:04
     */
    public void deleteByloanNo(String loanNo){
        this.deleteByField("deleteByloanNo", loanNo);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundingBackSetDao#queryIsPrizeDrawFlag(java.lang.String)
     */
    @Override
    public CrowdfundingBackSetModel queryIsPrizeDrawFlag(String loanNo) {
        // TODO Auto-generated method stub
        try{
            return this.selectOneByField("queryIsPrizeDrawFlag", loanNo);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("根据项目编号查询出有奖的回报异常:"+e.getMessage());
        }
    }
}
