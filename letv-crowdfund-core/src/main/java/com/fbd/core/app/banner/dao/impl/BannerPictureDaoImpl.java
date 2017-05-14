package com.fbd.core.app.banner.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.banner.dao.IBannerPictureDao;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.base.BaseDaoImpl;

@Repository
public class BannerPictureDaoImpl extends BaseDaoImpl<BannerPictureModel> implements IBannerPictureDao {

    public int saveBannerPicture(BannerPictureModel bannerPicture) {
        return this.save(bannerPicture);
    }

    /**
     * Description: 根据banner编码查询图片详情
     *
     * @param 
     * @return List<BannerPictureModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 上午11:05:31
     */
    public List<BannerPictureModel> getBannerPicturesByCode(String code) {
        return this.selectByField("selectByCode", code);
    }

    /**
     * Description: 根据banner Id查询图片详情
     *
     * @param 
     * @return List<BannerPictureModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 上午11:05:31
     */
    public List<BannerPictureModel> getBannerPicturesByBannerId(String bannerId) {
        return this.selectByField("selectByBannerId", bannerId);
    }

}
