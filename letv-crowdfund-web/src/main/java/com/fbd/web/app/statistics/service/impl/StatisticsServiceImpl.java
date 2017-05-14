package com.fbd.web.app.statistics.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.config.dao.IBusinessConfigDao;
import com.fbd.core.app.crowdfunding.dao.ICrowdfundingDao;
import com.fbd.core.app.user.dao.IUserDao;
import com.fbd.web.app.statistics.service.IStatisticsService;

@Service(value="statisticsService")
public class StatisticsServiceImpl implements IStatisticsService {
	
	@Resource
	private ICrowdfundingDao crowdfundingDao;
	@Resource
	private IUserDao userDao;
	@Resource
	private IBusinessConfigDao businessConfigDao;
	

	@Override
	public Map<String, Object> getWebStatistics() {
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try{
			//查询进行中的项目数量
			int fundingLoanCount = (int) this.crowdfundingDao.getFundingLoanCount();
			//查询已完成的项目数量
			int successLoanCount = (int) this.crowdfundingDao.getSuccessLoanCount();
			//查询已经筹款的项目数量
			double succcessLoanAmt = this.crowdfundingDao.getSuccessLoanAmt();
			//查询网站注册人数
			int userCount = (int) this.userDao.getCountUser();
			
			resultMap.put("fundingLoanCount", fundingLoanCount);
			resultMap.put("successLoanCount", successLoanCount);
			resultMap.put("succcessLoanAmt", succcessLoanAmt);
			resultMap.put("userCount", userCount);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultMap;
	}

}
