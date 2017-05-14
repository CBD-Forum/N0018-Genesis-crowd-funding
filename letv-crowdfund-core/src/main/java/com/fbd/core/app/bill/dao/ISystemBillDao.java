/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: ISystemBillDao.java 
 *
 * Created: [2014-12-22 下午3:31:27] by haolingfeng
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
import com.fbd.core.app.bill.model.SystemBillModel;
import com.fbd.core.base.BaseDao;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:平台账单
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface ISystemBillDao extends BaseDao<SystemBillModel> {
    /**
     * 
     * Description: 根据用户id查询最新的那条账单
     * 
     * @param
     * @return SystemBillModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午4:49:18
     */
    public SystemBillModel selectByUserId(String userId);
    
    /**
     * 
     * Description: 账单列表
     * 
     * @param
     * @return List<SystemBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public List<SystemBillModel> getList(SystemBillModel model);
    
    /**
     * 
     * Description: 账单列表条数
     * 
     * @param
     * @return List<SystemBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public long getListCount(SystemBillModel model);
    
    /**
     * 
     * Description: 汇总平台账户数据
     *
     * @param 
     * @return Map<String,Object>
     * @throws 
     * @Author haolingfeng
     * Create Date: 2015-3-14 下午6:12:11
     */
    public Map<String,Object> getTotalData();
    /**
     * 查询账户可查询余额
     * Description: 
     *
     * @param 
     * @return long
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016年10月22日 上午10:51:03
     */
    public  Map<String,Object> getWithDrawBalance();
}
