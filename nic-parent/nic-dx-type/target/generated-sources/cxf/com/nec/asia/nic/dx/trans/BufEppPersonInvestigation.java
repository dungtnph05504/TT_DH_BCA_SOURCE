
package com.nec.asia.nic.dx.trans;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for BufEppPersonInvestigation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BufEppPersonInvestigation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransacionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataImmihistory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataHistoryPassport" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataFamily" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SfDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SmDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SsDob" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BufEppPersonInvestigation", propOrder = {
    "transacionId",
    "createBy",
    "createDateTime",
    "createWkstnID",
    "updateBy",
    "updateDateTime",
    "updateWkstnID",
    "dataImmihistory",
    "dataHistoryPassport",
    "dataInfo",
    "dataFamily",
    "sfDob",
    "smDob",
    "ssDob"
})
public class BufEppPersonInvestigation {

    @XmlElement(name = "TransacionId")
    protected String transacionId;
    @XmlElement(name = "CreateBy")
    protected String createBy;
    @XmlElement(name = "CreateDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date createDateTime;
    @XmlElement(name = "CreateWkstnID")
    protected String createWkstnID;
    @XmlElement(name = "UpdateBy")
    protected String updateBy;
    @XmlElement(name = "UpdateDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date updateDateTime;
    @XmlElement(name = "UpdateWkstnID")
    protected String updateWkstnID;
    @XmlElement(name = "DataImmihistory")
    protected String dataImmihistory;
    @XmlElement(name = "DataHistoryPassport")
    protected String dataHistoryPassport;
    @XmlElement(name = "DataInfo")
    protected String dataInfo;
    @XmlElement(name = "DataFamily")
    protected String dataFamily;
    @XmlElement(name = "SfDob")
    protected String sfDob;
    @XmlElement(name = "SmDob")
    protected String smDob;
    @XmlElement(name = "SsDob")
    protected String ssDob;

    /**
     * Gets the value of the transacionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransacionId() {
        return transacionId;
    }

    /**
     * Sets the value of the transacionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransacionId(String value) {
        this.transacionId = value;
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
     * Gets the value of the createDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Sets the value of the createDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateDateTime(Date value) {
        this.createDateTime = value;
    }

    /**
     * Gets the value of the createWkstnID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateWkstnID() {
        return createWkstnID;
    }

    /**
     * Sets the value of the createWkstnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateWkstnID(String value) {
        this.createWkstnID = value;
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
     * Gets the value of the updateDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    /**
     * Sets the value of the updateDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDateTime(Date value) {
        this.updateDateTime = value;
    }

    /**
     * Gets the value of the updateWkstnID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateWkstnID() {
        return updateWkstnID;
    }

    /**
     * Sets the value of the updateWkstnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateWkstnID(String value) {
        this.updateWkstnID = value;
    }

    /**
     * Gets the value of the dataImmihistory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataImmihistory() {
        return dataImmihistory;
    }

    /**
     * Sets the value of the dataImmihistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataImmihistory(String value) {
        this.dataImmihistory = value;
    }

    /**
     * Gets the value of the dataHistoryPassport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataHistoryPassport() {
        return dataHistoryPassport;
    }

    /**
     * Sets the value of the dataHistoryPassport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataHistoryPassport(String value) {
        this.dataHistoryPassport = value;
    }

    /**
     * Gets the value of the dataInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInfo() {
        return dataInfo;
    }

    /**
     * Sets the value of the dataInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInfo(String value) {
        this.dataInfo = value;
    }

    /**
     * Gets the value of the dataFamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFamily() {
        return dataFamily;
    }

    /**
     * Sets the value of the dataFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFamily(String value) {
        this.dataFamily = value;
    }

    /**
     * Gets the value of the sfDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSfDob() {
        return sfDob;
    }

    /**
     * Sets the value of the sfDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSfDob(String value) {
        this.sfDob = value;
    }

    /**
     * Gets the value of the smDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSmDob() {
        return smDob;
    }

    /**
     * Sets the value of the smDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSmDob(String value) {
        this.smDob = value;
    }

    /**
     * Gets the value of the ssDob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsDob() {
        return ssDob;
    }

    /**
     * Sets the value of the ssDob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsDob(String value) {
        this.ssDob = value;
    }

}
