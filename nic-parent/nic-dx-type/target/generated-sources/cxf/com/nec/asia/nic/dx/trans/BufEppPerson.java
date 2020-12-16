
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
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for BufEppPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BufEppPerson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransacionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fullname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dob" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Nin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssueDateNin" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="IssuePlaceNin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Religion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressResident" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssueDatePassport" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="IssuePlacePassport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemarkApprove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FamilyData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransacionMasterId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BufEppPersonDoc" type="{http://trans.dx.nic.asia.nec.com/}BufEppPersonDoc" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BufEppPersonInvestigation" type="{http://trans.dx.nic.asia.nec.com/}BufEppPersonInvestigation" minOccurs="0"/>
 *         &lt;element name="ScoreBMS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DetailScoreBMS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StyleDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PobDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Jobs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HandoverA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateHandover" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateByHandover" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssueFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ExpiredPassport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssuePassport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InfoPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestUserB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestDateB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApproveUserB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApproveDateB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestNoteB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApproveNoteB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HandoverC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrintDateC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Serial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IcaoLineOne" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IcaoLineTwo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherLost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotherLost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GlobalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OriginId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BufEppPerson", propOrder = {
    "transacionId",
    "fullname",
    "gender",
    "dob",
    "nin",
    "pob",
    "issueDateNin",
    "issuePlaceNin",
    "nation",
    "religion",
    "addressResident",
    "phone",
    "passportNo",
    "passportStatus",
    "typeTransaction",
    "issueDatePassport",
    "issuePlacePassport",
    "note",
    "remarkApprove",
    "familyData",
    "createBy",
    "createDateTime",
    "createWkstnID",
    "updateBy",
    "updateDateTime",
    "updateWkstnID",
    "transacionMasterId",
    "bufEppPersonDoc",
    "bufEppPersonInvestigation",
    "scoreBMS",
    "detailScoreBMS",
    "styleDob",
    "pobDes",
    "jobs",
    "handoverA",
    "createDateHandover",
    "createByHandover",
    "issueFlag",
    "expiredPassport",
    "issuePassport",
    "infoPerson",
    "requestUserB",
    "requestDateB",
    "approveUserB",
    "approveDateB",
    "requestNoteB",
    "approveNoteB",
    "handoverC",
    "createDateC",
    "printDateC",
    "serial",
    "passportType",
    "icaoLineOne",
    "icaoLineTwo",
    "fatherLost",
    "motherLost",
    "globalId",
    "originId"
})
public class BufEppPerson {

    @XmlElement(name = "TransacionId")
    protected String transacionId;
    @XmlElement(name = "Fullname")
    protected String fullname;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "Dob", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dob;
    @XmlElement(name = "Nin")
    protected String nin;
    @XmlElement(name = "Pob")
    protected String pob;
    @XmlElement(name = "IssueDateNin", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date issueDateNin;
    @XmlElement(name = "IssuePlaceNin")
    protected String issuePlaceNin;
    @XmlElement(name = "Nation")
    protected String nation;
    @XmlElement(name = "Religion")
    protected String religion;
    @XmlElement(name = "AddressResident")
    protected String addressResident;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "PassportNo")
    protected String passportNo;
    @XmlElement(name = "PassportStatus")
    protected String passportStatus;
    @XmlElement(name = "TypeTransaction")
    protected String typeTransaction;
    @XmlElement(name = "IssueDatePassport", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date issueDatePassport;
    @XmlElement(name = "IssuePlacePassport")
    protected String issuePlacePassport;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "RemarkApprove")
    protected String remarkApprove;
    @XmlElement(name = "FamilyData")
    protected String familyData;
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
    @XmlElement(name = "TransacionMasterId")
    protected String transacionMasterId;
    @XmlElement(name = "BufEppPersonDoc", nillable = true)
    protected List<BufEppPersonDoc> bufEppPersonDoc;
    @XmlElement(name = "BufEppPersonInvestigation")
    protected BufEppPersonInvestigation bufEppPersonInvestigation;
    @XmlElement(name = "ScoreBMS")
    protected String scoreBMS;
    @XmlElement(name = "DetailScoreBMS")
    protected String detailScoreBMS;
    @XmlElement(name = "StyleDob")
    protected String styleDob;
    @XmlElement(name = "PobDes")
    protected String pobDes;
    @XmlElement(name = "Jobs")
    protected String jobs;
    @XmlElement(name = "HandoverA")
    protected String handoverA;
    @XmlElement(name = "CreateDateHandover")
    protected String createDateHandover;
    @XmlElement(name = "CreateByHandover")
    protected String createByHandover;
    @XmlElement(name = "IssueFlag")
    protected String issueFlag;
    @XmlElement(name = "ExpiredPassport")
    protected String expiredPassport;
    @XmlElement(name = "IssuePassport")
    protected String issuePassport;
    @XmlElement(name = "InfoPerson")
    protected String infoPerson;
    @XmlElement(name = "RequestUserB")
    protected String requestUserB;
    @XmlElement(name = "RequestDateB")
    protected String requestDateB;
    @XmlElement(name = "ApproveUserB")
    protected String approveUserB;
    @XmlElement(name = "ApproveDateB")
    protected String approveDateB;
    @XmlElement(name = "RequestNoteB")
    protected String requestNoteB;
    @XmlElement(name = "ApproveNoteB")
    protected String approveNoteB;
    @XmlElement(name = "HandoverC")
    protected String handoverC;
    @XmlElement(name = "CreateDateC")
    protected String createDateC;
    @XmlElement(name = "PrintDateC")
    protected String printDateC;
    @XmlElement(name = "Serial")
    protected String serial;
    @XmlElement(name = "PassportType")
    protected String passportType;
    @XmlElement(name = "IcaoLineOne")
    protected String icaoLineOne;
    @XmlElement(name = "IcaoLineTwo")
    protected String icaoLineTwo;
    @XmlElement(name = "FatherLost")
    protected String fatherLost;
    @XmlElement(name = "MotherLost")
    protected String motherLost;
    @XmlElement(name = "GlobalId")
    protected String globalId;
    @XmlElement(name = "OriginId")
    protected String originId;

