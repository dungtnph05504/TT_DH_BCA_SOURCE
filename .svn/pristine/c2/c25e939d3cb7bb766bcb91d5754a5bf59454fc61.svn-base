package com.nec.asia.nic.framework.security.webservices;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PasswordCallbackHandler, uses to populate the password to soapHeader for authentication purpose.
 */

public class ServerPasswordCallbackHandler implements CallbackHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Map<String, String> passwords = new HashMap<String, String>();
    
    public ServerPasswordCallbackHandler() {
    	logger.debug("[Server] In Contructor - ServerPasswordCallbackHandler()");
    	//TODO load the password from properties or database
        passwords.put("NICServer", "NICServer");
        passwords.put("CPDServer", "CpDsErVeR2013");
        passwords.put("nicwebservice", "N1cW3bServ1ceAcc3ssGr4nted");
    }

    /**
     * Here, we attempt to get the password from the private alias/passwords map.
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    	logger.debug("[Server] In handle()");
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];
            logger.debug("[Server] In handle() loop["+i+"] identifier:"+pc.getIdentifier()+ " password:"+pc.getPassword());
            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }
    }
    
    /**
     * Add an alias/password pair to the callback mechanism.
     */
    public void setAliasPassword(String alias, String password) {
    	logger.debug("[Server] In setAliasPassword()");
        passwords.put(alias, password);
    }
}
