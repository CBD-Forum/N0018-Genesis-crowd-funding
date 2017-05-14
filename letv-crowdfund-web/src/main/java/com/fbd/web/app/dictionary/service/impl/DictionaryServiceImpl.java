/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: DictionaryServiceImpl.java 
 *
 * Created: [2014-12-22 下午1:07:18] by haolingfeng
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
 * ProjectName: fbd-web 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.web.app.dictionary.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.core.app.dictionary.dao.IDictionaryDao;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.web.app.dictionary.service.IDictionaryService;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {
    @Resource
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
    public List<DictionaryModel> selectByType(String type) {
        return dictionaryDao.getDictionarysByType(type);
    }

    /**
     * 
     * Description: 根据pid查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:02:47
     */
    public List<DictionaryModel> selectByPid(String pid) {
        return this.dictionaryDao.getDictionarysByPid(pid);
    }

    public List<DictionaryModel> getDictionaryList(
            DictionaryModel dictionaryModel) {
        return dictionaryDao.getDictionaryList(dictionaryModel);
    }
}
