package com.fbd.admin.app.banner.service.impl;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.banner.service.IBannerService;
import com.fbd.core.app.banner.dao.IBannerDao;
import com.fbd.core.app.banner.dao.IBannerPictureDao;
import com.fbd.core.app.banner.model.BannerModel;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class BannerServiceImpl implements IBannerService {

    
    @Resource
    private IBannerDao bannerDao;
    
    @Resource
    private IBannerPictureDao bannerPictureDao;

    /**
     * Description: 添加banner
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-21 下午6:49:28
     */
    public int saveBanner(BannerModel banner,BannerPictureModel bannerPicture) {
        banner.setId(PKGenarator.getId());
        int num = bannerDao.saveBanner(banner);
        bannerPictureDao.deleteByField("deleteByBannerId", banner.getId());
        String[] pictures = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getPicture())) {
            pictures = bannerPicture.getPicture().split(",",-1);
        }
        String[] titles = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getTitle())) {
            titles = bannerPicture.getTitle().split(",",-1);
        }
        String[] urls = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getUrl())) {
            urls = bannerPicture.getUrl().split(",",-1);
        }
        String[] seqNums = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getSeqNumStr())) {
            seqNums = bannerPicture.getSeqNumStr().split(",",-1);
        }
        for (int i = 0; i < pictures.length; i++) {
            BannerPictureModel bannerPictureModel = new BannerPictureModel();
            bannerPictureModel.setId(PKGenarator.getId());
            bannerPictureModel.setBannerId(banner.getId());
            bannerPictureModel.setPicture(pictures[i]);
            if (titles.length == pictures.length) {
                bannerPictureModel.setTitle(titles[i]);
            }
            if (urls.length == pictures.length) {
                bannerPictureModel.setUrl(urls[i]);
            }
            if (seqNums.length == pictures.length) {
                bannerPictureModel.setSeqNum(Integer.parseInt(seqNums[i]));
            }
            bannerPictureDao.saveBannerPicture(bannerPictureModel);
        }
        return num;
    }

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
        return bannerDao.getBannerPage(banner);
    }

    public int saveBannerPicture(BannerPictureModel bannerPicture) {
        return bannerPictureDao.saveBannerPicture(bannerPicture);
    }

    /**
     * Description: 根据Id查询banner对象
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 上午10:54:07
     */
    public Map<String, Object> getBannerById(String id) {
        Map<String, Object> bannerMap = new HashMap<String, Object>();
        bannerMap.putAll(bannerDao.selectOneMapByField("selectMapById", id));
        bannerMap.put("pictures", bannerPictureDao.getBannerPicturesByBannerId(id));
        return bannerMap;
    }

    /**
     * Description: 修改banner
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 下午12:34:12
     */
    public int modifyBanner(BannerModel banner, BannerPictureModel bannerPicture) {
        //修改banner基本信息
        int num = bannerDao.updateBySelective(banner);
        //修改banner图片信息
        bannerPictureDao.deleteByField("deleteByBannerId", banner.getId());
        String[] pictures = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getPicture())) {
            pictures = bannerPicture.getPicture().split(",",-1);
        }
        String[] titles = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getTitle())) {
            titles = bannerPicture.getTitle().split(",",-1);
        }
        String[] urls = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getUrl())) {
            urls = bannerPicture.getUrl().split(",",-1);
        }
        String[] seqNums = new String[0];
        if (StringUtils.isNotEmpty(bannerPicture.getSeqNumStr())) {
            seqNums = bannerPicture.getSeqNumStr().split(",",-1);
        }
        for (int i = 0; i < pictures.length; i++) {
            BannerPictureModel bannerPictureModel = new BannerPictureModel();
            bannerPictureModel.setId(PKGenarator.getId());
            bannerPictureModel.setBannerId(banner.getId());
            bannerPictureModel.setPicture(pictures[i]);
            if (titles.length == pictures.length) {
                bannerPictureModel.setTitle(titles[i]);
            }
            if (urls.length == pictures.length) {
                bannerPictureModel.setUrl(urls[i]);
            }
            if (seqNums.length == pictures.length) {
                bannerPictureModel.setSeqNum(StringUtils.isEmpty(seqNums[i])?null:Integer.parseInt(seqNums[i]));
            }
            bannerPictureDao.saveBannerPicture(bannerPictureModel);
        }
        return num;
        
    }

    /**
     * Description: 根据Id删除
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-13 下午4:01:49
     */
    public int removeBanner(String id) {
        int num = bannerDao.deleteByPrimaryKey(id);
        bannerPictureDao.deleteByField("deleteByBannerId", id);
        return num;
    }
    
    public int removeBannerPhoto(String photoId) {
		return bannerPictureDao.deleteByField("deleteByBannerId", photoId);
	}
    
}
