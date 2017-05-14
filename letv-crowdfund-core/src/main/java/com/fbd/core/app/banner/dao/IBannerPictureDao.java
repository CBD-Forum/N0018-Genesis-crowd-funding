package com.fbd.core.app.banner.dao;

import java.util.List;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.base.BaseDao;

public interface IBannerPictureDao extends BaseDao<BannerPictureModel> {

    int saveBannerPicture(BannerPictureModel bannerPicture);

    /**
     * Description: 根据banner编码查询图片详情
     *
     * @param 
     * @return List<BannerPictureModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 上午11:05:31
     */
    List<BannerPictureModel> getBannerPicturesByCode(String code);
    
    /**
     * Description: 根据banner Id查询图片详情
     *
     * @param 
     * @return List<BannerPictureModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 上午11:05:31
     */
    List<BannerPictureModel> getBannerPicturesByBannerId(String bannerId);
}