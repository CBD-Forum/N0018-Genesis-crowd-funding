/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundingOperateDataDaoImpl.java 
 *
 * Created: [2016-8-12 下午2:59:53] by haolingfeng
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
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingOperateDataDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundingFounderModel;
import com.fbd.core.app.crowdfunding.model.CrowdfundingOperateDataModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository(value="crowdfundingOperateDataDao")
public class CrowdfundingOperateDataDaoImpl extends BaseDaoImpl<CrowdfundingOperateDataModel> implements
        ICrowdfundingOperateDataDao {
    
    
    public List<CrowdfundingOperateDataModel> selectList(CrowdfundingOperateDataModel model){
        return this.selectByModel("selectList",model);
    }
    
    
    public long selectCount(CrowdfundingOperateDataModel model){
        return this.getCount("selectCount", model);
    }
    
    
    public void updateloanNoByLoanNo(CrowdfundingOperateDataModel model){
        this.update("updateloanNoByLoanNo",model);
    }
 
}
