<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>

<div class="new-heading-2">THÔNG TIN CHI TIẾT DANH SÁCH BÁO MẤT/HỎNG</div>
<div class="col-md-12 col-md-12">
	<fieldset style="padding: 10px 10px;">
		<legend>Thông tin cá nhân</legend>
		<div class="col-md-4 col-sm-4">
			<div class="col-md-4 epp-form-group">Họ tên</div>
			<div class="col-md-8 epp-form-group"><input type="text" value="${HoSoXNC1.surnameLine1}" id="xnc_hoten" readonly="readonly" /> </div>
			<div class="col-md-4 epp-form-group">Ngày sinh</div>
			<div class="col-md-8 epp-form-group"><input type="text" value="${HoSoXNC1.dateOfBirth}" id="xnc_ngaysinh" readonly="readonly" /> </div>
			<div class="col-md-4">Giới tính</div>
			<div class="col-md-8"><input type="text" value="${HoSoXNC1.gender}" id="xnc_gt" readonly="readonly" /> </div>
		</div>
		<div class="col-md-4 col-sm-4">
			<div class="col-md-4 epp-form-group">Nơi sinh</div>
			<div class="col-md-8 epp-form-group"><input type="text" value="${HoSoXNC1.placeOfBirth}" id="xnc_noisinh" readonly="readonly" /> </div>
			<div class="col-md-4">Việt kiều</div>
			<div class="col-md-8"><input type="text" value="VN" id="xnc_vietkieu" readonly="readonly" /> </div>
		</div>
		<div class="col-md-4 col-sm-4">
			<div class="col-md-5 epp-form-group">QT hiện nay</div>
			<div class="col-md-7 epp-form-group"><input type="text" value="${HoSoXNC1.nationality}" id="xnc_QTHN" readonly="readonly" /> </div>
			<div class="col-md-5">QT gốc</div>
			<div class="col-md-7"><input type="text" value="VN" id="xnc_QTG" readonly="readonly" /> </div>
		</div>
	</fieldset>
	<fieldset style="padding: 10px 10px;">
		<legend>Thông tin mất / hủy</legend>
		<div class="col-md-9 col-sm-9" style="height: 200px;overflow: auto;">
	      	<table id="danhsachXNC" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
			  <thead>
			    <tr>
			      <th class="th-sm" style="max-width: 50px;">STT
			
			      </th>	
			      <th class="th-sm">Số CMND/CCCD
			
			      </th>
			      <th class="th-sm">Số Hộ chiếu
			
			      </th>
			      <th class="th-sm">Loại hộ chiếu
			
			      </th>
			      <th class="th-sm">Lý do báo mất/hủy
			
			      </th>
			      <th class="th-sm">Ngày tạo
			
			      </th>													     

			    </tr>
			  </thead>
				  <tbody>
				    <c:forEach items="${listXNC}" var="xnc_list">
				    	<c:set />
					    <tr>
					      	<td>${xnc_list.id}</td>
					      	<td>${xnc_list.nin}</td>
					      	<td>${xnc_list.prevPassportNo}</td>
					      	<td>${xnc_list.passportType}</td>
					      	<td>${xnc_list.nicRegistrationData.reasonKind}</td>
					      	<td>${xnc_list.createDatetime}</td>
				    	</tr>
				    </c:forEach>
				  </tbody>
			</table>
			<c:if test="${empty listXNC}">
			  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
			</c:if>
      </div>
      <div class="col-md-3 col-sm-3">
      	 <div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;margin-top: 10px;margin-left: 5px;">
			<div class="centerCaption">       
		         <div style="text-align:center;" id="anhMatXNC">	
		         							             									                 
					    <c:choose>
		                    <c:when test="${not empty photoXNC}">
		                        <div id="coAnhMat">
		                            <img src="data:image/jpg;base64,${photoXNC}"
		                                 class="img-border doGetAJpgSafeVersion" height="180" width="135" />
		
		                        </div>
		                    </c:when>
		                    <c:otherwise>
		                        <div id="khongAnhXNC">
		                      
		                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="180" width="135" title="Tham chiếu" />										                           
		                        </div>
		                    </c:otherwise>
		                </c:choose>									       					                       										                  								                    									               
		         </div>								        
		    </div>
	    </div>
      </div>
      <%-- <div class="col-md-9 col-md-9">
			<div class="epp-form-group">
				<div class="col-md-4">
					<div>Đ/C Việt Nam</div>
					<input type="text" value="" id="DCVNXNC"/>
				</div>
				<div class="col-md-4">
					<div>Đ/c Nước ngoài</div>
					<input type="text" value="" id="DCNNXNC"/>
				</div>
				<div class="col-md-4">
					<div>Nghề nghiệp</div>
					<input type="text" value="" id="ngheNghiepXNC"/>
				</div>
			</div>
			<div class="epp-form-group">
				<div class="col-md-3">
					<div>Loại HC</div>
					<input type="text" value="${HoSoXNC1.passportType}" id="LoaiHCXNC"/>
				</div>
				<div class="col-md-3">
					<div>Hạn HC</div>
					<input type="text" value="${HoSoXNC1.passportExpiredDate}" id="HanHCXNC"/>
				</div>
				<div class="col-md-3">
					<div>Nơi cấp HC</div>
					<input type="text" value="" id="noiCapHCXNC"/>
				</div>
				<div class="col-md-3">
					<div>Số trẻ em đi kèm</div>
					<input type="text" value="" id="soTreEmXNC"/>
				</div>
			</div>
			<div class="epp-form-group">
				<div class="col-md-3">
					<div>Ký hiệu thị thực</div>
					<input type="text" value="${HoSoXNC1.visaSymbol}" id="kyHieuTTXNC"/>
				</div>
				<div class="col-md-3">
					<div>Loại thị thực</div>
					<input type="text" value="${HoSoXNC1.visaType}" id="loaiTTXNC"/>
				</div>
				<div class="col-md-3">
					<div>Ngày thị thực</div>
					<input type="text" value="${HoSoXNC1.visaValidTo}" id="ngayTTXNC"/>
				</div>
				<div class="col-md-3">
					<div>Cơ quan đến</div>
					<input type="text" value="" id="coQuanDenXNC"/>
				</div>
			</div>
			<div class="epp-form-group">
				<div class="col-md-12">
					<div>Ghi chú</div>
					<input type="text" style="width: 100%;" value="" id="ghiChuXNC"/>														
				</div>
			</div>
	  </div>
	  <div class="col-md-3 col-md-3">
	  		<div class="epp-form-group">
				<div>Mục đích</div>
				<input type="text" value="${HoSoXNC1.purposeCode}" id="mucDichXNC"/>
	  		</div>
	  		<div class="epp-form-group">
				<div>Số phương tiện</div>
				<input type="text" value="" id="soPhuongTienXNC"/>
	  		</div>
	  		<div class="epp-form-group">
				<div>Tuyến đường</div>
				<input type="text" value="" id="tuyenDuongXNC"/>
	  		</div>
	  		<div class="epp-form-group">
				<div>KS viên</div>
				<input type="text" value="" id="kiemSatXNC"/>
	  		</div>
	  		<div class="epp-form-group">
				<div>GS viên</div>
				<input type="text" value="" id="giamSatXNC"/>
	  		</div>
	  </div> --%>
	</fieldset>
</div>	
					      
		
<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<script type="text/javascript">
$('#danhsachXNC').DataTable({
	"ordering": false
});	
	

</script>





