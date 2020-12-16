<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>

</style>

		<fieldset>
			<legend>Thông tin cá nhân</legend>
			<div class="col-md-4 col-sm-4">
				<div>Họ tên: <label id="hoTen0">${HSTrung1.fullName_O}</label></div>
				<div>CMND/CCCD: <label id="chungMinh0">${HSTrung1.nin_O}</label></div>
				<div>Dân tộc: <label id="danToc0">${HSTrung1.nation_O}</label></div>
			</div>
			<div class="col-md-4 col-sm-4">
				<div>Giới tính: <label id="gioiTinh0">${HSTrung1.gender_O}</label></div>
				<div>Ngày cấp: <label id="ngayCap0">${HSTrung1.dateNin_O}</label></div>
				<div>Tôn giáo: <label id="tonGiao0">${HSTrung1.religion_O}</label></div>
			</div>
			<div class="col-md-4 col-sm-4">
				<div>Ngày sinh: <label id="ngaySinh0">${HSTrung1.dob_O}</label></div>
				<div>Nơi cấp: <label id="noiCap0">${HSTrung1.addressNin_O}</label></div>
				<div>Số điện thoại: <label id="soDienThoai0">${HSTrung1.phone_O}</label></div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div>Địa chỉ thường trú: <label id="thuongTru0">${HSTrung1.address_O}</label></div>
			</div>
		</fieldset>
		<fieldset>
			<legend>Thông tin hồ sơ</legend>
			<div class="form-group">
				<label class="col-md-4">Số hồ sơ: <label>${HSTrung1.transactionId_O}</label>
				</label>
				<label class="col-md-4">Số biên nhận: <label>${HSTrung1.packageId_O}</label>
				</label>
				<label class="col-md-4">Loại hồ sơ: <label>${HSTrung1.typeTransaction_O}</label>
				</label>
			</div>
			<div class="form-group">
				<label class="col-md-4">Đơn vị tiếp nhận: <label>${HSTrung1.reg_O}</label>
				</label>
				<label class="col-md-8">Tình trạng: <label>${HSTrung1.status_O}</label>
				</label>
				
			</div>
			<div class="form-group">
				<label class="col-md-12">Nội dung đề xuất: <label>${HSTrung1.note_O}</label>
				</label>
			</div>
			<div class="form-group">
				<label class="col-md-4">Lãnh đạo phê duyệt: <label>${HSTrung1.officerMasterApprove_O}</label>
				</label>
				<label class="col-md-8">Chức vụ: <label>${HSTrung1.position_O}</label>
				</label>
				
			</div>
			<div class="form-group">
				<label class="col-md-4">Số hộ chiếu: <label>${HSTrung1.passportNo_O}</label>
				</label>
				<label class="col-md-8">Ngày cấp: <label>${HSTrung1.issueDatePassport_O}</label>
				</label>
				
			</div>
			<div class="form-group">
				<label class="col-md-4">Ngày trả: <label>${HSTrung1.payPlacePassport_O}</label>
				</label>
				<label class="col-md-4">Nơi trả: <label>${HSTrung1.payPlacePassport_O}</label>
				</label>
				<label class="col-md-4">Người nhận: <label>${HSTrung1.personRecieve_O}</label>
				</label>
			</div>
			<div class="form-group">
				<label class="col-md-12">Ghi chú: <label>${HSTrung1.remark_O}</label>
				</label>
			</div>
		</fieldset>
		<fieldset>
			<legend>Thông tin nhân thân</legend>
			<div class="form-group">
				<label class="col-md-4">Cha: <label>${HSTrung1.fullNameFather_O}</label>
				</label>
				<label class="col-md-8">Ngày sinh: <label>${HSTrung1.dobFather_O}</label>
				</label>				
			</div>
			<div class="form-group">
				<label class="col-md-4">Mẹ: <label>${HSTrung1.fullNameMother_O}</label>
				</label>
				<label class="col-md-8">Ngày sinh: <label>${HSTrung1.dobMother_O}</label>
				</label>
				
			</div>
			<div class="form-group">
				<label class="col-md-4">Vợ/Chồng: <label>${HSTrung1.fullNameSpouser_O}</label>
				</label>
				<label class="col-md-4">Ngày sinh: <label>${HSTrung1.dobSpouser_O}</label>
				</label>
				<label class="col-md-4">Giới tính: <label>${HSTrung1.genderS_O}</label>
				</label>
			</div>
			<c:forEach items="${HSTrung1.dsSon_O}" var="son">
				<div class="form-group">
					<label class="col-md-4">Con: <label>${son.name}</label>
					</label>
					<label class="col-md-4">Ngày sinh: <label>${son.dateOfBirth}</label>
					</label>
					<label class="col-md-2">Giới tính: <label>${son.gender}</label>
					</label>
					<label class="col-md-2">Nơi sinh: <label>${son.placeOfBirthId}</label>
					</label>
				</div>
			</c:forEach>
		</fieldset>
					
					      
		
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
	
	

</script>





