/* 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: UserModel.java 
 *
 * Created: [2014-12-3 10:46:57] by haolingfeng
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
 * ProjectName: fbd 
 * 
 * Description: 
 * 
 * ==========================================================*/
package com.fbd.core.app.invitecode.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:邀请码类
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */
public class InviteCodeModel extends BaseModel{
    private String id;

    private String inviteType;

    private String businessId;

    private String inviteCode;

    private String inviteUser;

    private String beingInviteUser;

    private Date registerTime;
    
    private String invitorType;
    
    private Date createStartTime;
    
    private Date createEndTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInviteType() {
        return inviteType;
    }

    public void setInviteType(String inviteType) {
        this.inviteType = inviteType == null ? null : inviteType.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public String getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser == null ? null : inviteUser.trim();
    }

    public String getBeingInviteUser() {
        return beingInviteUser;
    }

    public void setBeingInviteUser(String beingInviteUser) {
        this.beingInviteUser = beingInviteUser == null ? null : beingInviteUser
                .trim();
    }
    

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getCreateStartTime() {
        return createStartTime;
    }

    public void setCreateStartTime(Date createStartTime) {
        this.createStartTime = createStartTime;
    }

    public Date getCreateEndTime() {
        return createEndTime;
    }

    public void setCreateEndTime(Date createEndTime) {
        this.createEndTime = createEndTime;
    }

    public String getInvitorType() {
        return invitorType;
    }

    public void setInvitorType(String invitorType) {
        this.invitorType = invitorType;
    }
    
}