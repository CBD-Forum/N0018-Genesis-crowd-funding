/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: MockUtils.java 
 *
 * Created: [2016-8-25 下午9:03:18] by haolingfeng
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class CopyOfMockUtils {
    
    /**
     * 开户
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午9:17:39
     */
    public static String createAccount(String url,Map<String,String> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String resultStr = "";
        try{
            String productCode = params.get("productCode");
            if("1000000001".equals(productCode)){
                resultMap.put("status", "ProductIsExist");
                resultMap.put("message", "产品不存在");
                return JsonHelper.getStrFromObject(resultMap);
            }
            String serviceID = params.get("serviceID");
            if("createAccount".equals(serviceID)){
                resultMap.put("status", "ServiceException");
                resultMap.put("message", "服务异常");
                return JsonHelper.getStrFromObject(resultMap);
            }
            resultMap.put("address", PKGenarator.getId());
            resultMap.put("key", PKGenarator.getId());
            resultMap.put("productCode","1000000001");
            resultMap.put("status", "TransactionSuccess");
            resultMap.put("message", "事务成功");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }catch(Exception e){
            resultMap.put("productCode","1000000001");
            resultMap.put("status", "ServiceException");
            resultMap.put("message", "服务异常");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }
        return resultStr;
    }
    
    /**
     * 激活用户
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午9:26:57
     */
    public static String activeAccount(String url,Map<String,String> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String resultStr = "";
        try{
            String productCode = params.get("productCode");
            if("1000000001".equals(productCode)){
                resultMap.put("status", "ProductIsExist");
                resultMap.put("message", "产品不存在");
                return JsonHelper.getStrFromObject(resultMap);
            }
            String serviceID = params.get("serviceID");
            if("activeAccount".equals(serviceID)){
                resultMap.put("status", "ServiceException");
                resultMap.put("message", "服务异常");
                return JsonHelper.getStrFromObject(resultMap);
            }
            resultMap.put("address", PKGenarator.getId());
            resultMap.put("productCode","1000000001");
            resultMap.put("status", "TransactionSuccess");
            resultMap.put("transactionID",PKGenarator.getId());
            resultMap.put("message", "事务成功");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }catch(Exception e){
            resultMap.put("productCode","1000000001");
            resultMap.put("status", "ServiceException");
            resultMap.put("message", "服务异常");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }
        return resultStr;
    }
    
    /**
     * 转账接口
     * Description: 
     *
     * @param 
     * @return String
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-8-25 下午9:28:14
     */
    public static String transfer(String url,Map<String,String> params){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        String resultStr = "";
        try{
            String productCode = params.get("productCode");
            if("1000000001".equals(productCode)){
                resultMap.put("status", "ProductIsExist");
                resultMap.put("message", "产品不存在");
                return JsonHelper.getStrFromObject(resultMap);
            }
            String serviceID = params.get("serviceID");
            if("transfer".equals(serviceID)){
                resultMap.put("status", "ServiceException");
                resultMap.put("message", "服务异常");
                return JsonHelper.getStrFromObject(resultMap);
            }
            if(params.get("amt")==null){
                resultMap.put("status", "ParameterError");
                resultMap.put("message", "参数错误[amt]");
                return JsonHelper.getStrFromObject(resultMap);
            }
            if(Double.parseDouble(params.get("amt").toString())<=0){
                resultMap.put("status", "ParameterError");
                resultMap.put("message", "参数错误[amt不能为0]");
                return JsonHelper.getStrFromObject(resultMap);
            }
            Random random = new Random();
            int index = random.nextInt(10);
            boolean flag = randomResult(index);
            if(flag){
                resultMap.put("targetAddress", PKGenarator.getId());
                resultMap.put("productCode","1000000001");
                resultMap.put("status", "TransactionSuccess");
                resultMap.put("transactionID",PKGenarator.getId());
                resultMap.put("message", "事务成功");
                resultStr = JsonHelper.getStrFromObject(resultMap);
            }else{
                resultMap.put("targetAddress", PKGenarator.getId());
                resultMap.put("productCode","1000000001");
                resultMap.put("status", "TransactionFail");
                resultMap.put("transactionID",PKGenarator.getId());
                resultMap.put("message", "事务失败");
                resultStr = JsonHelper.getStrFromObject(resultMap);
            }
        }catch(Exception e){
            resultMap.put("productCode","1000000001");
            resultMap.put("status", "ServiceException");
            resultMap.put("message", "服务异常");
            resultStr = JsonHelper.getStrFromObject(resultMap);
        }
        return resultStr;
    }
    
    
    private static boolean randomResult(int index){
        
        List<Integer> result = new ArrayList<Integer>();
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(1);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(0);
        result.add(1);
        result.add(0);
//        System.out.println("result:"+result);
        return result.get(index)==0?true:false;
    }
    
    
    
    
    public static void main(String[] args) {
        Map<String,String> params = new HashMap<String,String>();
        params.put("amt","10");
        String result = CopyOfMockUtils.transfer("",params);
        System.out.println(result);
        
    }

}
