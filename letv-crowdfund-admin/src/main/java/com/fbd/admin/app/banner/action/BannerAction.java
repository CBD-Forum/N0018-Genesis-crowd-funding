package com.fbd.admin.app.banner.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.banner.service.IBannerService;
import com.fbd.core.app.banner.model.BannerModel;
import com.fbd.core.app.banner.model.BannerPictureModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/banner")
public class BannerAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2614754844948218378L;
    
    @Resource
    private IBannerService bannerService;
    
    /**
     * Description: 添加banner
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-1-21 下午6:49:28
     */
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveBanner(BannerModel banner,BannerPictureModel bannerPicture){
        int num = bannerService.saveBanner(banner,bannerPicture);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BANNER, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "编号："+banner.getCode());
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/list.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<BannerModel> getBannerPage(BannerModel banner){
        SearchResult<BannerModel> busConfigList = bannerService.getBannerPage(banner);
        return busConfigList;
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
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getBannerById(String id){
        return bannerService.getBannerById(id);
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
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> modifyBanner(BannerModel banner,BannerPictureModel bannerPicture){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int num = bannerService.modifyBanner(banner,bannerPicture);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BANNER, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "编号："+banner.getCode());
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
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
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeBanner(String id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int num = bannerService.removeBanner(id);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BANNER, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS);
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    /**
     * Description: 删除banner中的图片
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     */
    @RequestMapping(value = "/removePhoto.html", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> removeLoanPhoto(String photoId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int num = bannerService.removeBannerPhoto(photoId);
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_BANNER, AuditLogConstants.TYPE_DELETE, AuditLogConstants.RESULT_SUCCESS, "删除了banner图片");
        }else{
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

}
