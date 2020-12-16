package com.nec.asia.nic.util;

import javax.xml.bind.JAXBContext;
import org.apache.log4j.Logger;

import com.nec.asia.nic.comp.job.dto.PersoLogDataDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.framework.common.JaxbXmlConvertor;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;



/**
 * @author setia_budiyono
 *
 */
public class TransLogInfoXmlConvertor extends JaxbXmlConvertor {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(TransLogInfoXmlConvertor.class);
	
	//JAXBContext is thread safe, so instantiate only once
	/** The jaxb context. */
	private static volatile JAXBContext jaxbContext;
	
	/**
	 * Instantiates a new event data xml convertor.
	 *
	 * @throws RuntimeException the runtime exception
	 */
	public TransLogInfoXmlConvertor() throws RuntimeException {
		try {
			if(jaxbContext==null) 
		    {
				synchronized(TransLogInfoXmlConvertor.class) {
					if(jaxbContext==null) {
						jaxbContext = JAXBContext.newInstance(new Class[] {
								LogInfoDTO.class,
								PersoLogDataDTO.class,
						});
					}
				}
		    }
		}
		catch(Throwable th) {
			logger.fatal("Error in TransLogInfoXmlConvertor.init: "+th.getMessage(), th);
			throw new RuntimeException("Error in TransLogInfoXmlConvertor.init: "+th.getMessage(), th);
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
	public String marshal(Object obj, String defaultvalue)  {
		try {
			return super.marshal(obj);
		}
		catch(JaxbXmlConvertorException ex) {
			return defaultvalue;
		}
	}
	
	
}
