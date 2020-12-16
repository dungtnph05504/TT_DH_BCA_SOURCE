
package org.tempuri;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the authen package. 
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
public class ObjectFactoryAuthen {

    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Singlestring_QNAME = new QName("http://schemas.datacontract.org/2004/07/Vietbando.MessageType", "Singlestring");
    private final static QName _Error_QNAME = new QName("http://schemas.datacontract.org/2004/07/Vietbando.MessageType", "Error");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Response_QNAME = new QName("http://schemas.datacontract.org/2004/07/Vietbando.MessageType", "Response");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _Credential_QNAME = new QName("http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", "Credential");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _GetAccessTokenDigestResponseGetAccessTokenDigestResult_QNAME = new QName("http://tempuri.org/", "GetAccessTokenDigestResult");
    private final static QName _GetAccessTokenAdvangeCres_QNAME = new QName("http://tempuri.org/", "cres");
    private final static QName _GetAccessTokenAdvangeResponseGetAccessTokenAdvangeResult_QNAME = new QName("http://tempuri.org/", "GetAccessTokenAdvangeResult");
    private final static QName _SinglestringValue_QNAME = new QName("http://schemas.datacontract.org/2004/07/Vietbando.MessageType", "Value");
    private final static QName _GetAccessTokenResponseGetAccessTokenResult_QNAME = new QName("http://tempuri.org/", "GetAccessTokenResult");
    private final static QName _CredentialHash_QNAME = new QName("http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", "Hash");
    private final static QName _CredentialUsername_QNAME = new QName("http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", "Username");
    private final static QName _CredentialPassword_QNAME = new QName("http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", "Password");
    private final static QName _CredentialRealm_QNAME = new QName("http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", "Realm");
    private final static QName _ErrorExceptionType_QNAME = new QName("http://schemas.datacontract.org/2004/07/Vietbando.MessageType", "ExceptionType");
    private final static QName _ErrorMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/Vietbando.MessageType", "Message");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: authen
     * 
     */
    public ObjectFactoryAuthen() {
    }

    /**
     * Create an instance of {@link GetAccessTokenResponse }
     * 
     */
    public GetAccessTokenResponse createGetAccessTokenResponse() {
        return new GetAccessTokenResponse();
    }

    /**
     * Create an instance of {@link Singlestring }
     * 
     */
    public Singlestring createSinglestring() {
        return new Singlestring();
    }

    /**
     * Create an instance of {@link GetAccessTokenDigest }
     * 
     */
    public GetAccessTokenDigest createGetAccessTokenDigest() {
        return new GetAccessTokenDigest();
    }

    /**
     * Create an instance of {@link Credential }
     * 
     */
    public Credential createCredential() {
        return new Credential();
    }

    /**
     * Create an instance of {@link GetAccessTokenAdvange }
     * 
     */
    public GetAccessTokenAdvange createGetAccessTokenAdvange() {
        return new GetAccessTokenAdvange();
    }

    /**
     * Create an instance of {@link GetAccessToken }
     * 
     */
    public GetAccessToken createGetAccessToken() {
        return new GetAccessToken();
    }

    /**
     * Create an instance of {@link GetAccessTokenDigestResponse }
     * 
     */
    public GetAccessTokenDigestResponse createGetAccessTokenDigestResponse() {
        return new GetAccessTokenDigestResponse();
    }

