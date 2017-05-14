package com.fbd.web.app.banner.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.banner.dao.IBannerDao;
import com.fbd.core.app.banner.dao.IBannerPictureDao;
import com.fbd.core.app.banner.model.BannerModel;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.banner.service.IBannerService;

@Service
public class BannerServiceImpl implements IBannerService {
    
    @Resource
    private IBannerDao bannerDao;
    
    @Resource
    private IBannerPictureDao bannerPictureDao;

    public List<BannerPictureModel> getBannerPicturesByCode(String code) {
        return bannerPictureDao.getBannerPicturesByCode(code);
    }
    

}
