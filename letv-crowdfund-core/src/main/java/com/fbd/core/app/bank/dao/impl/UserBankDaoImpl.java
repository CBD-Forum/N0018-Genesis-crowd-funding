/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserBankDaoImpl.java 
 *
 * Created: [2015-1-16 下午3:28:58] by haolingfeng
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

package com.fbd.core.app.bank.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.base.BaseDaoImpl;

/** 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户银行
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Repository("userBankDao")
public class UserBankDaoImpl extends BaseDaoImpl<UserBankModel> implements IUserBankDao{

    public List<UserBankModel> getList(UserBankModel model){
        return super.selectByModel("selectList",model);
    }

	public int updateUserBankState(String userId, String state) {
		UserBankModel model = new UserBankModel();
		model.setUserId(userId);
		model.setState(state);
		return this.update("updateUserBankState", model);
	}

	/**
     * 
     * Description:通过卡号查询银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    public UserBankModel getBnakByBankAccount(String bankAccount) {
        return this.selectOneByField("selectBnakByBankAccount", bankAccount);
    }

    /**
     * Description: 通过卡号 和状态查询银行卡
     *
     * @param 
     * @return UserBankModel
     * @throws 
     * @Author wuwenbin
     * Create Date: 2016-9-2 下午2:29:09
     */
    public UserBankModel getBnakByBankAccountAndState(String bankAccount,
            String state) {
        return this.selectOneByField("selectBnakByBankAccountAndState", bankAccount);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.bank.dao.IUserBankDao#getBnakByUserAndBankAccount(java.lang.String, java.lang.String)
     */
    @Override
    public List<UserBankModel> getBnakByUserAndBankAccount(String userId,
            String bankAccount) {
        // TODO Auto-generated method stub
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("userId", userId);
        param.put("bankAccount", bankAccount);
        return this.selectByField("getBnakByUserAndBankAccount", param);
    }
}
