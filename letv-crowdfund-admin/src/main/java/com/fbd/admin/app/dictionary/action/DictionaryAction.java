package com.fbd.admin.app.dictionary.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.dictionary.service.IDictionaryService;
import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.AuditLogConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.common.utils.AuditLogUtils;

@Controller
@Scope("prototype")
@RequestMapping("/dictionary")
public class DictionaryAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 8589435043618728187L;

    @Resource
    public IDictionaryService dictionaryService;

    /**
     * Description: 查询字典列表
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author dongzhongwei 
     * Create Date: 2014-12-16 下午12:32:50
     */
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<DictionaryModel> getDictionaryPage(DictionaryModel dictionaryModel,HttpServletRequest request){
        SearchResult<DictionaryModel> dictionaryList = dictionaryService.getDictionaryPage(dictionaryModel);
        return dictionaryList;
    }
    
    

    /**
     * Description: 根据类型查询字典
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:32:50
     */
    @RequestMapping(value = "/getDic.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<DictionaryModel> getDicByType(String type) {
        SearchResult<DictionaryModel> dictionaryList = new SearchResult<DictionaryModel>();
        List<DictionaryModel> rows = dictionaryService.selectByType(type);
        dictionaryList.setRows(rows);
        return dictionaryList;
    }

    /**
     * Description: 根据父id查询字典
     * 
     * @param
     * @return List<DictionaryModel>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:32:50
     */
    @RequestMapping(value = "/getChildDic.html", method = RequestMethod.POST)
    public @ResponseBody
    SearchResult<DictionaryModel> getChildDic(String pid) {
        SearchResult<DictionaryModel> dictionaryList = new SearchResult<DictionaryModel>();
        List<DictionaryModel> rows = dictionaryService.selectByPid(pid);
        dictionaryList.setRows(rows);
        return dictionaryList;
    }

    /**
     * Description: 保存字典
     * 
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:36:45
     */
    @RequestMapping(value = "/save.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveDictionary(DictionaryModel dictionaryModel) {
        int num = dictionaryService.saveDictionary(dictionaryModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_DICTIONAR, AuditLogConstants.TYPE_ADD, AuditLogConstants.RESULT_SUCCESS, "类型名称："+dictionaryModel.getTypeName()+",名称："+dictionaryModel.getDisplayName());
        } else {
            if (num ==-1) {
                resultMap.put(MSG, "已经存在相同的编码");
            }
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

    /**
     * Description: 
     *  
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:48:16
     */
    @RequestMapping(value = "/getById.html", method = RequestMethod.POST)
    public @ResponseBody DictionaryModel getDictionaryById(String id){
        DictionaryModel dictionaryModel = new DictionaryModel();
        dictionaryModel.setId(id);
        List<DictionaryModel> list = dictionaryService.getDictionaryList(dictionaryModel);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }
    
    /**
     * Description: 根据类型编码查询
     *
     * @param 
     * @return DictionaryModel
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2014-12-29 上午11:51:21
     */
    @RequestMapping(value = "/getByType.html", method = RequestMethod.POST)
    public @ResponseBody List<DictionaryModel> getDictionaryByType(String typeCode){
        DictionaryModel dictionaryModel = new DictionaryModel();
        dictionaryModel.setTypeCode(typeCode);
        List<DictionaryModel> list = dictionaryService.getDictionaryList(dictionaryModel);
        return list;
    }
    
    
    /**
     * Description: 修改字典提示
     * 
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:48:16
     */
    @RequestMapping(value = "/modify.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> modifyDictionary(DictionaryModel dictionaryModel) {
        int num = dictionaryService.modifyDictionary(dictionaryModel);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_DICTIONAR, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "类型名称："+dictionaryModel.getTypeName()+",名称："+dictionaryModel.getDisplayName());
        } else {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }

    /**
     * Description: 删除字典提示
     * 
     * @param
     * @return Map<String,Object>
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-16 下午12:48:16
     */
    @RequestMapping(value = "/remove.html", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> removeDictionary(String id) {
        DictionaryModel dictionaryModel = getDictionaryById(id);
        int num = dictionaryService.removeDictionary(id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (num == 1) {
            resultMap.put(SUCCESS, true);
            AuditLogUtils.log(AuditLogConstants.MODEL_DICTIONAR, AuditLogConstants.TYPE_MODIFY, AuditLogConstants.RESULT_SUCCESS, "类型名称："+dictionaryModel.getTypeName()+",名称："+dictionaryModel.getDisplayName());
        } else {
            resultMap.put(SUCCESS, false);
        }
        return resultMap;
    }
    
    
    @RequestMapping(value = "/getAllType.html", method = RequestMethod.POST)
    public @ResponseBody List<DictionaryModel> getAllDicType(){
        return this.dictionaryService.getAllDicType();
    }
    
    @RequestMapping(value = "{typeCode}.html", method = RequestMethod.POST)
    public @ResponseBody List<Map<String, Object>> getData4combobox(@PathVariable("typeCode") String typeCode){
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        List<DictionaryModel>  list = this.dictionaryService.selectByType(typeCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("value", "");
        map.put("text", "-=请选择=-");
        map.put("select", true);
        result.add(map);
        for (DictionaryModel dictionaryModel : list) {
            map = new HashMap<String, Object>();
            map.put("value", dictionaryModel.getCode());
            map.put("text", dictionaryModel.getDisplayName());
            result.add(map);
        }
        return result;
    }

}
