
package com.nec.asia.nic.dx.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Payment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Payment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionSubtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoOfTimeLost" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="FeeAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ReduceRateFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WaivableFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReduceRateFeeAmount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Payment", propOrder = {
    "transactionType",
    "transactionSubtype",
    "noOfTimeLost",
    "feeAmount",
    "reduceRateFlag",
    "waivableFlag",
    "reduceRateFeeAmount"
})
public class Payment {

    @XmlElement(name = "TransactionType")
    protected String transactionType;
    @XmlElement(name = "TransactionSubtype")
    protected String transactionSubtype;
    @XmlElement(name = "NoOfTimeLost")
    protected Integer noOfTimeLost;
    @XmlElement(name = "FeeAmount")
    protected Double feeAmount;
    @XmlElement(name = "ReduceRateFlag")
    protected String reduceRateFlag;
    @XmlElement(name = "WaivableFlag")
    protected String waivableFlag;
    @XmlElement(name = "ReduceRateFeeAmount")
    protected Double reduceRateFeeAmount;

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
     * Gets the value of the transactionSubtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionSubtype() {
        return transactionSubtype;
    }

    /**
     * Sets the value of the transactionSubtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionSubtype(String value) {
        this.transactionSubtype = value;
    }

    /**
     * Gets the value of the noOfTimeLost property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNoOfTimeLost() {
        return noOfTimeLost;
    }

    /**
     * Sets the value of the noOfTimeLost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNoOfTimeLost(Integer value) {
        this.noOfTimeLost = value;
    }

    /**
     * Gets the value of the feeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getFeeAmount() {
        return feeAmount;
    }

    /**
     * Sets the value of the feeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setFeeAmount(Double value) {
        this.feeAmount = value;
    }

    /**
     * Gets the value of the reduceRateFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReduceRateFlag() {
        return reduceRateFlag;
    }

    /**
     * Sets the value of the reduceRateFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReduceRateFlag(String value) {
        this.reduceRateFlag = value;
    }

    /**
     * Gets the value of the waivableFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWaivableFlag() {
        return waivableFlag;
    }

    /**
     * Sets the value of the waivableFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWaivableFlag(String value) {
        this.waivableFlag = value;
    }

    /**
     * Gets the value of the reduceRateFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getReduceRateFeeAmount() {
        return reduceRateFeeAmount;
    }

    /**
     * Sets the value of the reduceRateFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setReduceRateFeeAmount(Double value) {
        this.reduceRateFeeAmount = value;
    }

}
