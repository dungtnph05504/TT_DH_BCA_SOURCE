
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
 * <p>Java class for GetConfigurations complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetConfigurations">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configType" type="{http://admin.dx.nic.asia.nec.com/}ConfigurationType" minOccurs="0"/>
 *         &lt;element name="retrievalType" type="{http://admin.dx.nic.asia.nec.com/}RetrievalType" minOccurs="0"/>
 *         &lt;element name="previousRetrievalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetConfigurations", propOrder = {
    "configType",
    "retrievalType",
    "previousRetrievalDate"
})
public class GetConfigurations {

    protected ConfigurationType configType;
    protected RetrievalType retrievalType;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date previousRetrievalDate;

    /**
     * Gets the value of the configType property.
     * 
     * @return
     *     possible object is
     *     {@link ConfigurationType }
     *     
     */
    public ConfigurationType getConfigType() {
        return configType;
    }

    /**
     * Sets the value of the configType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfigurationType }
     *     
     */
    public void setConfigType(ConfigurationType value) {
        this.configType = value;
    }

    /**
     * Gets the value of the retrievalType property.
     * 
     * @return
     *     possible object is
     *     {@link RetrievalType }
     *     
     */
    public RetrievalType getRetrievalType() {
        return retrievalType;
    }

    /**
     * Sets the value of the retrievalType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetrievalType }
     *     
     */
    public void setRetrievalType(RetrievalType value) {
        this.retrievalType = value;
    }

    /**
     * Gets the value of the previousRetrievalDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPreviousRetrievalDate() {
        return previousRetrievalDate;
    }

    /**
     * Sets the value of the previousRetrievalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreviousRetrievalDate(Date value) {
        this.previousRetrievalDate = value;
    }

}
