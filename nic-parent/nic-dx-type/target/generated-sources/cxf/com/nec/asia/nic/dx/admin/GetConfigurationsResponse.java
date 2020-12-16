
package com.nec.asia.nic.dx.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetConfigurationsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetConfigurationsResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="configType" type="{http://admin.dx.nic.asia.nec.com/}ConfigurationType" minOccurs="0"/>
 *         &lt;element name="configDataXml" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetConfigurationsResponse", propOrder = {
    "configType",
    "configDataXml"
})
public class GetConfigurationsResponse {

    protected ConfigurationType configType;
    protected String configDataXml;

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
     * Gets the value of the configDataXml property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigDataXml() {
        return configDataXml;
    }

    /**
     * Sets the value of the configDataXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigDataXml(String value) {
        this.configDataXml = value;
    }

}
