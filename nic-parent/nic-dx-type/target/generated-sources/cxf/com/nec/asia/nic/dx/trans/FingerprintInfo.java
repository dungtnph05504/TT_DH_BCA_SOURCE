
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FingerprintInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FingerprintInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CmlafTemplate" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="Fingerprints" type="{http://trans.dx.nic.asia.nec.com/}Fingerprint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FingerprintInfo", propOrder = {
    "cmlafTemplate",
    "fingerprints"
})
public class FingerprintInfo {

    @XmlElement(name = "CmlafTemplate", nillable = true)
    protected byte[] cmlafTemplate;
    @XmlElement(name = "Fingerprints", nillable = true)
    protected List<Fingerprint> fingerprints;

    /**
     * Gets the value of the cmlafTemplate property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getCmlafTemplate() {
        return cmlafTemplate;
    }

    /**
     * Sets the value of the cmlafTemplate property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setCmlafTemplate(byte[] value) {
        this.cmlafTemplate = value;
    }

    /**
     * Gets the value of the fingerprints property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fingerprints property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFingerprints().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fingerprint }
     * 
     * 
     */
    public List<Fingerprint> getFingerprints() {
        if (fingerprints == null) {
            fingerprints = new ArrayList<Fingerprint>();
        }
        return this.fingerprints;
    }

}
