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

package com.fbd.core.app.user.model;

import java.util.Date;

/**
 * Copyright (C) 2016-2017 NBIChain SunCrowdfund System.
 * 
 * Description:普通用戶
 * 
 * @author haolingfeng
 * @version 1.0
 * 
 */

public class UserModel extends UserSecurityModel {

    /**
     * 
     */
    private static final long serialVersionUID = -6570157644351725880L;

    // id
    private String id;

    // 用户名
    private String userId;

    // 第三方账户
    private String thirdAccount;

    // 邮箱
    private String email;

    // 密码
    private String password;

    // 真实姓名
    private String realName;

    // 昵称
    private String nickName;

    // 性别
    private String sex;

    // 生日
    private Date birthday;

    // QQ账号
    private String qqNo;
    // 微博账号
    private String weiboNo;
    // 手机号
    private String mobile;
    // 出借人电话号
    private String tel;
    // 证件类型
    private String certificateType;
    // 证件号
    private String certificateNo;
    // 所在国家
    private Integer country;
    // 所在省
    private Integer province;
    // 所在城市
    private Integer city;
    // 所在县
    private Integer county;
    // 家庭住址
    private String homeAddress;
    // 头像
    private String photo;
    // 认证时间
    private Date authTime;
    // 用户等级
    private String userLevel;
    // 开户时间
    private Date openAccTime;
    // 邀请码
    private String inviteCode;
    // 创建时间
    private Date createTime;

    private String isBorrower;
    
    private String emergencyContact;
    
    private String emergencyPhone;
    
    private String emergencyRelation;
    
    private String postCode;
    
    private String isAuth;
    
    private String selfDetail;
    //公司
    private String company;
    //职位
    private String position;
    
    //用户区块链地址
    private String blockChainAddress;
    //用户区块链秘钥
    private String blockChainSecretKey;
    //区块链原key
    public String skey;
    
    
    //区块链状态(0:未开户  1：已开户  2：已激活)
    private String blockChainState;
    //是否设置印章
    private String isSetSignature;
    
    
    private String UPSAuth;//UPS认证 
    
    private String SXYAuth;//首信易认证
    
    private String userType;//用户类型
    
    private String memberType;  //会员类型 0-个人  1-机构
    

    private Double balance;

    private Double frozenAmt;
    
    

    // ===================页面属性================================
    // 二次密码
    private String password2;
    // 页面的邀请码
    private String inputInviteCode;
    // 验证码
    private String valiCode;
    
    //====================查询条件======================
    //注册开始时间
    private Date startCreateTime;
    //注册结束时间
    private Date endCreateTime;
    
    private String isCelebrity; //是否为明星
    
    private String bankNo;
    //安全眼标识  0-显示  1-隐藏
    private String safetyEyeFlag; 
    //更新时间
    private Date updateTime;
    //最后更新时间
    private Date lastUpdateTime;

    /**
     * @return id id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return userId 用户名
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            用户名
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return thirdAccount 第三方账户
     */
    public String getThirdAccount() {
        return thirdAccount;
    }

