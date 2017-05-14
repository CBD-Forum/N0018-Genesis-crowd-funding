package com.fbd.core.common.utils;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import com.fbd.core.app.log.dao.IOperateLogDao;
import com.fbd.core.app.log.dao.impl.OperateLogDaoImpl;
import com.fbd.core.app.log.model.OperateLogModel;
import com.fbd.core.app.user.model.AdminUserDetails;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.HttpServletUtils;
import com.fbd.core.util.SpringPropertiesHolder;
import com.fbd.core.util.spring.SpringUtil;
import com.fbd.core.web.MyRequestContextHolder;

/**
 * 消息发送工具类
 */
public class AuditLogUtils {
	
	private static IOperateLogDao operateLogDao;
	
	static{
	    operateLogDao = (OperateLogDaoImpl)SpringUtil.getBean("operateLogDao");
	}
	
	/**
	 * Description: 
	 *
	 * @param operateModel 操作模块
	 * @param operateType 操作类型
	 * @param operateResult 操作结果
	 * @param resInfo 异常信息
	 * 
	 * @return int
	 * @throws 
	 * @Author dongzhongwei
	 * Create Date: 2015-3-24 下午10:45:55
	 */
	public static int log(String operateModel,String operateType,String operateResult,String resInfo){
	    int num = 0;
	    try {
	        HttpServletRequest request =MyRequestContextHolder.getCurrentRequest();
	        AdminUserDetails userDetails = MyRequestContextHolder.getCurrentUser();
	        String userId = userDetails.getAdminId();
	        String userType = SpringPropertiesHolder.getProperty("audit.user.type");
	        String ip = HttpServletUtils.getIpAddress(request);
	        resInfo = StringUtils.trimToEmpty(resInfo);
	        OperateLogModel operateLog = new OperateLogModel(PKGenarator.getId(), userId, userType, new Date(), ip, operateType, operateModel, operateResult, resInfo);
	        num = operateLogDao.saveOperateLog(operateLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return num;
	}
	
	/**
	 * Description: 
	 *
	 * @param userId 用户Id
	 * @param operateModel 操作模块
     * @param operateType 操作类型
     * @param operateResult 操作结果
     * @param resInfo 异常信息
	 * @return int
	 * @throws 
	 * @Author dongzhongwei
	 * Create Date: 2015-4-7 上午9:40:10
	 */
	public static int log(String userId,String operateModel,String operateType,String operateResult,String resInfo){
        int num = 0;
        try {
            HttpServletRequest request =MyRequestContextHolder.getCurrentRequest();
            String userType = SpringPropertiesHolder.getProperty("audit.user.type");
            String ip = HttpServletUtils.getIpAddress(request);
            resInfo = StringUtils.trimToEmpty(resInfo);
            OperateLogModel operateLog = new OperateLogModel(PKGenarator.getId(), userId, userType, new Date(), ip, operateType, operateModel, operateResult, resInfo);
            num = operateLogDao.saveOperateLog(operateLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }
	
	/**
     * Description: 
     *
     * @param operateModel 操作模块
     * @param operateType 操作类型
     * @param operateResult 操作结果
     * 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-24 下午10:45:55
     */
    public static int log(String operateModel,String operateType,String operateResult){
        return log(operateModel, operateType, operateResult, null);
    }
	
}