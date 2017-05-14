package com.fbd.core.app.blockasyntran.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class BlockAsynTranModel extends BaseModel{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String tranId;

    private Date createTime;

    private Date updateTime;

    private String parentId;

    private String type;
    private String requestID;
    

    /**
     * 调用查询接口前的异步状态
     */
    private String status;
    /**
     * 调用查询接口前的异步文本
     */
    private String message;
    
    /**
     * 查询接口的异步状态
     */
    private String queryStatus;
    /**
     * 查询接口的异步文本
     */
    private String queryMessage;
    /**
     * 查询接口的请求码
     */
    private String queryRequestID;
    private String memo;
    
    /**
     * 请求数据
     */
    private String requestData;
    /**
     * 任务次数
     */
    private Integer taskCount;
    /**
     * 任务状态
     */
    private String taskState;
    /**
     * 任务文本
     */
    private String taskMsg;
    /**
     * 来源(正常:normal;任务:job;)
     */
    private String source;
    
    //数据库外start
    /**
     * 查询逻辑(
     *     用户开户
     *      userCreateAccount_notAsyn:没异步;userCreateAccount_errorAsyn:异步异常
     *     用户激活
     *      userActivationAccount_notAsyn:没异步;userActivationAccount_errorAsyn:异步异常
     *     用户信任
     *      userTrustAccount_notAsyn:没异步;userTrustAccount_errorAsyn:异步异常
     * )
     */
    private String queryLogic;
    //数据库外end
    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo the memo to set
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId == null ? null : tranId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime ;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime ;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the queryStatus
     */
    public String getQueryStatus() {
        return queryStatus;
    }

    /**
     * @param queryStatus the queryStatus to set
     */
    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus;
    }

    /**
     * @return the queryMessage
     */
    public String getQueryMessage() {
        return queryMessage;
    }

    /**
     * @param queryMessage the queryMessage to set
     */
    public void setQueryMessage(String queryMessage) {
        this.queryMessage = queryMessage;
    }
    /**
     * @return the requestID
     */
    public String getRequestID() {
        return requestID;
    }

    /**
     * @param requestID the requestID to set
     */
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    /**
     * @return the queryRequestID
     */
    public String getQueryRequestID() {
        return queryRequestID;
    }

    /**
     * @param queryRequestID the queryRequestID to set
     */
    public void setQueryRequestID(String queryRequestID) {
        this.queryRequestID = queryRequestID;
    }

    /**
     * @return the requestData
     */
    public String getRequestData() {
        return requestData;
    }

    /**
     * @param requestData the requestData to set
     */
    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    /**
     * @return the taskCount
     */
    public Integer getTaskCount() {
        return taskCount;
    }

    /**
     * @param taskCount the taskCount to set
     */
    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    /**
     * @return the taskState
     */
    public String getTaskState() {
        return taskState;
    }

    /**
     * @param taskState the taskState to set
     */
    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    /**
     * @return the taskMsg
     */
    public String getTaskMsg() {
        return taskMsg;
    }

    /**
     * @param taskMsg the taskMsg to set
     */
    public void setTaskMsg(String taskMsg) {
        this.taskMsg = taskMsg;
    }

    /**
     * @return the queryLogic
     */
    public String getQueryLogic() {
        return queryLogic;
    }

    /**
     * @param queryLogic the queryLogic to set
     */
    public void setQueryLogic(String queryLogic) {
        this.queryLogic = queryLogic;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
}