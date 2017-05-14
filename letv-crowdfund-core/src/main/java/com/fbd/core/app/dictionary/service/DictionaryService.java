/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: DictionaryService.java 
 *
 * Created: [2014-12-15 下午2:56:39] by haolingfeng
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

package com.fbd.core.app.dictionary.service;

import java.util.List;
import com.fbd.core.app.dictionary.model.DictionaryModel;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public interface DictionaryService {
    /**
     * 
     * Description: 根据类型查询
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午2:59:20
     */
    public List<DictionaryModel> select(String type);

    /**
     * 
     * Description: 根据类型查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:02:47
     */
    public DictionaryModel selectOne(String type);

}
