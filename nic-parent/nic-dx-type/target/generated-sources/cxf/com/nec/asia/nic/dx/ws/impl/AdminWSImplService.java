package com.nec.asia.nic.dx.ws.impl;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import com.nec.asia.nic.dx.ws.AdminWS;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.2
 * 2020-10-22T10:20:55.344+07:00
 * Generated source version: 2.7.2
 * 
 */
@WebServiceClient(name = "adminWSImplService", 
                  wsdlLocation = "file:/F:/work/NabSolution/TT_DH_BCA_SOURCE/nic-parent/nic-dx-type/wsdl/admin.wsdl",
                  targetNamespace = "http://impl.ws.dx.nic.asia.nec.com/") 
public class AdminWSImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.ws.dx.nic.asia.nec.com/", "adminWSImplService");
    public final static QName AdminWSImplPort = new QName("http://impl.ws.dx.nic.asia.nec.com/", "adminWSImplPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/F:/work/NabSolution/TT_DH_BCA_SOURCE/nic-parent/nic-dx-type/wsdl/admin.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AdminWSImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/F:/work/NabSolution/TT_DH_BCA_SOURCE/nic-parent/nic-dx-type/wsdl/admin.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AdminWSImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AdminWSImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AdminWSImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AdminWSImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AdminWSImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AdminWSImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns AdminWS
     */
    @WebEndpoint(name = "adminWSImplPort")
    public AdminWS getAdminWSImplPort() {
        return super.getPort(AdminWSImplPort, AdminWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AdminWS
     */
    @WebEndpoint(name = "adminWSImplPort")
    public AdminWS getAdminWSImplPort(WebServiceFeature... features) {
        return super.getPort(AdminWSImplPort, AdminWS.class, features);
    }

}
