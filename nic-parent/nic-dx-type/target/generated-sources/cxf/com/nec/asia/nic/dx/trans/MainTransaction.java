
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
 * <p>Java class for MainTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MainTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplnRefNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateOfApplication" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="EstDateOfCollection" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PassportType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ValidatyPeriod" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="IssuingAuthority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="RegSiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssSiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrevPassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrevDateOfIssue" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegistrationData" type="{http://trans.dx.nic.asia.nec.com/}RegistrationData" minOccurs="0"/>
 *         &lt;element name="IssuanceData" type="{http://trans.dx.nic.asia.nec.com/}IssuanceData" minOccurs="0"/>
 *         &lt;element name="RejectionData" type="{http://trans.dx.nic.asia.nec.com/}RejectionData" minOccurs="0"/>
 *         &lt;element name="TransactionLogs" type="{http://trans.dx.nic.asia.nec.com/}TransactionLog" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Checksum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PackageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IsPostOffice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecieptNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InfoPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportStyle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CountTrans" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="CreateByhA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TranType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeUpload" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MainTransaction", propOrder = {
    "transactionID",
    "applnRefNo",
    "nin",
    "dateOfApplication",
    "estDateOfCollection",
    "passportType",
    "validatyPeriod",
    "issuingAuthority",
    "priority",
    "regSiteCode",
    "issSiteCode",
    "transactionType",
    "transactionStatus",
    "prevPassportNo",
    "prevDateOfIssue",
    "createBy",
    "createDateTime",
    "createWkstnID",
    "updateBy",
    "updateDateTime",
    "updateWkstnID",
    "registrationData",
    "issuanceData",
    "rejectionData",
    "transactionLogs",
    "checksum",
    "packageID",
    "isPostOffice",
    "recieptNo",
    "infoPerson",
    "passportStyle",
    "countTrans",
    "createByhA",
    "tranType",
    "typeUpload"
})
public class MainTransaction {

