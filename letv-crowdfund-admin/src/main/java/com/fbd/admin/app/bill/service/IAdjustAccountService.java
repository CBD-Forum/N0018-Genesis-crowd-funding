/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IAdjustAccountService.java 
 *
 * Created: [2015-3-13 上午10:04:15] by haolingfeng
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
 * ProjectName: fbd-admin 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.admin.app.bill.service;

import java.util.Map;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.common.model.SearchResult;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:调账 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IAdjustAccountService {
    
    /**
     * 
     * Description: 分页查询调账申请
     *
     * @param 
     * @return SearchResult<AdjustAccountModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-13 上午11:08:21
     */
    public SearchResult<Map<String, Object>> getPageList(Map<String, Object> param);
    
    /**
     * 
     * Description:调整申请提交 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-13 上午10:06:51
     */
    public void addAdjustAccountApply(String operator,AdjustAccountModel model);
    
    /**
     * 
     * Description:审核调账申请 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-13 上午10:18:06
     */
    public void saveAdjustAudit(AdjustAccountModel model);

}
