
package com.nec.asia.nic.dx.trans;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.common.LogInfo;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for TransactionLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionLog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LogCreateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TransactionStage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TransactionStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WkstnID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OfficerID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SiteCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PassportNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="EndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="LogInfo" type="{http://common.dx.nic.asia.nec.com/}LogInfo" minOccurs="0"/>
 *         &lt;element name="LogData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionLog", propOrder = {
    "transactionID",
    "logCreateTime",
    "transactionStage",
    "transactionStatus",
    "wkstnID",
    "officerID",
    "siteCode",
    "passportNo",
    "startTime",
    "endTime",
    "logInfo",
    "logData"
})
public class TransactionLog {

    @XmlElement(name = "TransactionID", required = true)
    protected String transactionID;
    @XmlElement(name = "LogCreateTime", required = true, type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date logCreateTime;
    @XmlElement(name = "TransactionStage", required = true)
    protected String transactionStage;
    @XmlElement(name = "TransactionStatus", required = true)
    protected String transactionStatus;
    @XmlElement(name = "WkstnID", required = true)
    protected String wkstnID;
    @XmlElement(name = "OfficerID", required = true)
    protected String officerID;
    @XmlElement(name = "SiteCode", required = true)
    protected String siteCode;
    @XmlElement(name = "PassportNo")
    protected String passportNo;
    @XmlElement(name = "StartTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date startTime;
    @XmlElement(name = "EndTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date endTime;
    @XmlElement(name = "LogInfo")
    protected LogInfo logInfo;
    @XmlElement(name = "LogData")
    protected String logData;

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
     * Gets the value of the logCreateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getLogCreateTime() {
        return logCreateTime;
    }

    /**
     * Sets the value of the logCreateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogCreateTime(Date value) {
        this.logCreateTime = value;
    }

    /**
     * Gets the value of the transactionStage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStage() {
        return transactionStage;
    }

    /**
     * Sets the value of the transactionStage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStage(String value) {
        this.transactionStage = value;
    }

    /**
     * Gets the value of the transactionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Sets the value of the transactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatus(String value) {
        this.transactionStatus = value;
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
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartTime(Date value) {
        this.startTime = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndTime(Date value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the logInfo property.
     * 
     * @return
     *     possible object is
     *     {@link LogInfo }
     *     
     */
    public LogInfo getLogInfo() {
        return logInfo;
    }

    /**
     * Sets the value of the logInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link LogInfo }
     *     
     */
    public void setLogInfo(LogInfo value) {
        this.logInfo = value;
    }

    /**
     * Gets the value of the logData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogData() {
        return logData;
    }

    /**
     * Sets the value of the logData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogData(String value) {
        this.logData = value;
    }

}
