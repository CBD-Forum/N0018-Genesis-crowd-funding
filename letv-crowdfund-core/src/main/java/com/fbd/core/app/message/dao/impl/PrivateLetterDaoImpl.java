package com.fbd.core.app.message.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.message.dao.IPrivateLetterDao;
import com.fbd.core.app.message.model.PrivateLetterModel;
import com.fbd.core.base.BaseDaoImpl;

@Repository("privateLetterDao")
public class PrivateLetterDaoImpl extends BaseDaoImpl<PrivateLetterModel>
	implements IPrivateLetterDao {

	/**
     * Description: 保存私信
     *
     * @param 
     * @Author 武文斌
     */
	public void savePrivateLetter(PrivateLetterModel model) {
		// TODO Auto-generated method stub
		this.save(model);
	}

	/**
     * 
     * Description:分页查询 
     *
     * @param 
     * @return List<PrivateLetterModel>
     * @throws 
     * @Author wuwenbin
     */
	public List<Map<String,Object>> getPageList(PrivateLetterModel model) {
		return this.selectMapByFields("selectPrivateLetterList", model);
	}

	/**
     * 
     * Description: 分页总条数
     *
     * @param 
     * @return Long
     * @throws 
     * @Author wuwenbin
     */
	public long getPageCount(PrivateLetterModel model) {
		return this.getCount(model);
	}

}
