/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: VerifyCodeDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:21:44] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.verifycode.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.fbd.core.app.message.model.MessageTemplateModel;
import com.fbd.core.app.verifycode.dao.IVerifyCodeDao;
import com.fbd.core.app.verifycode.model.VerifyCodeModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 验证码
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("verifyCodeDao")
public class VerifyCodeDaoImpl extends BaseDaoImpl<VerifyCodeModel> implements
        IVerifyCodeDao {
    /**
     * 
     * Description: 根据类型与发送目标查询最大的一条记录
     * 
     * @param
     * @return VerifyCodeModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午4:58:55
     */
    public VerifyCodeModel selectByTypeAndTarget(String type, String userType,String userId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("verifyType", type);
        params.put("userId", userId);
        params.put("userType", userType);
        return this.selectOneByField("selectByTypeAndTarget", params);
    }

    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午11:19:36
     */
    public VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId,String userType, String sendTarget, String verifyType) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("userType", userType);
        params.put("sendTarget", sendTarget);
        params.put("verifyType", verifyType);
        return this.selectOneByField("selectByTypeAndTarget", params);
    }

	public String getInviteContent(String nodeCode) {
		MessageTemplateModel messageTpl = sqlSession.selectOne("com.fbd.core.app.message.model.MessageTemplateModelMapper.selectByTplCode", nodeCode);
        String content = messageTpl.getTplContent();
		return content;
	}

}
