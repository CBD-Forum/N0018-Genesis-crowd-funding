package com.fbd.core.app.address.dao;

import java.util.List;
import java.util.Map;
import com.fbd.core.app.address.model.PostAddressModel;
import com.fbd.core.base.BaseDao;

public interface IPostAddressDao extends BaseDao<PostAddressModel>{
    
    public List<Map<String,Object>> getList(String userId);
    
    public void updateNotDefault(String userId);
    
    public PostAddressModel getByAddressNo(String addressNo);
}