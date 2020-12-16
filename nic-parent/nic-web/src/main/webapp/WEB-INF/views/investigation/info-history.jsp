<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>
#cp_tttn, #cp_tttre, #cp_ate {
	transition: height 1s;
	-webkit-transition: height 1s;
	height: 0px;
}
#tbThanNhan {
	display: none;
}
</style>

<div class="new-heading-2">CHI TIẾT HỒ SƠ HỘ CHIẾU</div>
<div class="col-md-12 col-md-12">
	<fieldset style="padding: 10px 10px;">
		<legend>Thông tin hồ sơ</legend>
		<div class="col-md-9">
			<div class="col-md-3 col-sm-3 top-mag-10">
				<div>Họ tên</div>
				<input type="text" value="${HSTrung1.fullName_O}" id="cp_hoten" readonly="readonly" />											
			</div>
			<div class="col-md-3 col-sm-3 top-mag-10">
				<div>Ngày sinh</div>
				<input type="text" value="${HSTrung1.dob_O}" id="cp_ngaysinh" readonly="readonly" />												
			</div>
			<div class="col-md-3 col-sm-3 top-mag-10">
				<div>Giới tính</div>
				<input type="text" value="${HSTrung1.gender_O}" id="cp_gt" readonly="readonly" />
			</div>
			<div class="col-md-3 col-sm-3 top-mag-10">
				<div>CMND/CCCD</div>
				<input type="text" value="${HSTrung1.nin_O}" id="cp_cmnd" readonly="readonly" />
			</div>
			<div class="col-md-5 top-mag-10">
				<div>Nơi cấp</div>
				<input type="text" value="${HSTrung1.addressNin_O}" id="cp_noicap" style="width: 100%;" readonly="readonly" />
			</div>
			<div class="col-md-7 top-mag-10">
				<div>Nơi thường trú</div>
				<input type="text" value="${HSTrung1.address_O}" id="cp_thuongtru" style="width: 100%;" readonly="readonly" />
			</div>
			<div class="col-md-3 col-sm-3 top-mag-10">
				<div>Nghề nghiệp</div>
				<input type="text" value="${HSTrung1.job_O}" id="cp_nghenghiep" readonly="readonly" />											
			</div>
			<div class="col-md-2 col-sm-2 top-mag-10">
				<div>Số điện thoại</div>
				<input type="text" value="${HSTrung1.phone_O}" id="cp_sdt" style="width: 100%;" readonly="readonly" />												
			</div>
			<div class="col-md-3 col-sm-3 top-mag-10">
				<div>Số DS A</div>
				<input type="text" value="${HSTrung1.danhSachA_O}" id="cp_SoDSA" readonly="readonly" />
			</div>
			<div class="col-md-2 col-sm-2 top-mag-10">
				<div>Ngày lập DS</div>
				<input type="text" value="${HSTrung1.dateA_O}" style="width: 100%;" id="cp_ngaylapds" readonly="readonly" />
			</div>
			<div class="col-md-2 col-sm-2 top-mag-10">
				<div>Người lập DS</div>
				<input type="text" value="${HSTrung1.byA_O}" id="cp_nguoilapds" readonly="readonly" />
			</div>
		</div>
		<div class="col-md-3">
			<div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;margin-left: 50px;">
				<div class="centerCaption">       
			         <div style="text-align:center;" id="anhMatXNC">	
			 							             									                 
						    <c:choose>
			                    <c:when test="${not empty HSTrung1.image_O}">
			                        <div id="coAnhMat">
			                            <img src="data:image/jpg;base64,${HSTrung1.image_O}"
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
		<div class="col-md-12">
			<div>
				<div>
					<div style="background: #ddd;padding: 2px 10px;">
						<h4> Thông tin trẻ em
							<a href="#" id="cp_hienThiTreEm" style="float: right;"><span class="glyphicon glyphicon-menu-right"></span></a>
							<a href="#" id="cp_dongTreEm" style="display: none;float: right;"><span class="glyphicon glyphicon-menu-down"></span></a>
						</h4>
					</div>
				</div>
				<div class="col-md-10 col-sm-10" id="cp_tttre">
					<div style="overflow: auto;max-height: 200px;">
						<table id="tbTreEm" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
						  <thead>
						    <tr>
						      <th class="th-sm">Họ tên
						
						      </th>											 
						      <th class="th-sm">Giới tính
						
						      </th>
						      <th class="th-sm">Ngày sinh
						
						      </th>										     
						      <th class="th-sm">Nơi sinh
						
						      </th>										     									     
						    </tr>
						  </thead>
						  <!--<c:if test="${not empty chiTietDS}">-->
							  <tbody>
							    <c:forEach items="${dsTreEm}" var="item">
								    <tr>												      	
								      	<td></td>
								      	<td></td>
								      	<td></td>
								      	<td></td>																													     
								    </tr>
							    </c:forEach>
							  </tbody>
						 <!-- </c:if>-->
						</table>
						
					</div>
				</div>
				<div class="col-md-2 col-sm-2" id="cp_ate">
					<div id="dialog-image_photoCompare" style="display: block;width: 135px;height: 180px;">
						<div class="centerCaption">       
					         <div style="text-align:center" id="cp_anhtreem">							             									                 
								   							       					                       										                  								                    									               
					         </div>								        
					    </div>
				    </div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div>
				<div>
					<div style="background: #ddd;padding: 2px 10px;">
						<h4> Thông tin nhân thân
							<a href="#" id="cp_hienThiThanNhan" style="float: right;"><span class="glyphicon glyphicon-menu-right"></span></a>
							<a href="#" id="cp_dongThanNhan" style="display: none;float: right;"><span class="glyphicon glyphicon-menu-down"></span></a>
						</h4>
					</div>
				</div>
				<div class="col-md-12 col-sm-12" id="cp_tttn">
					<div style="overflow: auto;max-height: 200px;">
						<table id="tbThanNhan" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
						  <thead>
						    <tr>
						      <th class="th-sm">Họ tên
						
						      </th>											 
						      <th class="th-sm">Ngày sinh
						
						      </th>
						      <th class="th-sm">Quan hệ
						
						      </th>										     
						      <th class="th-sm">Giới tính
						
						      </th>	
						      <th class="th-sm">Tình trạng
						
						      </th>									     									     
						    </tr>
						  </thead>
						  <!--<c:if test="${not empty chiTietDS}">-->
							  <tbody>
							    <c:forEach items="${dsThanNhan}" var="item">
								    <tr>												      	
								      	<td>${item.fullName_F}</td>
								      	<td>${item.dob_F}</td>
								      	<td>${item.stage_F}</td>
								      	<td>${item.gender_F}</td>
								      	<td>${item.toFamilly}</td>																													     
								    </tr>
							    </c:forEach>
							  </tbody>
						 <!-- </c:if>-->
						</table>
							
					</div>
				</div>
			</div>
		</div>
		<div class="col-sm-12">
             <div class="col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Người ĐX</label>
                     <input type="text" class="form-control" >
                 </div>
             </div>
             <div class="col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Ngày ĐX</label>
                     <input type="text" class="form-control">
                 </div>
             </div>
             <div class="col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Người duyệt</label>
                     <input type="text" class="form-control">
                 </div>
             </div>
             <div class="col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Ngày duyệt</label>
                     <input type="text" class="form-control">
                 </div>
             </div>
             <div class="col-sm-4">
                 <div class="epp-hc">
                     <label class="radio-inline e-radio">
                         <input type="radio" class="epp-radio">Mẫu chi tiết
                     </label>
                     <label class="radio-inline e-radio">
                         <input type="radio" class="epp-radio">Mẫu trao đổi cơ quan
                     </label>
                 </div>
             </div>
         </div>
         <div class="col-sm-7">
             <div class="col-sm-12">
                 <div class="epp-form-group">
                     <label class="control-label">Nội dung đề xuất</label>
                     <textarea class="form-control"></textarea>
                 </div>
             </div>
             <div class="col-sm-12">
                 <div class="epp-form-group">
                     <label class="control-label">Ý kiến phê duyệt</label>
                     <textarea class="form-control"></textarea>
                 </div>
             </div>
             <div class="col-sm-4">
                 <div class="epp-form-group">
                     <label class="control-label">Số danh sách C</label>
                     <input type="text" class="form-control">
                 </div>
             </div>
             <div class="col-sm-4">
                 <div class="epp-form-group">
                     <label class="control-label">Ngày in DS C</label>
                     <input type="text" class="form-control">
                 </div>
             </div>
             <div class="col-sm-4">
                 <div class="epp-form-group">
                     <label class="control-label">Ngày lập DS C</label>
                     <input type="text" class="form-control">
                 </div>
             </div>
             <div class="col-sm-12">
                 <div class="epp-form-group">
                     <label class="control-label">Ghi chú</label>
                     <textarea class="form-control"></textarea>
                 </div>
             </div>
             <fieldset class="scheduler-border">
                 <legend class="scheduler-border">Thông tin biên nhận</legend>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Số CV</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Ngày CV</label>
                         <input type="text" class="form-control" >
                     </div>
                 </div>
                 <div class="col-sm-12">
                     <div class="epp-form-group">
                         <label class="control-label">Tên đơn vị</label>
                         <input type="text" class="form-control" >
                     </div>
                 </div>
                 <div class="col-sm-12">
                     <div class="epp-form-group">
                         <label class="control-label">Đ/C đơn vị</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
             </fieldset>
         </div>
         <div class="col-sm-5">
             <fieldset class="scheduler-border">
                 <legend class="scheduler-border">Thông tin hộ chiếu</legend>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Số HC</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Ngày cấp</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Seri</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Thời hạn</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-12">
                     <div class="epp-form-group">
                         <label class="control-label">Nơi cấp</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Loại</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-6">
                     <div class="epp-form-group">
                         <label class="control-label">Tình trạng</label>
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-12">
                     <div class="epp-form-group">
                         <input type="text" class="form-control">
                     </div>
                 </div>
                 <div class="col-sm-12">
                     <div class="epp-form-group">
                         <input type="text" class="form-control">
                     </div>
                 </div>
             </fieldset>
         </div>
         <div class="col-sm-12">
             <div class="col-sm-2 e-col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Tình trạng hồ sơ</label>
                     <div>
                         <label class="control-label"></label>
                     </div>
                 </div>
             </div>
             <div class="col-sm-2 e-col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Người tra ĐT</label>
                     <div>
                         <label class="control-label"></label>
                     </div>
                 </div>
             </div>
             <div class="col-sm-2 e-col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Ngày tra</label>
                     <div>
                         <label class="control-label"></label>
                     </div>
                 </div>
             </div>
             <div class="col-sm-2 e-col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Người lập</label>
                     <div>
                         <label class="control-label"></label>
                     </div>
                 </div>
             </div>
             <div class="col-sm-2 e-col-sm-2">
                 <div class="epp-form-group">
                     <label class="control-label">Ngày lập</label>
                     <div>
                         <label class="control-label"></label>
                     </div>
                 </div>
             </div>
         </div>
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
$(function(){
	$('#cp_hienThiTreEm').click(function(){
		$('#cp_dongTreEm').css('display', 'block');
		$('#cp_hienThiTreEm').css('display', 'none');
		$('#cp_tttre').css('height', '200px');
		$('#cp_ate').css('height', '180px');
	})
	
	$('#cp_dongTreEm').click(function(){
		$('#cp_dongTreEm').css('display', 'none');
		$('#cp_hienThiTreEm').css('display', 'block');
		$('#cp_tttre').css('height', '0px');
		$('#cp_ate').css('height', '0px');
	})
	
	$('#cp_hienThiThanNhan').click(function(){
		$('#tbThanNhan').css('display', 'inline-table');
		$('#cp_dongThanNhan').css('display', 'block');
		$('#cp_hienThiThanNhan').css('display', 'none');
		$('#cp_tttn').css('height', '200px');
	})
	
	$('#cp_dongThanNhan').click(function(){
		$('#cp_dongThanNhan').css('display', 'none');
		$('#cp_hienThiThanNhan').css('display', 'block');
		$('#cp_tttn').css('height', '0px');
		$('#tbThanNhan').css('display', 'none');
	}) 	
	
})	

</script>





