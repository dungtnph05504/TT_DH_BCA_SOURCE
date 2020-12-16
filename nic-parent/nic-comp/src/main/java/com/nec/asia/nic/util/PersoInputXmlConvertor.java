package com.nec.asia.nic.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.perso.model.InputDataDTO;
import com.nec.asia.nic.framework.common.JaxbXmlConvertor;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;



/**
 * @author setia
 *
 */
/*
 * Modification History:
 * 
 * 30 May 2017 (setia): init class
 */
public class PersoInputXmlConvertor extends JaxbXmlConvertor {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PersoInputXmlConvertor.class);
	
	//JAXBContext is thread safe, so instantiate only once
	/** The jaxb context. */
	private static volatile JAXBContext jaxbContext;
	
	/** The Base64 utility for encode and decode **/
	private Base64 base64Util = new Base64(0);
	
	
	/**
	 * Instantiates a new event data xml convertor.
	 *
	 * @throws RuntimeException the runtime exception
	 */
	public PersoInputXmlConvertor() throws RuntimeException {
		try {
			if(jaxbContext==null) 
		    {
				synchronized(PersoInputXmlConvertor.class) {
					if(jaxbContext==null) {
						jaxbContext = JAXBContext.newInstance(new Class[] {
								InputDataDTO.class
						});
					}
				}
		    }
		}
		catch(Throwable th) {
			logger.fatal("Error in EventDataXmlConvertor.init: "+th.getMessage(), th);
			throw new RuntimeException("Error in EventDataXmlConvertor.init: "+th.getMessage(), th);
		}			
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.baf.comp.common.xml.JaxbXmlConvertor#getJAXBContext()
	 */
	@Override
	protected JAXBContext getJAXBContext() {
		// TODO Auto-generated method stub
		return jaxbContext;
	}
	
	/**
	 * Marshal.
	 *
	 * @param obj the obj
	 * @param defaultvalue the defaultvalue
	 * @return the string
	 */
	@Override
	public String marshal(Object obj) {
		try {
			try {
				Marshaller marshaller = getJAXBContext().createMarshaller();
				//marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
				marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "");
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
				//marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				StringWriter writer = new StringWriter();
				try {
					 marshaller.setProperty(CharacterEscapeHandler.class.getName(),
				                new CharacterEscapeHandler() {
				                    @Override
				                    public void escape(char[] ac, int i, int j, boolean flag,
				                            Writer writer) throws IOException {
				                        writer.write(ac, i, j);
				                    }
				                });
					
					marshaller.marshal(obj, writer);
					String objXml = writer.toString();

					if(objXml==null || objXml.trim().length()==0) {
						throw new JaxbXmlConvertorException("Error marshalling obj to xml, obj: "+obj);
					}
					return objXml;
				}
				catch(JAXBException ex) {
					throw new JaxbXmlConvertorException("Error marshalling objXml: "+ex.getMessage(), ex);
				}
				finally {
					//closing writer has no effect
					try { writer.close();  }
					catch (IOException e) {}
				}
			}
			catch(JAXBException ex) {
				throw new JaxbXmlConvertorException("Error creating Marshaller to generate objXml: "+ex.getMessage(), ex);			
			}
			
		}
		catch(JaxbXmlConvertorException ex) {
			return null;
		}
	}
	
/*
 * (non-Javadoc)
 * @see com.nec.asia.nic.framework.common.JaxbXmlConvertor#marshal(java.lang.Object, javax.xml.bind.JAXBContext)
 */
    public String marshal(Object obj, JAXBContext context) {
        String marshalValue = null;
        try {
            marshalValue = super.marshal(obj, context);
        }
        catch(JaxbXmlConvertorException ex) {
            marshalValue = null;
        }
        
       return marshalValue;
    }
    
}
