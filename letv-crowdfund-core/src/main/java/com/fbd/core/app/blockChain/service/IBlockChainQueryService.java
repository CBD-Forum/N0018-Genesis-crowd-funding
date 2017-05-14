/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBlockChainQueryService.java 
 *
 * Created: [2016-9-19 下午8:00:26] by haolingfeng
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

package com.fbd.core.app.blockChain.service;

import java.util.Map;


/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IBlockChainQueryService {
    
    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQuery(String transactionID);
    
    
    
    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQuery(String transactionID,String requestID);
    
    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQueryCommon(String transactionID,String returnUrl,String type);

    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQueryCommon(String transactionID,String returnUrl);
    
    
    /**
     * 
     * Description: 区块链事物查询异步处理数据
     *
     * @param 
     * @return String
     * @throws 
     * @Author hanchenghe
     * Create Date: 2016-9-23 上午9:55:01
     */
    public void modifyTranscationQueryAsyn(Map<String,String> result);
    
    /**
     * 调取区块链是否成功
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月11日 上午10:15:38
     */
    public String searchIsSucess(String requestId);
}
