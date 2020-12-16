
package com.nec.asia.nic.dx.admin;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetProofDocumentMatrixResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetProofDocumentMatrixResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProofDocuments" type="{http://admin.dx.nic.asia.nec.com/}ProofDocument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetProofDocumentMatrixResponse", propOrder = {
    "proofDocuments"
})
public class GetProofDocumentMatrixResponse {

    @XmlElement(name = "ProofDocuments")
    protected List<ProofDocument> proofDocuments;

    /**
     * Gets the value of the proofDocuments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the proofDocuments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProofDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProofDocument }
     * 
     * 
     */
    public List<ProofDocument> getProofDocuments() {
        if (proofDocuments == null) {
            proofDocuments = new ArrayList<ProofDocument>();
        }
        return this.proofDocuments;
    }

}
