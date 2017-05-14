package com.fbd.core.app.signature.util;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockChain.service.BlockChainCore.ResultStatus;
import com.fbd.core.common.utils.JsonHelper;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.core.util.DateUtils;
import com.fbd.core.util.spring.SpringUtil;

public class Test {
	
    static class TransTask extends java.util.TimerTask{
        private Timer timer;
        private Map<String,String> valueMap;
        
        
        public TransTask(Timer timer,Map<String,String> resultMap){
            this.timer=timer;
            this.valueMap=resultMap;
        }
        
        @Override
        public void run() {
/*            try {
                Thread.sleep(6000L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/
            int count=Integer.parseInt(this.valueMap.get("count"));
            System.out.println(this.scheduledExecutionTime()+"------------------------"+count);
            if(count==5){
                timer.cancel();
            }else{
                this.valueMap.put("count", (count+1)+"");
            }
        }
        
    }
    static class TransTask1 extends java.util.TimerTask{
        private ScheduledThreadPoolExecutor timer;
        private Map<String,String> valueMap;
        
        
        public TransTask1(ScheduledThreadPoolExecutor timer,Map<String,String> resultMap){
            this.timer=timer;
            this.valueMap=resultMap;
        }
        
        @Override
        public void run() {
            int count=Integer.parseInt(this.valueMap.get("count"));
            System.out.println("1------------------------"+count);
            if(count==5){
                //timer.cancel();
                timer.shutdown();
            }else{
                this.valueMap.put("count", (count+1)+"");
            }
        }
        
    }
	public static void main(String[] args) {
	    Map<String, String> map=new HashMap<String, String>();
        map.put("status", "false");
        map.put("count", "1");
        try{
            Timer timer=new Timer();
            timer.schedule(new TransTask(timer,map), 1000, 1000);
            Thread.sleep(3000);
            timer.cancel();
/*            map.put("status", "false");
            map.put("count", "1");
            timer=new Timer();
            timer.schedule(new TransTask(timer,map), 1000, 1000);*/
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            System.out.println("end-------------------");
        }
        //ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
/*        ScheduledThreadPoolExecutor pool=new ScheduledThreadPoolExecutor(1);
        pool.schedule((new TransTask(pool,map)),0, TimeUnit.MILLISECONDS);*/
        //map.put("count", "3");
/*        map=new HashMap<String, String>();
        map.put("status", "false");
        map.put("count", "1");
        pool.scheduleAtFixedRate((new TransTask1(pool,map)),0,1000, TimeUnit.MILLISECONDS);*/
	    
/*	    System.out.println(1347751%1+"___________"+1347751%2+"______________"+1347751%1);
	    System.out.println(1345710.87%2);
	    System.out.println(1345710.87%20);
	    DecimalFormat format=new DecimalFormat("#");
	    System.out.println(format.format(1345710.87));
	    System.out.println(1345501.76%2);
	    System.out.println(1345503%2);*/
	    //System.out.println("aaa_auditing".indexOf("auditing")==-1);
	    //System.out.println(PKGenarator.getOrderId());
/*	    Double d=Double.parseDouble("600");
	    System.out.println(d.compareTo(0.00));*/
	    //System.out.println(PKGenarator.getId());
	}

}
