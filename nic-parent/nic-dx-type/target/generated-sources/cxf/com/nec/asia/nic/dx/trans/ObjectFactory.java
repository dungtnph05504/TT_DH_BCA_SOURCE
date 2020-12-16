
package com.nec.asia.nic.dx.trans;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nec.asia.nic.dx.trans package. 
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

    private final static QName _BufEppPersonInvestigation_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "BufEppPersonInvestigation");
    private final static QName _BufEppRequest_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "BufEppRequest");
    private final static QName _RejectionData_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "RejectionData");
    private final static QName _Fingerprint_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "Fingerprint");
    private final static QName _FacialImage_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "FacialImage");
    private final static QName _PersonDetail_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "PersonDetail");
    private final static QName _MainTransaction_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "MainTransaction");
    private final static QName _TransactionRetrievalResult_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "TransactionRetrievalResult");
    private final static QName _EditDataRegistration_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "EditDataRegistration");
    private final static QName _FingerprintInfo_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "FingerprintInfo");
    private final static QName _SignatureInfo_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "SignatureInfo");
    private final static QName _FacialInfo_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "FacialInfo");
    private final static QName _BufEppPersonDoc_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "BufEppPersonDoc");
    private final static QName _PaymentDetail_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "PaymentDetail");
    private final static QName _PaymentInfo_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "PaymentInfo");
    private final static QName _TransactionLog_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "TransactionLog");
    private final static QName _IssuanceData_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "IssuanceData");
    private final static QName _RegistrationData_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "RegistrationData");
    private final static QName _BufEppPerson_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "BufEppPerson");
    private final static QName _BufEppListRequest_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "BufEppListRequest");
    private final static QName _BufEppDataPerson_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "BufEppDataPerson");
    private final static QName _TransactionRetrievalFilter_QNAME = new QName("http://trans.dx.nic.asia.nec.com/", "TransactionRetrievalFilter");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nec.asia.nic.dx.trans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PaymentDetail }
     * 
     */
    public PaymentDetail createPaymentDetail() {
        return new PaymentDetail();
    }

    /**
     * Create an instance of {@link BufEppPersonDoc }
     * 
     */
    public BufEppPersonDoc createBufEppPersonDoc() {
        return new BufEppPersonDoc();
    }

    /**
     * Create an instance of {@link BufEppRequest }
     * 
     */
    public BufEppRequest createBufEppRequest() {
        return new BufEppRequest();
    }

    /**
     * Create an instance of {@link FacialInfo }
     * 
     */
    public FacialInfo createFacialInfo() {
        return new FacialInfo();
    }

    /**
     * Create an instance of {@link BufEppPersonInvestigation }
     * 
     */
    public BufEppPersonInvestigation createBufEppPersonInvestigation() {
        return new BufEppPersonInvestigation();
    }

    /**
     * Create an instance of {@link FacialImage }
     * 
     */
    public FacialImage createFacialImage() {
        return new FacialImage();
    }

    /**
     * Create an instance of {@link PaymentInfo }
     * 
     */
    public PaymentInfo createPaymentInfo() {
        return new PaymentInfo();
    }

    /**
     * Create an instance of {@link RejectionData }
     * 
     */
    public RejectionData createRejectionData() {
        return new RejectionData();
    }

    /**
     * Create an instance of {@link Fingerprint }
     * 
     */
    public Fingerprint createFingerprint() {
        return new Fingerprint();
    }

    /**
     * Create an instance of {@link BufEppPerson }
     * 
     */
    public BufEppPerson createBufEppPerson() {
        return new BufEppPerson();
    }

    /**
     * Create an instance of {@link MainTransaction }
     * 
     */
    public MainTransaction createMainTransaction() {
        return new MainTransaction();
    }

    /**
     * Create an instance of {@link RegistrationData }
     * 
     */
    public RegistrationData createRegistrationData() {
        return new RegistrationData();
    }

    /**
     * Create an instance of {@link BufEppListRequest }
     * 
     */
    public BufEppListRequest createBufEppListRequest() {
        return new BufEppListRequest();
    }

    /**
     * Create an instance of {@link TransactionRetrievalResult }
     * 
     */
    public TransactionRetrievalResult createTransactionRetrievalResult() {
        return new TransactionRetrievalResult();
    }

    /**
     * Create an instance of {@link IssuanceData }
     * 
     */
    public IssuanceData createIssuanceData() {
        return new IssuanceData();
    }

    /**
     * Create an instance of {@link PersonDetail }
     * 
     */
    public PersonDetail createPersonDetail() {
        return new PersonDetail();
    }

    /**
     * Create an instance of {@link TransactionLog }
     * 
     */
    public TransactionLog createTransactionLog() {
        return new TransactionLog();
    }

    /**
     * Create an instance of {@link EditDataRegistration }
     * 
     */
    public EditDataRegistration createEditDataRegistration() {
        return new EditDataRegistration();
    }

    /**
     * Create an instance of {@link BufEppDataPerson }
     * 
     */
    public BufEppDataPerson createBufEppDataPerson() {
        return new BufEppDataPerson();
    }

    /**
     * Create an instance of {@link TransactionRetrievalFilter }
     * 
     */
    public TransactionRetrievalFilter createTransactionRetrievalFilter() {
        return new TransactionRetrievalFilter();
    }

    /**
     * Create an instance of {@link FingerprintInfo }
     * 
     */
    public FingerprintInfo createFingerprintInfo() {
        return new FingerprintInfo();
    }

    /**
     * Create an instance of {@link SignatureInfo }
     * 
     */
    public SignatureInfo createSignatureInfo() {
        return new SignatureInfo();
    }

    /**
     * Create an instance of {@link TransactionLogUpload }
     * 
     */
    public TransactionLogUpload createTransactionLogUpload() {
        return new TransactionLogUpload();
    }

    /**
     * Create an instance of {@link ReferenceDocument }
     * 
     */
    public ReferenceDocument createReferenceDocument() {
        return new ReferenceDocument();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link NicPersoInfoDownloadFilter }
     * 
     */
    public NicPersoInfoDownloadFilter createNicPersoInfoDownloadFilter() {
        return new NicPersoInfoDownloadFilter();
    }

    /**
     * Create an instance of {@link TransactionLogUploadResponse }
     * 
     */
    public TransactionLogUploadResponse createTransactionLogUploadResponse() {
        return new TransactionLogUploadResponse();
    }

    /**
     * Create an instance of {@link BufEppListResponse }
     * 
     */
    public BufEppListResponse createBufEppListResponse() {
        return new BufEppListResponse();
    }

    /**
     * Create an instance of {@link NicPersoInfoRef }
     * 
     */
    public NicPersoInfoRef createNicPersoInfoRef() {
        return new NicPersoInfoRef();
    }

    /**
     * Create an instance of {@link NicPersoInfoDownloadResult }
     * 
     */
    public NicPersoInfoDownloadResult createNicPersoInfoDownloadResult() {
        return new NicPersoInfoDownloadResult();
    }

    /**
     * Create an instance of {@link UpdateReceivedNicPersoInfo }
     * 
     */
    public UpdateReceivedNicPersoInfo createUpdateReceivedNicPersoInfo() {
        return new UpdateReceivedNicPersoInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BufEppPersonInvestigation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "BufEppPersonInvestigation")
    public JAXBElement<BufEppPersonInvestigation> createBufEppPersonInvestigation(BufEppPersonInvestigation value) {
        return new JAXBElement<BufEppPersonInvestigation>(_BufEppPersonInvestigation_QNAME, BufEppPersonInvestigation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BufEppRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "BufEppRequest")
    public JAXBElement<BufEppRequest> createBufEppRequest(BufEppRequest value) {
        return new JAXBElement<BufEppRequest>(_BufEppRequest_QNAME, BufEppRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RejectionData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "RejectionData")
    public JAXBElement<RejectionData> createRejectionData(RejectionData value) {
        return new JAXBElement<RejectionData>(_RejectionData_QNAME, RejectionData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Fingerprint }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "Fingerprint")
    public JAXBElement<Fingerprint> createFingerprint(Fingerprint value) {
        return new JAXBElement<Fingerprint>(_Fingerprint_QNAME, Fingerprint.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FacialImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "FacialImage")
    public JAXBElement<FacialImage> createFacialImage(FacialImage value) {
        return new JAXBElement<FacialImage>(_FacialImage_QNAME, FacialImage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "PersonDetail")
    public JAXBElement<PersonDetail> createPersonDetail(PersonDetail value) {
        return new JAXBElement<PersonDetail>(_PersonDetail_QNAME, PersonDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MainTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "MainTransaction")
    public JAXBElement<MainTransaction> createMainTransaction(MainTransaction value) {
        return new JAXBElement<MainTransaction>(_MainTransaction_QNAME, MainTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionRetrievalResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "TransactionRetrievalResult")
    public JAXBElement<TransactionRetrievalResult> createTransactionRetrievalResult(TransactionRetrievalResult value) {
        return new JAXBElement<TransactionRetrievalResult>(_TransactionRetrievalResult_QNAME, TransactionRetrievalResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditDataRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "EditDataRegistration")
    public JAXBElement<EditDataRegistration> createEditDataRegistration(EditDataRegistration value) {
        return new JAXBElement<EditDataRegistration>(_EditDataRegistration_QNAME, EditDataRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FingerprintInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "FingerprintInfo")
    public JAXBElement<FingerprintInfo> createFingerprintInfo(FingerprintInfo value) {
        return new JAXBElement<FingerprintInfo>(_FingerprintInfo_QNAME, FingerprintInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignatureInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "SignatureInfo")
    public JAXBElement<SignatureInfo> createSignatureInfo(SignatureInfo value) {
        return new JAXBElement<SignatureInfo>(_SignatureInfo_QNAME, SignatureInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FacialInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "FacialInfo")
    public JAXBElement<FacialInfo> createFacialInfo(FacialInfo value) {
        return new JAXBElement<FacialInfo>(_FacialInfo_QNAME, FacialInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BufEppPersonDoc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "BufEppPersonDoc")
    public JAXBElement<BufEppPersonDoc> createBufEppPersonDoc(BufEppPersonDoc value) {
        return new JAXBElement<BufEppPersonDoc>(_BufEppPersonDoc_QNAME, BufEppPersonDoc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "PaymentDetail")
    public JAXBElement<PaymentDetail> createPaymentDetail(PaymentDetail value) {
        return new JAXBElement<PaymentDetail>(_PaymentDetail_QNAME, PaymentDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PaymentInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "PaymentInfo")
    public JAXBElement<PaymentInfo> createPaymentInfo(PaymentInfo value) {
        return new JAXBElement<PaymentInfo>(_PaymentInfo_QNAME, PaymentInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "TransactionLog")
    public JAXBElement<TransactionLog> createTransactionLog(TransactionLog value) {
        return new JAXBElement<TransactionLog>(_TransactionLog_QNAME, TransactionLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IssuanceData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "IssuanceData")
    public JAXBElement<IssuanceData> createIssuanceData(IssuanceData value) {
        return new JAXBElement<IssuanceData>(_IssuanceData_QNAME, IssuanceData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistrationData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "RegistrationData")
    public JAXBElement<RegistrationData> createRegistrationData(RegistrationData value) {
        return new JAXBElement<RegistrationData>(_RegistrationData_QNAME, RegistrationData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BufEppPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "BufEppPerson")
    public JAXBElement<BufEppPerson> createBufEppPerson(BufEppPerson value) {
        return new JAXBElement<BufEppPerson>(_BufEppPerson_QNAME, BufEppPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BufEppListRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "BufEppListRequest")
    public JAXBElement<BufEppListRequest> createBufEppListRequest(BufEppListRequest value) {
        return new JAXBElement<BufEppListRequest>(_BufEppListRequest_QNAME, BufEppListRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BufEppDataPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "BufEppDataPerson")
    public JAXBElement<BufEppDataPerson> createBufEppDataPerson(BufEppDataPerson value) {
        return new JAXBElement<BufEppDataPerson>(_BufEppDataPerson_QNAME, BufEppDataPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionRetrievalFilter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://trans.dx.nic.asia.nec.com/", name = "TransactionRetrievalFilter")
    public JAXBElement<TransactionRetrievalFilter> createTransactionRetrievalFilter(TransactionRetrievalFilter value) {
        return new JAXBElement<TransactionRetrievalFilter>(_TransactionRetrievalFilter_QNAME, TransactionRetrievalFilter.class, null, value);
    }

}
