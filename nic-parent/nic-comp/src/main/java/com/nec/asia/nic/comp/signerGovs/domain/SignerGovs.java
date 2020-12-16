package com.nec.asia.nic.comp.signerGovs.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity 
@Table(name = "PARA_SIGNER_COMPARE")
public class SignerGovs implements java.io.Serializable {
    
	private static final long serialVersionUID = 15103120310231L;
	
	private Long id;
	private String codeSigner;
	private byte[] docData;
	private boolean activeT;
	private String active;
	private String createBy;
	private Date createDate;
	private Date updateDate;
	private String updateBy;
	private String description;
	private String nameSigner;
	private String nameGov;
	private String codeGovernment;
	private String docDataF;
	
	public SignerGovs() {
	}

	public SignerGovs(Long id) {
		this.id = id;
	}

	public SignerGovs(Long id, String codeSigner, byte[] docData, String active,
			String createBy, Date createDate, Date updateDate, String updateBy, String description, String nameSigner, String nameGov, String codeGovernment, String docDataF) {

		this.id = id;
		this.codeSigner = codeSigner;
		this.docData = docData;
		this.active = active;
		this.updateBy = updateBy;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.description = description;
		this.nameSigner = nameSigner;
		this.nameGov = nameGov;
		this.codeGovernment = codeGovernment;
		this.docDataF = docDataF;
	}

	
	public String getDocDataF() {
		return docDataF;
	}

	public void setDocDataF(String docDataF) {
		this.docDataF = docDataF;
	}

	public String getCodeGovernment() {
		return codeGovernment;
	}

	public void setCodeGovernment(String codeGovernment) {
		this.codeGovernment = codeGovernment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeSigner() {
		return codeSigner;
	}

	public void setCodeSigner(String codeSigner) {
		this.codeSigner = codeSigner;
	}

	public byte[] getDocData() {
		return docData;
	}

	public void setDocData(byte[] docData) {
		this.docData = docData;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getNameSigner() {
		return nameSigner;
	}

	public void setNameSigner(String nameSigner) {
		this.nameSigner = nameSigner;
	}

	public String getNameGov() {
		return nameGov;
	}

	public void setNameGov(String nameGov) {
		this.nameGov = nameGov;
	}

	public boolean getActiveT() {
		return activeT;
	}

	public void setActiveT(boolean activeT) {
		this.activeT = activeT;
	}
}
