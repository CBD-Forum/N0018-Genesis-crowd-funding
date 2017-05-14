/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: InvitecodeServiceImpl.java 
 *
 * Created: [2014-12-11 上午11:45:25] by haolingfeng
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

package com.fbd.web.app.invitecode.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.invitecode.dao.IInviteCodeDao;
import com.fbd.core.app.invitecode.model.InviteCodeModel;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.core.app.user.model.UserModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;
import com.fbd.web.app.common.FbdConstants;
import com.fbd.web.app.invitecode.service.IInviteCodeService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("inviteCodeService")
public class InvitecodeServiceImpl implements IInviteCodeService {
    @Resource
    private IInviteCodeDao inviteCodeDao;
    @Resource
    private IUserDao userDao;

    /**
     * 
     * Description: 邀请关系
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
    public void addInviteReleation(UserModel inviteUser,
            String businessId, String beingInviteduserId) {
        this.insert(FbdCoreConstants.inviteType.register, 
                inviteUser, businessId, beingInviteduserId,
                FbdCoreConstants.invitorType.direct);
        //查询邀请人是否存在邀请人,增加间接邀请人关系
        InviteCodeModel indirectInvitor = this.inviteCodeDao.getInviteUser(
                FbdCoreConstants.inviteType.register,
                inviteUser.getUserId(), FbdCoreConstants.invitorType.direct);
        if(indirectInvitor!=null){
            UserModel indirectUser = userDao.findByUserId(indirectInvitor.getInviteUser());
            this.insert(FbdCoreConstants.inviteType.register, indirectUser, 
                    businessId, beingInviteduserId,
                    FbdCoreConstants.invitorType.indirect);
        }
    }
    
    
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
            String businessId, String beingInviteduserId) {
        this.insert(FbdCoreConstants.inviteType.register, 
                inviteUser, businessId, beingInviteduserId,
                FbdCoreConstants.invitorType.direct);
    }
    
    private void insert(String inviteType, UserModel inviteUser,
            String businessId, String beingInviteduserId,String invitorType){
        InviteCodeModel inviteCodeModel = new InviteCodeModel();
        inviteCodeModel.setId(PKGenarator.getId());
        inviteCodeModel.setInviteType(inviteType);
        inviteCodeModel.setInviteCode(inviteUser.getInviteCode());
        inviteCodeModel.setBusinessId(businessId);
        inviteCodeModel.setInviteUser(inviteUser.getUserId());
        // 被邀请人
        inviteCodeModel.setBeingInviteUser(beingInviteduserId);
        inviteCodeModel.setRegisterTime(new Date());
        inviteCodeModel.setInvitorType(invitorType);
        this.inviteCodeDao.save(inviteCodeModel);
    }
    
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
    public SearchResult<Map<String,Object>> getInviteResult(InviteCodeModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        if(model.getCreateStartTime()!=null){
            model.setCreateStartTime(DateUtil.getDayMin(model.getCreateStartTime()));
        }
        if(model.getCreateEndTime()!=null){
            model.setCreateEndTime(DateUtil.getDayMax(model.getCreateEndTime()));
        }
        result.setRows(this.inviteCodeDao.getPageList(model));
        result.setTotal(this.inviteCodeDao.getPageCount(model));
        return result;
    }
    
    
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
    public SearchResult<Map<String,Object>> getDirectInviteResult(InviteCodeModel model){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        if(model.getCreateStartTime()!=null){
            model.setCreateStartTime(DateUtil.getDayMin(model.getCreateStartTime()));
        }
        if(model.getCreateEndTime()!=null){
            model.setCreateEndTime(DateUtil.getDayMax(model.getCreateEndTime()));
        }
        result.setRows(this.inviteCodeDao.getPageDirectList(model));
        result.setTotal(this.inviteCodeDao.getPageDirectCount(model));
        return result;
    }
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
    public Map<String,Object> getRecommendData(String inviteUser){
        return this.inviteCodeDao.getRecommendData(inviteUser);
    }
    
    
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
    public Map<String,Object> getDirectRecommendData(String inviteUser){
        return this.inviteCodeDao.getDirectRecommendData(inviteUser);
    }
}
