
package com.nec.asia.nic.dx.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nec.asia.nic.dx.common package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _LogInfo_QNAME = new QName("http://common.dx.nic.asia.nec.com/", "LogInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nec.asia.nic.dx.common
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LogInfo }
     * 
     */
    public LogInfo createLogInfo() {
        return new LogInfo();
    }

    /**
     * Create an instance of {@link FaultDetail }
     * 
     */
    public FaultDetail createFaultDetail() {
        return new FaultDetail();
    }

    /**
     * Create an instance of {@link ResponseCode }
     * 
     */
    public ResponseCode createResponseCode() {
        return new ResponseCode();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://common.dx.nic.asia.nec.com/", name = "LogInfo")
    public JAXBElement<LogInfo> createLogInfo(LogInfo value) {
        return new JAXBElement<LogInfo>(_LogInfo_QNAME, LogInfo.class, null, value);
    }

}
