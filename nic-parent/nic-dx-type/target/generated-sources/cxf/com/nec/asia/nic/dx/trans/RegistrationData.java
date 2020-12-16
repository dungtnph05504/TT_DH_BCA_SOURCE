
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for RegistrationData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegistrationData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WorkflowJobId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WorkflowJobCompleteFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrintRemark1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrintRemark2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OverseasAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OverseasCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TravelPurpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcquiredCitizenship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ForeignPassportHolder" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OccupationDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfficeContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfficeAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FpByPassDecision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FpByPassBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FpByPassDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="TotalFp" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="FpIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FpQuality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FpPattern" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FpEncode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SignatureFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FacialIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersonDetail" type="{http://trans.dx.nic.asia.nec.com/}PersonDetail" minOccurs="0"/>
 *         &lt;element name="ReferenceDocuments" type="{http://trans.dx.nic.asia.nec.com/}ReferenceDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FacialInfo" type="{http://trans.dx.nic.asia.nec.com/}FacialInfo" minOccurs="0"/>
 *         &lt;element name="FingerprintInfo" type="{http://trans.dx.nic.asia.nec.com/}FingerprintInfo" minOccurs="0"/>
 *         &lt;element name="SignatureInfo" type="{http://trans.dx.nic.asia.nec.com/}SignatureInfo" minOccurs="0"/>
 *         &lt;element name="PaymentInfo" type="{http://trans.dx.nic.asia.nec.com/}PaymentInfo" minOccurs="0"/>
 *         &lt;element name="Job" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MethodReceive" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Prioritize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Religion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressNin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateNin" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="TypeChild" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="AddressReceive" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressCompany" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherDob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="MotherDob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="SpouseDob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="AddressTempNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressTempStreet" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressTempGuild" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressTempDistrict" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressTempCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressTempNation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StManWorking" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumDecision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GovDecision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SignerDecision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NameDepartment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressDepartment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PhoneDepartment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Position" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PostionEng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CivilServants" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LevelRank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ToNation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransitNation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeExpense" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EstimateFrom" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="EstimateTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressLine5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DatePassport" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="NameProvider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateCountry" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="PurposeKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReasonKind" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StyleDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherLost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotherLost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SfDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SsDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationData", propOrder = {
    "transactionID",
    "workflowJobId",
    "workflowJobCompleteFlag",
    "createBy",
    "createDateTime",
    "createWkstnID",
    "updateBy",
    "updateDateTime",
    "updateWkstnID",
    "printRemark1",
    "printRemark2",
    "addressLine1",
    "addressLine2",
    "addressLine3",
    "addressLine4",
    "overseasAddress",
    "overseasCountry",
    "travelPurpose",
    "acquiredCitizenship",
    "foreignPassportHolder",
    "occupationDesc",
    "officeContactNo",
    "officeAddress",
    "fpByPassDecision",
    "fpByPassBy",
    "fpByPassDateTime",
    "totalFp",
    "fpIndicator",
    "fpQuality",
    "fpPattern",
    "fpEncode",
    "signatureFlag",
    "facialIndicator",
    "email",
    "contactNo",
    "personDetail",
    "referenceDocuments",
    "facialInfo",
    "fingerprintInfo",
    "signatureInfo",
    "paymentInfo",
    "job",
    "methodReceive",
    "prioritize",
    "religion",
    "nation",
    "addressNin",
    "dateNin",
    "typeChild",
    "addressReceive",
    "addressCompany",
    "fatherDob",
    "motherDob",
    "spouseDob",
    "addressTempNum",
    "addressTempStreet",
    "addressTempGuild",
    "addressTempDistrict",
    "addressTempCity",
    "addressTempNation",
    "stManWorking",
    "numDecision",
    "govDecision",
    "signerDecision",
    "nameDepartment",
    "addressDepartment",
    "phoneDepartment",
    "position",
    "rank",
    "postionEng",
    "civilServants",
    "levelRank",
    "quantum",
    "toNation",
    "transitNation",
    "purpose",
    "typeExpense",
    "estimateFrom",
    "estimateTo",
    "addressLine5",
    "datePassport",
    "nameProvider",
    "dateCountry",
    "purposeKind",
    "reasonKind",
    "styleDob",
    "fatherLost",
    "motherLost",
    "sfDob",
    "smDob",
    "ssDob"
})
public class RegistrationData {

