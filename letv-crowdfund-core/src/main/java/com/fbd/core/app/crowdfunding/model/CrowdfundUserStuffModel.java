package com.fbd.core.app.crowdfunding.model;

import java.util.Date;
import java.util.List;
import com.fbd.core.app.fileupload.model.FileUploadModel;
import com.fbd.core.base.BaseModel;

public class CrowdfundUserStuffModel extends BaseModel{
    private String id;

    private String userId;

    private String idCardFront;

    private String idCardBack;

    private String handCardFront;

    private String handCardBack;

    private String weixin;

    private String companyName;

    private String post;

    private String personWeb;

    private String userLevel;

    private String userIdentity;

    private String institution;

    private String role;

    private String concernIndustry;

    private String likeIndustry;

    private String concernCity;

    private Double investRatio;

    private String isInvest;

    private String investExperience;

    private String hasInvestPlan;

    private Integer investPlanNum;

    private Double investPlanAmt;

    private Double yearIncome;

    private Double personCapital;

    private String companyAddress;

    private String institutionIndustry;
    
    private String capitalDes;

    private String userExperience;
    
    private String investAuthState;
    
    private String leadAuthState;
    
    private Date investAuditTime;
    
    private Date leadAuditTime;
    
    private String investAuthor;
    
    private String leadAuthor;
    
    private String userIdLike;
    
    private String mobile;
    
    private String authInvestor;
    
    private String certNo;
    
    private String lendAuthPhoto;
    
    private String realName;
    
    private String position;
    
    private String cardPhoto;

    private String assetsCredentials;
    
    private String historicalInvestmentData;
    
    private String orgLendAuthState;
    
    private String orgInvestAuthState;
    
    private Date orgLendAuditTime;
    
    private Date orgInvestAuditTime;
    
    private String investAuth;
    //认证类型  orgLeadInvestor-领投机构  orgInvestor-跟投机构 leadInvestor-领投人   investor-投资人
    private String authType;

    //数据库外 start
    /**
     * 角色类型状态
     *  lead:领头状态
     *  nomal:跟头状态
     */
    private String authTypeState;
    //机构认证中机构证件
    private List<FileUploadModel> orgCardPhotoList;
    // 机构资产认证资料
    private List<FileUploadModel> orgAssetsCredentialsList;
    //机构历史投资证明
    private List<FileUploadModel> orgHistoricalInvestMentList;
    //个人资产认证资料
    private List<FileUploadModel> assetsCredentialsList;
    //历史投资资料
    private List<FileUploadModel> historicalInvestMentList;
    
    //临时ID
    private String tempId;
    
    
    
    
    
