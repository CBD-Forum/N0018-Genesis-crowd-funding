package com.fbd.web.app.crowdfunding.action;

import java.util.Map;
import java.util.Random;
import java.util.Timer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.blockChain.service.BlockChainCore;
import com.fbd.core.app.blockChain.service.IBlockChainQueryService;
import com.fbd.core.app.blockChain.service.IBlockChainReturnService;
import com.fbd.core.app.blockasyntran.dao.IBlockAsynTranDao;
import com.fbd.core.app.blockasyntran.model.BlockAsynTranModel;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.blockasyntran.service.IBlockAsynTranService;

@Controller
@Scope("prototype")
@RequestMapping("intentionReturn")
public class CrowdfundingIntentionReturnAction extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(CrowdfundingIntentionReturnAction.class);
    @Resource
    private IBlockChainReturnService blockChainReturnService;
    @Resource
	private IBlockAsynTranDao blockAsynTranDao;

	@Resource
	private ICrowdfundingSupportDao crowdfundingSupportDao ;
	@Resource
    private IBlockChainQueryService blockChainQuerySerivce;
	@Resource 
	private IBlockAsynTranService blockAsynTranService;
	
	  /**
     * 意向金退款
     * @param request
     * @return
     */
    @RequestMapping(value = "/returnS2S.html", method = RequestMethod.POST)
    public @ResponseBody void interestTransferS2S(HttpServletRequest request){
    	
    	//等待同步处理数据
        try {
        	Random random = new Random();
        	int ram = random.nextInt(2);
			Thread.sleep(ram*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	try{
	    	logger.info("=============意向金Job接口异步回调开始调用============");
	      	Map<String ,String>map = this.getBlockChainParamsStr();
	        logger.info("=============意向金Job接口通知参数："+map);
	        String transactionID =  map.get("transactionID").toString();
	        String status = map.get("status")== null?"":map.get("status").toString();
	        String requestID = map.get("requestID");
	  	    //查询事务异步通知信息
	        BlockAsynTranModel blockAsynTran = this.blockAsynTranDao.findByRequestId(requestID);
	        if(blockAsynTran!=null){
	        	 blockAsynTran.setStatus(status);
                 blockAsynTran.setTranId(transactionID);
                 this.blockAsynTranDao.updateBySelective(blockAsynTran);
         		Timer timer = new Timer();
         		logger.info("===========>意向金支付requestID:"+requestID+",事务状态："+status);
             	if(BlockChainCore.ResultStatus.SUCCESS.equals(map.get("status"))){
             		timer.schedule((new MyIntentionTask(timer,transactionID,BlockChainCore.ResultStatus.SUCCESS,"Intention",requestID)), 2000, 2000);  
             	}else if(BlockChainCore.ResultStatus.TRANSACTION_SUCCESS.equals(status)){
             		blockChainReturnService.returnBackSuccess(requestID,null);
             	}else  if(BlockChainCore.ResultStatus.TRANSACTION_FAIL.equals(status)){
             		
             	}
	        }  
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /**
     * 定时任务
     * @author 80bug
     *
     */
	class MyIntentionTask  extends java.util.TimerTask{
        String transId="";
        Timer timer =null;
        String status ="";
        String type ="";
        String requestId = "";
        public MyIntentionTask(Timer mytimer,String tranId,String status,String type,String requestId){
        	this.timer = mytimer;
        	this.transId = tranId;
        	this.status = status;
        	this.type = type;
        	this.requestId = requestId;
        }
		@Override
		public void run() {	
			blockChainQuerySerivce.transactionQuery(transId,requestId);
			try{
                Thread.sleep(500);
                blockChainReturnService.returnBackSuccess(requestId,timer);
			}catch(Exception e){
				e.printStackTrace();
				timer.cancel();
            	System.gc();
			}
	    }
	} 
}
