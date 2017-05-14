package com.fbd.web.app.blockChain.action;

import java.util.HashMap;
import java.util.Map;

import com.fbd.core.common.utils.HttpRequestUtils;
import com.fbd.core.helper.PKGenarator;

public class BlockChainTest {
	
	
    public static void main(String[] args) throws Exception {
     
        
    	testTransfer();
    	
//    	testTransactionQuery();
 
    }
    
    
    public static void testTransfer(){
    	   
        Map<String,String> params = new HashMap<String,String>();
        
        params.put("productCode","1000000002");
        params.put("serviceID","transfer");
        params.put("transferNO",PKGenarator.getOrderId());
        String requestID = PKGenarator.getOrderId();
        System.out.println("requestID:"+requestID);
        params.put("requestID",requestID);
        params.put("amount","0.01");
        params.put("notifyURL","http://10.75.164.118:8080/letv-web/blockChainTest/transferNotify.html");
        params.put("sourceKey","shL19fFkDBeTSNa4ccygigrWM1L9z");
        params.put("sourceAddress","rnwDpX4WBWnhYztJDu2599GqAxPpg5htjV");
        params.put("targetAddress","rfwEyHuvwi3bw2vDnUZQcQQUcGci58R9TD");
        
        String request = HttpRequestUtils.callHttpPOST("http://10.110.126.159:8088/gateway/V1", params);
        System.out.println(request); 
    }
    
    
    public static void testTransactionQuery(){
 	   
        Map<String,String> params = new HashMap<String,String>();
        
        params.put("serviceID","transactionQuery");
        params.put("notifyURL","http://10.75.166.94:8080/letv-web/blockChainTest/transactionQueryNotify.html");
        params.put("productCode","1000000002");
        params.put("requestID",PKGenarator.getOrderId());
        params.put("transactionID","22DFC663DF0DA403C0E9E302B159C76EEA3ACC926ABF656CF0CBE0585B86E773");
        String request = HttpRequestUtils.callHttpPOST("http://10.110.126.159:8088/service-gateway/transactionQuery", params);
        System.out.println(request); 
    }

}
