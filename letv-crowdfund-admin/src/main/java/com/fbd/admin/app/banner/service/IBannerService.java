package com.fbd.admin.app.banner.service;

import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.banner.model.BannerModel;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.common.model.SearchResult;

public interface IBannerService {

    /**
     * Description: 添加banner
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-21 下午6:49:28
     */
    @PreAuthorize("hasPermission(null, 'security.operation.spread_banner_add')")
    int saveBanner(BannerModel banner,BannerPictureModel bannerPicture);

    SearchResult<BannerModel> getBannerPage(BannerModel banner);

    int saveBannerPicture(BannerPictureModel bannerPicture);

    /**
     * Description: 根据Id查询banner对象
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 上午10:54:07
     */
    Map<String, Object> getBannerById(String id);

    /**
     * Description: 修改banner
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 下午12:34:12
     */
    @PreAuthorize("hasPermission(null, 'security.operation.spread_banner_modify')")
    int modifyBanner(BannerModel banner, BannerPictureModel bannerPicture);

    /**
     * Description: 根据Id删除
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 下午4:01:49
     */
    @PreAuthorize("hasPermission(null, 'security.operation.spread_banner_delete')")
    int removeBanner(String id);

	int removeBannerPhoto(String photoId);

}
