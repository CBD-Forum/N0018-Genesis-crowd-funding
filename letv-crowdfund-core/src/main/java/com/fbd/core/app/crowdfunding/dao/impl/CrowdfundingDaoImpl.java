/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingDaoImpl.java 
 *
 * Created: [2015-3-27 下午4:23:09] by haolingfeng
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
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundingDao")
public class CrowdfundingDaoImpl extends BaseDaoImpl<CrowdfundingModel> 
implements ICrowdfundingDao{

    public List<Map<String,Object>> getList(CrowdfundingModel model){
        return this.selectMapByFields("selectList", model);
    }
  
    
    public long getCount(CrowdfundingModel model){
        return this.getCount("getCount", model);
    }
    
    public CrowdfundingModel getByloanNo(String loanNo){
//        return this.selectByField("selectByLoanNo", loanNo).get(0);
        return this.selectOneByField("selectByLoanNo", loanNo);
    }
    
    public Map<String,Object> getLoanDetail(String loanNo){
        return this.selectOneMapByField("selectDetail", loanNo);
    }
    
    
    public List<Map<String,Object>> getUserCapitalList(CrowdfundingModel model){
      /*  Map<String,Object> amap = new HashMap<String,Object>();
        amap.put("userId",userId);*/
        return this.selectMapByFields("selectUserCapitalList", model);
    }
    
    public long getUserCapitalListCount(CrowdfundingModel model){
       /* Map<String,Object> amap = new HashMap<String,Object>();
        amap.put("userId",userId);*/
        return this.getCount("getUserCapitalCount", model);
    }
    
    /**
     * 
     * Description:根据项目编号删除项目 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-4-17 下午5:58:20
     */
    public void deleteByloanNo(String loanNo){
        this.deleteByField("deleteByLoanNo", loanNo);
    }
    
    
    /**
     * 
     * Description: 平台统计
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-12-11 下午12:24:40
     */
    public Map<String,Object> getStatsData(){
        return this.selectOneMapByField("selectStatsData", null);
    }
    /**
     * Description: 查询已投资总额
     *
     * @param 
     * @return double
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-16 上午10:13:31
     */
    public double getSumSupportAmt() {
        Map<String,Object> map = new HashMap<String, Object>();
        return this.getsumAmt("selectSumSupportAmt", map);
    }

    /**
     * Description: 统计项目总数
     *
     * @param 
     * @return int
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-2-16 上午10:26:24
     */
    public long getCountLoan() {
        return this.getCount("selectCountLoan","");
    }

    /**
     * Description: 查询可抽奖的产品项目
     *
     * @param 
     * @return CrowdfundingModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-10 下午6:14:12
     */
    public CrowdfundingModel getPrizeLoan(String loanNo) {
        return this.selectOneByField("selectPrizeLoan", loanNo);
    }
    /**
     * 募集失败数据推送
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 下午1:46:29
     */
    public List<Map<String, Object>> pushLoanFail(Map<String, Object> param) {
        return this.selectMapByFields("selectLoanFail", param);
    }
    /**
     * 投资划款
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-26 下午5:57:00
     */
    public List<Map<String, Object>> selectIsnvestmentFunds(Map<String, Object> param) {
        return this.selectMapByFields("selectIsnvestmentFunds", param);
    }
    /**
     * 
     * Description: 募集转账
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 上午11:26:34
     */
    public List<Map<String,Object>>raiseTransfer(Map<String,Object>param){
        return this.selectMapByFields("raiseTransfer", param);
    }
    /**
     * 项目结算
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月17日 下午4:06:55
     */
    public List<Map<String,Object>>selectJsData(Map<String,Object>param){
        return this.selectMapByFields("selectJsData", param);
    }
    
    /**
     * 查询用户项目统计
     * Description: 
     *
     * @param 
     * @return List<Map<String,Object>>
     * Create Date: 2016-8-31 下午7:33:49
     */
    public  Map<String, Object>  selectUserProjectInfo(String userId){
        return this.selectOneMapByField("selectUserProjectInfo", userId);
    }
    public long updateByLoanNo(CrowdfundingModel model){
        return this.update("updateByLoanNo", model);
    }


    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao#getByBlockChainAddress(java.lang.String)
     */
    @Override
    public CrowdfundingModel getByBlockChainAddress(String blockChainAddress) {
        // TODO Auto-generated method stub
        return this.selectOneByField("getByBlockChainAddress", blockChainAddress);
    }
    /**
     * 查询所有项目
     * Description: 
     *
     * @param 
     * @return List<CrowdfundingModel>
     * @throws 
     * Create Date: 2016-11-3 上午10:32:37
     */
    public List<CrowdfundingModel> selectByAll(CrowdfundingModel model){
        return this.selectByField("selectByAll", model);
    }
    
    /**
     * 查询进行中的项目数量(预热和筹款中)
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-12-14 下午5:20:48
     */
    public long getFundingLoanCount(){
        CrowdfundingModel model = new CrowdfundingModel();
        return this.getCount("getFundingLoanCount", model);
    }

    /**
     * 查询成功的项目数量(已放款和已结束)
     * Description: 
     * @param 
     * @return long
     * @throws 
     * Create Date: 2016-12-14 下午5:20:48
     */
    public long getSuccessLoanCount(){
        CrowdfundingModel model = new CrowdfundingModel();
        return this.getCount("getSuccessLoanCount", model);
    }
    /**
     * 查询成功筹款的金额
     * Description: 
     *
     * @param 
     * @return Double
     * @throws 
     * Create Date: 2016-12-14 下午5:22:34
     */
    public Double getSuccessLoanAmt(){
        CrowdfundingModel model = new CrowdfundingModel();
        return this.getsumAmt("getSuccessLoanAmt",model);
    }
 
}
