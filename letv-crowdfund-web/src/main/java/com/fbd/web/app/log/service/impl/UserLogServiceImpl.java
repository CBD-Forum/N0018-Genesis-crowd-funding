/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserLogServiceImpl.java 
 *
 * Created: [2014-12-10 下午6:38:50] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.log.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.log.dao.IUserLogDao;
import com.fbd.core.app.log.model.UserLogModel;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.log.service.IUserLogService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("userLogService")
public class UserLogServiceImpl implements IUserLogService {

    @Resource
    private IUserLogDao userLogDao;
    @Resource
    private ISysConfigDao sysConfigDao;

    /**
     * 
     * Description: 保存正常日志
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午6:54:31
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSuccessLog(String userId, String ipAddress) {
        if (this.getUserLogSwitch().equals(
                FbdConstants.USERLOG_SWITCH_CODE_TRUE)) {
            return;
        }
        UserLogModel userLog = new UserLogModel();
        userLog.setId(PKGenarator.getId());
        userLog.setIpAddress(ipAddress);
        userLog.setLoginTime(new Date());
        userLog.setLoginResult(FbdConstants.USER_LOG_RESULT_SUCCESS);
        userLog.setUserId(userId);
        this.userLogDao.save(userLog);
    }

    /**
     * 
     * Description: 保存失败日志
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-10 下午7:04:15
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addFailLog(String userId, String ipAddress, String resInfo) {
        if (this.getUserLogSwitch().equals(
                FbdConstants.USERLOG_SWITCH_CODE_TRUE)) {
            return;
        }
        UserLogModel userLog = new UserLogModel();
        userLog.setId(PKGenarator.getId());
        userLog.setIpAddress(ipAddress);
        userLog.setLoginTime(new Date());
        userLog.setLoginResult(FbdConstants.USER_LOG_RESULT_FAIL);
        userLog.setResInfo(resInfo);
        userLog.setUserId(userId);
        this.userLogDao.save(userLog);
    }

    /**
     * 
     * Description: 日志开关
     * 
     * @param
     * @return String
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:55:24
     */
    private String getUserLogSwitch() {
        SysConfigModel config = this.sysConfigDao
                .getByDisplayName(FbdConstants.USERLOG_SWITCH_CODE);
        String isSave = config.getCode();
        return isSave;
    }
    
    /**
     * 
     * Description: 获得上次登录时间
     *
     * @param 
     * @return Date
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-2-26 下午7:06:25
     */
    public Date getLastLoginTime(String userId){
        UserLogModel userLog = new UserLogModel();
        userLog.setUserId(userId);
        userLog.setSort("loginTime");
        userLog.setOrder("desc");
        List<UserLogModel> logList = this.userLogDao.getUserLogList(userLog);
        if(logList.size()<2){
            return null;
        }else{
            return logList.get(1).getLoginTime();
        }
    }

}
