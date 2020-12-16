
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
 * <p>Java class for EppRecieptManager complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EppRecieptManager">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecieptNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecieptName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AuthenticationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fullname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dob" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompetentAuthorities" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReferPersonNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NationPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PayPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateResultPlan" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Officers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Submitter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataPrint" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModifyDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ModifyBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NinNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReasonPayment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodeBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumberBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PaymentAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ReceiveFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EppRecieptManager", propOrder = {
    "recieptNo",
    "recieptName",
    "authenticationCode",
    "fullname",
    "dob",
    "status",
    "phone",
    "competentAuthorities",
    "referPersonNo",
    "nationPlan",
    "payPlan",
    "dateResultPlan",
    "note",
    "officers",
    "submitter",
    "dataPrint",
    "createDate",
    "createBy",
    "modifyDate",
    "modifyBy",
    "description",
    "ninNumber",
    "paymentFlag",
    "reasonPayment",
    "codeBill",
    "numberBill",
    "paymentAmount",
    "count",
    "receiveFlag",
    "dateDob"
})
public class EppRecieptManager {

    @XmlElement(name = "RecieptNo")
    protected String recieptNo;
    @XmlElement(name = "RecieptName")
    protected String recieptName;
    @XmlElement(name = "AuthenticationCode")
    protected String authenticationCode;
    @XmlElement(name = "Fullname")
    protected String fullname;
    @XmlElement(name = "Dob", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dob;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "CompetentAuthorities")
    protected String competentAuthorities;
    @XmlElement(name = "ReferPersonNo")
    protected String referPersonNo;
    @XmlElement(name = "NationPlan")
    protected String nationPlan;
    @XmlElement(name = "PayPlan")
    protected String payPlan;
    @XmlElement(name = "DateResultPlan", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dateResultPlan;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Officers")
    protected String officers;
    @XmlElement(name = "Submitter")
    protected String submitter;
    @XmlElement(name = "DataPrint")
    protected byte[] dataPrint;
    @XmlElement(name = "CreateDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date createDate;
    @XmlElement(name = "CreateBy")
    protected String createBy;
    @XmlElement(name = "ModifyDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date modifyDate;
    @XmlElement(name = "ModifyBy")
    protected String modifyBy;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "NinNumber")
    protected String ninNumber;
    @XmlElement(name = "PaymentFlag")
    protected String paymentFlag;
    @XmlElement(name = "ReasonPayment")
    protected String reasonPayment;
    @XmlElement(name = "CodeBill")
    protected String codeBill;
    @XmlElement(name = "NumberBill")
    protected String numberBill;
    @XmlElement(name = "PaymentAmount")
    protected Double paymentAmount;
    @XmlElement(name = "Count")
    protected Integer count;
    @XmlElement(name = "ReceiveFlag")
    protected String receiveFlag;
    @XmlElement(name = "DateDob")
    protected String dateDob;

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
     * Gets the value of the recieptName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecieptName() {
        return recieptName;
    }

    /**
     * Sets the value of the recieptName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecieptName(String value) {
        this.recieptName = value;
    }

    /**
     * Gets the value of the authenticationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthenticationCode() {
        return authenticationCode;
    }

    /**
     * Sets the value of the authenticationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthenticationCode(String value) {
        this.authenticationCode = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
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
     * Gets the value of the referPersonNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferPersonNo() {
        return referPersonNo;
    }

    /**
     * Sets the value of the referPersonNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferPersonNo(String value) {
        this.referPersonNo = value;
    }

    /**
     * Gets the value of the nationPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationPlan() {
        return nationPlan;
    }

    /**
     * Sets the value of the nationPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationPlan(String value) {
        this.nationPlan = value;
    }

    /**
     * Gets the value of the payPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayPlan() {
        return payPlan;
    }

    /**
     * Sets the value of the payPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayPlan(String value) {
        this.payPlan = value;
    }

    /**
     * Gets the value of the dateResultPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateResultPlan() {
        return dateResultPlan;
    }

    /**
     * Sets the value of the dateResultPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateResultPlan(Date value) {
        this.dateResultPlan = value;
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
     * Gets the value of the officers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficers() {
        return officers;
    }

    /**
     * Sets the value of the officers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficers(String value) {
        this.officers = value;
    }

    /**
     * Gets the value of the submitter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubmitter() {
        return submitter;
    }

    /**
     * Sets the value of the submitter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubmitter(String value) {
        this.submitter = value;
    }

    /**
     * Gets the value of the dataPrint property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getDataPrint() {
        return dataPrint;
    }

    /**
     * Sets the value of the dataPrint property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setDataPrint(byte[] value) {
        this.dataPrint = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDate(Date value) {
        this.createDate = value;
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
     * Gets the value of the modifyDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets the value of the modifyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyDate(Date value) {
        this.modifyDate = value;
    }

    /**
     * Gets the value of the modifyBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifyBy() {
        return modifyBy;
    }

    /**
     * Sets the value of the modifyBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifyBy(String value) {
        this.modifyBy = value;
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

    /**
     * Gets the value of the ninNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNinNumber() {
        return ninNumber;
    }

    /**
     * Sets the value of the ninNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNinNumber(String value) {
        this.ninNumber = value;
    }

    /**
     * Gets the value of the paymentFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentFlag() {
        return paymentFlag;
    }

    /**
     * Sets the value of the paymentFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentFlag(String value) {
        this.paymentFlag = value;
    }

    /**
     * Gets the value of the reasonPayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonPayment() {
        return reasonPayment;
    }

    /**
     * Sets the value of the reasonPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonPayment(String value) {
        this.reasonPayment = value;
    }

    /**
     * Gets the value of the codeBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeBill() {
        return codeBill;
    }

    /**
     * Sets the value of the codeBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeBill(String value) {
        this.codeBill = value;
    }

    /**
     * Gets the value of the numberBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberBill() {
        return numberBill;
    }

    /**
     * Sets the value of the numberBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberBill(String value) {
        this.numberBill = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPaymentAmount(Double value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the count property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCount(Integer value) {
        this.count = value;
    }

    /**
     * Gets the value of the receiveFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveFlag() {
        return receiveFlag;
    }

    /**
     * Sets the value of the receiveFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveFlag(String value) {
        this.receiveFlag = value;
    }

    /**
     * Gets the value of the dateDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateDob() {
        return dateDob;
    }

    /**
     * Sets the value of the dateDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateDob(String value) {
        this.dateDob = value;
    }

}
