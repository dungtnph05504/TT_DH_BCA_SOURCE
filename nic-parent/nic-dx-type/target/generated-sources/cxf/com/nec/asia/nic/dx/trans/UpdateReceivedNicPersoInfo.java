
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateReceivedNicPersoInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateReceivedNicPersoInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NicPersoInfoRefs" type="{http://trans.dx.nic.asia.nec.com/}NicPersoInfoRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="WkstnID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OfficerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SiteCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateReceivedNicPersoInfo", propOrder = {
    "nicPersoInfoRefs",
    "wkstnID",
    "officerID",
    "siteCode"
})
public class UpdateReceivedNicPersoInfo {

    @XmlElement(name = "NicPersoInfoRefs")
    protected List<NicPersoInfoRef> nicPersoInfoRefs;
    @XmlElement(name = "WkstnID", required = true)
    protected String wkstnID;
    @XmlElement(name = "OfficerID", required = true)
    protected String officerID;
    @XmlElement(name = "SiteCode", required = true)
    protected String siteCode;

    /**
     * Gets the value of the nicPersoInfoRefs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nicPersoInfoRefs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNicPersoInfoRefs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NicPersoInfoRef }
     * 
     * 
     */
    public List<NicPersoInfoRef> getNicPersoInfoRefs() {
        if (nicPersoInfoRefs == null) {
            nicPersoInfoRefs = new ArrayList<NicPersoInfoRef>();
        }
        return this.nicPersoInfoRefs;
    }

    /**
     * Gets the value of the wkstnID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWkstnID() {
        return wkstnID;
    }

    /**
     * Sets the value of the wkstnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWkstnID(String value) {
        this.wkstnID = value;
    }

    /**
     * Gets the value of the officerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficerID() {
        return officerID;
    }

    /**
     * Sets the value of the officerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficerID(String value) {
        this.officerID = value;
    }

    /**
     * Gets the value of the siteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * Sets the value of the siteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteCode(String value) {
        this.siteCode = value;
    }

}
