
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentityAdvanceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentityAdvanceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="QueryInfo" type="{http://schemas.datacontract.org/2004/07/CMND.Website.Service}ArrayOfFieldValue" minOccurs="0"/>
 *         &lt;element name="RequestId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityAdvanceRequest", namespace = "http://schemas.datacontract.org/2004/07/CMND.Website.Service", propOrder = {
    "queryInfo",
    "requestId"
})
public class IdentityAdvanceRequest {

    @XmlElementRef(name = "QueryInfo", namespace = "http://schemas.datacontract.org/2004/07/CMND.Website.Service", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfFieldValue> queryInfo;
    @XmlElementRef(name = "RequestId", namespace = "http://schemas.datacontract.org/2004/07/CMND.Website.Service", type = JAXBElement.class, required = false)
    protected JAXBElement<String> requestId;

    /**
     * Gets the value of the queryInfo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfFieldValue }{@code >}
     *     
     */
    public JAXBElement<ArrayOfFieldValue> getQueryInfo() {
        return queryInfo;
    }

    /**
     * Sets the value of the queryInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfFieldValue }{@code >}
     *     
     */
    public void setQueryInfo(JAXBElement<ArrayOfFieldValue> value) {
        this.queryInfo = value;
    }

    /**
     * Gets the value of the requestId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRequestId(JAXBElement<String> value) {
        this.requestId = value;
    }

}
