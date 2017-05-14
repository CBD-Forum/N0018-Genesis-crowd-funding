/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: FinancePushDataJob.java 
 *
 * Created: [2016-8-29 下午3:02:05] by haolingfeng
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

package com.fbd.core.common.quartz.job;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.crowdfunding.service.ICrowdFundProductTransferJobService;
import com.fbd.core.app.crowdfunding.service.ICrowdfundJobService;
import com.fbd.core.app.recharge.service.IRechargeServiceJob;
import com.fbd.core.app.reward.service.ICrowdfundBonusJobService;
import com.fbd.core.app.withdraw.service.IWithDrawQueryService;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.spring.SpringUtil;
import com.ls.DataAPI;

/**
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class FinancePushDataJob implements Job {
    private static final Logger logger = LoggerFactory
            .getLogger(FinancePushDataJob.class);
    private static IRechargeServiceJob rechargeServiceJob;
    private static ICrowdfundJobService crowdfundJobService;
    private static ICrowdfundBonusJobService crowdfundBonusJobService;
    private static IWithDrawQueryService withDrawQueryService;
    private static ICrowdFundProductTransferJobService crowdFundProductTransferJobService;

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        // TODO Auto-generated method stub
        ExecutorService executorService = Executors.newCachedThreadPool();   
        //众筹充值
        try {
            logger.info("======投资方充值开始=======");
            Map<String, Object> param = new HashMap<String, Object>();
            List<Map<String, Object>> list1 = getRechargeService()
                    .pushRechargeData(param);
                if(list1!=null && list1.size()>0)    
                    executorService.execute(new dealDataRunnable(list1));   
                closeDate("BC", list1.size(), list1);

            logger.info("======投资方充值结束=======");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     
      try {
          
          logger.info("======众筹提现申请开始=======");
          Map<String, Object> param = new HashMap<String, Object>();
          List<Map<String, Object>> list1 = getWithDrawQueryService().pushWithDrawData(param);
          if(list1!=null && list1.size()>0)        
          //    dealRechargeData("BC", list1.size(), list1);
          executorService.execute(new dealDataRunnable(list1));   

          
          closeDate("BC", list1.size(), list1);
          logger.info("======众筹提现结束=======");

      } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
      
try {
          
          logger.info("======众筹提现成功开始=======");
          Map<String, Object> param = new HashMap<String, Object>();
          List<Map<String, Object>> list1 = getWithDrawQueryService().pushWithDrawSuccessData(param);
          if(list1!=null && list1.size()>0)        
          //    dealRechargeData("BC", list1.size(), list1);
          executorService.execute(new dealDataRunnable(list1));   

          
          closeDate("BC", list1.size(), list1);
          logger.info("======众筹提现结束=======");

      } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }

try {
    
    logger.info("======众筹提现区块链审核成功开始=======");
    Map<String, Object> param = new HashMap<String, Object>();
    List<Map<String, Object>> list1 = getWithDrawQueryService().pushWithDrawAuditingSuccessData(param);
    if(list1!=null && list1.size()>0)        
    //    dealRechargeData("BC", list1.size(), list1);
    executorService.execute(new dealDataRunnable(list1));   

    
    closeDate("BC", list1.size(), list1);
    logger.info("======众筹提现区块链审核结束=======");

} catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
}
    }
    
    //防止数据并发
    class dealDataRunnable implements Runnable{  
        private byte[] lock = new byte[0]; // 特殊的instance变量
        private   List<Map<String, Object>> list1 ;
        public dealDataRunnable(List<Map<String, Object>> list){
            this.list1 =list;
        }
        public void run(){   
            synchronized(lock){
                try {
                    dealRechargeData("BC", list1.size(), list1);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
  
        }   
    }  
  //  List<Map<String, Object>> list1 = getWithDrawQueryService()
    public static IRechargeServiceJob getRechargeService() {
        if (rechargeServiceJob == null) {
            rechargeServiceJob = (IRechargeServiceJob) SpringUtil
                    .getBean("rechargeServiceJob");
        }
        return rechargeServiceJob;
    }
   public static ICrowdfundJobService getCrowdfundingService(){
       if (crowdfundJobService == null) {
           crowdfundJobService = (ICrowdfundJobService) SpringUtil
                   .getBean("crowdfundJobService");
       }
       return crowdfundJobService;
    }
   public static ICrowdfundBonusJobService getCrowdfundBonusJobService(){
       if (crowdfundBonusJobService == null) {
           crowdfundBonusJobService = (ICrowdfundBonusJobService) SpringUtil
                   .getBean("crowdfundBonusJobService");
       }
       return crowdfundBonusJobService;
    }
   public static ICrowdFundProductTransferJobService getCrowdFundProductTransferJobService(){
       if (crowdFundProductTransferJobService == null) {
           crowdFundProductTransferJobService = (ICrowdFundProductTransferJobService) SpringUtil
                   .getBean("crowdFundProductTransferJobService");
       }
       return crowdFundProductTransferJobService;
    }
   public static IWithDrawQueryService getWithDrawQueryService(){
       if (withDrawQueryService == null) {
           withDrawQueryService = (IWithDrawQueryService) SpringUtil
                   .getBean("withDrawQueryService");
       }
       return withDrawQueryService;
    }
    private void dealRechargeData(String platform, int count,
            List<Map<String, Object>> list1) throws IOException {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < list1.size(); i++) {
            Map<String, Object> map = list1.get(i);
            Set<Entry<String, Object>> entries = map.entrySet();
            for (Entry<String, Object> entry : entries) {
                System.out.println(entry.getKey()+":"+entry.getValue());
                jsonObject.put(entry.getKey(), entry.getValue());
            }
     
        System.out.println(jsonObject.toString());
        BufferedReader br = null;
        try {
            InputStream in_withcode = new ByteArrayInputStream(jsonObject
                    .toString().getBytes());
            br = new BufferedReader(new InputStreamReader(in_withcode));
            List<String> list = new ArrayList<String>();
            int allCount = 0;
            while (br.ready()) {
                String str = br.readLine();
                list.add(str);
                allCount++;
            }

            DataAPI api = new DataAPI();
            Map<String, Object> lists = api.collected(list, platform);

            List<Map<String, String>> returnList = (List<Map<String, String>>) (lists
                    .get("list"));
            if (lists.get("status").toString().equals("2")) {
                Map lastItem = returnList.get(returnList.size() - 1);
                System.out.println(lastItem.get("errorCode") + "--=="
                        + lastItem.get("errorMsg"));
                logger.error(lastItem.get("errorCode") + "--=="
                        + lastItem.get("errorMsg"));
            } else if (lists.get("status").toString().equals("1")) {
                for (Map m : returnList) {
                    System.out.println(m.get("serial") + " --== "
                            + m.get("errorMsg") + "  ==-- "
                            + m.get("errorCode"));
                    logger.error(m.get("serial") + " --== " + m.get("errorMsg")
                            + "  ==-- " + m.get("errorCode"));

                }
            } else if (lists.get("status").toString().equals("0")) {
                System.out.println("本批数据处理完成");
                logger.info("本批数据处理完成");
            }
            
          
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        }
    }
    
    public void closeDate(String platform, int count,
            List<Map<String, Object>> list1){
        String dateStringToParse = DateUtil.date2Str(new Date(),
                DateUtil.DEFAULT_DATE_FORMAT);
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date collectedDate = sim.parse("2016-09-21");
            DataAPI api = new DataAPI();
            
            Map<String, String> return_map = api.collectedClose("S01",
                    collectedDate, 0, platform);
            System.out.println(return_map.get("flag") + "======"
                    + return_map.get("message") + "==="
                    + return_map.get("messageCode"));
            logger.error(return_map.get("flag") + "======"
                    + return_map.get("message") + "==="
                    + return_map.get("messageCode"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       
    }

}
