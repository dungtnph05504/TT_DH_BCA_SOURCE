
package com.nec.asia.nic.dx.idserver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentificationResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentificationResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="matchResult" type="{http://idserver.dx.nic.asia.nec.com/}MatchResultDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentificationResponse", propOrder = {
    "matchResult"
})
public class IdentificationResponse {

    protected MatchResultDto matchResult;

    /**
     * Gets the value of the matchResult property.
     * 
     * @return
     *     possible object is
     *     {@link MatchResultDto }
     *     
     */
    public MatchResultDto getMatchResult() {
        return matchResult;
    }

    /**
     * Sets the value of the matchResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MatchResultDto }
     *     
     */
    public void setMatchResult(MatchResultDto value) {
        this.matchResult = value;
    }

}
