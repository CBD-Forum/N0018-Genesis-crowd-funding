/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IAreaDao.java 
 *
 * Created: [2014-12-23 下午12:07:31] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.area.dao;

import java.util.List;
import com.fbd.core.app.area.model.AreaModel;
import com.fbd.core.base.BaseDao;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 行政
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IAreaDao extends BaseDao<AreaModel> {
    /**
     * 
     * Description: 查询省
     * 
     * @param
     * @return List<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:14:47
     */
    public List<AreaModel> getProvince();

    /**
     * 
     * Description: 查询市
     * 
     * @param
     * @return List<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:17:00
     */
    public List<AreaModel> getCity(String pid);
}
