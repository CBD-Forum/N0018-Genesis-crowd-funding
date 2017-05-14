package com.fbd.core.app.user.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;

public class CrowdfundUserPrizeModel extends BaseModel{
    private static final long serialVersionUID = 5773283932787954628L;
    private String id;
    /**
     * 项目编号
     */
    private String loanNo;
    /**
     * 抽奖人
     */
    private String prizeUser;
    /**
     * 抽奖时间
     */
    private Date prizeTime;
    /**
     * 抽奖编号
     */
    private Integer prizeNo;
    /**
     * 是否中奖(1为中奖;0为不中奖;空:未开始;)
     * 
     */
    private String isPrize;
    /**
     * 支持编号
     */
    private String supportNo;
    /**
     * 回报编号
     */
    private String backNo;

    public String getId() {
        return id;
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

    public String getPrizeUser() {
        return prizeUser;
    }

    public void setPrizeUser(String prizeUser) {
        this.prizeUser = prizeUser == null ? null : prizeUser.trim();
    }

    public Date getPrizeTime() {
        return prizeTime;
    }

    public void setPrizeTime(Date prizeTime) {
        this.prizeTime = prizeTime;
    }

    public Integer getPrizeNo() {
        return prizeNo;
    }

    public void setPrizeNo(Integer prizeNo) {
        this.prizeNo = prizeNo;
    }

    public String getIsPrize() {
        return isPrize;
    }

    public void setIsPrize(String isPrize) {
        this.isPrize = isPrize;
    }

    /**
     * @return the supportNo
     */
    public String getSupportNo() {
        return supportNo;
    }

    /**
     * @param supportNo the supportNo to set
     */
    public void setSupportNo(String supportNo) {
        this.supportNo = supportNo;
    }

    /**
     * @return the backNo
     */
    public String getBackNo() {
        return backNo;
    }

    /**
     * @param backNo the backNo to set
     */
    public void setBackNo(String backNo) {
        this.backNo = backNo;
    }
    
}