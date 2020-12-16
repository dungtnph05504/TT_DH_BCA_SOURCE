
package com.nec.asia.nic.dx.trans;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for PaymentDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypePayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StatusFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SubTypePayment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PaymentAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreateWsktnId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UpdateWsktnId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDetail", propOrder = {
    "paymentID",
    "typePayment",
    "statusFee",
    "subTypePayment",
    "paymentAmount",
    "createBy",
    "createWsktnId",
    "createDate",
    "updateBy",
    "updateWsktnId",
    "updateDate",
    "note"
})
public class PaymentDetail {

    @XmlElement(name = "PaymentID")
    protected String paymentID;
    @XmlElement(name = "TypePayment", required = true)
    protected String typePayment;
    @XmlElement(name = "StatusFee", required = true)
    protected String statusFee;
    @XmlElement(name = "SubTypePayment", required = true)
    protected String subTypePayment;
    @XmlElement(name = "PaymentAmount")
    protected double paymentAmount;
    @XmlElement(name = "CreateBy", required = true)
    protected String createBy;
    @XmlElement(name = "CreateWsktnId", required = true)
    protected String createWsktnId;
    @XmlElement(name = "CreateDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date createDate;
    @XmlElement(name = "UpdateBy", required = true)
    protected String updateBy;
    @XmlElement(name = "UpdateWsktnId", required = true)
    protected String updateWsktnId;
    @XmlElement(name = "UpdateDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date updateDate;
    @XmlElement(name = "Note", required = true)
    protected String note;

    /**
     * Gets the value of the paymentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentID() {
        return paymentID;
    }

    /**
     * Sets the value of the paymentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentID(String value) {
        this.paymentID = value;
    }

    /**
     * Gets the value of the typePayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePayment() {
        return typePayment;
    }

    /**
     * Sets the value of the typePayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePayment(String value) {
        this.typePayment = value;
    }

    /**
     * Gets the value of the statusFee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusFee() {
        return statusFee;
    }

    /**
     * Sets the value of the statusFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusFee(String value) {
        this.statusFee = value;
    }

    /**
     * Gets the value of the subTypePayment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubTypePayment() {
        return subTypePayment;
    }

    /**
     * Sets the value of the subTypePayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubTypePayment(String value) {
        this.subTypePayment = value;
    }

    /**
     * Gets the value of the paymentAmount property.
     * 
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     */
    public void setPaymentAmount(double value) {
        this.paymentAmount = value;
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
     * Gets the value of the createWsktnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateWsktnId() {
        return createWsktnId;
    }

    /**
     * Sets the value of the createWsktnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateWsktnId(String value) {
        this.createWsktnId = value;
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
     * Gets the value of the updateWsktnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateWsktnId() {
        return updateWsktnId;
    }

    /**
     * Sets the value of the updateWsktnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateWsktnId(String value) {
        this.updateWsktnId = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDate(Date value) {
        this.updateDate = value;
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

}
