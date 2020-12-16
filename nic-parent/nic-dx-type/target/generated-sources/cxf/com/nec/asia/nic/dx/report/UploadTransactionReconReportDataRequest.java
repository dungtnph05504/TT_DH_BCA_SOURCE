
package com.nec.asia.nic.dx.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.nec.asia.nic.dx.utils.DateTimeAdapter;


/**
 * <p>Java class for uploadTransactionReconReportDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadTransactionReconReportDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionReconReportData" type="{http://asia.nec.com/nic/dx/report/}TransactionReconReportData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SourceSystemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReportGenerationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "uploadTransactionReconReportDataRequest", propOrder = {
    "transactionReconReportData",
    "sourceSystemId",
    "reportGenerationTime"
})
public class UploadTransactionReconReportDataRequest {

    @XmlElement(name = "TransactionReconReportData", nillable = true)
    protected List<TransactionReconReportData> transactionReconReportData;
    @XmlElement(name = "SourceSystemId")
    protected String sourceSystemId;
    @XmlElement(name = "ReportGenerationTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date reportGenerationTime;

    /**
     * Gets the value of the transactionReconReportData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionReconReportData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionReconReportData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionReconReportData }
     * 
     * 
     */
    public List<TransactionReconReportData> getTransactionReconReportData() {
        if (transactionReconReportData == null) {
            transactionReconReportData = new ArrayList<TransactionReconReportData>();
        }
        return this.transactionReconReportData;
    }

    /**
     * Gets the value of the sourceSystemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystemId() {
        return sourceSystemId;
    }

    /**
     * Sets the value of the sourceSystemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystemId(String value) {
        this.sourceSystemId = value;
    }

    /**
     * Gets the value of the reportGenerationTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getReportGenerationTime() {
        return reportGenerationTime;
    }

    /**
     * Sets the value of the reportGenerationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReportGenerationTime(Date value) {
        this.reportGenerationTime = value;
    }

}