    /**
     * Gets the value of the transacionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransacionId() {
        return transacionId;
    }

    /**
     * Sets the value of the transacionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransacionId(String value) {
        this.transacionId = value;
    }

    /**
     * Gets the value of the fullname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Sets the value of the fullname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullname(String value) {
        this.fullname = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDob(Date value) {
        this.dob = value;
    }

    /**
     * Gets the value of the nin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNin() {
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
    public void setNin(String value) {
        this.nin = value;
    }

    /**
     * Gets the value of the pob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPob() {
        return pob;
    }

    /**
     * Sets the value of the pob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPob(String value) {
        this.pob = value;
    }

    /**
     * Gets the value of the issueDateNin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getIssueDateNin() {
        return issueDateNin;
    }

    /**
     * Sets the value of the issueDateNin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueDateNin(Date value) {
        this.issueDateNin = value;
    }

    /**
     * Gets the value of the issuePlaceNin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuePlaceNin() {
        return issuePlaceNin;
    }

    /**
     * Sets the value of the issuePlaceNin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuePlaceNin(String value) {
        this.issuePlaceNin = value;
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
     * Gets the value of the addressResident property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressResident() {
        return addressResident;
    }

    /**
     * Sets the value of the addressResident property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressResident(String value) {
        this.addressResident = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * Gets the value of the passportNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportNo() {
        return passportNo;
    }

    /**
     * Sets the value of the passportNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportNo(String value) {
        this.passportNo = value;
    }

    /**
     * Gets the value of the passportStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportStatus() {
        return passportStatus;
    }

    /**
     * Sets the value of the passportStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportStatus(String value) {
        this.passportStatus = value;
    }

    /**
     * Gets the value of the typeTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeTransaction() {
        return typeTransaction;
    }

    /**
     * Sets the value of the typeTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeTransaction(String value) {
        this.typeTransaction = value;
    }

    /**
     * Gets the value of the issueDatePassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getIssueDatePassport() {
        return issueDatePassport;
    }

    /**
     * Sets the value of the issueDatePassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueDatePassport(Date value) {
        this.issueDatePassport = value;
    }

    /**
     * Gets the value of the issuePlacePassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuePlacePassport() {
        return issuePlacePassport;
    }

    /**
     * Sets the value of the issuePlacePassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuePlacePassport(String value) {
        this.issuePlacePassport = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the remarkApprove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarkApprove() {
        return remarkApprove;
    }

    /**
     * Sets the value of the remarkApprove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarkApprove(String value) {
        this.remarkApprove = value;
    }

    /**
     * Gets the value of the familyData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyData() {
        return familyData;
    }

    /**
     * Sets the value of the familyData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyData(String value) {
        this.familyData = value;
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
     * Gets the value of the transacionMasterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransacionMasterId() {
        return transacionMasterId;
    }

    /**
     * Sets the value of the transacionMasterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransacionMasterId(String value) {
        this.transacionMasterId = value;
    }

    /**
     * Gets the value of the bufEppPersonDoc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bufEppPersonDoc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBufEppPersonDoc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BufEppPersonDoc }
     * 
     * 
     */
    public List<BufEppPersonDoc> getBufEppPersonDoc() {
        if (bufEppPersonDoc == null) {
            bufEppPersonDoc = new ArrayList<BufEppPersonDoc>();
        }
        return this.bufEppPersonDoc;
    }

    /**
     * Gets the value of the bufEppPersonInvestigation property.
     * 
     * @return
     *     possible object is
     *     {@link BufEppPersonInvestigation }
     *     
     */
    public BufEppPersonInvestigation getBufEppPersonInvestigation() {
        return bufEppPersonInvestigation;
    }

