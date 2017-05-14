package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.crowdfunding.model.CrowdfundUserStuffModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundUserStuffDao extends BaseDao<CrowdfundUserStuffModel>{
    public List<CrowdfundUserStuffModel> getByUserId(CrowdfundUserStuffModel model);
    public void updateByUserId(CrowdfundUserStuffModel model);
    
    
    public List<Map<String,Object>> getList(CrowdfundUserStuffModel model);
    
    public long getCount(CrowdfundUserStuffModel model);
    
    
    public List<Map<String,Object>> getAuthList(CrowdfundUserStuffModel model);
    
    public long getAuthCount(CrowdfundUserStuffModel model);
    
    
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
    public Map<String,Object> getCrowdfundUserInfo(String userId);
    /**
     * Description: 查询用户 材料信息表
     *
     * @param 
     * @return CrowdfundUserStuffModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-3-4 上午9:57:08
     */
    
    public CrowdfundUserStuffModel findUserStuffByUserId(String userId);
    
    
    
    /**
     * 查询认证材料信息
     * Description: 
     *
     * @param 
     * @return CrowdfundUserStuffModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-9 下午4:41:42
     */
    public CrowdfundUserStuffModel selectByModel(CrowdfundUserStuffModel model);
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
            CrowdfundUserStuffModel model);
    /**
     * 
     * Description: 根据用户和角色清空数据
     *
     * @param 
     * @return long
     * @throws 
     * @Author hanchenghe
     * Create Date: 2016-9-19 下午2:51:41
     */
    public int deleteByUserAndAuth(CrowdfundUserStuffModel model);
}