
package com.nec.asia.nic.dx.trans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Fingerprint complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Fingerprint">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FingerprintData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="FingerprintPosition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MinutiaData" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="MinutiaFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Fingerprint", propOrder = {
    "fingerprintData",
    "fingerprintPosition",
    "minutiaData",
    "minutiaFormat"
})
public class Fingerprint {

    @XmlElement(name = "FingerprintData")
    protected byte[] fingerprintData;
    @XmlElement(name = "FingerprintPosition")
    protected String fingerprintPosition;
    @XmlElement(name = "MinutiaData", nillable = true)
    protected byte[] minutiaData;
    @XmlElement(name = "MinutiaFormat", nillable = true)
    protected String minutiaFormat;

    /**
     * Gets the value of the fingerprintData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFingerprintData() {
        return fingerprintData;
    }

    /**
     * Sets the value of the fingerprintData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFingerprintData(byte[] value) {
        this.fingerprintData = value;
    }

    /**
     * Gets the value of the fingerprintPosition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFingerprintPosition() {
        return fingerprintPosition;
    }

    /**
     * Sets the value of the fingerprintPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFingerprintPosition(String value) {
        this.fingerprintPosition = value;
    }

    /**
     * Gets the value of the minutiaData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getMinutiaData() {
        return minutiaData;
    }

    /**
     * Sets the value of the minutiaData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setMinutiaData(byte[] value) {
        this.minutiaData = value;
    }

    /**
     * Gets the value of the minutiaFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinutiaFormat() {
        return minutiaFormat;
    }

    /**
     * Sets the value of the minutiaFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinutiaFormat(String value) {
        this.minutiaFormat = value;
    }

}
