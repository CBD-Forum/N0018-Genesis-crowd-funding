
package cn.com.itrus.raapi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.com.itrus.raapi package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryCertsResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "queryCertsResponse");
    private final static QName _EnrollCertAA_QNAME = new QName("http://service.ra.tca.topca.cn/", "enrollCertAA");
    private final static QName _DoScriptResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "doScriptResponse");
    private final static QName _RenewCertAAResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "renewCertAAResponse");
    private final static QName _DownloadDeltaCRL_QNAME = new QName("http://service.ra.tca.topca.cn/", "downloadDeltaCRL");
    private final static QName _QueryCertBySerialNumberResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "queryCertBySerialNumberResponse");
    private final static QName _DownloadCRLResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "downloadCRLResponse");
    private final static QName _RaServiceUnavailable_QNAME = new QName("http://service.ra.tca.topca.cn/", "RaServiceUnavailable");
    private final static QName _SuspendCertResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "suspendCertResponse");
    private final static QName _DownloadDeltaCRLResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "downloadDeltaCRLResponse");
    private final static QName _RevokeCertResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "revokeCertResponse");
    private final static QName _DoScript_QNAME = new QName("http://service.ra.tca.topca.cn/", "doScript");
    private final static QName _QueryCertBySerialNumber_QNAME = new QName("http://service.ra.tca.topca.cn/", "queryCertBySerialNumber");
    private final static QName _EnrollCertResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "enrollCertResponse");
    private final static QName _PickupCertResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "pickupCertResponse");
    private final static QName _UnsuspendCertResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "unsuspendCertResponse");
    private final static QName _EnrollCert_QNAME = new QName("http://service.ra.tca.topca.cn/", "enrollCert");
    private final static QName _EnrollCertAAResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "enrollCertAAResponse");
    private final static QName _DownloadCAResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "downloadCAResponse");
    private final static QName _DownloadCA_QNAME = new QName("http://service.ra.tca.topca.cn/", "downloadCA");
    private final static QName _QueryCertByCertId_QNAME = new QName("http://service.ra.tca.topca.cn/", "queryCertByCertId");
    private final static QName _QueryCerts_QNAME = new QName("http://service.ra.tca.topca.cn/", "queryCerts");
    private final static QName _SynchroTemplate_QNAME = new QName("http://service.ra.tca.topca.cn/", "synchroTemplate");
    private final static QName _SynchroTemplateResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "synchroTemplateResponse");
    private final static QName _RevokeCert_QNAME = new QName("http://service.ra.tca.topca.cn/", "revokeCert");
    private final static QName _RenewCertResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "renewCertResponse");
    private final static QName _RenewCertAA_QNAME = new QName("http://service.ra.tca.topca.cn/", "renewCertAA");
    private final static QName _UnsuspendCert_QNAME = new QName("http://service.ra.tca.topca.cn/", "unsuspendCert");
    private final static QName _PickupCert_QNAME = new QName("http://service.ra.tca.topca.cn/", "pickupCert");
    private final static QName _RenewCert_QNAME = new QName("http://service.ra.tca.topca.cn/", "renewCert");
    private final static QName _SuspendCert_QNAME = new QName("http://service.ra.tca.topca.cn/", "suspendCert");
    private final static QName _QueryCertByCertIdResponse_QNAME = new QName("http://service.ra.tca.topca.cn/", "queryCertByCertIdResponse");
    private final static QName _DownloadCRL_QNAME = new QName("http://service.ra.tca.topca.cn/", "downloadCRL");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.com.itrus.raapi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UnsuspendCert }
     * 
     */
    public UnsuspendCert createUnsuspendCert() {
        return new UnsuspendCert();
    }

    /**
     * Create an instance of {@link RenewCertAA }
     * 
     */
    public RenewCertAA createRenewCertAA() {
        return new RenewCertAA();
    }

    /**
     * Create an instance of {@link PickupCert }
     * 
     */
    public PickupCert createPickupCert() {
        return new PickupCert();
    }

    /**
     * Create an instance of {@link DownloadCRL }
     * 
     */
    public DownloadCRL createDownloadCRL() {
        return new DownloadCRL();
    }

    /**
     * Create an instance of {@link QueryCertByCertIdResponse }
     * 
     */
    public QueryCertByCertIdResponse createQueryCertByCertIdResponse() {
        return new QueryCertByCertIdResponse();
    }

    /**
     * Create an instance of {@link SuspendCert }
     * 
     */
    public SuspendCert createSuspendCert() {
        return new SuspendCert();
    }

    /**
     * Create an instance of {@link RenewCert }
     * 
     */
    public RenewCert createRenewCert() {
        return new RenewCert();
    }

    /**
     * Create an instance of {@link SynchroTemplate }
     * 
     */
    public SynchroTemplate createSynchroTemplate() {
        return new SynchroTemplate();
    }

    /**
     * Create an instance of {@link QueryCerts }
     * 
     */
    public QueryCerts createQueryCerts() {
        return new QueryCerts();
    }

    /**
     * Create an instance of {@link QueryCertByCertId }
     * 
     */
    public QueryCertByCertId createQueryCertByCertId() {
        return new QueryCertByCertId();
    }

    /**
     * Create an instance of {@link RenewCertResponse }
     * 
     */
    public RenewCertResponse createRenewCertResponse() {
        return new RenewCertResponse();
    }

    /**
     * Create an instance of {@link RevokeCert }
     * 
     */
    public RevokeCert createRevokeCert() {
        return new RevokeCert();
    }

    /**
     * Create an instance of {@link SynchroTemplateResponse }
     * 
     */
    public SynchroTemplateResponse createSynchroTemplateResponse() {
        return new SynchroTemplateResponse();
    }

    /**
     * Create an instance of {@link RevokeCertResponse }
     * 
     */
    public RevokeCertResponse createRevokeCertResponse() {
        return new RevokeCertResponse();
    }

    /**
     * Create an instance of {@link SuspendCertResponse }
     * 
     */
    public SuspendCertResponse createSuspendCertResponse() {
        return new SuspendCertResponse();
    }

    /**
     * Create an instance of {@link DownloadDeltaCRLResponse }
     * 
     */
    public DownloadDeltaCRLResponse createDownloadDeltaCRLResponse() {
        return new DownloadDeltaCRLResponse();
    }

    /**
     * Create an instance of {@link DoScript }
     * 
     */
    public DoScript createDoScript() {
        return new DoScript();
    }

    /**
     * Create an instance of {@link QueryCertBySerialNumber }
     * 
     */
    public QueryCertBySerialNumber createQueryCertBySerialNumber() {
        return new QueryCertBySerialNumber();
    }

    /**
     * Create an instance of {@link UnsuspendCertResponse }
     * 
     */
    public UnsuspendCertResponse createUnsuspendCertResponse() {
        return new UnsuspendCertResponse();
    }

    /**
     * Create an instance of {@link EnrollCertResponse }
     * 
     */
    public EnrollCertResponse createEnrollCertResponse() {
        return new EnrollCertResponse();
    }

    /**
     * Create an instance of {@link PickupCertResponse }
     * 
     */
    public PickupCertResponse createPickupCertResponse() {
        return new PickupCertResponse();
    }

    /**
     * Create an instance of {@link DownloadCA }
     * 
     */
    public DownloadCA createDownloadCA() {
        return new DownloadCA();
    }

    /**
     * Create an instance of {@link EnrollCertAAResponse }
     * 
     */
    public EnrollCertAAResponse createEnrollCertAAResponse() {
        return new EnrollCertAAResponse();
    }

    /**
     * Create an instance of {@link DownloadCAResponse }
     * 
     */
    public DownloadCAResponse createDownloadCAResponse() {
        return new DownloadCAResponse();
    }

    /**
     * Create an instance of {@link EnrollCert }
     * 
     */
    public EnrollCert createEnrollCert() {
        return new EnrollCert();
    }

    /**
     * Create an instance of {@link EnrollCertAA }
     * 
     */
    public EnrollCertAA createEnrollCertAA() {
        return new EnrollCertAA();
    }

    /**
     * Create an instance of {@link QueryCertsResponse }
     * 
     */
    public QueryCertsResponse createQueryCertsResponse() {
        return new QueryCertsResponse();
    }

    /**
     * Create an instance of {@link RenewCertAAResponse }
     * 
     */
    public RenewCertAAResponse createRenewCertAAResponse() {
        return new RenewCertAAResponse();
    }

    /**
     * Create an instance of {@link DoScriptResponse }
     * 
     */
    public DoScriptResponse createDoScriptResponse() {
        return new DoScriptResponse();
    }

    /**
     * Create an instance of {@link DownloadDeltaCRL }
     * 
     */
    public DownloadDeltaCRL createDownloadDeltaCRL() {
        return new DownloadDeltaCRL();
    }

    /**
     * Create an instance of {@link RaServiceUnavailable }
     * 
     */
    public RaServiceUnavailable createRaServiceUnavailable() {
        return new RaServiceUnavailable();
    }

    /**
     * Create an instance of {@link DownloadCRLResponse }
     * 
     */
    public DownloadCRLResponse createDownloadCRLResponse() {
        return new DownloadCRLResponse();
    }

    /**
     * Create an instance of {@link QueryCertBySerialNumberResponse }
     * 
     */
    public QueryCertBySerialNumberResponse createQueryCertBySerialNumberResponse() {
        return new QueryCertBySerialNumberResponse();
    }

    /**
     * Create an instance of {@link AccountConfigResult }
     * 
     */
    public AccountConfigResult createAccountConfigResult() {
        return new AccountConfigResult();
    }

    /**
     * Create an instance of {@link CertInfo }
     * 
     */
    public CertInfo createCertInfo() {
        return new CertInfo();
    }

    /**
     * Create an instance of {@link UserInfo }
     * 
     */
    public UserInfo createUserInfo() {
        return new UserInfo();
    }

    /**
     * Create an instance of {@link QueryCertResult }
     * 
     */
    public QueryCertResult createQueryCertResult() {
        return new QueryCertResult();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCertsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "queryCertsResponse")
    public JAXBElement<QueryCertsResponse> createQueryCertsResponse(QueryCertsResponse value) {
        return new JAXBElement<QueryCertsResponse>(_QueryCertsResponse_QNAME, QueryCertsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrollCertAA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "enrollCertAA")
    public JAXBElement<EnrollCertAA> createEnrollCertAA(EnrollCertAA value) {
        return new JAXBElement<EnrollCertAA>(_EnrollCertAA_QNAME, EnrollCertAA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoScriptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "doScriptResponse")
    public JAXBElement<DoScriptResponse> createDoScriptResponse(DoScriptResponse value) {
        return new JAXBElement<DoScriptResponse>(_DoScriptResponse_QNAME, DoScriptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewCertAAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "renewCertAAResponse")
    public JAXBElement<RenewCertAAResponse> createRenewCertAAResponse(RenewCertAAResponse value) {
        return new JAXBElement<RenewCertAAResponse>(_RenewCertAAResponse_QNAME, RenewCertAAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadDeltaCRL }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "downloadDeltaCRL")
    public JAXBElement<DownloadDeltaCRL> createDownloadDeltaCRL(DownloadDeltaCRL value) {
        return new JAXBElement<DownloadDeltaCRL>(_DownloadDeltaCRL_QNAME, DownloadDeltaCRL.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCertBySerialNumberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "queryCertBySerialNumberResponse")
    public JAXBElement<QueryCertBySerialNumberResponse> createQueryCertBySerialNumberResponse(QueryCertBySerialNumberResponse value) {
        return new JAXBElement<QueryCertBySerialNumberResponse>(_QueryCertBySerialNumberResponse_QNAME, QueryCertBySerialNumberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadCRLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "downloadCRLResponse")
    public JAXBElement<DownloadCRLResponse> createDownloadCRLResponse(DownloadCRLResponse value) {
        return new JAXBElement<DownloadCRLResponse>(_DownloadCRLResponse_QNAME, DownloadCRLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RaServiceUnavailable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "RaServiceUnavailable")
    public JAXBElement<RaServiceUnavailable> createRaServiceUnavailable(RaServiceUnavailable value) {
        return new JAXBElement<RaServiceUnavailable>(_RaServiceUnavailable_QNAME, RaServiceUnavailable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuspendCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "suspendCertResponse")
    public JAXBElement<SuspendCertResponse> createSuspendCertResponse(SuspendCertResponse value) {
        return new JAXBElement<SuspendCertResponse>(_SuspendCertResponse_QNAME, SuspendCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadDeltaCRLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "downloadDeltaCRLResponse")
    public JAXBElement<DownloadDeltaCRLResponse> createDownloadDeltaCRLResponse(DownloadDeltaCRLResponse value) {
        return new JAXBElement<DownloadDeltaCRLResponse>(_DownloadDeltaCRLResponse_QNAME, DownloadDeltaCRLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevokeCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "revokeCertResponse")
    public JAXBElement<RevokeCertResponse> createRevokeCertResponse(RevokeCertResponse value) {
        return new JAXBElement<RevokeCertResponse>(_RevokeCertResponse_QNAME, RevokeCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoScript }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "doScript")
    public JAXBElement<DoScript> createDoScript(DoScript value) {
        return new JAXBElement<DoScript>(_DoScript_QNAME, DoScript.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCertBySerialNumber }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "queryCertBySerialNumber")
    public JAXBElement<QueryCertBySerialNumber> createQueryCertBySerialNumber(QueryCertBySerialNumber value) {
        return new JAXBElement<QueryCertBySerialNumber>(_QueryCertBySerialNumber_QNAME, QueryCertBySerialNumber.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrollCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "enrollCertResponse")
    public JAXBElement<EnrollCertResponse> createEnrollCertResponse(EnrollCertResponse value) {
        return new JAXBElement<EnrollCertResponse>(_EnrollCertResponse_QNAME, EnrollCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PickupCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "pickupCertResponse")
    public JAXBElement<PickupCertResponse> createPickupCertResponse(PickupCertResponse value) {
        return new JAXBElement<PickupCertResponse>(_PickupCertResponse_QNAME, PickupCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsuspendCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "unsuspendCertResponse")
    public JAXBElement<UnsuspendCertResponse> createUnsuspendCertResponse(UnsuspendCertResponse value) {
        return new JAXBElement<UnsuspendCertResponse>(_UnsuspendCertResponse_QNAME, UnsuspendCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrollCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "enrollCert")
    public JAXBElement<EnrollCert> createEnrollCert(EnrollCert value) {
        return new JAXBElement<EnrollCert>(_EnrollCert_QNAME, EnrollCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrollCertAAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "enrollCertAAResponse")
    public JAXBElement<EnrollCertAAResponse> createEnrollCertAAResponse(EnrollCertAAResponse value) {
        return new JAXBElement<EnrollCertAAResponse>(_EnrollCertAAResponse_QNAME, EnrollCertAAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadCAResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "downloadCAResponse")
    public JAXBElement<DownloadCAResponse> createDownloadCAResponse(DownloadCAResponse value) {
        return new JAXBElement<DownloadCAResponse>(_DownloadCAResponse_QNAME, DownloadCAResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadCA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "downloadCA")
    public JAXBElement<DownloadCA> createDownloadCA(DownloadCA value) {
        return new JAXBElement<DownloadCA>(_DownloadCA_QNAME, DownloadCA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCertByCertId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "queryCertByCertId")
    public JAXBElement<QueryCertByCertId> createQueryCertByCertId(QueryCertByCertId value) {
        return new JAXBElement<QueryCertByCertId>(_QueryCertByCertId_QNAME, QueryCertByCertId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCerts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "queryCerts")
    public JAXBElement<QueryCerts> createQueryCerts(QueryCerts value) {
        return new JAXBElement<QueryCerts>(_QueryCerts_QNAME, QueryCerts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SynchroTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "synchroTemplate")
    public JAXBElement<SynchroTemplate> createSynchroTemplate(SynchroTemplate value) {
        return new JAXBElement<SynchroTemplate>(_SynchroTemplate_QNAME, SynchroTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SynchroTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "synchroTemplateResponse")
    public JAXBElement<SynchroTemplateResponse> createSynchroTemplateResponse(SynchroTemplateResponse value) {
        return new JAXBElement<SynchroTemplateResponse>(_SynchroTemplateResponse_QNAME, SynchroTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RevokeCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "revokeCert")
    public JAXBElement<RevokeCert> createRevokeCert(RevokeCert value) {
        return new JAXBElement<RevokeCert>(_RevokeCert_QNAME, RevokeCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "renewCertResponse")
    public JAXBElement<RenewCertResponse> createRenewCertResponse(RenewCertResponse value) {
        return new JAXBElement<RenewCertResponse>(_RenewCertResponse_QNAME, RenewCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewCertAA }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "renewCertAA")
    public JAXBElement<RenewCertAA> createRenewCertAA(RenewCertAA value) {
        return new JAXBElement<RenewCertAA>(_RenewCertAA_QNAME, RenewCertAA.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsuspendCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "unsuspendCert")
    public JAXBElement<UnsuspendCert> createUnsuspendCert(UnsuspendCert value) {
        return new JAXBElement<UnsuspendCert>(_UnsuspendCert_QNAME, UnsuspendCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PickupCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "pickupCert")
    public JAXBElement<PickupCert> createPickupCert(PickupCert value) {
        return new JAXBElement<PickupCert>(_PickupCert_QNAME, PickupCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "renewCert")
    public JAXBElement<RenewCert> createRenewCert(RenewCert value) {
        return new JAXBElement<RenewCert>(_RenewCert_QNAME, RenewCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuspendCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "suspendCert")
    public JAXBElement<SuspendCert> createSuspendCert(SuspendCert value) {
        return new JAXBElement<SuspendCert>(_SuspendCert_QNAME, SuspendCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryCertByCertIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "queryCertByCertIdResponse")
    public JAXBElement<QueryCertByCertIdResponse> createQueryCertByCertIdResponse(QueryCertByCertIdResponse value) {
        return new JAXBElement<QueryCertByCertIdResponse>(_QueryCertByCertIdResponse_QNAME, QueryCertByCertIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadCRL }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ra.tca.topca.cn/", name = "downloadCRL")
    public JAXBElement<DownloadCRL> createDownloadCRL(DownloadCRL value) {
        return new JAXBElement<DownloadCRL>(_DownloadCRL_QNAME, DownloadCRL.class, null, value);
    }

}
