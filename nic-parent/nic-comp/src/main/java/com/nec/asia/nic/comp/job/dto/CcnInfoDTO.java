package com.nec.asia.nic.comp.job.dto;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author setia_budiyono
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CcnInfoDTO extends AbstractCardInfoDTO  {
	
	
	@XmlElement(name = "Ccn")
	protected List<String> ccnList;

	/**
	 * @return the ccnList
	 */
	public List<String> getCcnList() {
		return ccnList;
	}

	/**
	 * @param ccnList the ccnList to set
	 */
	public void setCcnList(List<String> ccnList) {
		this.ccnList = ccnList;
	}
	
}
