/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IAreaService.java 
 *
 * Created: [2014-12-23 下午12:21:27] by haolingfeng
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

package com.fbd.admin.app.area.service;

import com.fbd.core.app.area.model.AreaModel;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IAreaService {
    /**
     * 
     * Description:查询省
     * 
     * @param
     * @return List<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:23:47
     */
    public SearchResult<AreaModel> getProvince();

    /**
     * 
     * Description: 根据省查询市
     * 
     * @param
     * @return List<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:23:56
     */
    public SearchResult<AreaModel> getCity(String pid);

}