    /**
     * Create an instance of {@link GetAccessTokenAdvangeResponse }
     * 
     */
    public GetAccessTokenAdvangeResponse createGetAccessTokenAdvangeResponse() {
        return new GetAccessTokenAdvangeResponse();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link Error }
     * 
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Singlestring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "Singlestring")
    public JAXBElement<Singlestring> createSinglestring(Singlestring value) {
        return new JAXBElement<Singlestring>(_Singlestring_QNAME, Singlestring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "Error")
    public JAXBElement<Error> createError(Error value) {
        return new JAXBElement<Error>(_Error_QNAME, Error.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "Response")
    public JAXBElement<Response> createResponse(Response value) {
        return new JAXBElement<Response>(_Response_QNAME, Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Credential }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", name = "Credential")
    public JAXBElement<Credential> createCredential(Credential value) {
        return new JAXBElement<Credential>(_Credential_QNAME, Credential.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Singlestring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetAccessTokenDigestResult", scope = GetAccessTokenDigestResponse.class)
    public JAXBElement<Singlestring> createGetAccessTokenDigestResponseGetAccessTokenDigestResult(Singlestring value) {
        return new JAXBElement<Singlestring>(_GetAccessTokenDigestResponseGetAccessTokenDigestResult_QNAME, Singlestring.class, GetAccessTokenDigestResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Credential }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cres", scope = GetAccessTokenAdvange.class)
    public JAXBElement<Credential> createGetAccessTokenAdvangeCres(Credential value) {
        return new JAXBElement<Credential>(_GetAccessTokenAdvangeCres_QNAME, Credential.class, GetAccessTokenAdvange.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Singlestring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetAccessTokenAdvangeResult", scope = GetAccessTokenAdvangeResponse.class)
    public JAXBElement<Singlestring> createGetAccessTokenAdvangeResponseGetAccessTokenAdvangeResult(Singlestring value) {
        return new JAXBElement<Singlestring>(_GetAccessTokenAdvangeResponseGetAccessTokenAdvangeResult_QNAME, Singlestring.class, GetAccessTokenAdvangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "Value", scope = Singlestring.class)
    public JAXBElement<String> createSinglestringValue(String value) {
        return new JAXBElement<String>(_SinglestringValue_QNAME, String.class, Singlestring.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Singlestring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "GetAccessTokenResult", scope = GetAccessTokenResponse.class)
    public JAXBElement<Singlestring> createGetAccessTokenResponseGetAccessTokenResult(Singlestring value) {
        return new JAXBElement<Singlestring>(_GetAccessTokenResponseGetAccessTokenResult_QNAME, Singlestring.class, GetAccessTokenResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", name = "Hash", scope = Credential.class)
    public JAXBElement<String> createCredentialHash(String value) {
        return new JAXBElement<String>(_CredentialHash_QNAME, String.class, Credential.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", name = "Username", scope = Credential.class)
    public JAXBElement<String> createCredentialUsername(String value) {
        return new JAXBElement<String>(_CredentialUsername_QNAME, String.class, Credential.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", name = "Password", scope = Credential.class)
    public JAXBElement<String> createCredentialPassword(String value) {
        return new JAXBElement<String>(_CredentialPassword_QNAME, String.class, Credential.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VDMS.Web.Library.Provider.Model", name = "Realm", scope = Credential.class)
    public JAXBElement<String> createCredentialRealm(String value) {
        return new JAXBElement<String>(_CredentialRealm_QNAME, String.class, Credential.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Error }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "Error", scope = Response.class)
    public JAXBElement<Error> createResponseError(Error value) {
        return new JAXBElement<Error>(_Error_QNAME, Error.class, Response.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Credential }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cres", scope = GetAccessTokenDigest.class)
    public JAXBElement<Credential> createGetAccessTokenDigestCres(Credential value) {
        return new JAXBElement<Credential>(_GetAccessTokenAdvangeCres_QNAME, Credential.class, GetAccessTokenDigest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "ExceptionType", scope = Error.class)
    public JAXBElement<String> createErrorExceptionType(String value) {
        return new JAXBElement<String>(_ErrorExceptionType_QNAME, String.class, Error.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/Vietbando.MessageType", name = "Message", scope = Error.class)
    public JAXBElement<String> createErrorMessage(String value) {
        return new JAXBElement<String>(_ErrorMessage_QNAME, String.class, Error.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Credential }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "cres", scope = GetAccessToken.class)
    public JAXBElement<Credential> createGetAccessTokenCres(Credential value) {
        return new JAXBElement<Credential>(_GetAccessTokenAdvangeCres_QNAME, Credential.class, GetAccessToken.class, value);
    }

}
