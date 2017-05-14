/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IInviteCodeService.java 
 *
 * Created: [2014-12-11 下午12:12:06] by haolingfeng
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

package com.fbd.web.app.invitecode.service;

import java.util.Map;
import com.fbd.core.app.invitecode.model.InviteCodeModel;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IInviteCodeService {

	
	
    /**
     * 
     * Description: 邀请关系
     * 
     * @param
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午1:03:09
     */
    public void addInviteReleation(UserModel inviteUser,
            String businessId, String beingInviteduserId);
    
    /**
     * 
     * Description:分页查询邀请结果 
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-6 上午10:38:32
     */
    public SearchResult<Map<String,Object>> getInviteResult(InviteCodeModel inviteCodeModel);
    
    /**
     * 
     * Description: 查询推荐人的邀请结果
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-14 下午6:41:09
     */
    public Map<String,Object> getRecommendData(String inviteUser);
    
    /**
     * 
     * Description:分页查询一级邀请结果 
     *
     * @param 
     * @return SearchResult<Map<String,Object>>
     * @throws 
     * @Author haolingfeng<br/>
     * Create Date: 2015-3-6 上午10:38:32
     */
    public SearchResult<Map<String,Object>> getDirectInviteResult(InviteCodeModel model);
    
    
    /**
     * 
     * Description: 查询推荐人的邀请结果(直接推荐)
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-14 下午6:41:09
     */
    public Map<String,Object> getDirectRecommendData(String inviteUser);
    
    /**
     * 
     * Description: 邀请关系(直接邀请)
     * 
     * @param inviteType
     *            邀请类型
     * @param inviteUser
     *            邀请人
     * @param businessId
     *            业务编号
     * @param beingInviteduserId
     *            被邀请人
     * @return void
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午1:03:09
     */
    public void addDirectInviteReleation(UserModel inviteUser,
            String businessId, String beingInviteduserId);
}
