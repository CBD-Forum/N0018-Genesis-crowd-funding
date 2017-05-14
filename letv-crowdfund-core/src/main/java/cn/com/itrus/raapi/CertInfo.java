
package cn.com.itrus.raapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>certInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="certInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqTransid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqNonce" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqBufType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqChallenge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certApproveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certRejectDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSignDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certNotBefore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certNotAfter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certIssuerHashMd5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certIssuerDn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSubjectHashMd5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSubjectDn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSuspendDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certRevokeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certRevokeReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certRenewalDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certRenewalPrevId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certRenewalNextId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certReqOverrideValidity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certReqBuf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSignBuf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certPin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certKmcReq1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certKmcReq2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certKmcReq3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certKmcRep1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certKmcRep2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certKmcRep3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certDeliverMode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certApproveAdmin1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certApproveAdmin2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certRenewemailDeliver" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqBufKmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqBufTypeKmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSignBufKmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSignBufP7Kmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSignBufP7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certSerialnumberKmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certTypeKmc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqPublickey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certReqPublickeyHashMd5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="certIdRandom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "certInfo", propOrder = {
    "certStatus",
    "certReqDate",
    "certReqTransid",
    "certReqNonce",
    "certReqBufType",
    "certReqChallenge",
    "certReqComment",
    "certApproveDate",
    "certRejectDate",
    "certSignDate",
    "certSerialNumber",
    "certNotBefore",
    "certNotAfter",
    "certIssuerHashMd5",
    "certIssuerDn",
    "certSubjectHashMd5",
    "certSubjectDn",
    "certSuspendDate",
    "certRevokeDate",
    "certRevokeReason",
    "certRenewalDate",
    "certRenewalPrevId",
    "certRenewalNextId",
    "certReqOverrideValidity",
    "id",
    "certReqBuf",
    "certSignBuf",
    "certPin",
    "certType",
    "certKmcReq1",
    "certKmcReq2",
    "certKmcReq3",
    "certKmcRep1",
    "certKmcRep2",
    "certKmcRep3",
    "certDeliverMode",
    "certApproveAdmin1",
    "certApproveAdmin2",
    "certRenewemailDeliver",
    "certReqBufKmc",
    "certReqBufTypeKmc",
    "certSignBufKmc",
    "certSignBufP7Kmc",
    "certSignBufP7",
    "certSerialnumberKmc",
    "certTypeKmc",
    "certReqPublickey",
    "certReqPublickeyHashMd5",
    "certIdRandom"
})
public class CertInfo {

    protected String certStatus;
    protected String certReqDate;
    protected String certReqTransid;
    protected String certReqNonce;
    protected String certReqBufType;
    protected String certReqChallenge;
    protected String certReqComment;
    protected String certApproveDate;
    protected String certRejectDate;
    protected String certSignDate;
    protected String certSerialNumber;
    protected String certNotBefore;
    protected String certNotAfter;
    protected String certIssuerHashMd5;
    protected String certIssuerDn;
    protected String certSubjectHashMd5;
    protected String certSubjectDn;
    protected String certSuspendDate;
    protected String certRevokeDate;
    protected String certRevokeReason;
    protected String certRenewalDate;
    protected int certRenewalPrevId;
    protected int certRenewalNextId;
    protected int certReqOverrideValidity;
    protected int id;
    protected String certReqBuf;
    protected String certSignBuf;
    protected String certPin;
    protected String certType;
    protected String certKmcReq1;
    protected String certKmcReq2;
    protected String certKmcReq3;
    protected String certKmcRep1;
    protected String certKmcRep2;
    protected String certKmcRep3;
    protected String certDeliverMode;
    protected String certApproveAdmin1;
    protected String certApproveAdmin2;
    protected String certRenewemailDeliver;
    protected String certReqBufKmc;
    protected String certReqBufTypeKmc;
    protected String certSignBufKmc;
    protected String certSignBufP7Kmc;
    protected String certSignBufP7;
    protected String certSerialnumberKmc;
    protected String certTypeKmc;
    protected String certReqPublickey;
    protected String certReqPublickeyHashMd5;
    protected String certIdRandom;

