/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: PostAddressServiceImpl.java 
 *
 * Created: [2015-3-31 上午11:28:27] by haolingfeng
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

package com.fbd.core.app.address.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.address.dao.IPostAddressDao;
import com.fbd.core.app.address.model.PostAddressModel;
import com.fbd.core.app.address.service.IPostAddressService;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingSupportDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingSupportModel;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("postAddressService")
public class PostAddressServiceImpl implements IPostAddressService{
    
    @Resource
    private IPostAddressDao postAddressDao;
    
    @Resource
    private ICrowdfundingSupportDao crowdfundingSupportDao;

    /**
     * 
     * Description:保存收件人地址 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午11:33:06
     */
    public void savePostAddress(PostAddressModel model){
        try{
            
            model.setIsDefault(FbdCoreConstants.NOT);
            //查询当前用户是否有保存地址信息
            List<Map<String,Object>> list = this.postAddressDao.getList(model.getUserId()) ;
            if(list==null || list.size()==0){
                model.setIsDefault(FbdCoreConstants.IS); //将第一个设置为默认
            }
            String isDefault = model.getIsDefault();
            if(null!=isDefault && FbdCoreConstants.IS.equals(isDefault)){//是默认，取消其他的默认
                this.postAddressDao.updateNotDefault(model.getUserId());
            }
            model.setId(PKGenarator.getId());
            model.setAddressNo(PKGenarator.getOrderId());
            postAddressDao.save(model);
        }catch(Exception e){
        	e.printStackTrace();
            throw new ApplicationException("保存收件人地址失败");
        }
    }
    
    /**
     * 
     * Description:查询收件人地址列表 
     *
     * @param 
     * @return SearchResult<PostAddressModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午11:53:22
     */
    public SearchResult<Map<String,Object>> getList(String userId){
        SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
        result.setRows(this.postAddressDao.getList(userId));
        return result;
    }
    
    /**
     * 
     * Description:查询收件地址详情 
     *
     * @param 
     * @return PostAddressModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午11:57:25
     */
    public PostAddressModel selectPostAddressById(String id){
        return this.postAddressDao.selectByPrimaryKey(id);
    }
    
    /**
     * 
     * Description:查询收件地址详情 
     *
     * @param 
     * @return PostAddressModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午11:57:25
     */
    public PostAddressModel selectPostAddressByNo(String addressNo){
        return this.postAddressDao.getByAddressNo(addressNo);
    }
    
    /**
     * 
     * Description: 更新
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 上午11:59:07
     */
    public void updatePostAddress(PostAddressModel model){
        try{
            String isDefault = model.getIsDefault();
            if(FbdCoreConstants.IS.equals(isDefault)){//是默认，取消其他的默认
                this.postAddressDao.updateNotDefault(model.getUserId());
            }
            this.postAddressDao.updateBySelective(model);
        }catch(Exception e){
            e.printStackTrace();
            throw new ApplicationException("保存收件人地址失败");
        }
    }
    
    /**
     * 
     * Description:删除地址 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-31 下午12:00:03
     */
    public void deletePostAddress(String id){
        PostAddressModel model = this.postAddressDao.selectByPrimaryKey(id);
        CrowdfundingSupportModel support = new CrowdfundingSupportModel();
        support.setPostAddressNo(model.getAddressNo());
        support.setSupportUser(model.getUserId());
        support.setPayState(CrowdfundCoreConstants.crowdFundPayState.payed);
        long cnt = crowdfundingSupportDao.getCount(support);
        if(cnt > 0){
            throw new ApplicationException("该地址在支付成功的订单中已经使用，不能删除");
        }
        
        this.postAddressDao.deleteByPrimaryKey(id);
    }
    
    /**
     * 查询默认收货地址
     * Description: 
     * @param 
     * @return PostAddressModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-11-11 下午4:31:52
     */
    public Map<String,Object> selectDefaultAddress(String userId){
        List<Map<String,Object>> list = this.postAddressDao.getList(userId) ;
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    
    
}
