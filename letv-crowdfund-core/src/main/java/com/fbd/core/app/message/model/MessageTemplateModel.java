/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: MessageTemplate.java 
 *
 * Created: [2014-12-15 下午6:25:36] by haolingfeng
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
 * ProjectName: fbd-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.message.model;

import com.fbd.core.base.BaseModel;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class MessageTemplateModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = -869151669579915669L;

    private String id;

    private String tplName;

    private String tplCode;

    private String tplType;

    private String tplDesprition;

    private String tplStatus;

    private String tplContent;
    
    //节点名称
    private String nodeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTplName() {
        return tplName;
    }

    public void setTplName(String tplName) {
        this.tplName = tplName == null ? null : tplName.trim();
    }

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode == null ? null : tplCode.trim();
    }

    public String getTplType() {
        return tplType;
    }

    public void setTplType(String tplType) {
        this.tplType = tplType == null ? null : tplType.trim();
    }

    public String getTplDesprition() {
        return tplDesprition;
    }

    public void setTplDesprition(String tplDesprition) {
        this.tplDesprition = tplDesprition == null ? null : tplDesprition
                .trim();
    }

    public String getTplStatus() {
        return tplStatus;
    }

    public void setTplStatus(String tplStatus) {
        this.tplStatus = tplStatus == null ? null : tplStatus.trim();
    }

    public String getTplContent() {
        return tplContent;
    }

    public void setTplContent(String tplContent) {
        this.tplContent = tplContent == null ? null : tplContent.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
