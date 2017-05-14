/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserModel.java 
 *
 * Created: [2014-12-3 10:46:57] by haolingfeng
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
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.core.app.dictionary.dao;

import java.util.List;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.base.BaseDao;
import com.fbd.core.common.model.SearchResult;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 字典dao
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public interface IDictionaryDao extends BaseDao<DictionaryModel> {

    /**
     * 
     * Description: 根据type查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:18:34
     */
    public List<DictionaryModel> getDictionarysByType(String type);

    /**
     * 
     * Description: 根据pid查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:18:34
     */
    public List<DictionaryModel> getDictionarysByPid(String pid);

    /**
     * Description: 根据dictionaryModel实体查询字典列表
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 上午11:34:31
     */
    public List<DictionaryModel> getDictionaryList(
            DictionaryModel dictionaryModel);

    /**
     * Description: 添加字典实体
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:41:23
     */
    public int saveDictionary(DictionaryModel dictionaryModel);

    /**
     * Description: 删除字典实体
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:52:07
     */
    public int removeDictionary(String id);

    /**
     * Description: 根据条件获取查询到的总条数
     * 
     * @param
     * @return long
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午4:55:17
     */
    public long getDictionaryCount(DictionaryModel dictionaryModel);

    public SearchResult<DictionaryModel> getDictionaryPage(
            DictionaryModel dictionaryModel);

    public List<DictionaryModel> getAllDicType();

    /**
     * Description: 根据类型和编码查询
     *
     * @param 
     * @return List<DictionaryModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-5-23 下午8:20:51
     */
    public List<DictionaryModel> selectByTypeAndCode(DictionaryModel dictionary);
}