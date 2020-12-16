<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="saveUrl" value="/servlet/siteMgmt/saveSiteGroup"/>
<c:url var="url" value="/servlet/persoLocation/detailPersoLocations"/>
<c:url var="resetPage" value="/servlet/siteMgmt/editSiteGroup"/>


<style>
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50%
		50% no-repeat;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}
.pding-left {
	padding-left: 10px;
}
.fix-bottom-15 {
	margin-top: 15px;
}
.f-width-100 {
	width: 100%;
}
 </style>
<!-- <div id="main"> -->
<div id="content_main">
<form:form  modelAttribute="locationForm" id="locationForm" name="locationForm" autocomplete="off">	
<div class="container">
        	<div class="row">
        		<div class="ov_hidden">
	<div class="new-heading-2">THÊM/CHỈNH SỬA TRUNG TÂM CÁ THỂ HÓA</div>     
	<div id="global_icon_div"></div>
		<fieldset>
			<legend>Thông tin trung tâm cá thể hóa</legend>
		
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="form-group fix-bottom-15">
					<div class="col-md-4">
						
						<label for="countryCode">Trung tâm cá thể hóa <i style="color: red;padding: 5px;">*</i></label>
					</div>
					<div class="col-md-8">
						<form:select path="idPerso" disabled="true" id="idPerso" class="f-width-100 form-control">
							<form:option value="">-- Chọn --</form:option>
							<c:forEach items="${persoMap}" var="_item">
								<c:choose>
									<c:when test="${idPerso == _item.key}">
										<form:option value="${_item.key}" selected="selected">${_item.value}</form:option>
									</c:when>
									<c:otherwise>
										<form:option value="${_item.key}">${_item.value}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group fix-bottom-15">
					<div class="col-md-4">
						<label for="countryCode">Trung tâm tiếp nhận</label>
					</div>
					<div class="col-md-8">
						<form:select path="code" id="code" class="f-width-100 form-control">
							<form:option value="">-- Chọn --</form:option>
							<c:forEach items="${siteMap}" var="_item">
								<form:option value="${_item.key}">${_item.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group fix-bottom-15">
					<div class="col-md-4">
						<label for="countryCode">Trạng thái</label>
					</div>
					<div class="col-md-8">
						<form:select path="stage" class="f-width-100 form-control">
							<form:option value="Y">Đang hoạt động</form:option>
							<form:option value="N">Không hoạt động</form:option>
						</form:select>
					</div>				
				</div>
			 			
			 </div>
			 <div class="col-md-6">
			 </div>
          </div>
          </fieldset>        
          <div style="display: flex;flex: 0 auto;justify-content: center;">
			<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
				<div style="margin: 10px"> 			
				  <button type="button" class="btn btn-success" id="cancel_btn">
						<span class="glyphicon glyphicon-remove"></span> Hủy
				  </button>							
	           	  <c:if test='${empty locationForm.code}'>
	        	  	<button type="button" class="btn btn-success" id="save_btn">
						<span class="glyphicon glyphicon-ok"></span> Lưu
					</button>     	  	
	        	  </c:if>
	        	  <c:if test='${not empty locationForm.code}'>
	        	  	<button type="button" class="btn btn-success" id="update_btn" onclick="updateForm()">
						<span class="glyphicon glyphicon-ok"></span> Cập nhật
					</button>		      	
			      </c:if>
				</div>
			</div>
		 </div>
   </div>
   </div>
   </div>
   <div id="ajax-load-qa"></div>
   
</form:form>
 </div>

</div>

<script>

$("#save_btn").click(function(){
	var flag = 0;
	var err_msg = '';
	/*if ($("#idPerso").val() == "") {
		flag = flag + 1;	
		err_msg = 'Mã TT cá thể hóa chưa chọn?';

	}*/
	if ($("#code").val() == "") {
		flag = flag + 1;	
		err_msg = 'Trung tâm tiếp nhận chưa chọn?';

	}
	if (flag > 0) {			

		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
	}else{			
		$('#ajax-load-qa').show();	
		$('#idPerso').prop('disabled', false);
		$.post('<c:url value="/servlet/persoLocation/saveSiteLocation" />',$('#locationForm').serialize(),
		    function(data){
			$('#idPerso').prop('disabled', true);
			$('#ajax-load-qa').hide();
				if(data=='success'){
					$.notify('Đã lưu thành công trung tâm cá thể hóa.', {
						globalPosition: 'bottom left',
						className: 'success',
					});
				} else if(data=='fail'){
					$.notify('Không thể lưu trung tâm cá thể hóa.', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
				}else if(data=='exist'){
					$.notify('Trung tâm cá thể hóa đã tồn tại trong hệ thống.', {
						globalPosition: 'bottom left',
						className: 'warn',
					});
				}
		 });
	} 
});
$("#cancel_btn").click(function(){
	document.forms["locationForm"].action = '${url}/${idPerso}';
	document.forms["locationForm"].submit();
	
});


function updateForm() {
	$('#ajax-load-qa').show();
	$('#idPerso').prop('disabled', false);
	$.post('<c:url value="/servlet/persoLocation/updateSiteLocation" />',$('#locationForm').serialize(),
	     function(data){
			 $('#idPerso').prop('disabled', true);	
			 $('#ajax-load-qa').hide();	
			 if(data=='success'){
					
				    $.notify('Trung tâm cá thể hóa được cập nhật thành công.', {
						globalPosition: 'bottom left',
						className: 'success',
					});
			  } else if(data=='fail'){
				  
				  $.notify('Không cập nhật được trung tâm cá thể hóa.', {
						globalPosition: 'bottom left',
						className: 'warn',
				  });
			  }
		 }); 	
	
	
}



</script>