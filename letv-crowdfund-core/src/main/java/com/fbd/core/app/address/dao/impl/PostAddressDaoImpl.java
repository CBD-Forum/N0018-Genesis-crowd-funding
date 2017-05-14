/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: PostAddressDaoImpl.java 
 *
 * Created: [2015-3-31 上午11:25:38] by haolingfeng
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

package com.fbd.core.app.address.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.address.dao.IPostAddressDao;
import com.fbd.core.app.address.model.PostAddressModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 收件人地址
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("postAddressDao")
public class PostAddressDaoImpl extends BaseDaoImpl<PostAddressModel> 
implements IPostAddressDao{

    public List<Map<String,Object>> getList(String userId){
        return this.selectMapByFields("selectList", userId);
    }
    
    public void updateNotDefault(String userId){
        this.update("updateNotDefault", userId);
    }
    
    public PostAddressModel getByAddressNo(String addressNo){
        return this.selectOneByField("selectByAddressNo", addressNo);
    }
}
