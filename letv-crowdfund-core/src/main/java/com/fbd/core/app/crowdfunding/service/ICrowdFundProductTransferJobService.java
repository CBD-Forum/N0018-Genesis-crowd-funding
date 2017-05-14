package com.fbd.core.app.crowdfunding.service;

import java.util.List;
import java.util.Map;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹产品转让
 *
 * @author wuwenbin
 * @version 1.0
 *
 */
public interface ICrowdFundProductTransferJobService {

    
    
    
	void updateTransferEnd(String transferNo);
	
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
}
