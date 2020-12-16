package com.nec.asia.nic.framework.admin.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.CodeValuesDao;
import com.nec.asia.nic.framework.admin.code.dao.ProofDocumentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDefId;
import com.nec.asia.nic.framework.admin.code.dto.ProofDocumentDefDTO;
import com.nec.asia.nic.framework.admin.code.service.ProofDocumentDefService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * 
 * @author chris_wong
 * 
 */
/*
 * Modification History:
 *  * 
 * 2 Oct 2013 (Peddi Swapna): Modified getAllForPagination method to implement the external sorting.
 * 
 */
@Service("proofDocumentDefService")
public class ProofDocumentDefServiceImpl
		extends
		DefaultBusinessServiceImpl<ProofDocumentDef, ProofDocumentDefId, ProofDocumentDefDao>
		implements ProofDocumentDefService {
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public ProofDocumentDefServiceImpl() {
	}

	@Autowired
	public ProofDocumentDefServiceImpl(ProofDocumentDefDao dao) {
		this.dao = dao;
	}

	public List<ProofDocumentDef> findAllByTransType(String transactionType, String transactionSubtype) {
		return dao.findProofDocumentDefByTransType(transactionType, transactionSubtype);
	}
	@Autowired
	private CodeValuesDao codeValuesDao;
	
	@Override
	public PaginatedResult<ProofDocumentDefDTO> findAllForPagination(int pageNo, int pageSize)
			throws Exception {
		List<ProofDocumentDefDTO> joblstDto = new ArrayList<ProofDocumentDefDTO>();
		PaginatedResult<ProofDocumentDef>  pr = null;
		PaginatedResult<ProofDocumentDefDTO>  pageResult = new PaginatedResult<ProofDocumentDefDTO>();
		try{
			ProofDocumentDef proofDocumentDef = new ProofDocumentDef();
			pr = dao.findAllForPagination(proofDocumentDef, pageNo, pageSize);//dao.findAllInvestigationForPagination(nicUploadJob, pageNo, pageSize);
			if(pr.getRows()!=null){
				List<ProofDocumentDef> jobList = pr.getRows();
				for(ProofDocumentDef record : jobList){
					logger.info("Document Id=============>>>>>>>>>>>>>>>>>>>"+record.getId().getDocumentId());
					ProofDocumentDefDTO dto = new ProofDocumentDefDTO();
					
					dto.setDocumentId(record.getId().getDocumentId());
					dto.setDocumentDesc(record.getDocumentDesc());
					dto.setTransactionType(record.getId().getTransactionType());
					dto.setTransactionSubtype(record.getId().getTransactionSubtype());
					dto.setCreateBy(record.getCreateBy());
					dto.setCreateDate(record.getCreateDate());
					dto.setUpdateBy(record.getUpdateBy());
					dto.setUpdateDate(record.getUpdateDate());
					dto.setRequireIndicator(record.getRequireIndicator());
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		}catch(Exception ex){
			logger.error("Error occurred while getting the proof document list. Exception: "+ex.getMessage());
		}
		return pageResult;
	}

	/**
	 * To retrieve all the proof document matrix and sort by transaction type, subtype and required indicator.
	 */
	@Override
	public Map<String, Object> findProofDocumentDefForMatrix() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		/*
		 * 1) Get all proof document from proof_document_def
		 * 2) Get all Transaction Type and Transaction SubType from code_value order by priority
		 * 3) Prepare the 2nd Column Name List for the matrix (Transaction SubType)
		 * 4) Prepare the 1st Column Name List for the matrix (Transaction Type)
		 * 5) Prepare the Row Name List for the matrix (Document Name)
		 * 6) Prepare the Contents of require indicator
		 */
		//String[] document = {BIRTH_CERT,MAR_CERT,ADDR_PROOF_DOC,PAR_BIRTH_CERT} 
		//String[] transactionType = {"REG", "REP", "PAR_UPD", "CON"};
		//String[] transactionSubType = {"REG_CITIZEN", "REG_DESCENT", "REP", "PAR_UPD", "CON"}; 
		//String[][] requiredIndicatorMatrix
		List<String> documentIdList = new ArrayList<String>();
		List<String> typeKeyList = new ArrayList<String>();
		Map<String, List<String>> transactionTypeMap = new HashMap<String, List<String>>();
		List<ProofDocumentDef> proofDocumentList = dao.findAllDistinctProofDocuments();
		for (ProofDocumentDef proofDocument : proofDocumentList) {
			String documentId = proofDocument.getId().getDocumentId();
			String transactionType = proofDocument.getId().getTransactionType();
			String transactionSubtype = proofDocument.getId().getTransactionSubtype();			
			String typeKey = transactionType+","+transactionSubtype;
			if (!documentIdList.contains(documentId)) {
				documentIdList.add(documentId);
			}
			if (!typeKeyList.contains(typeKey)) {
				typeKeyList.add(typeKey);
			}
			if (transactionTypeMap.get(transactionType)==null) {
				transactionTypeMap.put(transactionType, new ArrayList<String>());
			} 
			if (!transactionTypeMap.get(transactionType).contains(transactionSubtype)) {
				transactionTypeMap.get(transactionType).add(transactionSubtype);
			}
		}
		//To populate requireIndicator matrix
		int rowSize = documentIdList.size();
		int colSize = typeKeyList.size();
		String[][] requiredIndicatorMatrix = new String[rowSize][colSize];
		for (int i=0; i<rowSize; i++) {
			for (int j=0; j<colSize; j++) {
				requiredIndicatorMatrix[i][j] = "";
			}
		}
		for (ProofDocumentDef proofDocument : proofDocumentList) {
			String documentId = proofDocument.getId().getDocumentId();
			String transactionType = proofDocument.getId().getTransactionType();
			String transactionSubtype = proofDocument.getId().getTransactionSubtype();			
			String typeKey = transactionType+","+transactionSubtype;
			String requireIndicator = proofDocument.getRequireIndicator();
			
			int rowIndex = this.getObjectIndex(documentIdList, documentId);
			int colIndex = this.getObjectIndex(typeKeyList, typeKey);
			if (rowIndex!=-1 && colIndex!=-1) {
				requiredIndicatorMatrix[rowIndex][colIndex] = requireIndicator;
			}
		}
		
		resultMap.put("documentIdList", documentIdList);
		resultMap.put("typeKeyList", typeKeyList);
		resultMap.put("transactionTypeMap", transactionTypeMap);
		resultMap.put("requiredIndicatorMatrix", requiredIndicatorMatrix);
		return resultMap;
	}
	
	private int getObjectIndex(List<String> sourceList, String sourceObject) {
		for (int i=0; i< sourceList.size(); i++) {
			if (StringUtils.equals(sourceList.get(i), sourceObject)) {
				return i;
			}
		}
		return -1;
	}
	public List<CodeValues> getCodeValue(String codeId){
		
		List<CodeValues> codeVaList  =codeValuesDao.findAllByCodeId(codeId);
		
		return codeVaList;
	}
	public String updateProofDocMatrix(ProofDocumentDef proofDocumentDef){
		String status="";
		try{
			ProofDocumentDefId proofDocId = new ProofDocumentDefId();
			proofDocId.setDocumentId(proofDocumentDef.getDocumentId());
			proofDocId.setTransactionType(proofDocumentDef.getTransactionType());
			proofDocId.setTransactionSubtype(proofDocumentDef.getTransactionSubtype());			
			ProofDocumentDef proofDocEntity =dao.findById(proofDocId);		
			proofDocEntity.setDocumentDesc(proofDocumentDef.getDocumentDesc());
			proofDocEntity.setRequireIndicator(proofDocumentDef.getRequireIndicator());
			proofDocEntity.setUpdateBy(proofDocumentDef.getUpdateBy());
			proofDocEntity.setUpdateDate(proofDocumentDef.getUpdateDate());
			proofDocEntity.setUpdateWkstnId(proofDocumentDef.getUpdateWkstnId());
			dao.saveOrUpdate(proofDocEntity);
		
			status="success";
		}catch(HibernateException exp){
			logger.error("Error occurred while update the proof document. Exception: "+exp.getMessage());
			status="fail";
		}
		
		return status;
	}
	
	public String deleteProofDocMatrix(ProofDocumentDef proofDocumentDef){
		String status="";
		try{
			ProofDocumentDefId proofDocId = new ProofDocumentDefId();
			proofDocId.setDocumentId(proofDocumentDef.getDocumentId());
			proofDocId.setTransactionType(proofDocumentDef.getTransactionType());
			proofDocId.setTransactionSubtype(proofDocumentDef.getTransactionSubtype());			
			ProofDocumentDef proofDocEntity = dao.findById(proofDocId);		
			proofDocEntity.setDeleteBy(proofDocumentDef.getDeleteBy());
			proofDocEntity.setDeleteDate(proofDocumentDef.getDeleteDate());
			proofDocEntity.setDeleteWkstnId(proofDocumentDef.getDeleteWkstnId());
			proofDocEntity.setDeleteFlag("Y");
			dao.saveOrUpdate(proofDocEntity);
		
			status="success";
		}catch(HibernateException exp){
			logger.error("Error occurred while delete the proof document. Exception: "+exp.getMessage());
			status="fail";
		}
		
		return status;
	}
	
	public PaginatedResult<ProofDocumentDef> getAllForPagination(int pageNo,int pageSize,Order... order){
		
		PaginatedResult<ProofDocumentDef> resultSet=dao.getAllForPagination(pageNo, pageSize, order) ;	
		return resultSet;
	}
	public List<ProofDocumentDef>  findAllDistinctProofDocuments(){
		List<ProofDocumentDef> distnictList = dao.findAllDistinctProofDocuments();
		return distnictList;
	}
}