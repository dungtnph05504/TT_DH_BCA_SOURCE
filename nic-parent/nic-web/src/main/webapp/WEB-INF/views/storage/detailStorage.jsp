<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
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
           <div class="col-sm-12" id="PersonInfo">
               <h4 style="text-align:left;">Thông tin đối tượng</h4>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Họ tên</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.fullName}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Đã lưu dữ liệu sinh trắc</label>
                   <div class="col-sm-8" style="padding-top:5px;">
                       <c:choose>
                       		<c:when test="${root.styleFP == 0}">
                       			<input type="checkbox" />
                       		</c:when>
                       		<c:otherwise>
                       			<input type="checkbox" checked="checked"/>
                       		</c:otherwise>
                       </c:choose>
                   </div>
               </div>
               <div class="clearfix"></div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Giới tính</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.genderDesc}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Ngày sinh</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1"  value="${root.dob}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Số CMND/CCCD</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.nin}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Ngày cấp</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.ngayCapCMND}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Dân tộc</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.danToc}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Tôn giáo</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.tonGiao}">
                   </div>
               </div>
           </div>

           <div class="col-sm-12">
               <h4 style="text-align:left;">Thông tin hộ chiếu</h4>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Số hộ chiếu</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.soHC}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Là hộ chiếu điện tử</label>
                   <div class="col-sm-8" style="padding-top:5px;">
                       <c:choose>
                       		<c:when test="${root.stylePP == 1}">
                       			<input type="checkbox" />
                       		</c:when>
                       		<c:otherwise>
                       			<input type="checkbox" checked="checked"/>
                       		</c:otherwise>
                       </c:choose>
                   </div>
               </div>
               <div class="form-group col-sm-12">
                   <label class="control-label col-sm-2" style="padding-right:5px;">Icao line 1</label>
                   <div class="col-sm-10" style="padding-left:10px;font-family: monospace;font-weight: 600;">
                       <input type="text" class="form-control-1" style="width: 855px !important;font-size: 14px;" value="${root.icao1}"/>
                   </div>
               </div>
               <div class="form-group col-sm-12">
                   <label class="control-label col-sm-2" style="padding-right:5px;">Icao line 2</label>
                   <div class="col-sm-10" style="padding-left:10px;font-family: monospace;font-weight: 600;">
                       <input type="text"  class="form-control-1" style="width: 855px !important;font-size: 14px;" value="${root.icao2}"/>
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Loại hộ chiếu</label>
                   <div class="col-sm-8">
                       <input type="text"  class="form-control-1" value="${root.loaiHC}" />
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4"> Mã CHIP</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" />
                   </div>
               </div>

               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Ngày cấp</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.ngayCapHC}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Ngày hết hiệu lực</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.ngayHetHan}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Nơi cấp</label>
                   <div class="col-sm-8">
                       <input type="text"  class="form-control-1" value="${root.noiCap}">
                   </div>

               </div>
               <div class="clearfix"></div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Người ký</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.nguoiKy}">
                   </div>
               </div>
               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Chức vụ</label>
                   <div class="col-sm-8">
                       <input type="text" class="form-control-1" value="${root.chucVu}">
                   </div>
               </div>

               <div class="form-group col-sm-6">
                   <label class="control-label col-sm-4">Trạng thái</label>
                   <div class="col-sm-8">
                       <input type="text"  class="form-control-1" value="${root.trangThai}">
                   </div>
               </div>
               <div class="form-group col-sm-12">
                   <label class="control-label col-sm-2" style="padding-right:5px;">Ghi chú</label>
                   <div class="col-sm-10" style="padding-left:10px;">
                       <textarea class="form-control-1" style="pointer-events: none;width: 855px;" rows="2">${root.ghiChu}</textarea>
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


</script>





