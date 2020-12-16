
package com.nec.asia.nic.dx.admin;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for OfficalNationTransaction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OfficalNationTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OfficalNationNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BusinessID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportExpireDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PassportIssueDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PassportIssuePlace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VisaNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescriptionON" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DecisionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Signer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SignDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CompetentAuthorities" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompetentAuthoritiesEng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TimePlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TripCost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InvitingAgency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransitNation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TimeLasts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CountryPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="DescriptionQD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Serial" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Fullname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DOB" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="POB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Agency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Position" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PositionEng" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AddressAgency" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rank" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Curb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Jaw" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfficalNationTransaction", propOrder = {
    "officalNationNo",
    "businessID",
    "passportNo",
    "passportType",
    "passportExpireDate",
    "passportIssueDate",
    "passportIssuePlace",
    "visaNo",
    "nationCode",
    "descriptionON",
    "decisionNumber",
    "signer",
    "signDate",
    "competentAuthorities",
    "competentAuthoritiesEng",
    "purpose",
    "timePlan",
    "tripCost",
    "invitingAgency",
    "transitNation",
    "timeLasts",
    "countryPlan",
    "data",
    "descriptionQD",
    "serial",
    "fullname",
    "gender",
    "dob",
    "pob",
    "address",
    "agency",
    "position",
    "positionEng",
    "addressAgency",
    "phone",
    "type",
    "rank",
    "curb",
    "jaw",
    "description"
})
public class OfficalNationTransaction {