    /**
     * Sets the value of the bufEppPersonInvestigation property.
     * 
     * @param value
     *     allowed object is
     *     {@link BufEppPersonInvestigation }
     *     
     */
    public void setBufEppPersonInvestigation(BufEppPersonInvestigation value) {
        this.bufEppPersonInvestigation = value;
    }

    /**
     * Gets the value of the scoreBMS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScoreBMS() {
        return scoreBMS;
    }

    /**
     * Sets the value of the scoreBMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScoreBMS(String value) {
        this.scoreBMS = value;
    }

    /**
     * Gets the value of the detailScoreBMS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailScoreBMS() {
        return detailScoreBMS;
    }

    /**
     * Sets the value of the detailScoreBMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailScoreBMS(String value) {
        this.detailScoreBMS = value;
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
     * Gets the value of the pobDes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPobDes() {
        return pobDes;
    }

    /**
     * Sets the value of the pobDes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPobDes(String value) {
        this.pobDes = value;
    }

    /**
     * Gets the value of the jobs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobs() {
        return jobs;
    }

    /**
     * Sets the value of the jobs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobs(String value) {
        this.jobs = value;
    }

    /**
     * Gets the value of the handoverA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandoverA() {
        return handoverA;
    }

    /**
     * Sets the value of the handoverA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandoverA(String value) {
        this.handoverA = value;
    }

    /**
     * Gets the value of the createDateHandover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateDateHandover() {
        return createDateHandover;
    }

    /**
     * Sets the value of the createDateHandover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDateHandover(String value) {
        this.createDateHandover = value;
    }

    /**
     * Gets the value of the createByHandover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateByHandover() {
        return createByHandover;
    }

    /**
     * Sets the value of the createByHandover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateByHandover(String value) {
        this.createByHandover = value;
    }

    /**
     * Gets the value of the issueFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueFlag() {
        return issueFlag;
    }

    /**
     * Sets the value of the issueFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueFlag(String value) {
        this.issueFlag = value;
    }

    /**
     * Gets the value of the expiredPassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpiredPassport() {
        return expiredPassport;
    }

    /**
     * Sets the value of the expiredPassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpiredPassport(String value) {
        this.expiredPassport = value;
    }

    /**
     * Gets the value of the issuePassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuePassport() {
        return issuePassport;
    }

    /**
     * Sets the value of the issuePassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuePassport(String value) {
        this.issuePassport = value;
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
     * Gets the value of the requestUserB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestUserB() {
        return requestUserB;
    }

    /**
     * Sets the value of the requestUserB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestUserB(String value) {
        this.requestUserB = value;
    }

    /**
     * Gets the value of the requestDateB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestDateB() {
        return requestDateB;
    }

    /**
     * Sets the value of the requestDateB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestDateB(String value) {
        this.requestDateB = value;
    }

    /**
     * Gets the value of the approveUserB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveUserB() {
        return approveUserB;
    }

    /**
     * Sets the value of the approveUserB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveUserB(String value) {
        this.approveUserB = value;
    }

    /**
     * Gets the value of the approveDateB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveDateB() {
        return approveDateB;
    }

    /**
     * Sets the value of the approveDateB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveDateB(String value) {
        this.approveDateB = value;
    }

    /**
     * Gets the value of the requestNoteB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestNoteB() {
        return requestNoteB;
    }

    /**
     * Sets the value of the requestNoteB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestNoteB(String value) {
        this.requestNoteB = value;
    }

    /**
     * Gets the value of the approveNoteB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveNoteB() {
        return approveNoteB;
    }

    /**
     * Sets the value of the approveNoteB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveNoteB(String value) {
        this.approveNoteB = value;
    }

    /**
     * Gets the value of the handoverC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHandoverC() {
        return handoverC;
    }

    /**
     * Sets the value of the handoverC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHandoverC(String value) {
        this.handoverC = value;
    }

    /**
     * Gets the value of the createDateC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateDateC() {
        return createDateC;
    }

    /**
     * Sets the value of the createDateC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDateC(String value) {
        this.createDateC = value;
    }

    /**
     * Gets the value of the printDateC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintDateC() {
        return printDateC;
    }

    /**
     * Sets the value of the printDateC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintDateC(String value) {
        this.printDateC = value;
    }

    /**
     * Gets the value of the serial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerial(String value) {
        this.serial = value;
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
     * Gets the value of the icaoLineOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoLineOne() {
        return icaoLineOne;
    }

    /**
     * Sets the value of the icaoLineOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoLineOne(String value) {
        this.icaoLineOne = value;
    }

    /**
     * Gets the value of the icaoLineTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcaoLineTwo() {
        return icaoLineTwo;
    }

    /**
     * Sets the value of the icaoLineTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcaoLineTwo(String value) {
        this.icaoLineTwo = value;
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
     * Gets the value of the globalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalId() {
        return globalId;
    }

    /**
     * Sets the value of the globalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalId(String value) {
        this.globalId = value;
    }

    /**
     * Gets the value of the originId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginId() {
        return originId;
    }

    /**
     * Sets the value of the originId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginId(String value) {
        this.originId = value;
    }

}
