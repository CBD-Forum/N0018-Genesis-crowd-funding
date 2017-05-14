package com.fbd.core.app.banner.dao;

import com.fbd.core.app.banner.model.BannerModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

public interface IBannerDao extends BaseDao<BannerModel> {

    /**
     * Description: 查询banner
     *
     * @param 
     * @return SearchResult<BannerModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-22 上午10:52:11
     */
    SearchResult<BannerModel> getBannerPage(BannerModel banner);

    int saveBanner(BannerModel banner);
    
}