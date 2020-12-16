
package com.nec.asia.nic.dx.trans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.nec.asia.nic.dx.common.FaultDetail;


/**
 * <p>Java class for TransactionLogUploadResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionLogUploadResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://common.dx.nic.asia.nec.com/}FaultDetail">
 *       &lt;sequence>
 *         &lt;element name="RejectedTransactionLogs" type="{http://trans.dx.nic.asia.nec.com/}TransactionLog" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionLogUploadResponse", propOrder = {
    "rejectedTransactionLogs"
})
public class TransactionLogUploadResponse
    extends FaultDetail
{

    @XmlElement(name = "RejectedTransactionLogs", required = true)
    protected List<TransactionLog> rejectedTransactionLogs;

    /**
     * Gets the value of the rejectedTransactionLogs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rejectedTransactionLogs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRejectedTransactionLogs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionLog }
     * 
     * 
     */
    public List<TransactionLog> getRejectedTransactionLogs() {
        if (rejectedTransactionLogs == null) {
            rejectedTransactionLogs = new ArrayList<TransactionLog>();
        }
        return this.rejectedTransactionLogs;
    }

}
