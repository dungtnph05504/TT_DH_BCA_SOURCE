
package com.nec.asia.nic.dx.trans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BufEppRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BufEppRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransactionMasterId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransCPD" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="TransBMS" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BufEppRequest", propOrder = {
    "transactionId",
    "transactionMasterId",
    "transCPD",
    "transBMS"
})
public class BufEppRequest {

    @XmlElement(name = "TransactionId")
    protected String transactionId;
    @XmlElement(name = "TransactionMasterId")
    protected String transactionMasterId;
    @XmlElement(name = "TransCPD")
    protected Long transCPD;
    @XmlElement(name = "TransBMS")
    protected Long transBMS;

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the transactionMasterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionMasterId() {
        return transactionMasterId;
    }

    /**
     * Sets the value of the transactionMasterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionMasterId(String value) {
        this.transactionMasterId = value;
    }

    /**
     * Gets the value of the transCPD property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTransCPD() {
        return transCPD;
    }

    /**
     * Sets the value of the transCPD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTransCPD(Long value) {
        this.transCPD = value;
    }

    /**
     * Gets the value of the transBMS property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTransBMS() {
        return transBMS;
    }

    /**
     * Sets the value of the transBMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTransBMS(Long value) {
        this.transBMS = value;
    }

}
