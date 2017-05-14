/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IPostAddressService.java 
 *
 * Created: [2015-3-31 上午11:27:46] by haolingfeng
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

package com.fbd.core.app.address.service;

import java.util.Map;
import com.fbd.core.app.address.model.PostAddressModel;
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

public interface IPostAddressService {
    
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
    public void savePostAddress(PostAddressModel model);
    
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
    public SearchResult<Map<String,Object>> getList(String userId);
    
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
    public void deletePostAddress(String id);
    
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
    public void updatePostAddress(PostAddressModel model);
    
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
    public PostAddressModel selectPostAddressById(String id);
    
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
    public PostAddressModel selectPostAddressByNo(String addressNo);
    
    
    /**
     * 查询默认收货地址
     * Description: 
     * @param 
     * @return PostAddressModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-11-11 下午4:31:52
     */
    public Map<String,Object> selectDefaultAddress(String userId);

}
