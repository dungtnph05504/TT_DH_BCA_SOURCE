
package com.nec.asia.nic.dx.trans;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for NicPersoInfoDownloadFilter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NicPersoInfoDownloadFilter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DispatchID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PackageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfficerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="SiteCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NicPersoInfoDownloadFilter", propOrder = {
    "dispatchID",
    "packageID",
    "passportNo",
    "nin",
    "wkstnID",
    "officerID"
})
public class NicPersoInfoDownloadFilter {

    @XmlElement(name = "DispatchID")
    protected String dispatchID;
    @XmlElement(name = "PackageID")
    protected String packageID;
    @XmlElement(name = "PassportNo")
    protected String passportNo;
    @XmlElement(name = "NIN")
    protected String nin;
    @XmlElement(name = "WkstnID")
    protected String wkstnID;
    @XmlElement(name = "OfficerID")
    protected String officerID;
    @XmlAttribute(name = "StartDate")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date startDate;
    @XmlAttribute(name = "EndDate")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date endDate;
    @XmlAttribute(name = "SiteCode")
    protected String siteCode;

    /**
     * Gets the value of the dispatchID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispatchID() {
        return dispatchID;
    }

    /**
     * Sets the value of the dispatchID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispatchID(String value) {
        this.dispatchID = value;
    }

    /**
     * Gets the value of the packageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackageID() {
        return packageID;
    }

    /**
     * Sets the value of the packageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageID(String value) {
        this.packageID = value;
    }

    /**
     * Gets the value of the passportNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassportNo() {
        return passportNo;
    }

    /**
     * Sets the value of the passportNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassportNo(String value) {
        this.passportNo = value;
    }

    /**
     * Gets the value of the nin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNIN() {
        return nin;
    }

    /**
     * Sets the value of the nin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNIN(String value) {
        this.nin = value;
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
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(Date value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDate(Date value) {
        this.endDate = value;
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
