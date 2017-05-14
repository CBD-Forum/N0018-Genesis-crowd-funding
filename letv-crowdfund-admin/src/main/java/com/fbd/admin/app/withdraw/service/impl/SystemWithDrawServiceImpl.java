package com.fbd.admin.app.withdraw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.admin.app.withdraw.service.ISystemWithDrawService;
import com.fbd.core.app.withdraw.dao.ISystemWithDrawDao;
import com.fbd.core.app.withdraw.model.SystemWithdrawModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
@Service
public class SystemWithDrawServiceImpl implements ISystemWithDrawService {
    @Resource
    private ISystemWithDrawDao systemWithDrawDao;
	public int saveSystemWithDraw(SystemWithdrawModel model) {
		model.setId(PKGenarator.getId());
		// TODO Auto-generated method stub
		return systemWithDrawDao.saveSystemWithDraw(model);
	}
	@Override
	public List<SystemWithdrawModel> getList(SystemWithdrawModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getListCounts(SystemWithdrawModel model) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SearchResult<Map<String, Object>> getSystemWithDrawPage(
			SystemWithdrawModel systemWithdrawModel) {
		// TODO Auto-generated method stub
		return systemWithDrawDao.getSystemWithDrawPage(systemWithdrawModel);
	}
	@Override
	public long updateById(SystemWithdrawModel model) {
		// TODO Auto-generated method stub
		return systemWithDrawDao.updateById(model);
	}
	@Override
	public SystemWithdrawModel selectByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return systemWithDrawDao.selectByOrderId(orderId);
	}

}
