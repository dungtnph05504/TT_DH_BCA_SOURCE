
package com.nec.asia.nic.dx.report;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for CardReconReportData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardReconReportData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReportCreateDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReconStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReconRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PersoReceived" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PersoRejected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PersoCardPacked" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PersoCardDelivered" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PersoWip" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciPendingStockedIn" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciStockedIn" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciRejected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciCollected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciCollectionFailed" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciPendingCollection" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CciCardReturned" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="InvCardReturned" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardReconReportData", propOrder = {
    "siteCode",
    "reportCreateDate",
    "reconStatus",
    "reconRemarks",
    "persoReceived",
    "persoRejected",
    "persoCardPacked",
    "persoCardDelivered",
    "persoWip",
    "cciPendingStockedIn",
    "cciStockedIn",
    "cciRejected",
    "cciCollected",
    "cciCollectionFailed",
    "cciPendingCollection",
    "cciCardReturned",
    "invCardReturned",
    "createBy",
    "createDate",
    "updateBy",
    "updateDate"
})
public class CardReconReportData {

    @XmlElement(name = "SiteCode")
    protected String siteCode;
    @XmlElement(name = "ReportCreateDate")
    protected String reportCreateDate;
    @XmlElement(name = "ReconStatus")
    protected String reconStatus;
    @XmlElement(name = "ReconRemarks")
    protected String reconRemarks;
    @XmlElement(name = "PersoReceived")
    protected Long persoReceived;
    @XmlElement(name = "PersoRejected")
    protected Long persoRejected;
    @XmlElement(name = "PersoCardPacked")
    protected Long persoCardPacked;
    @XmlElement(name = "PersoCardDelivered")
    protected Long persoCardDelivered;
    @XmlElement(name = "PersoWip")
    protected Long persoWip;
    @XmlElement(name = "CciPendingStockedIn")
    protected Long cciPendingStockedIn;
    @XmlElement(name = "CciStockedIn")
    protected Long cciStockedIn;
    @XmlElement(name = "CciRejected")
    protected Long cciRejected;
    @XmlElement(name = "CciCollected")
    protected Long cciCollected;
    @XmlElement(name = "CciCollectionFailed")
    protected Long cciCollectionFailed;
    @XmlElement(name = "CciPendingCollection")
    protected Long cciPendingCollection;
    @XmlElement(name = "CciCardReturned")
    protected Long cciCardReturned;
    @XmlElement(name = "InvCardReturned")
    protected Long invCardReturned;
    @XmlElement(name = "CreateBy")
    protected String createBy;
    @XmlElement(name = "CreateDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date createDate;
    @XmlElement(name = "UpdateBy")
    protected String updateBy;
    @XmlElement(name = "UpdateDate", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date updateDate;

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
     * Gets the value of the reportCreateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReportCreateDate() {
        return reportCreateDate;
    }

    /**
     * Sets the value of the reportCreateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportCreateDate(String value) {
        this.reportCreateDate = value;
    }

    /**
     * Gets the value of the reconStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReconStatus() {
        return reconStatus;
    }

    /**
     * Sets the value of the reconStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReconStatus(String value) {
        this.reconStatus = value;
    }

    /**
     * Gets the value of the reconRemarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReconRemarks() {
        return reconRemarks;
    }

    /**
     * Sets the value of the reconRemarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReconRemarks(String value) {
        this.reconRemarks = value;
    }

    /**
     * Gets the value of the persoReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPersoReceived() {
        return persoReceived;
    }

    /**
     * Sets the value of the persoReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPersoReceived(Long value) {
        this.persoReceived = value;
    }

    /**
     * Gets the value of the persoRejected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPersoRejected() {
        return persoRejected;
    }

    /**
     * Sets the value of the persoRejected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPersoRejected(Long value) {
        this.persoRejected = value;
    }

    /**
     * Gets the value of the persoCardPacked property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPersoCardPacked() {
        return persoCardPacked;
    }

    /**
     * Sets the value of the persoCardPacked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPersoCardPacked(Long value) {
        this.persoCardPacked = value;
    }

    /**
     * Gets the value of the persoCardDelivered property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPersoCardDelivered() {
        return persoCardDelivered;
    }

    /**
     * Sets the value of the persoCardDelivered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPersoCardDelivered(Long value) {
        this.persoCardDelivered = value;
    }

    /**
     * Gets the value of the persoWip property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getPersoWip() {
        return persoWip;
    }

    /**
     * Sets the value of the persoWip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setPersoWip(Long value) {
        this.persoWip = value;
    }

    /**
     * Gets the value of the cciPendingStockedIn property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciPendingStockedIn() {
        return cciPendingStockedIn;
    }

    /**
     * Sets the value of the cciPendingStockedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciPendingStockedIn(Long value) {
        this.cciPendingStockedIn = value;
    }

    /**
     * Gets the value of the cciStockedIn property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciStockedIn() {
        return cciStockedIn;
    }

    /**
     * Sets the value of the cciStockedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciStockedIn(Long value) {
        this.cciStockedIn = value;
    }

    /**
     * Gets the value of the cciRejected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciRejected() {
        return cciRejected;
    }

    /**
     * Sets the value of the cciRejected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciRejected(Long value) {
        this.cciRejected = value;
    }

    /**
     * Gets the value of the cciCollected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciCollected() {
        return cciCollected;
    }

    /**
     * Sets the value of the cciCollected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciCollected(Long value) {
        this.cciCollected = value;
    }

    /**
     * Gets the value of the cciCollectionFailed property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciCollectionFailed() {
        return cciCollectionFailed;
    }

    /**
     * Sets the value of the cciCollectionFailed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciCollectionFailed(Long value) {
        this.cciCollectionFailed = value;
    }

    /**
     * Gets the value of the cciPendingCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciPendingCollection() {
        return cciPendingCollection;
    }

    /**
     * Sets the value of the cciPendingCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciPendingCollection(Long value) {
        this.cciPendingCollection = value;
    }

    /**
     * Gets the value of the cciCardReturned property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCciCardReturned() {
        return cciCardReturned;
    }

    /**
     * Sets the value of the cciCardReturned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCciCardReturned(Long value) {
        this.cciCardReturned = value;
    }

    /**
     * Gets the value of the invCardReturned property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getInvCardReturned() {
        return invCardReturned;
    }

    /**
     * Sets the value of the invCardReturned property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setInvCardReturned(Long value) {
        this.invCardReturned = value;
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

}
