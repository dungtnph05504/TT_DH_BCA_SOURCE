
package com.nec.asia.nic.dx.admin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MappingAuthenData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MappingAuthenData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RolesUser" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RolesWorkstation" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="FunctionsRole" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MappingAuthenData", propOrder = {
    "rolesUser",
    "rolesWorkstation",
    "functionsRole"
})
public class MappingAuthenData {

    @XmlElement(name = "RolesUser", nillable = true)
    protected List<String> rolesUser;
    @XmlElement(name = "RolesWorkstation", nillable = true)
    protected List<String> rolesWorkstation;
    @XmlElement(name = "FunctionsRole", nillable = true)
    protected List<String> functionsRole;

    /**
     * Gets the value of the rolesUser property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rolesUser property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRolesUser().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRolesUser() {
        if (rolesUser == null) {
            rolesUser = new ArrayList<String>();
        }
        return this.rolesUser;
    }

    /**
     * Gets the value of the rolesWorkstation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rolesWorkstation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRolesWorkstation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRolesWorkstation() {
        if (rolesWorkstation == null) {
            rolesWorkstation = new ArrayList<String>();
        }
        return this.rolesWorkstation;
    }

    /**
     * Gets the value of the functionsRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the functionsRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFunctionsRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getFunctionsRole() {
        if (functionsRole == null) {
            functionsRole = new ArrayList<String>();
        }
        return this.functionsRole;
    }

}
