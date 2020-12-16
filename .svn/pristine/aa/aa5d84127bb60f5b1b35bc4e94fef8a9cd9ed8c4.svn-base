package com.nec.asia.nic.comp.trans.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicCardReconRptDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionReconRptDao;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRptId;
import com.nec.asia.nic.comp.trans.service.ReconReportService;
import com.nec.asia.nic.comp.trans.service.exception.ReconReportServiceException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/*
 * Modification History:
 * 
 * 27 Nov 2013 (chris) : init service
 * 12 Dec 2013 (chris) : add logging, set collection date to application date if it is null
 * 21 Jan 2014 (chris) : update recon status for nic
 * 24 Mar 2014 (chris) : skip update if nic data no changes.
 * 11 Apr 2014 (chris) : update method updateNicTransactionReconReportData()
 * 15 May 2014 (chris) : add method exportTransaction() -- export transactions to files
 * 02 Jun 2014 (chris) : update method updateTransactionReconReportBySubsystem()
 */

@Service("reconReportService")
public class ReconReportServiceImpl extends
		DefaultBusinessServiceImpl<NicTransactionReconRpt, NicTransactionReconRptId, NicTransactionReconRptDao>
		implements ReconReportService {
	
	@Autowired
	private NicCardReconRptDao nicCardReconRptDao;
	
	public ReconReportServiceImpl () {}
	
	@Autowired
	public ReconReportServiceImpl (NicTransactionReconRptDao dao) {
		this.dao = dao;
	}
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateNicTransactionReconReportData(String regSiteCode, String issSiteCode, String applicationDate, String collectionDate) throws ReconReportServiceException {
		Date startTime = new Date();
//		try {
//			logger.info("[updateNicTransactionReconReportData] for regSiteCode:{}, issSiteCode:{}, applicationDate:{} collectionDate:{}"
//					, new Object[] {regSiteCode, issSiteCode, applicationDate, collectionDate});
//			List<Object> reconRptDataList = dao.getNicTransactionReconRptData(regSiteCode, issSiteCode, applicationDate, collectionDate);
//			List<NicTransactionReconRpt> reconRptList = this.parseNicTransactionReconRptData(reconRptDataList);
//			
//			for (NicTransactionReconRpt reconRpt: reconRptList) {
//				//retrieve the reconRpt from database
//				NicTransactionReconRpt reconRptDBO = dao.findById(reconRpt.getId());
//				if (reconRptDBO!=null) {
//					if (StringUtils.isNotBlank(reconRptDBO.getReconStatus())&& this.isReconReportStatusTally(reconRptDBO.getReconStatus())) {
//						logger.warn("Recon Report already matched and tally, update is not required.");
//						continue;
//					}
//					logger.info("[updateNicTransactionReconReportData][before] regSiteCode:{}, issSiteCode:{}, applicationDate:{}, collectionDate:{}, result: nicReceived-{}, nicRejected-{}, nicWip-{}, nicPersoSubmitted-{}, nicPersoRejected-{}, nicPersoPersonalized-{}, nicPersoCardDelivered-{}, nicPersoWip-{}, nicCcricCardReceived-{}, nicCcricCardRejected-{}, nicCcricCardCollected-{}, nicCcricWip-{}"
//							, new Object[] {reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate()
//							, reconRptDBO.getNicReceived(), reconRptDBO.getNicRejected(), reconRptDBO.getNicWip(), reconRptDBO.getNicPersoSubmitted(), reconRptDBO.getNicPersoRejected(), reconRptDBO.getNicPersoPersonalized(), reconRptDBO.getNicPersoCardDelivered(), reconRpt.getNicPersoWip(), reconRptDBO.getNicCcricCardReceived(), reconRptDBO.getNicCcricCardRejected(), reconRptDBO.getNicCcricCardCollected(), reconRptDBO.getNicCcricWip()});
//					if (	(reconRpt.getNicReceived()!=null && reconRpt.getNicReceived().equals(reconRptDBO.getNicReceived())) &&
//							(reconRpt.getNicRejected()!=null && reconRpt.getNicRejected().equals(reconRptDBO.getNicRejected())) &&
//							(reconRpt.getNicWip()!=null && reconRpt.getNicWip().equals(reconRptDBO.getNicWip())) &&
//							(reconRpt.getNicPersoSubmitted()!=null && reconRpt.getNicPersoSubmitted().equals(reconRptDBO.getNicPersoSubmitted())) &&
//							(reconRpt.getNicPersoRejected()!=null && reconRpt.getNicPersoRejected().equals(reconRptDBO.getNicPersoRejected())) &&
//							(reconRpt.getNicPersoPersonalized()!=null && reconRpt.getNicPersoPersonalized().equals(reconRptDBO.getNicPersoPersonalized())) &&
//							(reconRpt.getNicPersoCardDelivered()!=null && reconRpt.getNicPersoCardDelivered().equals(reconRptDBO.getNicPersoCardDelivered())) &&
//							(reconRpt.getNicPersoWip()!=null && reconRpt.getNicPersoWip().equals(reconRptDBO.getNicPersoWip())) &&
//							(reconRpt.getNicCcricCardReceived()!=null && reconRpt.getNicCcricCardReceived().equals(reconRptDBO.getNicCcricCardReceived())) &&
//							(reconRpt.getNicCcricCardRejected()!=null && reconRpt.getNicCcricCardRejected().equals(reconRptDBO.getNicCcricCardRejected())) &&
//							(reconRpt.getNicCcricCardCollected()!=null && reconRpt.getNicCcricCardCollected().equals(reconRptDBO.getNicCcricCardCollected())) &&
//							(reconRpt.getNicCcricWip()!=null && reconRpt.getNicCcricWip().equals(reconRptDBO.getNicCcricWip())) 
//							) { //if all data is still same then update is not required.
//						logger.info("[updateNicTransactionReconReportData] [[[NO UPDATE]]] for regSiteCode:{}, issSiteCode:{}, applicationDate:{} collectionDate:{} - reconStatus:{}"
//								, new Object[] {reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate(), reconRptDBO.getReconStatus()});
//						continue;
//					}
//					//update the report count from nic and update back to database
//					BeanUtils.copyProperty(reconRptDBO, "nicReceived", reconRpt.getNicReceived());
//					BeanUtils.copyProperty(reconRptDBO, "nicRejected", reconRpt.getNicRejected());
//					BeanUtils.copyProperty(reconRptDBO, "nicWip", reconRpt.getNicWip());
//					BeanUtils.copyProperty(reconRptDBO, "nicPersoSubmitted", reconRpt.getNicPersoSubmitted());
//					BeanUtils.copyProperty(reconRptDBO, "nicPersoRejected", reconRpt.getNicPersoRejected());
//					BeanUtils.copyProperty(reconRptDBO, "nicPersoPersonalized", reconRpt.getNicPersoPersonalized());
//					BeanUtils.copyProperty(reconRptDBO, "nicPersoCardDelivered", reconRpt.getNicPersoCardDelivered());
//					BeanUtils.copyProperty(reconRptDBO, "nicPersoWip", reconRpt.getNicPersoWip());
//					BeanUtils.copyProperty(reconRptDBO, "nicCcricCardReceived", reconRpt.getNicCcricCardReceived());
//					BeanUtils.copyProperty(reconRptDBO, "nicCcricCardRejected", reconRpt.getNicCcricCardRejected());
//					BeanUtils.copyProperty(reconRptDBO, "nicCcricCardCollected", reconRpt.getNicCcricCardCollected());
//					BeanUtils.copyProperty(reconRptDBO, "nicCcricWip", reconRpt.getNicCcricWip());
//					logger.info("[updateNicTransactionReconReportData][ after] regSiteCode:{}, issSiteCode:{}, applicationDate:{}, collectionDate:{}, result: nicReceived-{}, nicRejected-{}, nicWip-{}, nicPersoSubmitted-{}, nicPersoRejected-{}, nicPersoPersonalized-{}, nicPersoCardDelivered-{}, nicPersoWip-{}, nicCcricCardReceived-{}, nicCcricCardRejected-{}, nicCcricCardCollected-{}, nicCcricWip-{}"
//							, new Object[] {reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate()
//							, reconRptDBO.getNicReceived(), reconRptDBO.getNicRejected(), reconRptDBO.getNicWip(), reconRptDBO.getNicPersoSubmitted(), reconRptDBO.getNicPersoRejected(), reconRptDBO.getNicPersoPersonalized(), reconRptDBO.getNicPersoCardDelivered(), reconRpt.getNicPersoWip(), reconRptDBO.getNicCcricCardReceived(), reconRptDBO.getNicCcricCardRejected(), reconRptDBO.getNicCcricCardCollected(), reconRptDBO.getNicCcricWip()});
//					reconRptDBO.setUpdateBy(SYSTEM_NIC);
//					reconRptDBO.setUpdateDate(new Date());
//					updateTransactionReconStatus(reconRptDBO);
//				} else {
//					//create the record in database
//					reconRpt.setCreateBy(SYSTEM_NIC);
//					reconRpt.setCreateDate(new Date());
//					reconRpt.setUpdateBy(SYSTEM_NIC);
//					reconRpt.setUpdateDate(new Date());
//					reconRptDBO = reconRpt;
//					updateTransactionReconStatus(reconRptDBO);
//				}
//				logger.info("[updateNicTransactionReconReportData] for regSiteCode:{}, issSiteCode:{}, applicationDate:{} collectionDate:{} - reconStatus:{}"
//						, new Object[] {reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate(), reconRptDBO.getReconStatus()});
//			}
//		} catch (IllegalAccessException e) {
//			throw new ReconReportServiceException(e);
//		} catch (InvocationTargetException e) {
//			throw new ReconReportServiceException(e);
//		}
		Date endTime = new Date();
		logger.info("[updateNicTransactionReconReportData] - startTime:{}, endTime:{}, cost:{} ms"
				, new Object[] {startTime, endTime, endTime.getTime()-startTime.getTime()});
	}
	
	//@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	private void updateTransactionReconStatus(NicTransactionReconRpt reconRpt) {	
//		String ccRicReconStatus = RECON_STATUS_MISSING;
//		String nicReconStatus = RECON_STATUS_MISSING;
//		String persoReconStatus = RECON_STATUS_MISSING;
//		String cciReconStatus = RECON_STATUS_MISSING;
//		String overallReconStatus = RECON_STATUS_MISSING; 
//		
//		String reconRemarks = ""; //[chris] 02 Jun 2014
//		if (reconRpt!=null) {
//			//ccric
//			if (reconRpt.getCcricRegistered()!=null && reconRpt.getCcricUploaded()!=null && reconRpt.getCcricCancelled()!=null && reconRpt.getCcricPending()!=null) {
//				ccRicReconStatus = RECON_STATUS_MATCHED;
//				if (reconRpt.getCcricPending().longValue()!=0) {
//					ccRicReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getCcricRegistered().longValue()!=reconRpt.getCcricUploaded().longValue()) {
//					ccRicReconStatus = RECON_STATUS_UNMATCHED;
//				}
//			}
//			//nic
//			if (reconRpt.getNicReceived()!=null && reconRpt.getNicRejected()!=null && reconRpt.getNicWip()!=null
//					&& reconRpt.getNicPersoSubmitted()!=null && reconRpt.getNicPersoRejected()!=null && reconRpt.getNicPersoPersonalized()!=null && reconRpt.getNicPersoCardDelivered()!=null && reconRpt.getNicPersoWip()!=null
//					&& reconRpt.getNicCcricCardReceived()!=null && reconRpt.getNicCcricCardRejected()!=null && reconRpt.getNicCcricCardCollected()!=null && reconRpt.getNicCcricWip()!=null) {
//				nicReconStatus = RECON_STATUS_MATCHED;
//				if (reconRpt.getNicWip().longValue()!=0) {
//					nicReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getNicPersoWip().longValue()!=0) {
//					nicReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getNicCcricWip().longValue()!=0) {
//					nicReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getNicReceived().longValue()!=	reconRpt.getNicRejected().longValue()+reconRpt.getNicWip().longValue()+reconRpt.getNicPersoSubmitted().longValue()) {
//					nicReconStatus = RECON_STATUS_UNMATCHED;
//					reconRemarks += "NIC-Perso Not Tally, ";
//				}				
//				if (reconRpt.getNicPersoSubmitted().longValue()!=reconRpt.getNicPersoRejected().longValue()+reconRpt.getNicPersoPersonalized().longValue()+reconRpt.getNicPersoWip().longValue()) {
//					nicReconStatus = RECON_STATUS_UNMATCHED;
//				}
//				if (reconRpt.getNicPersoCardDelivered().longValue()!=reconRpt.getNicCcricCardReceived().longValue()) {
//					nicReconStatus = RECON_STATUS_UNMATCHED;
//				}
//				if (reconRpt.getNicCcricCardReceived().longValue()!=reconRpt.getNicCcricCardRejected().longValue()+reconRpt.getNicCcricCardCollected().longValue()+reconRpt.getNicCcricWip().longValue()) {
//					nicReconStatus = RECON_STATUS_UNMATCHED;
//				}
//			}
//			//perso
//			if (reconRpt.getPersoReceived()!=null && reconRpt.getPersoRejected()!=null && reconRpt.getPersoCardPacked()!=null && reconRpt.getPersoCardDelivered()!=null && reconRpt.getPersoWip()!=null) {
//				persoReconStatus = RECON_STATUS_MATCHED;
//				if (reconRpt.getPersoWip().longValue()!=0) {
//					persoReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getPersoReceived().longValue()!=reconRpt.getPersoRejected().longValue()+reconRpt.getPersoCardPacked().longValue()+reconRpt.getPersoCardDelivered().longValue()+reconRpt.getPersoWip().longValue()) {
//					persoReconStatus = RECON_STATUS_UNMATCHED;
//				}
//			}
//			//cci
//			if (reconRpt.getCciStockedIn()!=null && reconRpt.getCciRejected()!=null && reconRpt.getCciCollected()!=null && reconRpt.getCciCollectionFailed()!=null && reconRpt.getCciPendingCollection()!=null) {
//				cciReconStatus = RECON_STATUS_MATCHED;
//				if (reconRpt.getCciPendingCollection().longValue()!=0) {
//					cciReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getCciStockedIn().longValue()!=reconRpt.getCciRejected().longValue()+reconRpt.getCciCollected().longValue()+reconRpt.getCciCollectionFailed().longValue()+reconRpt.getCciPendingCollection().longValue()) {
//					cciReconStatus = RECON_STATUS_UNMATCHED;
//				}
//			}
//			//=IF(K6-L6-M6<>N6,"NIC-Perso Not Tally", IF(Q6<>AB6,"PENDING STOCKED IN", IF(AF6=0,"ALL CARD ISSUED","PENDING CARD COLLECTION"))))
//			//overall
//			if (reconRpt.getCcricUploaded()!=null && reconRpt.getNicReceived()!=null
//					&& reconRpt.getNicCcricCardReceived()!=null && reconRpt.getCciStockedIn()!=null) {
//				overallReconStatus = RECON_STATUS_MATCHED;
//				if (StringUtils.equals(ccRicReconStatus, RECON_STATUS_PENDING)
//						|| StringUtils.equals(nicReconStatus, RECON_STATUS_PENDING)
//						|| StringUtils.equals(persoReconStatus, RECON_STATUS_PENDING)
//						|| StringUtils.equals(cciReconStatus, RECON_STATUS_PENDING)) {
//					overallReconStatus = RECON_STATUS_PENDING;
//				}
//				if (reconRpt.getCcricUploaded().longValue()!=reconRpt.getNicReceived().longValue()) {
//					overallReconStatus = RECON_STATUS_UNMATCHED;
//					reconRemarks += "CC Upload Not Tally, ";
//				}
//				if (reconRpt.getNicCcricCardReceived().longValue()!=reconRpt.getCciStockedIn().longValue()) {
//					overallReconStatus = RECON_STATUS_UNMATCHED;
//				}
//				if (reconRpt.getNicPersoCardDelivered()!=null && reconRpt.getCciStockedIn()!=null) {
//					if (reconRpt.getNicPersoCardDelivered().longValue()!=reconRpt.getCciStockedIn().longValue()) {
//						reconRemarks += "PENDING STOCKED IN, ";
//					}
//				}
//			}
//			//set remarks
//			if (StringUtils.isBlank(reconRemarks)) {
//				if (reconRpt.getCciPendingCollection()!=null) {
//					if (reconRpt.getCciPendingCollection().longValue()>0) {
//						reconRemarks += "PENDING CARD COLLECTION";
//					} else {
//						reconRemarks += "ALL CARD ISSUED";
//					}
//				}
//			}
//		}
//		//in format of CCRIC,NIC,PERSO,CCI,OVERALL
//		String reconStatus = ccRicReconStatus+nicReconStatus+persoReconStatus+cciReconStatus+overallReconStatus;
//		reconRpt.setReconStatus(reconStatus);
//		/*
//		 * 5.	System Design
//		 * h	For transaction with no collection date, set the collection date to be the same as Application date 
//		 */
//		if (StringUtils.isBlank(reconRpt.getId().getCollectionDate())) {
//			reconRpt.getId().setCollectionDate(reconRpt.getId().getApplicationDate());
//		}
//		dao.saveOrUpdate(reconRpt);
	}
	
	private List<NicTransactionReconRpt> parseNicTransactionReconRptData(List<Object> reconRptDataList) {
		List<NicTransactionReconRpt> reconRptList = new ArrayList<NicTransactionReconRpt>(); 
//		int count=0;
//		for (Object rptDatarow: reconRptDataList) {
//			Object[] rptDataArr = (Object[]) rptDatarow;
//			if (rptDataArr.length==16) {
//				String regSiteCode = (String) rptDataArr[0];
//				String issSiteCode = (String) rptDataArr[1];
//				String applicationDate = (String) rptDataArr[2];
//				String collectionDate = (String) rptDataArr[3];
//				Long nicReceived 	= this.parseBigDecimalToLong(rptDataArr[4]);
//				Long nicRejected 	= this.parseBigDecimalToLong(rptDataArr[5]);
//				Long nicWip 		= this.parseBigDecimalToLong(rptDataArr[6]);
//				Long nicPersoSubmitted 		= this.parseBigDecimalToLong(rptDataArr[7]);
//				Long nicPersoRejected 		= this.parseBigDecimalToLong(rptDataArr[8]);
//				Long nicPersoPersonalized 	= this.parseBigDecimalToLong(rptDataArr[9]);
//				Long nicPersoCardDelivered 	= this.parseBigDecimalToLong(rptDataArr[10]);
//				Long nicPersoWip 			=  this.parseBigDecimalToLong(rptDataArr[11]);
//				Long nicCcricCardReceived 	= this.parseBigDecimalToLong(rptDataArr[12]);
//				Long nicCcricCardRejected 	= this.parseBigDecimalToLong(rptDataArr[13]);
//				Long nicCcricCardCollected 	= this.parseBigDecimalToLong(rptDataArr[14]);
//				Long nicCcricWip 			= this.parseBigDecimalToLong(rptDataArr[15]);
//				
//				NicTransactionReconRptId id = new NicTransactionReconRptId(regSiteCode, issSiteCode, applicationDate, collectionDate); 
//				NicTransactionReconRpt reconRptObj = new NicTransactionReconRpt(id);
//				reconRptObj.setNicReceived(nicReceived);
//				reconRptObj.setNicRejected(nicRejected);
//				reconRptObj.setNicWip(nicWip);
//				reconRptObj.setNicPersoSubmitted(nicPersoSubmitted);
//				reconRptObj.setNicPersoRejected(nicPersoRejected);
//				reconRptObj.setNicPersoPersonalized(nicPersoPersonalized);
//				reconRptObj.setNicPersoCardDelivered(nicPersoCardDelivered);
//				reconRptObj.setNicPersoWip(nicPersoWip);
//				reconRptObj.setNicCcricCardReceived(nicCcricCardReceived);
//				reconRptObj.setNicCcricCardRejected(nicCcricCardRejected);
//				reconRptObj.setNicCcricCardCollected(nicCcricCardCollected);
//				reconRptObj.setNicCcricWip(nicCcricWip);
//				reconRptList.add(reconRptObj);
//			} else {
//				logger.error("Unexpected Data return. Please check with Application Support.");
//			}
//			count++;
//		}
//		logger.info("[parseNicTransactionReconRptData] return {} data out of {} records.", new Object[] {reconRptList.size(), count});
		return reconRptList;
	}

	private Long parseBigDecimalToLong(Object number) {
		Long result = null;
		if (number instanceof BigDecimal) {
			if (number != null) {
				result = new Long(((BigDecimal) number).longValue());
			}
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateTransactionReconReportBySubsystem(List<NicTransactionReconRpt> reconRptList, String sourceSystemId, Date reportGenerationTime) 
		throws ReconReportServiceException {
		try {
			if (CollectionUtils.isNotEmpty(reconRptList)) {
				for (NicTransactionReconRpt reconRpt : reconRptList) {
					//load report from database 
					NicTransactionReconRpt reconRptDBO = this.findById(reconRpt.getId());
					if (reconRptDBO!=null) {
						logger.info("[updateReconReportBySubsystem][{}] existing report regSiteCode:{}, issSiteCode:{}, applicationDate:{} collectionDate:{} - update records"
								, new Object[] {sourceSystemId, reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate()});
						if (StringUtils.isNotBlank(reconRptDBO.getReconStatus())&& this.isReconReportStatusTally(reconRptDBO.getReconStatus())) {
							logger.warn("Recon Report already matched & tally, update is not required.");
							//continue; /* to uncomment after testing */
						}
						//update the corresponding fields by each subsystem
						this.updateReportFieldsBySubSystem(sourceSystemId, reconRptDBO, reconRpt);
						
						reconRptDBO.setUpdateBy(sourceSystemId);
						reconRptDBO.setUpdateDatetime(new Date());
					} else {
						//reconRptDBO = reconRpt;
						reconRptDBO = new NicTransactionReconRpt(reconRpt.getId());
						this.updateReportFieldsBySubSystem(sourceSystemId, reconRptDBO, reconRpt);
						
						reconRptDBO.setCreateBy(sourceSystemId);
						reconRptDBO.setCreateDatetime(new Date());
						reconRptDBO.setUpdateBy(sourceSystemId);
						reconRptDBO.setUpdateDatetime(new Date());
						logger.info("[updateReconReportBySubsystem] new report subSystemId:{}, regSiteCode:{}, issSiteCode:{}, applicationDate:{} collectionDate:{} - create records"
								, new Object[] {sourceSystemId, reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate()});
					}
					//then update reconStatus
					updateTransactionReconStatus(reconRptDBO);	
					
					//To refresh NIC Report Data
					if (!this.isReconReportStatusTally(reconRptDBO.getReconStatus())) {
						this.updateNicTransactionReconReportData(reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getApplicationDate(), reconRptDBO.getId().getCollectionDate());
					}
				}
			}
		} catch (IllegalAccessException e) {
			throw new ReconReportServiceException(e);
		} catch (InvocationTargetException e) {
			throw new ReconReportServiceException(e);
		}
	}
	
	private NicTransactionReconRpt updateReportFieldsBySubSystem(String sourceSystemId, NicTransactionReconRpt reconRptDBO, NicTransactionReconRpt reconRptSubSystem) throws IllegalAccessException, InvocationTargetException {
//		if (StringUtils.equals(sourceSystemId, SYSTEM_CCI) || StringUtils.equals(sourceSystemId, SYSTEM_RIC) || StringUtils.equals(sourceSystemId, SYSTEM_CC)) {
//			if (reconRptSubSystem.getCcricRegistered()!=null && reconRptSubSystem.getCcricUploaded()!=null && reconRptSubSystem.getCcricCancelled()!=null && reconRptSubSystem.getCcricPending()!=null) {
//				logger.info("[updateReconReportBySubsystem][before] subSystemId:{}, regSiteCode:{}, applicationDate:{}, result: registered-{}, uploaded-{}, cancelled-{}, pending-{}"
//						, new Object[] {sourceSystemId, reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getApplicationDate()
//						, reconRptDBO.getCcricRegistered(), reconRptDBO.getCcricUploaded(), reconRptDBO.getCcricCancelled(), reconRptDBO.getCcricPending()});
//				BeanUtils.copyProperty(reconRptDBO, "ccricRegistered", reconRptSubSystem.getCcricRegistered());
//				BeanUtils.copyProperty(reconRptDBO, "ccricUploaded", reconRptSubSystem.getCcricUploaded());
//				BeanUtils.copyProperty(reconRptDBO, "ccricCancelled", reconRptSubSystem.getCcricCancelled());
//				BeanUtils.copyProperty(reconRptDBO, "ccricPending", reconRptSubSystem.getCcricPending());
//				logger.info("[updateReconReportBySubsystem][ after] subSystemId:{}, regSiteCode:{}, applicationDate:{}, result: registered-{}, uploaded-{}, cancelled-{}, pending-{}"
//						, new Object[] {sourceSystemId, reconRptDBO.getId().getRegSiteCode(), reconRptDBO.getId().getApplicationDate()
//						, reconRptDBO.getCcricRegistered(), reconRptDBO.getCcricUploaded(), reconRptDBO.getCcricCancelled(), reconRptDBO.getCcricPending()}); 
//			}
//		}  
//		if (StringUtils.equals(sourceSystemId, SYSTEM_PERSO)) {
//			if (reconRptSubSystem.getPersoReceived()!=null && reconRptSubSystem.getPersoRejected()!=null && reconRptSubSystem.getPersoCardPacked()!=null && reconRptSubSystem.getPersoCardDelivered()!=null && reconRptSubSystem.getPersoWip()!=null) {
//				logger.info("[updateReconReportBySubsystem][before] subSystemId:{}, issSiteCode:{}, collectionDate:{}, result: received-{}, rejected-{}, cardPacked-{}, cardDelivered-{}, wip-{}"
//						, new Object[] {sourceSystemId, reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getCollectionDate()
//						, reconRptDBO.getPersoReceived(), reconRptDBO.getPersoRejected(), reconRptDBO.getPersoCardPacked(), reconRptDBO.getPersoCardDelivered(), reconRptDBO.getPersoWip()});
//				BeanUtils.copyProperty(reconRptDBO, "persoReceived", reconRptSubSystem.getPersoReceived());
//				BeanUtils.copyProperty(reconRptDBO, "persoRejected", reconRptSubSystem.getPersoRejected());
//				BeanUtils.copyProperty(reconRptDBO, "persoCardPacked", reconRptSubSystem.getPersoCardPacked());
//				BeanUtils.copyProperty(reconRptDBO, "persoCardDelivered", reconRptSubSystem.getPersoCardDelivered());
//				BeanUtils.copyProperty(reconRptDBO, "persoWip", reconRptSubSystem.getPersoWip());
//				logger.info("[updateReconReportBySubsystem][ after] subSystemId:{}, issSiteCode:{}, collectionDate:{}, result: received-{}, rejected-{}, cardPacked-{}, cardDelivered-{}, wip-{}"
//						, new Object[] {sourceSystemId, reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getCollectionDate()
//						, reconRptDBO.getPersoReceived(), reconRptDBO.getPersoRejected(), reconRptDBO.getPersoCardPacked(), reconRptDBO.getPersoCardDelivered(), reconRptDBO.getPersoWip()}); 
//			}
//		}  
//		if (StringUtils.equals(sourceSystemId, SYSTEM_CCI) || StringUtils.equals(sourceSystemId, SYSTEM_RIC) || StringUtils.equals(sourceSystemId, SYSTEM_CC)) {
//			if (reconRptSubSystem.getCciStockedIn()!=null && reconRptSubSystem.getCciRejected()!=null && reconRptSubSystem.getCciCollected()!=null && reconRptSubSystem.getCciCollectionFailed()!=null && reconRptSubSystem.getCciPendingCollection()!=null) {
//				logger.info("[updateReconReportBySubsystem][before] subSystemId:{}, issSiteCode:{}, collectionDate:{}, result: stockedIn-{}, rejected-{}, collected-{}, collectionFailed-{}, pendingCollection-{}"
//						, new Object[] {sourceSystemId, reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getCollectionDate()
//						, reconRptDBO.getCciStockedIn(), reconRptDBO.getCciRejected(), reconRptDBO.getCciCollected(), reconRptDBO.getCciCollectionFailed(), reconRptDBO.getCciPendingCollection()});
//				if (reconRptSubSystem.getCciPendingStockedIn()!=null) {
//					BeanUtils.copyProperty(reconRptDBO, "cciPendingStockedIn", reconRptSubSystem.getCciPendingStockedIn());
//				}
//				BeanUtils.copyProperty(reconRptDBO, "cciStockedIn", reconRptSubSystem.getCciStockedIn());
//				BeanUtils.copyProperty(reconRptDBO, "cciRejected", reconRptSubSystem.getCciRejected());
//				BeanUtils.copyProperty(reconRptDBO, "cciCollected", reconRptSubSystem.getCciCollected());
//				BeanUtils.copyProperty(reconRptDBO, "cciCollectionFailed", reconRptSubSystem.getCciCollectionFailed());
//				BeanUtils.copyProperty(reconRptDBO, "cciPendingCollection", reconRptSubSystem.getCciPendingCollection());
//				logger.info("[updateReconReportBySubsystem][ after] subSystemId:{}, issSiteCode:{}, collectionDate:{}, result: stockedIn-{}, rejected-{}, collected-{}, collectionFailed-{}, pendingCollection-{}"
//						, new Object[] {sourceSystemId, reconRptDBO.getId().getIssSiteCode(), reconRptDBO.getId().getCollectionDate()
//						, reconRptDBO.getCciStockedIn(), reconRptDBO.getCciRejected(), reconRptDBO.getCciCollected(), reconRptDBO.getCciCollectionFailed(), reconRptDBO.getCciPendingCollection()});
//			}
//		}	
		return reconRptDBO;
	}
	
	private boolean isReconReportStatusTally(String reconStatus) {
		boolean result = false;
		String overallStatus = StringUtils.substring(reconStatus, 4, 5);
		if (StringUtils.equals(overallStatus, RECON_STATUS_MATCHED)) {
			result = true;
		}
		return result;
	}

	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	public List<NicTransactionReconRpt> findAllTransactionReconReport(String regSiteCode,
			String issSiteCode, String applicationDate, String collectionDate, String sourceSystemId)
			throws ReconReportServiceException {
		List<NicTransactionReconRpt> resultList = null;
		try {
			logger.info("[findAllTransactionReconReport], regSiteCode:{}, issSiteCode:{}, applicationDate:{}, collectionDate:{}", new Object[] {regSiteCode, issSiteCode, applicationDate, collectionDate});
			resultList = dao.findAllReconReport(regSiteCode, issSiteCode, applicationDate, collectionDate);
			logger.info("[findAllTransactionReconReport], result size:{}", resultList==null?0:resultList.size());
		} catch (Exception e) {
			throw new ReconReportServiceException(e);
		}
		return resultList;
	}

	@Override
	public void updateCardReconReportBySubsystem(
			List<NicCardReconRpt> reconRptList, String sourceSystemId,
			Date reportGenerationTime) throws ReconReportServiceException {
		try {
			if (CollectionUtils.isNotEmpty(reconRptList)) {
				for (NicCardReconRpt reconRpt : reconRptList) {
					//load report from database 
					NicCardReconRpt reconRptDBO = this.nicCardReconRptDao.findById(reconRpt.getId());
					if (reconRptDBO!=null) {
						logger.info("[updateCardReconReportBySubsystem][{}] existing report siteCode:{}, reportCreateDate:{} - update records"
								, new Object[] {sourceSystemId, reconRptDBO.getId().getSiteCode(), reconRptDBO.getId().getReportCreateDate()});
						if (StringUtils.isNotBlank(reconRptDBO.getReconStatus())&& this.isCardReconReportStatusTally(reconRptDBO.getReconStatus())) {
							logger.warn("Recon Report already matched, update is not required.");
							//return;
						}
						//update the corresponding fields by each subsystem
						if (StringUtils.equals(sourceSystemId, SYSTEM_RIC) || StringUtils.equals(sourceSystemId, SYSTEM_CC) || StringUtils.equals(sourceSystemId, SYSTEM_CCI)) {
							if (reconRpt.getCciStockedIn()!=null && reconRpt.getCciRejected()!=null && reconRpt.getCciCollected()!=null && reconRpt.getCciCollectionFailed()!=null && reconRpt.getCciPendingCollection()!=null) {
								if (reconRpt.getCciPendingStockedIn()!=null) {
									BeanUtils.copyProperty(reconRptDBO, "cciPendingStockedIn", reconRpt.getCciPendingStockedIn());
								}
								BeanUtils.copyProperty(reconRptDBO, "cciStockedIn", reconRpt.getCciStockedIn());
								BeanUtils.copyProperty(reconRptDBO, "cciRejected", reconRpt.getCciRejected());
								BeanUtils.copyProperty(reconRptDBO, "cciCollected", reconRpt.getCciCollected());
								BeanUtils.copyProperty(reconRptDBO, "cciCollectionFailed", reconRpt.getCciCollectionFailed());
								BeanUtils.copyProperty(reconRptDBO, "cciPendingCollection", reconRpt.getCciPendingCollection());
								BeanUtils.copyProperty(reconRptDBO, "cciCardReturned", reconRpt.getCciCardReturned());
								logger.info("[updateCardReconReportBySubsystem] subSystemId:{}, siteCode:{}, reportCreateDate:{}, result: stockedIn-{}, rejected-{}, collected-{}, collectionFailed-{}, pendingCollection-{}, cardReturned-{}"
										, new Object[] {sourceSystemId, reconRptDBO.getId().getSiteCode(), reconRptDBO.getId().getReportCreateDate()
										, reconRptDBO.getCciStockedIn(), reconRptDBO.getCciRejected(), reconRptDBO.getCciCollected(), reconRptDBO.getCciCollectionFailed(), reconRptDBO.getCciPendingCollection(), reconRptDBO.getCciCardReturned()});
							}
						} else if (StringUtils.equals(sourceSystemId, SYSTEM_PERSO)) {
							if (reconRpt.getPersoReceived()!=null && reconRpt.getPersoRejected()!=null && reconRpt.getPersoCardPacked()!=null && reconRpt.getPersoCardDelivered()!=null && reconRpt.getPersoWip()!=null) {
								BeanUtils.copyProperty(reconRptDBO, "persoReceived", reconRpt.getPersoReceived());
								BeanUtils.copyProperty(reconRptDBO, "persoRejected", reconRpt.getPersoRejected());
								BeanUtils.copyProperty(reconRptDBO, "persoCardPacked", reconRpt.getPersoCardPacked());
								BeanUtils.copyProperty(reconRptDBO, "persoCardDelivered", reconRpt.getPersoCardDelivered());
								BeanUtils.copyProperty(reconRptDBO, "persoWip", reconRpt.getPersoWip());
								logger.info("[updateCardReconReportBySubsystem] subSystemId:{}, siteCode:{}, reportCreateDate:{}, result: received-{}, rejected-{}, cardPacked-{}, cardDelivered-{}, wip-{}"
										, new Object[] {sourceSystemId, reconRptDBO.getId().getSiteCode(), reconRptDBO.getId().getReportCreateDate()
										, reconRptDBO.getPersoReceived(), reconRptDBO.getPersoRejected(), reconRptDBO.getPersoCardPacked(), reconRptDBO.getPersoCardDelivered(), reconRptDBO.getPersoWip()}); 
							}
						} else if (StringUtils.equals(sourceSystemId, SYSTEM_INVENTORY)) {
							if (reconRpt.getInvCardReturned()!=null) {								
								BeanUtils.copyProperty(reconRptDBO, "invCardReturned", reconRpt.getInvCardReturned());
								logger.info("[updateCardReconReportBySubsystem] subSystemId:{}, siteCode:{}, reportCreateDate:{}, result: cardReturned-{}"
										, new Object[] {sourceSystemId, reconRptDBO.getId().getSiteCode(), reconRptDBO.getId().getReportCreateDate()
										, reconRptDBO.getInvCardReturned()});
							}
						}	
						reconRptDBO.setUpdateBy(sourceSystemId);
						reconRptDBO.setUpdateDate(new Date());
					} else {
						reconRptDBO = reconRpt;
						reconRptDBO.setCreateBy(sourceSystemId);
						reconRptDBO.setCreateDate(new Date());
						reconRptDBO.setUpdateBy(sourceSystemId);
						reconRptDBO.setUpdateDate(new Date());
						logger.info("[updateCardReconReportBySubsystem] new report subSystemId:{}, siteCode:{}, reportCreateDate:{} collectionDate:{} - create records"
								, new Object[] {sourceSystemId, reconRptDBO.getId().getSiteCode(), reconRptDBO.getId().getReportCreateDate()});
					}
					//then update reconStatus
					updateCardReconStatus(reconRptDBO);	
				}
			}
		} catch (IllegalAccessException e) {
			throw new ReconReportServiceException(e);
		} catch (InvocationTargetException e) {
			throw new ReconReportServiceException(e);
		}
	}
	
	/**
	 * The Card Recon Report Status. In format of PERSO, CARD Issuance, Inventory, Overall. (e.g. 3333)
	 * @param reconStatus
	 * @return
	 */
	private boolean isCardReconReportStatusTally(String reconStatus) {
		boolean result = false;
		String overallStatus = StringUtils.substring(reconStatus, 3, 4);
		if (StringUtils.equals(overallStatus, RECON_STATUS_MATCHED)) {
			result = true;
		}
		return result;
	}
	
	private void updateCardReconStatus(NicCardReconRpt reconRpt) {	
		String persoReconStatus = RECON_STATUS_MISSING;
		String cciReconStatus = RECON_STATUS_MISSING;
		String invReconStatus = RECON_STATUS_MISSING;
		String overallReconStatus = RECON_STATUS_MISSING; 
		if (reconRpt!=null) {
			//perso
			if (reconRpt.getPersoReceived()!=null && reconRpt.getPersoRejected()!=null && reconRpt.getPersoCardPacked()!=null && reconRpt.getPersoCardDelivered()!=null && reconRpt.getPersoWip()!=null) {
				persoReconStatus = RECON_STATUS_MATCHED;
				if (reconRpt.getPersoWip().longValue()!=0) {
					persoReconStatus = RECON_STATUS_PENDING;
				}
				if (reconRpt.getPersoReceived().longValue()!=reconRpt.getPersoRejected().longValue()+reconRpt.getPersoCardPacked().longValue()+reconRpt.getPersoCardDelivered().longValue()+reconRpt.getPersoWip().longValue()) {
					persoReconStatus = RECON_STATUS_UNMATCHED;
				}
			}
			//cci
			if (reconRpt.getCciStockedIn()!=null && reconRpt.getCciRejected()!=null && reconRpt.getCciCollected()!=null && reconRpt.getCciCollectionFailed()!=null && reconRpt.getCciPendingCollection()!=null) {
				cciReconStatus = RECON_STATUS_MATCHED;
				if (reconRpt.getCciPendingCollection().longValue()!=0) {
					cciReconStatus = RECON_STATUS_PENDING;
				}
				if (reconRpt.getCciStockedIn().longValue()!=reconRpt.getCciRejected().longValue()+reconRpt.getCciCollected().longValue()+reconRpt.getCciCollectionFailed().longValue()+reconRpt.getCciPendingCollection().longValue()) {
					cciReconStatus = RECON_STATUS_UNMATCHED;
				}
			}
			//inv
			if (reconRpt.getInvCardReturned()!=null) {
				invReconStatus = RECON_STATUS_MATCHED;
			}
			//overall
			if (reconRpt.getCciCardReturned()!=null && reconRpt.getInvCardReturned()!=null
					&& reconRpt.getPersoCardDelivered()!=null && reconRpt.getCciStockedIn()!=null) {
				overallReconStatus = RECON_STATUS_MATCHED;
				if (StringUtils.equals(persoReconStatus, RECON_STATUS_PENDING)
						|| StringUtils.equals(cciReconStatus, RECON_STATUS_PENDING)) {
					overallReconStatus = RECON_STATUS_PENDING;
				}
				if (reconRpt.getPersoCardDelivered().longValue()!=reconRpt.getCciStockedIn().longValue()) {
					overallReconStatus = RECON_STATUS_UNMATCHED;
				}
				if (reconRpt.getCciCardReturned().longValue()!=reconRpt.getInvCardReturned().longValue()) {
					overallReconStatus = RECON_STATUS_UNMATCHED;
				}
			}
		}
		//in format of PERSO,CCI,INVENTORY,OVERALL
		String reconStatus = persoReconStatus+cciReconStatus+invReconStatus+overallReconStatus;
		reconRpt.setReconStatus(reconStatus);
		this.nicCardReconRptDao.saveOrUpdate(reconRpt);
	}

	@Override
	public List<NicCardReconRpt> findAllCardReconReport(String siteCode,
			String reportCreateDate, String sourceSystemId)
			throws ReconReportServiceException {
		List<NicCardReconRpt> resultList = null;
		try {
			logger.info("[findAllCardReconReport], siteCode:{}, reportCreateDate:{}", new Object[] {siteCode, reportCreateDate});
			resultList = this.nicCardReconRptDao.findAllReconReport(siteCode, reportCreateDate);
			logger.info("[findAllCardReconReport], result size:{}", resultList==null?0:resultList.size());
		} catch (Exception e) {
			throw new ReconReportServiceException(e);
		}
		return resultList;
	}
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	public boolean exportTransaction(String inputDate) throws ReconReportServiceException {
		boolean exported = false;
		List<Object> resultList = null;
		try {
			logger.info("[getNicTransactionsByDate], inputDate:{}", new Object[] {inputDate});
			String filename = ""+inputDate+".csv";
			File locationFolder = new File(DEFAULT_FILE_LOCATION);
			if (!locationFolder.exists()) {
				locationFolder.mkdirs();
			}
			File csvFile = new File(locationFolder, filename);
			resultList = dao.getNicTransactionsByDate(inputDate);
			StringBuffer sb = new StringBuffer();
			for (Object datarow: resultList) {
				Object[] dataArr = (Object[]) datarow;
				if (dataArr!=null && dataArr.length==7) {//NIN,TRANSACTION_ID,TRANSACTION_STATUS,APPLICATION_DATE,COLLECTION_DATE,REG_SITE_CODE,ISS_SITE_CODE
					sb.append(dataArr[0]).append(",");
					sb.append(dataArr[1]).append(",");
					sb.append(dataArr[2]).append(",");
					sb.append(dataArr[3]).append(",");
					sb.append(dataArr[4]).append(",");
					sb.append(dataArr[5]).append(",");
					sb.append(dataArr[6]).append(System.getProperty("line.separator"));
				}
			}
			FileUtils.writeStringToFile(csvFile, sb.toString());
			logger.info("[getNicTransactionsByDate], result size:{}", resultList==null?0:resultList.size());
			exported = true;
			//delete failed ack if any
			try {
				filename = ""+inputDate+".f";
				locationFolder = new File(DEFAULT_ACK_LOCATION);
				File ackFile = new File(locationFolder, filename);
				if (ackFile.exists() && ackFile.isFile()) {
					ackFile.delete();
				}
			} catch (Exception e) {
				logger.info("fail to remove ack file on {}.", inputDate);
			}
		} catch (Exception e) {
			throw new ReconReportServiceException(e);
		} finally {
			if (!exported) {
				String filename = ""+inputDate+".csv";
				File locationFolder = new File(DEFAULT_FILE_LOCATION);
				File csvFile = new File(locationFolder, filename);
				if (csvFile.exists() && csvFile.isFile()) {
					csvFile.renameTo(new File(DEFAULT_ACK_LOCATION, inputDate+".err"));
				}
			}
		}
		return exported;
	}
	
	@Override
	public boolean isExported(String inputDate) throws ReconReportServiceException {
		boolean exported = false;
		try {
			//logger.info("[isExported], inputDate:{}", new Object[] {inputDate});
			String filename = ""+inputDate+".csv";
			File locationFolder = new File(DEFAULT_FILE_LOCATION);
			File exportFile = new File(locationFolder, filename);
			if (exportFile.exists() && exportFile.isFile()) {
				exported = true;
			} else {
				filename = ""+inputDate+".s";
				locationFolder = new File(DEFAULT_ACK_LOCATION);
				exportFile = new File(locationFolder, filename);
				if (exportFile.exists() && exportFile.isFile()) {
					exported = true;
				} else {
					filename = ""+inputDate+".f";
					locationFolder = new File(DEFAULT_ACK_LOCATION);
					exportFile = new File(locationFolder, filename);
					if (exportFile.exists() && exportFile.isFile()) {
						exported = false;
					}
				}
			}
			logger.info("[isExported], exported:{}, last.check.file:{} ", exported, filename);
		} catch (Exception e) {
			throw new ReconReportServiceException(e);
		} 
		return exported;
	}
}
