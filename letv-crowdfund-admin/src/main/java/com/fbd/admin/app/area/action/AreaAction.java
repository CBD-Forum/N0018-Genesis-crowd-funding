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

package com.fbd.admin.app.area.action;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.area.service.IAreaService;
import com.fbd.core.app.area.model.AreaModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;

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
    /**
     * 
     */
    private static final long serialVersionUID = -7784038336348923488L;
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
    SearchResult<AreaModel> getProvince() {
        return this.areaService.getProvince();
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
    SearchResult<AreaModel> getCity(String pid) {
        return this.areaService.getCity(pid);
    }
}
