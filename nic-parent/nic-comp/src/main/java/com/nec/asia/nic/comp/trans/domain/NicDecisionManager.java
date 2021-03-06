package com.nec.asia.nic.comp.trans.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * NicDecisionManager generated by hbm2java
 */
public class NicDecisionManager implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L; 
    
    private Long id;
    private String decisionNumber;
	private String signer;
	private Date signDate;
	private String competentAuthorities;
	private String competentAuthoritiesEng;
	private String purpose;
	private String timeplan;
	private String tripCost;
	private String invitingAgency;
	private String transitNation;
	private String timeLasts;
	private String countryPlan;
	private String createBy;
	private Date createDate;
	private String modifyBy;
	private Date modifyDate;
	private String description;
	private byte[] data;
	private int numberPerson;
	private String dataF;
	private String fileUpload;
	public NicDecisionManager() {
	}

	public NicDecisionManager(Long id) {
		this.id = id;
	}

	public NicDecisionManager(Long id, String decisionNumber, String signer,
			Date signDate, String competentAuthorities,
			String competentAuthoritiesEng, String purpose, String timeplan,
			String tripCost, String invitingAgency, String transitNation,
			String timeLasts, String countryPlan, String createBy,
			Date createDate, String modifyBy, Date modifyDate,
			String description, byte[] data, int numberPerson, String dataF) {
		super();
		this.id = id;
		this.decisionNumber = decisionNumber;
		this.signer = signer;
		this.signDate = signDate;
		this.competentAuthorities = competentAuthorities;
		this.competentAuthoritiesEng = competentAuthoritiesEng;
		this.purpose = purpose;
		this.timeplan = timeplan;
		this.tripCost = tripCost;
		this.invitingAgency = invitingAgency;
		this.transitNation = transitNation;
		this.timeLasts = timeLasts;
		this.countryPlan = countryPlan;
		this.createBy = createBy;
		this.createDate = createDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
		this.description = description;
		this.data = data;
		this.numberPerson = numberPerson;
		this.dataF = dataF;
	}
	
	
	public String  getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(String  fileUpload) {
		this.fileUpload = fileUpload;
	}

	
	public String getDataF() {
		return dataF;
	}

	public void setDataF(String dataF) {
		this.dataF = dataF;
	}

	public int getNumberPerson() {
		return numberPerson;
	}

	public void setNumberPerson(int numberPerson) {
		this.numberPerson = numberPerson;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getCompetentAuthorities() {
		return competentAuthorities;
	}

	public void setCompetentAuthorities(String competentAuthorities) {
		this.competentAuthorities = competentAuthorities;
	}

	public String getCompetentAuthoritiesEng() {
		return competentAuthoritiesEng;
	}

	public void setCompetentAuthoritiesEng(String competentAuthoritiesEng) {
		this.competentAuthoritiesEng = competentAuthoritiesEng;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getTimeplan() {
		return timeplan;
	}

	public void setTimeplan(String timeplan) {
		this.timeplan = timeplan;
	}

	public String getTripCost() {
		return tripCost;
	}

	public void setTripCost(String tripCost) {
		this.tripCost = tripCost;
	}

	public String getInvitingAgency() {
		return invitingAgency;
	}

	public void setInvitingAgency(String invitingAgency) {
		this.invitingAgency = invitingAgency;
	}

	public String getTransitNation() {
		return transitNation;
	}

	public void setTransitNation(String transitNation) {
		this.transitNation = transitNation;
	}

	public String getTimeLasts() {
		return timeLasts;
	}

	public void setTimeLasts(String timeLasts) {
		this.timeLasts = timeLasts;
	}

	public String getCountryPlan() {
		return countryPlan;
	}

	public void setCountryPlan(String countryPlan) {
		this.countryPlan = countryPlan;
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

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
