/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: CrowdfundPhotoDaoImpl.java 
 *
 * Created: [2015-5-19 下午12:02:01] by haolingfeng
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

package com.fbd.core.app.crowdfunding.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundPhotoDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundPhotoModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:众筹图片 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("crowdfundPhotoDao")
public class CrowdfundPhotoDaoImpl extends BaseDaoImpl<CrowdfundPhotoModel>
implements ICrowdfundPhotoDao{
    
    public void deleteByLoanNo(String loanNo){
        this.deleteByField("deleteByLoanNo",loanNo);
    }
    
    public List<CrowdfundPhotoModel> getByLoanNo(String loanNo){
        return this.selectByField("selectByLoanNo", loanNo);
    }

}
