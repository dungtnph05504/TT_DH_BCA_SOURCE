
package com.nec.asia.nic.dx.admin;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfigurationType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConfigurationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CODE"/>
 *     &lt;enumeration value="CODE_VALUE"/>
 *     &lt;enumeration value="PARAMETER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConfigurationType")
@XmlEnum
public enum ConfigurationType {

    CODE,
    CODE_VALUE,
    PARAMETER;

    public String value() {
        return name();
    }

    public static ConfigurationType fromValue(String v) {
        return valueOf(v);
    }

}
