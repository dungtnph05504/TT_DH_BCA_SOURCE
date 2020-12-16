
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
 * <p>Java class for DetailRecieptFee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetailRecieptFee">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecieptNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodeBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumberBill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Price" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="PriceFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModifyDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ModifyBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Reason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailRecieptFee", propOrder = {
    "recieptNo",
    "codeBill",
    "numberBill",
    "price",
    "priceFlag",
    "createDate",
    "createBy",
    "modifyDate",
    "modifyBy",
    "description",
    "reason"
})
public class DetailRecieptFee {

    @XmlElement(name = "RecieptNo")
    protected String recieptNo;
    @XmlElement(name = "CodeBill")
    protected String codeBill;
    @XmlElement(name = "NumberBill")
    protected String numberBill;
    @XmlElement(name = "Price")
    protected Double price;
    @XmlElement(name = "PriceFlag")
    protected String priceFlag;
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
    @XmlElement(name = "Reason")
    protected String reason;

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
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPrice(Double value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceFlag() {
        return priceFlag;
    }

    /**
     * Sets the value of the priceFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceFlag(String value) {
        this.priceFlag = value;
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
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReason(String value) {
        this.reason = value;
    }

}
