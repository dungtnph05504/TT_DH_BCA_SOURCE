package com.nec.asia.nic.comp.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.InventoryRefService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/**
 * The job to reprocess sync dispatch info to inventory.
 * 
 * @author khang
 * 
 * Modification History:
 * 20 May 2016 (khang): update logic to send inventory update status one by one
 */
public class InventoryReprocessSyncJob extends QuartzJobBean implements StatefulJob {

	private static final Logger logger = LoggerFactory.getLogger(InventoryReprocessSyncJob.class);

	DocumentDataService documentDataService = (DocumentDataService) SpringServiceManager.getBean("documentDataService");
	InventoryRefService inventoryRefService = (InventoryRefService) SpringServiceManager.getBean("inventoryRefService");
	NicTransactionService nicTransactionService = (NicTransactionService) SpringServiceManager.getBean("nicTransactionService");

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		logger.info("-----------------InventoryReprocessSyncJob begins----------------------");
		try {
			List<NicDocumentData> documentDataList = documentDataService.findAllSyncErrorDocuments(200);
/*			if (CollectionUtils.isNotEmpty(documentDataList)) {
				logger.debug("----------------- Number of  ready to sync records: " + documentDataList.size() + " ----------------------");
				Map<String, List<String>> documentMap = new HashMap<>();
				for (NicDocumentData documentData : documentDataList) {
					NicTransaction transaction = nicTransactionService.findById(documentData.getId().getTransactionId());
					if (transaction != null) {
						String siteCode = transaction.getIssSiteCode();
						if (StringUtils.isNotBlank(siteCode)) {
							List<String> documentNoList = null;
							if (documentMap.containsKey(siteCode)) {
								documentNoList = documentMap.get(siteCode);
							} else {
								documentNoList = new ArrayList<>();
							}

							documentNoList.add(documentData.getId().getPassportNo());
							documentMap.put(siteCode, documentNoList);
						}
					}
				}

				// Sync dispatch status to Inventory
				for (Map.Entry<String, List<String>> entry : documentMap.entrySet()) {
					logger.debug("----------------- Re-Sync dispatch status for site: " + entry.getKey() + " ----------------------");
					boolean update = inventoryRefService.updateDispatchedStatus(entry.getValue(), entry.getKey(), StringUtils.EMPTY);

					// Update Sync Status for document data
					for (String documentNo : entry.getValue()) {
						NicDocumentData documentData = documentDataService.findByDocNumber(documentNo);
						if (update) {
							documentData.setSyncStatus("Y");
						} else {
							documentData.setSyncStatus("E");
						}
						documentData.setLastSyncDatetime(new Date());
						documentDataService.saveOrUpdate(documentData);
					}
				}
			}*/
			
			if (CollectionUtils.isNotEmpty(documentDataList)) {
				logger.debug("----------------- Number of  ready to re-sync records: " +documentDataList.size()+" ----------------------");
				for (NicDocumentData documentData : documentDataList) {
					NicTransaction transaction = nicTransactionService.findById(documentData.getId().getTransactionId());
					if (transaction != null) {
						String siteCode = transaction.getIssSiteCode();
						if (StringUtils.isNotBlank(siteCode)) {
							List<String> updateDipatchedDoc = new ArrayList<>();
							updateDipatchedDoc.add(documentData.getId().getPassportNo());
							boolean update = inventoryRefService.updateDispatchedStatus(updateDipatchedDoc, siteCode, StringUtils.EMPTY);
							
							if (update) {
								documentData.setSyncStatus("Y");
							} else {
								documentData.setSyncStatus("E");
							}
							documentData.setLastSyncDatetime(new Date());
							documentDataService.saveOrUpdate(documentData);
						}
					}
				}
			}
		} catch (Throwable th) {
			logger.error("[InventoryReprocessSyncJob] Exception:" + th.getMessage(), th);
			throw new JobExecutionException("Error encountered Update Perso Status job:" + th.getMessage());
		}
		logger.info("----------------------------------------------------------------");
		logger.info("--------- InventoryReprocessSyncJob Completed --------------------");
		logger.info("----------------------------------------------------------------");
	}

}
