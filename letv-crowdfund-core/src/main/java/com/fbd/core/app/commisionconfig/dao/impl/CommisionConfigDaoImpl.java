package com.fbd.core.app.commisionconfig.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fbd.core.app.commisionconfig.dao.ICommisionConfigDao;
import com.fbd.core.app.commisionconfig.model.CommisionConfigModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

@Repository
public class CommisionConfigDaoImpl extends BaseDaoImpl<CommisionConfigModel>
		implements ICommisionConfigDao {

	private static Logger logger = LoggerFactory
			.getLogger(CommisionConfigDaoImpl.class);

	public SearchResult<CommisionConfigModel> getCommisionConfigPage(
			CommisionConfigModel commisionConfigModel) {
		SearchResult<CommisionConfigModel> searchResult = new SearchResult<CommisionConfigModel>();
        searchResult.setTotal(getCommisionConfigCount(commisionConfigModel));
        searchResult.setRows(getCommisionConfigList(commisionConfigModel));
		return searchResult;
	}

	public List<CommisionConfigModel> getCommisionConfigList(
			CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return this.selectByModel("select", commisionConfigModel);
	}

	public long getCommisionConfigCount(
			CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return this.getCount(commisionConfigModel);
	}

	public int saveCommisionConfig(CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return this.save(commisionConfigModel);
	}

	public int modifyCommisionConfig(CommisionConfigModel commisionConfigModel) {
		// TODO Auto-generated method stub
		return this.updateBySelective(commisionConfigModel);
	}

	public int removeCommisionConfig(String id) {
		// TODO Auto-generated method stub
		 return this.deleteBatchById(id);
	}


	 
}
