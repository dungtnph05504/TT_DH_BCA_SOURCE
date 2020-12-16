package com.nec.asia.nic.comp.listHandover.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPaymentDetaiService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.utils.HelperClass;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.PaymentDetail;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.CodeValuesDao;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.admin.code.dto.PaymentDefDTO;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.BaseDTOMapper;

/**
 * 
 * @author chris_wong
 * 
 */
/* 
 * Modification History:
 *  
 * 18 Aug 2013 (chris): add method to retrieve payment def for replacement transaction.
 * 26 Sep 2013 (chris): add BASIC_FEE_FOR_SC for senior citizen fix rate.
 * 06 Dec 2013 (chris): change reduce amount=TOTAL-BASIC_FEE_FOR_SC instead of return BASIC_FEE_FOR_SC 
 * 19 Aug 2014 (chris): to fix reduceAmount = 0 if ReduceRateFlag is false.
 */

@Service("listHandoverService")
public class ListHandoverServiceImpl extends
		DefaultBusinessServiceImpl<NicListHandover, String, ListHandoverDao>
		implements ListHandoverService {
	@Autowired
	private ParametersDao parametersDao;
	
	@Autowired
	private CodeValuesDao codeValuesDao;
	
	@Autowired
	private NicTransactionPackageService nicTransactionPackageService;
	
	@Autowired
	private NicTransactionService nicTransactionService;
	
	@Autowired
	private NicTransactionPaymentDetaiService nicTransactionPaymentDetaiService;
	
	@Autowired
	private TransDTOMapper mapper;
	
	@Autowired
	private EppListHandoverDetailService eppListHandoverDetailService;
	
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public ListHandoverServiceImpl() {
	}

	@Autowired
	public ListHandoverServiceImpl(ListHandoverDao dao) {
		this.dao = dao;
	}
	
	public BaseModelList<NicListHandover> findAllByPackageId(NicListHandoverId id){
		try {
			return dao.findListHandoverByPackageId(id, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new BaseModelList<NicListHandover>(null, false, CreateMessageException.createMessageException(e) + " - findAllByPackageId - " + id.getPackageId() + " - thất bại.");
		}
	}
	
	public PaginatedResult<NicListHandover> findAllHandoverList(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception{
		PaginatedResult<NicListHandover> pageResult = new PaginatedResult<NicListHandover>();
		
		pageResult = dao.findAllListHandover(pageNo, pageSize, assignmentFilter);
		return pageResult;
	}
	
	/* update payment matrix */
	//public boolean updatePaymentMatrix(PaymentDef paymentDef, String userId, String workstationId);
	
	/* update payment matrix delete flag */
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public boolean createRecordHandover(NicListHandover hdvor){
			try {
				return dao.createHandoverList(hdvor);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public int createHandoverPackage(MainTransaction mainTransaction){
			int result = -1;
			try {
				BaseModelList<NicListHandover> listH = this.findAllByPackageId(new NicListHandoverId(mainTransaction.getPackageID(), NicListHandover.TYPE_LIST_A));
				if(listH.getListModel() == null || listH.getListModel().size() <= 0){
					NicListHandover newH = new NicListHandover();
					newH.setCreateBy(mainTransaction.getCreateByhA());
					newH.setCreateDate(new Date());
					//newH.setDescription("Tạo mới qua đồng bộ dữ liệu");
					newH.setPackageName("Danh sách đồng bộ " + mainTransaction.getRegSiteCode() + " ngày "+ new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
					newH.setId(new NicListHandoverId(mainTransaction.getPackageID(), NicListHandover.TYPE_LIST_A));
//					newH.setPackageId(mainTransaction.getPackageID());
					//newH.setTransactionId(mainTransaction.getTransactionID());
//					newH.setTypeList(NicListHandover.TYPE_LIST_A); //Dành cho danh sách đồng bộ từ Địa phương
					newH.setCountTransaction(mainTransaction.getCountTrans());
					newH.setSiteCode(mainTransaction.getRegSiteCode());
					this.createRecordHandover(newH);
					logger.info("Create handover success ==== " + mainTransaction.getPackageID());
					result = -2;
				}
				else
				{
					/*NicListHandover updateH = listH.get(0);
					NicListHandoverDetail detail = nicTransactionPackageService.getListPackageByPackageIdAndTranID(updateH.getPackageId(), mainTransaction.getTransactionID());
					if(!updateH.getTransactionId().contains(mainTransaction.getTransactionID()))
					{
						updateH.setUpdateBy(mainTransaction.getCreateByhA());
						updateH.setUpdateDate(new Date());
						String text = updateH.getTransactionId();
						updateH.setTransactionId(text + "," + mainTransaction.getTransactionID());
						this.createRecordHandover(updateH);
						
						logger.info("Update handover success ==== " + mainTransaction.getPackageID());
					}
					result = -3;*/
				}
				
				//Thêm vào bảng package trung gian
				EppListHandoverDetail tran = new EppListHandoverDetail();
				tran.setPackageId(mainTransaction.getPackageID());
				tran.setTransactionId(mainTransaction.getTransactionID());
				eppListHandoverDetailService.insertDataTable(tran);
				logger.info("create transaction package success ==== " + mainTransaction.getPackageID() + "- [tranid: " + mainTransaction.getTransactionID());
				result = -4;
				
				NicTransaction nicTran = nicTransactionService.getNicTransactionById(mainTransaction.getTransactionID()).getModel();
				if(nicTran != null){
					//nicTran.setpackageID(mainTransaction.getPackageID());
					nicTran.setIsEpassport(mainTransaction.getPassportStyle().equals("Y") ? true : false);
					nicTran.getNicRegistrationData().setMotherLost(mainTransaction.getRegistrationData().getMotherLost());					
					nicTran.getNicRegistrationData().setFatherLost(mainTransaction.getRegistrationData().getFatherLost());
					nicTransactionService.saveOrUpdate(nicTran);
				}
				logger.info("update transaction success ==== [tranid: " + mainTransaction.getTransactionID());
				result = -5;
			
				List<PaymentDetail> list = mainTransaction.getRegistrationData().getPaymentInfo().getPaymentDetail();//nicTransactionPaymentDetaiService.findListDetailPaymentList(paymentId);
				if(list != null && list.size() > 0){
					for(PaymentDetail detail : list){
						nicTransactionPaymentDetaiService.saveOrUpdatePaymentDetail(mapper.parseTransactionPaymentDetailDataDTO(detail));
					}
				}
				logger.info("create list payment detail success ==== [tranid: " + mainTransaction.getTransactionID() + "]");
				result = 1;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
	}
/*	@Autowired
	public ListHandoverServiceImpl(ListHandoverDao dao) {
		this.dao = dao;
	}*/

	@Override
	public PaginatedResult<NicUploadJobDto> findListHandoverNoAssign(String[] arraySite,
			String userAssign, String siteCode, int typeList, int pageNo,
			int pageSize, String createDate, String endDate) {
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		Date date1 = !StringUtils.isEmpty(createDate) ? HelperClass.convertStringToDate1(createDate) : null;
		Date date2 = !StringUtils.isEmpty(endDate) ? HelperClass.convertStringToDate1(endDate) : null;
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(date2);
		eCal.add(Calendar.DATE, 1);
		Date eDate2 = eCal.getTime();
		PaginatedResult<NicListHandover> result = dao.findListHandoverNoAssign(arraySite, userAssign, siteCode, typeList, pageNo, pageSize, date1, eDate2);
		
		try {
			if(result != null && result.getRows().size() > 0){
				int i = 0;
				for(NicListHandover record : result.getRows()){
					i++;
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setPackageId(record.getId().getPackageId());
					dto.setNumberTran(String.valueOf(record.getCountTransaction()));
					dto.setRicName(record.getSiteCode());
					dto.setStt(i);
					list.add(dto);
				}
				return new PaginatedResult<>(result.getTotal(), pageNo, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<>(0, pageNo, list);

	}

	@Override
	public void listHandoverUpdateAssignJob(String[] packageId, int typeList,
			String siteCode, String[] listAssign, String userId) {
		try {
			for(int i = 0; i < packageId.length; i++){
				dao.listHandoverUpdateAssignJob(packageId[i], typeList, siteCode, listAssign, userId);				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public NicListHandover findListHandoverByOrther(String packageId,
			int typeList) {
		try {
			List<NicListHandover> listHandover = dao.findListHandoverByOrther(packageId, typeList);
			if(listHandover != null && listHandover.size() > 0)
				return listHandover.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public NicListHandover findListHandoverByOrther1(String packageId,
			String typeList) throws Exception {
		try {
			List<NicListHandover> listHandover = dao.findListHandoverByOrther1(packageId, typeList);
			if(listHandover != null && listHandover.size() > 0)
				return listHandover.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(CreateMessageException.createMessageException(e) + "findListHandoverByOrther1 false");
		}
		return null;
	}
	@Override
	public String getNUpdateCodeValueFromCodeId(String codeId) {
		try {
			String countId = dao.getNUpdateCodeValueFromCodeId(codeId);
			return countId;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  null;
	}

	@Override
	public NicListHandover findHandoverByTransactionId(String transactionId) {
		try {
			NicListHandover handover = dao.findHandoverByTransactionId(transactionId);
			return handover;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  null;
	}

	@Override
	public BaseModelSingle<NicListHandover> findByPackageId(NicListHandoverId id) {
		try {
			BaseModelList<NicListHandover> listH = dao.findListHandoverByPackageId(id, null);
			if(listH.getListModel() != null && listH.getListModel().size() > 0){
				return new BaseModelSingle<NicListHandover>(listH.getListModel().get(0), listH.isError(), listH.getMessage());
			}else return new BaseModelSingle<NicListHandover>(null, listH.isError(), listH.getMessage());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new BaseModelSingle<NicListHandover>(null, false,  CreateMessageException.createMessageException(new Exception(e)) + " - findByPackageId: " + id.getPackageId() + " - thất bại.");
		}
	}
	
	@Override
	public BaseModelSingle<NicListHandover> findHandoverByCriteria(
			String packageId, String type, Integer status) throws Exception{
		
		try {
			return dao.findHandoverByCriteria(packageId, type, status);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicListHandover>(null, false, CreateMessageException.createMessageException(e) + " - findHandoverByCriteria - " + packageId + " - thất bại.");
		}
	}

	@Override
	public NicListHandover findHandoverByTransactionId(String transactionId,
			int type, Integer status, Boolean stage) {
		try {
			NicListHandover handover = null; //dao.findHandoverByTransactionId(transactionId, type, status, stage);
			return handover;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return  null;
	}

	@Override
	public BaseModelList<NicListHandover> findAllHandoverByTransactionId(
			String transactionId, String type, Integer status, Boolean stage) {
		try {
			return dao.findAllHandoverByTransactionId(transactionId, type, status, stage);
			
		} catch (Exception e) {
			return new BaseModelList<NicListHandover>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findAllHandoverByTransactionId - "
							+ transactionId + " - thất bại.");
		}
	}
	
	@Override
	public List<CountPassport> countHandoverAProcess(String datefrom) throws Exception {

		List<CountPassport> result = null;
		try {
			result =  dao.countHandoverAProcess(datefrom);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public List<CountPassport> countHandoverA(String datefrom) throws Exception {
		List<CountPassport> result = null;
		try {
			result = dao.countHandoverA(datefrom);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public List<String> listTransactionA(String pack) throws Exception {
		List<String> result = null;
		try {
			result = dao.listTransactionA(pack);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public BaseModelSingle<Void> saveHandover(NicListHandover handover)
			throws Exception {
		try {
			return this.dao.saveHandover(handover);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<Void>(null, false,  CreateMessageException.createMessageException(new Exception(e)) + " - saveHandover: packageId = " + handover.getId().getPackageId() + " - thất bại.");
		}	
	}

	@Override
	public List<NicListHandover> findListHandoverByCriteria(String packageId,
			String typeList, Integer status) {
		try {
			return this.dao.findListHandoverByCriteria(packageId, typeList, status);
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateHandover(
			NicListHandover hanCheck) {
		try {
			return this.dao.saveOrUpdateHandover(hanCheck);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(e)) + " - saveOrUpdateHandover: packageId = " + hanCheck.getId().getPackageId() + " - thất bại.");
		}
	}
}