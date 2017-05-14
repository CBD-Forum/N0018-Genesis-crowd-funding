package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹支持
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingSupportModel extends BaseModel{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String orderId;

    private String supportUser;

    private Double supportAmt;

    private Date supportTime;
    
    private Date completeTime;

    private String loanNo;

    private String backNo;

    private String payState;

    private String state;

    private String postAddressNo;
    
    private String loanName;
    
    private Double hyFee;
    
    private Double p2pFee;
    
    private Date sendTime;
    
    private String sendOrderId;
    
    private String sendDelivery;
    
    private String orderIdLike;
    
    private String supportUserLike;
    
    private String supportUserName;
    
    private String investType;
    
    private String payWay;
    
    private String modifyUser;
    
    private Date modifyTime;
    
    private Integer buyNum;
    
    private Integer newBuyNum;
    
    //收货人姓名
    private String deliveryName;
    //收货联系人电话
    private String deliveryPhone;
    //收货地址
    private String deliveryAddress;
    
    
    /**
     * @return the newBuyNum
     */
    public Integer getNewBuyNum() {
        return newBuyNum;
    }

    /**
     * @param newBuyNum the newBuyNum to set
     */
    public void setNewBuyNum(Integer newBuyNum) {
        this.newBuyNum = newBuyNum;
    }

    private String supportWay;
    
    private String leadInvestor;
    
    private String payBank;
    
    private String loanType;
    
    private String buyType;
    
    private Double actualPayAmt;
    
    private String rewardNo;
    
    private String applyLeadDes;
    
    private Double transferAmt;
    
    private Double partMoney;
    
    private String supportClass;
    /**
     * 内容
     */
    private String content;
    
    //查询条件
    private String leadInvestorName;
    
    private String leadInvestorNickName;
    
    private String leadInvestorUser;
    
    
    private String userIp;
    
    
    //===========支付用到的卡号===========
    private String cardTop;//卡号前6位
    private String cardLast;//卡号后4位
    
    private String payResult;
    
    private Date startInvestTime;
    
    private Date endInvestTime;
    //0-未转让   1-已转让
    private String isTransfer;
    

    
 
    //==============合同相关信息===========
    //合同状态
    private String contractState;
    //合同ID
    private String contractId;
    //合同下载地址
    private String contractUrl;
    //投资方签署状态
    private String contractInvestSignState;
    //投资方签署时间
    private Date contractInvestSignTime;
    //项目方签署状态
    private String contractProjectSignState;
    //项目方签署时间
    private Date contractProjectSignTime;
    //合同创建时间
    private Date contractCreateTime;
    
    private String loanState;
    
    private String loanUser;
    
    private String mySupport;
    
    private String applicationContent;
    
    private String isApplicationDelivery;//是否已申请发货
    
    private Double intentionAmt; //意向金支付金额
    
    private String intentionNo; //意向金订单号
    
    private String intentionState; //意向金支付状态
    
    private String intentionFlag;   //意向金支付标识  0-不是意向金支付  1-意向金支付
    
    private Date intentionEndTime;  //意向金尾款支付截止时间
 
    private String remark;
    
    private Double transFee;//运费
    
    //数据库外 start
    private String custom;
    //数据库外 end

    private String deviceType;//支付设备类型
    
    private String refundStateType;//申请退款状态（查询条件）
    private String refundStateName;
    private String refundState;//退款状态
    private Date refuseApplyTime;  //退款申请时间
    private String refuseReason;  //项目方审核意见
    private Date refuseAuditTime;   //退款审核时间(平台)(已废弃，使用refuseComplateTime)
    private Date refuseLoanAuditTime; //退款项目方审核时间
    private Date refuseComplateTime;   //退款审核通过时间
    
    
    private String sendStateType;  //发货状态  sended:发货完成    lended:代发货
    private String prizeDrawFlag;  //是否抽奖
    private String refuseFailReason;  //退款失败原因(平台审核意见)
    
    //数据库外 start
    /**
     * 抽奖状态 
     *      0:不是中奖回报
     *      1:中奖
     */
    private String prizeDrawStatus;
    //数据库外 end
    
    
    public String searchState;  //查询状态条件 
   
    public String getId() {
        return id;
    }

    /**
     * @return the prizeDrawStatus
     */
    public String getPrizeDrawStatus() {
        return prizeDrawStatus;
    }

    /**
     * @param prizeDrawStatus the prizeDrawStatus to set
     */
    public void setPrizeDrawStatus(String prizeDrawStatus) {
        this.prizeDrawStatus = prizeDrawStatus;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getSupportUser() {
        return supportUser;
    }

    public void setSupportUser(String supportUser) {
        this.supportUser = supportUser == null ? null : supportUser.trim();
    }

    public Double getSupportAmt() {
        return supportAmt;
    }

    public void setSupportAmt(Double supportAmt) {
        this.supportAmt = supportAmt;
    }

    public Date getSupportTime() {
        return supportTime;
    }

    public void setSupportTime(Date supportTime) {
        this.supportTime = supportTime;
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public String getBackNo() {
        return backNo;
    }

    public void setBackNo(String backNo) {
        this.backNo = backNo == null ? null : backNo.trim();
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState == null ? null : payState.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getPostAddressNo() {
        return postAddressNo;
    }

    public void setPostAddressNo(String postAddressNo) {
        this.postAddressNo = postAddressNo == null ? null : postAddressNo.trim();
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Double getHyFee() {
        return hyFee;
    }

    public void setHyFee(Double hyFee) {
        this.hyFee = hyFee;
    }

    public Double getP2pFee() {
        return p2pFee;
    }

    public void setP2pFee(Double p2pFee) {
        this.p2pFee = p2pFee;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendOrderId() {
        return sendOrderId;
    }

    public void setSendOrderId(String sendOrderId) {
        this.sendOrderId = sendOrderId;
    }

    public String getSendDelivery() {
        return sendDelivery;
    }

    public void setSendDelivery(String sendDelivery) {
        this.sendDelivery = sendDelivery;
    }

    public String getOrderIdLike() {
        return orderIdLike;
    }

    public void setOrderIdLike(String orderIdLike) {
        this.orderIdLike = orderIdLike;
    }

    public String getSupportUserLike() {
        return supportUserLike;
    }

    public void setSupportUserLike(String supportUserLike) {
        this.supportUserLike = supportUserLike;
    }

    public String getSupportUserName() {
        return supportUserName;
    }

    public void setSupportUserName(String supportUserName) {
        this.supportUserName = supportUserName;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getSupportWay() {
        return supportWay;
    }

    public void setSupportWay(String supportWay) {
        this.supportWay = supportWay;
    }

    public String getLeadInvestor() {
        return leadInvestor;
    }

    public void setLeadInvestor(String leadInvestor) {
        this.leadInvestor = leadInvestor;
    }

    public String getPayBank() {
        return payBank;
    }

    public void setPayBank(String payBank) {
        this.payBank = payBank;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public Double getActualPayAmt() {
        return actualPayAmt;
    }

    public void setActualPayAmt(Double actualPayAmt) {
        this.actualPayAmt = actualPayAmt;
    }

    public String getRewardNo() {
        return rewardNo;
    }

    public void setRewardNo(String rewardNo) {
        this.rewardNo = rewardNo;
    }

    public String getLeadInvestorName() {
        return leadInvestorName;
    }

    public void setLeadInvestorName(String leadInvestorName) {
        this.leadInvestorName = leadInvestorName;
    }

    public String getApplyLeadDes() {
        return applyLeadDes;
    }

    public void setApplyLeadDes(String applyLeadDes) {
        this.applyLeadDes = applyLeadDes;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public Double getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(Double transferAmt) {
        this.transferAmt = transferAmt;
    }

    public Double getPartMoney() {
        return partMoney;
    }

    public void setPartMoney(Double partMoney) {
        this.partMoney = partMoney;
    }

    public String getSupportClass() {
        return supportClass;
    }

    public void setSupportClass(String supportClass) {
        this.supportClass = supportClass;
    }

    /**
     * @return the cardTop
     */
    public String getCardTop() {
        return cardTop;
    }

    /**
     * @param cardTop the cardTop to set
     */
    public void setCardTop(String cardTop) {
        this.cardTop = cardTop;
    }

    /**
     * @return the cardLast
     */
    public String getCardLast() {
        return cardLast;
    }

    /**
     * @param cardLast the cardLast to set
     */
    public void setCardLast(String cardLast) {
        this.cardLast = cardLast;
    }

    /**
     * @return the payResult
     */
    public String getPayResult() {
        return payResult;
    }

    /**
     * @param payResult the payResult to set
     */
    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }
 

    /**
     * @return the startInvestTime
     */
    public Date getStartInvestTime() {
        return startInvestTime;
    }

    /**
     * @param startInvestTime the startInvestTime to set
     */
    public void setStartInvestTime(Date startInvestTime) {
        this.startInvestTime = startInvestTime;
    }

    /**
     * @return the endInvestTime
     */
    public Date getEndInvestTime() {
        return endInvestTime;
    }

    /**
     * @param endInvestTime the endInvestTime to set
     */
    public void setEndInvestTime(Date endInvestTime) {
        this.endInvestTime = endInvestTime;
    }
 

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer;
    }

    public String getRefundState() {
        return refundState;
    }

    public void setRefundState(String refundState) {
        this.refundState = refundState;
    }
 
    /**
     * @return the contractState
     */
    public String getContractState() {
        return contractState;
    }

    /**
     * @param contractState the contractState to set
     */
    public void setContractState(String contractState) {
        this.contractState = contractState;
    }

    /**
     * @return the contractId
     */
    public String getContractId() {
        return contractId;
    }

    /**
     * @param contractId the contractId to set
     */
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    /**
     * @return the contractUrl
     */
    public String getContractUrl() {
        return contractUrl;
    }

    /**
     * @param contractUrl the contractUrl to set
     */
    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }
    
    
 

    /**
     * @return the refundStateName
     */
    public String getRefundStateName() {
        return refundStateName;
    }

    /**
     * @param refundStateName the refundStateName to set
     */
    public void setRefundStateName(String refundStateName) {
        this.refundStateName = refundStateName;
    }

    /**
     * @return the loanState
     */
    public String getLoanState() {
        return loanState;
    }

    /**
     * @param loanState the loanState to set
     */
    public void setLoanState(String loanState) {
        this.loanState = loanState;
    }

    /**
     * @return the loanUser
     */
    public String getLoanUser() {
        return loanUser;
    }

    /**
     * @param loanUser the loanUser to set
     */
    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser;
    }

    /**
     * @return the mySupport
     */
    public String getMySupport() {
        return mySupport;
    }

    /**
     * @param mySupport the mySupport to set
     */
    public void setMySupport(String mySupport) {
        this.mySupport = mySupport;
    }

    /**
     * @return the applicationContent
     */
    public String getApplicationContent() {
        return applicationContent;
    }

    /**
     * @param applicationContent the applicationContent to set
     */
    public void setApplicationContent(String applicationContent) {
        this.applicationContent = applicationContent;
    }

    /**
     * @return the isApplicationDelivery
     */
    public String getIsApplicationDelivery() {
        return isApplicationDelivery;
    }

    /**
     * @param isApplicationDelivery the isApplicationDelivery to set
     */
    public void setIsApplicationDelivery(String isApplicationDelivery) {
        this.isApplicationDelivery = isApplicationDelivery;
    }

    /**
     * @return the intentionAmt
     */
    public Double getIntentionAmt() {
        return intentionAmt;
    }

    /**
     * @param intentionAmt the intentionAmt to set
     */
    public void setIntentionAmt(Double intentionAmt) {
        this.intentionAmt = intentionAmt;
    }

    /**
     * @return the intentionNo
     */
    public String getIntentionNo() {
        return intentionNo;
    }

    /**
     * @param intentionNo the intentionNo to set
     */
    public void setIntentionNo(String intentionNo) {
        this.intentionNo = intentionNo;
    }

    /**
     * @return the intentionState
     */
    public String getIntentionState() {
        return intentionState;
    }

    /**
     * @param intentionState the intentionState to set
     */
    public void setIntentionState(String intentionState) {
        this.intentionState = intentionState;
    }
 

    /**
     * @return the contractInvestSignState
     */
    public String getContractInvestSignState() {
        return contractInvestSignState;
    }

    /**
     * @param contractInvestSignState the contractInvestSignState to set
     */
    public void setContractInvestSignState(String contractInvestSignState) {
        this.contractInvestSignState = contractInvestSignState;
    }

    /**
     * @return the contractProjectSignState
     */
    public String getContractProjectSignState() {
        return contractProjectSignState;
    }

    /**
     * @param contractProjectSignState the contractProjectSignState to set
     */
    public void setContractProjectSignState(String contractProjectSignState) {
        this.contractProjectSignState = contractProjectSignState;
    }
 
    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the transFee
     */
    public Double getTransFee() {
        return transFee = transFee==null?0.0:transFee;
    }

    /**
     * @param transFee the transFee to set
     */
    public void setTransFee(Double transFee) {
        this.transFee = transFee;
    }

    /**
     * @return the contractInvestSignTime
     */
    public Date getContractInvestSignTime() {
        return contractInvestSignTime;
    }

    /**
     * @param contractInvestSignTime the contractInvestSignTime to set
     */
    public void setContractInvestSignTime(Date contractInvestSignTime) {
        this.contractInvestSignTime = contractInvestSignTime;
    }

    /**
     * @return the contractProjectSignTime
     */
    public Date getContractProjectSignTime() {
        return contractProjectSignTime;
    }

    /**
     * @param contractProjectSignTime the contractProjectSignTime to set
     */
    public void setContractProjectSignTime(Date contractProjectSignTime) {
        this.contractProjectSignTime = contractProjectSignTime;
    }

    /**
     * @return the contractCreateTime
     */
    public Date getContractCreateTime() {
        return contractCreateTime;
    }

    /**
     * @param contractCreateTime the contractCreateTime to set
     */
    public void setContractCreateTime(Date contractCreateTime) {
        this.contractCreateTime = contractCreateTime;
    }

    /**
     * @return the deviceType
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * @param deviceType the deviceType to set
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the refundStateType
     */
    public String getRefundStateType() {
        return refundStateType;
    }

    /**
     * @param refundStateType the refundStateType to set
     */
    public void setRefundStateType(String refundStateType) {
        this.refundStateType = refundStateType;
    }

    /**
     * @return the refuseReason
     */
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * @param refuseReason the refuseReason to set
     */
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    /**
     * @return the refuseApplyTime
     */
    public Date getRefuseApplyTime() {
        return refuseApplyTime;
    }

    /**
     * @param refuseApplyTime the refuseApplyTime to set
     */
    public void setRefuseApplyTime(Date refuseApplyTime) {
        this.refuseApplyTime = refuseApplyTime;
    }

    /**
     * @return the refuseAuditTime
     */
    public Date getRefuseAuditTime() {
        return refuseAuditTime;
    }

    /**
     * @param refuseAuditTime the refuseAuditTime to set
     */
    public void setRefuseAuditTime(Date refuseAuditTime) {
        this.refuseAuditTime = refuseAuditTime;
    }

    /**
     * @return the refuseLoanAuditTime
     */
    public Date getRefuseLoanAuditTime() {
        return refuseLoanAuditTime;
    }

    /**
     * @param refuseLoanAuditTime the refuseLoanAuditTime to set
     */
    public void setRefuseLoanAuditTime(Date refuseLoanAuditTime) {
        this.refuseLoanAuditTime = refuseLoanAuditTime;
    }

    /**
     * @return the refuseComplateTime
     */
    public Date getRefuseComplateTime() {
        return refuseComplateTime;
    }

    /**
     * @param refuseComplateTime the refuseComplateTime to set
     */
    public void setRefuseComplateTime(Date refuseComplateTime) {
        this.refuseComplateTime = refuseComplateTime;
    }

    /**
     * @return the sendStateType
     */
    public String getSendStateType() {
        return sendStateType;
    }

    /**
     * @param sendStateType the sendStateType to set
     */
    public void setSendStateType(String sendStateType) {
        this.sendStateType = sendStateType;
    }

    /**
     * @return the prizeDrawFlag
     */
    public String getPrizeDrawFlag() {
        return prizeDrawFlag;
    }

    /**
     * @param prizeDrawFlag the prizeDrawFlag to set
     */
    public void setPrizeDrawFlag(String prizeDrawFlag) {
        this.prizeDrawFlag = prizeDrawFlag;
    }

    /**
     * @return the refuseFailReason
     */
    public String getRefuseFailReason() {
        return refuseFailReason;
    }

    /**
     * @param refuseFailReason the refuseFailReason to set
     */
    public void setRefuseFailReason(String refuseFailReason) {
        this.refuseFailReason = refuseFailReason;
    }

    /**
     * @return the searchState
     */
    public String getSearchState() {
        return searchState;
    }

    /**
     * @param searchState the searchState to set
     */
    public void setSearchState(String searchState) {
        this.searchState = searchState;
    }

    /**
     * @return the leadInvestorNickName
     */
    public String getLeadInvestorNickName() {
        return leadInvestorNickName;
    }

    /**
     * @param leadInvestorNickName the leadInvestorNickName to set
     */
    public void setLeadInvestorNickName(String leadInvestorNickName) {
        this.leadInvestorNickName = leadInvestorNickName;
    }

    /**
     * @return the leadInvestorUser
     */
    public String getLeadInvestorUser() {
        return leadInvestorUser;
    }

    /**
     * @param leadInvestorUser the leadInvestorUser to set
     */
    public void setLeadInvestorUser(String leadInvestorUser) {
        this.leadInvestorUser = leadInvestorUser;
    }

    /**
     * @return the intentionFlag
     */
    public String getIntentionFlag() {
        return intentionFlag;
    }

    /**
     * @param intentionFlag the intentionFlag to set
     */
    public void setIntentionFlag(String intentionFlag) {
        this.intentionFlag = intentionFlag;
    }

    /**
     * @return the intentionEndTime
     */
    public Date getIntentionEndTime() {
        return intentionEndTime;
    }

    /**
     * @param intentionEndTime the intentionEndTime to set
     */
    public void setIntentionEndTime(Date intentionEndTime) {
        this.intentionEndTime = intentionEndTime;
    }

    /**
     * @return the deliveryName
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * @param deliveryName the deliveryName to set
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    /**
     * @return the deliveryPhone
     */
    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    /**
     * @param deliveryPhone the deliveryPhone to set
     */
    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    /**
     * @return the deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * @param deliveryAddress the deliveryAddress to set
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    
    
    
}