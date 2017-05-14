/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: IUserBillDao.java 
 *
 * Created: [2014-12-22 下午3:31:12] by haolingfeng
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

package com.fbd.core.app.bill.dao;

import java.util.List;
import java.util.Map;

import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.base.BaseDao;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:用账单
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface IUserBillDao extends BaseDao<UserBillModel> {
    /**
     * 
     * Description: 根据用户id查询序号最大的用户账单
     * 
     * @param
     * @return UserBillModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午4:01:39
     */
    public UserBillModel selectByUserId(String userId);

    /**
     * 
     * Description: 账单列表
     * 
     * @param
     * @return List<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public List<UserBillModel> getList(UserBillModel model);
    public List<Map<String, Object>> getList(Map<String, Object> paramMap);

    /**
     * 
     * Description: 账单列表条数
     * 
     * @param
     * @return List<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public long getListCount(UserBillModel model);
    
    /**
     * 查询用户资金信息
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     */
    public Map<String,Object>selectUserCapitalInfo(String userId);

    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return UserBillModel
     * @throws
     */
    public UserBillModel getUserBillDetail(UserBillModel model);
}
