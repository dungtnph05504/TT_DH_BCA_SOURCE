
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetIdentityInfoAdvanceAsJsonResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getIdentityInfoAdvanceAsJsonResult"
})
@XmlRootElement(name = "GetIdentityInfoAdvanceAsJsonResponse")
public class GetIdentityInfoAdvanceAsJsonResponse {

    @XmlElementRef(name = "GetIdentityInfoAdvanceAsJsonResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> getIdentityInfoAdvanceAsJsonResult;

    /**
     * Gets the value of the getIdentityInfoAdvanceAsJsonResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getGetIdentityInfoAdvanceAsJsonResult() {
        return getIdentityInfoAdvanceAsJsonResult;
    }

    /**
     * Sets the value of the getIdentityInfoAdvanceAsJsonResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setGetIdentityInfoAdvanceAsJsonResult(JAXBElement<String> value) {
        this.getIdentityInfoAdvanceAsJsonResult = value;
    }

}