    /**
     * 获取certStatus属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertStatus() {
        return certStatus;
    }

    /**
     * 设置certStatus属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertStatus(String value) {
        this.certStatus = value;
    }

    /**
     * 获取certReqDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqDate() {
        return certReqDate;
    }

    /**
     * 设置certReqDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqDate(String value) {
        this.certReqDate = value;
    }

    /**
     * 获取certReqTransid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqTransid() {
        return certReqTransid;
    }

    /**
     * 设置certReqTransid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqTransid(String value) {
        this.certReqTransid = value;
    }

    /**
     * 获取certReqNonce属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqNonce() {
        return certReqNonce;
    }

    /**
     * 设置certReqNonce属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqNonce(String value) {
        this.certReqNonce = value;
    }

    /**
     * 获取certReqBufType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqBufType() {
        return certReqBufType;
    }

    /**
     * 设置certReqBufType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqBufType(String value) {
        this.certReqBufType = value;
    }

    /**
     * 获取certReqChallenge属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqChallenge() {
        return certReqChallenge;
    }

    /**
     * 设置certReqChallenge属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqChallenge(String value) {
        this.certReqChallenge = value;
    }

    /**
     * 获取certReqComment属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqComment() {
        return certReqComment;
    }

    /**
     * 设置certReqComment属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqComment(String value) {
        this.certReqComment = value;
    }

    /**
     * 获取certApproveDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertApproveDate() {
        return certApproveDate;
    }

    /**
     * 设置certApproveDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertApproveDate(String value) {
        this.certApproveDate = value;
    }

    /**
     * 获取certRejectDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertRejectDate() {
        return certRejectDate;
    }

    /**
     * 设置certRejectDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertRejectDate(String value) {
        this.certRejectDate = value;
    }

    /**
     * 获取certSignDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSignDate() {
        return certSignDate;
    }

    /**
     * 设置certSignDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSignDate(String value) {
        this.certSignDate = value;
    }

    /**
     * 获取certSerialNumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSerialNumber() {
        return certSerialNumber;
    }

    /**
     * 设置certSerialNumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSerialNumber(String value) {
        this.certSerialNumber = value;
    }

    /**
     * 获取certNotBefore属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertNotBefore() {
        return certNotBefore;
    }

    /**
     * 设置certNotBefore属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertNotBefore(String value) {
        this.certNotBefore = value;
    }

    /**
     * 获取certNotAfter属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertNotAfter() {
        return certNotAfter;
    }

    /**
     * 设置certNotAfter属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertNotAfter(String value) {
        this.certNotAfter = value;
    }

    /**
     * 获取certIssuerHashMd5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertIssuerHashMd5() {
        return certIssuerHashMd5;
    }

    /**
     * 设置certIssuerHashMd5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertIssuerHashMd5(String value) {
        this.certIssuerHashMd5 = value;
    }

    /**
     * 获取certIssuerDn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertIssuerDn() {
        return certIssuerDn;
    }

    /**
     * 设置certIssuerDn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertIssuerDn(String value) {
        this.certIssuerDn = value;
    }

    /**
     * 获取certSubjectHashMd5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSubjectHashMd5() {
        return certSubjectHashMd5;
    }

    /**
     * 设置certSubjectHashMd5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSubjectHashMd5(String value) {
        this.certSubjectHashMd5 = value;
    }

    /**
     * 获取certSubjectDn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSubjectDn() {
        return certSubjectDn;
    }

    /**
     * 设置certSubjectDn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSubjectDn(String value) {
        this.certSubjectDn = value;
    }

    /**
     * 获取certSuspendDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSuspendDate() {
        return certSuspendDate;
    }

    /**
     * 设置certSuspendDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSuspendDate(String value) {
        this.certSuspendDate = value;
    }

    /**
     * 获取certRevokeDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertRevokeDate() {
        return certRevokeDate;
    }

    /**
     * 设置certRevokeDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertRevokeDate(String value) {
        this.certRevokeDate = value;
    }

    /**
     * 获取certRevokeReason属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertRevokeReason() {
        return certRevokeReason;
    }

    /**
     * 设置certRevokeReason属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertRevokeReason(String value) {
        this.certRevokeReason = value;
    }

    /**
     * 获取certRenewalDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertRenewalDate() {
        return certRenewalDate;
    }

    /**
     * 设置certRenewalDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertRenewalDate(String value) {
        this.certRenewalDate = value;
    }

    /**
     * 获取certRenewalPrevId属性的值。
     * 
     */
    public int getCertRenewalPrevId() {
        return certRenewalPrevId;
    }

    /**
     * 设置certRenewalPrevId属性的值。
     * 
     */
    public void setCertRenewalPrevId(int value) {
        this.certRenewalPrevId = value;
    }

    /**
     * 获取certRenewalNextId属性的值。
     * 
     */
    public int getCertRenewalNextId() {
        return certRenewalNextId;
    }

    /**
     * 设置certRenewalNextId属性的值。
     * 
     */
    public void setCertRenewalNextId(int value) {
        this.certRenewalNextId = value;
    }

    /**
     * 获取certReqOverrideValidity属性的值。
     * 
     */
    public int getCertReqOverrideValidity() {
        return certReqOverrideValidity;
    }

    /**
     * 设置certReqOverrideValidity属性的值。
     * 
     */
    public void setCertReqOverrideValidity(int value) {
        this.certReqOverrideValidity = value;
    }

