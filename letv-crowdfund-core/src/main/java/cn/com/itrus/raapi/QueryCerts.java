
package cn.com.itrus.raapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryCerts complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="queryCerts">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="userInfo" type="{http://service.ra.tca.topca.cn/}userInfo" minOccurs="0"/>
 *         &lt;element name="certInfo" type="{http://service.ra.tca.topca.cn/}certInfo" minOccurs="0"/>
 *         &lt;element name="pageIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="pageSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sqlTemplateFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nextResultTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalTag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountHash" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryCerts", propOrder = {
    "userInfo",
    "certInfo",
    "pageIndex",
    "pageSize",
    "sqlTemplateFileName",
    "nextResultTag",
    "totalTag",
    "accountHash"
})
public class QueryCerts {

    protected UserInfo userInfo;
    protected CertInfo certInfo;
    protected int pageIndex;
    protected int pageSize;
    protected String sqlTemplateFileName;
    protected String nextResultTag;
    protected String totalTag;
    protected String accountHash;

    /**
     * 获取userInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link UserInfo }
     *     
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 设置userInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link UserInfo }
     *     
     */
    public void setUserInfo(UserInfo value) {
        this.userInfo = value;
    }

    /**
     * 获取certInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CertInfo }
     *     
     */
    public CertInfo getCertInfo() {
        return certInfo;
    }

    /**
     * 设置certInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CertInfo }
     *     
     */
    public void setCertInfo(CertInfo value) {
        this.certInfo = value;
    }

    /**
     * 获取pageIndex属性的值。
     * 
     */
    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * 设置pageIndex属性的值。
     * 
     */
    public void setPageIndex(int value) {
        this.pageIndex = value;
    }

    /**
     * 获取pageSize属性的值。
     * 
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置pageSize属性的值。
     * 
     */
    public void setPageSize(int value) {
        this.pageSize = value;
    }

    /**
     * 获取sqlTemplateFileName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlTemplateFileName() {
        return sqlTemplateFileName;
    }

    /**
     * 设置sqlTemplateFileName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlTemplateFileName(String value) {
        this.sqlTemplateFileName = value;
    }

    /**
     * 获取nextResultTag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNextResultTag() {
        return nextResultTag;
    }

    /**
     * 设置nextResultTag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNextResultTag(String value) {
        this.nextResultTag = value;
    }

    /**
     * 获取totalTag属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalTag() {
        return totalTag;
    }

    /**
     * 设置totalTag属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalTag(String value) {
        this.totalTag = value;
    }

    /**
     * 获取accountHash属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountHash() {
        return accountHash;
    }

    /**
     * 设置accountHash属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountHash(String value) {
        this.accountHash = value;
    }

}
