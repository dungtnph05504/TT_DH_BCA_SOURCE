
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
 * <p>Java class for TransactionReconReportData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionReconReportData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RegSiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IssSiteCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ApplicationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CollectionDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReconStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReconRemarks" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CcricRegistered" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CcricUploaded" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CcricCancelled" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="CcricPending" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicReceived" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicRejected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicWip" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicPersoSubmitted" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicPersoRejected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicPersoPersonalized" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicPersoCardDelivered" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicPersoWip" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicCcricCardReceived" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicCcricCardRejected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicCcricCardCollected" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="NicCcricWip" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
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
@XmlType(name = "TransactionReconReportData", propOrder = {
    "regSiteCode",
    "issSiteCode",
    "applicationDate",
    "collectionDate",
    "reconStatus",
    "reconRemarks",
    "ccricRegistered",
    "ccricUploaded",
    "ccricCancelled",
    "ccricPending",
    "nicReceived",
    "nicRejected",
    "nicWip",
    "nicPersoSubmitted",
    "nicPersoRejected",
    "nicPersoPersonalized",
    "nicPersoCardDelivered",
    "nicPersoWip",
    "nicCcricCardReceived",
    "nicCcricCardRejected",
    "nicCcricCardCollected",
    "nicCcricWip",
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
    "createBy",
    "createDate",
    "updateBy",
    "updateDate"
})
public class TransactionReconReportData {

    @XmlElement(name = "RegSiteCode")
    protected String regSiteCode;
    @XmlElement(name = "IssSiteCode")
    protected String issSiteCode;
    @XmlElement(name = "ApplicationDate")
    protected String applicationDate;
    @XmlElement(name = "CollectionDate")
    protected String collectionDate;
    @XmlElement(name = "ReconStatus")
    protected String reconStatus;
    @XmlElement(name = "ReconRemarks")
    protected String reconRemarks;
    @XmlElement(name = "CcricRegistered")
    protected Long ccricRegistered;
    @XmlElement(name = "CcricUploaded")
    protected Long ccricUploaded;
    @XmlElement(name = "CcricCancelled")
    protected Long ccricCancelled;
    @XmlElement(name = "CcricPending")
    protected Long ccricPending;
    @XmlElement(name = "NicReceived")
    protected Long nicReceived;
    @XmlElement(name = "NicRejected")
    protected Long nicRejected;
    @XmlElement(name = "NicWip")
    protected Long nicWip;
    @XmlElement(name = "NicPersoSubmitted")
    protected Long nicPersoSubmitted;
    @XmlElement(name = "NicPersoRejected")
    protected Long nicPersoRejected;
    @XmlElement(name = "NicPersoPersonalized")
    protected Long nicPersoPersonalized;
    @XmlElement(name = "NicPersoCardDelivered")
    protected Long nicPersoCardDelivered;
    @XmlElement(name = "NicPersoWip")
    protected Long nicPersoWip;
    @XmlElement(name = "NicCcricCardReceived")
    protected Long nicCcricCardReceived;
    @XmlElement(name = "NicCcricCardRejected")
    protected Long nicCcricCardRejected;
    @XmlElement(name = "NicCcricCardCollected")
    protected Long nicCcricCardCollected;
    @XmlElement(name = "NicCcricWip")
    protected Long nicCcricWip;
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
     * Gets the value of the regSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegSiteCode() {
        return regSiteCode;
    }

