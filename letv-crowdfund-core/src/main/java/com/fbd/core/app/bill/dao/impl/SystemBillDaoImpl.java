/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: SystemBillDaoImpl.java 
 *
 * Created: [2014-12-22 下午3:31:58] by haolingfeng
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

package com.fbd.core.app.bill.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.bill.dao.ISystemBillDao;
import com.fbd.core.app.bill.model.SystemBillModel;
import com.fbd.core.app.bill.model.UserBillModel;
import com.fbd.core.base.BaseDaoImpl;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("systemBillDao")
public class SystemBillDaoImpl extends BaseDaoImpl<SystemBillModel> implements
        ISystemBillDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemBillDaoImpl.class);
	
	/**
	 * 保存之前先查询系统账单表是否已存在seqnum的记录
	 */
	public synchronized int save(SystemBillModel systemBill) {
		SystemBillModel lastSystemBill = selectByUserId(systemBill.getUserId());
		if(null != lastSystemBill){
			logger.debug("-----------lastSystemBill seqNum:" + lastSystemBill.getSeqNum() + " systemBill seqNum:" + systemBill.getSeqNum());
			if(lastSystemBill.getSeqNum() >= systemBill.getSeqNum()){
				systemBill.setSeqNum(lastSystemBill.getSeqNum() + 1);
				logger.debug("-----------最后一条记录大于要插入的记录，要插入的记录值+1,systemBill seqNum：" + systemBill.getSeqNum());
			}
		} else {
			logger.debug("-----------lastSystemBill is null");
		}
		return super.save(systemBill);
	}
	
	
    /**
     * 
     * Description: 根据用户id查询最新的那条账单
     * 
     * @param
     * @return SystemBillModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午4:49:18
     */
    public SystemBillModel selectByUserId(String userId) {
        return this.selectOneByField("selectByUserId", userId);
    }
    
    /**
     * 
     * Description: 账单列表
     * 
     * @param
     * @return List<SystemBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public List<SystemBillModel> getList(SystemBillModel model) {
        return this.selectByModel("selectList", model);
    }
    
    /**
     * 
     * Description: 账单列表条数
     * 
     * @param
     * @return List<SystemBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public long getListCount(SystemBillModel model) {
        return this.getCount("selectCount", model);
    }
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
    public Map<String,Object> getTotalData(){
        return this.selectOneMapByField("selectTotalData",null);
    }
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
    public  Map<String,Object>  getWithDrawBalance(){
        Map<String,Object> map = this.selectOneMapByField("getWithDrawBalance", null);
        return map;
    }
}
