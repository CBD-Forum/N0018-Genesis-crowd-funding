package com.fbd.admin.app.dictionary.service;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.common.model.SearchResult;

public interface IDictionaryService {

    SearchResult<DictionaryModel> getDictionaryPage(
            DictionaryModel dictionaryModel);

    public List<DictionaryModel> getDictionaryList(
            DictionaryModel dictionaryModel);

    /**
     * Description: 根据条件获取查询到的总条数
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-17 下午4:55:17
     */
    public long getDictionaryCount(DictionaryModel dictionaryModel);

    /**
     * Description: 添加字典实体
     * 
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:37:02
     */
    @PreAuthorize("hasPermission(null, 'security.operation.system_dictionary_add')")
    public int saveDictionary(DictionaryModel dictionaryModel);

    /**
     * Description: 修改字典实体
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:45:00
     */
    @PreAuthorize("hasPermission(null, 'security.operation.system_dictionary_modify')")
    public int modifyDictionary(DictionaryModel dictionaryModel);

    /**
     * Description: 删除字典
     * 
     * @param
     * @return int
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:50:59
     */
    @PreAuthorize("hasPermission(null, 'security.operation.system_dictionary_delete')")
    public int removeDictionary(String id);

    /**
     * 
     * Description: 根据类型查询
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午2:59:20
     */
    public List<DictionaryModel> selectByType(String type);

    /**
     * 
     * Description: 根据pid查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author haolingfeng Create Date: 2014-12-15 下午3:02:47
     */
    public List<DictionaryModel> selectByPid(String pid);

    List<DictionaryModel> getAllDicType();
}
