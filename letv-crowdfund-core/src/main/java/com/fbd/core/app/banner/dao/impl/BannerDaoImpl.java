package com.fbd.core.app.banner.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.banner.dao.IBannerDao;
import com.fbd.core.app.banner.model.BannerModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository
public class BannerDaoImpl extends BaseDaoImpl<BannerModel> implements IBannerDao {

    /**
     * Description: 查询banner
     *
     * @param 
     * @return SearchResult<BannerModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-22 上午10:52:11
     */
    public SearchResult<BannerModel> getBannerPage(BannerModel banner) {
        SearchResult<BannerModel> searchResult = new SearchResult<BannerModel>();
        searchResult.setTotal(getBannerCount(banner));
        searchResult.setRows(getBannerList(banner));
        return searchResult;
    }
    
    public List<BannerModel> getBannerList(BannerModel banner){
        return this.selectByModel("select", banner);
    }
    
    public long getBannerCount(BannerModel banner) {
        return 10;
    }

    public int saveBanner(BannerModel banner) {
        return this.save(banner);
    }

}
