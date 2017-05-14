/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: PushDataLog.java 
 *
 * Created: [2016-8-24 下午6:05:07] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-crowdfund-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.push.model;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author haolingfeng
 * @version 1.0
 *
 */

public class PushDataLogModel {
       private String id;
       private String lastPushTime;
       private String pushTime;
       private String memo;
       private String eventCode;
    /**
     * @return the eventCode
     */
    public String getEventCode() {
        return eventCode;
    }
    /**
     * @param eventCode the eventCode to set
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the lastPushTime
     */
    public String getLastPushTime() {
        return lastPushTime;
    }
    /**
     * @param lastPushTime the lastPushTime to set
     */
    public void setLastPushTime(String lastPushTime) {
        this.lastPushTime = lastPushTime;
    }
    /**
     * @return the pushTime
     */
    public String getPushTime() {
        return pushTime;
    }
    /**
     * @param pushTime the pushTime to set
     */
    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }
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
}
