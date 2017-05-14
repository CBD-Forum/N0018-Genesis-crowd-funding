package com.fbd.core.common.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.dao.impl.SysConfigDaoImpl;
import com.fbd.core.util.spring.SpringUtil;

public class FTPManagerFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(SFTPUtils.class);
    
    private static ISysConfigDao sysConfigDao = (SysConfigDaoImpl) SpringUtil.getBean("sysConfigDaoImpl");

    
    private static FTPManager ftpManager;

    public static FTPManager getFtpManager() {
        return ftpManager;
    }
    
    static {
        initFTPManager();
    }
    
    /**
     * 初始化缓存管理器
     */
    private static void initFTPManager() {
        int port = Integer.parseInt(sysConfigDao.getByDisplayName("ftp_port").getCode());
        String manager = "";
        if (port==21) {
            manager = "com.fbd.core.common.ftp.FTPUtils";
        }else { 
            manager = "com.fbd.core.common.ftp.SFTPUtils";
        }

        try {
            ftpManager = (FTPManager) Class.forName(manager).newInstance();
        } catch (Exception e) {
            logger.warn("FTP[class=" + manager + "]  init error:", e);
        }
    }
    
}
