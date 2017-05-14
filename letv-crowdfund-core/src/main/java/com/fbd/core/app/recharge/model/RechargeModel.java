package com.fbd.core.app.recharge.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class RechargeModel extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 5384576901660737886L;

    private String id;

    private String orderId;
    
    private String userId;

    private Double rechargeAmt;

    private Double actualAmt;

    private String rechargeWay;

    private Double rechargeFee;

    private String feeChildAcctno;

    private String feeAcctNo;

    private String rewardNo;

    private String isRechargedAdmin;

    //充值到账时间(审核时间)
    private Date completeTime;
    //充值单创建时间
    private Date createTime;
    /**充值查询时间**/
    private Date startCompleteTime;
    private Date endCompleteTime;

    private Date createStartTime;
    
    private Date createEndTime;
    
    private String stateName;
    
    private String bankName;
    
    private Double hyFee;
    
    private Double p2pFee;
    
    private String addressIp;
    
    private String bankId;
    
    private String realName;
    //运营审核意见
    private String operatorOpinion;
    //财务审核意见
    private String financialOpinion;
    //运营审核时间
    private String operatorAuditTime;
    //财务审核时间
    private String financialAuditTime;
    //运营审核人
    private String operator;
    //财务审核人
    private String financialAuditor;
    //充值类型
    private String thirdRechargeType;//第三方充值类型
    
    private String stateIn; //查询状态
    
    private String bankThirdNo;//提现银行卡第三方编号
    
    private String transactionId; //区块链事务ID
    
    
    private String agreementState; //协议签署状态  0-未下载  1-已签署
    
    private String agreementUrl;  //协议地址
    //充值状态
    private String state;
    //区块链转账状态(transfer_lock：众筹令转账锁定   success:众筹令转账成功    pushSuccess:数据推送完成)
    private String blockChainState;
    //众筹令转账完成时间
    private Date transferComplateTime;
    //平台在第三方到账状态(账户对账状态 )
    private String reconciliationState;
    //对账时间
    private Date reconciliationTime;
    
    
    
    
    
    
    /**
     * @return the operatorAuditTime
     */
    public String getOperatorAuditTime() {
        return operatorAuditTime;
    }

    /**
     * @param operatorAuditTime the operatorAuditTime to set
     */
    public void setOperatorAuditTime(String operatorAuditTime) {
        this.operatorAuditTime = operatorAuditTime;
    }

    /**
     * @return the financialAuditTime
     */
    public String getFinancialAuditTime() {
        return financialAuditTime;
    }

    /**
     * @param financialAuditTime the financialAuditTime to set
     */
    public void setFinancialAuditTime(String financialAuditTime) {
        this.financialAuditTime = financialAuditTime;
    }

    /**
     * @return the operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the operator to set
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

   

    /**
     * @return the financialAuditor
     */
    public String getFinancialAuditor() {
        return financialAuditor;
    }

    /**
     * @param financialAuditor the financialAuditor to set
     */
    public void setFinancialAuditor(String financialAuditor) {
        this.financialAuditor = financialAuditor;
    }

    /**
     * @return the operatorOpinion
     */
    public String getOperatorOpinion() {
        return operatorOpinion;
    }

    /**
     * @param operatorOpinion the operatorOpinion to set
     */
    public void setOperatorOpinion(String operatorOpinion) {
        this.operatorOpinion = operatorOpinion;
    }

    /**
     * @return the financialOpinion
     */
    public String getFinancialOpinion() {
        return financialOpinion;
    }

    /**
     * @param financialOpinion the financialOpinion to set
     */
    public void setFinancialOpinion(String financialOpinion) {
        this.financialOpinion = financialOpinion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Double getRechargeAmt() {
        return rechargeAmt;
    }

    public void setRechargeAmt(Double rechargeAmt) {
        this.rechargeAmt = rechargeAmt;
    }

    public Double getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(Double actualAmt) {
        this.actualAmt = actualAmt;
    }

    public String getRechargeWay() {
        return rechargeWay;
    }

    public void setRechargeWay(String rechargeWay) {
        this.rechargeWay = rechargeWay == null ? null : rechargeWay.trim();
    }

    public Double getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(Double rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public String getFeeChildAcctno() {
        return feeChildAcctno;
    }

    public void setFeeChildAcctno(String feeChildAcctno) {
        this.feeChildAcctno = feeChildAcctno == null ? null : feeChildAcctno
                .trim();
    }

    public String getFeeAcctNo() {
        return feeAcctNo;
    }

    public void setFeeAcctNo(String feeAcctNo) {
        this.feeAcctNo = feeAcctNo == null ? null : feeAcctNo.trim();
    }

    public String getRewardNo() {
        return rewardNo;
    }

    public void setRewardNo(String rewardNo) {
        this.rewardNo = rewardNo == null ? null : rewardNo.trim();
    }

    public String getIsRechargedAdmin() {
        return isRechargedAdmin;
    }

    public void setIsRechargedAdmin(String isRechargedAdmin) {
        this.isRechargedAdmin = isRechargedAdmin == null ? null
                : isRechargedAdmin.trim();
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    /**
     * @return the addressIp
     */
    public String getAddressIp() {
        return addressIp;
    }

    /**
     * @param addressIp the addressIp to set
     */
    public void setAddressIp(String addressIp) {
        this.addressIp = addressIp;
    }

    /**
     * @return the bankId
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return the thirdRechargeType
     */
    public String getThirdRechargeType() {
        return thirdRechargeType;
    }

    /**
     * @param thirdRechargeType the thirdRechargeType to set
     */
    public void setThirdRechargeType(String thirdRechargeType) {
        this.thirdRechargeType = thirdRechargeType;
    }

    /**
     * @return the stateIn
     */
    public String getStateIn() {
        return stateIn;
    }

    /**
     * @param stateIn the stateIn to set
     */
    public void setStateIn(String stateIn) {
        this.stateIn = stateIn;
    }

    /**
     * @return the bankThirdNo
     */
    public String getBankThirdNo() {
        return bankThirdNo;
    }

    /**
     * @param bankThirdNo the bankThirdNo to set
     */
    public void setBankThirdNo(String bankThirdNo) {
        this.bankThirdNo = bankThirdNo;
    }

    /**
     * @return the transactionId
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * @return the blockChainState
     */
    public String getBlockChainState() {
        return blockChainState;
    }
    /**
     * @param blockChainState the blockChainState to set
     */
    public void setBlockChainState(String blockChainState) {
        this.blockChainState = blockChainState;
    }

    /**
     * @return the agreementState
     */
    public String getAgreementState() {
        return agreementState;
    }

    /**
     * @param agreementState the agreementState to set
     */
    public void setAgreementState(String agreementState) {
        this.agreementState = agreementState;
    }

    /**
     * @return the agreementUrl
     */
    public String getAgreementUrl() {
        return agreementUrl;
    }

    /**
     * @param agreementUrl the agreementUrl to set
     */
    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }
    

    /**
     * @return the startCompleteTime
     */
    public Date getStartCompleteTime() {
        return startCompleteTime;
    }

    /**
     * @param startCompleteTime the startCompleteTime to set
     */
    public void setStartCompleteTime(Date startCompleteTime) {
        this.startCompleteTime = startCompleteTime;
    }

    /**
     * @return the endCompleteTime
     */
    public Date getEndCompleteTime() {
        return endCompleteTime;
    }
    /**
     * @param endCompleteTime the endCompleteTime to set
     */
    public void setEndCompleteTime(Date endCompleteTime) {
        this.endCompleteTime = endCompleteTime;
    }
   

   
    /**
     * @return the reconciliationState
     */
    public String getReconciliationState() {
        return reconciliationState;
    }

    /**
     * @param reconciliationState the reconciliationState to set
     */
    public void setReconciliationState(String reconciliationState) {
        this.reconciliationState = reconciliationState;
    }

   

    /**
     * @return the transferComplateTime
     */
    public Date getTransferComplateTime() {
        return transferComplateTime;
    }

    /**
     * @param transferComplateTime the transferComplateTime to set
     */
    public void setTransferComplateTime(Date transferComplateTime) {
        this.transferComplateTime = transferComplateTime;
    }

    /**
     * @return the reconciliationTime
     */
    public Date getReconciliationTime() {
        return reconciliationTime;
    }

    /**
     * @param reconciliationTime the reconciliationTime to set
     */
    public void setReconciliationTime(Date reconciliationTime) {
        this.reconciliationTime = reconciliationTime;
    }
    
}