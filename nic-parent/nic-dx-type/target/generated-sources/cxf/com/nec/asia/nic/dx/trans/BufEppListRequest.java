
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BufEppListRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BufEppListRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BufEppListResponse" type="{http://trans.dx.nic.asia.nec.com/}BufEppListResponse" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="resultCheck" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BufEppListRequest", propOrder = {
    "bufEppListResponse",
    "resultCheck"
})
public class BufEppListRequest {

    @XmlElement(name = "BufEppListResponse", nillable = true)
    protected List<BufEppListResponse> bufEppListResponse;
    protected String resultCheck;

    /**
     * Gets the value of the bufEppListResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bufEppListResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBufEppListResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BufEppListResponse }
     * 
     * 
     */
    public List<BufEppListResponse> getBufEppListResponse() {
        if (bufEppListResponse == null) {
            bufEppListResponse = new ArrayList<BufEppListResponse>();
        }
        return this.bufEppListResponse;
    }

    /**
     * Gets the value of the resultCheck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCheck() {
        return resultCheck;
    }

    /**
     * Sets the value of the resultCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCheck(String value) {
        this.resultCheck = value;
    }

}
