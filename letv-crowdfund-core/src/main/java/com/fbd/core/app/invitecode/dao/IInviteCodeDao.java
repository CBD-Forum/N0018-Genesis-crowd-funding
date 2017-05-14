package com.fbd.core.app.invitecode.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.invitecode.model.InviteCodeModel;
import com.fbd.core.base.BaseDao;

public interface IInviteCodeDao extends BaseDao<InviteCodeModel> {
    
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
    public List<Map<String, Object>> getPageList(InviteCodeModel model);
    
    public long getPageCount(InviteCodeModel model);
    
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
    public List<InviteCodeModel> getByModel(InviteCodeModel model);
    
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
    public InviteCodeModel getInviteUser(String inviteType,String beingInvitor,String invitorType);
    
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
     * Description: 一级邀请结果查询
     *
     * @param 
     * @return List<Map<String,Object>>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-6 上午10:31:42
     */
    public List<Map<String, Object>> getPageDirectList(InviteCodeModel model);
    
    public long getPageDirectCount(InviteCodeModel model);
    
    
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
    
    
}