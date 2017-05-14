package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundProductTransferModel extends BaseModel{
    private String id;

    private String loanNo;

    private String supportNo;

    private String transferUser;

    private Double transferAmt;

    private String backNo;

    private Date transferTime;

    private Date transferEndTime;

    private String buyUser;

    private Date buyTime;

    private String transferState;
    
    private String transferNo;
    
    private String transferAuditState;//购买转让后台审核状态
    
    private String transferAuditStateIn;
    
    private Double transFee;
    /**
     * 转让手续费
     */
    private Double transferFee;
    /**
     * 购买后的支持编号
     */
    private String buySupportNo;
    
    /**
     * 审核意见
     */
    private String transferAuditOpinion;
    //数据库外 start
    /**
     * 地址编号
     */
    private String postAddressNo;
    /**
     * 支持备注
     */
    private String supportRemark;
    /**
     * 支持内容
     */
    private String supportContent;
    /**
     * 扩展表中id
     */
    private String transferExtendId;
    
    /**
     * 转让查询类型(
     *  等待:wait;
     *  成功:success;
     *  失败:fail;
     * )
     */
    private String transferQueryType;
    
    private String loanNoLike;
    private String loanNameLike;
    private String buyUserIdLike;
    private String transferUserLike;
    //数据库外 end
    
    
    public String getId() {
        return id;
    }
    
    /**
     * @return the transferAuditOpinion
     */
    public String getTransferAuditOpinion() {
        return transferAuditOpinion;
    }

    /**
     * @param transferAuditOpinion the transferAuditOpinion to set
     */
    public void setTransferAuditOpinion(String transferAuditOpinion) {
        this.transferAuditOpinion = transferAuditOpinion;
    }

    /**
     * @return the transferUserLike
     */
    public String getTransferUserLike() {
        return transferUserLike;
    }

    /**
     * @param transferUserLike the transferUserLike to set
     */
    public void setTransferUserLike(String transferUserLike) {
        this.transferUserLike = transferUserLike;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoanNo() {
        return loanNo;
    }

    public void setLoanNo(String loanNo) {
        this.loanNo = loanNo == null ? null : loanNo.trim();
    }

    public String getSupportNo() {
        return supportNo;
    }

    public void setSupportNo(String supportNo) {
        this.supportNo = supportNo == null ? null : supportNo.trim();
    }

    public String getTransferUser() {
        return transferUser;
    }

    public void setTransferUser(String transferUser) {
        this.transferUser = transferUser == null ? null : transferUser.trim();
    }

    public Double getTransferAmt() {
        return transferAmt;
    }

    public void setTransferAmt(Double transferAmt) {
        this.transferAmt = transferAmt;
    }

    public String getBackNo() {
        return backNo;
    }

    public void setBackNo(String backNo) {
        this.backNo = backNo == null ? null : backNo.trim();
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Date getTransferEndTime() {
        return transferEndTime;
    }

    public void setTransferEndTime(Date transferEndTime) {
        this.transferEndTime = transferEndTime;
    }

    public String getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(String buyUser) {
        this.buyUser = buyUser == null ? null : buyUser.trim();
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime ;
    }

    public String getTransferState() {
        return transferState;
    }

    public void setTransferState(String transferState) {
        this.transferState = transferState == null ? null : transferState.trim();
    }

    public String getTransferNo() {
        return transferNo;
    }

    public void setTransferNo(String transferNo) {
        this.transferNo = transferNo;
    }

    public String getTransferAuditState() {
        return transferAuditState;
    }

    public void setTransferAuditState(String transferAuditState) {
        this.transferAuditState = transferAuditState;
    }

    public String getTransferAuditStateIn() {
        return transferAuditStateIn;
    }

    public void setTransferAuditStateIn(String transferAuditStateIn) {
        this.transferAuditStateIn = transferAuditStateIn;
    }

    /**
     * @return the transFee
     */
    public Double getTransFee() {
        return transFee;
    }

    /**
     * @param transFee the transFee to set
     */
    public void setTransFee(Double transFee) {
        this.transFee = transFee;
    }

    /**
     * @return the postAddressNo
     */
    public String getPostAddressNo() {
        return postAddressNo;
    }

    /**
     * @param postAddressNo the postAddressNo to set
     */
    public void setPostAddressNo(String postAddressNo) {
        this.postAddressNo = postAddressNo;
    }

    /**
     * @return the supportRemark
     */
    public String getSupportRemark() {
        return supportRemark;
    }

    /**
     * @param supportRemark the supportRemark to set
     */
    public void setSupportRemark(String supportRemark) {
        this.supportRemark = supportRemark;
    }

    /**
     * @return the supportContent
     */
    public String getSupportContent() {
        return supportContent;
    }

    /**
     * @param supportContent the supportContent to set
     */
    public void setSupportContent(String supportContent) {
        this.supportContent = supportContent;
    }

    /**
     * @return the transferExtendId
     */
    public String getTransferExtendId() {
        return transferExtendId;
    }

    /**
     * @param transferExtendId the transferExtendId to set
     */
    public void setTransferExtendId(String transferExtendId) {
        this.transferExtendId = transferExtendId;
    }

    /**
     * @return the transferQueryType
     */
    public String getTransferQueryType() {
        return transferQueryType;
    }

    /**
     * @param transferQueryType the transferQueryType to set
     */
    public void setTransferQueryType(String transferQueryType) {
        this.transferQueryType = transferQueryType;
    }

    /**
     * @return the loanNoLike
     */
    public String getLoanNoLike() {
        return loanNoLike;
    }

    /**
     * @param loanNoLike the loanNoLike to set
     */
    public void setLoanNoLike(String loanNoLike) {
        this.loanNoLike = loanNoLike;
    }

    /**
     * @return the loanNameLike
     */
    public String getLoanNameLike() {
        return loanNameLike;
    }

    /**
     * @param loanNameLike the loanNameLike to set
     */
    public void setLoanNameLike(String loanNameLike) {
        this.loanNameLike = loanNameLike;
    }

    /**
     * @return the buyUserIdLike
     */
    public String getBuyUserIdLike() {
        return buyUserIdLike;
    }

    /**
     * @param buyUserIdLike the buyUserIdLike to set
     */
    public void setBuyUserIdLike(String buyUserIdLike) {
        this.buyUserIdLike = buyUserIdLike;
    }

    /**
     * @return the buySupportNo
     */
    public String getBuySupportNo() {
        return buySupportNo;
    }

    /**
     * @param buySupportNo the buySupportNo to set
     */
    public void setBuySupportNo(String buySupportNo) {
        this.buySupportNo = buySupportNo;
    }

    /**
     * @return the transferFee
     */
    public Double getTransferFee() {
        return transferFee;
    }

    /**
     * @param transferFee the transferFee to set
     */
    public void setTransferFee(Double transferFee) {
        this.transferFee = transferFee;
    }
}