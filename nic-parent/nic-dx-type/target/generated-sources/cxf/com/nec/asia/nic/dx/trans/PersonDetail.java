
package com.nec.asia.nic.dx.trans;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateAdapter;


/**
 * <p>Java class for PersonDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SurnameFull" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurnameLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurnameLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SurnameLenExceedFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstnameFull" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstnameLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstnameLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FirstnameLenExceedFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MiddlenameFull" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MiddlenameLine1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MiddlenameLine2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MiddlenameLenExceedFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Aliasname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateOfBirth" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ApproxDOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrintDOB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlaceOfBirth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Gender" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaritalStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherMiddlename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FatherCitizenship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotherFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotherMiddlename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotherSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MotherCitizenship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpouseFirstname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpouseMiddlename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpouseSurname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpouseCitizenship" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonDetail", propOrder = {
    "surnameFull",
    "surnameLine1",
    "surnameLine2",
    "surnameLenExceedFlag",
    "firstnameFull",
    "firstnameLine1",
    "firstnameLine2",
    "firstnameLenExceedFlag",
    "middlenameFull",
    "middlenameLine1",
    "middlenameLine2",
    "middlenameLenExceedFlag",
    "aliasname",
    "nationality",
    "dateOfBirth",
    "approxDOB",
    "printDOB",
    "placeOfBirth",
    "gender",
    "maritalStatus",
    "fatherFirstname",
    "fatherMiddlename",
    "fatherSurname",
    "fatherCitizenship",
    "motherFirstname",
    "motherMiddlename",
    "motherSurname",
    "motherCitizenship",
    "spouseFirstname",
    "spouseMiddlename",
    "spouseSurname",
    "spouseCitizenship"
})
public class PersonDetail {

    @XmlElement(name = "SurnameFull")
    protected String surnameFull;
    @XmlElement(name = "SurnameLine1")
    protected String surnameLine1;
    @XmlElement(name = "SurnameLine2")
    protected String surnameLine2;
    @XmlElement(name = "SurnameLenExceedFlag")
    protected String surnameLenExceedFlag;
    @XmlElement(name = "FirstnameFull")
    protected String firstnameFull;
    @XmlElement(name = "FirstnameLine1")
    protected String firstnameLine1;
    @XmlElement(name = "FirstnameLine2")
    protected String firstnameLine2;
    @XmlElement(name = "FirstnameLenExceedFlag")
    protected String firstnameLenExceedFlag;
    @XmlElement(name = "MiddlenameFull")
    protected String middlenameFull;
    @XmlElement(name = "MiddlenameLine1")
    protected String middlenameLine1;
    @XmlElement(name = "MiddlenameLine2")
    protected String middlenameLine2;
    @XmlElement(name = "MiddlenameLenExceedFlag")
    protected String middlenameLenExceedFlag;
    @XmlElement(name = "Aliasname")
    protected String aliasname;
    @XmlElement(name = "Nationality")
    protected String nationality;
    @XmlElement(name = "DateOfBirth", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dateOfBirth;
    @XmlElement(name = "ApproxDOB")
    protected String approxDOB;
    @XmlElement(name = "PrintDOB")
    protected String printDOB;
    @XmlElement(name = "PlaceOfBirth")
    protected String placeOfBirth;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "MaritalStatus")
    protected String maritalStatus;
    @XmlElement(name = "FatherFirstname")
    protected String fatherFirstname;
    @XmlElement(name = "FatherMiddlename")
    protected String fatherMiddlename;
    @XmlElement(name = "FatherSurname")
    protected String fatherSurname;
    @XmlElement(name = "FatherCitizenship")
    protected String fatherCitizenship;
    @XmlElement(name = "MotherFirstname")
    protected String motherFirstname;
    @XmlElement(name = "MotherMiddlename")
    protected String motherMiddlename;
    @XmlElement(name = "MotherSurname")
    protected String motherSurname;
    @XmlElement(name = "MotherCitizenship")
    protected String motherCitizenship;
    @XmlElement(name = "SpouseFirstname")
    protected String spouseFirstname;
    @XmlElement(name = "SpouseMiddlename")
    protected String spouseMiddlename;
    @XmlElement(name = "SpouseSurname")
    protected String spouseSurname;
    @XmlElement(name = "SpouseCitizenship")
    protected String spouseCitizenship;

    /**
     * Gets the value of the surnameFull property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurnameFull() {
        return surnameFull;
    }

    /**
     * Sets the value of the surnameFull property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurnameFull(String value) {
        this.surnameFull = value;
    }

    /**
     * Gets the value of the surnameLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurnameLine1() {
        return surnameLine1;
    }

    /**
     * Sets the value of the surnameLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurnameLine1(String value) {
        this.surnameLine1 = value;
    }

    /**
     * Gets the value of the surnameLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurnameLine2() {
        return surnameLine2;
    }

    /**
     * Sets the value of the surnameLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurnameLine2(String value) {
        this.surnameLine2 = value;
    }

    /**
     * Gets the value of the surnameLenExceedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurnameLenExceedFlag() {
        return surnameLenExceedFlag;
    }

    /**
     * Sets the value of the surnameLenExceedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurnameLenExceedFlag(String value) {
        this.surnameLenExceedFlag = value;
    }

    /**
     * Gets the value of the firstnameFull property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstnameFull() {
        return firstnameFull;
    }

    /**
     * Sets the value of the firstnameFull property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstnameFull(String value) {
        this.firstnameFull = value;
    }

    /**
     * Gets the value of the firstnameLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstnameLine1() {
        return firstnameLine1;
    }

    /**
     * Sets the value of the firstnameLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstnameLine1(String value) {
        this.firstnameLine1 = value;
    }

    /**
     * Gets the value of the firstnameLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstnameLine2() {
        return firstnameLine2;
    }

    /**
     * Sets the value of the firstnameLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstnameLine2(String value) {
        this.firstnameLine2 = value;
    }

    /**
     * Gets the value of the firstnameLenExceedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstnameLenExceedFlag() {
        return firstnameLenExceedFlag;
    }

    /**
     * Sets the value of the firstnameLenExceedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstnameLenExceedFlag(String value) {
        this.firstnameLenExceedFlag = value;
    }

    /**
     * Gets the value of the middlenameFull property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddlenameFull() {
        return middlenameFull;
    }

    /**
     * Sets the value of the middlenameFull property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddlenameFull(String value) {
        this.middlenameFull = value;
    }

    /**
     * Gets the value of the middlenameLine1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddlenameLine1() {
        return middlenameLine1;
    }

    /**
     * Sets the value of the middlenameLine1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddlenameLine1(String value) {
        this.middlenameLine1 = value;
    }

    /**
     * Gets the value of the middlenameLine2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddlenameLine2() {
        return middlenameLine2;
    }

    /**
     * Sets the value of the middlenameLine2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddlenameLine2(String value) {
        this.middlenameLine2 = value;
    }

    /**
     * Gets the value of the middlenameLenExceedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddlenameLenExceedFlag() {
        return middlenameLenExceedFlag;
    }

    /**
     * Sets the value of the middlenameLenExceedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddlenameLenExceedFlag(String value) {
        this.middlenameLenExceedFlag = value;
    }

    /**
     * Gets the value of the aliasname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAliasname() {
        return aliasname;
    }

    /**
     * Sets the value of the aliasname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAliasname(String value) {
        this.aliasname = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfBirth(Date value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the approxDOB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproxDOB() {
        return approxDOB;
    }

    /**
     * Sets the value of the approxDOB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproxDOB(String value) {
        this.approxDOB = value;
    }

    /**
     * Gets the value of the printDOB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintDOB() {
        return printDOB;
    }

    /**
     * Sets the value of the printDOB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintDOB(String value) {
        this.printDOB = value;
    }

    /**
     * Gets the value of the placeOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * Sets the value of the placeOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlaceOfBirth(String value) {
        this.placeOfBirth = value;
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
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the fatherFirstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFatherFirstname() {
        return fatherFirstname;
    }

    /**
     * Sets the value of the fatherFirstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherFirstname(String value) {
        this.fatherFirstname = value;
    }

    /**
     * Gets the value of the fatherMiddlename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFatherMiddlename() {
        return fatherMiddlename;
    }

    /**
     * Sets the value of the fatherMiddlename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherMiddlename(String value) {
        this.fatherMiddlename = value;
    }

    /**
     * Gets the value of the fatherSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFatherSurname() {
        return fatherSurname;
    }

    /**
     * Sets the value of the fatherSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherSurname(String value) {
        this.fatherSurname = value;
    }

    /**
     * Gets the value of the fatherCitizenship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFatherCitizenship() {
        return fatherCitizenship;
    }

    /**
     * Sets the value of the fatherCitizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFatherCitizenship(String value) {
        this.fatherCitizenship = value;
    }

    /**
     * Gets the value of the motherFirstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotherFirstname() {
        return motherFirstname;
    }

    /**
     * Sets the value of the motherFirstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotherFirstname(String value) {
        this.motherFirstname = value;
    }

    /**
     * Gets the value of the motherMiddlename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotherMiddlename() {
        return motherMiddlename;
    }

    /**
     * Sets the value of the motherMiddlename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotherMiddlename(String value) {
        this.motherMiddlename = value;
    }

    /**
     * Gets the value of the motherSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotherSurname() {
        return motherSurname;
    }

    /**
     * Sets the value of the motherSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotherSurname(String value) {
        this.motherSurname = value;
    }

    /**
     * Gets the value of the motherCitizenship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotherCitizenship() {
        return motherCitizenship;
    }

    /**
     * Sets the value of the motherCitizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotherCitizenship(String value) {
        this.motherCitizenship = value;
    }

    /**
     * Gets the value of the spouseFirstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseFirstname() {
        return spouseFirstname;
    }

    /**
     * Sets the value of the spouseFirstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseFirstname(String value) {
        this.spouseFirstname = value;
    }

    /**
     * Gets the value of the spouseMiddlename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseMiddlename() {
        return spouseMiddlename;
    }

    /**
     * Sets the value of the spouseMiddlename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseMiddlename(String value) {
        this.spouseMiddlename = value;
    }

    /**
     * Gets the value of the spouseSurname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseSurname() {
        return spouseSurname;
    }

    /**
     * Sets the value of the spouseSurname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseSurname(String value) {
        this.spouseSurname = value;
    }

    /**
     * Gets the value of the spouseCitizenship property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpouseCitizenship() {
        return spouseCitizenship;
    }

    /**
     * Sets the value of the spouseCitizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpouseCitizenship(String value) {
        this.spouseCitizenship = value;
    }

}
