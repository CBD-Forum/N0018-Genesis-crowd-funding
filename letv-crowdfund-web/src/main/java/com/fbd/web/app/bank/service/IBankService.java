/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IBankService.java 
 *
 * Created: [2015-6-28 下午4:21:52] by haolingfeng
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
 * ProjectName: crowdfund-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.bank.service;

import java.util.List;
import com.fbd.core.app.bank.model.UserBankModel;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description: 银行卡
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public interface IBankService {
    /**
     * 
     * Description:保存用户银行信息 
     *
     * @param 
     * @return void
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-1-16 下午3:35:34
     */
    public void saveUserBank(UserBankModel userBank);
    
    /**
     * 
     * Description:查询银行卡列表 
     *
     * @param 
     * @return List<UserBankModel>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-6-28 下午4:46:02
     */
    public List<UserBankModel> getUserBank(String userId);
    
    public void delUserBank(UserBankModel userBank);

}
