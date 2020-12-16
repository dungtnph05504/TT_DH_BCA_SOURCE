package com.nec.asia.nic.comp.immihistory.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;

/**
 * NicDecisionManager generated by hbm2java
 */
public class ImmiHistoryResult implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L; 
    
    private long id;
    private Integer createdBy;
	private Integer lastModifiedBy;
	private Date createdTime;
	private Date lastModifiedtime;
	private Long immiId;
	private String transactionId;
	private Integer preprocess;
	private Integer supervisorId;
	private Integer gateUserId;
	private String passportNo;
	private String gateNote; //Ghi chú
	private String supervisorNote; //Ghi chú giám sát viên
	private String rsBlacklist;
	private Integer rsBlacklistStatus;
	private String rsVisa;
	private Integer rsVisaStatus;
	private String rsEVisa;
	private Integer rsEVisaStatus;
	private String rsFreeVisa;
	private Integer rsFreeVisaStatus;
	private String rsFreeVisaCard;
	private Integer rsFreeVisaCardStatus;
	private String rsCa;
	private Integer rsCaStatus;
	private String rsFlight;
	private Integer rsFlightStatus;
	private String rsPassport;
	private Integer rsPassportStatus;
	private String rsImmiHistory;
	private Integer rsImmiHistoryStatus;
	private Integer systemResult;
	private Integer superviorResult;
	private Integer adResult;
	private Integer partitionCheck;
	private Integer adId;
	private String adNote;
	private String rsBlacklistChecked;
	private Integer rsBlacklistStatusChecked;
	private String rsBlacklistNoteChecked;
	private Integer rsBlacklistUserIdChecked;
	private String rsBlacklistUsernameChecked;
	private String rsVisaImmi;
	
	private ImmiHistory immi;
	
	public ImmiHistoryResult(){
		
	}
	
	public ImmiHistoryResult(long id, Integer createdBy,
			Integer lastModifiedBy, Date createdTime, Date lastModifiedtime,
			Long immiId, String transactionId, Integer preprocess,
			Integer supervisorId, Integer gateUserId, String passportNo,
			String gateNote, String supervisorNote, String rsBlacklist,
			Integer rsBlacklistStatus, String rsVisa, Integer rsVisaStatus,
			String rsEVisa, Integer rsEVisaStatus, String rsFreeVisa,
			Integer rsFreeVisaStatus, String rsFreeVisaCard,
			Integer rsFreeVisaCardStatus, String rsCa, Integer rsCaStatus,
			String rsFlight, Integer rsFlightStatus, String rsPassport,
			Integer rsPassportStatus, String rsImmiHistory,
			Integer rsImmiHistoryStatus, Integer systemResult,
			Integer superviorResult, Integer adResult, Integer partitionCheck,
			Integer adId, String adNote, String rsBlacklistChecked,
			Integer rsBlacklistStatusChecked, String rsBlacklistNoteChecked,
			Integer rsBlacklistUserIdChecked,
			String rsBlacklistUsernameChecked, String rsVisaImmi) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createdTime = createdTime;
		this.lastModifiedtime = lastModifiedtime;
		this.immiId = immiId;
		this.transactionId = transactionId;
		this.preprocess = preprocess;
		this.supervisorId = supervisorId;
		this.gateUserId = gateUserId;
		this.passportNo = passportNo;
		this.gateNote = gateNote;
		this.supervisorNote = supervisorNote;
		this.rsBlacklist = rsBlacklist;
		this.rsBlacklistStatus = rsBlacklistStatus;
		this.rsVisa = rsVisa;
		this.rsVisaStatus = rsVisaStatus;
		this.rsEVisa = rsEVisa;
		this.rsEVisaStatus = rsEVisaStatus;
		this.rsFreeVisa = rsFreeVisa;
		this.rsFreeVisaStatus = rsFreeVisaStatus;
		this.rsFreeVisaCard = rsFreeVisaCard;
		this.rsFreeVisaCardStatus = rsFreeVisaCardStatus;
		this.rsCa = rsCa;
		this.rsCaStatus = rsCaStatus;
		this.rsFlight = rsFlight;
		this.rsFlightStatus = rsFlightStatus;
		this.rsPassport = rsPassport;
		this.rsPassportStatus = rsPassportStatus;
		this.rsImmiHistory = rsImmiHistory;
		this.rsImmiHistoryStatus = rsImmiHistoryStatus;
		this.systemResult = systemResult;
		this.superviorResult = superviorResult;
		this.adResult = adResult;
		this.partitionCheck = partitionCheck;
		this.adId = adId;
		this.adNote = adNote;
		this.rsBlacklistChecked = rsBlacklistChecked;
		this.rsBlacklistStatusChecked = rsBlacklistStatusChecked;
		this.rsBlacklistNoteChecked = rsBlacklistNoteChecked;
		this.rsBlacklistUserIdChecked = rsBlacklistUserIdChecked;
		this.rsBlacklistUsernameChecked = rsBlacklistUsernameChecked;
		this.rsVisaImmi = rsVisaImmi;
	}

	
	public ImmiHistory getImmi() {
		return immi;
	}

	public void setImmi(ImmiHistory immi) {
		this.immi = immi;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public Integer getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}


	public Integer getLastModifiedBy() {
		return lastModifiedBy;
	}


	public void setLastModifiedBy(Integer lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getLastModifiedtime() {
		return lastModifiedtime;
	}


	public void setLastModifiedtime(Date lastModifiedtime) {
		this.lastModifiedtime = lastModifiedtime;
	}


	public Long getImmiId() {
		return immiId;
	}


	public void setImmiId(Long immiId) {
		this.immiId = immiId;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public Integer getPreprocess() {
		return preprocess;
	}


	public void setPreprocess(Integer preprocess) {
		this.preprocess = preprocess;
	}


	public Integer getSupervisorId() {
		return supervisorId;
	}


	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}


	public Integer getGateUserId() {
		return gateUserId;
	}


	public void setGateUserId(Integer gateUserId) {
		this.gateUserId = gateUserId;
	}


	public String getPassportNo() {
		return passportNo;
	}


	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}


	public String getGateNote() {
		return gateNote;
	}


	public void setGateNote(String gateNote) {
		this.gateNote = gateNote;
	}


	public String getSupervisorNote() {
		return supervisorNote;
	}


	public void setSupervisorNote(String supervisorNote) {
		this.supervisorNote = supervisorNote;
	}


	public String getRsBlacklist() {
		return rsBlacklist;
	}


	public void setRsBlacklist(String rsBlacklist) {
		this.rsBlacklist = rsBlacklist;
	}


	public Integer getRsBlacklistStatus() {
		return rsBlacklistStatus;
	}


	public void setRsBlacklistStatus(Integer rsBlacklistStatus) {
		this.rsBlacklistStatus = rsBlacklistStatus;
	}


	public String getRsVisa() {
		return rsVisa;
	}


	public void setRsVisa(String rsVisa) {
		this.rsVisa = rsVisa;
	}


	public Integer getRsVisaStatus() {
		return rsVisaStatus;
	}


	public void setRsVisaStatus(Integer rsVisaStatus) {
		this.rsVisaStatus = rsVisaStatus;
	}


	public String getRsEVisa() {
		return rsEVisa;
	}


	public void setRsEVisa(String rsEVisa) {
		this.rsEVisa = rsEVisa;
	}


	public Integer getRsEVisaStatus() {
		return rsEVisaStatus;
	}


	public void setRsEVisaStatus(Integer rsEVisaStatus) {
		this.rsEVisaStatus = rsEVisaStatus;
	}


	public String getRsFreeVisa() {
		return rsFreeVisa;
	}


	public void setRsFreeVisa(String rsFreeVisa) {
		this.rsFreeVisa = rsFreeVisa;
	}


	public Integer getRsFreeVisaStatus() {
		return rsFreeVisaStatus;
	}


	public void setRsFreeVisaStatus(Integer rsFreeVisaStatus) {
		this.rsFreeVisaStatus = rsFreeVisaStatus;
	}


	public String getRsFreeVisaCard() {
		return rsFreeVisaCard;
	}


	public void setRsFreeVisaCard(String rsFreeVisaCard) {
		this.rsFreeVisaCard = rsFreeVisaCard;
	}


	public Integer getRsFreeVisaCardStatus() {
		return rsFreeVisaCardStatus;
	}


	public void setRsFreeVisaCardStatus(Integer rsFreeVisaCardStatus) {
		this.rsFreeVisaCardStatus = rsFreeVisaCardStatus;
	}


	public String getRsCa() {
		return rsCa;
	}


	public void setRsCa(String rsCa) {
		this.rsCa = rsCa;
	}


	public Integer getRsCaStatus() {
		return rsCaStatus;
	}


	public void setRsCaStatus(Integer rsCaStatus) {
		this.rsCaStatus = rsCaStatus;
	}


	public String getRsFlight() {
		return rsFlight;
	}


	public void setRsFlight(String rsFlight) {
		this.rsFlight = rsFlight;
	}


	public Integer getRsFlightStatus() {
		return rsFlightStatus;
	}


	public void setRsFlightStatus(Integer rsFlightStatus) {
		this.rsFlightStatus = rsFlightStatus;
	}


	public String getRsPassport() {
		return rsPassport;
	}


	public void setRsPassport(String rsPassport) {
		this.rsPassport = rsPassport;
	}


	public Integer getRsPassportStatus() {
		return rsPassportStatus;
	}


	public void setRsPassportStatus(Integer rsPassportStatus) {
		this.rsPassportStatus = rsPassportStatus;
	}


	public String getRsImmiHistory() {
		return rsImmiHistory;
	}


	public void setRsImmiHistory(String rsImmiHistory) {
		this.rsImmiHistory = rsImmiHistory;
	}


	public Integer getRsImmiHistoryStatus() {
		return rsImmiHistoryStatus;
	}


	public void setRsImmiHistoryStatus(Integer rsImmiHistoryStatus) {
		this.rsImmiHistoryStatus = rsImmiHistoryStatus;
	}


	public Integer getSystemResult() {
		return systemResult;
	}


	public void setSystemResult(Integer systemResult) {
		this.systemResult = systemResult;
	}


	public Integer getSuperviorResult() {
		return superviorResult;
	}


	public void setSuperviorResult(Integer superviorResult) {
		this.superviorResult = superviorResult;
	}


	public Integer getAdResult() {
		return adResult;
	}


	public void setAdResult(Integer adResult) {
		this.adResult = adResult;
	}


	public Integer getPartitionCheck() {
		return partitionCheck;
	}


	public void setPartitionCheck(Integer partitionCheck) {
		this.partitionCheck = partitionCheck;
	}


	public Integer getAdId() {
		return adId;
	}


	public void setAdId(Integer adId) {
		this.adId = adId;
	}


	public String getAdNote() {
		return adNote;
	}


	public void setAdNote(String adNote) {
		this.adNote = adNote;
	}


	public String getRsBlacklistChecked() {
		return rsBlacklistChecked;
	}


	public void setRsBlacklistChecked(String rsBlacklistChecked) {
		this.rsBlacklistChecked = rsBlacklistChecked;
	}


	public Integer getRsBlacklistStatusChecked() {
		return rsBlacklistStatusChecked;
	}


	public void setRsBlacklistStatusChecked(Integer rsBlacklistStatusChecked) {
		this.rsBlacklistStatusChecked = rsBlacklistStatusChecked;
	}


	public String getRsBlacklistNoteChecked() {
		return rsBlacklistNoteChecked;
	}


	public void setRsBlacklistNoteChecked(String rsBlacklistNoteChecked) {
		this.rsBlacklistNoteChecked = rsBlacklistNoteChecked;
	}


	public Integer getRsBlacklistUserIdChecked() {
		return rsBlacklistUserIdChecked;
	}


	public void setRsBlacklistUserIdChecked(Integer rsBlacklistUserIdChecked) {
		this.rsBlacklistUserIdChecked = rsBlacklistUserIdChecked;
	}


	public String getRsBlacklistUsernameChecked() {
		return rsBlacklistUsernameChecked;
	}


	public void setRsBlacklistUsernameChecked(String rsBlacklistUsernameChecked) {
		this.rsBlacklistUsernameChecked = rsBlacklistUsernameChecked;
	}


	public String getRsVisaImmi() {
		return rsVisaImmi;
	}


	public void setRsVisaImmi(String rsVisaImmi) {
		this.rsVisaImmi = rsVisaImmi;
	}
	
	
}
