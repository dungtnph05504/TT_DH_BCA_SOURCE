
package com.nec.asia.nic.dx.idserver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nec.asia.nic.dx.idserver package. 
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

    private final static QName _Verification_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "Verification");
    private final static QName _VerificationResponse_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "VerificationResponse");
    private final static QName _SubjectDto_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "SubjectDto");
    private final static QName _Identification_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "Identification");
    private final static QName _IdentificationResponse_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "IdentificationResponse");
    private final static QName _ImageFingerprintDto_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "ImageFingerprintDto");
    private final static QName _MatchResultDto_QNAME = new QName("http://idserver.dx.nic.asia.nec.com/", "MatchResultDto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nec.asia.nic.dx.idserver
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VerificationResponse }
     * 
     */
    public VerificationResponse createVerificationResponse() {
        return new VerificationResponse();
    }

    /**
     * Create an instance of {@link Verification }
     * 
     */
    public Verification createVerification() {
        return new Verification();
    }

    /**
     * Create an instance of {@link ImageFingerprintDto }
     * 
     */
    public ImageFingerprintDto createImageFingerprintDto() {
        return new ImageFingerprintDto();
    }

    /**
     * Create an instance of {@link MatchResultDto }
     * 
     */
    public MatchResultDto createMatchResultDto() {
        return new MatchResultDto();
    }

    /**
     * Create an instance of {@link Identification }
     * 
     */
    public Identification createIdentification() {
        return new Identification();
    }

    /**
     * Create an instance of {@link SubjectDto }
     * 
     */
    public SubjectDto createSubjectDto() {
        return new SubjectDto();
    }

    /**
     * Create an instance of {@link IdentificationResponse }
     * 
     */
    public IdentificationResponse createIdentificationResponse() {
        return new IdentificationResponse();
    }

    /**
     * Create an instance of {@link ScoreDto }
     * 
     */
    public ScoreDto createScoreDto() {
        return new ScoreDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Verification }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "Verification")
    public JAXBElement<Verification> createVerification(Verification value) {
        return new JAXBElement<Verification>(_Verification_QNAME, Verification.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerificationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "VerificationResponse")
    public JAXBElement<VerificationResponse> createVerificationResponse(VerificationResponse value) {
        return new JAXBElement<VerificationResponse>(_VerificationResponse_QNAME, VerificationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubjectDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "SubjectDto")
    public JAXBElement<SubjectDto> createSubjectDto(SubjectDto value) {
        return new JAXBElement<SubjectDto>(_SubjectDto_QNAME, SubjectDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Identification }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "Identification")
    public JAXBElement<Identification> createIdentification(Identification value) {
        return new JAXBElement<Identification>(_Identification_QNAME, Identification.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "IdentificationResponse")
    public JAXBElement<IdentificationResponse> createIdentificationResponse(IdentificationResponse value) {
        return new JAXBElement<IdentificationResponse>(_IdentificationResponse_QNAME, IdentificationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImageFingerprintDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "ImageFingerprintDto")
    public JAXBElement<ImageFingerprintDto> createImageFingerprintDto(ImageFingerprintDto value) {
        return new JAXBElement<ImageFingerprintDto>(_ImageFingerprintDto_QNAME, ImageFingerprintDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchResultDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://idserver.dx.nic.asia.nec.com/", name = "MatchResultDto")
    public JAXBElement<MatchResultDto> createMatchResultDto(MatchResultDto value) {
        return new JAXBElement<MatchResultDto>(_MatchResultDto_QNAME, MatchResultDto.class, null, value);
    }

}
