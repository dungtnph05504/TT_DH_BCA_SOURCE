
package com.nec.asia.nic.dx.admin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LstFunc" type="{http://admin.dx.nic.asia.nec.com/}Functions" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LstRole" type="{http://admin.dx.nic.asia.nec.com/}Roles" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LstUser" type="{http://admin.dx.nic.asia.nec.com/}Users" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LstWkst" type="{http://admin.dx.nic.asia.nec.com/}Workstations" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserData", propOrder = {
    "lstFunc",
    "lstRole",
    "lstUser",
    "lstWkst"
})
public class UserData {

    @XmlElement(name = "LstFunc", nillable = true)
    protected List<Functions> lstFunc;
    @XmlElement(name = "LstRole", nillable = true)
    protected List<Roles> lstRole;
    @XmlElement(name = "LstUser", nillable = true)
    protected List<Users> lstUser;
    @XmlElement(name = "LstWkst", nillable = true)
    protected List<Workstations> lstWkst;

    /**
     * Gets the value of the lstFunc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstFunc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstFunc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Functions }
     * 
     * 
     */
    public List<Functions> getLstFunc() {
        if (lstFunc == null) {
            lstFunc = new ArrayList<Functions>();
        }
        return this.lstFunc;
    }

    /**
     * Gets the value of the lstRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Roles }
     * 
     * 
     */
    public List<Roles> getLstRole() {
        if (lstRole == null) {
            lstRole = new ArrayList<Roles>();
        }
        return this.lstRole;
    }

    /**
     * Gets the value of the lstUser property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstUser property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Users }
     * 
     * 
     */
    public List<Users> getLstUser() {
        if (lstUser == null) {
            lstUser = new ArrayList<Users>();
        }
        return this.lstUser;
    }

    /**
     * Gets the value of the lstWkst property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lstWkst property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLstWkst().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Workstations }
     * 
     * 
     */
    public List<Workstations> getLstWkst() {
        if (lstWkst == null) {
            lstWkst = new ArrayList<Workstations>();
        }
        return this.lstWkst;
    }

}
