
package cn.com.itrus.raapi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>queryCertResult complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="queryCertResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="totalSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="certInfos" type="{http://service.ra.tca.topca.cn/}certInfo" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="userInfos" type="{http://service.ra.tca.topca.cn/}userInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryCertResult", propOrder = {
    "totalSize",
    "certInfos",
    "userInfos"
})
public class QueryCertResult {

    protected int totalSize;
    @XmlElement(nillable = true)
    protected List<CertInfo> certInfos;
    @XmlElement(nillable = true)
    protected List<UserInfo> userInfos;

    /**
     * 获取totalSize属性的值。
     * 
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * 设置totalSize属性的值。
     * 
     */
    public void setTotalSize(int value) {
        this.totalSize = value;
    }

    /**
     * Gets the value of the certInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the certInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCertInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CertInfo }
     * 
     * 
     */
    public List<CertInfo> getCertInfos() {
        if (certInfos == null) {
            certInfos = new ArrayList<CertInfo>();
        }
        return this.certInfos;
    }

    /**
     * Gets the value of the userInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserInfo }
     * 
     * 
     */
    public List<UserInfo> getUserInfos() {
        if (userInfos == null) {
            userInfos = new ArrayList<UserInfo>();
        }
        return this.userInfos;
    }

}
