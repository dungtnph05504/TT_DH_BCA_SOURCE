package com.nec.asia.nic.comp.immihistory.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * NicDecisionManager generated by hbm2java
 */

@XmlRootElement(name = "ImmiHistorys")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImmiHistorys implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L; 
    
    @XmlElement(name = "immiHistory") 
    private List<ImmiHistory> immiHistory = null;
  
	public ImmiHistorys() {
		super();
	}

	public List<ImmiHistory> getImmiHistory() {
		return immiHistory;
	}

	public void setImmiHistory(List<ImmiHistory> immiHistory) {
		this.immiHistory = immiHistory;
	}
	
	
}