    @XmlElement(name = "TransactionID")
    protected String transactionID;
    @XmlElement(name = "ApplnRefNo")
    protected String applnRefNo;
    @XmlElement(name = "NIN")
    protected String nin;
    @XmlElement(name = "DateOfApplication", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dateOfApplication;
    @XmlElement(name = "EstDateOfCollection", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date estDateOfCollection;
    @XmlElement(name = "PassportType")
    protected String passportType;
    @XmlElement(name = "ValidatyPeriod")
    protected Integer validatyPeriod;
    @XmlElement(name = "IssuingAuthority")
    protected String issuingAuthority;
    @XmlElement(name = "Priority")
    protected Integer priority;
    @XmlElement(name = "RegSiteCode")
    protected String regSiteCode;
    @XmlElement(name = "IssSiteCode")
    protected String issSiteCode;
    @XmlElement(name = "TransactionType")
    protected String transactionType;
    @XmlElement(name = "TransactionStatus")
    protected String transactionStatus;
    @XmlElement(name = "PrevPassportNo")
    protected String prevPassportNo;
    @XmlElement(name = "PrevDateOfIssue", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date prevDateOfIssue;
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
    @XmlElement(name = "RegistrationData")
    protected RegistrationData registrationData;
    @XmlElement(name = "IssuanceData")
    protected IssuanceData issuanceData;
    @XmlElement(name = "RejectionData")
    protected RejectionData rejectionData;
    @XmlElement(name = "TransactionLogs", nillable = true)
    protected List<TransactionLog> transactionLogs;
    @XmlElement(name = "Checksum")
    protected String checksum;
    @XmlElement(name = "PackageID")
    protected String packageID;
    @XmlElement(name = "IsPostOffice")
    protected String isPostOffice;
    @XmlElement(name = "RecieptNo")
    protected String recieptNo;
    @XmlElement(name = "InfoPerson")
    protected String infoPerson;
    @XmlElement(name = "PassportStyle")
    protected String passportStyle;
    @XmlElement(name = "CountTrans")
    protected Integer countTrans;
    @XmlElement(name = "CreateByhA")
    protected String createByhA;
    @XmlElement(name = "TranType")
    protected String tranType;
    @XmlElement(name = "TypeUpload")
    protected Integer typeUpload;

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
     * Gets the value of the applnRefNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplnRefNo() {
        return applnRefNo;
    }

    /**
     * Sets the value of the applnRefNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplnRefNo(String value) {
        this.applnRefNo = value;
    }

    /**
     * Gets the value of the nin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIN() {
        return nin;
    }

    /**
     * Sets the value of the nin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIN(String value) {
        this.nin = value;
    }

    /**
     * Gets the value of the dateOfApplication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateOfApplication() {
        return dateOfApplication;
    }

    /**
     * Sets the value of the dateOfApplication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfApplication(Date value) {
        this.dateOfApplication = value;
    }

    /**
     * Gets the value of the estDateOfCollection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEstDateOfCollection() {
        return estDateOfCollection;
    }

    /**
     * Sets the value of the estDateOfCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstDateOfCollection(Date value) {
        this.estDateOfCollection = value;
    }

    /**
     * Gets the value of the passportType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportType() {
        return passportType;
    }

    /**
     * Sets the value of the passportType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportType(String value) {
        this.passportType = value;
    }

    /**
     * Gets the value of the validatyPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getValidatyPeriod() {
        return validatyPeriod;
    }

    /**
     * Sets the value of the validatyPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setValidatyPeriod(Integer value) {
        this.validatyPeriod = value;
    }

    /**
     * Gets the value of the issuingAuthority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    /**
     * Sets the value of the issuingAuthority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuingAuthority(String value) {
        this.issuingAuthority = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

    /**
     * Gets the value of the regSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegSiteCode() {
        return regSiteCode;
    }

    /**
     * Sets the value of the regSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegSiteCode(String value) {
        this.regSiteCode = value;
    }

    /**
     * Gets the value of the issSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssSiteCode() {
        return issSiteCode;
    }

    /**
     * Sets the value of the issSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssSiteCode(String value) {
        this.issSiteCode = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionType(String value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the transactionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Sets the value of the transactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatus(String value) {
        this.transactionStatus = value;
    }

    /**
     * Gets the value of the prevPassportNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrevPassportNo() {
        return prevPassportNo;
    }

    /**
     * Sets the value of the prevPassportNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrevPassportNo(String value) {
        this.prevPassportNo = value;
    }

    /**
     * Gets the value of the prevDateOfIssue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPrevDateOfIssue() {
        return prevDateOfIssue;
    }

    /**
     * Sets the value of the prevDateOfIssue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrevDateOfIssue(Date value) {
        this.prevDateOfIssue = value;
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
     * Gets the value of the registrationData property.
     * 
     * @return
     *     possible object is
     *     {@link RegistrationData }
     *     
     */
    public RegistrationData getRegistrationData() {
        return registrationData;
    }

    /**
     * Sets the value of the registrationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistrationData }
     *     
     */
    public void setRegistrationData(RegistrationData value) {
        this.registrationData = value;
    }

    /**
     * Gets the value of the issuanceData property.
     * 
     * @return
     *     possible object is
     *     {@link IssuanceData }
     *     
     */
    public IssuanceData getIssuanceData() {
        return issuanceData;
    }

    /**
     * Sets the value of the issuanceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link IssuanceData }
     *     
     */
    public void setIssuanceData(IssuanceData value) {
        this.issuanceData = value;
    }

    /**
     * Gets the value of the rejectionData property.
     * 
     * @return
     *     possible object is
     *     {@link RejectionData }
     *     
     */
    public RejectionData getRejectionData() {
        return rejectionData;
    }

    /**
     * Sets the value of the rejectionData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RejectionData }
     *     
     */
    public void setRejectionData(RejectionData value) {
        this.rejectionData = value;
    }

    /**
     * Gets the value of the transactionLogs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionLogs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionLogs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionLog }
     * 
     * 
     */
    public List<TransactionLog> getTransactionLogs() {
        if (transactionLogs == null) {
            transactionLogs = new ArrayList<TransactionLog>();
        }
        return this.transactionLogs;
    }

    /**
     * Gets the value of the checksum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChecksum(String value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the packageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     * Sets the value of the packageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageID(String value) {
        this.packageID = value;
    }

    /**
     * Gets the value of the isPostOffice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPostOffice() {
        return isPostOffice;
    }

    /**
     * Sets the value of the isPostOffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPostOffice(String value) {
        this.isPostOffice = value;
    }

    /**
     * Gets the value of the recieptNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecieptNo() {
        return recieptNo;
    }

    /**
     * Sets the value of the recieptNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecieptNo(String value) {
        this.recieptNo = value;
    }

    /**
     * Gets the value of the infoPerson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoPerson() {
        return infoPerson;
    }

    /**
     * Sets the value of the infoPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoPerson(String value) {
        this.infoPerson = value;
    }

    /**
     * Gets the value of the passportStyle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportStyle() {
        return passportStyle;
    }

    /**
     * Sets the value of the passportStyle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportStyle(String value) {
        this.passportStyle = value;
    }

    /**
     * Gets the value of the countTrans property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCountTrans() {
        return countTrans;
    }

    /**
     * Sets the value of the countTrans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCountTrans(Integer value) {
        this.countTrans = value;
    }

    /**
     * Gets the value of the createByhA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateByhA() {
        return createByhA;
    }

    /**
     * Sets the value of the createByhA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateByhA(String value) {
        this.createByhA = value;
    }

    /**
     * Gets the value of the tranType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranType() {
        return tranType;
    }

    /**
     * Sets the value of the tranType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranType(String value) {
        this.tranType = value;
    }

    /**
     * Gets the value of the typeUpload property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTypeUpload() {
        return typeUpload;
    }

    /**
     * Sets the value of the typeUpload property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTypeUpload(Integer value) {
        this.typeUpload = value;
    }

}
