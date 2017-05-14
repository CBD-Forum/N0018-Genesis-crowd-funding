package com.fbd.core.app.todo.dao;

import com.fbd.core.app.todo.model.TodoModel;
import com.fbd.core.base.BaseDao;

public interface ITodoDao extends BaseDao<TodoModel> {
    /**
     * 
     * Description:根据事件对象修改待办事项 
     *
     * @param 
     * @return int
     * @throws 
     * @Author haolingfeng
     * Create Date: 2016-9-26 下午4:43:35
     */
    public int modifyTodoByEventObj(String event, String eventObj,String manager,String manageResult);
    /**
     * Description: 保存待办事项
     *
     * @param 
     * @return int
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-11 下午8:12:57
     */
    public int saveTodo(String event,String eventObj,String detail,String link,String post);
}