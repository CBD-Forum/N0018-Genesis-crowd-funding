/* 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: BankServiceImpl.java 
 *
 * Created: [2015-6-28 下午4:22:10] by haolingfeng
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

package com.fbd.web.app.bank.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.bank.dao.IUserBankDao;
import com.fbd.core.app.bank.model.UserBankModel;
import com.fbd.core.app.bank.util.BankUtil;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.exception.ApplicationException;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.bank.service.IBankService;

/** 
 * Copyright (C) 2014-2015方便贷(北京)资产管理有限公司.
 * 
 * Description:银行卡 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
@Service("bankService")
public class BankServiceImpl implements IBankService{
    @Resource
    private IUserBankDao userBankDao;
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
    public void saveUserBank(UserBankModel userBank){
    	
    	List<UserBankModel> list=userBankDao.getBnakByUserAndBankAccount(userBank.getUserId(), userBank.getBankAccount());
    	if(list!=null&&list.size()>0){
    		throw new ApplicationException("该银行卡已存在");
    	}else{
    		if(BankUtil.checkBankCard(userBank.getBankAccount())){
                userBank.setId(PKGenarator.getId());
                userBank.setBankNo(PKGenarator.getOrderId());
                userBank.setCreateTime(new Date());
                userBank.setState(FbdCoreConstants.USER_BANK_STATE_BIND);
                this.userBankDao.save(userBank);		
    		}else{
    			throw new ApplicationException("请输入正确的银行卡号");
    		}
    	}
    	
    }
    
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
    public List<UserBankModel> getUserBank(String userId){
        UserBankModel q = new UserBankModel();
        q.setUserId(userId);
        return this.userBankDao.getList(q);
    }
    
    public void delUserBank(UserBankModel userBank) {
        List<UserBankModel> userBankList = getUserBank(userBank.getUserId());
        if(null == userBankList || userBankList.size() == 0){
            throw new ApplicationException("用户未绑卡");
        }
        boolean flag = false;
        for (UserBankModel b : userBankList) {
            if(b.getId().equals(userBank.getId())){
                flag = true;
                break;
            }
        }
        if(flag){
            this.userBankDao.deleteByPrimaryKey(userBank.getId());
        } else {
            throw new ApplicationException("用户未绑定此银行卡");
        }
    }
}
