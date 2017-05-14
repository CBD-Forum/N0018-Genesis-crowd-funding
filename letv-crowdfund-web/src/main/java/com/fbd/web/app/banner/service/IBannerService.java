package com.fbd.web.app.banner.service;

import java.util.List;
import com.fbd.core.app.banner.model.BannerPictureModel;

public interface IBannerService {

    List<BannerPictureModel> getBannerPicturesByCode(String code);

}
