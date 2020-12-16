
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
 * <p>Java class for uploadCardReconReportDataRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="uploadCardReconReportDataRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CardReconReportData" type="{http://asia.nec.com/nic/dx/report/}CardReconReportData" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "uploadCardReconReportDataRequest", propOrder = {
    "cardReconReportData",
    "sourceSystemId",
    "reportGenerationTime"
})
public class UploadCardReconReportDataRequest {

    @XmlElement(name = "CardReconReportData", nillable = true)
    protected List<CardReconReportData> cardReconReportData;
    @XmlElement(name = "SourceSystemId")
    protected String sourceSystemId;
    @XmlElement(name = "ReportGenerationTime", type = String.class)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Date reportGenerationTime;

    /**
     * Gets the value of the cardReconReportData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cardReconReportData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCardReconReportData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CardReconReportData }
     * 
     * 
     */
    public List<CardReconReportData> getCardReconReportData() {
        if (cardReconReportData == null) {
            cardReconReportData = new ArrayList<CardReconReportData>();
        }
        return this.cardReconReportData;
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
