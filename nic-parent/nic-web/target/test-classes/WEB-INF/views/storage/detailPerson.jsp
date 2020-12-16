<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<style>
.form-control-1-1 {
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
         <div class="col-sm-12">
             <h4 style="text-align:left;">Thông tin công dân</h4>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Họ tên</label>
                 <div class="col-sm-8">
                     <input type="text" class="form-control-1" value="${root.fullName}">
                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Giới tính</label>
                 <div class="col-sm-8 gender">
                     <input type="text" class="form-control-1" value="${root.gender}">
                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Ngày sinh</label>
                 <div class="col-sm-8">
                     <input type="text" class="form-control-1" value="${root.dob}">
                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Nơi sinh</label>
                 <div class="col-sm-8">
                     <input type="text" class="form-control-1" value="${root.placeOfBirth}">

                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Dân tộc</label>
                 <div class="col-sm-8 gender">
                     <input type="text" class="form-control-1" value="${root.nation}">
                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Tôn giáo</label>
                 <div class="col-sm-8 gender">
                     <input type="text" class="form-control-1" value="${root.religion}">
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
                     <input type="text" class="form-control-1" value="${root.dateNin}">
                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Nơi cấp</label>
                 <div class="col-sm-8">
                     <input type="text" class="form-control-1" value="${root.addressNin}">
                 </div>
             </div>
             <div class="form-group col-sm-6">
                 <label class="control-label col-sm-4">Đã lưu dữ liệu sinh trắc</label>
                 <div class="col-sm-8" style="padding-top:5px;">
                     <c:choose>
                     	<c:when test="${root.stageList == 'Y'}">
                     		<input type="checkbox" checked="checked">
                     	</c:when>
                     	<c:otherwise>
                     		<input type="checkbox">
                     	</c:otherwise>
                     </c:choose>
                 </div>
             </div>
         </div>
         <div class="col-sm-12" style="padding-top:10px;">
             <h4 style="text-align:left;">Thông tin đính kèm</h4>
             <c:forEach items="${root.imgList}" var="item">
             	 <c:choose>
                    <c:when test="${not empty item}">
                            <img src="data:image/jpg;base64,${item}"
                                 class="img-border doGetAJpgSafeVersion" height="130px" width="90px" />
                    </c:when>
                    <c:otherwise>
                      
                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="130px" width="90px" />										                           
                    </c:otherwise>
                </c:choose>	
             </c:forEach>
         </div>
         <div class="col-sm-2">
         </div>
         <div class="col-sm-12" style="padding-top:20px;">
             <h4 style="text-align:left;">Thân nhân</h4>
         </div>
         <div class="col-sm-12">
             <table class="table e-grid-table table-bordered table-hover">
                 <thead class="table-thead">
                     <tr>
                         <th class="e-cursor-pointer text-center" style="width:50px;">STT</th>
                         <th class="e-cursor-pointer text-center">Họ và tên</th>
                         <th class="e-cursor-pointer text-center">Giới tính</th>
                         <th class="e-cursor-pointer text-center">Ngày sinh</th>
                         <th class="e-cursor-pointer text-center">Quan hệ</th>
                     </tr>
                 </thead>
                 <tbody class="table-body">
                 	 <% int i = 0; %>	
                 	 <c:forEach items="${root.infoFamily}" var="items">
                 	 	  <%i++; %>		
	                     <tr>
	                         <td align="center"><%=i %></td>
	                         <td>${items.fullName_F}</td>
	                         <td>
	                             ${items.gender_F}
	                         </td>
	                         <td align="center">${items.dob_F}</td>
	                         <td>${items.stage_F}</td>
	                     </tr>
                     </c:forEach>
                 </tbody>
             </table>
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





