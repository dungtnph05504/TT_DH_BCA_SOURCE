package com.nec.asia.nic.framework.common;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

/**
 * @author setia_budiyono
 *
 */


/*
 * Modification History:
 * 
 * 30 Dec 2013 (jp): comment out "marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);" to avoid perso error when XML format has "\n" at end
 * 06 Jan 2015 (khangtv): Added new function marshal(Object obj, JAXBContext context)  to marshal by specific context
 * 
 */
public abstract class JaxbXmlConvertor {
	private static final Logger logger = Logger.getLogger(JaxbXmlConvertor.class);
	
	protected abstract JAXBContext getJAXBContext();
	
	
	public Object unmarshal(String objXml) throws JaxbXmlConvertorException {
		try {
			
			Unmarshaller unMarshaller = getJAXBContext().createUnmarshaller();
			StringReader reader = new StringReader(objXml);
			try {
				Object obj = (Object)unMarshaller.unmarshal(reader);
				if(obj==null) {
					throw new JaxbXmlConvertorException("Invalid objXml");
				}
				
				return obj;
			}
			catch(JAXBException ex) {
				logger.trace("objXml: "+objXml);
				throw new JaxbXmlConvertorException("Error unmarshalling objXml: "+ex.getMessage(), ex);
			}
			finally {
				reader.close();
			}
		}
		catch(JAXBException ex) {
			logger.trace("objXml: "+objXml);
			throw new JaxbXmlConvertorException("Error creating Unmarshaller to parse objXml: "+ex.getMessage()+", objXml: "+objXml, ex);			
		}
	}
	
	public Object unmarshal(Node inNode) throws JaxbXmlConvertorException {
		try {
			
			Unmarshaller unMarshaller = getJAXBContext().createUnmarshaller();
			try {
				Object obj = (Object)unMarshaller.unmarshal(inNode);
				
				if(obj==null) {
					throw new JaxbXmlConvertorException("Invalid inNode");
				}
				
				return obj;
			}
			catch(JAXBException ex) {
				logger.trace("inNode: "+inNode);
				throw new JaxbXmlConvertorException("Error unmarshalling inNode: "+ex.getMessage(), ex);
			}
		}
		catch(JAXBException ex) {
			throw new JaxbXmlConvertorException("Error creating Unmarshaller to parse inNode: "+ex.getMessage()+", inNode: "+inNode, ex);			
		}
	}
	
	
	public String marshal(Object obj) throws JaxbXmlConvertorException {
		try {
			Marshaller marshaller = getJAXBContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "");
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter writer = new StringWriter();
			try {

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

	public String marshal(Object obj, JAXBContext context) throws JaxbXmlConvertorException {
        try {
            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//            marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter writer = new StringWriter();
            try {
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
	public void marshal(Object obj, Node outNode) throws JaxbXmlConvertorException {
		try {
			Marshaller marshaller = getJAXBContext().createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "");
			 marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			try {
				marshaller.marshal(obj, outNode);
				if(!outNode.hasChildNodes()) {
					throw new JaxbXmlConvertorException("Error marshalling obj to xml node, No child nodes found, obj: "+obj);
				}
			}
			catch(JAXBException ex) {
				throw new JaxbXmlConvertorException("Error marshalling obj to Xml node: "+ex.getMessage(), ex);
			}
		}
		catch(JAXBException ex) {
			throw new JaxbXmlConvertorException("Error creating Marshaller to generate objXml: "+ex.getMessage(), ex);			
		}
	}

}
