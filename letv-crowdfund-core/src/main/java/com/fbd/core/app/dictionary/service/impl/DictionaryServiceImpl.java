/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: DictionaryServiceImpl.java 
 *
 * Created: [2014-12-15 下午2:57:07] by haolingfeng
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

package com.fbd.core.app.dictionary.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.dictionary.dao.IDictionaryDao;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.app.dictionary.service.DictionaryService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class DictionaryServiceImpl implements DictionaryService {
    private IDictionaryDao dictionaryDao;

    /**
     * 
     * Description: 根据类型查询
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午2:59:20
     */
    public List<DictionaryModel> select(String type) {
        return dictionaryDao.getDictionarysByType(type);
    }

    /**
     * 
     * Description: 根据类型查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:02:47
     */
    public DictionaryModel selectOne(String type) {
        List<DictionaryModel> aList = this.dictionaryDao.getDictionarysByType(type);
        if (aList.size() > 0) {
            return aList.get(0);
        } else {
            return null;
        }
    }
}
