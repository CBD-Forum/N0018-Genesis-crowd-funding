/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdminDaoImpl.java 
 *
 * Created: [2014-12-12 下午4:14:46] by haolingfeng
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

package com.fbd.core.app.verifycode.dao;

import com.fbd.core.app.verifycode.model.VerifyCodeModel;
import com.fbd.core.base.BaseDao;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 验证码
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IVerifyCodeDao extends BaseDao<VerifyCodeModel> {
    /**
     * 
     * Description: 根据类型与发送目标查询最大的一条记录
     * 
     * @param
     * @return VerifyCodeModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-16 下午4:58:55
     */
    public VerifyCodeModel selectByTypeAndTarget(String type, String userType,String target);

    /**
     * Description: 根据userId、userType和verifyType查询有效的验证码
     *
     * @param 
     * @return VerifyCodeModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-3-6 上午11:19:36
     */
    public VerifyCodeModel getVerifyCodeByUserAndVerifyType(String userId,String userType,String sendTarget, String verifyType);

	public String getInviteContent(String nodeCode);
}