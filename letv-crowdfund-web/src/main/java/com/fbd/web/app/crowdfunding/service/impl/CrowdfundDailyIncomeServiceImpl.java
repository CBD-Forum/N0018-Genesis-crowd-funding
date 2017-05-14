package com.fbd.web.app.crowdfunding.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundDailyIncomeDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundDailyIncomeModel;
import com.fbd.web.app.crowdfunding.service.ICrowdfundDailyIncomeService;


@Service(value="crowdfundDailyIncomeService")
public class CrowdfundDailyIncomeServiceImpl implements ICrowdfundDailyIncomeService{
    @Resource
    private ICrowdfundDailyIncomeDao crowdfundDailyIncomeDao;
	@Override
	public int save(CrowdfundDailyIncomeModel crowdfundDailyIncomeModel) {
		// TODO Auto-generated method stub
		return crowdfundDailyIncomeDao.save(crowdfundDailyIncomeModel);
	}

}
