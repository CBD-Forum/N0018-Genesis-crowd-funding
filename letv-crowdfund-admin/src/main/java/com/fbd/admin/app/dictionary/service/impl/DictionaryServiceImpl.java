package com.fbd.admin.app.dictionary.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fbd.admin.app.dictionary.service.IDictionaryService;
import com.fbd.admin.common.mail.Log;
import com.fbd.core.app.dictionary.dao.IDictionaryDao;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
@Transactional
public class DictionaryServiceImpl implements IDictionaryService {

    @Resource
    public IDictionaryDao dictionaryDao;
    
//    @Resource
//    private StdScheduler scheduler;

    /**
     * Description: 根据条件获取查询到的总条数
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午4:55:17
     */
    public long getDictionaryCount(DictionaryModel dictionaryModel) {
        return dictionaryDao.getDictionaryCount(dictionaryModel);
    }

    public List<DictionaryModel> getDictionaryList(
            DictionaryModel dictionaryModel) {
        return dictionaryDao.getDictionaryList(dictionaryModel);
    }

    public SearchResult<DictionaryModel> getDictionaryPage(
            DictionaryModel dictionaryModel) {
        return dictionaryDao.getDictionaryPage(dictionaryModel);
    }

    /**
     * Description: 添加字典实体
     * 
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:37:02
     */
    public int saveDictionary(DictionaryModel dictionaryModel) {
        DictionaryModel dictionary4Select = new DictionaryModel();
        dictionary4Select.setTypeCode(dictionaryModel.getTypeCode());
        dictionary4Select.setCode(dictionaryModel.getCode());
        List<DictionaryModel> list = dictionaryDao.getDictionaryList(dictionary4Select);
        if (list.size() > 0) {
            return -1;
        }
        dictionaryModel.setId(PKGenarator.getId());
        
        
        return dictionaryDao.saveDictionary(dictionaryModel);
    }

    /**
     * Description: 修改字典实体
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:45:00
     */
    @Log(name = "删除日历", model = "基础信息→节假日信息")
    public int modifyDictionary(DictionaryModel dictionaryModel) {
        return dictionaryDao.updateBySelective(dictionaryModel);
    }

    /**
     * Description: 删除字典
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:50:59
     */
    public int removeDictionary(String id) {
        return dictionaryDao.removeDictionary(id);
    }

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

    public List<DictionaryModel> getAllDicType() {
        return this.dictionaryDao.getAllDicType();
    }

}
