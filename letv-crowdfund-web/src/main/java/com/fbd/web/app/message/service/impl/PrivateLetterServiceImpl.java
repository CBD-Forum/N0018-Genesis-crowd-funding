package com.fbd.web.app.message.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fbd.core.app.message.dao.IPrivateLetterDao;
import com.fbd.core.app.message.model.PrivateLetterModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;
import com.fbd.web.app.message.service.IPrivateLetterService;
@Service
public class PrivateLetterServiceImpl implements IPrivateLetterService {

	@Resource
	private IPrivateLetterDao privateLetterDao;

	/**
     * Description: 保存私信
     *
     * @param 
     * @Author 武文斌
     */
	public void savePrivateLetter(PrivateLetterModel model) {
		model.setSendDate(new Date());
		model.setId(PKGenarator.getId());
		privateLetterDao.savePrivateLetter(model);
	}

	/**
     * Description: 分页查询私信
     *
     * @param 
     * @return SearchResult<PrivateLetterModel>
     * @throws 
     * @Author wuwenbin
     */
	public SearchResult<Map<String,Object>> getPageList(PrivateLetterModel model) {
		 SearchResult<Map<String,Object>> result = new SearchResult<Map<String,Object>>();
	        result.setRows(privateLetterDao.getPageList(model));
	        result.setTotal(privateLetterDao.getPageCount(model));
	        return result;
	}
	
	
}
