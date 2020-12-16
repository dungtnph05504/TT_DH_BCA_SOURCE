
package com.nec.asia.nic.dx.trans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionRetrievalDataType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionRetrievalDataType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="TRANSACTION_INFO"/>
 *     &lt;enumeration value="REGISTRATION_DATA"/>
 *     &lt;enumeration value="TRANSACTION_DOC"/>
 *     &lt;enumeration value="ISSUANCE_DATA"/>
 *     &lt;enumeration value="PAYMENT"/>
 *     &lt;enumeration value="TRANSACTION_LOG"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionRetrievalDataType")
@XmlEnum
public enum TransactionRetrievalDataType {

    ALL,
    TRANSACTION_INFO,
    REGISTRATION_DATA,
    TRANSACTION_DOC,
    ISSUANCE_DATA,
    PAYMENT,
    TRANSACTION_LOG;

    public String value() {
        return name();
    }

    public static TransactionRetrievalDataType fromValue(String v) {
        return valueOf(v);
    }

}
