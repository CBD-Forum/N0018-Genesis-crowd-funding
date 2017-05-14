/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AreaDaoImpl.java 
 *
 * Created: [2014-12-23 下午12:07:46] by haolingfeng
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

package com.fbd.core.app.area.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.area.dao.IAreaDao;
import com.fbd.core.app.area.model.AreaModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 行政
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("areaDao")
public class AreaDaoImpl extends BaseDaoImpl<AreaModel> implements IAreaDao {

    /**
     * 
     * Description: 查询省
     * 
     * @param
     * @return List<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:14:47
     */
    public List<AreaModel> getProvince() {
        return this.selectByField("selectProvince", null);
    }

    /**
     * 
     * Description: 查询市
     * 
     * @param
     * @return List<AreaModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-23 下午12:17:00
     */
    public List<AreaModel> getCity(String pid) {
        return this.selectByField("selectCity", pid);
    }
}
