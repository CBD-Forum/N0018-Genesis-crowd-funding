/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundUserStuffDaoImpl.java 
 *
 * Created: [2015-5-19 下午12:06:24] by haolingfeng
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

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.utils.StringUtil;
import com.fbd.core.exception.ApplicationException;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹用户资料
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundUserStuffDao")
public class CrowdfundUserStuffDaoImpl extends BaseDaoImpl<CrowdfundUserStuffModel>
implements ICrowdfundUserStuffDao{
    
    public List<CrowdfundUserStuffModel> getByUserId(CrowdfundUserStuffModel model){
        if(StringUtil.isEmpty(model.getUserId())){
            throw new ApplicationException("用户编号不能为空");
        }
        return this.selectByField("selectByUserId", model);
    }
    
    public void updateByUserId(CrowdfundUserStuffModel model){
        this.update("updateByUserId",model);
    }
    
    public List<Map<String,Object>> getList(CrowdfundUserStuffModel model){
        return this.selectMapByFields("selectList",model);
    }
    
    public long getCount(CrowdfundUserStuffModel model){
        return this.getCount("selectCount",model);
    }
    
    
    public List<Map<String,Object>> getAuthList(CrowdfundUserStuffModel model){
        return this.selectMapByFields("selectAuthList",model);
    }
    public long getAuthCount(CrowdfundUserStuffModel model){
        return this.getCount("selectAuthCount",model);
    }
    
    /**
     * 
     * Description:查询众筹用户信息 
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-2 下午2:49:20
     */
    public Map<String,Object> getCrowdfundUserInfo(String userId){
        return this.selectOneMapByField("selectCrowdfundUser", userId);
    }

    /**
     * Description: 查询用户 材料信息表
     *
     * @param 
     * @return CrowdfundUserStuffModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-3-4 上午9:57:08
     */
    public CrowdfundUserStuffModel findUserStuffByUserId(String userId) {
        return this.selectOneByField("selectUserStuffByUserId", userId);
    }

    /**
     * Description: 查询认证信息
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-8-12 下午3:18:47
     */
    public CrowdfundUserStuffModel getCrowdfundUserAuth(
            CrowdfundUserStuffModel model) {
        return this.selectOneByField("selectCrowdfundUserAuth", model);
    }
    
    
    public CrowdfundUserStuffModel selectByModel(
            CrowdfundUserStuffModel model) {
        return this.selectOneByField("selectByModel", model);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.crowdfunding.dao.ICrowdfundUserStuffDao#deleteByUserAndAuth(com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel)
     */
    @Override
    public int deleteByUserAndAuth(CrowdfundUserStuffModel model) {
        // TODO Auto-generated method stub
        if(StringUtil.isEmpty(model.getUserId())){
            throw new ApplicationException("用户编号不要能为空");
        }
        if(StringUtil.isEmpty(model.getAuthType())){
            throw new ApplicationException("角色类型不能为空");
        }
        return this.deleteByField("deleteByUserAndAuth", model);
    }
     
     
}
