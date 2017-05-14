/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IInviteCodeDaoImpl.java 
 *
 * Created: [2014-12-10 下午6:26:12] by haolingfeng
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

package com.fbd.core.app.invitecode.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.invitecode.dao.IInviteCodeDao;
import com.fbd.core.app.invitecode.model.InviteCodeModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:邀请
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("inviteCodeDao")
public class InviteCodeDaoImpl extends BaseDaoImpl<InviteCodeModel> implements
        IInviteCodeDao {
    /**
     * 
     * Description: 邀请结果查询
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-6 上午10:31:42
     */
    public List<Map<String, Object>> getPageList(InviteCodeModel model){
        return this.selectMapByFields("selectByInviteUser", model);
    }
    
    public long getPageCount(InviteCodeModel model){
        return this.getCount("selectCountByInviteUser", model);
    }
    
    /**
     * 
     * Description: 一级邀请结果查询
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-6 上午10:31:42
     */
    public List<Map<String, Object>> getPageDirectList(InviteCodeModel model){
        return this.selectMapByFields("selectDirectByInviteUser", model);
    }
    
    public long getPageDirectCount(InviteCodeModel model){
        return this.getCount("selectDirectCountByInviteUser", model);
    }
    /**
     * 
     * Description: 查询邀请对象
     *
     * @param 
     * @return List<InviteCodeModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-11 下午6:34:33
     */
    public List<InviteCodeModel> getByModel(InviteCodeModel model){
        return this.selectByModel("selectByModel", model);
    }
    
    /**
     * 
     * Description:查询邀请人的上级邀请人 
     *
     * @param 
     * @return InviteCodeModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-11 下午6:39:29
     */
    public InviteCodeModel getInviteUser(String inviteType,String beingInvitor,String invitorType){
        InviteCodeModel qModel = new InviteCodeModel();
        qModel.setInviteType(inviteType);
        qModel.setInvitorType(invitorType);
        qModel.setBeingInviteUser(beingInvitor);
        if(this.getByModel(qModel).size()>0){
            return this.getByModel(qModel).get(0);
        }else{
            return null;
        }
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
        return this.selectOneMapByField("selectTotalInviteData", inviteUser);
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
        return this.selectOneMapByField("selectDirectTotalData", inviteUser);
    }
}
