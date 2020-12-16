<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="getImgTranUrl" value="/servlet/storage/getFacialById" />
<style>
.form-control-1 {
	height: 24px;
    font-size: 12px;
    border-radius: 0px;
    outline: none;
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
    border-color: rgba(0, 0, 0, 0.44);
    color: rgba(0,0,0,0.87);
    
}
.form-horizontal div input[type="text"] {
	width: 100% !important;
	pointer-events: none;
}
</style>

<div class="form-horizontal">
              <div class="col-sm-12" style="padding-right:0px; padding-left:0px;">
                  <ul class="nav nav-tabs">
                      <li class="active"><a data-toggle="tab" href="http://192.168.1.15:8084/DanhSachHoSo/Index#home" aria-expanded="true">Thông tin hồ sơ</a></li>
                      <li class=""><a data-toggle="tab" href="http://192.168.1.15:8084/DanhSachHoSo/Index#menu1" aria-expanded="false">Phí đã thanh toán</a></li>
                      <li class=""><a data-toggle="tab" href="http://192.168.1.15:8084/DanhSachHoSo/Index#menu2" aria-expanded="false">Danh sách</a></li>
                      <li class=""><a data-toggle="tab" href="http://192.168.1.15:8084/DanhSachHoSo/Index#menu3" aria-expanded="false">Thông tin đính kèm</a></li>
                      <li class=""><a data-toggle="tab" href="http://192.168.1.15:8084/DanhSachHoSo/Index#menu4" aria-expanded="false">Thông tin đề xuất</a></li>
                  </ul>

                  <div class="tab-content">
                      <div id="home" class="tab-pane fade active in">
                          <div class="col-sm-12 form-profile">
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Mã hồ sơ</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.transactionId}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Số biên nhận</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.recieptNo}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Hộ chiếu</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.passportNo}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Họ tên</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.fullName}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Ngày sinh</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.dob}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Nơi sinh</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.placeOfBirth}">
                                  </div>
                              </div>

                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Số CMND/CCCD</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.nin}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Ngày Cấp</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.dateNin}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Nơi Cấp</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.addressNin}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Giới tính</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.gender}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-8">
                                  <label class="control-label col-sm-3" style="margin-right: -29px;padding-right: 37px;">Địa chỉ thường trú</label>
                                  <div class="col-sm-9" style="padding-left: 8px;">
                                      <input type="text" class="form-control" value="${root.address1}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Số điện thoại</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.phone}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-8">
                                  <label class="control-label col-sm-3" style="margin-right: -29px;padding-right: 37px;">Địa chỉ tạm trú</label>
                                  <div class="col-sm-9" style="padding-left: 8px;">
                                      <input type="text" class="form-control" value="${root.address2}">
                                  </div>
                              </div>

                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">HC cấp gần nhất</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.passportBefore}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Ngày cấp gần nhất</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.dateIssBefore}">
                                  </div>
                              </div>
                              <div class="clearfix"></div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Nghề nghiệp</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.job}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-8">
                                  <label class="control-label col-sm-3" style="margin-right: -29px;padding-right: 37px;">Địa chỉ cơ quan</label>
                                  <div class="col-sm-9" style="padding-left: 8px;">
                                      <input type="text" class="form-control" value="${root.addressCompany}">
                                  </div>
                              </div>

                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">HC điện tử</label>
                                  <div class="col-sm-7" style="padding-top:5px;">
                                       <c:choose>
				                       		<c:when test="${root.stylePP == 0}">
				                       			<input type="checkbox" />
				                       		</c:when>
				                       		<c:otherwise>
				                       			<input type="checkbox" checked="checked"/>
				                       		</c:otherwise>
				                       </c:choose>
                                  </div>
                              </div>
                              <div class="form-group col-sm-8">
                                  <label class="control-label col-sm-3" style="margin-right: -29px;padding-right: 37px;">Ghi chú</label>
                                  <div class="col-sm-9" style="padding-left: 8px;">
                                      <input type="text" class="form-control">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Nơi tiếp nhận </label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.ricName}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Ngày hẹn trả </label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.esColectionDate}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Nơi trả </label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.placePassport}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Độ ưu tiên</label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.priorityDesc}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-8">
                                  <label class="control-label col-sm-3" style="margin-right: -29px;padding-right: 37px;">Nội dung đề nghị</label>
                                  <div class="col-sm-9" style="padding-left: 8px;">
                                      <input type="text" class="form-control" value="">
                                  </div>
                              </div>
                              <div class="form-group col-sm-4">
                                  <label class="control-label col-sm-5">Trạng thái </label>
                                  <div class="col-sm-7">
                                      <input type="text" class="form-control" value="${root.transactionStatus}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-8">
                                  <label class="control-label col-sm-3" style="margin-right: -29px;padding-right: 37px;">Thông tin thêm</label>
                                  <div class="col-sm-9" style="padding-left: 8px;">
                                      <input type="text" class="form-control" value="">
                                  </div>
                              </div>
                          </div>
                      </div>

                      <div id="menu1" class="tab-pane fade">
                          <div class="col-sm-12">
                              <table class="table e-grid-table table-bordered table-hover">
                                  <thead class="table-thead">
                                      <tr>
                                          <th class="e-cursor-pointer text-center">Tên</th>
                                          <th class="e-cursor-pointer text-center">Số tiền</th>
                                          <th class="e-cursor-pointer text-center">Mô tả</th>
                                      </tr>
                                  </thead>

                                  <tbody class="table-body">
                                  	 <c:forEach items="${root.infoStorage}" var="ite">
	                                      <tr>
	                                          <td>${ite.tenPhi}</td>
	                                          <td align="right">${ite.soTien}</td>
	                                          <td></td>
	                                      </tr>                                    
                                  	 </c:forEach>		
                                  </tbody>
                              </table>
                          </div>
                      </div>
                      <div id="menu2" class="tab-pane fade">
                          <div class="col-sm-12">
                              <table class="table e-grid-table table-bordered table-hover">
                                  <thead class="table-thead">
                                      <tr>
                                          <th class="e-cursor-pointer text-center">Mã</th>
                                          <th class="e-cursor-pointer text-center">Loại</th>
                                          <th class="e-cursor-pointer text-center">Cơ quan</th>
                                          <th class="e-cursor-pointer text-center">Người ký</th>
                                          <th class="e-cursor-pointer text-center">Chức vụ</th>
                                          
                                      </tr>
                                  </thead>
                                  <tbody class="table-body">
                                  	 <c:forEach items="${root.infoList}" var="ite">
                                      <tr>
                                          <td>${ite.maDS}</td>
                                          <td>
                                             ${ite.loạiDS}
                                          </td>
                                          <td>${ite.coQuan}</td>
                                          <td>${ite.nguoiKy}</td>
                                          <td>${ite.chucVu}</td>
                                          
                                      </tr>                                     
                                      </c:forEach>
                                  </tbody>
                              </table>
                          </div>
                      </div>
                      <div id="menu3" class="tab-pane fade">
                          <div class="col-sm-9">
                              <table class="table e-grid-table table-bordered table-hover">
                                  <thead class="table-thead">
                                      <tr>
                                          <th class="e-cursor-pointer text-center">Tên file</th>
                                          <th class="e-cursor-pointer text-center">Loại</th>
                                          <th class="e-cursor-pointer text-center">Số thứ tự</th>
                                          <th class="e-cursor-pointer text-center">Mô tả</th>
                                      </tr>
                                  </thead>
                                  <tbody class="table-body">
                                  	<c:forEach items="${root.infoAttach}" var="ite">
                                      <tr>
                                          <td>${ite.tenFile}</td>
                                          <td>
                                              
                                              <c:choose>
                                              	 <c:when test="${ite.tenFile == 'PH_CAP' || ite.tenFile == 'SCAN_DOCUMENT'}">
                                              	 	  <a href="#" onclick="hienThiAnh('${ite.transactionId}','${ite.tenFile}');"><i class="glyphicon glyphicon-eye-open"></i>${ite.loaiFile}</a>
                                              	 </c:when>
                                              	 <c:otherwise>
		                                              ${ite.loaiFile}
                                              	 </c:otherwise>
                                              </c:choose>
                                          </td>
                                          <td align="right">${ite.stt}</td>
                                          <td>${ite.moTaFile}</td>
                                      </tr>
                                    </c:forEach>
                                  </tbody>
                              </table>
                          </div>
                          <div class="col-sm-3">
                          		 <c:choose>
				                    <c:when test="${not empty root.imgPhoto}">
				                    	<div id="imgShow">
				                            <img src="data:image/jpg;base64,${root.imgPhoto}"
				                                 class="img-border doGetAJpgSafeVersion" height="250px" width="200px" />
				                    	</div>
				                    </c:when>
				                    <c:otherwise>
				                        <div id="imgShow">
				                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="250px" width="200px" />										                           
				                        </div>
				                    </c:otherwise>
				                </c:choose>		
                          </div>
                      </div>
                      <div id="menu4" class="tab-pane fade">
                          <div class="col-sm-12">
                              <div class="form-group col-sm-12">
                                  <label class="control-label col-sm-2">Loại đề xuất</label>
                                  <div class="col-sm-10">
                                      <input type="text" class="form-control" value="${root.typeApprove}">
                                  </div>
                              </div>
                              <div class="clearfix"></div>
                              <div class="form-group col-sm-12">
                                  <label class="control-label col-sm-2">Người phê duyệt</label>
                                  <div class="col-sm-10">
                                      <input type="text" class="form-control" value="${root.leaderId}">
                                  </div>
                              </div>
                              <div class="clearfix"></div>
                              <div class="form-group col-sm-12">
                                  <label class="control-label col-sm-2">Chức vụ</label>
                                  <div class="col-sm-10">
                                      <input type="text" class="form-control" value="${root.position}">
                                  </div>
                              </div>
                              <div class="form-group col-sm-12">
                                  <label class="control-label col-sm-2">Nội dung đề xuất</label>
                                  <div class="col-sm-10">
                                      <textarea class="form-control" rows="3">${root.leaderNote}</textarea>
                                  </div>
                              </div>
                              <table class="table e-grid-table table-bordered table-hover">
                                  <thead class="table-thead">
                                      <tr>
                                          <th class="e-cursor-pointer text-center">Tên</th>
                                          <th class="e-cursor-pointer text-center">Số tiền</th>
                                          <th class="e-cursor-pointer text-center">Mô tả</th>
                                      </tr>
                                  </thead>
                                  <tbody class="table-body">
                                      <c:forEach items="${root.infoStorage}" var="ite">
	                                      <tr>
	                                          <td>${ite.tenPhi}</td>
	                                          <td align="right">${ite.soTien}</td>
	                                          <td></td>
	                                      </tr>                                    
                                  	 </c:forEach>	
                                  </tbody>
                              </table>
                          </div>
                      </div>
                  </div>
              </div>
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

		function hienThiAnh(bien1, bien2) {
			//alert('${getImgTranUrl}');
			var urlImg = '${getImgTranUrl}'
			//alert(urlImg);
			
			$.ajax({
				url : urlImg,
				cache: false,
				type: "GET",
				data : {
					tranactionId: bien1,
					type: bien2
				},
				success : function(data) {
					$('#imgShow').html(data);		
				}
			});
		}	
		


</script>





