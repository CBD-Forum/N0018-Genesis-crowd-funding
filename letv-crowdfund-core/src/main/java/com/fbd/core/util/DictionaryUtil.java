/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: DictionaryUtil.java 
 *
 * Created: [2014-12-13 下午5:39:55] by haolingfeng
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

package com.fbd.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fbd.core.app.dictionary.dao.IDictionaryDao;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.util.spring.SpringUtil;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class DictionaryUtil {
    private static IDictionaryDao dictionaryDao;

    static {
        dictionaryDao = (IDictionaryDao) SpringUtil.getBean("dictionaryDao");
    }

    /**
     * 
     * Description: 根据type与code查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:18:34
     */
    public static DictionaryModel select(String type, String code) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("typeCode", type);
        paramMap.put("code", code);
        List<DictionaryModel> reList = dictionaryDao.selectByFields(
                "selectByTypeAndCode", paramMap);
        if (reList.size() > 0) {
            return reList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 
     * Description: 根据type查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-11 下午3:18:34
     */
    public static List<DictionaryModel> select(String type) {
        return dictionaryDao.selectByField("selectByType", type);
    }

    /**
     * 
     * Description: 根据type查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-13 下午5:49:44
     */
    public static DictionaryModel selectOne(String type) {
        if (select(type).size() > 0) {
            return select(type).get(0);
        } else {
            return null;
        }
    }
}
