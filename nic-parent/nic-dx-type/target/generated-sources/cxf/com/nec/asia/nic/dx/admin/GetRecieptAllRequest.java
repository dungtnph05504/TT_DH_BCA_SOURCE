
package com.nec.asia.nic.dx.admin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetRecieptAllRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetRecieptAllRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EppDetailReciepts" type="{http://admin.dx.nic.asia.nec.com/}EppDetailReciept" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="DetailRecieptFees" type="{http://admin.dx.nic.asia.nec.com/}DetailRecieptFee" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EppRecieptManagers" type="{http://admin.dx.nic.asia.nec.com/}EppRecieptManager" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EppRecieptPayments" type="{http://admin.dx.nic.asia.nec.com/}EppRecieptPayment" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetRecieptAllRequest", propOrder = {
    "eppDetailReciepts",
    "detailRecieptFees",
    "eppRecieptManagers",
    "eppRecieptPayments"
})
public class GetRecieptAllRequest {

    @XmlElement(name = "EppDetailReciepts")
    protected List<EppDetailReciept> eppDetailReciepts;
    @XmlElement(name = "DetailRecieptFees")
    protected List<DetailRecieptFee> detailRecieptFees;
    @XmlElement(name = "EppRecieptManagers")
    protected List<EppRecieptManager> eppRecieptManagers;
    @XmlElement(name = "EppRecieptPayments")
    protected List<EppRecieptPayment> eppRecieptPayments;

    /**
     * Gets the value of the eppDetailReciepts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eppDetailReciepts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEppDetailReciepts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EppDetailReciept }
     * 
     * 
     */
    public List<EppDetailReciept> getEppDetailReciepts() {
        if (eppDetailReciepts == null) {
            eppDetailReciepts = new ArrayList<EppDetailReciept>();
        }
        return this.eppDetailReciepts;
    }

    /**
     * Gets the value of the detailRecieptFees property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the detailRecieptFees property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDetailRecieptFees().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DetailRecieptFee }
     * 
     * 
     */
    public List<DetailRecieptFee> getDetailRecieptFees() {
        if (detailRecieptFees == null) {
            detailRecieptFees = new ArrayList<DetailRecieptFee>();
        }
        return this.detailRecieptFees;
    }

    /**
     * Gets the value of the eppRecieptManagers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eppRecieptManagers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEppRecieptManagers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EppRecieptManager }
     * 
     * 
     */
    public List<EppRecieptManager> getEppRecieptManagers() {
        if (eppRecieptManagers == null) {
            eppRecieptManagers = new ArrayList<EppRecieptManager>();
        }
        return this.eppRecieptManagers;
    }

    /**
     * Gets the value of the eppRecieptPayments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eppRecieptPayments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEppRecieptPayments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EppRecieptPayment }
     * 
     * 
     */
    public List<EppRecieptPayment> getEppRecieptPayments() {
        if (eppRecieptPayments == null) {
            eppRecieptPayments = new ArrayList<EppRecieptPayment>();
        }
        return this.eppRecieptPayments;
    }

}
