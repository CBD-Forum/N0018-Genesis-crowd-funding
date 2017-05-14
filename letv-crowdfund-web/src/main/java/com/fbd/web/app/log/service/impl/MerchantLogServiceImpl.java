/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: MerchantLogServiceImpl.java 
 *
 * Created: [2014-12-12 下午5:19:59] by haolingfeng
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
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.model.SysConfigModel;
import com.fbd.core.app.log.dao.IMerchantLogDao;
import com.fbd.core.app.log.model.MerchantLogModel;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.log.service.IMerchantLogService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("merchantLogService")
public class MerchantLogServiceImpl implements IMerchantLogService {

    @Resource
    private IMerchantLogDao merchantLogDao;
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
    public void addSuccessLog(String merchantId, String ipAddress) {
        if (this.getUserLogSwitch().equals(
                FbdConstants.USERLOG_SWITCH_CODE_TRUE)) {
            return;
        }
        MerchantLogModel userLog = new MerchantLogModel();
        userLog.setId(PKGenarator.getId());
        userLog.setIpAddress(ipAddress);
        userLog.setLoginTime(new Date());
        userLog.setLoginResult(FbdConstants.USER_LOG_RESULT_SUCCESS);
        userLog.setUserId(merchantId);
        this.merchantLogDao.save(userLog);
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
    public void addFailLog(String merchantId, String ipAddress, String resInfo) {
        if (this.getUserLogSwitch().equals(
                FbdConstants.USERLOG_SWITCH_CODE_TRUE)) {
            return;
        }
        MerchantLogModel userLog = new MerchantLogModel();
        userLog.setId(PKGenarator.getId());
        userLog.setIpAddress(ipAddress);
        userLog.setLoginTime(new Date());
        userLog.setLoginResult(FbdConstants.USER_LOG_RESULT_FAIL);
        userLog.setResInfo(resInfo);
        userLog.setUserId(merchantId);
        this.merchantLogDao.save(userLog);
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
                .getByDisplayName(FbdConstants.MERCHANTLOG_SWITCH_CODE);
        String isSave = config.getCode();
        return isSave;
    }

}
