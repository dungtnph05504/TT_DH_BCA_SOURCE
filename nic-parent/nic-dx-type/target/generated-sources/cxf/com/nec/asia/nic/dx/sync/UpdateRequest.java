
package com.nec.asia.nic.dx.sync;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionStatusInfo" type="{http://sync.dx.nic.asia.nec.com/}TransStatusUpdate" minOccurs="0"/>
 *         &lt;element name="PassportStatusInfo" type="{http://sync.dx.nic.asia.nec.com/}PassportStatusUpdate" minOccurs="0"/>
 *         &lt;element name="PackageInfo" type="{http://sync.dx.nic.asia.nec.com/}PackageInfoStatusUpdate" minOccurs="0"/>
 *         &lt;element name="DispatchInfo" type="{http://sync.dx.nic.asia.nec.com/}DispatchInfoStatusUpdate" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateRequest", propOrder = {
    "transactionStatusInfo",
    "passportStatusInfo",
    "packageInfo",
    "dispatchInfo"
})
public class UpdateRequest {

    @XmlElement(name = "TransactionStatusInfo")
    protected TransStatusUpdate transactionStatusInfo;
    @XmlElement(name = "PassportStatusInfo")
    protected PassportStatusUpdate passportStatusInfo;
    @XmlElement(name = "PackageInfo")
    protected PackageInfoStatusUpdate packageInfo;
    @XmlElement(name = "DispatchInfo")
    protected DispatchInfoStatusUpdate dispatchInfo;

    /**
     * Gets the value of the transactionStatusInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TransStatusUpdate }
     *     
     */
    public TransStatusUpdate getTransactionStatusInfo() {
        return transactionStatusInfo;
    }

    /**
     * Sets the value of the transactionStatusInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransStatusUpdate }
     *     
     */
    public void setTransactionStatusInfo(TransStatusUpdate value) {
        this.transactionStatusInfo = value;
    }

    /**
     * Gets the value of the passportStatusInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PassportStatusUpdate }
     *     
     */
    public PassportStatusUpdate getPassportStatusInfo() {
        return passportStatusInfo;
    }

    /**
     * Sets the value of the passportStatusInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PassportStatusUpdate }
     *     
     */
    public void setPassportStatusInfo(PassportStatusUpdate value) {
        this.passportStatusInfo = value;
    }

    /**
     * Gets the value of the packageInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PackageInfoStatusUpdate }
     *     
     */
    public PackageInfoStatusUpdate getPackageInfo() {
        return packageInfo;
    }

    /**
     * Sets the value of the packageInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageInfoStatusUpdate }
     *     
     */
    public void setPackageInfo(PackageInfoStatusUpdate value) {
        this.packageInfo = value;
    }

    /**
     * Gets the value of the dispatchInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DispatchInfoStatusUpdate }
     *     
     */
    public DispatchInfoStatusUpdate getDispatchInfo() {
        return dispatchInfo;
    }

    /**
     * Sets the value of the dispatchInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispatchInfoStatusUpdate }
     *     
     */
    public void setDispatchInfo(DispatchInfoStatusUpdate value) {
        this.dispatchInfo = value;
    }

}
