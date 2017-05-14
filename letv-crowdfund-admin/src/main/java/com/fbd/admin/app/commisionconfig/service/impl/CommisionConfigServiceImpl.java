package com.fbd.admin.app.commisionconfig.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.admin.app.commisionconfig.service.ICommisionConfigService;
import com.fbd.core.app.commisionconfig.dao.ICommisionConfigDao;
import com.fbd.core.app.commisionconfig.model.CommisionConfigModel;
import com.fbd.core.common.model.SearchResult;

@Service
public class CommisionConfigServiceImpl implements ICommisionConfigService {

	@Resource
	private ICommisionConfigDao commisionConfigDao ;
	
	public SearchResult<CommisionConfigModel> getCommisionConfigPage(
			CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return commisionConfigDao.getCommisionConfigPage(commisionConfigModel);
	}

	public int saveCommisionConfig(CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return commisionConfigDao.saveCommisionConfig(commisionConfigModel);
	}

	public List<CommisionConfigModel> getCommisionConfigList(
			CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return commisionConfigDao.getCommisionConfigList(commisionConfigModel);
	}

	public int modifyCommisionConfig(CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return commisionConfigDao.modifyCommisionConfig(commisionConfigModel);
	}

	public int removeCommisionConfig(String id) {
		// TODO Auto-generated method stub
		return commisionConfigDao.removeCommisionConfig(id);
	}

	public CommisionConfigModel getCommisionConfigById(String id) {
		return commisionConfigDao.selectByPrimaryKey(id);
	}

    
     

}
