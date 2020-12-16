
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FacialInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FacialInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FacialImages" type="{http://trans.dx.nic.asia.nec.com/}FacialImage" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FacialInfo", propOrder = {
    "facialImages"
})
public class FacialInfo {

    @XmlElement(name = "FacialImages", nillable = true)
    protected List<FacialImage> facialImages;

    /**
     * Gets the value of the facialImages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the facialImages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFacialImages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacialImage }
     * 
     * 
     */
    public List<FacialImage> getFacialImages() {
        if (facialImages == null) {
            facialImages = new ArrayList<FacialImage>();
        }
        return this.facialImages;
    }

}
