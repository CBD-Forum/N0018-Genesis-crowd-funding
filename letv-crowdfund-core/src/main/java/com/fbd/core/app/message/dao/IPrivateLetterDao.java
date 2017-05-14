package com.fbd.core.app.message.dao;

import java.util.List;
import java.util.Map;

import com.fbd.core.app.message.model.PrivateLetterModel;

public interface IPrivateLetterDao {

	/**
     * Description: 保存私信
     *
     * @param 
     * @Author 武文斌
     */
	void savePrivateLetter(PrivateLetterModel model);

	/**
     * 
     * Description:分页查询 
     *
     * @param 
     * @return List<PrivateLetterModel>
     * @throws 
     * @Author wuwenbin
     */
	List<Map<String,Object>> getPageList(PrivateLetterModel model);
	/**
     * 
     * Description: 分页总条数
     *
     * @param 
     * @return Long
     * @throws 
     * @Author wuwenbin
     */
	long getPageCount(PrivateLetterModel model);
	
}