    /**
     * 获取id属性的值。
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * 获取certReqBuf属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqBuf() {
        return certReqBuf;
    }

    /**
     * 设置certReqBuf属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqBuf(String value) {
        this.certReqBuf = value;
    }

    /**
     * 获取certSignBuf属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSignBuf() {
        return certSignBuf;
    }

    /**
     * 设置certSignBuf属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSignBuf(String value) {
        this.certSignBuf = value;
    }

    /**
     * 获取certPin属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertPin() {
        return certPin;
    }

    /**
     * 设置certPin属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertPin(String value) {
        this.certPin = value;
    }

    /**
     * 获取certType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertType() {
        return certType;
    }

    /**
     * 设置certType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertType(String value) {
        this.certType = value;
    }

    /**
     * 获取certKmcReq1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKmcReq1() {
        return certKmcReq1;
    }

    /**
     * 设置certKmcReq1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKmcReq1(String value) {
        this.certKmcReq1 = value;
    }

    /**
     * 获取certKmcReq2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKmcReq2() {
        return certKmcReq2;
    }

    /**
     * 设置certKmcReq2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKmcReq2(String value) {
        this.certKmcReq2 = value;
    }

    /**
     * 获取certKmcReq3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKmcReq3() {
        return certKmcReq3;
    }

    /**
     * 设置certKmcReq3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKmcReq3(String value) {
        this.certKmcReq3 = value;
    }

    /**
     * 获取certKmcRep1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKmcRep1() {
        return certKmcRep1;
    }

    /**
     * 设置certKmcRep1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKmcRep1(String value) {
        this.certKmcRep1 = value;
    }

    /**
     * 获取certKmcRep2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKmcRep2() {
        return certKmcRep2;
    }

    /**
     * 设置certKmcRep2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKmcRep2(String value) {
        this.certKmcRep2 = value;
    }

    /**
     * 获取certKmcRep3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertKmcRep3() {
        return certKmcRep3;
    }

    /**
     * 设置certKmcRep3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertKmcRep3(String value) {
        this.certKmcRep3 = value;
    }

    /**
     * 获取certDeliverMode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertDeliverMode() {
        return certDeliverMode;
    }

    /**
     * 设置certDeliverMode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertDeliverMode(String value) {
        this.certDeliverMode = value;
    }

    /**
     * 获取certApproveAdmin1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertApproveAdmin1() {
        return certApproveAdmin1;
    }

    /**
     * 设置certApproveAdmin1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertApproveAdmin1(String value) {
        this.certApproveAdmin1 = value;
    }

    /**
     * 获取certApproveAdmin2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertApproveAdmin2() {
        return certApproveAdmin2;
    }

    /**
     * 设置certApproveAdmin2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertApproveAdmin2(String value) {
        this.certApproveAdmin2 = value;
    }

    /**
     * 获取certRenewemailDeliver属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertRenewemailDeliver() {
        return certRenewemailDeliver;
    }

    /**
     * 设置certRenewemailDeliver属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertRenewemailDeliver(String value) {
        this.certRenewemailDeliver = value;
    }

    /**
     * 获取certReqBufKmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqBufKmc() {
        return certReqBufKmc;
    }

    /**
     * 设置certReqBufKmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqBufKmc(String value) {
        this.certReqBufKmc = value;
    }

    /**
     * 获取certReqBufTypeKmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqBufTypeKmc() {
        return certReqBufTypeKmc;
    }

    /**
     * 设置certReqBufTypeKmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqBufTypeKmc(String value) {
        this.certReqBufTypeKmc = value;
    }

    /**
     * 获取certSignBufKmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSignBufKmc() {
        return certSignBufKmc;
    }

    /**
     * 设置certSignBufKmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSignBufKmc(String value) {
        this.certSignBufKmc = value;
    }

    /**
     * 获取certSignBufP7Kmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSignBufP7Kmc() {
        return certSignBufP7Kmc;
    }

    /**
     * 设置certSignBufP7Kmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSignBufP7Kmc(String value) {
        this.certSignBufP7Kmc = value;
    }

    /**
     * 获取certSignBufP7属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSignBufP7() {
        return certSignBufP7;
    }

    /**
     * 设置certSignBufP7属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSignBufP7(String value) {
        this.certSignBufP7 = value;
    }

    /**
     * 获取certSerialnumberKmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertSerialnumberKmc() {
        return certSerialnumberKmc;
    }

    /**
     * 设置certSerialnumberKmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertSerialnumberKmc(String value) {
        this.certSerialnumberKmc = value;
    }

    /**
     * 获取certTypeKmc属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertTypeKmc() {
        return certTypeKmc;
    }

    /**
     * 设置certTypeKmc属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertTypeKmc(String value) {
        this.certTypeKmc = value;
    }

    /**
     * 获取certReqPublickey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqPublickey() {
        return certReqPublickey;
    }

    /**
     * 设置certReqPublickey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqPublickey(String value) {
        this.certReqPublickey = value;
    }

    /**
     * 获取certReqPublickeyHashMd5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertReqPublickeyHashMd5() {
        return certReqPublickeyHashMd5;
    }

    /**
     * 设置certReqPublickeyHashMd5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertReqPublickeyHashMd5(String value) {
        this.certReqPublickeyHashMd5 = value;
    }

    /**
     * 获取certIdRandom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertIdRandom() {
        return certIdRandom;
    }

    /**
     * 设置certIdRandom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertIdRandom(String value) {
        this.certIdRandom = value;
    }

}
