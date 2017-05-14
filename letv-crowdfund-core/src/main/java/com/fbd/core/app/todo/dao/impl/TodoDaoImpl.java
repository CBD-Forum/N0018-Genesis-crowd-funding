package com.fbd.core.app.todo.dao.impl;

import java.util.Date;
import org.springframework.stereotype.Repository;
import com.fbd.core.app.todo.dao.ITodoDao;
import com.fbd.core.app.todo.model.TodoModel;
import com.fbd.core.base.BaseDaoImpl;
import com.fbd.core.common.FbdCoreConstants;
import com.fbd.core.helper.PKGenarator;

@Repository("todoDao")
public class TodoDaoImpl extends BaseDaoImpl<TodoModel> implements ITodoDao {

    /* (non-Javadoc)
     * @see com.fbd.core.app.todo.dao.ITodoDao#modifyTodoByEventObj(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public int modifyTodoByEventObj(String event, String eventObj,
            String manager, String manageResult) {
        TodoModel todo = new TodoModel();
        todo.setEvent(event);
        todo.setEventObj(eventObj);
        todo.setManager(manager);
        todo.setManageResult(manageResult);
        todo.setStatus(FbdCoreConstants.todoStatus.todoed);
        todo.setManageTime(new Date());
        return this.update("updateByEventObj", todo);
    }

    /* (non-Javadoc)
     * @see com.fbd.core.app.todo.dao.ITodoDao#saveTodo(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public int saveTodo(String event, String eventObj, String detail,
            String link, String post) {
        // TODO Auto-generated method stub
        TodoModel todo = new TodoModel();
        todo.setId(PKGenarator.getId());
        todo.setEvent(event);
        todo.setEventObj(eventObj);
        todo.setDetail(detail);
        todo.setLink(link);
        todo.setPost(post);
        todo.setCreateTime(new Date());
        todo.setStatus(FbdCoreConstants.todoStatus.untodo);
        return this.save(todo);
    }
    
}
