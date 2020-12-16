
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BufEppDataPerson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BufEppDataPerson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BufEppPerson" type="{http://trans.dx.nic.asia.nec.com/}BufEppPerson" maxOccurs="unbounded"/>
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
@XmlType(name = "BufEppDataPerson", propOrder = {
    "bufEppPerson",
    "resultCheck"
})
public class BufEppDataPerson {

    @XmlElement(name = "BufEppPerson", required = true, nillable = true)
    protected List<BufEppPerson> bufEppPerson;
    protected String resultCheck;

    /**
     * Gets the value of the bufEppPerson property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bufEppPerson property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBufEppPerson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BufEppPerson }
     * 
     * 
     */
    public List<BufEppPerson> getBufEppPerson() {
        if (bufEppPerson == null) {
            bufEppPerson = new ArrayList<BufEppPerson>();
        }
        return this.bufEppPerson;
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
