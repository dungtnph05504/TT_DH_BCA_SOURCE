
package com.nec.asia.nic.dx.trans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BufEppListResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BufEppListResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tranId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdHitSearchCPD" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="IdHitSearchBMS" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BufEppListResponse", propOrder = {
    "tranId",
    "idHitSearchCPD",
    "idHitSearchBMS"
})
public class BufEppListResponse {

    protected String tranId;
    @XmlElement(name = "IdHitSearchCPD")
    protected Long idHitSearchCPD;
    @XmlElement(name = "IdHitSearchBMS")
    protected Long idHitSearchBMS;

    /**
     * Gets the value of the tranId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTranId() {
        return tranId;
    }

    /**
     * Sets the value of the tranId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTranId(String value) {
        this.tranId = value;
    }

    /**
     * Gets the value of the idHitSearchCPD property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdHitSearchCPD() {
        return idHitSearchCPD;
    }

    /**
     * Sets the value of the idHitSearchCPD property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdHitSearchCPD(Long value) {
        this.idHitSearchCPD = value;
    }

    /**
     * Gets the value of the idHitSearchBMS property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdHitSearchBMS() {
        return idHitSearchBMS;
    }

    /**
     * Sets the value of the idHitSearchBMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdHitSearchBMS(Long value) {
        this.idHitSearchBMS = value;
    }

}