    //数据库外 end
    
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

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront == null ? null : idCardFront.trim();
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack == null ? null : idCardBack.trim();
    }

    public String getHandCardFront() {
        return handCardFront;
    }

    public void setHandCardFront(String handCardFront) {
        this.handCardFront = handCardFront == null ? null : handCardFront.trim();
    }

    public String getHandCardBack() {
        return handCardBack;
    }

    public void setHandCardBack(String handCardBack) {
        this.handCardBack = handCardBack == null ? null : handCardBack.trim();
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getPersonWeb() {
        return personWeb;
    }

    public void setPersonWeb(String personWeb) {
        this.personWeb = personWeb == null ? null : personWeb.trim();
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel == null ? null : userLevel.trim();
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity == null ? null : userIdentity.trim();
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution == null ? null : institution.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getConcernIndustry() {
        return concernIndustry;
    }

    public void setConcernIndustry(String concernIndustry) {
        this.concernIndustry = concernIndustry == null ? null : concernIndustry.trim();
    }

    public String getLikeIndustry() {
        return likeIndustry;
    }

    public void setLikeIndustry(String likeIndustry) {
        this.likeIndustry = likeIndustry == null ? null : likeIndustry.trim();
    }

    public String getConcernCity() {
        return concernCity;
    }

    public void setConcernCity(String concernCity) {
        this.concernCity = concernCity == null ? null : concernCity.trim();
    }

    public Double getInvestRatio() {
        return investRatio;
    }

    public void setInvestRatio(Double investRatio) {
        this.investRatio = investRatio;
    }

    public String getIsInvest() {
        return isInvest;
    }

    public void setIsInvest(String isInvest) {
        this.isInvest = isInvest == null ? null : isInvest.trim();
    }

    public String getInvestExperience() {
        return investExperience;
    }

    public void setInvestExperience(String investExperience) {
        this.investExperience = investExperience == null ? null : investExperience.trim();
    }

    public String getHasInvestPlan() {
        return hasInvestPlan;
    }

    public void setHasInvestPlan(String hasInvestPlan) {
        this.hasInvestPlan = hasInvestPlan == null ? null : hasInvestPlan.trim();
    }

    public Integer getInvestPlanNum() {
        return investPlanNum;
    }

    public void setInvestPlanNum(Integer investPlanNum) {
        this.investPlanNum = investPlanNum;
    }

    public Double getInvestPlanAmt() {
        return investPlanAmt;
    }

    public void setInvestPlanAmt(Double investPlanAmt) {
        this.investPlanAmt = investPlanAmt;
    }

    public Double getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(Double yearIncome) {
        this.yearIncome = yearIncome;
    }

    public Double getPersonCapital() {
        return personCapital;
    }

    public void setPersonCapital(Double personCapital) {
        this.personCapital = personCapital;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getInstitutionIndustry() {
        return institutionIndustry;
    }

    public void setInstitutionIndustry(String institutionIndustry) {
        this.institutionIndustry = institutionIndustry == null ? null : institutionIndustry.trim();
    }
    
    public String getCapitalDes() {
        return capitalDes;
    }

    public void setCapitalDes(String capitalDes) {
        this.capitalDes = capitalDes == null ? null : capitalDes.trim();
    }

    public String getUserExperience() {
        return userExperience;
    }

    public void setUserExperience(String userExperience) {
        this.userExperience = userExperience == null ? null : userExperience.trim();
    }

    public String getInvestAuthState() {
        return investAuthState;
    }

    public void setInvestAuthState(String investAuthState) {
        this.investAuthState = investAuthState;
    }

    public String getLeadAuthState() {
        return leadAuthState;
    }

    public void setLeadAuthState(String leadAuthState) {
        this.leadAuthState = leadAuthState;
    }

    public Date getInvestAuditTime() {
        return investAuditTime;
    }

    public void setInvestAuditTime(Date investAuditTime) {
        this.investAuditTime = investAuditTime;
    }

    public Date getLeadAuditTime() {
        return leadAuditTime;
    }

    public void setLeadAuditTime(Date leadAuditTime) {
        this.leadAuditTime = leadAuditTime;
    }

    public String getInvestAuthor() {
        return investAuthor;
    }

    public void setInvestAuthor(String investAuthor) {
        this.investAuthor = investAuthor;
    }

    public String getLeadAuthor() {
        return leadAuthor;
    }

    public void setLeadAuthor(String leadAuthor) {
        this.leadAuthor = leadAuthor;
    }

    public String getUserIdLike() {
        return userIdLike;
    }

    public void setUserIdLike(String userIdLike) {
        this.userIdLike = userIdLike;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAuthInvestor() {
        return authInvestor;
    }

    public void setAuthInvestor(String authInvestor) {
        this.authInvestor = authInvestor;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    /**
     * @return the lendAuthPhoto
     */
    public String getLendAuthPhoto() {
        return lendAuthPhoto;
    }

    /**
     * @param lendAuthPhoto the lendAuthPhoto to set
     */
    public void setLendAuthPhoto(String lendAuthPhoto) {
        this.lendAuthPhoto = lendAuthPhoto;
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
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the cardPhoto
     */
    public String getCardPhoto() {
        return cardPhoto;
    }

    /**
     * @param cardPhoto the cardPhoto to set
     */
    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    /**
     * @return the assetsCredentials
     */
    public String getAssetsCredentials() {
        return assetsCredentials;
    }

    /**
     * @param assetsCredentials the assetsCredentials to set
     */
    public void setAssetsCredentials(String assetsCredentials) {
        this.assetsCredentials = assetsCredentials;
    }

    /**
     * @return the historicalInvestmentData
     */
    public String getHistoricalInvestmentData() {
        return historicalInvestmentData;
    }

    /**
     * @param historicalInvestmentData the historicalInvestmentData to set
     */
    public void setHistoricalInvestmentData(String historicalInvestmentData) {
        this.historicalInvestmentData = historicalInvestmentData;
    }

    /**
     * @return the orgLendAuthState
     */
    public String getOrgLendAuthState() {
        return orgLendAuthState;
    }

    /**
     * @param orgLendAuthState the orgLendAuthState to set
     */
    public void setOrgLendAuthState(String orgLendAuthState) {
        this.orgLendAuthState = orgLendAuthState;
    }

    /**
     * @return the orgInvestAuthState
     */
    public String getOrgInvestAuthState() {
        return orgInvestAuthState;
    }

    /**
     * @param orgInvestAuthState the orgInvestAuthState to set
     */
    public void setOrgInvestAuthState(String orgInvestAuthState) {
        this.orgInvestAuthState = orgInvestAuthState;
    }

    /**
     * @return the orgLendAuditTime
     */
    public Date getOrgLendAuditTime() {
        return orgLendAuditTime;
    }

    /**
     * @param orgLendAuditTime the orgLendAuditTime to set
     */
    public void setOrgLendAuditTime(Date orgLendAuditTime) {
        this.orgLendAuditTime = orgLendAuditTime;
    }

    /**
     * @return the orgInvestAuditTime
     */
    public Date getOrgInvestAuditTime() {
        return orgInvestAuditTime;
    }

    /**
     * @param orgInvestAuditTime the orgInvestAuditTime to set
     */
    public void setOrgInvestAuditTime(Date orgInvestAuditTime) {
        this.orgInvestAuditTime = orgInvestAuditTime;
    }

    /**
     * @return the investAuth
     */
    public String getInvestAuth() {
        return investAuth;
    }

    /**
     * @param investAuth the investAuth to set
     */
    public void setInvestAuth(String investAuth) {
        this.investAuth = investAuth;
    }

    /**
     * @return the authType
     */
    public String getAuthType() {
        return authType;
    }

    /**
     * @param authType the authType to set
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    /**
     * @return the authTypeState
     */
    public String getAuthTypeState() {
        return authTypeState;
    }

    /**
     * @param authTypeState the authTypeState to set
     */
    public void setAuthTypeState(String authTypeState) {
        this.authTypeState = authTypeState;
    }

    /**
     * @return the orgCardPhotoList
     */
    public List<FileUploadModel> getOrgCardPhotoList() {
        return orgCardPhotoList;
    }

    /**
     * @param orgCardPhotoList the orgCardPhotoList to set
     */
    public void setOrgCardPhotoList(List<FileUploadModel> orgCardPhotoList) {
        this.orgCardPhotoList = orgCardPhotoList;
    }

    /**
     * @return the orgAssetsCredentialsList
     */
    public List<FileUploadModel> getOrgAssetsCredentialsList() {
        return orgAssetsCredentialsList;
    }

    /**
     * @param orgAssetsCredentialsList the orgAssetsCredentialsList to set
     */
    public void setOrgAssetsCredentialsList(
            List<FileUploadModel> orgAssetsCredentialsList) {
        this.orgAssetsCredentialsList = orgAssetsCredentialsList;
    }

    /**
     * @return the orgHistoricalInvestMentList
     */
    public List<FileUploadModel> getOrgHistoricalInvestMentList() {
        return orgHistoricalInvestMentList;
    }

    /**
     * @param orgHistoricalInvestMentList the orgHistoricalInvestMentList to set
     */
    public void setOrgHistoricalInvestMentList(
            List<FileUploadModel> orgHistoricalInvestMentList) {
        this.orgHistoricalInvestMentList = orgHistoricalInvestMentList;
    }

    /**
     * @return the assetsCredentialsList
     */
    public List<FileUploadModel> getAssetsCredentialsList() {
        return assetsCredentialsList;
    }

    /**
     * @param assetsCredentialsList the assetsCredentialsList to set
     */
    public void setAssetsCredentialsList(List<FileUploadModel> assetsCredentialsList) {
        this.assetsCredentialsList = assetsCredentialsList;
    }

    /**
     * @return the historicalInvestMentList
     */
    public List<FileUploadModel> getHistoricalInvestMentList() {
        return historicalInvestMentList;
    }

    /**
     * @param historicalInvestMentList the historicalInvestMentList to set
     */
    public void setHistoricalInvestMentList(
            List<FileUploadModel> historicalInvestMentList) {
        this.historicalInvestMentList = historicalInvestMentList;
    }

    /**
     * @return the tempId
     */
    public String getTempId() {
        return tempId;
    }

    /**
     * @param tempId the tempId to set
     */
    public void setTempId(String tempId) {
        this.tempId = tempId;
    }
    
    
    
}