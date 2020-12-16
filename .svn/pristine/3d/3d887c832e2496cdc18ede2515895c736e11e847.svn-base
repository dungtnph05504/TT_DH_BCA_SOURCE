<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:url var="saveUrl" value="/servlet/siteMgmt/saveSiteGroup"/>
<c:url var="url" value="/servlet/persoLocation/persoLocations"/>
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
/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}

/* Anytime the body has the loading class, our
   modal element will be visible */

.pding-left {
	padding-left: 10px;
}
.fix-bottom-1 {
	margin-top: 15px;
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
				<div class="col-md-4 fix-bottom-1">
					
					<label for="countryCode">Mã trung tâm<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<c:choose>
			   			 <c:when test='${not empty locationForm.code}'>
			   			 	<form:input id="code" path="code" class="form-control" type="text" size='50' maxlength="50" disabled="true"/>
			   			 </c:when>
			   			 <c:otherwise>
			   			 	<form:input id="code" path="code" class="form-control" type="text" size='50' maxlength="50"/>
			   			 </c:otherwise>
		   			</c:choose>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tên trung tâm</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="name" path="name" class="form-control" type="text" size='100' />
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Địa chỉ</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:input  id="address" class="form-control" path="address" type="text" size='50' />
				</div>				
			 			
			 </div>
			 <div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">ID Trung tâm<i style="color: red;padding: 5px;">*</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<c:choose>
						<c:when test="${not empty locationForm.idPerso}">
							<form:input  id="idPerso" class="form-control" disabled="true" path="idPerso" type="text" size='5' />
						</c:when>
						<c:otherwise>
							<form:input  id="idPerso" class="form-control" path="idPerso" type="text" size='5' />						
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Trạng thái</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<form:select id="status" path="status" class="form-control">
						<form:option value="1" label="Đang hoạt động"/>
						<form:option value="0" label="Không hoạt động"/>
	   	        	</form:select>
				</div>		
			 	<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Ghi chú</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:textarea path="description" rows="4" style="width:100%;"/>
				</div>
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
function checkDigit(){
	var mes = '';
	if($("#idPerso").val().trim() != '' && isNaN($("#idPerso").val())){
		mes = 'Vui lòng nhập giá trị số nguyên.';
	}
	return mes;
}	
$("#save_btn").click(function(){
	var flag = 0;
	var err_msg = '';
	if ($("#code").val() == "") {
		flag = flag + 1;	
		err_msg = 'Mã trung tâm không được bỏ trống!';

	}
	if ($("#idPerso").val() == "") {
		flag = flag + 1;	
		err_msg = 'ID trung tâm không được bỏ trống!';

	}
	var mes = checkDigit();
	if(mes != ''){
		flag = flag + 1;	
		err_msg = mes;
	}
	if (flag > 0) {			

		$.notify(err_msg, {
			globalPosition: 'bottom left',
			className: 'warn',
		});
	}else{			
		$('#ajax-load-qa').show();		
		$.post('<c:url value="/servlet/persoLocation/saveLocation" />',$('#locationForm').serialize(),
		    function(data){
			$('#ajax-load-qa').hide();
				if(data=='success'){
					$.notify('Đã lưu thành công trung tâm: ' + $("#code").val(), {
						globalPosition: 'bottom left',
						className: 'success',
					});
				} else if(data=='fail'){
					$.notify('Không thể lưu trung tâm: ' + $("#code").val(), {
						globalPosition: 'bottom left',
						className: 'warn',
					});
				}else if(data=='exist'){
					$.notify('Mã hoặc ID Trung tâm đã tồn tại trong hệ thống: ' + $("#code").val(), {
						globalPosition: 'bottom left',
						className: 'warn',
					});
				}
		 });
	} 
});
$("#cancel_btn").click(function(){
	document.forms["locationForm"].action = '${url}';
	document.forms["locationForm"].submit();
	
});


function updateForm() {
	$('#ajax-load-qa').show();
	$("#code").attr('disabled', false);
	$("#idPerso").attr('disabled', false);
	$.post('<c:url value="/servlet/persoLocation/updateLocation" />',$('#locationForm').serialize(),
	     function(data){
			 $('#ajax-load-qa').hide();	
			 if(data=='success'){
					
				    $.notify('Trung tâm được cập nhật thành công: ' + $("#code").val(), {
						globalPosition: 'bottom left',
						className: 'success',
					});
			  } else if(data=='fail'){
				  
				  $.notify('Không cập nhật được trung tâm: ' + $("#code").val(), {
						globalPosition: 'bottom left',
						className: 'warn',
				  });
			  }
		 }); 	
	
	 $("#code").attr('disabled', true);
	 $("#idPerso").attr('disabled', false);
}



</script>