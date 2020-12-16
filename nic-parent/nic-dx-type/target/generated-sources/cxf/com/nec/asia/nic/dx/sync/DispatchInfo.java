
package com.nec.asia.nic.dx.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for DispatchInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DispatchInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DispatchID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DispatchDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PackageInfoList" type="{http://sync.dx.nic.asia.nec.com/}PackageInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DispatchInfo", propOrder = {
    "dispatchID",
    "dispatchDateTime",
    "packageInfoList"
})
@XmlSeeAlso({
    DispatchInfoStatusUpdate.class
})
public class DispatchInfo {

    @XmlElement(name = "DispatchID", required = true)
    protected String dispatchID;
    @XmlElement(name = "DispatchDateTime", required = true, type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dispatchDateTime;
    @XmlElement(name = "PackageInfoList")
    protected List<PackageInfo> packageInfoList;

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
     * Gets the value of the dispatchDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDispatchDateTime() {
        return dispatchDateTime;
    }

    /**
     * Sets the value of the dispatchDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispatchDateTime(Date value) {
        this.dispatchDateTime = value;
    }

    /**
     * Gets the value of the packageInfoList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the packageInfoList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackageInfoList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PackageInfo }
     * 
     * 
     */
    public List<PackageInfo> getPackageInfoList() {
        if (packageInfoList == null) {
            packageInfoList = new ArrayList<PackageInfo>();
        }
        return this.packageInfoList;
    }

}
