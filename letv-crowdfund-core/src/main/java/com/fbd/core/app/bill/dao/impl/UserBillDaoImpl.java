/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserBillDaoImpl.java 
 *
 * Created: [2014-12-22 下午3:31:43] by haolingfeng
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

import com.fbd.core.app.bill.dao.IUserBillDao;
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
@Repository("userBillDao")
public class UserBillDaoImpl extends BaseDaoImpl<UserBillModel> implements
        IUserBillDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserBillDaoImpl.class);
	

	/**
	 * 保存之前先查询用户账单表是否已存在seqnum的记录
	 */
	public synchronized int save(UserBillModel userBill) {
//		UserBillModel lastUserBill = selectByUserId(userBill.getUserId());
//		if(null != lastUserBill){
//			logger.debug("-----------lastUserBill seqNum:" + lastUserBill.getSeqNum() + " userBill seqNum:" + userBill.getSeqNum());
//			if(lastUserBill.getSeqNum() >= userBill.getSeqNum()){
//				userBill.setSeqNum(lastUserBill.getSeqNum() + 1);
//				logger.debug("-----------最后一条记录大于要插入的记录，要插入的记录值+1,userBill seqNum：" + userBill.getSeqNum());
//			}
//		} else {
//			logger.debug("-----------lastUserBill is null");
//		}
		return super.save(userBill);
	}

    /**
     * 
     * Description: 根据用户id查询序号最大的用户账单
     * 
     * @param
     * @return UserBillModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-22 下午4:01:39
     */
    public UserBillModel selectByUserId(String userId) {
        this.sqlSession.clearCache();
        return this.selectOneByField("selectByUserId", userId);
    }

    /**
     * 
     * Description: 账单列表
     * 
     * @param
     * @return List<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public List<UserBillModel> getList(UserBillModel model) {
        return this.selectByModel("selectList", model);
    }
    public List<Map<String, Object>> getList(Map<String, Object> paramMap) {
    	return this.sqlSession.selectList("selectList", paramMap);
    }

    /**
     * 
     * Description: 账单列表条数
     * 
     * @param
     * @return List<UserBillModel>
     * @throws
     * @Author haolingfeng Create Date: 2015-1-8 下午12:09:35
     */
    public long getListCount(UserBillModel model) {
        return this.getCount("selectCount", model);
    }
    
    /**
     * 查询用户资金信息
     * Description: 
     * @param 
     * @return Map<String,Object>
     * @throws 
     */
    public Map<String,Object>selectUserCapitalInfo(String userId){
        return this.selectOneMapByField("selectUserCapitalInfo",userId);
    }

    /**
     * 
     * Description: 查询用户交易明细
     * 
     * @param
     * @return UserBillModel
     * @throws
     */
    public UserBillModel getUserBillDetail(UserBillModel model) {
        return this.selectOneByField("selectUserBillDetail", model);
    }

}
