package com.fbd.core.app.crowdfunding.service.impl;

import java.util.Date;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.crowdfunding.dao.ICrowdfundTransferDao;
import com.fbd.core.app.crowdfunding.model.CrowdfundTransferModel;
import com.fbd.core.app.crowdfunding.service.ICrowdFundTransferJobService;
import com.fbd.core.common.CrowdfundCoreConstants;
import com.fbd.core.exception.ApplicationException;

@Service("crowdfundTransferJobService")
public class CrowdfundTransferJobServiceImpl implements
ICrowdFundTransferJobService {

	@Resource
	private ICrowdfundTransferDao crowdfundTransferDao;
	
	public void update(String transferNo) {
		// TODO Auto-generated method stub
	    CrowdfundTransferModel model = this.crowdfundTransferDao.selectModelByTransferNo(transferNo);
		model.setStatus(CrowdfundCoreConstants.transferStateFbd.transferend.getValue());
		model.setDeadline(new Date());
		try {
			crowdfundTransferDao.update(model);
		} catch (Exception e) {
			throw new ApplicationException("更新挂牌失败");
		}
		
	}

}
