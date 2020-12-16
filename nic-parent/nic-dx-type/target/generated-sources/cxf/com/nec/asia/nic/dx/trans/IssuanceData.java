
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for IssuanceData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IssuanceData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ChipSerialNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PrintingSite" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateOfIssue" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DateOfExpiry" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StatusUpdateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PackageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PackageDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DispatchID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DispatchDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="IssuedFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActiveFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReceiveBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReceiveDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="IssueDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="IssueBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RejectBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RejectDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CancelBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CancelDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="UpdateWkstnID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NameApprover" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DateApprove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoteApprove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SyncStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastSyncDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PaymentDetail" type="{http://trans.dx.nic.asia.nec.com/}PaymentDetail" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssuanceData", propOrder = {
    "transactionID",
    "passportNo",
    "chipSerialNo",
    "printingSite",
    "dateOfIssue",
    "dateOfExpiry",
    "status",
    "statusUpdateTime",
    "packageID",
    "packageDateTime",
    "dispatchID",
    "dispatchDateTime",
    "issuedFlag",
    "activeFlag",
    "receiveBy",
    "receiveDateTime",
    "issueDateTime",
    "issueBy",
    "rejectBy",
    "rejectDateTime",
    "cancelBy",
    "cancelDateTime",
    "createBy",
    "createDateTime",
    "createWkstnID",
    "updateBy",
    "updateDateTime",
    "updateWkstnID",
    "nameApprover",
    "dateApprove",
    "noteApprove",
    "syncStatus",
    "lastSyncDateTime",
    "paymentDetail"
})
public class IssuanceData {

