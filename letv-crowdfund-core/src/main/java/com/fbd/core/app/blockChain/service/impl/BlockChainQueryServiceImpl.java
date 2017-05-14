/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainQueryServiceImpl.java 
 *
 * Created: [2016-9-19 下午8:00:43] by haolingfeng
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

package com.fbd.core.app.blockChain.service.impl;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.exception.ApplicationException;
/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="blockChainQueryService")
public class BlockChainQueryServiceImpl implements IBlockChainQueryService {
    
    private static final Logger logger = LoggerFactory.getLogger(BlockChainQueryServiceImpl.class);
    @Resource
    private IBlockAsynTranDao blockAsynTranDao;
    
    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQuery(String transactionID){
        String resultStr = "";
        try{
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "transactionQuery");
            params.put("transactionID",transactionID);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainQuery/transactionQueryNotify.html");
            logger.info("区块链-事务查询请求参数："+params);
            String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
            resultStr = HttpRequestUtils.callHttpPOST(requestUrl, params);
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("事务查询失败！");
        }
        return resultStr;
    }
    
    
    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQuery(String transactionID,String requestID){
        String resultStr = "";
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        try{
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "transactionQuery");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("transactionID",transactionID);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainQuery/transactionQueryNotify.html");
            params.put("requestID",requestID);
            logger.info("区块链-事务查询请求参数："+params);
            resultStr =  HttpRequestUtils.callHttpPOST(requestUrl, params);
            logger.info("区块链-事务查询响应结果："+resultStr);
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("事务查询失败！");
        }
        return resultStr;
    }

    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQueryCommon(String transactionID,String returnUrl){
        String resultStr = "";
        String requestUrl = FbdCoreConstants.BLOCK_CHAIN_URL;
        try{
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "transactionQuery");
            params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
            params.put("transactionID",transactionID);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainQuery/transactionQueryNotify.html");
            params.put("requestID","2016100847353715");
            logger.info("区块链-事务查询请求参数："+params);
            resultStr =  HttpRequestUtils.callHttpPOST(requestUrl, params);
            logger.info("区块链-事务查询响应结果："+resultStr);
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("事务查询失败！");
        }
        return resultStr;
    }
    
    /**
     * 区块链事务查询
     * Description: 
     * @param 
     * @return String
     * @throws 
     * Create Date: 2016-9-19 下午8:01:33
     */
    public String transactionQueryCommon(String transactionID,String returnUrl,String type){
        String resultStr = "";
        try{
            Map<String,String> params = new HashMap<String,String>();
            params.put("serviceID", "transactionQuery");
            params.put("transactionID",transactionID);
            params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+"blockChainQuery/transactionQueryNotify.html");
            params.put("requestID", type);
            logger.info("区块链-事务查询请求参数："+params);
            resultStr =  HttpRequestUtils.callHttpPOST("http://10.75.166.165/letv-web/blockChainQuery/transactionQueryNotify.html", params);
            logger.info("区块链-事务查询结果："+resultStr);
        }catch(ApplicationException e1){
            throw new ApplicationException(e1.getMessage());
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("事务查询失败！");
        }
        return resultStr;
    }
    @Override
    public void modifyTranscationQueryAsyn(Map<String, String> result) {
        // TODO Auto-generated method stub
        String tranId=result.get(BlockChainCore.TRANSACTION_ID);
        String status=result.get(BlockChainCore.STATUS);
        String message=result.get(BlockChainCore.MESSAGE);
        
        //查询事务异步通知信息
        BlockAsynTranModel queryModel = new BlockAsynTranModel();
        queryModel.setTranId(tranId);
        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.selectByModel(queryModel);
        if(blockAsynTran!=null){
            blockAsynTran.setQueryStatus(status);
            blockAsynTran.setQueryMessage(message);
            this.blockAsynTranDao.updateBySelective(blockAsynTran);
        }
    }
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
    public String searchIsSucess(String requestId){
        BlockAsynTranModel model = blockAsynTranDao.findByRequestId(requestId);
        return model.getQueryStatus();
    }
}
