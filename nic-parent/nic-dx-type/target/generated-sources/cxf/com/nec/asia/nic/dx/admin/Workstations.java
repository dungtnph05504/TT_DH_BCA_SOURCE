
package com.nec.asia.nic.dx.admin;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for Workstations complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Workstations">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="WkstnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WkstnDesc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WkstnType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessibleFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CounterPriority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateWkstnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateWkstnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DeleteBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeleteWkstnId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeleteDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DeleteFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Workstations", propOrder = {
    "wkstnId",
    "wkstnDesc",
    "wkstnType",
    "accessibleFlag",
    "counterPriority",
    "siteCode",
    "systemId",
    "createBy",
    "createWkstnId",
    "createDate",
    "updateBy",
    "updateWkstnId",
    "updateDate",
    "deleteBy",
    "deleteWkstnId",
    "deleteDate",
    "deleteFlag"
})
public class Workstations {

    @XmlElement(name = "WkstnId")
    protected String wkstnId;
    @XmlElement(name = "WkstnDesc")
    protected String wkstnDesc;
    @XmlElement(name = "WkstnType")
    protected String wkstnType;
    @XmlElement(name = "AccessibleFlag")
    protected String accessibleFlag;
    @XmlElement(name = "CounterPriority")
    protected String counterPriority;
    @XmlElement(name = "SiteCode")
    protected String siteCode;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "CreateBy")
    protected String createBy;
    @XmlElement(name = "CreateWkstnId")
    protected String createWkstnId;
    @XmlElement(name = "CreateDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date createDate;
    @XmlElement(name = "UpdateBy")
    protected String updateBy;
    @XmlElement(name = "UpdateWkstnId")
    protected String updateWkstnId;
    @XmlElement(name = "UpdateDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date updateDate;
    @XmlElement(name = "DeleteBy")
    protected String deleteBy;
    @XmlElement(name = "DeleteWkstnId")
    protected String deleteWkstnId;
    @XmlElement(name = "DeleteDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date deleteDate;
    @XmlElement(name = "DeleteFlag")
    protected String deleteFlag;

    /**
     * Gets the value of the wkstnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWkstnId() {
        return wkstnId;
    }

    /**
     * Sets the value of the wkstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWkstnId(String value) {
        this.wkstnId = value;
    }

    /**
     * Gets the value of the wkstnDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWkstnDesc() {
        return wkstnDesc;
    }

    /**
     * Sets the value of the wkstnDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWkstnDesc(String value) {
        this.wkstnDesc = value;
    }

    /**
     * Gets the value of the wkstnType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWkstnType() {
        return wkstnType;
    }

    /**
     * Sets the value of the wkstnType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWkstnType(String value) {
        this.wkstnType = value;
    }

    /**
     * Gets the value of the accessibleFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessibleFlag() {
        return accessibleFlag;
    }

    /**
     * Sets the value of the accessibleFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessibleFlag(String value) {
        this.accessibleFlag = value;
    }

    /**
     * Gets the value of the counterPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCounterPriority() {
        return counterPriority;
    }

    /**
     * Sets the value of the counterPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCounterPriority(String value) {
        this.counterPriority = value;
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

    /**
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

    /**
     * Gets the value of the createBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Sets the value of the createBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateBy(String value) {
        this.createBy = value;
    }

    /**
     * Gets the value of the createWkstnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateWkstnId() {
        return createWkstnId;
    }

    /**
     * Sets the value of the createWkstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateWkstnId(String value) {
        this.createWkstnId = value;
    }

    /**
     * Gets the value of the createDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the createDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDate(Date value) {
        this.createDate = value;
    }

    /**
     * Gets the value of the updateBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * Sets the value of the updateBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateBy(String value) {
        this.updateBy = value;
    }

    /**
     * Gets the value of the updateWkstnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateWkstnId() {
        return updateWkstnId;
    }

    /**
     * Sets the value of the updateWkstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateWkstnId(String value) {
        this.updateWkstnId = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDate(Date value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the deleteBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteBy() {
        return deleteBy;
    }

    /**
     * Sets the value of the deleteBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteBy(String value) {
        this.deleteBy = value;
    }

    /**
     * Gets the value of the deleteWkstnId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteWkstnId() {
        return deleteWkstnId;
    }

    /**
     * Sets the value of the deleteWkstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteWkstnId(String value) {
        this.deleteWkstnId = value;
    }

    /**
     * Gets the value of the deleteDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDeleteDate() {
        return deleteDate;
    }

    /**
     * Sets the value of the deleteDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteDate(Date value) {
        this.deleteDate = value;
    }

    /**
     * Gets the value of the deleteFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the value of the deleteFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeleteFlag(String value) {
        this.deleteFlag = value;
    }

}
