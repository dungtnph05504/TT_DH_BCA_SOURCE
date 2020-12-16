package service.perso.bussiness;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import service.perso.model.PassportInfo;
import service.perso.model.PropertyModel;
import service.perso.model.ResponseBase;
import service.perso.model.XLHoSoInfo;
import service.perso.util.Contants;

import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;


@Path("/report/")
@WebService
@Provider
public class ReportService {
	
	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;
	
	@Autowired
	private TransactionLogService nicTransactionLogService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private DocumentDataDao documentDataDao;

	@Autowired
	private DocumentDataService documentDataService;
	
	@Autowired
	private RptStatisticsTransmitDataService rptService;
	
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/reportxlhs/{yearnow}")
	public ResponseBase<List<XLHoSoInfo>> listALLXLHS(@PathParam("yearnow") int yearnow) {
		

		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		ResponseBase<List<XLHoSoInfo>> rep = new ResponseBase<List<XLHoSoInfo>>();
		List<XLHoSoInfo> listXLHS = new ArrayList<XLHoSoInfo>();
		try {
			//Không được cấp
			XLHoSoInfo infoN = new XLHoSoInfo();
			infoN.setName("Không được cấp");
			infoN.setType("column");
			int[] dataN = new int[2];
			List<NicUploadJob> yearNowN = uploadJobService.getListByStatusAndYear(new String[]{NicUploadJob.RECORD_STATUS_REJECTED}, yearnow, 1);
			List<NicUploadJob> yearBeforeN = uploadJobService.getListByStatusAndYear(new String[]{NicUploadJob.RECORD_STATUS_REJECTED}, yearnow, 0);
			if(yearNowN != null){
				dataN[0] = yearNowN.size();
			}else{
				dataN[0] = 0;
			}
			if(yearBeforeN != null){
				dataN[1] = yearBeforeN.size();
			}else{
				dataN[1] = 0;
			}
			infoN.setData(dataN);
			listXLHS.add(infoN);
			//end
			
			//Đã được cấp
			XLHoSoInfo infoY = new XLHoSoInfo();
			infoY.setName("Đã cấp");
			infoY.setType("column");
			int[] dataY = new int[2];
			List<NicUploadJob> yearNowY = uploadJobService.getListByStatusAndYear(new String[]{NicUploadJob.RECORD_STATUS_APPROVE_PERSO}, yearnow, 1);
			List<NicUploadJob> yearBeforeY = uploadJobService.getListByStatusAndYear(new String[]{NicUploadJob.RECORD_STATUS_APPROVE_PERSO}, yearnow, 0);
			if(yearNowY != null){
				dataY[0] = yearNowY.size();
			}else{
				dataY[0] = 0;
			}
			if(yearBeforeY != null){
				dataY[1] = yearBeforeY.size();
			}else{
				dataY[1] = 0;
			}
			infoY.setData(dataY);
			listXLHS.add(infoY);
			//end
			
			//Đang xử lý
			XLHoSoInfo infoC = new XLHoSoInfo();
			infoC.setName("Đang xử lý");
			infoC.setType("column");
			int[] dataC = new int[2];
			List<NicUploadJob> yearNowC = uploadJobService.getListByStatusAndYear(new String[]{NicUploadJob.RECORD_STATUS_INITIAL, NicUploadJob.RECORD_STATUS_IN_PROGRESS, NicUploadJob.RECORD_STATUS_COMPLETED}, yearnow, 1);
			List<NicUploadJob> yearBeforeC = uploadJobService.getListByStatusAndYear(new String[]{NicUploadJob.RECORD_STATUS_INITIAL, NicUploadJob.RECORD_STATUS_IN_PROGRESS, NicUploadJob.RECORD_STATUS_COMPLETED}, yearnow, 0);
			if(yearNowC != null){
				dataC[0] = yearNowC.size();
			}else{
				dataC[0] = 0;
			}
			if(yearBeforeC != null){
				dataC[1] = yearBeforeC.size();
			}else{
				dataC[1] = 0;
			}
			listXLHS.add(infoC);
			//end
			rep.setResponse(listXLHS);
			prModel.setCode(99);
			prModel.setMessage("");
			
			/* Lưu bảng thống kê truyền nhận*/
			this.saveOrUpRptData(Contants.URL_LIST_ALL_XLHS, listXLHS.size(), null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}

		rep.setProperty(prModel);
		return rep;
	}
	//save or update rptData
			private void saveOrUpRptData(String type, int count, String siteCode){
				try {
					RptStatisticsTransmitData rptData = rptService.findSingerByConditions(type, siteCode, DateUtil.strToDate(com.nec.asia.nic.utils.HelperClass.convertDateType3(new Date()), DateUtil.FORMAT_YYYYMMDD));
					if (rptData != null) {
						rptData.setTotal(rptData.getTotal() + count);
					} else {
						rptData = new RptStatisticsTransmitData();
						rptData.setRptDate(DateUtil.strToDate(com.nec.asia.nic.utils.HelperClass.convertDateType3(new Date()), DateUtil.FORMAT_YYYYMMDD));
						rptData.setTotal(count);
						rptData.setType(type);
						rptData.setSiteCode(siteCode);
					}
					rptData.setUpdateDatetime(new Date());
					rptService.saveOrUpdateData(rptData);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
}