    /**
     * Sets the value of the regSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegSiteCode(String value) {
        this.regSiteCode = value;
    }

    /**
     * Gets the value of the issSiteCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssSiteCode() {
        return issSiteCode;
    }

    /**
     * Sets the value of the issSiteCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssSiteCode(String value) {
        this.issSiteCode = value;
    }

    /**
     * Gets the value of the applicationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the value of the applicationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationDate(String value) {
        this.applicationDate = value;
    }

    /**
     * Gets the value of the collectionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollectionDate() {
        return collectionDate;
    }

    /**
     * Sets the value of the collectionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollectionDate(String value) {
        this.collectionDate = value;
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
     * Gets the value of the ccricRegistered property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCcricRegistered() {
        return ccricRegistered;
    }

    /**
     * Sets the value of the ccricRegistered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCcricRegistered(Long value) {
        this.ccricRegistered = value;
    }

    /**
     * Gets the value of the ccricUploaded property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCcricUploaded() {
        return ccricUploaded;
    }

    /**
     * Sets the value of the ccricUploaded property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCcricUploaded(Long value) {
        this.ccricUploaded = value;
    }

    /**
     * Gets the value of the ccricCancelled property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCcricCancelled() {
        return ccricCancelled;
    }

    /**
     * Sets the value of the ccricCancelled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCcricCancelled(Long value) {
        this.ccricCancelled = value;
    }

    /**
     * Gets the value of the ccricPending property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCcricPending() {
        return ccricPending;
    }

    /**
     * Sets the value of the ccricPending property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCcricPending(Long value) {
        this.ccricPending = value;
    }

    /**
     * Gets the value of the nicReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicReceived() {
        return nicReceived;
    }

    /**
     * Sets the value of the nicReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicReceived(Long value) {
        this.nicReceived = value;
    }

    /**
     * Gets the value of the nicRejected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicRejected() {
        return nicRejected;
    }

    /**
     * Sets the value of the nicRejected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicRejected(Long value) {
        this.nicRejected = value;
    }

    /**
     * Gets the value of the nicWip property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicWip() {
        return nicWip;
    }

    /**
     * Sets the value of the nicWip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicWip(Long value) {
        this.nicWip = value;
    }

    /**
     * Gets the value of the nicPersoSubmitted property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicPersoSubmitted() {
        return nicPersoSubmitted;
    }

    /**
     * Sets the value of the nicPersoSubmitted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicPersoSubmitted(Long value) {
        this.nicPersoSubmitted = value;
    }

    /**
     * Gets the value of the nicPersoRejected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicPersoRejected() {
        return nicPersoRejected;
    }

    /**
     * Sets the value of the nicPersoRejected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicPersoRejected(Long value) {
        this.nicPersoRejected = value;
    }

    /**
     * Gets the value of the nicPersoPersonalized property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicPersoPersonalized() {
        return nicPersoPersonalized;
    }

    /**
     * Sets the value of the nicPersoPersonalized property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicPersoPersonalized(Long value) {
        this.nicPersoPersonalized = value;
    }

    /**
     * Gets the value of the nicPersoCardDelivered property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicPersoCardDelivered() {
        return nicPersoCardDelivered;
    }

    /**
     * Sets the value of the nicPersoCardDelivered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicPersoCardDelivered(Long value) {
        this.nicPersoCardDelivered = value;
    }

    /**
     * Gets the value of the nicPersoWip property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicPersoWip() {
        return nicPersoWip;
    }

    /**
     * Sets the value of the nicPersoWip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicPersoWip(Long value) {
        this.nicPersoWip = value;
    }

    /**
     * Gets the value of the nicCcricCardReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicCcricCardReceived() {
        return nicCcricCardReceived;
    }

    /**
     * Sets the value of the nicCcricCardReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicCcricCardReceived(Long value) {
        this.nicCcricCardReceived = value;
    }

    /**
     * Gets the value of the nicCcricCardRejected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicCcricCardRejected() {
        return nicCcricCardRejected;
    }

    /**
     * Sets the value of the nicCcricCardRejected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicCcricCardRejected(Long value) {
        this.nicCcricCardRejected = value;
    }

    /**
     * Gets the value of the nicCcricCardCollected property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicCcricCardCollected() {
        return nicCcricCardCollected;
    }

    /**
     * Sets the value of the nicCcricCardCollected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicCcricCardCollected(Long value) {
        this.nicCcricCardCollected = value;
    }

    /**
     * Gets the value of the nicCcricWip property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNicCcricWip() {
        return nicCcricWip;
    }

    /**
     * Sets the value of the nicCcricWip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNicCcricWip(Long value) {
        this.nicCcricWip = value;
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
