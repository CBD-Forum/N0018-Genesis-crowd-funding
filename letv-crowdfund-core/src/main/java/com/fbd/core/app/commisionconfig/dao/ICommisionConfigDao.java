package com.fbd.core.app.commisionconfig.dao;

import java.util.List;

import com.fbd.core.app.commisionconfig.model.CommisionConfigModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

public interface ICommisionConfigDao extends BaseDao<CommisionConfigModel>{
	/**
	 * 条件查询结果集
	 * @param commisionConfigModel
	 * @return
	 */
	SearchResult<CommisionConfigModel> getCommisionConfigPage(CommisionConfigModel commisionConfigModel);
	/**
	 * 列表
	 * @param commisionConfigModel
	 * @return
	 */
    List<CommisionConfigModel> getCommisionConfigList(CommisionConfigModel commisionConfigModel);

    long getCommisionConfigCount(CommisionConfigModel commisionConfigModel);
	/**
	 * 增加 一条 佣金比例记录
	 * @param commisionConfigModel
	 * @return
	 */
    int saveCommisionConfig(CommisionConfigModel commisionConfigModel);

    /**
	 * 修改 一条 佣金比例记录
	 * @param commisionConfigModel
	 * @return
	 */
    int modifyCommisionConfig(CommisionConfigModel commisionConfigModel);
    /**
	 * 删除 一条 佣金比例记录
	 * @param commisionConfigModel
	 * @return
	 */
    int removeCommisionConfig(String id);
}