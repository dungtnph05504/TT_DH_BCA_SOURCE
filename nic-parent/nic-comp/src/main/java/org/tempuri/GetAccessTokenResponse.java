
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
 *         &lt;element name="GetAccessTokenResult" type="{http://schemas.datacontract.org/2004/07/Vietbando.MessageType}Singlestring" minOccurs="0"/>
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
    "getAccessTokenResult"
})
@XmlRootElement(name = "GetAccessTokenResponse")
public class GetAccessTokenResponse {

    @XmlElementRef(name = "GetAccessTokenResult", namespace = "http://tempuri.org/", type = JAXBElement.class, required = false)
    protected JAXBElement<Singlestring> getAccessTokenResult;

    /**
     * Gets the value of the getAccessTokenResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Singlestring }{@code >}
     *     
     */
    public JAXBElement<Singlestring> getGetAccessTokenResult() {
        return getAccessTokenResult;
    }

    /**
     * Sets the value of the getAccessTokenResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Singlestring }{@code >}
     *     
     */
    public void setGetAccessTokenResult(JAXBElement<Singlestring> value) {
        this.getAccessTokenResult = value;
    }

}