    @XmlElement(name = "TransactionID", required = true)
    protected String transactionID;
    @XmlElement(name = "PassportNo")
    protected String passportNo;
    @XmlElement(name = "ChipSerialNo", required = true)
    protected String chipSerialNo;
    @XmlElement(name = "PrintingSite", required = true)
    protected String printingSite;
    @XmlElement(name = "DateOfIssue", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dateOfIssue;
    @XmlElement(name = "DateOfExpiry", type = String.class)
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Date dateOfExpiry;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "StatusUpdateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date statusUpdateTime;
    @XmlElement(name = "PackageID")
    protected String packageID;
    @XmlElement(name = "PackageDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date packageDateTime;
    @XmlElement(name = "DispatchID")
    protected String dispatchID;
    @XmlElement(name = "DispatchDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date dispatchDateTime;
    @XmlElement(name = "IssuedFlag")
    protected String issuedFlag;
    @XmlElement(name = "ActiveFlag")
    protected String activeFlag;
    @XmlElement(name = "ReceiveBy")
    protected String receiveBy;
    @XmlElement(name = "ReceiveDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date receiveDateTime;
    @XmlElement(name = "IssueDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date issueDateTime;
    @XmlElement(name = "IssueBy")
    protected String issueBy;
    @XmlElement(name = "RejectBy")
    protected String rejectBy;
    @XmlElement(name = "RejectDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date rejectDateTime;
    @XmlElement(name = "CancelBy")
    protected String cancelBy;
    @XmlElement(name = "CancelDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date cancelDateTime;
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
    @XmlElement(name = "NameApprover")
    protected String nameApprover;
    @XmlElement(name = "DateApprove")
    protected String dateApprove;
    @XmlElement(name = "NoteApprove")
    protected String noteApprove;
    @XmlElement(name = "SyncStatus")
    protected String syncStatus;
    @XmlElement(name = "LastSyncDateTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date lastSyncDateTime;
    @XmlElement(name = "PaymentDetail", required = true, nillable = true)
    protected List<PaymentDetail> paymentDetail;

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
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
     * Gets the value of the chipSerialNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChipSerialNo() {
        return chipSerialNo;
    }

    /**
     * Sets the value of the chipSerialNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChipSerialNo(String value) {
        this.chipSerialNo = value;
    }

    /**
     * Gets the value of the printingSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintingSite() {
        return printingSite;
    }

    /**
     * Sets the value of the printingSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintingSite(String value) {
        this.printingSite = value;
    }

    /**
     * Gets the value of the dateOfIssue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    /**
     * Sets the value of the dateOfIssue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfIssue(Date value) {
        this.dateOfIssue = value;
    }

    /**
     * Gets the value of the dateOfExpiry property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDateOfExpiry() {
        return dateOfExpiry;
    }

    /**
     * Sets the value of the dateOfExpiry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfExpiry(Date value) {
        this.dateOfExpiry = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the statusUpdateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    /**
     * Sets the value of the statusUpdateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusUpdateTime(Date value) {
        this.statusUpdateTime = value;
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
     * Gets the value of the packageDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getPackageDateTime() {
        return packageDateTime;
    }

    /**
     * Sets the value of the packageDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackageDateTime(Date value) {
        this.packageDateTime = value;
    }

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
     * Gets the value of the issuedFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuedFlag() {
        return issuedFlag;
    }

    /**
     * Sets the value of the issuedFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuedFlag(String value) {
        this.issuedFlag = value;
    }

    /**
     * Gets the value of the activeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveFlag() {
        return activeFlag;
    }

    /**
     * Sets the value of the activeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveFlag(String value) {
        this.activeFlag = value;
    }

    /**
     * Gets the value of the receiveBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiveBy() {
        return receiveBy;
    }

    /**
     * Sets the value of the receiveBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveBy(String value) {
        this.receiveBy = value;
    }

    /**
     * Gets the value of the receiveDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getReceiveDateTime() {
        return receiveDateTime;
    }

    /**
     * Sets the value of the receiveDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiveDateTime(Date value) {
        this.receiveDateTime = value;
    }

    /**
     * Gets the value of the issueDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getIssueDateTime() {
        return issueDateTime;
    }

    /**
     * Sets the value of the issueDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueDateTime(Date value) {
        this.issueDateTime = value;
    }

    /**
     * Gets the value of the issueBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssueBy() {
        return issueBy;
    }

    /**
     * Sets the value of the issueBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssueBy(String value) {
        this.issueBy = value;
    }

    /**
     * Gets the value of the rejectBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRejectBy() {
        return rejectBy;
    }

    /**
     * Sets the value of the rejectBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRejectBy(String value) {
        this.rejectBy = value;
    }

    /**
     * Gets the value of the rejectDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getRejectDateTime() {
        return rejectDateTime;
    }

    /**
     * Sets the value of the rejectDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRejectDateTime(Date value) {
        this.rejectDateTime = value;
    }

    /**
     * Gets the value of the cancelBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCancelBy() {
        return cancelBy;
    }

    /**
     * Sets the value of the cancelBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelBy(String value) {
        this.cancelBy = value;
    }

    /**
     * Gets the value of the cancelDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getCancelDateTime() {
        return cancelDateTime;
    }

    /**
     * Sets the value of the cancelDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCancelDateTime(Date value) {
        this.cancelDateTime = value;
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
     * Gets the value of the nameApprover property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameApprover() {
        return nameApprover;
    }

    /**
     * Sets the value of the nameApprover property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameApprover(String value) {
        this.nameApprover = value;
    }

    /**
     * Gets the value of the dateApprove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateApprove() {
        return dateApprove;
    }

    /**
     * Sets the value of the dateApprove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateApprove(String value) {
        this.dateApprove = value;
    }

    /**
     * Gets the value of the noteApprove property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteApprove() {
        return noteApprove;
    }

    /**
     * Sets the value of the noteApprove property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteApprove(String value) {
        this.noteApprove = value;
    }

    /**
     * Gets the value of the syncStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSyncStatus() {
        return syncStatus;
    }

    /**
     * Sets the value of the syncStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSyncStatus(String value) {
        this.syncStatus = value;
    }

    /**
     * Gets the value of the lastSyncDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getLastSyncDateTime() {
        return lastSyncDateTime;
    }

    /**
     * Sets the value of the lastSyncDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastSyncDateTime(Date value) {
        this.lastSyncDateTime = value;
    }

    /**
     * Gets the value of the paymentDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentDetail }
     * 
     * 
     */
    public List<PaymentDetail> getPaymentDetail() {
        if (paymentDetail == null) {
            paymentDetail = new ArrayList<PaymentDetail>();
        }
        return this.paymentDetail;
    }

}
