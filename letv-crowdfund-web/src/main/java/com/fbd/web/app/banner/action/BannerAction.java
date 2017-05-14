package com.fbd.web.app.banner.action;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.banner.service.IBannerService;

@Controller
@Scope("prototype")
@RequestMapping("/banner")
public class BannerAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4544662316586536913L;
    
    @Resource
    private IBannerService bannerService;
    
    
    @RequestMapping(value = "/getBannerByCode.html", method = RequestMethod.POST)
    public @ResponseBody List<BannerPictureModel> getBannerPicturesByCode(String code){
        List<BannerPictureModel> bannerPictureList = bannerService.getBannerPicturesByCode(code);
        return bannerPictureList;
    }

}
