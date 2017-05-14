/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BlockChainUtil.java 
 *
 * Created: [2016-10-14 下午2:58:33] by haolingfeng
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

package com.fbd.core.app.blockChain.util;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.util.Arith;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class BlockChainUtil {
    /**
     * 
     * Description: 区块链转让封装
     *
     * @param transferNo:转账订单号;sourceAddress:转出账号地址;sourceKey:转出账号秘钥;targetAddress:转入账号地址;amt:转账金额;notifyUrl:异步回调地址;requestId:请求码;
     * @return Map<String,String>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-14 下午3:08:01
     */
    public static Map<String,String> transfer(String transferNo,String sourceAddress,String sourceKey,String targetAddress,Double amt,String notifyUrl,String requestId){
        Map<String,String> params = new HashMap<String,String>();
        //接口标识
        params.put("serviceID", "transfer");
        //转账订单号
        params.put("transferNO",BlockChainStringUtil.getUniqueTransferNoEnc(transferNo));
        //产品码
        params.put("productCode", FbdCoreConstants.BLOCK_CHAIN_PRODUCTCODE);
        //转出账号地址
        params.put("sourceAddress",sourceAddress); //转出账户地址为平台出账户
        //转出账号秘钥
        params.put("sourceKey",sourceKey);
        //转入账号地址
        params.put("targetAddress",targetAddress);  //转入账户为用户账号(测试使用用户)
        DecimalFormat dFormat=new DecimalFormat("#.00");
        String amount = dFormat.format(amt);
        //转账金额
        params.put("amount",amount);
        //异步回调地址
        params.put("notifyURL",FbdCoreConstants.BLOCK_CHAIN_NOTIFY_URL+notifyUrl);
        params.put(BlockChainCore.REQUEST_ID, requestId);
        if(Boolean.parseBoolean(FbdCoreConstants.BLOCK_CHAIN_IS_TEST)){
            params.put("amount","0.01");
        }
        return params;
    }
}
