
package cn.com.itrus.raapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>userInfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="userInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userSerialnumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userOrganization" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userOrgunit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userLocality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userStreet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userDns" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userIp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userAdditionalField10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userInfo", propOrder = {
    "accountId",
    "userName",
    "userSurname",
    "userSerialnumber",
    "userEmail",
    "userOrganization",
    "userOrgunit",
    "userCountry",
    "userState",
    "userLocality",
    "userStreet",
    "userDns",
    "userIp",
    "userTitle",
    "userDescription",
    "userAdditionalField1",
    "userAdditionalField2",
    "userAdditionalField3",
    "userAdditionalField4",
    "userAdditionalField5",
    "userAdditionalField6",
    "userAdditionalField7",
    "userAdditionalField8",
    "userAdditionalField9",
    "userAdditionalField10"
})
public class UserInfo {

    protected int accountId;
    protected String userName;
    protected String userSurname;
    protected String userSerialnumber;
    protected String userEmail;
    protected String userOrganization;
    protected String userOrgunit;
    protected String userCountry;
    protected String userState;
    protected String userLocality;
    protected String userStreet;
    protected String userDns;
    protected String userIp;
    protected String userTitle;
    protected String userDescription;
    protected String userAdditionalField1;
    protected String userAdditionalField2;
    protected String userAdditionalField3;
    protected String userAdditionalField4;
    protected String userAdditionalField5;
    protected String userAdditionalField6;
    protected String userAdditionalField7;
    protected String userAdditionalField8;
    protected String userAdditionalField9;
    protected String userAdditionalField10;

    /**
     * 获取accountId属性的值。
     * 
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * 设置accountId属性的值。
     * 
     */
    public void setAccountId(int value) {
        this.accountId = value;
    }

    /**
     * 获取userName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置userName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * 获取userSurname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSurname() {
        return userSurname;
    }

    /**
     * 设置userSurname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSurname(String value) {
        this.userSurname = value;
    }

    /**
     * 获取userSerialnumber属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSerialnumber() {
        return userSerialnumber;
    }

    /**
     * 设置userSerialnumber属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSerialnumber(String value) {
        this.userSerialnumber = value;
    }

    /**
     * 获取userEmail属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * 设置userEmail属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserEmail(String value) {
        this.userEmail = value;
    }

    /**
     * 获取userOrganization属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserOrganization() {
        return userOrganization;
    }

    /**
     * 设置userOrganization属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserOrganization(String value) {
        this.userOrganization = value;
    }

    /**
     * 获取userOrgunit属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserOrgunit() {
        return userOrgunit;
    }

    /**
     * 设置userOrgunit属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserOrgunit(String value) {
        this.userOrgunit = value;
    }

    /**
     * 获取userCountry属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCountry() {
        return userCountry;
    }

    /**
     * 设置userCountry属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCountry(String value) {
        this.userCountry = value;
    }

    /**
     * 获取userState属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserState() {
        return userState;
    }

    /**
     * 设置userState属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserState(String value) {
        this.userState = value;
    }

    /**
     * 获取userLocality属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserLocality() {
        return userLocality;
    }

    /**
     * 设置userLocality属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserLocality(String value) {
        this.userLocality = value;
    }

    /**
     * 获取userStreet属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserStreet() {
        return userStreet;
    }

    /**
     * 设置userStreet属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserStreet(String value) {
        this.userStreet = value;
    }

    /**
     * 获取userDns属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDns() {
        return userDns;
    }

    /**
     * 设置userDns属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDns(String value) {
        this.userDns = value;
    }

    /**
     * 获取userIp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     * 设置userIp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserIp(String value) {
        this.userIp = value;
    }

    /**
     * 获取userTitle属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserTitle() {
        return userTitle;
    }

    /**
     * 设置userTitle属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserTitle(String value) {
        this.userTitle = value;
    }

    /**
     * 获取userDescription属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDescription() {
        return userDescription;
    }

    /**
     * 设置userDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDescription(String value) {
        this.userDescription = value;
    }

    /**
     * 获取userAdditionalField1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField1() {
        return userAdditionalField1;
    }

    /**
     * 设置userAdditionalField1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField1(String value) {
        this.userAdditionalField1 = value;
    }

    /**
     * 获取userAdditionalField2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField2() {
        return userAdditionalField2;
    }

    /**
     * 设置userAdditionalField2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField2(String value) {
        this.userAdditionalField2 = value;
    }

    /**
     * 获取userAdditionalField3属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField3() {
        return userAdditionalField3;
    }

    /**
     * 设置userAdditionalField3属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField3(String value) {
        this.userAdditionalField3 = value;
    }

    /**
     * 获取userAdditionalField4属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField4() {
        return userAdditionalField4;
    }

    /**
     * 设置userAdditionalField4属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField4(String value) {
        this.userAdditionalField4 = value;
    }

    /**
     * 获取userAdditionalField5属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField5() {
        return userAdditionalField5;
    }

    /**
     * 设置userAdditionalField5属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField5(String value) {
        this.userAdditionalField5 = value;
    }

    /**
     * 获取userAdditionalField6属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField6() {
        return userAdditionalField6;
    }

    /**
     * 设置userAdditionalField6属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField6(String value) {
        this.userAdditionalField6 = value;
    }

    /**
     * 获取userAdditionalField7属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField7() {
        return userAdditionalField7;
    }

    /**
     * 设置userAdditionalField7属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField7(String value) {
        this.userAdditionalField7 = value;
    }

    /**
     * 获取userAdditionalField8属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField8() {
        return userAdditionalField8;
    }

    /**
     * 设置userAdditionalField8属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField8(String value) {
        this.userAdditionalField8 = value;
    }

    /**
     * 获取userAdditionalField9属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField9() {
        return userAdditionalField9;
    }

    /**
     * 设置userAdditionalField9属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField9(String value) {
        this.userAdditionalField9 = value;
    }

    /**
     * 获取userAdditionalField10属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserAdditionalField10() {
        return userAdditionalField10;
    }

    /**
     * 设置userAdditionalField10属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserAdditionalField10(String value) {
        this.userAdditionalField10 = value;
    }

}
