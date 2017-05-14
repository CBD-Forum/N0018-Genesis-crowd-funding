package com.fbd.web.app.dictionary.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fbd.core.app.dictionary.model.DictionaryModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;
import com.fbd.web.app.dictionary.service.IDictionaryService;

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
     * Description: 根据类型编码查询
     * 
     * @param
     * @return DictionaryModel
     * @throws
     * @Author dongzhongwei Create Date: 2014-12-29 上午11:51:21
     */
    @RequestMapping(value = "/getByType.html", method = RequestMethod.POST)
    public @ResponseBody
    List<DictionaryModel> getDictionaryByType(String typeCode) {
        List<DictionaryModel> list = dictionaryService.selectByType(typeCode);
        return list;
    }
}
