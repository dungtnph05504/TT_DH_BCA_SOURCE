
package com.nec.asia.nic.dx.sync;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.nec.asia.nic.dx.sync package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.nec.asia.nic.dx.sync
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DispatchInfo }
     * 
     */
    public DispatchInfo createDispatchInfo() {
        return new DispatchInfo();
    }

    /**
     * Create an instance of {@link UpdateRequest }
     * 
     */
    public UpdateRequest createUpdateRequest() {
        return new UpdateRequest();
    }

    /**
     * Create an instance of {@link DispatchInfoStatusUpdate }
     * 
     */
    public DispatchInfoStatusUpdate createDispatchInfoStatusUpdate() {
        return new DispatchInfoStatusUpdate();
    }

    /**
     * Create an instance of {@link TransStatusUpdate }
     * 
     */
    public TransStatusUpdate createTransStatusUpdate() {
        return new TransStatusUpdate();
    }

    /**
     * Create an instance of {@link PassportInfo }
     * 
     */
    public PassportInfo createPassportInfo() {
        return new PassportInfo();
    }

    /**
     * Create an instance of {@link PassportStatusUpdate }
     * 
     */
    public PassportStatusUpdate createPassportStatusUpdate() {
        return new PassportStatusUpdate();
    }

    /**
     * Create an instance of {@link PackageInfo }
     * 
     */
    public PackageInfo createPackageInfo() {
        return new PackageInfo();
    }

    /**
     * Create an instance of {@link PackageInfoStatusUpdate }
     * 
     */
    public PackageInfoStatusUpdate createPackageInfoStatusUpdate() {
        return new PackageInfoStatusUpdate();
    }

}
