
package com.nec.asia.nic.dx.idserver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImageFingerprintDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImageFingerprintDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="imageData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="imageType" type="{http://idserver.dx.nic.asia.nec.com/}ImageType"/>
 *         &lt;element name="fingerPosition" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageFingerprintDto", propOrder = {
    "imageData",
    "imageType",
    "fingerPosition"
})
public class ImageFingerprintDto {

    @XmlElement(required = true)
    protected byte[] imageData;
    @XmlElement(required = true)
    protected ImageType imageType;
    protected short fingerPosition;

    /**
     * Gets the value of the imageData property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getImageData() {
        return imageData;
    }

    /**
     * Sets the value of the imageData property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setImageData(byte[] value) {
        this.imageData = value;
    }

    /**
     * Gets the value of the imageType property.
     * 
     * @return
     *     possible object is
     *     {@link ImageType }
     *     
     */
    public ImageType getImageType() {
        return imageType;
    }

    /**
     * Sets the value of the imageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageType }
     *     
     */
    public void setImageType(ImageType value) {
        this.imageType = value;
    }

    /**
     * Gets the value of the fingerPosition property.
     * 
     */
    public short getFingerPosition() {
        return fingerPosition;
    }

    /**
     * Sets the value of the fingerPosition property.
     * 
     */
    public void setFingerPosition(short value) {
        this.fingerPosition = value;
    }

}
