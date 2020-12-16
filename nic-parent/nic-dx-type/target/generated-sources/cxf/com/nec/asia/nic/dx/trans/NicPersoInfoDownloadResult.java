
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NicPersoInfoDownloadResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NicPersoInfoDownloadResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IssuanceDatas" type="{http://trans.dx.nic.asia.nec.com/}IssuanceData" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RejectionDatas" type="{http://trans.dx.nic.asia.nec.com/}RejectionData" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NicPersoInfoDownloadResult", propOrder = {
    "issuanceDatas",
    "rejectionDatas"
})
public class NicPersoInfoDownloadResult {

    @XmlElement(name = "IssuanceDatas")
    protected List<IssuanceData> issuanceDatas;
    @XmlElement(name = "RejectionDatas")
    protected List<RejectionData> rejectionDatas;

    /**
     * Gets the value of the issuanceDatas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the issuanceDatas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIssuanceDatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IssuanceData }
     * 
     * 
     */
    public List<IssuanceData> getIssuanceDatas() {
        if (issuanceDatas == null) {
            issuanceDatas = new ArrayList<IssuanceData>();
        }
        return this.issuanceDatas;
    }

    /**
     * Gets the value of the rejectionDatas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rejectionDatas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRejectionDatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RejectionData }
     * 
     * 
     */
    public List<RejectionData> getRejectionDatas() {
        if (rejectionDatas == null) {
            rejectionDatas = new ArrayList<RejectionData>();
        }
        return this.rejectionDatas;
    }

}
