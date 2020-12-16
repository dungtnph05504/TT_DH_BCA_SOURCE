
package com.nec.asia.nic.dx.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FunctionDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FunctionDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FunctionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FunctionURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FunctionDto", propOrder = {
    "functionID",
    "functionURL"
})
public class FunctionDto {

    @XmlElement(name = "FunctionID")
    protected String functionID;
    @XmlElement(name = "FunctionURL")
    protected String functionURL;

    /**
     * Gets the value of the functionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionID() {
        return functionID;
    }

    /**
     * Sets the value of the functionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionID(String value) {
        this.functionID = value;
    }

    /**
     * Gets the value of the functionURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunctionURL() {
        return functionURL;
    }

    /**
     * Sets the value of the functionURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunctionURL(String value) {
        this.functionURL = value;
    }

}
