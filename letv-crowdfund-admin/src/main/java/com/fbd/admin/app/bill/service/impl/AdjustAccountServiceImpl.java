/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: AdjustAccountServiceImpl.java 
 *
 * Created: [2015-3-13 上午10:04:35] by haolingfeng
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

package com.fbd.admin.app.bill.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.bill.service.IAdjustAccountService;
import com.fbd.core.app.bill.dao.IAdjustAccountDao;
import com.fbd.core.app.bill.model.AdjustAccountModel;
import com.fbd.core.app.bill.service.IUserBillService;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.core.util.DateUtil;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 调账
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("adjustAccountService")
public class AdjustAccountServiceImpl implements IAdjustAccountService{
    @Resource
    private IAdjustAccountDao adjustAccountDao;
    @Resource
    private IUserBillService userBillService;

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
    public void addAdjustAccountApply(String operator,AdjustAccountModel model){
        model.setId(PKGenarator.getId());
        model.setOrderId(PKGenarator.getOrderId());
        model.setOperator(operator);
        model.setApplyTime(new Date());
        model.setAuditState(FbdCoreConstants.adjustAccountState.auditing);
        adjustAccountDao.save(model);
    }
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
    public void saveAdjustAudit(AdjustAccountModel model){
        AdjustAccountModel adjustModel = this.adjustAccountDao.selectByPrimaryKey(model.getId());
        String state = model.getAuditState();
        if(state!=null && state.equals(FbdCoreConstants.adjustAccountState.passed)){
            //插入调账账单
            userBillService.addAdjustAccountUserBill(adjustModel);
        }
        adjustModel.setAuditor(model.getAuditor());
        adjustModel.setAuditTime(new Date());
        adjustModel.setAuditState(model.getAuditState());
        adjustModel.setAuditOpinion(model.getAuditOpinion());
        this.adjustAccountDao.update(adjustModel);
    }
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
    public SearchResult<Map<String, Object>> getPageList(Map<String, Object> param){
        return this.adjustAccountDao.getPage("selectListCount", "selectList", param);
    }
}
