/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AreaAction.java 
 *
 * Created: [2014-12-23 下午12:24:17] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.area.action;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.core.app.area.model.AreaModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.area.service.IAreaService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:行政区
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/area")
public class AreaAction extends BaseAction {
    @Resource
    private IAreaService areaService;

    /**
     * 
     * Description: 查询省
     * 
     * @param
     * @return SearchResult<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:30:32
     */
    @RequestMapping(value = "/getProvince.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getProvince() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            SearchResult<AreaModel> areaResult =  this.areaService.getProvince();
            resultMap.put(SUCCESS,true);
            resultMap.put(MSG, areaResult);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
        }
        return resultMap;
    }

    /**
     * 
     * Description: 查询市
     * 
     * @param
     * @return SearchResult<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:30:43
     */
    @RequestMapping(value = "/getCity.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCity(String pid) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try{
            SearchResult<AreaModel> areaResult =  this.areaService.getCity(pid);
            resultMap.put(SUCCESS,true);
            resultMap.put(MSG, areaResult);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put(SUCCESS,false);
        }
        return resultMap;
    }
}
