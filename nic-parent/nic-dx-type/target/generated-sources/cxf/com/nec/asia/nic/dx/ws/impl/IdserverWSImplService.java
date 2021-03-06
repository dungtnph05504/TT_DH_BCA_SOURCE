package com.nec.asia.nic.dx.ws.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import com.nec.asia.nic.dx.ws.IdserverWS;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.2
 * 2020-10-22T10:20:53.912+07:00
 * Generated source version: 2.7.2
 * 
 */
@WebServiceClient(name = "idserverWSImplService", 
                  wsdlLocation = "file:/F:/work/NabSolution/TT_DH_BCA_SOURCE/nic-parent/nic-dx-type/wsdl/idserver.wsdl",
                  targetNamespace = "http://impl.ws.dx.nic.asia.nec.com/") 
public class IdserverWSImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.ws.dx.nic.asia.nec.com/", "idserverWSImplService");
    public final static QName IdserverWSImplPort = new QName("http://impl.ws.dx.nic.asia.nec.com/", "idserverWSImplPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/F:/work/NabSolution/TT_DH_BCA_SOURCE/nic-parent/nic-dx-type/wsdl/idserver.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(IdserverWSImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/F:/work/NabSolution/TT_DH_BCA_SOURCE/nic-parent/nic-dx-type/wsdl/idserver.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public IdserverWSImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public IdserverWSImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IdserverWSImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public IdserverWSImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public IdserverWSImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public IdserverWSImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns IdserverWS
     */
    @WebEndpoint(name = "idserverWSImplPort")
    public IdserverWS getIdserverWSImplPort() {
        return super.getPort(IdserverWSImplPort, IdserverWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IdserverWS
     */
    @WebEndpoint(name = "idserverWSImplPort")
    public IdserverWS getIdserverWSImplPort(WebServiceFeature... features) {
        return super.getPort(IdserverWSImplPort, IdserverWS.class, features);
    }

}
