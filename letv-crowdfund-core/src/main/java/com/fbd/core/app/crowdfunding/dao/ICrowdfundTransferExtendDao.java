package com.fbd.core.app.crowdfunding.dao;

import java.util.List;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferExtendModel;
import com.fbd.core.base.BaseDao;

public interface ICrowdfundTransferExtendDao extends BaseDao<CrowdfundTransferExtendModel>{
    /**
     * 根据用户和转让编号清数据
     */
    public int deleteUserAndTrans(CrowdfundTransferExtendModel model);
    public List<CrowdfundTransferExtendModel> findByModel(CrowdfundTransferExtendModel model);
}