    @XmlElement(name = "TransactionID")
    protected String transactionID;
    @XmlElement(name = "WorkflowJobId")
    protected String workflowJobId;
    @XmlElement(name = "WorkflowJobCompleteFlag")
    protected String workflowJobCompleteFlag;
    @XmlElement(name = "CreateBy")
    protected String createBy;
    @XmlElement(name = "CreateDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date createDateTime;
    @XmlElement(name = "CreateWkstnID")
    protected String createWkstnID;
    @XmlElement(name = "UpdateBy")
    protected String updateBy;
    @XmlElement(name = "UpdateDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date updateDateTime;
    @XmlElement(name = "UpdateWkstnID")
    protected String updateWkstnID;
    @XmlElement(name = "PrintRemark1")
    protected String printRemark1;
    @XmlElement(name = "PrintRemark2")
    protected String printRemark2;
    @XmlElement(name = "AddressLine1")
    protected String addressLine1;
    @XmlElement(name = "AddressLine2")
    protected String addressLine2;
    @XmlElement(name = "AddressLine3")
    protected String addressLine3;
    @XmlElement(name = "AddressLine4")
    protected String addressLine4;
    @XmlElement(name = "OverseasAddress")
    protected String overseasAddress;
    @XmlElement(name = "OverseasCountry")
    protected String overseasCountry;
    @XmlElement(name = "TravelPurpose")
    protected String travelPurpose;
    @XmlElement(name = "AcquiredCitizenship")
    protected String acquiredCitizenship;
    @XmlElement(name = "ForeignPassportHolder")
    protected String foreignPassportHolder;
    @XmlElement(name = "OccupationDesc")
    protected String occupationDesc;
    @XmlElement(name = "OfficeContactNo")
    protected String officeContactNo;
    @XmlElement(name = "OfficeAddress")
    protected String officeAddress;
    @XmlElement(name = "FpByPassDecision")
    protected String fpByPassDecision;
    @XmlElement(name = "FpByPassBy")
    protected String fpByPassBy;
    @XmlElement(name = "FpByPassDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date fpByPassDateTime;
    @XmlElement(name = "TotalFp")
    protected Integer totalFp;
    @XmlElement(name = "FpIndicator")
    protected String fpIndicator;
    @XmlElement(name = "FpQuality")
    protected String fpQuality;
    @XmlElement(name = "FpPattern")
    protected String fpPattern;
    @XmlElement(name = "FpEncode")
    protected String fpEncode;
    @XmlElement(name = "SignatureFlag")
    protected String signatureFlag;
    @XmlElement(name = "FacialIndicator")
    protected String facialIndicator;
    @XmlElement(name = "Email")
    protected String email;
    @XmlElement(name = "ContactNo")
    protected String contactNo;
    @XmlElement(name = "PersonDetail")
    protected PersonDetail personDetail;
    @XmlElement(name = "ReferenceDocuments", nillable = true)
    protected List<ReferenceDocument> referenceDocuments;
    @XmlElement(name = "FacialInfo")
    protected FacialInfo facialInfo;
    @XmlElement(name = "FingerprintInfo")
    protected FingerprintInfo fingerprintInfo;
    @XmlElement(name = "SignatureInfo")
    protected SignatureInfo signatureInfo;
    @XmlElement(name = "PaymentInfo")
    protected PaymentInfo paymentInfo;
    @XmlElement(name = "Job")
    protected String job;
    @XmlElement(name = "MethodReceive")
    protected Integer methodReceive;
    @XmlElement(name = "Prioritize")
    protected String prioritize;
    @XmlElement(name = "Religion")
    protected String religion;
    @XmlElement(name = "Nation")
    protected String nation;
    @XmlElement(name = "AddressNin")
    protected String addressNin;
    @XmlElement(name = "DateNin", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dateNin;
    @XmlElement(name = "TypeChild")
    protected Integer typeChild;
    @XmlElement(name = "AddressReceive")
    protected String addressReceive;
    @XmlElement(name = "AddressCompany")
    protected String addressCompany;
    @XmlElement(name = "FatherDob", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date fatherDob;
    @XmlElement(name = "MotherDob", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date motherDob;
    @XmlElement(name = "SpouseDob", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date spouseDob;
    @XmlElement(name = "AddressTempNum")
    protected String addressTempNum;
    @XmlElement(name = "AddressTempStreet")
    protected String addressTempStreet;
    @XmlElement(name = "AddressTempGuild")
    protected String addressTempGuild;
    @XmlElement(name = "AddressTempDistrict")
    protected String addressTempDistrict;
    @XmlElement(name = "AddressTempCity")
    protected String addressTempCity;
    @XmlElement(name = "AddressTempNation")
    protected String addressTempNation;
    @XmlElement(name = "StManWorking")
    protected String stManWorking;
    @XmlElement(name = "NumDecision")
    protected String numDecision;
    @XmlElement(name = "GovDecision")
    protected String govDecision;
    @XmlElement(name = "SignerDecision")
    protected String signerDecision;
    @XmlElement(name = "NameDepartment")
    protected String nameDepartment;
    @XmlElement(name = "AddressDepartment")
    protected String addressDepartment;
    @XmlElement(name = "PhoneDepartment")
    protected String phoneDepartment;
    @XmlElement(name = "Position")
    protected String position;
    @XmlElement(name = "Rank")
    protected String rank;
    @XmlElement(name = "PostionEng")
    protected String postionEng;
    @XmlElement(name = "CivilServants")
    protected String civilServants;
    @XmlElement(name = "LevelRank")
    protected String levelRank;
    @XmlElement(name = "Quantum")
    protected String quantum;
    @XmlElement(name = "ToNation")
    protected String toNation;
    @XmlElement(name = "TransitNation")
    protected String transitNation;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "TypeExpense")
    protected String typeExpense;
    @XmlElement(name = "EstimateFrom", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date estimateFrom;
    @XmlElement(name = "EstimateTo")
    protected String estimateTo;
    @XmlElement(name = "AddressLine5")
    protected String addressLine5;
    @XmlElement(name = "DatePassport", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date datePassport;
    @XmlElement(name = "NameProvider")
    protected String nameProvider;
    @XmlElement(name = "DateCountry", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dateCountry;
    @XmlElement(name = "PurposeKind")
    protected String purposeKind;
    @XmlElement(name = "ReasonKind")
    protected String reasonKind;
    @XmlElement(name = "StyleDob")
    protected String styleDob;
    @XmlElement(name = "FatherLost")
    protected String fatherLost;
    @XmlElement(name = "MotherLost")
    protected String motherLost;
    @XmlElement(name = "SfDob")
    protected String sfDob;
    @XmlElement(name = "SmDob")
    protected String smDob;
    @XmlElement(name = "SsDob")
    protected String ssDob;

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    /**
     * Gets the value of the workflowJobId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowJobId() {
        return workflowJobId;
    }

    /**
     * Sets the value of the workflowJobId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowJobId(String value) {
        this.workflowJobId = value;
    }

    /**
     * Gets the value of the workflowJobCompleteFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflowJobCompleteFlag() {
        return workflowJobCompleteFlag;
    }

    /**
     * Sets the value of the workflowJobCompleteFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflowJobCompleteFlag(String value) {
        this.workflowJobCompleteFlag = value;
    }

    /**
     * Gets the value of the createBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Sets the value of the createBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateBy(String value) {
        this.createBy = value;
    }

    /**
     * Gets the value of the createDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Sets the value of the createDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDateTime(Date value) {
        this.createDateTime = value;
    }

    /**
     * Gets the value of the createWkstnID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateWkstnID() {
        return createWkstnID;
    }

    /**
     * Sets the value of the createWkstnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateWkstnID(String value) {
        this.createWkstnID = value;
    }

    /**
     * Gets the value of the updateBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Sets the value of the updateBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateBy(String value) {
        this.updateBy = value;
    }

    /**
     * Gets the value of the updateDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    /**
     * Sets the value of the updateDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDateTime(Date value) {
        this.updateDateTime = value;
    }

    /**
     * Gets the value of the updateWkstnID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateWkstnID() {
        return updateWkstnID;
    }

    /**
     * Sets the value of the updateWkstnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateWkstnID(String value) {
        this.updateWkstnID = value;
    }

    /**
     * Gets the value of the printRemark1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintRemark1() {
        return printRemark1;
    }

    /**
     * Sets the value of the printRemark1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintRemark1(String value) {
        this.printRemark1 = value;
    }

    /**
     * Gets the value of the printRemark2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintRemark2() {
        return printRemark2;
    }

    /**
     * Sets the value of the printRemark2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintRemark2(String value) {
        this.printRemark2 = value;
    }

    /**
     * Gets the value of the addressLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Sets the value of the addressLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /**
     * Gets the value of the addressLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Sets the value of the addressLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /**
     * Gets the value of the addressLine3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Sets the value of the addressLine3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine3(String value) {
        this.addressLine3 = value;
    }

    /**
     * Gets the value of the addressLine4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine4() {
        return addressLine4;
    }

    /**
     * Sets the value of the addressLine4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine4(String value) {
        this.addressLine4 = value;
    }

    /**
     * Gets the value of the overseasAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverseasAddress() {
        return overseasAddress;
    }

    /**
     * Sets the value of the overseasAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverseasAddress(String value) {
        this.overseasAddress = value;
    }

    /**
     * Gets the value of the overseasCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverseasCountry() {
        return overseasCountry;
    }

    /**
     * Sets the value of the overseasCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverseasCountry(String value) {
        this.overseasCountry = value;
    }

    /**
     * Gets the value of the travelPurpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravelPurpose() {
        return travelPurpose;
    }

    /**
     * Sets the value of the travelPurpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravelPurpose(String value) {
        this.travelPurpose = value;
    }

    /**
     * Gets the value of the acquiredCitizenship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcquiredCitizenship() {
        return acquiredCitizenship;
    }

    /**
     * Sets the value of the acquiredCitizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcquiredCitizenship(String value) {
        this.acquiredCitizenship = value;
    }

    /**
     * Gets the value of the foreignPassportHolder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForeignPassportHolder() {
        return foreignPassportHolder;
    }

    /**
     * Sets the value of the foreignPassportHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForeignPassportHolder(String value) {
        this.foreignPassportHolder = value;
    }

    /**
     * Gets the value of the occupationDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupationDesc() {
        return occupationDesc;
    }

    /**
     * Sets the value of the occupationDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupationDesc(String value) {
        this.occupationDesc = value;
    }

    /**
     * Gets the value of the officeContactNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeContactNo() {
        return officeContactNo;
    }

    /**
     * Sets the value of the officeContactNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeContactNo(String value) {
        this.officeContactNo = value;
    }

    /**
     * Gets the value of the officeAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /**
     * Sets the value of the officeAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficeAddress(String value) {
        this.officeAddress = value;
    }

    /**
     * Gets the value of the fpByPassDecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFpByPassDecision() {
        return fpByPassDecision;
    }

    /**
     * Sets the value of the fpByPassDecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpByPassDecision(String value) {
        this.fpByPassDecision = value;
    }

    /**
     * Gets the value of the fpByPassBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFpByPassBy() {
        return fpByPassBy;
    }

    /**
     * Sets the value of the fpByPassBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpByPassBy(String value) {
        this.fpByPassBy = value;
    }

    /**
     * Gets the value of the fpByPassDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getFpByPassDateTime() {
        return fpByPassDateTime;
    }

    /**
     * Sets the value of the fpByPassDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpByPassDateTime(Date value) {
        this.fpByPassDateTime = value;
    }

    /**
     * Gets the value of the totalFp property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTotalFp() {
        return totalFp;
    }

    /**
     * Sets the value of the totalFp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTotalFp(Integer value) {
        this.totalFp = value;
    }

    /**
     * Gets the value of the fpIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFpIndicator() {
        return fpIndicator;
    }

    /**
     * Sets the value of the fpIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpIndicator(String value) {
        this.fpIndicator = value;
    }

    /**
     * Gets the value of the fpQuality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFpQuality() {
        return fpQuality;
    }

    /**
     * Sets the value of the fpQuality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpQuality(String value) {
        this.fpQuality = value;
    }

    /**
     * Gets the value of the fpPattern property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFpPattern() {
        return fpPattern;
    }

    /**
     * Sets the value of the fpPattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpPattern(String value) {
        this.fpPattern = value;
    }

    /**
     * Gets the value of the fpEncode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFpEncode() {
        return fpEncode;
    }

    /**
     * Sets the value of the fpEncode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFpEncode(String value) {
        this.fpEncode = value;
    }

    /**
     * Gets the value of the signatureFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureFlag() {
        return signatureFlag;
    }

    /**
     * Sets the value of the signatureFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureFlag(String value) {
        this.signatureFlag = value;
    }

    /**
     * Gets the value of the facialIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacialIndicator() {
        return facialIndicator;
    }

    /**
     * Sets the value of the facialIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacialIndicator(String value) {
        this.facialIndicator = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the contactNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * Sets the value of the contactNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactNo(String value) {
        this.contactNo = value;
    }

    /**
     * Gets the value of the personDetail property.
     * 
     * @return
     *     possible object is
     *     {@link PersonDetail }
     *     
     */
    public PersonDetail getPersonDetail() {
        return personDetail;
    }

    /**
     * Sets the value of the personDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonDetail }
     *     
     */
    public void setPersonDetail(PersonDetail value) {
        this.personDetail = value;
    }

    /**
     * Gets the value of the referenceDocuments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceDocuments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceDocument }
     * 
     * 
     */
    public List<ReferenceDocument> getReferenceDocuments() {
        if (referenceDocuments == null) {
            referenceDocuments = new ArrayList<ReferenceDocument>();
        }
        return this.referenceDocuments;
    }

    /**
     * Gets the value of the facialInfo property.
     * 
     * @return
     *     possible object is
     *     {@link FacialInfo }
     *     
     */
    public FacialInfo getFacialInfo() {
        return facialInfo;
    }

    /**
     * Sets the value of the facialInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link FacialInfo }
     *     
     */
    public void setFacialInfo(FacialInfo value) {
        this.facialInfo = value;
    }

    /**
     * Gets the value of the fingerprintInfo property.
     * 
     * @return
     *     possible object is
     *     {@link FingerprintInfo }
     *     
     */
    public FingerprintInfo getFingerprintInfo() {
        return fingerprintInfo;
    }

    /**
     * Sets the value of the fingerprintInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link FingerprintInfo }
     *     
     */
    public void setFingerprintInfo(FingerprintInfo value) {
        this.fingerprintInfo = value;
    }

    /**
     * Gets the value of the signatureInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureInfo }
     *     
     */
    public SignatureInfo getSignatureInfo() {
        return signatureInfo;
    }

    /**
     * Sets the value of the signatureInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureInfo }
     *     
     */
    public void setSignatureInfo(SignatureInfo value) {
        this.signatureInfo = value;
    }

    /**
     * Gets the value of the paymentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentInfo }
     *     
     */
    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    /**
     * Sets the value of the paymentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentInfo }
     *     
     */
    public void setPaymentInfo(PaymentInfo value) {
        this.paymentInfo = value;
    }

    /**
     * Gets the value of the job property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJob() {
        return job;
    }

    /**
     * Sets the value of the job property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJob(String value) {
        this.job = value;
    }

    /**
     * Gets the value of the methodReceive property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMethodReceive() {
        return methodReceive;
    }

    /**
     * Sets the value of the methodReceive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMethodReceive(Integer value) {
        this.methodReceive = value;
    }

    /**
     * Gets the value of the prioritize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrioritize() {
        return prioritize;
    }

    /**
     * Sets the value of the prioritize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrioritize(String value) {
        this.prioritize = value;
    }

    /**
     * Gets the value of the religion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReligion() {
        return religion;
    }

    /**
     * Sets the value of the religion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReligion(String value) {
        this.religion = value;
    }

    /**
     * Gets the value of the nation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNation() {
        return nation;
    }

    /**
     * Sets the value of the nation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNation(String value) {
        this.nation = value;
    }

    /**
     * Gets the value of the addressNin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressNin() {
        return addressNin;
    }

    /**
     * Sets the value of the addressNin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressNin(String value) {
        this.addressNin = value;
    }

    /**
     * Gets the value of the dateNin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateNin() {
        return dateNin;
    }

    /**
     * Sets the value of the dateNin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateNin(Date value) {
        this.dateNin = value;
    }

    /**
     * Gets the value of the typeChild property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTypeChild() {
        return typeChild;
    }

    /**
     * Sets the value of the typeChild property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTypeChild(Integer value) {
        this.typeChild = value;
    }

    /**
     * Gets the value of the addressReceive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressReceive() {
        return addressReceive;
    }

    /**
     * Sets the value of the addressReceive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressReceive(String value) {
        this.addressReceive = value;
    }

    /**
     * Gets the value of the addressCompany property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressCompany() {
        return addressCompany;
    }

    /**
     * Sets the value of the addressCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressCompany(String value) {
        this.addressCompany = value;
    }

    /**
     * Gets the value of the fatherDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getFatherDob() {
        return fatherDob;
    }

    /**
     * Sets the value of the fatherDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherDob(Date value) {
        this.fatherDob = value;
    }

    /**
     * Gets the value of the motherDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getMotherDob() {
        return motherDob;
    }

    /**
     * Sets the value of the motherDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotherDob(Date value) {
        this.motherDob = value;
    }

    /**
     * Gets the value of the spouseDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getSpouseDob() {
        return spouseDob;
    }

    /**
     * Sets the value of the spouseDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseDob(Date value) {
        this.spouseDob = value;
    }

    /**
     * Gets the value of the addressTempNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTempNum() {
        return addressTempNum;
    }

    /**
     * Sets the value of the addressTempNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTempNum(String value) {
        this.addressTempNum = value;
    }

    /**
     * Gets the value of the addressTempStreet property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTempStreet() {
        return addressTempStreet;
    }

    /**
     * Sets the value of the addressTempStreet property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTempStreet(String value) {
        this.addressTempStreet = value;
    }

    /**
     * Gets the value of the addressTempGuild property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTempGuild() {
        return addressTempGuild;
    }

    /**
     * Sets the value of the addressTempGuild property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTempGuild(String value) {
        this.addressTempGuild = value;
    }

    /**
     * Gets the value of the addressTempDistrict property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTempDistrict() {
        return addressTempDistrict;
    }

    /**
     * Sets the value of the addressTempDistrict property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTempDistrict(String value) {
        this.addressTempDistrict = value;
    }

    /**
     * Gets the value of the addressTempCity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTempCity() {
        return addressTempCity;
    }

    /**
     * Sets the value of the addressTempCity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTempCity(String value) {
        this.addressTempCity = value;
    }

    /**
     * Gets the value of the addressTempNation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressTempNation() {
        return addressTempNation;
    }

    /**
     * Sets the value of the addressTempNation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressTempNation(String value) {
        this.addressTempNation = value;
    }

    /**
     * Gets the value of the stManWorking property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStManWorking() {
        return stManWorking;
    }

    /**
     * Sets the value of the stManWorking property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStManWorking(String value) {
        this.stManWorking = value;
    }

    /**
     * Gets the value of the numDecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDecision() {
        return numDecision;
    }

    /**
     * Sets the value of the numDecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDecision(String value) {
        this.numDecision = value;
    }

    /**
     * Gets the value of the govDecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGovDecision() {
        return govDecision;
    }

    /**
     * Sets the value of the govDecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGovDecision(String value) {
        this.govDecision = value;
    }

    /**
     * Gets the value of the signerDecision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignerDecision() {
        return signerDecision;
    }

    /**
     * Sets the value of the signerDecision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignerDecision(String value) {
        this.signerDecision = value;
    }

    /**
     * Gets the value of the nameDepartment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameDepartment() {
        return nameDepartment;
    }

    /**
     * Sets the value of the nameDepartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameDepartment(String value) {
        this.nameDepartment = value;
    }

    /**
     * Gets the value of the addressDepartment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressDepartment() {
        return addressDepartment;
    }

    /**
     * Sets the value of the addressDepartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressDepartment(String value) {
        this.addressDepartment = value;
    }

    /**
     * Gets the value of the phoneDepartment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneDepartment() {
        return phoneDepartment;
    }

    /**
     * Sets the value of the phoneDepartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneDepartment(String value) {
        this.phoneDepartment = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the rank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the value of the rank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRank(String value) {
        this.rank = value;
    }

    /**
     * Gets the value of the postionEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostionEng() {
        return postionEng;
    }

    /**
     * Sets the value of the postionEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostionEng(String value) {
        this.postionEng = value;
    }

    /**
     * Gets the value of the civilServants property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivilServants() {
        return civilServants;
    }

    /**
     * Sets the value of the civilServants property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivilServants(String value) {
        this.civilServants = value;
    }

    /**
     * Gets the value of the levelRank property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevelRank() {
        return levelRank;
    }

    /**
     * Sets the value of the levelRank property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevelRank(String value) {
        this.levelRank = value;
    }

    /**
     * Gets the value of the quantum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantum() {
        return quantum;
    }

    /**
     * Sets the value of the quantum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantum(String value) {
        this.quantum = value;
    }

    /**
     * Gets the value of the toNation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToNation() {
        return toNation;
    }

    /**
     * Sets the value of the toNation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToNation(String value) {
        this.toNation = value;
    }

    /**
     * Gets the value of the transitNation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransitNation() {
        return transitNation;
    }

    /**
     * Sets the value of the transitNation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransitNation(String value) {
        this.transitNation = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the typeExpense property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeExpense() {
        return typeExpense;
    }

    /**
     * Sets the value of the typeExpense property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeExpense(String value) {
        this.typeExpense = value;
    }

    /**
     * Gets the value of the estimateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEstimateFrom() {
        return estimateFrom;
    }

    /**
     * Sets the value of the estimateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimateFrom(Date value) {
        this.estimateFrom = value;
    }

    /**
     * Gets the value of the estimateTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstimateTo() {
        return estimateTo;
    }

    /**
     * Sets the value of the estimateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstimateTo(String value) {
        this.estimateTo = value;
    }

    /**
     * Gets the value of the addressLine5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressLine5() {
        return addressLine5;
    }

    /**
     * Sets the value of the addressLine5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressLine5(String value) {
        this.addressLine5 = value;
    }

    /**
     * Gets the value of the datePassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDatePassport() {
        return datePassport;
    }

    /**
     * Sets the value of the datePassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatePassport(Date value) {
        this.datePassport = value;
    }

    /**
     * Gets the value of the nameProvider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameProvider() {
        return nameProvider;
    }

    /**
     * Sets the value of the nameProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameProvider(String value) {
        this.nameProvider = value;
    }

    /**
     * Gets the value of the dateCountry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateCountry() {
        return dateCountry;
    }

    /**
     * Sets the value of the dateCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateCountry(Date value) {
        this.dateCountry = value;
    }

    /**
     * Gets the value of the purposeKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurposeKind() {
        return purposeKind;
    }

    /**
     * Sets the value of the purposeKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurposeKind(String value) {
        this.purposeKind = value;
    }

    /**
     * Gets the value of the reasonKind property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonKind() {
        return reasonKind;
    }

    /**
     * Sets the value of the reasonKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonKind(String value) {
        this.reasonKind = value;
    }

    /**
     * Gets the value of the styleDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyleDob() {
        return styleDob;
    }

    /**
     * Sets the value of the styleDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyleDob(String value) {
        this.styleDob = value;
    }

    /**
     * Gets the value of the fatherLost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFatherLost() {
        return fatherLost;
    }

    /**
     * Sets the value of the fatherLost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherLost(String value) {
        this.fatherLost = value;
    }

    /**
     * Gets the value of the motherLost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotherLost() {
        return motherLost;
    }

    /**
     * Sets the value of the motherLost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotherLost(String value) {
        this.motherLost = value;
    }

    /**
     * Gets the value of the sfDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSfDob() {
        return sfDob;
    }

    /**
     * Sets the value of the sfDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSfDob(String value) {
        this.sfDob = value;
    }

    /**
     * Gets the value of the smDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmDob() {
        return smDob;
    }

    /**
     * Sets the value of the smDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmDob(String value) {
        this.smDob = value;
    }

    /**
     * Gets the value of the ssDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsDob() {
        return ssDob;
    }

    /**
     * Sets the value of the ssDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsDob(String value) {
        this.ssDob = value;
    }

}