    /**
     * @param thirdAccount
     *            第三方账户
     */
    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    /**
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return realName 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     *            真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return nickName 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     *            昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return sex 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     *            性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return birthday 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return qqNo QQ账号
     */
    public String getQqNo() {
        return qqNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @param qqNo
     *            QQ账号
     */
    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    /**
     * @return weiboNo 微博账号
     */
    public String getWeiboNo() {
        return weiboNo;
    }

    /**
     * @param weiboNo
     *            微博账号
     */
    public void setWeiboNo(String weiboNo) {
        this.weiboNo = weiboNo;
    }

    /**
     * @return mobile 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *            手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return tel 出借人电话号
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     *            出借人电话号
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return certificateType 证件类型
     */
    public String getCertificateType() {
        return certificateType;
    }

    /**
     * @param certificateType
     *            证件类型
     */
    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    /**
     * @return certificateNo 证件号
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * @param certificateNo
     *            证件号
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * @return country 所在国家
     */
    public Integer getCountry() {
        return country;
    }

    /**
     * @param country
     *            所在国家
     */
    public void setCountry(Integer country) {
        this.country = country;
    }

    /**
     * @return province 所在省
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * @param province
     *            所在省
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * @return city 所在城市
     */
    public Integer getCity() {
        return city;
    }

    /**
     * @param city
     *            所在城市
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * @return county 所在县
     */
    public Integer getCounty() {
        return county;
    }

    /**
     * @param county
     *            所在县
     */
    public void setCounty(Integer county) {
        this.county = county;
    }

    /**
     * @return homeAddress 家庭住址
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /**
     * @param homeAddress
     *            家庭住址
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /**
     * @return photo 头像
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo
     *            头像
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return authTime 认证时间
     */
    public Date getAuthTime() {
        return authTime;
    }

    /**
     * @param authTime
     *            认证时间
     */
    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    /**
     * @return userLevel 用户等级
     */
    public String getUserLevel() {
        return userLevel;
    }

    /**
     * @param userLevel
     *            用户等级
     */
    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * @return openAccTime 开户时间
     */
    public Date getOpenAccTime() {
        return openAccTime;
    }

    /**
     * @param openAccTime
     *            开户时间
     */
    public void setOpenAccTime(Date openAccTime) {
        this.openAccTime = openAccTime;
    }

    /**
     * @return inviteCode 邀请码
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * @param inviteCode
     *            邀请码
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getInputInviteCode() {
        return inputInviteCode;
    }

    public void setInputInviteCode(String inputInviteCode) {
        this.inputInviteCode = inputInviteCode;
    }

    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }

    public String getIsBorrower() {
        return isBorrower;
    }

    public void setIsBorrower(String isBorrower) {
        this.isBorrower = isBorrower;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getEmergencyRelation() {
        return emergencyRelation;
    }

    public void setEmergencyRelation(String emergencyRelation) {
        this.emergencyRelation = emergencyRelation;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(String isAuth) {
        this.isAuth = isAuth;
    }

    public String getSelfDetail() {
        return selfDetail;
    }

    public void setSelfDetail(String selfDetail) {
        this.selfDetail = selfDetail;
    }

    /**
     * @return the isCelebrity
     */
    public String getIsCelebrity() {
        return isCelebrity;
    }

    /**
     * @param isCelebrity the isCelebrity to set
     */
    public void setIsCelebrity(String isCelebrity) {
        this.isCelebrity = isCelebrity;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
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
     * @return the bankNo
     */
    public String getBankNo() {
        return bankNo;
    }

    /**
     * @param bankNo the bankNo to set
     */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
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
     * @return the isSetSignature
     */
    public String getIsSetSignature() {
        return isSetSignature;
    }

    /**
     * @param isSetSignature the isSetSignature to set
     */
    public void setIsSetSignature(String isSetSignature) {
        this.isSetSignature = isSetSignature;
    }

    /**
     * @return the uPSAuth
     */
    public String getUPSAuth() {
        return UPSAuth;
    }

    /**
     * @param uPSAuth the uPSAuth to set
     */
    public void setUPSAuth(String uPSAuth) {
        UPSAuth = uPSAuth;
    }

    /**
     * @return the sXYAuth
     */
    public String getSXYAuth() {
        return SXYAuth;
    }

    /**
     * @param sXYAuth the sXYAuth to set
     */
    public void setSXYAuth(String sXYAuth) {
        SXYAuth = sXYAuth;
    }

    /**
     * @return the memberType
     */
    public String getMemberType() {
        return memberType;
    }

    /**
     * @param memberType the memberType to set
     */
    public void setMemberType(String memberType) {
        this.memberType = memberType;
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
     * @return the balance
     */
    public Double getBalance() {
        return balance = balance==null?0.0:balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * @return the frozenAmt
     */
    public Double getFrozenAmt() {
        return frozenAmt = frozenAmt==null?0.0:frozenAmt;
    }

    /**
     * @param frozenAmt the frozenAmt to set
     */
    public void setFrozenAmt(Double frozenAmt) {
        this.frozenAmt = frozenAmt;
    }

    /**
     * @return the safetyEyeFlag
     */
    public String getSafetyEyeFlag() {
        return safetyEyeFlag;
    }

    /**
     * @param safetyEyeFlag the safetyEyeFlag to set
     */
    public void setSafetyEyeFlag(String safetyEyeFlag) {
        this.safetyEyeFlag = safetyEyeFlag;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the lastUpdateTime
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    
    
}
