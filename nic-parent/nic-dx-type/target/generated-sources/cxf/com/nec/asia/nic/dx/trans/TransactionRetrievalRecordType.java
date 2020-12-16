
package com.nec.asia.nic.dx.trans;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionRetrievalRecordType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TransactionRetrievalRecordType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="LATEST"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TransactionRetrievalRecordType")
@XmlEnum
public enum TransactionRetrievalRecordType {

    ALL,
    LATEST;

    public String value() {
        return name();
    }

    public static TransactionRetrievalRecordType fromValue(String v) {
        return valueOf(v);
    }

}