    @XmlElement(name = "OfficalNationNo")
    protected String officalNationNo;
    @XmlElement(name = "BusinessID")
    protected Long businessID;
    @XmlElement(name = "PassportNo")
    protected String passportNo;
    @XmlElement(name = "PassportType")
    protected String passportType;
    @XmlElement(name = "PassportExpireDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date passportExpireDate;
    @XmlElement(name = "PassportIssueDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date passportIssueDate;
    @XmlElement(name = "PassportIssuePlace")
    protected String passportIssuePlace;
    @XmlElement(name = "VisaNo")
    protected String visaNo;
    @XmlElement(name = "NationCode")
    protected String nationCode;
    @XmlElement(name = "DescriptionON")
    protected String descriptionON;
    @XmlElement(name = "DecisionNumber")
    protected String decisionNumber;
    @XmlElement(name = "Signer")
    protected String signer;
    @XmlElement(name = "SignDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date signDate;
    @XmlElement(name = "CompetentAuthorities")
    protected String competentAuthorities;
    @XmlElement(name = "CompetentAuthoritiesEng")
    protected String competentAuthoritiesEng;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "TimePlan")
    protected String timePlan;
    @XmlElement(name = "TripCost")
    protected String tripCost;
    @XmlElement(name = "InvitingAgency")
    protected String invitingAgency;
    @XmlElement(name = "TransitNation")
    protected String transitNation;
    @XmlElement(name = "TimeLasts")
    protected String timeLasts;
    @XmlElement(name = "CountryPlan")
    protected String countryPlan;
    @XmlElement(name = "Data")
    protected byte[] data;
    @XmlElement(name = "DescriptionQD")
    protected String descriptionQD;
    @XmlElement(name = "Serial")
    protected Integer serial;
    @XmlElement(name = "Fullname")
    protected String fullname;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "DOB", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dob;
    @XmlElement(name = "POB")
    protected String pob;
    @XmlElement(name = "Address")
    protected String address;
    @XmlElement(name = "Agency")
    protected String agency;
    @XmlElement(name = "Position")
    protected String position;
    @XmlElement(name = "PositionEng")
    protected String positionEng;
    @XmlElement(name = "AddressAgency")
    protected String addressAgency;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "Type")
    protected String type;
    @XmlElement(name = "Rank")
    protected String rank;
    @XmlElement(name = "Curb")
    protected String curb;
    @XmlElement(name = "Jaw")
    protected String jaw;
    @XmlElement(name = "Description")
    protected String description;

    /**
     * Gets the value of the officalNationNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficalNationNo() {
        return officalNationNo;
    }

    /**
     * Sets the value of the officalNationNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficalNationNo(String value) {
        this.officalNationNo = value;
    }

    /**
     * Gets the value of the businessID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getBusinessID() {
        return businessID;
    }

    /**
     * Sets the value of the businessID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setBusinessID(Long value) {
        this.businessID = value;
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
     * Gets the value of the passportExpireDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPassportExpireDate() {
        return passportExpireDate;
    }

    /**
     * Sets the value of the passportExpireDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportExpireDate(Date value) {
        this.passportExpireDate = value;
    }

    /**
     * Gets the value of the passportIssueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPassportIssueDate() {
        return passportIssueDate;
    }

    /**
     * Sets the value of the passportIssueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportIssueDate(Date value) {
        this.passportIssueDate = value;
    }

    /**
     * Gets the value of the passportIssuePlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportIssuePlace() {
        return passportIssuePlace;
    }

    /**
     * Sets the value of the passportIssuePlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportIssuePlace(String value) {
        this.passportIssuePlace = value;
    }

    /**
     * Gets the value of the visaNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisaNo() {
        return visaNo;
    }

    /**
     * Sets the value of the visaNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisaNo(String value) {
        this.visaNo = value;
    }

    /**
     * Gets the value of the nationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationCode() {
        return nationCode;
    }

    /**
     * Sets the value of the nationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationCode(String value) {
        this.nationCode = value;
    }

    /**
     * Gets the value of the descriptionON property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescriptionON() {
        return descriptionON;
    }

    /**
     * Sets the value of the descriptionON property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescriptionON(String value) {
        this.descriptionON = value;
    }

    /**
     * Gets the value of the decisionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecisionNumber() {
        return decisionNumber;
    }

    /**
     * Sets the value of the decisionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecisionNumber(String value) {
        this.decisionNumber = value;
    }

    /**
     * Gets the value of the signer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigner() {
        return signer;
    }

    /**
     * Sets the value of the signer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigner(String value) {
        this.signer = value;
    }

    /**
     * Gets the value of the signDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getSignDate() {
        return signDate;
    }

    /**
     * Sets the value of the signDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignDate(Date value) {
        this.signDate = value;
    }

    /**
     * Gets the value of the competentAuthorities property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompetentAuthorities() {
        return competentAuthorities;
    }

    /**
     * Sets the value of the competentAuthorities property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompetentAuthorities(String value) {
        this.competentAuthorities = value;
    }

    /**
     * Gets the value of the competentAuthoritiesEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompetentAuthoritiesEng() {
        return competentAuthoritiesEng;
    }

    /**
     * Sets the value of the competentAuthoritiesEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompetentAuthoritiesEng(String value) {
        this.competentAuthoritiesEng = value;
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
     * Gets the value of the timePlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimePlan() {
        return timePlan;
    }

    /**
     * Sets the value of the timePlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimePlan(String value) {
        this.timePlan = value;
    }

    /**
     * Gets the value of the tripCost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTripCost() {
        return tripCost;
    }

    /**
     * Sets the value of the tripCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTripCost(String value) {
        this.tripCost = value;
    }

    /**
     * Gets the value of the invitingAgency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvitingAgency() {
        return invitingAgency;
    }

    /**
     * Sets the value of the invitingAgency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvitingAgency(String value) {
        this.invitingAgency = value;
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
     * Gets the value of the timeLasts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeLasts() {
        return timeLasts;
    }

    /**
     * Sets the value of the timeLasts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeLasts(String value) {
        this.timeLasts = value;
    }

    /**
     * Gets the value of the countryPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryPlan() {
        return countryPlan;
    }

    /**
     * Sets the value of the countryPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryPlan(String value) {
        this.countryPlan = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setData(byte[] value) {
        this.data = value;
    }

    /**
     * Gets the value of the descriptionQD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescriptionQD() {
        return descriptionQD;
    }

    /**
     * Sets the value of the descriptionQD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescriptionQD(String value) {
        this.descriptionQD = value;
    }

    /**
     * Gets the value of the serial property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSerial(Integer value) {
        this.serial = value;
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
    public Date getDOB() {
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
    public void setDOB(Date value) {
        this.dob = value;
    }

    /**
     * Gets the value of the pob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOB() {
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
    public void setPOB(String value) {
        this.pob = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the agency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgency() {
        return agency;
    }

    /**
     * Sets the value of the agency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgency(String value) {
        this.agency = value;
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
     * Gets the value of the positionEng property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositionEng() {
        return positionEng;
    }

    /**
     * Sets the value of the positionEng property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositionEng(String value) {
        this.positionEng = value;
    }

    /**
     * Gets the value of the addressAgency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressAgency() {
        return addressAgency;
    }

    /**
     * Sets the value of the addressAgency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressAgency(String value) {
        this.addressAgency = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
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
     * Gets the value of the curb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurb() {
        return curb;
    }

    /**
     * Sets the value of the curb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurb(String value) {
        this.curb = value;
    }

    /**
     * Gets the value of the jaw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJaw() {
        return jaw;
    }

    /**
     * Sets the value of the jaw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJaw(String value) {
        this.jaw = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
