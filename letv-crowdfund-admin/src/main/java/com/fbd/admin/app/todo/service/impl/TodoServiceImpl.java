package com.fbd.admin.app.todo.service.impl;

import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fbd.admin.app.todo.service.ITodoService;
import com.fbd.core.app.todo.dao.ITodoDao;
import com.fbd.core.app.todo.model.TodoModel;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.common.model.SearchResult;
import com.fbd.core.helper.PKGenarator;

@Service
public class TodoServiceImpl implements ITodoService {

    @Resource
    private ITodoDao todoDao;

    /**
     * Description: 查询待办事项
     *
     * @param 
     * @return SearchResult<TodoModel>
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-11 下午8:10:35
     */
    public SearchResult<Map<String, Object>> getTodoPage(TodoModel todo) {
        SearchResult<Map<String, Object>> result = new SearchResult<Map<String,Object>>();
        todo.setPosts(todo.getPost().split(","));
        result.setTotal(todoDao.getCount(todo));
        result.setRows(todoDao.selectMapByFields("select", todo));
        return result;
    }

    /**
     * Description: 保存待办事项
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-11 下午8:12:57
     */
    public int saveTodo(String event,String eventObj,String detail,String link,String post) {
/*        TodoModel todo = new TodoModel();
        todo.setId(PKGenarator.getId());
        todo.setEvent(event);
        todo.setEventObj(eventObj);
        todo.setDetail(detail);
        todo.setLink(link);
        todo.setPost(post);
        todo.setCreateTime(new Date());
        todo.setStatus(FbdCoreConstants.todoStatus.untodo);
        return todoDao.save(todo);*/
    	return todoDao.saveTodo(event, eventObj, detail, link, post);
    }

    /**
     * Description: 根据事件对象修改待办事项
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-12 上午11:11:21
     */
    public int modifyTodoByEventObj(String event, String eventObj,String manager,String manageResult) {
/*        TodoModel todo = new TodoModel();
        todo.setEvent(event);
        todo.setEventObj(eventObj);
        todo.setManager(manager);
        todo.setManageResult(manageResult);
        todo.setStatus(FbdCoreConstants.todoStatus.todoed);
        todo.setManageTime(new Date());
        return todoDao.update("updateByEventObj", todo);*/
    	return todoDao.modifyTodoByEventObj(event, eventObj, manager, manageResult);
    }
}
