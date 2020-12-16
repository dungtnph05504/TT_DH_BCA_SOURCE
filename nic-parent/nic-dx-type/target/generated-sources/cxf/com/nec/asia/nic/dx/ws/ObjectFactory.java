
package com.nec.asia.nic.dx.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.nec.asia.nic.dx.admin.ChangeUserPassword;
import com.nec.asia.nic.dx.admin.ChangeUserPasswordResponse;
import com.nec.asia.nic.dx.admin.GetAuthorizedFunctions;
import com.nec.asia.nic.dx.admin.GetAuthorizedFunctionsResponse;
import com.nec.asia.nic.dx.admin.GetConfigurations;
import com.nec.asia.nic.dx.admin.GetConfigurationsResponse;
import com.nec.asia.nic.dx.admin.GetPaymentMatrix;
import com.nec.asia.nic.dx.admin.GetPaymentMatrixAllRequest;
import com.nec.asia.nic.dx.admin.GetPaymentMatrixAllResponse;
import com.nec.asia.nic.dx.admin.GetPaymentMatrixResponse;
import com.nec.asia.nic.dx.admin.GetProofDocumentMatrix;
import com.nec.asia.nic.dx.admin.GetProofDocumentMatrixResponse;
import com.nec.asia.nic.dx.admin.GetRecieptAllRequest;
import com.nec.asia.nic.dx.admin.MappingAuthenData;
import com.nec.asia.nic.dx.admin.OfficalNationTransaction;
import com.nec.asia.nic.dx.admin.ParaSignerCompareRequest;
import com.nec.asia.nic.dx.admin.ParaSignerCompareResponse;
import com.nec.asia.nic.dx.admin.UserData;
import com.nec.asia.nic.dx.admin.UserRequest;
import com.nec.asia.nic.dx.common.FaultDetail;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nec.asia.nic.dx.ws package. 
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

    private final static QName _FaultException_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "FaultException");
    private final static QName _OfficalNationUpdateStatus_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationUpdateStatus");
    private final static QName _ParaSignerCompareRequest_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "ParaSignerCompareRequest");
    private final static QName _GetDecisionManager_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetDecisionManager");
    private final static QName _GetPaymentMatrixAllResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetPaymentMatrixAllResponse");
    private final static QName _GetRecieptRequest_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetRecieptRequest");
    private final static QName _OfficalNationUpdateStatusResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationUpdateStatusResponse");
    private final static QName _OfficalNationResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationResponse");
    private final static QName _OfficalNationUpdateStatusFail_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationUpdateStatusFail");
    private final static QName _OfficalNationUpload_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationUpload");
    private final static QName _GetPaymentMatrix_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetPaymentMatrix");
    private final static QName _GetAuthorizedFunctionsResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetAuthorizedFunctionsResponse");
    private final static QName _GetAuthenUserRequest_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetAuthenUserRequest");
    private final static QName _GetPaymentMatrixResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetPaymentMatrixResponse");
    private final static QName _GetAuthorizedFunctions_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetAuthorizedFunctions");
    private final static QName _OfficalNationFind_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationFind");
    private final static QName _ChangeUserPassword_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "ChangeUserPassword");
    private final static QName _GetMappingAuthenticationRequest_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetMappingAuthenticationRequest");
    private final static QName _GetAuthenUserResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetAuthenUserResponse");
    private final static QName _GetConfigurationsResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetConfigurationsResponse");
    private final static QName _ParaSignerCompareResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "ParaSignerCompareResponse");
    private final static QName _GetDecisionManagerResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetDecisionManagerResponse");
    private final static QName _OfficalNationFindResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationFindResponse");
    private final static QName _OfficalNationUpdateStatusFailResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "OfficalNationUpdateStatusFailResponse");
    private final static QName _GetProofDocumentMatrix_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetProofDocumentMatrix");
    private final static QName _GetConfigurations_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetConfigurations");
    private final static QName _GetPaymentMatrixAllRequest_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetPaymentMatrixAllRequest");
    private final static QName _ChangeUserPasswordResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "ChangeUserPasswordResponse");
    private final static QName _GetMappingAuthenticationResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetMappingAuthenticationResponse");
    private final static QName _GetProofDocumentMatrixResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetProofDocumentMatrixResponse");
    private final static QName _GetRecieptResponse_QNAME = new QName("http://ws.dx.nic.asia.nec.com/", "GetRecieptResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nec.asia.nic.dx.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "FaultException")
    public JAXBElement<FaultDetail> createFaultException(FaultDetail value) {
        return new JAXBElement<FaultDetail>(_FaultException_QNAME, FaultDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationUpdateStatus")
    public JAXBElement<String> createOfficalNationUpdateStatus(String value) {
        return new JAXBElement<String>(_OfficalNationUpdateStatus_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParaSignerCompareRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "ParaSignerCompareRequest")
    public JAXBElement<ParaSignerCompareRequest> createParaSignerCompareRequest(ParaSignerCompareRequest value) {
        return new JAXBElement<ParaSignerCompareRequest>(_ParaSignerCompareRequest_QNAME, ParaSignerCompareRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetDecisionManager")
    public JAXBElement<GetConfigurations> createGetDecisionManager(GetConfigurations value) {
        return new JAXBElement<GetConfigurations>(_GetDecisionManager_QNAME, GetConfigurations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPaymentMatrixAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetPaymentMatrixAllResponse")
    public JAXBElement<GetPaymentMatrixAllResponse> createGetPaymentMatrixAllResponse(GetPaymentMatrixAllResponse value) {
        return new JAXBElement<GetPaymentMatrixAllResponse>(_GetPaymentMatrixAllResponse_QNAME, GetPaymentMatrixAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRecieptAllRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetRecieptRequest")
    public JAXBElement<GetRecieptAllRequest> createGetRecieptRequest(GetRecieptAllRequest value) {
        return new JAXBElement<GetRecieptAllRequest>(_GetRecieptRequest_QNAME, GetRecieptAllRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationUpdateStatusResponse")
    public JAXBElement<String> createOfficalNationUpdateStatusResponse(String value) {
        return new JAXBElement<String>(_OfficalNationUpdateStatusResponse_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationResponse")
    public JAXBElement<String> createOfficalNationResponse(String value) {
        return new JAXBElement<String>(_OfficalNationResponse_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationUpdateStatusFail")
    public JAXBElement<String> createOfficalNationUpdateStatusFail(String value) {
        return new JAXBElement<String>(_OfficalNationUpdateStatusFail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OfficalNationTransaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationUpload")
    public JAXBElement<OfficalNationTransaction> createOfficalNationUpload(OfficalNationTransaction value) {
        return new JAXBElement<OfficalNationTransaction>(_OfficalNationUpload_QNAME, OfficalNationTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPaymentMatrix }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetPaymentMatrix")
    public JAXBElement<GetPaymentMatrix> createGetPaymentMatrix(GetPaymentMatrix value) {
        return new JAXBElement<GetPaymentMatrix>(_GetPaymentMatrix_QNAME, GetPaymentMatrix.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthorizedFunctionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetAuthorizedFunctionsResponse")
    public JAXBElement<GetAuthorizedFunctionsResponse> createGetAuthorizedFunctionsResponse(GetAuthorizedFunctionsResponse value) {
        return new JAXBElement<GetAuthorizedFunctionsResponse>(_GetAuthorizedFunctionsResponse_QNAME, GetAuthorizedFunctionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetAuthenUserRequest")
    public JAXBElement<UserRequest> createGetAuthenUserRequest(UserRequest value) {
        return new JAXBElement<UserRequest>(_GetAuthenUserRequest_QNAME, UserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPaymentMatrixResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetPaymentMatrixResponse")
    public JAXBElement<GetPaymentMatrixResponse> createGetPaymentMatrixResponse(GetPaymentMatrixResponse value) {
        return new JAXBElement<GetPaymentMatrixResponse>(_GetPaymentMatrixResponse_QNAME, GetPaymentMatrixResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuthorizedFunctions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetAuthorizedFunctions")
    public JAXBElement<GetAuthorizedFunctions> createGetAuthorizedFunctions(GetAuthorizedFunctions value) {
        return new JAXBElement<GetAuthorizedFunctions>(_GetAuthorizedFunctions_QNAME, GetAuthorizedFunctions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationFind")
    public JAXBElement<String> createOfficalNationFind(String value) {
        return new JAXBElement<String>(_OfficalNationFind_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "ChangeUserPassword")
    public JAXBElement<ChangeUserPassword> createChangeUserPassword(ChangeUserPassword value) {
        return new JAXBElement<ChangeUserPassword>(_ChangeUserPassword_QNAME, ChangeUserPassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetMappingAuthenticationRequest")
    public JAXBElement<UserRequest> createGetMappingAuthenticationRequest(UserRequest value) {
        return new JAXBElement<UserRequest>(_GetMappingAuthenticationRequest_QNAME, UserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetAuthenUserResponse")
    public JAXBElement<UserData> createGetAuthenUserResponse(UserData value) {
        return new JAXBElement<UserData>(_GetAuthenUserResponse_QNAME, UserData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetConfigurationsResponse")
    public JAXBElement<GetConfigurationsResponse> createGetConfigurationsResponse(GetConfigurationsResponse value) {
        return new JAXBElement<GetConfigurationsResponse>(_GetConfigurationsResponse_QNAME, GetConfigurationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParaSignerCompareResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "ParaSignerCompareResponse")
    public JAXBElement<ParaSignerCompareResponse> createParaSignerCompareResponse(ParaSignerCompareResponse value) {
        return new JAXBElement<ParaSignerCompareResponse>(_ParaSignerCompareResponse_QNAME, ParaSignerCompareResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetDecisionManagerResponse")
    public JAXBElement<GetConfigurationsResponse> createGetDecisionManagerResponse(GetConfigurationsResponse value) {
        return new JAXBElement<GetConfigurationsResponse>(_GetDecisionManagerResponse_QNAME, GetConfigurationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationFindResponse")
    public JAXBElement<String> createOfficalNationFindResponse(String value) {
        return new JAXBElement<String>(_OfficalNationFindResponse_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "OfficalNationUpdateStatusFailResponse")
    public JAXBElement<String> createOfficalNationUpdateStatusFailResponse(String value) {
        return new JAXBElement<String>(_OfficalNationUpdateStatusFailResponse_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProofDocumentMatrix }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetProofDocumentMatrix")
    public JAXBElement<GetProofDocumentMatrix> createGetProofDocumentMatrix(GetProofDocumentMatrix value) {
        return new JAXBElement<GetProofDocumentMatrix>(_GetProofDocumentMatrix_QNAME, GetProofDocumentMatrix.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetConfigurations")
    public JAXBElement<GetConfigurations> createGetConfigurations(GetConfigurations value) {
        return new JAXBElement<GetConfigurations>(_GetConfigurations_QNAME, GetConfigurations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPaymentMatrixAllRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetPaymentMatrixAllRequest")
    public JAXBElement<GetPaymentMatrixAllRequest> createGetPaymentMatrixAllRequest(GetPaymentMatrixAllRequest value) {
        return new JAXBElement<GetPaymentMatrixAllRequest>(_GetPaymentMatrixAllRequest_QNAME, GetPaymentMatrixAllRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "ChangeUserPasswordResponse")
    public JAXBElement<ChangeUserPasswordResponse> createChangeUserPasswordResponse(ChangeUserPasswordResponse value) {
        return new JAXBElement<ChangeUserPasswordResponse>(_ChangeUserPasswordResponse_QNAME, ChangeUserPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MappingAuthenData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetMappingAuthenticationResponse")
    public JAXBElement<MappingAuthenData> createGetMappingAuthenticationResponse(MappingAuthenData value) {
        return new JAXBElement<MappingAuthenData>(_GetMappingAuthenticationResponse_QNAME, MappingAuthenData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProofDocumentMatrixResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetProofDocumentMatrixResponse")
    public JAXBElement<GetProofDocumentMatrixResponse> createGetProofDocumentMatrixResponse(GetProofDocumentMatrixResponse value) {
        return new JAXBElement<GetProofDocumentMatrixResponse>(_GetProofDocumentMatrixResponse_QNAME, GetProofDocumentMatrixResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.dx.nic.asia.nec.com/", name = "GetRecieptResponse")
    public JAXBElement<String> createGetRecieptResponse(String value) {
        return new JAXBElement<String>(_GetRecieptResponse_QNAME, String.class, null, value);
    }

}
