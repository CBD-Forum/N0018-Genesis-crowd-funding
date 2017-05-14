package com.fbd.web.app.seo.action;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.seo.model.SeoTdkModel;
import com.fbd.core.base.BaseAction;
import com.fbd.web.app.seo.service.ISeoService;

@Controller
@Scope("prototype")
@RequestMapping("/seo")
public class SeoAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 4074193845853989542L;
    
    @Resource
    private ISeoService seoService;
    
    
    @RequestMapping(value = "/getTDKByCode.html", method = RequestMethod.POST)
    public @ResponseBody SeoTdkModel getSeoTdkByCode(String code){
        return seoService.getTDKBycode(code);
    }

}
