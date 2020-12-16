
package com.nec.asia.nic.dx.idserver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScoreDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScoreDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fingerPosition" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScoreDto", propOrder = {
    "fingerPosition",
    "score"
})
public class ScoreDto {

    protected Short fingerPosition;
    protected Float score;

    /**
     * Gets the value of the fingerPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getFingerPosition() {
        return fingerPosition;
    }

    /**
     * Sets the value of the fingerPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setFingerPosition(Short value) {
        this.fingerPosition = value;
    }

    /**
     * Gets the value of the score property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setScore(Float value) {
        this.score = value;
    }

}
