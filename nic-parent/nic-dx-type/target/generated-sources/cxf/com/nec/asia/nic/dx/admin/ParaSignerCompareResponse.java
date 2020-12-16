
package com.nec.asia.nic.dx.admin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParaSignerCompareResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParaSignerCompareResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParaSignerCompares" type="{http://admin.dx.nic.asia.nec.com/}ParaSignerCompare" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParaSignerCompareResponse", propOrder = {
    "paraSignerCompares"
})
public class ParaSignerCompareResponse {

    @XmlElement(name = "ParaSignerCompares")
    protected List<ParaSignerCompare> paraSignerCompares;

    /**
     * Gets the value of the paraSignerCompares property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paraSignerCompares property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParaSignerCompares().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParaSignerCompare }
     * 
     * 
     */
    public List<ParaSignerCompare> getParaSignerCompares() {
        if (paraSignerCompares == null) {
            paraSignerCompares = new ArrayList<ParaSignerCompare>();
        }
        return this.paraSignerCompares;
    }

}
