package com.fbd.core.app.bank.dao;

import java.util.List;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.base.BaseDao;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 用户银行
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public interface IUserBankDao extends BaseDao<UserBankModel>{
    public List<UserBankModel> getList(UserBankModel model);

    /**
     * 根据userid更新银行卡状态
     * @param operator
     * @param state
     */
	public int updateUserBankState(String userId, String state);

	/**
     * 
     * Description:通过卡号查询银行卡
     *
     * @param 
     * @return void
     * @throws 
     * @Author wuwenbin<br/>
     */
    public UserBankModel getBnakByBankAccount(String bankAccount);
    /**
     * 
     * Description: 通过用户和银行卡号查询过
     *
     * @param 
     * @return UserBankModel
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-10-12 下午6:10:44
     */
    public List<UserBankModel> getBnakByUserAndBankAccount(String userId,String bankAccount);

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
            String state);

}