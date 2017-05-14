package com.fbd.web.app.todo.service;

import java.util.Map;
import com.fbd.core.app.todo.model.TodoModel;
import com.fbd.core.common.model.SearchResult;

public interface ITodoService {

    /**
     * Description: 查询待办事项
     *
     * @param 
     * @return SearchResult<TodoModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-11 下午8:10:35
     */
    SearchResult<Map<String, Object>> getTodoPage(TodoModel todo);
    
    /**
     * Description: 保存待办事项
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-11 下午8:12:57
     */
    int saveTodo(String event,String eventObj,String detail,String link,String post);
    
    /**
     * Description: 根据事件对象修改待办事项
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-12 上午11:11:21
     */
    int modifyTodoByEventObj(String event,String eventObj,String manager);
    
}
