package com.nec.asia.nic.comp.admin.code.domain;


/**
 * VRicParameter 
 */
public class VRicParameter implements java.io.Serializable {

	private static final long serialVersionUID = 8938792575434201108L;

	private String id;
	private String paraXml;

	public VRicParameter() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the paraXml
	 */
	public String getParaXml() {
		return paraXml;
	}

	/**
	 * @param paraXml the paraXml to set
	 */
	public void setParaXml(String paraXml) {
		this.paraXml = paraXml;
	}


}
