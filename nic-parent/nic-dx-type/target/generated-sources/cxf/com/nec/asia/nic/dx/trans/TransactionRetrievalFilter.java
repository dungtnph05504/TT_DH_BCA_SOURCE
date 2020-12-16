
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateAdapter;


/**
 * <p>Java class for TransactionRetrievalFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionRetrievalFilter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataType" type="{http://trans.dx.nic.asia.nec.com/}TransactionRetrievalDataType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="recordType" type="{http://trans.dx.nic.asia.nec.com/}TransactionRetrievalRecordType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PassportNo" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="NIN" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Gender" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Surname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="birthDate" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="birthDateApprox" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="regisSite" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionRetrievalFilter", propOrder = {
    "dataType",
    "recordType"
})
public class TransactionRetrievalFilter {

    protected List<TransactionRetrievalDataType> dataType;
    protected TransactionRetrievalRecordType recordType;
    @XmlAttribute(name = "PassportNo")
    protected String passportNo;
    @XmlAttribute(name = "NIN")
    protected String nin;
    @XmlAttribute(name = "TransactionID")
    protected String transactionID;
    @XmlAttribute(name = "Gender")
    protected String gender;
    @XmlAttribute(name = "Surname")
    protected String surname;
    @XmlAttribute(name = "FirstName")
    protected String firstName;
    @XmlAttribute(name = "MiddleName")
    protected String middleName;
    @XmlAttribute(name = "birthDate")
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date birthDate;
    @XmlAttribute(name = "birthDateApprox")
    protected String birthDateApprox;
    @XmlAttribute(name = "regisSite")
    protected String regisSite;

    /**
     * Gets the value of the dataType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionRetrievalDataType }
     * 
     * 
     */
    public List<TransactionRetrievalDataType> getDataType() {
        if (dataType == null) {
            dataType = new ArrayList<TransactionRetrievalDataType>();
        }
        return this.dataType;
    }

    /**
     * Gets the value of the recordType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionRetrievalRecordType }
     *     
     */
    public TransactionRetrievalRecordType getRecordType() {
        return recordType;
    }

    /**
     * Sets the value of the recordType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionRetrievalRecordType }
     *     
     */
    public void setRecordType(TransactionRetrievalRecordType value) {
        this.recordType = value;
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
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthDate(Date value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the birthDateApprox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBirthDateApprox() {
        return birthDateApprox;
    }

    /**
     * Sets the value of the birthDateApprox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthDateApprox(String value) {
        this.birthDateApprox = value;
    }

    /**
     * Gets the value of the regisSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegisSite() {
        return regisSite;
    }

    /**
     * Sets the value of the regisSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegisSite(String value) {
        this.regisSite = value;
    }

}
