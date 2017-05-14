package com.fbd.core.common.utils;

import java.util.Date;
import com.fbd.core.app.log.service.ILogService;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.util.spring.SpringUtil;

/**
 * 日志工具类
 */
public class LogUtil {
	
	private static ILogService logService;
	
	static{
		logService = (ILogService)SpringUtil.getBean("logService");
	}
	/**
	 * 
	 * Description: 成功日志
	 *
	 * @param 
	 * @return void
	 * @throws 
	 * @Author haolingfeng
	 * Create Date: 2015-2-28 上午11:50:46
	 */
	public static void addSuccessLog(String busiId,String operateType, Date startTime, String summary, String content){
		logService.saveJobOperationLog(busiId,operateType, summary, content, startTime,FbdCoreConstants.RESULT_SUCCESS);
	}
	
	/**
	 * 
	 * Description: 失败日志
	 *
	 * @param 
	 * @return void
	 * @throws 
	 * @Author haolingfeng
	 * Create Date: 2015-2-28 上午11:50:54
	 */
	public static void addFailLog(String busiId,String operateType, Date startTime, String summary, String content){
	    logService.saveJobOperationLog(busiId,operateType, summary, content, startTime,FbdCoreConstants.RESULT_FAIL);
	}
}