package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import com.fbd.core.base.BaseModel;
/**
 * 
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 众筹项目信息
 *
 * @author haolingfeng
 * @version 1.0
 *
 */
public class CrowdfundingModel extends BaseModel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 操作人(临时)
     */
    private String operate;
    
    private String id;
    private String loanNo;
    private String loanName;
    private String loanUser;
    private String loanType;
    private String loanLabel;
    private Double fundAmt;
    private Double fundAmtUpper;
    private Integer fundDays;
    private String province;
    private String city;
    private String county;
    private Double chargeFee;
    private Double loanDeposit;
    private Double approveAmt;
    private Date createTime;
    private Date releaseTime;
    private Date fundEndTime;
    private Date auditTime;
    private String auditOpinion;
    private String auditor;
    private String cashUser;
    private String creator;
    private String isSystem;
    private Date cashTime;
    private String payState;
    private String loanState;
    private String loanDes;
    private String loanLogo;
    private String loanLevel;
    private Double hyFee;
    private Double p2pFee;
    private Double perfectRatio;
    private Double houseArea;
    private Integer houseLimit;
    private String authSet;
    private String canOver;
    private String superIndustry;
    private String childIndustry;
    private String loanAddress;
    private Double orderAmt;
    private Double leadMinAmt;
    private Double actualFee;
    private Double prepayAmt;
    private Date preheatEndTime;
    private String loanManager;
    private String loanStateIn;
    private String loanNoLike;
    private String loanUserLike;
    private String loanUserNameLike;
    private String userId;
    private String fixLoanType;//固定的众筹类型
    private String loanProcess;//项目列表使用
    private String loanMobileLogo; //手机端项目封面图
    private String contractNo; //合同编号
    private String contractTplNo; //合同模板
    private String loanDetail; //项目详情字段（参数）
    private String loanVideo; //项目视频字段（参数）
    private String mobileVideo; //项目手机视频字段（参数）
    
    
    public String isPublicAgencies ;  //是否是公募机构
    private String orgCertificate;   //公益机构登记证书
    private String orgCode;   //组织机构代码
    private String promoterIdentitySign;  //发起人身份证复印件签名
    private String promoterIdentityPhoto;  //发起人手持身份证照片
    private String orgLoanReceiveProve;  //项目接收证明（非公募机构或个人发起）
    
    
    private String loanIntroduction; //项目一句话的描述
    private String loanPhoto; //项目头图
    
    
    private String loanStage;  //项目阶段
    
    private String competence; //核心竞争力
    
    private String profitModel;  //盈利模式
    
    private String website; 
    
    private String weibo; 
    
    private String weixin; 
    
    private String logo; 
    
    private String bankInfo;
    
    private Integer prizeNum; 
    
    private Integer lockDay;
    
    private Double dailyEarningsForecast;
    
    private Double overFundAmt;
    
    private String tempLoanNo;  //保存收益附件时用到的临时编号
    
    /**
     * 预热时间
     */
    private Date preheatTime;
    
    //用户区块链地址
    private String blockChainAddress;
    //用户区块链秘钥
    private String blockChainSecretKey;
    //区块链原始key
    private String skey;
    //区块链状态(0:未开户  1：已开户  2：已激活)
    private String blockChainState;
    //放款时平台手续费状态
    private String platformTransferState;
  //放款时项目方款项状态
    private String loanTransferState;
    //股权项目份数
    private Integer stockNum;
    //股权项目每份金额
    private Double stockPartAmt;
    
    //查询条件  查询全部的项目
    private String loanStateAll;
    
    /**
     * 抽奖状态
     *   空:没执行;
     *   start:开启中;
     *   end:完成;
     */
    private String prizeStatus;
    //查询 start
    /**
     * 回报编号
     */
    private String backNo;
    /**
     * 查询代码块
     *   waitPrize:等待处理的抽奖
     */
    private String queryMethod;
    //查询 end
    
    
    public String getId() {
        return id;
    }

    /**
     * @return the prizeStatus
     */
    public String getPrizeStatus() {
        return prizeStatus;
    }

    /**
     * @param prizeStatus the prizeStatus to set
     */
    public void setPrizeStatus(String prizeStatus) {
        this.prizeStatus = prizeStatus;
    }

    /**
     * @return the preheatTime
     */
    public Date getPreheatTime() {
        return preheatTime;
    }

    /**
     * @param preheatTime the preheatTime to set
     */
    public void setPreheatTime(Date preheatTime) {
        this.preheatTime = preheatTime;
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

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName == null ? null : loanName.trim();
    }

    public String getLoanUser() {
        return loanUser;
    }

    public void setLoanUser(String loanUser) {
        this.loanUser = loanUser == null ? null : loanUser.trim();
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
    }

    public String getLoanLabel() {
        return loanLabel;
    }

    public void setLoanLabel(String loanLabel) {
        this.loanLabel = loanLabel == null ? null : loanLabel.trim();
    }

    public Double getFundAmt() {
        return fundAmt;
    }

    public void setFundAmt(Double fundAmt) {
        this.fundAmt = fundAmt;
    }

    public Double getFundAmtUpper() {
        return fundAmtUpper;
    }

    /**
     * @return the queryMethod
     */
    public String getQueryMethod() {
        return queryMethod;
    }

    /**
     * @param queryMethod the queryMethod to set
     */
    public void setQueryMethod(String queryMethod) {
        this.queryMethod = queryMethod;
    }

    public void setFundAmtUpper(Double fundAmtUpper) {
        this.fundAmtUpper = fundAmtUpper;
    }

    public Integer getFundDays() {
        return fundDays;
    }

    public void setFundDays(Integer fundDays) {
        this.fundDays = fundDays;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Double getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(Double chargeFee) {
        this.chargeFee = chargeFee;
    }

    public Double getLoanDeposit() {
        return loanDeposit;
    }

    public void setLoanDeposit(Double loanDeposit) {
        this.loanDeposit = loanDeposit;
    }

    public Double getApproveAmt() {
        return approveAmt;
    }

    public void setApproveAmt(Double approveAmt) {
        this.approveAmt = approveAmt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getFundEndTime() {
        return fundEndTime;
    }

    public void setFundEndTime(Date fundEndTime) {
        this.fundEndTime = fundEndTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getCashUser() {
        return cashUser;
    }

    public void setCashUser(String cashUser) {
        this.cashUser = cashUser == null ? null : cashUser.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(String isSystem) {
        this.isSystem = isSystem == null ? null : isSystem.trim();
    }

    public Date getCashTime() {
        return cashTime;
    }

    public void setCashTime(Date cashTime) {
        this.cashTime = cashTime;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState == null ? null : payState.trim();
    }

    public String getLoanState() {
        return loanState;
    }

    public void setLoanState(String loanState) {
        this.loanState = loanState == null ? null : loanState.trim();
    }

    public String getLoanDes() {
        return loanDes;
    }

    public void setLoanDes(String loanDes) {
        this.loanDes = loanDes == null ? null : loanDes.trim();
    }

    public String getLoanLogo() {
        return loanLogo;
    }

    public void setLoanLogo(String loanLogo) {
        this.loanLogo = loanLogo == null ? null : loanLogo.trim();
    }
    public String getLoanLevel() {
        return loanLevel;
    }

    public void setLoanLevel(String loanLevel) {
        this.loanLevel = loanLevel == null ? null : loanLevel.trim();
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

    public Double getPerfectRatio() {
        return perfectRatio;
    }

    public void setPerfectRatio(Double perfectRatio) {
        this.perfectRatio = perfectRatio;
    }

    public Double getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(Double houseArea) {
        this.houseArea = houseArea;
    }

    public String getAuthSet() {
        return authSet;
    }

    public void setAuthSet(String authSet) {
        this.authSet = authSet == null ? null : authSet.trim();
    }

    public String getCanOver() {
        return canOver;
    }

    public void setCanOver(String canOver) {
        this.canOver = canOver == null ? null : canOver.trim();
    }

    public String getSuperIndustry() {
        return superIndustry;
    }

    public void setSuperIndustry(String superIndustry) {
        this.superIndustry = superIndustry == null ? null : superIndustry
                .trim();
    }

    public String getChildIndustry() {
        return childIndustry;
    }

    public void setChildIndustry(String childIndustry) {
        this.childIndustry = childIndustry == null ? null : childIndustry
                .trim();
    }

    public String getLoanStateIn() {
        return loanStateIn;
    }

    public void setLoanStateIn(String loanStateIn) {
        this.loanStateIn = loanStateIn;
    }

    public String getLoanNoLike() {
        return loanNoLike;
    }

    public void setLoanNoLike(String loanNoLike) {
        this.loanNoLike = loanNoLike;
    }

    public String getLoanUserLike() {
        return loanUserLike;
    }

    public void setLoanUserLike(String loanUserLike) {
        this.loanUserLike = loanUserLike;
    }

    public String getLoanUserNameLike() {
        return loanUserNameLike;
    }

    public void setLoanUserNameLike(String loanUserNameLike) {
        this.loanUserNameLike = loanUserNameLike;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getHouseLimit() {
        return houseLimit;
    }

    public void setHouseLimit(Integer houseLimit) {
        this.houseLimit = houseLimit;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLoanProcess() {
        return loanProcess;
    }

    public void setLoanProcess(String loanProcess) {
        this.loanProcess = loanProcess;
    }

    public String getLoanAddress() {
        return loanAddress;
    }

    public void setLoanAddress(String loanAddress) {
        this.loanAddress = loanAddress;
    }

    public Double getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(Double orderAmt) {
        this.orderAmt = orderAmt;
    }

    public Double getLeadMinAmt() {
        return leadMinAmt;
    }

    public void setLeadMinAmt(Double leadMinAmt) {
        this.leadMinAmt = leadMinAmt;
    }

    public Double getActualFee() {
        return actualFee;
    }

    public void setActualFee(Double actualFee) {
        this.actualFee = actualFee;
    }

    public Double getPrepayAmt() {
        return prepayAmt;
    }

    public void setPrepayAmt(Double prepayAmt) {
        this.prepayAmt = prepayAmt;
    }

    public Date getPreheatEndTime() {
        return preheatEndTime;
    }

    public void setPreheatEndTime(Date preheatEndTime) {
        this.preheatEndTime = preheatEndTime;
    }

    public String getFixLoanType() {
        return fixLoanType;
    }

    public void setFixLoanType(String fixLoanType) {
        this.fixLoanType = fixLoanType;
    }

    public String getLoanManager() {
        return loanManager;
    }

    public void setLoanManager(String loanManager) {
        this.loanManager = loanManager;
    }
   
    public String getLoanMobileLogo() {
        return loanMobileLogo;
    }

    public void setLoanMobileLogo(String loanMobileLogo) {
        this.loanMobileLogo = loanMobileLogo;
    }

    /**
     * @return the contractNo
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * @param contractNo the contractNo to set
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * @return the contractTplNo
     */
    public String getContractTplNo() {
        return contractTplNo;
    }
    /**
     * @param contractTplNo the contractTplNo to set
     */
    public void setContractTplNo(String contractTplNo) {
        this.contractTplNo = contractTplNo;
    }


    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * @return the promoterIdentitySign
     */
    public String getPromoterIdentitySign() {
        return promoterIdentitySign;
    }

    /**
     * @param promoterIdentitySign the promoterIdentitySign to set
     */
    public void setPromoterIdentitySign(String promoterIdentitySign) {
        this.promoterIdentitySign = promoterIdentitySign;
    }

    /**
     * @return the promoterIdentityPhoto
     */
    public String getPromoterIdentityPhoto() {
        return promoterIdentityPhoto;
    }

    /**
     * @param promoterIdentityPhoto the promoterIdentityPhoto to set
     */
    public void setPromoterIdentityPhoto(String promoterIdentityPhoto) {
        this.promoterIdentityPhoto = promoterIdentityPhoto;
    }

    /**
     * @return the orgLoanReceiveProve
     */
    public String getOrgLoanReceiveProve() {
        return orgLoanReceiveProve;
    }

    /**
     * @param orgLoanReceiveProve the orgLoanReceiveProve to set
     */
    public void setOrgLoanReceiveProve(String orgLoanReceiveProve) {
        this.orgLoanReceiveProve = orgLoanReceiveProve;
    }

    /**
     * @return the isPublicAgencies
     */
    public String getIsPublicAgencies() {
        return isPublicAgencies;
    }

    /**
     * @param isPublicAgencies the isPublicAgencies to set
     */
    public void setIsPublicAgencies(String isPublicAgencies) {
        this.isPublicAgencies = isPublicAgencies;
    }

    /**
     * @return the orgCertificate
     */
    public String getOrgCertificate() {
        return orgCertificate;
    }

    /**
     * @param orgCertificate the orgCertificate to set
     */
    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate;
    }

    /**
     * @return the loanIntroduction
     */
    public String getLoanIntroduction() {
        return loanIntroduction;
    }

    /**
     * @param loanIntroduction the loanIntroduction to set
     */
    public void setLoanIntroduction(String loanIntroduction) {
        this.loanIntroduction = loanIntroduction;
    }

    /**
     * @return the loanPhoto
     */
    public String getLoanPhoto() {
        return loanPhoto;
    }

    /**
     * @param loanPhoto the loanPhoto to set
     */
    public void setLoanPhoto(String loanPhoto) {
        this.loanPhoto = loanPhoto;
    }

    /**
     * @return the loanStage
     */
    public String getLoanStage() {
        return loanStage;
    }

    /**
     * @param loanStage the loanStage to set
     */
    public void setLoanStage(String loanStage) {
        this.loanStage = loanStage;
    }

    /**
     * @return the competence
     */
    public String getCompetence() {
        return competence;
    }

    /**
     * @param competence the competence to set
     */
    public void setCompetence(String competence) {
        this.competence = competence;
    }

    /**
     * @return the profitModel
     */
    public String getProfitModel() {
        return profitModel;
    }

    /**
     * @param profitModel the profitModel to set
     */
    public void setProfitModel(String profitModel) {
        this.profitModel = profitModel;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * @return the weibo
     */
    public String getWeibo() {
        return weibo;
    }

    /**
     * @param weibo the weibo to set
     */
    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    /**
     * @return the weixin
     */
    public String getWeixin() {
        return weixin;
    }

    /**
     * @param weixin the weixin to set
     */
    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return the bankInfo
     */
    public String getBankInfo() {
        return bankInfo;
    }

    /**
     * @param bankInfo the bankInfo to set
     */
    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    /**
     * @return the prizeNum
     */
    public Integer getPrizeNum() {
        return prizeNum;
    }

    /**
     * @param prizeNum the prizeNum to set
     */
    public void setPrizeNum(Integer prizeNum) {
        this.prizeNum = prizeNum;
    }

    /**
     * @return the lockDay
     */
    public Integer getLockDay() {
        return lockDay;
    }

    /**
     * @param lockDay the lockDay to set
     */
    public void setLockDay(Integer lockDay) {
        this.lockDay = lockDay;
    }

    /**
     * @return the dailyEarningsForecast
     */
    public Double getDailyEarningsForecast() {
        return dailyEarningsForecast;
    }

    /**
     * @param dailyEarningsForecast the dailyEarningsForecast to set
     */
    public void setDailyEarningsForecast(Double dailyEarningsForecast) {
        this.dailyEarningsForecast = dailyEarningsForecast;
    }

    /**
     * @return the overFundAmt
     */
    public Double getOverFundAmt() {
        //return overFundAmt = overFundAmt ==null ?0.0:overFundAmt;
        return overFundAmt;
    }

    /**
     * @param overFundAmt the overFundAmt to set
     */
    public void setOverFundAmt(Double overFundAmt) {
        this.overFundAmt = overFundAmt;
    }

    public String getLoanDetail() {
        return loanDetail;
    }

    public void setLoanDetail(String loanDetail) {
        this.loanDetail = loanDetail;
    }

    public String getLoanVideo() {
        return loanVideo;
    }

    public void setLoanVideo(String loanVideo) {
        this.loanVideo = loanVideo;
    }

    /**
     * @return the tempLoanNo
     */
    public String getTempLoanNo() {
        return tempLoanNo;
    }

    /**
     * @param tempLoanNo the tempLoanNo to set
     */
    public void setTempLoanNo(String tempLoanNo) {
        this.tempLoanNo = tempLoanNo;
    }

    /**
     * @return the blockChainAddress
     */
    public String getBlockChainAddress() {
        return blockChainAddress;
    }

    /**
     * @param blockChainAddress the blockChainAddress to set
     */
    public void setBlockChainAddress(String blockChainAddress) {
        this.blockChainAddress = blockChainAddress;
    }

    /**
     * @return the blockChainSecretKey
     */
    public String getBlockChainSecretKey() {
        return blockChainSecretKey;
    }

    /**
     * @param blockChainSecretKey the blockChainSecretKey to set
     */
    public void setBlockChainSecretKey(String blockChainSecretKey) {
        this.blockChainSecretKey = blockChainSecretKey;
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
     * @return the platformTransferState
     */
    public String getPlatformTransferState() {
        return platformTransferState;
    }

    /**
     * @param platformTransferState the platformTransferState to set
     */
    public void setPlatformTransferState(String platformTransferState) {
        this.platformTransferState = platformTransferState;
    }

    /**
     * @return the loanTransferState
     */
    public String getLoanTransferState() {
        return loanTransferState;
    }

    /**
     * @param loanTransferState the loanTransferState to set
     */
    public void setLoanTransferState(String loanTransferState) {
        this.loanTransferState = loanTransferState;
    }

    /**
     * @return the stockNum
     */
    public Integer getStockNum() {
        return stockNum;
    }

    /**
     * @param stockNum the stockNum to set
     */
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    /**
     * @return the stockPartAmt
     */
    public Double getStockPartAmt() {
        return stockPartAmt;
    }

    /**
     * @param stockPartAmt the stockPartAmt to set
     */
    public void setStockPartAmt(Double stockPartAmt) {
        this.stockPartAmt = stockPartAmt;
    }

    /**
     * @return the loanStateAll
     */
    public String getLoanStateAll() {
        return loanStateAll;
    }

    /**
     * @param loanStateAll the loanStateAll to set
     */
    public void setLoanStateAll(String loanStateAll) {
        this.loanStateAll = loanStateAll;
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

    /**
     * @return the operate
     */
    public String getOperate() {
        return operate;
    }

    /**
     * @param operate the operate to set
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * @return the skey
     */
    public String getSkey() {
        return skey;
    }

    /**
     * @param skey the skey to set
     */
    public void setSkey(String skey) {
        this.skey = skey;
    }

    /**
     * @return the mobileVideo
     */
    public String getMobileVideo() {
        return mobileVideo;
    }

    /**
     * @param mobileVideo the mobileVideo to set
     */
    public void setMobileVideo(String mobileVideo) {
        this.mobileVideo = mobileVideo;
    }
    
    
}