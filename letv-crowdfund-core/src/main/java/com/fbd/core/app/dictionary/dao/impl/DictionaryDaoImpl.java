/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: DictionaryDaoImpl.java 
 *
 * Created: [2014-12-11 下午3:04:55] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.dictionary.dao.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.dictionary.dao.IDictionaryDao;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.model.SearchResult;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Repository("dictionaryDao")
public class DictionaryDaoImpl extends BaseDaoImpl<DictionaryModel> implements
        IDictionaryDao {
    Logger logger = LoggerFactory.getLogger(DictionaryDaoImpl.class);

    /**
     * 
     * Description: 根据type查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:18:34
     */
    public List<DictionaryModel> getDictionarysByType(String type) {
        return super.selectByField("selectByType", type);
    }

    /**
     * 
     * Description: 根据pid查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:18:34
     */
    public List<DictionaryModel> getDictionarysByPid(String pid) {
        return super.selectByField("selectByPid", pid);
    }

    /**
     * Description: 根据dictionaryModel实体查询字典列表
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 上午11:34:31
     */
    public List<DictionaryModel> getDictionaryList(
            DictionaryModel dictionaryModel) {
        return this.selectByModel("select", dictionaryModel);
    }

    /**
     * Description: 添加字典实体
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:41:23
     */
    public int saveDictionary(DictionaryModel dictionaryModel) {
        return this.save(dictionaryModel);
    }

    /**
     * Description: 删除字典实体
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:52:07
     */
    public int removeDictionary(String id) {
        return this.deleteBatchById(id);
    }

    /**
     * Description: 根据条件获取查询到的总条数
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午4:55:17
     */
    public long getDictionaryCount(DictionaryModel dictionaryModel) {
        return this.getCount(dictionaryModel);
    }

    public SearchResult<DictionaryModel> getDictionaryPage(
            DictionaryModel dictionaryModel) {
        SearchResult<DictionaryModel> searchResult = new SearchResult<DictionaryModel>();
        searchResult.setTotal(getDictionaryCount(dictionaryModel));
        searchResult.setRows(getDictionaryList(dictionaryModel));
        return searchResult;
    }

    public List<DictionaryModel> getAllDicType() {
        return this.select("getAllType");
    }

    /**
     * Description: 根据类型和编码查询
     *
     * @param 
     * @return List<DictionaryModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-5-23 下午8:20:51
     */
    public List<DictionaryModel> selectByTypeAndCode(DictionaryModel dictionary) {
        return selectByModel("selectByTypeAndCode", dictionary);
    }
}
