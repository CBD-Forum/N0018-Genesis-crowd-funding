/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CertSignServiceImpl.java 
 *
 * Created: [2016-8-25 下午1:38:11] by haolingfeng
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

package com.fbd.core.app.signature.service.impl;
/*import cn.com.itrus.raapi.*;
import cn.com.itrus.*;*/
import org.springframework.stereotype.Service;
import cn.com.itrus.Config;
import cn.com.itrus.raapi.CertInfo;
import cn.com.itrus.raapi.RaServiceUnavailable_Exception;
import cn.com.itrus.raapi.UserAPIService;
import cn.com.itrus.raapi.UserAPIServicePortType;
import cn.com.itrus.raapi.UserInfo;
import com.fbd.core.app.signature.service.ICertSignService;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

@Service(value="certSignService")
public class CertSignServiceImpl implements ICertSignService {

    @Override
    public String applyCertAA(UserInfo userInfo, String certReqBuf,String accountHash, String userPassword) {

        CertInfo certInfo = null;
        String certSignBufP7 = null;
        UserAPIService service = new UserAPIService();
        UserAPIServicePortType userApi = service.getUserAPIServicePort();
        try {
            certInfo = userApi.enrollCertAA(userInfo, certReqBuf, accountHash,userPassword, null, null);
        } catch (RaServiceUnavailable_Exception e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        certSignBufP7 =certInfo.getCertSignBufP7();
        certInfo.getId();
        System.out.println(certSignBufP7);        
    
        return certSignBufP7;
    }
 
}
