package com.fbd.web.app.message.service;

import java.util.Map;
import com.fbd.core.app.message.model.PrivateLetterModel;
import com.fbd.core.common.model.SearchResult;

public interface IPrivateLetterService {

	/**
     * Description: 保存私信
     *
     * @param 
     * @Author 武文斌
     */
	void savePrivateLetter(PrivateLetterModel model);

	/**
     * Description: 分页查询私信
     *
     * @param 
     * @return SearchResult<PrivateLetterModel>
     * @throws 
     * @Author wuwenbin
     * Create Date: 2015-2-4 下午12:20:04
     */
	SearchResult<Map<String,Object>> getPageList(PrivateLetterModel model);

}
