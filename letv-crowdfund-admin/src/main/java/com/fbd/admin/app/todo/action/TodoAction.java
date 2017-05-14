package com.fbd.admin.app.todo.action;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fbd.admin.app.todo.service.ITodoService;
import com.fbd.core.app.todo.model.TodoModel;
import com.fbd.core.base.BaseAction;
import com.fbd.core.common.model.SearchResult;

@Controller
@Scope("prototype")
@RequestMapping("/todo")
public class TodoAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 3061911270541978403L;
    
    @Resource
    private ITodoService todoService;
    
    /**
     * Description: 查询待办事项
     *
     * @param 
     * @return SearchResult<TodoModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-11 下午8:10:35
     */
    @RequestMapping(value = "/getlist.html", method = RequestMethod.POST)
    public @ResponseBody SearchResult<Map<String, Object>> getTodoPage(TodoModel todo){
        SearchResult<Map<String, Object>> todos = todoService.getTodoPage(todo);
        return todos;
    }

}
